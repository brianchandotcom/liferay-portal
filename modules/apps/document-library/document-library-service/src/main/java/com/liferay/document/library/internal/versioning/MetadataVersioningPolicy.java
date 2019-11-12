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

package com.liferay.document.library.internal.versioning;

import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.kernel.service.DLFileEntryMetadataLocalService;
import com.liferay.document.library.versioning.VersioningPolicy;
import com.liferay.dynamic.data.mapping.kernel.DDMFormValues;
import com.liferay.dynamic.data.mapping.kernel.DDMStructure;
import com.liferay.dynamic.data.mapping.kernel.StorageEngineManagerUtil;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.Serializable;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = VersioningPolicy.class)
public class MetadataVersioningPolicy implements VersioningPolicy {

	@Override
	public Optional<DLVersionNumberIncrease> computeDLVersionNumberIncrease(
		DLFileVersion previousDLFileVersion, DLFileVersion nextDLFileVersion) {

		if (!Objects.equals(
				previousDLFileVersion.getTitle(),
				nextDLFileVersion.getTitle())) {

			return Optional.of(DLVersionNumberIncrease.MINOR);
		}

		if (!Objects.equals(
				previousDLFileVersion.getDescription(),
				nextDLFileVersion.getDescription())) {

			return Optional.of(DLVersionNumberIncrease.MINOR);
		}

		if (previousDLFileVersion.getFileEntryTypeId() !=
				nextDLFileVersion.getFileEntryTypeId()) {

			return Optional.of(DLVersionNumberIncrease.MINOR);
		}

		if (_isDLFileEntryTypeUpdated(
				previousDLFileVersion, nextDLFileVersion)) {

			return Optional.of(DLVersionNumberIncrease.MINOR);
		}

		if (_isExpandoUpdated(previousDLFileVersion, nextDLFileVersion)) {
			return Optional.of(DLVersionNumberIncrease.MINOR);
		}

		return Optional.empty();
	}

	private boolean _isDLFileEntryTypeUpdated(
		DLFileVersion previousDLFileVersion, DLFileVersion nextDLFileVersion) {

		try {
			DLFileEntryType dlFileEntryType =
				previousDLFileVersion.getDLFileEntryType();

			for (DDMStructure ddmStructure :
					dlFileEntryType.getDDMStructures()) {

				DLFileEntryMetadata previousFileEntryMetadata =
					_dlFileEntryMetadataLocalService.fetchFileEntryMetadata(
						ddmStructure.getStructureId(),
						previousDLFileVersion.getFileVersionId());

				if (previousFileEntryMetadata == null) {
					return true;
				}

				DLFileEntryMetadata nextFileEntryMetadata =
					_dlFileEntryMetadataLocalService.getFileEntryMetadata(
						ddmStructure.getStructureId(),
						nextDLFileVersion.getFileVersionId());

				DDMFormValues previousDDMFormValues =
					StorageEngineManagerUtil.getDDMFormValues(
						previousFileEntryMetadata.getDDMStorageId());
				DDMFormValues nextDDMFormValues =
					StorageEngineManagerUtil.getDDMFormValues(
						nextFileEntryMetadata.getDDMStorageId());

				if (!previousDDMFormValues.equals(nextDDMFormValues)) {
					return true;
				}
			}

			return false;
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(pe, pe);
			}

			return false;
		}
	}

	private boolean _isExpandoUpdated(
		DLFileVersion previousDLFileVersion, DLFileVersion nextDLFileVersion) {

		ExpandoBridge previousExpandoBridge =
			previousDLFileVersion.getExpandoBridge();
		ExpandoBridge nextExpandoBridge = nextDLFileVersion.getExpandoBridge();

		Map<String, Serializable> previousAttributes =
			previousExpandoBridge.getAttributes();
		Map<String, Serializable> nextAttributes =
			nextExpandoBridge.getAttributes();

		if (!previousAttributes.equals(nextAttributes)) {
			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MetadataVersioningPolicy.class);

	@Reference
	private DLFileEntryMetadataLocalService _dlFileEntryMetadataLocalService;

}