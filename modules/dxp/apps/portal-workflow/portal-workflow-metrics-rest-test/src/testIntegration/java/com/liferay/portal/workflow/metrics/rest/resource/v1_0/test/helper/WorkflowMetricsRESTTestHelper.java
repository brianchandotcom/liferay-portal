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

package com.liferay.portal.workflow.metrics.rest.resource.v1_0.test.helper;

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.DeleteDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.search.CountSearchRequest;
import com.liferay.portal.search.engine.adapter.search.CountSearchResponse;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.test.util.IdempotentRetryAssert;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Instance;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Node;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Process;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Task;

import java.io.Serializable;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.digest.DigestUtils;

import org.junit.Assert;

/**
 * @author Inácio Nery
 */
public class WorkflowMetricsRESTTestHelper {

	public WorkflowMetricsRESTTestHelper(
		Queries queries, SearchEngineAdapter searchEngineAdapter) {

		_queries = queries;
		_searchEngineAdapter = searchEngineAdapter;
	}

	public Instance addInstance(
			long companyId, long processId, Instance instance)
		throws Exception {

		IndexDocumentRequest indexDocumentRequest = new IndexDocumentRequest(
			"workflow-metrics-instances",
			_createWorkflowMetricsInstanceDocument(
				companyId, instance.getId(), processId)) {

			{
				setType("WorkflowMetricsInstanceType");
			}
		};

		_searchEngineAdapter.execute(indexDocumentRequest);

		_retryAssertCount(
			"workflow-metrics-instances", "instanceId", instance.getId());

		return new Instance() {
			{
				id = instance.getId();
			}
		};
	}

	public Node addNode(long companyId, long processId, Node node)
		throws Exception {

		IndexDocumentRequest indexDocumentRequest = new IndexDocumentRequest(
			"workflow-metrics-nodes",
			_createWorkflowMetricsNodeDocument(
				companyId, node.getId(), node.getName(), processId, "STATE")) {

			{
				setType("WorkflowMetricsNodeType");
			}
		};

		_searchEngineAdapter.execute(indexDocumentRequest);

		_retryAssertCount("workflow-metrics-nodes", "nodeId", node.getId());

		return new Node() {
			{
				id = node.getId();
				initial = false;
				name = node.getName();
				terminal = false;
				type = "STATE";
			}
		};
	}

	public void addProcess(Document document) throws Exception {
		IndexDocumentRequest indexDocumentRequest = new IndexDocumentRequest(
			"workflow-metrics-processes", document) {

			{
				setType("WorkflowMetricsProcessType");
			}
		};

		_searchEngineAdapter.execute(indexDocumentRequest);
	}

	public Process addProcess(long companyId) throws Exception {
		Process process = new Process() {
			{
				id = RandomTestUtil.randomLong();
				title = RandomTestUtil.randomString();
			}
		};

		return addProcess(companyId, process);
	}

	public Process addProcess(long companyId, Process process)
		throws Exception {

		BulkDocumentRequest bulkDocumentRequest = new BulkDocumentRequest();

		bulkDocumentRequest.addBulkableDocumentRequest(
			new IndexDocumentRequest(
				"workflow-metrics-instances",
				_createWorkflowMetricsInstanceDocument(
					companyId, 0, process.getId())) {

				{
					setType("WorkflowMetricsInstanceType");
				}
			});
		bulkDocumentRequest.addBulkableDocumentRequest(
			new IndexDocumentRequest(
				"workflow-metrics-sla-process-results",
				_creatWorkflowMetricsSLAProcessResultDocument(
					companyId, process.getId())) {

				{
					setType("WorkflowMetricsSLAProcessResultType");
				}
			});
		bulkDocumentRequest.addBulkableDocumentRequest(
			new IndexDocumentRequest(
				"workflow-metrics-processes",
				_createWorkflowMetricsProcessDocument(
					companyId, process.getId())) {

				{
					setType("WorkflowMetricsProcessType");
				}
			});

		_searchEngineAdapter.execute(bulkDocumentRequest);

		_retryAssertCount(
			"workflow-metrics-instances", "processId", process.getId());
		_retryAssertCount(
			"workflow-metrics-processes", "processId", process.getId());
		_retryAssertCount(
			"workflow-metrics-sla-process-results", "processId",
			process.getId());

		return new Process() {
			{
				id = process.getId();
				instanceCount = 0L;
				onTimeInstanceCount = 0L;
				overdueInstanceCount = 0L;
				title = process.getTitle();
				untrackedInstanceCount = 0L;
			}
		};
	}

