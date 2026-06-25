/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.service.internal.search.index.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.search.engine.adapter.index.DeleteIndexRequest;
import com.liferay.portal.search.engine.adapter.index.GetMappingIndexRequest;
import com.liferay.portal.search.engine.adapter.index.GetMappingIndexResponse;
import com.liferay.portal.search.engine.adapter.index.IndicesExistsIndexRequest;
import com.liferay.portal.search.engine.adapter.index.IndicesExistsIndexResponse;
import com.liferay.portal.search.index.IndexNameBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.workflow.metrics.search.index.constants.WorkflowMetricsIndexNameConstants;
import com.liferay.portal.workflow.metrics.search.index.reindexer.WorkflowMetricsReindexer;
import com.liferay.portal.workflow.metrics.search.index.reindexer.WorkflowMetricsReindexerRegistry;
import com.liferay.portal.workflow.metrics.service.util.BaseWorkflowMetricsIndexerTestCase;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Felipe Lorenz
 */
@RunWith(Arquillian.class)
public class WorkflowMetricsReindexerTest
	extends BaseWorkflowMetricsIndexerTestCase {

	@Test
	public void testReindexCreatesMissingIndexes() throws Exception {
		long companyId = TestPropsValues.getCompanyId();

		String indexNamePrefix = _indexNameBuilder.getIndexName(companyId);

		for (String suffix : _SUFFIXES) {
			searchEngineAdapter.execute(
				new DeleteIndexRequest(indexNamePrefix + suffix));
		}

		WorkflowMetricsReindexer workflowMetricsReindexer =
			_workflowMetricsReindexerRegistry.getWorkflowMetricsReindexer(
				"instance");

		workflowMetricsReindexer.reindex(companyId);

		for (String suffix : _SUFFIXES) {
			IndicesExistsIndexResponse indicesExistsIndexResponse =
				searchEngineAdapter.execute(
					new IndicesExistsIndexRequest(indexNamePrefix + suffix));

			Assert.assertTrue(indicesExistsIndexResponse.isExists());
		}

		String instanceIndexName =
			indexNamePrefix + WorkflowMetricsIndexNameConstants.SUFFIX_INSTANCE;

		GetMappingIndexResponse getMappingIndexResponse =
			searchEngineAdapter.execute(
				new GetMappingIndexRequest(new String[] {instanceIndexName}));

		String mappingJSON = getMappingIndexResponse.getIndexMappings(
		).get(
			instanceIndexName
		);

		Assert.assertNotNull(mappingJSON);
		Assert.assertTrue(mappingJSON.contains("\"instanceId\""));
		Assert.assertTrue(mappingJSON.contains("\"tasks\""));
	}

	private static final String[] _SUFFIXES = {
		WorkflowMetricsIndexNameConstants.SUFFIX_INSTANCE,
		WorkflowMetricsIndexNameConstants.SUFFIX_NODE,
		WorkflowMetricsIndexNameConstants.SUFFIX_PROCESS,
		WorkflowMetricsIndexNameConstants.SUFFIX_SLA_INSTANCE_RESULT,
		WorkflowMetricsIndexNameConstants.SUFFIX_SLA_TASK_RESULT,
		WorkflowMetricsIndexNameConstants.SUFFIX_TASK,
		WorkflowMetricsIndexNameConstants.SUFFIX_TRANSITION
	};

	@Inject
	private IndexNameBuilder _indexNameBuilder;

	@Inject
	private WorkflowMetricsReindexerRegistry _workflowMetricsReindexerRegistry;

}