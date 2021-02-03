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
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	property = {
		"osgi.command.function=update", "osgi.command.scope=dl-storage-quota"
	},
	service = DLStorageQuotaOSGiCommands.class
)
public class DLStorageQuotaOSGiCommands {

	public void update(String... companyIds) {
		for (long companyId : _getCompanyIds(companyIds)) {
			try {
				_dlStorageQuotaLocalService.updateStorageSize(companyId);

				System.out.printf(
					"Successfully updated storage quota for company %d%n",
					companyId);
			}
			catch (Exception exception) {
				_log.error(exception, exception);

				System.out.printf(
					"Error while updating storage quota for company %d. See " +
						"server log for more details.%n",
					companyId);
			}
		}
	}

	private Iterable<Long> _getCompanyIds(String... companyIds) {
		if (companyIds.length == 0) {
			List<Company> companies = _companyLocalService.getCompanies();

			Stream<Company> companyStream = companies.stream();

			return companyStream.map(
				Company::getCompanyId
			).collect(
				Collectors.toList()
			);
		}

		Stream<String> companyIdStream = Arrays.stream(companyIds);

		return companyIdStream.map(
			Long::parseLong
		).collect(
			Collectors.toList()
		);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DLStorageQuotaOSGiCommands.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private DLStorageQuotaLocalService _dlStorageQuotaLocalService;

}