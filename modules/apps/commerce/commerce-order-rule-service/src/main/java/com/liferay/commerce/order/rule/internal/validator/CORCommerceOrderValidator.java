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

package com.liferay.commerce.order.rule.internal.validator;

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.util.CommerceAccountHelper;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.order.CommerceOrderValidator;
import com.liferay.commerce.order.CommerceOrderValidatorResult;
import com.liferay.commerce.order.rule.entry.type.COREntryType;
import com.liferay.commerce.order.rule.entry.type.COREntryTypeRegistry;
import com.liferay.commerce.order.rule.model.COREntry;
import com.liferay.commerce.order.rule.service.COREntryLocalService;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 */
@Component(
	immediate = true,
	property = {
		"commerce.order.validator.key=" + CORCommerceOrderValidator.KEY,
		"commerce.order.validator.priority:Integer=50"
	},
	service = CommerceOrderValidator.class
)
public class CORCommerceOrderValidator implements CommerceOrderValidator {

	public static final String KEY = "order-rules";

	@Override
	public String getKey() {
		return CORCommerceOrderValidator.KEY;
	}

	@Override
	public CommerceOrderValidatorResult validate(
			Locale locale, CommerceOrder commerceOrder)
		throws PortalException {

		CommerceAccount commerceAccount = commerceOrder.getCommerceAccount();

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannelByOrderGroupId(
				commerceOrder.getGroupId());

		List<COREntry> corEntries =
			_corEntryLocalService.
				getAccountEntryAndCommerceChannelAndCommerceOrderTypeCOREntries(
					commerceOrder.getCompanyId(),
					commerceAccount.getCommerceAccountId(),
					commerceChannel.getCommerceChannelId(),
					commerceOrder.getCommerceOrderTypeId());

		if (!corEntries.isEmpty()) {
			String errorMessage = _validate(commerceOrder, corEntries, locale);

			if (Validator.isBlank(errorMessage)) {
				return new CommerceOrderValidatorResult(true);
			}

			return new CommerceOrderValidatorResult(false, errorMessage);
		}

		corEntries =
			_corEntryLocalService.getAccountEntryAndCommerceOrderTypeCOREntries(
				commerceOrder.getCompanyId(),
				commerceAccount.getCommerceAccountId(),
				commerceOrder.getCommerceOrderTypeId());

		if (!corEntries.isEmpty()) {
			String errorMessage = _validate(commerceOrder, corEntries, locale);

			if (Validator.isBlank(errorMessage)) {
				return new CommerceOrderValidatorResult(true);
			}

			return new CommerceOrderValidatorResult(false, errorMessage);
		}

		corEntries =
			_corEntryLocalService.getAccountEntryAndCommerceChannelCOREntries(
				commerceOrder.getCompanyId(),
				commerceAccount.getCommerceAccountId(),
				commerceChannel.getCommerceChannelId());

		if (!corEntries.isEmpty()) {
			String errorMessage = _validate(commerceOrder, corEntries, locale);

			if (Validator.isBlank(errorMessage)) {
				return new CommerceOrderValidatorResult(true);
			}

			return new CommerceOrderValidatorResult(false, errorMessage);
		}

		corEntries = _corEntryLocalService.getAccountEntryCOREntries(
			commerceOrder.getCompanyId(),
			commerceAccount.getCommerceAccountId());

		if (!corEntries.isEmpty()) {
			String errorMessage = _validate(commerceOrder, corEntries, locale);

			if (Validator.isBlank(errorMessage)) {
				return new CommerceOrderValidatorResult(true);
			}

			return new CommerceOrderValidatorResult(false, errorMessage);
		}

		long[] commerceAccountGroupIds =
			_commerceAccountHelper.getCommerceAccountGroupIds(
				commerceAccount.getCommerceAccountId());

		corEntries =
			_corEntryLocalService.
				getAccountGroupsAndCommerceChannelAndCommerceOrderTypeCOREntries(
					commerceOrder.getCompanyId(), commerceAccountGroupIds,
					commerceChannel.getCommerceChannelId(),
					commerceOrder.getCommerceOrderTypeId());

		if (!corEntries.isEmpty()) {
			String errorMessage = _validate(commerceOrder, corEntries, locale);

			if (Validator.isBlank(errorMessage)) {
				return new CommerceOrderValidatorResult(true);
			}

			return new CommerceOrderValidatorResult(false, errorMessage);
		}

		corEntries =
			_corEntryLocalService.
				getAccountGroupsAndCommerceOrderTypeCOREntries(
					commerceOrder.getCompanyId(), commerceAccountGroupIds,
					commerceOrder.getCommerceOrderTypeId());

		if (!corEntries.isEmpty()) {
			String errorMessage = _validate(commerceOrder, corEntries, locale);

			if (Validator.isBlank(errorMessage)) {
				return new CommerceOrderValidatorResult(true);
			}

			return new CommerceOrderValidatorResult(false, errorMessage);
		}

		corEntries =
			_corEntryLocalService.getAccountGroupsAndCommerceChannelCOREntries(
				commerceOrder.getCompanyId(), commerceAccountGroupIds,
				commerceChannel.getCommerceChannelId());

		if (!corEntries.isEmpty()) {
			String errorMessage = _validate(commerceOrder, corEntries, locale);

			if (Validator.isBlank(errorMessage)) {
				return new CommerceOrderValidatorResult(true);
			}

			return new CommerceOrderValidatorResult(false, errorMessage);
		}

		corEntries = _corEntryLocalService.getAccountGroupsCOREntries(
			commerceOrder.getCompanyId(), commerceAccountGroupIds);

		if (!corEntries.isEmpty()) {
			String errorMessage = _validate(commerceOrder, corEntries, locale);

			if (Validator.isBlank(errorMessage)) {
				return new CommerceOrderValidatorResult(true);
			}

			return new CommerceOrderValidatorResult(false, errorMessage);
		}

		corEntries =
			_corEntryLocalService.
				getCommerceChannelAndCommerceOrderTypeCOREntries(
					commerceOrder.getCompanyId(),
					commerceChannel.getCommerceChannelId(),
					commerceOrder.getCommerceOrderTypeId());

		if (!corEntries.isEmpty()) {
			String errorMessage = _validate(commerceOrder, corEntries, locale);

			if (Validator.isBlank(errorMessage)) {
				return new CommerceOrderValidatorResult(true);
			}

			return new CommerceOrderValidatorResult(false, errorMessage);
		}

		corEntries = _corEntryLocalService.getCommerceOrderTypeCOREntries(
			commerceOrder.getCompanyId(),
			commerceOrder.getCommerceOrderTypeId());

		if (!corEntries.isEmpty()) {
			String errorMessage = _validate(commerceOrder, corEntries, locale);

			if (Validator.isBlank(errorMessage)) {
				return new CommerceOrderValidatorResult(true);
			}

			return new CommerceOrderValidatorResult(false, errorMessage);
		}

		corEntries = _corEntryLocalService.getCommerceChannelCOREntries(
			commerceOrder.getCompanyId(),
			commerceChannel.getCommerceChannelId());

		if (!corEntries.isEmpty()) {
			String errorMessage = _validate(commerceOrder, corEntries, locale);

			if (Validator.isBlank(errorMessage)) {
				return new CommerceOrderValidatorResult(true);
			}

			return new CommerceOrderValidatorResult(false, errorMessage);
		}

		corEntries = _corEntryLocalService.getUnqualifiedCOREntries(
			commerceOrder.getCompanyId());

		if (!corEntries.isEmpty()) {
			String errorMessage = _validate(commerceOrder, corEntries, locale);

			if (Validator.isBlank(errorMessage)) {
				return new CommerceOrderValidatorResult(true);
			}

			return new CommerceOrderValidatorResult(false, errorMessage);
		}

		return new CommerceOrderValidatorResult(true);
	}

