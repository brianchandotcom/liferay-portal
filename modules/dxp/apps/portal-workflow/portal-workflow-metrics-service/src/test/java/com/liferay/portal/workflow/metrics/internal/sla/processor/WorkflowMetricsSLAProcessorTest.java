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

import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.document.DocumentBuilder;
import com.liferay.portal.search.internal.document.DocumentBuilderImpl;
import com.liferay.portal.util.PropsImpl;
import com.liferay.portal.workflow.metrics.internal.sla.calendar.DefaultWorkflowMetricsSLACalendar;
import com.liferay.portal.workflow.metrics.internal.sla.calendar.WorkflowMetricsSLACalendarTrackerImpl;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinitionVersion;
import com.liferay.portal.workflow.metrics.sla.calendar.WorkflowMetricsSLACalendarTracker;
import com.liferay.portal.workflow.metrics.sla.processor.WorkflowMetricsSLAStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import java.util.Arrays;
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
				HashMapBuilder.<String, Object>put(
					"createDate", _format(nowLocalDateTime)
				).build()),
			nowLocalDateTime, nowLocalDateTime.plusHours(1));
		_assertIsBreached(
			false,
			_createDocument(
				HashMapBuilder.<String, Object>put(
					"createDate", _format(nowLocalDateTime.minusHours(1))
				).build()),
			nowLocalDateTime, nowLocalDateTime.minusHours(2));
		_assertIsBreached(
			false,
			_createDocument(
				HashMapBuilder.<String, Object>put(
					"completionDate", _format(nowLocalDateTime.minusHours(1))
				).put(
					"createDate", _format(nowLocalDateTime.minusHours(2))
				).build()),
			nowLocalDateTime, nowLocalDateTime.minusHours(1));
		_assertIsBreached(
			true,
			_createDocument(
				HashMapBuilder.<String, Object>put(
					"createDate", _format(nowLocalDateTime.minusHours(2))
				).build()),
			nowLocalDateTime, nowLocalDateTime.minusHours(1));
		_assertIsBreached(
			true,
			_createDocument(
				HashMapBuilder.<String, Object>put(
					"completionDate", _format(nowLocalDateTime)
				).put(
					"createDate", _format(nowLocalDateTime.minusHours(2))
				).build()),
			nowLocalDateTime, nowLocalDateTime.minusHours(1));
	}

	@Test
	public void testIsOnTime() {
		LocalDateTime nowLocalDateTime = _createLocalDateTime();

		_assertIsOnTime(
			true,
			_createDocument(
				HashMapBuilder.<String, Object>put(
					"createDate", _format(nowLocalDateTime)
				).build()),
			nowLocalDateTime, nowLocalDateTime.plusHours(1));
		_assertIsOnTime(
			true,
			_createDocument(
				HashMapBuilder.<String, Object>put(
					"completionDate", _format(nowLocalDateTime.minusHours(1))
				).put(
					"createDate", _format(nowLocalDateTime.minusHours(2))
				).build()),
			nowLocalDateTime, nowLocalDateTime.minusHours(1));
		_assertIsOnTime(
			false,
			_createDocument(
				HashMapBuilder.<String, Object>put(
					"createDate", _format(nowLocalDateTime.minusHours(1))
				).build()),
			nowLocalDateTime, nowLocalDateTime.minusHours(2));
		_assertIsOnTime(
			false,
			_createDocument(
				HashMapBuilder.<String, Object>put(
					"createDate", _format(nowLocalDateTime.minusHours(2))
				).build()),
			nowLocalDateTime, nowLocalDateTime.minusHours(1));
		_assertIsOnTime(
			false,
			_createDocument(
				HashMapBuilder.<String, Object>put(
					"completionDate", _format(nowLocalDateTime)
				).put(
					"createDate", _format(nowLocalDateTime.minusHours(2))
				).build()),
			nowLocalDateTime, nowLocalDateTime.minusHours(1));
	}

	@Test
	public void testProcessCompletedInstanceWithoutTasks() throws Exception {
		LocalDateTime nowLocalDateTime = _createLocalDateTime();

		LocalDateTime createLocalDateTime = nowLocalDateTime.minus(
			10, ChronoUnit.SECONDS);

		_test(
			nowLocalDateTime, createLocalDateTime, 10000, 0, null,
			nowLocalDateTime, true, 10000, WorkflowMetricsSLAStatus.COMPLETED,
			null);
	}

	@Test
	public void testProcessCompletedOnTimeInstance() throws Exception {
		LocalDateTime nowLocalDateTime = _createLocalDateTime();

		LocalDateTime createLocalDateTime = nowLocalDateTime.minus(
			10, ChronoUnit.SECONDS);

		_test(
			nowLocalDateTime, createLocalDateTime, 10000, 9000, null,
			nowLocalDateTime, true, 1000, WorkflowMetricsSLAStatus.COMPLETED,
			Arrays.asList(
				_createDocument(
					HashMapBuilder.<String, Object>put(
						"completionDate",
						_format(nowLocalDateTime.minus(3, ChronoUnit.SECONDS))
					).put(
						"createDate",
						_format(nowLocalDateTime.minus(9, ChronoUnit.SECONDS))
					).put(
						"nodeId", 1
					).put(
						"taskId", 1
					).build())));
	}

	@Test
	public void testProcessCompletedOverdueInstance() throws Exception {
		LocalDateTime nowLocalDateTime = _createLocalDateTime();

		LocalDateTime createLocalDateTime = nowLocalDateTime.minus(
			10, ChronoUnit.SECONDS);

		_test(
			nowLocalDateTime, createLocalDateTime, 5000, 9000, null,
			nowLocalDateTime, false, -4000, WorkflowMetricsSLAStatus.COMPLETED,
			Arrays.asList(
				_createDocument(
					HashMapBuilder.<String, Object>put(
						"completionDate",
						_format(nowLocalDateTime.minus(3, ChronoUnit.SECONDS))
					).put(
						"createDate",
						_format(nowLocalDateTime.minus(9, ChronoUnit.SECONDS))
					).put(
						"nodeId", 1
					).put(
						"taskId", 1
					).build())));
	}

	@Test
	public void testProcessOnTimeInstance() throws Exception {
		LocalDateTime nowLocalDateTime = _createLocalDateTime();

		LocalDateTime createLocalDateTime = nowLocalDateTime.minus(
			5, ChronoUnit.SECONDS);

		_test(
			null, createLocalDateTime, 5000, 5000, null, nowLocalDateTime, true,
			0, WorkflowMetricsSLAStatus.RUNNING,
			Arrays.asList(
				_createDocument(
					HashMapBuilder.<String, Object>put(
						"createDate", _format(createLocalDateTime)
					).put(
						"nodeId", 1
					).put(
						"taskId", 1
					).build())));
	}

	@Test
	public void testProcessOnTimeInstanceWithParallelTasks() throws Exception {
		LocalDateTime nowLocalDateTime = _createLocalDateTime();

		LocalDateTime createLocalDateTime = nowLocalDateTime.minus(
			10, ChronoUnit.SECONDS);

		_test(
			null, createLocalDateTime, 10000, 10000, null, nowLocalDateTime,
			true, 0, WorkflowMetricsSLAStatus.RUNNING,
			Arrays.asList(
				_createDocument(
					HashMapBuilder.<String, Object>put(
						"completionDate",
						_format(nowLocalDateTime.minus(4, ChronoUnit.SECONDS))
					).put(
						"createDate", _format(createLocalDateTime)
					).put(
						"nodeId", 1
					).put(
						"taskId", 1
					).build()),
				_createDocument(
					HashMapBuilder.<String, Object>put(
						"createDate",
						_format(nowLocalDateTime.minus(5, ChronoUnit.SECONDS))
					).put(
						"nodeId", 2
					).put(
						"taskId", 2
					).build())));
	}

	@Test
	public void testProcessOnTimeSLANotStarted() throws Exception {
		LocalDateTime nowLocalDateTime = _createLocalDateTime();

		LocalDateTime createdLocalDateTime = nowLocalDateTime.minus(
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
			null, createdLocalDateTime, 0, null, nowLocalDateTime, true, 0, 0,
			workflowMetricsSLADefinitionVersion, WorkflowMetricsSLAStatus.NEW,
			Arrays.asList(
				_createDocument(
					HashMapBuilder.<String, Object>put(
						"createDate", _format(createdLocalDateTime)
					).put(
						"nodeId", 1
					).put(
						"taskId", 1
					).build())));
	}

	@Test
	public void testProcessOnTimeSLAPaused() throws Exception {
		LocalDateTime nowLocalDateTime = _createLocalDateTime();

		LocalDateTime createLocalDateTime = nowLocalDateTime.minus(
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
			null, createLocalDateTime, 5000, null, nowLocalDateTime, true, 0, 1,
			workflowMetricsSLADefinitionVersion,
			WorkflowMetricsSLAStatus.PAUSED,
			Arrays.asList(
				_createDocument(
					HashMapBuilder.<String, Object>put(
						"completionDate",
						_format(nowLocalDateTime.minus(5, ChronoUnit.SECONDS))
					).put(
						"createDate", _format(createLocalDateTime)
					).put(
						"nodeId", 1
					).put(
						"taskId", 1
					).build()),
				_createDocument(
					HashMapBuilder.<String, Object>put(
						"createDate",
						_format(nowLocalDateTime.minus(5, ChronoUnit.SECONDS))
					).put(
						"nodeId", 2
					).put(
						"taskId", 2
					).build())));
	}

	@Test
	public void testProcessOnTimeSLAStopped() throws Exception {
		LocalDateTime nowLocalDateTime = _createLocalDateTime();

		LocalDateTime createLocalDateTime = nowLocalDateTime.minus(
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
			null, createLocalDateTime, 10000, null, nowLocalDateTime, true, 0,
			1, workflowMetricsSLADefinitionVersion,
			WorkflowMetricsSLAStatus.STOPPED,
			Arrays.asList(
				_createDocument(
					HashMapBuilder.<String, Object>put(
						"completionDate",
						_format(nowLocalDateTime.minus(5, ChronoUnit.SECONDS))
					).put(
						"createDate", _format(createLocalDateTime)
					).put(
						"nodeId", 1
					).put(
						"taskId", 1
					).build()),
				_createDocument(
					HashMapBuilder.<String, Object>put(
						"completionDate", _format(nowLocalDateTime)
					).put(
						"createDate",
						_format(nowLocalDateTime.minus(5, ChronoUnit.SECONDS))
					).put(
						"nodeId", 2
					).put(
						"taskId", 2
					).build())));
	}

	@Test
	public void testProcessOnTimeSLAStoppedWithSameTask() throws Exception {
		LocalDateTime nowLocalDateTime = _createLocalDateTime();

		LocalDateTime createLocalDateTime = nowLocalDateTime.minus(
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
			null, createLocalDateTime, 5000, null, nowLocalDateTime, true, 5000,
			1, workflowMetricsSLADefinitionVersion,
			WorkflowMetricsSLAStatus.STOPPED,
			Arrays.asList(
				_createDocument(
					HashMapBuilder.<String, Object>put(
						"completionDate",
						_format(nowLocalDateTime.minus(5, ChronoUnit.SECONDS))
					).put(
						"createDate", _format(createLocalDateTime)
					).put(
						"nodeId", 1
					).put(
						"taskId", 1
					).build())));
	}

	@Test
	public void testProcessOverdueInstance() throws Exception {
		LocalDateTime nowLocalDateTime = _createLocalDateTime();

		LocalDateTime createLocalDateTime = nowLocalDateTime.minus(
			6, ChronoUnit.SECONDS);

		_test(
			null, createLocalDateTime, 5000, 6000, null, nowLocalDateTime,
			false, -1000, WorkflowMetricsSLAStatus.RUNNING,
			Arrays.asList(
				_createDocument(
					HashMapBuilder.<String, Object>put(
						"createDate", _format(createLocalDateTime)
					).put(
						"nodeId", 1
					).put(
						"taskId", 1
					).build())));
	}

	@Test
	public void testProcessOverdueInstanceWithParallelTasks() throws Exception {
		LocalDateTime nowLocalDateTime = _createLocalDateTime();

		LocalDateTime createLocalDateTime = nowLocalDateTime.minus(
			10, ChronoUnit.SECONDS);

		_test(
			null, createLocalDateTime, 5000, 10000, null, nowLocalDateTime,
			false, -5000, WorkflowMetricsSLAStatus.RUNNING,
			Arrays.asList(
				_createDocument(
					HashMapBuilder.<String, Object>put(
						"completionDate",
						_format(nowLocalDateTime.minus(4, ChronoUnit.SECONDS))
					).put(
						"createDate", _format(createLocalDateTime)
					).put(
						"nodeId", 1
					).put(
						"taskId", 1
					).build()),
				_createDocument(
					HashMapBuilder.<String, Object>put(
						"createDate", _format(createLocalDateTime)
					).put(
						"nodeId", 2
					).put(
						"taskId", 2
					).build())));
	}

	@Test
	public void testProcessSLAStopped() throws Exception {
		LocalDateTime nowLocalDateTime = _createLocalDateTime();

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
			"2:leave"
		);

		_test(
			null, nowLocalDateTime.minus(10, ChronoUnit.SECONDS), 10000,
			new WorkflowMetricsSLAInstanceResult() {
				{
					setElapsedTime(10000);
					setLastCheckLocalDateTime(nowLocalDateTime);
					setRemainingTime(0);
					setWorkflowMetricsSLAStatus(
						WorkflowMetricsSLAStatus.STOPPED);
					setOnTime(true);
				}
			},
			nowLocalDateTime, true, 0, 1, workflowMetricsSLADefinitionVersion,
			WorkflowMetricsSLAStatus.STOPPED, null);
	}

	protected WorkflowMetricsSLACalendarTracker
			mockWorkflowMetricsSLACalendarTracker()
		throws Exception {

		WorkflowMetricsSLACalendarTrackerImpl
			workflowMetricsSLACalendarTrackerImpl =
				new WorkflowMetricsSLACalendarTrackerImpl();

		field(
			WorkflowMetricsSLACalendarTrackerImpl.class,
			"_defaultWorkflowMetricsSLACalendar"
		).set(
			workflowMetricsSLACalendarTrackerImpl,
			new DefaultWorkflowMetricsSLACalendar()
		);

		return workflowMetricsSLACalendarTrackerImpl;
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
		LocalDateTime nowLocalDateTime = LocalDateTime.now();

		return nowLocalDateTime.withNano(0);
	}

	private String _format(LocalDateTime nowLocalDateTime) {
		return _dateTimeFormatter.format(nowLocalDateTime);
	}

	private void _test(
			LocalDateTime completionLocalDateTime,
			LocalDateTime createLocalDateTime, long duration, long elapsedTime,
			WorkflowMetricsSLAInstanceResult
				lastWorkflowMetricsSLAInstanceResult,
			LocalDateTime nowLocalDateTime, boolean onTime, long remainingTime,
			WorkflowMetricsSLAStatus workflowMetricsSLAStatus,
			List<Document> documents)
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
			completionLocalDateTime, createLocalDateTime, elapsedTime,
			lastWorkflowMetricsSLAInstanceResult, nowLocalDateTime, onTime,
			remainingTime, 0, workflowMetricsSLADefinitionVersion,
			workflowMetricsSLAStatus, documents);
	}

	private void _test(
			LocalDateTime completionLocalDateTime,
			LocalDateTime createLocalDateTime, long elapsedTime,
			WorkflowMetricsSLAInstanceResult
				lastWorkflowMetricsSLAInstanceResult,
			LocalDateTime nowLocalDateTime, boolean onTime, long remainingTime,
			long startNodeId,
			WorkflowMetricsSLADefinitionVersion
				workflowMetricsSLADefinitionVersion,
			WorkflowMetricsSLAStatus workflowMetricsSLAStatus,
			List<Document> documents)
		throws Exception {

		WorkflowMetricsSLAProcessor workflowMetricsSLAProcessor =
			new WorkflowMetricsSLAProcessor();

		field(
			WorkflowMetricsSLAProcessor.class,
			"_workflowMetricsSLACalendarTracker"
		).set(
			workflowMetricsSLAProcessor, mockWorkflowMetricsSLACalendarTracker()
		);

		Optional<WorkflowMetricsSLAInstanceResult> optional =
			workflowMetricsSLAProcessor.process(
				completionLocalDateTime, createLocalDateTime, documents, 0,
				nowLocalDateTime, startNodeId,
				workflowMetricsSLADefinitionVersion,
				lastWorkflowMetricsSLAInstanceResult);

		WorkflowMetricsSLAInstanceResult workflowMetricsSLAInstanceResult =
			optional.get();

		Assert.assertEquals(
			elapsedTime, workflowMetricsSLAInstanceResult.getElapsedTime());
		Assert.assertEquals(
			remainingTime, workflowMetricsSLAInstanceResult.getRemainingTime());
		Assert.assertEquals(
			workflowMetricsSLAStatus,
			workflowMetricsSLAInstanceResult.getWorkflowMetricsSLAStatus());
		Assert.assertEquals(
			onTime, workflowMetricsSLAInstanceResult.isOnTime());
	}

	private final DateTimeFormatter _dateTimeFormatter =
		DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

}