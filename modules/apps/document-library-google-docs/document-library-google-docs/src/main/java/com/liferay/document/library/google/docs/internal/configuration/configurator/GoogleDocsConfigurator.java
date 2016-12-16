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

package com.liferay.document.library.google.docs.internal.configuration.configurator;

import com.liferay.document.library.ddm.DLFileEntryMetadataDDMPermissionSupport;
import com.liferay.document.library.google.docs.internal.util.GoogleDocsDLFileEntryTypeHelper;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.dynamic.data.mapping.io.DDMFormXSDDeserializer;
import com.liferay.dynamic.data.mapping.kernel.DDMStructureLinkManager;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureVersionLocalService;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.UserLocalService;

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
								company, _classNameLocalService, _ddm,
								_ddmFormXSDDeserializer,
								_ddmStructureLocalService,
								_dlFileEntryTypeLocalService,
								_userLocalService);

					googleDocsDLFileEntryTypeHelper.
						addGoogleDocsDLFileEntryType();
				}

			});

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
	protected void setDDM(DDM ddm) {
		_ddm = ddm;
	}

	@Reference(unbind = "-")
	protected void setDDMFormXSDDeserializer(
		DDMFormXSDDeserializer ddmFormXSDDeserializer) {

		_ddmFormXSDDeserializer = ddmFormXSDDeserializer;
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
	protected void setDDMStructureVersionLocalService(
		DDMStructureVersionLocalService ddmStructureVersionLocalService) {

		_ddmStructureVersionLocalService = ddmStructureVersionLocalService;
	}

	@Reference(unbind = "-")
	protected void setDLFileEntryMetadataDDMPermissionSupport(
		DLFileEntryMetadataDDMPermissionSupport
			dlFileEntryMetadataDDMPermissionSupport) {
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
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private ClassNameLocalService _classNameLocalService;
	private CompanyLocalService _companyLocalService;
	private DDM _ddm;
	private DDMFormXSDDeserializer _ddmFormXSDDeserializer;
	private DDMStructureLocalService _ddmStructureLocalService;
	private DDMStructureVersionLocalService _ddmStructureVersionLocalService;
	private DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;
	private UserLocalService _userLocalService;

}