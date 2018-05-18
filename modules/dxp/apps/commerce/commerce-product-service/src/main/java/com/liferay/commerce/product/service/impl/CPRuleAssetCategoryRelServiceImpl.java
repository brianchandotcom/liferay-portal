/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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
	public long[] getAssetCategoryIds(long cpRuleId) throws PortalException {
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