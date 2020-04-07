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
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Creator;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Instance;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Node;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Process;
import com.liferay.portal.workflow.metrics.rest.client.dto.v1_0.Task;
import com.liferay.portal.workflow.metrics.search.index.InstanceWorkflowMetricsIndexer;
import com.liferay.portal.workflow.metrics.search.index.NodeWorkflowMetricsIndexer;
import com.liferay.portal.workflow.metrics.search.index.ProcessWorkflowMetricsIndexer;
import com.liferay.portal.workflow.metrics.search.index.TaskWorkflowMetricsIndexer;
import com.liferay.portal.workflow.metrics.search.index.name.WorkflowMetricsIndexNameBuilder;

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
import org.apache.commons.lang.time.DateUtils;

import org.junit.Assert;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Inácio Nery
 */
@Component(immediate = true, service = WorkflowMetricsRESTTestHelper.class)
public class WorkflowMetricsRESTTestHelper {

	public Instance addInstance(
			long companyId, boolean completed, long processId)
		throws Exception {

		Instance instance = new Instance();

		instance.setCreator(
			new Creator() {
				{
					id = RandomTestUtil.randomLong();
					name = RandomTestUtil.randomString();
				}
			});

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

		instance.setCreator(
			new Creator() {
				{
					id = RandomTestUtil.nextLong();
					name = RandomTestUtil.randomString();
				}
			});
		instance.setDateCompletion(dateCompletion);
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
			0, companyId, instance.getDateCompletion(),
			Optional.ofNullable(
				instance.getDateCreated()
			).orElseGet(
				Date::new
			),
			instance.getId(), new Date(), instance.getProcessId(), "1.0",
			creator.getId(), creator.getName());

		_assertCount(
			_instanceWorkflowMetricsIndexNameBuilder.getIndexName(companyId),
			"companyId", companyId, "deleted", false, "instanceId",
			instance.getId(), "processId", instance.getProcessId());

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

		_nodeWorkflowMetricsIndexer.addNode(
			companyId, new Date(), false, new Date(), node.getName(),
			node.getId(), processId, version, false,
			Optional.ofNullable(
				node.getType()
			).orElseGet(
				() -> "TASK"
			));

		_assertCount(
			_nodeWorkflowMetricsIndexNameBuilder.getIndexName(companyId),
			"companyId", companyId, "deleted", false, "name", node.getName(),
			"nodeId", node.getId(), "processId", processId, "version", version);

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

		_processWorkflowMetricsIndexer.addProcess(
			Boolean.TRUE, companyId, new Date(), StringPool.BLANK, new Date(),
			RandomTestUtil.randomString(), process.getId(), process.getTitle(),
			_createLocalizationMap(process.getTitle()), version);

		Long onTimeInstanceCount = process.getOnTimeInstanceCount();
		Long overdueInstanceCount = process.getOverdueInstanceCount();

		for (int i = 0; i < process.getInstanceCount(); i++) {
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

		_assertCount(
			_processWorkflowMetricsIndexNameBuilder.getIndexName(companyId),
			"companyId", companyId, "deleted", false, "processId",
			process.getId());

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

		_assertCount(
			_slaInstanceResultWorkflowMetricsIndexNameBuilder.getIndexName(
				companyId),
			"companyId", companyId, "deleted", false, "instanceCompleted",
			Objects.nonNull(instance.getDateCompletion()), "instanceId",
			instance.getId(), "onTime", onTime, "processId",
			instance.getProcessId(), "slaDefinitionId", slaDefinitionId);
	}

	public void addSLATaskResult(
			long assigneeId, boolean breached, long companyId,
			Instance instance, long nodeId, boolean onTime, String status,
			long taskId, String taskName)
		throws Exception {

		long slaDefinitionId = RandomTestUtil.randomLong();

		_invokeAddDocument(
			_getIndexer(_CLASS_NAME_SLA_TASK_RESULT_INDEXER),
			_creatWorkflowMetricsSLATaskResultDocument(
				assigneeId, breached, companyId,
				Objects.nonNull(instance.getDateCompletion()), instance.getId(),
				nodeId, onTime, instance.getProcessId(), slaDefinitionId,
				status, taskId, taskName));

		_assertCount(
			_slaTaskResultWorkflowMetricsIndexNameBuilder.getIndexName(
				companyId),
			"breached", breached, "assigneeId", assigneeId, "companyId",
			companyId, "deleted", false, "instanceCompleted",
			Objects.nonNull(instance.getDateCompletion()), "instanceId",
			instance.getId(), "onTime", onTime, "processId",
			instance.getProcessId(), "slaDefinitionId", slaDefinitionId,
			"taskId", taskId, "taskName", taskName);
	}

