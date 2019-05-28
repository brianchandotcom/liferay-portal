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
import com.liferay.portal.kernel.language.LanguageUtil;
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
import java.util.Locale;
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

	public Instance addInstance(
			long companyId, boolean completed, long processId)
		throws Exception {

		Instance instance = new Instance();

		if (completed) {
			instance.setDateCompletion(RandomTestUtil.nextDate());
		}

		instance.setId(RandomTestUtil.randomLong());
		instance.setProcessId(processId);

		return addInstance(companyId, instance);
	}

	public Instance addInstance(long companyId, Instance instance)
		throws Exception {

		_invokeAddDocument(
			_getIndexer(_CLASS_NAME_INSTANCE_INDEXER),
			_createWorkflowMetricsInstanceDocument(
				companyId, instance.getDateCompletion() != null,
				instance.getId(), instance.getProcessId()));

		_retryAssertCount(
			"workflow-metrics-instances", "instanceId", instance.getId());

		return instance;
	}

	public Node addNode(
			long companyId, long processId, String version, Node node)
		throws Exception {

		_invokeAddDocument(
			_getIndexer(_CLASS_NAME_NODE_INDEXER),
			_createWorkflowMetricsNodeDocument(
				companyId, node.getId(), node.getName(), processId, "STATE",
				version));

		_retryAssertCount("workflow-metrics-nodes", "nodeId", node.getId());

		return node;
	}

	public Process addProcess(long companyId) throws Exception {
		Process process = new Process() {
			{
				id = RandomTestUtil.randomLong();
				instanceCount = 0L;
				onTimeInstanceCount = 0L;
				overdueInstanceCount = 0L;
				title = RandomTestUtil.randomString();
				untrackedInstanceCount = 0L;
			}
		};

		return addProcess(companyId, process);
	}

	public Process addProcess(long companyId, Process process)
		throws Exception {

		_invokeAddDocument(
			_getIndexer(_CLASS_NAME_PROCESS_INDEXER),
			_createWorkflowMetricsProcessDocument(
				companyId, process.getId(), "1.0", process.getTitle()));

		Long onTimeInstanceCount = process.getOnTimeInstanceCount();
		Long overdueInstanceCount = process.getOverdueInstanceCount();

		for (int i = 0; i < process.getInstanceCount(); i++) {
			Instance instance = addInstance(companyId, false, process.getId());

			if (onTimeInstanceCount > 0) {
				addSLAProcessResult(companyId, instance, true);

				onTimeInstanceCount--;
			}
			else if (overdueInstanceCount > 0) {
				addSLAProcessResult(companyId, instance, false);

				overdueInstanceCount--;
			}
		}

		_retryAssertCount(
			"workflow-metrics-processes", "processId", process.getId());

		return process;
	}

	public void addSLAProcessResult(
			long companyId, Instance instance, boolean onTime)
		throws Exception {

		long slaDefinitionId = RandomTestUtil.randomLong();

		_invokeAddDocument(
			_getIndexer(_CLASS_NAME_SLA_PROCESS_RESULT_INDEXER),
			_creatWorkflowMetricsSLAProcessResultDocument(
				companyId, instance.getProcessId(), instance.getId(),
				slaDefinitionId, onTime));

		_retryAssertCount(
			"workflow-metrics-sla-process-results", "slaDefinitionId",
			slaDefinitionId);
	}

	public Task addTask(long companyId, long processId, Task task)
		throws Exception {

		long taskId = RandomTestUtil.randomLong();

		_invokeAddDocument(
			_getIndexer(_CLASS_NAME_NODE_INDEXER),
			_createWorkflowMetricsNodeDocument(
				companyId, taskId, task.getName(), processId, "TASK", "1.0"));

		_retryAssertCount("workflow-metrics-nodes", "nodeId", taskId);
		_retryAssertCount(
			"workflow-metrics-sla-task-results", "taskId", taskId);
		_retryAssertCount("workflow-metrics-tokens", "taskId", taskId);

		return task;
	}

	public void deleteInstance(long companyId, long instanceId, long processId)
		throws Exception {

		_invokeDeleteDocument(
			_getIndexer(_CLASS_NAME_INSTANCE_INDEXER),
			_createWorkflowMetricsInstanceDocument(
				companyId, false, instanceId, processId));
	}

	public void deleteNode(long companyId, long processId, String name)
		throws Exception {

		_invokeDeleteDocument(
			_getIndexer(_CLASS_NAME_NODE_INDEXER),
			_createWorkflowMetricsNodeDocument(
				companyId, 0, name, processId, "STATE", "1.0"));
	}

	public void deleteProcess(Document document) throws Exception {
		_invokeDeleteDocument(
			_getIndexer(_CLASS_NAME_PROCESS_INDEXER), document);
	}

	public void deleteProcess(long companyId, long processId) throws Exception {
		deleteProcess(
			_createWorkflowMetricsProcessDocument(
				companyId, processId, "1.0", null));
	}

	public void deleteTask(long companyId, long processId, String taskName)
		throws Exception {

		_invokeDeleteDocument(
			_getIndexer(_CLASS_NAME_NODE_INDEXER),
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

	public void restoreProcess(Document document) throws Exception {
		document.addKeyword("deleted", false);

		_invokeAddDocument(_getIndexer(_CLASS_NAME_PROCESS_INDEXER), document);
	}

	public void updateProcess(long companyId, long processId, String version)
		throws Exception {

		_invokeUpdateDocument(
			_getIndexer(_CLASS_NAME_PROCESS_INDEXER),
			_createWorkflowMetricsProcessDocument(
				companyId, processId, version, null));

		_retryAssertCount("workflow-metrics-processes", "processId", processId);
	}

	private Map<Locale, String> _createLocalizationMap(String title) {
		Map<Locale, String> localizationMap = new HashMap<>();

		for (Locale availableLocale : LanguageUtil.getAvailableLocales()) {
			localizationMap.put(availableLocale, title);
		}

		return localizationMap;
	}

	private Document _createWorkflowMetricsInstanceDocument(
		long companyId, boolean completed, long instanceId, long processId) {

		Document document = new DocumentImpl();

		document.addUID(
			"WorkflowMetricsInstance",
			_digest(companyId, processId, instanceId));
		document.addKeyword("companyId", companyId);
		document.addKeyword("completed", completed);
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
		long companyId, long processId, String version, String title) {

		Document document = new DocumentImpl();

		document.addUID(
			"WorkflowMetricsProcess", _digest(companyId, processId));
		document.addKeyword("active", true);
		document.addKeyword("companyId", companyId);
		document.addKeyword("deleted", false);
		document.addKeyword("name", RandomTestUtil.randomString());
		document.addKeyword("processId", processId);
		document.addLocalizedKeyword(
			"title", _createLocalizationMap(title), false, true);
		document.addKeyword("version", version);

		return document;
	}

	private Document _creatWorkflowMetricsSLAProcessResultDocument(
		long companyId, long processId, long instanceId, long slaDefinitionId,
		boolean onTime) {

		Document document = new DocumentImpl();

		document.addUID(
			"WorkflowMetricsSLAProcessResult",
			_digest(companyId, instanceId, processId, processId));
		document.addKeyword("companyId", companyId);
		document.addKeyword("deleted", false);
		document.addKeyword("elapsedTime", onTime ? 1000 : -1000);
		document.addKeyword("instanceId", instanceId);
		document.addKeyword("onTime", onTime);
		document.addKeyword("processId", processId);
		document.addKeyword("slaDefinitionId", slaDefinitionId);
		document.addKeyword("status", "RUNNING");

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

		BundleContext bundleContext = bundle.getBundleContext();

		int count = 0;

		ServiceReference<?> serviceReference = null;

		do {
			ServiceReference<?>[] serviceReferences =
				bundleContext.getServiceReferences(
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

		Object indexer = bundleContext.getService(serviceReference);

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

	private static final String _CLASS_NAME_INSTANCE_INDEXER =
		"com.liferay.portal.workflow.metrics.internal.search.index." +
			"InstanceWorkflowMetricsIndexer";

	private static final String _CLASS_NAME_NODE_INDEXER =
		"com.liferay.portal.workflow.metrics.internal.search.index." +
			"NodeWorkflowMetricsIndexer";

	private static final String _CLASS_NAME_PROCESS_INDEXER =
		"com.liferay.portal.workflow.metrics.internal.search.index." +
			"ProcessWorkflowMetricsIndexer";

	private static final String _CLASS_NAME_SLA_PROCESS_RESULT_INDEXER =
		"com.liferay.portal.workflow.metrics.internal.search.index." +
			"SLAProcessResultWorkflowMetricsIndexer";

	private static Map<String, Object> _indexers = new HashMap<>();

	private final Queries _queries;
	private final SearchEngineAdapter _searchEngineAdapter;

}