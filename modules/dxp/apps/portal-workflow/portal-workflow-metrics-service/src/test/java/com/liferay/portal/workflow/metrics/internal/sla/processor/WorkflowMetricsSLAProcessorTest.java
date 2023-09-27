/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.internal.sla.processor;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.document.DocumentBuilder;
import com.liferay.portal.search.internal.document.DocumentBuilderImpl;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.workflow.metrics.internal.sla.calendar.DefaultWorkflowMetricsSLACalendar;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinitionVersion;
import com.liferay.portal.workflow.metrics.sla.calendar.WorkflowMetricsSLACalendarRegistry;
import com.liferay.portal.workflow.metrics.sla.processor.WorkflowMetricsSLAStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Rafael Praxedes
 */
public class WorkflowMetricsSLAProcessorTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

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
			nowLocalDateTime, createLocalDateTime, null, 10000, 0, null,
			nowLocalDateTime, true, 10000, WorkflowMetricsSLAStatus.COMPLETED);
	}

	@Test
	public void testProcessCompletedOnTimeInstance() throws Exception {
		LocalDateTime nowLocalDateTime = _createLocalDateTime();

		LocalDateTime createLocalDateTime = nowLocalDateTime.minus(
			10, ChronoUnit.SECONDS);

		_test(
			nowLocalDateTime, createLocalDateTime,
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
					).build())),
			10000, 9000, null, nowLocalDateTime, true, 1000,
			WorkflowMetricsSLAStatus.COMPLETED);
	}

	@Test
	public void testProcessCompletedOverdueInstance() throws Exception {
		LocalDateTime nowLocalDateTime = _createLocalDateTime();

		LocalDateTime createLocalDateTime = nowLocalDateTime.minus(
			10, ChronoUnit.SECONDS);

		_test(
			nowLocalDateTime, createLocalDateTime,
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
					).build())),
			5000, 9000, null, nowLocalDateTime, false, -4000,
			WorkflowMetricsSLAStatus.COMPLETED);
	}

	@Test
	public void testProcessOnTimeInstance() throws Exception {
		LocalDateTime nowLocalDateTime = _createLocalDateTime();

		LocalDateTime createLocalDateTime = nowLocalDateTime.minus(
			5, ChronoUnit.SECONDS);

		_test(
			null, createLocalDateTime,
			Arrays.asList(
				_createDocument(
					HashMapBuilder.<String, Object>put(
						"createDate", _format(createLocalDateTime)
					).put(
						"nodeId", 1
					).put(
						"taskId", 1
					).build())),
			5000, 5000, null, nowLocalDateTime, true, 0,
			WorkflowMetricsSLAStatus.RUNNING);
	}

	@Test
	public void testProcessOnTimeInstanceWithParallelTasks() throws Exception {
		LocalDateTime nowLocalDateTime = _createLocalDateTime();

		LocalDateTime createLocalDateTime = nowLocalDateTime.minus(
			10, ChronoUnit.SECONDS);

		_test(
			null, createLocalDateTime,
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
					).build())),
			10000, 10000, null, nowLocalDateTime, true, 0,
			WorkflowMetricsSLAStatus.RUNNING);
	}

	@Test
	public void testProcessOnTimeSLANotStarted() throws Exception {
		LocalDateTime nowLocalDateTime = _createLocalDateTime();

		LocalDateTime createdLocalDateTime = nowLocalDateTime.minus(
			5, ChronoUnit.SECONDS);

		WorkflowMetricsSLADefinitionVersion
			workflowMetricsSLADefinitionVersion = Mockito.mock(
				WorkflowMetricsSLADefinitionVersion.class);

		Mockito.when(
			workflowMetricsSLADefinitionVersion.getStartNodeKeys()
		).thenReturn(
			"1:leave"
		);

		_test(
			null, createdLocalDateTime,
			Arrays.asList(
				_createDocument(
					HashMapBuilder.<String, Object>put(
						"createDate", _format(createdLocalDateTime)
					).put(
						"nodeId", 1
					).put(
						"taskId", 1
					).build())),
			0, null, nowLocalDateTime, true, 0, 0,
			workflowMetricsSLADefinitionVersion, WorkflowMetricsSLAStatus.NEW);
	}

	@Test
	public void testProcessOnTimeSLAPaused() throws Exception {
		LocalDateTime nowLocalDateTime = _createLocalDateTime();

		LocalDateTime createLocalDateTime = nowLocalDateTime.minus(
			10, ChronoUnit.SECONDS);

		WorkflowMetricsSLADefinitionVersion
			workflowMetricsSLADefinitionVersion = Mockito.mock(
				WorkflowMetricsSLADefinitionVersion.class);

		Mockito.when(
			workflowMetricsSLADefinitionVersion.getDuration()
		).thenReturn(
			5000L
		);

		Mockito.when(
			workflowMetricsSLADefinitionVersion.getStartNodeKeys()
		).thenReturn(
			"1:enter"
		);

		Mockito.when(
			workflowMetricsSLADefinitionVersion.getPauseNodeKeys()
		).thenReturn(
			"2:enter"
		);

		_test(
			null, createLocalDateTime,
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
					).build())),
			5000, null, nowLocalDateTime, true, 0, 1,
			workflowMetricsSLADefinitionVersion,
			WorkflowMetricsSLAStatus.PAUSED);
	}

	@Test
	public void testProcessOnTimeSLAStopped() throws Exception {
		LocalDateTime nowLocalDateTime = _createLocalDateTime();

		LocalDateTime createLocalDateTime = nowLocalDateTime.minus(
			10, ChronoUnit.SECONDS);

		WorkflowMetricsSLADefinitionVersion
			workflowMetricsSLADefinitionVersion = Mockito.mock(
				WorkflowMetricsSLADefinitionVersion.class);

		Mockito.when(
			workflowMetricsSLADefinitionVersion.getDuration()
		).thenReturn(
			10000L
		);

		Mockito.when(
			workflowMetricsSLADefinitionVersion.getPauseNodeKeys()
		).thenReturn(
			"2"
		);

		Mockito.when(
			workflowMetricsSLADefinitionVersion.getStartNodeKeys()
		).thenReturn(
			"1:enter"
		);

		Mockito.when(
			workflowMetricsSLADefinitionVersion.getStopNodeKeys()
		).thenReturn(
			"2:leave"
		);

		_test(
			null, createLocalDateTime,
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
					).build())),
			10000, null, nowLocalDateTime, true, 0, 1,
			workflowMetricsSLADefinitionVersion,
			WorkflowMetricsSLAStatus.STOPPED);
	}

	@Test
	public void testProcessOnTimeSLAStoppedWithSameTask() throws Exception {
		LocalDateTime nowLocalDateTime = _createLocalDateTime();

		LocalDateTime createLocalDateTime = nowLocalDateTime.minus(
			10, ChronoUnit.SECONDS);

		WorkflowMetricsSLADefinitionVersion
			workflowMetricsSLADefinitionVersion = Mockito.mock(
				WorkflowMetricsSLADefinitionVersion.class);

		Mockito.when(
			workflowMetricsSLADefinitionVersion.getDuration()
		).thenReturn(
			10000L
		);

		Mockito.when(
			workflowMetricsSLADefinitionVersion.getStartNodeKeys()
		).thenReturn(
			"1:enter"
		);

		Mockito.when(
			workflowMetricsSLADefinitionVersion.getStopNodeKeys()
		).thenReturn(
			"1:leave"
		);

		_test(
			null, createLocalDateTime,
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
					).build())),
			5000, null, nowLocalDateTime, true, 5000, 1,
			workflowMetricsSLADefinitionVersion,
			WorkflowMetricsSLAStatus.STOPPED);
	}

	@Test
	public void testProcessOverdueInstance() throws Exception {
		LocalDateTime nowLocalDateTime = _createLocalDateTime();

		LocalDateTime createLocalDateTime = nowLocalDateTime.minus(
			6, ChronoUnit.SECONDS);

		_test(
			null, createLocalDateTime,
			Arrays.asList(
				_createDocument(
					HashMapBuilder.<String, Object>put(
						"createDate", _format(createLocalDateTime)
					).put(
						"nodeId", 1
					).put(
						"taskId", 1
					).build())),
			5000, 6000, null, nowLocalDateTime, false, -1000,
			WorkflowMetricsSLAStatus.RUNNING);
	}

	@Test
	public void testProcessOverdueInstanceWithParallelTasks() throws Exception {
		LocalDateTime nowLocalDateTime = _createLocalDateTime();

		LocalDateTime createLocalDateTime = nowLocalDateTime.minus(
			10, ChronoUnit.SECONDS);

		_test(
			null, createLocalDateTime,
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
					).build())),
			5000, 10000, null, nowLocalDateTime, false, -5000,
			WorkflowMetricsSLAStatus.RUNNING);
	}

	@Test
	public void testProcessSLAStopped() throws Exception {
		LocalDateTime nowLocalDateTime = _createLocalDateTime();

		WorkflowMetricsSLADefinitionVersion
			workflowMetricsSLADefinitionVersion = Mockito.mock(
				WorkflowMetricsSLADefinitionVersion.class);

		Mockito.when(
			workflowMetricsSLADefinitionVersion.getDuration()
		).thenReturn(
			10000L
		);

		Mockito.when(
			workflowMetricsSLADefinitionVersion.getStartNodeKeys()
		).thenReturn(
			"1:enter"
		);

		Mockito.when(
			workflowMetricsSLADefinitionVersion.getStopNodeKeys()
		).thenReturn(
			"2:leave"
		);

		_test(
			null, nowLocalDateTime.minus(10, ChronoUnit.SECONDS), null, 10000,
			new WorkflowMetricsSLAInstanceResult() {
				{
					setElapsedTime(10000);
					setModifiedLocalDateTime(nowLocalDateTime);
					setOnTime(true);
					setRemainingTime(0);
					setWorkflowMetricsSLAStatus(
						WorkflowMetricsSLAStatus.STOPPED);
				}
			},
			nowLocalDateTime, true, 0, 1, workflowMetricsSLADefinitionVersion,
			WorkflowMetricsSLAStatus.STOPPED);
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

	private WorkflowMetricsSLACalendarRegistry
		_mockWorkflowMetricsSLACalendarRegistry() {

		WorkflowMetricsSLACalendarRegistry workflowMetricsSLACalendarRegistry =
			Mockito.mock(WorkflowMetricsSLACalendarRegistry.class);

		Mockito.when(
			workflowMetricsSLACalendarRegistry.getWorkflowMetricsSLACalendar(
				Mockito.anyString())
		).thenReturn(
			new DefaultWorkflowMetricsSLACalendar()
		);

		return workflowMetricsSLACalendarRegistry;
	}

	private void _test(
			LocalDateTime completionLocalDateTime,
			LocalDateTime createLocalDateTime, List<Document> documents,
			long duration, long elapsedTime,
			WorkflowMetricsSLAInstanceResult
				lastWorkflowMetricsSLAInstanceResult,
			LocalDateTime nowLocalDateTime, boolean onTime, long remainingTime,
			WorkflowMetricsSLAStatus workflowMetricsSLAStatus)
		throws Exception {

		WorkflowMetricsSLADefinitionVersion
			workflowMetricsSLADefinitionVersion = Mockito.mock(
				WorkflowMetricsSLADefinitionVersion.class);

		Mockito.when(
			workflowMetricsSLADefinitionVersion.getDuration()
		).thenReturn(
			duration
		);

		Mockito.when(
			workflowMetricsSLADefinitionVersion.getStartNodeKeys()
		).thenReturn(
			"0"
		);

		_test(
			completionLocalDateTime, createLocalDateTime, documents,
			elapsedTime, lastWorkflowMetricsSLAInstanceResult, nowLocalDateTime,
			onTime, remainingTime, 0, workflowMetricsSLADefinitionVersion,
			workflowMetricsSLAStatus);
	}

	private void _test(
		LocalDateTime completionLocalDateTime,
		LocalDateTime createLocalDateTime, List<Document> documents,
		long elapsedTime,
		WorkflowMetricsSLAInstanceResult lastWorkflowMetricsSLAInstanceResult,
		LocalDateTime nowLocalDateTime, boolean onTime, long remainingTime,
		long startNodeId,
		WorkflowMetricsSLADefinitionVersion workflowMetricsSLADefinitionVersion,
		WorkflowMetricsSLAStatus workflowMetricsSLAStatus) {

		Mockito.when(
			workflowMetricsSLADefinitionVersion.getCalendarKey()
		).thenReturn(
			""
		);

		WorkflowMetricsSLAProcessor workflowMetricsSLAProcessor =
			new WorkflowMetricsSLAProcessor();

		ReflectionTestUtil.setFieldValue(
			workflowMetricsSLAProcessor, "_workflowMetricsSLACalendarRegistry",
			_mockWorkflowMetricsSLACalendarRegistry());

		WorkflowMetricsSLAInstanceResult workflowMetricsSLAInstanceResult =
			workflowMetricsSLAProcessor.process(
				completionLocalDateTime, createLocalDateTime, documents, 0,
				nowLocalDateTime, startNodeId,
				workflowMetricsSLADefinitionVersion,
				lastWorkflowMetricsSLAInstanceResult);

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