	public Task addTask(long companyId, long processId, Task task)
		throws Exception {

		long taskId = RandomTestUtil.randomLong();

		BulkDocumentRequest bulkDocumentRequest = new BulkDocumentRequest();

		bulkDocumentRequest.addBulkableDocumentRequest(
			new IndexDocumentRequest(
				"workflow-metrics-sla-task-results",
				_creatWorkflowMetricsSLATaskResultDocument(
					companyId, processId, taskId, task.getName())) {

				{
					setType("WorkflowMetricsSLATaskResultType");
				}
			});
		bulkDocumentRequest.addBulkableDocumentRequest(
			new IndexDocumentRequest(
				"workflow-metrics-tokens",
				_createWorkflowMetricsTokenDocument(
					companyId, processId, taskId, task.getName())) {

				{
					setType("WorkflowMetricsTokenType");
				}
			});
		bulkDocumentRequest.addBulkableDocumentRequest(
			new IndexDocumentRequest(
				"workflow-metrics-nodes",
				_createWorkflowMetricsNodeDocument(
					companyId, taskId, task.getName(), processId, "TASK")) {

				{
					setType("WorkflowMetricsNodeType");
				}
			});

		_searchEngineAdapter.execute(bulkDocumentRequest);

		_retryAssertCount("workflow-metrics-nodes", "nodeId", taskId);
		_retryAssertCount(
			"workflow-metrics-sla-task-results", "taskId", taskId);
		_retryAssertCount("workflow-metrics-tokens", "taskId", taskId);

		return new Task() {
			{
				instanceCount = 0L;
				key = task.getKey();
				name = task.getName();
				onTimeInstanceCount = 0L;
				overdueInstanceCount = 0L;
			}
		};
	}

	public void deleteInstance(
		long companyId, long instanceId, long processId) {

		Document instanceDocument = _createWorkflowMetricsInstanceDocument(
			companyId, instanceId, processId);

		DeleteDocumentRequest deleteDocumentRequest = new DeleteDocumentRequest(
			"workflow-metrics-instances", instanceDocument.getUID()) {

			{
				setType("WorkflowMetricsInstanceType");
			}
		};

		_searchEngineAdapter.execute(deleteDocumentRequest);
	}

	public void deleteNode(long companyId, long processId, String name) {
		Document nodeDocument = _createWorkflowMetricsNodeDocument(
			companyId, 0, name, processId, "STATE");

		DeleteDocumentRequest deleteDocumentRequest = new DeleteDocumentRequest(
			"workflow-metrics-nodes", nodeDocument.getUID()) {

			{
				setType("WorkflowMetricsNodeType");
			}
		};

		_searchEngineAdapter.execute(deleteDocumentRequest);
	}

	public void deleteProcess(Document document) {
		DeleteDocumentRequest deleteDocumentRequest = new DeleteDocumentRequest(
			"workflow-metrics-processes", document.getUID()) {

			{
				setType("WorkflowMetricsProcessType");
			}
		};

		_searchEngineAdapter.execute(deleteDocumentRequest);
	}

	public void deleteProcess(long companyId, long processId) {
		BulkDocumentRequest bulkDocumentRequest = new BulkDocumentRequest();

		Document instanceDocument = _createWorkflowMetricsInstanceDocument(
			companyId, 0, processId);

		bulkDocumentRequest.addBulkableDocumentRequest(
			new DeleteDocumentRequest(
				"workflow-metrics-instances", instanceDocument.getUID()) {

				{
					setType("WorkflowMetricsInstanceType");
				}
			});

		Document slaProcessResultDocument =
			_creatWorkflowMetricsSLAProcessResultDocument(companyId, processId);

		bulkDocumentRequest.addBulkableDocumentRequest(
			new DeleteDocumentRequest(
				"workflow-metrics-sla-process-results",
				slaProcessResultDocument.getUID()) {

				{
					setType("WorkflowMetricsSLAProcessResultType");
				}
			});

		Document processDocument = _createWorkflowMetricsProcessDocument(
			companyId, processId);

		bulkDocumentRequest.addBulkableDocumentRequest(
			new DeleteDocumentRequest(
				"workflow-metrics-processes", processDocument.getUID()) {

				{
					setType("WorkflowMetricsProcessType");
				}
			});

		_searchEngineAdapter.execute(bulkDocumentRequest);
	}

	public void deleteTask(long companyId, long processId, String taskName) {
		BulkDocumentRequest bulkDocumentRequest = new BulkDocumentRequest();

		Document slaTaskResultDocument =
			_creatWorkflowMetricsSLATaskResultDocument(
				companyId, processId, 0, taskName);

		bulkDocumentRequest.addBulkableDocumentRequest(
			new DeleteDocumentRequest(
				"workflow-metrics-sla-task-results",
				slaTaskResultDocument.getUID()) {

				{
					setType("WorkflowMetricsSLATaskResultType");
				}
			});

		Document tokenDocument = _createWorkflowMetricsTokenDocument(
			companyId, processId, 0, taskName);

		bulkDocumentRequest.addBulkableDocumentRequest(
			new DeleteDocumentRequest(
				"workflow-metrics-tokens", tokenDocument.getUID()) {

				{
					setType("WorkflowMetricsTokenType");
				}
			});

		Document nodeDocument = _createWorkflowMetricsNodeDocument(
			companyId, 0, taskName, processId, "TASK");

		bulkDocumentRequest.addBulkableDocumentRequest(
			new DeleteDocumentRequest(
				"workflow-metrics-nodes", nodeDocument.getUID()) {

				{
					setType("WorkflowMetricsNodeType");
				}
			});

		_searchEngineAdapter.execute(bulkDocumentRequest);
	}

