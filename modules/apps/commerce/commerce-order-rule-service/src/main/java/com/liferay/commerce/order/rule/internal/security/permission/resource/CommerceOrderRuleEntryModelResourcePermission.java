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

package com.liferay.commerce.order.rule.internal.security.permission.resource;

import com.liferay.commerce.order.rule.constants.CommerceOrderRuleEntryConstants;
import com.liferay.commerce.order.rule.model.CommerceOrderRuleEntry;
import com.liferay.commerce.order.rule.permission.CommerceOrderRuleEntryPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = "model.class.name=com.liferay.commerce.order.rule.model.CommerceOrderRuleEntry",
	service = ModelResourcePermission.class
)
public class CommerceOrderRuleEntryModelResourcePermission
	implements ModelResourcePermission<CommerceOrderRuleEntry> {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			CommerceOrderRuleEntry commerceOrderRuleEntry, String actionId)
		throws PortalException {

		_commerceOrderRuleEntryPermission.check(
			permissionChecker, commerceOrderRuleEntry, actionId);
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long commercePriceListId,
			String actionId)
		throws PortalException {

		_commerceOrderRuleEntryPermission.check(
			permissionChecker, commercePriceListId, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			CommerceOrderRuleEntry commercePriceList, String actionId)
		throws PortalException {

		return _commerceOrderRuleEntryPermission.contains(
			permissionChecker, commercePriceList, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long commercePriceListId,
			String actionId)
		throws PortalException {

		return _commerceOrderRuleEntryPermission.contains(
			permissionChecker, commercePriceListId, actionId);
	}

	@Override
	public String getModelName() {
		return CommerceOrderRuleEntry.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return _portletResourcePermission;
	}

	@Reference
	private CommerceOrderRuleEntryPermission _commerceOrderRuleEntryPermission;

	@Reference(
		target = "(resource.name=" + CommerceOrderRuleEntryConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}