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

package com.liferay.portal.struts;

import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.struts.StrutsPortletAction;
import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceRegistration;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;
import com.liferay.registry.collections.StringServiceRegistrationMap;
import com.liferay.registry.collections.StringServiceRegistrationMapImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.struts.action.Action;

/**
 * @author Mika Koivisto
 * @author Raymond Augé
 */
public class StrutsActionRegistryUtil {

	public static Action getAction(String path) {
		Action action = _actions.get(path);

		if (action != null) {
			return action;
		}

		for (Map.Entry<String, Action> entry : _actions.entrySet()) {
			if (path.startsWith(entry.getKey())) {
				return entry.getValue();
			}
		}

		return null;
	}

	public static Map<String, Action> getActions() {
		return _actions;
	}

	public static void register(String path, StrutsAction strutsAction) {
		Registry registry = RegistryUtil.getRegistry();

		Map<String, Object> properties = new HashMap<>();

		properties.put("path", path);

		ServiceRegistration<StrutsAction> serviceRegistration =
			registry.registerService(
				StrutsAction.class, strutsAction, properties);

		_strutsActionServiceRegistrations.put(path, serviceRegistration);
	}

	public static void register(
		String path, StrutsPortletAction strutsPortletAction) {

		Registry registry = RegistryUtil.getRegistry();

		Map<String, Object> properties = new HashMap<>();

		properties.put("path", path);

		ServiceRegistration<StrutsPortletAction> serviceRegistration =
			registry.registerService(
				StrutsPortletAction.class, strutsPortletAction, properties);

		_strutsPortletActionServiceRegistrations.put(path, serviceRegistration);
	}

	public static void unregister(String path) {
		ServiceRegistration<?> serviceRegistration =
			_strutsActionServiceRegistrations.remove(path);

		if (serviceRegistration != null) {
			serviceRegistration.unregister();
		}

		serviceRegistration = _strutsPortletActionServiceRegistrations.remove(
			path);

		if (serviceRegistration != null) {
			serviceRegistration.unregister();
		}
	}

	private static final Map<String, Action> _actions =
		new ConcurrentHashMap<>();
	private static final ServiceTracker<?, Action> _serviceTracker;
	private static final StringServiceRegistrationMap<StrutsAction>
		_strutsActionServiceRegistrations =
			new StringServiceRegistrationMapImpl<>();
	private static final StringServiceRegistrationMap<StrutsPortletAction>
		_strutsPortletActionServiceRegistrations =
			new StringServiceRegistrationMapImpl<>();

	private static class ActionServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<Object, Action> {

		@Override
		public Action addingService(ServiceReference<Object> serviceReference) {
			Registry registry = RegistryUtil.getRegistry();

			Object service = registry.getService(serviceReference);

			Action action = null;

			if (service instanceof StrutsAction) {
				action = new ActionAdapter((StrutsAction)service);
			}
			else if (service instanceof StrutsPortletAction) {
				action = new PortletActionAdapter((StrutsPortletAction)service);
			}

			String[] paths = _getPaths(serviceReference);

			for (String path : paths) {
				_actions.put(path, action);
			}

			return action;
		}

		@Override
		public void modifiedService(
			ServiceReference<Object> serviceReference, Action service) {
		}

		@Override
		public void removedService(
			ServiceReference<Object> serviceReference, Action service) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			String[] paths = _getPaths(serviceReference);

			for (String path : paths) {
				_actions.remove(path);
			}
		}

		private String[] _getPaths(ServiceReference<Object> serviceReference) {
			Object object = serviceReference.getProperty("path");

			if (object instanceof String[]) {
				return (String[])object;
			}
			else {
				return new String[] {(String)object};
			}
		}

	}

	static {
		Registry registry = RegistryUtil.getRegistry();

		Filter filter = registry.getFilter(
			"(&(|(objectClass=" + StrutsAction.class.getName() +
				")(objectClass=" + StrutsPortletAction.class.getName() +
					"))(path=*))");

		_serviceTracker = registry.trackServices(
			filter, new ActionServiceTrackerCustomizer());

		_serviceTracker.open();
	}

}