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

package com.liferay.commerce.product.internal.security.permission.resource.definition;

import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.internal.security.permission.resource.CPOptionCategoryModelResourcePermissionLogic;
import com.liferay.commerce.product.model.CPOptionCategory;
import com.liferay.commerce.product.service.CPOptionCategoryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionLogic;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.definition.ModelResourcePermissionDefinition;

import java.util.function.Consumer;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(immediate = true, service = ModelResourcePermissionDefinition.class)
public class CPOptionCategoryModelResourcePermissionDefinition
	implements ModelResourcePermissionDefinition<CPOptionCategory> {

	@Override
	public CPOptionCategory getModel(long cpOptionCategoryId)
		throws PortalException {

		return _cpOptionCategoryLocalService.getCPOptionCategory(
			cpOptionCategoryId);
	}

	@Override
	public Class<CPOptionCategory> getModelClass() {
		return CPOptionCategory.class;
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return _portletResourcePermission;
	}

	@Override
	public long getPrimaryKey(CPOptionCategory cpOptionCategory) {
		return cpOptionCategory.getCPOptionCategoryId();
	}

	@Override
	public void registerModelResourcePermissionLogics(
		ModelResourcePermission<CPOptionCategory> modelResourcePermission,
		Consumer<ModelResourcePermissionLogic<CPOptionCategory>>
			modelResourcePermissionLogicConsumer) {

		modelResourcePermissionLogicConsumer.accept(
			new CPOptionCategoryModelResourcePermissionLogic(
				_portletResourcePermission));
	}

	@Reference
	private CPOptionCategoryLocalService _cpOptionCategoryLocalService;

	@Reference(target = "(resource.name=" + CPConstants.RESOURCE_NAME + ")")
	private PortletResourcePermission _portletResourcePermission;

}