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
import com.liferay.document.library.kernel.store.DLStoreRequest;
import com.liferay.document.library.kernel.store.DLStoreUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.repository.model.FileVersion;

import java.io.InputStream;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(service = ImageStorage.class)
public class ImageStorage {

	public void delete(FileVersion fileVersion, String configurationUuid) {
		try {
			DLStoreUtil.deleteDirectory(
				fileVersion.getCompanyId(), CompanyConstants.SYSTEM,
				AMStoreUtil.getFileVersionPath(fileVersion, configurationUuid));
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException);
			}
		}
	}

	public void delete(long companyId, String configurationUuid) {
		try {
			DLStoreUtil.deleteDirectory(
				companyId, CompanyConstants.SYSTEM,
				getConfigurationEntryPath(configurationUuid));
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException);
			}
		}
	}

	public InputStream getContentInputStream(
		FileVersion fileVersion, String configurationUuid) {

		try {
			String fileVersionPath = AMStoreUtil.getFileVersionPath(
				fileVersion, configurationUuid);

			return DLStoreUtil.getFileAsStream(
				fileVersion.getCompanyId(), CompanyConstants.SYSTEM,
				fileVersionPath);
		}
		catch (PortalException portalException) {
			throw new AMRuntimeException.IOException(portalException);
		}
	}

	public boolean hasContent(
		FileVersion fileVersion, String configurationUuid) {

		try {
			String fileVersionPath = AMStoreUtil.getFileVersionPath(
				fileVersion, configurationUuid);

			return DLStoreUtil.hasFile(
				fileVersion.getCompanyId(), CompanyConstants.SYSTEM,
				fileVersionPath);
		}
		catch (PortalException portalException) {
			throw new AMRuntimeException.IOException(portalException);
		}
	}

	public void save(
		FileVersion fileVersion, String configurationUuid,
		InputStream inputStream) {

		try {
			String fileVersionPath = AMStoreUtil.getFileVersionPath(
				fileVersion, configurationUuid);

			DLStoreUtil.addFile(
				DLStoreRequest.builder(
					fileVersion.getCompanyId(), CompanyConstants.SYSTEM,
					fileVersionPath
				).className(
					this
				).size(
					fileVersion.getSize()
				).sourceFileName(
					fileVersion.getFileName()
				).build(),
				inputStream);
		}
		catch (PortalException portalException) {
			throw new AMRuntimeException.IOException(portalException);
		}
	}

	protected String getConfigurationEntryPath(String configurationUuid) {
		return String.format("adaptive/%s", configurationUuid);
	}

	private static final Log _log = LogFactoryUtil.getLog(ImageStorage.class);

}