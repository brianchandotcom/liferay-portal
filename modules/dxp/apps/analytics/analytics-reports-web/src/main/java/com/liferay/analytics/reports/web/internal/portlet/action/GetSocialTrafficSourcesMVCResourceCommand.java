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
import com.liferay.analytics.reports.web.internal.model.ReferringSocialMedia;
import com.liferay.analytics.reports.web.internal.model.TimeRange;
import com.liferay.analytics.reports.web.internal.model.TimeSpan;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(
	property = {
		"javax.portlet.name=" + AnalyticsReportsPortletKeys.ANALYTICS_REPORTS,
		"mvc.command.name=/analytics_reports/get_social_traffic_sources"
	},
	service = MVCResourceCommand.class
)
public class GetSocialTrafficSourcesMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			themeDisplay.getLocale(), getClass());

		try {
			AnalyticsReportsDataProvider analyticsReportsDataProvider =
				new AnalyticsReportsDataProvider(_http);
			String canonicalURL = ParamUtil.getString(
				resourceRequest, "canonicalURL");

			String timeSpanKey = ParamUtil.getString(
				resourceRequest, "timeSpanKey", TimeSpan.defaultTimeSpanKey());

			TimeSpan timeSpan = TimeSpan.of(timeSpanKey);

			int timeSpanOffset = ParamUtil.getInteger(
				resourceRequest, "timeSpanOffset");

			JSONObject jsonObject = JSONUtil.put(
				"referringSocialMedia",
				_getReferringSocialMediaJSONArray(
					_getReferringSocialMediaList(
						analyticsReportsDataProvider, canonicalURL,
						themeDisplay.getCompanyId(),
						timeSpan.toTimeRange(timeSpanOffset)),
					resourceBundle));

			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse, jsonObject);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONUtil.put(
					"error",
					ResourceBundleUtil.getString(
						resourceBundle, "an-unexpected-error-occurred")));
		}
	}

	private JSONArray _getReferringSocialMediaJSONArray(
		List<ReferringSocialMedia> referringSocialMediaList,
		ResourceBundle resourceBundle) {

		if (ListUtil.isEmpty(referringSocialMediaList)) {
			return _jsonFactory.createJSONArray();
		}

		Stream<ReferringSocialMedia> stream = referringSocialMediaList.stream();

		Comparator<ReferringSocialMedia> comparator = Comparator.comparingInt(
			ReferringSocialMedia::getTrafficAmount);

		return JSONUtil.putAll(
			stream.filter(
				referringSocialMedia ->
					referringSocialMedia.getTrafficAmount() > 0
			).sorted(
				comparator.reversed()
			).map(
				referringSocialMedia -> referringSocialMedia.toJSONObject(
					resourceBundle)
			).toArray());
	}

	private List<ReferringSocialMedia> _getReferringSocialMediaList(
			AnalyticsReportsDataProvider analyticsReportsDataProvider,
			String canonicalURL, long companyId, TimeRange timeRange)
		throws PortalException {

		if (!analyticsReportsDataProvider.isValidAnalyticsConnection(
				companyId)) {

			throw new PortalException("Unable to get social media ");
		}

		return analyticsReportsDataProvider.getReferringSocialMediaList(
			companyId, timeRange, canonicalURL);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GetSocialTrafficSourcesMVCResourceCommand.class);

	@Reference
	private Http _http;

	@Reference
	private JSONFactory _jsonFactory;

}