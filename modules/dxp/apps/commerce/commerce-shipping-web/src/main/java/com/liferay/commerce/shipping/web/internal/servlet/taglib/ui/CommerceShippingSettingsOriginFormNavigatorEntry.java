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

package com.liferay.commerce.shipping.web.internal.servlet.taglib.ui;

import com.liferay.commerce.configuration.CommerceShippingGroupServiceConfiguration;
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
	property = "form.navigator.entry.order:Integer=10",
	service = FormNavigatorEntry.class
)
public class CommerceShippingSettingsOriginFormNavigatorEntry
	extends
		BaseJSPFormNavigatorEntry<CommerceShippingGroupServiceConfiguration> {

	@Override
	public String getCategoryKey() {
		return CommerceShippingFormNavigatorConstants.
			CATEGORY_KEY_COMMERCE_SHIPPING_SETTINGS_GENERAL;
	}

	@Override
	public String getFormNavigatorId() {
		return CommerceShippingFormNavigatorConstants.
			FORM_NAVIGATOR_ID_COMMERCE_SHIPPING_SETTINGS;
	}

	@Override
	public String getKey() {
		return "origin";
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, getKey());
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.shipping.web)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	@Override
	protected String getJspPath() {
		return "/shipping_settings/origin.jsp";
	}

}