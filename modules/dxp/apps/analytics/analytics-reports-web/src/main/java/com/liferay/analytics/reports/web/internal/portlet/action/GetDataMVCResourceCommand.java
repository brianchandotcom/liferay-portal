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
import com.liferay.analytics.reports.info.item.ClassNameClassPKInfoItemIdentifier;
import com.liferay.analytics.reports.info.item.provider.AnalyticsReportsInfoItemObjectProvider;
import com.liferay.analytics.reports.web.internal.constants.AnalyticsReportsPortletKeys;
import com.liferay.analytics.reports.web.internal.data.provider.AnalyticsReportsDataProvider;
import com.liferay.analytics.reports.web.internal.info.item.provider.AnalyticsReportsInfoItemObjectProviderTracker;
import com.liferay.analytics.reports.web.internal.model.TimeRange;
import com.liferay.analytics.reports.web.internal.model.TimeSpan;
import com.liferay.analytics.reports.web.internal.util.AnalyticsReportsUtil;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemReference;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.NoSuchModelException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
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
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import javax.portlet.MimeResponse;
import javax.portlet.PortletRequest;
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

		try {
			InfoItemReference infoItemReference = _getInfoItemReference(
				httpServletRequest);

			Object analyticsReportsInfoItemObject = Optional.ofNullable(
				_analyticsReportsInfoItemObjectProviderTracker.
					getAnalyticsReportsInfoItemObjectProvider(
						infoItemReference.getClassName())
			).map(
				analyticsReportsInfoItemObjectProvider ->
					analyticsReportsInfoItemObjectProvider.
						getAnalyticsReportsInfoItemObject(infoItemReference)
			).orElseThrow(
				() -> new NoSuchModelException(
					"No analytics reports info item object provider found " +
						"for " + infoItemReference)
			);

			AnalyticsReportsInfoItem<Object> analyticsReportsInfoItem =
				(AnalyticsReportsInfoItem<Object>)
					_analyticsReportsInfoItemTracker.
						getAnalyticsReportsInfoItem(
							infoItemReference.getClassName());

			ThemeDisplay themeDisplay =
				(ThemeDisplay)resourceRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONUtil.put(
					"context",
					_getJSONObject(
						analyticsReportsInfoItem, infoItemReference,
						themeDisplay.getLayout(),
						themeDisplay.getLayoutFriendlyURL(
							themeDisplay.getLayout()),
						themeDisplay.getLocale(),
						_getLocale(
							httpServletRequest, themeDisplay.getLanguageId()),
						analyticsReportsInfoItemObject, resourceRequest,
						resourceResponse, _getTimeRange(resourceRequest))));
		}
		catch (Exception exception) {
			_log.error(exception);

			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONUtil.put(
					"error",
					_language.get(
						httpServletRequest, "an-unexpected-error-occurred")));
		}
	}

	private JSONObject _getAnalyticsDataJSONObject(Layout layout) {
		return JSONUtil.put(
			"cloudTrialURL", AnalyticsReportsUtil.ANALYTICS_CLOUD_TRIAL_URL
		).put(
			"hasValidConnection",
			() -> {
				AnalyticsReportsDataProvider analyticsReportsDataProvider =
					new AnalyticsReportsDataProvider(_http);

				return analyticsReportsDataProvider.isValidAnalyticsConnection(
					layout.getCompanyId());
			}
		).put(
			"isSynced",
			() -> AnalyticsReportsUtil.isAnalyticsSynced(
				layout.getCompanyId(), layout.getGroupId())
		).put(
			"url",
			() -> PrefsPropsUtil.getString(
				layout.getCompanyId(), "liferayAnalyticsURL")
		);
	}

	private JSONObject _getAuthorJSONObject(
		AnalyticsReportsInfoItem<Object> analyticsReportsInfoItem,
		Locale locale, Object object) {

		return Optional.ofNullable(
			analyticsReportsInfoItem.getAuthorWebImage(object, locale)
		).filter(
			webImage -> Validator.isNotNull(webImage.getUrl())
		).map(
			webImage -> {
				long portraitId = GetterUtil.getLong(
					HttpComponentsUtil.getParameter(
						HtmlUtil.escape(webImage.getUrl()), "img_id"));

				if (portraitId > 0) {
					return JSONUtil.put(
						"authorId",
						analyticsReportsInfoItem.getAuthorUserId(object)
					).put(
						"name", analyticsReportsInfoItem.getAuthorName(object)
					).put(
						"url", webImage.getUrl()
					);
				}

				return JSONUtil.put(
					"authorId", analyticsReportsInfoItem.getAuthorUserId(object)
				).put(
					"name", analyticsReportsInfoItem.getAuthorName(object)
				);
			}
		).orElse(
			null
		);
	}

	private String _getClassName(HttpServletRequest httpServletRequest) {
		String className = ParamUtil.getString(httpServletRequest, "className");

		if (Validator.isNull(className)) {
			return Layout.class.getName();
		}

		return className;
	}

	private long _getClassPK(HttpServletRequest httpServletRequest) {
		long classPK = ParamUtil.getLong(httpServletRequest, "classPK");

		if (classPK == 0) {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			return themeDisplay.getPlid();
		}

		return classPK;
	}

	private String _getClassTypeName(HttpServletRequest httpServletRequest) {
		return ParamUtil.getString(httpServletRequest, "classTypeName");
	}

	private JSONObject _getEndpointsJSONObject(
		AnalyticsReportsInfoItem<Object> analyticsReportsInfoItem,
		String canonicalURL, Locale locale, ResourceRequest resourceRequest,
		ResourceResponse resourceResponse) {

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		Optional.ofNullable(
			analyticsReportsInfoItem.getActions()
		).orElseGet(
			Collections::emptyList
		).stream(
		).map(
			_objectValuePairs::get
		).forEach(
			objectValuePair -> jsonObject.put(
				objectValuePair.getKey(),
				_getResourceURL(
					canonicalURL, locale, resourceRequest, resourceResponse,
					objectValuePair.getValue()))
		);

		return jsonObject;
	}

	private InfoItemReference _getInfoItemReference(
		HttpServletRequest httpServletRequest) {

		return Optional.ofNullable(
			_getClassTypeName(httpServletRequest)
		).filter(
			Validator::isNotNull
		).map(
			classTypeName -> new InfoItemReference(
				_getClassName(httpServletRequest),
				new ClassNameClassPKInfoItemIdentifier(
					classTypeName, _getClassPK(httpServletRequest)))
		).orElseGet(
			() -> new InfoItemReference(
				_getClassName(httpServletRequest),
				_getClassPK(httpServletRequest))
		);
	}

	private JSONObject _getJSONObject(
		AnalyticsReportsInfoItem<Object> analyticsReportsInfoItem,
		InfoItemReference infoItemReference, Layout layout,
		String layoutFriendlyURL, Locale locale, Locale urlLocale,
		Object object, ResourceRequest resourceRequest,
		ResourceResponse resourceResponse, TimeRange timeRange) {

		String canonicalURL = analyticsReportsInfoItem.getCanonicalURL(
			object, urlLocale);
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			locale, getClass());

		return JSONUtil.put(
			"analyticsData", _getAnalyticsDataJSONObject(layout)
		).put(
			"author",
			_getAuthorJSONObject(analyticsReportsInfoItem, locale, object)
		).put(
			"canonicalURL", canonicalURL
		).put(
			"endpoints",
			_getEndpointsJSONObject(
				analyticsReportsInfoItem, canonicalURL, urlLocale,
				resourceRequest, resourceResponse)
		).put(
			"hideAnalyticsReportsPanelURL",
			PortletURLBuilder.createActionURL(
				resourceResponse
			).setActionName(
				"/analytics_reports/hide_panel"
			).setRedirect(
				() -> {
					String redirect = ParamUtil.getString(
						resourceRequest, "redirect");

					if (Validator.isNotNull(redirect)) {
						return redirect;
					}

					return layoutFriendlyURL;
				}
			).buildString()
		).put(
			"languageTag", locale.toLanguageTag()
		).put(
			"namespace",
			_portal.getPortletNamespace(
				AnalyticsReportsPortletKeys.ANALYTICS_REPORTS)
		).put(
			"page", JSONUtil.put("plid", layout.getPlid())
		).put(
			"pathToAssets", _portal.getPathContext(resourceRequest)
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
			"title", analyticsReportsInfoItem.getTitle(object, urlLocale)
		).put(
			"viewURLs",
			_getViewURLsJSONArray(
				analyticsReportsInfoItem, infoItemReference, locale, object,
				resourceRequest, resourceResponse, urlLocale)
		);
	}

	private Locale _getLocale(
		HttpServletRequest httpServletRequest, String languageId) {

		return LocaleUtil.fromLanguageId(
			ParamUtil.getString(httpServletRequest, "languageId", languageId));
	}

	private ResourceURL _getResourceURL(
		InfoItemReference infoItemReference, Locale locale,
		ResourceRequest resourceRequest, ResourceResponse resourceResponse,
		String resourceID) {

		LiferayPortletRequest liferayPortletRequest =
			_portal.getLiferayPortletRequest(resourceRequest);

		ResourceURL resourceURL =
			(ResourceURL)PortletURLBuilder.createLiferayPortletURL(
				_portal.getLiferayPortletResponse(resourceResponse),
				liferayPortletRequest.getPlid(),
				liferayPortletRequest.getPortletName(),
				PortletRequest.RESOURCE_PHASE, MimeResponse.Copy.PUBLIC
			).setParameter(
				"className", infoItemReference.getClassName()
			).setParameter(
				"languageId", LocaleUtil.toLanguageId(locale)
			).buildPortletURL();

		if (infoItemReference.getInfoItemIdentifier() instanceof
				ClassNameClassPKInfoItemIdentifier) {

			ClassNameClassPKInfoItemIdentifier
				classNameClassPKInfoItemIdentifier =
					(ClassNameClassPKInfoItemIdentifier)
						infoItemReference.getInfoItemIdentifier();

			resourceURL.setParameter(
				"classPK",
				String.valueOf(
					classNameClassPKInfoItemIdentifier.getClassPK()));
			resourceURL.setParameter(
				"classTypeName",
				classNameClassPKInfoItemIdentifier.getClassName());
		}
		else if (infoItemReference.getInfoItemIdentifier() instanceof
					ClassPKInfoItemIdentifier) {

			ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
				(ClassPKInfoItemIdentifier)
					infoItemReference.getInfoItemIdentifier();

			resourceURL.setParameter(
				"classPK",
				String.valueOf(classPKInfoItemIdentifier.getClassPK()));
		}

		resourceURL.setResourceID(resourceID);

		return resourceURL;
	}

	private ResourceURL _getResourceURL(
		String canonicalURL, Locale locale, ResourceRequest resourceRequest,
		ResourceResponse resourceResponse, String resourceID) {

		LiferayPortletRequest liferayPortletRequest =
			_portal.getLiferayPortletRequest(resourceRequest);

		ResourceURL resourceURL =
			(ResourceURL)PortletURLBuilder.createLiferayPortletURL(
				_portal.getLiferayPortletResponse(resourceResponse),
				liferayPortletRequest.getPlid(),
				liferayPortletRequest.getPortletName(),
				PortletRequest.RESOURCE_PHASE, MimeResponse.Copy.PUBLIC
			).setParameter(
				"canonicalURL", canonicalURL
			).setParameter(
				"languageId", LocaleUtil.toLanguageId(locale)
			).buildPortletURL();

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
		InfoItemReference infoItemReference, Locale locale, Object object,
		ResourceRequest resourceRequest, ResourceResponse resourceResponse,
		Locale urlLocale) {

		List<Locale> locales = analyticsReportsInfoItem.getAvailableLocales(
			object);

		Stream<Locale> stream = locales.stream();

		return JSONUtil.putAll(
			stream.sorted(
				(locale1, locale2) -> {
					if (Objects.equals(
							locale1,
							analyticsReportsInfoItem.getDefaultLocale(
								object))) {

						return -1;
					}

					if (Objects.equals(
							locale2,
							analyticsReportsInfoItem.getDefaultLocale(
								object))) {

						return 1;
					}

					String displayLanguage1 = locale1.getDisplayLanguage(
						locale);
					String displayLanguage2 = locale2.getDisplayLanguage(
						locale);

					return displayLanguage1.compareToIgnoreCase(
						displayLanguage2);
				}
			).map(
				currentLocale -> JSONUtil.put(
					"default",
					Objects.equals(
						currentLocale,
						analyticsReportsInfoItem.getDefaultLocale(object))
				).put(
					"languageId", LocaleUtil.toW3cLanguageId(currentLocale)
				).put(
					"languageLabel",
					StringBundler.concat(
						currentLocale.getDisplayLanguage(locale),
						StringPool.SPACE, StringPool.OPEN_PARENTHESIS,
						currentLocale.getDisplayCountry(locale),
						StringPool.CLOSE_PARENTHESIS)
				).put(
					"selected", Objects.equals(currentLocale, urlLocale)
				).put(
					"viewURL",
					_getResourceURL(
						infoItemReference, currentLocale, resourceRequest,
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

	private static final Map
		<AnalyticsReportsInfoItem.Action, ObjectValuePair<String, String>>
			_objectValuePairs =
				HashMapBuilder.
					<AnalyticsReportsInfoItem.Action,
					 ObjectValuePair<String, String>>put(
						AnalyticsReportsInfoItem.Action.HISTORICAL_READS,
						new ObjectValuePair<>(
							"analyticsReportsHistoricalReadsURL",
							"/analytics_reports/get_historical_reads")
					).put(
						AnalyticsReportsInfoItem.Action.HISTORICAL_VIEWS,
						new ObjectValuePair<>(
							"analyticsReportsHistoricalViewsURL",
							"/analytics_reports/get_historical_views")
					).put(
						AnalyticsReportsInfoItem.Action.TOTAL_READS,
						new ObjectValuePair<>(
							"analyticsReportsTotalReadsURL",
							"/analytics_reports/get_total_reads")
					).put(
						AnalyticsReportsInfoItem.Action.TOTAL_VIEWS,
						new ObjectValuePair<>(
							"analyticsReportsTotalViewsURL",
							"/analytics_reports/get_total_views")
					).put(
						AnalyticsReportsInfoItem.Action.TRAFFIC_CHANNELS,
						new ObjectValuePair<>(
							"analyticsReportsTrafficSourcesURL",
							"/analytics_reports/get_traffic_sources")
					).build();

	@Reference
	private AnalyticsReportsInfoItemObjectProvider
		_analyticsReportsInfoItemObjectProvider;

	@Reference
	private AnalyticsReportsInfoItemObjectProviderTracker
		_analyticsReportsInfoItemObjectProviderTracker;

	@Reference
	private AnalyticsReportsInfoItemTracker _analyticsReportsInfoItemTracker;

	@Reference
	private Http _http;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}