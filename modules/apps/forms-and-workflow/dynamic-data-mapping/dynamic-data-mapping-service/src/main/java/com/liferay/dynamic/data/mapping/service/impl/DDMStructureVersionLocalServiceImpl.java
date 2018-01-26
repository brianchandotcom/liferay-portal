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

import com.liferay.dynamic.data.mapping.exception.NoSuchStructureVersionException;
import com.liferay.dynamic.data.mapping.io.DDMFormJSONDeserializer;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.base.DDMStructureVersionLocalServiceBaseImpl;
import com.liferay.dynamic.data.mapping.util.comparator.StructureVersionVersionComparator;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Pablo Carvalho
 */
public class DDMStructureVersionLocalServiceImpl
	extends DDMStructureVersionLocalServiceBaseImpl {

	@Override
	public DDMStructureVersion addStructureVersion(
			long userId, long parentStructureId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String definition,
			String version, DDMStructure structure, DDMFormLayout ddmFormLayout,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		long structureVersionId = counterLocalService.increment();

		DDMStructureVersion structureVersion =
			ddmStructureVersionPersistence.create(structureVersionId);

		structureVersion.setUuid(serviceContext.getUuid());
		structureVersion.setGroupId(structure.getGroupId());
		structureVersion.setCompanyId(structure.getCompanyId());
		structureVersion.setUserId(user.getUserId());
		structureVersion.setUserName(user.getFullName());
		structureVersion.setCreateDate(structure.getModifiedDate());
		structureVersion.setModifiedDate(structure.getModifiedDate());
		structureVersion.setStructureId(structure.getStructureId());
		structureVersion.setVersion(version);
		structureVersion.setParentStructureId(parentStructureId);
		structureVersion.setNameMap(nameMap);
		structureVersion.setDescriptionMap(descriptionMap);
		structureVersion.setDefinition(definition);
		structureVersion.setStorageType(structure.getStorageType());
		structureVersion.setType(structure.getType());

		int status = GetterUtil.getInteger(
			serviceContext.getAttribute("status"),
			WorkflowConstants.STATUS_APPROVED);

		structureVersion.setStatus(status);

		structureVersion.setStatusByUserId(user.getUserId());
		structureVersion.setStatusByUserName(user.getFullName());
		structureVersion.setStatusDate(structure.getModifiedDate());

		ddmStructureVersionPersistence.update(structureVersion);

		ddmStructureLayoutLocalService.addStructureLayout(
			user.getUserId(), structureVersion.getGroupId(),
			structureVersion.getStructureVersionId(), ddmFormLayout,
			serviceContext);

		return structureVersion;
	}

	@Override
	public void deleteStructureVersion(long structureId)
		throws PortalException {

		List<DDMStructureVersion> structureVersions =
			ddmStructureVersionLocalService.getStructureVersions(structureId);

		for (DDMStructureVersion structureVersion : structureVersions) {
			ddmStructureLayoutPersistence.removeByStructureVersionId(
				structureVersion.getStructureVersionId());

			ddmStructureVersionPersistence.remove(structureVersion);
		}
	}

	@Override
	public DDMStructureVersion getLatestStructureVersion(long structureId)
		throws PortalException {

		List<DDMStructureVersion> structureVersions =
			ddmStructureVersionPersistence.findByStructureId(structureId);

		if (structureVersions.isEmpty()) {
			throw new NoSuchStructureVersionException(
				"No structure versions found for structure ID " + structureId);
		}

		structureVersions = ListUtil.copy(structureVersions);

		Collections.sort(
			structureVersions, new StructureVersionVersionComparator());

		return structureVersions.get(0);
	}

	@Override
	public DDMStructureVersion getStructureVersion(long structureVersionId)
		throws PortalException {

		return ddmStructureVersionPersistence.findByPrimaryKey(
			structureVersionId);
	}

	@Override
	public DDMStructureVersion getStructureVersion(
			long structureId, String version)
		throws PortalException {

		return ddmStructureVersionPersistence.findByS_V(structureId, version);
	}

	@Override
	public DDMForm getStructureVersionDDMForm(
			DDMStructureVersion structureVersion)
		throws PortalException {

		return ddmFormJSONDeserializer.deserialize(
			structureVersion.getDefinition());
	}

	@Override
	public List<DDMStructureVersion> getStructureVersions(long structureId) {
		return ddmStructureVersionPersistence.findByStructureId(structureId);
	}

	@Override
	public List<DDMStructureVersion> getStructureVersions(
		long structureId, int start, int end,
		OrderByComparator<DDMStructureVersion> orderByComparator) {

		return ddmStructureVersionPersistence.findByStructureId(
			structureId, start, end, orderByComparator);
	}

	@Override
	public int getStructureVersionsCount(long structureId) {
		return ddmStructureVersionPersistence.countByStructureId(structureId);
	}

	@ServiceReference(type = DDMFormJSONDeserializer.class)
	protected DDMFormJSONDeserializer ddmFormJSONDeserializer;

}