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

package com.liferay.portal.events;

import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.events.LifecycleEvent;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistration;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerMap;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Brian Wing Shun Chan
 * @author Michael Young
 * @author Raymond Augé
 */
public class EventsProcessorUtil {

	public static void process(String key, String[] classes)
		throws ActionException {

		process(key, classes, new LifecycleEvent());
	}

	public static void process(
			String key, String[] classes, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws ActionException {

		process(
			key, classes,
			new LifecycleEvent(httpServletRequest, httpServletResponse));
	}

	public static void process(
			String key, String[] classes, HttpSession session)
		throws ActionException {

		process(key, classes, new LifecycleEvent(session));
	}

	public static void process(
			String key, String[] classes, LifecycleEvent lifecycleEvent)
		throws ActionException {

		for (String className : classes) {
			if (Validator.isNull(className)) {
				return;
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Process event " + className);
			}

			LifecycleAction lifecycleAction = (LifecycleAction)InstancePool.get(
				className);

			lifecycleAction.processLifecycleEvent(lifecycleEvent);
		}

		if (Validator.isNull(key)) {
			return;
		}

		List<LifecycleAction> lifecycleActions = _lifecycleActions.getService(
			key);

		if (lifecycleActions != null) {
			for (LifecycleAction lifecycleAction : lifecycleActions) {
				lifecycleAction.processLifecycleEvent(lifecycleEvent);
			}
		}
	}

	public static void process(String key, String[] classes, String[] ids)
		throws ActionException {

		process(key, classes, new LifecycleEvent(ids));
	}

	public static void processEvent(
			LifecycleAction lifecycleAction, LifecycleEvent lifecycleEvent)
		throws ActionException {

		lifecycleAction.processLifecycleEvent(lifecycleEvent);
	}

	public static void registerEvent(String key, Object event) {
		Registry registry = RegistryUtil.getRegistry();

		Map<String, Object> properties = new HashMap<>();

		properties.put("key", key);

		ServiceRegistration<LifecycleAction> serviceRegistration =
			registry.registerService(
				LifecycleAction.class, (LifecycleAction)event, properties);

		Map<Object, ServiceRegistration<LifecycleAction>>
			serviceRegistrationMap = _serviceRegistrationMaps.get(key);

		if (serviceRegistrationMap == null) {
			_serviceRegistrationMaps.putIfAbsent(
				key,
				new ConcurrentHashMap
					<Object, ServiceRegistration<LifecycleAction>>());

			serviceRegistrationMap = _serviceRegistrationMaps.get(key);
		}

		serviceRegistrationMap.put(event, serviceRegistration);
	}

	public static void unregisterEvent(String key, Object event) {
		Map<Object, ServiceRegistration<LifecycleAction>>
			serviceRegistrationMap = _serviceRegistrationMaps.get(key);

		if (serviceRegistrationMap != null) {
			ServiceRegistration<LifecycleAction> serviceRegistration =
				serviceRegistrationMap.remove(event);

			if (serviceRegistration != null) {
				serviceRegistration.unregister();
			}

			_serviceRegistrationMaps.remove(key, Collections.emptyList());
		}
	}

	protected EventsProcessorUtil() {
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	protected Collection<LifecycleAction> _getLifecycleActions(String key) {
		List<LifecycleAction> lifecycleActions = _lifecycleActions.getService(
			key);

		if (lifecycleActions == null) {
			lifecycleActions = Collections.emptyList();
		}

		return lifecycleActions;
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link #process(String,
	 *             String[], LifecycleEvent)}
	 */
	@Deprecated
	protected void _process(
			String key, String[] classes, LifecycleEvent lifecycleEvent)
		throws ActionException {

		process(key, classes, lifecycleEvent);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #processEvent(LifecycleAction, LifecycleEvent)}
	 */
	@Deprecated
	protected void _processEvent(
			LifecycleAction lifecycleAction, LifecycleEvent lifecycleEvent)
		throws ActionException {

		processEvent(lifecycleAction, lifecycleEvent);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #registerEvent(String, Object)}
	 */
	@Deprecated
	protected void _registerEvent(String key, Object event) {
		registerEvent(key, event);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #unregisterEvent(String, Object)}
	 */
	@Deprecated
	protected void _unregisterEvent(String key, Object event) {
		unregisterEvent(key, event);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EventsProcessorUtil.class);

	private static final ServiceTrackerMap<String, List<LifecycleAction>>
		_lifecycleActions = ServiceTrackerCollections.openMultiValueMap(
			LifecycleAction.class, "key");
	private static final ConcurrentMap
		<String, Map<Object, ServiceRegistration<LifecycleAction>>>
			_serviceRegistrationMaps = new ConcurrentHashMap<>();

}