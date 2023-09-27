/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
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

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
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
			_serviceTrackerMap.getService(objectDefinition.getResourceName());

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

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, PortletResourcePermission.class,
			"(&(com.liferay.object=true)(resource.name=*))",
			(serviceReference, emitter) -> emitter.emit(
				(String)serviceReference.getProperty("resource.name")));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
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

	private ServiceTrackerMap<String, PortletResourcePermission>
		_serviceTrackerMap;

}