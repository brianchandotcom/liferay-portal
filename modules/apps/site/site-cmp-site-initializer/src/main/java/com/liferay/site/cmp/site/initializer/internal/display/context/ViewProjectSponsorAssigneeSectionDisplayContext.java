/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cmp.site.initializer.internal.display.context;

import com.liferay.object.model.ObjectEntry;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;

/**
 * @author Igor Franca
 */
public class ViewProjectSponsorAssigneeSectionDisplayContext
	extends BaseAssigneeSectionDisplayContext {

	public ViewProjectSponsorAssigneeSectionDisplayContext(
		Language language, ObjectEntry objectEntry, ThemeDisplay themeDisplay,
		UserLocalService userLocalService) {

		super(language, objectEntry, themeDisplay, userLocalService);
	}

	@Override
	public String getAdditionalAPIURLParameters() {
		return "type=User";
	}

	@Override
	public String getLabelKey() {
		return "sponsor";
	}

	@Override
	public String getName() {
		return "ObjectField_r_userToCMPProjectSponsor_userId";
	}

	@Override
	public boolean getUsersOnly() {
		return true;
	}

	@Override
	public JSONObject getValueJSONObject() throws Exception {
		return getValueJSONObject("r_userToCMPProjectSponsor_userId");
	}

}