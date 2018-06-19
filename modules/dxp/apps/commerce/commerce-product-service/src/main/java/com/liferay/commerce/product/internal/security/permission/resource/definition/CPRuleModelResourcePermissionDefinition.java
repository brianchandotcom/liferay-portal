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
import com.liferay.commerce.product.model.CPRule;
import com.liferay.commerce.product.service.CPRuleLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionLogic;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.definition.ModelResourcePermissionDefinition;

import java.util.function.Consumer;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(immediate = true, service = ModelResourcePermissionDefinition.class)
public class CPRuleModelResourcePermissionDefinition
	implements ModelResourcePermissionDefinition<CPRule> {

	@Override
	public CPRule getModel(long cpRuleId) throws PortalException {
		return _cpRuleLocalService.getCPRule(cpRuleId);
	}

	@Override
	public Class<CPRule> getModelClass() {
		return CPRule.class;
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return _portletResourcePermission;
	}

	@Override
	public long getPrimaryKey(CPRule cpRule) {
		return cpRule.getCPRuleId();
	}

	@Override
	public void registerModelResourcePermissionLogics(
		ModelResourcePermission<CPRule> modelResourcePermission,
		Consumer<ModelResourcePermissionLogic<CPRule>>
			modelResourcePermissionLogicConsumer) {
	}

	@Reference
	private CPRuleLocalService _cpRuleLocalService;

	@Reference(target = "(resource.name=" + CPConstants.RESOURCE_NAME + ")")
	private PortletResourcePermission _portletResourcePermission;

}