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

	public Instance addInstance(
			long companyId, Date dateCompletion, long processId)
		throws Exception {

		Instance instance = new Instance();

		instance.setDateCompletion(dateCompletion);
		instance.setId(RandomTestUtil.randomLong());
		instance.setProcessId(processId);

		return addInstance(companyId, instance);
	}

	public Instance addInstance(long companyId, Instance instance)
		throws Exception {

		_invokeAddDocument(
			_getIndexer(_CLASS_NAME_INSTANCE_INDEXER),
			_createWorkflowMetricsInstanceDocument(
				instance.getAssetTitle(), instance.getAssetType(), companyId,
				instance.getDateCompletion() != null,
				instance.getDateCompletion(), instance.getDateCreated(),
				instance.getId(), instance.getProcessId()));

		_retryAssertCount(
			"workflow-metrics-instances", "companyId", companyId, "deleted",
			false, "instanceId", instance.getId(), "processId",
			instance.getProcessId());

		return instance;
	}

	public Node addNode(long companyId, long processId, String version)
		throws Exception {

		Node node = new Node();

		node.setId(RandomTestUtil.randomLong());
		node.setName(RandomTestUtil.randomString());

		return addNode(companyId, node, processId, version);
	}

	public Node addNode(
			long companyId, Node node, long processId, String version)
		throws Exception {

		_invokeAddDocument(
			_getIndexer(_CLASS_NAME_NODE_INDEXER),
			_createWorkflowMetricsNodeDocument(
				companyId, node.getName(), node.getId(), processId, "STATE",
				version));

		_retryAssertCount(
			"workflow-metrics-nodes", "companyId", companyId, "deleted", false,
			"name", node.getName(), "processId", processId, "version", version);

		return node;
	}

	public Process addProcess(long companyId) throws Exception {
		return addProcess(companyId, "1.0");
	}

	public Process addProcess(long companyId, Process process)
		throws Exception {

		return addProcess(companyId, process, "1.0");
	}

	public Process addProcess(long companyId, Process process, String version)
		throws Exception {

		_invokeAddDocument(
			_getIndexer(_CLASS_NAME_PROCESS_INDEXER),
			_createWorkflowMetricsProcessDocument(
				companyId, process.getId(), process.getTitle(), version));

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
			"workflow-metrics-processes", "companyId", companyId, "deleted",
			false, "processId", process.getId());

		return process;
	}

	public Process addProcess(long companyId, String version) throws Exception {
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

		return addProcess(companyId, process, version);
	}

	public void addSLAProcessResult(
			long companyId, Instance instance, boolean onTime)
		throws Exception {

		long slaDefinitionId = RandomTestUtil.randomLong();

		_invokeAddDocument(
			_getIndexer(_CLASS_NAME_SLA_PROCESS_RESULT_INDEXER),
			_creatWorkflowMetricsSLAProcessResultDocument(
				companyId, instance.getId(), onTime, instance.getProcessId(),
				slaDefinitionId));

		_retryAssertCount(
			"workflow-metrics-sla-process-results", "companyId", companyId,
			"deleted", false, "instanceId", instance.getId(), "onTime", onTime,
			"processId", instance.getProcessId(), "slaDefinitionId",
			slaDefinitionId);
	}

	public void addSLATaskResult(
			long companyId, Instance instance, boolean onTime, long taskId,
			String taskName)
		throws Exception {

		long slaDefinitionId = RandomTestUtil.randomLong();

		_invokeAddDocument(
			_getIndexer(_CLASS_NAME_SLA_TASK_RESULT_INDEXER),
			_creatWorkflowMetricsSLATaskResultDocument(
				companyId, instance.getId(), onTime, instance.getProcessId(),
				slaDefinitionId, taskId, taskName));

		_retryAssertCount(
			"workflow-metrics-sla-task-results", "companyId", companyId,
			"deleted", false, "instanceId", instance.getId(), "onTime", onTime,
			"processId", instance.getProcessId(), "slaDefinitionId",
			slaDefinitionId, "taskId", taskId, "taskName", taskName);
	}

	public Task addTask(
			long companyId, long processId, Task task, String version)
		throws Exception {

		long taskId = RandomTestUtil.randomLong();

		_invokeAddDocument(
			_getIndexer(_CLASS_NAME_NODE_INDEXER),
			_createWorkflowMetricsNodeDocument(
				companyId, task.getKey(), taskId, processId, "TASK", version));

		Long onTimeInstanceCount = task.getOnTimeInstanceCount();
		Long overdueInstanceCount = task.getOverdueInstanceCount();

		for (int i = 0; i < task.getInstanceCount(); i++) {
			Instance instance = addInstance(companyId, false, processId);

			if (onTimeInstanceCount > 0) {
				addSLATaskResult(
					companyId, instance, true, taskId, task.getKey());

				onTimeInstanceCount--;
			}
			else if (overdueInstanceCount > 0) {
				addSLATaskResult(
					companyId, instance, false, taskId, task.getKey());

				overdueInstanceCount--;
			}

			addToken(
				companyId, task.getDurationAvg(), instance, taskId,
				task.getKey());
		}

		_retryAssertCount(
			"workflow-metrics-nodes", "companyId", companyId, "deleted", false,
			"name", task.getKey(), "processId", processId);

		return task;
	}

	public void addToken(
			long companyId, long durationAvg, Instance instance, long taskId,
			String taskName)
		throws Exception {

		long tokenId = RandomTestUtil.randomLong();

		_invokeAddDocument(
			_getIndexer(_CLASS_NAME_TOKEN_INDEXER),
			_creatWorkflowMetricsTokenDocument(
				companyId, durationAvg, instance.getId(),
				instance.getProcessId(), taskId, taskName, tokenId, "1.0"));

		_retryAssertCount(
			"workflow-metrics-tokens", "companyId", companyId, "deleted", false,
			"instanceId", instance.getId(), "processId",
			instance.getProcessId(), "taskId", taskId, "taskName", taskName,
			"tokenId", tokenId);
	}

	public void deleteInstance(long companyId, Instance instance)
		throws Exception {

		_invokeDeleteDocument(
			_getIndexer(_CLASS_NAME_INSTANCE_INDEXER),
			_createWorkflowMetricsInstanceDocument(
				instance.getAssetTitle(), instance.getAssetType(), companyId,
				instance.getDateCompletion() != null,
				instance.getDateCompletion(), instance.getDateCreated(),
				instance.getId(), instance.getProcessId()));

		_retryAssertCount(
			"workflow-metrics-instances", "companyId", companyId, "deleted",
			true, "instanceId", instance.getId(), "processId",
			instance.getProcessId());
	}

	public void deleteNode(long companyId, Node node, long processId)
		throws Exception {

		_invokeDeleteDocument(
			_getIndexer(_CLASS_NAME_NODE_INDEXER),
			_createWorkflowMetricsNodeDocument(
				companyId, node.getName(), node.getId(), processId, "STATE",
				"1.0"));

		_retryAssertCount(
			"workflow-metrics-nodes", "companyId", companyId, "deleted", true,
			"name", node.getName(), "processId", processId);
	}

	public void deleteProcess(Document document) throws Exception {
		_invokeDeleteDocument(
			_getIndexer(_CLASS_NAME_PROCESS_INDEXER), document);

		_retryAssertCount(
			"workflow-metrics-processes", "companyId",
			document.get("companyId"), "deleted", true, "processId",
			document.get("processId"));
	}

	public void deleteProcess(long companyId, Process process)
		throws Exception {

		deleteProcess(
			_createWorkflowMetricsProcessDocument(
				companyId, process.getId(), process.getTitle(), "1.0"));
	}

	public void deleteTask(long companyId, long processId, Task task)
		throws Exception {

		_invokeDeleteDocument(
			_getIndexer(_CLASS_NAME_NODE_INDEXER),
			_createWorkflowMetricsNodeDocument(
				companyId, task.getKey(), 0, processId, "TASK", "1.0"));

		_retryAssertCount(
			"workflow-metrics-nodes", "companyId", companyId, "deleted", true,
			"name", task.getKey(), "processId", processId);
	}

	public Document[] getDocuments(long companyId) throws Exception {
		if (_searchEngineAdapter == null) {
			return new Document[0];
		}

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
						_queries.term("deleted", Boolean.FALSE)));

				searchSearchRequest.setSize(10000);

				SearchSearchResponse searchSearchResponse =
					_searchEngineAdapter.execute(searchSearchRequest);

				Hits hits = searchSearchResponse.getHits();

				Assert.assertNotEquals(hits.toString(), 0, hits.getLength());

				return hits.getDocs();
			});
	}

	public void restoreProcess(Document document) throws Exception {
		document.addKeyword("deleted", false);

		_invokeAddDocument(_getIndexer(_CLASS_NAME_PROCESS_INDEXER), document);

		_retryAssertCount(
			"workflow-metrics-processes", "companyId",
			document.get("companyId"), "deleted", false, "processId",
			document.get("processId"));
	}

	public void updateProcess(long companyId, long processId, String version)
		throws Exception {

		_invokeUpdateDocument(
			_getIndexer(_CLASS_NAME_PROCESS_INDEXER),
			_createWorkflowMetricsProcessDocument(
				companyId, processId, null, version));

		_retryAssertCount(
			"workflow-metrics-processes", "companyId", companyId, "deleted",
			false, "processId", processId, "version", version);
	}

	private Map<Locale, String> _createLocalizationMap(String title) {
		Map<Locale, String> localizationMap = new HashMap<>();

		for (Locale availableLocale : LanguageUtil.getAvailableLocales()) {
			localizationMap.put(availableLocale, title);
		}

		return localizationMap;
	}

	private Document _createWorkflowMetricsInstanceDocument(
		String assetTitle, String assetType, long companyId, boolean completed,
		Date completionDate, Date createDate, long instanceId, long processId) {

		Document document = new DocumentImpl();

		document.addUID(
			"WorkflowMetricsInstance",
			_digest(companyId, processId, instanceId));
		document.addLocalizedKeyword(
			"assetTitle", _createLocalizationMap(assetTitle), false, true);
		document.addLocalizedKeyword(
			"assetType", _createLocalizationMap(assetType), false, true);
		document.addKeyword("companyId", companyId);
		document.addKeyword("completed", completed);
		document.addDateSortable("completionDate", completionDate);
		document.addDateSortable("createDate", createDate);
		document.addKeyword("deleted", false);
		document.addKeyword("instanceId", instanceId);
		document.addKeyword("processId", processId);
		document.addKeyword("version", "1.0");

		return document;
	}

	private Document _createWorkflowMetricsNodeDocument(
		long companyId, String name, long nodeId, long processId, String type,
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
		long companyId, long processId, String title, String version) {

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
		long companyId, long instanceId, boolean onTime, long processId,
		long slaDefinitionId) {

		Document document = new DocumentImpl();

		document.addUID(
			"WorkflowMetricsSLAProcessResult",
			_digest(companyId, instanceId, processId, slaDefinitionId));
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

	private Document _creatWorkflowMetricsSLATaskResultDocument(
		long companyId, long instanceId, boolean onTime, long processId,
		long slaDefinitionId, long taskId, String taskName) {

		Document document = new DocumentImpl();

		document.addUID(
			"WorkflowMetricsSLATaskResult",
			_digest(companyId, instanceId, processId, slaDefinitionId, taskId));
		document.addKeyword("companyId", companyId);
		document.addKeyword("deleted", false);
		document.addKeyword("elapsedTime", onTime ? 1000 : -1000);
		document.addKeyword("instanceId", instanceId);
		document.addKeyword("onTime", onTime);
		document.addKeyword("processId", processId);
		document.addKeyword("slaDefinitionId", slaDefinitionId);
		document.addKeyword("status", "RUNNING");
		document.addKeyword("taskId", taskId);
		document.addKeyword("taskName", taskName);

		return document;
	}

	private Document _creatWorkflowMetricsTokenDocument(
		long companyId, long durationAvg, long instanceId, long processId,
		long taskId, String taskName, long tokenId, String version) {

		Document document = new DocumentImpl();

		document.addUID(
			"WorkflowMetricsToken",
			_digest(companyId, instanceId, processId, taskId, tokenId));
		document.addKeyword("companyId", companyId);
		document.addKeyword("completed", durationAvg > 0);
		document.addDateSortable(
			"completionDate", (durationAvg > 0) ? new Date() : null);
		document.addKeyword("deleted", false);
		document.addKeyword("duration", durationAvg);
		document.addKeyword("instanceId", instanceId);
		document.addKeyword("processId", processId);
		document.addKeyword("taskId", taskId);
		document.addKeyword("taskName", taskName);
		document.addKeyword("tokenId", tokenId);
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

		while ((indexerClass != Object.class) && (method == null)) {
			try {
				method = ReflectionUtil.getDeclaredMethod(
					indexerClass, methodName, Document.class);
			}
			catch (NoSuchMethodException nsme) {
			}

			indexerClass = indexerClass.getSuperclass();
		}

		method.invoke(indexer, document);
	}

	private void _invokeUpdateDocument(Object indexer, Document document)
		throws Exception {

		_invokeMethod(indexer, "updateDocument", document);
	}

	private void _retryAssertCount(String indexName, Object... parameters)
		throws Exception {

		if (_searchEngineAdapter == null) {
			return;
		}

		if (parameters != null) {
			if ((parameters.length % 2) != 0) {
				throw new IllegalArgumentException(
					"Parameters length is not an even number");
			}

			IdempotentRetryAssert.retryAssert(
				10, TimeUnit.SECONDS,
				() -> {
					CountSearchRequest countSearchRequest =
						new CountSearchRequest();

					countSearchRequest.setIndexNames(indexName);

					BooleanQuery booleanQuery = _queries.booleanQuery();

					for (int i = 0; i < parameters.length; i = i + 2) {
						booleanQuery.addMustQueryClauses(
							_queries.term(
								String.valueOf(parameters[i]),
								parameters[i + 1]));
					}

					countSearchRequest.setQuery(booleanQuery);

					CountSearchResponse countSearchResponse =
						_searchEngineAdapter.execute(countSearchRequest);

					Assert.assertEquals(1, countSearchResponse.getCount());

					return null;
				});
		}
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

	private static final String _CLASS_NAME_SLA_TASK_RESULT_INDEXER =
		"com.liferay.portal.workflow.metrics.internal.search.index." +
			"SLATaskResultWorkflowMetricsIndexer";

	private static final String _CLASS_NAME_TOKEN_INDEXER =
		"com.liferay.portal.workflow.metrics.internal.search.index." +
			"TokenWorkflowMetricsIndexer";

	private static Map<String, Object> _indexers = new HashMap<>();

	private final Queries _queries;
	private final SearchEngineAdapter _searchEngineAdapter;

}