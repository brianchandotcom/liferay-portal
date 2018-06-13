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
import com.liferay.commerce.product.internal.security.permission.resource.CPSpecificationOptionModelResourcePermissionLogic;
import com.liferay.commerce.product.model.CPSpecificationOption;
import com.liferay.commerce.product.service.CPSpecificationOptionLocalService;
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
public class CPSpecificationOptionModelResourcePermissionDefinition
	implements ModelResourcePermissionDefinition<CPSpecificationOption> {

	@Override
	public CPSpecificationOption getModel(long cpSpecificationOptionId)
		throws PortalException {

		return _cpSpecificationOptionLocalService.getCPSpecificationOption(
			cpSpecificationOptionId);
	}

	@Override
	public Class<CPSpecificationOption> getModelClass() {
		return CPSpecificationOption.class;
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return _portletResourcePermission;
	}

	@Override
	public long getPrimaryKey(CPSpecificationOption cpSpecificationOption) {
		return cpSpecificationOption.getCPSpecificationOptionId();
	}

	@Override
	public void registerModelResourcePermissionLogics(
		ModelResourcePermission<CPSpecificationOption> modelResourcePermission,
		Consumer<ModelResourcePermissionLogic<CPSpecificationOption>>
			modelResourcePermissionLogicConsumer) {

		modelResourcePermissionLogicConsumer.accept(
			new CPSpecificationOptionModelResourcePermissionLogic(
				_portletResourcePermission));
	}

	@Reference
	private CPSpecificationOptionLocalService
		_cpSpecificationOptionLocalService;

	@Reference(target = "(resource.name=" + CPConstants.RESOURCE_NAME + ")")
	private PortletResourcePermission _portletResourcePermission;

}