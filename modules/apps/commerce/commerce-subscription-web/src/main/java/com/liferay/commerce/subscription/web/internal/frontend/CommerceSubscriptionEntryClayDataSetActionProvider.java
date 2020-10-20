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

package com.liferay.commerce.subscription.web.internal.frontend;

import com.liferay.commerce.constants.CommerceActionKeys;
import com.liferay.commerce.constants.CommerceConstants;
import com.liferay.commerce.model.CommerceSubscriptionEntry;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.subscription.web.internal.frontend.constants.CommerceSubscriptionDataSetConstants;
import com.liferay.commerce.subscription.web.internal.model.SubscriptionEntry;
import com.liferay.frontend.taglib.clay.data.set.ClayDataSetActionProvider;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.Portal;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = "clay.data.provider.key=" + CommerceSubscriptionDataSetConstants.COMMERCE_DATA_SET_KEY_SUBSCRIPTION_ENTRIES,
	service = ClayDataSetActionProvider.class
)
public class CommerceSubscriptionEntryClayDataSetActionProvider
	implements ClayDataSetActionProvider {

	@Override
	public List<DropdownItem> getDropdownItems(
			HttpServletRequest httpServletRequest, long groupId, Object model)
		throws PortalException {

		SubscriptionEntry subscriptionEntry = (SubscriptionEntry)model;

		return DropdownItemListBuilder.add(
			() -> _portletResourcePermission.contains(
				PermissionThreadLocal.getPermissionChecker(), null,
				CommerceActionKeys.MANAGE_COMMERCE_SUBSCRIPTIONS),
			dropdownItem -> {
				dropdownItem.setHref(
					_getSubscriptionEntryEditURL(
						subscriptionEntry.getSubscriptionId(),
						httpServletRequest));
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "edit"));
			}
		).add(
			() -> _portletResourcePermission.contains(
				PermissionThreadLocal.getPermissionChecker(), null,
				CommerceActionKeys.MANAGE_COMMERCE_SUBSCRIPTIONS),
			dropdownItem -> {
				dropdownItem.setHref(
					_getSubscriptionEntryDeleteURL(
						subscriptionEntry.getSubscriptionId(),
						httpServletRequest));
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "delete"));
			}
		).build();
	}

	private PortletURL _getSubscriptionEntryDeleteURL(
		long commerceSubscriptionEntryId,
		HttpServletRequest httpServletRequest) {

		PortletURL portletURL = _portal.getControlPanelPortletURL(
			httpServletRequest, CPPortletKeys.COMMERCE_SUBSCRIPTION_ENTRY,
			PortletRequest.ACTION_PHASE);

		portletURL.setParameter("redirect", portletURL.toString());

		portletURL.setParameter(
			ActionRequest.ACTION_NAME,
			"/commerce_subscription_entry/edit_commerce_subscription_entry");
		portletURL.setParameter(Constants.CMD, Constants.DELETE);
		portletURL.setParameter(
			"commerceSubscriptionEntryId",
			String.valueOf(commerceSubscriptionEntryId));

		return portletURL;
	}

	private PortletURL _getSubscriptionEntryEditURL(
			long commerceSubscriptionEntryId,
			HttpServletRequest httpServletRequest)
		throws PortalException {

		PortletURL portletURL = PortletProviderUtil.getPortletURL(
			httpServletRequest, CommerceSubscriptionEntry.class.getName(),
			PortletProvider.Action.MANAGE);

		portletURL.setParameter(
			"mvcRenderCommandName",
			"/commerce_subscription_entry/edit_commerce_subscription_entry");
		portletURL.setParameter(
			"redirect", _portal.getCurrentURL(httpServletRequest));
		portletURL.setParameter(
			"commerceSubscriptionEntryId",
			String.valueOf(commerceSubscriptionEntryId));

		return portletURL;
	}

	@Reference
	private Portal _portal;

	@Reference(
		target = "(resource.name=" + CommerceConstants.SUBSCRIPTION_RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}