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

package com.liferay.object.internal.action;

import com.liferay.object.action.ObjectActionTrigger;
import com.liferay.object.action.ObjectActionTriggerRegistry;
import com.liferay.object.constants.ObjectActionConstants;
import com.liferay.object.internal.messaging.ObjectActionTriggerMessageListener;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Marco Leo
 */
@Component(service = ObjectActionTriggerRegistryImpl.class)
public class ObjectActionTriggerRegistryImpl
	implements ObjectActionTriggerRegistry {

	@Override
	public List<ObjectActionTrigger> getObjectActionTriggers(String className) {
		List<ObjectActionTrigger> objectActionTriggers =
			_serviceTrackerMap.getService(className);

		if (objectActionTriggers == null) {
			return Collections.emptyList();
		}

		return objectActionTriggers;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, ObjectActionTrigger.class, "model.class.name");

		_destinationServiceTrackerMap =
			ServiceTrackerMapFactory.openMultiValueMap(
				bundleContext, Destination.class, "model.class.name",
				new ServiceTrackerCustomizer<Destination, Destination>() {

					@Override
					public Destination addingService(
						ServiceReference<Destination> serviceReference) {

						Destination destination = _bundleContext.getService(
							serviceReference);

						String className = String.valueOf(
							serviceReference.getProperty("model.class.name"));

						_bundleContext.registerService(
							ObjectActionTrigger.class,
							new ObjectActionTrigger(
								className, destination.getName(),
								ObjectActionConstants.
									OBJECT_ACTION_TYPE_MESSAGE_BUS),
							HashMapDictionaryBuilder.<String, Object>put(
								"model.class.name", className
							).put(
								"object.action.trigger.name",
								destination.getName()
							).build());

						_bundleContext.registerService(
							MessageListener.class,
							new ObjectActionTriggerMessageListener(),
							HashMapDictionaryBuilder.<String, Object>put(
								"destination.name", destination.getName()
							).put(
								"object.action.trigger.name",
								destination.getName()
							).build());

						return destination;
					}

					@Override
					public void modifiedService(
						ServiceReference<Destination> serviceReference,
						Destination destination) {
					}

					@Override
					public void removedService(
						ServiceReference<Destination> serviceReference,
						Destination destination) {

						Collection<ServiceReference<ObjectActionTrigger>>
							serviceReferences = null;

						try {
							serviceReferences =
								_bundleContext.getServiceReferences(
									ObjectActionTrigger.class,
									StringBundler.concat(
										"(model.class.name=",
										serviceReference.getProperty(
											"model.class.name"),
										")"));

							serviceReferences.forEach(
								objectActionTriggerServiceReference ->
									_bundleContext.ungetService(
										objectActionTriggerServiceReference));

							Collection<ServiceReference<MessageListener>>
								messageListenerServiceReferences =
									_bundleContext.getServiceReferences(
										MessageListener.class,
										"(object.action.trigger.name=" +
											destination.getName() + ")");

							messageListenerServiceReferences.forEach(
								messageListenerServiceReference ->
									_bundleContext.ungetService(
										messageListenerServiceReference));
						}
						catch (InvalidSyntaxException invalidSyntaxException) {
							_log.error(
								invalidSyntaxException, invalidSyntaxException);
						}
					}

				});
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
		_destinationServiceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectActionTriggerRegistryImpl.class);

	private BundleContext _bundleContext;
	private ServiceTrackerMap<String, List<Destination>>
		_destinationServiceTrackerMap;
	private ServiceTrackerMap<String, List<ObjectActionTrigger>>
		_serviceTrackerMap;

}