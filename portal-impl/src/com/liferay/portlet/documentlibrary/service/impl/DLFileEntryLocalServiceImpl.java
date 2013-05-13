/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.service.impl;

import com.liferay.portal.ExpiredLockException;
import com.liferay.portal.InvalidLockException;
import com.liferay.portal.NoSuchLockException;
import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.image.ImageBag;
import com.liferay.portal.kernel.image.ImageToolUtil;
import com.liferay.portal.kernel.increment.BufferedIncrement;
import com.liferay.portal.kernel.increment.NumberIncrement;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.DigesterUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.model.Image;
import com.liferay.portal.model.Lock;
import com.liferay.portal.model.ModelHintsUtil;
import com.liferay.portal.model.Repository;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileVersion;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetLink;
import com.liferay.portlet.asset.model.AssetLinkConstants;
import com.liferay.portlet.documentlibrary.DuplicateFileException;
import com.liferay.portlet.documentlibrary.DuplicateFolderNameException;
import com.liferay.portlet.documentlibrary.FileExtensionException;
import com.liferay.portlet.documentlibrary.FileNameException;
import com.liferay.portlet.documentlibrary.ImageSizeException;
import com.liferay.portlet.documentlibrary.InvalidFileEntryTypeException;
import com.liferay.portlet.documentlibrary.InvalidFileVersionException;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.NoSuchFileVersionException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.model.DLSyncConstants;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryImpl;
import com.liferay.portlet.documentlibrary.service.base.DLFileEntryLocalServiceBaseImpl;
import com.liferay.portlet.documentlibrary.store.DLStoreUtil;
import com.liferay.portlet.documentlibrary.util.DL;
import com.liferay.portlet.documentlibrary.util.DLAppUtil;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.documentlibrary.util.comparator.RepositoryModelModifiedDateComparator;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;
import com.liferay.portlet.dynamicdatamapping.storage.StorageEngineUtil;
import com.liferay.portlet.expando.NoSuchRowException;
import com.liferay.portlet.expando.NoSuchTableException;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.model.ExpandoColumnConstants;
import com.liferay.portlet.expando.model.ExpandoRow;
import com.liferay.portlet.expando.model.ExpandoTable;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;
import com.liferay.portlet.trash.model.TrashVersion;

import java.awt.image.RenderedImage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Provides the local service for accessing, adding, checking in/out, deleting,
 * locking/unlocking, moving, reverting, updating, and verifying document
 * library file entries.
 *
 * <p>
 * Due to legacy code, the names of some file entry properties are not
 * intuitive. Each file entry has both a name and title. The <code>name</code>
 * is a unique identifier for a given file and is generally numeric, whereas the
 * <code>title</code> is the actual name specified by the user (such as
 * &quot;Budget.xls&quot;).
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @author Harry Mark
 * @author Alexander Chow
 * @author Manuel de la Peña
 */
