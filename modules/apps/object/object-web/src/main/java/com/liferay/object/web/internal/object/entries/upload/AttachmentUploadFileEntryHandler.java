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

package com.liferay.object.web.internal.object.entries.upload;

import com.liferay.document.library.kernel.exception.InvalidFileException;
import com.liferay.object.constants.ObjectActionKeys;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.web.internal.object.entries.upload.util.AttachmentValidator;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermissionFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MimeTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.upload.UploadFileEntryHandler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carolina Barbosa
 */
@Component(service = AttachmentUploadFileEntryHandler.class)
public class AttachmentUploadFileEntryHandler
	implements UploadFileEntryHandler {

	@Override
	public FileEntry upload(UploadPortletRequest uploadPortletRequest)
		throws IOException, PortalException {

		long objectFieldId = ParamUtil.getLong(
			uploadPortletRequest, "objectFieldId");

		ObjectField objectField = _objectFieldLocalService.fetchObjectField(
			objectFieldId);

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				objectField.getObjectDefinitionId());

		PortletResourcePermission portletResourcePermission =
			PortletResourcePermissionFactory.create(
				objectDefinition.getResourceName(),
				(permissionChecker, name, group, actionId) ->
					permissionChecker.hasPermission(group, name, 0, actionId));

		ThemeDisplay themeDisplay =
			(ThemeDisplay)uploadPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		long groupId = _getGroupId(objectDefinition, themeDisplay);

		portletResourcePermission.check(
			themeDisplay.getPermissionChecker(), groupId,
			ObjectActionKeys.ADD_OBJECT_ENTRY);

		String fileName = uploadPortletRequest.getFileName("file");

		_attachmentValidator.validateFileExtension(fileName, objectFieldId);

		File file = null;

		try (InputStream inputStream = uploadPortletRequest.getFileAsStream(
				"file")) {

			file = FileUtil.createTempFile(inputStream);

			if (file == null) {
				throw new InvalidFileException("File is null for " + fileName);
			}

			_attachmentValidator.validateFileSize(
				fileName, file.length(), objectFieldId,
				themeDisplay.isSignedIn());

			return TempFileEntryUtil.addTempFileEntry(
				groupId, themeDisplay.getUserId(),
				objectDefinition.getPortletId(),
				TempFileEntryUtil.getTempFileName(fileName), file,
				_mimeTypes.getContentType(file, fileName));
		}
		finally {
			if (file != null) {
				FileUtil.delete(file);
			}
		}
	}

	private long _getGroupId(
			ObjectDefinition objectDefinition, ThemeDisplay themeDisplay)
		throws PortalException {

		long groupId = themeDisplay.getScopeGroupId();

		if (Objects.equals(
				ObjectDefinitionConstants.SCOPE_COMPANY,
				objectDefinition.getScope())) {

			Company company = themeDisplay.getCompany();

			groupId = company.getGroupId();
		}

		return groupId;
	}

	@Reference
	private AttachmentValidator _attachmentValidator;

	@Reference
	private MimeTypes _mimeTypes;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

}