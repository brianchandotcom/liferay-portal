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
import com.liferay.portal.search.document.Field;
import com.liferay.portal.util.PropsImpl;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinition;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import java.util.Arrays;
import java.util.List;

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
	public void testProcessOntimeInstance() {
		LocalDateTime localDateTime = _createLocalDateTime();

		_test(
			5000, 5000, localDateTime, true, 0,
			_createDocument(
				_createField(
					"createDate", localDateTime.minus(5, ChronoUnit.SECONDS))));
	}

	@Test
	public void testProcessOntimeInstanceWithParallelTasks() {
		LocalDateTime localDateTime = _createLocalDateTime();

		_test(
			10000, 10000, localDateTime, true, 0,
			_createDocument(
				_createField(
					"createDate", localDateTime.minus(10, ChronoUnit.SECONDS)),
				_createField(
					"completionDate",
					localDateTime.minus(4, ChronoUnit.SECONDS))),
			_createDocument(
				_createField(
					"createDate", localDateTime.minus(5, ChronoUnit.SECONDS))));
	}

	@Test
	public void testProcessOverdueInstance() {
		LocalDateTime localDateTime = _createLocalDateTime();

		_test(
			5000, 6000, localDateTime, false, -1000,
			_createDocument(
				_createField(
					"createDate", localDateTime.minus(6, ChronoUnit.SECONDS))));
	}

	@Test
	public void testProcessOverdueInstanceWithParallelTasks() {
		LocalDateTime localDateTime = _createLocalDateTime();

		_test(
			5000, 10000, localDateTime, false, -5000,
			_createDocument(
				_createField(
					"createDate", localDateTime.minus(10, ChronoUnit.SECONDS)),
				_createField(
					"completionDate",
					localDateTime.minus(4, ChronoUnit.SECONDS))),
			_createDocument(
				_createField(
					"createDate", localDateTime.minus(5, ChronoUnit.SECONDS))));
	}

	private Document _createDocument(Field... fields) {
		return new Document() {
			{
				for (Field field : fields) {
					addField(field);
				}
			}
		};
	}

	private Field _createField(String fieldName, LocalDateTime localDateTime) {
		Field field = new Field(fieldName);

		field.addValue(localDateTime.format(_dateTimeFormatter));

		return field;
	}

	private LocalDateTime _createLocalDateTime() {
		LocalDateTime localDateTime = LocalDateTime.now();

		return localDateTime.withNano(0);
	}

	private void _test(
		long duration, long elapsedTime, LocalDateTime localDateTime,
		boolean onTime, long remainingTime, Document... tokenDocuments) {

		WorkflowMetricsSLAProcessor workflowMetricsSLAProcessor =
			new WorkflowMetricsSLAProcessor() {

				@Override
				protected List<Document> getTokenDocuments(
					long companyId, long instanceId) {

					return Arrays.asList(tokenDocuments);
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