/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.filter;

import com.liferay.frontend.data.set.filter.BaseDateTimeRangeFDSFilter;
import com.liferay.frontend.data.set.filter.DateTimeFDSFilterItem;
import com.liferay.frontend.data.set.filter.FDSFilter;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Mikel Lorza
 */
public class DateTimeRangeFDSFilterContextContributorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
	}

	@Test
	public void testGetFDSFilterContextReturnsEmptyMapForUnrelatedFilter() {
		Assert.assertEquals(
			Collections.emptyMap(),
			_dateTimeRangeFDSFilterContextContributor.getFDSFilterContext(
				new UnrelatedFDSFilter(), LocaleUtil.US));
	}

	@Test
	public void testGetFDSFilterContextSerializesMinAndMax() {
		Map<String, Object> fdsFilterContext =
			_dateTimeRangeFDSFilterContextContributor.getFDSFilterContext(
				new TestDateTimeRangeFDSFilter(
					new DateTimeFDSFilterItem(1, 1, 2026, 0, 0),
					new DateTimeFDSFilterItem(31, 12, 2026, 23, 59)),
				LocaleUtil.US);

		Assert.assertTrue(fdsFilterContext.containsKey("min"));
		Assert.assertTrue(fdsFilterContext.containsKey("max"));

		JSONObject minJSONObject = (JSONObject)fdsFilterContext.get("min");

		Assert.assertEquals(1, minJSONObject.getInt("day"));
		Assert.assertEquals(1, minJSONObject.getInt("month"));
		Assert.assertEquals(2026, minJSONObject.getInt("year"));
		Assert.assertEquals(0, minJSONObject.getInt("hour"));
		Assert.assertEquals(0, minJSONObject.getInt("minute"));

		JSONObject maxJSONObject = (JSONObject)fdsFilterContext.get("max");

		Assert.assertEquals(31, maxJSONObject.getInt("day"));
		Assert.assertEquals(12, maxJSONObject.getInt("month"));
		Assert.assertEquals(2026, maxJSONObject.getInt("year"));
		Assert.assertEquals(23, maxJSONObject.getInt("hour"));
		Assert.assertEquals(59, maxJSONObject.getInt("minute"));
	}

	@Test
	public void testGetFDSFilterContextSerializesNowAsString() {
		Map<String, Object> fdsFilterContext =
			_dateTimeRangeFDSFilterContextContributor.getFDSFilterContext(
				new TestDateTimeRangeFDSFilter(
					new DateTimeFDSFilterItem(1, 1, 2026, 0, 0),
					DateTimeFDSFilterItem.NOW),
				LocaleUtil.US);

		Assert.assertEquals("now", fdsFilterContext.get("max"));
	}

	private final DateTimeRangeFDSFilterContextContributor
		_dateTimeRangeFDSFilterContextContributor =
			new DateTimeRangeFDSFilterContextContributor();

	private static class TestDateTimeRangeFDSFilter
		extends BaseDateTimeRangeFDSFilter {

		public TestDateTimeRangeFDSFilter(
			DateTimeFDSFilterItem minDateTimeFDSFilterItem,
			DateTimeFDSFilterItem maxDateTimeFDSFilterItem) {

			_minDateTimeFDSFilterItem = minDateTimeFDSFilterItem;
			_maxDateTimeFDSFilterItem = maxDateTimeFDSFilterItem;
		}

		@Override
		public String getId() {
			return "testField";
		}

		@Override
		public String getLabel() {
			return "Test";
		}

		@Override
		public DateTimeFDSFilterItem getMaxDateTimeFDSFilterItem() {
			return _maxDateTimeFDSFilterItem;
		}

		@Override
		public DateTimeFDSFilterItem getMinDateTimeFDSFilterItem() {
			return _minDateTimeFDSFilterItem;
		}

		@Override
		public Map<String, Object> getPreloadedData() {
			return null;
		}

		private final DateTimeFDSFilterItem _maxDateTimeFDSFilterItem;
		private final DateTimeFDSFilterItem _minDateTimeFDSFilterItem;

	}

	private static class UnrelatedFDSFilter implements FDSFilter {

		@Override
		public String getEntityFieldType() {
			return "string";
		}

		@Override
		public String getId() {
			return "unrelated";
		}

		@Override
		public String getLabel() {
			return "Unrelated";
		}

		@Override
		public Map<String, Object> getPreloadedData() {
			return null;
		}

		@Override
		public String getType() {
			return "selection";
		}

	}

}