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

package com.liferay.osb.faro.web.internal.controller.api;

import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Alejo Ceballos
 */
public class ReportControllerResponseFactoryTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testBuildLastExportResultedAnError() {
		Map<String, Object> responseMap = HashMapBuilder.<String, Object>put(
			"createdDate", "2022-05-01'T'12:00:00.000'Z'"
		).put(
			"fromDate", "2022-04-01'T'12:00:00.000'Z'"
		).put(
			"previousStatus", "ERROR"
		).put(
			"status", "PENDING"
		).put(
			"toDate", "2022-04-30'T'12:00:00.000'Z'"
		).put(
			"type", "PAGE"
		).build();

		Response response = _reportControllerResponseFactory.create(
			responseMap, Response.Status.OK);

		Map<String, Object> expectedMap = new HashMap<>(responseMap);

		expectedMap.remove("previousStatus");

		expectedMap.put(
			"message",
			"The last data export for this date range and type failed. A new " +
				"data export file will be created. Please come back later.");

		AssertUtils.assertEquals(
			expectedMap, (Map<String, Object>)response.getEntity());
	}

	@Test
	public void testBuildNoPreviousExportProcess() {
		Map<String, Object> responseMap = HashMapBuilder.<String, Object>put(
			"createdDate", "2022-05-01'T'12:00:00.000'Z'"
		).put(
			"fromDate", "2022-04-01'T'12:00:00.000'Z'"
		).put(
			"status", "PENDING"
		).put(
			"toDate", "2022-04-30'T'12:00:00.000'Z'"
		).put(
			"type", "PAGE"
		).build();

		Response response = _reportControllerResponseFactory.create(
			responseMap, Response.Status.OK);

		AssertUtils.assertEquals(
			HashMapBuilder.putAll(
				responseMap
			).put(
				"message",
				"A new data export file for this date range and type will be " +
					"created. Please come back later."
			).build(),
			(Map<String, Object>)response.getEntity());
	}

	@Test
	public void testBuildResponseStatusNotOk() {
		Map<String, Object> responseMap = HashMapBuilder.<String, Object>put(
			"message", "Some error message"
		).build();

		Response response = _reportControllerResponseFactory.create(
			responseMap, Response.Status.BAD_REQUEST);

		AssertUtils.assertEquals(
			HashMapBuilder.putAll(
				responseMap
			).put(
				"status", "ERROR"
			).build(),
			(Map<String, Object>)response.getEntity());
	}

	@Test
	public void testBuildSameExportAlreadyRunning() {
		Map<String, Object> responseMap = HashMapBuilder.<String, Object>put(
			"createdDate", "2022-05-01'T'12:00:00.000'Z'"
		).put(
			"fromDate", "2022-04-01'T'12:00:00.000'Z'"
		).put(
			"startedDate", "2022-05-02'T'12:00:00.000'Z'"
		).put(
			"status", "RUNNING"
		).put(
			"toDate", "2022-04-30'T'12:00:00.000'Z'"
		).put(
			"type", "PAGE"
		).build();

		Response response = _reportControllerResponseFactory.create(
			responseMap, Response.Status.OK);

		AssertUtils.assertEquals(
			HashMapBuilder.putAll(
				responseMap
			).put(
				"message",
				"The data export file for this date range and type is being " +
					"created. Please come back later."
			).build(),
			(Map<String, Object>)response.getEntity());
	}

	@Test
	public void testBuildSameExportAlreadyScheduled() {
		Map<String, Object> responseMap = HashMapBuilder.<String, Object>put(
			"createdDate", "2022-05-01'T'12:00:00.000'Z'"
		).put(
			"fromDate", "2022-04-01'T'12:00:00.000'Z'"
		).put(
			"previousStatus", "PENDING"
		).put(
			"status", "PENDING"
		).put(
			"toDate", "2022-04-30'T'12:00:00.000'Z'"
		).put(
			"type", "PAGE"
		).build();

		Response response = _reportControllerResponseFactory.create(
			responseMap, Response.Status.OK);

		Map<String, Object> expectedMap = new HashMap<>(responseMap);

		expectedMap.remove("previousStatus");

		expectedMap.put(
			"message",
			"A data export for this date range and type has already been " +
				"scheduled. Please come back later.");

		AssertUtils.assertEquals(
			expectedMap, (Map<String, Object>)response.getEntity());
	}

	private static final ReportControllerResponseFactory
		_reportControllerResponseFactory =
			new ReportControllerResponseFactory();

}