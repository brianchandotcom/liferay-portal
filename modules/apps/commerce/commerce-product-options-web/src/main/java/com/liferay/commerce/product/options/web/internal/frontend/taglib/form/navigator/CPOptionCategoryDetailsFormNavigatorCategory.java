/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.options.web.internal.frontend.taglib.form.navigator;

import com.liferay.commerce.product.options.web.internal.servlet.taglib.ui.constants.CPOptionCategoryFormNavigatorConstants;
import com.liferay.frontend.taglib.form.navigator.FormNavigatorCategory;
import com.liferay.portal.kernel.language.Language;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "form.navigator.category.order:Integer=100",
	service = FormNavigatorCategory.class
)
public class CPOptionCategoryDetailsFormNavigatorCategory
	implements FormNavigatorCategory {

	@Override
	public String getFormNavigatorId() {
		return CPOptionCategoryFormNavigatorConstants.
			FORM_NAVIGATOR_ID_COMMERCE_PRODUCT_OPTION_CATEGORY;
	}

	@Override
	public String getKey() {
		return CPOptionCategoryFormNavigatorConstants.
			CATEGORY_KEY_COMMERCE_PRODUCT_OPTION_CATEGORY_DETAILS;
	}

	@Override
	public String getLabel(Locale locale) {
		return _languagel.get(locale, "details");
	}

	@Reference
	private Language _languagel;

}