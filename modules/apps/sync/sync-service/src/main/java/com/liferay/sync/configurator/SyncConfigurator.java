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

package com.liferay.sync.configurator;

import com.liferay.document.library.kernel.service.DLSyncEventLocalService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.sender.SingleDestinationMessageSenderFactory;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.sync.configuration.SyncServiceConfigurationValues;
import com.liferay.sync.messaging.SyncDLFileVersionDiffMessageListener;
import com.liferay.sync.util.VerifyUtil;

import java.util.Dictionary;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shinn Lok
 */
@Component(immediate = true, service = SyncConfigurator.class)
public class SyncConfigurator {

	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		try {
			if (SyncServiceConfigurationValues.SYNC_VERIFY) {
				VerifyUtil.verify();
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		_serviceRegistration = registerMessageListener(
			DestinationNames.DOCUMENT_LIBRARY_SYNC_EVENT_PROCESSOR);

		if (SyncServiceConfigurationValues.SYNC_FILE_DIFF_CACHE_ENABLED) {
			registerMessageListener(
				SyncDLFileVersionDiffMessageListener.DESTINATION_NAME);
		}
	}

	@Deactivate
	protected void deactivate() {
		if (_serviceRegistration != null) {
			Destination destination = _bundleContext.getService(
				_serviceRegistration.getReference());

			_serviceRegistration.unregister();

			destination.destroy();
		}

		_bundleContext = null;
	}

	protected ServiceRegistration<Destination> registerMessageListener(
		String destinationName) {

		DestinationConfiguration destinationConfiguration =
			new DestinationConfiguration(
				DestinationConfiguration.DESTINATION_TYPE_SERIAL,
				destinationName);

		Destination destination = _destinationFactory.createDestination(
			destinationConfiguration);

		Dictionary<String, Object> destinationProperties =
			new HashMapDictionary<>();

		destinationProperties.put("destination.name", destination.getName());

		return _bundleContext.registerService(
			Destination.class, destination, destinationProperties);
	}

	@Reference(unbind = "-")
	protected void setDLSyncEventLocalService(
		DLSyncEventLocalService dlSyncEventLocalService) {

		_dlSyncEventLocalService = dlSyncEventLocalService;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SyncConfigurator.class);

	private volatile BundleContext _bundleContext;

	@Reference
	private DestinationFactory _destinationFactory;

	private DLSyncEventLocalService _dlSyncEventLocalService;
	private ServiceRegistration<Destination> _serviceRegistration;

	@Reference
	private SingleDestinationMessageSenderFactory
		_singleDestinationMessageSenderFactory;

}