/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.deploy.hot;

import com.liferay.portal.deploy.RequiredPluginsUtil;
import com.liferay.portal.kernel.deploy.hot.HotDeploy;
import com.liferay.portal.kernel.deploy.hot.HotDeployEvent;
import com.liferay.portal.kernel.deploy.hot.HotDeployException;
import com.liferay.portal.kernel.deploy.hot.HotDeployListener;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletClassLoaderUtil;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.url.ServletContextURLContainer;
import com.liferay.portal.kernel.url.URLContainer;
import com.liferay.portal.kernel.util.BasePortalLifecycle;
import com.liferay.portal.kernel.util.ClassLoaderUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.PortalLifecycle;
import com.liferay.portal.kernel.util.PortalLifecycleUtil;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.File;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletContext;

/**
 * @author Ivica Cardic
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
@DoPrivileged
public class HotDeployImpl implements HotDeploy {

	public HotDeployImpl() {
		if (_log.isDebugEnabled()) {
			_log.debug("Initializing hot deploy manager " + this.hashCode());
		}

		_dependentHotDeployEvents = new ArrayList<>();
		_deployedServletContextNames = new HashSet<>();
		_hotDeployListeners = new ArrayList<>();
	}

	@Override
	public synchronized void fireDeployEvent(
		final HotDeployEvent hotDeployEvent) {

		PortalLifecycleUtil.register(
			new HotDeployPortalLifecycle(hotDeployEvent),
			PortalLifecycle.METHOD_INIT);

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

			doFireDeployEvent(hotDeployEvent);
		}
	}

	@Override
	public synchronized void fireUndeployEvent(HotDeployEvent hotDeployEvent) {
		for (int i = _hotDeployListeners.size() - 1; i >= 0; i--) {
			HotDeployListener hotDeployListener = _hotDeployListeners.get(i);

			PortletClassLoaderUtil.setServletContextName(
				hotDeployEvent.getServletContextName());

			try {
				hotDeployListener.invokeUndeploy(hotDeployEvent);
			}
			catch (HotDeployException hde) {
				_log.error(hde, hde);
			}
			finally {
				PortletClassLoaderUtil.setServletContextName(null);
			}
		}

		_deployedServletContextNames.remove(
			hotDeployEvent.getServletContextName());

		ClassLoader classLoader = hotDeployEvent.getContextClassLoader();

		TemplateManagerUtil.destroy(classLoader);

		_pacl.unregister(classLoader);

		RequiredPluginsUtil.startCheckingRequiredPlugins();
	}

	@Override
	public synchronized boolean registerDependentPortalLifecycle(
		String servletContextName, PortalLifecycle portalLifecycle) {

		for (HotDeployEvent hotDeployEvent : _dependentHotDeployEvents) {
			if (Validator.equals(
					servletContextName,
					hotDeployEvent.getServletContextName())) {

				hotDeployEvent.addPortalLifecycle(portalLifecycle);

				return true;
			}
		}

		return false;
	}

	@Override
	public synchronized void registerListener(
		HotDeployListener hotDeployListener) {

		_hotDeployListeners.add(hotDeployListener);
	}

	@Override
	public synchronized void reset() {
		_capturePrematureEvents = true;
		_dependentHotDeployEvents.clear();
		_deployedServletContextNames.clear();
		_hotDeployListeners.clear();
	}

	@Override
	public synchronized void setCapturePrematureEvents(
		boolean capturePrematureEvents) {

		_capturePrematureEvents = capturePrematureEvents;
	}

	@Override
	public synchronized void unregisterListener(
		HotDeployListener hotDeployListener) {

		_hotDeployListeners.remove(hotDeployListener);
	}

	@Override
	public synchronized void unregisterListeners() {
		_hotDeployListeners.clear();
	}

	public interface PACL {

		public void initPolicy(
			String contextName, URLContainer urlContainer,
			ClassLoader classLoader, Properties properties);

		public void unregister(ClassLoader classLoader);

	}

	protected void doFireDeployEvent(HotDeployEvent hotDeployEvent) {
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
			if (_log.isInfoEnabled()) {
				_log.info("Deploying " + servletContextName + " from queue");
			}

			for (int i = 0; i < _hotDeployListeners.size(); i++) {
				HotDeployListener hotDeployListener = _hotDeployListeners.get(
					i);

				PortletClassLoaderUtil.setServletContextName(
					hotDeployEvent.getServletContextName());

				try {
					hotDeployListener.invokeDeploy(hotDeployEvent);
				}
				catch (HotDeployException hde) {
					_log.error(hde, hde);
				}
				finally {
					PortletClassLoaderUtil.setServletContextName(null);
				}
			}

			_deployedServletContextNames.add(servletContextName);

			_dependentHotDeployEvents.remove(hotDeployEvent);

			ClassLoader contextClassLoader = getContextClassLoader();

			try {
				setContextClassLoader(ClassLoaderUtil.getPortalClassLoader());

				List<HotDeployEvent> dependentEvents = new ArrayList<>(
					_dependentHotDeployEvents);

				for (HotDeployEvent dependentEvent : dependentEvents) {
					setContextClassLoader(
						dependentEvent.getContextClassLoader());

					doFireDeployEvent(dependentEvent);

					if (!_dependentHotDeployEvents.contains(dependentEvent)) {
						dependentEvent.flushInits();
					}
				}
			}
			finally {
				setContextClassLoader(contextClassLoader);
			}
		}
		else {
			if (!_dependentHotDeployEvents.contains(hotDeployEvent)) {
				if (_log.isInfoEnabled()) {
					StringBundler sb = new StringBundler(4);

					sb.append("Queueing ");
					sb.append(servletContextName);
					sb.append(" for deploy because it is missing ");
					sb.append(getRequiredServletContextNames(hotDeployEvent));

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
							getRequiredServletContextNames(
								dependentHotDeployEvent));

						_log.info(sb.toString());
					}
				}
			}
		}
	}

	protected ClassLoader getContextClassLoader() {
		return ClassLoaderUtil.getContextClassLoader();
	}

	protected String getRequiredServletContextNames(
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

	protected void setContextClassLoader(ClassLoader contextClassLoader) {
		ClassLoaderUtil.setContextClassLoader(contextClassLoader);
	}

	private static final Log _log = LogFactoryUtil.getLog(HotDeployImpl.class);

	private static final PACL _pacl = new NoPACL();

	private boolean _capturePrematureEvents = true;
	private final List<HotDeployEvent> _dependentHotDeployEvents;
	private final Set<String> _deployedServletContextNames;
	private final List<HotDeployListener> _hotDeployListeners;

	private static class HotDeployPortalLifecycle extends BasePortalLifecycle {

		public HotDeployPortalLifecycle(HotDeployEvent hotDeployEvent) {
			_servletContext = hotDeployEvent.getServletContext();
			_classLoader = hotDeployEvent.getContextClassLoader();

			ServletContextPool.put(
				_servletContext.getServletContextName(), _servletContext);
		}

		@Override
		protected void doPortalDestroy() {
		}

		@Override
		protected void doPortalInit() throws Exception {
			Properties properties = null;

			String propertiesString = HttpUtil.URLtoString(
				_servletContext.getResource(
					"/WEB-INF/liferay-plugin-package.properties"));

			if (propertiesString != null) {
				properties = PropertiesUtil.load(propertiesString);
			}
			else {
				properties = new Properties();
			}

			File tempDir = (File)_servletContext.getAttribute(
				JavaConstants.JAVAX_SERVLET_CONTEXT_TEMPDIR);

			properties.put(
				JavaConstants.JAVAX_SERVLET_CONTEXT_TEMPDIR,
				tempDir.getAbsolutePath());

			_pacl.initPolicy(
				_servletContext.getServletContextName(),
				new ServletContextURLContainer(_servletContext), _classLoader,
				properties);
		}

		private final ClassLoader _classLoader;
		private final ServletContext _servletContext;

	}

	private static class NoPACL implements PACL {

		@Override
		public void initPolicy(
			String contextName, URLContainer urlContainer,
			ClassLoader classLoader, Properties properties) {
		}

		@Override
		public void unregister(ClassLoader classLoader) {
		}

	}

}