public class DLFileEntryLocalServiceImpl
	extends DLFileEntryLocalServiceBaseImpl {

	@Override
	public DLFileEntry addFileEntry(
			long userId, long groupId, long repositoryId, long folderId,
			String sourceFileName, String mimeType, String title,
			String description, String changeLog, long fileEntryTypeId,
			Map<String, Fields> fieldsMap, File file, InputStream is, long size,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		if (Validator.isNull(title)) {
			if (size == 0) {
				throw new FileNameException();
			}
			else {
				title = sourceFileName;
			}
		}

		// File entry

		User user = userPersistence.findByPrimaryKey(userId);
		folderId = dlFolderLocalService.getFolderId(
			user.getCompanyId(), folderId);
		String name = String.valueOf(
			counterLocalService.increment(DLFileEntry.class.getName()));
		String extension = DLAppUtil.getExtension(title, sourceFileName);

		if (fileEntryTypeId == -1) {
			fileEntryTypeId =
				dlFileEntryTypeLocalService.getDefaultFileEntryTypeId(folderId);
		}

		Date now = new Date();

		validateFileEntryTypeId(
			PortalUtil.getSiteAndCompanyGroupIds(groupId), folderId,
			fileEntryTypeId);

		validateFile(
			groupId, folderId, 0, title, extension, sourceFileName, file, is);

		long fileEntryId = counterLocalService.increment();

		DLFileEntry dlFileEntry = dlFileEntryPersistence.create(fileEntryId);

		dlFileEntry.setUuid(serviceContext.getUuid());
		dlFileEntry.setGroupId(groupId);
		dlFileEntry.setCompanyId(user.getCompanyId());
		dlFileEntry.setUserId(user.getUserId());
		dlFileEntry.setUserName(user.getFullName());
		dlFileEntry.setVersionUserId(user.getUserId());
		dlFileEntry.setVersionUserName(user.getFullName());
		dlFileEntry.setCreateDate(serviceContext.getCreateDate(now));
		dlFileEntry.setModifiedDate(serviceContext.getModifiedDate(now));

		DLFolder repositoryDLFolder = null;

		if (repositoryId != groupId) {
			Repository repository = repositoryLocalService.getRepository(
				repositoryId);

			repositoryDLFolder = dlFolderPersistence.findByPrimaryKey(
				repository.getDlFolderId());
		}

		if ((repositoryDLFolder != null) && repositoryDLFolder.isHidden()) {
			long classNameId = PortalUtil.getClassNameId(
				(String)serviceContext.getAttribute("className"));
			long classPK = ParamUtil.getLong(serviceContext, "classPK");

			if (Validator.isNotNull(classNameId) &&
				Validator.isNotNull(classPK)) {

				dlFileEntry.setClassNameId(classNameId);
				dlFileEntry.setClassPK(classPK);
			}
		}

		dlFileEntry.setRepositoryId(repositoryId);
		dlFileEntry.setFolderId(folderId);
		dlFileEntry.setName(name);
		dlFileEntry.setExtension(extension);
		dlFileEntry.setMimeType(mimeType);
		dlFileEntry.setTitle(title);
		dlFileEntry.setDescription(description);
		dlFileEntry.setFileEntryTypeId(fileEntryTypeId);
		dlFileEntry.setVersion(DLFileEntryConstants.VERSION_DEFAULT);
		dlFileEntry.setSize(size);
		dlFileEntry.setReadCount(DLFileEntryConstants.DEFAULT_READ_COUNT);

		dlFileEntryPersistence.update(dlFileEntry);

		// File version

		addFileVersion(
			user, dlFileEntry, serviceContext.getModifiedDate(now), extension,
			mimeType, title, description, null, StringPool.BLANK,
			fileEntryTypeId, fieldsMap, DLFileEntryConstants.VERSION_DEFAULT,
			size, WorkflowConstants.STATUS_DRAFT, serviceContext);

		// Folder

		if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			DLFolder dlFolder = dlFolderPersistence.findByPrimaryKey(
				dlFileEntry.getFolderId());

			dlFolder.setLastPostDate(dlFileEntry.getModifiedDate());

			dlFolderPersistence.update(dlFolder);
		}

		// File

		if (file != null) {
			DLStoreUtil.addFile(
				user.getCompanyId(), dlFileEntry.getDataRepositoryId(), name,
				false, file);
		}
		else {
			DLStoreUtil.addFile(
				user.getCompanyId(), dlFileEntry.getDataRepositoryId(), name,
				false, is);
		}

		return dlFileEntry;
	}

	@Override
	public DLFileVersion cancelCheckOut(long userId, long fileEntryId)
		throws PortalException, SystemException {

		if (!isFileEntryCheckedOut(fileEntryId)) {
			return null;
		}

		DLFileEntry dlFileEntry = dlFileEntryPersistence.findByPrimaryKey(
			fileEntryId);

		DLFileVersion dlFileVersion =
			dlFileVersionLocalService.getLatestFileVersion(fileEntryId, false);

		removeFileVersion(dlFileEntry, dlFileVersion);

		if (dlFileEntry.getFolderId() !=
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			DLFolder dlFolder = dlFolderPersistence.findByPrimaryKey(
				dlFileEntry.getFolderId());

			dlFolder.setLastPostDate(new Date());

			dlFolderPersistence.update(dlFolder);
		}

		return dlFileVersion;
	}

	@Override
	public void checkInFileEntry(
			long userId, long fileEntryId, boolean majorVersion,
			String changeLog, ServiceContext serviceContext)
		throws PortalException, SystemException {

		if (!isFileEntryCheckedOut(fileEntryId)) {
			return;
		}

		User user = userPersistence.findByPrimaryKey(userId);

		DLFileEntry dlFileEntry = dlFileEntryPersistence.findByPrimaryKey(
			fileEntryId);

		boolean webDAVCheckInMode = GetterUtil.getBoolean(
			serviceContext.getAttribute(DL.WEBDAV_CHECK_IN_MODE));

		boolean manualCheckInRequired = dlFileEntry.getManualCheckInRequired();

		if (!webDAVCheckInMode && manualCheckInRequired) {
			dlFileEntry.setManualCheckInRequired(false);

			dlFileEntryPersistence.update(dlFileEntry);
		}

		DLFileVersion lastDLFileVersion =
			dlFileVersionLocalService.getFileVersion(
				dlFileEntry.getFileEntryId(), dlFileEntry.getVersion());

		DLFileVersion latestDLFileVersion =
			dlFileVersionLocalService.getLatestFileVersion(fileEntryId, false);

		if (isKeepFileVersionLabel(
				dlFileEntry, lastDLFileVersion, latestDLFileVersion,
				serviceContext.getWorkflowAction())) {

			if (lastDLFileVersion.getSize() != latestDLFileVersion.getSize()) {

				// File version

				lastDLFileVersion.setExtension(
					latestDLFileVersion.getExtension());
				lastDLFileVersion.setMimeType(
					latestDLFileVersion.getMimeType());
				lastDLFileVersion.setSize(latestDLFileVersion.getSize());

				dlFileVersionPersistence.update(lastDLFileVersion);

				// File

				try {
					DLStoreUtil.deleteFile(
						user.getCompanyId(), dlFileEntry.getDataRepositoryId(),
						dlFileEntry.getName(), lastDLFileVersion.getVersion());
				}
				catch (NoSuchModelException nsme) {
				}

				DLStoreUtil.copyFileVersion(
					user.getCompanyId(), dlFileEntry.getDataRepositoryId(),
					dlFileEntry.getName(),
					DLFileEntryConstants.PRIVATE_WORKING_COPY_VERSION,
					lastDLFileVersion.getVersion());
			}

			// Latest file version

			removeFileVersion(dlFileEntry, latestDLFileVersion);

			latestDLFileVersion = lastDLFileVersion;
		}
		else {

			// File version

			String version = getNextVersion(
				dlFileEntry, majorVersion, serviceContext.getWorkflowAction());

			latestDLFileVersion.setVersion(version);
			latestDLFileVersion.setChangeLog(changeLog);

			dlFileVersionPersistence.update(latestDLFileVersion);

			// File

			DLStoreUtil.updateFileVersion(
				user.getCompanyId(), dlFileEntry.getDataRepositoryId(),
				dlFileEntry.getName(),
				DLFileEntryConstants.PRIVATE_WORKING_COPY_VERSION, version);
		}

		// Folder

		if (dlFileEntry.getFolderId() !=
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			DLFolder dlFolder = dlFolderPersistence.findByPrimaryKey(
				dlFileEntry.getFolderId());

			dlFolder.setLastPostDate(dlFileEntry.getModifiedDate());

			dlFolderPersistence.update(dlFolder);
		}

		// Workflow

		if (serviceContext.getWorkflowAction() ==
				WorkflowConstants.ACTION_PUBLISH) {

			startWorkflowInstance(
				userId, serviceContext, latestDLFileVersion,
				DLSyncConstants.EVENT_UPDATE);
		}

		unlockFileEntry(fileEntryId);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #checkInFileEntry(long, long,
	 *             String, ServiceContext)}
	 */
	@Override
	public void checkInFileEntry(long userId, long fileEntryId, String lockUuid)
		throws PortalException, SystemException {

		checkInFileEntry(userId, fileEntryId, lockUuid, new ServiceContext());
	}

	@Override
	public void checkInFileEntry(
			long userId, long fileEntryId, String lockUuid,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		if (Validator.isNotNull(lockUuid)) {
			try {
				Lock lock = lockLocalService.getLock(
					DLFileEntry.class.getName(), fileEntryId);

				if (!lock.getUuid().equals(lockUuid)) {
					throw new InvalidLockException("UUIDs do not match");
				}
			}
			catch (PortalException pe) {
				if ((pe instanceof ExpiredLockException) ||
					(pe instanceof NoSuchLockException)) {
				}
				else {
					throw pe;
				}
			}
		}

		checkInFileEntry(
			userId, fileEntryId, false, StringPool.BLANK, serviceContext);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #checkOutFileEntry(long,
	 *             long, ServiceContext)}
	 */
	@Override
	public DLFileEntry checkOutFileEntry(long userId, long fileEntryId)
		throws PortalException, SystemException {

		return checkOutFileEntry(userId, fileEntryId, new ServiceContext());
	}

	@Override
	public DLFileEntry checkOutFileEntry(
			long userId, long fileEntryId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		return checkOutFileEntry(
			userId, fileEntryId, StringPool.BLANK,
			DLFileEntryImpl.LOCK_EXPIRATION_TIME, serviceContext);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #checkOutFileEntry(long,
	 *             long, String, long, ServiceContext)}
	 */
	@Override
	public DLFileEntry checkOutFileEntry(
			long userId, long fileEntryId, String owner, long expirationTime)
		throws PortalException, SystemException {

		return checkOutFileEntry(
			userId, fileEntryId, owner, expirationTime, new ServiceContext());
	}

	@Override
	public DLFileEntry checkOutFileEntry(
			long userId, long fileEntryId, String owner, long expirationTime,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFileEntry dlFileEntry = dlFileEntryPersistence.findByPrimaryKey(
			fileEntryId);

		boolean hasLock = hasFileEntryLock(userId, fileEntryId);

		if (!hasLock) {
			if ((expirationTime <= 0) ||
				(expirationTime > DLFileEntryImpl.LOCK_EXPIRATION_TIME)) {

				expirationTime = DLFileEntryImpl.LOCK_EXPIRATION_TIME;
			}

			lockLocalService.lock(
				userId, DLFileEntry.class.getName(), fileEntryId, owner, false,
				expirationTime);
		}

		User user = userPersistence.findByPrimaryKey(userId);

		serviceContext.setCompanyId(user.getCompanyId());

		DLFileVersion dlFileVersion =
			dlFileVersionLocalService.getLatestFileVersion(fileEntryId, false);

		long dlFileVersionId = dlFileVersion.getFileVersionId();

		Map<String, Serializable> expandoBridgeAttributes =
			serviceContext.getExpandoBridgeAttributes();

		if (expandoBridgeAttributes.isEmpty()) {
			ExpandoBridge expandoBridge =
				ExpandoBridgeFactoryUtil.getExpandoBridge(
					serviceContext.getCompanyId(), DLFileEntry.class.getName(),
					dlFileVersionId);

			serviceContext.setExpandoBridgeAttributes(
				expandoBridge.getAttributes());
		}

		serviceContext.setUserId(userId);

		boolean manualCheckinRequired = GetterUtil.getBoolean(
			serviceContext.getAttribute(DL.MANUAL_CHECK_IN_REQUIRED));

		dlFileEntry.setManualCheckInRequired(manualCheckinRequired);

		dlFileEntryPersistence.update(dlFileEntry);

		String version = dlFileVersion.getVersion();

		if (!version.equals(
				DLFileEntryConstants.PRIVATE_WORKING_COPY_VERSION)) {

			long existingDLFileVersionId = ParamUtil.getLong(
				serviceContext, "existingDLFileVersionId");

			if (existingDLFileVersionId > 0) {
				DLFileVersion existingDLFileVersion =
					dlFileVersionPersistence.findByPrimaryKey(
						existingDLFileVersionId);

				dlFileVersion = updateFileVersion(
					user, existingDLFileVersion, null,
					existingDLFileVersion.getExtension(),
					existingDLFileVersion.getMimeType(),
					existingDLFileVersion.getTitle(),
					existingDLFileVersion.getDescription(),
					existingDLFileVersion.getChangeLog(),
					existingDLFileVersion.getExtraSettings(),
					existingDLFileVersion.getFileEntryTypeId(), null,
					DLFileEntryConstants.PRIVATE_WORKING_COPY_VERSION,
					existingDLFileVersion.getSize(),
					WorkflowConstants.STATUS_DRAFT, new Date(), serviceContext);
			}
			else {
				long oldDLFileVersionId = dlFileVersion.getFileVersionId();

				dlFileVersion = addFileVersion(
					user, dlFileEntry, new Date(), dlFileVersion.getExtension(),
					dlFileVersion.getMimeType(), dlFileVersion.getTitle(),
					dlFileVersion.getDescription(),
					dlFileVersion.getChangeLog(),
					dlFileVersion.getExtraSettings(),
					dlFileVersion.getFileEntryTypeId(), null,
					DLFileEntryConstants.PRIVATE_WORKING_COPY_VERSION,
					dlFileVersion.getSize(), WorkflowConstants.STATUS_DRAFT,
					serviceContext);

				copyExpandoRowModifiedDate(
					dlFileEntry.getCompanyId(), oldDLFileVersionId,
					dlFileVersion.getFileVersionId());
			}

			try {
				DLStoreUtil.deleteFile(
					dlFileEntry.getCompanyId(),
					dlFileEntry.getDataRepositoryId(), dlFileEntry.getName(),
					DLFileEntryConstants.PRIVATE_WORKING_COPY_VERSION);
			}
			catch (NoSuchModelException nsme) {
			}

			DLStoreUtil.copyFileVersion(
				user.getCompanyId(), dlFileEntry.getDataRepositoryId(),
				dlFileEntry.getName(), version,
				DLFileEntryConstants.PRIVATE_WORKING_COPY_VERSION);

			copyFileEntryMetadata(
				dlFileEntry.getCompanyId(), dlFileVersion.getFileEntryTypeId(),
				fileEntryId, dlFileVersionId, dlFileVersion.getFileVersionId(),
				serviceContext);
		}

		if (dlFileEntry.getFolderId() !=
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			DLFolder dlFolder = dlFolderPersistence.findByPrimaryKey(
				dlFileEntry.getFolderId());

			dlFolder.setLastPostDate(dlFileVersion.getModifiedDate());

			dlFolderPersistence.update(dlFolder);
		}

		return dlFileEntry;
	}

	@Override
	public void convertExtraSettings(String[] keys)
		throws PortalException, SystemException {

		int count = dlFileEntryFinder.countByExtraSettings();

		int pages = count / Indexer.DEFAULT_INTERVAL;

		for (int i = 0; i <= pages; i++) {
			int start = (i * Indexer.DEFAULT_INTERVAL);
			int end = start + Indexer.DEFAULT_INTERVAL;

			List<DLFileEntry> dlFileEntries =
				dlFileEntryFinder.findByExtraSettings(start, end);

			for (DLFileEntry dlFileEntry : dlFileEntries) {
				convertExtraSettings(dlFileEntry, keys);
			}
		}
	}

	@Override
	public void copyFileEntryMetadata(
			long companyId, long fileEntryTypeId, long fileEntryId,
			long fromFileVersionId, long toFileVersionId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		Map<String, Fields> fieldsMap = new HashMap<String, Fields>();

		List<DDMStructure> ddmStructures = null;

		if (fileEntryTypeId > 0) {
			DLFileEntryType dlFileEntryType =
				dlFileEntryTypeLocalService.getFileEntryType(fileEntryTypeId);

			ddmStructures = dlFileEntryType.getDDMStructures();
		}
		else {
			long classNameId = PortalUtil.getClassNameId(
				DLFileEntryMetadata.class);

			ddmStructures = ddmStructureLocalService.getClassStructures(
				companyId, classNameId);
		}

		copyFileEntryMetadata(
			companyId, fileEntryTypeId, fileEntryId, fromFileVersionId,
			toFileVersionId, serviceContext, fieldsMap, ddmStructures);
	}

	@Override
	public void deleteFileEntries(long groupId, long folderId)
		throws PortalException, SystemException {

		deleteFileEntries(groupId, folderId, true);
	}

	@Override
	public void deleteFileEntries(
			long groupId, long folderId, boolean includeTrashedEntries)
		throws PortalException, SystemException {

		int count = dlFileEntryPersistence.countByG_F(groupId, folderId);

		int pages = count / _DELETE_INTERVAL;

		for (int i = 0; i <= pages; i++) {
			int start = (i * _DELETE_INTERVAL);
			int end = start + _DELETE_INTERVAL;

			List<DLFileEntry> dlFileEntries = dlFileEntryPersistence.findByG_F(
				groupId, folderId, start, end);

			for (DLFileEntry dlFileEntry : dlFileEntries) {
				DLFileVersion dlFileVersion = dlFileEntry.getLatestFileVersion(
					true);

				if (includeTrashedEntries || !dlFileVersion.isInTrash()) {
					dlAppHelperLocalService.deleteFileEntry(
						new LiferayFileEntry(dlFileEntry));

					dlFileEntryLocalService.deleteFileEntry(dlFileEntry);
				}
			}
		}
	}

	@Override
	@Indexable(type = IndexableType.DELETE)
	public DLFileEntry deleteFileEntry(DLFileEntry dlFileEntry)
		throws PortalException, SystemException {

		// File entry

		dlFileEntryPersistence.remove(dlFileEntry);

		// Resources

		resourceLocalService.deleteResource(
			dlFileEntry.getCompanyId(), DLFileEntry.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, dlFileEntry.getFileEntryId());

		// WebDAVProps

		webDAVPropsLocalService.deleteWebDAVProps(
			DLFileEntry.class.getName(), dlFileEntry.getFileEntryId());

		// File entry metadata

		dlFileEntryMetadataLocalService.deleteFileEntryMetadata(
			dlFileEntry.getFileEntryId());

		// File versions

		List<DLFileVersion> dlFileVersions =
			dlFileVersionPersistence.findByFileEntryId(
				dlFileEntry.getFileEntryId());

		for (DLFileVersion dlFileVersion : dlFileVersions) {
			dlFileVersionPersistence.remove(dlFileVersion);

			expandoValueLocalService.deleteValues(
				DLFileEntry.class.getName(), dlFileVersion.getFileVersionId());

			workflowInstanceLinkLocalService.deleteWorkflowInstanceLinks(
				dlFileEntry.getCompanyId(), dlFileEntry.getGroupId(),
				DLFileEntry.class.getName(), dlFileVersion.getFileVersionId());
		}

		// Expando

		expandoValueLocalService.deleteValues(
			DLFileEntry.class.getName(), dlFileEntry.getFileEntryId());

		// Lock

		unlockFileEntry(dlFileEntry.getFileEntryId());

		// File

		try {
			DLStoreUtil.deleteFile(
				dlFileEntry.getCompanyId(), dlFileEntry.getDataRepositoryId(),
				dlFileEntry.getName());
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		return dlFileEntry;
	}

	@Override
	@Indexable(type = IndexableType.DELETE)
	public DLFileEntry deleteFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		DLFileEntry dlFileEntry = getFileEntry(fileEntryId);

		return deleteFileEntry(dlFileEntry);
	}

	@Override
	@Indexable(type = IndexableType.DELETE)
	public DLFileEntry deleteFileEntry(long userId, long fileEntryId)
		throws PortalException, SystemException {

		if (!hasFileEntryLock(userId, fileEntryId)) {
			lockFileEntry(userId, fileEntryId);
		}

		try {
			return deleteFileEntry(fileEntryId);
		}
		finally {
			unlockFileEntry(fileEntryId);
		}
	}

	@Override
	@Indexable(type = IndexableType.REINDEX)
	public DLFileEntry deleteFileVersion(
			long userId, long fileEntryId, String version)
		throws PortalException, SystemException {

		if (Validator.isNull(version) ||
			version.equals(DLFileEntryConstants.PRIVATE_WORKING_COPY_VERSION)) {

			throw new InvalidFileVersionException();
		}

		if (!hasFileEntryLock(userId, fileEntryId)) {
			lockFileEntry(userId, fileEntryId);
		}

		boolean latestVersion = false;

		DLFileEntry dlFileEntry = null;

		try {
			DLFileVersion dlFileVersion = dlFileVersionPersistence.findByF_V(
				fileEntryId, version);

			if (!dlFileVersion.isApproved()) {
				throw new InvalidFileVersionException(
					"Cannot delete an unapproved file version");
			}
			else {
				int count = dlFileVersionPersistence.countByF_S(
					fileEntryId, WorkflowConstants.STATUS_APPROVED);

				if (count <= 1) {
					throw new InvalidFileVersionException(
						"Cannot delete the only approved file version");
				}
			}

			dlFileVersionPersistence.remove(dlFileVersion);

			expandoValueLocalService.deleteValues(
				DLFileEntry.class.getName(), dlFileVersion.getFileVersionId());

			dlFileEntry = dlFileEntryPersistence.findByPrimaryKey(fileEntryId);

			latestVersion = version.equals(dlFileEntry.getVersion());

			if (latestVersion) {
				try {
					DLFileVersion dlLatestFileVersion =
						dlFileVersionLocalService.getLatestFileVersion(
							dlFileEntry.getFileEntryId(), true);

					dlFileEntry.setVersionUserId(
						dlLatestFileVersion.getUserId());
					dlFileEntry.setVersionUserName(
						dlLatestFileVersion.getUserName());
					dlFileEntry.setModifiedDate(
						dlLatestFileVersion.getCreateDate());
					dlFileEntry.setExtension(
						dlLatestFileVersion.getExtension());
					dlFileEntry.setMimeType(dlLatestFileVersion.getMimeType());
					dlFileEntry.setTitle(dlLatestFileVersion.getTitle());
					dlFileEntry.setDescription(
						dlLatestFileVersion.getDescription());
					dlFileEntry.setExtraSettings(
						dlLatestFileVersion.getExtraSettings());
					dlFileEntry.setFileEntryTypeId(
						dlLatestFileVersion.getFileEntryTypeId());
					dlFileEntry.setVersion(dlLatestFileVersion.getVersion());
					dlFileEntry.setSize(dlLatestFileVersion.getSize());

					dlFileEntry = dlFileEntryPersistence.update(dlFileEntry);
				}
				catch (NoSuchFileVersionException nsfve) {
				}
			}

			try {
				DLStoreUtil.deleteFile(
					dlFileEntry.getCompanyId(),
					dlFileEntry.getDataRepositoryId(), dlFileEntry.getName(),
					version);
			}
			catch (NoSuchModelException nsme) {
			}
		}
		finally {
			unlockFileEntry(fileEntryId);
		}

		if (latestVersion) {
			return dlFileEntry;
		}

		return null;
	}

	@Override
	public DLFileEntry fetchFileEntry(long groupId, long folderId, String title)
		throws SystemException {

		return dlFileEntryPersistence.fetchByG_F_T(groupId, folderId, title);
	}

	@Override
	public DLFileEntry fetchFileEntryByAnyImageId(long imageId)
		throws SystemException {

		return dlFileEntryFinder.fetchByAnyImageId(imageId);
	}

	@Override
	public DLFileEntry fetchFileEntryByName(
			long groupId, long folderId, String name)
		throws SystemException {

		return dlFileEntryPersistence.fetchByG_F_N(groupId, folderId, name);
	}

	@Override
	public List<DLFileEntry> getDDMStructureFileEntries(long[] ddmStructureIds)
		throws SystemException {

		return dlFileEntryFinder.findByDDMStructureIds(
			ddmStructureIds, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	@Override
	public List<DLFileEntry> getExtraSettingsFileEntries(int start, int end)
		throws SystemException {

		return dlFileEntryFinder.findByExtraSettings(start, end);
	}

	@Override
	public File getFile(
			long userId, long fileEntryId, String version,
			boolean incrementCounter)
		throws PortalException, SystemException {

		return getFile(userId, fileEntryId, version, incrementCounter, 1);
	}

	@Override
	public File getFile(
			long userId, long fileEntryId, String version,
			boolean incrementCounter, int increment)
		throws PortalException, SystemException {

		DLFileEntry dlFileEntry = dlFileEntryPersistence.findByPrimaryKey(
			fileEntryId);

		if (incrementCounter) {
			dlFileEntryLocalService.incrementViewCounter(
				dlFileEntry, increment);
		}

		dlAppHelperLocalService.getFileAsStream(
			userId, new LiferayFileEntry(dlFileEntry), incrementCounter);

		return DLStoreUtil.getFile(
			dlFileEntry.getCompanyId(), dlFileEntry.getDataRepositoryId(),
			dlFileEntry.getName(), version);
	}

	@Override
	public InputStream getFileAsStream(
			long userId, long fileEntryId, String version)
		throws PortalException, SystemException {

		return getFileAsStream(userId, fileEntryId, version, true, 1);
	}

	@Override
	public InputStream getFileAsStream(
			long userId, long fileEntryId, String version,
			boolean incrementCounter)
		throws PortalException, SystemException {

		return getFileAsStream(
			userId, fileEntryId, version, incrementCounter, 1);
	}

	@Override
	public InputStream getFileAsStream(
			long userId, long fileEntryId, String version,
			boolean incrementCounter, int increment)
		throws PortalException, SystemException {

		DLFileEntry dlFileEntry = dlFileEntryPersistence.findByPrimaryKey(
			fileEntryId);

		if (incrementCounter) {
			dlFileEntryLocalService.incrementViewCounter(
				dlFileEntry, increment);
		}

		dlAppHelperLocalService.getFileAsStream(
			userId, new LiferayFileEntry(dlFileEntry), incrementCounter);

		return DLStoreUtil.getFileAsStream(
			dlFileEntry.getCompanyId(), dlFileEntry.getDataRepositoryId(),
			dlFileEntry.getName(), version);
	}

	@Override
	public List<DLFileEntry> getFileEntries(int start, int end)
		throws SystemException {

		return dlFileEntryPersistence.findAll(start, end);
	}

	@Override
	public List<DLFileEntry> getFileEntries(long groupId, long folderId)
		throws SystemException {

		return dlFileEntryPersistence.findByG_F(groupId, folderId);
	}

	@Override
	public List<DLFileEntry> getFileEntries(
			long groupId, long folderId, int status, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		List<Long> folderIds = new ArrayList<Long>();

		folderIds.add(folderId);

		QueryDefinition queryDefinition = new QueryDefinition(
			status, false, start, end, obc);

		return dlFileEntryFinder.findByG_F(groupId, folderIds, queryDefinition);
	}

	@Override
	public List<DLFileEntry> getFileEntries(
			long groupId, long folderId, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return dlFileEntryPersistence.findByG_F(
			groupId, folderId, start, end, obc);
	}

	@Override
	public List<DLFileEntry> getFileEntries(long folderId, String name)
		throws SystemException {

		return dlFileEntryPersistence.findByF_N(folderId, name);
	}

	@Override
	public int getFileEntriesCount() throws SystemException {
		return dlFileEntryPersistence.countAll();
	}

	@Override
	public int getFileEntriesCount(long groupId, long folderId)
		throws SystemException {

		return dlFileEntryPersistence.countByG_F(groupId, folderId);
	}

	@Override
	public int getFileEntriesCount(long groupId, long folderId, int status)
		throws SystemException {

		List<Long> folderIds = new ArrayList<Long>();

		folderIds.add(folderId);

		return dlFileEntryFinder.countByG_F(
			groupId, folderIds, new QueryDefinition(status));
	}

	@Override
	public DLFileEntry getFileEntry(long fileEntryId)
		throws PortalException, SystemException {

		return dlFileEntryPersistence.findByPrimaryKey(fileEntryId);
	}

	@Override
	public DLFileEntry getFileEntry(long groupId, long folderId, String title)
		throws PortalException, SystemException {

		DLFileEntry dlFileEntry = dlFileEntryPersistence.fetchByG_F_T(
			groupId, folderId, title);

		if (dlFileEntry != null) {
			return dlFileEntry;
		}

		List<DLFileVersion> dlFileVersions =
			dlFileVersionPersistence.findByG_F_T_V(
				groupId, folderId, title,
				DLFileEntryConstants.PRIVATE_WORKING_COPY_VERSION);

		long userId = PrincipalThreadLocal.getUserId();

		for (DLFileVersion dlFileVersion : dlFileVersions) {
			if (hasFileEntryLock(userId, dlFileVersion.getFileEntryId())) {
				return dlFileVersion.getFileEntry();
			}
		}

		StringBundler sb = new StringBundler(8);

		sb.append("No DLFileEntry exists with the key {");
		sb.append("groupId=");
		sb.append(groupId);
		sb.append(", folderId=");
		sb.append(folderId);
		sb.append(", title=");
		sb.append(title);
		sb.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFileEntryException(sb.toString());
	}

	@Override
	public DLFileEntry getFileEntryByName(
			long groupId, long folderId, String name)
		throws PortalException, SystemException {

		return dlFileEntryPersistence.findByG_F_N(groupId, folderId, name);
	}

	@Override
	public DLFileEntry getFileEntryByUuidAndGroupId(String uuid, long groupId)
		throws PortalException, SystemException {

		return dlFileEntryPersistence.findByUUID_G(uuid, groupId);
	}

	@Override
	public List<DLFileEntry> getGroupFileEntries(
			long groupId, int start, int end)
		throws SystemException {

		return getGroupFileEntries(
			groupId, start, end, new RepositoryModelModifiedDateComparator());
	}

	@Override
	public List<DLFileEntry> getGroupFileEntries(
			long groupId, int start, int end, OrderByComparator obc)
		throws SystemException {

		return dlFileEntryPersistence.findByGroupId(groupId, start, end, obc);
	}

	@Override
	public List<DLFileEntry> getGroupFileEntries(
			long groupId, long userId, int start, int end)
		throws SystemException {

		return getGroupFileEntries(
			groupId, userId, start, end,
			new RepositoryModelModifiedDateComparator());
	}

	@Override
	public List<DLFileEntry> getGroupFileEntries(
			long groupId, long userId, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		if (userId <= 0) {
			return dlFileEntryPersistence.findByGroupId(
				groupId, start, end, obc);
		}
		else {
			return dlFileEntryPersistence.findByG_U(
				groupId, userId, start, end, obc);
		}
	}

	@Override
	public int getGroupFileEntriesCount(long groupId) throws SystemException {
		return dlFileEntryPersistence.countByGroupId(groupId);
	}

	@Override
	public int getGroupFileEntriesCount(long groupId, long userId)
		throws SystemException {

		if (userId <= 0) {
			return dlFileEntryPersistence.countByGroupId(groupId);
		}
		else {
			return dlFileEntryPersistence.countByG_U(groupId, userId);
		}
	}

	@Override
	public List<DLFileEntry> getMisversionedFileEntries()
		throws SystemException {

		return dlFileEntryFinder.findByMisversioned();
	}

	@Override
	public List<DLFileEntry> getNoAssetFileEntries() throws SystemException {
		return dlFileEntryFinder.findByNoAssets();
	}

	@Override
	public List<DLFileEntry> getOrphanedFileEntries() throws SystemException {
		return dlFileEntryFinder.findByOrphanedFileEntries();
	}

	@Override
	public boolean hasExtraSettings() throws SystemException {
		if (dlFileEntryFinder.countByExtraSettings() > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean hasFileEntryLock(long userId, long fileEntryId)
		throws PortalException, SystemException {

		DLFileEntry dlFileEntry = getFileEntry(fileEntryId);

		long folderId = dlFileEntry.getFolderId();

		boolean hasLock = lockLocalService.hasLock(
			userId, DLFileEntry.class.getName(), fileEntryId);

		if (!hasLock &&
			(folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {

			hasLock = dlFolderService.hasInheritableLock(folderId);
		}

		return hasLock;
	}

	@Override
	@BufferedIncrement(
		configuration = "DLFileEntry", incrementClass = NumberIncrement.class)
	public void incrementViewCounter(DLFileEntry dlFileEntry, int increment)
		throws SystemException {

		dlFileEntry.setReadCount(dlFileEntry.getReadCount() + increment);

		dlFileEntryPersistence.update(dlFileEntry);
	}

	@Override
	public boolean isFileEntryCheckedOut(long fileEntryId)
		throws PortalException, SystemException {

		DLFileVersion dlFileVersion =
			dlFileVersionLocalService.getLatestFileVersion(fileEntryId, false);

		String version = dlFileVersion.getVersion();

		if (version.equals(DLFileEntryConstants.PRIVATE_WORKING_COPY_VERSION)) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public Lock lockFileEntry(long userId, long fileEntryId)
		throws PortalException, SystemException {

		if (hasFileEntryLock(userId, fileEntryId)) {
			return lockLocalService.getLock(
				DLFileEntry.class.getName(), fileEntryId);
		}

		return lockLocalService.lock(
			userId, DLFileEntry.class.getName(), fileEntryId, null, false,
			DLFileEntryImpl.LOCK_EXPIRATION_TIME);
	}

	@Override
	@Indexable(type = IndexableType.REINDEX)
	public DLFileEntry moveFileEntry(
			long userId, long fileEntryId, long newFolderId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		if (!hasFileEntryLock(userId, fileEntryId)) {
			lockFileEntry(userId, fileEntryId);
		}

		try {
			DLFileEntry dlFileEntry = moveFileEntryImpl(
				userId, fileEntryId, newFolderId, serviceContext);

			dlAppHelperLocalService.moveFileEntry(
				new LiferayFileEntry(dlFileEntry));

			return dlFileEntryTypeLocalService.updateFileEntryFileEntryType(
				dlFileEntry, serviceContext);
		}
		finally {
			if (!isFileEntryCheckedOut(fileEntryId)) {
				unlockFileEntry(fileEntryId);
			}
		}
	}

	@Override
	public void revertFileEntry(
			long userId, long fileEntryId, String version,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		if (Validator.isNull(version) ||
			version.equals(DLFileEntryConstants.PRIVATE_WORKING_COPY_VERSION)) {

			throw new InvalidFileVersionException();
		}

		DLFileVersion dlFileVersion = dlFileVersionLocalService.getFileVersion(
			fileEntryId, version);

		if (!dlFileVersion.isApproved()) {
			throw new InvalidFileVersionException(
				"Cannot revert from an unapproved file version");
		}

		DLFileVersion latestDLFileVersion =
			dlFileVersionLocalService.getLatestFileVersion(fileEntryId, false);

		if (version.equals(latestDLFileVersion.getVersion())) {
			throw new InvalidFileVersionException(
				"Cannot revert from the latest file version");
		}

		String sourceFileName = dlFileVersion.getTitle();
		String extension = dlFileVersion.getExtension();
		String mimeType = dlFileVersion.getMimeType();
		String title = dlFileVersion.getTitle();
		String description = dlFileVersion.getDescription();
		String changeLog = "Reverted to " + version;
		boolean majorVersion = true;
		String extraSettings = dlFileVersion.getExtraSettings();
		long fileEntryTypeId = dlFileVersion.getFileEntryTypeId();
		Map<String, Fields> fieldsMap = null;
		InputStream is = getFileAsStream(userId, fileEntryId, version);
		long size = dlFileVersion.getSize();

		DLFileEntry dlFileEntry = updateFileEntry(
			userId, fileEntryId, sourceFileName, extension, mimeType, title,
			description, changeLog, majorVersion, extraSettings,
			fileEntryTypeId, fieldsMap, null, is, size, serviceContext);

		DLFileVersion newDlFileVersion =
			dlFileVersionLocalService.getFileVersion(
				fileEntryId, dlFileEntry.getVersion());

		copyFileEntryMetadata(
			dlFileVersion.getCompanyId(), dlFileVersion.getFileEntryTypeId(),
			fileEntryId, newDlFileVersion.getFileVersionId(),
			dlFileVersion.getFileVersionId(), serviceContext);
	}

	@Override
	public void unlockFileEntry(long fileEntryId) throws SystemException {
		lockLocalService.unlock(DLFileEntry.class.getName(), fileEntryId);
	}

	@Override
	public DLFileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			boolean majorVersion, long fileEntryTypeId,
			Map<String, Fields> fieldsMap, File file, InputStream is, long size,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		DLFileEntry dlFileEntry = dlFileEntryPersistence.findByPrimaryKey(
			fileEntryId);

		String extension = DLAppUtil.getExtension(title, sourceFileName);

		String extraSettings = StringPool.BLANK;

		if (fileEntryTypeId == -1) {
			fileEntryTypeId = dlFileEntry.getFileEntryTypeId();
		}

		validateFileEntryTypeId(
			PortalUtil.getSiteAndCompanyGroupIds(dlFileEntry.getGroupId()),
			dlFileEntry.getFolderId(), fileEntryTypeId);

		return updateFileEntry(
			userId, fileEntryId, sourceFileName, extension, mimeType, title,
			description, changeLog, majorVersion, extraSettings,
			fileEntryTypeId, fieldsMap, file, is, size, serviceContext);
	}

	@Override
	public void updateSmallImage(long smallImageId, long largeImageId)
		throws PortalException, SystemException {

		try {
			RenderedImage renderedImage = null;

			Image largeImage = imageLocalService.getImage(largeImageId);

			byte[] bytes = largeImage.getTextObj();
			String contentType = largeImage.getType();

			if (bytes != null) {
				ImageBag imageBag = ImageToolUtil.read(bytes);

				renderedImage = imageBag.getRenderedImage();

				//validate(bytes);
			}

			if (renderedImage != null) {
				int height = PrefsPropsUtil.getInteger(
					PropsKeys.DL_FILE_ENTRY_THUMBNAIL_MAX_HEIGHT);
				int width = PrefsPropsUtil.getInteger(
					PropsKeys.DL_FILE_ENTRY_THUMBNAIL_MAX_WIDTH);

				RenderedImage thumbnailRenderedImage = ImageToolUtil.scale(
					renderedImage, height, width);

				imageLocalService.updateImage(
					smallImageId,
					ImageToolUtil.getBytes(
						thumbnailRenderedImage, contentType));
			}
		}
		catch (IOException ioe) {
			throw new ImageSizeException(ioe);
		}
	}

	@Override
	public DLFileEntry updateStatus(
			long userId, long fileVersionId, int status,
			Map<String, Serializable> workflowContext,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// File version

		User user = userPersistence.findByPrimaryKey(userId);

		DLFileVersion dlFileVersion = dlFileVersionPersistence.findByPrimaryKey(
			fileVersionId);

		int oldStatus = dlFileVersion.getStatus();

		int oldDLFileVersionStatus = WorkflowConstants.STATUS_ANY;

		List<ObjectValuePair<Long, Integer>> dlFileVersionStatusOVPs =
			new ArrayList<ObjectValuePair<Long, Integer>>();

		List<DLFileVersion> dlFileVersions =
			(List<DLFileVersion>)workflowContext.get("dlFileVersions");

		if ((dlFileVersions != null) && !dlFileVersions.isEmpty()) {
			DLFileVersion oldDLFileVersion = dlFileVersions.get(0);

			oldDLFileVersionStatus = oldDLFileVersion.getStatus();

			dlFileVersionStatusOVPs = getDlFileVersionStatuses(dlFileVersions);
		}

		dlFileVersion.setStatus(status);
		dlFileVersion.setStatusByUserId(user.getUserId());
		dlFileVersion.setStatusByUserName(user.getFullName());
		dlFileVersion.setStatusDate(new Date());

		dlFileVersionPersistence.update(dlFileVersion);

		// File entry

		DLFileEntry dlFileEntry = dlFileEntryPersistence.findByPrimaryKey(
			dlFileVersion.getFileEntryId());

		if (status == WorkflowConstants.STATUS_APPROVED) {
			if (DLUtil.compareVersions(
					dlFileEntry.getVersion(),
					dlFileVersion.getVersion()) <= 0) {

				dlFileEntry.setExtension(dlFileVersion.getExtension());
				dlFileEntry.setMimeType(dlFileVersion.getMimeType());
				dlFileEntry.setTitle(dlFileVersion.getTitle());
				dlFileEntry.setDescription(dlFileVersion.getDescription());
				dlFileEntry.setExtraSettings(dlFileVersion.getExtraSettings());
				dlFileEntry.setFileEntryTypeId(
					dlFileVersion.getFileEntryTypeId());
				dlFileEntry.setVersion(dlFileVersion.getVersion());
				dlFileEntry.setVersionUserId(dlFileVersion.getUserId());
				dlFileEntry.setVersionUserName(dlFileVersion.getUserName());
				dlFileEntry.setModifiedDate(dlFileVersion.getCreateDate());
				dlFileEntry.setSize(dlFileVersion.getSize());

				dlFileEntryPersistence.update(dlFileEntry);
			}
		}
		else {

			// File entry

			if ((status != WorkflowConstants.STATUS_IN_TRASH) &&
				dlFileEntry.getVersion().equals(dlFileVersion.getVersion())) {

				String newVersion = DLFileEntryConstants.VERSION_DEFAULT;

				List<DLFileVersion> approvedFileVersions =
					dlFileVersionPersistence.findByF_S(
						dlFileEntry.getFileEntryId(),
						WorkflowConstants.STATUS_APPROVED);

				if (!approvedFileVersions.isEmpty()) {
					newVersion = approvedFileVersions.get(0).getVersion();
				}

				dlFileEntry.setVersion(newVersion);

				dlFileEntryPersistence.update(dlFileEntry);
			}

			// Indexer

			if (dlFileVersion.getVersion().equals(
					DLFileEntryConstants.VERSION_DEFAULT)) {

				Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
					DLFileEntry.class);

				indexer.delete(dlFileEntry);
			}
		}

		// File versions

		if (oldStatus == WorkflowConstants.STATUS_IN_TRASH) {

			// Trash

			List<TrashVersion> trashVersions =
				(List<TrashVersion>)workflowContext.get("trashVersions");

			for (TrashVersion trashVersion : trashVersions) {
				DLFileVersion trashDLFileVersion =
					dlFileVersionPersistence.findByPrimaryKey(
						trashVersion.getClassPK());

				trashDLFileVersion.setStatus(trashVersion.getStatus());

				dlFileVersionPersistence.update(trashDLFileVersion);
			}

			trashEntryLocalService.deleteEntry(
				DLFileEntryConstants.getClassName(),
				dlFileEntry.getFileEntryId());

			// Indexer

			Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
				DLFileEntry.class);

			indexer.delete(dlFileEntry);
		}
		else if (status == WorkflowConstants.STATUS_IN_TRASH) {

			// Trash

			for (DLFileVersion curDLFileVersion : dlFileVersions) {
				curDLFileVersion.setStatus(WorkflowConstants.STATUS_IN_TRASH);

				dlFileVersionPersistence.update(curDLFileVersion);
			}

			UnicodeProperties typeSettingsProperties = new UnicodeProperties();

			typeSettingsProperties.put("title", dlFileEntry.getTitle());

			trashEntryLocalService.addTrashEntry(
				userId, dlFileEntry.getGroupId(),
				DLFileEntryConstants.getClassName(),
				dlFileEntry.getFileEntryId(), oldDLFileVersionStatus,
				dlFileVersionStatusOVPs, typeSettingsProperties);
		}

		// App helper

		dlAppHelperLocalService.updateStatus(
			userId, new LiferayFileEntry(dlFileEntry),
			new LiferayFileVersion(dlFileVersion), oldStatus, status,
			workflowContext, serviceContext);

		// Indexer

		if (((status == WorkflowConstants.STATUS_APPROVED) ||
			 (status == WorkflowConstants.STATUS_IN_TRASH) ||
			 (oldStatus == WorkflowConstants.STATUS_IN_TRASH)) &&
			((serviceContext == null) || serviceContext.isIndexingEnabled())) {

			reindex(dlFileEntry);
		}

		return dlFileEntry;
	}

	@Override
	public boolean verifyFileEntryCheckOut(long fileEntryId, String lockUuid)
		throws PortalException, SystemException {

		if (verifyFileEntryLock(fileEntryId, lockUuid) &&
			isFileEntryCheckedOut(fileEntryId)) {

			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean verifyFileEntryLock(long fileEntryId, String lockUuid)
		throws PortalException, SystemException {

		boolean lockVerified = false;

		try {
			Lock lock = lockLocalService.getLock(
				DLFileEntry.class.getName(), fileEntryId);

			if (lock.getUuid().equals(lockUuid)) {
				lockVerified = true;
			}
		}
		catch (PortalException pe) {
			if ((pe instanceof ExpiredLockException) ||
				(pe instanceof NoSuchLockException)) {

				DLFileEntry dlFileEntry = dlFileEntryLocalService.getFileEntry(
					fileEntryId);

				lockVerified = dlFolderService.verifyInheritableLock(
					dlFileEntry.getFolderId(), lockUuid);
			}
			else {
				throw pe;
			}
		}

		return lockVerified;
	}

	protected DLFileVersion addFileVersion(
			User user, DLFileEntry dlFileEntry, Date modifiedDate,
			String extension, String mimeType, String title, String description,
			String changeLog, String extraSettings, long fileEntryTypeId,
			Map<String, Fields> fieldsMap, String version, long size,
			int status, ServiceContext serviceContext)
		throws PortalException, SystemException {

		long fileVersionId = counterLocalService.increment();

		DLFileVersion dlFileVersion = dlFileVersionPersistence.create(
			fileVersionId);

		String uuid = ParamUtil.getString(
			serviceContext, "fileVersionUuid", serviceContext.getUuid());

		dlFileVersion.setUuid(uuid);

		dlFileVersion.setGroupId(dlFileEntry.getGroupId());
		dlFileVersion.setCompanyId(dlFileEntry.getCompanyId());

		long versionUserId = dlFileEntry.getVersionUserId();

		if (versionUserId <= 0) {
			versionUserId = dlFileEntry.getUserId();
		}

		dlFileVersion.setUserId(versionUserId);

		String versionUserName = GetterUtil.getString(
			dlFileEntry.getVersionUserName(), dlFileEntry.getUserName());

		dlFileVersion.setUserName(versionUserName);

		dlFileVersion.setCreateDate(modifiedDate);
		dlFileVersion.setModifiedDate(modifiedDate);
		dlFileVersion.setRepositoryId(dlFileEntry.getRepositoryId());
		dlFileVersion.setFolderId(dlFileEntry.getFolderId());
		dlFileVersion.setFileEntryId(dlFileEntry.getFileEntryId());
		dlFileVersion.setExtension(extension);
		dlFileVersion.setMimeType(mimeType);
		dlFileVersion.setTitle(title);
		dlFileVersion.setDescription(description);
		dlFileVersion.setChangeLog(changeLog);
		dlFileVersion.setExtraSettings(extraSettings);
		dlFileVersion.setFileEntryTypeId(fileEntryTypeId);
		dlFileVersion.setVersion(version);
		dlFileVersion.setSize(size);
		dlFileVersion.setStatus(status);
		dlFileVersion.setStatusByUserId(user.getUserId());
		dlFileVersion.setStatusByUserName(user.getFullName());
		dlFileVersion.setStatusDate(dlFileEntry.getModifiedDate());
		dlFileVersion.setExpandoBridgeAttributes(serviceContext);

		dlFileVersionPersistence.update(dlFileVersion);

		if ((fileEntryTypeId > 0) && (fieldsMap != null)) {
			dlFileEntryMetadataLocalService.updateFileEntryMetadata(
				fileEntryTypeId, dlFileEntry.getFileEntryId(), fileVersionId,
				fieldsMap, serviceContext);
		}

		return dlFileVersion;
	}

	protected void convertExtraSettings(
			DLFileEntry dlFileEntry, DLFileVersion dlFileVersion, String[] keys)
		throws PortalException, SystemException {

		UnicodeProperties extraSettingsProperties =
			dlFileVersion.getExtraSettingsProperties();

		ExpandoBridge expandoBridge = dlFileVersion.getExpandoBridge();

		convertExtraSettings(extraSettingsProperties, expandoBridge, keys);

		dlFileVersion.setExtraSettingsProperties(extraSettingsProperties);

		dlFileVersionPersistence.update(dlFileVersion);

		int status = dlFileVersion.getStatus();

		if ((status == WorkflowConstants.STATUS_APPROVED) &&
			(DLUtil.compareVersions(
				dlFileEntry.getVersion(), dlFileVersion.getVersion()) <= 0)) {

			reindex(dlFileEntry);
		}
	}

	protected void convertExtraSettings(DLFileEntry dlFileEntry, String[] keys)
		throws PortalException, SystemException {

		UnicodeProperties extraSettingsProperties =
			dlFileEntry.getExtraSettingsProperties();

		ExpandoBridge expandoBridge = dlFileEntry.getExpandoBridge();

		convertExtraSettings(extraSettingsProperties, expandoBridge, keys);

		dlFileEntry.setExtraSettingsProperties(extraSettingsProperties);

		dlFileEntryPersistence.update(dlFileEntry);

		List<DLFileVersion> dlFileVersions =
			dlFileVersionLocalService.getFileVersions(
				dlFileEntry.getFileEntryId(), WorkflowConstants.STATUS_ANY);

		for (DLFileVersion dlFileVersion : dlFileVersions) {
			convertExtraSettings(dlFileEntry, dlFileVersion, keys);
		}
	}

	protected void convertExtraSettings(
		UnicodeProperties extraSettingsProperties, ExpandoBridge expandoBridge,
		String[] keys) {

		for (String key : keys) {
			String value = extraSettingsProperties.remove(key);

			if (Validator.isNull(value)) {
				continue;
			}

			int type = expandoBridge.getAttributeType(key);

			Serializable serializable = ExpandoColumnConstants.getSerializable(
				type, value);

			expandoBridge.setAttribute(key, serializable);
		}
	}

	protected void copyExpandoRowModifiedDate(
			long companyId, long sourceFileVersionId,
			long destinationFileVersionId)
		throws PortalException, SystemException {

		ExpandoTable expandoTable = null;

		try {
			expandoTable = expandoTableLocalService.getDefaultTable(
				companyId, DLFileEntry.class.getName());
		}
		catch (NoSuchTableException nste) {
			return;
		}

		Date sourceModifiedDate = null;

		try {
			ExpandoRow sourceExpandoRow = expandoRowLocalService.getRow(
				expandoTable.getTableId(), sourceFileVersionId);

			sourceModifiedDate = sourceExpandoRow.getModifiedDate();
		}
		catch (NoSuchRowException nsre) {
			return;
		}

		try {
			ExpandoRow destinationExpandoRow = expandoRowLocalService.getRow(
				expandoTable.getTableId(), destinationFileVersionId);

			destinationExpandoRow.setModifiedDate(sourceModifiedDate);

			expandoRowLocalService.updateExpandoRow(destinationExpandoRow);
		}
		catch (NoSuchRowException nsre) {
		}
	}

	protected void copyFileEntryMetadata(
			long companyId, long fileEntryTypeId, long fileEntryId,
			long fromFileVersionId, long toFileVersionId,
			ServiceContext serviceContext, Map<String, Fields> fieldsMap,
			List<DDMStructure> ddmStructures)
		throws PortalException, SystemException {

		for (DDMStructure ddmStructure : ddmStructures) {
			DLFileEntryMetadata dlFileEntryMetadata =
				dlFileEntryMetadataLocalService.fetchFileEntryMetadata(
					ddmStructure.getStructureId(), fromFileVersionId);

			if (dlFileEntryMetadata == null) {
				continue;
			}

			Fields fields = StorageEngineUtil.getFields(
				dlFileEntryMetadata.getDDMStorageId());

			fieldsMap.put(ddmStructure.getStructureKey(), fields);
		}

		if (!fieldsMap.isEmpty()) {
			dlFileEntryMetadataLocalService.updateFileEntryMetadata(
				companyId, ddmStructures, fileEntryTypeId, fileEntryId,
				toFileVersionId, fieldsMap, serviceContext);
		}
	}

	protected List<ObjectValuePair<Long, Integer>> getDlFileVersionStatuses(
		List<DLFileVersion> dlFileVersions) {

		List<ObjectValuePair<Long, Integer>> dlFileVersionStatusOVPs =
			new ArrayList<ObjectValuePair<Long, Integer>>(
				dlFileVersions.size());

		for (DLFileVersion dlFileVersion : dlFileVersions) {
			int status = dlFileVersion.getStatus();

			if (status == WorkflowConstants.STATUS_PENDING) {
				status = WorkflowConstants.STATUS_DRAFT;
			}

			ObjectValuePair<Long, Integer> dlFileVersionStatusOVP =
				new ObjectValuePair<Long, Integer>(
					dlFileVersion.getFileVersionId(), status);

			dlFileVersionStatusOVPs.add(dlFileVersionStatusOVP);
		}

		return dlFileVersionStatusOVPs;
	}

	protected String getNextVersion(
			DLFileEntry dlFileEntry, boolean majorVersion, int workflowAction)
		throws PortalException, SystemException {

		String version = dlFileEntry.getVersion();

		try {
			DLFileVersion dlFileVersion =
				dlFileVersionLocalService.getLatestFileVersion(
					dlFileEntry.getFileEntryId(), true);

			version = dlFileVersion.getVersion();
		}
		catch (NoSuchFileVersionException nsfve) {
		}

		if (workflowAction == WorkflowConstants.ACTION_SAVE_DRAFT) {
			majorVersion = false;
		}

		int[] versionParts = StringUtil.split(version, StringPool.PERIOD, 0);

		if (majorVersion) {
			versionParts[0]++;
			versionParts[1] = 0;
		}
		else {
			versionParts[1]++;
		}

		return versionParts[0] + StringPool.PERIOD + versionParts[1];
	}

	protected boolean isKeepFileVersionLabel(
			DLFileEntry dlFileEntry, DLFileVersion lastDLFileVersion,
			DLFileVersion latestDLFileVersion, int workflowAction)
		throws PortalException, SystemException {

		if (workflowAction == WorkflowConstants.ACTION_SAVE_DRAFT) {
			return false;
		}

		if (PropsValues.DL_FILE_ENTRY_VERSION_POLICY != 1) {
			return false;
		}

		if ((lastDLFileVersion.getFolderId() ==
				latestDLFileVersion.getFolderId()) &&
			Validator.equals(
				lastDLFileVersion.getTitle(), latestDLFileVersion.getTitle()) &&
			Validator.equals(
				lastDLFileVersion.getDescription(),
				latestDLFileVersion.getDescription()) &&
			(lastDLFileVersion.getFileEntryTypeId() ==
				latestDLFileVersion.getFileEntryTypeId())) {

			// Asset

			AssetEntry lastAssetEntry = assetEntryLocalService.getEntry(
				DLFileEntryConstants.getClassName(),
				dlFileEntry.getFileEntryId());
			AssetEntry latestAssetEntry = assetEntryLocalService.getEntry(
				DLFileEntryConstants.getClassName(),
				latestDLFileVersion.getFileVersionId());

			if (!Validator.equalsSorted(
					lastAssetEntry.getCategoryIds(),
					latestAssetEntry.getCategoryIds())) {

				return false;
			}

			if (!Validator.equalsSorted(
					lastAssetEntry.getTagNames(),
					latestAssetEntry.getTagNames())) {

				return false;
			}

			List<AssetLink> lastAssetLinks =
				assetLinkLocalService.getDirectLinks(
					lastAssetEntry.getEntryId(),
					AssetLinkConstants.TYPE_RELATED);
			List<AssetLink> latestAssetLinks =
				assetLinkLocalService.getDirectLinks(
					latestAssetEntry.getEntryId(),
					AssetLinkConstants.TYPE_RELATED);

			if (!Validator.equalsSorted(
					StringUtil.split(
						ListUtil.toString(
							lastAssetLinks, AssetLink.ENTRY_ID2_ACCESSOR), 0L),
					StringUtil.split(
						ListUtil.toString(
							latestAssetLinks, AssetLink.ENTRY_ID2_ACCESSOR),
							0L))) {

				return false;
			}

			// Expando

			ExpandoTable expandoTable = null;

			try {
				expandoTable = expandoTableLocalService.getDefaultTable(
					lastDLFileVersion.getCompanyId(),
					DLFileEntry.class.getName());
			}
			catch (NoSuchTableException nste) {
			}

			if (expandoTable != null) {
				Date lastModifiedDate = null;

				try {
					ExpandoRow lastExpandoRow = expandoRowLocalService.getRow(
						expandoTable.getTableId(),
						lastDLFileVersion.getPrimaryKey());

					lastModifiedDate = lastExpandoRow.getModifiedDate();
				}
				catch (NoSuchRowException nsre) {
				}

				Date latestModifiedDate = null;

				try {
					ExpandoRow latestExpandoRow = expandoRowLocalService.getRow(
						expandoTable.getTableId(),
						latestDLFileVersion.getPrimaryKey());

					latestModifiedDate = latestExpandoRow.getModifiedDate();
				}
				catch (NoSuchRowException nsre) {
				}

				if (!Validator.equals(lastModifiedDate, latestModifiedDate)) {
					return false;
				}
			}

			// File entry type

			List<DLFileEntryMetadata> lastFileEntryMetadatas =
				dlFileEntryMetadataLocalService.
					getFileVersionFileEntryMetadatas(
						lastDLFileVersion.getFileVersionId());
			List<DLFileEntryMetadata> latestFileEntryMetadatas =
				dlFileEntryMetadataLocalService.
					getFileVersionFileEntryMetadatas(
						latestDLFileVersion.getFileVersionId());

			for (DLFileEntryMetadata lastFileEntryMetadata :
					lastFileEntryMetadatas) {

				Fields lastFields = StorageEngineUtil.getFields(
					lastFileEntryMetadata.getDDMStorageId());

				boolean found = false;

				for (DLFileEntryMetadata latestEntryMetadata :
						latestFileEntryMetadatas) {

					Fields latestFields = StorageEngineUtil.getFields(
						latestEntryMetadata.getDDMStorageId());

					if (lastFields.equals(latestFields)) {
						found = true;

						break;
					}
				}

				if (!found) {
					return false;
				}
			}

			// Size

			long lastSize = lastDLFileVersion.getSize();
			long latestSize = latestDLFileVersion.getSize();

			if ((lastSize == 0) && ((latestSize == 0) || (latestSize > 0))) {
				return true;
			}

			if (lastSize != latestSize) {
				return false;
			}

			// Checksum

			InputStream lastInputStream = null;
			InputStream latestInputStream = null;

			try {
				String lastChecksum = lastDLFileVersion.getChecksum();

				if (Validator.isNull(lastChecksum)) {
					lastInputStream = DLStoreUtil.getFileAsStream(
						dlFileEntry.getCompanyId(),
						dlFileEntry.getDataRepositoryId(),
						dlFileEntry.getName(), lastDLFileVersion.getVersion());

					lastChecksum = DigesterUtil.digestBase64(lastInputStream);

					lastDLFileVersion.setChecksum(lastChecksum);

					dlFileVersionPersistence.update(lastDLFileVersion);
				}

				latestInputStream = DLStoreUtil.getFileAsStream(
					dlFileEntry.getCompanyId(),
					dlFileEntry.getDataRepositoryId(), dlFileEntry.getName(),
					latestDLFileVersion.getVersion());

				String latestChecksum = DigesterUtil.digestBase64(
					latestInputStream);

				if (lastChecksum.equals(latestChecksum)) {
					return true;
				}

				latestDLFileVersion.setChecksum(latestChecksum);

				dlFileVersionPersistence.update(latestDLFileVersion);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(e, e);
				}
			}
			finally {
				StreamUtil.cleanUp(lastInputStream);
				StreamUtil.cleanUp(latestInputStream);
			}
		}

		return false;
	}

	protected DLFileEntry moveFileEntryImpl(
			long userId, long fileEntryId, long newFolderId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// File entry

		User user = userPersistence.findByPrimaryKey(userId);
		DLFileEntry dlFileEntry = dlFileEntryPersistence.findByPrimaryKey(
			fileEntryId);

		long oldDataRepositoryId = dlFileEntry.getDataRepositoryId();

		validateFile(
			dlFileEntry.getGroupId(), newFolderId, dlFileEntry.getFileEntryId(),
			dlFileEntry.getTitle(), dlFileEntry.getExtension());

		if (DLStoreUtil.hasFile(
				user.getCompanyId(),
				DLFolderConstants.getDataRepositoryId(
					dlFileEntry.getGroupId(), newFolderId),
				dlFileEntry.getName(), StringPool.BLANK)) {

			throw new DuplicateFileException(dlFileEntry.getName());
		}

		dlFileEntry.setModifiedDate(serviceContext.getModifiedDate(null));
		dlFileEntry.setFolderId(newFolderId);

		dlFileEntryPersistence.update(dlFileEntry);

		// File version

		List<DLFileVersion> dlFileVersions =
			dlFileVersionPersistence.findByFileEntryId(fileEntryId);

		for (DLFileVersion dlFileVersion : dlFileVersions) {
			dlFileVersion.setFolderId(newFolderId);

			dlFileVersionPersistence.update(dlFileVersion);
		}

		// Folder

		if (newFolderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			DLFolder dlFolder = dlFolderPersistence.findByPrimaryKey(
				newFolderId);

			dlFolder.setModifiedDate(serviceContext.getModifiedDate(null));

			dlFolderPersistence.update(dlFolder);
		}

		// File

		DLStoreUtil.updateFile(
			user.getCompanyId(), oldDataRepositoryId,
			dlFileEntry.getDataRepositoryId(), dlFileEntry.getName());

		return dlFileEntry;
	}

	protected void reindex(DLFileEntry dlFileEntry) throws SearchException {
		Indexer indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			DLFileEntry.class);

		indexer.reindex(dlFileEntry);
	}

	protected void removeFileVersion(
			DLFileEntry dlFileEntry, DLFileVersion dlFileVersion)
		throws PortalException, SystemException {

		dlFileVersionPersistence.remove(dlFileVersion);

		expandoValueLocalService.deleteValues(
			DLFileEntry.class.getName(), dlFileVersion.getFileVersionId());

		dlFileEntryMetadataLocalService.deleteFileVersionFileEntryMetadata(
			dlFileVersion.getFileVersionId());

		try {
			DLStoreUtil.deleteFile(
				dlFileEntry.getCompanyId(), dlFileEntry.getDataRepositoryId(),
				dlFileEntry.getName(),
				DLFileEntryConstants.PRIVATE_WORKING_COPY_VERSION);
		}
		catch (NoSuchModelException nsme) {
		}

		unlockFileEntry(dlFileEntry.getFileEntryId());
	}

	protected void startWorkflowInstance(
			long userId, ServiceContext serviceContext,
			DLFileVersion dlFileVersion, String syncEventType)
		throws PortalException, SystemException {

		Map<String, Serializable> workflowContext =
			new HashMap<String, Serializable>();

		workflowContext.put("event", syncEventType);

		WorkflowHandlerRegistryUtil.startWorkflowInstance(
			dlFileVersion.getCompanyId(), dlFileVersion.getGroupId(), userId,
			DLFileEntry.class.getName(), dlFileVersion.getFileVersionId(),
			dlFileVersion, serviceContext, workflowContext);
	}

	protected DLFileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName,
			String extension, String mimeType, String title, String description,
			String changeLog, boolean majorVersion, String extraSettings,
			long fileEntryTypeId, Map<String, Fields> fieldsMap, File file,
			InputStream is, long size, ServiceContext serviceContext)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);
		DLFileEntry dlFileEntry = dlFileEntryPersistence.findByPrimaryKey(
			fileEntryId);

		boolean checkedOut = dlFileEntry.isCheckedOut();

		DLFileVersion dlFileVersion =
			dlFileVersionLocalService.getLatestFileVersion(
				fileEntryId, !checkedOut);

		boolean autoCheckIn = !checkedOut && dlFileVersion.isApproved();

		if (autoCheckIn) {
			dlFileEntry = checkOutFileEntry(
				userId, fileEntryId, serviceContext);
		}
		else if (!checkedOut) {
			lockFileEntry(userId, fileEntryId);
		}

		if (!hasFileEntryLock(userId, fileEntryId)) {
			lockFileEntry(userId, fileEntryId);
		}

		if (checkedOut || autoCheckIn) {
			dlFileVersion = dlFileVersionLocalService.getLatestFileVersion(
				fileEntryId, false);
		}

		try {
			if (Validator.isNull(extension)) {
				extension = dlFileEntry.getExtension();
			}

			if (Validator.isNull(mimeType)) {
				mimeType = dlFileEntry.getMimeType();
			}

			if (Validator.isNull(title)) {
				title = sourceFileName;

				if (Validator.isNull(title)) {
					title = dlFileEntry.getTitle();
				}
			}

			Date now = new Date();

			validateFile(
				dlFileEntry.getGroupId(), dlFileEntry.getFolderId(),
				dlFileEntry.getFileEntryId(), title, extension, sourceFileName,
				file, is);

			// File version

			String version = dlFileVersion.getVersion();

			if (size == 0) {
				size = dlFileVersion.getSize();
			}

			updateFileVersion(
				user, dlFileVersion, sourceFileName, extension, mimeType, title,
				description, changeLog, extraSettings, fileEntryTypeId,
				fieldsMap, version, size, dlFileVersion.getStatus(),
				serviceContext.getModifiedDate(now), serviceContext);

			// Folder

			if (dlFileEntry.getFolderId() !=
					DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

				DLFolder dlFolder = dlFolderPersistence.findByPrimaryKey(
					dlFileEntry.getFolderId());

				dlFolder.setLastPostDate(serviceContext.getModifiedDate(now));

				dlFolderPersistence.update(dlFolder);
			}

			// App helper

			dlAppHelperLocalService.updateAsset(
				userId, new LiferayFileEntry(dlFileEntry),
				new LiferayFileVersion(dlFileVersion),
				serviceContext.getAssetCategoryIds(),
				serviceContext.getAssetTagNames(),
				serviceContext.getAssetLinkEntryIds());

			// File

			if ((file != null) || (is != null)) {
				try {
					DLStoreUtil.deleteFile(
						user.getCompanyId(), dlFileEntry.getDataRepositoryId(),
						dlFileEntry.getName(), version);
				}
				catch (NoSuchModelException nsme) {
				}

				if (file != null) {
					DLStoreUtil.updateFile(
						user.getCompanyId(), dlFileEntry.getDataRepositoryId(),
						dlFileEntry.getName(), dlFileEntry.getExtension(),
						false, version, sourceFileName, file);
				}
				else {
					DLStoreUtil.updateFile(
						user.getCompanyId(), dlFileEntry.getDataRepositoryId(),
						dlFileEntry.getName(), dlFileEntry.getExtension(),
						false, version, sourceFileName, is);
				}
			}

			if (autoCheckIn) {
				dlFileEntryService.checkInFileEntry(
					fileEntryId, majorVersion, changeLog, serviceContext);
			}
			else if (!checkedOut &&
					 (serviceContext.getWorkflowAction() ==
						WorkflowConstants.ACTION_PUBLISH)) {

				String syncEvent = DLSyncConstants.EVENT_UPDATE;

				if (dlFileVersion.getVersion().equals(
						DLFileEntryConstants.VERSION_DEFAULT)) {

					syncEvent = DLSyncConstants.EVENT_ADD;
				}

				startWorkflowInstance(
					userId, serviceContext, dlFileVersion, syncEvent);
			}
		}
		catch (PortalException pe) {
			if (autoCheckIn) {
				dlFileEntryService.cancelCheckOut(fileEntryId);
			}

			throw pe;
		}
		catch (SystemException se) {
			if (autoCheckIn) {
				dlFileEntryService.cancelCheckOut(fileEntryId);
			}

			throw se;
		}
		finally {
			if (!autoCheckIn && !checkedOut) {
				unlockFileEntry(fileEntryId);
			}
		}

		return dlFileEntryPersistence.findByPrimaryKey(fileEntryId);
	}

	protected DLFileVersion updateFileVersion(
			User user, DLFileVersion dlFileVersion, String sourceFileName,
			String extension, String mimeType, String title, String description,
			String changeLog, String extraSettings, long fileEntryTypeId,
			Map<String, Fields> fieldsMap, String version, long size,
			int status, Date statusDate, ServiceContext serviceContext)
		throws PortalException, SystemException {

		dlFileVersion.setModifiedDate(statusDate);

		if (Validator.isNotNull(sourceFileName)) {
			dlFileVersion.setExtension(extension);
			dlFileVersion.setMimeType(mimeType);
		}

		dlFileVersion.setTitle(title);
		dlFileVersion.setDescription(description);
		dlFileVersion.setChangeLog(changeLog);
		dlFileVersion.setExtraSettings(extraSettings);
		dlFileVersion.setFileEntryTypeId(fileEntryTypeId);
		dlFileVersion.setVersion(version);
		dlFileVersion.setSize(size);
		dlFileVersion.setStatus(status);
		dlFileVersion.setStatusByUserId(user.getUserId());
		dlFileVersion.setStatusByUserName(user.getFullName());
		dlFileVersion.setStatusDate(statusDate);
		dlFileVersion.setExpandoBridgeAttributes(serviceContext);

		dlFileVersion = dlFileVersionPersistence.update(dlFileVersion);

		if ((fileEntryTypeId > 0) && (fieldsMap != null)) {
			dlFileEntryMetadataLocalService.updateFileEntryMetadata(
				fileEntryTypeId, dlFileVersion.getFileEntryId(),
				dlFileVersion.getFileVersionId(), fieldsMap, serviceContext);
		}

		return dlFileVersion;
	}

	protected void validateFile(
			long groupId, long folderId, long fileEntryId, String title,
			String extension)
		throws PortalException, SystemException {

		DLFolder dlFolder = dlFolderPersistence.fetchByG_P_N(
			groupId, folderId, title);

		if (dlFolder != null) {
			throw new DuplicateFolderNameException(title);
		}

		DLFileEntry dlFileEntry = dlFileEntryPersistence.fetchByG_F_T(
			groupId, folderId, title);

		if ((dlFileEntry != null) &&
			(dlFileEntry.getFileEntryId() != fileEntryId)) {

			throw new DuplicateFileException(title);
		}

		String periodAndExtension = StringPool.PERIOD + extension;

		if (!title.endsWith(periodAndExtension)) {
			title += periodAndExtension;

			dlFileEntry = dlFileEntryPersistence.fetchByG_F_T(
				groupId, folderId, title);

			if ((dlFileEntry != null) &&
				(dlFileEntry.getFileEntryId() != fileEntryId)) {

				throw new DuplicateFileException(title);
			}
		}
	}

	protected void validateFile(
			long groupId, long folderId, long fileEntryId, String title,
			String extension, String sourceFileName, File file, InputStream is)
		throws PortalException, SystemException {

		if (Validator.isNotNull(sourceFileName)) {
			if (file != null) {
				DLStoreUtil.validate(
					sourceFileName, extension, sourceFileName, true, file);
			}
			else {
				DLStoreUtil.validate(
					sourceFileName, extension, sourceFileName, true, is);
			}
		}

		validateFileExtension(extension);
		validateFileName(title);

		DLStoreUtil.validate(title, false);

		validateFile(groupId, folderId, fileEntryId, title, extension);
	}

	protected void validateFileEntryTypeId(
			long[] groupIds, long folderId, long fileEntryTypeId)
		throws PortalException, SystemException {

		List<DLFileEntryType> dlFileEntryTypes =
			dlFileEntryTypeLocalService.getFolderFileEntryTypes(
				groupIds, folderId, true);

		for (DLFileEntryType dlFileEntryType : dlFileEntryTypes) {
			if (dlFileEntryType.getFileEntryTypeId() == fileEntryTypeId) {
				return;
			}
		}

		throw new InvalidFileEntryTypeException(
			"Invalid file entry type " + fileEntryTypeId + " for folder " +
				folderId);
	}

	protected void validateFileExtension(String extension)
		throws PortalException {

		if (Validator.isNotNull(extension)) {
			int maxLength = ModelHintsUtil.getMaxLength(
				DLFileEntry.class.getName(), "extension");

			if (extension.length() > maxLength) {
				throw new FileExtensionException();
			}
		}
	}

	protected void validateFileName(String fileName) throws PortalException {
		if (fileName.contains(StringPool.SLASH)) {
			throw new FileNameException(fileName);
		}
	}

	private static final int _DELETE_INTERVAL = 100;

	private static Log _log = LogFactoryUtil.getLog(
		DLFileEntryLocalServiceImpl.class);

}