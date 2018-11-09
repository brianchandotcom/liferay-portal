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

package com.liferay.portal.repository.liferayrepository;

import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppHelperLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryService;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFileShortcutLocalService;
import com.liferay.document.library.kernel.service.DLFileShortcutService;
import com.liferay.document.library.kernel.service.DLFileVersionLocalService;
import com.liferay.document.library.kernel.service.DLFileVersionService;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.document.library.kernel.service.DLFolderService;
import com.liferay.dynamic.data.mapping.kernel.DDMFormValues;
import com.liferay.dynamic.data.mapping.kernel.DDMStructure;
import com.liferay.dynamic.data.mapping.kernel.StorageEngineManagerUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.capabilities.Capability;
import com.liferay.portal.kernel.repository.capabilities.CapabilityProvider;
import com.liferay.portal.kernel.service.RepositoryLocalService;
import com.liferay.portal.kernel.service.RepositoryService;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.SortedArrayList;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Alexander Chow
 */
public abstract class LiferayRepositoryBase implements CapabilityProvider {

	public LiferayRepositoryBase(
		RepositoryLocalService repositoryLocalService,
		RepositoryService repositoryService,
		DLAppHelperLocalService dlAppHelperLocalService,
		DLFileEntryLocalService dlFileEntryLocalService,
		DLFileEntryService dlFileEntryService,
		DLFileEntryTypeLocalService dlFileEntryTypeLocalService,
		DLFileShortcutLocalService dlFileShortcutLocalService,
		DLFileShortcutService dlFileShortcutService,
		DLFileVersionLocalService dlFileVersionLocalService,
		DLFileVersionService dlFileVersionService,
		DLFolderLocalService dlFolderLocalService,
		DLFolderService dlFolderService,
		ResourceLocalService resourceLocalService, long groupId,
		long repositoryId, long dlFolderId) {

		this.repositoryLocalService = repositoryLocalService;
		this.repositoryService = repositoryService;
		this.dlAppHelperLocalService = dlAppHelperLocalService;
		this.dlFileEntryLocalService = dlFileEntryLocalService;
		this.dlFileEntryService = dlFileEntryService;
		this.dlFileEntryTypeLocalService = dlFileEntryTypeLocalService;
		this.dlFileShortcutLocalService = dlFileShortcutLocalService;
		this.dlFileShortcutService = dlFileShortcutService;
		this.dlFileVersionLocalService = dlFileVersionLocalService;
		this.dlFileVersionService = dlFileVersionService;
		this.dlFolderLocalService = dlFolderLocalService;
		this.dlFolderService = dlFolderService;
		this.resourceLocalService = resourceLocalService;
		_groupId = groupId;
		_repositoryId = repositoryId;
		_dlFolderId = dlFolderId;
	}

	@Override
	public <T extends Capability> T getCapability(Class<T> capabilityClass) {
		throw new IllegalArgumentException(
			String.format(
				"Capability %s is not supported by repository %s",
				capabilityClass.getName(), getRepositoryId()));
	}

	public long getRepositoryId() {
		return _repositoryId;
	}

	@Override
	public <T extends Capability> boolean isCapabilityProvided(
		Class<T> capabilityClass) {

		return false;
	}

	protected HashMap<String, DDMFormValues> getDDMFormValuesMap(
			ServiceContext serviceContext, long fileEntryTypeId)
		throws PortalException {

		HashMap<String, DDMFormValues> ddmFormValuesMap = new HashMap<>();

		if (fileEntryTypeId <= 0) {
			return ddmFormValuesMap;
		}

		DLFileEntryType fileEntryType =
			DLFileEntryTypeLocalServiceUtil.getFileEntryType(fileEntryTypeId);

		List<DDMStructure> ddmStructures = fileEntryType.getDDMStructures();

		for (DDMStructure ddmStructure : ddmStructures) {
			DDMFormValues ddmFormValues =
				(DDMFormValues)serviceContext.getAttribute(
					DDMFormValues.class.getName() + StringPool.POUND +
						ddmStructure.getStructureId());

			if (ddmFormValues == null) {
				ddmFormValues = StorageEngineManagerUtil.getDDMFormValues(
					ddmStructure.getStructureId(),
					String.valueOf(ddmStructure.getStructureId()),
					serviceContext);
			}

			ddmFormValuesMap.put(ddmStructure.getStructureKey(), ddmFormValues);
		}

		return ddmFormValuesMap;
	}

	protected long getDefaultFileEntryTypeId(
			ServiceContext serviceContext, long folderId)
		throws PortalException {

		folderId = dlFolderLocalService.getFolderId(
			serviceContext.getCompanyId(), folderId);

		return dlFileEntryTypeLocalService.getDefaultFileEntryTypeId(folderId);
	}

	protected long getGroupId() {
		return _groupId;
	}

	protected SortedArrayList<Long> getLongList(
		ServiceContext serviceContext, String name) {

		String value = ParamUtil.getString(serviceContext, name);

		if (value == null) {
			return new SortedArrayList<>();
		}

		long[] longArray = StringUtil.split(value, 0L);

		SortedArrayList<Long> longList = new SortedArrayList<>();

		for (long longValue : longArray) {
			longList.add(longValue);
		}

		return longList;
	}

	protected boolean isDefaultRepository() {
		if (_groupId == _repositoryId) {
			return true;
		}

		return false;
	}

	protected long toFolderId(long folderId) {
		if (folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return _dlFolderId;
		}

		return folderId;
	}

	protected List<Long> toFolderIds(List<Long> folderIds) {
		List<Long> toFolderIds = new ArrayList<>(folderIds.size());

		for (long folderId : folderIds) {
			toFolderIds.add(toFolderId(folderId));
		}

		return toFolderIds;
	}

	protected DLAppHelperLocalService dlAppHelperLocalService;
	protected DLFileEntryLocalService dlFileEntryLocalService;
	protected DLFileEntryService dlFileEntryService;
	protected DLFileEntryTypeLocalService dlFileEntryTypeLocalService;
	protected DLFileShortcutLocalService dlFileShortcutLocalService;
	protected DLFileShortcutService dlFileShortcutService;
	protected DLFileVersionLocalService dlFileVersionLocalService;
	protected DLFileVersionService dlFileVersionService;
	protected DLFolderLocalService dlFolderLocalService;
	protected DLFolderService dlFolderService;
	protected RepositoryLocalService repositoryLocalService;
	protected RepositoryService repositoryService;
	protected ResourceLocalService resourceLocalService;

	private final long _dlFolderId;
	private final long _groupId;
	private final long _repositoryId;

}