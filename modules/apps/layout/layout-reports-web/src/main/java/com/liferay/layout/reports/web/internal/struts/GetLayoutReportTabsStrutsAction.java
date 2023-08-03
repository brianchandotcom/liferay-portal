/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.reports.web.internal.struts;

import com.liferay.layout.reports.web.internal.configuration.provider.LayoutReportsGooglePageSpeedConfigurationProvider;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
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

		Layout layout = themeDisplay.getLayout();

		JSONArray jsonArray = _jsonFactory.createJSONArray();

		if (layout.isTypeContent() || layout.isTypeAssetDisplay()) {
			jsonArray.put(
				JSONUtil.put(
					"id", "render-times"
				).put(
					"name",
					_language.get(themeDisplay.getLocale(), "render-times")
				).put(
					"url",
					() -> {
						String url = HttpComponentsUtil.addParameter(
							themeDisplay.getPortalURL() +
								themeDisplay.getPathMain() +
									"/layout_reports/get_render_times_data",
							"p_l_id", themeDisplay.getPlid());

						long segmentsExperienceId = ParamUtil.getLong(
							_portal.getOriginalServletRequest(
								httpServletRequest),
							"segmentsExperienceId", -1);

						if (segmentsExperienceId == -1) {
							return url;
						}

						return HttpComponentsUtil.addParameter(
							url, "segmentsExperienceId", segmentsExperienceId);
					}
				));
		}

		if (_layoutReportsGooglePageSpeedConfigurationProvider.isEnabled(
				themeDisplay.getScopeGroup())) {

			jsonArray.put(
				JSONUtil.put(
					"id", "page-speed-insights"
				).put(
					"name",
					_language.get(
						themeDisplay.getLocale(), "page-speed-insights")
				).put(
					"url",
					HttpComponentsUtil.addParameter(
						themeDisplay.getPortalURL() +
							themeDisplay.getPathMain() +
								"/layout_reports/get_layout_reports_data",
						"p_l_id", themeDisplay.getPlid())
				));
		}

		ServletResponseUtil.write(httpServletResponse, jsonArray.toString());

		return null;
	}

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

	@Reference
	private LayoutReportsGooglePageSpeedConfigurationProvider
		_layoutReportsGooglePageSpeedConfigurationProvider;

	@Reference
	private Portal _portal;

}