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

package com.liferay.portal.verify;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileVersion;
import com.liferay.portal.repository.liferayrepository.model.LiferayFolder;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.DuplicateFileException;
import com.liferay.portlet.documentlibrary.DuplicateFolderNameException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFileEntryTypeConstants;
import com.liferay.portlet.documentlibrary.model.DLFileVersion;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLAppHelperLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryMetadataLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileVersionLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.portlet.documentlibrary.store.DLStoreUtil;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.documentlibrary.util.comparator.FileVersionVersionComparator;
import com.liferay.portlet.documentlibrary.webdav.DLWebDAVStorageImpl;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLinkLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.storage.StorageAdapter;
import com.liferay.portlet.dynamicdatamapping.storage.StorageAdapterRegistryUtil;
import com.liferay.portlet.dynamicdatamapping.storage.StorageType;

import java.io.InputStream;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author Raymond Augé
 * @author Douglas Wong
 * @author Alexander Chow
 */
public class VerifyDocumentLibrary extends VerifyProcess {

	protected void addDLFileVersion(DLFileEntry dlFileEntry) {
		long fileVersionId = CounterLocalServiceUtil.increment();

		DLFileVersion dlFileVersion =
			DLFileVersionLocalServiceUtil.createDLFileVersion(fileVersionId);

		dlFileVersion.setGroupId(dlFileEntry.getGroupId());
		dlFileVersion.setCompanyId(dlFileEntry.getCompanyId());

		long userId = dlFileEntry.getUserId();

		dlFileVersion.setUserId(userId);

		String userName = dlFileEntry.getUserName();

		dlFileVersion.setUserName(userName);

		dlFileVersion.setCreateDate(dlFileEntry.getModifiedDate());
		dlFileVersion.setModifiedDate(dlFileEntry.getModifiedDate());
		dlFileVersion.setRepositoryId(dlFileEntry.getRepositoryId());
		dlFileVersion.setFolderId(dlFileEntry.getFolderId());
		dlFileVersion.setFileEntryId(dlFileEntry.getFileEntryId());
		dlFileVersion.setExtension(dlFileEntry.getExtension());
		dlFileVersion.setMimeType(dlFileEntry.getMimeType());
		dlFileVersion.setTitle(dlFileEntry.getTitle());
		dlFileVersion.setDescription(dlFileEntry.getDescription());
		dlFileVersion.setExtraSettings(dlFileEntry.getExtraSettings());
		dlFileVersion.setFileEntryTypeId(dlFileEntry.getFileEntryTypeId());
		dlFileVersion.setVersion(dlFileEntry.getVersion());
		dlFileVersion.setSize(dlFileEntry.getSize());
		dlFileVersion.setStatus(WorkflowConstants.STATUS_APPROVED);
		dlFileVersion.setStatusByUserId(userId);
		dlFileVersion.setStatusByUserName(userName);
		dlFileVersion.setStatusDate(new Date());

		DLFileVersionLocalServiceUtil.updateDLFileVersion(dlFileVersion);
	}

