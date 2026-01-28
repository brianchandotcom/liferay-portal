/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.cmp.internal.resource.v1_0;

import com.liferay.depot.constants.DepotConstants;
import com.liferay.depot.model.DepotEntryModel;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.headless.cmp.dto.v1_0.TaskStatistics;
import com.liferay.headless.cmp.resource.v1_0.TaskStatisticsResource;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.rest.filter.factory.FilterFactory;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Carolina Barbosa
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/task-statistics.properties",
	scope = ServiceScope.PROTOTYPE, service = TaskStatisticsResource.class
)
public class TaskStatisticsResourceImpl extends BaseTaskStatisticsResourceImpl {

	@Override
	public TaskStatistics getProjectTaskStatistics(
			Long projectId, Filter filter)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-58677")) {

			throw new UnsupportedOperationException();
		}

		return _toTaskStatistics(
			_objectEntryLocalService.getObjectEntry(
				GetterUtil.getLong(projectId)),
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					"L_CMP_TASK", contextCompany.getCompanyId()));
	}

	@Override
	public TaskStatistics getTaskStatistics(Filter filter) throws Exception {
		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-58677")) {

			throw new UnsupportedOperationException();
		}

		return _toTaskStatistics(
			null,
			_objectDefinitionLocalService.
				getObjectDefinitionByExternalReferenceCode(
					"L_CMP_TASK", contextCompany.getCompanyId()));
	}

	private long _getCount(
			String filterString, ObjectEntry projectObjectEntry,
			ObjectDefinition taskObjectDefinition)
		throws Exception {

		List<Long> groupIds = new ArrayList<>();

		if (projectObjectEntry == null) {
			groupIds = transform(
				_depotEntryLocalService.getDepotEntries(
					contextCompany.getCompanyId(), DepotConstants.TYPE_PROJECT),
				DepotEntryModel::getGroupId);
		}
		else {
			groupIds.add(projectObjectEntry.getGroupId());
		}

		return _objectEntryLocalService.getValuesListCount(
			groupIds.toArray(new Long[0]), 0, 0,
			taskObjectDefinition.getObjectDefinitionId(),
			_filterFactory.create(filterString, taskObjectDefinition), true,
			null);
	}

	private TaskStatistics _toTaskStatistics(
		ObjectEntry projectObjectEntry, ObjectDefinition taskObjectDefinition) {

		return new TaskStatistics() {
			{
				setBlockedCount(
					() -> _getCount(
						"state eq 'blocked'", projectObjectEntry,
						taskObjectDefinition));
				setInProgressCount(
					() -> _getCount(
						"state eq 'inProgress'", projectObjectEntry,
						taskObjectDefinition));
				setOverdueCount(
					() -> _getCount(
						"dueDate lt " + LocalDate.now() +
							" and state ne 'done'",
						projectObjectEntry, taskObjectDefinition));
				setTotalCount(
					() -> {
						long tasksTotalCount = _getCount(
							StringPool.BLANK, projectObjectEntry,
							taskObjectDefinition);

						if (projectObjectEntry != null) {
							return tasksTotalCount;
						}

						SearchResponse searchResponse = _searcher.search(
							_searchRequestBuilderFactory.builder(
							).companyId(
								contextCompany.getCompanyId()
							).emptySearchEnabled(
								true
							).entryClassNames(
								KaleoTaskInstanceToken.class.getName()
							).query(
								_queries.wildcard(
									"assetTagNames.lowercase", "L_CMP_TASK*")
							).build());

						return tasksTotalCount + searchResponse.getTotalHits();
					});
			}
		};
	}

	@Reference
	private DepotEntryLocalService _depotEntryLocalService;

	@Reference(
		target = "(filter.factory.key=" + ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT + ")"
	)
	private FilterFactory<Predicate> _filterFactory;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private Queries _queries;

	@Reference
	private Searcher _searcher;

	@Reference
	private SearchRequestBuilderFactory _searchRequestBuilderFactory;

}