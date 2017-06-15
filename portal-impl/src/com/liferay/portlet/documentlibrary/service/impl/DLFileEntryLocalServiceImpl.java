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

package com.liferay.portlet.documentlibrary.service.impl;

import com.liferay.document.library.kernel.exception.DuplicateFileEntryException;
import com.liferay.document.library.kernel.exception.DuplicateFolderNameException;
import com.liferay.document.library.kernel.exception.FileExtensionException;
import com.liferay.document.library.kernel.exception.FileNameException;
import com.liferay.document.library.kernel.exception.ImageSizeException;
import com.liferay.document.library.kernel.exception.InvalidFileEntryTypeException;
import com.liferay.document.library.kernel.exception.InvalidFileVersionException;
import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.exception.NoSuchFolderException;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.store.DLStoreUtil;
import com.liferay.document.library.kernel.util.DL;
import com.liferay.document.library.kernel.util.DLFileVersionPolicy;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.document.library.kernel.util.DLValidatorUtil;
import com.liferay.document.library.kernel.util.comparator.RepositoryModelModifiedDateComparator;
import com.liferay.dynamic.data.mapping.kernel.DDMFormValues;
import com.liferay.dynamic.data.mapping.kernel.DDMStructure;
import com.liferay.dynamic.data.mapping.kernel.DDMStructureManagerUtil;
import com.liferay.dynamic.data.mapping.kernel.StorageEngineManagerUtil;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.model.ExpandoRow;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.util.ExpandoBridgeUtil;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.comment.CommentManagerUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.image.ImageBag;
import com.liferay.portal.kernel.image.ImageToolUtil;
import com.liferay.portal.kernel.increment.BufferedIncrement;
import com.liferay.portal.kernel.increment.NumberIncrement;
import com.liferay.portal.kernel.interval.IntervalActionProcessor;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.lock.ExpiredLockException;
import com.liferay.portal.kernel.lock.InvalidLockException;
import com.liferay.portal.kernel.lock.Lock;
import com.liferay.portal.kernel.lock.LockManagerUtil;
import com.liferay.portal.kernel.lock.NoSuchLockException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Image;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.event.RepositoryEventTrigger;
import com.liferay.portal.kernel.repository.event.RepositoryEventType;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.DateRange;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileVersion;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.RepositoryUtil;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryImpl;
import com.liferay.portlet.documentlibrary.service.base.DLFileEntryLocalServiceBaseImpl;
import com.liferay.portlet.documentlibrary.util.DLAppUtil;

