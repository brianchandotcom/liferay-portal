/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.configuration.css.web.internal.frontend.taglib.form.navigator;

import com.liferay.frontend.taglib.form.navigator.FormNavigatorCategory;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portlet.configuration.css.web.internal.constants.PortletConfigurationCSSConstants;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = "form.navigator.category.order:Integer=30",
	service = FormNavigatorCategory.class
)
public class BorderStylesFormNavigatorCategory
	implements FormNavigatorCategory {

	@Override
	public String getFormNavigatorId() {
		return PortletConfigurationCSSConstants.FORM_NAVIGATOR_ID;
	}

	@Override
	public String getKey() {
		return PortletConfigurationCSSConstants.CATEGORY_KEY_BORDER_STYLES;
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "border-styles");
	}

	@Reference
	private Language _language;

}