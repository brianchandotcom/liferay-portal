/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.lar;

import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.DuplicateFileException;
import com.liferay.portlet.documentlibrary.NoSuchFileException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileRank;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileRankUtil;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileShortcutUtil;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.util.PwdGenerator;

import java.io.IOException;
import java.io.InputStream;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.portlet.PortletPreferences;

/**
 * @author Bruno Farache
 * @author Raymond Augé
 */
public class DLPortletDataHandlerImpl extends BasePortletDataHandler {

	public static void exportFileEntry(
			PortletDataContext portletDataContext, Element foldersElement,
			Element fileEntriesElement, Element fileRanksElement,
			FileEntry fileEntry, boolean checkDateRange)
		throws Exception {

		if (checkDateRange &&
			!portletDataContext.isWithinDateRange(
				fileEntry.getModifiedDate())) {

			return;
		}

		FileVersion fileVersion = fileEntry.getFileVersion();

		if (fileVersion.getStatus() != WorkflowConstants.STATUS_APPROVED) {
			return;
		}

		if (foldersElement != null) {
			exportParentFolder(
				portletDataContext, foldersElement, fileEntry.getFolderId());
		}

		String path = getFileEntryPath(portletDataContext, fileEntry);

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		Element fileEntryElement = fileEntriesElement.addElement("file-entry");

		if (!portletDataContext.isPerformDirectBinaryImport()) {
			String binPath = getFileEntryBinPath(portletDataContext, fileEntry);

			fileEntryElement.addAttribute("bin-path", binPath);

			InputStream is = null;

			try {
				is = FileEntryUtil.getContentStream(fileEntry);
			}
			catch (NoSuchFileException nsfe) {
			}

			if (is == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No file found for file entry " +
							fileEntry.getFileEntryId());
				}

				fileEntryElement.detach();

				return;
			}

