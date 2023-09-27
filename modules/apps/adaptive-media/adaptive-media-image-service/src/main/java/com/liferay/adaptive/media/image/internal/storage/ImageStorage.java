/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.image.internal.storage;

import com.liferay.adaptive.media.exception.AMRuntimeException;
import com.liferay.document.library.kernel.store.Store;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.repository.model.FileVersion;

import java.io.InputStream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = ImageStorage.class)
public class ImageStorage {

	public void delete(FileVersion fileVersion, String configurationUuid) {
		_store.deleteDirectory(
			fileVersion.getCompanyId(), CompanyConstants.SYSTEM,
			AMStoreUtil.getFileVersionPath(fileVersion, configurationUuid));
	}

	public void delete(long companyId, String configurationUuid) {
		_store.deleteDirectory(
			companyId, CompanyConstants.SYSTEM,
			getConfigurationEntryPath(configurationUuid));
	}

	public InputStream getContentInputStream(
		FileVersion fileVersion, String configurationUuid) {

		try {
			String fileVersionPath = AMStoreUtil.getFileVersionPath(
				fileVersion, configurationUuid);

			return _store.getFileAsStream(
				fileVersion.getCompanyId(), CompanyConstants.SYSTEM,
				fileVersionPath, Store.VERSION_DEFAULT);
		}
		catch (PortalException portalException) {
			throw new AMRuntimeException.IOException(portalException);
		}
	}

	public boolean hasContent(
		FileVersion fileVersion, String configurationUuid) {

		String fileVersionPath = AMStoreUtil.getFileVersionPath(
			fileVersion, configurationUuid);

		return _store.hasFile(
			fileVersion.getCompanyId(), CompanyConstants.SYSTEM,
			fileVersionPath, Store.VERSION_DEFAULT);
	}

	public void save(
		FileVersion fileVersion, String configurationUuid,
		InputStream inputStream) {

		try {
			String fileVersionPath = AMStoreUtil.getFileVersionPath(
				fileVersion, configurationUuid);

			_store.addFile(
				fileVersion.getCompanyId(), CompanyConstants.SYSTEM,
				fileVersionPath, Store.VERSION_DEFAULT, inputStream);
		}
		catch (PortalException portalException) {
			throw new AMRuntimeException.IOException(portalException);
		}
	}

	protected String getConfigurationEntryPath(String configurationUuid) {
		return String.format("adaptive/%s", configurationUuid);
	}

	protected void setStore(Store store) {
		_store = store;
	}

	@Reference(target = "(default=true)")
	private Store _store;

}