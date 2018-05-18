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

import com.liferay.commerce.model.CommercePaymentMethod;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.BaseJSPFormNavigatorEntry;
import com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorEntry;

import java.util.Locale;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(
	property = "form.navigator.entry.order:Integer=20",
	service = FormNavigatorEntry.class
)
public class CommercePaymentMethodDetailsFormNavigatorEntry
	extends BaseJSPFormNavigatorEntry<CommercePaymentMethod> {

	@Override
	public String getCategoryKey() {
		return CommercePaymentMethodFormNavigatorConstants.
			CATEGORY_KEY_COMMERCE_PAYMENT_METHOD_GENERAL;
	}

	@Override
	public String getFormNavigatorId() {
		return CommercePaymentMethodFormNavigatorConstants.
			FORM_NAVIGATOR_ID_COMMERCE_PAYMENT_METHOD;
	}

	@Override
	public String getKey() {
		return "details";
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, getKey());
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.payment.method.web)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	@Override
	protected String getJspPath() {
		return "/payment_method/details.jsp";
	}

}