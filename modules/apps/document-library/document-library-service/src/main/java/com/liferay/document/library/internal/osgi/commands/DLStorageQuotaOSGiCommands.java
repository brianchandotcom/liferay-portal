/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.internal.osgi.commands;

import com.liferay.document.library.service.DLStorageQuotaLocalService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = {
		"osgi.command.function=update", "osgi.command.scope=documentLibrary"
	},
	service = DLStorageQuotaOSGiCommands.class
)
public class DLStorageQuotaOSGiCommands {

	public void update(String... companyIds) {
		for (String companyId : companyIds) {
			try {
				_dlStorageQuotaLocalService.updateStorageSize(
					GetterUtil.getLong(companyId));

				System.out.printf(
					"Successfully updated document library storage quota for " +
						"company %s%n",
					companyId);
			}
			catch (Exception exception) {
				_log.error(exception);

				System.out.printf(
					"Unable to update document library storage quota for " +
						"company %s. See server log for more details.%n",
					companyId);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DLStorageQuotaOSGiCommands.class);

	@Reference
	private DLStorageQuotaLocalService _dlStorageQuotaLocalService;

}