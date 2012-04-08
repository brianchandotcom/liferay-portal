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
import com.liferay.portal.kernel.spring.context.PortletContextLoaderListener;
import com.liferay.portal.kernel.util.BasePortalLifecycle;
import com.liferay.portal.kernel.util.PortalLifecycle;
import com.liferay.portal.kernel.util.PortalLifecycleUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Ivica Cardic
 * @author Brian Wing Shun Chan
 */
public class HotDeployUtil {

	public static void fireDeployEvent(HotDeployEvent event) {
		_instance._fireDeployEvent(event);
	}

	public static void fireUndeployEvent(HotDeployEvent event) {
		_instance._fireUndeployEvent(event);
	}

	public static void registerListener(HotDeployListener listener) {
		_instance._registerListener(listener);
	}

	public static void reset() {
		_instance._reset();
	}

	public static void setCapturePrematureEvents(
		boolean capturePrematureEvents) {

		_instance._setCapturePrematureEvents(capturePrematureEvents);
	}

	public static void unregisterListener(HotDeployListener listener) {
		_instance._unregisterListener(listener);
	}

	public static void unregisterListeners() {
		_instance._unregisterListeners();
	}

	private HotDeployUtil() {
		if (_log.isInfoEnabled()) {
			_log.info("Initializing hot deploy manager " + this.hashCode());
		}

		_dependentEvents = new ArrayList<HotDeployEvent>();
		_deployedServletContextNames = new HashSet<String>();
		_listeners = new CopyOnWriteArrayList<HotDeployListener>();
	}

	private void _doFireDeployEvent(HotDeployEvent event) {
		if (_deployedServletContextNames.contains(
				event.getServletContextName())) {

			return;
		}

		boolean hasDependencies = true;

		for (String dependentServletContextName :
				event.getDependentServletContextNames()) {

			if (!_deployedServletContextNames.contains(
					dependentServletContextName)) {

				hasDependencies = false;

				break;
			}
		}

		if (hasDependencies) {
			if (_dependentEvents.contains(event)) {
				if (_log.isInfoEnabled()) {
					_log.info(
						"Deploying " + event.getServletContextName() +
							" from queue");
				}
			}

			for (HotDeployListener listener : _listeners) {
				try {
					listener.invokeDeploy(event);
				}
				catch (HotDeployException hde) {
					_log.error(hde, hde);
				}
			}

			_deployedServletContextNames.add(event.getServletContextName());

			_dependentEvents.remove(event);

			List<HotDeployEvent> dependentEvents =
				new ArrayList<HotDeployEvent>(_dependentEvents);

			for (HotDeployEvent dependentEvent : dependentEvents) {
				_doFireDeployEvent(dependentEvent);
			}
		}
		else {
			if (!_dependentEvents.contains(event)) {
				if (_log.isInfoEnabled()) {
					StringBuilder sb = new StringBuilder();

					sb.append("Queueing ");
					sb.append(event.getServletContextName());
					sb.append(" for deploy because it is missing ");
					sb.append(_getRequiredServletContextNames(event));

					_log.info(sb.toString());
				}

				_dependentEvents.add(event);
			}
			else {
				if (_log.isInfoEnabled()) {
					for (HotDeployEvent dependentEvent : _dependentEvents) {

						StringBuilder sb = new StringBuilder();

						sb.append(dependentEvent.getServletContextName());
						sb.append(" is still in queue because it is missing ");
						sb.append(
							_getRequiredServletContextNames(dependentEvent));

						_log.info(sb.toString());
					}
				}
			}
		}
	}

	private void _fireDeployEvent(final HotDeployEvent event) {
		if (_capturePrematureEvents) {

			// Capture events that are fired before the portal initialized

			PortalLifecycle portalLifecycle = new BasePortalLifecycle() {

				@Override
				protected void doPortalDestroy() {
				}

				@Override
				protected void doPortalInit() {
					HotDeployUtil.fireDeployEvent(event);
				}

			};

			PortalLifecycleUtil.register(
				portalLifecycle, PortalLifecycle.METHOD_INIT);
		}
		else {

			// Fire current event

			try {
				_doFireDeployEvent(event);
			}
			finally {
				String lockKey = PortletContextLoaderListener.getLockKey(
					event.getServletContext());

				LockRegistry.finallyFreeLock(lockKey, lockKey, true);
			}
		}
	}

	private void _fireUndeployEvent(HotDeployEvent event) {
		for (HotDeployListener listener : _listeners) {
			try {
				listener.invokeUndeploy(event);
			}
			catch (HotDeployException hde) {
				_log.error(hde, hde);
			}
		}

		_deployedServletContextNames.remove(event.getServletContextName());
	}

	private String _getRequiredServletContextNames(HotDeployEvent event) {
		List<String> requiredServletContextNames = new ArrayList<String>();

		for (String dependentServletContextName :
				event.getDependentServletContextNames()) {

			if (!_deployedServletContextNames.contains(
					dependentServletContextName)) {

				requiredServletContextNames.add(dependentServletContextName);
			}
		}

		Collections.sort(requiredServletContextNames);

		return StringUtil.merge(requiredServletContextNames, ", ");
	}

	private void _registerListener(HotDeployListener listener) {
		_listeners.add(listener);
	}

	private void _reset() {
		_capturePrematureEvents = true;
		_dependentEvents.clear();
		_deployedServletContextNames.clear();
		_listeners.clear();
	}

	private void _setCapturePrematureEvents(boolean capturePrematureEvents) {
		_capturePrematureEvents = capturePrematureEvents;
	}

	private void _unregisterListener(HotDeployListener listener) {
		_listeners.remove(listener);
	}

	private void _unregisterListeners() {
		_listeners.clear();
	}

	private static Log _log = LogFactoryUtil.getLog(HotDeployUtil.class);

	private static HotDeployUtil _instance = new HotDeployUtil();

	private boolean _capturePrematureEvents = true;
	private List<HotDeployEvent> _dependentEvents;
	private Set<String> _deployedServletContextNames;
	private List<HotDeployListener> _listeners;

}