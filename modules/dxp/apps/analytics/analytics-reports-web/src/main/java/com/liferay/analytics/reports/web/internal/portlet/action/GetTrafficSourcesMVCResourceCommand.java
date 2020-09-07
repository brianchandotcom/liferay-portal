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

import com.liferay.analytics.reports.info.item.AnalyticsReportsInfoItemTracker;
import com.liferay.analytics.reports.web.internal.constants.AnalyticsReportsPortletKeys;
import com.liferay.analytics.reports.web.internal.data.provider.AnalyticsReportsDataProvider;
import com.liferay.analytics.reports.web.internal.layout.seo.CanonicalURLProvider;
import com.liferay.analytics.reports.web.internal.model.TrafficSource;
import com.liferay.layout.display.page.LayoutDisplayPageProviderTracker;
import com.liferay.layout.seo.kernel.LayoutSEOLinkManager;
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
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
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
			CanonicalURLProvider canonicalURLProvider =
				new CanonicalURLProvider(
					_portal.getHttpServletRequest(resourceRequest),
					_layoutSEOLinkManager, _portal);

			JSONObject jsonObject = JSONUtil.put(
				"trafficSources",
				_getTrafficSourcesJSONArray(
					analyticsReportsDataProvider, themeDisplay.getCompanyId(),
					canonicalURLProvider.getCanonicalURL(),
					themeDisplay.getLocale(), resourceBundle));

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

	private List<TrafficSource> _getTrafficSources(
		AnalyticsReportsDataProvider analyticsReportsDataProvider,
		String canonicalURL, long companyId) {

		if (!analyticsReportsDataProvider.isValidAnalyticsConnection(
				companyId)) {

			return Collections.emptyList();
		}

		try {
			return analyticsReportsDataProvider.getTrafficSources(
				companyId, canonicalURL);
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);

			return Collections.emptyList();
		}
	}

	private JSONArray _getTrafficSourcesJSONArray(
		AnalyticsReportsDataProvider analyticsReportsDataProvider,
		long companyId, String canonicalURL, Locale locale,
		ResourceBundle resourceBundle) {

		Map<String, String> helpMessageMap = HashMapBuilder.put(
			"organic",
			ResourceBundleUtil.getString(
				resourceBundle,
				"this-number-refers-to-the-volume-of-people-that-find-your-" +
					"page-through-a-search-engine")
		).put(
			"paid",
			ResourceBundleUtil.getString(
				resourceBundle,
				"this-number-refers-to-the-volume-of-people-that-find-your-" +
					"page-through-paid-keywords")
		).build();

		Map<String, String> titleMap = HashMapBuilder.put(
			"organic", ResourceBundleUtil.getString(resourceBundle, "organic")
		).put(
			"paid", ResourceBundleUtil.getString(resourceBundle, "paid")
		).build();

		List<TrafficSource> trafficSources = _getTrafficSources(
			analyticsReportsDataProvider, canonicalURL, companyId);

		return JSONUtil.putAll(
			Stream.of(
				"organic", "paid"
			).map(
				name -> {
					Stream<TrafficSource> stream = trafficSources.stream();

					return stream.filter(
						trafficSource -> Objects.equals(
							name, trafficSource.getName())
					).findFirst(
					).map(
						trafficSource -> trafficSource.toJSONObject(
							helpMessageMap.get(name), locale,
							titleMap.get(name))
					).orElse(
						JSONUtil.put(
							"helpMessage", helpMessageMap.get(name)
						).put(
							"name", name
						).put(
							"title", titleMap.get(name)
						)
					);
				}
			).toArray());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GetTrafficSourcesMVCResourceCommand.class);

	@Reference
	private AnalyticsReportsInfoItemTracker _analyticsReportsInfoItemTracker;

	@Reference
	private Http _http;

	@Reference
	private Language _language;

	@Reference
	private LayoutDisplayPageProviderTracker _layoutDisplayPageProviderTracker;

	@Reference
	private LayoutSEOLinkManager _layoutSEOLinkManager;

	@Reference
	private Portal _portal;

}