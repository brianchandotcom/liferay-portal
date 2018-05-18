/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.internal.email.notification.type;

import com.liferay.commerce.notification.type.CommerceNotificationType;
import com.liferay.portal.kernel.language.LanguageUtil;
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
			"[$ORDER_CREATOR$]",
			LanguageUtil.get(resourceBundle, "order-creator-definition-term"));
		map.put(
			"[$ORDER_ID$]",
			LanguageUtil.get(resourceBundle, "order-id-definition-term"));

		return map;
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

}