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

package com.liferay.object.internal.system;

import com.liferay.object.action.engine.ObjectActionEngine;
import com.liferay.object.internal.model.listener.SystemObjectDefinitionMetadataModelListener;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.system.SystemObjectDefinitionMetadata;
import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.kernel.cluster.ClusterMasterExecutor;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Luis Miguel Barcos
 */
@Component(immediate = true, service = {})
public class SystemObjectDefinitionMetadataRegistry {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_serviceTracker = ServiceTrackerFactory.create(
			bundleContext, SystemObjectDefinitionMetadata.class,
			new SystemObjectDefinitionMetadataServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		for (ServiceRegistration<?> serviceRegistration :
				_systemObjectDefinitionMetadataServiceRegistrations.values()) {

			serviceRegistration.unregister();
		}

		_systemObjectDefinitionMetadataServiceRegistrations.clear();

		_serviceTracker.close();

		_serviceTracker = null;

		_bundleContext = null;
	}

	private BundleContext _bundleContext;

	@Reference
	private ClusterMasterExecutor _clusterMasterExecutor;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private MessageBus _messageBus;

	@Reference
	private ObjectActionEngine _objectActionEngine;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	private ServiceTracker
		<SystemObjectDefinitionMetadata, SystemObjectDefinitionMetadata>
			_serviceTracker;
	private final Map<String, ServiceRegistration<?>>
		_systemObjectDefinitionMetadataServiceRegistrations =
			new ConcurrentHashMap<>();

	@Reference
	private UserLocalService _userLocalService;

	private class SystemObjectDefinitionMetadataServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<SystemObjectDefinitionMetadata, SystemObjectDefinitionMetadata> {

		@Override
		public SystemObjectDefinitionMetadata addingService(
			ServiceReference<SystemObjectDefinitionMetadata> serviceReference) {

			if (!_clusterMasterExecutor.isMaster()) {
				return null;
			}

			Bundle bundle = serviceReference.getBundle();

			String bundleSymbolicName = bundle.getSymbolicName();

			SystemObjectDefinitionMetadata systemObjectDefinitionMetadata =
				_bundleContext.getService(serviceReference);

			SystemObjectDefinitionMetadataModelListener
				systemObjectDefinitionMetadataModelListener =
					new SystemObjectDefinitionMetadataModelListener(
						_jsonFactory,
						systemObjectDefinitionMetadata.getModelClass(),
						_objectActionEngine, _objectDefinitionLocalService,
						_objectEntryLocalService, _userLocalService,
						_dtoConverterRegistry);

			ServiceRegistration<?> serviceRegistration =
				_bundleContext.registerService(
					ModelListener.class.getName(),
					systemObjectDefinitionMetadataModelListener,
					new HashMapDictionary<String, Object>());

			_systemObjectDefinitionMetadataServiceRegistrations.put(
				bundleSymbolicName, serviceRegistration);

			Message message = new Message();

			message.put("command", "deploy");
			message.put("servletContextName", bundleSymbolicName);

			_messageBus.sendMessage(DestinationNames.HOT_DEPLOY, message);

			return systemObjectDefinitionMetadata;
		}

		@Override
		public void modifiedService(
			ServiceReference<SystemObjectDefinitionMetadata> serviceReference,
			SystemObjectDefinitionMetadata systemObjectDefinitionMetadata) {
		}

		@Override
		public void removedService(
			ServiceReference<SystemObjectDefinitionMetadata> serviceReference,
			SystemObjectDefinitionMetadata systemObjectDefinitionMetadata) {

			Bundle bundle = serviceReference.getBundle();

			String bundleSymbolicName = bundle.getSymbolicName();

			ServiceRegistration<?> serviceRegistration =
				_systemObjectDefinitionMetadataServiceRegistrations.remove(
					bundleSymbolicName);

			if (serviceRegistration != null) {
				serviceRegistration.unregister();
			}
		}

	}

}