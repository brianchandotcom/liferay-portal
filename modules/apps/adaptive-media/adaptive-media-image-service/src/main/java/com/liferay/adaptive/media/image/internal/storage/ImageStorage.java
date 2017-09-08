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

package com.liferay.adaptive.media.image.internal.storage;

import com.liferay.adaptive.media.exception.AMRuntimeException;
import com.liferay.document.library.kernel.store.DLStoreUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.repository.model.FileVersion;

import java.io.InputStream;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = ImageStorage.class)
public class ImageStorage {

	public void delete(FileVersion fileVersion, String configurationUuid) {
		DLStoreUtil.deleteDirectory(
			fileVersion.getCompanyId(), CompanyConstants.SYSTEM,
			getFileVersionPath(fileVersion, configurationUuid));
	}

	public void delete(long companyId, String configurationUuid) {
		DLStoreUtil.deleteDirectory(
			companyId, CompanyConstants.SYSTEM,
			getConfigurationEntryPath(configurationUuid));
	}

	public InputStream getContentStream(
		FileVersion fileVersion, String configurationUuid) {

		try {
			String fileVersionPath = getFileVersionPath(
				fileVersion, configurationUuid);

			return getFileAsStream(fileVersion.getCompanyId(), fileVersionPath);
		}
		catch (PortalException pe) {
			throw new AMRuntimeException.IOException(pe);
		}
	}

	public void save(
		FileVersion fileVersion, String configurationUuid,
		InputStream inputStream) {

		try {
			String fileVersionPath = getFileVersionPath(
				fileVersion, configurationUuid);

			DLStoreUtil.addFile(
				fileVersion.getCompanyId(), CompanyConstants.SYSTEM,
				fileVersionPath, false, inputStream);
		}
		catch (PortalException pe) {
			throw new AMRuntimeException.IOException(pe);
		}
	}

	protected String getConfigurationEntryPath(String configurationUuid) {
		return String.format("adaptive/%s", configurationUuid);
	}

	protected InputStream getFileAsStream(long companyId, String path)
		throws PortalException {

		return DLStoreUtil.getFileAsStream(
			companyId, CompanyConstants.SYSTEM, path);
	}

	protected String getFileVersionPath(
		FileVersion fileVersion, String configurationUuid) {

		return String.format(
			"adaptive/%s/%d/%d/%d/%d/", configurationUuid,
			fileVersion.getGroupId(), fileVersion.getRepositoryId(),
			fileVersion.getFileEntryId(), fileVersion.getFileVersionId());
	}

}