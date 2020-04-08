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
import com.liferay.analytics.reports.web.internal.data.provider.AnalyticsReportsDataProvider;
import com.liferay.analytics.reports.web.internal.model.TrafficSource;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.FastDateFormatFactoryImpl;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author David Arques
 */
@RunWith(MockitoJUnitRunner.class)
public class AnalyticsReportsDisplayContextTest {

	@BeforeClass
	public static void setUpClass() {
		FastDateFormatFactoryUtil fastDateFormatFactoryUtil =
			new FastDateFormatFactoryUtil();

		fastDateFormatFactoryUtil.setFastDateFormatFactory(
			new FastDateFormatFactoryImpl());

		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
	}

	@Test
	public void testGetPropsWithInvalidAnalyticsConnection() {
		int organicTrafficAmount = RandomTestUtil.randomInt();
		double organicTrafficShare = RandomTestUtil.randomDouble();

		int paidTrafficAmount = RandomTestUtil.randomInt();
		double paidTrafficShare = RandomTestUtil.randomDouble();

		AnalyticsReportsDataProvider analyticsReportsDataProvider =
			_getAnalyticsReportsDataProvider(
				organicTrafficAmount, organicTrafficShare, paidTrafficAmount,
				paidTrafficShare, false);

		AnalyticsReportsInfoItem analyticsReportsInfoItem =
			_getAnalyticsReportsItem();

		Layout layout = _getLayout();

		AnalyticsReportsDisplayContext analyticsReportsDisplayContext =
			new AnalyticsReportsDisplayContext(
				analyticsReportsDataProvider, analyticsReportsInfoItem, null,
				null, null, null, _getResourceBundle(),
				_getThemeDisplay(layout));

		Map<String, Object> props = analyticsReportsDisplayContext.getProps();

		Assert.assertEquals(
			analyticsReportsInfoItem.getAuthorName(null),
			props.get("authorName"));

		Assert.assertEquals(layout.getPublishDate(), props.get("publishDate"));

		Assert.assertEquals(
			analyticsReportsInfoItem.getTitle(null, LocaleUtil.US),
			props.get("title"));

		JSONArray trafficSourcesJSONArray = (JSONArray)props.get(
			"trafficSources");

		Assert.assertEquals(
			JSONUtil.putAll(
				JSONUtil.put(
					"helpMessage", _titles.get(_MESSAGE_KEY_HELP)
				).put(
					"name", _TITLE_KEY_PAID
				).put(
					"title", _titles.get(_TITLE_KEY_PAID)
				),
				JSONUtil.put(
					"helpMessage", _titles.get(_MESSAGE_KEY_HELP)
				).put(
					"name", _TITLE_KEY_ORGANIC
				).put(
					"title", _titles.get(_TITLE_KEY_ORGANIC)
				)
			).toJSONString(),
			trafficSourcesJSONArray.toJSONString());
	}

	@Test
	public void testGetPropsWithValidAnalyticsConnection() {
		int organicTrafficAmount = RandomTestUtil.randomInt();
		double organicTrafficShare = RandomTestUtil.randomDouble();

		int paidTrafficAmount = RandomTestUtil.randomInt();
		double paidTrafficShare = RandomTestUtil.randomDouble();

		AnalyticsReportsDataProvider analyticsReportsDataProvider =
			_getAnalyticsReportsDataProvider(
				organicTrafficAmount, organicTrafficShare, paidTrafficAmount,
				paidTrafficShare, true);

		AnalyticsReportsInfoItem analyticsReportsInfoItem =
			_getAnalyticsReportsItem();

		Layout layout = _getLayout();

		AnalyticsReportsDisplayContext analyticsReportsDisplayContext =
			new AnalyticsReportsDisplayContext(
				analyticsReportsDataProvider, analyticsReportsInfoItem, null,
				null, null, null, _getResourceBundle(),
				_getThemeDisplay(layout));

		Map<String, Object> props = analyticsReportsDisplayContext.getProps();

		Assert.assertEquals(
			analyticsReportsInfoItem.getAuthorName(null),
			props.get("authorName"));

		Assert.assertEquals(layout.getPublishDate(), props.get("publishDate"));

		Assert.assertEquals(
			analyticsReportsInfoItem.getTitle(null, LocaleUtil.US),
			props.get("title"));

		JSONArray trafficSourcesJSONArray = (JSONArray)props.get(
			"trafficSources");

		Assert.assertEquals(
			JSONUtil.putAll(
				JSONUtil.put(
					"helpMessage", _titles.get(_MESSAGE_KEY_HELP)
				).put(
					"name", _TITLE_KEY_PAID
				).put(
					"share", paidTrafficShare
				).put(
					"title", _titles.get(_TITLE_KEY_PAID)
				).put(
					"value", paidTrafficAmount
				),
				JSONUtil.put(
					"helpMessage", _titles.get(_MESSAGE_KEY_HELP)
				).put(
					"name", _TITLE_KEY_ORGANIC
				).put(
					"share", organicTrafficShare
				).put(
					"title", _titles.get(_TITLE_KEY_ORGANIC)
				).put(
					"value", organicTrafficAmount
				)
			).toJSONString(),
			trafficSourcesJSONArray.toJSONString());
	}