			try {
				portletDataContext.addZipEntry(binPath, is);
			}
			finally {
				try {
					is.close();
				}
				catch (IOException ioe) {
					_log.error(ioe, ioe);
				}
			}
		}

		if (portletDataContext.getBooleanParameter(_NAMESPACE, "ranks")) {
			List<DLFileRank> fileRanks = DLFileRankUtil.findByFileEntryId(
				fileEntry.getFileEntryId());

			for (DLFileRank fileRank : fileRanks) {
				exportFileRank(
					portletDataContext, fileRanksElement, fileRank);
			}
		}

		portletDataContext.addClassedModel(
			fileEntryElement, path, fileEntry, _NAMESPACE);
	}

	public static String getFileEntryPath(
		PortletDataContext portletDataContext, FileEntry fileEntry) {

		StringBundler sb = new StringBundler(6);

		sb.append(
			portletDataContext.getPortletPath(PortletKeys.DOCUMENT_LIBRARY));
		sb.append("/file-entries/");
		sb.append(fileEntry.getFileEntryId());
		sb.append(StringPool.SLASH);
		sb.append(fileEntry.getVersion());
		sb.append(".xml");

		return sb.toString();
	}

	public static void importFileEntry(
			PortletDataContext portletDataContext, Element fileEntryElement)
		throws Exception {

		String path = fileEntryElement.attributeValue("path");

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		FileEntry fileEntry = (FileEntry)portletDataContext.getZipEntryAsObject(
			path);

		long userId = portletDataContext.getUserId(fileEntry.getUserUuid());

		Map<Long, Long> folderPKs =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DLFolder.class);

		long folderId = MapUtil.getLong(
			folderPKs, fileEntry.getFolderId(), fileEntry.getFolderId());

		long[] assetCategoryIds = null;
		String[] assetTagNames = null;

		if (portletDataContext.getBooleanParameter(_NAMESPACE, "categories")) {
			assetCategoryIds = portletDataContext.getAssetCategoryIds(
				FileEntry.class, fileEntry.getFileEntryId());
		}

		if (portletDataContext.getBooleanParameter(_NAMESPACE, "tags")) {
			assetTagNames = portletDataContext.getAssetTagNames(
				FileEntry.class, fileEntry.getFileEntryId());
		}

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			fileEntryElement, fileEntry, _NAMESPACE);

		serviceContext.setAttribute(
			"sourceFileName", "A." + fileEntry.getExtension());

		String binPath = fileEntryElement.attributeValue("bin-path");

		InputStream is = null;

		if (Validator.isNull(binPath) &&
			portletDataContext.isPerformDirectBinaryImport()) {

			is = FileEntryUtil.getContentStream(fileEntry);
		}
		else {
			is = portletDataContext.getZipEntryAsInputStream(binPath);
		}

		String folderUuid = StringPool.BLANK;

		if ((folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) &&
			(folderId == fileEntry.getFolderId())) {

			String folderPath = getImportFolderPath(
				portletDataContext, folderId);

			Folder folder = (Folder)portletDataContext.getZipEntryAsObject(
				folderPath);

			importFolder(portletDataContext, folderPath, folder);

			folderUuid = folder.getUuid();

			folderId = MapUtil.getLong(
				folderPKs, fileEntry.getFolderId(), fileEntry.getFolderId());
		}

		FileEntry importedFileEntry = null;

		String titleWithExtension = fileEntry.getTitle();
		String extension = fileEntry.getExtension();

		if (!titleWithExtension.endsWith(StringPool.PERIOD + extension)) {
			titleWithExtension += StringPool.PERIOD + extension;
		}

		if (portletDataContext.isDataStrategyMirror()) {
			FileEntry existingFileEntry = FileEntryUtil.fetchByUUID_R(
				fileEntry.getUuid(), portletDataContext.getScopeGroupId());

			if (existingFileEntry == null) {
				FileEntry existingTitleFileEntry = FileEntryUtil.fetchByR_F_T(
					portletDataContext.getScopeGroupId(), folderId,
					fileEntry.getTitle());

				if (existingTitleFileEntry != null) {
					if (portletDataContext.
							isDataStrategyMirrorWithOverwritting()) {

						DLAppLocalServiceUtil.deleteFileEntry(
							existingTitleFileEntry.getFileEntryId());
					}
					else {
						String originalTitle = fileEntry.getTitle();
						String dotExtension = StringPool.PERIOD + extension;

						if (originalTitle.endsWith(dotExtension)) {
							int pos = originalTitle.lastIndexOf(dotExtension);

							originalTitle = originalTitle.substring(0, pos);
						}

						for (int i = 1;; i++) {
							titleWithExtension =
								originalTitle + StringPool.SPACE + i +
									dotExtension;

							existingTitleFileEntry = FileEntryUtil.findByR_F_T(
								portletDataContext.getScopeGroupId(), folderId,
								titleWithExtension);

							if (existingTitleFileEntry == null) {
								break;
							}
						}
					}
				}

				serviceContext.setUuid(fileEntry.getUuid());

				importedFileEntry = DLAppLocalServiceUtil.addFileEntry(
					userId, portletDataContext.getScopeGroupId(), folderId,
					titleWithExtension, fileEntry.getMimeType(),
					titleWithExtension, fileEntry.getDescription(), null, is,
					fileEntry.getSize(), serviceContext);
			}
			else if (!isDuplicateFileEntry(
						folderUuid, fileEntry, existingFileEntry)) {

				importedFileEntry = DLAppLocalServiceUtil.updateFileEntry(
					userId, existingFileEntry.getFileEntryId(),
					fileEntry.getTitle(), fileEntry.getMimeType(),
					fileEntry.getTitle(), fileEntry.getDescription(), null,
					true, is, fileEntry.getSize(), serviceContext);
			}
			else {
				FileVersion latestFileVersion =
					existingFileEntry.getLatestFileVersion();

				DLAppLocalServiceUtil.updateAsset(
					userId, existingFileEntry, latestFileVersion,
					assetCategoryIds, assetTagNames, null);

				if (existingFileEntry instanceof LiferayFileEntry) {
					LiferayFileEntry liferayFileEntry =
						(LiferayFileEntry)existingFileEntry;

					Indexer indexer = IndexerRegistryUtil.getIndexer(
						DLFileEntry.class);

					indexer.reindex(liferayFileEntry.getModel());
				}

				importedFileEntry = existingFileEntry;
			}
		}
		else {
			try {
				importedFileEntry = DLAppLocalServiceUtil.addFileEntry(
					userId, portletDataContext.getScopeGroupId(), folderId,
					titleWithExtension, fileEntry.getMimeType(),
					titleWithExtension, fileEntry.getDescription(), null, is,
					fileEntry.getSize(), serviceContext);
			}
			catch (DuplicateFileException dfe) {
				String title = fileEntry.getTitle();

				String[] titleParts = title.split("\\.", 2);

				title = titleParts[0] + PwdGenerator.getPassword();

				if (titleParts.length > 1) {
					title += StringPool.PERIOD + titleParts[1];
				}

				if (!title.endsWith(StringPool.PERIOD + extension)) {
					title += StringPool.PERIOD + extension;
				}

				importedFileEntry = DLAppLocalServiceUtil.addFileEntry(
					userId, portletDataContext.getScopeGroupId(), folderId,
					title, fileEntry.getMimeType(), title,
					fileEntry.getDescription(), null, is, fileEntry.getSize(),
					serviceContext);
			}
		}

		Map<String, String> fileEntryTitles =
			(Map<String, String>)portletDataContext.getNewPrimaryKeysMap(
				FileEntry.class.getName() + ".title");

		fileEntryTitles.put(fileEntry.getTitle(), importedFileEntry.getTitle());

		portletDataContext.importClassedModel(
			fileEntry, importedFileEntry, _NAMESPACE);
	}

	public static void importFileRank(
			PortletDataContext portletDataContext, Element fileRankElement)
		throws Exception {

		String path = fileRankElement.attributeValue("path");

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		DLFileRank fileRank =
			(DLFileRank)portletDataContext.getZipEntryAsObject(path);

		importFileRank(portletDataContext, fileRank);
	}

	public static void importFolder(
			PortletDataContext portletDataContext, Element folderElement)
		throws Exception {

		String path = folderElement.attributeValue("path");

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		Folder folder = (Folder)portletDataContext.getZipEntryAsObject(path);

		importFolder(portletDataContext, path, folder);
	}

	@Override
	public PortletDataHandlerControl[] getExportControls() {
		return new PortletDataHandlerControl[] {
			_foldersAndDocuments, _shortcuts, _ranks, _categories, _comments,
			_ratings, _tags
		};
	}

	@Override
	public PortletDataHandlerControl[] getImportControls() {
		return new PortletDataHandlerControl[] {
			_foldersAndDocuments, _shortcuts, _ranks, _categories, _comments,
			_ratings, _tags
		};
	}

	@Override
	public boolean isAlwaysExportable() {
		return _ALWAYS_EXPORTABLE;
	}

	@Override
	public boolean isPublishToLiveByDefault() {
		return PropsValues.DL_PUBLISH_TO_LIVE_BY_DEFAULT;
	}

	protected static void exportFileRank(
			PortletDataContext portletDataContext, Element fileRanksElement,
			DLFileRank fileRank)
		throws Exception {

		String path = getFileRankPath(portletDataContext, fileRank);

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		Element fileRankElement = fileRanksElement.addElement("file-rank");

		portletDataContext.addClassedModel(
			fileRankElement, path, fileRank, _NAMESPACE);
	}

	protected static void exportFileShortcut(
			PortletDataContext portletDataContext, Element foldersElement,
			Element fileShortcutsElement, DLFileShortcut fileShortcut)
		throws Exception {

		exportParentFolder(
			portletDataContext, foldersElement, fileShortcut.getFolderId());

		String path = getFileShortcutPath(portletDataContext, fileShortcut);

		if (portletDataContext.isPathNotProcessed(path)) {
			Element fileShortcutElement = fileShortcutsElement.addElement(
				"file-shortcut");

			portletDataContext.addClassedModel(
				fileShortcutElement, path, fileShortcut, _NAMESPACE);
		}
	}

	protected static void exportFolder(
			PortletDataContext portletDataContext, Element foldersElement,
			Element fileEntriesElement, Element fileShortcutsElement,
			Element fileRanksElement, Folder folder, boolean recurse)
		throws Exception {

		if (portletDataContext.isWithinDateRange(folder.getModifiedDate())) {
			exportParentFolder(
				portletDataContext, foldersElement, folder.getParentFolderId());

			String path = getFolderPath(portletDataContext, folder);

			if (portletDataContext.isPathNotProcessed(path)) {
				Element folderElement = foldersElement.addElement("folder");

				portletDataContext.addClassedModel(
					folderElement, path, folder, _NAMESPACE);
			}
		}

		if (recurse) {
			List<Folder> folders = FolderUtil.findByR_P(
				folder.getRepositoryId(), folder.getFolderId());

			for (Folder curFolder : folders) {
				exportFolder(
					portletDataContext, foldersElement, fileEntriesElement,
					fileShortcutsElement, fileRanksElement, curFolder, recurse);
			}
		}

		List<FileEntry> fileEntries = FileEntryUtil.findByR_F(
			folder.getRepositoryId(), folder.getFolderId());

		for (FileEntry fileEntry : fileEntries) {
			exportFileEntry(
				portletDataContext, foldersElement, fileEntriesElement,
				fileRanksElement, fileEntry, true);
		}

		if (portletDataContext.getBooleanParameter(_NAMESPACE, "shortcuts")) {
			List<DLFileShortcut> fileShortcuts = DLFileShortcutUtil.findByG_F(
				folder.getRepositoryId(), folder.getFolderId());

			for (DLFileShortcut fileShortcut : fileShortcuts) {
				exportFileShortcut(
					portletDataContext, foldersElement, fileShortcutsElement,
					fileShortcut);
			}
		}
	}

	protected static void exportParentFolder(
			PortletDataContext portletDataContext, Element foldersElement,
			long folderId)
		throws Exception {

		if (folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			return;
		}

		Folder folder = FolderUtil.findByPrimaryKey(folderId);

		exportParentFolder(
			portletDataContext, foldersElement, folder.getParentFolderId());

		String path = getFolderPath(portletDataContext, folder);

		if (portletDataContext.isPathNotProcessed(path)) {
			Element folderElement = foldersElement.addElement("folder");

			portletDataContext.addClassedModel(
				folderElement, path, folder, _NAMESPACE);
		}
	}

	protected static String getFileEntryBinPath(
		PortletDataContext portletDataContext, FileEntry fileEntry) {

		StringBundler sb = new StringBundler(5);

		sb.append(
			portletDataContext.getPortletPath(PortletKeys.DOCUMENT_LIBRARY));
		sb.append("/bin/");
		sb.append(fileEntry.getFileEntryId());
		sb.append(StringPool.SLASH);
		sb.append(fileEntry.getVersion());

		return sb.toString();
	}

	protected static String getFileRankPath(
		PortletDataContext portletDataContext, DLFileRank fileRank) {

		StringBundler sb = new StringBundler(4);

		sb.append(
			portletDataContext.getPortletPath(PortletKeys.DOCUMENT_LIBRARY));
		sb.append("/ranks/");
		sb.append(fileRank.getFileRankId());
		sb.append(".xml");

		return sb.toString();
	}

	protected static String getFileShortcutPath(
		PortletDataContext portletDataContext, DLFileShortcut fileShortcut) {

		StringBundler sb = new StringBundler(4);

		sb.append(
			portletDataContext.getPortletPath(PortletKeys.DOCUMENT_LIBRARY));
		sb.append("/shortcuts/");
		sb.append(fileShortcut.getFileShortcutId());
		sb.append(".xml");

		return sb.toString();
	}

	protected static String getFolderName(
			String uuid, long companyId, long groupId, long parentFolderId,
			String name, int count)
		throws Exception {

		Folder folder = FolderUtil.fetchByR_P_N(groupId, parentFolderId, name);

		if (folder == null) {
			return name;
		}

		if (Validator.isNotNull(uuid) && uuid.equals(folder.getUuid())) {
			return name;
		}

		if (Pattern.matches(".* \\(\\d+\\)", name)) {
			int pos = name.lastIndexOf(" (");

			name = name.substring(0, pos);
		}

		StringBundler sb = new StringBundler(5);

		sb.append(name);
		sb.append(StringPool.SPACE);
		sb.append(StringPool.OPEN_PARENTHESIS);
		sb.append(count);
		sb.append(StringPool.CLOSE_PARENTHESIS);

		name = sb.toString();

		return getFolderName(
			uuid, companyId, groupId, parentFolderId, name, ++count);
	}

	protected static String getFolderPath(
		PortletDataContext portletDataContext, Folder folder) {

		StringBundler sb = new StringBundler(4);

		sb.append(
			portletDataContext.getPortletPath(PortletKeys.DOCUMENT_LIBRARY));
		sb.append("/folders/");
		sb.append(folder.getFolderId());
		sb.append(".xml");

		return sb.toString();
	}

	protected static String getImportFolderPath(
		PortletDataContext portletDataContext, long folderId) {

		StringBundler sb = new StringBundler(4);

		sb.append(
			portletDataContext.getSourcePortletPath(
				PortletKeys.DOCUMENT_LIBRARY));
		sb.append("/folders/");
		sb.append(folderId);
		sb.append(".xml");

		return sb.toString();
	}

	protected static void importFileRank(
			PortletDataContext portletDataContext, DLFileRank fileRank)
		throws Exception {

		long userId = portletDataContext.getUserId(fileRank.getUserUuid());

		Map<Long, Long> fileEntryPKs =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				FileEntry.class);

		long fileEntryId = MapUtil.getLong(
			fileEntryPKs, fileRank.getFileEntryId(), fileRank.getFileEntryId());

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCreateDate(fileRank.getCreateDate());

		DLAppLocalServiceUtil.updateFileRank(
			portletDataContext.getScopeGroupId(),
			portletDataContext.getCompanyId(), userId, fileEntryId,
			serviceContext);
	}

	protected static void importFileShortcut(
			PortletDataContext portletDataContext, Element fileShortcutElement,
			DLFileShortcut fileShortcut)
		throws Exception {

		long userId = portletDataContext.getUserId(fileShortcut.getUserUuid());

		Map<Long, Long> folderPKs =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DLFolder.class);

		long folderId = MapUtil.getLong(
			folderPKs, fileShortcut.getFolderId(), fileShortcut.getFolderId());

		long groupId = portletDataContext.getScopeGroupId();

		if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			Folder folder = FolderUtil.findByPrimaryKey(folderId);

			groupId = folder.getRepositoryId();
		}

		Map<Long, Long> fileEntryPKs =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				FileEntry.class);

		long fileEntryId = MapUtil.getLong(
			fileEntryPKs, fileShortcut.getToFileEntryId(),
			fileShortcut.getToFileEntryId());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			fileShortcutElement, fileShortcut, _NAMESPACE);

		DLFileShortcut importedFileShortcut = null;

		if (portletDataContext.isDataStrategyMirror()) {
			DLFileShortcut existingFileShortcut =
				DLFileShortcutUtil.fetchByUUID_G(
					fileShortcut.getUuid(),
					portletDataContext.getScopeGroupId());

			if (existingFileShortcut == null) {
				serviceContext.setUuid(fileShortcut.getUuid());

				importedFileShortcut = DLAppLocalServiceUtil.addFileShortcut(
					userId, groupId, folderId, fileEntryId, serviceContext);
			}
			else {
				importedFileShortcut = DLAppLocalServiceUtil.updateFileShortcut(
					userId, existingFileShortcut.getFileShortcutId(), folderId,
					fileEntryId, serviceContext);
			}
		}
		else {
			importedFileShortcut = DLAppLocalServiceUtil.addFileShortcut(
				userId, groupId, folderId, fileEntryId, serviceContext);
		}

		portletDataContext.importClassedModel(
			fileShortcut, importedFileShortcut, _NAMESPACE);
	}

	protected static void importFileShortcut(
			PortletDataContext portletDataContext, Element fileShortcutElement)
		throws Exception {

		String path = fileShortcutElement.attributeValue("path");

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		DLFileShortcut fileShortcut =
			(DLFileShortcut)portletDataContext.getZipEntryAsObject(path);

		importFileShortcut(
			portletDataContext, fileShortcutElement, fileShortcut);
	}

	protected static void importFolder(
			PortletDataContext portletDataContext, String folderPath,
			Folder folder)
		throws Exception {

		long userId = portletDataContext.getUserId(folder.getUserUuid());

		Map<Long, Long> folderPKs =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				DLFolder.class);

		long parentFolderId = MapUtil.getLong(
			folderPKs, folder.getParentFolderId(), folder.getParentFolderId());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			folderPath, folder, _NAMESPACE);

		if ((parentFolderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) &&
			(parentFolderId == folder.getParentFolderId())) {

			String path = getImportFolderPath(
				portletDataContext, parentFolderId);

			Folder parentFolder =
				(Folder)portletDataContext.getZipEntryAsObject(path);

			importFolder(portletDataContext, path, parentFolder);

			parentFolderId = MapUtil.getLong(
				folderPKs, folder.getParentFolderId(),
				folder.getParentFolderId());
		}

		Folder importedFolder = null;

		if (portletDataContext.isDataStrategyMirror()) {
			Folder existingFolder = FolderUtil.fetchByUUID_R(
				folder.getUuid(), portletDataContext.getScopeGroupId());

			if (existingFolder == null) {
				String name = getFolderName(
					null, portletDataContext.getCompanyId(),
					portletDataContext.getScopeGroupId(), parentFolderId,
					folder.getName(), 2);

				serviceContext.setUuid(folder.getUuid());

				importedFolder = DLAppLocalServiceUtil.addFolder(
					userId, portletDataContext.getScopeGroupId(),
					parentFolderId, name, folder.getDescription(),
					serviceContext);
			}
			else {
				String name = getFolderName(
					folder.getUuid(), portletDataContext.getCompanyId(),
					portletDataContext.getScopeGroupId(), parentFolderId,
					folder.getName(), 2);

				importedFolder = DLAppLocalServiceUtil.updateFolder(
					existingFolder.getFolderId(), parentFolderId, name,
					folder.getDescription(), serviceContext);
			}
		}
		else {
			String name = getFolderName(
				null, portletDataContext.getCompanyId(),
				portletDataContext.getScopeGroupId(), parentFolderId,
				folder.getName(), 2);

			importedFolder = DLAppLocalServiceUtil.addFolder(
				userId, portletDataContext.getScopeGroupId(), parentFolderId,
				name, folder.getDescription(), serviceContext);
		}

		portletDataContext.importClassedModel(
			folder, importedFolder, _NAMESPACE);
	}

	protected static boolean isDuplicateFileEntry(
		String folderUuid, FileEntry fileEntry1, FileEntry fileEntry2) {

		try {
			Folder folder2 = fileEntry2.getFolder();

			if (folderUuid.equals(folder2.getUuid()) &&
				(fileEntry1.getSize() == fileEntry2.getSize()) &&
				(DLUtil.compareVersions(
					fileEntry1.getVersion(), fileEntry2.getVersion()) == 0) &&
				fileEntry1.getVersionUserUuid().equals(
					fileEntry2.getVersionUserUuid())) {

				return true;
			}
			else {
				return false;
			}
		}
		catch (Exception e) {
			return false;
		}
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		if (!portletDataContext.addPrimaryKey(
				DLPortletDataHandlerImpl.class, "deleteData")) {

			DLAppLocalServiceUtil.deleteAll(
				portletDataContext.getScopeGroupId());
		}

		return null;
	}

	@Override
	protected String doExportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		portletDataContext.addPermissions(
			"com.liferay.portlet.documentlibrary",
			portletDataContext.getScopeGroupId());

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("documentlibrary-data");

		rootElement.addAttribute(
			"group-id", String.valueOf(portletDataContext.getScopeGroupId()));

		long rootFolderId = GetterUtil.getLong(
			portletPreferences.getValue("rootFolderId", null));

		if (rootFolderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			rootElement.addAttribute(
				"root-folder-id", String.valueOf(rootFolderId));
		}

		Element foldersElement = rootElement.addElement("folders");
		Element fileEntriesElement = rootElement.addElement("file-entries");
		Element fileShortcutsElement = rootElement.addElement("file-shortcuts");
		Element fileRanksElement = rootElement.addElement("file-ranks");

		List<Folder> folders = FolderUtil.findByRepositoryId(
			portletDataContext.getScopeGroupId());

		for (Folder folder : folders) {
			exportFolder(
				portletDataContext, foldersElement, fileEntriesElement,
				fileShortcutsElement, fileRanksElement, folder, false);
		}

		List<FileEntry> fileEntries = FileEntryUtil.findByR_F(
			portletDataContext.getScopeGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		for (FileEntry fileEntry : fileEntries) {
			exportFileEntry(
				portletDataContext, foldersElement, fileEntriesElement,
				fileRanksElement, fileEntry, true);
		}

		if (portletDataContext.getBooleanParameter(_NAMESPACE, "shortcuts")) {
			List<DLFileShortcut> fileShortcuts = DLFileShortcutUtil.findByG_F(
				portletDataContext.getScopeGroupId(),
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

			for (DLFileShortcut fileShortcut : fileShortcuts) {
				exportFileShortcut(
					portletDataContext, foldersElement, fileShortcutsElement,
					fileShortcut);
			}
		}

		return document.formattedString();
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		portletDataContext.importPermissions(
			"com.liferay.portlet.documentlibrary",
			portletDataContext.getSourceGroupId(),
			portletDataContext.getScopeGroupId());

		Document document = SAXReaderUtil.read(data);

		Element rootElement = document.getRootElement();

		Element foldersElement = rootElement.element("folders");

		List<Element> folderElements = foldersElement.elements("folder");

		for (Element folderElement : folderElements) {
			importFolder(portletDataContext, folderElement);
		}

		Element fileEntriesElement = rootElement.element("file-entries");

		List<Element> fileEntryElements = fileEntriesElement.elements(
			"file-entry");

		for (Element fileEntryElement : fileEntryElements) {
			importFileEntry(portletDataContext, fileEntryElement);
		}

		if (portletDataContext.getBooleanParameter(_NAMESPACE, "shortcuts")) {
			List<Element> fileShortcutElements = rootElement.element(
				"file-shortcuts").elements("file-shortcut");

			for (Element fileShortcutElement : fileShortcutElements) {
				importFileShortcut(portletDataContext, fileShortcutElement);
			}
		}

		if (portletDataContext.getBooleanParameter(_NAMESPACE, "ranks")) {
			Element fileRanksElement = rootElement.element("file-ranks");

			List<Element> fileRankElements = fileRanksElement.elements(
				"file-rank");

			for (Element fileRankElement : fileRankElements) {
				importFileRank(portletDataContext, fileRankElement);
			}
		}

		long rootFolderId = GetterUtil.getLong(
			rootElement.attributeValue("root-folder-id"));

		if (rootFolderId > 0) {
			Map<Long, Long> folderPKs =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					DLFolder.class);

			rootFolderId = MapUtil.getLong(
				folderPKs, rootFolderId, rootFolderId);

			portletPreferences.setValue(
				"rootFolderId", String.valueOf(rootFolderId));
		}

		return portletPreferences;
	}

	private static final boolean _ALWAYS_EXPORTABLE = true;

	private static final String _NAMESPACE = "document_library";

	private static Log _log = LogFactoryUtil.getLog(
		DLPortletDataHandlerImpl.class);

	private static PortletDataHandlerBoolean _categories =
		new PortletDataHandlerBoolean(_NAMESPACE, "categories");

	private static PortletDataHandlerBoolean _comments =
		new PortletDataHandlerBoolean(_NAMESPACE, "comments");

	private static PortletDataHandlerBoolean _foldersAndDocuments =
		new PortletDataHandlerBoolean(
			_NAMESPACE, "folders-and-documents", true, true);

	private static PortletDataHandlerBoolean _ranks =
		new PortletDataHandlerBoolean(_NAMESPACE, "ranks");

	private static PortletDataHandlerBoolean _ratings =
		new PortletDataHandlerBoolean(_NAMESPACE, "ratings");

	private static PortletDataHandlerBoolean _shortcuts=
		new PortletDataHandlerBoolean(_NAMESPACE, "shortcuts");

	private static PortletDataHandlerBoolean _tags =
		new PortletDataHandlerBoolean(_NAMESPACE, "tags");

}