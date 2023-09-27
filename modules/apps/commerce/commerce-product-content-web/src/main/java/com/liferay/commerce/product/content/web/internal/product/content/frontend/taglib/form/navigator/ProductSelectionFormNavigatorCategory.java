/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.content.web.internal.product.content.frontend.taglib.form.navigator;

import com.liferay.commerce.product.content.web.internal.constants.CPPublisherConstants;
import com.liferay.frontend.taglib.form.navigator.FormNavigatorCategory;
import com.liferay.portal.kernel.language.Language;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	property = "form.navigator.category.order:Integer=30",
	service = FormNavigatorCategory.class
)
public class ProductSelectionFormNavigatorCategory
	implements FormNavigatorCategory {

	@Override
	public String getFormNavigatorId() {
		return CPPublisherConstants.FORM_NAVIGATOR_ID_CONFIGURATION;
	}

	@Override
	public String getKey() {
		return CPPublisherConstants.CATEGORY_KEY_PRODUCT_SELECTION;
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "asset-selection");
	}

	@Reference
	private Language _language;

}