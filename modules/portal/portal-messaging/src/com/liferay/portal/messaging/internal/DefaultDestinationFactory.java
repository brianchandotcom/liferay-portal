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

package com.liferay.portal.messaging.internal;

import com.liferay.portal.kernel.executor.PortalExecutorManager;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.messaging.DestinationPrototype;

import java.util.Collection;
import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = DestinationFactory.class)
public class DefaultDestinationFactory implements DestinationFactory {

	public DefaultDestinationFactory() {
		_destinationPrototypes.put(
			DestinationConfiguration.DESTINATION_TYPE_PARALLEL,
			new ParallelDestinationPrototype());
		_destinationPrototypes.put(
			DestinationConfiguration.DESTINATION_TYPE_SERIAL,
			new SerialDestinationPrototype());
		_destinationPrototypes.put(
			DestinationConfiguration.DESTINATION_TYPE_SYNCHRONOUS,
			new SynchronousDestinationPrototype());
	}

	@Override
	public Destination createDestination(
		DestinationConfiguration destinationConfiguration) {

		String type = destinationConfiguration.getDestinationType();

		DestinationPrototype destinationPrototype = _destinationPrototypes.get(
			type);

		if (destinationPrototype == null) {
			throw new IllegalArgumentException(
				"No destination prototype configured for " + type);
		}

		return destinationPrototype.createDestination(destinationConfiguration);
	}

	@Override
	public Collection<String> getDestinationTypes() {
		return Collections.unmodifiableCollection(
			_destinationPrototypes.keySet());
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		synchronized (_serviceRegistrations) {
			_bundleContext = bundleContext;

			for (DestinationConfiguration destinationConfiguration :
					_queuedDestinationConfigurations) {

				addDestinationConfiguration(destinationConfiguration);
			}

			_queuedDestinationConfigurations.clear();
		}
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void addDestinationConfiguration(
		DestinationConfiguration destinationConfiguration) {

		synchronized (_serviceRegistrations) {
			if (_bundleContext == null) {
				_queuedDestinationConfigurations.add(destinationConfiguration);

				return;
			}

			Destination destination = createDestination(
				destinationConfiguration);

			Dictionary<String, Object> dictionary = new HashMapDictionary<>();

			dictionary.put("destination.name", destination.getName());

			ServiceRegistration<Destination> serviceRegistration =
				_bundleContext.registerService(
					Destination.class, destination, dictionary);

			_serviceRegistrations.put(
				destination.getName(), serviceRegistration);
		}
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void addDestinationPrototype(
		DestinationPrototype destinationPrototype,
		Map<String, Object> properties) {

		synchronized (_destinationPrototypes) {
			String destinationType = MapUtil.getString(
				properties, "destination.type");

			_destinationPrototypes.put(destinationType, destinationPrototype);
		}
	}

	@Deactivate
	protected void deactivate() {
		synchronized(_serviceRegistrations) {
			for (ServiceRegistration<Destination>
				destinationServiceRegistration :
				_serviceRegistrations.values()) {

				destinationServiceRegistration.unregister();
			}

			_serviceRegistrations.clear();
		}

		synchronized (_destinationPrototypes) {
			_destinationPrototypes.clear();
		}

		_bundleContext = null;
	}

	protected void removeDestinationConfiguration(
		DestinationConfiguration destinationConfiguration) {

		synchronized (_serviceRegistrations) {
			if (_serviceRegistrations.containsKey(
					destinationConfiguration.getDestinationName())) {

				ServiceRegistration<Destination> serviceRegistration =
					_serviceRegistrations.remove(
						destinationConfiguration.getDestinationName());

				serviceRegistration.unregister();
			}
		}
	}

	protected void removeDestinationPrototype(
		DestinationPrototype destinationPrototype,
		Map<String, Object> properties) {

		synchronized (_destinationPrototypes) {
			String destinationType = MapUtil.getString(
				properties, "destination.type");

			_destinationPrototypes.remove(destinationType);
		}
	}

	@Reference(unbind = "-")
	protected void setPortalExecutorManager(
		PortalExecutorManager portalExecutorManager) {
	}

	private BundleContext _bundleContext;
	private final Map<String, DestinationPrototype> _destinationPrototypes =
		new ConcurrentHashMap<>();
	private final Set<DestinationConfiguration>
		_queuedDestinationConfigurations = new HashSet<>();
	private final Map<String, ServiceRegistration<Destination>>
		_serviceRegistrations = new HashMap<>();

}