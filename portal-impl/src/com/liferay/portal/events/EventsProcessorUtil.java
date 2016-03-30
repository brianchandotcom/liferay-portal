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

		_instance.doProcess(key, classes, new LifecycleEvent());
	}

	public static void process(
			String key, String[] classes, HttpServletRequest request,
			HttpServletResponse response)
		throws ActionException {

		_instance.doProcess(key, classes, new LifecycleEvent(request, response));
	}

	public static void process(
			String key, String[] classes, HttpSession session)
		throws ActionException {

		_instance.doProcess(key, classes, new LifecycleEvent(session));
	}

	public static void process(
			String key, String[] classes, LifecycleEvent lifecycleEvent)
		throws ActionException {

		_instance.doProcess(key, classes, lifecycleEvent);
	}

	public static void process(String key, String[] classes, String[] ids)
		throws ActionException {

		_instance.doProcess(key, classes, new LifecycleEvent(ids));
	}

	public static void processEvent(
			LifecycleAction lifecycleAction, LifecycleEvent lifecycleEvent)
		throws ActionException {

		_instance.doProcessEvent(lifecycleAction, lifecycleEvent);
	}

	public static void registerEvent(String key, Object event) {
		_instance.doRegisterEvent(key, event);
	}

	public static void unregisterEvent(String key, Object event) {
		_instance.doUnregisterEvent(key, event);
	}

	protected EventsProcessorUtil() {
	}

	protected Collection<LifecycleAction> getLifecycleActions(String key) {
		List<LifecycleAction> lifecycleActions = _lifecycleActions.getService(
			key);

		if (lifecycleActions == null) {
			lifecycleActions = Collections.emptyList();
		}

		return lifecycleActions;
	}

	protected void doProcess(
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

		for (LifecycleAction lifecycleAction : _instance.getLifecycleActions(
				key)) {

			lifecycleAction.processLifecycleEvent(lifecycleEvent);
		}
	}

	protected void doProcessEvent(
			LifecycleAction lifecycleAction, LifecycleEvent lifecycleEvent)
		throws ActionException {

		lifecycleAction.processLifecycleEvent(lifecycleEvent);
	}

	protected void doRegisterEvent(String key, Object event) {
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

	protected void doUnregisterEvent(String key, Object event) {
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

	private static final Log _log = LogFactoryUtil.getLog(
		EventsProcessorUtil.class);

	private static final EventsProcessorUtil _instance =
		new EventsProcessorUtil();

	private final ServiceTrackerMap<String, List<LifecycleAction>>
		_lifecycleActions = ServiceTrackerCollections.openMultiValueMap(
			LifecycleAction.class, "key");
	private final
		ConcurrentMap<String, Map<Object, ServiceRegistration<LifecycleAction>>>
			_serviceRegistrationMaps = new ConcurrentHashMap<>();

}