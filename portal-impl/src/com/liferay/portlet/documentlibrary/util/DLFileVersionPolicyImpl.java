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

package com.liferay.portlet.documentlibrary.util;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.service.DLFileEntryMetadataLocalService;
import com.liferay.document.library.kernel.service.persistence.DLFileVersionPersistence;
import com.liferay.document.library.kernel.store.DLStoreUtil;
import com.liferay.document.library.kernel.util.DLFileVersionPolicy;
import com.liferay.dynamic.data.mapping.kernel.DDMFormValues;
import com.liferay.dynamic.data.mapping.kernel.DDMStructure;
import com.liferay.dynamic.data.mapping.kernel.StorageEngineManagerUtil;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.DigesterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.util.PropsValues;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerList;

import java.io.InputStream;
import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author     Adolfo Pérez
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@Deprecated
public class DLFileVersionPolicyImpl implements DLFileVersionPolicy {

	public void destroy() {
		_serviceTrackerList.close();
	}

	@Override
	public boolean isKeepFileVersionLabel(
			DLFileVersion lastDLFileVersion, DLFileVersion latestDLFileVersion,
			boolean majorVersion, ServiceContext serviceContext)
		throws PortalException {

		for (DLFileVersionPolicy dlFileVersionPolicy : _serviceTrackerList) {
			if ((dlFileVersionPolicy != this) &&
				!dlFileVersionPolicy.isKeepFileVersionLabel(
					lastDLFileVersion, latestDLFileVersion, majorVersion,
					serviceContext)) {

				return false;
			}
		}

		return isKeepFileVersionLabel(
			lastDLFileVersion.getFileEntry(), lastDLFileVersion,
			latestDLFileVersion, majorVersion, serviceContext);
	}

	/**
	 * @see com.liferay.dynamic.data.lists.service.impl.DDLRecordLocalServiceImpl#isKeepRecordVersionLabel(
	 *      com.liferay.dynamic.data.lists.model.DDLRecordVersion,
	 *      com.liferay.dynamic.data.lists.model.DDLRecordVersion,
	 *      ServiceContext)
	 */
	protected boolean isKeepFileVersionLabel(
			DLFileEntry dlFileEntry, DLFileVersion lastDLFileVersion,
			DLFileVersion latestDLFileVersion, boolean majorVersion,
			ServiceContext serviceContext)
		throws PortalException {

		if (PropsValues.DL_FILE_ENTRY_VERSION_POLICY != 1) {
			return false;
		}

		if (majorVersion) {
			return false;
		}

		if (Objects.equals(serviceContext.getCommand(), Constants.REVERT)) {
			return false;
		}

		if (!Objects.equals(
				lastDLFileVersion.getTitle(), latestDLFileVersion.getTitle())) {

			return false;
		}

		if (!Objects.equals(
				lastDLFileVersion.getDescription(),
				latestDLFileVersion.getDescription())) {

			return false;
		}

		if (lastDLFileVersion.getFileEntryTypeId() !=
				latestDLFileVersion.getFileEntryTypeId()) {

			return false;
		}

		if (serviceContext.getWorkflowAction() ==
				WorkflowConstants.ACTION_SAVE_DRAFT) {

			return false;
		}

		// File entry type

		DLFileEntryType dlFileEntryType =
			lastDLFileVersion.getDLFileEntryType();

		List<DDMStructure> ddmStructures = dlFileEntryType.getDDMStructures();

		for (DDMStructure ddmStructure : ddmStructures) {
			DLFileEntryMetadata lastFileEntryMetadata =
				dlFileEntryMetadataLocalService.fetchFileEntryMetadata(
					ddmStructure.getStructureId(),
					lastDLFileVersion.getFileVersionId());

			if (lastFileEntryMetadata == null) {
				return false;
			}

			DLFileEntryMetadata latestFileEntryMetadata =
				dlFileEntryMetadataLocalService.getFileEntryMetadata(
					ddmStructure.getStructureId(),
					latestDLFileVersion.getFileVersionId());

			DDMFormValues lastDDMFormValues =
				StorageEngineManagerUtil.getDDMFormValues(
					lastFileEntryMetadata.getDDMStorageId());
			DDMFormValues latestDDMFormValues =
				StorageEngineManagerUtil.getDDMFormValues(
					latestFileEntryMetadata.getDDMStorageId());

			if (!lastDDMFormValues.equals(latestDDMFormValues)) {
				return false;
			}
		}

		// Expando

		ExpandoBridge lastExpandoBridge = lastDLFileVersion.getExpandoBridge();
		ExpandoBridge latestExpandoBridge =
			latestDLFileVersion.getExpandoBridge();

		Map<String, Serializable> lastAttributes =
			lastExpandoBridge.getAttributes();
		Map<String, Serializable> latestAttributes =
			latestExpandoBridge.getAttributes();

		if (!lastAttributes.equals(latestAttributes)) {
			return false;
		}

		// Size

		long lastSize = lastDLFileVersion.getSize();
		long latestSize = latestDLFileVersion.getSize();

		if ((lastSize == 0) && (latestSize >= 0)) {
			return true;
		}

		if (lastSize != latestSize) {
			return false;
		}

		// Checksum

		try {
			String lastChecksum = lastDLFileVersion.getChecksum();

			if (Validator.isNull(lastChecksum)) {
				try (InputStream lastInputStream = DLStoreUtil.getFileAsStream(
						dlFileEntry.getCompanyId(),
						dlFileEntry.getDataRepositoryId(),
						dlFileEntry.getName(),
						lastDLFileVersion.getVersion())) {

					lastChecksum = DigesterUtil.digestBase64(lastInputStream);
				}

				lastDLFileVersion.setChecksum(lastChecksum);

				dlFileVersionPersistence.update(lastDLFileVersion);
			}

			try (InputStream latestInputStream = DLStoreUtil.getFileAsStream(
					dlFileEntry.getCompanyId(),
					dlFileEntry.getDataRepositoryId(), dlFileEntry.getName(),
					latestDLFileVersion.getVersion())) {

				String latestChecksum = DigesterUtil.digestBase64(
					latestInputStream);

				if (lastChecksum.equals(latestChecksum)) {
					return true;
				}

				latestDLFileVersion.setChecksum(latestChecksum);
			}

			dlFileVersionPersistence.update(latestDLFileVersion);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		return false;
	}

	@BeanReference(type = DLFileEntryMetadataLocalService.class)
	protected DLFileEntryMetadataLocalService dlFileEntryMetadataLocalService;

	@BeanReference(type = DLFileVersionPersistence.class)
	protected DLFileVersionPersistence dlFileVersionPersistence;

	private static final Log _log = LogFactoryUtil.getLog(
		DLFileVersionPolicyImpl.class);

	private final ServiceTrackerList<DLFileVersionPolicy> _serviceTrackerList =
		ServiceTrackerCollections.openList(DLFileVersionPolicy.class);

}