	protected void checkDLFileEntryMetadata() throws Exception {
		List<DLFileEntryMetadata> mismatchedCompanyIdDLFileEntryMetadatas =
			DLFileEntryMetadataLocalServiceUtil.
				getMismatchedCompanyIdFileEntryMetadatas();

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Deleting " + mismatchedCompanyIdDLFileEntryMetadatas.size() +
					" file entry metadatas with mismatched company IDs");
		}

		for (DLFileEntryMetadata dlFileEntryMetadata :
				mismatchedCompanyIdDLFileEntryMetadatas) {

			deleteUnusedDLFileEntryMetadata(dlFileEntryMetadata);
		}

		List<DLFileEntryMetadata> noStructuresDLFileEntryMetadatas =
			DLFileEntryMetadataLocalServiceUtil.
				getNoStructuresFileEntryMetadatas();

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Deleting " + noStructuresDLFileEntryMetadatas.size() +
					" file entry metadatas with no structures");
		}

		for (DLFileEntryMetadata dlFileEntryMetadata :
				noStructuresDLFileEntryMetadatas ) {

			deleteUnusedDLFileEntryMetadata(dlFileEntryMetadata);
		}
	}

	protected void checkDLFileEntryType() throws Exception {
		DLFileEntryType dlFileEntryType =
			DLFileEntryTypeLocalServiceUtil.fetchDLFileEntryType(
				DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT);

		if (dlFileEntryType != null) {
			return;
		}

		Date now = new Date();

		dlFileEntryType = DLFileEntryTypeLocalServiceUtil.createDLFileEntryType(
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT);

		dlFileEntryType.setCreateDate(now);
		dlFileEntryType.setModifiedDate(now);
		dlFileEntryType.setFileEntryTypeKey(
			StringUtil.toUpperCase(
				DLFileEntryTypeConstants.NAME_BASIC_DOCUMENT));
		dlFileEntryType.setName(
			DLFileEntryTypeConstants.NAME_BASIC_DOCUMENT,
			LocaleUtil.getDefault());

		DLFileEntryTypeLocalServiceUtil.updateDLFileEntryType(dlFileEntryType);
	}

	protected void checkFileVersionMimeTypes(final String[] originalMimeTypes)
		throws Exception {

		ActionableDynamicQuery actionableDynamicQuery =
			DLFileVersionLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					Criterion criterion = RestrictionsFactoryUtil.eq(
						"mimeType", originalMimeTypes[0]);

					for (int i = 1; i < originalMimeTypes.length; i++) {
						criterion = RestrictionsFactoryUtil.or(
							criterion,
							RestrictionsFactoryUtil.eq(
								"mimeType", originalMimeTypes[i]));
					}

					dynamicQuery.add(criterion);
				}

			});
		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod() {

				@Override
				public void performAction(Object object) {
					DLFileVersion dlFileVersion = (DLFileVersion)object;

					InputStream inputStream = null;

					try {
						inputStream =
							DLFileEntryLocalServiceUtil.getFileAsStream(
								dlFileVersion.getFileEntryId(),
								dlFileVersion.getVersion(), false);
					}
					catch (Exception e) {
						if (_log.isWarnEnabled()) {
							DLFileEntry dlFileEntry =
								DLFileEntryLocalServiceUtil.fetchDLFileEntry(
									dlFileVersion.getFileEntryId());

							if (dlFileEntry == null) {
								_log.warn(
									"Unable to find file entry associated " +
										"with file version " +
											dlFileVersion.getFileVersionId(),
									e);
							}
							else {
								StringBundler sb = new StringBundler(4);

								sb.append("Unable to find file version ");
								sb.append(dlFileVersion.getVersion());
								sb.append(" for file entry ");
								sb.append(dlFileEntry.getName());

								_log.warn(sb.toString(), e);
							}
						}

						return;
					}

					String title = DLUtil.getTitleWithExtension(
						dlFileVersion.getTitle(), dlFileVersion.getExtension());

					String mimeType = getMimeType(inputStream, title);

					if (mimeType.equals(dlFileVersion.getMimeType())) {
						return;
					}

					dlFileVersion.setMimeType(mimeType);

					DLFileVersionLocalServiceUtil.updateDLFileVersion(
						dlFileVersion);

					try {
						DLFileEntry dlFileEntry = dlFileVersion.getFileEntry();

						if (Validator.equals(
								dlFileEntry.getVersion(),
								dlFileVersion.getVersion())) {

							dlFileEntry.setMimeType(mimeType);

							DLFileEntryLocalServiceUtil.updateDLFileEntry(
								dlFileEntry);
						}
					}
					catch (PortalException e) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"Unable to get file entry " +
									dlFileVersion.getFileEntryId(),
								e);
						}
					}
				}

			});

		if (_log.isDebugEnabled()) {
			long count = actionableDynamicQuery.performCount();

			_log.debug(
				"Processing " + count + " file versions with mime types: " +
					StringUtil.merge(originalMimeTypes, StringPool.COMMA));
		}

		actionableDynamicQuery.performActions();
	}

	protected void checkMimeTypes() throws Exception {
		String[] mimeTypes = {
			ContentTypes.APPLICATION_OCTET_STREAM,
			DLWebDAVStorageImpl.MS_OFFICE_2010_TEXT_XML_UTF8
		};

		checkFileVersionMimeTypes(mimeTypes);

		if (_log.isDebugEnabled()) {
			_log.debug("Fixed file entries with invalid mime types");
		}
	}

	protected void checkMisversionedDLFileEntries() throws Exception {
		List<DLFileEntry> dlFileEntries =
			DLFileEntryLocalServiceUtil.getMisversionedFileEntries();

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Processing " + dlFileEntries.size() +
					" misversioned file entries");
		}

		for (DLFileEntry dlFileEntry : dlFileEntries) {
			copyDLFileEntry(dlFileEntry);

			addDLFileVersion(dlFileEntry);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Fixed misversioned file entries");
		}
	}

	protected void checkTitles() throws Exception {
		ActionableDynamicQuery actionableDynamicQuery =
			DLFileEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod() {

				@Override
				public void performAction(Object object) {
					DLFileEntry dlFileEntry = (DLFileEntry)object;

					if (dlFileEntry.isInTrash()) {
						return;
					}

					String title = dlFileEntry.getTitle();

					if (StringUtil.contains(
							title, StringPool.DOUBLE_BACK_SLASH)) {

						String newTitle = title.replace(
							StringPool.BACK_SLASH, StringPool.UNDERLINE);

						try {
							dlFileEntry = renameTitle(dlFileEntry, newTitle);
						}
						catch (Exception e) {
							if (_log.isWarnEnabled()) {
								_log.warn(
									"Unable to rename duplicate title for " +
										"file entry " +
											dlFileEntry.getFileEntryId(),
									e);
							}
						}
					}

					try {
						DLFileEntryLocalServiceUtil.validateFile(
							dlFileEntry.getGroupId(), dlFileEntry.getFolderId(),
							dlFileEntry.getFileEntryId(),
							dlFileEntry.getFileName(), dlFileEntry.getTitle());
					}
					catch (PortalException pe) {
						if (!(pe instanceof DuplicateFileException) &&
							!(pe instanceof DuplicateFolderNameException)) {

							return;
						}

						try {
							renameDuplicateTitle(dlFileEntry);
						}
						catch (Exception e) {
							if (_log.isWarnEnabled()) {
								_log.warn(
									"Unable to rename duplicate title for " +
										"file entry " +
											dlFileEntry.getFileEntryId(),
									e);
							}
						}
					}
				}

			});

		actionableDynamicQuery.performActions();
	}

	protected void copyDLFileEntry(DLFileEntry dlFileEntry)
		throws PortalException {

		long companyId = dlFileEntry.getCompanyId();
		long dataRepositoryId = dlFileEntry.getDataRepositoryId();
		String name = dlFileEntry.getName();
		String version = dlFileEntry.getVersion();

		if (DLStoreUtil.hasFile(companyId, dataRepositoryId, name, version)) {
			return;
		}

		FileVersionVersionComparator comparator =
			new FileVersionVersionComparator();

		List<DLFileVersion> dlFileVersions = dlFileEntry.getFileVersions(
			WorkflowConstants.STATUS_APPROVED);

		if (dlFileVersions.isEmpty()) {
			dlFileVersions = dlFileEntry.getFileVersions(
				WorkflowConstants.STATUS_ANY);
		}

		if (dlFileVersions.isEmpty()) {
			DLStoreUtil.addFile(companyId, dataRepositoryId, name, new byte[0]);

			return;
		}

		dlFileVersions = ListUtil.copy(dlFileVersions);

		Collections.sort(dlFileVersions, comparator);

		DLFileVersion dlFileVersion = dlFileVersions.get(0);

		DLStoreUtil.copyFileVersion(
			companyId, dataRepositoryId, name, dlFileVersion.getVersion(),
			version);
	}

	protected void deleteOrphanedDLFileEntries() throws Exception {
		List<DLFileEntry> dlFileEntries =
			DLFileEntryLocalServiceUtil.getOrphanedFileEntries();

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Processing " + dlFileEntries.size() +
					" file entries with no group");
		}

		for (DLFileEntry dlFileEntry : dlFileEntries) {
			try {
				DLFileEntryLocalServiceUtil.deleteFileEntry(
					dlFileEntry.getFileEntryId());
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to remove file entry " +
							dlFileEntry.getFileEntryId() + ": " +
								e.getMessage());
				}
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Removed orphaned file entries");
		}
	}

	protected void deleteUnusedDLFileEntryMetadata(
			DLFileEntryMetadata dlFileEntryMetadata)
		throws Exception {

		DLFileEntryMetadataLocalServiceUtil.deleteDLFileEntryMetadata(
			dlFileEntryMetadata);

		StorageAdapter storageAdapter =
			StorageAdapterRegistryUtil.getStorageAdapter(
				StorageType.JSON.toString());

		storageAdapter.deleteByClass(dlFileEntryMetadata.getDDMStorageId());

		DDMStructureLinkLocalServiceUtil.deleteStructureLinks(
			PortalUtil.getClassNameId(DLFileEntryMetadata.class),
			dlFileEntryMetadata.getFileEntryMetadataId());
	}

	@Override
	protected void doVerify() throws Exception {
		checkMisversionedDLFileEntries();

		checkDLFileEntryType();
		checkDLFileEntryMetadata();
		checkMimeTypes();
		checkTitles();
		deleteOrphanedDLFileEntries();
		updateClassNameId();
		updateFileEntryAssets();
		updateFolderAssets();
		verifyTree();
	}

	protected String getMimeType(InputStream inputStream, String title) {
		String mimeType = null;

		try {
			mimeType = MimeTypesUtil.getContentType(inputStream, title);
		}
		finally {
			StreamUtil.cleanUp(inputStream);
		}

		return mimeType;
	}

	protected void renameDuplicateTitle(DLFileEntry dlFileEntry)
		throws PortalException {

		String title = dlFileEntry.getTitle();
		String titleExtension = StringPool.BLANK;
		String titleWithoutExtension = dlFileEntry.getTitle();

		if (title.endsWith(
				StringPool.PERIOD.concat(dlFileEntry.getExtension()))) {

			titleExtension = dlFileEntry.getExtension();
			titleWithoutExtension = FileUtil.stripExtension(title);
		}

		for (int i = 1;;) {
			String uniqueTitle =
				titleWithoutExtension + StringPool.UNDERLINE +
					String.valueOf(i);

			if (Validator.isNotNull(titleExtension)) {
				uniqueTitle = uniqueTitle.concat(
					StringPool.PERIOD.concat(titleExtension));
			}

			String uniqueFileName = DLUtil.getSanitizedFileName(
				uniqueTitle, dlFileEntry.getExtension());

			try {
				DLFileEntryLocalServiceUtil.validateFile(
					dlFileEntry.getGroupId(), dlFileEntry.getFolderId(),
					dlFileEntry.getFileEntryId(), uniqueFileName, uniqueTitle);

				renameTitle(dlFileEntry, uniqueTitle);

				return;
			}
			catch (PortalException pe) {
				if (!(pe instanceof DuplicateFolderNameException) &&
					 !(pe instanceof DuplicateFileException)) {

					throw pe;
				}

				i++;
			}
		}
	}

	protected DLFileEntry renameTitle(DLFileEntry dlFileEntry, String newTitle)
		throws PortalException {

		String title = dlFileEntry.getTitle();

		dlFileEntry.setTitle(newTitle);

		String fileName = DLUtil.getSanitizedFileName(
			newTitle, dlFileEntry.getExtension());

		dlFileEntry.setFileName(fileName);

		DLFileEntry renamedDLFileEntry =
			DLFileEntryLocalServiceUtil.updateDLFileEntry(dlFileEntry);

		DLFileVersion dlFileVersion = dlFileEntry.getFileVersion();

		dlFileVersion.setTitle(newTitle);
		dlFileVersion.setFileName(fileName);

		DLFileVersionLocalServiceUtil.updateDLFileVersion(dlFileVersion);

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Invalid title " + title + " renamed to " + newTitle +
					" for file entry " + dlFileEntry.getFileEntryId());
		}

		return renamedDLFileEntry;
	}

	protected void updateClassNameId() {
		try {
			runSQL(
				"update DLFileEntry set classNameId = 0 where classNameId is " +
					"null");
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to fix file entries where class name ID is null",
					e);
			}
		}
	}

	protected void updateFileEntryAssets() throws Exception {
		List<DLFileEntry> dlFileEntries =
			DLFileEntryLocalServiceUtil.getNoAssetFileEntries();

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Processing " + dlFileEntries.size() +
					" file entries with no asset");
		}

		for (DLFileEntry dlFileEntry : dlFileEntries) {
			FileEntry fileEntry = new LiferayFileEntry(dlFileEntry);
			FileVersion fileVersion = new LiferayFileVersion(
				dlFileEntry.getFileVersion());

			try {
				DLAppHelperLocalServiceUtil.updateAsset(
					dlFileEntry.getUserId(), fileEntry, fileVersion, null, null,
					null);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to update asset for file entry " +
							dlFileEntry.getFileEntryId() + ": " +
								e.getMessage());
				}
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Assets verified for file entries");
		}
	}

	protected void updateFolderAssets() throws Exception {
		List<DLFolder> dlFolders = DLFolderLocalServiceUtil.getNoAssetFolders();

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Processing " + dlFolders.size() + " folders with no asset");
		}

		for (DLFolder dlFolder : dlFolders) {
			Folder folder = new LiferayFolder(dlFolder);

			try {
				DLAppHelperLocalServiceUtil.updateAsset(
					dlFolder.getUserId(), folder, null, null, null);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to update asset for folder " +
							dlFolder.getFolderId() + ": " + e.getMessage());
				}
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Assets verified for folders");
		}
	}

	protected void verifyTree() throws Exception {
		long[] companyIds = PortalInstances.getCompanyIdsBySQL();

		for (long companyId : companyIds) {
			DLFolderLocalServiceUtil.rebuildTree(companyId);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		VerifyDocumentLibrary.class);

}