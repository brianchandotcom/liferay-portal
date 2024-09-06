/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.field.attachment;

import com.liferay.document.library.kernel.exception.FileExtensionException;
import com.liferay.document.library.kernel.exception.FileNameException;
import com.liferay.document.library.kernel.exception.FileSizeException;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.document.library.kernel.service.DLFolderService;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.document.library.kernel.util.DLValidator;
import com.liferay.object.configuration.ObjectConfiguration;
import com.liferay.object.constants.ObjectFieldSettingConstants;
import com.liferay.object.field.attachment.AttachmentManager;
import com.liferay.object.field.setting.util.ObjectFieldSettingUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectFieldSetting;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectFieldSettingLocalService;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MimeTypes;
import com.liferay.portal.kernel.util.Validator;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carolina Barbosa
 * @author Carlos Correa
 */
@Component(
	configurationPid = "com.liferay.object.configuration.ObjectConfiguration",
	service = AttachmentManager.class
)
public class AttachmentManagerImpl implements AttachmentManager {

	@Override
	public String[] getAcceptedFileExtensions(long objectFieldId) {
		ObjectFieldSetting objectFieldSetting =
			_objectFieldSettingLocalService.fetchObjectFieldSetting(
				objectFieldId,
				ObjectFieldSettingConstants.NAME_ACCEPTED_FILE_EXTENSIONS);

		String value = objectFieldSetting.getValue();

		return value.split("\\s*,\\s*");
	}

	@Override
	public DLFolder getDLFolder(
			long companyId, long groupId, long objectFieldId,
			ServiceContext serviceContext, long userId)
		throws PortalException {

		Long dlFolderId = null;

		ObjectField objectField = _objectFieldLocalService.getObjectField(
			objectFieldId);

		boolean showFilesInDocumentsAndMedia = GetterUtil.getBoolean(
			ObjectFieldSettingUtil.getValue(
				ObjectFieldSettingConstants.NAME_SHOW_FILES_IN_DOCS_AND_MEDIA,
				objectField.getObjectFieldSettings()));

		if (showFilesInDocumentsAndMedia) {
			String storageDLFolderPath = ObjectFieldSettingUtil.getValue(
				ObjectFieldSettingConstants.NAME_STORAGE_DL_FOLDER_PATH,
				objectField.getObjectFieldSettings());

			dlFolderId = _getStorageDLFolderId(
				companyId, groupId, serviceContext, storageDLFolderPath);
		}
		else {
			ObjectDefinition objectDefinition =
				objectField.getObjectDefinition();

			dlFolderId = _getRepositoryFolderId(
				companyId, groupId, objectDefinition.getPortletId(),
				serviceContext, userId);
		}

		return _dlFolderLocalService.getDLFolder(dlFolderId);
	}

	@Override
	public long getMaximumFileSize(long objectFieldId, boolean signedIn) {
		ObjectFieldSetting objectFieldSetting =
			_objectFieldSettingLocalService.fetchObjectFieldSetting(
				objectFieldId, ObjectFieldSettingConstants.NAME_MAX_FILE_SIZE);

		long maximumFileSize = GetterUtil.getLong(
			objectFieldSetting.getValue());

		if (signedIn ||
			(maximumFileSize <
				_objectConfiguration.maximumFileSizeForGuestUsers())) {

			return maximumFileSize * _FILE_LENGTH_MB;
		}

		return _objectConfiguration.maximumFileSizeForGuestUsers() *
			_FILE_LENGTH_MB;
	}

	@Override
	public FileEntry getOrAddFileEntry(
			long companyId, String externalReferenceCode, byte[] fileContent,
			String fileName, long groupId, long objectFieldId,
			ServiceContext serviceContext)
		throws Exception {

		FileEntry fileEntry =
			_dlAppLocalService.fetchFileEntryByExternalReferenceCode(
				groupId, externalReferenceCode);

		if (fileEntry != null) {
			return fileEntry;
		}

		_validateFile(
			fileContent, fileName, objectFieldId, serviceContext.getUserId());

		DLFolder dlFolder = getDLFolder(
			companyId, groupId, objectFieldId, serviceContext,
			serviceContext.getUserId());

		try (InputStream inputStream = new ByteArrayInputStream(fileContent)) {
			return _dlAppLocalService.addFileEntry(
				externalReferenceCode, serviceContext.getUserId(),
				dlFolder.getRepositoryId(), dlFolder.getFolderId(),
				DLUtil.getUniqueFileName(
					groupId, dlFolder.getFolderId(), fileName, true),
				_mimeTypes.getContentType(inputStream, fileName),
				DLUtil.getUniqueTitle(
					groupId, dlFolder.getFolderId(),
					FileUtil.stripExtension(fileName)),
				StringPool.BLANK, null, null, inputStream, fileContent.length,
				null, null, null, serviceContext);
		}
	}