	@Override
	public CommerceOrderValidatorResult validate(
			Locale locale, CommerceOrder commerceOrder, CPInstance cpInstance,
			int quantity)
		throws PortalException {

		return new CommerceOrderValidatorResult(true);
	}

	@Override
	public CommerceOrderValidatorResult validate(
			Locale locale, CommerceOrderItem commerceOrderItem)
		throws PortalException {

		return new CommerceOrderValidatorResult(true);
	}

	private String _validate(
			CommerceOrder commerceOrder, List<COREntry> corEntries,
			Locale locale)
		throws PortalException {

		Set<String> validatedRuleType = new HashSet<>();

		for (COREntry corEntry : corEntries) {
			String type = corEntry.getType();

			if (!validatedRuleType.contains(type)) {
				validatedRuleType.add(type);

				COREntryType corEntryType =
					_corEntryTypeRegistry.getCOREntryType(type);

				if (!corEntryType.evaluate(corEntry, commerceOrder)) {
					return corEntryType.getErrorMessage(
						corEntry, commerceOrder, locale);
				}
			}
		}

		return null;
	}

	@Reference
	private CommerceAccountHelper _commerceAccountHelper;

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private COREntryLocalService _corEntryLocalService;

	@Reference
	private COREntryTypeRegistry _corEntryTypeRegistry;

}