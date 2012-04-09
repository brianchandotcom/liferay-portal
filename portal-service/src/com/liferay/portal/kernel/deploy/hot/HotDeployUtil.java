/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.kernel.deploy.hot;

import com.liferay.portal.kernel.concurrent.LockRegistry;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletClassLoaderUtil;
import com.liferay.portal.kernel.spring.context.PortletContextLoaderListener;
import com.liferay.portal.kernel.util.BasePortalLifecycle;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PortalLifecycle;
import com.liferay.portal.kernel.util.PortalLifecycleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author Ivica Cardic
 * @author Brian Wing Shun Chan
 */
public class HotDeployUtil {

	public static void fireDeployEvent(HotDeployEvent hotDeployEvent) {
		_instance._fireDeployEvent(hotDeployEvent);
	}

	public static void fireUndeployEvent(HotDeployEvent hotDeployEvent) {
		_instance._fireUndeployEvent(hotDeployEvent);
	}

	public static boolean isMissingDependentServletContext(
		HotDeployEvent hotDeployEvent) {

		return _instance._isMissingDependentServletContext(hotDeployEvent);
	}

	public static void registerDependentServletContextListener(
		HotDeployEvent hotDeployEvent, ServletContextEvent servletContextEvent,
		ServletContextListener servletContextListener) {

		_instance._registerDependentServletContextListener(
			hotDeployEvent, servletContextEvent, servletContextListener);
	}

	public static void registerListener(HotDeployListener hotDeployListener) {
		_instance._registerListener(hotDeployListener);
	}

	public static void reset() {
		_instance._reset();
	}

	public static void setCapturePrematureEvents(
		boolean capturePrematureEvents) {

		_instance._setCapturePrematureEvents(capturePrematureEvents);
	}

	public static void unregisterListener(HotDeployListener hotDeployListener) {
		_instance._unregisterListener(hotDeployListener);
	}

	public static void unregisterListeners() {
		_instance._unregisterListeners();
	}

	private HotDeployUtil() {
		if (_log.isInfoEnabled()) {
			_log.info("Initializing hot deploy manager " + this.hashCode());
		}

		_dependentHotDeployEvents = new ArrayList<HotDeployEvent>();
		_dependentServletContexts =
			new HashMap<String, Set<DependentServletContext>>();
		_deployedServletContextNames = new HashSet<String>();
		_hotDeployListeners = new CopyOnWriteArrayList<HotDeployListener>();
	}

	private void _doFireDeployEvent(HotDeployEvent hotDeployEvent) {
		String servletContextName = hotDeployEvent.getServletContextName();

		if (!_isMissingDependentServletContext(hotDeployEvent)) {
			if (_dependentHotDeployEvents.contains(hotDeployEvent)) {
				if (_log.isInfoEnabled()) {
					_log.info(
						"Deploying " + servletContextName + " from queue");
				}
			}

			synchronized (_dependentServletContexts) {
				_initDependentServletContextListeners(servletContextName);

				_dependentServletContexts.remove(servletContextName);
			}

			for (HotDeployListener hotDeployListener : _hotDeployListeners) {
				try {
					hotDeployListener.invokeDeploy(hotDeployEvent);
				}
				catch (HotDeployException hde) {
					_log.error(hde, hde);
				}
			}

			_deployedServletContextNames.add(servletContextName);

			_dependentHotDeployEvents.remove(hotDeployEvent);

			List<HotDeployEvent> dependentEvents =
				new ArrayList<HotDeployEvent>(_dependentHotDeployEvents);

			for (HotDeployEvent dependentEvent : dependentEvents) {
				_doFireDeployEvent(dependentEvent);
			}
		}
		else {
			if (!_dependentHotDeployEvents.contains(hotDeployEvent)) {
				if (_log.isInfoEnabled()) {
					StringBundler sb = new StringBundler(4);

					sb.append("Queueing ");
					sb.append(servletContextName);
					sb.append(" for deploy because it is missing ");
					sb.append(_getRequiredServletContextNames(hotDeployEvent));

					_log.info(sb.toString());
				}

				_dependentHotDeployEvents.add(hotDeployEvent);
			}
			else {
				if (_log.isInfoEnabled()) {
					for (HotDeployEvent dependentHotDeployEvent :
							_dependentHotDeployEvents) {

						StringBundler sb = new StringBundler(3);

						sb.append(servletContextName);
						sb.append(" is still in queue because it is missing ");
						sb.append(
							_getRequiredServletContextNames(
								dependentHotDeployEvent));

						_log.info(sb.toString());
					}
				}
			}
		}
	}

	private void _fireDeployEvent(final HotDeployEvent hotDeployEvent) {
		if (_capturePrematureEvents) {

			// Capture events that are fired before the portal initialized

			PortalLifecycle portalLifecycle = new BasePortalLifecycle() {

				@Override
				protected void doPortalDestroy() {
				}

				@Override
				protected void doPortalInit() {
					HotDeployUtil.fireDeployEvent(hotDeployEvent);
				}

			};

			PortalLifecycleUtil.register(
				portalLifecycle, PortalLifecycle.METHOD_INIT);
		}
		else {

			// Fire current event

			try {
				_doFireDeployEvent(hotDeployEvent);
			}
			finally {
				String lockKey = PortletContextLoaderListener.getLockKey(
					hotDeployEvent.getServletContext());

				LockRegistry.finallyFreeLock(lockKey, lockKey, true);
			}
		}
	}

