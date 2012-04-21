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

package com.liferay.portal.deploy.hot;

import com.liferay.portal.kernel.concurrent.LockRegistry;
import com.liferay.portal.kernel.deploy.hot.HotDeploy;
import com.liferay.portal.kernel.deploy.hot.HotDeployEvent;
import com.liferay.portal.kernel.deploy.hot.HotDeployException;
import com.liferay.portal.kernel.deploy.hot.HotDeployListener;
import com.liferay.portal.kernel.deploy.hot.HotDeployUtil;
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
import java.util.Iterator;
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
 * @author Raymond Augé
 */
public class HotDeployImpl implements HotDeploy {

	public HotDeployImpl() {
		if (_log.isInfoEnabled()) {
			_log.info("Initializing hot deploy manager " + this.hashCode());
		}

		_dependentHotDeployEvents = new ArrayList<HotDeployEvent>();
		_dependentServletContexts =
			new HashMap<String, Set<DependentServletContext>>();
		_deployedServletContextNames = new HashSet<String>();
		_hotDeployListeners = new CopyOnWriteArrayList<HotDeployListener>();
	}

	public void fireDeployEvent(final HotDeployEvent hotDeployEvent) {
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
				doFireDeployEvent(hotDeployEvent);
			}
			finally {
				String lockKey = PortletContextLoaderListener.getLockKey(
					hotDeployEvent.getServletContext());

				LockRegistry.finallyFreeLock(lockKey, lockKey, true);
			}
		}
	}

	public void fireUndeployEvent(HotDeployEvent hotDeployEvent) {
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

	public boolean isMissingDependentServletContext(
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

	public void registerDependentServletContextListener(
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
					hotDeployEvent, servletContextEvent,
					servletContextListener);

			dependentServletContexts.add(dependentServletContext);
		}
	}

	public void registerListener(HotDeployListener hotDeployListener) {
		_hotDeployListeners.add(hotDeployListener);
	}

	public void reset() {
		_capturePrematureEvents = true;
		_dependentHotDeployEvents.clear();
		_dependentServletContexts.clear();
		_deployedServletContextNames.clear();
		_hotDeployListeners.clear();
	}

	public void setCapturePrematureEvents(boolean capturePrematureEvents) {
		_capturePrematureEvents = capturePrematureEvents;
	}

	public void unregisterListener(HotDeployListener hotDeployListener) {
		_hotDeployListeners.remove(hotDeployListener);
	}

	public void unregisterListeners() {
		_hotDeployListeners.clear();
	}

	protected void doFireDeployEvent(HotDeployEvent hotDeployEvent) {
		String servletContextName = hotDeployEvent.getServletContextName();

		if (!isMissingDependentServletContext(hotDeployEvent)) {
			if (_dependentHotDeployEvents.contains(hotDeployEvent)) {
				if (_log.isInfoEnabled()) {
					_log.info(
						"Deploying " + servletContextName + " from queue");
				}
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

			ClassLoader contextClassLoader = getContextClassLoader();

			try {
				setContextClassLoader(PortalClassLoaderUtil.getClassLoader());

				initDependentServletContextListeners();

				List<HotDeployEvent> dependentEvents =
					new ArrayList<HotDeployEvent>(_dependentHotDeployEvents);

				for (HotDeployEvent dependentEvent : dependentEvents) {
					setContextClassLoader(
						dependentEvent.getContextClassLoader());

					fireDeployEvent(dependentEvent);
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
		Thread thread = Thread.currentThread();

		return thread.getContextClassLoader();
	}

	protected String getRequiredServletContextNames(
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

	protected void initDependentServletContextListener(
		DependentServletContext dependentServletContext) {

		ClassLoader portletClassLoader =
			PortletClassLoaderUtil.getClassLoader();
		String portletServletContextName =
			PortletClassLoaderUtil.getServletContextName();

		try {
			PortletClassLoaderUtil.setClassLoader(
				dependentServletContext.getClassLoader());
			PortletClassLoaderUtil.setServletContextName(
				dependentServletContext.getServletContextName());

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

	protected void initDependentServletContextListeners() {
		Set<String> servletContextNames = _dependentServletContexts.keySet();

		Iterator<String> servletContextNamesIterator =
			servletContextNames.iterator();

		while (servletContextNamesIterator.hasNext()) {
			String servletContextName = servletContextNamesIterator.next();

			Set<DependentServletContext> dependentServletContexts =
				_dependentServletContexts.get(servletContextName);

			Iterator<DependentServletContext> dependentServletContextIterator =
				dependentServletContexts.iterator();

			while (dependentServletContextIterator.hasNext()) {
				DependentServletContext dependentServletContext =
					dependentServletContextIterator.next();

				HotDeployEvent hotDeployEvent =
					dependentServletContext.getHotDeployEvent();

				if (isMissingDependentServletContext(hotDeployEvent)) {
					continue;
				}

				initDependentServletContextListener(dependentServletContext);

				dependentServletContextIterator.remove();
			}

			if (dependentServletContexts.isEmpty()) {
				servletContextNamesIterator.remove();
			}
		}
	}

	protected void setContextClassLoader(ClassLoader contextClassLoader) {
		Thread thread = Thread.currentThread();

		thread.setContextClassLoader(contextClassLoader);
	}

	private static Log _log = LogFactoryUtil.getLog(HotDeployUtil.class);

	private boolean _capturePrematureEvents = true;
	private List<HotDeployEvent> _dependentHotDeployEvents;
	private Set<String> _deployedServletContextNames;
	private Map<String, Set<DependentServletContext>>
		_dependentServletContexts;
	private List<HotDeployListener> _hotDeployListeners;

	private class DependentServletContext {

		public DependentServletContext(
			HotDeployEvent hotDeployEvent,
			ServletContextEvent servletContextEvent,
			ServletContextListener servletContextListener) {

			_hotDeployEvent = hotDeployEvent;
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
			return _hotDeployEvent.getContextClassLoader();
		}

		public HotDeployEvent getHotDeployEvent() {
			return _hotDeployEvent;
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

		private HotDeployEvent _hotDeployEvent;
		private ServletContextEvent _servletContextEvent;
		private ServletContextListener _servletContextListener;

	}

}