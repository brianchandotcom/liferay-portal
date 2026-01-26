/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.rest.internal.manager.v1_0;

import com.liferay.ai.hub.rest.dto.v1_0.TaskDefinition;
import com.liferay.ai.hub.rest.manager.v1_0.TaskDefinitionManager;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.SearchUtil;
import com.liferay.portal.workflow.constants.WorkflowDefinitionConstants;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.manager.WorkflowDefinitionManager;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Feliphe Marinho
 * @author João Victor Alves
 */
@Component(service = TaskDefinitionManager.class)
public class TaskDefinitionManagerImpl implements TaskDefinitionManager {

	@Override
	public Page<TaskDefinition> getTaskDefinitions(
			long companyId, DTOConverterContext dtoConverterContext,
			String search, Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			dtoConverterContext.getActions(),
			booleanQuery -> {
				BooleanFilter booleanFilter =
					booleanQuery.getPreBooleanFilter();

				booleanFilter.addRequiredTerm("latest", Boolean.TRUE);
				booleanFilter.addRequiredTerm(
					"scope", WorkflowDefinitionConstants.SCOPE_AI);
			},
			filter, KaleoDefinitionVersion.class.getName(), search, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.NAME, Field.VERSION),
			searchContext -> searchContext.setCompanyId(companyId), sorts,
			document -> _toTaskDefinition(
				_workflowDefinitionManager.getWorkflowDefinition(
					companyId, document.get(Field.NAME),
					GetterUtil.getInteger(document.get(Field.VERSION)))));
	}

	private TaskDefinition _toTaskDefinition(
			WorkflowDefinition workflowDefinition)
		throws PortalException {

		return new TaskDefinition() {
			{
				setActive(workflowDefinition::isActive);
				setDescription(workflowDefinition::getDescription);
				setName(workflowDefinition::getName);
				setVersion(workflowDefinition::getVersion);
			}
		};
	}

	@Reference
	private WorkflowDefinitionManager _workflowDefinitionManager;

}