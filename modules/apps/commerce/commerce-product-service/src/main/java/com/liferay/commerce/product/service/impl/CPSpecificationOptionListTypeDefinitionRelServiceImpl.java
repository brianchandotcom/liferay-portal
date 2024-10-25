/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.service.impl;

import com.liferay.commerce.product.constants.CPActionKeys;
import com.liferay.commerce.product.model.CPSpecificationOption;
import com.liferay.commerce.product.model.CPSpecificationOptionListTypeDefinitionRel;
import com.liferay.commerce.product.service.base.CPSpecificationOptionListTypeDefinitionRelServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	property = {
		"json.web.service.context.name=commerce",
		"json.web.service.context.path=CPSpecificationOptionListTypeDefinitionRel"
	},
	service = AopService.class
)
public class CPSpecificationOptionListTypeDefinitionRelServiceImpl
	extends CPSpecificationOptionListTypeDefinitionRelServiceBaseImpl {

	@Override
	public CPSpecificationOptionListTypeDefinitionRel
			addCPSpecificationOptionListTypeDefinitionRel(
				long cpSpecificationOptionId, long listTypeDefinitionId)
		throws PortalException {

		PortletResourcePermission portletResourcePermission =
			_cpSpecificationOptionModelResourcePermission.
				getPortletResourcePermission();

		portletResourcePermission.check(
			getPermissionChecker(), null,
			CPActionKeys.ADD_COMMERCE_PRODUCT_SPECIFICATION_OPTION);

		return cpSpecificationOptionListTypeDefinitionRelLocalService.
			addCPSpecificationOptionListTypeDefinitionRel(
				cpSpecificationOptionId, listTypeDefinitionId);
	}

	@Override
	public void deleteCPSpecificationOptionListTypeDefinitionRel(
			long cpSpecificationOptionId, long listTypeDefinitionId)
		throws PortalException {

		_cpSpecificationOptionModelResourcePermission.check(
			getPermissionChecker(), cpSpecificationOptionId, ActionKeys.DELETE);

		cpSpecificationOptionListTypeDefinitionRelLocalService.
			deleteCPSpecificationOptionListTypeDefinitionRel(
				cpSpecificationOptionId, listTypeDefinitionId);
	}

	@Reference(
		target = "(model.class.name=com.liferay.commerce.product.model.CPSpecificationOption)"
	)
	private ModelResourcePermission<CPSpecificationOption>
		_cpSpecificationOptionModelResourcePermission;

}