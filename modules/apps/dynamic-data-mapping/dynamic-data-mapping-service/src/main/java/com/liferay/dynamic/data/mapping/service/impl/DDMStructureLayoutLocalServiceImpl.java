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

package com.liferay.dynamic.data.mapping.service.impl;

import com.liferay.dynamic.data.mapping.io.DDMFormLayoutDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutDeserializerTracker;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializerSerializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializerSerializeResponse;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutSerializerTracker;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.dynamic.data.mapping.service.base.DDMStructureLayoutLocalServiceBaseImpl;
import com.liferay.dynamic.data.mapping.validator.DDMFormLayoutValidator;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.spring.extender.service.ServiceReference;

/**
 * @author Marcellus Tavares
 */
public class DDMStructureLayoutLocalServiceImpl
	extends DDMStructureLayoutLocalServiceBaseImpl {

	@Override
	public DDMStructureLayout addStructureLayout(
			long userId, long groupId, long structureVersionId,
			DDMFormLayout ddmFormLayout, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		validate(ddmFormLayout);

		long structureLayoutId = counterLocalService.increment();

		DDMStructureLayout structureLayout =
			ddmStructureLayoutPersistence.create(structureLayoutId);

		structureLayout.setUuid(serviceContext.getUuid());
		structureLayout.setGroupId(groupId);
		structureLayout.setCompanyId(user.getCompanyId());
		structureLayout.setUserId(user.getUserId());
		structureLayout.setUserName(user.getFullName());
		structureLayout.setStructureVersionId(structureVersionId);
		structureLayout.setDefinition(serialize(ddmFormLayout));

		return ddmStructureLayoutPersistence.update(structureLayout);
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public void deleteStructureLayout(DDMStructureLayout structureLayout) {
		ddmStructureLayoutPersistence.remove(structureLayout);
	}

	@Override
	public void deleteStructureLayout(long structureLayoutId)
		throws PortalException {

		DDMStructureLayout structureLayout =
			ddmStructureLayoutPersistence.findByPrimaryKey(structureLayoutId);

		ddmStructureLayoutLocalService.deleteStructureLayout(structureLayout);
	}

	@Override
	public DDMStructureLayout getStructureLayout(long structureLayoutId)
		throws PortalException {

		return ddmStructureLayoutPersistence.findByPrimaryKey(
			structureLayoutId);
	}

	@Override
	public DDMStructureLayout getStructureLayoutByStructureVersionId(
			long structureVersionId)
		throws PortalException {

		return ddmStructureLayoutPersistence.findByStructureVersionId(
			structureVersionId);
	}

	@Override
	public DDMFormLayout getStructureLayoutDDMFormLayout(
		DDMStructureLayout structureLayout) {

		DDMFormLayoutDeserializer ddmFormLayoutDeserializer =
			ddmFormLayoutDeserializerTracker.getDDMFormLayoutDeserializer(
				"json");

		DDMFormLayoutDeserializerDeserializeRequest.Builder builder =
			DDMFormLayoutDeserializerDeserializeRequest.Builder.newBuilder(
				structureLayout.getDefinition());

		DDMFormLayoutDeserializerDeserializeResponse
			ddmFormLayoutDeserializerDeserializeResponse =
				ddmFormLayoutDeserializer.deserialize(builder.build());

		return ddmFormLayoutDeserializerDeserializeResponse.getDDMFormLayout();
	}

	@Override
	public DDMStructureLayout updateStructureLayout(
			long structureLayoutId, DDMFormLayout ddmFormLayout,
			ServiceContext serviceContext)
		throws PortalException {

		DDMStructureLayout structureLayout =
			ddmStructureLayoutPersistence.findByPrimaryKey(structureLayoutId);

		validate(ddmFormLayout);

		structureLayout.setDefinition(serialize(ddmFormLayout));

		return ddmStructureLayoutPersistence.update(structureLayout);
	}

	protected String serialize(DDMFormLayout ddmFormLayout) {
		DDMFormLayoutSerializer ddmFormLayoutSerializer =
			ddmFormLayoutSerializerTracker.getDDMFormLayoutSerializer("json");

		DDMFormLayoutSerializerSerializeRequest.Builder builder =
			DDMFormLayoutSerializerSerializeRequest.Builder.newBuilder(
				ddmFormLayout);

		DDMFormLayoutSerializerSerializeResponse
			ddmFormLayoutSerializerSerializeResponse =
				ddmFormLayoutSerializer.serialize(builder.build());

		return ddmFormLayoutSerializerSerializeResponse.getContent();
	}

	protected void validate(DDMFormLayout ddmFormLayout)
		throws PortalException {

		ddmFormLayoutValidator.validate(ddmFormLayout);
	}

	@ServiceReference(type = DDMFormLayoutDeserializerTracker.class)
	protected DDMFormLayoutDeserializerTracker ddmFormLayoutDeserializerTracker;

	@ServiceReference(type = DDMFormLayoutSerializerTracker.class)
	protected DDMFormLayoutSerializerTracker ddmFormLayoutSerializerTracker;

	@ServiceReference(type = DDMFormLayoutValidator.class)
	protected DDMFormLayoutValidator ddmFormLayoutValidator;

}