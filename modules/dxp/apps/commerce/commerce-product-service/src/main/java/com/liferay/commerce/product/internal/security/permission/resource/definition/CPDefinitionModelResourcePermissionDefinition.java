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

import com.liferay.commerce.product.catalog.rule.CPRuleTypeRegistry;
import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.internal.security.permission.resource.CPDefinitionModelResourcePermissionLogic;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
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
public class CPDefinitionModelResourcePermissionDefinition
	implements ModelResourcePermissionDefinition<CPDefinition> {

	@Override
	public CPDefinition getModel(long cpDefinitionId) throws PortalException {
		return _cpDefinitionLocalService.getCPDefinition(cpDefinitionId);
	}

	@Override
	public Class<CPDefinition> getModelClass() {
		return CPDefinition.class;
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return _portletResourcePermission;
	}

	@Override
	public long getPrimaryKey(CPDefinition cpDefinition) {
		return cpDefinition.getCPDefinitionId();
	}

	@Override
	public void registerModelResourcePermissionLogics(
		ModelResourcePermission<CPDefinition> modelResourcePermission,
		Consumer<ModelResourcePermissionLogic<CPDefinition>>
			modelResourcePermissionLogicConsumer) {

		modelResourcePermissionLogicConsumer.accept(
			new CPDefinitionModelResourcePermissionLogic(
				_cpRuleTypeRegistry, _portletResourcePermission));
	}

	@Reference
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Reference
	private CPRuleTypeRegistry _cpRuleTypeRegistry;

	@Reference(target = "(resource.name=" + CPConstants.RESOURCE_NAME + ")")
	private PortletResourcePermission _portletResourcePermission;

}