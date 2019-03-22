/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.data.engine.rest.internal.resource.v1_0;

import com.liferay.data.engine.rest.dto.v1_0.DataDefinition;
import com.liferay.data.engine.rest.internal.dto.v1_0.util.DataDefinitionUtil;
import com.liferay.data.engine.rest.internal.dto.v1_0.util.LocalizedValueUtil;
import com.liferay.data.engine.rest.internal.model.InternalDataDefinition;
import com.liferay.data.engine.rest.resource.v1_0.DataDefinitionResource;
import com.liferay.dynamic.data.mapping.model.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureService;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Jeyvison Nascimento
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/data-definition.properties",
	scope = ServiceScope.PROTOTYPE, service = DataDefinitionResource.class
)
public class DataDefinitionResourceImpl extends BaseDataDefinitionResourceImpl {

	@Override
	public boolean deleteDataDefinition(Long dataDefinitionId)
		throws Exception {

		_ddmStructureService.deleteStructure(dataDefinitionId);

		return true;
	}

	@Override
	public Page<DataDefinition> getContentSpaceDataDefinitionsPage(
			Long contentSpaceId, String keywords, Pagination pagination)
		throws Exception {

		if (keywords == null) {
			return Page.of(
				transform(
					_ddmStructureService.getStructures(
						contextCompany.getCompanyId(),
						new long[] {contentSpaceId},
						_portal.getClassNameId(InternalDataDefinition.class),
						pagination.getStartPosition(),
						pagination.getEndPosition(), null),
					DataDefinitionUtil::toDataDefinition),
				pagination,
				_ddmStructureService.getStructuresCount(
					contextCompany.getCompanyId(), new long[] {contentSpaceId},
					_portal.getClassNameId(InternalDataDefinition.class)));
		}

		return Page.of(
			transform(
				_ddmStructureService.search(
					contextCompany.getCompanyId(), new long[] {contentSpaceId},
					_portal.getClassNameId(InternalDataDefinition.class),
					keywords, WorkflowConstants.STATUS_ANY,
					pagination.getStartPosition(), pagination.getEndPosition(),
					null),
				DataDefinitionUtil::toDataDefinition),
			pagination,
			_ddmStructureService.searchCount(
				contextCompany.getCompanyId(), new long[] {contentSpaceId},
				_portal.getClassNameId(InternalDataDefinition.class), keywords,
				WorkflowConstants.STATUS_ANY));
	}

	@Override
	public DataDefinition getDataDefinition(Long dataDefinitionId)
		throws Exception {

		return DataDefinitionUtil.toDataDefinition(
			_ddmStructureService.getStructure(dataDefinitionId));
	}

	@Override
	public DataDefinition postContentSpaceDataDefinition(
			Long contentSpaceId, DataDefinition dataDefinition)
		throws Exception {

		return DataDefinitionUtil.toDataDefinition(
			_ddmStructureLocalService.addStructure(
				PrincipalThreadLocal.getUserId(), contentSpaceId,
				DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
				_portal.getClassNameId(InternalDataDefinition.class), null,
				LocalizedValueUtil.toLocalizationMap(dataDefinition.getName()),
				LocalizedValueUtil.toLocalizationMap(
					dataDefinition.getDescription()),
				DataDefinitionUtil.toJSON(dataDefinition),
				dataDefinition.getStorageType(), new ServiceContext()));
	}

	@Override
	public DataDefinition putDataDefinition(
			Long contentSpaceId, DataDefinition dataDefinition)
		throws Exception {

		return DataDefinitionUtil.toDataDefinition(
			_ddmStructureLocalService.updateStructure(
				PrincipalThreadLocal.getUserId(), dataDefinition.getId(),
				DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
				LocalizedValueUtil.toLocalizationMap(dataDefinition.getName()),
				LocalizedValueUtil.toLocalizationMap(
					dataDefinition.getDescription()),
				DataDefinitionUtil.toJSON(dataDefinition),
				new ServiceContext()));
	}

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private DDMStructureService _ddmStructureService;

	@Reference
	private Portal _portal;

}