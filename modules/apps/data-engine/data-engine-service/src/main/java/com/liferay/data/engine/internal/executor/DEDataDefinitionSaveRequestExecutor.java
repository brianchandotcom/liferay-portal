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

package com.liferay.data.engine.internal.executor;

import com.liferay.data.engine.exception.DEDataDefinitionFieldsSerializerException;
import com.liferay.data.engine.internal.io.DEDataDefinitionFieldsSerializerTracker;
import com.liferay.data.engine.io.DEDataDefinitionFieldsSerializer;
import com.liferay.data.engine.io.DEDataDefinitionFieldsSerializerApplyRequest;
import com.liferay.data.engine.io.DEDataDefinitionFieldsSerializerApplyResponse;
import com.liferay.data.engine.model.DEDataDefinition;
import com.liferay.data.engine.service.DEDataDefinitionSaveRequest;
import com.liferay.data.engine.service.DEDataDefinitionSaveResponse;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Jeyvison Nascimento
 */
public class DEDataDefinitionSaveRequestExecutor {

	public DEDataDefinitionSaveRequestExecutor(
		DDMStructureLocalService ddmStructureLocalService,
		DEDataDefinitionFieldsSerializerTracker
			deDataDefinitionFieldsSerializerTracker,
		Portal portal, ResourceLocalService resourceLocalService) {

		_ddmStructureLocalService = ddmStructureLocalService;
		_deDataDefinitionFieldsSerializerTracker =
			deDataDefinitionFieldsSerializerTracker;
		_portal = portal;
		_resourceLocalService = resourceLocalService;
	}

	public DEDataDefinitionSaveResponse execute(
			DEDataDefinitionSaveRequest deDataDefinitionSaveRequest)
		throws Exception {

		DEDataDefinition deDataDefinition =
			deDataDefinitionSaveRequest.getDEDataDefinition();

		long deDataDefinitionId = deDataDefinition.getDEDataDefinitionId();

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if (deDataDefinitionId == 0) {
			DDMStructure ddmStructure = createDDMStructure(
				deDataDefinitionSaveRequest.getUserId(),
				deDataDefinitionSaveRequest.getGroupId(),
				_portal.getClassNameId(DEDataDefinition.class),
				deDataDefinition, serviceContext);

			deDataDefinitionId = ddmStructure.getStructureId();

			_resourceLocalService.addModelResources(
				ddmStructure.getCompanyId(),
				deDataDefinitionSaveRequest.getGroupId(),
				deDataDefinitionSaveRequest.getUserId(),
				DEDataDefinition.class.getName(), deDataDefinitionId,
				serviceContext.getModelPermissions());
		}
		else {
			updateDDMStructure(
				deDataDefinitionSaveRequest.getUserId(), deDataDefinition,
				serviceContext);
		}

		return DEDataDefinitionSaveResponse.Builder.of(deDataDefinitionId);
	}

	protected DDMStructure createDDMStructure(
			long userId, long groupId, long classNameId,
			DEDataDefinition deDataDefinition, ServiceContext serviceContext)
		throws PortalException {

		Map<Locale, String> nameMap = createLocalizedMap(
			deDataDefinition.getName());
		Map<Locale, String> descriptionMap = createLocalizedMap(
			deDataDefinition.getDescription());

		return _ddmStructureLocalService.addStructure(
			userId, groupId, DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			classNameId, null, nameMap, descriptionMap,
			serialize(deDataDefinition), deDataDefinition.getStorageType(),
			serviceContext);
	}

	protected Map<Locale, String> createLocalizedMap(Map<String, String> map) {
		Map<Locale, String> localeMap = new HashMap<>();

		for (Map.Entry<String, String> entry : map.entrySet()) {
			localeMap.put(
				LocaleUtil.fromLanguageId(entry.getKey()), entry.getValue());
		}

		return localeMap;
	}

	protected String serialize(DEDataDefinition deDataDefinition)
		throws DEDataDefinitionFieldsSerializerException {

		DEDataDefinitionFieldsSerializer deDataDefinitionFieldsSerializer =
			_deDataDefinitionFieldsSerializerTracker.
				getDEDataDefinitionFieldsSerializer("json");

		DEDataDefinitionFieldsSerializerApplyRequest
			deDataDefinitionFieldsSerializerApplyRequest =
				DEDataDefinitionFieldsSerializerApplyRequest.Builder.of(
					deDataDefinition.getDEDataDefinitionFields());

		DEDataDefinitionFieldsSerializerApplyResponse
			deDataDefinitionFieldsSerializerApplyResponse =
				deDataDefinitionFieldsSerializer.apply(
					deDataDefinitionFieldsSerializerApplyRequest);

		return deDataDefinitionFieldsSerializerApplyResponse.getContent();
	}

	protected void updateDDMStructure(
			long userId, DEDataDefinition deDataDefinition,
			ServiceContext serviceContext)
		throws PortalException {

		Map<Locale, String> nameMap = createLocalizedMap(
			deDataDefinition.getName());
		Map<Locale, String> descriptionMap = createLocalizedMap(
			deDataDefinition.getDescription());

		_ddmStructureLocalService.updateStructure(
			userId, deDataDefinition.getDEDataDefinitionId(),
			DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID, nameMap,
			descriptionMap, serialize(deDataDefinition), serviceContext);
	}

	private final DDMStructureLocalService _ddmStructureLocalService;
	private final DEDataDefinitionFieldsSerializerTracker
		_deDataDefinitionFieldsSerializerTracker;
	private final Portal _portal;
	private final ResourceLocalService _resourceLocalService;

}