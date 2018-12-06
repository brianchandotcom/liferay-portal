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

import com.liferay.data.engine.exception.DEDataDefinitionException;
import com.liferay.data.engine.exception.DEDataDefinitionFieldsSerializerException;
import com.liferay.data.engine.executor.DEDataDefinitionSaveRequestExecutor;
import com.liferay.data.engine.internal.io.DEDataDefinitionFieldsSerializerTracker;
import com.liferay.data.engine.io.DEDataDefinitionFieldsSerializer;
import com.liferay.data.engine.io.DEDataDefinitionFieldsSerializerApplyRequest;
import com.liferay.data.engine.io.DEDataDefinitionFieldsSerializerApplyResponse;
import com.liferay.data.engine.model.DEDataDefinition;
import com.liferay.data.engine.service.DEDataDefinitionSaveRequest;
import com.liferay.data.engine.service.DEDataDefinitionSaveResponse;
import com.liferay.dynamic.data.lists.model.DDLRecordSetConstants;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jeyvison Nascimento
 */
@Component(
	immediate = true, service = DEDataDefinitionSaveRequestExecutor.class
)
public class DEDataDefinitionSaveRequestExecutorImpl
	implements DEDataDefinitionSaveRequestExecutor {

	@Override
	public DEDataDefinitionSaveResponse execute(
			DEDataDefinitionSaveRequest deDataDefinitionSaveRequest)
		throws DEDataDefinitionException {

		try {
			long userId = deDataDefinitionSaveRequest.getUserId();

			long groupId = deDataDefinitionSaveRequest.getGroupId();

			long classNameId = portal.getClassNameId(DEDataDefinition.class);

			DEDataDefinition deDataDefinition =
				deDataDefinitionSaveRequest.getDEDataDefinition();

			long deDataDefinitionId = deDataDefinition.getDEDataDefinitionId();

			ServiceContext serviceContext =
				ServiceContextThreadLocal.getServiceContext();

			if (deDataDefinitionId == 0) {
				DDMStructure ddmStructure = createDDMStructure(
					userId, groupId, classNameId, deDataDefinition,
					serviceContext);

				deDataDefinitionId = ddmStructure.getStructureId();

				resourceLocalService.addModelResources(
					ddmStructure.getCompanyId(), groupId, userId,
					DEDataDefinition.class.getName(), deDataDefinitionId,
					serviceContext.getModelPermissions());

				ddlRecordSetLocalService.addRecordSet(
					userId, groupId, deDataDefinitionId,
					String.valueOf(deDataDefinitionId),
					ddmStructure.getNameMap(), ddmStructure.getDescriptionMap(),
					0, DDLRecordSetConstants.SCOPE_DATA_ENGINE, serviceContext);
			}
			else {
				updateDDMStructure(userId, deDataDefinition, serviceContext);
			}

			return DEDataDefinitionSaveResponse.Builder.of(deDataDefinitionId);
		}
		catch (Exception e)
		{
			throw new DEDataDefinitionException(e);
		}
	}

	protected DDMStructure createDDMStructure(
			long userId, long groupId, long classNameId,
			DEDataDefinition deDataDefinition, ServiceContext serviceContext)
		throws PortalException {

		Map<Locale, String> nameMap = createLocalizedMap(
			deDataDefinition.getName());

		Map<Locale, String> descriptionMap = createLocalizedMap(
			deDataDefinition.getDescription());

		return ddmStructureLocalService.addStructure(
			groupId, userId, DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			classNameId, null, nameMap, descriptionMap,
			serialize(deDataDefinition), deDataDefinition.getStorageType(),
			serviceContext);
	}

	protected Map<Locale, String> createLocalizedMap(Map<String, String> map) {
		Set<Map.Entry<String, String>> entrySet = map.entrySet();

		Stream<Map.Entry<String, String>> entryStream = entrySet.stream();

		return entryStream.collect(
			Collectors.toMap(
				entry -> LocaleUtil.fromLanguageId(entry.getKey()),
				entry -> entry.getValue()));
	}

	protected String serialize(DEDataDefinition deDataDefinition)
		throws DEDataDefinitionFieldsSerializerException {

		DEDataDefinitionFieldsSerializer deDataDefinitionFieldsSerializer =
			deDataDefinitionFieldsSerializerTracker.
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

		ddmStructureLocalService.updateStructure(
			deDataDefinition.getDEDataDefinitionId(), userId,
			DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID, nameMap,
			descriptionMap, serialize(deDataDefinition), serviceContext);
	}

	@Reference
	protected DDLRecordSetLocalService ddlRecordSetLocalService;

	@Reference
	protected DDMStructureLocalService ddmStructureLocalService;

	@Reference
	protected DEDataDefinitionFieldsSerializerTracker
		deDataDefinitionFieldsSerializerTracker;

	@Reference
	protected Portal portal;

	@Reference
	protected ResourceLocalService resourceLocalService;

}