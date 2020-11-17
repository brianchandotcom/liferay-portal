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

package com.liferay.data.engine.rest.internal.storage;

import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.data.engine.rest.dto.v2_0.DataRecordCollection;
import com.liferay.data.engine.rest.internal.dto.v2_0.util.DataRecordCollectionUtil;
import com.liferay.data.engine.rest.internal.dto.v2_0.util.MapToDDMFormValuesConverterUtil;
import com.liferay.data.engine.rest.internal.storage.util.DataStorageUtil;
import com.liferay.data.engine.storage.DataStorage;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMFieldLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(
	immediate = true, property = "data.storage.type=default",
	service = DataStorage.class
)
public class DefaultDataStorage implements DataStorage {

	@Override
	public long delete(long dataStorageId) {
		_ddmFieldLocalService.deleteDDMFormValues(dataStorageId);

		return dataStorageId;
	}

	@Override
	public Map<String, Object> get(long dataDefinitionId, long dataStorageId)
		throws PortalException {

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			dataDefinitionId);

		return DataStorageUtil.toDataRecordValues(
			_ddmFieldLocalService.getDDMFormValues(
				ddmStructure.getFullHierarchyDDMForm(), dataStorageId),
			ddmStructure);
	}

	@Override
	public long save(
			long dataRecordCollectionId, Map<String, Object> dataRecordValues,
			long siteId)
		throws PortalException {

		DataRecordCollection dataRecordCollection =
			DataRecordCollectionUtil.toDataRecordCollection(
				_ddlRecordSetLocalService.getRecordSet(dataRecordCollectionId));

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			dataRecordCollection.getDataDefinitionId());

		long primaryKey = _counterLocalService.increment();

		_ddmFieldLocalService.updateDDMFormValues(
			ddmStructure.getStructureId(), primaryKey,
			MapToDDMFormValuesConverterUtil.toDDMFormValues(
				dataRecordValues, ddmStructure.getFullHierarchyDDMForm(),
				null));

		return primaryKey;
	}

	@Reference
	private CounterLocalService _counterLocalService;

	@Reference
	private DDLRecordSetLocalService _ddlRecordSetLocalService;

	@Reference
	private DDMFieldLocalService _ddmFieldLocalService;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

}