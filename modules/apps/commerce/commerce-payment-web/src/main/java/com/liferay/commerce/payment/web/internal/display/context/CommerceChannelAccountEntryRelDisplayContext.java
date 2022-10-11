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

package com.liferay.commerce.payment.web.internal.display.context;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryService;
import com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel;
import com.liferay.commerce.payment.service.CommercePaymentMethodGroupRelService;
import com.liferay.commerce.payment.util.comparator.CommercePaymentMethodGroupRelNameOrderByComparator;
import com.liferay.commerce.payment.web.internal.display.context.helper.CommercePaymentMethodRequestHelper;
import com.liferay.commerce.product.constants.CommerceChannelAccountEntryRelConstants;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.model.CommerceChannelAccountEntryRel;
import com.liferay.commerce.product.service.CommerceChannelAccountEntryRelService;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Crescenzo Rega
 */
public class CommerceChannelAccountEntryRelDisplayContext {

	public CommerceChannelAccountEntryRelDisplayContext(
			AccountEntryService accountEntryService,
			CommerceChannelService commerceChannelService,
			CommerceChannelAccountEntryRelService
				commerceChannelAccountEntryRelService,
			CommercePaymentMethodGroupRelService
				commercePaymentMethodGroupRelService,
			HttpServletRequest httpServletRequest, Portal portal)
		throws PortalException {

		_accountEntryService = accountEntryService;
		_commerceChannelService = commerceChannelService;
		_commerceChannelAccountEntryRelService =
			commerceChannelAccountEntryRelService;
		_commercePaymentMethodGroupRelService =
			commercePaymentMethodGroupRelService;
		_portal = portal;

		long accountEntryId = ParamUtil.getLong(
			httpServletRequest, "accountEntryId");

		_accountEntry = _accountEntryService.getAccountEntry(accountEntryId);

		long commerceChannelId = ParamUtil.getLong(
			httpServletRequest, "commerceChannelId");

		_commerceChannel = _commerceChannelService.fetchCommerceChannel(
			commerceChannelId);

		commercePaymentMethodRequestHelper =
			new CommercePaymentMethodRequestHelper(httpServletRequest);

		_locale = _portal.getLocale(httpServletRequest);
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

	public CommercePaymentMethodGroupRel getCommercePaymentMethodGroupRel()
		throws PortalException {

		if (_commercePaymentMethodGroupRel != null) {
			return _commercePaymentMethodGroupRel;
		}

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			_commerceChannelAccountEntryRelService.
				fetchCommerceChannelAccountEntryRel(
					_accountEntry.getAccountEntryId(),
					_commerceChannel.getCommerceChannelId(),
					CommerceChannelAccountEntryRelConstants.TYPE_PAYMENT);

		if (commerceChannelAccountEntryRel != null) {
			_commercePaymentMethodGroupRel =
				_commercePaymentMethodGroupRelService.
					fetchCommercePaymentMethodGroupRel(
						commerceChannelAccountEntryRel.getClassPK());

			return _commercePaymentMethodGroupRel;
		}

		return null;
	}

	public List<CommercePaymentMethodGroupRel>
			getCommercePaymentMethodGroupRels()
		throws PortalException {

		return _commercePaymentMethodGroupRelService.
			getCommercePaymentMethodGroupRels(
				_commerceChannel.getGroupId(), true, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS,
				new CommercePaymentMethodGroupRelNameOrderByComparator(
					_locale));
	}

	public boolean isCommercePaymentChecked(String key) {
		if ((_commercePaymentMethodGroupRel == null) &&
			Validator.isBlank(key)) {

			return true;
		}

		if ((_commercePaymentMethodGroupRel != null) &&
			key.equals(_commercePaymentMethodGroupRel.getEngineKey())) {

			return true;
		}

		return false;
	}

	protected final CommercePaymentMethodRequestHelper
		commercePaymentMethodRequestHelper;

	private final AccountEntry _accountEntry;
	private final AccountEntryService _accountEntryService;
	private final CommerceChannel _commerceChannel;
	private final CommerceChannelAccountEntryRelService
		_commerceChannelAccountEntryRelService;
	private final CommerceChannelService _commerceChannelService;
	private CommercePaymentMethodGroupRel _commercePaymentMethodGroupRel;
	private final CommercePaymentMethodGroupRelService
		_commercePaymentMethodGroupRelService;
	private final Locale _locale;
	private final Portal _portal;

}