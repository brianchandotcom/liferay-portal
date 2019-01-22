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

import com.liferay.data.engine.constants.DEDataRecordCollectionConstants;
import com.liferay.data.engine.model.DEDataDefinition;
import com.liferay.data.engine.model.DEDataRecordCollection;
import com.liferay.data.engine.service.DEDataRecordCollectionSaveRequest;
import com.liferay.data.engine.service.DEDataRecordCollectionSaveResponse;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordSetConstants;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Leonardo Barros
 */
public class DEDataRecordCollectionSaveRequestExecutor {

	public DEDataRecordCollectionSaveRequestExecutor(
		DDLRecordSetLocalService ddlRecordSetLocalService,
		ResourceLocalService resourceLocalService) {

		_ddlRecordSetLocalService = ddlRecordSetLocalService;
		_resourceLocalService = resourceLocalService;
	}

	public DEDataRecordCollectionSaveResponse execute(
			DEDataRecordCollectionSaveRequest deDataRecordCollectionSaveRequest)
		throws Exception {

		DEDataRecordCollection deDataRecordCollection =
			deDataRecordCollectionSaveRequest.getDEDataRecordCollection();

		long deDataRecordCollectionId =
			deDataRecordCollection.getDEDataRecordCollectionId();

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		serviceContext.setAttribute("addRecordSetResources", Boolean.FALSE);

		if (deDataRecordCollectionId == 0) {
			DDLRecordSet ddlRecordSet = createDDLRecordSet(
				deDataRecordCollectionSaveRequest.getUserId(),
				deDataRecordCollectionSaveRequest.getGroupId(),
				deDataRecordCollection, serviceContext);

			deDataRecordCollectionId = ddlRecordSet.getRecordSetId();

			_resourceLocalService.addModelResources(
				ddlRecordSet.getCompanyId(),
				deDataRecordCollectionSaveRequest.getGroupId(),
				deDataRecordCollectionSaveRequest.getUserId(),
				DEDataRecordCollectionConstants.MODEL_RESOURCE_NAME,
				deDataRecordCollectionId, serviceContext.getModelPermissions());
		}
		else {
			updateDDLRecordSet(deDataRecordCollection, serviceContext);
		}

		return DEDataRecordCollectionSaveResponse.Builder.of(
			deDataRecordCollectionId);
	}

	protected DDLRecordSet createDDLRecordSet(
			long userId, long groupId,
			DEDataRecordCollection deDataRecordCollection,
			ServiceContext serviceContext)
		throws PortalException {

		Map<Locale, String> nameMap = createLocalizedMap(
			deDataRecordCollection.getName());
		Map<Locale, String> descriptionMap = createLocalizedMap(
			deDataRecordCollection.getDescription());

		DEDataDefinition deDataDefinition =
			deDataRecordCollection.getDEDataDefinition();

		return _ddlRecordSetLocalService.addRecordSet(
			userId, groupId, deDataDefinition.getDEDataDefinitionId(), null,
			nameMap, descriptionMap, 0, DDLRecordSetConstants.SCOPE_DATA_ENGINE,
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

	protected void updateDDLRecordSet(
			DEDataRecordCollection deDataRecordCollection,
			ServiceContext serviceContext)
		throws PortalException {

		Map<Locale, String> nameMap = createLocalizedMap(
			deDataRecordCollection.getName());
		Map<Locale, String> descriptionMap = createLocalizedMap(
			deDataRecordCollection.getDescription());

		DEDataDefinition deDataDefinition =
			deDataRecordCollection.getDEDataDefinition();

		_ddlRecordSetLocalService.updateRecordSet(
			deDataRecordCollection.getDEDataRecordCollectionId(),
			deDataDefinition.getDEDataDefinitionId(), nameMap, descriptionMap,
			0, serviceContext);
	}

	private final DDLRecordSetLocalService _ddlRecordSetLocalService;
	private final ResourceLocalService _resourceLocalService;

}