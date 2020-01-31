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
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.FastDateFormatFactoryImpl;

import java.text.Format;

import java.util.Date;
import java.util.Locale;
import java.util.Map;

import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author David Arques
 */
@RunWith(MockitoJUnitRunner.class)
public class AnalyticsReportsDisplayContextTest {

	@Before
	public void setUp() {
		FastDateFormatFactoryUtil fastDateFormatFactoryUtil =
			new FastDateFormatFactoryUtil();

		fastDateFormatFactoryUtil.setFastDateFormatFactory(
			new FastDateFormatFactoryImpl());
	}

	@Test
	public void testGetProps() {
		String authorName = RandomTestUtil.randomString();
		Locale locale = LocaleUtil.getDefault();
		String title = RandomTestUtil.randomString();

		AnalyticsReportsInfoItem analyticsReportsInfoItem =
			_getAnalyticsReportsItem(
				authorName, locale, RandomTestUtil.nextDate(), title);

		Date layoutPublishDate = RandomTestUtil.nextDate();

		HttpServletRequest httpServletRequest = _getHttpServletRequest(
			locale, layoutPublishDate);

		AnalyticsReportsDisplayContext analyticsReportsDisplayContext =
			new AnalyticsReportsDisplayContext(
				analyticsReportsInfoItem, Mockito.mock(Object.class),
				httpServletRequest, Mockito.mock(Portal.class),
				Mockito.mock(RenderResponse.class));

		Map<String, Object> props = analyticsReportsDisplayContext.getProps();

		Assert.assertEquals(authorName, props.get("authorName"));

		Format simpleDateFormat = FastDateFormatFactoryUtil.getSimpleDateFormat(
			"MMMM dd, yyyy", locale);

		Assert.assertEquals(
			simpleDateFormat.format(layoutPublishDate),
			props.get("publishDate"));

		Assert.assertEquals(title, props.get("title"));
	}

	private AnalyticsReportsInfoItem _getAnalyticsReportsItem(
		String authorName, Locale locale, Date publishDate, String title) {

		AnalyticsReportsInfoItem analyticsReportsInfoItem = Mockito.mock(
			AnalyticsReportsInfoItem.class);

		Mockito.doReturn(
			authorName
		).when(
			analyticsReportsInfoItem
		).getAuthorName(
			Mockito.any()
		);

		Mockito.doReturn(
			publishDate
		).when(
			analyticsReportsInfoItem
		).getPublishDate(
			Mockito.any()
		);

		Mockito.doReturn(
			title
		).when(
			analyticsReportsInfoItem
		).getTitle(
			Mockito.any(), Mockito.eq(locale)
		);

		return analyticsReportsInfoItem;
	}

	private HttpServletRequest _getHttpServletRequest(
		Locale locale, Date publishDate) {

		HttpServletRequest httpServletRequest = Mockito.mock(
			HttpServletRequest.class);

		Mockito.doReturn(
			_getThemeDisplay(locale, publishDate)
		).when(
			httpServletRequest
		).getAttribute(
			WebKeys.THEME_DISPLAY
		);

		return httpServletRequest;
	}

	private ThemeDisplay _getThemeDisplay(Locale locale, Date publishDate) {
		ThemeDisplay themeDisplay = Mockito.mock(ThemeDisplay.class);

		Mockito.doReturn(
			locale
		).when(
			themeDisplay
		).getLocale();

		Layout layout = Mockito.mock(Layout.class);

		Mockito.doReturn(
			publishDate
		).when(
			layout
		).getPublishDate();

		Mockito.doReturn(
			layout
		).when(
			themeDisplay
		).getLayout();

		return themeDisplay;
	}

}