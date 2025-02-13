/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.internal.exportimport.data.handler;

import com.liferay.batch.engine.BatchEngineExportTaskExecutor;
import com.liferay.batch.engine.service.BatchEngineExportTaskService;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.Serializable;

import java.text.DateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Petteri Karttunen
 */
public class VulcanBatchEnginePortletDataHandlerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_vulcanBatchEnginePortletDataHandler =
			new VulcanBatchEnginePortletDataHandler(
				_batchEngineExportTaskExecutor, _batchEngineExportTaskService,
				null, null, RandomTestUtil.randomString(),
				RandomTestUtil.randomString());
	}

	@Test
	public void testBuildParameterMapWithEndDateOnly() throws Exception {
		Date endDate = _getDate(0);

		Map<String, Serializable> parameterMap =
			_vulcanBatchEnginePortletDataHandler.buildParameterMap(
				_mockPortletDataContext(endDate, null));

		Assert.assertEquals(
			"dateModified le " + _dateFormat.format(endDate),
			parameterMap.get("filter"));
	}

	@Test
	public void testBuildParameterMapWithNoDates() throws Exception {
		Map<String, Serializable> parameterMap =
			_vulcanBatchEnginePortletDataHandler.buildParameterMap(
				_mockPortletDataContext(null, null));

		Assert.assertNull(parameterMap.get("filter"));
	}

	@Test
	public void testBuildParameterMapWithStartAndEndDate() throws Exception {
		Date endDate = _getDate(0);
		Date startDate = _getDate(-1);

		Map<String, Serializable> parameterMap =
			_vulcanBatchEnginePortletDataHandler.buildParameterMap(
				_mockPortletDataContext(endDate, startDate));

		Assert.assertEquals(
			StringBundler.concat(
				"dateModified le ", _dateFormat.format(endDate),
				" and dateModified ge ", _dateFormat.format(startDate)),
			parameterMap.get("filter"));
	}

	@Test
	public void testBuildParameterMapWithStartDateOnly() throws Exception {
		Date startDate = _getDate(-1);

		Map<String, Serializable> parameterMap =
			_vulcanBatchEnginePortletDataHandler.buildParameterMap(
				_mockPortletDataContext(null, startDate));

		Assert.assertEquals(
			"dateModified ge " + _dateFormat.format(startDate),
			parameterMap.get("filter"));
	}

	private Date _getDate(int addDays) {
		Calendar calendar = Calendar.getInstance();

		calendar.add(Calendar.DATE, addDays);

		return calendar.getTime();
	}

	private PortletDataContext _mockPortletDataContext(
		Date endDate, Date startDate) {

		PortletDataContext portletDataContext = Mockito.mock(
			PortletDataContext.class);

		Mockito.when(
			portletDataContext.getEndDate()
		).thenReturn(
			endDate
		);

		Mockito.when(
			portletDataContext.getStartDate()
		).thenReturn(
			startDate
		);

		return portletDataContext;
	}

	private final BatchEngineExportTaskExecutor _batchEngineExportTaskExecutor =
		Mockito.mock(BatchEngineExportTaskExecutor.class);
	private final BatchEngineExportTaskService _batchEngineExportTaskService =
		Mockito.mock(BatchEngineExportTaskService.class);
	private final DateFormat _dateFormat =
		DateFormatFactoryUtil.getSimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
	private VulcanBatchEnginePortletDataHandler
		_vulcanBatchEnginePortletDataHandler;

}