	@Override
	public FileEntry getOrAddFileEntry(
			long companyId, String externalReferenceCode, byte[] fileContent,
			String fileName, String folderExternalReferenceCode, long groupId,
			long objectFieldId, ServiceContext serviceContext)
		throws Exception {

		FileEntry fileEntry =
			_dlAppLocalService.fetchFileEntryByExternalReferenceCode(
				groupId, externalReferenceCode);

		if (fileEntry != null) {
			return fileEntry;
		}

		_validateFile(
			fileContent, fileName, objectFieldId, serviceContext.getUserId());

		long repositoryId = groupId;
		long folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

		if (Validator.isNotNull(folderExternalReferenceCode)) {
			DLFolder dlFolder =
				_dlFolderService.getDLFolderByExternalReferenceCode(
					folderExternalReferenceCode, groupId);

			repositoryId = dlFolder.getRepositoryId();
			folderId = dlFolder.getFolderId();
		}

		ServiceContext cloneServiceContext =
			(ServiceContext)serviceContext.clone();

		cloneServiceContext.setCompanyId(companyId);

		try (InputStream inputStream = new ByteArrayInputStream(fileContent)) {
			_dlValidator.validateFileSize(
				groupId, fileName,
				_mimeTypes.getContentType(inputStream, fileName),
				fileContent.length);

			return _dlAppService.addFileEntry(
				externalReferenceCode, repositoryId, folderId,
				DLUtil.getUniqueFileName(groupId, folderId, fileName, true),
				_mimeTypes.getContentType(inputStream, fileName),
				DLUtil.getUniqueTitle(
					groupId, folderId, FileUtil.stripExtension(fileName)),
				StringPool.BLANK, null, null, inputStream, fileContent.length,
				null, null, null, cloneServiceContext);
		}
	}

	@Override
	public void validateFileExtension(String fileName, long objectFieldId)
		throws FileExtensionException {

		if (!ArrayUtil.contains(
				getAcceptedFileExtensions(objectFieldId),
				FileUtil.getExtension(fileName), true)) {

			throw new FileExtensionException.InvalidExtension(
				"Invalid file extension for " + fileName);
		}
	}

	@Override
	public void validateFileName(String fileName) throws FileNameException {
		if (Validator.isNull(fileName)) {
			throw new FileNameException("File name is null");
		}
	}

	@Override
	public void validateFileSize(
			String fileName, long fileSize, long objectFieldId,
			boolean signedIn)
		throws FileSizeException {

		long maximumFileSize = getMaximumFileSize(objectFieldId, signedIn);

		if ((maximumFileSize > 0) && (fileSize > maximumFileSize)) {
			throw new FileSizeException(
				StringBundler.concat(
					"File ", fileName,
					" exceeds the maximum permitted size of ",
					maximumFileSize / _FILE_LENGTH_MB, " MB"));
		}
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_objectConfiguration = ConfigurableUtil.createConfigurable(
			ObjectConfiguration.class, properties);
	}

	private Repository _getRepository(
			long groupId, String portletId, ServiceContext serviceContext)
		throws PortalException {

		Repository repository = _portletFileRepository.fetchPortletRepository(
			groupId, portletId);

		if (repository != null) {
			return repository;
		}

		serviceContext = (ServiceContext)serviceContext.clone();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		return _portletFileRepository.addPortletRepository(
			groupId, portletId, serviceContext);
	}

	private Long _getRepositoryFolderId(
			long companyId, long groupId, String portletId,
			ServiceContext serviceContext, long userId)
		throws PortalException {

		Repository repository = _getRepository(
			groupId, portletId, serviceContext);

		if (repository == null) {
			return null;
		}

		DLFolder dlFolder = _dlFolderLocalService.fetchFolder(
			repository.getGroupId(), repository.getDlFolderId(),
			String.valueOf(userId));

		if (dlFolder != null) {
			return dlFolder.getFolderId();
		}

		dlFolder = _dlFolderLocalService.addFolder(
			null, _userLocalService.getGuestUserId(companyId),
			repository.getGroupId(), repository.getRepositoryId(), false,
			repository.getDlFolderId(), String.valueOf(userId), null, false,
			serviceContext);

		return dlFolder.getFolderId();
	}

	private Long _getStorageDLFolderId(
			long companyId, long groupId, ServiceContext serviceContext,
			String storageDLFolderPath)
		throws PortalException {

		long storageDLFolderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

		for (String name :
				StringUtil.split(storageDLFolderPath, CharPool.FORWARD_SLASH)) {

			DLFolder dlFolder = _dlFolderLocalService.fetchFolder(
				groupId, storageDLFolderId, name);

			if (dlFolder != null) {
				storageDLFolderId = dlFolder.getFolderId();

				continue;
			}

			Folder folder = _dlAppLocalService.addFolder(
				null, _userLocalService.getGuestUserId(companyId), groupId,
				storageDLFolderId, name, null, serviceContext);

			storageDLFolderId = folder.getFolderId();
		}

		return storageDLFolderId;
	}

	private void _validateFile(
			byte[] fileContent, String fileName, long objectFieldId,
			long userId)
		throws Exception {

		validateFileName(fileName);
		validateFileExtension(fileName, objectFieldId);

		User user = _userLocalService.getUser(userId);

		validateFileSize(
			fileName, fileContent.length, objectFieldId, !user.isGuestUser());
	}

	private static final long _FILE_LENGTH_MB = 1024 * 1024;

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DLFolderLocalService _dlFolderLocalService;

	@Reference
	private DLFolderService _dlFolderService;

	@Reference
	private DLValidator _dlValidator;

	@Reference
	private MimeTypes _mimeTypes;

	private volatile ObjectConfiguration _objectConfiguration;

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

	@Reference
	private ObjectFieldSettingLocalService _objectFieldSettingLocalService;

	@Reference
	private PortletFileRepository _portletFileRepository;

	@Reference
	private UserLocalService _userLocalService;

}