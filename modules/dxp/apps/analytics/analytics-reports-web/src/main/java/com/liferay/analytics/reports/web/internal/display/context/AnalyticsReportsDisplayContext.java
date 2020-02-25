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

package com.liferay.analytics.reports.web.internal.display.context;

import com.liferay.analytics.reports.info.item.AnalyticsReportsInfoItem;
import com.liferay.analytics.reports.web.internal.constants.AnalyticsReportsPortletKeys;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Stream;

import javax.portlet.RenderResponse;
import javax.portlet.ResourceURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author David Arques
 * @author Sarai Díaz
 */
public class AnalyticsReportsDisplayContext {

	public AnalyticsReportsDisplayContext(
		AnalyticsReportsInfoItem analyticsReportsInfoItem,
		Object analyticsReportsInfoItemObject,
		HttpServletRequest httpServletRequest, Portal portal,
		RenderResponse renderResponse) {

		_analyticsReportsInfoItem = analyticsReportsInfoItem;
		_analyticsReportsInfoItemObject = analyticsReportsInfoItemObject;
		_httpServletRequest = httpServletRequest;
		_portal = portal;
		_renderResponse = renderResponse;

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public Map<String, Object> getData() {
		if (_data != null) {
			return _data;
		}

		_data = HashMapBuilder.<String, Object>put(
			"context",
			HashMapBuilder.<String, Object>put(
				"endpoints",
				HashMapBuilder.<String, Object>put(
					"getAnalyticsReportsHistoricalReadsURL",
					() -> {
						ResourceURL resourceURL =
							_renderResponse.createResourceURL();

						resourceURL.setResourceID(
							"/analytics_reports/get_historical_reads");

						return resourceURL.toString();
					}
				).put(
					"getAnalyticsReportsHistoricalViewsURL",
					() -> {
						ResourceURL resourceURL =
							_renderResponse.createResourceURL();

						resourceURL.setResourceID(
							"/analytics_reports/get_historical_views");

						return resourceURL.toString();
					}
				).put(
					"getAnalyticsReportsTotalReadsURL",
					() -> {
						ResourceURL resourceURL =
							_renderResponse.createResourceURL();

						resourceURL.setResourceID(
							"/analytics_reports/get_total_reads");

						return resourceURL.toString();
					}
				).put(
					"getAnalyticsReportsTotalViewsURL",
					() -> {
						ResourceURL resourceURL =
							_renderResponse.createResourceURL();

						resourceURL.setResourceID(
							"/analytics_reports/get_total_views");

						return resourceURL.toString();
					}
				).build()
			).put(
				"languageTag",
				() -> {
					Locale locale = _themeDisplay.getLocale();

					return locale.toLanguageTag();
				}
			).put(
				"namespace",
				_portal.getPortletNamespace(
					AnalyticsReportsPortletKeys.ANALYTICS_REPORTS)
			).put(
				"page",
				HashMapBuilder.<String, Object>put(
					"plid",
					() -> {
						Layout layout = _themeDisplay.getLayout();

						return layout.getPlid();
					}
				).build()
			).put(
				"timeSpans", _getTimeSpansJSONArray()
			).build()
		).put(
			"props", getProps()
		).build();

		return _data;
	}

	public String getLiferayAnalyticsURL(long companyId) {
		return PrefsPropsUtil.getString(companyId, "liferayAnalyticsURL");
	}

	public enum TimeSpan {

		LAST_7_DAYS("last-7-days", 2), LAST_24_HOURS("last-24-hours", 1),
		LAST_30_DAYS("last-30-days", 3);

		public String getLabel() {
			return _label;
		}

		public int getOrder() {
			return _order;
		}

		private TimeSpan(String label, int order) {
			_label = label;
			_order = order;
		}

		private final String _label;
		private final int _order;

	}

	protected Map<String, Object> getProps() {
		return HashMapBuilder.<String, Object>put(
			"authorName",
			_analyticsReportsInfoItem.getAuthorName(
				_analyticsReportsInfoItemObject)
		).put(
			"publishDate",
			FastDateFormatFactoryUtil.getSimpleDateFormat(
				"MMMM dd, yyyy", _themeDisplay.getLocale()
			).format(
				_getPublishDate()
			)
		).put(
			"title",
			_analyticsReportsInfoItem.getTitle(
				_analyticsReportsInfoItemObject, _themeDisplay.getLocale())
		).build();
	}

	private Date _getPublishDate() {
		Date publishDate = _analyticsReportsInfoItem.getPublishDate(
			_analyticsReportsInfoItemObject);

		Layout layout = _themeDisplay.getLayout();

		if (DateUtil.compareTo(publishDate, layout.getPublishDate()) > 0) {
			return publishDate;
		}

		return layout.getPublishDate();
	}

	private JSONArray _getTimeSpansJSONArray() {
		JSONArray timeSpansJSONArray = JSONFactoryUtil.createJSONArray();

		Stream<TimeSpan> stream = Arrays.stream(TimeSpan.values());

		stream.sorted(
			Comparator.comparingInt(TimeSpan::getOrder)
		).forEach(
			timeSpan -> timeSpansJSONArray.put(
				JSONUtil.put(
					"label",
					LanguageUtil.get(_httpServletRequest, timeSpan.getLabel())
				).put(
					"value", timeSpan.getLabel()
				))
		);

		return timeSpansJSONArray;
	}

	private final AnalyticsReportsInfoItem _analyticsReportsInfoItem;
	private final Object _analyticsReportsInfoItemObject;
	private Map<String, Object> _data;
	private final HttpServletRequest _httpServletRequest;
	private final Portal _portal;
	private final RenderResponse _renderResponse;
	private final ThemeDisplay _themeDisplay;

}