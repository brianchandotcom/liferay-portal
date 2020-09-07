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
import com.liferay.analytics.reports.web.internal.info.display.contributor.util.LayoutDisplayPageProviderUtil;
import com.liferay.analytics.reports.web.internal.layout.seo.CanonicalURLProvider;
import com.liferay.analytics.reports.web.internal.model.TimeRange;
import com.liferay.analytics.reports.web.internal.model.TimeSpan;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.type.WebImage;
import com.liferay.layout.display.page.LayoutDisplayPageObjectProvider;
import com.liferay.layout.display.page.LayoutDisplayPageProviderTracker;
import com.liferay.layout.seo.kernel.LayoutSEOLinkManager;
import com.liferay.portal.kernel.json.JSONArray;
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
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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

		LayoutDisplayPageObjectProvider<Object>
			layoutDisplayPageObjectProvider =
				(LayoutDisplayPageObjectProvider<Object>)
					LayoutDisplayPageProviderUtil.
						getLayoutDisplayPageObjectProvider(
							httpServletRequest,
							_layoutDisplayPageProviderTracker, _portal);

		if (layoutDisplayPageObjectProvider == null) {
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
								layoutDisplayPageObjectProvider.
									getClassNameId()));
			CanonicalURLProvider canonicalURLProvider =
				new CanonicalURLProvider(
					httpServletRequest, _layoutSEOLinkManager, _portal);
			InfoItemFieldValuesProvider<Object> infoItemFieldValuesProvider =
				_infoItemServiceTracker.getFirstInfoItemService(
					InfoItemFieldValuesProvider.class,
					_portal.getClassName(
						layoutDisplayPageObjectProvider.getClassNameId()));
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
						themeDisplay.getCompanyId(),
						infoItemFieldValuesProvider,
						layoutDisplayPageObjectProvider,
						themeDisplay.getLayout(), themeDisplay.getLocale(),
						_getLocale(
							httpServletRequest, themeDisplay.getLanguageId()),
						layoutDisplayPageObjectProvider.getDisplayObject(),
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
		InfoItemFieldValuesProvider<Object> infoItemFieldValuesProvider,
		Locale locale, Object object) {

		String authorProfileImage = null;

		InfoItemFieldValues infoItemFieldValues =
			infoItemFieldValuesProvider.getInfoItemFieldValues(object);

		InfoFieldValue<Object> authorProfileImageInfoFieldValue =
			infoItemFieldValues.getInfoFieldValue("authorProfileImage");

		WebImage webImage = (WebImage)authorProfileImageInfoFieldValue.getValue(
			locale);

		long portraitId = GetterUtil.getLong(
			_http.getParameter(HtmlUtil.escape(webImage.getUrl()), "img_id"));

		if (portraitId > 0) {
			authorProfileImage = webImage.getUrl();
		}

		return JSONUtil.put(
			"authorId", analyticsReportsInfoItem.getAuthorUserId(object)
		).put(
			"name", webImage.getAlt()
		).put(
			"url", authorProfileImage
		);
	}

	private JSONObject _getJSONObject(
		AnalyticsReportsInfoItem<Object> analyticsReportsInfoItem,
		String canonicalURL, long companyId,
		InfoItemFieldValuesProvider<Object> infoItemFieldValuesProvider,
		LayoutDisplayPageObjectProvider<Object> layoutDisplayPageObjectProvider,
		Layout layout, Locale locale, Locale urlLocale, Object object,
		ResourceResponse resourceResponse, TimeRange timeRange) {

		AnalyticsReportsDataProvider analyticsReportsDataProvider =
			new AnalyticsReportsDataProvider(_http);

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			locale, getClass());

		return JSONUtil.put(
			"author",
			_getAuthorJSONObject(
				analyticsReportsInfoItem, infoItemFieldValuesProvider, locale,
				object)
		).put(
			"canonicalURL", canonicalURL
		).put(
			"endpoints",
			JSONUtil.put(
				"analyticsReportsHistoricalReadsURL",
				String.valueOf(
					_getResourceURL(
						layoutDisplayPageObjectProvider, urlLocale,
						resourceResponse,
						"/analytics_reports/get_historical_reads"))
			).put(
				"analyticsReportsHistoricalViewsURL",
				String.valueOf(
					_getResourceURL(
						layoutDisplayPageObjectProvider, urlLocale,
						resourceResponse,
						"/analytics_reports/get_historical_views"))
			).put(
				"analyticsReportsTotalReadsURL",
				String.valueOf(
					_getResourceURL(
						layoutDisplayPageObjectProvider, urlLocale,
						resourceResponse, "/analytics_reports/get_total_reads"))
			).put(
				"analyticsReportsTotalViewsURL",
				String.valueOf(
					_getResourceURL(
						layoutDisplayPageObjectProvider, urlLocale,
						resourceResponse, "/analytics_reports/get_total_views"))
			).put(
				"analyticsReportsTrafficSourcesURL",
				String.valueOf(
					_getResourceURL(
						layoutDisplayPageObjectProvider, urlLocale,
						resourceResponse,
						"/analytics_reports/get_traffic_sources"))
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
			"timeSpanKey", _getTimeSpanKey(timeRange)
		).put(
			"timeSpans", _getTimeSpansJSONArray(resourceBundle)
		).put(
			"title", layoutDisplayPageObjectProvider.getTitle(urlLocale)
		).put(
			"validAnalyticsConnection",
			analyticsReportsDataProvider.isValidAnalyticsConnection(companyId)
		).put(
			"viewURLs",
			_getViewURLsJSONArray(
				analyticsReportsInfoItem, layoutDisplayPageObjectProvider,
				object, resourceResponse, urlLocale)
		);
	}

	private Locale _getLocale(
		HttpServletRequest httpServletRequest, String languageId) {

		return LocaleUtil.fromLanguageId(
			ParamUtil.getString(httpServletRequest, "languageId", languageId));
	}

	private ResourceURL _getResourceURL(
		LayoutDisplayPageObjectProvider<Object> layoutDisplayPageObjectProvider,
		Locale locale, ResourceResponse resourceResponse, String resourceID) {

		ResourceURL resourceURL = resourceResponse.createResourceURL();

		resourceURL.setParameter(
			"classNameId",
			String.valueOf(layoutDisplayPageObjectProvider.getClassNameId()));
		resourceURL.setParameter(
			"classPK",
			String.valueOf(layoutDisplayPageObjectProvider.getClassPK()));
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

	private String _getTimeSpanKey(TimeRange timeRange) {
		TimeSpan timeSpan = timeRange.getTimeSpan();

		return timeSpan.getKey();
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

	private JSONArray _getViewURLsJSONArray(
		AnalyticsReportsInfoItem<Object> analyticsReportsInfoItem,
		LayoutDisplayPageObjectProvider<Object> layoutDisplayPageObjectProvider,
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
						layoutDisplayPageObjectProvider, locale,
						resourceResponse, "/analytics_reports/get_data")
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
	private Http _http;

	@Reference
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Reference
	private Language _language;

	@Reference
	private LayoutDisplayPageProviderTracker _layoutDisplayPageProviderTracker;

	@Reference
	private LayoutSEOLinkManager _layoutSEOLinkManager;

	@Reference
	private Portal _portal;

}