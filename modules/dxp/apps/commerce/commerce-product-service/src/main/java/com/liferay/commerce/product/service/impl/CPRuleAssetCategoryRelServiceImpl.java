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

package com.liferay.commerce.product.service.impl;

import com.liferay.commerce.product.constants.CPActionKeys;
import com.liferay.commerce.product.model.CPRule;
import com.liferay.commerce.product.model.CPRuleAssetCategoryRel;
import com.liferay.commerce.product.service.base.CPRuleAssetCategoryRelServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.List;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CPRuleAssetCategoryRelServiceImpl
	extends CPRuleAssetCategoryRelServiceBaseImpl {

	@Override
	public CPRuleAssetCategoryRel addCPRuleAssetCategoryRel(
			long cpRuleId, long assetCategoryId, ServiceContext serviceContext)
		throws PortalException {

		_cpRuleResourcePermission.check(
			getPermissionChecker(), cpRuleId,
			CPActionKeys.ADD_COMMERCE_PRODUCT_RULE_CATEGORY_REL);

		return cpRuleAssetCategoryRelLocalService.addCPRuleAssetCategoryRel(
			cpRuleId, assetCategoryId, serviceContext);
	}

	@Override
	public void deleteCPRuleAssetCategoryRel(long cpRuleAssetCategoryRelId)
		throws PortalException {

		CPRuleAssetCategoryRel cpRuleAssetCategoryRel =
			cpRuleAssetCategoryRelLocalService.getCPRuleAssetCategoryRel(
				cpRuleAssetCategoryRelId);

		_cpRuleResourcePermission.check(
			getPermissionChecker(), cpRuleAssetCategoryRel.getCPRuleId(),
			CPActionKeys.DELETE_COMMERCE_PRODUCT_RULE_CATEGORY_REL);

		cpRuleAssetCategoryRelLocalService.deleteCPRuleAssetCategoryRel(
			cpRuleAssetCategoryRel);
	}

	@Override
	public List<Long> getAssetCategoryIds(long cpRuleId)
		throws PortalException {

		_cpRuleResourcePermission.check(
			getPermissionChecker(), cpRuleId, ActionKeys.VIEW);

		return cpRuleAssetCategoryRelLocalService.getAssetCategoryIds(cpRuleId);
	}

	@Override
	public CPRuleAssetCategoryRel getCPRuleAssetCategoryRel(
			long cpRuleAssetCategoryRelId)
		throws PortalException {

		CPRuleAssetCategoryRel cpRuleAssetCategoryRel =
			cpRuleAssetCategoryRelLocalService.getCPRuleAssetCategoryRel(
				cpRuleAssetCategoryRelId);

		_cpRuleResourcePermission.check(
			getPermissionChecker(), cpRuleAssetCategoryRel.getCPRuleId(),
			ActionKeys.VIEW);

		return cpRuleAssetCategoryRel;
	}

	@Override
	public List<CPRuleAssetCategoryRel> getCPRuleAssetCategoryRels(
			long cpRuleId)
		throws PortalException {

		_cpRuleResourcePermission.check(
			getPermissionChecker(), cpRuleId, ActionKeys.VIEW);

		return cpRuleAssetCategoryRelLocalService.getCPRuleAssetCategoryRels(
			cpRuleId);
	}

	private static volatile ModelResourcePermission<CPRule>
		_cpRuleResourcePermission = ModelResourcePermissionFactory.getInstance(
			CPRuleUserSegmentRelServiceImpl.class, "_cpRuleResourcePermission",
			CPRule.class);

}