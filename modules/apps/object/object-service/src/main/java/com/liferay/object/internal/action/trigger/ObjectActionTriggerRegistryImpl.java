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

package com.liferay.object.internal.action.trigger;

import com.liferay.object.action.engine.ObjectActionEngine;
import com.liferay.object.action.trigger.ObjectActionTrigger;
import com.liferay.object.action.trigger.ObjectActionTriggerRegistry;
import com.liferay.object.internal.action.trigger.messaging.ObjectActionTriggerMessageListener;
import com.liferay.object.internal.action.trigger.util.ObjectActionTriggerUtil;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.Arrays;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Marco Leo
 */
@Component(service = ObjectActionTriggerRegistry.class)
public class ObjectActionTriggerRegistryImpl
	implements ObjectActionTriggerRegistry {

	@Override
	public List<ObjectActionTrigger> getObjectActionTriggers(String className) {
		List<ObjectActionTrigger> objectActionTriggers =
			_objectActionTriggerServiceTrackerMap.getService(className);

		if (objectActionTriggers == null) {
			return ObjectActionTriggerUtil.getDefaultObjectActionTriggers();
		}

		return ObjectActionTriggerUtil.sort(
			ListUtil.concat(
				ObjectActionTriggerUtil.getDefaultObjectActionTriggers(),
				objectActionTriggers));
	}

	@Activate
	protected void activate(BundleContext bundleContext)
		throws InvalidSyntaxException {

		_serviceTracker = new ServiceTracker<>(
			bundleContext,
			bundleContext.createFilter(
				StringBundler.concat(
					"(&(objectClass=", Destination.class.getName(),
					")(object.action.trigger.class.name=*))")),
			new ServiceTrackerCustomizer
				<Destination, List<ServiceRegistration<?>>>() {

				@Override
				public List<ServiceRegistration<?>> addingService(
					ServiceReference<Destination> serviceReference) {

					String className = String.valueOf(
						serviceReference.getProperty(
							"object.action.trigger.class.name"));
					Destination destination = bundleContext.getService(
						serviceReference);

					return Arrays.asList(
						bundleContext.registerService(
							MessageListener.class,
							new ObjectActionTriggerMessageListener(
								className, _objectActionEngine,
								destination.getName()),
							HashMapDictionaryBuilder.<String, Object>put(
								"destination.name", destination.getName()
							).put(
								"object.action.trigger.key",
								destination.getName()
							).build()),
						bundleContext.registerService(
							ObjectActionTrigger.class,
							new ObjectActionTrigger(destination.getName()),
							HashMapDictionaryBuilder.<String, Object>put(
								"object.action.trigger.class.name", className
							).put(
								"object.action.trigger.key",
								destination.getName()
							).build()));
				}

				@Override
				public void modifiedService(
					ServiceReference<Destination> serviceReference,
					List<ServiceRegistration<?>> serviceRegistrations) {
				}

				@Override
				public void removedService(
					ServiceReference<Destination> serviceReference,
					List<ServiceRegistration<?>> serviceRegistrations) {

					for (ServiceRegistration<?> serviceRegistration :
							serviceRegistrations) {

						serviceRegistration.unregister();
					}
				}

			});

		_serviceTracker.open();

		_objectActionTriggerServiceTrackerMap =
			ServiceTrackerMapFactory.openMultiValueMap(
				bundleContext, ObjectActionTrigger.class,
				"object.action.trigger.class.name");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();

		_objectActionTriggerServiceTrackerMap.close();
	}

	@Reference
	private ObjectActionEngine _objectActionEngine;

	private ServiceTrackerMap<String, List<ObjectActionTrigger>>
		_objectActionTriggerServiceTrackerMap;
	private ServiceTracker<Destination, List<ServiceRegistration<?>>>
		_serviceTracker;

}