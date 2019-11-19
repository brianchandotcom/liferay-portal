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

import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.document.DocumentBuilderFactory;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.document.UpdateDocumentRequest;
import com.liferay.portal.search.engine.adapter.search.CountSearchRequest;
import com.liferay.portal.search.engine.adapter.search.CountSearchResponse;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.test.util.IdempotentRetryAssert;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.CreatorUser;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Instance;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Node;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Process;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Task;

import java.io.Serializable;

import java.lang.reflect.Method;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

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
		DocumentBuilderFactory documentBuilderFactory, Queries queries,
		SearchEngineAdapter searchEngineAdapter) {

		_documentBuilderFactory = documentBuilderFactory;
		_queries = queries;
		_searchEngineAdapter = searchEngineAdapter;
	}

	public Instance addInstance(
			long companyId, boolean completed, long processId)
		throws Exception {

		Instance instance = new Instance();

		instance.setCreatorUser(new CreatorUser());

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

		instance.setCreatorUser(new CreatorUser());
		instance.setDateCompletion(dateCompletion);
		instance.setId(RandomTestUtil.randomLong());
		instance.setProcessId(processId);

		return addInstance(companyId, instance);
	}

	public Instance addInstance(long companyId, Instance instance)
		throws Exception {

		CreatorUser creatorUser = instance.getCreatorUser();

		_invokeAddDocument(
			_getIndexer(_CLASS_NAME_INSTANCE_INDEXER),
			_createWorkflowMetricsInstanceDocument(
				instance.getAssetTitle(), instance.getAssetType(), companyId,
				instance.getDateCompletion() != null,
				instance.getDateCompletion(), instance.getDateCreated(),
				instance.getId(), instance.getProcessId(), creatorUser.getId(),
				creatorUser.getName()));

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
			long assigneeId, boolean breached, long companyId,
			Instance instance, boolean onTime, String status, long taskId,
			String taskName)
		throws Exception {

		long slaDefinitionId = RandomTestUtil.randomLong();
		long tokenId = RandomTestUtil.randomLong();

		_invokeAddDocument(
			_getIndexer(_CLASS_NAME_SLA_TASK_RESULT_INDEXER),
			_creatWorkflowMetricsSLATaskResultDocument(
				assigneeId, breached, companyId,
				Objects.nonNull(instance.getDateCompletion()), instance.getId(),
				onTime, instance.getProcessId(), slaDefinitionId, status,
				taskId, taskName, tokenId));

		_retryAssertCount(
			"workflow-metrics-sla-task-results", "breached", breached,
			"assigneeId", assigneeId, "companyId", companyId, "deleted", false,
			"instanceCompleted", Objects.nonNull(instance.getDateCompletion()),
			"instanceId", instance.getId(), "onTime", onTime, "processId",
			instance.getProcessId(), "slaDefinitionId", slaDefinitionId,
			"taskId", taskId, "taskName", taskName);
	}

	public Task addTask(long assigneeId, long companyId, long processId)
		throws Exception {

		String randomString = RandomTestUtil.randomString();

		Task task = new Task() {
			{
				durationAvg = 0L;
				instanceCount = 1L;
				key = randomString;
				name = randomString;
				onTimeInstanceCount = 0L;
				overdueInstanceCount = 0L;
			}
		};

		return addTask(
			assigneeId, companyId, processId, "RUNNING", task, "1.0");
	}

	public Task addTask(
			long assigneeId, long companyId, long processId, String status,
			Task task, String version)
		throws Exception {

		return addTask(
			assigneeId, companyId,
			() -> addInstance(companyId, false, processId), processId, status,
			task, version);
	}

	public Task addTask(
			long assigneeId, long companyId,
			UnsafeSupplier<Instance, Exception> instanceSuplier, long processId,
			String status, Task task, String version)
		throws Exception {

		long taskId = RandomTestUtil.randomLong();

		_invokeAddDocument(
			_getIndexer(_CLASS_NAME_NODE_INDEXER),
			_createWorkflowMetricsNodeDocument(
				companyId, task.getKey(), taskId, processId, "TASK", version));

		Long onTimeInstanceCount = task.getOnTimeInstanceCount();
		Long overdueInstanceCount = task.getOverdueInstanceCount();

		for (int i = 0; i < task.getInstanceCount(); i++) {
			Instance instance = instanceSuplier.get();

			if (onTimeInstanceCount > 0) {
				addSLATaskResult(
					assigneeId, false, companyId, instance, true, status,
					taskId, task.getKey());

				onTimeInstanceCount--;
			}
			else if (overdueInstanceCount > 0) {
				addSLATaskResult(
					assigneeId, true, companyId, instance, false, status,
					taskId, task.getKey());

				overdueInstanceCount--;
			}

			addToken(
				assigneeId, companyId, task.getDurationAvg(), instance, taskId,
				task.getKey());
		}

		_retryAssertCount(
			"workflow-metrics-nodes", "companyId", companyId, "deleted", false,
			"name", task.getKey(), "processId", processId);

		return task;
	}

	public void addToken(long assigneeId, long companyId, Instance instance)
		throws Exception {

		addToken(
			assigneeId, companyId, 0L, instance, RandomTestUtil.randomLong(),
			RandomTestUtil.randomString());
	}

	public void addToken(
			long assigneeId, long companyId, long durationAvg,
			boolean instanceCompleted, long instanceId, long processId,
			long taskId, String taskName)
		throws Exception {

		long tokenId = RandomTestUtil.randomLong();

		_invokeAddDocument(
			_getIndexer(_CLASS_NAME_TOKEN_INDEXER),
			_creatWorkflowMetricsTokenDocument(
				assigneeId, companyId, durationAvg, instanceCompleted,
				instanceId, processId, taskId, taskName, tokenId, "1.0"));

		_retryAssertCount(
			"workflow-metrics-tokens", "assigneeId", assigneeId, "companyId",
			companyId, "deleted", false, "instanceCompleted", instanceCompleted,
			"instanceId", instanceId, "processId", processId, "taskId", taskId,
			"taskName", taskName, "tokenId", tokenId);
	}

	public void addToken(
			long assigneeId, long companyId, long durationAvg,
			Instance instance, long taskId, String taskName)
		throws Exception {

		addToken(
			assigneeId, companyId, durationAvg,
			Objects.nonNull(instance.getDateCompletion()), instance.getId(),
			instance.getProcessId(), taskId, taskName);
	}

	public void deleteInstance(long companyId, Instance instance)
		throws Exception {

		CreatorUser creatorUser = instance.getCreatorUser();

		_invokeDeleteDocument(
			_getIndexer(_CLASS_NAME_INSTANCE_INDEXER),
			_createWorkflowMetricsInstanceDocument(
				instance.getAssetTitle(), instance.getAssetType(), companyId,
				instance.getDateCompletion() != null,
				instance.getDateCompletion(), instance.getDateCreated(),
				instance.getId(), instance.getProcessId(), creatorUser.getId(),
				creatorUser.getName()));

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

	public void deleteSLATaskResults(long companyId, long processId)
		throws Exception {

		_deleteDocuments(
			"workflow-metrics-sla-task-results",
			"WorkflowMetricsSLATaskResultType", "companyId", companyId,
			"processId", processId);
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

	public void deleteTokens(long companyId, long processId) throws Exception {
		_deleteDocuments(
			"workflow-metrics-tokens", "WorkflowMetricsTokenType", "companyId",
			companyId, "processId", processId);
	}

	public Document[] getDocuments(long companyId) throws Exception {
		if (_searchEngineAdapter == null) {
			return new Document[0];
		}

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

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

		return hits.getDocs();
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
		Date completionDate, Date createDate, long instanceId, long processId,
		Long userId, String userName) {

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
		document.addKeyword("userId", userId);
		document.addKeyword("userName", userName);
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
		long assigneeId, boolean breached, long companyId,
		boolean instanceCompleted, long instanceId, boolean onTime,
		long processId, long slaDefinitionId, String status, long taskId,
		String taskName, long tokenId) {

		Document document = new DocumentImpl();

		document.addUID(
			"WorkflowMetricsSLATaskResult",
			_digest(companyId, instanceId, processId, slaDefinitionId, taskId));
		document.addKeyword("assigneeId", assigneeId);
		document.addKeyword("breached", breached);
		document.addKeyword("companyId", companyId);
		document.addDateSortable(
			"completionDate",
			Objects.equals(status, "COMPLETED") ? new Date() : null);
		document.addKeyword("deleted", false);
		document.addKeyword("elapsedTime", onTime ? 1000 : -1000);
		document.addKeyword("instanceCompleted", instanceCompleted);
		document.addKeyword("instanceId", instanceId);
		document.addKeyword("onTime", onTime);
		document.addKeyword("processId", processId);
		document.addKeyword("slaDefinitionId", slaDefinitionId);
		document.addKeyword("status", status);
		document.addKeyword("taskId", taskId);
		document.addKeyword("taskName", taskName);
		document.addKeyword("tokenId", tokenId);

		return document;
	}

	private Document _creatWorkflowMetricsTokenDocument(
		long assigneeId, long companyId, long durationAvg,
		boolean instanceCompleted, long instanceId, long processId, long taskId,
		String taskName, long tokenId, String version) {

		Document document = new DocumentImpl();

		document.addUID(
			"WorkflowMetricsToken",
			_digest(companyId, instanceId, processId, taskId, tokenId));
		document.addKeyword("assigneeId", assigneeId);
		document.addKeyword("companyId", companyId);
		document.addKeyword("completed", durationAvg > 0);
		document.addDateSortable(
			"completionDate", (durationAvg > 0) ? new Date() : null);
		document.addKeyword("deleted", false);
		document.addKeyword("duration", durationAvg);
		document.addKeyword("instanceCompleted", instanceCompleted);
		document.addKeyword("instanceId", instanceId);
		document.addKeyword("processId", processId);
		document.addKeyword("taskId", taskId);
		document.addKeyword("taskName", taskName);
		document.addKeyword("tokenId", tokenId);
		document.addKeyword("version", version);

		return document;
	}

	private void _deleteDocuments(
			String indexName, String indexType, Object... parameters)
		throws Exception {

		if (_searchEngineAdapter == null) {
			return;
		}

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.setIndexNames(indexName);

		BooleanQuery booleanQuery = _queries.booleanQuery();

		for (int j = 0; j < parameters.length; j = j + 2) {
			booleanQuery.addMustQueryClauses(
				_queries.term(
					String.valueOf(parameters[j]), parameters[j + 1]));
		}

		searchSearchRequest.setQuery(booleanQuery);

		searchSearchRequest.setSize(10000);
		searchSearchRequest.setTypes(indexType);

		SearchSearchResponse searchSearchResponse =
			_searchEngineAdapter.execute(searchSearchRequest);

		Stream.of(
			searchSearchResponse
		).map(
			SearchSearchResponse::getSearchHits
		).map(
			SearchHits::getSearchHits
		).flatMap(
			List::stream
		).map(
			SearchHit::getDocument
		).map(
			_documentBuilderFactory::builder
		).map(
			documentBuilder -> {
				documentBuilder.setValue("deleted", true);

				return documentBuilder.build();
			}
		).forEach(
			document -> {
				UpdateDocumentRequest updateDocumentRequest =
					new UpdateDocumentRequest(
						indexName, document.getString("uid"), document);

				updateDocumentRequest.setType(indexType);

				_searchEngineAdapter.execute(updateDocumentRequest);
			}
		);

		_retryAssertCount(
			searchSearchResponse.getCount(), indexName,
			ArrayUtil.append(new Object[] {"deleted", true}, parameters));
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

	private void _retryAssertCount(
			long expectedCount, String indexName, Object... parameters)
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

					Assert.assertEquals(
						expectedCount, countSearchResponse.getCount());

					return null;
				});
		}
	}

	private void _retryAssertCount(String indexName, Object... parameters)
		throws Exception {

		_retryAssertCount(1, indexName, parameters);
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

	private final DocumentBuilderFactory _documentBuilderFactory;
	private final Queries _queries;
	private final SearchEngineAdapter _searchEngineAdapter;

}