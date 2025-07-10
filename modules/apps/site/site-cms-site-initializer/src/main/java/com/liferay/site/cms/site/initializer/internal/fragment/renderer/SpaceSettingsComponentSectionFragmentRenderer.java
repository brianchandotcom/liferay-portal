/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.fragment.renderer;

import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.fragment.renderer.FragmentRendererContext;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.site.cms.site.initializer.internal.util.InfoItemUtil;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Verónica González
 */
@Component(service = FragmentRenderer.class)
public class SpaceSettingsComponentSectionFragmentRenderer
	extends BaseComponentSectionFragmentRenderer {

	@Override
	public String getCollectionKey() {
		return "sections";
	}

	@Override
	protected String getLabelKey() {
		return "space-settings";
	}

	@Override
	protected String getModuleName() {
		return "SpaceSettings";
	}

	@Override
	protected Map<String, Object> getProps(
		FragmentRendererContext fragmentRendererContext,
		HttpServletRequest httpServletRequest) {
		
		ThemeDisplay themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		JSONArray companyAvailableLanguagesJSONArray = JSONFactoryUtil.createJSONArray();

		for (Locale availableLocale : LanguageUtil.getAvailableLocales(themeDisplay.getScopeGroupId())) {
			companyAvailableLanguagesJSONArray.put(
				JSONUtil.put(
					"label",
					availableLocale.getDisplayName(themeDisplay.getLocale())
				).put(
					"value", LanguageUtil.getLanguageId(availableLocale)
				));
		}

		return HashMapBuilder.<String, Object>put(
			"companyAvailableLanguages", companyAvailableLanguagesJSONArray
		).put(
			"backURL", ParamUtil.getString(httpServletRequest, "redirect")
		).put(
			"depotEntryId", InfoItemUtil.getDepotEntryId(httpServletRequest)
		).put(
			"groupId", InfoItemUtil.getGroupId(httpServletRequest)
		).build();
	}

}