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

package com.liferay.sync.internal.configurator;

import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.sync.internal.messaging.SyncMaintenanceMessageListener;
import com.liferay.sync.internal.util.VerifyUtil;
import com.liferay.sync.service.configuration.SyncServiceConfigurationKeys;
import com.liferay.sync.service.internal.configuration.SyncServiceConfigurationValues;
import com.liferay.sync.util.SyncHelper;

import java.util.Dictionary;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shinn Lok
 */
@Component(immediate = true, service = PortalInstanceLifecycleListener.class)
public class SyncConfigurator extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		boolean lanEnabled = PrefsPropsUtil.getBoolean(
			company.getCompanyId(),
			SyncServiceConfigurationKeys.SYNC_LAN_ENABLED,
			SyncServiceConfigurationValues.SYNC_LAN_ENABLED);

		if (lanEnabled) {
			try {
				_syncHelper.enableLanSync(company.getCompanyId());
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		try {
			if (SyncServiceConfigurationValues.SYNC_VERIFY) {
				_verifyUtil.doVerify();
			}
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		_dlSyncEventProcessorServiceRegistration = registerMessageListener(
			DestinationNames.DOCUMENT_LIBRARY_SYNC_EVENT_PROCESSOR);

		_syncMaintenanceProcessorServiceRegistration = registerMessageListener(
			SyncMaintenanceMessageListener.DESTINATION_NAME);
	}

	@Deactivate
	protected void deactivate() {
		if (_dlSyncEventProcessorServiceRegistration != null) {
			Destination destination = _bundleContext.getService(
				_dlSyncEventProcessorServiceRegistration.getReference());

			_dlSyncEventProcessorServiceRegistration.unregister();

			destination.destroy();
		}

		if (_syncMaintenanceProcessorServiceRegistration != null) {
			Destination destination = _bundleContext.getService(
				_syncMaintenanceProcessorServiceRegistration.getReference());

			_syncMaintenanceProcessorServiceRegistration.unregister();

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

	private static final Log _log = LogFactoryUtil.getLog(
		SyncConfigurator.class);

	private volatile BundleContext _bundleContext;

	@Reference
	private DestinationFactory _destinationFactory;

	private ServiceRegistration<Destination>
		_dlSyncEventProcessorServiceRegistration;

	@Reference
	private SyncHelper _syncHelper;

	private ServiceRegistration<Destination>
		_syncMaintenanceProcessorServiceRegistration;

	@Reference
	private VerifyUtil _verifyUtil;

}