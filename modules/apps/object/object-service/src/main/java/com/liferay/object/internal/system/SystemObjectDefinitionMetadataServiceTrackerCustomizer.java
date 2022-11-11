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
import com.liferay.object.internal.system.model.listener.SystemObjectDefinitionMetadataModelListener;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectValidationRuleLocalService;
import com.liferay.object.system.SystemObjectDefinitionMetadata;
import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
 * @author Drew Brokke
 */
@Component(service = {})
public class SystemObjectDefinitionMetadataServiceTrackerCustomizer
	implements ServiceTrackerCustomizer
		<SystemObjectDefinitionMetadata, SystemObjectDefinitionMetadata> {

	@Override
	public SystemObjectDefinitionMetadata addingService(
		ServiceReference<SystemObjectDefinitionMetadata> serviceReference) {

		SystemObjectDefinitionMetadata systemObjectDefinitionMetadata =
			_bundleContext.getService(serviceReference);

		_registerRelatedServices(systemObjectDefinitionMetadata);

		return systemObjectDefinitionMetadata;
	}

	@Override
	public void modifiedService(
		ServiceReference<SystemObjectDefinitionMetadata> serviceReference,
		SystemObjectDefinitionMetadata systemObjectDefinitionMetadata) {

		_unregisterRelatedServices(systemObjectDefinitionMetadata);

		_registerRelatedServices(systemObjectDefinitionMetadata);
	}

	@Override
	public void removedService(
		ServiceReference<SystemObjectDefinitionMetadata> serviceReference,
		SystemObjectDefinitionMetadata systemObjectDefinitionMetadata) {

		_unregisterRelatedServices(systemObjectDefinitionMetadata);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_serviceTracker = ServiceTrackerFactory.open(
			bundleContext, SystemObjectDefinitionMetadata.class, this);
	}

	@Deactivate
	protected void deactivate() {
		_bundleContext = null;

		_serviceTracker.close();
	}

	private void _registerRelatedServices(
		SystemObjectDefinitionMetadata systemObjectDefinitionMetadata) {

		_serviceRegistrationsMap.put(
			systemObjectDefinitionMetadata.getModelClass(),
			ListUtil.fromArray(
				_bundleContext.registerService(
					ModelListener.class.getName(),
					new SystemObjectDefinitionMetadataModelListener(
						_dtoConverterRegistry, _jsonFactory,
						systemObjectDefinitionMetadata.getModelClass(),
						_objectActionEngine, _objectDefinitionLocalService,
						_objectEntryLocalService,
						_objectValidationRuleLocalService,
						systemObjectDefinitionMetadata, _userLocalService),
					null)));
	}

	private void _unregisterRelatedServices(
		SystemObjectDefinitionMetadata systemObjectDefinitionMetadata) {

		List<ServiceRegistration<?>> serviceRegistrations =
			_serviceRegistrationsMap.remove(
				systemObjectDefinitionMetadata.getModelClass());

		for (ServiceRegistration<?> serviceRegistration :
				serviceRegistrations) {

			serviceRegistration.unregister();
		}
	}

	private BundleContext _bundleContext;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private ObjectActionEngine _objectActionEngine;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private ObjectValidationRuleLocalService _objectValidationRuleLocalService;

	private final Map<Class<?>, List<ServiceRegistration<?>>>
		_serviceRegistrationsMap = new ConcurrentHashMap<>();
	private ServiceTracker
		<SystemObjectDefinitionMetadata, SystemObjectDefinitionMetadata>
			_serviceTracker;

	@Reference
	private UserLocalService _userLocalService;

}