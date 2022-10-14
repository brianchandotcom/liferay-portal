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

package com.liferay.analytics.reports.web.internal.portlet.action;

import com.liferay.analytics.reports.web.internal.constants.AnalyticsReportsPortletKeys;
import com.liferay.analytics.reports.web.internal.data.provider.AnalyticsReportsDataProvider;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Arques
 */
@Component(
	property = {
		"javax.portlet.name=" + AnalyticsReportsPortletKeys.ANALYTICS_REPORTS,
		"mvc.command.name=/analytics_reports/get_total_reads"
	},
	service = MVCResourceCommand.class
)
public class GetTotalReadsMVCResourceCommand extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		AnalyticsReportsDataProvider analyticsReportsDataProvider =
			new AnalyticsReportsDataProvider(_http);
		String canonicalURL = ParamUtil.getString(
			resourceRequest, "canonicalURL");

		try {
			JSONObject jsonObject = JSONUtil.put(
				"analyticsReportsTotalReads",
				analyticsReportsDataProvider.getTotalReads(
					_portal.getCompanyId(resourceRequest), canonicalURL));

			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse, jsonObject);
		}
		catch (Exception exception) {
			if (_log.isInfoEnabled()) {
				_log.info(exception);
			}

			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONUtil.put(
					"error",
					() -> {
						ThemeDisplay themeDisplay =
							(ThemeDisplay)resourceRequest.getAttribute(
								WebKeys.THEME_DISPLAY);

						return ResourceBundleUtil.getString(
							ResourceBundleUtil.getBundle(
								themeDisplay.getLocale(), getClass()),
							"an-unexpected-error-occurred");
					}));
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GetTotalReadsMVCResourceCommand.class);

	@Reference
	private Http _http;

	@Reference
	private Portal _portal;

}