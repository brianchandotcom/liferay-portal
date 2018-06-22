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

package com.liferay.commerce.internal.notification.type;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.notification.type.CommerceNotificationType;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"commerce.notification.type.key=" + OrderPlacedCommerceNotificationTypeImpl.KEY,
		"commerce.notification.type.order:Integer=10"
	},
	service = CommerceNotificationType.class
)
public class OrderPlacedCommerceNotificationTypeImpl
	implements CommerceNotificationType {

	public static final String KEY = "order-placed";

	@Override
	public Map<String, String> getDefinitionTerms(Locale locale) {
		Map<String, String> map = new HashMap<>();

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		map.put(
			_ORDER_CREATOR,
			LanguageUtil.get(resourceBundle, "order-creator-definition-term"));
		map.put(
			_ORDER_ID,
			LanguageUtil.get(resourceBundle, "order-id-definition-term"));

		return map;
	}

	@Override
	public String getFilledTerm(String term, Object object, Locale locale)
		throws PortalException {

		if (!(object instanceof CommerceOrder)) {
			return term;
		}

		CommerceOrder commerceOrder = (CommerceOrder)object;

		if (term.equals(_ORDER_CREATOR)) {
			Organization organization = commerceOrder.getOrderOrganization();

			if (organization != null) {
				return organization.getName();
			}

			User user = commerceOrder.getOrderUser();

			return user.getFullName(true, true);
		}

		if (term.equals(_ORDER_ID)) {
			return String.valueOf(commerceOrder.getCommerceOrderId());
		}

		return term;
	}

	@Override
	public String getKey() {
		return KEY;
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(resourceBundle, KEY);
	}

	private static final String _ORDER_CREATOR = "[%ORDER_CREATOR%]";

	private static final String _ORDER_ID = "[%ORDER_ID%]";

}