	private void _fireUndeployEvent(HotDeployEvent hotDeployEvent) {
		for (HotDeployListener hotDeployListener : _hotDeployListeners) {
			try {
				hotDeployListener.invokeUndeploy(hotDeployEvent);
			}
			catch (HotDeployException hde) {
				_log.error(hde, hde);
			}
		}

		_deployedServletContextNames.remove(
			hotDeployEvent.getServletContextName());
	}

	private String _getRequiredServletContextNames(
		HotDeployEvent hotDeployEvent) {

		List<String> requiredServletContextNames = new ArrayList<String>();

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

	private void _initDependentServletContextListener(
		Thread currentThread, DependentServletContext dependentServletContext) {

		ClassLoader portletClassLoader =
			PortletClassLoaderUtil.getClassLoader();
		String portletServletContextName =
			PortletClassLoaderUtil.getServletContextName();

		try {
			PortletClassLoaderUtil.setClassLoader(
				dependentServletContext.getClassLoader());
			PortletClassLoaderUtil.setServletContextName(
				dependentServletContext.getServletContextName());

			currentThread.setContextClassLoader(
				PortalClassLoaderUtil.getClassLoader());

			ServletContextListener servletContextListener =
				dependentServletContext.getServletContextListener();

			servletContextListener.contextInitialized(
				dependentServletContext.getServletContextEvent());
		}
		finally {
			PortletClassLoaderUtil.setClassLoader(portletClassLoader);
			PortletClassLoaderUtil.setServletContextName(
				portletServletContextName);
		}
	}

	private void _initDependentServletContextListeners(
		String servletContextName) {

		Set<DependentServletContext> dependentServletContexts =
			_dependentServletContexts.get(servletContextName);

		if ((dependentServletContexts == null) ||
			dependentServletContexts.isEmpty()) {

			return;
		}

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			for (DependentServletContext dependentServletContext :
					dependentServletContexts) {

				_initDependentServletContextListener(
					currentThread, dependentServletContext);
			}
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	private boolean _isMissingDependentServletContext(
		HotDeployEvent hotDeployEvent) {

		if (_deployedServletContextNames.contains(
				hotDeployEvent.getServletContextName())) {

			return false;
		}

		for (String dependentServletContextName :
				hotDeployEvent.getDependentServletContextNames()) {

			if (!_deployedServletContextNames.contains(
					dependentServletContextName)) {

				return true;
			}
		}

		return false;
	}

	private void _registerDependentServletContextListener(
		HotDeployEvent hotDeployEvent, ServletContextEvent servletContextEvent,
		ServletContextListener servletContextListener) {

		String servletContextName = hotDeployEvent.getServletContextName();

		synchronized (_dependentServletContexts) {
			Set<DependentServletContext> dependentServletContexts =
				_dependentServletContexts.get(servletContextName);

			if (dependentServletContexts == null) {
				dependentServletContexts =
					new HashSet<DependentServletContext>();

				_dependentServletContexts.put(
					servletContextName, dependentServletContexts);
			}

			DependentServletContext dependentServletContext =
				new DependentServletContext(
					hotDeployEvent.getContextClassLoader(), servletContextEvent,
					servletContextListener);

			dependentServletContexts.add(dependentServletContext);
		}
	}

	private void _registerListener(HotDeployListener hotDeployListener) {
		_hotDeployListeners.add(hotDeployListener);
	}

	private void _reset() {
		_capturePrematureEvents = true;
		_dependentHotDeployEvents.clear();
		_dependentServletContexts.clear();
		_deployedServletContextNames.clear();
		_hotDeployListeners.clear();
	}

	private void _setCapturePrematureEvents(boolean capturePrematureEvents) {
		_capturePrematureEvents = capturePrematureEvents;
	}

	private void _unregisterListener(HotDeployListener hotDeployListener) {
		_hotDeployListeners.remove(hotDeployListener);
	}

	private void _unregisterListeners() {
		_hotDeployListeners.clear();
	}

	private static Log _log = LogFactoryUtil.getLog(HotDeployUtil.class);

	private static HotDeployUtil _instance = new HotDeployUtil();

	private boolean _capturePrematureEvents = true;
	private List<HotDeployEvent> _dependentHotDeployEvents;
	private Set<String> _deployedServletContextNames;
	private Map<String, Set<DependentServletContext>>
		_dependentServletContexts;
	private List<HotDeployListener> _hotDeployListeners;

	private class DependentServletContext {

		public DependentServletContext(
			ClassLoader contextClassLoader,
			ServletContextEvent servletContextEvent,
			ServletContextListener servletContextListener) {

			_classLoader = contextClassLoader;
			_servletContextEvent = servletContextEvent;
			_servletContextListener = servletContextListener;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}

			if (!(obj instanceof DependentServletContext)) {
				return false;
			}

			DependentServletContext dependentServletContext =
				(DependentServletContext)obj;

			if (Validator.equals(
					_servletContextEvent,
					dependentServletContext._servletContextEvent)) {

				return true;
			}

			return false;
		}

		public ClassLoader getClassLoader() {
			return _classLoader;
		}

		public ServletContextEvent getServletContextEvent() {
			return _servletContextEvent;
		}

		public ServletContextListener getServletContextListener() {
			return _servletContextListener;
		}

		public String getServletContextName() {
			ServletContext servletContext =
				_servletContextEvent.getServletContext();

			return servletContext.getServletContextName();
		}

		@Override
		public int hashCode() {
			if (_servletContextEvent != null) {
				return _servletContextEvent.hashCode();
			}

			return 0;
		}

		private ClassLoader _classLoader;
		private ServletContextEvent _servletContextEvent;
		private ServletContextListener _servletContextListener;

	}

}