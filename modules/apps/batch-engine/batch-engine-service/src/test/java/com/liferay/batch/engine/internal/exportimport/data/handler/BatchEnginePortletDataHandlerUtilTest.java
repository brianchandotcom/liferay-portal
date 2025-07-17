/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.internal.exportimport.data.handler;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.Serializable;

import java.text.DateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Petteri Karttunen
 */
public class BatchEnginePortletDataHandlerUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss'Z'");

		_fastDateFormatFactoryUtilMockedStatic = Mockito.mockStatic(
			FastDateFormatFactoryUtil.class);

		Mockito.when(
			FastDateFormatFactoryUtil.getSimpleDateFormat(Mockito.anyString())
		).thenReturn(
			_dateFormat
		);
	}

	@After
	public void tearDown() {
		_fastDateFormatFactoryUtilMockedStatic.close();
	}

	@Test
	public void testBuildExportParametersWithEndDate() {
		Date endDate = _getDate(0);

		Map<String, Serializable> parameters =
			BatchEnginePortletDataHandlerUtil.buildExportParameters(
				null, _mockPortletDataContext(endDate, null, null));

		Assert.assertEquals(
			"dateModified le " + _dateFormat.format(endDate),
			parameters.get("filter"));
	}

	@Test
	public void testBuildExportParametersWithEndDateAndStartDate() {
		Date endDate = _getDate(0);
		Date startDate = _getDate(-1);

		Map<String, Serializable> parameters =
			BatchEnginePortletDataHandlerUtil.buildExportParameters(
				null, _mockPortletDataContext(endDate, null, startDate));

		Assert.assertEquals(
			StringBundler.concat(
				"dateModified le ", _dateFormat.format(endDate),
				" and dateModified ge ", _dateFormat.format(startDate)),
			parameters.get("filter"));
	}

	@Test
	public void testBuildExportParametersWithNestedFields() {
		Map<String, Serializable> parameters =
			BatchEnginePortletDataHandlerUtil.buildExportParameters(
				List.of("nestedField1", "nestedField2"),
				_mockPortletDataContext(null, null, null));

		Assert.assertEquals(
			"nestedField1,nestedField2", parameters.get("batchNestedFields"));

		parameters = BatchEnginePortletDataHandlerUtil.buildExportParameters(
			List.of("nestedField1", "nestedField2"),
			_mockPortletDataContext(
				null,
				HashMapBuilder.put(
					PortletDataHandlerKeys.PERMISSIONS, new String[] {"true"}
				).build(),
				null));

		Assert.assertEquals(
			"permissions,nestedField1,nestedField2",
			parameters.get("batchNestedFields"));
	}

	@Test
	public void testBuildExportParametersWithNoDates() {
		Map<String, Serializable> parameters =
			BatchEnginePortletDataHandlerUtil.buildExportParameters(
				null, _mockPortletDataContext(null, null, null));

		Assert.assertNull(parameters.get("filter"));
	}

	@Test
	public void testBuildExportParametersWithStartDate() {
		Date startDate = _getDate(-1);

		Map<String, Serializable> parameters =
			BatchEnginePortletDataHandlerUtil.buildExportParameters(
				null, _mockPortletDataContext(null, null, startDate));

		Assert.assertEquals(
			"dateModified ge " + _dateFormat.format(startDate),
			parameters.get("filter"));
	}

	private Date _getDate(int days) {
		Calendar calendar = Calendar.getInstance();

		calendar.add(Calendar.DATE, days);

		return calendar.getTime();
	}

	private PortletDataContext _mockPortletDataContext(
		Date endDate, Map<String, String[]> parameterMap, Date startDate) {

		PortletDataContext portletDataContext = Mockito.mock(
			PortletDataContext.class);

		Mockito.when(
			portletDataContext.getEndDate()
		).thenReturn(
			endDate
		);

		Mockito.when(
			portletDataContext.getParameterMap()
		).thenReturn(
			(parameterMap == null) ? new HashMap<>() : parameterMap
		);

		Mockito.when(
			portletDataContext.getStartDate()
		).thenReturn(
			startDate
		);

		return portletDataContext;
	}

	private DateFormat _dateFormat;
	private MockedStatic<FastDateFormatFactoryUtil>
		_fastDateFormatFactoryUtilMockedStatic;

}