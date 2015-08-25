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

import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portlet.exportimport.lifecycle.ExportImportLifecycleEvent;
import com.liferay.portlet.exportimport.lifecycle.ExportImportLifecycleEventFactory;
import com.liferay.portlet.exportimport.lifecycle.ExportImportLifecycleManager;

import java.io.Serializable;

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

	public void fireExportImportLifecycleEvent(
		int code, int processFlag, Serializable... arguments) {

		Message message = new Message();

		ExportImportLifecycleEvent exportImportLifecycleEvent =
			_exportImportLifecycleEventFactory.create(
				code, processFlag, arguments);

		message.put("exportImportLifecycleEvent", exportImportLifecycleEvent);

		_messageBus.sendMessage(
			DestinationNames.EXPORT_IMPORT_LIFECYCLE_EVENT_ASYNC,
			message.clone());

		_messageBus.sendMessage(
			DestinationNames.EXPORT_IMPORT_LIFECYCLE_EVENT_SYNC,
			message.clone());
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		registerDestinationConfig(
			bundleContext, DestinationConfiguration.DESTINATION_TYPE_SERIAL,
			DestinationNames.EXPORT_IMPORT_LIFECYCLE_EVENT_ASYNC);

		registerDestinationConfig(
			bundleContext,
			DestinationConfiguration.DESTINATION_TYPE_SYNCHRONOUS,
			DestinationNames.EXPORT_IMPORT_LIFECYCLE_EVENT_SYNC);
	}

	@Deactivate
	protected void deactivate() {
		for (ServiceRegistration<DestinationConfiguration> serviceRegistration :
				_serviceRegistrations) {

			serviceRegistration.unregister();
		}
	}

	protected ServiceRegistration<DestinationConfiguration>
		registerDestinationConfig(
			BundleContext bundleContext, String destinationType,
			String destinationName) {

		DestinationConfiguration destinationConfiguration =
			new DestinationConfiguration(destinationType, destinationName);

		ServiceRegistration<DestinationConfiguration> serviceRegistration =
			bundleContext.registerService(
				DestinationConfiguration.class, destinationConfiguration,
				new HashMapDictionary<String, Object>());

		_serviceRegistrations.add(serviceRegistration);

		return serviceRegistration;
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

	private ExportImportLifecycleEventFactory
		_exportImportLifecycleEventFactory;
	private MessageBus _messageBus;
	private final Set<ServiceRegistration<DestinationConfiguration>>
		_serviceRegistrations = new HashSet<>();

}