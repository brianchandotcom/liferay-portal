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

package com.liferay.search.experiences.web.internal.application.list;

import com.liferay.application.list.BasePanelCategory;
import com.liferay.application.list.PanelCategory;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.search.experiences.constants.SXPPanelCategoryKeys;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Kevin Tan
 */
@Component(
	enabled = false, immediate = true,
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
		return LanguageUtil.get(locale, "search-experiences");
	}

}