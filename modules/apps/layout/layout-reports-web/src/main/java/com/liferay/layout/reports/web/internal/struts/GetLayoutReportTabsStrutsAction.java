/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.reports.web.internal.struts;

import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mikel Lorza
 */
@Component(
	property = "path=/layout_reports/get_layout_reports_tabs",
	service = StrutsAction.class
)
public class GetLayoutReportTabsStrutsAction implements StrutsAction {

	@Override
	public String execute(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		ServletResponseUtil.write(
			httpServletResponse,
			JSONUtil.putAll(
				JSONUtil.put(
					"id", "render-times"
				).put(
					"name",
					_language.get(themeDisplay.getLocale(), "render-times")
				).put(
					"url",
					() -> HttpComponentsUtil.addParameters(
						themeDisplay.getPortalURL() +
							themeDisplay.getPathMain() +
								"/layout_reports/get_render_times_data",
						"p_l_id", themeDisplay.getPlid())
				),
				JSONUtil.put(
					"id", "page-speed-insights"
				).put(
					"name",
					_language.get(
						themeDisplay.getLocale(), "page-speed-insights")
				).put(
					"url",
					HttpComponentsUtil.addParameters(
						themeDisplay.getPortalURL() +
							themeDisplay.getPathMain() +
								"/layout_reports/get_layout_reports_data",
						"p_l_id", themeDisplay.getPlid())
				)
			).toString());

		return null;
	}

	@Reference
	private Language _language;

}