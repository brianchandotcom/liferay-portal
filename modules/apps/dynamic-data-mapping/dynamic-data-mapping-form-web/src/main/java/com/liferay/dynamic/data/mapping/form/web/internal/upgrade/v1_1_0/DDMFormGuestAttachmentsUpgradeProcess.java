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

package com.liferay.dynamic.data.mapping.form.web.internal.upgrade.v1_1_0;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.dynamic.data.mapping.constants.DDMFormConstants;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Roberto Díaz
 */
public class DDMFormGuestAttachmentsUpgradeProcess extends UpgradeProcess {

	public DDMFormGuestAttachmentsUpgradeProcess(
		DDMFormInstanceRecordLocalService ddmFormInstanceRecordLocalService,
		DLFileEntryLocalService dlFileEntryLocalService,
		JSONFactory jsonFactory, PortletFileRepository portletFileRepository) {

		_ddmFormInstanceRecordLocalService = ddmFormInstanceRecordLocalService;
		_dlFileEntryLocalService = dlFileEntryLocalService;
		_jsonFactory = jsonFactory;
		_portletFileRepository = portletFileRepository;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_updateGuestAttachments();
	}

	private long _getFormsFolderId(long repositoryId) throws PortalException {
		Folder formsFolder = _folderMap.get(repositoryId);

		if (formsFolder == null) {
			formsFolder = _portletFileRepository.getPortletFolder(
				repositoryId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				DDMFormConstants.DDM_FORM_UPLOADED_FILES_FOLDER_NAME);

			_folderMap.put(repositoryId, formsFolder);
		}

		return formsFolder.getFolderId();
	}

	private long _getRepositoryId(long groupId) {
		Repository repository = _repositoryMap.get(groupId);

		if (repository == null) {
			repository = _portletFileRepository.fetchPortletRepository(
				groupId, DDMFormConstants.SERVICE_NAME);

			if (repository == null) {
				return 0;
			}

			_repositoryMap.put(groupId, repository);
		}

		return repository.getRepositoryId();
	}

	private void _updateGuestAttachments() throws Exception {
		try (PreparedStatement preparedStatement1 = connection.prepareStatement(
				"select DDMFormInstanceRecord.formInstanceRecordId from " +
					"DDMFormInstanceRecord");
			ResultSet resultSet = preparedStatement1.executeQuery()) {

			while (resultSet.next()) {
				long formInstanceRecordId = resultSet.getLong(
					"formInstanceRecordId");

				DDMFormInstanceRecord ddmFormInstanceRecord =
					_ddmFormInstanceRecordLocalService.getDDMFormInstanceRecord(
						formInstanceRecordId);

				long groupId = ddmFormInstanceRecord.getGroupId();

				DDMFormValues ddmFormValues =
					ddmFormInstanceRecord.getDDMFormValues();

				for (DDMFormFieldValue ddmFormFieldValue :
						ddmFormValues.getDDMFormFieldValues()) {

					if (Objects.equals(
							ddmFormFieldValue.getType(),
							DDMFormFieldTypeConstants.DOCUMENT_LIBRARY)) {

						Value value = ddmFormFieldValue.getValue();

						JSONObject valueJSONObject =
							_jsonFactory.createJSONObject(
								value.getString(
									ddmFormValues.getDefaultLocale()));

						DLFileEntry dlFileEntry =
							_dlFileEntryLocalService.fetchFileEntry(
								valueJSONObject.getString("uuid"), groupId);

						if (dlFileEntry == null) {
							continue;
						}

						long repositoryId = _getRepositoryId(groupId);

						if (repositoryId == 0) {
							continue;
						}

						long formsFolderId = _getFormsFolderId(repositoryId);

						if (dlFileEntry.getFolderId() == formsFolderId) {
							dlFileEntry.setClassName(
								DDMFormInstanceRecord.class.getName());
							dlFileEntry.setClassPK(formInstanceRecordId);

							_dlFileEntryLocalService.updateDLFileEntry(
								dlFileEntry);
						}
					}
				}
			}
		}
	}

	private final DDMFormInstanceRecordLocalService
		_ddmFormInstanceRecordLocalService;
	private final DLFileEntryLocalService _dlFileEntryLocalService;
	private final Map<Long, Folder> _folderMap = new HashMap<>();
	private final JSONFactory _jsonFactory;
	private final PortletFileRepository _portletFileRepository;
	private final Map<Long, Repository> _repositoryMap = new HashMap<>();

}