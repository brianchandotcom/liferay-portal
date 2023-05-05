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

package com.liferay.commerce.internal.order;

import com.liferay.account.model.AccountGroupRel;
import com.liferay.account.service.AccountGroupRelLocalService;
import com.liferay.commerce.account.util.CommerceAccountHelper;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.order.CommerceOrderValidator;
import com.liferay.commerce.order.CommerceOrderValidatorResult;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.model.CommerceChannelRel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.product.service.CommerceChannelRelLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian I. Kim
 */
@Component(
	property = {
		"commerce.order.validator.key=" + VisibilityCommerceOrderValidatorImpl.KEY,
		"commerce.order.validator.priority:Integer=30"
	},
	service = CommerceOrderValidator.class
)
public class VisibilityCommerceOrderValidatorImpl
	implements CommerceOrderValidator {

	public static final String KEY = "visibility";

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public CommerceOrderValidatorResult validate(
			Locale locale, CommerceOrder commerceOrder, CPInstance cpInstance,
			int quantity)
		throws PortalException {

		if (cpInstance == null) {
			return new CommerceOrderValidatorResult(false);
		}

		if (!_accountEnabled(commerceOrder, cpInstance.getCPDefinition()) ||
			!_channelEnabled(commerceOrder, cpInstance.getCPDefinition())) {

			return new CommerceOrderValidatorResult(
				false,
				_getLocalizedMessage(
					locale, "one-or-more-products-is-no-longer-available",
					null));
		}

		return new CommerceOrderValidatorResult(true);
	}

	@Override
	public CommerceOrderValidatorResult validate(
			Locale locale, CommerceOrderItem commerceOrderItem)
		throws PortalException {

		CPInstance cpInstance = commerceOrderItem.fetchCPInstance();

		if (cpInstance == null) {
			return new CommerceOrderValidatorResult(false);
		}

		if (!_accountEnabled(
				commerceOrderItem.getCommerceOrder(),
				cpInstance.getCPDefinition()) ||
			!_channelEnabled(
				commerceOrderItem.getCommerceOrder(),
				commerceOrderItem.getCPDefinition())) {

			return new CommerceOrderValidatorResult(
				commerceOrderItem.getCommerceOrderItemId(), false,
				_getLocalizedMessage(
					locale, "one-or-more-products-is-no-longer-available",
					null));
		}

		return new CommerceOrderValidatorResult(true);
	}

	private boolean _accountEnabled(
		CommerceOrder commerceOrder, CPDefinition cpDefinition) {

		if (!cpDefinition.isAccountGroupFilterEnabled()) {
			return true;
		}

		List<AccountGroupRel> accountGroupRels =
			_accountGroupRelLocalService.getAccountGroupRels(
				CPDefinition.class.getName(), cpDefinition.getCPDefinitionId());

		long[] accountGroupIds =
			_commerceAccountHelper.getCommerceAccountGroupIds(
				commerceOrder.getCommerceAccountId());

		for (AccountGroupRel accountGroupRel : accountGroupRels) {
			if (ArrayUtil.contains(
					accountGroupIds, accountGroupRel.getAccountGroupId())) {

				return true;
			}
		}

		return false;
	}

	private boolean _channelEnabled(
			CommerceOrder commerceOrder, CPDefinition cpDefinition)
		throws PortalException {

		if (cpDefinition.isChannelFilterEnabled()) {
			CommerceChannel commerceChannel =
				_commerceChannelLocalService.getCommerceChannelByOrderGroupId(
					commerceOrder.getGroupId());

			CommerceChannelRel commerceChannelRel =
				_commerceChannelRelLocalService.fetchCommerceChannelRel(
					CPDefinition.class.getName(),
					cpDefinition.getCPDefinitionId(),
					commerceChannel.getCommerceChannelId());

			if (commerceChannelRel == null) {
				return false;
			}
		}

		return true;
	}

	private String _getLocalizedMessage(
		Locale locale, String key, Object[] arguments) {

		if (locale == null) {
			return key;
		}

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		if (arguments == null) {
			return _language.get(resourceBundle, key);
		}

		return _language.format(resourceBundle, key, arguments);
	}

	@Reference
	private AccountGroupRelLocalService _accountGroupRelLocalService;

	@Reference
	private CommerceAccountHelper _commerceAccountHelper;

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommerceChannelRelLocalService _commerceChannelRelLocalService;

	@Reference
	private Language _language;

}