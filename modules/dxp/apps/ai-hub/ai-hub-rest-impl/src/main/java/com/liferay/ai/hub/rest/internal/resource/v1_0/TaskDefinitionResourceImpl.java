/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.hub.rest.internal.resource.v1_0;

import com.liferay.ai.hub.rest.dto.v1_0.TaskDefinition;
import com.liferay.ai.hub.rest.internal.odata.entity.v1_0.TaskDefinitionEntityModel;
import com.liferay.ai.hub.rest.manager.v1_0.TaskDefinitionManager;
import com.liferay.ai.hub.rest.resource.v1_0.TaskDefinitionResource;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import jakarta.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Feliphe Marinho
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/task-definition.properties",
	scope = ServiceScope.PROTOTYPE, service = TaskDefinitionResource.class
)
public class TaskDefinitionResourceImpl extends BaseTaskDefinitionResourceImpl {

	@Override
	public void deleteTaskDefinition(Long taskDefinitionId) throws Exception {
		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-62272")) {

			throw new UnsupportedOperationException();
		}

		_taskDefinitionManager.deleteTaskDefinition(
			_createDefaultDTOConverterContext(taskDefinitionId),
			taskDefinitionId);
	}

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap) {
		return _entityModel;
	}

	@Override
	public Page<TaskDefinition> getTaskDefinitionsPage(
			String search, Filter filter, Pagination pagination, Sort[] sorts)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-62272")) {

			throw new UnsupportedOperationException();
		}

		return _taskDefinitionManager.getTaskDefinitions(
			contextCompany.getCompanyId(),
			_createDefaultDTOConverterContext(null), filter, pagination, search,
			sorts);
	}

	@Override
	public TaskDefinition patchTaskDefinitionUpdateActive(
			Long taskDefinitionId, Boolean active)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-62272")) {

			throw new UnsupportedOperationException();
		}

		return _taskDefinitionManager.patchTaskDefinitionUpdateActive(
			active, _createDefaultDTOConverterContext(taskDefinitionId),
			taskDefinitionId);
	}

	@Override
	public TaskDefinition postTaskDefinitionCopy(Long taskDefinitionId)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-62272")) {

			throw new UnsupportedOperationException();
		}

		return _taskDefinitionManager.postTaskDefinitionCopy(
			_createDefaultDTOConverterContext(taskDefinitionId),
			taskDefinitionId);
	}

	private DefaultDTOConverterContext _createDefaultDTOConverterContext(
		Long taskDefinitionId) {

		return new DefaultDTOConverterContext(
			contextAcceptLanguage.isAcceptAllLanguages(), null,
			_dtoConverterRegistry, contextHttpServletRequest, taskDefinitionId,
			contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
			contextUser);
	}

	private static final EntityModel _entityModel =
		new TaskDefinitionEntityModel();

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private TaskDefinitionManager _taskDefinitionManager;

}