	public Document getSingleApproverDocument() throws Exception {
		return IdempotentRetryAssert.retryAssert(
			10, TimeUnit.SECONDS,
			() -> {
				SearchSearchRequest searchSearchRequest =
					new SearchSearchRequest();

				searchSearchRequest.setIndexNames("workflow-metrics-processes");
				searchSearchRequest.setQuery(
					_queries.term("name", "Single Approver"));

				SearchSearchResponse searchSearchResponse =
					_searchEngineAdapter.execute(searchSearchRequest);

				Hits hits = searchSearchResponse.getHits();

				Document[] documents = hits.getDocs();

				Assert.assertEquals(
					Arrays.toString(documents), 1, documents.length);

				return documents[0];
			});
	}

	private Document _createWorkflowMetricsInstanceDocument(
		long companyId, long instanceId, long processId) {

		Document document = new DocumentImpl();

		document.addUID(
			"WorkflowMetricsInstance",
			_digest(companyId, processId, instanceId));
		document.addKeyword("companyId", companyId);
		document.addKeyword("completed", false);
		document.addKeyword("deleted", false);
		document.addKeyword("instanceId", instanceId);
		document.addKeyword("processId", processId);
		document.addKeyword("version", "1.0");

		return document;
	}

	private Document _createWorkflowMetricsNodeDocument(
		long companyId, long nodeId, String name, long processId, String type) {

		Document document = new DocumentImpl();

		document.addUID(
			"WorkflowMetricsNode", _digest(companyId, processId, name));
		document.addKeyword("companyId", companyId);
		document.addKeyword("deleted", false);
		document.addKeyword("initial", false);
		document.addKeyword("name", name);
		document.addKeyword("nodeId", nodeId);
		document.addKeyword("processId", processId);
		document.addKeyword("terminal", false);
		document.addKeyword("type", type);
		document.addKeyword("version", "1.0");

		return document;
	}

	private Document _createWorkflowMetricsProcessDocument(
		long companyId, long processId) {

		Document document = new DocumentImpl();

		document.addUID(
			"WorkflowMetricsProcess", _digest(companyId, processId));
		document.addKeyword("active", true);
		document.addKeyword("companyId", companyId);
		document.addKeyword("deleted", false);
		document.addKeyword("name", RandomTestUtil.randomString());
		document.addKeyword("processId", processId);
		document.addKeyword("version", "1.0");

		return document;
	}

	private Document _createWorkflowMetricsTokenDocument(
		long companyId, long processId, long taskId, String taskName) {

		Document document = new DocumentImpl();

		document.addUID(
			"WorkflowMetricsToken",
			_digest(companyId, processId, 0, taskName, 0));
		document.addKeyword("companyId", companyId);
		document.addKeyword("completed", false);
		document.addKeyword("deleted", false);
		document.addKeyword("instanceId", 0);
		document.addKeyword("processId", processId);
		document.addKeyword("taskId", taskId);
		document.addKeyword("taskName", taskName);
		document.addKeyword("tokenId", 0);
		document.addKeyword("version", "1.0");

		return document;
	}

	private Document _creatWorkflowMetricsSLAProcessResultDocument(
		long companyId, long processId) {

		Document document = new DocumentImpl();

		document.addUID(
			"WorkflowMetricsSLAProcessResult",
			_digest(companyId, 0, processId, 0));
		document.addKeyword("companyId", companyId);
		document.addKeyword("deleted", false);
		document.addKeyword("instanceId", 0);
		document.addKeyword("processId", processId);
		document.addKeyword("slaDefinitionId", 0);

		return document;
	}

	private Document _creatWorkflowMetricsSLATaskResultDocument(
		long companyId, long processId, long taskId, String taskName) {

		Document document = new DocumentImpl();

		document.addUID(
			"WorkflowMetricsSLATaskResult",
			_digest(companyId, 0, processId, 0, taskName));
		document.addKeyword("companyId", companyId);
		document.addKeyword("deleted", false);
		document.addKeyword("instanceId", 0);
		document.addKeyword("processId", processId);
		document.addKeyword("slaDefinitionId", 0);
		document.addKeyword("taskId", taskId);
		document.addKeyword("taskName", taskName);

		return document;
	}

	private String _digest(Serializable... parts) {
		StringBuilder sb = new StringBuilder();

		for (Serializable part : parts) {
			sb.append(part);
		}

		return DigestUtils.sha256Hex(sb.toString());
	}

	private void _retryAssertCount(String indexName, String field, Object value)
		throws Exception {

		IdempotentRetryAssert.retryAssert(
			5, TimeUnit.SECONDS,
			() -> {
				CountSearchRequest countSearchRequest =
					new CountSearchRequest();

				countSearchRequest.setIndexNames(indexName);
				countSearchRequest.setQuery(_queries.term(field, value));

				CountSearchResponse countSearchResponse =
					_searchEngineAdapter.execute(countSearchRequest);

				Assert.assertEquals(1, countSearchResponse.getCount());

				return null;
			});
	}

	private final Queries _queries;
	private final SearchEngineAdapter _searchEngineAdapter;

}