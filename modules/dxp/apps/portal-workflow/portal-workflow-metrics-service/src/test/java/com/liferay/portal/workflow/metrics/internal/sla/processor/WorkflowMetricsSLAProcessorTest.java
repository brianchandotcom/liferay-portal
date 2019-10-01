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
import com.liferay.portal.workflow.metrics.internal.sla.calendar.DefaultWorkflowMetricsSLACalendar;
import com.liferay.portal.workflow.metrics.internal.sla.calendar.WorkflowMetricsSLACalendarTrackerImpl;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinitionVersion;
import com.liferay.portal.workflow.metrics.sla.calendar.WorkflowMetricsSLACalendarTracker;
import com.liferay.portal.workflow.metrics.sla.processor.WorkfowMetricsSLAStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
	public void testIsBreached() {
		LocalDateTime nowLocalDateTime = _createLocalDateTime();

		_assertIsBreached(
			false,
			_createDocument(
				new HashMap<String, Object>() {
					{
						put("createDate", _format(nowLocalDateTime));
					}
				}),
			nowLocalDateTime, nowLocalDateTime.plusHours(1));
		_assertIsBreached(
			false,
			_createDocument(
				new HashMap<String, Object>() {
					{
						put(
							"createDate",
							_format(nowLocalDateTime.minusHours(1)));
					}
				}),
			nowLocalDateTime, nowLocalDateTime.minusHours(2));
		_assertIsBreached(
			false,
			_createDocument(
				new HashMap<String, Object>() {
					{
						put(
							"completionDate",
							_format(nowLocalDateTime.minusHours(1)));
						put(
							"createDate",
							_format(nowLocalDateTime.minusHours(2)));
					}
				}),
			nowLocalDateTime, nowLocalDateTime.minusHours(1));
		_assertIsBreached(
			true,
			_createDocument(
				new HashMap<String, Object>() {
					{
						put(
							"createDate",
							_format(nowLocalDateTime.minusHours(2)));
					}
				}),
			nowLocalDateTime, nowLocalDateTime.minusHours(1));
		_assertIsBreached(
			true,
			_createDocument(
				new HashMap<String, Object>() {
					{
						put("completionDate", _format(nowLocalDateTime));
						put(
							"createDate",
							_format(nowLocalDateTime.minusHours(2)));
					}
				}),
			nowLocalDateTime, nowLocalDateTime.minusHours(1));
	}

	@Test
	public void testIsOnTime() {
		LocalDateTime nowLocalDateTime = _createLocalDateTime();

		_assertIsOnTime(
			true,
			_createDocument(
				new HashMap<String, Object>() {
					{
						put("createDate", _format(nowLocalDateTime));
					}
				}),
			nowLocalDateTime, nowLocalDateTime.plusHours(1));
		_assertIsOnTime(
			true,
			_createDocument(
				new HashMap<String, Object>() {
					{
						put(
							"completionDate",
							_format(nowLocalDateTime.minusHours(1)));
						put(
							"createDate",
							_format(nowLocalDateTime.minusHours(2)));
					}
				}),
			nowLocalDateTime, nowLocalDateTime.minusHours(1));
		_assertIsOnTime(
			false,
			_createDocument(
				new HashMap<String, Object>() {
					{
						put(
							"createDate",
							_format(nowLocalDateTime.minusHours(1)));
					}
				}),
			nowLocalDateTime, nowLocalDateTime.minusHours(2));
		_assertIsOnTime(
			false,
			_createDocument(
				new HashMap<String, Object>() {
					{
						put(
							"createDate",
							_format(nowLocalDateTime.minusHours(2)));
					}
				}),
			nowLocalDateTime, nowLocalDateTime.minusHours(1));
		_assertIsOnTime(
			false,
			_createDocument(
				new HashMap<String, Object>() {
					{
						put("completionDate", _format(nowLocalDateTime));
						put(
							"createDate",
							_format(nowLocalDateTime.minusHours(2)));
					}
				}),
			nowLocalDateTime, nowLocalDateTime.minusHours(1));
	}

	@Test
	public void testProcessOnTimeInstance() throws Exception {
		LocalDateTime localDateTime = _createLocalDateTime();

		LocalDateTime createDateLocalDateTime = localDateTime.minus(
			5, ChronoUnit.SECONDS);

		_test(
			createDateLocalDateTime, 5000, 5000, localDateTime, true, 0,
			WorkfowMetricsSLAStatus.RUNNING,
			_createDocument(
				new HashMap<String, Object>() {
					{
						put("createDate", _format(createDateLocalDateTime));
						put("taskId", 1);
						put("tokenId", 1);
					}
				}));
	}

	@Test
	public void testProcessOnTimeInstanceWithParallelTasks() throws Exception {
		LocalDateTime localDateTime = _createLocalDateTime();

		LocalDateTime createDateLocalDateTime = localDateTime.minus(
			10, ChronoUnit.SECONDS);

		_test(
			createDateLocalDateTime, 10000, 10000, localDateTime, true, 0,
			WorkfowMetricsSLAStatus.RUNNING,
			_createDocument(
				new HashMap<String, Object>() {
					{
						put(
							"completionDate",
							_format(
								localDateTime.minus(4, ChronoUnit.SECONDS)));
						put("createDate", _format(createDateLocalDateTime));
						put("taskId", 1);
						put("tokenId", 1);
					}
				}),
			_createDocument(
				new HashMap<String, Object>() {
					{
						put(
							"createDate",
							_format(
								localDateTime.minus(5, ChronoUnit.SECONDS)));
						put("taskId", 2);
						put("tokenId", 2);
					}
				}));
	}

	@Test
	public void testProcessOnTimeSLANotStarted() throws Exception {
		LocalDateTime localDateTime = _createLocalDateTime();

		LocalDateTime createDateLocalDateTime = localDateTime.minus(
			5, ChronoUnit.SECONDS);

		WorkflowMetricsSLADefinitionVersion
			workflowMetricsSLADefinitionVersion = mock(
				WorkflowMetricsSLADefinitionVersion.class);

		when(
			workflowMetricsSLADefinitionVersion.getStartNodeKeys()
		).thenReturn(
			"1:leave"
		);

		_test(
			createDateLocalDateTime, 0, null, localDateTime, true, 0, 0,
			workflowMetricsSLADefinitionVersion, WorkfowMetricsSLAStatus.NEW,
			_createDocument(
				new HashMap<String, Object>() {
					{
						put("createDate", _format(createDateLocalDateTime));
						put("taskId", 1);
						put("tokenId", 1);
					}
				}));
	}

	@Test
	public void testProcessOnTimeSLAPaused() throws Exception {
		LocalDateTime localDateTime = _createLocalDateTime();

		LocalDateTime createDateLocalDateTime = localDateTime.minus(
			10, ChronoUnit.SECONDS);

		WorkflowMetricsSLADefinitionVersion
			workflowMetricsSLADefinitionVersion = mock(
				WorkflowMetricsSLADefinitionVersion.class);

		when(
			workflowMetricsSLADefinitionVersion.getDuration()
		).thenReturn(
			5000L
		);

		when(
			workflowMetricsSLADefinitionVersion.getStartNodeKeys()
		).thenReturn(
			"1:enter"
		);

		when(
			workflowMetricsSLADefinitionVersion.getPauseNodeKeys()
		).thenReturn(
			"2:enter"
		);

		_test(
			createDateLocalDateTime, 5000, null, localDateTime, true, 0, 1,
			workflowMetricsSLADefinitionVersion, WorkfowMetricsSLAStatus.PAUSED,
			_createDocument(
				new HashMap<String, Object>() {
					{
						put(
							"completionDate",
							_format(
								localDateTime.minus(5, ChronoUnit.SECONDS)));
						put("createDate", _format(createDateLocalDateTime));
						put("taskId", 1);
						put("tokenId", 1);
					}
				}),
			_createDocument(
				new HashMap<String, Object>() {
					{
						put(
							"createDate",
							_format(
								localDateTime.minus(5, ChronoUnit.SECONDS)));
						put("taskId", 2);
						put("tokenId", 2);
					}
				}));
	}

	@Test
	public void testProcessOnTimeSLAStopped() throws Exception {
		LocalDateTime localDateTime = _createLocalDateTime();

		LocalDateTime createDateLocalDateTime = localDateTime.minus(
			10, ChronoUnit.SECONDS);

		WorkflowMetricsSLADefinitionVersion
			workflowMetricsSLADefinitionVersion = mock(
				WorkflowMetricsSLADefinitionVersion.class);

		when(
			workflowMetricsSLADefinitionVersion.getDuration()
		).thenReturn(
			10000L
		);

		when(
			workflowMetricsSLADefinitionVersion.getPauseNodeKeys()
		).thenReturn(
			"2"
		);

		when(
			workflowMetricsSLADefinitionVersion.getStartNodeKeys()
		).thenReturn(
			"1:enter"
		);

		when(
			workflowMetricsSLADefinitionVersion.getStopNodeKeys()
		).thenReturn(
			"2:leave"
		);

		_test(
			createDateLocalDateTime, 10000, null, localDateTime, true, 0, 1,
			workflowMetricsSLADefinitionVersion,
			WorkfowMetricsSLAStatus.STOPPED,
			_createDocument(
				new HashMap<String, Object>() {
					{
						put(
							"completionDate",
							_format(
								localDateTime.minus(5, ChronoUnit.SECONDS)));
						put("createDate", _format(createDateLocalDateTime));
						put("taskId", 1);
						put("tokenId", 1);
					}
				}),
			_createDocument(
				new HashMap<String, Object>() {
					{
						put("completionDate", _format(localDateTime));
						put(
							"createDate",
							_format(
								localDateTime.minus(5, ChronoUnit.SECONDS)));
						put("taskId", 2);
						put("tokenId", 2);
					}
				}));
	}

	@Test
	public void testProcessOnTimeSLAStoppedWithSameTask() throws Exception {
		LocalDateTime localDateTime = _createLocalDateTime();

		LocalDateTime createDateLocalDateTime = localDateTime.minus(
			10, ChronoUnit.SECONDS);

		WorkflowMetricsSLADefinitionVersion
			workflowMetricsSLADefinitionVersion = mock(
				WorkflowMetricsSLADefinitionVersion.class);

		when(
			workflowMetricsSLADefinitionVersion.getDuration()
		).thenReturn(
			10000L
		);

		when(
			workflowMetricsSLADefinitionVersion.getStartNodeKeys()
		).thenReturn(
			"1:enter"
		);

		when(
			workflowMetricsSLADefinitionVersion.getStopNodeKeys()
		).thenReturn(
			"1:leave"
		);

		_test(
			createDateLocalDateTime, 5000, null, localDateTime, true, 5000, 1,
			workflowMetricsSLADefinitionVersion,
			WorkfowMetricsSLAStatus.STOPPED,
			_createDocument(
				new HashMap<String, Object>() {
					{
						put(
							"completionDate",
							_format(
								localDateTime.minus(5, ChronoUnit.SECONDS)));
						put("createDate", _format(createDateLocalDateTime));
						put("taskId", 1);
						put("tokenId", 1);
					}
				}));
	}

	@Test
	public void testProcessOverdueInstance() throws Exception {
		LocalDateTime localDateTime = _createLocalDateTime();

		LocalDateTime createDateLocalDateTime = localDateTime.minus(
			6, ChronoUnit.SECONDS);

		_test(
			createDateLocalDateTime, 5000, 6000, localDateTime, false, -1000,
			WorkfowMetricsSLAStatus.RUNNING,
			_createDocument(
				new HashMap<String, Object>() {
					{
						put("createDate", _format(createDateLocalDateTime));
						put("taskId", 1);
						put("tokenId", 1);
					}
				}));
	}

	@Test
	public void testProcessOverdueInstanceWithParallelTasks() throws Exception {
		LocalDateTime localDateTime = _createLocalDateTime();

		LocalDateTime createDateLocalDateTime = localDateTime.minus(
			10, ChronoUnit.SECONDS);

		_test(
			createDateLocalDateTime, 5000, 10000, localDateTime, false, -5000,
			WorkfowMetricsSLAStatus.RUNNING,
			_createDocument(
				new HashMap<String, Object>() {
					{
						put(
							"completionDate",
							_format(
								localDateTime.minus(4, ChronoUnit.SECONDS)));
						put("createDate", _format(createDateLocalDateTime));
						put("taskId", 1);
						put("tokenId", 1);
					}
				}),
			_createDocument(
				new HashMap<String, Object>() {
					{
						put("createDate", _format(createDateLocalDateTime));
						put("taskId", 2);
						put("tokenId", 2);
					}
				}));
	}

	protected WorkflowMetricsSLACalendarTracker
			mockWorkflowMetricsSLACalendarTracker()
		throws Exception {

		WorkflowMetricsSLACalendarTrackerImpl metricsSLACalendarTrackerImpl =
			new WorkflowMetricsSLACalendarTrackerImpl();

		field(
			WorkflowMetricsSLACalendarTrackerImpl.class,
			"_defaultWorkflowMetricsSLACalendar"
		).set(
			metricsSLACalendarTrackerImpl,
			new DefaultWorkflowMetricsSLACalendar()
		);

		return metricsSLACalendarTrackerImpl;
	}

	private void _assertIsBreached(
		boolean breached, Document document, LocalDateTime nowLocalDateTime,
		LocalDateTime overdueLocalDateTime) {

		WorkflowMetricsSLAProcessor workflowMetricsSLAProcessor =
			new WorkflowMetricsSLAProcessor();

		Assert.assertEquals(
			breached,
			workflowMetricsSLAProcessor.isBreached(
				document, nowLocalDateTime, overdueLocalDateTime));
	}

	private void _assertIsOnTime(
		boolean breached, Document document, LocalDateTime nowLocalDateTime,
		LocalDateTime overdueLocalDateTime) {

		WorkflowMetricsSLAProcessor workflowMetricsSLAProcessor =
			new WorkflowMetricsSLAProcessor();

		Assert.assertEquals(
			breached,
			workflowMetricsSLAProcessor.isOnTime(
				document, nowLocalDateTime, overdueLocalDateTime));
	}

	private Document _createDocument(Map<String, Object> values) {
		DocumentBuilder documentBuilder = new DocumentBuilderImpl();

		for (Map.Entry<String, Object> entry : values.entrySet()) {
			documentBuilder.setValue(entry.getKey(), entry.getValue());
		}

		return documentBuilder.build();
	}

	private LocalDateTime _createLocalDateTime() {
		LocalDateTime localDateTime = LocalDateTime.now();

		return localDateTime.withNano(0);
	}

	private String _format(LocalDateTime localDateTime) {
		return _dateTimeFormatter.format(localDateTime);
	}

	private void _test(
			LocalDateTime createDateLocalDateTime, long duration,
			long elapsedTime, LocalDateTime localDateTime, boolean onTime,
			long remainingTime, WorkfowMetricsSLAStatus workfowMetricsSLAStatus,
			Document... documents)
		throws Exception {

		WorkflowMetricsSLADefinitionVersion
			workflowMetricsSLADefinitionVersion = mock(
				WorkflowMetricsSLADefinitionVersion.class);

		when(
			workflowMetricsSLADefinitionVersion.getDuration()
		).thenReturn(
			duration
		);

		when(
			workflowMetricsSLADefinitionVersion.getStartNodeKeys()
		).thenReturn(
			"0"
		);

		_test(
			createDateLocalDateTime, elapsedTime, null, localDateTime, onTime,
			remainingTime, 0, workflowMetricsSLADefinitionVersion,
			workfowMetricsSLAStatus, documents);
	}

	private void _test(
			LocalDateTime createDateLocalDateTime, long elapsedTime,
			WorkflowMetricsSLAProcessResult lastWorkflowMetricsSLAProcessResult,
			LocalDateTime localDateTime, boolean onTime, long remainingTime,
			long startNodeId,
			WorkflowMetricsSLADefinitionVersion
				workflowMetricsSLADefinitionVersion,
			WorkfowMetricsSLAStatus workfowMetricsSLAStatus,
			Document... documents)
		throws Exception {

		WorkflowMetricsSLAProcessor workflowMetricsSLAProcessor =
			new WorkflowMetricsSLAProcessor() {

				@Override
				protected WorkflowMetricsSLAProcessResult
					fetchLastWorkflowMetricsSLAProcessResult(
						WorkflowMetricsSLADefinitionVersion
							workflowMetricsSLADefinitionVersion,
						long instanceId) {

					return lastWorkflowMetricsSLAProcessResult;
				}

				@Override
				protected List<Document> getDocuments(
					long companyId, long instanceId,
					LocalDateTime lastCheckLocalDateTime) {

					return Arrays.asList(documents);
				}

			};

		field(
			WorkflowMetricsSLAProcessor.class,
			"_workflowMetricsSLACalendarTracker"
		).set(
			workflowMetricsSLAProcessor, mockWorkflowMetricsSLACalendarTracker()
		);

		Optional<WorkflowMetricsSLAProcessResult> optional =
			workflowMetricsSLAProcessor.process(
				0, createDateLocalDateTime, 0, localDateTime, startNodeId,
				workflowMetricsSLADefinitionVersion);

		WorkflowMetricsSLAProcessResult workflowMetricsSLAProcessResult =
			optional.get();

		Assert.assertEquals(
			elapsedTime, workflowMetricsSLAProcessResult.getElapsedTime());
		Assert.assertEquals(
			remainingTime, workflowMetricsSLAProcessResult.getRemainingTime());
		Assert.assertEquals(
			workflowMetricsSLAProcessResult.getWorkfowMetricsSLAStatus(),
			workfowMetricsSLAStatus);
		Assert.assertEquals(workflowMetricsSLAProcessResult.isOnTime(), onTime);
	}

	private final DateTimeFormatter _dateTimeFormatter =
		DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

}