import java.awt.image.RenderedImage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
			Map<String, DDMFormValues> ddmFormValuesMap, File file,
			InputStream is, long size, ServiceContext serviceContext)
		throws PortalException {

		if (Validator.isNull(title)) {
			throw new FileNameException("Title is null");
		}

		// File entry

		User user = userPersistence.findByPrimaryKey(userId);

		folderId = dlFolderLocalService.getFolderId(
			user.getCompanyId(), folderId);

		String name = String.valueOf(
			counterLocalService.increment(DLFileEntry.class.getName()));
		String extension = DLAppUtil.getExtension(title, sourceFileName);

		String fileName = DLUtil.getSanitizedFileName(title, extension);

		if (fileEntryTypeId == -1) {
			fileEntryTypeId =
				dlFileEntryTypeLocalService.getDefaultFileEntryTypeId(folderId);
		}

		validateFileEntryTypeId(
			PortalUtil.getCurrentAndAncestorSiteGroupIds(groupId), folderId,
			fileEntryTypeId);

		validateFile(
			groupId, folderId, 0, sourceFileName, fileName, extension, title);

		long fileEntryId = counterLocalService.increment();

		DLFileEntry dlFileEntry = dlFileEntryPersistence.create(fileEntryId);

		dlFileEntry.setUuid(serviceContext.getUuid());
		dlFileEntry.setGroupId(groupId);
		dlFileEntry.setCompanyId(user.getCompanyId());
		dlFileEntry.setUserId(user.getUserId());
		dlFileEntry.setUserName(user.getFullName());

		DLFolder repositoryDLFolder = null;

		if (repositoryId != groupId) {
			Repository repository = repositoryLocalService.getRepository(
				repositoryId);

			repositoryDLFolder = dlFolderPersistence.findByPrimaryKey(
				repository.getDlFolderId());
		}

		long classNameId = 0;
		long classPK = 0;

		if ((repositoryDLFolder != null) && repositoryDLFolder.isHidden()) {
			classNameId = classNameLocalService.getClassNameId(
				(String)serviceContext.getAttribute("className"));
			classPK = ParamUtil.getLong(serviceContext, "classPK");
		}

		dlFileEntry.setClassNameId(classNameId);
		dlFileEntry.setClassPK(classPK);
		dlFileEntry.setRepositoryId(repositoryId);
		dlFileEntry.setFolderId(folderId);
		dlFileEntry.setTreePath(dlFileEntry.buildTreePath());
		dlFileEntry.setName(name);
		dlFileEntry.setFileName(fileName);
		dlFileEntry.setExtension(extension);
		dlFileEntry.setMimeType(mimeType);
		dlFileEntry.setTitle(title);
		dlFileEntry.setDescription(description);
		dlFileEntry.setFileEntryTypeId(fileEntryTypeId);
		dlFileEntry.setVersion(DLFileEntryConstants.VERSION_DEFAULT);
		dlFileEntry.setSize(size);
		dlFileEntry.setReadCount(DLFileEntryConstants.DEFAULT_READ_COUNT);

		dlFileEntryPersistence.update(dlFileEntry);

		// Resources

		addFileEntryResources(dlFileEntry, serviceContext);

		// File version

		addFileVersion(
			user, dlFileEntry, fileName, extension, mimeType, title,
			description, changeLog, StringPool.BLANK, fileEntryTypeId,
			ddmFormValuesMap, DLFileEntryConstants.VERSION_DEFAULT, size,
			WorkflowConstants.STATUS_DRAFT, serviceContext);

		// Folder

		if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			dlFolderLocalService.updateLastPostDate(
				dlFileEntry.getFolderId(), dlFileEntry.getModifiedDate());
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
		throws PortalException {

		if (!isFileEntryCheckedOut(fileEntryId)) {
			return null;
		}

		DLFileEntry dlFileEntry = dlFileEntryPersistence.findByPrimaryKey(
			fileEntryId);

		DLFileVersion dlFileVersion =
			dlFileVersionLocalService.getLatestFileVersion(fileEntryId, false);

		removeFileVersion(dlFileEntry, dlFileVersion);

		return dlFileVersion;
	}

	@Override
	public void checkInFileEntry(
			long userId, long fileEntryId, boolean majorVersion,
			String changeLog, ServiceContext serviceContext)
		throws PortalException {

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

		if (dlFileVersionPolicy.isKeepFileVersionLabel(
				lastDLFileVersion, latestDLFileVersion, majorVersion,
				serviceContext)) {

			if (lastDLFileVersion.getSize() != latestDLFileVersion.getSize()) {

				// File entry

				dlFileEntry.setExtension(latestDLFileVersion.getExtension());
				dlFileEntry.setMimeType(latestDLFileVersion.getMimeType());
				dlFileEntry.setModifiedDate(
					latestDLFileVersion.getModifiedDate());
				dlFileEntry.setSize(latestDLFileVersion.getSize());

				dlFileEntryPersistence.update(dlFileEntry);

				// File version

				lastDLFileVersion.setExtension(
					latestDLFileVersion.getExtension());
				lastDLFileVersion.setMimeType(
					latestDLFileVersion.getMimeType());
				lastDLFileVersion.setSize(latestDLFileVersion.getSize());

				dlFileVersionPersistence.update(lastDLFileVersion);

				// File

				DLStoreUtil.deleteFile(
					user.getCompanyId(), dlFileEntry.getDataRepositoryId(),
					dlFileEntry.getName(), lastDLFileVersion.getVersion());

				DLStoreUtil.copyFileVersion(
					user.getCompanyId(), dlFileEntry.getDataRepositoryId(),
					dlFileEntry.getName(),
					DLFileEntryConstants.PRIVATE_WORKING_COPY_VERSION,
					lastDLFileVersion.getVersion());
			}

			// Latest file version

			removeFileVersion(dlFileEntry, latestDLFileVersion);
		}
		else {

			// File version

			String version = getNextVersion(
				dlFileEntry, majorVersion, serviceContext.getWorkflowAction());

			latestDLFileVersion.setVersion(version);

			latestDLFileVersion.setChangeLog(changeLog);

			dlFileVersionPersistence.update(latestDLFileVersion);

			// Folder

			if (dlFileEntry.getFolderId() !=
					DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

				dlFolderLocalService.updateLastPostDate(
					dlFileEntry.getFolderId(),
					latestDLFileVersion.getModifiedDate());
			}

			// File

			DLStoreUtil.updateFileVersion(
				user.getCompanyId(), dlFileEntry.getDataRepositoryId(),
				dlFileEntry.getName(),
				DLFileEntryConstants.PRIVATE_WORKING_COPY_VERSION, version);
		}

		unlockFileEntry(fileEntryId);
	}

	@Override
	public void checkInFileEntry(
			long userId, long fileEntryId, String lockUuid,
			ServiceContext serviceContext)
		throws PortalException {

		if (Validator.isNotNull(lockUuid)) {
			try {
				Lock lock = LockManagerUtil.getLock(
					DLFileEntry.class.getName(), fileEntryId);

				if (!Objects.equals(lock.getUuid(), lockUuid)) {
					throw new InvalidLockException("UUIDs do not match");
				}
			}
			catch (PortalException pe) {
				if (pe instanceof ExpiredLockException ||
					pe instanceof NoSuchLockException) {
				}
				else {
					throw pe;
				}
			}
		}

		checkInFileEntry(
			userId, fileEntryId, false, StringPool.BLANK, serviceContext);
	}

	@Override
	public DLFileEntry checkOutFileEntry(
			long userId, long fileEntryId, long fileEntryTypeId,
			ServiceContext serviceContext)
		throws PortalException {

		return checkOutFileEntry(
			userId, fileEntryId, fileEntryTypeId, StringPool.BLANK,
			DLFileEntryImpl.LOCK_EXPIRATION_TIME, serviceContext);
	}

	@Override
	public DLFileEntry checkOutFileEntry(
			long userId, long fileEntryId, long fileEntryTypeId, String owner,
			long expirationTime, ServiceContext serviceContext)
		throws PortalException {

		DLFileEntry dlFileEntry = dlFileEntryPersistence.findByPrimaryKey(
			fileEntryId);

		boolean hasLock = hasFileEntryLock(userId, fileEntryId);

		if (!hasLock) {
			if ((expirationTime <= 0) ||
				(expirationTime > DLFileEntryImpl.LOCK_EXPIRATION_TIME)) {

				expirationTime = DLFileEntryImpl.LOCK_EXPIRATION_TIME;
			}

			LockManagerUtil.lock(
				userId, DLFileEntry.class.getName(), fileEntryId, owner, false,
				expirationTime);
		}

		User user = userPersistence.findByPrimaryKey(userId);

		serviceContext.setCompanyId(user.getCompanyId());

		DLFileVersion dlFileVersion =
			dlFileVersionLocalService.getLatestFileVersion(fileEntryId, false);

		DLFileVersion oldDLFileVersion = dlFileVersion;

		long oldDLFileVersionId = dlFileVersion.getFileVersionId();

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
					existingDLFileVersion.getFileName(),
					existingDLFileVersion.getExtension(),
					existingDLFileVersion.getMimeType(),
					existingDLFileVersion.getTitle(),
					existingDLFileVersion.getDescription(),
					existingDLFileVersion.getChangeLog(),
					existingDLFileVersion.getExtraSettings(),
					existingDLFileVersion.getFileEntryTypeId(), null,
					DLFileEntryConstants.PRIVATE_WORKING_COPY_VERSION,
					existingDLFileVersion.getSize(),
					WorkflowConstants.STATUS_DRAFT,
					serviceContext.getModifiedDate(null), serviceContext);
			}
			else {
				dlFileVersion = addFileVersion(
					user, dlFileEntry, oldDLFileVersion.getFileName(),
					oldDLFileVersion.getExtension(),
					oldDLFileVersion.getMimeType(), oldDLFileVersion.getTitle(),
					oldDLFileVersion.getDescription(),
					oldDLFileVersion.getChangeLog(),
					oldDLFileVersion.getExtraSettings(),
					oldDLFileVersion.getFileEntryTypeId(), null,
					DLFileEntryConstants.PRIVATE_WORKING_COPY_VERSION,
					oldDLFileVersion.getSize(), WorkflowConstants.STATUS_DRAFT,
					serviceContext);

				copyExpandoRowModifiedDate(
					dlFileEntry.getCompanyId(), oldDLFileVersionId,
					dlFileVersion.getFileVersionId());
			}

			if (DLStoreUtil.hasFile(
					dlFileEntry.getCompanyId(),
					dlFileEntry.getDataRepositoryId(), dlFileEntry.getName(),
					DLFileEntryConstants.PRIVATE_WORKING_COPY_VERSION)) {

				DLStoreUtil.deleteFile(
					dlFileEntry.getCompanyId(),
					dlFileEntry.getDataRepositoryId(), dlFileEntry.getName(),
					DLFileEntryConstants.PRIVATE_WORKING_COPY_VERSION);
			}

			DLStoreUtil.copyFileVersion(
				user.getCompanyId(), dlFileEntry.getDataRepositoryId(),
				dlFileEntry.getName(), version,
				DLFileEntryConstants.PRIVATE_WORKING_COPY_VERSION);

			serviceContext.setAttribute("validateDDMFormValues", Boolean.FALSE);

			if (fileEntryTypeId == oldDLFileVersion.getFileEntryTypeId()) {
				copyFileEntryMetadata(
					dlFileEntry.getCompanyId(), fileEntryTypeId, fileEntryId,
					oldDLFileVersionId, dlFileVersion.getFileVersionId(),
					serviceContext);
			}

			serviceContext.setAttribute("validateDDMFormValues", Boolean.TRUE);
		}

		return dlFileEntry;
	}

	@Override
	public DLFileEntry checkOutFileEntry(
			long userId, long fileEntryId, ServiceContext serviceContext)
		throws PortalException {

		return checkOutFileEntry(
			userId, fileEntryId, StringPool.BLANK,
			DLFileEntryImpl.LOCK_EXPIRATION_TIME, serviceContext);
	}

	@Override
	public DLFileEntry checkOutFileEntry(
			long userId, long fileEntryId, String owner, long expirationTime,
			ServiceContext serviceContext)
		throws PortalException {

		DLFileEntry dlFileEntry = dlFileEntryPersistence.findByPrimaryKey(
			fileEntryId);

		return checkOutFileEntry(
			userId, fileEntryId, dlFileEntry.getFileEntryTypeId(), owner,
			expirationTime, serviceContext);
	}

	@Override
	public void convertExtraSettings(final String[] keys)
		throws PortalException {

		int total = dlFileEntryFinder.countByExtraSettings();

		final IntervalActionProcessor<Void> intervalActionProcessor =
			new IntervalActionProcessor<>(total);

		intervalActionProcessor.setPerformIntervalActionMethod(
			new IntervalActionProcessor.PerformIntervalActionMethod<Void>() {

				@Override
				public Void performIntervalAction(int start, int end)
					throws PortalException {

					List<DLFileEntry> dlFileEntries =
						dlFileEntryFinder.findByExtraSettings(start, end);

					for (DLFileEntry dlFileEntry : dlFileEntries) {
						convertExtraSettings(dlFileEntry, keys);
					}

					intervalActionProcessor.incrementStart(
						dlFileEntries.size());

					return null;
				}

			});

		intervalActionProcessor.performIntervalActions();
	}

	@Override
	public DLFileEntry copyFileEntry(
			long userId, long groupId, long repositoryId, long fileEntryId,
			long destFolderId, ServiceContext serviceContext)
		throws PortalException {

		DLFileEntry dlFileEntry = getFileEntry(fileEntryId);

		String sourceFileName = "A";

		String extension = dlFileEntry.getExtension();

		if (Validator.isNotNull(extension)) {
			sourceFileName = sourceFileName.concat(StringPool.PERIOD).concat(
				extension);
		}

		InputStream inputStream = DLStoreUtil.getFileAsStream(
			dlFileEntry.getCompanyId(), dlFileEntry.getDataRepositoryId(),
			dlFileEntry.getName());

		DLFileEntry newDLFileEntry = addFileEntry(
			userId, groupId, repositoryId, destFolderId, sourceFileName,
			dlFileEntry.getMimeType(), dlFileEntry.getTitle(),
			dlFileEntry.getDescription(), null,
			dlFileEntry.getFileEntryTypeId(), null, null, inputStream,
			dlFileEntry.getSize(), serviceContext);

		DLFileVersion dlFileVersion = dlFileEntry.getFileVersion();

		DLFileVersion newDLFileVersion = newDLFileEntry.getFileVersion();

		ExpandoBridgeUtil.copyExpandoBridgeAttributes(
			dlFileVersion.getExpandoBridge(),
			newDLFileVersion.getExpandoBridge());

		copyFileEntryMetadata(
			dlFileVersion.getCompanyId(), dlFileVersion.getFileEntryTypeId(),
			fileEntryId, dlFileVersion.getFileVersionId(),
			newDLFileVersion.getFileVersionId(), serviceContext);

		return newDLFileEntry;
	}

	@Override
	public void copyFileEntryMetadata(
			long companyId, long fileEntryTypeId, long fileEntryId,
			long fromFileVersionId, long toFileVersionId,
			ServiceContext serviceContext)
		throws PortalException {

		Map<String, DDMFormValues> ddmFormValuesMap = new HashMap<>();

		List<DDMStructure> ddmStructures = null;

		if (fileEntryTypeId > 0) {
			DLFileEntryType dlFileEntryType =
				dlFileEntryTypeLocalService.getFileEntryType(fileEntryTypeId);

			ddmStructures = dlFileEntryType.getDDMStructures();
		}
		else {
			long classNameId = classNameLocalService.getClassNameId(
				DLFileEntryMetadata.class);

			ddmStructures = DDMStructureManagerUtil.getClassStructures(
				companyId, classNameId);
		}

		copyFileEntryMetadata(
			companyId, fileEntryId, fromFileVersionId, toFileVersionId,
			serviceContext, ddmFormValuesMap, ddmStructures);
	}

	@Override
	public void deleteFileEntries(long groupId, long folderId)
		throws PortalException {

		deleteFileEntries(groupId, folderId, true);
	}

	@Override
	public void deleteFileEntries(
			long groupId, final long folderId,
			final boolean includeTrashedEntries)
		throws PortalException {

		final RepositoryEventTrigger repositoryEventTrigger =
			getFolderRepositoryEventTrigger(groupId, folderId);

		ActionableDynamicQuery actionableDynamicQuery =
			dlFileEntryLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					Property folderIdproperty = PropertyFactoryUtil.forName(
						"folderId");

					dynamicQuery.add(folderIdproperty.eq(folderId));
				}

			});
		actionableDynamicQuery.setGroupId(groupId);
		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<DLFileEntry>() {

				@Override
				public void performAction(DLFileEntry dlFileEntry)
					throws PortalException {

					if (includeTrashedEntries ||
						!dlFileEntry.isInTrashExplicitly()) {

						repositoryEventTrigger.trigger(
							RepositoryEventType.Delete.class, FileEntry.class,
							new LiferayFileEntry(dlFileEntry));

						dlFileEntryLocalService.deleteFileEntry(dlFileEntry);
					}
				}

			});

		actionableDynamicQuery.performActions();
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(
		action = SystemEventConstants.ACTION_SKIP,
		type = SystemEventConstants.TYPE_DELETE
	)
	public DLFileEntry deleteFileEntry(DLFileEntry dlFileEntry)
		throws PortalException {

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

			expandoRowLocalService.deleteRows(dlFileVersion.getFileVersionId());

			workflowInstanceLinkLocalService.deleteWorkflowInstanceLinks(
				dlFileEntry.getCompanyId(), dlFileEntry.getGroupId(),
				DLFileEntry.class.getName(), dlFileVersion.getFileVersionId());
		}

		// Expando

		expandoRowLocalService.deleteRows(dlFileEntry.getFileEntryId());

		// Ratings

		ratingsStatsLocalService.deleteStats(
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

	@Indexable(type = IndexableType.DELETE)
	@Override
	public DLFileEntry deleteFileEntry(long fileEntryId)
		throws PortalException {

		DLFileEntry dlFileEntry = getFileEntry(fileEntryId);

		return dlFileEntryLocalService.deleteFileEntry(dlFileEntry);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	public DLFileEntry deleteFileEntry(long userId, long fileEntryId)
		throws PortalException {

		if (!hasFileEntryLock(userId, fileEntryId)) {
			lockFileEntry(userId, fileEntryId);
		}

		try {
			return dlFileEntryLocalService.deleteFileEntry(fileEntryId);
		}
		finally {
			unlockFileEntry(fileEntryId);
		}
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public DLFileEntry deleteFileVersion(
			long userId, long fileEntryId, String version)
		throws PortalException {

		if (Validator.isNull(version)) {
			throw new InvalidFileVersionException("Version is null");
		}

		if (version.equals(DLFileEntryConstants.PRIVATE_WORKING_COPY_VERSION)) {
			throw new InvalidFileVersionException(
				"Unable to delete a private working copy file version");
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

			expandoRowLocalService.deleteRows(dlFileVersion.getFileVersionId());

			dlFileEntryMetadataLocalService.deleteFileVersionFileEntryMetadata(
				dlFileVersion.getFileVersionId());

			workflowInstanceLinkLocalService.deleteWorkflowInstanceLinks(
				dlFileVersion.getCompanyId(), dlFileVersion.getGroupId(),
				DLFileEntryConstants.getClassName(),
				dlFileVersion.getFileVersionId());

			dlFileEntry = dlFileEntryPersistence.findByPrimaryKey(fileEntryId);

			latestVersion = version.equals(dlFileEntry.getVersion());

			if (latestVersion) {
				DLFileVersion dlLatestFileVersion =
					dlFileVersionLocalService.fetchLatestFileVersion(
						dlFileEntry.getFileEntryId(), true);

				if (dlLatestFileVersion != null) {
					long fileEntryTypeId = getValidFileEntryTypeId(
						dlLatestFileVersion.getFileEntryTypeId(), dlFileEntry);

					dlLatestFileVersion.setModifiedDate(new Date());
					dlLatestFileVersion.setFileEntryTypeId(fileEntryTypeId);
					dlLatestFileVersion.setStatusDate(new Date());

					dlFileVersionPersistence.update(dlLatestFileVersion);

					dlFileEntry.setModifiedDate(new Date());
					dlFileEntry.setFileName(dlLatestFileVersion.getFileName());
					dlFileEntry.setExtension(
						dlLatestFileVersion.getExtension());
					dlFileEntry.setMimeType(dlLatestFileVersion.getMimeType());
					dlFileEntry.setTitle(dlLatestFileVersion.getTitle());
					dlFileEntry.setDescription(
						dlLatestFileVersion.getDescription());
					dlFileEntry.setExtraSettings(
						dlLatestFileVersion.getExtraSettings());
					dlFileEntry.setFileEntryTypeId(fileEntryTypeId);
					dlFileEntry.setVersion(dlLatestFileVersion.getVersion());
					dlFileEntry.setSize(dlLatestFileVersion.getSize());

					dlFileEntry = dlFileEntryPersistence.update(dlFileEntry);
				}
			}

			DLStoreUtil.deleteFile(
				dlFileEntry.getCompanyId(), dlFileEntry.getDataRepositoryId(),
				dlFileEntry.getName(), version);
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
	public void deleteRepositoryFileEntries(long repositoryId, long folderId)
		throws PortalException {

		deleteRepositoryFileEntries(repositoryId, folderId, true);
	}

	@Override
	public void deleteRepositoryFileEntries(
			final long repositoryId, final long folderId,
			final boolean includeTrashedEntries)
		throws PortalException {

		final RepositoryEventTrigger repositoryEventTrigger =
			RepositoryUtil.getRepositoryEventTrigger(repositoryId);

		int total = dlFileEntryPersistence.countByR_F(repositoryId, folderId);

		final IntervalActionProcessor<Void> intervalActionProcessor =
			new IntervalActionProcessor<>(total);

		intervalActionProcessor.setPerformIntervalActionMethod(
			new IntervalActionProcessor.PerformIntervalActionMethod<Void>() {

				@Override
				public Void performIntervalAction(int start, int end)
					throws PortalException {

					List<DLFileEntry> dlFileEntries =
						dlFileEntryPersistence.findByR_F(
							repositoryId, folderId, start, end);

					for (DLFileEntry dlFileEntry : dlFileEntries) {
						if (includeTrashedEntries ||
							!dlFileEntry.isInTrashExplicitly()) {

							repositoryEventTrigger.trigger(
								RepositoryEventType.Delete.class,
								FileEntry.class,
								new LiferayFileEntry(dlFileEntry));

							dlFileEntryLocalService.deleteFileEntry(
								dlFileEntry);
						}
						else {
							intervalActionProcessor.incrementStart();
						}
					}

					return null;
				}

			});

		intervalActionProcessor.performIntervalActions();
	}

	@Override
	public DLFileEntry fetchFileEntry(
		long groupId, long folderId, String title) {

		return dlFileEntryPersistence.fetchByG_F_T(groupId, folderId, title);
	}

	@Override
	public DLFileEntry fetchFileEntry(String uuid, long groupId) {
		return dlFileEntryPersistence.fetchByUUID_G(uuid, groupId);
	}

	@Override
	public DLFileEntry fetchFileEntryByAnyImageId(long imageId) {
		return dlFileEntryFinder.fetchByAnyImageId(imageId);
	}

	@Override
	public DLFileEntry fetchFileEntryByFileName(
		long groupId, long folderId, String fileName) {

		return dlFileEntryPersistence.fetchByG_F_FN(
			groupId, folderId, fileName);
	}

	@Override
	public DLFileEntry fetchFileEntryByName(
		long groupId, long folderId, String name) {

		return dlFileEntryPersistence.fetchByG_F_N(groupId, folderId, name);
	}

	@Override
	public List<DLFileEntry> getDDMStructureFileEntries(
		long groupId, long[] ddmStructureIds) {

		return dlFileEntryFinder.findByDDMStructureIds(
			groupId, ddmStructureIds, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	@Override
	public List<DLFileEntry> getDDMStructureFileEntries(
		long[] ddmStructureIds) {

		return dlFileEntryFinder.findByDDMStructureIds(
			ddmStructureIds, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	@Override
	public List<DLFileEntry> getExtraSettingsFileEntries(int start, int end) {
		return dlFileEntryFinder.findByExtraSettings(start, end);
	}

	@Override
	public int getExtraSettingsFileEntriesCount() {
		return dlFileEntryFinder.countByExtraSettings();
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #getFile(long, String,
	 *             boolean)}
	 */
	@Deprecated
	@Override
	public File getFile(
			long userId, long fileEntryId, String version,
			boolean incrementCounter)
		throws PortalException {

		return getFile(fileEntryId, version, incrementCounter, 1);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #getFile(long, String,
	 *             boolean, int)}
	 */
	@Deprecated
	@Override
	public File getFile(
			long userId, long fileEntryId, String version,
			boolean incrementCounter, int increment)
		throws PortalException {

		return getFile(fileEntryId, version, incrementCounter, increment);
	}

	@Override
	public File getFile(
			long fileEntryId, String version, boolean incrementCounter)
		throws PortalException {

		return getFile(fileEntryId, version, incrementCounter, 1);
	}

	@Override
	public File getFile(
			long fileEntryId, String version, boolean incrementCounter,
			int increment)
		throws PortalException {

		DLFileEntry dlFileEntry = dlFileEntryPersistence.findByPrimaryKey(
			fileEntryId);

		if (incrementCounter) {
			dlFileEntryLocalService.incrementViewCounter(
				dlFileEntry, increment);
		}

		return DLStoreUtil.getFile(
			dlFileEntry.getCompanyId(), dlFileEntry.getDataRepositoryId(),
			dlFileEntry.getName(), version);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #getFileAsStream(long,
	 *             String)}
	 */
	@Deprecated
	@Override
	public InputStream getFileAsStream(
			long userId, long fileEntryId, String version)
		throws PortalException {

		return getFileAsStream(fileEntryId, version, true, 1);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #getFileAsStream(long,
	 *             String, boolean)}
	 */
	@Deprecated
	@Override
	public InputStream getFileAsStream(
			long userId, long fileEntryId, String version,
			boolean incrementCounter)
		throws PortalException {

		return getFileAsStream(fileEntryId, version, incrementCounter, 1);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #getFileAsStream(long,
	 *             String, boolean, int)}
	 */
	@Deprecated
	@Override
	public InputStream getFileAsStream(
			long userId, long fileEntryId, String version,
			boolean incrementCounter, int increment)
		throws PortalException {

		return getFileAsStream(
			fileEntryId, version, incrementCounter, increment);
	}

	@Override
	public InputStream getFileAsStream(long fileEntryId, String version)
		throws PortalException {

		return getFileAsStream(fileEntryId, version, true, 1);
	}

	@Override
	public InputStream getFileAsStream(
			long fileEntryId, String version, boolean incrementCounter)
		throws PortalException {

		return getFileAsStream(fileEntryId, version, incrementCounter, 1);
	}

	@Override
	public InputStream getFileAsStream(
			long fileEntryId, String version, boolean incrementCounter,
			int increment)
		throws PortalException {

		DLFileEntry dlFileEntry = dlFileEntryPersistence.findByPrimaryKey(
			fileEntryId);

		if (incrementCounter) {
			dlFileEntryLocalService.incrementViewCounter(
				dlFileEntry, increment);
		}

		return DLStoreUtil.getFileAsStream(
			dlFileEntry.getCompanyId(), dlFileEntry.getDataRepositoryId(),
			dlFileEntry.getName(), version);
	}

	@Override
	public List<DLFileEntry> getFileEntries(int start, int end) {
		return dlFileEntryPersistence.findAll(start, end);
	}

	@Override
	public List<DLFileEntry> getFileEntries(long groupId, long folderId) {
		return dlFileEntryPersistence.findByG_F(groupId, folderId);
	}

	@Override
	public List<DLFileEntry> getFileEntries(
		long groupId, long folderId, int status, int start, int end,
		OrderByComparator<DLFileEntry> obc) {

		List<Long> folderIds = new ArrayList<>();

		folderIds.add(folderId);

		QueryDefinition<DLFileEntry> queryDefinition = new QueryDefinition<>(
			status, false, start, end, obc);

		return dlFileEntryFinder.findByG_F(groupId, folderIds, queryDefinition);
	}

	@Override
	public List<DLFileEntry> getFileEntries(
		long groupId, long folderId, int start, int end,
		OrderByComparator<DLFileEntry> obc) {

		return dlFileEntryPersistence.findByG_F(
			groupId, folderId, start, end, obc);
	}

	@Override
	public List<DLFileEntry> getFileEntries(
			long groupId, long userId, List<Long> repositoryIds,
			List<Long> folderIds, String[] mimeTypes,
			QueryDefinition<DLFileEntry> queryDefinition)
		throws Exception {

		return dlFileEntryFinder.findByG_U_R_F_M(
			groupId, userId, repositoryIds, folderIds, mimeTypes,
			queryDefinition);
	}

	@Override
	public List<DLFileEntry> getFileEntries(
			long groupId, long userId, List<Long> folderIds, String[] mimeTypes,
			QueryDefinition<DLFileEntry> queryDefinition)
		throws Exception {

		return dlFileEntryFinder.findByG_U_F_M(
			groupId, userId, folderIds, mimeTypes, queryDefinition);
	}

	@Override
	public List<DLFileEntry> getFileEntries(long folderId, String name) {
		return dlFileEntryPersistence.findByF_N(folderId, name);
	}

	@Override
	public int getFileEntriesCount() {
		return dlFileEntryPersistence.countAll();
	}

	/**
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	@Override
	public int getFileEntriesCount(
		long groupId, DateRange dateRange, long repositoryId,
		QueryDefinition<DLFileEntry> queryDefinition) {

		return 0;
	}

	@Override
	public int getFileEntriesCount(long groupId, long folderId) {
		return dlFileEntryPersistence.countByG_F(groupId, folderId);
	}

	@Override
	public int getFileEntriesCount(long groupId, long folderId, int status) {
		List<Long> folderIds = new ArrayList<>();

		folderIds.add(folderId);

		return dlFileEntryFinder.countByG_F(
			groupId, folderIds, new QueryDefinition<DLFileEntry>(status));
	}

	@Override
	public int getFileEntriesCount(
			long groupId, long userId, List<Long> repositoryIds,
			List<Long> folderIds, String[] mimeTypes,
			QueryDefinition<DLFileEntry> queryDefinition)
		throws Exception {

		return dlFileEntryFinder.countByG_U_R_F_M(
			groupId, userId, repositoryIds, folderIds, mimeTypes,
			queryDefinition);
	}

	@Override
	public int getFileEntriesCount(
			long groupId, long userId, List<Long> folderIds, String[] mimeTypes,
			QueryDefinition<DLFileEntry> queryDefinition)
		throws Exception {

		return dlFileEntryFinder.countByG_U_F_M(
			groupId, userId, folderIds, mimeTypes, queryDefinition);
	}

	@Override
	public DLFileEntry getFileEntry(long fileEntryId) throws PortalException {
		return dlFileEntryPersistence.findByPrimaryKey(fileEntryId);
	}

	@Override
	public DLFileEntry getFileEntry(long groupId, long folderId, String title)
		throws PortalException {

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

		StringBundler sb = new StringBundler(7);

		sb.append("No DLFileEntry exists with the key {groupId=");
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
		throws PortalException {

		return dlFileEntryPersistence.findByG_F_N(groupId, folderId, name);
	}

	@Override
	public DLFileEntry getFileEntryByUuidAndGroupId(String uuid, long groupId)
		throws PortalException {

		return dlFileEntryPersistence.findByUUID_G(uuid, groupId);
	}

	@Override
	public List<DLFileEntry> getGroupFileEntries(
		long groupId, int start, int end) {

		return getGroupFileEntries(
			groupId, start, end,
			new RepositoryModelModifiedDateComparator<DLFileEntry>());
	}

	@Override
	public List<DLFileEntry> getGroupFileEntries(
		long groupId, int start, int end, OrderByComparator<DLFileEntry> obc) {

		return dlFileEntryPersistence.findByGroupId(groupId, start, end, obc);
	}

	@Override
	public List<DLFileEntry> getGroupFileEntries(
		long groupId, long userId, int start, int end) {

		return getGroupFileEntries(
			groupId, userId, start, end,
			new RepositoryModelModifiedDateComparator<DLFileEntry>());
	}

	@Override
	public List<DLFileEntry> getGroupFileEntries(
		long groupId, long userId, int start, int end,
		OrderByComparator<DLFileEntry> obc) {

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
	public List<DLFileEntry> getGroupFileEntries(
		long groupId, long userId, long rootFolderId, int start, int end,
		OrderByComparator<DLFileEntry> obc) {

		return getGroupFileEntries(
			groupId, userId, 0, rootFolderId, start, end, obc);
	}

	@Override
	public List<DLFileEntry> getGroupFileEntries(
		long groupId, long userId, long repositoryId, long rootFolderId,
		int start, int end, OrderByComparator<DLFileEntry> obc) {

		List<Long> folderIds = null;

		if (repositoryId != 0) {
			folderIds = dlFolderLocalService.getRepositoryFolderIds(
				repositoryId, rootFolderId);
		}
		else {
			folderIds = dlFolderLocalService.getGroupFolderIds(
				groupId, rootFolderId);
		}

		if (folderIds.isEmpty()) {
			return Collections.emptyList();
		}

		QueryDefinition<DLFileEntry> queryDefinition = new QueryDefinition<>(
			WorkflowConstants.STATUS_ANY, start, end, obc);

		if (repositoryId == 0) {
			if (userId <= 0) {
				return dlFileEntryFinder.findByG_F(
					groupId, folderIds, queryDefinition);
			}
			else {
				return dlFileEntryFinder.findByG_U_F(
					groupId, userId, folderIds, queryDefinition);
			}
		}
		else {
			List<Long> repositoryIds = new ArrayList<>();

			repositoryIds.add(repositoryId);

			if (userId <= 0) {
				return dlFileEntryFinder.findByG_R_F(
					groupId, repositoryIds, folderIds, queryDefinition);
			}
			else {
				return dlFileEntryFinder.findByG_U_R_F(
					groupId, userId, repositoryIds, folderIds, queryDefinition);
			}
		}
	}

	@Override
	public int getGroupFileEntriesCount(long groupId) {
		return dlFileEntryPersistence.countByGroupId(groupId);
	}

	@Override
	public int getGroupFileEntriesCount(long groupId, long userId) {
		if (userId <= 0) {
			return dlFileEntryPersistence.countByGroupId(groupId);
		}
		else {
			return dlFileEntryPersistence.countByG_U(groupId, userId);
		}
	}

	@Override
	public List<DLFileEntry> getMisversionedFileEntries() {
		return dlFileEntryFinder.findByMisversioned();
	}

	@Override
	public List<DLFileEntry> getNoAssetFileEntries() {
		return dlFileEntryFinder.findByNoAssets();
	}

	@Override
	public List<DLFileEntry> getOrphanedFileEntries() {
		return dlFileEntryFinder.findByOrphanedFileEntries();
	}

	@Override
	public List<DLFileEntry> getRepositoryFileEntries(
		long repositoryId, int start, int end) {

		return dlFileEntryPersistence.findByRepositoryId(
			repositoryId, start, end);
	}

	@Override
	public int getRepositoryFileEntriesCount(long repositoryId) {
		return dlFileEntryPersistence.countByRepositoryId(repositoryId);
	}

	@Override
	public String getUniqueTitle(
			long groupId, long folderId, long fileEntryId, String title,
			String extension)
		throws PortalException {

		String uniqueTitle = title;

		for (int i = 1;; i++) {
			String uniqueFileName = DLUtil.getSanitizedFileName(
				uniqueTitle, extension);

			try {
				validateFile(
					groupId, folderId, fileEntryId, uniqueFileName,
					uniqueTitle);

				return uniqueTitle;
			}
			catch (PortalException pe) {
				if (!(pe instanceof DuplicateFolderNameException) &&
					 !(pe instanceof DuplicateFileEntryException)) {

					throw pe;
				}
			}

			uniqueTitle = FileUtil.appendParentheticalSuffix(
				title, String.valueOf(i));
		}
	}

	@Override
	public boolean hasExtraSettings() {
		if (dlFileEntryFinder.countByExtraSettings() > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean hasFileEntryLock(long userId, long fileEntryId)
		throws PortalException {

		DLFileEntry dlFileEntry = getFileEntry(fileEntryId);

		long folderId = dlFileEntry.getFolderId();

		boolean hasLock = LockManagerUtil.hasLock(
			userId, DLFileEntry.class.getName(), fileEntryId);

		if (!hasLock &&
			(folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {

			hasLock = dlFolderLocalService.hasInheritableLock(folderId);
		}

		return hasLock;
	}

	@BufferedIncrement(
		configuration = "DLFileEntry", incrementClass = NumberIncrement.class
	)
	@Override
	public void incrementViewCounter(DLFileEntry dlFileEntry, int increment) {
		if (ExportImportThreadLocal.isImportInProcess()) {
			return;
		}

		dlFileEntry = dlFileEntryPersistence.fetchByPrimaryKey(
			dlFileEntry.getFileEntryId());

		if (dlFileEntry == null) {
			return;
		}

		dlFileEntry.setModifiedDate(dlFileEntry.getModifiedDate());
		dlFileEntry.setReadCount(dlFileEntry.getReadCount() + increment);

		dlFileEntryPersistence.update(dlFileEntry);
	}

	@Override
	public boolean isFileEntryCheckedOut(long fileEntryId)
		throws PortalException {

		DLFileVersion dlFileVersion = dlFileVersionPersistence.fetchByF_V(
			fileEntryId, DLFileEntryConstants.PRIVATE_WORKING_COPY_VERSION);

		if (dlFileVersion == null) {
			return false;
		}

		return true;
	}

	@Override
	public boolean isKeepFileVersionLabel(
			long fileEntryId, boolean majorVersion,
			ServiceContext serviceContext)
		throws PortalException {

		DLFileEntry dlFileEntry = dlFileEntryPersistence.findByPrimaryKey(
			fileEntryId);

		DLFileVersion lastDLFileVersion =
			dlFileVersionLocalService.getFileVersion(
				dlFileEntry.getFileEntryId(), dlFileEntry.getVersion());

		DLFileVersion latestDLFileVersion =
			dlFileVersionLocalService.getLatestFileVersion(fileEntryId, false);

		return dlFileVersionPolicy.isKeepFileVersionLabel(
			lastDLFileVersion, latestDLFileVersion, majorVersion,
			serviceContext);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #isKeepFileVersionLabel(long,
	 *             boolean, ServiceContext)}
	 */
	@Deprecated
	@Override
	public boolean isKeepFileVersionLabel(
			long fileEntryId, ServiceContext serviceContext)
		throws PortalException {

		return isKeepFileVersionLabel(fileEntryId, false, serviceContext);
	}

	@Override
	public Lock lockFileEntry(long userId, long fileEntryId)
		throws PortalException {

		if (hasFileEntryLock(userId, fileEntryId)) {
			return LockManagerUtil.getLock(
				DLFileEntry.class.getName(), fileEntryId);
		}

		return LockManagerUtil.lock(
			userId, DLFileEntry.class.getName(), fileEntryId, null, false,
			DLFileEntryImpl.LOCK_EXPIRATION_TIME);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public DLFileEntry moveFileEntry(
			long userId, long fileEntryId, long newFolderId,
			ServiceContext serviceContext)
		throws PortalException {

		if (!hasFileEntryLock(userId, fileEntryId)) {
			lockFileEntry(userId, fileEntryId);
		}

		try {
			DLFileEntry dlFileEntry = moveFileEntryImpl(
				userId, fileEntryId, newFolderId, serviceContext);

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
	public void rebuildTree(long companyId) throws PortalException {
		dlFolderLocalService.rebuildTree(companyId);
	}

	@Override
	public void revertFileEntry(
			long userId, long fileEntryId, String version,
			ServiceContext serviceContext)
		throws PortalException {

		if (Validator.isNull(version)) {
			throw new InvalidFileVersionException("Version is null");
		}

		if (version.equals(DLFileEntryConstants.PRIVATE_WORKING_COPY_VERSION)) {
			throw new InvalidFileVersionException(
				"Unable to revert a private working copy file version");
		}

		DLFileVersion dlFileVersion = dlFileVersionLocalService.getFileVersion(
			fileEntryId, version);

		if (!dlFileVersion.isApproved()) {
			throw new InvalidFileVersionException(
				"Unable to revert from an unapproved file version");
		}

		DLFileVersion latestDLFileVersion =
			dlFileVersionLocalService.getLatestFileVersion(fileEntryId, false);

		if (version.equals(latestDLFileVersion.getVersion())) {
			throw new InvalidFileVersionException(
				"Unable to revert from the latest file version");
		}

		String sourceFileName = dlFileVersion.getTitle();
		String extension = dlFileVersion.getExtension();
		String mimeType = dlFileVersion.getMimeType();
		String title = dlFileVersion.getTitle();
		String description = dlFileVersion.getDescription();
		String changeLog = LanguageUtil.format(
			serviceContext.getLocale(), "reverted-to-x", version, false);
		boolean majorVersion = true;
		String extraSettings = dlFileVersion.getExtraSettings();
		Map<String, DDMFormValues> ddmFormValuesMap = null;
		InputStream is = getFileAsStream(fileEntryId, version, false);
		long size = dlFileVersion.getSize();

		serviceContext.setCommand(Constants.REVERT);

		DLFileEntry dlFileEntry = dlFileEntryLocalService.getFileEntry(
			fileEntryId);

		long fileEntryTypeId = getValidFileEntryTypeId(
			dlFileVersion.getFileEntryTypeId(), dlFileEntry);

		updateFileEntry(
			userId, fileEntryId, sourceFileName, extension, mimeType, title,
			description, changeLog, majorVersion, extraSettings,
			fileEntryTypeId, ddmFormValuesMap, null, is, size, serviceContext);

		DLFileVersion newDLFileVersion =
			dlFileVersionLocalService.getLatestFileVersion(fileEntryId, false);

		copyFileEntryMetadata(
			dlFileVersion.getCompanyId(), dlFileVersion.getFileEntryTypeId(),
			fileEntryId, dlFileVersion.getFileVersionId(),
			newDLFileVersion.getFileVersionId(), serviceContext);
	}

	@Override
	public Hits search(
			long groupId, long userId, long creatorUserId, int status,
			int start, int end)
		throws PortalException {

		return search(
			groupId, userId, creatorUserId,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, null, status, start,
			end);
	}

	@Override
	public Hits search(
			long groupId, long userId, long creatorUserId, long folderId,
			String[] mimeTypes, int status, int start, int end)
		throws PortalException {

		Indexer<DLFileEntry> indexer = IndexerRegistryUtil.getIndexer(
			DLFileEntryConstants.getClassName());

		SearchContext searchContext = new SearchContext();

		searchContext.setAttribute(Field.STATUS, status);

		if (creatorUserId > 0) {
			searchContext.setAttribute(
				Field.USER_ID, String.valueOf(creatorUserId));
		}

		if (ArrayUtil.isNotEmpty(mimeTypes)) {
			searchContext.setAttribute("mimeTypes", mimeTypes);
		}

		searchContext.setAttribute("paginationType", "none");

		Group group = groupLocalService.getGroup(groupId);

		searchContext.setCompanyId(group.getCompanyId());

		searchContext.setEnd(end);
		searchContext.setFolderIds(new long[] {folderId});
		searchContext.setGroupIds(new long[] {groupId});
		searchContext.setSorts(new Sort(Field.MODIFIED_DATE, true));
		searchContext.setStart(start);
		searchContext.setUserId(userId);

		return indexer.search(searchContext);
	}

	@Override
	public void setTreePaths(
			final long folderId, final String treePath, final boolean reindex)
		throws PortalException {

		if (treePath == null) {
			throw new IllegalArgumentException("Tree path is null");
		}

		final IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					Property folderIdProperty = PropertyFactoryUtil.forName(
						"folderId");

					dynamicQuery.add(folderIdProperty.eq(folderId));

					Property treePathProperty = PropertyFactoryUtil.forName(
						"treePath");

					dynamicQuery.add(
						RestrictionsFactoryUtil.or(
							treePathProperty.isNull(),
							treePathProperty.ne(treePath)));
				}

			});

		final Indexer<DLFileEntry> indexer = IndexerRegistryUtil.getIndexer(
			DLFileEntry.class.getName());

		indexableActionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<DLFileEntry>() {

				@Override
				public void performAction(DLFileEntry dlFileEntry)
					throws PortalException {

					dlFileEntry.setTreePath(treePath);

					updateDLFileEntry(dlFileEntry);

					if (!reindex) {
						return;
					}

					Document document = indexer.getDocument(dlFileEntry);

					indexableActionableDynamicQuery.addDocuments(document);
				}

			});

		indexableActionableDynamicQuery.performActions();
	}

	@Override
	public void unlockFileEntry(long fileEntryId) {
		LockManagerUtil.unlock(DLFileEntry.class.getName(), fileEntryId);
	}

	@Override
	public DLFileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			boolean majorVersion, long fileEntryTypeId,
			Map<String, DDMFormValues> ddmFormValuesMap, File file,
			InputStream is, long size, ServiceContext serviceContext)
		throws PortalException {

		DLFileEntry dlFileEntry = dlFileEntryPersistence.findByPrimaryKey(
			fileEntryId);

		String extension = DLAppUtil.getExtension(title, sourceFileName);

		String extraSettings = StringPool.BLANK;

		if (fileEntryTypeId == -1) {
			fileEntryTypeId = dlFileEntry.getFileEntryTypeId();
		}

		validateFileEntryTypeId(
			PortalUtil.getCurrentAndAncestorSiteGroupIds(
				dlFileEntry.getGroupId()),
			dlFileEntry.getFolderId(), fileEntryTypeId);

		return updateFileEntry(
			userId, fileEntryId, sourceFileName, extension, mimeType, title,
			description, changeLog, majorVersion, extraSettings,
			fileEntryTypeId, ddmFormValuesMap, file, is, size, serviceContext);
	}

	@Override
	public DLFileEntry updateFileEntryType(
			long userId, long fileEntryId, long fileEntryTypeId,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userPersistence.findByPrimaryKey(userId);

		DLFileEntry dlFileEntry = dlFileEntryLocalService.getFileEntry(
			fileEntryId);

		dlFileEntry.setFileEntryTypeId(fileEntryTypeId);

		dlFileEntryLocalService.updateDLFileEntry(dlFileEntry);

		DLFileVersion dlFileVersion =
			dlFileVersionLocalService.getLatestFileVersion(
				fileEntryId, !dlFileEntry.isCheckedOut());

		dlFileVersion.setUserId(user.getUserId());
		dlFileVersion.setUserName(user.getFullName());
		dlFileVersion.setFileEntryTypeId(fileEntryTypeId);

		dlFileVersionLocalService.updateDLFileVersion(dlFileVersion);

		return dlFileEntry;
	}

	@Override
	public void updateSmallImage(long smallImageId, long largeImageId)
		throws PortalException {

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
			ServiceContext serviceContext,
			Map<String, Serializable> workflowContext)
		throws PortalException {

		// File version

		User user = userPersistence.findByPrimaryKey(userId);

		DLFileVersion dlFileVersion = dlFileVersionPersistence.findByPrimaryKey(
			fileVersionId);

		int oldStatus = dlFileVersion.getStatus();

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

				dlFileEntry.setFileName(dlFileVersion.getFileName());
				dlFileEntry.setExtension(dlFileVersion.getExtension());
				dlFileEntry.setMimeType(dlFileVersion.getMimeType());
				dlFileEntry.setTitle(dlFileVersion.getTitle());
				dlFileEntry.setDescription(dlFileVersion.getDescription());
				dlFileEntry.setExtraSettings(dlFileVersion.getExtraSettings());
				dlFileEntry.setFileEntryTypeId(
					dlFileVersion.getFileEntryTypeId());
				dlFileEntry.setVersion(dlFileVersion.getVersion());
				dlFileEntry.setModifiedDate(dlFileVersion.getModifiedDate());
				dlFileEntry.setSize(dlFileVersion.getSize());

				dlFileEntryPersistence.update(dlFileEntry);
			}
		}
		else {

			// File entry

			if ((status != WorkflowConstants.STATUS_IN_TRASH) &&
				Objects.equals(
					dlFileEntry.getVersion(), dlFileVersion.getVersion())) {

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

			if (Objects.equals(
					dlFileVersion.getVersion(),
					DLFileEntryConstants.VERSION_DEFAULT)) {

				Indexer<DLFileEntry> indexer =
					IndexerRegistryUtil.nullSafeGetIndexer(DLFileEntry.class);

				indexer.delete(dlFileEntry);
			}
		}

		// App helper

		dlAppHelperLocalService.updateStatus(
			userId, new LiferayFileEntry(dlFileEntry),
			new LiferayFileVersion(dlFileVersion), oldStatus, status,
			serviceContext, workflowContext);

		if (PropsValues.DL_FILE_ENTRY_COMMENTS_ENABLED) {
			if (status == WorkflowConstants.STATUS_IN_TRASH) {
				CommentManagerUtil.moveDiscussionToTrash(
					DLFileEntry.class.getName(), dlFileEntry.getFileEntryId());
			}
			else if (oldStatus == WorkflowConstants.STATUS_IN_TRASH) {
				CommentManagerUtil.restoreDiscussionFromTrash(
					DLFileEntry.class.getName(), dlFileEntry.getFileEntryId());
			}
		}

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
	public void validateFile(
			long groupId, long folderId, long fileEntryId, String fileName,
			String title)
		throws PortalException {

		if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			DLFolder parentDLFolder = dlFolderPersistence.findByPrimaryKey(
				folderId);

			if (groupId != parentDLFolder.getGroupId()) {
				throw new NoSuchFolderException();
			}
		}

		DLFolder dlFolder = dlFolderPersistence.fetchByG_P_N(
			groupId, folderId, title);

		if (dlFolder != null) {
			throw new DuplicateFolderNameException(title);
		}

		DLFileEntry dlFileEntry = dlFileEntryPersistence.fetchByG_F_T(
			groupId, folderId, title);

		if ((dlFileEntry != null) &&
			(dlFileEntry.getFileEntryId() != fileEntryId)) {

			throw new DuplicateFileEntryException(title);
		}

		dlFileEntry = dlFileEntryPersistence.fetchByG_F_FN(
			groupId, folderId, fileName);

		if ((dlFileEntry != null) &&
			(dlFileEntry.getFileEntryId() != fileEntryId)) {

			throw new DuplicateFileEntryException(title);
		}
	}

	@Override
	public boolean verifyFileEntryCheckOut(long fileEntryId, String lockUuid)
		throws PortalException {

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
		throws PortalException {

		boolean lockVerified = false;

		try {
			Lock lock = LockManagerUtil.getLock(
				DLFileEntry.class.getName(), fileEntryId);

			if (Objects.equals(lock.getUuid(), lockUuid)) {
				lockVerified = true;
			}
		}
		catch (PortalException pe) {
			if (pe instanceof ExpiredLockException ||
				pe instanceof NoSuchLockException) {

				DLFileEntry dlFileEntry = dlFileEntryLocalService.getFileEntry(
					fileEntryId);

				lockVerified = dlFolderLocalService.verifyInheritableLock(
					dlFileEntry.getFolderId(), lockUuid);
			}
			else {
				throw pe;
			}
		}

		return lockVerified;
	}

	protected void addFileEntryResources(
			DLFileEntry dlFileEntry, ServiceContext serviceContext)
		throws PortalException {

		if (serviceContext.isAddGroupPermissions() ||
			serviceContext.isAddGuestPermissions()) {

			resourceLocalService.addResources(
				dlFileEntry.getCompanyId(), dlFileEntry.getGroupId(),
				dlFileEntry.getUserId(), DLFileEntry.class.getName(),
				dlFileEntry.getFileEntryId(), false,
				serviceContext.isAddGroupPermissions(),
				serviceContext.isAddGuestPermissions());
		}
		else {
			if (serviceContext.isDeriveDefaultPermissions()) {
				serviceContext.deriveDefaultPermissions(
					dlFileEntry.getRepositoryId(),
					DLFileEntryConstants.getClassName());
			}

			resourceLocalService.addModelResources(
				dlFileEntry.getCompanyId(), dlFileEntry.getGroupId(),
				dlFileEntry.getUserId(), DLFileEntry.class.getName(),
				dlFileEntry.getFileEntryId(),
				serviceContext.getModelPermissions());
		}
	}

	protected DLFileVersion addFileVersion(
			User user, DLFileEntry dlFileEntry, String fileName,
			String extension, String mimeType, String title, String description,
			String changeLog, String extraSettings, long fileEntryTypeId,
			Map<String, DDMFormValues> ddmFormValuesMap, String version,
			long size, int status, ServiceContext serviceContext)
		throws PortalException {

		long fileVersionId = counterLocalService.increment();

		DLFileVersion dlFileVersion = dlFileVersionPersistence.create(
			fileVersionId);

		String uuid = ParamUtil.getString(
			serviceContext, "fileVersionUuid", serviceContext.getUuid());

		dlFileVersion.setUuid(uuid);

		dlFileVersion.setGroupId(dlFileEntry.getGroupId());
		dlFileVersion.setCompanyId(dlFileEntry.getCompanyId());
		dlFileVersion.setUserId(user.getUserId());
		dlFileVersion.setUserName(user.getFullName());
		dlFileVersion.setRepositoryId(dlFileEntry.getRepositoryId());
		dlFileVersion.setFolderId(dlFileEntry.getFolderId());
		dlFileVersion.setFileEntryId(dlFileEntry.getFileEntryId());
		dlFileVersion.setTreePath(dlFileVersion.buildTreePath());
		dlFileVersion.setFileName(fileName);
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

		ExpandoBridge oldExpandoBridge = dlFileVersion.getExpandoBridge();

		DLFileVersion latestFileVersion =
			dlFileVersionLocalService.fetchLatestFileVersion(
				dlFileEntry.getFileEntryId(), false);

		if (latestFileVersion != null) {
			oldExpandoBridge = latestFileVersion.getExpandoBridge();
		}

		ExpandoBridgeUtil.setExpandoBridgeAttributes(
			oldExpandoBridge, dlFileVersion.getExpandoBridge(), serviceContext);

		dlFileVersionPersistence.update(dlFileVersion);

		if ((fileEntryTypeId > 0) && (ddmFormValuesMap != null)) {
			dlFileEntryMetadataLocalService.updateFileEntryMetadata(
				fileEntryTypeId, dlFileEntry.getFileEntryId(), fileVersionId,
				ddmFormValuesMap, serviceContext);
		}

		return dlFileVersion;
	}

	protected void convertExtraSettings(
			DLFileEntry dlFileEntry, DLFileVersion dlFileVersion, String[] keys)
		throws PortalException {

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
		throws PortalException {

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
		long destinationFileVersionId) {

		ExpandoTable expandoTable = expandoTableLocalService.fetchDefaultTable(
			companyId, DLFileEntry.class.getName());

		if (expandoTable == null) {
			return;
		}

		ExpandoRow sourceExpandoRow = expandoRowLocalService.fetchRow(
			expandoTable.getTableId(), sourceFileVersionId);

		if (sourceExpandoRow == null) {
			return;
		}

		ExpandoRow destinationExpandoRow = expandoRowLocalService.fetchRow(
			expandoTable.getTableId(), destinationFileVersionId);

		if (destinationExpandoRow == null) {
			return;
		}

		destinationExpandoRow.setModifiedDate(
			sourceExpandoRow.getModifiedDate());

		expandoRowLocalService.updateExpandoRow(destinationExpandoRow);
	}

	protected void copyFileEntryMetadata(
			long companyId, long fileEntryId, long fromFileVersionId,
			long toFileVersionId, ServiceContext serviceContext,
			Map<String, DDMFormValues> ddmFormValuesMap,
			List<DDMStructure> ddmStructures)
		throws PortalException {

		for (DDMStructure ddmStructure : ddmStructures) {
			DLFileEntryMetadata dlFileEntryMetadata =
				dlFileEntryMetadataLocalService.fetchFileEntryMetadata(
					ddmStructure.getStructureId(), fromFileVersionId);

			if (dlFileEntryMetadata == null) {
				continue;
			}

			DDMFormValues ddmFormValues =
				StorageEngineManagerUtil.getDDMFormValues(
					dlFileEntryMetadata.getDDMStorageId());

			ddmFormValuesMap.put(ddmStructure.getStructureKey(), ddmFormValues);
		}

		if (!ddmFormValuesMap.isEmpty()) {
			dlFileEntryMetadataLocalService.updateFileEntryMetadata(
				companyId, ddmStructures, fileEntryId, toFileVersionId,
				ddmFormValuesMap, serviceContext);
		}
	}

	protected RepositoryEventTrigger getFolderRepositoryEventTrigger(
			long groupId, long folderId)
		throws PortalException {

		if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return RepositoryUtil.getFolderRepositoryEventTrigger(folderId);
		}

		return RepositoryUtil.getRepositoryEventTrigger(groupId);
	}

	protected String getNextVersion(
		DLFileEntry dlFileEntry, boolean majorVersion, int workflowAction) {

		String version = dlFileEntry.getVersion();

		DLFileVersion dlFileVersion =
			dlFileVersionLocalService.fetchLatestFileVersion(
				dlFileEntry.getFileEntryId(), true);

		if (dlFileVersion != null) {
			version = dlFileVersion.getVersion();
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

	protected long getValidFileEntryTypeId(
			long fileEntryTypeId, DLFileEntry dlFileEntry)
		throws PortalException {

		try {
			validateFileEntryTypeId(
				PortalUtil.getCurrentAndAncestorSiteGroupIds(
					dlFileEntry.getGroupId()),
				dlFileEntry.getFolderId(), fileEntryTypeId);

			return fileEntryTypeId;
		}
		catch (InvalidFileEntryTypeException ifete) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(ifete, ifete);
			}

			return dlFileEntryTypeLocalService.getDefaultFileEntryTypeId(
				dlFileEntry.getFolderId());
		}
	}

	protected DLFileEntry moveFileEntryImpl(
			long userId, long fileEntryId, long newFolderId,
			ServiceContext serviceContext)
		throws PortalException {

		// File entry

		User user = userPersistence.findByPrimaryKey(userId);
		DLFileEntry dlFileEntry = dlFileEntryPersistence.findByPrimaryKey(
			fileEntryId);

		long oldDataRepositoryId = dlFileEntry.getDataRepositoryId();

		validateFile(
			dlFileEntry.getGroupId(), newFolderId, dlFileEntry.getFileEntryId(),
			dlFileEntry.getFileName(), dlFileEntry.getTitle());

		dlFileEntry.setFolderId(newFolderId);
		dlFileEntry.setTreePath(dlFileEntry.buildTreePath());

		dlFileEntryPersistence.update(dlFileEntry);

		// File version

		List<DLFileVersion> dlFileVersions =
			dlFileVersionPersistence.findByFileEntryId(fileEntryId);

		for (DLFileVersion dlFileVersion : dlFileVersions) {
			dlFileVersion.setFolderId(newFolderId);
			dlFileVersion.setTreePath(dlFileVersion.buildTreePath());
			dlFileVersion.setStatusByUserId(userId);
			dlFileVersion.setStatusByUserName(user.getFullName());

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
		Indexer<DLFileEntry> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			DLFileEntry.class);

		indexer.reindex(dlFileEntry);
	}

	protected void removeFileVersion(
			DLFileEntry dlFileEntry, DLFileVersion dlFileVersion)
		throws PortalException {

		dlFileVersionPersistence.remove(dlFileVersion);

		expandoRowLocalService.deleteRows(dlFileVersion.getFileVersionId());

		dlFileEntryMetadataLocalService.deleteFileVersionFileEntryMetadata(
			dlFileVersion.getFileVersionId());

		assetEntryLocalService.deleteEntry(
			DLFileEntryConstants.getClassName(), dlFileVersion.getPrimaryKey());

		DLStoreUtil.deleteFile(
			dlFileEntry.getCompanyId(), dlFileEntry.getDataRepositoryId(),
			dlFileEntry.getName(),
			DLFileEntryConstants.PRIVATE_WORKING_COPY_VERSION);

		unlockFileEntry(dlFileEntry.getFileEntryId());
	}

	protected DLFileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName,
			String extension, String mimeType, String title, String description,
			String changeLog, boolean majorVersion, String extraSettings,
			long fileEntryTypeId, Map<String, DDMFormValues> ddmFormValuesMap,
			File file, InputStream is, long size, ServiceContext serviceContext)
		throws PortalException {

		User user = userPersistence.findByPrimaryKey(userId);
		DLFileEntry dlFileEntry = dlFileEntryPersistence.findByPrimaryKey(
			fileEntryId);

		boolean checkedOut = dlFileEntry.isCheckedOut();

		DLFileVersion dlFileVersion =
			dlFileVersionLocalService.getLatestFileVersion(
				fileEntryId, !checkedOut);

		boolean autoCheckIn = false;

		if (!checkedOut && dlFileVersion.isApproved() &&
			!Objects.equals(
				dlFileVersion.getUuid(),
				serviceContext.getUuidWithoutReset())) {

			autoCheckIn = true;
		}

		if (autoCheckIn) {
			dlFileEntry = checkOutFileEntry(
				userId, fileEntryId, fileEntryTypeId, serviceContext);
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

			String fileName = DLUtil.getSanitizedFileName(title, extension);

			Date now = new Date();

			validateFile(
				dlFileEntry.getGroupId(), dlFileEntry.getFolderId(),
				dlFileEntry.getFileEntryId(), sourceFileName, fileName,
				extension, title);

			// File version

			String version = dlFileVersion.getVersion();

			if (size == 0) {
				size = dlFileVersion.getSize();
			}

			updateFileVersion(
				user, dlFileVersion, sourceFileName, fileName, extension,
				mimeType, title, description, changeLog, extraSettings,
				fileEntryTypeId, ddmFormValuesMap, version, size,
				dlFileVersion.getStatus(), serviceContext.getModifiedDate(now),
				serviceContext);

			// Folder

			if (!checkedOut &&
				(dlFileEntry.getFolderId() !=
					DLFolderConstants.DEFAULT_PARENT_FOLDER_ID)) {

				dlFolderLocalService.updateLastPostDate(
					dlFileEntry.getFolderId(),
					serviceContext.getModifiedDate(now));
			}

			// File

			if ((file != null) || (is != null)) {
				DLStoreUtil.deleteFile(
					user.getCompanyId(), dlFileEntry.getDataRepositoryId(),
					dlFileEntry.getName(), version);

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
				checkInFileEntry(
					userId, fileEntryId, majorVersion, changeLog,
					serviceContext);
			}
		}
		catch (PortalException pe) {
			if (autoCheckIn) {
				try {
					cancelCheckOut(userId, fileEntryId);
				}
				catch (Exception e) {
					_log.error(e, e);
				}
			}

			throw pe;
		}
		catch (SystemException se) {
			if (autoCheckIn) {
				try {
					cancelCheckOut(userId, fileEntryId);
				}
				catch (Exception e) {
					_log.error(e, e);
				}
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
			String fileName, String extension, String mimeType, String title,
			String description, String changeLog, String extraSettings,
			long fileEntryTypeId, Map<String, DDMFormValues> ddmFormValuesMap,
			String version, long size, int status, Date statusDate,
			ServiceContext serviceContext)
		throws PortalException {

		dlFileVersion.setUserId(user.getUserId());
		dlFileVersion.setUserName(user.getFullName());
		dlFileVersion.setModifiedDate(statusDate);
		dlFileVersion.setFileName(fileName);

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

		ExpandoBridgeUtil.setExpandoBridgeAttributes(
			dlFileVersion.getExpandoBridge(), dlFileVersion.getExpandoBridge(),
			serviceContext);

		dlFileVersion = dlFileVersionPersistence.update(dlFileVersion);

		if ((fileEntryTypeId > 0) && (ddmFormValuesMap != null)) {
			dlFileEntryMetadataLocalService.updateFileEntryMetadata(
				fileEntryTypeId, dlFileVersion.getFileEntryId(),
				dlFileVersion.getFileVersionId(), ddmFormValuesMap,
				serviceContext);
		}

		return dlFileVersion;
	}

	protected void validateFile(
			long groupId, long folderId, long fileEntryId,
			String sourceFileName, String fileName, String extension,
			String title)
		throws PortalException {

		DLValidatorUtil.validateFileName(title);

		validateFileExtension(extension);

		validateFile(groupId, folderId, fileEntryId, fileName, title);
	}

	protected void validateFileEntryTypeId(
			long[] groupIds, long folderId, long fileEntryTypeId)
		throws PortalException {

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
				throw new FileExtensionException(
					extension + " exceeds max length of " + maxLength);
			}
		}
	}

	@BeanReference(type = DLFileVersionPolicy.class)
	protected DLFileVersionPolicy dlFileVersionPolicy;

	private static final Log _log = LogFactoryUtil.getLog(
		DLFileEntryLocalServiceImpl.class);

}