/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.seo.studio.web.internal.fragment.renderer;

import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.seo.studio.web.internal.constants.SEOStudioFDSNames;
import com.liferay.seo.studio.web.internal.display.context.ViewOnPageInsightDetailsDisplayContext;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;

/**
 * @author Noor Najjar
 */
@Component(service = FragmentRenderer.class)
public class ViewOnPageInsightDetailsFragmentRenderer
	extends BaseFragmentRenderer<ViewOnPageInsightDetailsDisplayContext> {

	@Override
	public String getCollectionKey() {
		return "sections";
	}

	@Override
	public String getLabel(Locale locale) {
		return language.get(locale, "insight-detail-view");
	}

	@Override
	protected ViewOnPageInsightDetailsDisplayContext getDisplayContext(
		HttpServletRequest httpServletRequest) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		JSONArray viewsJSONArray = fdsSerializer.serializeViews(
			SEOStudioFDSNames.AFFECTED_PAGES_SECTION, httpServletRequest);

		return new ViewOnPageInsightDetailsDisplayContext(
			httpServletRequest, language, themeDisplay, viewsJSONArray);
	}

	@Override
	protected String getJSPPath() {
		return "/on_page_insight_details_view.jsp";
	}

}
