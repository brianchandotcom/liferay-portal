/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.deploy.hot;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletClassLoaderUtil;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.util.BasePortalLifecycle;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PortalLifecycle;
import com.liferay.portal.kernel.util.PortalLifecycleUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import javax.servlet.ServletContext;

/**
 * @author Ivica Cardic
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class HotDeployUtil {

	public static synchronized void fireDeployEvent(
		final HotDeployEvent hotDeployEvent) {

		ServletContext servletContext = hotDeployEvent.getServletContext();

		ServletContextPool.put(
			servletContext.getServletContextName(), servletContext);

		if (_capturePrematureEvents) {

			// Capture events that are fired before the portal initialized

			PortalLifecycle portalLifecycle = new BasePortalLifecycle() {

				@Override
				protected void doPortalDestroy() {
				}

				@Override
				protected void doPortalInit() {
					fireDeployEvent(hotDeployEvent);
				}

			};

			PortalLifecycleUtil.register(
				portalLifecycle, PortalLifecycle.METHOD_INIT);
		}
		else {

			// Fire event

			_fireDeployEvent(hotDeployEvent);
		}
	}

	public static synchronized void fireUndeployEvent(
		HotDeployEvent hotDeployEvent) {

		for (int i = _hotDeployListeners.size() - 1; i >= 0; i--) {
			HotDeployListener hotDeployListener = _hotDeployListeners.get(i);

			PortletClassLoaderUtil.setServletContextName(
				hotDeployEvent.getServletContextName());

			try {
				hotDeployListener.invokeUndeploy(hotDeployEvent);
			}
			catch (HotDeployException hotDeployException) {
				_log.error(hotDeployException);
			}
			finally {
				PortletClassLoaderUtil.setServletContextName(null);
			}
		}

		_deployedServletContextNames.remove(
			hotDeployEvent.getServletContextName());
	}

	public static synchronized boolean registerDependentPortalLifecycle(
		String servletContextName, PortalLifecycle portalLifecycle) {

		for (HotDeployEvent hotDeployEvent : _dependentHotDeployEvents) {
			if (Objects.equals(
					servletContextName,
					hotDeployEvent.getServletContextName())) {

				hotDeployEvent.addPortalLifecycle(portalLifecycle);

				return true;
			}
		}

		return false;
	}

	public static synchronized void registerListener(
		HotDeployListener hotDeployListener) {

		_hotDeployListeners.add(hotDeployListener);
	}

	public static synchronized void reset() {
		_capturePrematureEvents = true;
		_dependentHotDeployEvents.clear();
		_deployedServletContextNames.clear();
		_hotDeployListeners.clear();
	}

	public static synchronized void setCapturePrematureEvents(
		boolean capturePrematureEvents) {

		_capturePrematureEvents = capturePrematureEvents;
	}

	public static synchronized void unregisterListener(
		HotDeployListener hotDeployListener) {

		_hotDeployListeners.remove(hotDeployListener);
	}

	public static synchronized void unregisterListeners() {
		_hotDeployListeners.clear();
	}

	private static void _fireDeployEvent(HotDeployEvent hotDeployEvent) {
		String servletContextName = hotDeployEvent.getServletContextName();

		if (_deployedServletContextNames.contains(servletContextName)) {
			return;
		}

		boolean hasDependencies = true;

		for (String dependentServletContextName :
				hotDeployEvent.getDependentServletContextNames()) {

			if (!_deployedServletContextNames.contains(
					dependentServletContextName)) {

				hasDependencies = false;

				break;
			}
		}

		if (hasDependencies) {
			if (_log.isDebugEnabled()) {
				_log.debug("Deploying " + servletContextName + " from queue");
			}

			for (HotDeployListener hotDeployListener : _hotDeployListeners) {
				PortletClassLoaderUtil.setServletContextName(
					hotDeployEvent.getServletContextName());

				try {
					hotDeployListener.invokeDeploy(hotDeployEvent);
				}
				catch (HotDeployException hotDeployException) {
					_log.error(hotDeployException);
				}
				finally {
					PortletClassLoaderUtil.setServletContextName(null);
				}
			}

			_deployedServletContextNames.add(servletContextName);

			_dependentHotDeployEvents.remove(hotDeployEvent);

			ClassLoader contextClassLoader = _getContextClassLoader();

			try {
				_setContextClassLoader(PortalClassLoaderUtil.getClassLoader());

				List<HotDeployEvent> dependentEvents = new ArrayList<>(
					_dependentHotDeployEvents);

				for (HotDeployEvent dependentEvent : dependentEvents) {
					_setContextClassLoader(
						dependentEvent.getContextClassLoader());

					_fireDeployEvent(dependentEvent);

					if (!_dependentHotDeployEvents.contains(dependentEvent)) {
						dependentEvent.flushInits();
					}
				}
			}
			finally {
				_setContextClassLoader(contextClassLoader);
			}
		}
		else {
			if (!_dependentHotDeployEvents.contains(hotDeployEvent)) {
				if (_log.isInfoEnabled()) {
					_log.info(
						StringBundler.concat(
							"Queueing ", servletContextName,
							" for deploy because it is missing ",
							_getRequiredServletContextNames(hotDeployEvent)));
				}

				_dependentHotDeployEvents.add(hotDeployEvent);
			}
			else {
				if (_log.isInfoEnabled()) {
					for (HotDeployEvent dependentHotDeployEvent :
							_dependentHotDeployEvents) {

						_log.info(
							StringBundler.concat(
								servletContextName,
								" is still in queue because it is missing ",
								_getRequiredServletContextNames(
									dependentHotDeployEvent)));
					}
				}
			}
		}
	}

	private static ClassLoader _getContextClassLoader() {
		Thread currentThread = Thread.currentThread();

		return currentThread.getContextClassLoader();
	}

	private static String _getRequiredServletContextNames(
		HotDeployEvent hotDeployEvent) {

		List<String> requiredServletContextNames = new ArrayList<>();

		for (String dependentServletContextName :
				hotDeployEvent.getDependentServletContextNames()) {

			if (!_deployedServletContextNames.contains(
					dependentServletContextName)) {

				requiredServletContextNames.add(dependentServletContextName);
			}
		}

		Collections.sort(requiredServletContextNames);

		return StringUtil.merge(requiredServletContextNames, ", ");
	}

	private static void _setContextClassLoader(ClassLoader contextClassLoader) {
		Thread currentThread = Thread.currentThread();

		currentThread.setContextClassLoader(contextClassLoader);
	}

	private static final Log _log = LogFactoryUtil.getLog(HotDeployUtil.class);

	private static boolean _capturePrematureEvents = true;
	private static final Queue<HotDeployEvent> _dependentHotDeployEvents =
		new ConcurrentLinkedQueue<>();
	private static final Set<String> _deployedServletContextNames =
		new HashSet<>();
	private static final List<HotDeployListener> _hotDeployListeners =
		new ArrayList<>();

}