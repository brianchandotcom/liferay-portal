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

package com.liferay.commerce.account.web.internal.display.context;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryService;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.model.CommerceShippingOptionAccountEntryRel;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.commerce.service.CommerceShippingMethodService;
import com.liferay.commerce.service.CommerceShippingOptionAccountEntryRelService;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption;
import com.liferay.commerce.shipping.engine.fixed.service.CommerceShippingFixedOptionService;
import com.liferay.commerce.shipping.engine.fixed.util.comparator.CommerceShippingFixedOptionNameComparator;
import com.liferay.commerce.util.comparator.CommerceShippingMethodNameComparator;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Andrea Sbarra
 * @author Alessio Antonio Rendina
 */
public class CommerceAccountDisplayContext {

	public CommerceAccountDisplayContext(
			AccountEntryService accountEntryService,
			CommerceChannelService commerceChannelService,
			CommerceShippingFixedOptionService
				commerceShippingFixedOptionService,
			CommerceShippingMethodService commerceShippingMethodService,
			CommerceShippingOptionAccountEntryRelService
				commerceShippingOptionAccountEntryRelService,
			HttpServletRequest httpServletRequest, Portal portal)
		throws PortalException {

		_accountEntryService = accountEntryService;
		_commerceChannelService = commerceChannelService;
		_commerceShippingFixedOptionService =
			commerceShippingFixedOptionService;
		_commerceShippingMethodService = commerceShippingMethodService;
		_commerceShippingOptionAccountEntryRelService =
			commerceShippingOptionAccountEntryRelService;
		_httpServletRequest = httpServletRequest;
		_portal = portal;

		long accountEntryId = ParamUtil.getLong(
			_httpServletRequest, "accountEntryId");

		_accountEntry = _accountEntryService.fetchAccountEntry(accountEntryId);

		long commerceChannelId = ParamUtil.getLong(
			_httpServletRequest, "commerceChannelId");

		_commerceChannel = _commerceChannelService.fetchCommerceChannel(
			commerceChannelId);

		_commerceShippingOptionAccountEntryRel =
			_commerceShippingOptionAccountEntryRelService.
				fetchCommerceShippingOptionAccountEntryRel(
					accountEntryId, commerceChannelId);
	}

	public long getAccountEntryId() {
		if (_accountEntry == null) {
			return 0;
		}

		return _accountEntry.getAccountEntryId();
	}

	public long getCommerceChannelId() {
		if (_commerceChannel == null) {
			return 0;
		}

		return _commerceChannel.getCommerceChannelId();
	}

	public List<CommerceShippingFixedOption> getCommerceShippingFixedOptions()
		throws PortalException {

		if (_commerceChannel == null) {
			return Collections.emptyList();
		}

		Locale locale = _portal.getLocale(_httpServletRequest);

		List<CommerceShippingFixedOption> commerceShippingFixedOptions =
			new ArrayList<>();

		for (CommerceShippingMethod commerceShippingMethod :
				_commerceShippingMethodService.getCommerceShippingMethods(
					_commerceChannel.getGroupId(), QueryUtil.ALL_POS,
					QueryUtil.ALL_POS,
					new CommerceShippingMethodNameComparator(true, locale))) {

			commerceShippingFixedOptions.addAll(
				_commerceShippingFixedOptionService.
					getCommerceShippingFixedOptions(
						commerceShippingMethod.getCommerceShippingMethodId(),
						QueryUtil.ALL_POS, QueryUtil.ALL_POS,
						new CommerceShippingFixedOptionNameComparator(
							true, locale)));
		}

		return commerceShippingFixedOptions;
	}

	public CommerceShippingOptionAccountEntryRel
		getCommerceShippingOptionAccountEntryRel() {

		return _commerceShippingOptionAccountEntryRel;
	}

	public String getCommerceShippingOptionLabel(
			CommerceShippingFixedOption commerceShippingFixedOption)
		throws PortalException {

		CommerceShippingMethod commerceShippingMethod =
			_commerceShippingMethodService.getCommerceShippingMethod(
				commerceShippingFixedOption.getCommerceShippingMethodId());

		StringBundler sb = new StringBundler(3);

		Locale locale = _portal.getLocale(_httpServletRequest);

		sb.append(commerceShippingMethod.getName(locale));

		sb.append(" / ");
		sb.append(commerceShippingFixedOption.getName(locale));

		return sb.toString();
	}

	public String getName() {
		return _accountEntry.getName();
	}

	public boolean isCommerceShippingFixedOptionChecked(String key) {
		if ((_commerceShippingOptionAccountEntryRel == null) &&
			Validator.isBlank(key)) {

			return true;
		}

		if ((_commerceShippingOptionAccountEntryRel != null) &&
			key.equals(
				_commerceShippingOptionAccountEntryRel.
					getCommerceShippingOptionKey())) {

			return true;
		}

		return false;
	}

	private final AccountEntry _accountEntry;
	private final AccountEntryService _accountEntryService;
	private final CommerceChannel _commerceChannel;
	private final CommerceChannelService _commerceChannelService;
	private final CommerceShippingFixedOptionService
		_commerceShippingFixedOptionService;
	private final CommerceShippingMethodService _commerceShippingMethodService;
	private final CommerceShippingOptionAccountEntryRel
		_commerceShippingOptionAccountEntryRel;
	private final CommerceShippingOptionAccountEntryRelService
		_commerceShippingOptionAccountEntryRelService;
	private final HttpServletRequest _httpServletRequest;
	private final Portal _portal;

}