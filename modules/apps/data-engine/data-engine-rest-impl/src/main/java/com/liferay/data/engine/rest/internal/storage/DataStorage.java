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

import com.liferay.data.engine.rest.dto.v1_0.DataRecord;
import com.liferay.data.engine.rest.dto.v1_0.DataRecordCollection;
import com.liferay.data.engine.rest.dto.v1_0.DataRecordValue;
import com.liferay.data.engine.rest.internal.dto.v1_0.util.DataRecordValueUtil;
import com.liferay.data.engine.rest.resource.v1_0.DataDefinitionResource;
import com.liferay.data.engine.rest.resource.v1_0.DataRecordCollectionResource;
import com.liferay.dynamic.data.mapping.model.DDMContent;
import com.liferay.dynamic.data.mapping.service.DDMContentLocalService;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;

/**
 * @author Jeyvison Nascimento
 */
public class DataStorage {

	public DataStorage(
		DataRecordCollectionResource dataRecordCollectionResource,
		DataDefinitionResource dataDefinitionResource,
		DDMContentLocalService ddmContentLocalService) {

		_dataRecordCollectionResource = dataRecordCollectionResource;
		_dataDefinitionResource = dataDefinitionResource;
		_ddmContentLocalService = ddmContentLocalService;
	}

	public long delete(long dataStorageId) throws Exception {
		DDMContent ddmContent = _ddmContentLocalService.fetchDDMContent(
			dataStorageId);

		if (ddmContent != null) {
			_ddmContentLocalService.deleteDDMContent(ddmContent);
		}

		return dataStorageId;
	}

	public DataRecordValue[] get(long dataDefinitionId, long dataStorageId)
		throws Exception {

		DDMContent ddmContent = _ddmContentLocalService.getContent(
			dataStorageId);

		return DataRecordValueUtil.toDataRecordValues(
			_dataDefinitionResource.getDataDefinition(dataDefinitionId),
			ddmContent.getData());
	}

	public long save(long groupId, DataRecord dataRecord) throws Exception {
		DataRecordCollection dataRecordCollection =
			_dataRecordCollectionResource.getDataRecordCollection(
				dataRecord.getDataRecordCollectionId());

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(groupId);
		serviceContext.setUserId(PrincipalThreadLocal.getUserId());

		DDMContent ddmContent = _ddmContentLocalService.addContent(
			PrincipalThreadLocal.getUserId(), groupId,
			DataRecord.class.getName(), null,
			DataRecordValueUtil.toJSON(
				_dataDefinitionResource.getDataDefinition(
					dataRecordCollection.getDataDefinitionId()),
				dataRecord.getDataRecordValues()),
			serviceContext);

		return ddmContent.getPrimaryKey();
	}

	private final DataDefinitionResource _dataDefinitionResource;
	private final DataRecordCollectionResource _dataRecordCollectionResource;
	private final DDMContentLocalService _ddmContentLocalService;

}