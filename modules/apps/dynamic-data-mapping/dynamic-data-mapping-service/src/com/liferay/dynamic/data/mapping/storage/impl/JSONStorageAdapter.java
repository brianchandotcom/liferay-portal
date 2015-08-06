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

package com.liferay.dynamic.data.mapping.storage.impl;

import com.liferay.dynamic.data.mapping.io.DDMFormValuesJSONDeserializerUtil;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesJSONSerializerUtil;
import com.liferay.dynamic.data.mapping.model.DDMContent;
import com.liferay.dynamic.data.mapping.model.DDMStorageLink;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMContentLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMStorageLinkLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.dynamic.data.mapping.storage.BaseStorageAdapter;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;

import java.util.List;

/**
 * @author Pablo Carvalho
 */
public class JSONStorageAdapter extends BaseStorageAdapter {

	@Override
	public long doCreate(
			long companyId, long ddmStructureId, DDMFormValues ddmFormValues,
			ServiceContext serviceContext)
		throws Exception {

		long classNameId = PortalUtil.getClassNameId(
			DDMContent.class.getName());

		String serializedDDMFormValues =
			DDMFormValuesJSONSerializerUtil.serialize(ddmFormValues);

		DDMContent ddmContent = DDMContentLocalServiceUtil.addContent(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(),
			DDMStorageLink.class.getName(), null, serializedDDMFormValues,
			serviceContext);

		DDMStorageLinkLocalServiceUtil.addStorageLink(
			classNameId, ddmContent.getPrimaryKey(), ddmStructureId,
			serviceContext);

		return ddmContent.getPrimaryKey();
	}

	@Override
	public void doUpdate(
			long classPK, DDMFormValues ddmFormValues,
			ServiceContext serviceContext)
		throws Exception {

		DDMContent ddmContent = DDMContentLocalServiceUtil.getContent(classPK);

		ddmContent.setModifiedDate(serviceContext.getModifiedDate(null));

		String serializedDDMFormValues =
			DDMFormValuesJSONSerializerUtil.serialize(ddmFormValues);

		ddmContent.setData(serializedDDMFormValues);

		DDMContentLocalServiceUtil.updateContent(
			ddmContent.getPrimaryKey(), ddmContent.getName(),
			ddmContent.getDescription(), ddmContent.getData(), serviceContext);
	}

	@Override
	public String getStorageType() {
		return StorageType.JSON.toString();
	}

	@Override
	protected void doDeleteByClass(long classPK) throws Exception {
		DDMContentLocalServiceUtil.deleteDDMContent(classPK);

		DDMStorageLinkLocalServiceUtil.deleteClassStorageLink(classPK);
	}

	@Override
	protected void doDeleteByDDMStructure(long ddmStructureId)
		throws Exception {

		List<DDMStorageLink> ddmStorageLinks =
			DDMStorageLinkLocalServiceUtil.getStructureStorageLinks(
				ddmStructureId);

		for (DDMStorageLink ddmStorageLink : ddmStorageLinks) {
			doDeleteByClass(ddmStorageLink.getClassPK());
		}
	}

	@Override
	protected DDMFormValues doGetDDMFormValues(long classPK) throws Exception {
		DDMContent ddmContent = DDMContentLocalServiceUtil.getContent(classPK);

		DDMStorageLink ddmStorageLink =
			DDMStorageLinkLocalServiceUtil.getClassStorageLink(
				ddmContent.getPrimaryKey());

		DDMStructure ddmStructure = DDMStructureLocalServiceUtil.getStructure(
			ddmStorageLink.getStructureId());

		DDMFormValues ddmFormValues =
			DDMFormValuesJSONDeserializerUtil.deserialize(
				ddmStructure.getDDMForm(), ddmContent.getData());

		return ddmFormValues;
	}

}