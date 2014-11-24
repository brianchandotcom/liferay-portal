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

package com.liferay.document.library.google.docs.configuration.configurator;

import com.liferay.document.library.google.docs.migration.LegacyGoogleDocsMigration;
import com.liferay.document.library.google.docs.util.GoogleDocsDLFileEntryTypeHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.model.Company;
import com.liferay.portal.service.ClassNameLocalService;
import com.liferay.portal.service.CompanyLocalService;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileEntryMetadataLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalService;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalService;
import com.liferay.portlet.dynamicdatamapping.storage.StorageEngine;

import java.util.List;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera
 */
@Component(
	immediate = true, service = GoogleDocsConfigurator.class
)
public class GoogleDocsConfigurator {

	@Activate
	public void activate() throws PortalException {
		List<Company> companies = _companyLocalService.getCompanies();

		for (Company company : companies) {
			GoogleDocsDLFileEntryTypeHelper googleDocsDLFileEntryTypeHelper =
				new GoogleDocsDLFileEntryTypeHelper(
					company, _classNameLocalService, _ddmStructureLocalService,
					_dlFileEntryTypeLocalService, _userLocalService);

			LegacyGoogleDocsMigration legacyGoogleDocsMigration =
				new LegacyGoogleDocsMigration(
					company, _ddmStructureLocalService,
					_dlFileEntryTypeLocalService, _dlFileEntryLocalService,
					_dlFileEntryMetadataLocalService,
					googleDocsDLFileEntryTypeHelper, _storageEngine);

			if (legacyGoogleDocsMigration.isMigrationNeeded()) {
				legacyGoogleDocsMigration.migrate();
			}
			else {
				googleDocsDLFileEntryTypeHelper.addGoogleDocsDLFileEntryType();
			}
		}
	}

	@Reference
	public void setClassNameLocalService(
		ClassNameLocalService classNameLocalService) {

		_classNameLocalService = classNameLocalService;
	}

	@Reference
	public void setCompanyLocalService(
		CompanyLocalService companyLocalService) {

		_companyLocalService = companyLocalService;
	}

	@Reference
	public void setDDMStructureLocalService(
		DDMStructureLocalService ddmStructureLocalService) {

		_ddmStructureLocalService = ddmStructureLocalService;
	}

	@Reference
	public void setDLFileEntryLocalService(
		DLFileEntryLocalService dlFileEntryLocalService) {

		_dlFileEntryLocalService = dlFileEntryLocalService;
	}

	@Reference
	public void setDLFileEntryMetadataLocalService(
		DLFileEntryMetadataLocalService dlFileEntryMetadataLocalService) {

		_dlFileEntryMetadataLocalService = dlFileEntryMetadataLocalService;
	}

	@Reference
	public void setDLFileEntryTypeLocalService(
		DLFileEntryTypeLocalService dlFileEntryTypeLocalService) {

		_dlFileEntryTypeLocalService = dlFileEntryTypeLocalService;
	}

	@Reference
	public void setStorageEngine(StorageEngine storageEngine) {
		_storageEngine = storageEngine;
	}

	@Reference
	public void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private ClassNameLocalService _classNameLocalService;
	private CompanyLocalService _companyLocalService;
	private DDMStructureLocalService _ddmStructureLocalService;
	private DLFileEntryLocalService _dlFileEntryLocalService;
	private DLFileEntryMetadataLocalService _dlFileEntryMetadataLocalService;
	private DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;
	private StorageEngine _storageEngine;
	private UserLocalService _userLocalService;

}