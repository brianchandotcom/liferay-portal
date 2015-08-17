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
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.storage.StorageEngine;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.model.Company;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileEntryMetadataLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalService;
import com.liferay.portlet.dynamicdatamapping.DDMStructure;

/**
 * @author Iván Zaera
 */
public class LegacyGoogleDocsMigration {

	public LegacyGoogleDocsMigration(
		Company company, DDMStructureLocalService ddmStructureLocalService,
		DLFileEntryTypeLocalService dlFileEntryTypeLocalService,
		DLFileEntryLocalService dlFileEntryLocalService,
		DLFileEntryMetadataLocalService dlFileEntryMetadataLocalService,
		GoogleDocsDLFileEntryTypeHelper googleDocsDLFileEntryTypeHelper,
		StorageEngine storageEngine) {

		_company = company;
		_ddmStructureLocalService = ddmStructureLocalService;
		_dlFileEntryTypeLocalService = dlFileEntryTypeLocalService;
		_dlFileEntryLocalService = dlFileEntryLocalService;
		_dlFileEntryMetadataLocalService = dlFileEntryMetadataLocalService;
		_googleDocsDLFileEntryTypeHelper = googleDocsDLFileEntryTypeHelper;
		_storageEngine = storageEngine;

		try {
			_dlFileEntryType = _dlFileEntryTypeLocalService.fetchFileEntryType(
				_company.getGroupId(),
				LegacyGoogleDocsConstants.DL_FILE_ENTRY_TYPE_KEY);
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
		}
	}

	public boolean isMigrationNeeded() {
		if (_dlFileEntryType == null) {
			return false;
		}

		return true;
	}

	public void migrate() throws PortalException {
		com.liferay.dynamic.data.mapping.model.DDMStructure ddmStructure =
			_googleDocsDLFileEntryTypeHelper.addGoogleDocsDDMStructure();

		_dlFileEntryType.setFileEntryTypeKey(
			GoogleDocsConstants.DL_FILE_ENTRY_TYPE_KEY);

		_dlFileEntryType = _dlFileEntryTypeLocalService.updateDLFileEntryType(
			_dlFileEntryType);

		_dlFileEntryTypeLocalService.addDDMStructureLinks(
			_dlFileEntryType.getFileEntryTypeId(),
			SetUtil.fromArray(new long[] {ddmStructure.getStructureId()}));

		upgradeDLFileEntries();

		deleteLegacyGoogleDocsDDMStructureFields();
	}

	protected void deleteLegacyGoogleDocsDDMStructureFields()
		throws PortalException {

		DDMStructure legacyDDMStructure =
			LegacyGoogleDocsMetadataHelper.getGoogleDocsDDMStructure(
				_dlFileEntryType);

		String definition = legacyDDMStructure.getDefinition();

		definition = definition.replaceAll(
			"(?s)<dynamic-element[^>]*>.*?</dynamic-element>", "");

		com.liferay.dynamic.data.mapping.model.DDMStructure ddmStructure =
			_ddmStructureLocalService.getDDMStructure(
				legacyDDMStructure.getStructureId());

		ddmStructure.setDefinition(definition);

		_ddmStructureLocalService.updateDDMStructure(ddmStructure);
	}

	protected void upgradeDLFileEntries() throws PortalException {
		ActionableDynamicQuery actionableDynamicQuery =
			_dlFileEntryLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod() {

				@Override
				public void performAction(Object object) {
					DLFileEntry dlFileEntry = (DLFileEntry)object;

					GoogleDocsMetadataHelper googleDocsMetadataHelper =
						new GoogleDocsMetadataHelper(
							_ddmStructureLocalService, dlFileEntry,
							_dlFileEntryMetadataLocalService, _storageEngine);

					LegacyGoogleDocsMetadataHelper
						legacyGoogleDocsMetadataHelper =
							new LegacyGoogleDocsMetadataHelper(
								_ddmStructureLocalService, dlFileEntry,
								_storageEngine);

					googleDocsMetadataHelper.setFieldValue(
						GoogleDocsConstants.DDM_FIELD_NAME_EMBEDDABLE_URL,
						legacyGoogleDocsMetadataHelper.getFieldValue(
							LegacyGoogleDocsConstants.DDM_FIELD_NAME_VIEW_URL));
					googleDocsMetadataHelper.setFieldValue(
						GoogleDocsConstants.DDM_FIELD_NAME_ICON_URL,
						legacyGoogleDocsMetadataHelper.getFieldValue(
							LegacyGoogleDocsConstants.DDM_FIELD_NAME_ICON_URL));
					googleDocsMetadataHelper.setFieldValue(
						GoogleDocsConstants.DDM_FIELD_NAME_ID,
						legacyGoogleDocsMetadataHelper.getFieldValue(
							LegacyGoogleDocsConstants.DDM_FIELD_NAME_ID));
					googleDocsMetadataHelper.setFieldValue(
						GoogleDocsConstants.DDM_FIELD_NAME_NAME,
						legacyGoogleDocsMetadataHelper.getFieldValue(
							LegacyGoogleDocsConstants.DDM_FIELD_NAME_NAME));
					googleDocsMetadataHelper.setFieldValue(
						GoogleDocsConstants.DDM_FIELD_NAME_URL,
						legacyGoogleDocsMetadataHelper.getFieldValue(
							LegacyGoogleDocsConstants.DDM_FIELD_NAME_EDIT_URL));

					googleDocsMetadataHelper.update();

					legacyGoogleDocsMetadataHelper.delete();
				}
			}

		);

		actionableDynamicQuery.performActions();
	}

	private final Company _company;
	private final DDMStructureLocalService _ddmStructureLocalService;
	private final DLFileEntryLocalService _dlFileEntryLocalService;
	private final DLFileEntryMetadataLocalService
		_dlFileEntryMetadataLocalService;
	private DLFileEntryType _dlFileEntryType;
	private final DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;
	private final GoogleDocsDLFileEntryTypeHelper
		_googleDocsDLFileEntryTypeHelper;
	private final StorageEngine _storageEngine;

}