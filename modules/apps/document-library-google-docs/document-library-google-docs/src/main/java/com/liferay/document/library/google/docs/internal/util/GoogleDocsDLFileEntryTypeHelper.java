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

package com.liferay.document.library.google.docs.internal.util;

import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Iván Zaera
 */
public class GoogleDocsDLFileEntryTypeHelper {

	public GoogleDocsDLFileEntryTypeHelper(
		Company company, long dlFileEntryMetadataClassNameId, DDM ddm,
		DDMFormDeserializer xsdDDMFormDeserializer,
		DDMStructureLocalService ddmStructureLocalService,
		DLFileEntryTypeLocalService dlFileEntryTypeLocalService,
		UserLocalService userLocalService) {

		_company = company;
		_dlFileEntryMetadataClassNameId = dlFileEntryMetadataClassNameId;
		_ddm = ddm;
		_xsdDDMFormDeserializer = xsdDDMFormDeserializer;
		_ddmStructureLocalService = ddmStructureLocalService;
		_dlFileEntryTypeLocalService = dlFileEntryTypeLocalService;
		_userLocalService = userLocalService;
	}

	public void addGoogleDocsDLFileEntryType() throws PortalException {
		DDMStructure ddmStructure = _ddmStructureLocalService.fetchStructure(
			_company.getGroupId(), _dlFileEntryMetadataClassNameId,
			GoogleDocsConstants.DDM_STRUCTURE_KEY_GOOGLE_DOCS);

		if (ddmStructure == null) {
			ddmStructure = _addGoogleDocsDDMStructure();
		}

		List<DLFileEntryType> dlFileEntryTypes =
			_dlFileEntryTypeLocalService.getFileEntryTypes(
				ddmStructure.getStructureId());

		if (dlFileEntryTypes.isEmpty()) {
			_addGoogleDocsDLFileEntryType(ddmStructure.getStructureId());
		}
	}

	private DDMStructure _addGoogleDocsDDMStructure() throws PortalException {
		long defaultUserId = _userLocalService.getDefaultUserId(
			_company.getCompanyId());

		Map<Locale, String> nameMap = new HashMap<>();
		Map<Locale, String> descriptionMap = new HashMap<>();

		for (Locale curLocale :
				LanguageUtil.getAvailableLocales(_company.getCompanyId())) {

			nameMap.put(curLocale, "Google Docs Metadata");
			descriptionMap.put(curLocale, "Google Docs Metadata");
		}

		String definition = null;

		try {
			definition = StringUtil.read(
				GoogleDocsDLFileEntryTypeHelper.class.getResourceAsStream(
					"dependencies/ddm_structure_google_docs.xml"));
		}
		catch (IOException ioe) {
			throw new PortalException(ioe);
		}

		Locale locale = _company.getLocale();

		definition = StringUtil.replace(
			definition, "[$LOCALE_DEFAULT$]", locale.toString());

		DDMFormDeserializerDeserializeRequest.Builder builder =
			DDMFormDeserializerDeserializeRequest.Builder.newBuilder(
				definition);

		DDMFormDeserializerDeserializeResponse
			ddmFormDeserializerDeserializeResponse =
				_xsdDDMFormDeserializer.deserialize(builder.build());

		DDMForm ddmForm = ddmFormDeserializerDeserializeResponse.getDDMForm();

		DDMFormLayout ddmFormLayout = _ddm.getDefaultDDMFormLayout(ddmForm);

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

	private void _addGoogleDocsDLFileEntryType(long ddmStructureId)
		throws PortalException {

		long defaultUserId = _userLocalService.getDefaultUserId(
			_company.getCompanyId());

		Map<Locale, String> nameMap = new HashMap<>();

		nameMap.put(
			LocaleUtil.getDefault(),
			GoogleDocsConstants.DL_FILE_ENTRY_TYPE_NAME);

		Map<Locale, String> descriptionMap = new HashMap<>();

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGuestPermissions(true);
		serviceContext.setAddGroupPermissions(true);
		serviceContext.setScopeGroupId(_company.getGroupId());
		serviceContext.setUserId(defaultUserId);

		_dlFileEntryTypeLocalService.addFileEntryType(
			defaultUserId, _company.getGroupId(),
			GoogleDocsConstants.DL_FILE_ENTRY_TYPE_KEY, nameMap, descriptionMap,
			new long[] {ddmStructureId}, serviceContext);
	}

	private final Company _company;
	private final DDM _ddm;
	private final DDMStructureLocalService _ddmStructureLocalService;
	private final long _dlFileEntryMetadataClassNameId;
	private final DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;
	private final UserLocalService _userLocalService;
	private final DDMFormDeserializer _xsdDDMFormDeserializer;

}