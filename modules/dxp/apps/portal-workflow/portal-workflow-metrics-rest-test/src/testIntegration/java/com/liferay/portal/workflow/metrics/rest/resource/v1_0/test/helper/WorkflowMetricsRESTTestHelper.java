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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.document.DocumentBuilder;
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
import com.liferay.portal.vulcan.util.LocalizedMapUtil;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Creator;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Instance;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Node;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.NodeMetric;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Process;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.ProcessMetric;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Task;
import com.liferay.portal.workflow.metrics.search.index.InstanceWorkflowMetricsIndexer;
import com.liferay.portal.workflow.metrics.search.index.NodeWorkflowMetricsIndexer;
import com.liferay.portal.workflow.metrics.search.index.ProcessWorkflowMetricsIndexer;
import com.liferay.portal.workflow.metrics.search.index.TaskWorkflowMetricsIndexer;

import java.io.Serializable;

import java.lang.reflect.Method;

import java.text.DateFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
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
		DocumentBuilderFactory documentBuilderFactory,
		InstanceWorkflowMetricsIndexer instanceWorkflowMetricsIndexer,
		NodeWorkflowMetricsIndexer nodeWorkflowMetricsIndexer,
		ProcessWorkflowMetricsIndexer processWorkflowMetricsIndexer,
		Queries queries, SearchEngineAdapter searchEngineAdapter,
		TaskWorkflowMetricsIndexer taskWorkflowMetricsIndexer) {

		_documentBuilderFactory = documentBuilderFactory;
		_instanceWorkflowMetricsIndexer = instanceWorkflowMetricsIndexer;
		_nodeWorkflowMetricsIndexer = nodeWorkflowMetricsIndexer;
		_processWorkflowMetricsIndexer = processWorkflowMetricsIndexer;
		_queries = queries;
		_searchEngineAdapter = searchEngineAdapter;
		_taskWorkflowMetricsIndexer = taskWorkflowMetricsIndexer;
	}

	public Instance addInstance(
			long companyId, boolean completed, long processId)
		throws Exception {

		Instance instance = new Instance();

		instance.setCreator(
			new Creator() {
				{
					id = RandomTestUtil.nextLong();
					name = RandomTestUtil.randomString();
				}
			});

		instance.setCompleted(completed);

		if (completed) {
			instance.setDateCompletion(RandomTestUtil.nextDate());
			instance.setDuration(1000L);
		}

		instance.setId(RandomTestUtil.randomLong());
		instance.setProcessId(processId);

		return addInstance(companyId, instance);
	}

	public Instance addInstance(long companyId, Instance instance)
		throws Exception {

		Creator creator = instance.getCreator();

		_instanceWorkflowMetricsIndexer.addInstance(
			_createLocalizationMap(instance.getAssetTitle()),
			_createLocalizationMap(instance.getAssetType()), StringPool.BLANK,
			0, companyId, null,
			Optional.ofNullable(
				instance.getDateCreated()
			).orElseGet(
				Date::new
			),
			instance.getId(),
			Optional.ofNullable(
				instance.getDateModified()
			).orElseGet(
				Date::new
			),
			instance.getProcessId(), instance.getProcessVersion(),
			creator.getId(), creator.getName());

		_retryAssertCount(
			"workflow-metrics-instances", "companyId", companyId, "deleted",
			false, "instanceId", instance.getId(), "processId",
			instance.getProcessId());

		return instance;
	}

	public Instance addInstance(long companyId, long processId)
		throws Exception {

		Instance instance = new Instance();

		instance.setCreator(
			new Creator() {
				{
					id = RandomTestUtil.randomLong();
					name = RandomTestUtil.randomString();
				}
			});
		instance.setId(RandomTestUtil.randomLong());
		instance.setProcessId(processId);

		return addInstance(companyId, instance);
	}

	public Node addNode(long companyId, long processId, String version)
		throws Exception {

		Node node = new Node();

		node.setId(RandomTestUtil.randomLong());
		node.setName(RandomTestUtil.randomString());
		node.setProcessVersion(version);

		return addNode(companyId, node, processId, version);
	}

	public Node addNode(
			long companyId, Node node, long processId, String version)
		throws Exception {

		_nodeWorkflowMetricsIndexer.addNode(
			companyId,
			Optional.ofNullable(
				node.getDateCreated()
			).orElseGet(
				Date::new
			),
			false,
			Optional.ofNullable(
				node.getDateModified()
			).orElseGet(
				Date::new
			),
			node.getName(), node.getId(), processId, version, false,
			Optional.ofNullable(
				node.getType()
			).orElseGet(
				() -> "TASK"
			));

		_retryAssertCount(
			"workflow-metrics-nodes", "companyId", companyId, "deleted", false,
			"name", node.getName(), "processId", processId, "version", version);

		return node;
	}

	public NodeMetric addNodeMetric(
			long assigneeId, long companyId, long processId)
		throws Exception {

		String randomString = RandomTestUtil.randomString();

		NodeMetric nodeMetric = new NodeMetric() {
			{
				durationAvg = 0L;
				instanceCount = 1L;
				node = new Node() {
					{
						id = RandomTestUtil.randomLong();
						label = randomString;
						name = randomString;
					}
				};

				onTimeInstanceCount = 0L;
				overdueInstanceCount = 0L;
			}
		};

		return addNodeMetric(
			assigneeId, companyId, processId, "RUNNING", nodeMetric, "1.0");
	}

	public NodeMetric addNodeMetric(
			long assigneeId, long companyId, long processId, String status,
			NodeMetric task, String version)
		throws Exception {

		return addNodeMetric(
			assigneeId, companyId,
			() -> addInstance(companyId, false, processId), processId, status,
			task, version);
	}

	public NodeMetric addNodeMetric(
			long assigneeId, long companyId,
			UnsafeSupplier<Instance, Exception> instanceSuplier, long processId,
			String status)
		throws Exception {

		String randomString = RandomTestUtil.randomString();

		NodeMetric task = new NodeMetric() {
			{
				durationAvg = Objects.equals(status, "COMPLETED") ? 1000L : 0L;
				instanceCount = 1L;
				node = new Node() {
					{
						id = RandomTestUtil.randomLong();
						label = randomString;
						name = randomString;
					}
				};

				onTimeInstanceCount = 0L;
				overdueInstanceCount = 0L;
			}
		};

		return addNodeMetric(
			assigneeId, companyId, instanceSuplier, processId, status, task,
			"1.0");
	}

	public NodeMetric addNodeMetric(
			long assigneeId, long companyId,
			UnsafeSupplier<Instance, Exception> instanceSuplier, long processId,
			String status, NodeMetric nodeMetric, String version)
		throws Exception {

		Node node = addNode(
			companyId, nodeMetric.getNode(), processId, version);

		Long onTimeInstanceCount = nodeMetric.getOnTimeInstanceCount();
		Long overdueInstanceCount = nodeMetric.getOverdueInstanceCount();

		for (int i = 0; i < nodeMetric.getInstanceCount(); i++) {
			Instance instance = instanceSuplier.get();
			Long taskId = RandomTestUtil.nextLong();

			if (onTimeInstanceCount > 0) {
				addSLATaskResult(
					assigneeId, false, companyId, instance, true, status,
					node.getId(), taskId, node.getName());

				onTimeInstanceCount--;
			}
			else if (overdueInstanceCount > 0) {
				addSLATaskResult(
					assigneeId, true, companyId, instance, false, status,
					node.getId(), taskId, node.getName());

				overdueInstanceCount--;
			}

			addTask(
				assigneeId, companyId, nodeMetric.getDurationAvg(), instance,
				processId, node.getId(), taskId, node.getName());

			if (instance.getCompleted()) {
				completeInstance(companyId, instance);
			}
		}

		_retryAssertCount(
			"workflow-metrics-nodes", "companyId", companyId, "deleted", false,
			"name", node.getName(), "processId", processId);

		return nodeMetric;
	}

	public Process addProcess(long companyId) throws Exception {
		Process process = new Process() {
			{
				id = RandomTestUtil.randomLong();
				title = RandomTestUtil.randomString();
				version = "1.0";
			}
		};

		return addProcess(companyId, process);
	}

	public Process addProcess(long companyId, Process process)
		throws Exception {

		_processWorkflowMetricsIndexer.addProcess(
			Optional.ofNullable(
				process.getActive()
			).orElseGet(
				() -> Boolean.TRUE
			),
			companyId,
			Optional.ofNullable(
				process.getDateCreated()
			).orElseGet(
				Date::new
			),
			process.getDescription(),
			Optional.ofNullable(
				process.getDateModified()
			).orElseGet(
				Date::new
			),
			process.getName(), process.getId(), process.getTitle(),
			LocalizedMapUtil.getLocalizedMap(process.getTitle_i18n()),
			process.getVersion());

		_retryAssertCount(
			"workflow-metrics-processes", "companyId", companyId, "deleted",
			false, "processId", process.getId());

		return process;
	}

	public ProcessMetric addProcessMetric(long companyId) throws Exception {
		return addProcessMetric(companyId, "1.0");
	}

	public ProcessMetric addProcessMetric(
			long companyId, ProcessMetric processMetric)
		throws Exception {

		Process process = addProcess(companyId, processMetric.getProcess());

		Long onTimeInstanceCount = processMetric.getOnTimeInstanceCount();
		Long overdueInstanceCount = processMetric.getOverdueInstanceCount();

		for (int i = 0; i < processMetric.getInstanceCount(); i++) {
			Instance instance = addInstance(companyId, false, process.getId());

			if (onTimeInstanceCount > 0) {
				addSLAInstanceResult(companyId, instance, true);

				onTimeInstanceCount--;
			}
			else if (overdueInstanceCount > 0) {
				addSLAInstanceResult(companyId, instance, false);

				overdueInstanceCount--;
			}
		}

		return processMetric;
	}

	public ProcessMetric addProcessMetric(long companyId, String version)
		throws Exception {

		ProcessMetric processMetric = new ProcessMetric() {
			{
				instanceCount = 0L;
				onTimeInstanceCount = 0L;
				overdueInstanceCount = 0L;

				setProcess(
					() -> {
						Process process = new Process();

						process.setId(RandomTestUtil.randomLong());
						process.setTitle(RandomTestUtil.randomString());
						process.setVersion(version);

						return process;
					});

				untrackedInstanceCount = 0L;
			}
		};

		return addProcessMetric(companyId, processMetric);
	}

	public void addSLAInstanceResult(
			long companyId, Instance instance, boolean onTime)
		throws Exception {

		long slaDefinitionId = RandomTestUtil.randomLong();

		_invokeAddDocument(
			_getIndexer(_CLASS_NAME_SLA_INSTANCE_RESULT_INDEXER),
			_creatWorkflowMetricsSLAInstanceResultDocument(
				companyId, Objects.nonNull(instance.getDateCompletion()),
				instance.getId(), onTime, instance.getProcessId(),
				slaDefinitionId));

		_retryAssertCount(
			"workflow-metrics-sla-instance-results", "companyId", companyId,
			"deleted", false, "instanceCompleted",
			Objects.nonNull(instance.getDateCompletion()), "instanceId",
			instance.getId(), "onTime", onTime, "processId",
			instance.getProcessId(), "slaDefinitionId", slaDefinitionId);
	}

	public void addSLATaskResult(
			long assigneeId, boolean breached, long companyId,
			Instance instance, boolean onTime, String status, long nodeId,
			long taskId, String taskName)
		throws Exception {

		long slaDefinitionId = RandomTestUtil.randomLong();

		_invokeAddDocument(
			_getIndexer(_CLASS_NAME_SLA_TASK_RESULT_INDEXER),
			_creatWorkflowMetricsSLATaskResultDocument(
				assigneeId, breached, companyId,
				Objects.nonNull(instance.getDateCompletion()), instance.getId(),
				onTime, instance.getProcessId(), slaDefinitionId, status,
				nodeId, taskName, taskId));

		_retryAssertCount(
			"workflow-metrics-sla-task-results", "breached", breached,
			"assigneeId", assigneeId, "companyId", companyId, "deleted", false,
			"instanceCompleted", Objects.nonNull(instance.getDateCompletion()),
			"instanceId", instance.getId(), "onTime", onTime, "processId",
			instance.getProcessId(), "slaDefinitionId", slaDefinitionId,
			"taskId", taskId, "taskName", taskName);
	}

	public Task addTask(long companyId, Instance instance, Task task)
		throws Exception {

		_taskWorkflowMetricsIndexer.addTask(
			task.getAssigneeId(), task.getClassName(), task.getClassPK(),
			companyId, false, null, null, task.getDateCreated(), false,
			instance.getId(), task.getDateModified(), task.getName(),
			task.getNodeId(), task.getProcessId(), task.getProcessVersion(),
			task.getId(), 0);

		_retryAssertCount(
			"workflow-metrics-tasks", "companyId", companyId, "deleted", false,
			"instanceId", instance.getId(), "processId", task.getProcessId(),
			"nodeId", task.getNodeId(), "name", task.getName(), "taskId",
			task.getId());

		if (task.getAssigneeId() != 0) {
			_taskWorkflowMetricsIndexer.updateTask(
				task.getAssigneeId(), companyId, new Date(), task.getId(), 0);

			_retryAssertCount(
				"workflow-metrics-tasks", "assigneeId", task.getAssigneeId(),
				"companyId", companyId, "deleted", false, "instanceId",
				instance.getId(), "processId", task.getProcessId(), "nodeId",
				task.getNodeId(), "name", task.getName(), "taskId",
				task.getId());
		}

		if (task.getCompleted()) {
			_taskWorkflowMetricsIndexer.completeTask(
				companyId, task.getDateCompletion(), task.getCompletionUserId(),
				task.getDuration(), task.getDateModified(), task.getId(), 0);

			_retryAssertCount(
				"workflow-metrics-tasks", "companyId", companyId, "completed",
				true, "completionUserId", task.getCompletionUserId(), "deleted",
				false, "duration", task.getDuration(), "instanceId",
				instance.getId(), "processId", task.getProcessId(), "nodeId",
				task.getNodeId(), "name", task.getName(), "taskId",
				task.getId());
		}

		return task;
	}

	public Task addTask(long assigneeId, long companyId, Instance instance)
		throws Exception {

		return addTask(
			assigneeId, companyId, 0L, instance, instance.getProcessId(),
			RandomTestUtil.randomLong(), RandomTestUtil.randomLong(),
			RandomTestUtil.randomString());
	}

	public Task addTask(
			long assigneeId, long companyId, long durationAvg,
			Instance instance, long processId, long nodeId, long taskId,
			String name)
		throws Exception {

		Task task = new Task();

		task.setAssigneeId(assigneeId);
		task.setClassName(StringPool.BLANK);
		task.setClassPK(0L);
		task.setCompleted(durationAvg > 0);
		task.setDateCompletion((durationAvg > 0) ? new Date() : null);
		task.setCompletionUserId((durationAvg > 0) ? assigneeId : null);
		task.setDateCreated(new Date());
		task.setDateModified(new Date());
		task.setDuration(durationAvg);
		task.setId(taskId);
		task.setInstanceId(instance.getId());
		task.setName(name);
		task.setNodeId(nodeId);
		task.setProcessId(processId);
		task.setProcessVersion("1.0");

		return addTask(companyId, instance, task);
	}

	public void completeInstance(long companyId, Instance instance)
		throws Exception {

		_instanceWorkflowMetricsIndexer.completeInstance(
			companyId,
			Optional.ofNullable(
				instance.getDateCompletion()
			).orElseGet(
				Date::new
			),
			Optional.ofNullable(
				instance.getDuration()
			).orElse(
				1000L
			),
			instance.getId(),
			Optional.ofNullable(
				instance.getDateModified()
			).orElseGet(
				Date::new
			));

		_retryAssertCount(
			"workflow-metrics-instances", "companyId", companyId, "completed",
			true, "deleted", false, "instanceId", instance.getId(), "processId",
			instance.getProcessId());
	}

	public Map<String, String> createI18nMap(String value) {
		Map<String, String> localizationMap = new HashMap<>();

		for (Locale availableLocale : LanguageUtil.getAvailableLocales()) {
			localizationMap.put(availableLocale.toLanguageTag(), value);
		}

		return localizationMap;
	}

	public void deleteInstance(long companyId, Instance instance)
		throws Exception {

		_instanceWorkflowMetricsIndexer.deleteInstance(
			companyId, instance.getId());

		_retryAssertCount(
			"workflow-metrics-instances", "companyId", companyId, "deleted",
			true, "instanceId", instance.getId(), "processId",
			instance.getProcessId());
	}

	public void deleteNode(long companyId, Node node, long processId)
		throws Exception {

		_nodeWorkflowMetricsIndexer.deleteNode(companyId, node.getId());

		_retryAssertCount(
			"workflow-metrics-nodes", "companyId", companyId, "deleted", true,
			"name", node.getName(), "processId", processId);
	}

	public void deleteProcess(long companyId, long processId) throws Exception {
		_processWorkflowMetricsIndexer.deleteProcess(companyId, processId);

		_retryAssertCount(
			"workflow-metrics-processes", "companyId", companyId, "deleted",
			true, "processId", processId);
	}

	public void deleteProcess(long companyId, Process process)
		throws Exception {

		deleteProcess(companyId, process.getId());
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

		_nodeWorkflowMetricsIndexer.deleteNode(companyId, task.getNodeId());

		_retryAssertCount(
			"workflow-metrics-nodes", "companyId", companyId, "deleted", true,
			"name", task.getName(), "processId", processId);
	}

	public void deleteTasks(long companyId, long processId) throws Exception {
		_deleteDocuments(
			"workflow-metrics-tasks", "WorkflowMetricsTaskType", "companyId",
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

		return Stream.of(
			searchSearchResponse.getSearchHits()
		).map(
			SearchHits::getSearchHits
		).flatMap(
			List::stream
		).map(
			SearchHit::getDocument
		).toArray(
			Document[]::new
		);
	}

	public void restoreProcess(Document document) throws Exception {
		_processWorkflowMetricsIndexer.addProcess(
			document.getBoolean("active"), document.getLong("companyId"),
			_parseDate(document.getDate("createDate")),
			document.getString("description"),
			_parseDate(document.getDate("modifiedDate")),
			document.getString("name"), document.getLong("processId"),
			document.getString("title"),
			_createLocalizationMap(document.getString("title")),
			document.getString("version"));

		_retryAssertCount(
			"workflow-metrics-processes", "companyId",
			document.getLong("companyId"), "deleted", false, "processId",
			document.getLong("processId"));
	}

	public void updateProcess(long companyId, long processId, String version)
		throws Exception {

		_processWorkflowMetricsIndexer.updateProcess(
			Optional.empty(), companyId, Optional.empty(), new Date(),
			processId, Optional.empty(), Optional.empty(),
			Optional.of(version));

		_retryAssertCount(
			"workflow-metrics-processes", "companyId", companyId, "deleted",
			false, "processId", processId, "version", version);
	}

	protected String formatDate(Date date) {
		DateFormat dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			"yyyyMMddHHmmss");

		try {
			return dateFormat.format(date);
		}
		catch (Exception exception) {
			return null;
		}
	}

	private Map<Locale, String> _createLocalizationMap(String value) {
		Map<Locale, String> localizationMap = new HashMap<>();

		for (Locale availableLocale : LanguageUtil.getAvailableLocales()) {
			localizationMap.put(availableLocale, value);
		}

		return localizationMap;
	}

	private Document _creatWorkflowMetricsSLAInstanceResultDocument(
		long companyId, boolean instanceCompleted, long instanceId,
		boolean onTime, long processId, long slaDefinitionId) {

		DocumentBuilder documentBuilder = _documentBuilderFactory.builder();

		documentBuilder.setString(
			Field.UID,
			"WorkflowMetricsSLAInstanceResult" +
				_digest(companyId, instanceId, processId, slaDefinitionId)
		).setValue(
			"companyId", companyId
		).setValue(
			"deleted", false
		).setValue(
			"elapsedTime", onTime ? 1000 : -1000
		).setValue(
			"instanceCompleted", instanceCompleted
		).setValue(
			"instanceId", instanceId
		).setValue(
			"onTime", onTime
		).setValue(
			"processId", processId
		).setValue(
			"slaDefinitionId", slaDefinitionId
		).setValue(
			"status", "RUNNING"
		);

		return documentBuilder.build();
	}

	private Document _creatWorkflowMetricsSLATaskResultDocument(
		long assigneeId, boolean breached, long companyId,
		boolean instanceCompleted, long instanceId, boolean onTime,
		long processId, long slaDefinitionId, String status, long nodeId,
		String taskName, long taskId) {

		DocumentBuilder documentBuilder = _documentBuilderFactory.builder();

		String digest = _digest(
			companyId, instanceId, processId, slaDefinitionId, taskId);

		documentBuilder.setString(
			Field.UID, "WorkflowMetricsSLATaskResult" + digest
		).setValue(
			"assigneeId", assigneeId
		).setValue(
			"breached", breached
		).setValue(
			"companyId", companyId
		);

		if (Objects.equals(status, "COMPLETED")) {
			documentBuilder.setDate(
				"completionDate", formatDate(new Date())
			).setValue(
				"completionUserId", assigneeId
			);
		}

		documentBuilder.setValue(
			"deleted", false
		).setValue(
			"elapsedTime", onTime ? 1000 : -1000
		).setValue(
			"instanceCompleted", instanceCompleted
		).setValue(
			"instanceId", instanceId
		).setValue(
			"onTime", onTime
		).setValue(
			"processId", processId
		).setValue(
			"slaDefinitionId", slaDefinitionId
		).setValue(
			"status", status
		).setValue(
			"nodeId", nodeId
		).setValue(
			"taskName", taskName
		).setValue(
			"taskId", taskId
		);

		return documentBuilder.build();
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

				updateDocumentRequest.setRefresh(true);
				updateDocumentRequest.setType(indexType);
				updateDocumentRequest.setUpsert(true);

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
			catch (NoSuchMethodException noSuchMethodException) {
			}

			indexerClass = indexerClass.getSuperclass();
		}

		method.invoke(indexer, document);
	}

	private Date _parseDate(String formattedDate) {
		DateFormat dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			"yyyyMMddHHmmss");

		try {
			return dateFormat.parse(formattedDate);
		}
		catch (Exception exception) {
			return new Date();
		}
	}

	private void _retryAssertCount(
			long expectedCount, String indexName, Object... parameters)
		throws Exception {

		if (_searchEngineAdapter == null) {
			return;
		}

		if (parameters == null) {
			return;
		}

		if ((parameters.length % 2) != 0) {
			throw new IllegalArgumentException(
				"Parameters length is not an even number");
		}

		CountSearchRequest countSearchRequest = new CountSearchRequest();

		countSearchRequest.setIndexNames(indexName);

		BooleanQuery booleanQuery = _queries.booleanQuery();

		for (int i = 0; i < parameters.length; i = i + 2) {
			booleanQuery.addMustQueryClauses(
				_queries.term(
					String.valueOf(parameters[i]), parameters[i + 1]));
		}

		countSearchRequest.setQuery(booleanQuery);

		CountSearchResponse countSearchResponse = _searchEngineAdapter.execute(
			countSearchRequest);

		Assert.assertEquals(
			countSearchResponse.getSearchRequestString(), expectedCount,
			countSearchResponse.getCount());
	}

	private void _retryAssertCount(String indexName, Object... parameters)
		throws Exception {

		_retryAssertCount(1, indexName, parameters);
	}

	private static final String _CLASS_NAME_SLA_INSTANCE_RESULT_INDEXER =
		"com.liferay.portal.workflow.metrics.internal.search.index." +
			"SLAInstanceResultWorkflowMetricsIndexer";

	private static final String _CLASS_NAME_SLA_TASK_RESULT_INDEXER =
		"com.liferay.portal.workflow.metrics.internal.search.index." +
			"SLATaskResultWorkflowMetricsIndexer";

	private static Map<String, Object> _indexers = new HashMap<>();

	private final DocumentBuilderFactory _documentBuilderFactory;
	private final InstanceWorkflowMetricsIndexer
		_instanceWorkflowMetricsIndexer;
	private final NodeWorkflowMetricsIndexer _nodeWorkflowMetricsIndexer;
	private final ProcessWorkflowMetricsIndexer _processWorkflowMetricsIndexer;
	private final Queries _queries;
	private final SearchEngineAdapter _searchEngineAdapter;
	private final TaskWorkflowMetricsIndexer _taskWorkflowMetricsIndexer;

}