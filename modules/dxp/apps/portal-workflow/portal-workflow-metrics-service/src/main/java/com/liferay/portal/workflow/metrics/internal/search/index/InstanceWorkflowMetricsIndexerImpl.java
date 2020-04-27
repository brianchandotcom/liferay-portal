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

package com.liferay.portal.workflow.metrics.internal.search.index;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.document.DocumentBuilder;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.workflow.metrics.search.index.InstanceWorkflowMetricsIndexer;
import com.liferay.portal.workflow.metrics.search.index.TaskWorkflowMetricsIndexer;

import java.time.Duration;

import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(immediate = true, service = InstanceWorkflowMetricsIndexer.class)
public class InstanceWorkflowMetricsIndexerImpl
	extends BaseWorkflowMetricsIndexer
	implements InstanceWorkflowMetricsIndexer {

	@Override
	public Document addInstance(
		Map<Locale, String> assetTitleMap, Map<Locale, String> assetTypeMap,
		String className, long classPK, long companyId, Date completionDate,
		Date createDate, long instanceId, Date modifiedDate, long processId,
		String processVersion, long userId, String userName) {

		DocumentBuilder documentBuilder = documentBuilderFactory.builder();

		documentBuilder.setString(
			"className", className
		).setLong(
			"classPK", classPK
		).setLong(
			"companyId", companyId
		).setValue(
			"completed", completionDate != null
		);

		if (completionDate != null) {
			documentBuilder.setDate(
				"completionDate", getDate(completionDate)
			).setValue(
				Field.getSortableFieldName(
					StringBundler.concat(
						"completionDate", StringPool.UNDERLINE, "Number")),
				completionDate.getTime()
			);
		}

		documentBuilder.setDate(
			"createDate", getDate(createDate)
		).setValue(
			Field.getSortableFieldName(
				StringBundler.concat(
					"createDate", StringPool.UNDERLINE, "Number")),
			createDate.getTime()
		).setValue(
			"deleted", Boolean.FALSE
		);

		if (completionDate != null) {
			documentBuilder.setLong(
				"duration", _getDuration(completionDate, createDate));
		}

		documentBuilder.setLong(
			"instanceId", instanceId
		).setDate(
			"modifiedDate", getDate(modifiedDate)
		).setLong(
			"processId", processId
		).setString(
			"uid", digest(companyId, instanceId)
		).setLong(
			"userId", userId
		).setString(
			"userName", userName
		).setString(
			"version", processVersion
		);

		setLocalizedField(documentBuilder, "assetTitle", assetTitleMap);
		setLocalizedField(documentBuilder, "assetType", assetTypeMap);

		Document document = documentBuilder.build();

		workflowMetricsPortalExecutor.execute(() -> addDocument(document));

		return document;
	}

	@Override
	public Document completeInstance(
		long companyId, Date completionDate, long duration, long instanceId,
		Date modifiedDate) {

		DocumentBuilder documentBuilder = documentBuilderFactory.builder();

		documentBuilder.setLong(
			"companyId", companyId
		).setValue(
			"completed", completionDate != null
		).setDate(
			"completionDate", getDate(completionDate)
		).setValue(
			Field.getSortableFieldName(
				StringBundler.concat(
					"completionDate", StringPool.UNDERLINE, "Number")),
			completionDate.getTime()
		).setLong(
			"duration", duration
		).setLong(
			"instanceId", instanceId
		).setDate(
			"modifiedDate", getDate(modifiedDate)
		).setString(
			"uid", digest(companyId, instanceId)
		);

		Document document = documentBuilder.build();

		workflowMetricsPortalExecutor.execute(
			() -> {
				updateDocument(document);

				BooleanQuery booleanQuery = queries.booleanQuery();

				booleanQuery.addMustQueryClauses(
					queries.term("companyId", companyId),
					queries.term("instanceId", instanceId));

				_slaInstanceResultWorkflowMetricsIndexer.updateDocuments(
					companyId,
					HashMapBuilder.<String, Object>put(
						"completionDate", document.getDate("completionDate")
					).put(
						"instanceCompleted", Boolean.TRUE
					).build(),
					booleanQuery);

				_slaTaskResultWorkflowMetricsIndexer.updateDocuments(
					companyId,
					HashMapBuilder.<String, Object>put(
						"instanceCompleted", Boolean.TRUE
					).put(
						"instanceCompletionDate",
						document.getDate("completionDate")
					).build(),
					booleanQuery);

				BaseWorkflowMetricsIndexer baseWorkflowMetricsIndexer =
					(BaseWorkflowMetricsIndexer)_taskWorkflowMetricsIndexer;

				baseWorkflowMetricsIndexer.updateDocuments(
					companyId,
					HashMapBuilder.<String, Object>put(
						"instanceCompleted", Boolean.TRUE
					).put(
						"instanceCompletionDate",
						document.getDate("completionDate")
					).build(),
					booleanQuery);
			});

		return document;
	}

	@Override
	public void deleteInstance(long companyId, long instanceId) {
		DocumentBuilder documentBuilder = documentBuilderFactory.builder();

		documentBuilder.setLong(
			"companyId", companyId
		).setLong(
			"instanceId", instanceId
		).setString(
			"uid", digest(companyId, instanceId)
		);

		workflowMetricsPortalExecutor.execute(
			() -> {
				deleteDocument(documentBuilder);

				_slaInstanceResultWorkflowMetricsIndexer.deleteDocuments(
					companyId, instanceId);

				_slaTaskResultWorkflowMetricsIndexer.deleteDocuments(
					companyId, instanceId);
			});
	}

	@Override
	public String getIndexName(long companyId) {
		return _instanceWorkflowMetricsIndex.getIndexName(companyId);
	}

	@Override
	public String getIndexType() {
		return _instanceWorkflowMetricsIndex.getIndexType();
	}

	@Override
	public Document updateInstance(
		Map<Locale, String> assetTitleMap, Map<Locale, String> assetTypeMap,
		long companyId, long instanceId, Date modifiedDate) {

		DocumentBuilder documentBuilder = documentBuilderFactory.builder();

		documentBuilder.setLong(
			"companyId", companyId
		).setDate(
			"modifiedDate", getDate(modifiedDate)
		).setString(
			"uid", digest(companyId, instanceId)
		);

		setLocalizedField(documentBuilder, "assetTitle", assetTitleMap);
		setLocalizedField(documentBuilder, "assetType", assetTypeMap);

		Document document = documentBuilder.build();

		workflowMetricsPortalExecutor.execute(() -> updateDocument(document));

		return document;
	}

	private long _getDuration(Date completionDate, Date createDate) {
		Duration duration = Duration.between(
			createDate.toInstant(), completionDate.toInstant());

		return duration.toMillis();
	}

	@Reference(target = "(workflow.metrics.index.entity.name=instance)")
	private WorkflowMetricsIndex _instanceWorkflowMetricsIndex;

	@Reference
	private SLAInstanceResultWorkflowMetricsIndexer
		_slaInstanceResultWorkflowMetricsIndexer;

	@Reference
	private SLATaskResultWorkflowMetricsIndexer
		_slaTaskResultWorkflowMetricsIndexer;

	@Reference
	private TaskWorkflowMetricsIndexer _taskWorkflowMetricsIndexer;

}