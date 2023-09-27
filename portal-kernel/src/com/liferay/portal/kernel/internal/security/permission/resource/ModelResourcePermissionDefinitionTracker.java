/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.internal.security.permission.resource;

import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionLogic;
import com.liferay.portal.kernel.security.permission.resource.definition.ModelResourcePermissionDefinition;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Preston Crary
 */
public class ModelResourcePermissionDefinitionTracker {

	public void afterPropertiesSet() {
		_serviceTracker = new ServiceTracker<>(
			_bundleContext,
			(Class<ModelResourcePermissionDefinition<?>>)
				(Class<?>)ModelResourcePermissionDefinition.class,
			new ModelResourcePermissionDefinitionServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	public void destroy() {
		_serviceTracker.close();
	}

	private <T extends GroupedModel> ModelResourcePermission<T> _create(
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

	private final BundleContext _bundleContext =
		SystemBundleUtil.getBundleContext();
	private ServiceTracker
		<ModelResourcePermissionDefinition<?>, ServiceRegistration<?>>
			_serviceTracker;

	private class ModelResourcePermissionDefinitionServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<ModelResourcePermissionDefinition<?>, ServiceRegistration<?>> {

		@Override
		public ServiceRegistration<?> addingService(
			ServiceReference<ModelResourcePermissionDefinition<?>>
				serviceReference) {

			ModelResourcePermissionDefinition<?>
				modelResourcePermissionDefinition = _bundleContext.getService(
					serviceReference);

			ModelResourcePermission<?> modelResourcePermission = _create(
				modelResourcePermissionDefinition);

			return _bundleContext.registerService(
				ModelResourcePermission.class, modelResourcePermission,
				HashMapDictionaryBuilder.<String, Object>put(
					"model.class.name",
					() -> {
						Class<?> modelClass =
							modelResourcePermissionDefinition.getModelClass();

						return modelClass.getName();
					}
				).put(
					"service.ranking",
					() -> serviceReference.getProperty("service.ranking")
				).build());
		}

		@Override
		public void modifiedService(
			ServiceReference<ModelResourcePermissionDefinition<?>>
				serviceReference,
			ServiceRegistration<?> serviceRegistration) {
		}

		@Override
		public void removedService(
			ServiceReference<ModelResourcePermissionDefinition<?>>
				serviceReference,
			ServiceRegistration<?> serviceRegistration) {

			serviceRegistration.unregister();

			_bundleContext.ungetService(serviceReference);
		}

	}

}