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

import com.liferay.data.engine.model.DEDataRecord;
import com.liferay.data.engine.rest.dto.v1_0.DataRecord;
import com.liferay.data.engine.rest.dto.v1_0.DataRecordCollection;
import com.liferay.data.engine.rest.dto.v1_0.DataRecordValue;
import com.liferay.data.engine.rest.internal.dto.v1_0.util.DataRecordUtil;
import com.liferay.data.engine.rest.resource.v1_0.DataDefinitionResource;
import com.liferay.data.engine.rest.resource.v1_0.DataRecordCollectionResource;
import com.liferay.data.engine.storage.DEDataStorage;
import com.liferay.dynamic.data.mapping.model.DDMContent;
import com.liferay.dynamic.data.mapping.service.DDMContentLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true, property = "de.data.storage.type=json",
	service = DEDataStorage.class
)
public class DEDataJSONStorage implements DEDataStorage {

	@Override
	public long delete(long dataStorageId) throws Exception {
		DDMContent ddmContent = ddmContentLocalService.fetchDDMContent(
			dataStorageId);

		if (ddmContent != null) {
			ddmContentLocalService.deleteDDMContent(ddmContent);
		}

		return dataStorageId;
	}

	@Override
	public DataRecordValue[] get(long dataDefinitionId, long dataStorageId)
		throws Exception {

		DDMContent ddmContent = ddmContentLocalService.getContent(
			dataStorageId);

		return DataRecordUtil.toDataRecordValues(
			dataDefinitionResource.getDataDefinition(dataDefinitionId),
			ddmContent.getData());
	}

	@Override
	public long save(long groupId, DataRecord dataRecord) throws Exception {
		DataRecordCollection dataRecordCollection =
			dataRecordCollectionResource.getDataRecordCollection(
				dataRecord.getId());

		return insert(
			PrincipalThreadLocal.getUserId(), groupId,
			DataRecordUtil.toJSONString(
				dataDefinitionResource.getDataDefinition(
					dataRecordCollection.getDataDefinitionId()),
				dataRecord.getDataRecordValues()));
	}

	protected long insert(long userId, long groupId, String content)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(groupId);
		serviceContext.setUserId(userId);

		DDMContent ddmContent = ddmContentLocalService.addContent(
			userId, groupId, DEDataRecord.class.getName(), null, content,
			serviceContext);

		return ddmContent.getPrimaryKey();
	}

	@Reference
	protected DataDefinitionResource dataDefinitionResource;

	@Reference
	protected DataRecordCollectionResource dataRecordCollectionResource;

	@Reference
	protected DDMContentLocalService ddmContentLocalService;

}