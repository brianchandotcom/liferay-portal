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

package com.liferay.portal.workflow.metrics.rest.internal.resource.v1_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.background.task.constants.BackgroundTaskContextMapConstants;
import com.liferay.portal.background.task.service.BackgroundTaskLocalService;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.Index;
import com.liferay.portal.workflow.metrics.rest.internal.dto.v1_0.util.IndexUtil;
import com.liferay.portal.workflow.metrics.rest.internal.resource.exception.IndexKeyException;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.IndexResource;
import com.liferay.portal.workflow.metrics.search.background.task.WorkflowMetricsBackgroundTaskExecutorNames;
import com.liferay.portal.workflow.metrics.search.index.reindexer.WorkflowMetricsReindexer;

import java.io.Serializable;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Rafael Praxedes
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/index.properties",
	scope = ServiceScope.PROTOTYPE, service = IndexResource.class
)
public class IndexResourceImpl extends BaseIndexResourceImpl {

	@Override
	public Page<Index> getIndexesPage() throws Exception {
		return Page.of(
			transform(
				_indexEntityNameSet,
				indexEntityName -> IndexUtil.toIndex(
					indexEntityName, _language,
					contextAcceptLanguage.getPreferredLocale(),
					ResourceBundleUtil.getModuleAndPortalResourceBundle(
						contextAcceptLanguage.getPreferredLocale(),
						IndexResourceImpl.class))));
	}

	@Override
	public void patchIndexesReindex(Index index) throws Exception {
		if (Objects.isNull(index) || Validator.isNull(index.getKey())) {
			throw new IndexKeyException();
		}

		String[] indexEntityNames = _getIndexEntityNames(index);

		if (ArrayUtil.isEmpty(indexEntityNames)) {
			throw new IndexKeyException();
		}

		_backgroundTaskLocalService.addBackgroundTask(
			contextUser.getUserId(), contextCompany.getGroupId(),
			_getBackgroundTaskName(index),
			WorkflowMetricsBackgroundTaskExecutorNames.
				WORKFLOW_METRICS_REINDEX_BACKGROUND_TASK_EXECUTOR,
			HashMapBuilder.<String, Serializable>put(
				BackgroundTaskContextMapConstants.DELETE_ON_SUCCESS, true
			).put(
				"workflow.metrics.index.entity.names", indexEntityNames
			).put(
				"workflow.metrics.index.key", index.getKey()
			).build(),
			new ServiceContext());
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void addWorkflowMetricsReindexer(
		WorkflowMetricsReindexer workflowMetricsReindexer,
		Map<String, Object> properties) {

		String workflowMetricsIndexEntityName = GetterUtil.getString(
			properties.get("workflow.metrics.index.entity.name"));

		if (Validator.isNull(workflowMetricsIndexEntityName)) {
			return;
		}

		_indexEntityNameSet.add(workflowMetricsIndexEntityName);
	}

	protected void removeWorkflowMetricsReindexer(
		WorkflowMetricsReindexer workflowMetricsReindexer,
		Map<String, Object> properties) {

		String workflowMetricsIndexEntityName = GetterUtil.getString(
			properties.get("workflow.metrics.index.entity.name"));

		if (Validator.isNull(workflowMetricsIndexEntityName)) {
			return;
		}

		_indexEntityNameSet.remove(workflowMetricsIndexEntityName);
	}

	private String _getBackgroundTaskName(Index index) {
		return StringBundler.concat(
			WorkflowMetricsBackgroundTaskExecutorNames.
				WORKFLOW_METRICS_REINDEX_BACKGROUND_TASK_EXECUTOR,
			StringPool.DASH, contextCompany.getCompanyId(), StringPool.DASH,
			index.getKey());
	}

	private String[] _getIndexEntityNames(Index index) {
		if (Objects.equals(index.getKey(), Index.Group.ALL.getValue())) {
			return _indexEntityNameSet.toArray(new String[0]);
		}
		else if (Objects.equals(
					index.getKey(), Index.Group.METRIC.getValue())) {

			return Stream.of(
				_indexEntityNameSet
			).flatMap(
				Collection::stream
			).filter(
				value -> !value.startsWith("sla")
			).toArray(
				String[]::new
			);
		}
		else if (Objects.equals(index.getKey(), Index.Group.SLA.getValue())) {
			return Stream.of(
				_indexEntityNameSet
			).flatMap(
				Collection::stream
			).filter(
				value -> value.startsWith("sla")
			).toArray(
				String[]::new
			);
		}

		if (_indexEntityNameSet.contains(index.getKey())) {
			return new String[] {index.getKey()};
		}

		return new String[0];
	}

	private static final Set<String> _indexEntityNameSet = new HashSet<>();

	@Reference
	private BackgroundTaskLocalService _backgroundTaskLocalService;

	@Reference
	private Language _language;

}