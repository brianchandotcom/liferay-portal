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

package com.liferay.document.library.google.docs.context;

import com.liferay.document.library.google.docs.util.GoogleDocsConstants;
import com.liferay.document.library.google.docs.util.GoogleDocsDLFileEntryTypeFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.model.Company;
import com.liferay.portal.service.ClassNameLocalService;
import com.liferay.portal.service.CompanyLocalService;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portlet.documentlibrary.context.DLFileVersionActionsDisplayContext;
import com.liferay.portlet.documentlibrary.context.DLFileVersionActionsDisplayContextFactory;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalService;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalService;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera
 */
@Component(
	immediate = true, service = DLFileVersionActionsDisplayContextFactory.class
)
public class GoogleDocsDLFileVersionActionsDisplayContextFactory
	implements DLFileVersionActionsDisplayContextFactory {

	@Activate
	public void activate() throws PortalException {
		List<Company> companies = _companyLocalService.getCompanies();

		for (Company company : companies) {
			GoogleDocsDLFileEntryTypeFactory googleDocumentTypeCreator =
				new GoogleDocsDLFileEntryTypeFactory(
					company, _classNameLocalService, _ddmStructureLocalService,
					_dlFileEntryTypeLocalService, _userLocalService);

			googleDocumentTypeCreator.addGoogleDocsDLFileEntryType();
		}
	}

	@Override
	public DLFileVersionActionsDisplayContext
		getDLFileVersionActionsDisplayContext(
			DLFileVersionActionsDisplayContext
				parentDLFileEntryActionsDisplayContext,
			HttpServletRequest request, HttpServletResponse response,
			FileVersion fileVersion) {

		try {
			if (isGoogleDocs(fileVersion)) {
				return new GoogleDocsDLFileVersionActionsDisplayContext(
					parentDLFileEntryActionsDisplayContext, request, response,
					fileVersion);
			}
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
		}

		return parentDLFileEntryActionsDisplayContext;
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
	public void setDLFileEntryTypeLocalService(
		DLFileEntryTypeLocalService dlFileEntryTypeLocalService) {

		_dlFileEntryTypeLocalService = dlFileEntryTypeLocalService;
	}

	@Reference
	public void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	protected boolean isGoogleDocs(FileVersion fileVersion)
		throws PortalException {

		Object fileVersionModel = fileVersion.getModel();

		if (!(fileVersionModel instanceof DLFileVersion)) {
			return false;
		}

		DLFileVersion dlFileVersion = (DLFileVersion)fileVersionModel;

		DLFileEntryType dlFileEntryType =
			_dlFileEntryTypeLocalService.getFileEntryType(
				dlFileVersion.getFileEntryTypeId());

		List<DDMStructure> ddmStructures = dlFileEntryType.getDDMStructures();

		for (DDMStructure ddmStructure : ddmStructures) {
			String structureKey = ddmStructure.getStructureKey();

			if (structureKey.equals(
					GoogleDocsConstants.DDM_STRUCTURE_KEY_GOOGLE_DOCS)) {

				return true;
			}
		}

		return false;
	}

	private ClassNameLocalService _classNameLocalService;
	private CompanyLocalService _companyLocalService;
	private DDMStructureLocalService _ddmStructureLocalService;
	private DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;
	private UserLocalService _userLocalService;

}