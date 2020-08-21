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

import com.liferay.analytics.reports.info.item.AnalyticsReportsInfoItem;
import com.liferay.analytics.reports.info.item.AnalyticsReportsInfoItemTracker;
import com.liferay.analytics.reports.web.internal.constants.AnalyticsReportsPortletKeys;
import com.liferay.analytics.reports.web.internal.data.provider.AnalyticsReportsDataProvider;
import com.liferay.analytics.reports.web.internal.info.display.contributor.util.InfoDisplayContributorUtil;
import com.liferay.analytics.reports.web.internal.layout.seo.CanonicalURLProvider;
import com.liferay.analytics.reports.web.internal.model.TimeRange;
import com.liferay.analytics.reports.web.internal.model.TimeSpan;
import com.liferay.analytics.reports.web.internal.model.TrafficSource;
import com.liferay.asset.display.page.portlet.AssetDisplayPageFriendlyURLProvider;
import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayContributorTracker;
import com.liferay.info.display.contributor.InfoDisplayObjectProvider;
import com.liferay.layout.seo.kernel.LayoutSEOLinkManager;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.ResourceURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + AnalyticsReportsPortletKeys.ANALYTICS_REPORTS,
		"mvc.command.name=/analytics_reports/get_data"
	},
	service = MVCResourceCommand.class
)
public class GetDataMVCResourceCommand extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			resourceRequest);

		InfoDisplayObjectProvider<Object> infoDisplayObjectProvider =
			(InfoDisplayObjectProvider<Object>)
				InfoDisplayContributorUtil.getInfoDisplayObjectProvider(
					httpServletRequest, _infoDisplayContributorTracker,
					_portal);

		if (infoDisplayObjectProvider == null) {
			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONUtil.put(
					"error",
					_language.get(
						httpServletRequest, "an-unexpected-error-occurred")));

			return;
		}

		try {
			AnalyticsReportsInfoItem<Object> analyticsReportsInfoItem =
				(AnalyticsReportsInfoItem<Object>)
					_analyticsReportsInfoItemTracker.
						getAnalyticsReportsInfoItem(
							_portal.getClassName(
								infoDisplayObjectProvider.getClassNameId()));
			CanonicalURLProvider canonicalURLProvider =
				new CanonicalURLProvider(
					_assetDisplayPageFriendlyURLProvider, httpServletRequest,
					InfoDisplayContributorUtil.getInfoDisplayObjectProvider(
						httpServletRequest, _infoDisplayContributorTracker,
						_portal),
					_language, _layoutSEOLinkManager, _portal);
			InfoDisplayContributor<Object> infoDisplayContributor =
				(InfoDisplayContributor<Object>)
					_infoDisplayContributorTracker.getInfoDisplayContributor(
						_portal.getClassName(
							infoDisplayObjectProvider.getClassNameId()));
			ThemeDisplay themeDisplay =
				(ThemeDisplay)resourceRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONUtil.put(
					"context",
					_getJSONObject(
						analyticsReportsInfoItem,
						canonicalURLProvider.getCanonicalURL(),
						themeDisplay.getCompanyId(), infoDisplayContributor,
						infoDisplayObjectProvider, themeDisplay.getLayout(),
						themeDisplay.getLocale(),
						_getLocale(
							httpServletRequest, themeDisplay.getLanguageId()),
						infoDisplayObjectProvider.getDisplayObject(),
						resourceResponse, _getTimeRange(resourceRequest))));
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONUtil.put(
					"error",
					_language.get(
						httpServletRequest, "an-unexpected-error-occurred")));
		}
	}

	private JSONObject _getAuthorJSONObject(
		AnalyticsReportsInfoItem<Object> analyticsReportsInfoItem,
		InfoDisplayContributor<Object> infoDisplayContributor, Locale locale,
		Object object) {

		try {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				String.valueOf(
					infoDisplayContributor.getInfoDisplayFieldValue(
						object, "authorProfileImage", locale)));

			long portraitId = GetterUtil.getLong(
				_http.getParameter(
					HtmlUtil.escape(jsonObject.getString("url")), "img_id"));

			if (portraitId <= 0) {
				jsonObject.put("url", (String)null);
			}

			jsonObject.put(
				"authorId", analyticsReportsInfoItem.getAuthorUserId(object));

			return jsonObject;
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);

			return JSONFactoryUtil.createJSONObject();
		}
	}

	private JSONObject _getJSONObject(
		AnalyticsReportsInfoItem<Object> analyticsReportsInfoItem,
		String canonicalURL, long companyId,
		InfoDisplayContributor<Object> infoDisplayContributor,
		InfoDisplayObjectProvider<Object> infoDisplayObjectProvider,
		Layout layout, Locale locale, Locale urlLocale, Object object,
		ResourceResponse resourceResponse, TimeRange timeRange) {

		AnalyticsReportsDataProvider analyticsReportsDataProvider =
			new AnalyticsReportsDataProvider(_http);

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			locale, getClass());

		return JSONUtil.put(
			"author",
			_getAuthorJSONObject(
				analyticsReportsInfoItem, infoDisplayContributor, locale,
				object)
		).put(
			"canonicalURL", canonicalURL
		).put(
			"endpoints",
			JSONUtil.put(
				"getAnalyticsReportsHistoricalReadsURL",
				String.valueOf(
					_getResourceURL(
						infoDisplayObjectProvider, urlLocale, resourceResponse,
						"/analytics_reports/get_historical_reads"))
			).put(
				"getAnalyticsReportsHistoricalViewsURL",
				String.valueOf(
					_getResourceURL(
						infoDisplayObjectProvider, urlLocale, resourceResponse,
						"/analytics_reports/get_historical_views"))
			).put(
				"getAnalyticsReportsTotalReadsURL",
				String.valueOf(
					_getResourceURL(
						infoDisplayObjectProvider, urlLocale, resourceResponse,
						"/analytics_reports/get_total_reads"))
			).put(
				"getAnalyticsReportsTotalViewsURL",
				String.valueOf(
					_getResourceURL(
						infoDisplayObjectProvider, urlLocale, resourceResponse,
						"/analytics_reports/get_total_views"))
			)
		).put(
			"languageTag", locale.toLanguageTag()
		).put(
			"namespace",
			_portal.getPortletNamespace(
				AnalyticsReportsPortletKeys.ANALYTICS_REPORTS)
		).put(
			"page", JSONUtil.put("plid", layout.getPlid())
		).put(
			"publishDate",
			DateTimeFormatter.ISO_DATE.format(
				_toLocaleDate(analyticsReportsInfoItem.getPublishDate(object)))
		).put(
			"timeRange",
			JSONUtil.put(
				"endDate",
				DateTimeFormatter.ISO_DATE.format(timeRange.getEndLocalDate())
			).put(
				"startDate",
				DateTimeFormatter.ISO_DATE.format(timeRange.getStartLocalDate())
			)
		).put(
			"timeSpanKey", timeRange.getTimeSpan()
		).put(
			"timeSpans", _getTimeSpansJSONArray(resourceBundle)
		).put(
			"title", infoDisplayObjectProvider.getTitle(urlLocale)
		).put(
			"trafficSources",
			_getTrafficSourcesJSONArray(
				analyticsReportsDataProvider, companyId, canonicalURL, locale,
				resourceBundle)
		).put(
			"validAnalyticsConnection",
			analyticsReportsDataProvider.isValidAnalyticsConnection(companyId)
		).put(
			"viewURLs",
			_getViewURLsJSONArray(
				analyticsReportsInfoItem, infoDisplayObjectProvider, object,
				resourceResponse, urlLocale)
		);
	}

	private Locale _getLocale(
		HttpServletRequest httpServletRequest, String languageId) {

		return LocaleUtil.fromLanguageId(
			ParamUtil.getString(httpServletRequest, "languageId", languageId));
	}

	private ResourceURL _getResourceURL(
		InfoDisplayObjectProvider<Object> infoDisplayObjectProvider,
		Locale locale, ResourceResponse resourceResponse, String resourceID) {

		ResourceURL resourceURL = resourceResponse.createResourceURL();

		resourceURL.setParameter(
			"classNameId",
			String.valueOf(infoDisplayObjectProvider.getClassNameId()));
		resourceURL.setParameter(
			"classPK", String.valueOf(infoDisplayObjectProvider.getClassPK()));
		resourceURL.setParameter("languageId", LocaleUtil.toLanguageId(locale));
		resourceURL.setResourceID(resourceID);

		return resourceURL;
	}

	private TimeRange _getTimeRange(ResourceRequest resourceRequest) {
		String timeSpanKey = ParamUtil.getString(
			resourceRequest, "timeSpanKey", TimeSpan.defaultTimeSpanKey());

		if (Validator.isNull(timeSpanKey)) {
			TimeSpan defaultTimeSpan = TimeSpan.of(
				TimeSpan.defaultTimeSpanKey());

			return defaultTimeSpan.toTimeRange(0);
		}

		TimeSpan timeSpan = TimeSpan.of(timeSpanKey);

		int timeSpanOffset = ParamUtil.getInteger(
			resourceRequest, "timeSpanOffset");

		return timeSpan.toTimeRange(timeSpanOffset);
	}

	private JSONArray _getTimeSpansJSONArray(ResourceBundle resourceBundle) {
		Stream<TimeSpan> stream = Arrays.stream(TimeSpan.values());

		return JSONUtil.putAll(
			stream.filter(
				timeSpan -> timeSpan != TimeSpan.TODAY
			).sorted(
				Comparator.comparingInt(TimeSpan::getDays)
			).map(
				timeSpan -> JSONUtil.put(
					"key", timeSpan.getKey()
				).put(
					"label",
					ResourceBundleUtil.getString(
						resourceBundle, timeSpan.getKey())
				)
			).toArray());
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

		JSONArray trafficSourcesJSONArray = JSONFactoryUtil.createJSONArray();

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

		titleMap.forEach(
			(name, title) -> {
				Stream<TrafficSource> stream = trafficSources.stream();

				trafficSourcesJSONArray.put(
					stream.filter(
						trafficSource -> Objects.equals(
							name, trafficSource.getName())
					).findFirst(
					).map(
						trafficSource -> trafficSource.toJSONObject(
							helpMessageMap.get(name), locale, title)
					).orElse(
						JSONUtil.put(
							"helpMessage", helpMessageMap.get(name)
						).put(
							"name", name
						).put(
							"title", title
						)
					));
			});

		return trafficSourcesJSONArray;
	}

	private JSONArray _getViewURLsJSONArray(
		AnalyticsReportsInfoItem<Object> analyticsReportsInfoItem,
		InfoDisplayObjectProvider<Object> infoDisplayObjectProvider,
		Object object, ResourceResponse resourceResponse, Locale urlLocale) {

		List<Locale> locales = analyticsReportsInfoItem.getAvailableLocales(
			object);

		Stream<Locale> stream = locales.stream();

		return JSONUtil.putAll(
			stream.map(
				locale -> JSONUtil.put(
					"default",
					Objects.equals(
						locale,
						analyticsReportsInfoItem.getDefaultLocale(object))
				).put(
					"languageId", LocaleUtil.toBCP47LanguageId(locale)
				).put(
					"selected", Objects.equals(locale, urlLocale)
				).put(
					"viewURL",
					_getResourceURL(
						infoDisplayObjectProvider, locale, resourceResponse,
						"/analytics_reports/get_data")
				)
			).toArray());
	}

	private LocalDate _toLocaleDate(Date date) {
		Instant instant = date.toInstant();

		ZonedDateTime zonedDateTime = instant.atZone(ZoneId.systemDefault());

		return zonedDateTime.toLocalDate();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GetDataMVCResourceCommand.class);

	@Reference
	private AnalyticsReportsInfoItemTracker _analyticsReportsInfoItemTracker;

	@Reference
	private AssetDisplayPageFriendlyURLProvider
		_assetDisplayPageFriendlyURLProvider;

	@Reference
	private Http _http;

	@Reference
	private InfoDisplayContributorTracker _infoDisplayContributorTracker;

	@Reference
	private Language _language;

	@Reference
	private LayoutSEOLinkManager _layoutSEOLinkManager;

	@Reference
	private Portal _portal;

}