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

package com.liferay.commerce.payment.method.web.internal.servlet.taglib.ui;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorCategory;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Andrea Di Giorgi
 */
@Component(
	property = "form.navigator.category.order:Integer=10",
	service = FormNavigatorCategory.class
)
public class CommercePaymentMethodGeneralFormNavigatorCategory
	implements FormNavigatorCategory {

	@Override
	public String getFormNavigatorId() {
		return CommercePaymentMethodFormNavigatorConstants.
			FORM_NAVIGATOR_ID_COMMERCE_PAYMENT_METHOD;
	}

	@Override
	public String getKey() {
		return CommercePaymentMethodFormNavigatorConstants.
			CATEGORY_KEY_COMMERCE_PAYMENT_METHOD_GENERAL;
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "general");
	}

}