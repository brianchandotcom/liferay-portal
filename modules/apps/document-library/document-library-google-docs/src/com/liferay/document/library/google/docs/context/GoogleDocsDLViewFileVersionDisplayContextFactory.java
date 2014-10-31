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

import com.liferay.document.library.google.docs.util.GoogleDocsDLFileEntryTypeHelper;
import com.liferay.document.library.google.docs.util.GoogleDocsMetadataHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.model.Company;
import com.liferay.portal.service.ClassNameLocalService;
import com.liferay.portal.service.CompanyLocalService;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portlet.documentlibrary.context.DLViewFileVersionDisplayContext;
import com.liferay.portlet.documentlibrary.context.DLViewFileVersionDisplayContextFactory;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalService;
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
	immediate = true, service = DLViewFileVersionDisplayContextFactory.class
)
public class GoogleDocsDLViewFileVersionDisplayContextFactory
	implements DLViewFileVersionDisplayContextFactory {

	@Activate
	public void activate() throws PortalException {
		List<Company> companies = _companyLocalService.getCompanies();

		for (Company company : companies) {
			GoogleDocsDLFileEntryTypeHelper googleDocsDLFileEntryTypeHelper =
				new GoogleDocsDLFileEntryTypeHelper(
					company, _classNameLocalService, _ddmStructureLocalService,
					_dlFileEntryTypeLocalService, _userLocalService);

			googleDocsDLFileEntryTypeHelper.addGoogleDocsDLFileEntryType();
		}
	}

	@Override
	public DLViewFileVersionDisplayContext
		getDLFileVersionActionsDisplayContext(
			DLViewFileVersionDisplayContext
				parentDLFileEntryActionsDisplayContext,
			HttpServletRequest request, HttpServletResponse response,
			FileVersion fileVersion) {

		GoogleDocsMetadataHelper googleDocsMetadataHelper =
			new GoogleDocsMetadataHelper((DLFileVersion)fileVersion.getModel());

		if (googleDocsMetadataHelper.isGoogleDocs()) {
			return new GoogleDocsDLViewFileVersionDisplayContext(
				parentDLFileEntryActionsDisplayContext, request, response,
				fileVersion);
		}

		return parentDLFileEntryActionsDisplayContext;
	}

	@Override
	public DLViewFileVersionDisplayContext
		getIGFileVersionActionsDisplayContext(
			DLViewFileVersionDisplayContext
				parentDLViewFileVersionDisplayContext,
			HttpServletRequest request, HttpServletResponse response,
			FileVersion fileVersion) {

		return getDLFileVersionActionsDisplayContext(
			parentDLViewFileVersionDisplayContext, request, response,
			fileVersion);
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

	private ClassNameLocalService _classNameLocalService;
	private CompanyLocalService _companyLocalService;
	private DDMStructureLocalService _ddmStructureLocalService;
	private DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;
	private UserLocalService _userLocalService;

}