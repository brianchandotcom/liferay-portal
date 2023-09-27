/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.order;

import com.liferay.account.model.AccountGroupRel;
import com.liferay.account.service.AccountGroupLocalService;
import com.liferay.account.service.AccountGroupRelLocalService;
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

import java.math.BigDecimal;

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
			BigDecimal quantity)
		throws PortalException {

		if (cpInstance == null) {
			return new CommerceOrderValidatorResult(false);
		}

		if (!_isAccountEnabled(commerceOrder, cpInstance.getCPDefinition()) ||
			!_isChannelEnabled(commerceOrder, cpInstance.getCPDefinition())) {

			return new CommerceOrderValidatorResult(
				false,
				_getLocalizedMessage(
					locale, "one-or-more-products-are-no-longer-available",
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

		if (!_isAccountEnabled(
				commerceOrderItem.getCommerceOrder(),
				cpInstance.getCPDefinition()) ||
			!_isChannelEnabled(
				commerceOrderItem.getCommerceOrder(),
				commerceOrderItem.getCPDefinition())) {

			return new CommerceOrderValidatorResult(
				commerceOrderItem.getCommerceOrderItemId(), false,
				_getLocalizedMessage(
					locale, "one-or-more-products-are-no-longer-available",
					null));
		}

		return new CommerceOrderValidatorResult(true);
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

	private boolean _isAccountEnabled(
		CommerceOrder commerceOrder, CPDefinition cpDefinition) {

		if (!cpDefinition.isAccountGroupFilterEnabled()) {
			return true;
		}

		List<AccountGroupRel> accountGroupRels =
			_accountGroupRelLocalService.getAccountGroupRels(
				CPDefinition.class.getName(), cpDefinition.getCPDefinitionId());

		long[] accountGroupIds = _accountGroupLocalService.getAccountGroupIds(
			commerceOrder.getCommerceAccountId());

		for (AccountGroupRel accountGroupRel : accountGroupRels) {
			if (ArrayUtil.contains(
					accountGroupIds, accountGroupRel.getAccountGroupId())) {

				return true;
			}
		}

		return false;
	}

	private boolean _isChannelEnabled(
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

	@Reference
	private AccountGroupLocalService _accountGroupLocalService;

	@Reference
	private AccountGroupRelLocalService _accountGroupRelLocalService;

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommerceChannelRelLocalService _commerceChannelRelLocalService;

	@Reference
	private Language _language;

}