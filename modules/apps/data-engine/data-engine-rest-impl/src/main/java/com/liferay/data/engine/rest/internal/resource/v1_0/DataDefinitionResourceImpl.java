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
import com.liferay.data.engine.rest.dto.v1_0.LocalizedValue;
import com.liferay.data.engine.rest.internal.dto.v1_0.util.DataDefinitionUtil;
import com.liferay.data.engine.rest.resource.v1_0.DataDefinitionResource;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureService;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
	public DataDefinition getDataDefinition(Long dataDefinitionId)
		throws Exception {

		return _toDataDefinition(
			_ddmStructureService.getStructure(dataDefinitionId));
	}

	@Override
	public Page<DataDefinition> getDataDefinitionSearchPage(
			Long groupId, String keywords, Pagination pagination)
		throws Exception {

		return Page.of(
			transform(
				_ddmStructureService.search(
					contextCompany.getCompanyId(), new long[] {groupId},
					_portal.getClassNameId(DataDefinition.class), keywords,
					WorkflowConstants.STATUS_ANY, pagination.getStartPosition(),
					pagination.getEndPosition(), null),
				this::_toDataDefinition),
			pagination,
			_ddmStructureService.searchCount(
				contextCompany.getCompanyId(), new long[] {groupId},
				_portal.getClassNameId(DataDefinition.class), keywords,
				WorkflowConstants.STATUS_ANY));
	}

	@Override
	public Page<DataDefinition> getDataDefinitionsPage(
			Long groupId, Pagination pagination)
		throws Exception {

		return Page.of(
			transform(
				_ddmStructureService.getStructures(
					contextCompany.getCompanyId(), new long[] {groupId},
					_portal.getClassNameId(DataDefinition.class),
					pagination.getStartPosition(), pagination.getEndPosition(),
					null),
				this::_toDataDefinition),
			pagination,
			_ddmStructureService.getStructuresCount(
				contextCompany.getCompanyId(), new long[] {groupId},
				_portal.getClassNameId(DataDefinition.class)));
	}

	@Override
	public DataDefinition postDataDefinition(
			Long groupId, DataDefinition dataDefinition)
		throws Exception {

		return _toDataDefinition(
			_ddmStructureLocalService.addStructure(
				PrincipalThreadLocal.getUserId(), groupId,
				DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
				_portal.getClassNameId(DataDefinition.class), null,
				_toLocalizationMap(dataDefinition.getName()),
				_toLocalizationMap(dataDefinition.getDescription()),
				DataDefinitionUtil.toJSON(dataDefinition),
				dataDefinition.getStorageType(), new ServiceContext()));
	}

	@Override
	public DataDefinition putDataDefinition(
			Long groupId, DataDefinition dataDefinition)
		throws Exception {

		return _toDataDefinition(
			_ddmStructureLocalService.updateStructure(
				PrincipalThreadLocal.getUserId(), dataDefinition.getId(),
				DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
				_toLocalizationMap(dataDefinition.getName()),
				_toLocalizationMap(dataDefinition.getDescription()),
				DataDefinitionUtil.toJSON(dataDefinition),
				new ServiceContext()));
	}

	private DataDefinition _toDataDefinition(DDMStructure ddmStructure)
		throws Exception {

		DataDefinition dataDefinition = DataDefinitionUtil.toDataDefinition(
			ddmStructure.getDefinition());

		dataDefinition.setDateCreated(ddmStructure.getCreateDate());
		dataDefinition.setDateModified(ddmStructure.getModifiedDate());
		dataDefinition.setDescription(
			_toLocalizedValues(ddmStructure.getDescriptionMap()));
		dataDefinition.setId(ddmStructure.getStructureId());
		dataDefinition.setName(_toLocalizedValues(ddmStructure.getNameMap()));
		dataDefinition.setStorageType(ddmStructure.getStorageType());
		dataDefinition.setUserId(ddmStructure.getUserId());

		return dataDefinition;
	}

	private Map<Locale, String> _toLocalizationMap(
		LocalizedValue[] localizedValues) {

		Map<Locale, String> localizationMap = new HashMap<>();

		for (LocalizedValue localizedValue : localizedValues) {
			localizationMap.put(
				LocaleUtil.fromLanguageId(localizedValue.getKey()),
				localizedValue.getValue());
		}

		return localizationMap;
	}

	private LocalizedValue[] _toLocalizedValues(Map<Locale, String> map) {
		List<LocalizedValue> localizedValues = new ArrayList<>();

		for (Map.Entry<Locale, String> entry : map.entrySet()) {
			LocalizedValue localizedValue = new LocalizedValue();

			localizedValue.setKey(String.valueOf(entry.getKey()));
			localizedValue.setValue(entry.getValue());

			localizedValues.add(localizedValue);
		}

		return localizedValues.toArray(new LocalizedValue[map.size()]);
	}

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private DDMStructureService _ddmStructureService;

	@Reference
	private Portal _portal;

}