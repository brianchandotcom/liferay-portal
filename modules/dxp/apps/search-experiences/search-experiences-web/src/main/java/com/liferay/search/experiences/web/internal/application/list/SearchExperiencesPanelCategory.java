/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.web.internal.application.list;

import com.liferay.application.list.BasePanelCategory;
import com.liferay.application.list.PanelCategory;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.portal.kernel.language.Language;
import com.liferay.search.experiences.constants.SXPPanelCategoryKeys;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Kevin Tan
 */
@Component(
	enabled = false,
	property = {
		"panel.category.key=" + PanelCategoryKeys.APPLICATIONS_MENU_APPLICATIONS,
		"panel.category.order:Integer=500"
	},
	service = PanelCategory.class
)
public class SearchExperiencesPanelCategory extends BasePanelCategory {

	@Override
	public String getKey() {
		return SXPPanelCategoryKeys.CONTROL_PANEL_SEARCH_EXPERIENCES;
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, "search-experiences");
	}

	@Reference
	private Language _language;

}