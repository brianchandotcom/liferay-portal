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

package com.liferay.commerce.media.internal;

import com.liferay.commerce.media.CommerceCatalogDefaultImage;
import com.liferay.commerce.media.constants.CommerceMediaConstants;
import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.capabilities.TemporaryFileEntriesCapability;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.settings.ModifiableSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.TempFileEntryUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alec Sloan
 */
@Component(immediate = true, service = CommerceCatalogDefaultImage.class)
public class CommerceCatalogDefaultImageImpl
	implements CommerceCatalogDefaultImage {

	@Override
	public long getDefaultCatalogFileEntryId(long groupId)
		throws PortalException {

		Settings settings = _settingsFactory.getSettings(
			new GroupServiceSettingsLocator(
				groupId, CommerceMediaConstants.SERVICE_NAME));

		return GetterUtil.getLong(settings.getValue("defaultFileEntryId", "0"));
	}

	@Override
	public void updateDefaultCatalogFileEntryId(long groupId, long fileEntryId)
		throws Exception {

		FileEntry fileEntry = _dlAppLocalService.getFileEntry(fileEntryId);

		boolean tempFile = fileEntry.isRepositoryCapabilityProvided(
			TemporaryFileEntriesCapability.class);

		if (tempFile) {
			String uniqueFileName = PortletFileRepositoryUtil.getUniqueFileName(
				groupId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				fileEntry.getFileName());

			FileEntry newFileEntry =
				PortletFileRepositoryUtil.addPortletFileEntry(
					groupId, PrincipalThreadLocal.getUserId(),
					CommerceCatalogDefaultImageImpl.class.getName(), 0,
					CPConstants.SERVICE_NAME,
					DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
					fileEntry.getContentStream(), uniqueFileName,
					fileEntry.getMimeType(), true);

			fileEntryId = newFileEntry.getFileEntryId();

			TempFileEntryUtil.deleteTempFileEntry(fileEntry.getFileEntryId());
		}

		Settings settings = _settingsFactory.getSettings(
			new GroupServiceSettingsLocator(
				groupId, CommerceMediaConstants.SERVICE_NAME));

		ModifiableSettings modifiableSettings =
			settings.getModifiableSettings();

		modifiableSettings.setValue(
			"defaultFileEntryId", String.valueOf(fileEntryId));

		modifiableSettings.store();
	}

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private SettingsFactory _settingsFactory;

}