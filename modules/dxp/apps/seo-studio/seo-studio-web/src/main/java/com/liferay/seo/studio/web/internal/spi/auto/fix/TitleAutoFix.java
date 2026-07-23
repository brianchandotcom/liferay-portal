/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.web.internal.spi.auto.fix;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.seo.studio.spi.auto.fix.AutoFix;
import com.liferay.seo.studio.spi.auto.fix.BaseAutoFix;

import org.osgi.service.component.annotations.Component;

/**
 * @author David Truong
 */
@Component(service = AutoFix.class)
public class TitleAutoFix extends BaseAutoFix {

	@Override
	public String getInsightType() {
		return "missingOrEmptyTitleTag";
	}

	@Override
	protected String getPatchBody(
		String languageId, String type, String value) {

		JSONObject seoSettingsJSONObject = JSONUtil.put(
			"htmlTitle_i18n", JSONUtil.put(languageId, value));

		return JSONUtil.put(
			"pageSettings",
			JSONUtil.put(
				"seoSettings", seoSettingsJSONObject
			).put(
				"type", type
			)
		).toString();
	}

	@Override
	protected String[] getVerificationPaths(String languageId) {
		return new String[] {
			"JSONObject/pageSettings", "JSONObject/seoSettings",
			"JSONObject/htmlTitle_i18n", "Object/" + languageId
		};
	}

}