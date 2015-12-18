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

package com.liferay.document.library.google.docs.util;

import com.liferay.dynamic.data.mapping.io.DDMFormXSDDeserializerUtil;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.util.DDMUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.service.ClassNameLocalService;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalService;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Iván Zaera
 */
public class GoogleDocsDLFileEntryTypeHelper {

	public GoogleDocsDLFileEntryTypeHelper(
		Company company, ClassNameLocalService classNameLocalService,
		DDMStructureLocalService ddmStructureLocalService,
		DLFileEntryTypeLocalService dlFileEntryTypeLocalService,
		UserLocalService userLocalService) {

		_company = company;
		_classNameLocalService = classNameLocalService;
		_ddmStructureLocalService = ddmStructureLocalService;
		_dlFileEntryTypeLocalService = dlFileEntryTypeLocalService;
		_userLocalService = userLocalService;

		_dlFileEntryMetadataClassNameId = _classNameLocalService.getClassNameId(
			DLFileEntryMetadata.class);
	}

	public DDMStructure addGoogleDocsDDMStructure() throws PortalException {
		long defaultUserId = _userLocalService.getDefaultUserId(
			_company.getCompanyId());

		Map<Locale, String> nameMap = new HashMap<>();
		Map<Locale, String> descriptionMap = new HashMap<>();

		for (Locale curLocale :
				LanguageUtil.getAvailableLocales(_company.getCompanyId())) {

			nameMap.put(curLocale, "Google Docs Metadata");
			descriptionMap.put(curLocale, "Google Docs Metadata");
		}

		String definition = ResourceUtil.get(
			this, "dependencies/ddm_structure_google_docs.xml");

		DDMForm ddmForm = DDMFormXSDDeserializerUtil.deserialize(definition);

		DDMFormLayout ddmFormLayout = DDMUtil.getDefaultDDMFormLayout(ddmForm);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGuestPermissions(true);
		serviceContext.setAddGroupPermissions(true);
		serviceContext.setScopeGroupId(_company.getGroupId());
		serviceContext.setUserId(defaultUserId);

		return _ddmStructureLocalService.addStructure(
			defaultUserId, _company.getGroupId(),
			DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			_dlFileEntryMetadataClassNameId,
			GoogleDocsConstants.DDM_STRUCTURE_KEY_GOOGLE_DOCS, nameMap,
			descriptionMap, ddmForm, ddmFormLayout, StorageType.JSON.toString(),
			DDMStructureConstants.TYPE_DEFAULT, serviceContext);
	}

	public DLFileEntryType addGoogleDocsDLFileEntryType()
		throws PortalException {

		DDMStructure ddmStructure = _ddmStructureLocalService.fetchStructure(
			_company.getGroupId(), _dlFileEntryMetadataClassNameId,
			GoogleDocsConstants.DDM_STRUCTURE_KEY_GOOGLE_DOCS);

		if (ddmStructure == null) {
			ddmStructure = addGoogleDocsDDMStructure();
		}

		List<DLFileEntryType> dlFileEntryTypes =
			_dlFileEntryTypeLocalService.getFileEntryTypes(
				ddmStructure.getStructureId());

		if (!dlFileEntryTypes.isEmpty()) {
			return dlFileEntryTypes.get(0);
		}

		return addGoogleDocsDLFileEntryType(ddmStructure.getStructureId());
	}

	protected DLFileEntryType addGoogleDocsDLFileEntryType(long ddmStructureId)
		throws PortalException {

		long defaultUserId = _userLocalService.getDefaultUserId(
			_company.getCompanyId());

		Map<Locale, String> nameMap = new HashMap<>();

		nameMap.put(LocaleUtil.getDefault(), "Google Docs");

		Map<Locale, String> descriptionMap = new HashMap<>();

		descriptionMap.put(LocaleUtil.getDefault(), "Google Docs");

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGuestPermissions(true);
		serviceContext.setAddGroupPermissions(true);
		serviceContext.setScopeGroupId(_company.getGroupId());
		serviceContext.setUserId(defaultUserId);

		return _dlFileEntryTypeLocalService.addFileEntryType(
			defaultUserId, _company.getGroupId(),
			GoogleDocsConstants.DL_FILE_ENTRY_TYPE_KEY, nameMap, descriptionMap,
			new long[] {ddmStructureId}, serviceContext);
	}

	private final ClassNameLocalService _classNameLocalService;
	private final Company _company;
	private final DDMStructureLocalService _ddmStructureLocalService;
	private final long _dlFileEntryMetadataClassNameId;
	private final DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;
	private final UserLocalService _userLocalService;

}