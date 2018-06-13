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
import com.liferay.commerce.product.internal.security.permission.resource.CPOptionModelResourcePermissionLogic;
import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.service.CPOptionLocalService;
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
public class CPOptionModelResourcePermissionDefinition
	implements ModelResourcePermissionDefinition<CPOption> {

	@Override
	public CPOption getModel(long cpOptionId) throws PortalException {
		return _cpOptionLocalService.getCPOption(cpOptionId);
	}

	@Override
	public Class<CPOption> getModelClass() {
		return CPOption.class;
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return _portletResourcePermission;
	}

	@Override
	public long getPrimaryKey(CPOption cpOption) {
		return cpOption.getCPOptionId();
	}

	@Override
	public void registerModelResourcePermissionLogics(
		ModelResourcePermission<CPOption> modelResourcePermission,
		Consumer<ModelResourcePermissionLogic<CPOption>>
			modelResourcePermissionLogicConsumer) {

		modelResourcePermissionLogicConsumer.accept(
			new CPOptionModelResourcePermissionLogic(
				_portletResourcePermission));
	}

	@Reference
	private CPOptionLocalService _cpOptionLocalService;

	@Reference(target = "(resource.name=" + CPConstants.RESOURCE_NAME + ")")
	private PortletResourcePermission _portletResourcePermission;

}