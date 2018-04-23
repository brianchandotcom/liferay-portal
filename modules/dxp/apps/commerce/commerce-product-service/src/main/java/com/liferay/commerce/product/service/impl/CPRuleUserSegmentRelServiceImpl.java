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
import com.liferay.commerce.product.model.CPRuleUserSegmentRel;
import com.liferay.commerce.product.service.base.CPRuleUserSegmentRelServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CPRuleUserSegmentRelServiceImpl
	extends CPRuleUserSegmentRelServiceBaseImpl {

	@Override
	public CPRuleUserSegmentRel addCPRuleUserSegmentRel(
			long cpRuleId, long commerceUserSegmentEntryId,
			ServiceContext serviceContext)
		throws PortalException {

		_cpRuleResourcePermission.check(
			getPermissionChecker(), cpRuleId,
			CPActionKeys.ADD_COMMERCE_PRODUCT_RULE_USER_SEGMENT);

		return cpRuleUserSegmentRelLocalService.addCPRuleUserSegmentRel(
			cpRuleId, commerceUserSegmentEntryId, serviceContext);
	}

	@Override
	public void deleteCPRuleUserSegmentRel(long cpRuleUserSegmentRelId)
		throws PortalException {

		CPRuleUserSegmentRel cpRuleUserSegmentRel =
			cpRuleUserSegmentRelLocalService.getCPRuleUserSegmentRel(
				cpRuleUserSegmentRelId);

		_cpRuleResourcePermission.check(
			getPermissionChecker(), cpRuleUserSegmentRel.getCPRuleId(),
			CPActionKeys.DELETE_COMMERCE_PRODUCT_RULE_USER_SEGMENT);

		cpRuleUserSegmentRelLocalService.deleteCPRuleUserSegmentRel(
			cpRuleUserSegmentRel);
	}

	@Override
	public CPRuleUserSegmentRel getCPRuleUserSegmentRel(
			long cpRuleUserSegmentRelId)
		throws PortalException {

		CPRuleUserSegmentRel cpRuleUserSegmentRel =
			cpRuleUserSegmentRelLocalService.getCPRuleUserSegmentRel(
				cpRuleUserSegmentRelId);

		_cpRuleResourcePermission.check(
			getPermissionChecker(), cpRuleUserSegmentRel.getCPRuleId(),
			ActionKeys.VIEW);

		return cpRuleUserSegmentRel;
	}

	@Override
	public List<CPRuleUserSegmentRel> getCPRuleUserSegmentRels(
			long cpRuleId, int start, int end,
			OrderByComparator<CPRuleUserSegmentRel> orderByComparator)
		throws PortalException {

		_cpRuleResourcePermission.check(
			getPermissionChecker(), cpRuleId, ActionKeys.VIEW);

		return cpRuleUserSegmentRelLocalService.getCPRuleUserSegmentRels(
			cpRuleId, start, end, orderByComparator);
	}

	@Override
	public int getCPRuleUserSegmentRelsCount(long cpRuleId)
		throws PortalException {

		_cpRuleResourcePermission.check(
			getPermissionChecker(), cpRuleId, ActionKeys.VIEW);

		return cpRuleUserSegmentRelLocalService.getCPRuleUserSegmentRelsCount(
			cpRuleId);
	}

	private static volatile ModelResourcePermission<CPRule>
		_cpRuleResourcePermission = ModelResourcePermissionFactory.getInstance(
			CPRuleUserSegmentRelServiceImpl.class, "_cpRuleResourcePermission",
			CPRule.class);

}