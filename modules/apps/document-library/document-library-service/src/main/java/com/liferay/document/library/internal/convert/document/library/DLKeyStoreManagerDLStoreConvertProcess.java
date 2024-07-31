/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.convert.document.library;

import com.liferay.document.library.kernel.store.Store;
import com.liferay.portal.convert.documentlibrary.DLStoreConvertProcess;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.util.MaintenanceUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge García Jiménez
 */
@Component(service = DLStoreConvertProcess.class)
public class DLKeyStoreManagerDLStoreConvertProcess
	implements DLStoreConvertProcess {

	@Override
	public void copy(Store sourceStore, Store targetStore)
		throws PortalException {

		_transfer(sourceStore, targetStore, _SAML_KEYSTORE_PATH, false);
	}

	@Override
	public void move(Store sourceStore, Store targetStore)
		throws PortalException {

		_transfer(sourceStore, targetStore, _SAML_KEYSTORE_PATH, true);
	}

	private void _transfer(
			Store sourceStore, Store targetStore, String path, boolean delete)
		throws PortalException {

		MaintenanceUtil.appendStatus("Migrating files from " + path);

		_companyLocalService.forEachCompanyId(
			companyId -> {
				if (sourceStore.hasFile(
						companyId, CompanyConstants.SYSTEM, _SAML_KEYSTORE_PATH,
						Store.VERSION_DEFAULT)) {

					try {
						transferFile(
							sourceStore, targetStore, companyId,
							CompanyConstants.SYSTEM, _SAML_KEYSTORE_PATH,
							Store.VERSION_DEFAULT, delete);
					}
					catch (Exception exception) {
						_log.error(
							"Unable to migrate " + _SAML_KEYSTORE_PATH,
							exception);
					}
				}
			});
	}

	private static final String _SAML_KEYSTORE_PATH = "saml/keystore.jks";

	private static final Log _log = LogFactoryUtil.getLog(
		DLKeyStoreManagerDLStoreConvertProcess.class);

	@Reference
	private CompanyLocalService _companyLocalService;

}