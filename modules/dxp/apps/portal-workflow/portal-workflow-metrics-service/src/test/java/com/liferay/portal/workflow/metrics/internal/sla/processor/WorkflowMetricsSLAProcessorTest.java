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

package com.liferay.portal.workflow.metrics.internal.sla.processor;

import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.document.DocumentBuilder;
import com.liferay.portal.search.internal.document.DocumentBuilderImpl;
import com.liferay.portal.util.PropsImpl;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinition;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Rafael Praxedes
 */
@RunWith(PowerMockRunner.class)
public class WorkflowMetricsSLAProcessorTest extends PowerMockito {

	@BeforeClass
	public static void setUpClass() throws Exception {
		PropsUtil.setProps(new PropsImpl());
	}

	@Test
	public void testProcessOnTimeInstance() {
		LocalDateTime localDateTime = _createLocalDateTime();

		_test(
			5000, 5000, localDateTime, true, 0,
			_createDocument(
				new HashMap<String, LocalDateTime>() {
					{
						put(
							"createDate",
							localDateTime.minus(5, ChronoUnit.SECONDS));
					}
				}));
	}

	@Test
	public void testProcessOnTimeInstanceWithParallelTasks() {
		LocalDateTime localDateTime = _createLocalDateTime();

		_test(
			10000, 10000, localDateTime, true, 0,
			_createDocument(
				new HashMap<String, LocalDateTime>() {
					{
						put(
							"completionDate",
							localDateTime.minus(4, ChronoUnit.SECONDS));
						put(
							"createDate",
							localDateTime.minus(10, ChronoUnit.SECONDS));
					}
				}),
			_createDocument(
				new HashMap<String, LocalDateTime>() {
					{
						put(
							"createDate",
							localDateTime.minus(5, ChronoUnit.SECONDS));
					}
				}));
	}

	@Test
	public void testProcessOverdueInstance() {
		LocalDateTime localDateTime = _createLocalDateTime();

		_test(
			5000, 6000, localDateTime, false, -1000,
			_createDocument(
				new HashMap<String, LocalDateTime>() {
					{
						put(
							"createDate",
							localDateTime.minus(6, ChronoUnit.SECONDS));
					}
				}));
	}

	@Test
	public void testProcessOverdueInstanceWithParallelTasks() {
		LocalDateTime localDateTime = _createLocalDateTime();

		_test(
			5000, 10000, localDateTime, false, -5000,
			_createDocument(
				new HashMap<String, LocalDateTime>() {
					{
						put(
							"completionDate",
							localDateTime.minus(4, ChronoUnit.SECONDS));
						put(
							"createDate",
							localDateTime.minus(10, ChronoUnit.SECONDS));
					}
				}),
			_createDocument(
				new HashMap<String, LocalDateTime>() {
					{
						put(
							"createDate",
							localDateTime.minus(5, ChronoUnit.SECONDS));
					}
				}));
	}

	private Document _createDocument(Map<String, LocalDateTime> values) {
		DocumentBuilder documentBuilder = new DocumentBuilderImpl();

		for (Entry<String, LocalDateTime> entry : values.entrySet()) {
			documentBuilder.setValue(
				entry.getKey(), _dateTimeFormatter.format(entry.getValue()));
		}

		return documentBuilder.build();
	}

	private LocalDateTime _createLocalDateTime() {
		LocalDateTime localDateTime = LocalDateTime.now();

		return localDateTime.withNano(0);
	}

	private void _test(
		long duration, long elapsedTime, LocalDateTime localDateTime,
		boolean onTime, long remainingTime, Document... documents) {

		WorkflowMetricsSLAProcessor workflowMetricsSLAProcessor =
			new WorkflowMetricsSLAProcessor() {

				@Override
				protected List<Document> getDocuments(
					long companyId, long instanceId) {

					return Arrays.asList(documents);
				}

			};

		WorkflowMetricsSLADefinition workflowMetricsSLADefinition = mock(
			WorkflowMetricsSLADefinition.class);

		when(
			workflowMetricsSLADefinition.getDuration()
		).thenReturn(
			duration
		);

		WorkflowMetricsSLAProcessResult workflowMetricsSLAProcessResult =
			workflowMetricsSLAProcessor.process(
				0, 0, localDateTime, workflowMetricsSLADefinition);

		Assert.assertEquals(
			elapsedTime, workflowMetricsSLAProcessResult.getElapsedTime());
		Assert.assertEquals(
			remainingTime, workflowMetricsSLAProcessResult.getRemainingTime());
		Assert.assertEquals(workflowMetricsSLAProcessResult.isOnTime(), onTime);
	}

	private final DateTimeFormatter _dateTimeFormatter =
		DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

}