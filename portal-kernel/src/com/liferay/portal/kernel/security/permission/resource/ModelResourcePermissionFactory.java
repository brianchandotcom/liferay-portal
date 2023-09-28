/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.permission.resource;

import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.internal.security.permission.resource.DefaultModelResourcePermission;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.model.GroupedModel;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.ToLongFunction;
import java.util.function.UnaryOperator;

/**
 * @author Preston Crary
 */
public class ModelResourcePermissionFactory {

	public static <T extends GroupedModel> ModelResourcePermission<T> create(
		Class<T> modelClass, ToLongFunction<T> primKeyToLongFunction,
		UnsafeFunction<Long, T, ? extends PortalException>
			getModelUnsafeFunction,
		PortletResourcePermission portletResourcePermission,
		ModelResourcePermissionConfigurator<T>
			modelResourcePermissionConfigurator) {

		return create(
			modelClass, primKeyToLongFunction, getModelUnsafeFunction,
			portletResourcePermission, modelResourcePermissionConfigurator,
			UnaryOperator.identity());
	}

	public static <T extends GroupedModel> ModelResourcePermission<T> create(
		Class<T> modelClass, ToLongFunction<T> primKeyToLongFunction,
		UnsafeFunction<Long, T, ? extends PortalException>
			getModelUnsafeFunction,
		PortletResourcePermission portletResourcePermission,
		ModelResourcePermissionConfigurator<T>
			modelResourcePermissionConfigurator,
		UnaryOperator<String> actionIdMapper) {

		List<ModelResourcePermissionLogic<T>> modelResourcePermissionLogics =
			new ArrayList<>();

		ModelResourcePermission<T> modelResourcePermission =
			new DefaultModelResourcePermission<>(
				modelResourcePermissionLogics, modelClass,
				primKeyToLongFunction, getModelUnsafeFunction,
				portletResourcePermission, actionIdMapper);

		modelResourcePermissionConfigurator.
			configureModelResourcePermissionLogics(
				modelResourcePermission, modelResourcePermissionLogics::add);

		return modelResourcePermission;
	}

	@SuppressWarnings("unchecked")
	public static <T extends ClassedModel> ModelResourcePermission<T>
		getInstance(
			Class<? extends BaseService> declaringServiceClass,
			String fieldName, Class<T> modelClass) {

		return ServiceProxyFactory.newServiceTrackedInstance(
			ModelResourcePermission.class, declaringServiceClass, fieldName,
			"(model.class.name=" + modelClass.getName() + ")", true);
	}

	@FunctionalInterface
	public interface ModelResourcePermissionConfigurator
		<T extends GroupedModel> {

		public void configureModelResourcePermissionLogics(
			ModelResourcePermission<T> modelResourcePermission,
			Consumer<ModelResourcePermissionLogic<T>> consumer);

	}

}