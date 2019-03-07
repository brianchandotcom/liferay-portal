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
import com.liferay.data.engine.rest.internal.io.DataDefinitionJSONDeserializer;
import com.liferay.data.engine.rest.internal.io.DataDefinitionJSONSerializer;
import com.liferay.data.engine.rest.resource.v1_0.DataDefinitionResource;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

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
	public Page<DataDefinition> getDataDefinitionsPage(
			Long groupId, Pagination pagination)
		throws Exception {

		List<DDMStructure> ddmStructures =
			_ddmStructureLocalService.getStructures(
				contextCompany.getCompanyId(), new long[] {groupId},
				_portal.getClassNameId(DataDefinition.class),
				pagination.getStartPosition(), pagination.getEndPosition(),
				null);

		List<DataDefinition> deDataDefinitions = new ArrayList<>();

		for (DDMStructure ddmStructure : ddmStructures) {
			deDataDefinitions.add(mapToDataDefinition(ddmStructure));
		}

		return Page.of(deDataDefinitions);
	}

	@Override
	public DataDefinition postDataDefinition(
			Long groupId, DataDefinition dataDefinition)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		DDMStructure ddmStructure = null;

		if (dataDefinition.getId() == 0) {
			ddmStructure = createDDMStructure(
				PrincipalThreadLocal.getUserId(), groupId,
				_portal.getClassNameId(DataDefinition.class), dataDefinition,
				serviceContext);
		}
		else {
			ddmStructure = updateDDMStructure(
				PrincipalThreadLocal.getUserId(), dataDefinition,
				serviceContext);
		}

		return mapToDataDefinition(ddmStructure);
	}

	protected DDMStructure createDDMStructure(
			long userId, long groupId, long classNameId,
			DataDefinition dataDefinition, ServiceContext serviceContext)
		throws Exception {

		Map<Locale, String> nameMap = createLocalizedMap(
			dataDefinition.getName());
		Map<Locale, String> descriptionMap = createLocalizedMap(
			dataDefinition.getDescription());

		return _ddmStructureLocalService.addStructure(
			userId, groupId, DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			classNameId, null, nameMap, descriptionMap,
			serialize(dataDefinition), dataDefinition.getStorageType(),
			serviceContext);
	}

	protected Map<Locale, String> createLocalizedMap(
		LocalizedValue[] localizedValues) {

		Map<Locale, String> localeMap = new HashMap<>();

		for (LocalizedValue localizedValue : localizedValues) {
			localeMap.put(
				LocaleUtil.fromLanguageId(localizedValue.getKey()),
				localizedValue.getValue());
		}

		return localeMap;
	}

	protected DataDefinition mapToDataDefinition(DDMStructure ddmStructure)
		throws Exception {

		DataDefinitionJSONDeserializer dataDefinitionJSONDeserializer =
			new DataDefinitionJSONDeserializer(_jsonFactory);

		DataDefinition dataDefinition =
			dataDefinitionJSONDeserializer.deserialize(
				ddmStructure.getDefinition());

		dataDefinition.setCreateDate(
			String.valueOf(ddmStructure.getCreateDate()));
		dataDefinition.setDescription(
			mapToLocalizedValueArray(ddmStructure.getDescriptionMap()));
		dataDefinition.setId(ddmStructure.getStructureId());
		dataDefinition.setModifiedDate(
			String.valueOf(ddmStructure.getModifiedDate()));
		dataDefinition.setName(
			mapToLocalizedValueArray(ddmStructure.getNameMap()));
		dataDefinition.setStorageType(ddmStructure.getStorageType());
		dataDefinition.setUserId(ddmStructure.getUserId());

		return dataDefinition;
	}

	protected LocalizedValue[] mapToLocalizedValueArray(
		Map<Locale, String> map) {

		List<LocalizedValue> localizedValues = new ArrayList<>();

		Set<Locale> keys = map.keySet();

		for (Locale locale : keys) {
			LocalizedValue localizedValue = new LocalizedValue();

			localizedValue.setKey(locale.toString());
			localizedValue.setValue(map.get(locale));

			localizedValues.add(localizedValue);
		}

		return localizedValues.toArray(new LocalizedValue[0]);
	}

	protected String serialize(DataDefinition dataDefinition) throws Exception {
		DataDefinitionJSONSerializer dataDefinitionJSONSerializer =
			new DataDefinitionJSONSerializer(_jsonFactory);

		return dataDefinitionJSONSerializer.serialize(dataDefinition);
	}

	protected DDMStructure updateDDMStructure(
			long userId, DataDefinition dataDefinition,
			ServiceContext serviceContext)
		throws Exception {

		Map<Locale, String> nameMap = createLocalizedMap(
			dataDefinition.getName());
		Map<Locale, String> descriptionMap = createLocalizedMap(
			dataDefinition.getDescription());

		return _ddmStructureLocalService.updateStructure(
			userId, dataDefinition.getId(),
			DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID, nameMap,
			descriptionMap, serialize(dataDefinition), serviceContext);
	}

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Portal _portal;

}