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
import com.liferay.data.engine.rest.internal.dto.v1_0.util.DataDefinitionUtil;
import com.liferay.data.engine.rest.internal.dto.v1_0.util.DataRecordCollectionUtil;
import com.liferay.data.engine.rest.internal.dto.v1_0.util.DataRecordValueUtil;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.mapping.model.DDMContent;
import com.liferay.dynamic.data.mapping.service.DDMContentLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;

/**
 * @author Jeyvison Nascimento
 */
public class DataStorage {

	public DataStorage(
		DDLRecordSetLocalService ddlRecordSetLocalService,
		DDMContentLocalService ddmContentLocalService,
		DDMStructureLocalService ddmStructureLocalService) {

		_ddlRecordSetLocalService = ddlRecordSetLocalService;
		_ddmContentLocalService = ddmContentLocalService;
		_ddmStructureLocalService = ddmStructureLocalService;
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
			DataDefinitionUtil.toDataDefinition(
				_ddmStructureLocalService.getStructure(dataDefinitionId)),
			ddmContent.getData());
	}

	public long save(long siteId, DataRecord dataRecord) throws Exception {
		DataRecordCollection dataRecordCollection =
			DataRecordCollectionUtil.toDataRecordCollection(
				_ddlRecordSetLocalService.getRecordSet(
					dataRecord.getDataRecordCollectionId()));

		DDMContent ddmContent = _ddmContentLocalService.addContent(
			PrincipalThreadLocal.getUserId(), siteId,
			DataRecord.class.getName(), null,
			DataRecordValueUtil.toJSON(
				DataDefinitionUtil.toDataDefinition(
					_ddmStructureLocalService.getStructure(
						dataRecordCollection.getDataDefinitionId())),
				dataRecord.getDataRecordValues()),
			new ServiceContext() {
				{
					setScopeGroupId(siteId);
					setUserId(PrincipalThreadLocal.getUserId());
				}
			});

		return ddmContent.getPrimaryKey();
	}

	private final DDLRecordSetLocalService _ddlRecordSetLocalService;
	private final DDMContentLocalService _ddmContentLocalService;
	private final DDMStructureLocalService _ddmStructureLocalService;

}