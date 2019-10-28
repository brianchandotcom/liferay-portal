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

package com.liferay.portal.kernel.internal.security.permission.resource;

import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionLogic;
import com.liferay.portal.kernel.security.permission.resource.definition.ModelResourcePermissionDefinition;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceRegistration;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Preston Crary
 */
public class ModelResourcePermissionDefinitionTracker {

	public void afterPropertiesSet() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			ModelResourcePermissionDefinition.class,
			new ModelResourcePermissionDefinitionServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	public void destroy() {
		_serviceTracker.close();
	}

	private static <T extends GroupedModel> ModelResourcePermission<T> _create(
		ModelResourcePermissionDefinition<T>
			modelResourcePermissionDefinition) {

		List<ModelResourcePermissionLogic<T>> modelResourcePermissionLogics =
			new ArrayList<>();

		ModelResourcePermission<T> modelResourcePermission =
			new DefaultModelResourcePermission<>(
				modelResourcePermissionDefinition,
				modelResourcePermissionLogics);

		modelResourcePermissionDefinition.registerModelResourcePermissionLogics(
			modelResourcePermission, modelResourcePermissionLogics::add);

		return modelResourcePermission;
	}

	private ServiceTracker
		<ModelResourcePermissionDefinition, ServiceRegistration<?>>
			_serviceTracker;

	private static class
		ModelResourcePermissionDefinitionServiceTrackerCustomizer
			implements ServiceTrackerCustomizer
				<ModelResourcePermissionDefinition, ServiceRegistration<?>> {

		@Override
		public ServiceRegistration<?> addingService(
			ServiceReference<ModelResourcePermissionDefinition>
				serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			ModelResourcePermissionDefinition<?>
				modelResourcePermissionDefinition = registry.getService(
					serviceReference);

			ModelResourcePermission<?> modelResourcePermission = _create(
				modelResourcePermissionDefinition);

			Class<?> modelClass =
				modelResourcePermissionDefinition.getModelClass();

			Map<String, Object> properties = HashMapBuilder.<String, Object>put(
				"model.class.name", modelClass.getName()
			).build();

			Object serviceRanking = serviceReference.getProperty(
				"service.ranking");

			if (serviceRanking != null) {
				properties.put("service.ranking", serviceRanking);
			}

			return registry.registerService(
				ModelResourcePermission.class, modelResourcePermission,
				properties);
		}

		@Override
		public void modifiedService(
			ServiceReference<ModelResourcePermissionDefinition>
				serviceReference,
			ServiceRegistration<?> serviceRegistration) {
		}

		@Override
		public void removedService(
			ServiceReference<ModelResourcePermissionDefinition>
				serviceReference,
			ServiceRegistration<?> serviceRegistration) {

			serviceRegistration.unregister();

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);
		}

	}

}