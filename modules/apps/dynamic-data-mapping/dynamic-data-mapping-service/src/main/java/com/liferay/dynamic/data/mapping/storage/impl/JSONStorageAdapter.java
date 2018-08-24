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

import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializerTracker;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializerSerializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializerSerializeResponse;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializerTracker;
import com.liferay.dynamic.data.mapping.model.DDMContent;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStorageLink;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.DDMContentLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStorageLinkLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureVersionLocalService;
import com.liferay.dynamic.data.mapping.storage.BaseStorageAdapter;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.StorageAdapter;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.validator.DDMFormValuesValidator;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pablo Carvalho
 */
@Component(service = StorageAdapter.class)
public class JSONStorageAdapter extends BaseStorageAdapter {

	@Override
	public long doCreate(
			long companyId, long ddmStructureId, DDMFormValues ddmFormValues,
			ServiceContext serviceContext)
		throws Exception {

		validate(ddmFormValues, serviceContext);

		long classNameId = _portal.getClassNameId(DDMContent.class.getName());

		String serializedDDMFormValues = serialize(ddmFormValues);

		DDMContent ddmContent = _ddmContentLocalService.addContent(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(),
			DDMStorageLink.class.getName(), null, serializedDDMFormValues,
			serviceContext);

		DDMStructure ddmStructure = _ddmStructureLocalService.getDDMStructure(
			ddmStructureId);

		DDMStructureVersion ddmStructureVersion =
			ddmStructure.getLatestStructureVersion();

		_ddmStorageLinkLocalService.addStorageLink(
			classNameId, ddmContent.getPrimaryKey(),
			ddmStructureVersion.getStructureVersionId(), serviceContext);

		return ddmContent.getPrimaryKey();
	}

	@Override
	public void doUpdate(
			long classPK, DDMFormValues ddmFormValues,
			ServiceContext serviceContext)
		throws Exception {

		validate(ddmFormValues, serviceContext);

		DDMContent ddmContent = _ddmContentLocalService.getContent(classPK);

		ddmContent.setModifiedDate(serviceContext.getModifiedDate(null));

		String serializedDDMFormValues = serialize(ddmFormValues);

		ddmContent.setData(serializedDDMFormValues);

		_ddmContentLocalService.updateContent(
			ddmContent.getPrimaryKey(), ddmContent.getName(),
			ddmContent.getDescription(), ddmContent.getData(), serviceContext);
	}

	@Override
	public String getStorageType() {
		return StorageType.JSON.toString();
	}

	protected DDMFormValues deserialize(String content, DDMForm ddmForm) {
		DDMFormValuesDeserializer ddmFormValuesDeserializer =
			_ddmFormValuesDeserializerTracker.getDDMFormValuesDeserializer(
				"json");

		DDMFormValuesDeserializerDeserializeRequest.Builder builder =
			DDMFormValuesDeserializerDeserializeRequest.Builder.newBuilder(
				content, ddmForm);

		DDMFormValuesDeserializerDeserializeResponse
			ddmFormValuesDeserializerDeserializeResponse =
				ddmFormValuesDeserializer.deserialize(builder.build());

		return ddmFormValuesDeserializerDeserializeResponse.getDDMFormValues();
	}

	@Override
	protected void doDeleteByClass(long classPK) throws Exception {
		_ddmContentLocalService.deleteDDMContent(classPK);

		_ddmStorageLinkLocalService.deleteClassStorageLink(classPK);
	}

	@Override
	protected void doDeleteByDDMStructure(long ddmStructureId)
		throws Exception {

		List<DDMStorageLink> ddmStorageLinks =
			_ddmStorageLinkLocalService.getStructureStorageLinks(
				ddmStructureId);

		for (DDMStorageLink ddmStorageLink : ddmStorageLinks) {
			doDeleteByClass(ddmStorageLink.getClassPK());
		}
	}

	@Override
	protected DDMFormValues doGetDDMFormValues(long classPK) throws Exception {
		DDMContent ddmContent = _ddmContentLocalService.getContent(classPK);

		DDMStorageLink ddmStorageLink =
			_ddmStorageLinkLocalService.getClassStorageLink(
				ddmContent.getPrimaryKey());

		DDMStructureVersion ddmStructureVersion =
			_ddmStructureVersionLocalService.getDDMStructureVersion(
				ddmStorageLink.getStructureVersionId());

		return deserialize(
			ddmContent.getData(), ddmStructureVersion.getDDMForm());
	}

	protected String serialize(DDMFormValues ddmFormValues) {
		DDMFormValuesSerializer ddmFormValuesSerializer =
			_ddmFormValuesSerializerTracker.getDDMFormValuesSerializer("json");

		DDMFormValuesSerializerSerializeRequest.Builder builder =
			DDMFormValuesSerializerSerializeRequest.Builder.newBuilder(
				ddmFormValues);

		DDMFormValuesSerializerSerializeResponse
			ddmFormValuesSerializerSerializeResponse =
				ddmFormValuesSerializer.serialize(builder.build());

		return ddmFormValuesSerializerSerializeResponse.getContent();
	}

	@Reference(unbind = "-")
	protected void setDDMContentLocalService(
		DDMContentLocalService ddmContentLocalService) {

		_ddmContentLocalService = ddmContentLocalService;
	}

	@Reference(unbind = "-")
	protected void setDDMFormValuesDeserializerTracker(
		DDMFormValuesDeserializerTracker ddmFormValuesDeserializerTracker) {

		_ddmFormValuesDeserializerTracker = ddmFormValuesDeserializerTracker;
	}

	@Reference(unbind = "-")
	protected void setDDMFormValuesSerializerTracker(
		DDMFormValuesSerializerTracker ddmFormValuesSerializerTracker) {

		_ddmFormValuesSerializerTracker = ddmFormValuesSerializerTracker;
	}

	@Reference(unbind = "-")
	protected void setDDMFormValuesValidator(
		DDMFormValuesValidator ddmFormValuesValidator) {

		_ddmFormValuesValidator = ddmFormValuesValidator;
	}

	@Reference(unbind = "-")
	protected void setDDMStorageLinkLocalService(
		DDMStorageLinkLocalService ddmStorageLinkLocalService) {

		_ddmStorageLinkLocalService = ddmStorageLinkLocalService;
	}

	@Reference(unbind = "-")
	protected void setDDMStructureLocalService(
		DDMStructureLocalService ddmStructureLocalService) {

		_ddmStructureLocalService = ddmStructureLocalService;
	}

	@Reference(unbind = "-")
	protected void setDDMStructureVersionLocalService(
		DDMStructureVersionLocalService ddmStructureVersionLocalService) {

		_ddmStructureVersionLocalService = ddmStructureVersionLocalService;
	}

	protected void validate(
			DDMFormValues ddmFormValues, ServiceContext serviceContext)
		throws Exception {

		boolean validateDDMFormValues = GetterUtil.getBoolean(
			serviceContext.getAttribute("validateDDMFormValues"), true);

		if (!validateDDMFormValues) {
			return;
		}

		_ddmFormValuesValidator.validate(ddmFormValues);
	}

	private DDMContentLocalService _ddmContentLocalService;
	private DDMFormValuesDeserializerTracker _ddmFormValuesDeserializerTracker;
	private DDMFormValuesSerializerTracker _ddmFormValuesSerializerTracker;
	private DDMFormValuesValidator _ddmFormValuesValidator;
	private DDMStorageLinkLocalService _ddmStorageLinkLocalService;
	private DDMStructureLocalService _ddmStructureLocalService;
	private DDMStructureVersionLocalService _ddmStructureVersionLocalService;

	@Reference
	private Portal _portal;

}