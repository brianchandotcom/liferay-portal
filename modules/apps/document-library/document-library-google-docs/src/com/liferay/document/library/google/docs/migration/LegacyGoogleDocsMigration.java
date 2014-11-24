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

package com.liferay.document.library.google.docs.migration;

import com.liferay.document.library.google.docs.util.GoogleDocsConstants;
import com.liferay.document.library.google.docs.util.GoogleDocsDLFileEntryTypeHelper;
import com.liferay.document.library.google.docs.util.GoogleDocsMetadataHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Company;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryTypeException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalService;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;

import java.util.List;

/**
 * @author Ivan Zaera
 */
public class LegacyGoogleDocsMigration {

	public LegacyGoogleDocsMigration(
		Company company,
		DLFileEntryTypeLocalService dlFileEntryTypeLocalService,
		DLFileEntryLocalService dlFileEntryLocalService,
		GoogleDocsDLFileEntryTypeHelper googleDocsDLFileEntryTypeHelper) {

		_company = company;
		_dlFileEntryTypeLocalService = dlFileEntryTypeLocalService;
		_dlFileEntryLocalService = dlFileEntryLocalService;
		_googleDocsDLFileEntryTypeHelper = googleDocsDLFileEntryTypeHelper;

		try {
			_dlFileEntryType = _dlFileEntryTypeLocalService.getFileEntryType(
				_company.getGroupId(),
				LegacyGoogleDocsConstants.DL_FILE_ENTRY_TYPE_KEY);
		}
		catch (NoSuchFileEntryTypeException nsfete) {
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
		}
	}

	public boolean isMigrationNeeded() throws PortalException {
		if (_dlFileEntryType == null) {
			return false;
		}

		return true;
	}

	public void migrate() throws PortalException {
		DDMStructure ddmStructure =
			_googleDocsDLFileEntryTypeHelper.addGoogleDocsDDMStructure();

		_dlFileEntryType.setFileEntryTypeKey(
			GoogleDocsConstants.DL_FILE_ENTRY_TYPE_KEY);

		_dlFileEntryType = _dlFileEntryTypeLocalService.updateDLFileEntryType(
			_dlFileEntryType);

		_dlFileEntryTypeLocalService.addDDMStructureDLFileEntryType(
			ddmStructure.getStructureId(), _dlFileEntryType);

		upgradeDLFileEntries();

		deleteLegacyGoogleDocsDDMStructureFields();
	}

	protected void deleteLegacyGoogleDocsDDMStructureFields() {
		DDMStructure legacyDDMStructure =
			LegacyGoogleDocsMetadataHelper.getGoogleDocsDDMStructure(
				_dlFileEntryType);

		String definition = legacyDDMStructure.getDefinition();

		definition = definition.replaceAll(
			"(?s)<dynamic-element[^>]*>.*?</dynamic-element>", "");

		legacyDDMStructure.setDefinition(definition);

		DDMStructureLocalServiceUtil.updateDDMStructure(legacyDDMStructure);
	}

	protected void upgradeDLFileEntries() throws PortalException {
		List<DLFileEntry> dlFileEntries =
			_dlFileEntryLocalService.getCompanyFileEntriesByFileEntryType(
				_company.getCompanyId(), _dlFileEntryType.getFileEntryTypeId());

		for (DLFileEntry dlFileEntry : dlFileEntries) {

			LegacyGoogleDocsMetadataHelper legacyGoogleDocsMetadataHelper =
				new LegacyGoogleDocsMetadataHelper(dlFileEntry);

			String id = legacyGoogleDocsMetadataHelper.getFieldValue(
				LegacyGoogleDocsConstants.DDM_FIELD_NAME_ID);
			String name = legacyGoogleDocsMetadataHelper.getFieldValue(
				LegacyGoogleDocsConstants.DDM_FIELD_NAME_NAME);
			String iconURL = legacyGoogleDocsMetadataHelper.getFieldValue(
				LegacyGoogleDocsConstants.DDM_FIELD_NAME_ICON_URL);
			String viewURL = legacyGoogleDocsMetadataHelper.getFieldValue(
				LegacyGoogleDocsConstants.DDM_FIELD_NAME_VIEW_URL);
			String editURL = legacyGoogleDocsMetadataHelper.getFieldValue(
				LegacyGoogleDocsConstants.DDM_FIELD_NAME_EDIT_URL);

			GoogleDocsMetadataHelper googleDocsMetadataHelper =
				new GoogleDocsMetadataHelper(dlFileEntry);

			googleDocsMetadataHelper.setFieldValue(
				GoogleDocsConstants.DDM_FIELD_NAME_ID, id);
			googleDocsMetadataHelper.setFieldValue(
				GoogleDocsConstants.DDM_FIELD_NAME_NAME, name);
			googleDocsMetadataHelper.setFieldValue(
				GoogleDocsConstants.DDM_FIELD_NAME_ICON_URL, iconURL);
			googleDocsMetadataHelper.setFieldValue(
				GoogleDocsConstants.DDM_FIELD_NAME_EMBEDDABLE_URL, viewURL);
			googleDocsMetadataHelper.setFieldValue(
				GoogleDocsConstants.DDM_FIELD_NAME_URL, editURL);

			googleDocsMetadataHelper.update();

			legacyGoogleDocsMetadataHelper.delete();
		}
	}

	private final Company _company;
	private final DLFileEntryLocalService _dlFileEntryLocalService;
	private DLFileEntryType _dlFileEntryType;
	private final DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;
	private final GoogleDocsDLFileEntryTypeHelper
		_googleDocsDLFileEntryTypeHelper;

}