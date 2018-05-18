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

package com.liferay.commerce.product.definitions.web.internal.servlet.taglib.ui;

import com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.BaseJSPFormNavigatorEntry;
import com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorEntry;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Andrea Di Giorgi
 */
@Component(
	property = "form.navigator.entry.order:Integer=100",
	service = FormNavigatorEntry.class
)
public class CPDefinitionSpecificationOptionValueDetailsFormNavigatorEntry
	extends BaseJSPFormNavigatorEntry<CPDefinitionSpecificationOptionValue> {

	@Override
	public String getCategoryKey() {
		return CPDefinitionSpecificationOptionValueFormNavigatorConstants.
			CATEGORY_KEY_COMMERCE_PRODUCT_DEFINITION_SPECIFICATION_OPTION_VALUE_DETAILS;
	}

	@Override
	public String getFormNavigatorId() {
		return CPDefinitionSpecificationOptionValueFormNavigatorConstants.
			FORM_NAVIGATOR_ID_COMMERCE_PRODUCT_DEFINITION_SPECIFICATION_OPTION_VALUE;
	}

	@Override
	public String getKey() {
		return "details";
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "details");
	}

	@Override
	protected String getJspPath() {
		return "/definition_specification_option_value/details.jsp";
	}

}