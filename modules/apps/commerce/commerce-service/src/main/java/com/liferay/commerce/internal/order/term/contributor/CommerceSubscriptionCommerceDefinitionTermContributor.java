/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.order.term.contributor;

import com.liferay.account.model.AccountEntry;
import com.liferay.commerce.constants.CommerceDefinitionTermConstants;
import com.liferay.commerce.constants.CommerceSubscriptionNotificationConstants;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommerceSubscriptionEntry;
import com.liferay.commerce.order.CommerceDefinitionTermContributor;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.service.CommerceOrderItemLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 */
@Component(
	property = {
		"commerce.definition.term.contributor.key=" + CommerceSubscriptionCommerceDefinitionTermContributor.KEY,
		"commerce.notification.type.key=" + CommerceSubscriptionNotificationConstants.SUBSCRIPTION_ACTIVATED,
		"commerce.notification.type.key=" + CommerceSubscriptionNotificationConstants.SUBSCRIPTION_CANCELLED,
		"commerce.notification.type.key=" + CommerceSubscriptionNotificationConstants.SUBSCRIPTION_RENEWED,
		"commerce.notification.type.key=" + CommerceSubscriptionNotificationConstants.SUBSCRIPTION_SUSPENDED
	},
	service = CommerceDefinitionTermContributor.class
)
public class CommerceSubscriptionCommerceDefinitionTermContributor
	implements CommerceDefinitionTermContributor {

	public static final String KEY =
		CommerceDefinitionTermConstants.
			BODY_AND_SUBJECT_DEFINITION_TERMS_CONTRIBUTOR;

	@Override
	public String getFilledTerm(String term, Object object, Locale locale)
		throws PortalException {

		if (!(object instanceof CommerceSubscriptionEntry)) {
			return term;
		}

		CommerceSubscriptionEntry commerceSubscriptionEntry =
			(CommerceSubscriptionEntry)object;

		CommerceOrderItem commerceOrderItem =
			_commerceOrderItemLocalService.getCommerceOrderItem(
				commerceSubscriptionEntry.getCommerceOrderItemId());

		if (term.equals(_ORDER_CREATOR)) {
			CommerceOrder commerceOrder = commerceOrderItem.getCommerceOrder();

			AccountEntry accountEntry = commerceOrder.getAccountEntry();

			if (accountEntry.isPersonalAccount()) {
				User user = _userLocalService.getUser(accountEntry.getUserId());

				return user.getFullName(true, true);
			}

			return accountEntry.getName();
		}

		if (term.equals(_ORDER_ID)) {
			return String.valueOf(commerceOrderItem.getCommerceOrderId());
		}

		if (term.equals(_PRODUCT_NAME)) {
			CPDefinition cpDefinition = commerceOrderItem.getCPDefinition();

			return cpDefinition.getName(LocaleUtil.toLanguageId(locale));
		}

		return term;
	}

	@Override
	public String getLabel(String term, Locale locale) {
		return _language.get(locale, _languageKeys.get(term));
	}

	@Override
	public List<String> getTerms() {
		return new ArrayList<>(_languageKeys.keySet());
	}

	private static final String _ORDER_CREATOR = "[%ORDER_CREATOR%]";

	private static final String _ORDER_ID = "[%ORDER_ID%]";

	private static final String _PRODUCT_NAME = "[%PRODUCT_NAME%]";

	private static final Map<String, String> _languageKeys = HashMapBuilder.put(
		_ORDER_CREATOR, "order-creator-definition-term"
	).put(
		_ORDER_ID, "order-id-definition-term"
	).put(
		_PRODUCT_NAME, "product-name"
	).build();

	@Reference
	private CommerceOrderItemLocalService _commerceOrderItemLocalService;

	@Reference
	private Language _language;

	@Reference
	private UserLocalService _userLocalService;

}