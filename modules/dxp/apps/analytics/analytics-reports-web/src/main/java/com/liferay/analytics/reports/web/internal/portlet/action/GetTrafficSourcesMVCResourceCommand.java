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
import com.liferay.analytics.reports.web.internal.model.DirectTrafficChannelImpl;
import com.liferay.analytics.reports.web.internal.model.OrganicTrafficChannelImpl;
import com.liferay.analytics.reports.web.internal.model.PaidTrafficChannelImpl;
import com.liferay.analytics.reports.web.internal.model.ReferralTrafficChannelImpl;
import com.liferay.analytics.reports.web.internal.model.SocialTrafficChannelImpl;
import com.liferay.analytics.reports.web.internal.model.TrafficChannel;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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
	immediate = true,
	property = {
		"javax.portlet.name=" + AnalyticsReportsPortletKeys.ANALYTICS_REPORTS,
		"mvc.command.name=/analytics_reports/get_traffic_sources"
	},
	service = MVCResourceCommand.class
)
public class GetTrafficSourcesMVCResourceCommand
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

			JSONObject jsonObject = JSONUtil.put(
				"trafficSources",
				_getTrafficSourcesJSONArray(
					analyticsReportsDataProvider, themeDisplay.getCompanyId(),
					canonicalURL, themeDisplay.getLocale(), resourceBundle));

			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse, jsonObject);
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONUtil.put(
					"error",
					ResourceBundleUtil.getString(
						resourceBundle, "an-unexpected-error-occurred")));
		}
	}

	private List<TrafficChannel> _getTrafficChannels(
		AnalyticsReportsDataProvider analyticsReportsDataProvider,
		String canonicalURL, long companyId) {

		Map<String, TrafficChannel> emptyMap = HashMapBuilder.put(
			"direct", (TrafficChannel)new DirectTrafficChannelImpl(false)
		).put(
			"organic", new OrganicTrafficChannelImpl(false)
		).put(
			"paid", new PaidTrafficChannelImpl(false)
		).put(
			"referral", new ReferralTrafficChannelImpl(false)
		).put(
			"social", new SocialTrafficChannelImpl(false)
		).build();

		if (!analyticsReportsDataProvider.isValidAnalyticsConnection(
				companyId)) {

			return new ArrayList<>(emptyMap.values());
		}

		try {
			Map<String, TrafficChannel> trafficChannels =
				analyticsReportsDataProvider.getTrafficChannels(
					companyId, canonicalURL);

			emptyMap.forEach(
				(name, trafficChannel) -> trafficChannels.merge(
					name, trafficChannel,
					(trafficChannel1, trafficChannel2) -> trafficChannel1));

			return new ArrayList<>(trafficChannels.values());
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);

			return Arrays.asList(
				new DirectTrafficChannelImpl(true),
				new OrganicTrafficChannelImpl(true),
				new PaidTrafficChannelImpl(true),
				new ReferralTrafficChannelImpl(true),
				new SocialTrafficChannelImpl(true));
		}
	}

	private JSONArray _getTrafficSourcesJSONArray(
		AnalyticsReportsDataProvider analyticsReportsDataProvider,
		long companyId, String canonicalURL, Locale locale,
		ResourceBundle resourceBundle) {

		List<TrafficChannel> trafficChannels = _getTrafficChannels(
			analyticsReportsDataProvider, canonicalURL, companyId);

		Stream<TrafficChannel> stream = trafficChannels.stream();

		Comparator<TrafficChannel> comparator = Comparator.comparing(
			TrafficChannel::getTrafficShare);

		return JSONUtil.putAll(
			stream.sorted(
				comparator.reversed()
			).map(
				trafficChannel -> trafficChannel.toJSONObject(
					locale, resourceBundle)
			).toArray());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GetTrafficSourcesMVCResourceCommand.class);

	@Reference
	private Http _http;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}