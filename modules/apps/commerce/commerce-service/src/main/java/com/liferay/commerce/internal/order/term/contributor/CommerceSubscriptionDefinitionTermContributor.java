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

package com.liferay.commerce.internal.order.term.contributor;

import com.liferay.commerce.account.constants.CommerceAccountConstants;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.constants.CommerceDefinitionTermConstants;
import com.liferay.commerce.constants.CommerceSubscriptionNotificationConstants;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommerceSubscriptionEntry;
import com.liferay.commerce.order.CommerceDefinitionTermContributor;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.service.CommerceOrderItemLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 */
@Component(
	immediate = true,
	property = {
		"commerce.definition.term.contributor.key=" + CommerceSubscriptionDefinitionTermContributor.KEY,
		"commerce.notification.type.key=" + CommerceSubscriptionNotificationConstants.SUBSCRIPTION_ACTIVATED,
		"commerce.notification.type.key=" + CommerceSubscriptionNotificationConstants.SUBSCRIPTION_CANCELLED,
		"commerce.notification.type.key=" + CommerceSubscriptionNotificationConstants.SUBSCRIPTION_RENEWED,
		"commerce.notification.type.key=" + CommerceSubscriptionNotificationConstants.SUBSCRIPTION_SUSPENDED
	},
	service = CommerceDefinitionTermContributor.class
)
public class CommerceSubscriptionDefinitionTermContributor
	implements CommerceDefinitionTermContributor {

	public static final String KEY =
		CommerceDefinitionTermConstants.
			BODY_AND_SUBJECT_DEFINITION_TERMS_CONTRIBUTOR;

	@Override
	public Map<String, String> getDefinitionTerms(Locale locale) {
		Map<String, String> map = new HashMap<>();

		List<String> terms = getTerms();

		for (String term : terms) {
			map.put(term, getLabel(term, locale));
		}

		return map;
	}

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

			CommerceAccount commerceAccount =
				commerceOrder.getCommerceAccount();

			if (commerceAccount.getType() ==
					CommerceAccountConstants.ACCOUNT_TYPE_PERSONAL) {

				User user = _userLocalService.getUser(
					GetterUtil.getLong(commerceAccount.getName()));

				return user.getFullName(true, true);
			}

			return commerceAccount.getName();
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
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(
			resourceBundle, _commerceOrderDefinitionTermsMap.get(term));
	}

	@Override
	public List<String> getTerms() {
		return new ArrayList<>(_commerceOrderDefinitionTermsMap.keySet());
	}

	private static final String _ORDER_CREATOR = "[%ORDER_CREATOR%]";

	private static final String _ORDER_ID = "[%ORDER_ID%]";

	private static final String _PRODUCT_NAME = "[%PRODUCT_NAME%]";

	private static final Map<String, String> _commerceOrderDefinitionTermsMap =
		new HashMap<String, String>() {
			{
				put(_ORDER_CREATOR, "order-creator-definition-term");
				put(_ORDER_ID, "order-id-definition-term");
				put(_PRODUCT_NAME, "product-name");
			}
		};

	@Reference
	private CommerceOrderItemLocalService _commerceOrderItemLocalService;

	@Reference
	private UserLocalService _userLocalService;

}