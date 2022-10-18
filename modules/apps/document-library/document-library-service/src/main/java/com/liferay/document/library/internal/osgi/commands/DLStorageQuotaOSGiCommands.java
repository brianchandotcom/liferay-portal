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