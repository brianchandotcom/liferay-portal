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

package com.liferay.commerce.subscription.web.internal.display.context.util;

import com.liferay.commerce.configuration.CommerceSubscriptionConfiguration;
import com.liferay.commerce.constants.CommerceSubscriptionEntryConstants;
import com.liferay.commerce.model.CommerceSubscriptionEntry;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.PortalUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionURL;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceSubscriptionDisplayContextHelper {

	public CommerceSubscriptionDisplayContextHelper(
		CommerceSubscriptionEntry commerceSubscriptionEntry,
		ConfigurationProvider configurationProvider,
		PortletRequest portletRequest, PortletResponse portletResponse) {

		_commerceSubscriptionEntry = commerceSubscriptionEntry;
		_configurationProvider = configurationProvider;
		_portletRequest = portletRequest;
		_portletResponse = portletResponse;
	}

	public DropdownItemList getCommerceSubscriptionEntryActionItemList()
		throws PortalException {

		if (_commerceSubscriptionEntry == null) {
			return null;
		}

		int subscriptionStatus =
			_commerceSubscriptionEntry.getSubscriptionStatus();

		if (_isSubscriptionOver(subscriptionStatus)) {
			return null;
		}

		DropdownItemList dropdownItems = new DropdownItemList();

		if (CommerceSubscriptionEntryConstants.SUBSCRIPTION_STATUS_SUSPENDED ==
				subscriptionStatus) {

			dropdownItems.add(_getDropdownItem("activate"));
		}
		else {
			CommerceSubscriptionConfiguration
				commerceSubscriptionConfiguration =
					_configurationProvider.getSystemConfiguration(
						CommerceSubscriptionConfiguration.class);

			if (commerceSubscriptionConfiguration.
					subscriptionSuspensionAllowed() &&
				(subscriptionStatus !=
					CommerceSubscriptionEntryConstants.
						SUBSCRIPTION_STATUS_SUSPENDED)) {

				dropdownItems.add(_getDropdownItem("suspend"));
			}

			if (commerceSubscriptionConfiguration.
					subscriptionCancellationAllowed() &&
				(subscriptionStatus !=
					CommerceSubscriptionEntryConstants.
						SUBSCRIPTION_STATUS_CANCELLED)) {

				dropdownItems.add(_getDropdownItem("cancel"));
			}
		}

		return dropdownItems;
	}

	private PortletURL _getActionURL(String cmd) {
		LiferayPortletResponse liferayPortletResponse =
			PortalUtil.getLiferayPortletResponse(_portletResponse);

		ActionURL actionURL = liferayPortletResponse.createActionURL();

		actionURL.setParameter(
			ActionRequest.ACTION_NAME,
			"/commerce_subscription_content_web" +
				"/edit_commerce_subscription_content");
		actionURL.setParameter(Constants.CMD, cmd);
		actionURL.setParameter(
			"commerceSubscriptionEntryId",
			String.valueOf(
				_commerceSubscriptionEntry.getCommerceSubscriptionEntryId()));
		actionURL.setParameter(
			"redirect", PortalUtil.getCurrentURL(_portletRequest));

		return actionURL;
	}

	private DropdownItem _getDropdownItem(String cmd) {
		DropdownItem dropdownItem = new DropdownItem();

		dropdownItem.setHref(_getActionURL(cmd));
		dropdownItem.setLabel(
			LanguageUtil.get(
				PortalUtil.getHttpServletRequest(_portletRequest), cmd));

		return dropdownItem;
	}

	private boolean _isSubscriptionOver(int subscriptionStatus) {
		if ((subscriptionStatus ==
				CommerceSubscriptionEntryConstants.
					SUBSCRIPTION_STATUS_COMPLETED) ||
			(subscriptionStatus ==
				CommerceSubscriptionEntryConstants.
					SUBSCRIPTION_STATUS_CANCELLED)) {

			return true;
		}

		return false;
	}

	private final CommerceSubscriptionEntry _commerceSubscriptionEntry;
	private final ConfigurationProvider _configurationProvider;
	private final PortletRequest _portletRequest;
	private final PortletResponse _portletResponse;

}