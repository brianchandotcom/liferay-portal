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

package com.liferay.commerce.order.rule.service.impl;

import com.liferay.commerce.order.rule.constants.CommerceOrderRuleEntryActionKeys;
import com.liferay.commerce.order.rule.model.CommerceOrderRuleEntry;
import com.liferay.commerce.order.rule.service.base.CommerceOrderRuleEntryServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Luca Pellizzon
 */
@Component(
	enabled = false,
	property = {
		"json.web.service.context.name=commerce",
		"json.web.service.context.path=CommerceOrderRuleEntry"
	},
	service = AopService.class
)
public class CommerceOrderRuleEntryServiceImpl
	extends CommerceOrderRuleEntryServiceBaseImpl {

	@Override
	public CommerceOrderRuleEntry addCommerceOrderRuleEntry(
			String externalReferenceCode, boolean active, String description,
			String name, int priority, String type, String typeSettings)
		throws PortalException {

		PortletResourcePermission portletResourcePermission =
			_commerceOrderRuleEntryModelResourcePermission.
				getPortletResourcePermission();

		portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceOrderRuleEntryActionKeys.ADD_COMMERCE_ORDER_RULE);

		return commerceOrderRuleEntryLocalService.addCommerceOrderRuleEntry(
			externalReferenceCode, getUserId(), active, description, name,
			priority, type, typeSettings);
	}

	@Override
	public CommerceOrderRuleEntry deleteCommerceOrderRuleEntry(
			long commerceOrderRuleEntryId)
		throws PortalException {

		_commerceOrderRuleEntryModelResourcePermission.check(
			getPermissionChecker(), commerceOrderRuleEntryId,
			ActionKeys.DELETE);

		return commerceOrderRuleEntryLocalService.deleteCommerceOrderRuleEntry(
			commerceOrderRuleEntryId);
	}

	@Override
	public List<CommerceOrderRuleEntry> getCommerceOrderRuleEntries(
			long companyId, boolean active, int start, int end)
		throws PortalException {

		PortletResourcePermission portletResourcePermission =
			_commerceOrderRuleEntryModelResourcePermission.
				getPortletResourcePermission();

		portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceOrderRuleEntryActionKeys.VIEW_COMMERCE_ORDER_RULES);

		return commerceOrderRuleEntryLocalService.getCommerceOrderRuleEntries(
			companyId, active, start, end);
	}

	@Override
	public List<CommerceOrderRuleEntry> getCommerceOrderRuleEntries(
			long companyId, boolean active, String type, int start, int end)
		throws PortalException {

		PortletResourcePermission portletResourcePermission =
			_commerceOrderRuleEntryModelResourcePermission.
				getPortletResourcePermission();

		portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceOrderRuleEntryActionKeys.VIEW_COMMERCE_ORDER_RULES);

		return commerceOrderRuleEntryLocalService.getCommerceOrderRuleEntries(
			companyId, active, type, start, end);
	}

	@Override
	public List<CommerceOrderRuleEntry> getCommerceOrderRuleEntries(
			long companyId, String type, int start, int end)
		throws PortalException {

		PortletResourcePermission portletResourcePermission =
			_commerceOrderRuleEntryModelResourcePermission.
				getPortletResourcePermission();

		portletResourcePermission.check(
			getPermissionChecker(), null,
			CommerceOrderRuleEntryActionKeys.VIEW_COMMERCE_ORDER_RULES);

		return commerceOrderRuleEntryLocalService.getCommerceOrderRuleEntries(
			companyId, type, start, end);
	}

	@Override
	public CommerceOrderRuleEntry updateCommerceOrderRuleEntry(
			long commerceOrderRuleEntryId, boolean active, String description,
			String name, int priority, String typeSettings)
		throws PortalException {

		_commerceOrderRuleEntryModelResourcePermission.check(
			getPermissionChecker(), commerceOrderRuleEntryId,
			ActionKeys.UPDATE);

		return commerceOrderRuleEntryLocalService.updateCommerceOrderRuleEntry(
			commerceOrderRuleEntryId, active, description, name, priority,
			typeSettings);
	}

	private static volatile ModelResourcePermission<CommerceOrderRuleEntry>
		_commerceOrderRuleEntryModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CommerceOrderRuleEntryServiceImpl.class,
				"_commerceOrderRuleEntryModelResourcePermission",
				CommerceOrderRuleEntry.class);

}