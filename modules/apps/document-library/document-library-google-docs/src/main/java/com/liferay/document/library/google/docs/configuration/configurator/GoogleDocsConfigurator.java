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
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.storage.StorageEngine;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.model.Company;
import com.liferay.portal.service.ClassNameLocalService;
import com.liferay.portal.service.CompanyLocalService;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileEntryMetadataLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalService;
import com.liferay.portlet.dynamicdatamapping.DDMStructureLinkManager;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera
 */
@Component(immediate = true, service = GoogleDocsConfigurator.class)
public class GoogleDocsConfigurator {

	@Activate
	public void activate() throws PortalException {
		ActionableDynamicQuery actionableDynamicQuery =
			_companyLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<Company>() {

				@Override
				public void performAction(Company company)
					throws PortalException {

					GoogleDocsDLFileEntryTypeHelper
						googleDocsDLFileEntryTypeHelper =
							new GoogleDocsDLFileEntryTypeHelper(
								company, _classNameLocalService,
								_ddmStructureLocalService,
								_dlFileEntryTypeLocalService,
								_userLocalService);

					LegacyGoogleDocsMigration legacyGoogleDocsMigration =
						new LegacyGoogleDocsMigration(
							company, _ddmStructureLocalService,
							_dlFileEntryTypeLocalService,
							_dlFileEntryLocalService,
							_dlFileEntryMetadataLocalService,
							googleDocsDLFileEntryTypeHelper, _storageEngine);

					if (legacyGoogleDocsMigration.isMigrationNeeded()) {
						legacyGoogleDocsMigration.migrate();
					}
					else {
						googleDocsDLFileEntryTypeHelper.
							addGoogleDocsDLFileEntryType();
					}
				}

			}
		);

		actionableDynamicQuery.performActions();
	}

	@Reference(unbind = "-")
	protected void setClassNameLocalService(
		ClassNameLocalService classNameLocalService) {

		_classNameLocalService = classNameLocalService;
	}

	@Reference(unbind = "-")
	protected void setCompanyLocalService(
		CompanyLocalService companyLocalService) {

		_companyLocalService = companyLocalService;
	}

	@Reference(unbind = "-")
	protected void setDDMStructureLinkManager(
		DDMStructureLinkManager ddmStructureLinkManager) {
	}

	@Reference(unbind = "-")
	protected void setDDMStructureLocalService(
		DDMStructureLocalService ddmStructureLocalService) {

		_ddmStructureLocalService = ddmStructureLocalService;
	}

	@Reference(unbind = "-")
	protected void setDLFileEntryLocalService(
		DLFileEntryLocalService dlFileEntryLocalService) {

		_dlFileEntryLocalService = dlFileEntryLocalService;
	}

	@Reference(unbind = "-")
	protected void setDLFileEntryMetadataLocalService(
		DLFileEntryMetadataLocalService dlFileEntryMetadataLocalService) {

		_dlFileEntryMetadataLocalService = dlFileEntryMetadataLocalService;
	}

	@Reference(unbind = "-")
	protected void setDLFileEntryTypeLocalService(
		DLFileEntryTypeLocalService dlFileEntryTypeLocalService) {

		_dlFileEntryTypeLocalService = dlFileEntryTypeLocalService;
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	@Reference(unbind = "-")
	protected void setStorageEngine(StorageEngine storageEngine) {
		_storageEngine = storageEngine;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private volatile ClassNameLocalService _classNameLocalService;
	private volatile CompanyLocalService _companyLocalService;
	private volatile DDMStructureLocalService _ddmStructureLocalService;
	private volatile DLFileEntryLocalService _dlFileEntryLocalService;
	private volatile DLFileEntryMetadataLocalService
		_dlFileEntryMetadataLocalService;
	private volatile DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;
	private volatile StorageEngine _storageEngine;
	private volatile UserLocalService _userLocalService;

}