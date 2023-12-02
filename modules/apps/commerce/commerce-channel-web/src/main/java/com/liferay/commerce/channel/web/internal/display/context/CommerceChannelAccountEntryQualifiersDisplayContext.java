/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.channel.web.internal.display.context;

import com.liferay.commerce.product.constants.CommerceChannelAccountEntryRelConstants;
import com.liferay.commerce.product.display.context.helper.CPRequestHelper;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelAccountEntryRelService;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Danny Situ
 */
public class CommerceChannelAccountEntryQualifiersDisplayContext {

	public CommerceChannelAccountEntryQualifiersDisplayContext(
		CommerceChannelAccountEntryRelService
			commerceChannelAccountEntryRelService,
		CommerceChannelLocalService commerceChannelLocalService,
		HttpServletRequest httpServletRequest) {

		_commerceChannelAccountEntryRelService =
			commerceChannelAccountEntryRelService;
		_commerceChannelLocalService = commerceChannelLocalService;
		_httpServletRequest = httpServletRequest;

		_cpRequestHelper = new CPRequestHelper(httpServletRequest);
	}

	public String getActiveAccountEligibility() throws PortalException {
		long commerceChannelAccountRelsCount =
			_commerceChannelAccountEntryRelService.
				getCommerceChannelAccountEntryRelsCount(
					getCommerceChannelId(), null,
					CommerceChannelAccountEntryRelConstants.TYPE_ELIGIBILITY);

		if (commerceChannelAccountRelsCount > 0) {
			return "accounts";
		}

		return "all";
	}

	public CommerceChannel getCommerceChannel() throws PortalException {
		if (_commerceChannel != null) {
			return _commerceChannel;
		}

		long commerceChannelId = ParamUtil.getLong(
			_cpRequestHelper.getRenderRequest(), "commerceChannelId");

		if (commerceChannelId > 0) {
			_commerceChannel = _commerceChannelLocalService.getCommerceChannel(
				commerceChannelId);
		}

		return _commerceChannel;
	}

	public String getCommerceChannelAccountEntriesAPIURL()
		throws PortalException {

		return "/o/headless-commerce-admin-channel/v1.0/channels/" +
			getCommerceChannelId() + "/channel-accounts?nestedFields=account";
	}

	public List<FDSActionDropdownItem>
			getCommerceChannelAccountEntryFDSActionDropdownItems()
		throws PortalException {

		return getFDSActionTemplates(false);
	}

	public long getCommerceChannelId() throws PortalException {
		CommerceChannel commerceChannel = getCommerceChannel();

		if (commerceChannel == null) {
			return 0;
		}

		return commerceChannel.getCommerceChannelId();
	}

	protected List<FDSActionDropdownItem> getFDSActionTemplates(
		boolean sidePanel) {

		List<FDSActionDropdownItem> fdsActionDropdownItems = new ArrayList<>();

		FDSActionDropdownItem fdsActionDropdownItem = new FDSActionDropdownItem(
			null, "trash", "remove",
			LanguageUtil.get(_httpServletRequest, "remove"), "delete", "delete",
			"headless");

		if (sidePanel) {
			fdsActionDropdownItem.setTarget("sidePanel");
		}

		fdsActionDropdownItems.add(fdsActionDropdownItem);

		return fdsActionDropdownItems;
	}

	private CommerceChannel _commerceChannel;
	private final CommerceChannelAccountEntryRelService
		_commerceChannelAccountEntryRelService;
	private final CommerceChannelLocalService _commerceChannelLocalService;
	private final CPRequestHelper _cpRequestHelper;
	private final HttpServletRequest _httpServletRequest;

}