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

package com.liferay.exportimport.lifecycle;

import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleEvent;
import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleEventFactory;
import com.liferay.exportimport.kernel.lifecycle.ExportImportLifecycleManager;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.util.HashMapDictionary;

import java.io.Serializable;

import java.util.Dictionary;
import java.util.HashSet;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = ExportImportLifecycleManager.class)
public class ExportImportLifecycleManagerImpl
	implements ExportImportLifecycleManager {

	/**
	 * @deprecated As of Judson (7.1.x)
	 */
	@Deprecated
	@Override
	public void fireExportImportLifecycleEvent(
		int code, int processFlag, Serializable... arguments) {

		ExportImportLifecycleEvent exportImportLifecycleEvent =
			_exportImportLifecycleEventFactory.create(
				code, processFlag, arguments);

		fireExportImportLifecycleEvent(exportImportLifecycleEvent);
	}

	@Override
	public void fireExportImportLifecycleEvent(
		int code, int processFlag, String processId,
		Serializable... arguments) {

		ExportImportLifecycleEvent exportImportLifecycleEvent =
			_exportImportLifecycleEventFactory.create(
				code, processFlag, processId, arguments);

		fireExportImportLifecycleEvent(exportImportLifecycleEvent);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		registerDestination(
			bundleContext, DestinationConfiguration.DESTINATION_TYPE_SERIAL,
			DestinationNames.EXPORT_IMPORT_LIFECYCLE_EVENT_ASYNC);
		registerDestination(
			bundleContext,
			DestinationConfiguration.DESTINATION_TYPE_SYNCHRONOUS,
			DestinationNames.EXPORT_IMPORT_LIFECYCLE_EVENT_SYNC);
	}

	@Deactivate
	protected void deactivate() {
		for (ServiceRegistration<Destination> serviceRegistration :
				_serviceRegistrations) {

			Destination destination = _bundleContext.getService(
				serviceRegistration.getReference());

			serviceRegistration.unregister();

			destination.destroy();
		}

		_bundleContext = null;
	}

	protected void fireExportImportLifecycleEvent(
		ExportImportLifecycleEvent exportImportLifecycleEvent) {

		Message message = new Message();

		message.put("exportImportLifecycleEvent", exportImportLifecycleEvent);

		_messageBus.sendMessage(
			DestinationNames.EXPORT_IMPORT_LIFECYCLE_EVENT_ASYNC,
			message.clone());
		_messageBus.sendMessage(
			DestinationNames.EXPORT_IMPORT_LIFECYCLE_EVENT_SYNC,
			message.clone());
	}

	protected ServiceRegistration<Destination> registerDestination(
		BundleContext bundleContext, String destinationType,
		String destinationName) {

		DestinationConfiguration destinationConfiguration =
			new DestinationConfiguration(destinationType, destinationName);

		Destination destination = _destinationFactory.createDestination(
			destinationConfiguration);

		Dictionary<String, Object> dictionary = new HashMapDictionary<>();

		dictionary.put("destination.name", destination.getName());

		ServiceRegistration<Destination> serviceRegistration =
			bundleContext.registerService(
				Destination.class, destination, dictionary);

		_serviceRegistrations.add(serviceRegistration);

		return serviceRegistration;
	}

	@Reference(unbind = "-")
	protected void setDestinationFactory(
		DestinationFactory destinationFactory) {

		_destinationFactory = destinationFactory;
	}

	@Reference(unbind = "-")
	protected void setExportImportLifecycleEventFactory(
		ExportImportLifecycleEventFactory exportImportLifecycleEventFactory) {

		_exportImportLifecycleEventFactory = exportImportLifecycleEventFactory;
	}

	@Reference(unbind = "-")
	protected void setMessageBus(MessageBus messageBus) {
		_messageBus = messageBus;
	}

	private volatile BundleContext _bundleContext;
	private DestinationFactory _destinationFactory;
	private ExportImportLifecycleEventFactory
		_exportImportLifecycleEventFactory;
	private MessageBus _messageBus;
	private final Set<ServiceRegistration<Destination>> _serviceRegistrations =
		new HashSet<>();

}