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

package com.liferay.document.library.internal.service;

import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalServiceWrapper;
import com.liferay.dynamic.data.mapping.constants.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceWrapper;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = ServiceWrapper.class)
public class DataEngineDLFileEntryTypeLocalServiceWrapper
	extends DLFileEntryTypeLocalServiceWrapper {

	public DataEngineDLFileEntryTypeLocalServiceWrapper() {
		this(null);
	}

	public DataEngineDLFileEntryTypeLocalServiceWrapper(
		DLFileEntryTypeLocalService dlFileEntryTypeLocalService) {

		super(dlFileEntryTypeLocalService);
	}

	@Override
	public DLFileEntryType addDLFileEntryType(DLFileEntryType dlFileEntryType) {
		DLFileEntryType fileEntryType = super.addDLFileEntryType(
			dlFileEntryType);

		_updateDDMStructure(fileEntryType.getDataDefinitionId());

		return fileEntryType;
	}

	@Override
	public DLFileEntryType addFileEntryType(
			long userId, long groupId, long dataDefinitionId,
			String fileEntryTypeKey, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, int scope,
			ServiceContext serviceContext)
		throws PortalException {

		DLFileEntryType fileEntryType = super.addFileEntryType(
			userId, groupId, dataDefinitionId, fileEntryTypeKey, nameMap,
			descriptionMap, scope, serviceContext);

		_updateDDMStructure(dataDefinitionId);

		return fileEntryType;
	}

	@Override
	public DLFileEntryType addFileEntryType(
			long userId, long groupId, long dataDefinitionId,
			String fileEntryTypeKey, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, ServiceContext serviceContext)
		throws PortalException {

		DLFileEntryType fileEntryType = super.addFileEntryType(
			userId, groupId, dataDefinitionId, fileEntryTypeKey, nameMap,
			descriptionMap, serviceContext);

		_updateDDMStructure(dataDefinitionId);

		return fileEntryType;
	}

	@Override
	public void deleteFileEntryType(DLFileEntryType dlFileEntryType)
		throws PortalException {

		super.deleteFileEntryType(dlFileEntryType);

		updateDDMStructureLinks(
			dlFileEntryType.getFileEntryTypeId(), Collections.emptySet());
	}

	private void _updateDDMStructure(long structureId) {
		DDMStructure ddmStructure = _ddmStructureLocalService.fetchDDMStructure(
			structureId);

		if (ddmStructure == null) {
			return;
		}

		ddmStructure.setType(DDMStructureConstants.TYPE_AUTO);

		_ddmStructureLocalService.updateDDMStructure(ddmStructure);
	}

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

}