	public void addTask(long assigneeId, long companyId, Instance instance)
		throws Exception {

		addTask(
			assigneeId, companyId, 0L, instance, RandomTestUtil.randomString(),
			RandomTestUtil.randomLong(), RandomTestUtil.randomLong());
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

	public void addTask(
			long assigneeId, long companyId, long durationAvg,
			Instance instance, String name, long nodeId, long taskId)
		throws Exception {

		Date createDate = new Date();

		if (durationAvg > 0) {
			createDate = DateUtils.addMilliseconds(
				instance.getDateCompletion(), -(int)durationAvg);
		}

		_taskWorkflowMetricsIndexer.addTask(
			assigneeId, StringPool.BLANK, 0L, companyId, durationAvg > 0,
			instance.getDateCompletion(), (durationAvg > 0) ? assigneeId : null,
			createDate, Objects.nonNull(instance.getDateCompletion()),
			instance.getId(), new Date(), name, nodeId, instance.getProcessId(),
			"1.0", taskId, 0);

		_assertCount(
			_taskWorkflowMetricsIndexNameBuilder.getIndexName(companyId),
			"assigneeId", assigneeId, "companyId", companyId, "deleted", false,
			"instanceCompleted", Objects.nonNull(instance.getDateCompletion()),
			"instanceId", instance.getId(), "nodeId", nodeId, "processId",
			instance.getProcessId(), "name", name, "taskId", taskId);
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
			String status)
		throws Exception {

		String randomString = RandomTestUtil.randomString();

		Task task = new Task() {
			{
				durationAvg = Objects.equals(status, "COMPLETED") ? 1000L : 0L;
				instanceCount = 1L;
				key = randomString;
				name = randomString;
				onTimeInstanceCount = 0L;
				overdueInstanceCount = 0L;
			}
		};

		return addTask(
			assigneeId, companyId, instanceSuplier, processId, status, task,
			"1.0");
	}

	public Task addTask(
			long assigneeId, long companyId,
			UnsafeSupplier<Instance, Exception> instanceSuplier, long processId,
			String status, Task task, String version)
		throws Exception {

		long nodeId = RandomTestUtil.randomLong();

		addNode(
			companyId,
			new Node() {
				{
					id = nodeId;
					name = task.getKey();
				}
			},
			processId, version);

		Long onTimeInstanceCount = task.getOnTimeInstanceCount();
		Long overdueInstanceCount = task.getOverdueInstanceCount();

		for (int i = 0; i < task.getInstanceCount(); i++) {
			Instance instance = instanceSuplier.get();
			Long taskId = RandomTestUtil.nextLong();

			if (onTimeInstanceCount > 0) {
				addSLATaskResult(
					assigneeId, false, companyId, instance, nodeId, true,
					status, taskId, task.getKey());

				onTimeInstanceCount--;
			}
			else if (overdueInstanceCount > 0) {
				addSLATaskResult(
					assigneeId, true, companyId, instance, nodeId, false,
					status, taskId, task.getKey());

				overdueInstanceCount--;
			}

			addTask(
				assigneeId, companyId, task.getDurationAvg(), instance,
				task.getKey(), nodeId, taskId);
		}

		return task;
	}

	public void deleteInstance(long companyId, Instance instance)
		throws Exception {

		_instanceWorkflowMetricsIndexer.deleteInstance(
			companyId, instance.getId());

		_assertCount(
			_instanceWorkflowMetricsIndexNameBuilder.getIndexName(companyId),
			"companyId", companyId, "deleted", true, "instanceId",
			instance.getId(), "processId", instance.getProcessId());
	}

	public void deleteNode(long companyId, Node node, long processId)
		throws Exception {

		_nodeWorkflowMetricsIndexer.deleteNode(companyId, node.getId());

		_assertCount(
			_nodeWorkflowMetricsIndexNameBuilder.getIndexName(companyId),
			"companyId", companyId, "deleted", true, "name", node.getName(),
			"processId", processId);
	}

	public void deleteProcess(Document document) throws Exception {
		_processWorkflowMetricsIndexer.deleteProcess(
			document.getLong("companyId"), document.getLong("processId"));

		_assertCount(
			_processWorkflowMetricsIndexNameBuilder.getIndexName(
				document.getLong("companyId")),
			"companyId", document.getLong("companyId"), "deleted", true,
			"processId", document.getLong("processId"));
	}

	public void deleteProcess(long companyId, Process process)
		throws Exception {

		_processWorkflowMetricsIndexer.deleteProcess(
			companyId, process.getId());
	}

	public void deleteSLATaskResults(long companyId, long processId)
		throws Exception {

		_deleteDocuments(
			_slaTaskResultWorkflowMetricsIndexNameBuilder.getIndexName(
				companyId),
			"WorkflowMetricsSLATaskResultType", "companyId", companyId,
			"processId", processId);
	}

	public void deleteTasks(long companyId, long processId) throws Exception {
		_deleteDocuments(
			_taskWorkflowMetricsIndexNameBuilder.getIndexName(companyId),
			"WorkflowMetricsTaskType", "companyId", companyId, "processId",
			processId);
	}

	public Document[] getDocuments(long companyId) throws Exception {
		if (_searchEngineAdapter == null) {
			return new Document[0];
		}

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.setIndexNames(
			_processWorkflowMetricsIndexNameBuilder.getIndexName(companyId));

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

		_assertCount(
			_processWorkflowMetricsIndexNameBuilder.getIndexName(
				document.getLong("companyId")),
			"companyId", document.getLong("companyId"), "deleted", false,
			"processId", document.getLong("processId"));
	}

	public void updateProcess(long companyId, long processId, String version)
		throws Exception {

		_processWorkflowMetricsIndexer.updateProcess(
			null, companyId, null, new Date(), processId, null, null, version);

		_assertCount(
			_processWorkflowMetricsIndexNameBuilder.getIndexName(companyId),
			"companyId", companyId, "deleted", false, "processId", processId,
			"version", version);
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

	private void _assertCount(
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

		Assert.assertEquals(expectedCount, countSearchResponse.getCount());
	}

	private void _assertCount(String indexName, Object... parameters)
		throws Exception {

		_assertCount(1, indexName, parameters);
	}

	private Map<Locale, String> _createLocalizationMap(String title) {
		Map<Locale, String> localizationMap = new HashMap<>();

		for (Locale availableLocale : LanguageUtil.getAvailableLocales()) {
			localizationMap.put(availableLocale, title);
		}

		return localizationMap;
	}

	private Document _creatWorkflowMetricsSLAInstanceResultDocument(
		long companyId, boolean instanceCompleted, long instanceId,
		boolean onTime, long processId, long slaDefinitionId) {

		DocumentBuilder documentBuilder = _documentBuilderFactory.builder();

		documentBuilder.setValue(
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
		).setString(
			"uid",
			_digest(
				"WorkflowMetricsSLAInstanceResult", companyId, instanceId,
				processId, slaDefinitionId)
		);

		return documentBuilder.build();
	}

	private Document _creatWorkflowMetricsSLATaskResultDocument(
		long assigneeId, boolean breached, long companyId,
		boolean instanceCompleted, long instanceId, long nodeId, boolean onTime,
		long processId, long slaDefinitionId, String status, long taskId,
		String taskName) {

		DocumentBuilder documentBuilder = _documentBuilderFactory.builder();

		documentBuilder.setValue(
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
			"nodeId", nodeId
		).setValue(
			"onTime", onTime
		).setValue(
			"processId", processId
		).setValue(
			"slaDefinitionId", slaDefinitionId
		).setValue(
			"status", status
		).setValue(
			"taskId", taskId
		).setValue(
			"taskName", taskName
		).setString(
			"uid",
			_digest(
				"WorkflowMetricsSLATaskResult", companyId, instanceId,
				processId, slaDefinitionId, taskId)
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

		_assertCount(
			searchSearchResponse.getCount(), indexName,
			ArrayUtil.append(new Object[] {"deleted", true}, parameters));
	}

	private String _digest(String indexNamePrefix, Serializable... parts) {
		StringBuilder sb = new StringBuilder();

		for (Serializable part : parts) {
			sb.append(part);
		}

		return indexNamePrefix + DigestUtils.sha256Hex(sb.toString());
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

	private static final String _CLASS_NAME_SLA_INSTANCE_RESULT_INDEXER =
		"com.liferay.portal.workflow.metrics.internal.search.index." +
			"SLAInstanceResultWorkflowMetricsIndexer";

	private static final String _CLASS_NAME_SLA_TASK_RESULT_INDEXER =
		"com.liferay.portal.workflow.metrics.internal.search.index." +
			"SLATaskResultWorkflowMetricsIndexer";

	@Reference
	private DocumentBuilderFactory _documentBuilderFactory;

	private final Map<String, Object> _indexers = new HashMap<>();

	@Reference
	private InstanceWorkflowMetricsIndexer _instanceWorkflowMetricsIndexer;

	@Reference(target = "(workflow.metrics.index.entity.name=instance)")
	private WorkflowMetricsIndexNameBuilder
		_instanceWorkflowMetricsIndexNameBuilder;

	@Reference
	private NodeWorkflowMetricsIndexer _nodeWorkflowMetricsIndexer;

	@Reference(target = "(workflow.metrics.index.entity.name=node)")
	private WorkflowMetricsIndexNameBuilder
		_nodeWorkflowMetricsIndexNameBuilder;

	@Reference
	private ProcessWorkflowMetricsIndexer _processWorkflowMetricsIndexer;

	@Reference(target = "(workflow.metrics.index.entity.name=process)")
	private WorkflowMetricsIndexNameBuilder
		_processWorkflowMetricsIndexNameBuilder;

	@Reference
	private Queries _queries;

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(search.engine.impl=Elasticsearch)"
	)
	private volatile SearchEngineAdapter _searchEngineAdapter;

	@Reference(
		target = "(workflow.metrics.index.entity.name=sla-instance-result)"
	)
	private WorkflowMetricsIndexNameBuilder
		_slaInstanceResultWorkflowMetricsIndexNameBuilder;

	@Reference(target = "(workflow.metrics.index.entity.name=sla-task-result)")
	private WorkflowMetricsIndexNameBuilder
		_slaTaskResultWorkflowMetricsIndexNameBuilder;

	@Reference
	private TaskWorkflowMetricsIndexer _taskWorkflowMetricsIndexer;

	@Reference(target = "(workflow.metrics.index.entity.name=task)")
	private WorkflowMetricsIndexNameBuilder
		_taskWorkflowMetricsIndexNameBuilder;

}