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

package com.liferay.portlet.dynamicdatamapping.service.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.model.SystemEventConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.dynamicdatamapping.io.DDMFormLayoutJSONSerializerUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormLayout;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureLayout;
import com.liferay.portlet.dynamicdatamapping.service.base.DDMStructureLayoutLocalServiceBaseImpl;

/**
 * @author Marcellus Tavares
 */
@ProviderType
public class DDMStructureLayoutLocalServiceImpl
	extends DDMStructureLayoutLocalServiceBaseImpl {

	@Override
	public DDMStructureLayout addStructureLayout(
			long userId, long groupId, long structureVersionId,
			DDMFormLayout ddmFormLayout, ServiceContext serviceContext)
		throws PortalException {

		User user = userPersistence.findByPrimaryKey(userId);

		long structureLayoutId = counterLocalService.increment();

		DDMStructureLayout structureLayout =
			ddmStructureLayoutPersistence.create(structureLayoutId);

		structureLayout.setUuid(serviceContext.getUuid());
		structureLayout.setGroupId(groupId);
		structureLayout.setCompanyId(user.getCompanyId());
		structureLayout.setUserId(user.getUserId());
		structureLayout.setUserName(user.getFullName());
		structureLayout.setStructureVersionId(structureVersionId);
		structureLayout.setDefinition(
			DDMFormLayoutJSONSerializerUtil.serialize(ddmFormLayout));

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
	public DDMStructureLayout updateStructureLayout(
			long structureLayoutId, DDMFormLayout ddmFormLayout,
			ServiceContext serviceContext)
		throws PortalException {

		DDMStructureLayout structureLayout =
			ddmStructureLayoutPersistence.findByPrimaryKey(structureLayoutId);

		structureLayout.setDefinition(
			DDMFormLayoutJSONSerializerUtil.serialize(ddmFormLayout));

		return ddmStructureLayoutPersistence.update(structureLayout);
	}

}