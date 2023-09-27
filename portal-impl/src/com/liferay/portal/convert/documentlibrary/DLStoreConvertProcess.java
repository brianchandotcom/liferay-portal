/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.convert.documentlibrary;

import com.liferay.document.library.kernel.store.Store;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Iván Zaera
 * @author Roberto Díaz
 */
public interface DLStoreConvertProcess {

	public void copy(Store sourceStore, Store targetStore)
		throws PortalException;

	public void move(Store sourceStore, Store targetStore)
		throws PortalException;

	public default void transferFile(
		Store sourceStore, Store targetStore, long companyId, long repositoryId,
		String fileName, String versionLabel, boolean delete) {

		try (InputStream inputStream = sourceStore.getFileAsStream(
				companyId, repositoryId, fileName, versionLabel)) {

			targetStore.addFile(
				companyId, repositoryId, fileName, versionLabel, inputStream);

			if (delete) {
				sourceStore.deleteFile(
					companyId, repositoryId, fileName, versionLabel);
			}
		}
		catch (IOException | PortalException exception) {
			throw new SystemException(exception);
		}
	}

}