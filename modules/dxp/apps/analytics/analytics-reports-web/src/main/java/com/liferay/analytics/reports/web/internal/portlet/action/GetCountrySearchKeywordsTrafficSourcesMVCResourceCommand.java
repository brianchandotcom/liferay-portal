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
import com.liferay.analytics.reports.web.internal.model.CountrySearchKeywords;
import com.liferay.analytics.reports.web.internal.model.TrafficSource;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
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

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
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
		"mvc.command.name=/analytics_reports/get_country_search_keywords_traffic_sources"
	},
	service = MVCResourceCommand.class
)
public class GetCountrySearchKeywordsTrafficSourcesMVCResourceCommand
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

			String name = ParamUtil.getString(resourceRequest, "name");

			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONUtil.put(
					"countrySearchKeywords",
					_getCountrySearchKeywordsJSONArray(
						_getCountrySearchKeywords(
							analyticsReportsDataProvider, canonicalURL,
							themeDisplay.getCompanyId(), name),
						themeDisplay.getLocale())));
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

	private List<CountrySearchKeywords> _getCountrySearchKeywords(
			AnalyticsReportsDataProvider analyticsReportsDataProvider,
			String canonicalURL, long companyId, String name)
		throws PortalException {

		if (!analyticsReportsDataProvider.isValidAnalyticsConnection(
				companyId)) {

			throw new PortalException("Unable to get referring domains");
		}

		Map<String, TrafficSource> trafficSources =
			analyticsReportsDataProvider.getTrafficSources(
				companyId, canonicalURL);

		return Optional.ofNullable(
			trafficSources.get(name)
		).map(
			TrafficSource::getCountrySearchKeywordsList
		).orElse(
			Collections.emptyList()
		);
	}

	private JSONArray _getCountrySearchKeywordsJSONArray(
		List<CountrySearchKeywords> countrySearchKeywordsList, Locale locale) {

		if (ListUtil.isEmpty(countrySearchKeywordsList)) {
			return JSONFactoryUtil.createJSONArray();
		}

		Stream<CountrySearchKeywords> stream =
			countrySearchKeywordsList.stream();

		return JSONUtil.putAll(
			stream.map(
				countrySearchKeywords -> countrySearchKeywords.toJSONObject(
					locale)
			).toArray());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GetCountrySearchKeywordsTrafficSourcesMVCResourceCommand.class);

	@Reference
	private Http _http;

}