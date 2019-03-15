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

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import org.junit.Assert;
import org.junit.Before;
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

	@Before
	public void setUp() {
		setUpWorkflowMetricsSLAProcessor();
	}

	@Test
	public void testProcessOntimeInstance() {
		LocalDateTime now = now();

		mockGetTokenDocuments(
			() -> {
				List<Document> tokenDocuments = new ArrayList<>();

				Document document = new Document();

				document.addField(
					createField(
						"createDate", now.minus(5, ChronoUnit.SECONDS)));

				tokenDocuments.add(document);

				return tokenDocuments;
			});

		WorkflowMetricsSLAProcessResult workflowMetricsSLAProcessResult =
			_workflowMetricsSLAProcessor.process(
				0, 0, now, mockWorkflowMetricsSLADefinition(5000));

		Assert.assertTrue(workflowMetricsSLAProcessResult.isOnTime());
		Assert.assertEquals(
			5000, workflowMetricsSLAProcessResult.getElapsedTime());
		Assert.assertEquals(
			0, workflowMetricsSLAProcessResult.getRemainingTime());
	}

	@Test
	public void testProcessOntimeInstanceWithParallelTasks() {
		LocalDateTime now = now();

		mockGetTokenDocuments(
			() -> {
				List<Document> tokenDocuments = new ArrayList<>();

				Document document = new Document();

				document.addField(
					createField(
						"createDate", now.minus(10, ChronoUnit.SECONDS)));

				document.addField(
					createField(
						"completionDate", now.minus(4, ChronoUnit.SECONDS)));

				tokenDocuments.add(document);

				document = new Document();

				document.addField(
					createField(
						"createDate", now.minus(5, ChronoUnit.SECONDS)));

				tokenDocuments.add(document);

				return tokenDocuments;
			});

		WorkflowMetricsSLAProcessResult workflowMetricsSLAProcessResult =
			_workflowMetricsSLAProcessor.process(
				0, 0, now, mockWorkflowMetricsSLADefinition(10000));

		Assert.assertTrue(workflowMetricsSLAProcessResult.isOnTime());
		Assert.assertEquals(
			10000, workflowMetricsSLAProcessResult.getElapsedTime());
		Assert.assertEquals(
			0, workflowMetricsSLAProcessResult.getRemainingTime());
	}

	@Test
	public void testProcessOverdueInstance() {
		LocalDateTime now = now();

		mockGetTokenDocuments(
			() -> {
				List<Document> tokenDocuments = new ArrayList<>();

				Document document = new Document();

				document.addField(
					createField(
						"createDate", now.minus(6, ChronoUnit.SECONDS)));

				tokenDocuments.add(document);

				return tokenDocuments;
			});

		WorkflowMetricsSLAProcessResult workflowMetricsSLAProcessResult =
			_workflowMetricsSLAProcessor.process(
				0, 0, now, mockWorkflowMetricsSLADefinition(5000));

		Assert.assertFalse(workflowMetricsSLAProcessResult.isOnTime());
		Assert.assertEquals(
			6000, workflowMetricsSLAProcessResult.getElapsedTime());
		Assert.assertEquals(
			-1000, workflowMetricsSLAProcessResult.getRemainingTime());
	}

	@Test
	public void testProcessOverdueInstanceWithParallelTasks() {
		LocalDateTime now = now();

		mockGetTokenDocuments(
			() -> {
				List<Document> tokenDocuments = new ArrayList<>();

				Document document = new Document();

				document.addField(
					createField(
						"createDate", now.minus(10, ChronoUnit.SECONDS)));

				document.addField(
					createField(
						"completionDate", now.minus(4, ChronoUnit.SECONDS)));

				tokenDocuments.add(document);

				document = new Document();

				document.addField(
					createField(
						"createDate", now.minus(5, ChronoUnit.SECONDS)));

				tokenDocuments.add(document);

				return tokenDocuments;
			});

		WorkflowMetricsSLAProcessResult workflowMetricsSLAProcessResult =
			_workflowMetricsSLAProcessor.process(
				0, 0, now, mockWorkflowMetricsSLADefinition(5000));

		Assert.assertFalse(workflowMetricsSLAProcessResult.isOnTime());
		Assert.assertEquals(
			10000, workflowMetricsSLAProcessResult.getElapsedTime());
		Assert.assertEquals(
			-5000, workflowMetricsSLAProcessResult.getRemainingTime());
	}

	protected Field createField(String fieldName, LocalDateTime value) {
		Field field = new Field(fieldName);

		field.addValue(value.format(_dateTimeFormatter));

		return field;
	}

	protected void mockGetTokenDocuments(
		Supplier<List<Document>> documentsSupplier) {

		_documentsSupplier = documentsSupplier;
	}

	protected WorkflowMetricsSLADefinition mockWorkflowMetricsSLADefinition(
		long duration) {

		WorkflowMetricsSLADefinition workflowMetricsSLADefinition = mock(
			WorkflowMetricsSLADefinition.class);

		when(
			workflowMetricsSLADefinition.getDuration()
		).thenReturn(
			duration
		);

		return workflowMetricsSLADefinition;
	}

	protected LocalDateTime now() {
		LocalDateTime now = LocalDateTime.now();

		return now.withNano(0);
	}

	protected void setUpWorkflowMetricsSLAProcessor() {
		_workflowMetricsSLAProcessor = new WorkflowMetricsSLAProcessor() {

			@Override
			protected List<Document> getTokenDocuments(
				long companyId, long instanceId) {

				return _documentsSupplier.get();
			}

		};
	}

	private final DateTimeFormatter _dateTimeFormatter =
		DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
	private Supplier<List<Document>> _documentsSupplier;
	private WorkflowMetricsSLAProcessor _workflowMetricsSLAProcessor;

}