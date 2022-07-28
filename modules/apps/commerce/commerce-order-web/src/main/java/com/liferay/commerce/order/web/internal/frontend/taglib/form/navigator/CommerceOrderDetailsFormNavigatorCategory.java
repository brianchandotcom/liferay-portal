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

package com.liferay.commerce.order.web.internal.frontend.taglib.form.navigator;

import com.liferay.commerce.order.web.internal.constants.CommerceOrderFormNavigatorConstants;
import com.liferay.frontend.taglib.form.navigator.FormNavigatorCategory;
import com.liferay.portal.kernel.language.Language;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(
	enabled = false, property = "form.navigator.category.order:Integer=10",
	service = FormNavigatorCategory.class
)
public class CommerceOrderDetailsFormNavigatorCategory
	implements FormNavigatorCategory {

	@Override
	public String getFormNavigatorId() {
		return CommerceOrderFormNavigatorConstants.
			FORM_NAVIGATOR_ID_COMMERCE_ORDER_DETAILS;
	}

	@Override
	public String getKey() {
		return CommerceOrderFormNavigatorConstants.
			CATEGORY_KEY_COMMERCE_ORDER_DETAILS;
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, getKey());
	}

	@Reference
	private Language _language;

}