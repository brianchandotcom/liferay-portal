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
	public void testProcessOnTimeInstance() throws Exception {
		LocalDateTime nowLocalDateTime = _createLocalDateTime();

		LocalDateTime createLocalDateTime = nowLocalDateTime.minus(
			5, ChronoUnit.SECONDS);

		_test(
			createLocalDateTime, 5000, 5000, nowLocalDateTime, true, 0,
			WorkflowMetricsSLAStatus.RUNNING,
			_createDocument(
				HashMapBuilder.<String, Object>put(
					"createDate", _format(createLocalDateTime)
				).put(
					"taskId", 1
				).put(
					"tokenId", 1
				).build()));
	}

	@Test
	public void testProcessOnTimeInstanceWithParallelTasks() throws Exception {
		LocalDateTime nowLocalDateTime = _createLocalDateTime();

		LocalDateTime createLocalDateTime = nowLocalDateTime.minus(
			10, ChronoUnit.SECONDS);

		_test(
			createLocalDateTime, 10000, 10000, nowLocalDateTime, true, 0,
			WorkflowMetricsSLAStatus.RUNNING,
			_createDocument(
				HashMapBuilder.<String, Object>put(
					"completionDate",
					_format(nowLocalDateTime.minus(4, ChronoUnit.SECONDS))
				).put(
					"createDate", _format(createLocalDateTime)
				).put(
					"taskId", 1
				).put(
					"tokenId", 1
				).build()),
			_createDocument(
				HashMapBuilder.<String, Object>put(
					"createDate",
					_format(nowLocalDateTime.minus(5, ChronoUnit.SECONDS))
				).put(
					"taskId", 2
				).put(
					"tokenId", 2
				).build()));
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
			createdLocalDateTime, 0, null, nowLocalDateTime, true, 0, 0,
			workflowMetricsSLADefinitionVersion, WorkflowMetricsSLAStatus.NEW,
			_createDocument(
				HashMapBuilder.<String, Object>put(
					"createDate", _format(createdLocalDateTime)
				).put(
					"taskId", 1
				).put(
					"tokenId", 1
				).build()));
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
			createLocalDateTime, 5000, null, nowLocalDateTime, true, 0, 1,
			workflowMetricsSLADefinitionVersion,
			WorkflowMetricsSLAStatus.PAUSED,
			_createDocument(
				HashMapBuilder.<String, Object>put(
					"completionDate",
					_format(nowLocalDateTime.minus(5, ChronoUnit.SECONDS))
				).put(
					"createDate", _format(createLocalDateTime)
				).put(
					"taskId", 1
				).put(
					"tokenId", 1
				).build()),
			_createDocument(
				HashMapBuilder.<String, Object>put(
					"createDate",
					_format(nowLocalDateTime.minus(5, ChronoUnit.SECONDS))
				).put(
					"taskId", 2
				).put(
					"tokenId", 2
				).build()));
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
			createLocalDateTime, 10000, null, nowLocalDateTime, true, 0, 1,
			workflowMetricsSLADefinitionVersion,
			WorkflowMetricsSLAStatus.STOPPED,
			_createDocument(
				HashMapBuilder.<String, Object>put(
					"completionDate",
					_format(nowLocalDateTime.minus(5, ChronoUnit.SECONDS))
				).put(
					"createDate", _format(createLocalDateTime)
				).put(
					"taskId", 1
				).put(
					"tokenId", 1
				).build()),
			_createDocument(
				HashMapBuilder.<String, Object>put(
					"completionDate", _format(nowLocalDateTime)
				).put(
					"createDate",
					_format(nowLocalDateTime.minus(5, ChronoUnit.SECONDS))
				).put(
					"taskId", 2
				).put(
					"tokenId", 2
				).build()));
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
			createLocalDateTime, 5000, null, nowLocalDateTime, true, 5000, 1,
			workflowMetricsSLADefinitionVersion,
			WorkflowMetricsSLAStatus.STOPPED,
			_createDocument(
				HashMapBuilder.<String, Object>put(
					"completionDate",
					_format(nowLocalDateTime.minus(5, ChronoUnit.SECONDS))
				).put(
					"createDate", _format(createLocalDateTime)
				).put(
					"taskId", 1
				).put(
					"tokenId", 1
				).build()));
	}

	@Test
	public void testProcessOverdueInstance() throws Exception {
		LocalDateTime nowLocalDateTime = _createLocalDateTime();

		LocalDateTime createLocalDateTime = nowLocalDateTime.minus(
			6, ChronoUnit.SECONDS);

		_test(
			createLocalDateTime, 5000, 6000, nowLocalDateTime, false, -1000,
			WorkflowMetricsSLAStatus.RUNNING,
			_createDocument(
				HashMapBuilder.<String, Object>put(
					"createDate", _format(createLocalDateTime)
				).put(
					"taskId", 1
				).put(
					"tokenId", 1
				).build()));
	}

	@Test
	public void testProcessOverdueInstanceWithParallelTasks() throws Exception {
		LocalDateTime nowLocalDateTime = _createLocalDateTime();

		LocalDateTime createLocalDateTime = nowLocalDateTime.minus(
			10, ChronoUnit.SECONDS);

		_test(
			createLocalDateTime, 5000, 10000, nowLocalDateTime, false, -5000,
			WorkflowMetricsSLAStatus.RUNNING,
			_createDocument(
				HashMapBuilder.<String, Object>put(
					"completionDate",
					_format(nowLocalDateTime.minus(4, ChronoUnit.SECONDS))
				).put(
					"createDate", _format(createLocalDateTime)
				).put(
					"taskId", 1
				).put(
					"tokenId", 1
				).build()),
			_createDocument(
				HashMapBuilder.<String, Object>put(
					"createDate", _format(createLocalDateTime)
				).put(
					"taskId", 2
				).put(
					"tokenId", 2
				).build()));
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
			nowLocalDateTime.minus(10, ChronoUnit.SECONDS), 10000,
			new WorkflowMetricsSLAProcessResult() {
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
			WorkflowMetricsSLAStatus.STOPPED);
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
			LocalDateTime createLocalDateTime, long duration, long elapsedTime,
			LocalDateTime nowLocalDateTime, boolean onTime, long remainingTime,
			WorkflowMetricsSLAStatus workflowMetricsSLAStatus,
			Document... documents)
		throws Exception {

		_test(
			createLocalDateTime, duration, elapsedTime, null, nowLocalDateTime,
			onTime, remainingTime, workflowMetricsSLAStatus, documents);
	}

	private void _test(
			LocalDateTime createLocalDateTime, long duration, long elapsedTime,
			WorkflowMetricsSLAProcessResult lastWorkflowMetricsSLAProcessResult,
			LocalDateTime nowLocalDateTime, boolean onTime, long remainingTime,
			WorkflowMetricsSLAStatus workflowMetricsSLAStatus,
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
			createLocalDateTime, elapsedTime,
			lastWorkflowMetricsSLAProcessResult, nowLocalDateTime, onTime,
			remainingTime, 0, workflowMetricsSLADefinitionVersion,
			workflowMetricsSLAStatus, documents);
	}

	private void _test(
			LocalDateTime createLocalDateTime, long elapsedTime,
			WorkflowMetricsSLAProcessResult lastWorkflowMetricsSLAProcessResult,
			LocalDateTime nowLocalDateTime, boolean onTime, long remainingTime,
			long startNodeId,
			WorkflowMetricsSLADefinitionVersion
				workflowMetricsSLADefinitionVersion,
			WorkflowMetricsSLAStatus workflowMetricsSLAStatus,
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
				0, createLocalDateTime, 0, nowLocalDateTime, startNodeId,
				workflowMetricsSLADefinitionVersion);

		WorkflowMetricsSLAProcessResult workflowMetricsSLAProcessResult =
			optional.get();

		Assert.assertEquals(
			elapsedTime, workflowMetricsSLAProcessResult.getElapsedTime());
		Assert.assertEquals(
			remainingTime, workflowMetricsSLAProcessResult.getRemainingTime());
		Assert.assertEquals(
			workflowMetricsSLAProcessResult.getWorkflowMetricsSLAStatus(),
			workflowMetricsSLAStatus);
		Assert.assertEquals(workflowMetricsSLAProcessResult.isOnTime(), onTime);
	}

	private final DateTimeFormatter _dateTimeFormatter =
		DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

}