	private AnalyticsReportsDataProvider _getAnalyticsReportsDataProvider(
		int organicTrafficAmount, double organicTrafficShare,
		int paidTrafficAmount, double paidTrafficShare,
		boolean validAnalyticsConnection) {

		return new AnalyticsReportsDataProvider() {

			@Override
			public List<TrafficSource> getTrafficSources(
				long companyId, String url) {

				return Arrays.asList(
					new TrafficSource(
						_TITLE_KEY_ORGANIC, organicTrafficAmount,
						organicTrafficShare),
					new TrafficSource(
						_TITLE_KEY_PAID, paidTrafficAmount, paidTrafficShare));
			}

			@Override
			public boolean isValidAnalyticsConnection(long companyId) {
				return validAnalyticsConnection;
			}

		};
	}

	private AnalyticsReportsInfoItem _getAnalyticsReportsItem() {
		String authorName = StringUtil.randomString();
		Date publishDate = RandomTestUtil.nextDate();
		String title = StringUtil.randomString();

		return new AnalyticsReportsInfoItem() {

			@Override
			public String getAuthorName(Object model) {
				return authorName;
			}

			@Override
			public Date getPublishDate(Object model) {
				return publishDate;
			}

			@Override
			public String getTitle(Object model, Locale locale) {
				return title;
			}

		};
	}

	private Layout _getLayout() {
		Layout layout = Mockito.mock(Layout.class);

		Date date = RandomTestUtil.nextDate();

		Mockito.doReturn(
			date
		).when(
			layout
		).getPublishDate();

		return layout;
	}

	private ResourceBundle _getResourceBundle() {
		return new ResourceBundle() {

			@Override
			public Enumeration<String> getKeys() {
				return Collections.enumeration(
					Arrays.asList(
						_TITLE_KEY_ORGANIC, _TITLE_KEY_PAID,
						_MESSAGE_KEY_HELP));
			}

			@Override
			protected Object handleGetObject(String key) {
				return _titles.getOrDefault(key, key);
			}

		};
	}

	private ThemeDisplay _getThemeDisplay(Layout layout) {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		Company company = Mockito.mock(Company.class);

		try {
			themeDisplay.setCompany(company);
		}
		catch (PortalException portalException) {
			throw new AssertionError("Error found ", portalException);
		}

		themeDisplay.setLayout(layout);

		themeDisplay.setLocale(LocaleUtil.US);

		return themeDisplay;
	}

	private static final String _MESSAGE_KEY_HELP =
		"this-number-refers-to-the-volume-of-people-that-find-your-page-" +
			"through-a-search-engine";

	private static final String _TITLE_KEY_ORGANIC = "organic";

	private static final String _TITLE_KEY_PAID = "paid";

	private final Map<String, String> _titles = HashMapBuilder.put(
		_MESSAGE_KEY_HELP, "helpMessage"
	).put(
		_TITLE_KEY_ORGANIC, "organic"
	).put(
		_TITLE_KEY_PAID, "paid"
	).build();

}