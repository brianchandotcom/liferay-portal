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
import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.model.CPRule;
import com.liferay.commerce.product.service.base.CPRuleServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CPRuleServiceImpl extends CPRuleServiceBaseImpl {

	@Override
	public CPRule addCPRule(
			String name, boolean active, String type, String typeSettings,
			ServiceContext serviceContext)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			CPActionKeys.ADD_COMMERCE_PRODUCT_RULE);

		return cpRuleLocalService.addCPRule(
			name, active, type, typeSettings, serviceContext);
	}

	@Override
	public CPRule deleteCPRule(long cpRuleId) throws PortalException {
		_cpRuleResourcePermission.check(
			getPermissionChecker(), cpRuleId, ActionKeys.DELETE);

		return cpRuleLocalService.deleteCPRule(cpRuleId);
	}

	@Override
	public CPRule getCPRule(long cpRuleId) throws PortalException {
		_cpRuleResourcePermission.check(
			getPermissionChecker(), cpRuleId, ActionKeys.VIEW);

		return cpRuleLocalService.getCPRule(cpRuleId);
	}

	@Override
	public List<CPRule> getCPRules(
			long groupId, int start, int end,
			OrderByComparator<CPRule> orderByComparator)
		throws PortalException {

		_portletResourcePermission.check(
			getPermissionChecker(), groupId,
			CPActionKeys.VIEW_COMMERCE_PRODUCT_RULES);

		return cpRuleLocalService.getCPRules(
			groupId, start, end, orderByComparator);
	}

	@Override
	public int getCPRulesCount(long groupId) throws PortalException {
		_portletResourcePermission.check(
			getPermissionChecker(), groupId,
			CPActionKeys.VIEW_COMMERCE_PRODUCT_RULES);

		return cpRuleLocalService.getCPRulesCount(groupId);
	}

	@Override
	public BaseModelSearchResult<CPRule> searchCPRules(
			long companyId, long groupId, String keywords, int start, int end,
			Sort sort)
		throws PortalException {

		return cpRuleLocalService.searchCPRules(
			companyId, groupId, keywords, start, end, sort);
	}

	@Override
	public BaseModelSearchResult<CPRule> searchCPRules(
			SearchContext searchContext)
		throws PortalException {

		return cpRuleLocalService.searchCPRules(searchContext);
	}

	@Override
	public CPRule updateCPRule(
			long cpRuleId, String name, boolean active, String type,
			String typeSettings, ServiceContext serviceContext)
		throws PortalException {

		_cpRuleResourcePermission.check(
			getPermissionChecker(), cpRuleId, ActionKeys.UPDATE);

		return cpRuleLocalService.updateCPRule(
			cpRuleId, name, active, type, typeSettings, serviceContext);
	}

	private static volatile ModelResourcePermission<CPRule>
		_cpRuleResourcePermission = ModelResourcePermissionFactory.getInstance(
			CPRuleServiceImpl.class, "_cpRuleResourcePermission", CPRule.class);
	private static volatile PortletResourcePermission
		_portletResourcePermission =
			PortletResourcePermissionFactory.getInstance(
				CPRuleServiceImpl.class, "_portletResourcePermission",
				CPConstants.RESOURCE_NAME);

}