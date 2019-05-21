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

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.search.CountSearchRequest;
import com.liferay.portal.search.engine.adapter.search.CountSearchResponse;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.test.util.IdempotentRetryAssert;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Instance;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Node;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Process;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Task;

import java.io.Serializable;

import java.lang.reflect.Method;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.digest.DigestUtils;

import org.junit.Assert;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * @author Inácio Nery
 */
public class WorkflowMetricsRESTTestHelper {

	public WorkflowMetricsRESTTestHelper(
		Queries queries, SearchEngineAdapter searchEngineAdapter) {

		_queries = queries;
		_searchEngineAdapter = searchEngineAdapter;
	}

	public Instance addInstance(long companyId, Instance instance)
		throws Exception {

		_invokeAddDocument(
			_getIndexer(_INSTANCE_INDEXER_CLASS_NAME),
			_createWorkflowMetricsInstanceDocument(
				companyId, instance.getId(), instance.getProcessId()));

		_retryAssertCount(
			"workflow-metrics-instances", "instanceId", instance.getId());

		return new Instance() {
			{
				id = instance.getId();
				processId = instance.getProcessId();
				slaStatus = instance.getSLAStatus();
				status = instance.getStatus();
			}
		};
	}

	public Node addNode(
			long companyId, long processId, String version, Node node)
		throws Exception {

		_invokeAddDocument(
			_getIndexer(_NODE_INDEXER_CLASS_NAME),
			_createWorkflowMetricsNodeDocument(
				companyId, node.getId(), node.getName(), processId, "STATE",
				version));

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
		_invokeAddDocument(_getIndexer(_PROCESS_INDEXER_CLASS_NAME), document);
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

		_invokeAddDocument(
			_getIndexer(_PROCESS_INDEXER_CLASS_NAME),
			_createWorkflowMetricsProcessDocument(
				companyId, process.getId(), "1.0"));

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

		_invokeAddDocument(
			_getIndexer(_NODE_INDEXER_CLASS_NAME),
			_createWorkflowMetricsNodeDocument(
				companyId, taskId, task.getName(), processId, "TASK", "1.0"));

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

	public void deleteInstance(long companyId, long instanceId, long processId)
		throws Exception {

		_invokeDeleteDocument(
			_getIndexer(_INSTANCE_INDEXER_CLASS_NAME),
			_createWorkflowMetricsInstanceDocument(
				companyId, instanceId, processId));
	}

	public void deleteNode(long companyId, long processId, String name)
		throws Exception {

		_invokeDeleteDocument(
			_getIndexer(_NODE_INDEXER_CLASS_NAME),
			_createWorkflowMetricsNodeDocument(
				companyId, 0, name, processId, "STATE", "1.0"));
	}

	public void deleteProcess(Document document) throws Exception {
		_invokeDeleteDocument(
			_getIndexer(_PROCESS_INDEXER_CLASS_NAME), document);
	}

	public void deleteProcess(long companyId, long processId) throws Exception {
		deleteProcess(
			_createWorkflowMetricsProcessDocument(companyId, processId, "1.0"));
	}

	public void deleteTask(long companyId, long processId, String taskName)
		throws Exception {

		_invokeDeleteDocument(
			_getIndexer(_NODE_INDEXER_CLASS_NAME),
			_createWorkflowMetricsNodeDocument(
				companyId, 0, taskName, processId, "TASK", "1.0"));
	}

	public Document getSingleApproverDocument(long companyId) throws Exception {
		return IdempotentRetryAssert.retryAssert(
			10, TimeUnit.SECONDS,
			() -> {
				SearchSearchRequest searchSearchRequest =
					new SearchSearchRequest();

				searchSearchRequest.setIndexNames("workflow-metrics-processes");

				BooleanQuery booleanQuery = _queries.booleanQuery();

				searchSearchRequest.setQuery(
					booleanQuery.addMustQueryClauses(
						_queries.term("companyId", companyId),
						_queries.term("name", "Single Approver")));

				SearchSearchResponse searchSearchResponse =
					_searchEngineAdapter.execute(searchSearchRequest);

				Hits hits = searchSearchResponse.getHits();

				Document[] documents = hits.getDocs();

				Assert.assertEquals(
					Arrays.toString(documents), 1, documents.length);

				return documents[0];
			});
	}

	public void updateProcess(long companyId, long processId, String version)
		throws Exception {

		_invokeUpdateDocument(
			_getIndexer(_PROCESS_INDEXER_CLASS_NAME),
			_createWorkflowMetricsProcessDocument(
				companyId, processId, version));

		_retryAssertCount("workflow-metrics-processes", "processId", processId);
	}

	private Document _createWorkflowMetricsInstanceDocument(
		long companyId, long instanceId, long processId) {

		Document document = new DocumentImpl();

		document.addUID(
			"WorkflowMetricsInstance",
			_digest(companyId, processId, instanceId));
		document.addKeyword("companyId", companyId);
		document.addKeyword("completed", false);
		document.addDate("createDate", new Date());
		document.addKeyword("deleted", false);
		document.addKeyword("instanceId", instanceId);
		document.addKeyword("processId", processId);
		document.addKeyword("version", "1.0");

		return document;
	}

	private Document _createWorkflowMetricsNodeDocument(
		long companyId, long nodeId, String name, long processId, String type,
		String version) {

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
		document.addKeyword("version", version);

		return document;
	}

	private Document _createWorkflowMetricsProcessDocument(
		long companyId, long processId, String version) {

		Document document = new DocumentImpl();

		document.addUID(
			"WorkflowMetricsProcess", _digest(companyId, processId));
		document.addKeyword("active", true);
		document.addKeyword("companyId", companyId);
		document.addKeyword("deleted", false);
		document.addKeyword("name", RandomTestUtil.randomString());
		document.addKeyword("processId", processId);
		document.addKeyword("version", version);

		return document;
	}

	private String _digest(Serializable... parts) {
		StringBuilder sb = new StringBuilder();

		for (Serializable part : parts) {
			sb.append(part);
		}

		return DigestUtils.sha256Hex(sb.toString());
	}

	private Object _getIndexer(String className) throws Exception {
		if (_indexers.containsKey(className)) {
			return _indexers.get(className);
		}

		Bundle bundle = FrameworkUtil.getBundle(
			WorkflowMetricsRESTTestHelper.class);

		BundleContext _bundleContext = bundle.getBundleContext();

		int count = 0;

		ServiceReference<?> serviceReference = null;

		do {
			ServiceReference<?>[] serviceReferences =
				_bundleContext.getServiceReferences(
					className, "(objectClass=" + className + ")");

			if (ArrayUtil.isEmpty(serviceReferences)) {
				count++;

				if (count >= 5) {
					throw new IllegalStateException(
						"Unable to get reference to " + className);
				}

				Thread.sleep(500);
			}

			serviceReference = serviceReferences[0];
		}
		while (serviceReference == null);

		Object indexer = _bundleContext.getService(serviceReference);

		_indexers.put(className, indexer);

		return indexer;
	}

	private void _invokeAddDocument(Object indexer, Document document)
		throws Exception {

		_invokeMethod(indexer, "addDocument", document);
	}

	private void _invokeDeleteDocument(Object indexer, Document document)
		throws Exception {

		_invokeMethod(indexer, "deleteDocument", document);
	}

	private void _invokeMethod(
			Object indexer, String methodName, Document document)
		throws Exception {

		Class<?> indexerClass = indexer.getClass();

		Method method = null;

		try {
			method = ReflectionUtil.getDeclaredMethod(
				indexerClass, methodName, Document.class);
		}
		catch (NoSuchMethodException nsme) {
			method = ReflectionUtil.getDeclaredMethod(
				indexerClass.getSuperclass(), methodName, Document.class);
		}

		method.invoke(indexer, document);
	}

	private void _invokeUpdateDocument(Object indexer, Document document)
		throws Exception {

		_invokeMethod(indexer, "updateDocument", document);
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

	private static final String _INSTANCE_INDEXER_CLASS_NAME =
		"com.liferay.portal.workflow.metrics.internal.search.index." +
			"InstanceWorkflowMetricsIndexer";

	private static final String _NODE_INDEXER_CLASS_NAME =
		"com.liferay.portal.workflow.metrics.internal.search.index." +
			"NodeWorkflowMetricsIndexer";

	private static final String _PROCESS_INDEXER_CLASS_NAME =
		"com.liferay.portal.workflow.metrics.internal.search.index." +
			"ProcessWorkflowMetricsIndexer";

	private static Map<String, Object> _indexers = new HashMap<>();

	private final Queries _queries;
	private final SearchEngineAdapter _searchEngineAdapter;

}