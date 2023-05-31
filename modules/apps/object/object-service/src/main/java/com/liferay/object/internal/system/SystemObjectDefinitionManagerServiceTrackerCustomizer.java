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

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFactory;
import com.liferay.object.action.engine.ObjectActionEngine;
import com.liferay.object.internal.system.model.listener.SystemObjectDefinitionManagerModelListener;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectValidationRuleLocalService;
import com.liferay.object.system.SystemObjectDefinitionManager;
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
public class SystemObjectDefinitionManagerServiceTrackerCustomizer
	implements ServiceTrackerCustomizer
		<SystemObjectDefinitionManager, SystemObjectDefinitionManager> {

	@Override
	public SystemObjectDefinitionManager addingService(
		ServiceReference<SystemObjectDefinitionManager> serviceReference) {

		SystemObjectDefinitionManager systemObjectDefinitionManager =
			_bundleContext.getService(serviceReference);

		_registerRelatedServices(systemObjectDefinitionManager);

		return systemObjectDefinitionManager;
	}

	@Override
	public void modifiedService(
		ServiceReference<SystemObjectDefinitionManager> serviceReference,
		SystemObjectDefinitionManager systemObjectDefinitionManager) {

		_unregisterRelatedServices(systemObjectDefinitionManager);

		_registerRelatedServices(systemObjectDefinitionManager);
	}

	@Override
	public void removedService(
		ServiceReference<SystemObjectDefinitionManager> serviceReference,
		SystemObjectDefinitionManager systemObjectDefinitionManager) {

		_unregisterRelatedServices(systemObjectDefinitionManager);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_serviceTracker = ServiceTrackerFactory.open(
			bundleContext, SystemObjectDefinitionManager.class, this);
	}

	@Deactivate
	protected void deactivate() {
		_bundleContext = null;

		_serviceTracker.close();
	}

	private void _registerRelatedServices(
		SystemObjectDefinitionManager systemObjectDefinitionManager) {

		_serviceRegistrationsMap.put(
			systemObjectDefinitionManager.getModelClass(),
			ListUtil.fromArray(
				_bundleContext.registerService(
					ModelListener.class.getName(),
					new SystemObjectDefinitionManagerModelListener(
						_ddmExpressionFactory, _dtoConverterRegistry,
						_jsonFactory,
						systemObjectDefinitionManager.getModelClass(),
						_objectActionEngine, _objectDefinitionLocalService,
						_objectEntryLocalService, _objectFieldLocalService,
						_objectValidationRuleLocalService,
						systemObjectDefinitionManager, _userLocalService),
					null)));
	}

	private void _unregisterRelatedServices(
		SystemObjectDefinitionManager systemObjectDefinitionManager) {

		List<ServiceRegistration<?>> serviceRegistrations =
			_serviceRegistrationsMap.remove(
				systemObjectDefinitionManager.getModelClass());

		for (ServiceRegistration<?> serviceRegistration :
				serviceRegistrations) {

			serviceRegistration.unregister();
		}
	}

	private BundleContext _bundleContext;

	@Reference
	private DDMExpressionFactory _ddmExpressionFactory;

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
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private ObjectValidationRuleLocalService _objectValidationRuleLocalService;

	private final Map<Class<?>, List<ServiceRegistration<?>>>
		_serviceRegistrationsMap = new ConcurrentHashMap<>();
	private ServiceTracker
		<SystemObjectDefinitionManager, SystemObjectDefinitionManager>
			_serviceTracker;

	@Reference
	private UserLocalService _userLocalService;

}