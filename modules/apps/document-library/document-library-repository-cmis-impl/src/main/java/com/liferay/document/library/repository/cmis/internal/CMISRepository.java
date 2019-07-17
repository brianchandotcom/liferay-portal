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

package com.liferay.document.library.repository.cmis.internal;

import com.liferay.document.library.kernel.exception.DuplicateFileEntryException;
import com.liferay.document.library.kernel.exception.DuplicateFolderNameException;
import com.liferay.document.library.kernel.exception.FileNameException;
import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.exception.NoSuchFileVersionException;
import com.liferay.document.library.kernel.exception.NoSuchFolderException;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.kernel.util.comparator.RepositoryModelCreateDateComparator;
import com.liferay.document.library.kernel.util.comparator.RepositoryModelModifiedDateComparator;
import com.liferay.document.library.kernel.util.comparator.RepositoryModelSizeComparator;
import com.liferay.document.library.kernel.util.comparator.RepositoryModelTitleComparator;
import com.liferay.document.library.repository.cmis.BaseCmisRepository;
import com.liferay.document.library.repository.cmis.CMISRepositoryHandler;
import com.liferay.document.library.repository.cmis.configuration.CMISRepositoryConfiguration;
import com.liferay.document.library.repository.cmis.internal.model.CMISFileEntry;
import com.liferay.document.library.repository.cmis.internal.model.CMISFileVersion;
import com.liferay.document.library.repository.cmis.internal.model.CMISFolder;
import com.liferay.document.library.repository.cmis.search.CMISSearchQueryBuilder;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.NoSuchRepositoryEntryException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.lock.Lock;
import com.liferay.portal.kernel.lock.LockManager;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.RepositoryEntry;
import com.liferay.portal.kernel.repository.RepositoryException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.search.DocumentHelper;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.HitsImpl;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.RepositoryEntryLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;

import java.io.InputStream;

import java.math.BigInteger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.FileableCmisObject;
import org.apache.chemistry.opencmis.client.api.ItemIterable;
import org.apache.chemistry.opencmis.client.api.ObjectId;
import org.apache.chemistry.opencmis.client.api.OperationContext;
import org.apache.chemistry.opencmis.client.api.QueryResult;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.client.runtime.ObjectIdImpl;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.data.AllowableActions;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.data.PropertyData;
import org.apache.chemistry.opencmis.commons.data.RepositoryCapabilities;
import org.apache.chemistry.opencmis.commons.data.RepositoryInfo;
import org.apache.chemistry.opencmis.commons.enums.Action;
import org.apache.chemistry.opencmis.commons.enums.BaseTypeId;
import org.apache.chemistry.opencmis.commons.enums.CapabilityQuery;
import org.apache.chemistry.opencmis.commons.enums.UnfileObject;
import org.apache.chemistry.opencmis.commons.enums.VersioningState;
import org.apache.chemistry.opencmis.commons.exceptions.CmisObjectNotFoundException;
import org.apache.chemistry.opencmis.commons.exceptions.CmisPermissionDeniedException;
import org.apache.chemistry.opencmis.commons.exceptions.CmisRuntimeException;
import org.apache.chemistry.opencmis.commons.impl.Base64;
import org.apache.chemistry.opencmis.commons.impl.dataobjects.ContentStreamImpl;

/**
 * CMIS does not provide vendor neutral support for workflow, metadata, tags,
 * categories, etc. They will be ignored in this implementation.
 *
 * @author Alexander Chow
 * @see    <a href="http://wiki.oasis-open.org/cmis/Candidate%20v2%20topics">
 *         Candidate v2 topics</a>
 * @see    <a href="http://wiki.oasis-open.org/cmis/Mixin_Proposal">Mixin /
 *         Aspect Support</a>
 * @see    <a
 *         href="http://www.oasis-open.org/committees/document.php?document_id=39631">
 *         CMIS Type Mutability proposal</a>
 */
public class CMISRepository extends BaseCmisRepository {

	public CMISRepository(
		CMISRepositoryConfiguration cmisRepositoryConfiguration,
		CMISRepositoryHandler cmisRepositoryHandler,
		CMISSearchQueryBuilder cmisSearchQueryBuilder,
		CMISSessionCache cmisSessionCache, LockManager lockManager) {

		_cmisRepositoryConfiguration = cmisRepositoryConfiguration;
		_cmisRepositoryHandler = cmisRepositoryHandler;
		_cmisSearchQueryBuilder = cmisSearchQueryBuilder;
		_cmisSessionCache = cmisSessionCache;
		_lockManager = lockManager;
	}

	@Override
	public FileEntry addFileEntry(
			long userId, long folderId, String sourceFileName, String mimeType,
			String title, String description, String changeLog, InputStream is,
			long size, ServiceContext serviceContext)
		throws PortalException {

		if (Validator.isNull(title)) {
			if (size == 0) {
				throw new FileNameException("Title is null");
			}

			title = sourceFileName;
		}

		try {
			Session session = getSession();

			validateTitle(session, folderId, title);

			org.apache.chemistry.opencmis.client.api.Folder cmisFolder =
				getCmisFolder(session, folderId);

			Map<String, Object> properties = new HashMap<>();

			properties.put(PropertyIds.NAME, title);
			properties.put(
				PropertyIds.OBJECT_TYPE_ID, BaseTypeId.CMIS_DOCUMENT.value());

			ContentStream contentStream = new ContentStreamImpl(
				title, BigInteger.valueOf(size), mimeType, is);

			Document document = null;

			if (_cmisRepositoryDetector.isNuxeo5_5OrHigher()) {
				document = cmisFolder.createDocument(
					properties, contentStream, VersioningState.NONE);

				document.checkIn(
					true, Collections.<String, Object>emptyMap(), null,
					StringPool.BLANK);
			}
			else {
				document = cmisFolder.createDocument(
					properties, contentStream, null);
			}

			return toFileEntry(document);
		}
		catch (PortalException | SystemException e) {
			throw e;
		}
		catch (Exception e) {
			processException(e);

			throw new RepositoryException(e);
		}
	}

	@Override
	public FileShortcut addFileShortcut(
		long userId, long folderId, long toFileEntryId,
		ServiceContext serviceContext) {

		throw new UnsupportedOperationException();
	}

	@Override
	public Folder addFolder(
			long userId, long parentFolderId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException {

		try {
			Session session = getSession();

			validateTitle(session, parentFolderId, name);

			org.apache.chemistry.opencmis.client.api.Folder cmisFolder =
				getCmisFolder(session, parentFolderId);

			Map<String, Object> properties = new HashMap<>();

			properties.put(PropertyIds.NAME, name);
			properties.put(
				PropertyIds.OBJECT_TYPE_ID, BaseTypeId.CMIS_FOLDER.value());

			return toFolder(cmisFolder.createFolder(properties));
		}
		catch (PortalException | SystemException e) {
			throw e;
		}
		catch (Exception e) {
			processException(e);

			throw new RepositoryException(e);
		}
	}

	@Override
	public FileVersion cancelCheckOut(long fileEntryId) throws PortalException {
		Document draftDocument = null;

		try {
			Session session = getSession();

			String versionSeriesId = toFileEntryId(fileEntryId);

			Document document = (Document)session.getObject(versionSeriesId);

			document.refresh();

			String versionSeriesCheckedOutId =
				document.getVersionSeriesCheckedOutId();

			if (Validator.isNotNull(versionSeriesCheckedOutId)) {
				draftDocument = (Document)session.getObject(
					versionSeriesCheckedOutId);

				draftDocument.cancelCheckOut();

				document = (Document)session.getObject(versionSeriesId);

				document.refresh();
			}
		}
		catch (Exception e) {
			_log.error(
				"Unable to cancel checkout for file entry with {fileEntryId=" +
					fileEntryId + "}",
				e);
		}

		if (draftDocument != null) {
			return toFileVersion(null, draftDocument);
		}

		return null;
	}

	@Override
	public void checkInFileEntry(
		long userId, long fileEntryId,
		DLVersionNumberIncrease dlVersionNumberIncrease, String changeLog,
		ServiceContext serviceContext) {

		try {
			clearManualCheckInRequired(fileEntryId, serviceContext);

			Session session = getSession();

			String versionSeriesId = toFileEntryId(fileEntryId);

			Document document = (Document)session.getObject(versionSeriesId);

			document.refresh();

			String versionSeriesCheckedOutId =
				document.getVersionSeriesCheckedOutId();

			if (Validator.isNotNull(versionSeriesCheckedOutId)) {
				boolean major = false;

				if (!isSupportsMinorVersions() ||
					(dlVersionNumberIncrease ==
						DLVersionNumberIncrease.MAJOR)) {

					major = true;
				}

				document = (Document)session.getObject(
					versionSeriesCheckedOutId);

				document.checkIn(major, null, null, changeLog);

				document = (Document)session.getObject(versionSeriesId);

				document.refresh();
			}
		}
		catch (Exception e) {
			_log.error(
				"Unable to check in file entry with {fileEntryId=" +
					fileEntryId + "}",
				e);
		}
	}

	@Override
	public void checkInFileEntry(
		long userId, long fileEntryId, String lockUuid,
		ServiceContext serviceContext) {

		checkInFileEntry(
			userId, fileEntryId, DLVersionNumberIncrease.MINOR,
			StringPool.BLANK, serviceContext);
	}

	@Override
	public FileEntry checkOutFileEntry(
			long fileEntryId, ServiceContext serviceContext)
		throws PortalException {

		try {
			setManualCheckInRequired(fileEntryId, serviceContext);

			Session session = getSession();

			String versionSeriesId = toFileEntryId(fileEntryId);

			Document document = (Document)session.getObject(versionSeriesId);

			document.refresh();

			document.checkOut();

			document = (Document)session.getObject(versionSeriesId);

			document.refresh();
		}
		catch (Exception e) {
			_log.error(
				"Unable checkout file entry with {fileEntryId=" + fileEntryId +
					"}",
				e);
		}

		return getFileEntry(fileEntryId);
	}

	@Override
	public FileEntry checkOutFileEntry(
		long fileEntryId, String owner, long expirationTime,
		ServiceContext serviceContext) {

		throw new UnsupportedOperationException();
	}

	@Override
	public FileEntry copyFileEntry(
			long userId, long groupId, long fileEntryId, long destFolderId,
			ServiceContext serviceContext)
		throws PortalException {

		try {
			Session session = getSession();

			Document document = getDocument(session, fileEntryId);

			validateTitle(session, destFolderId, document.getName());

			String destFolderObjectId = toFolderId(session, destFolderId);

			Document newDocument = document.copy(
				new ObjectIdImpl(destFolderObjectId));

			return toFileEntry(newDocument);
		}
		catch (CmisObjectNotFoundException confe) {
			throw new NoSuchFolderException(
				"No CMIS folder with {folderId=" + destFolderId + "}", confe);
		}
		catch (PortalException | SystemException e) {
			throw e;
		}
		catch (Exception e) {
			processException(e);

			throw new RepositoryException(e);
		}
	}

	@Override
	public void deleteFileEntry(long fileEntryId) throws PortalException {
		try {
			Document document = getDocument(getSession(), fileEntryId);

			deleteMappedFileEntry(document);

			document.deleteAllVersions();
		}
		catch (PortalException | SystemException e) {
			throw e;
		}
		catch (Exception e) {
			processException(e);

			throw new RepositoryException(e);
		}
	}

	@Override
	public void deleteFileShortcut(long fileShortcutId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteFileShortcuts(long toFileEntryId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteFolder(long folderId) throws PortalException {
		try {
			org.apache.chemistry.opencmis.client.api.Folder cmisFolder =
				getCmisFolder(getSession(), folderId);

			deleteMappedFolder(cmisFolder);

			cmisFolder.deleteTree(true, UnfileObject.DELETE, false);
		}
		catch (PortalException | SystemException e) {
			throw e;
		}
		catch (Exception e) {
			processException(e);

			throw new RepositoryException(e);
		}
	}

	public CMISRepositoryDetector getCMISRepositoryDetector() {
		return _cmisRepositoryDetector;
	}

	@Override
	public List<FileEntry> getFileEntries(
		long folderId, int status, int start, int end,
		OrderByComparator<FileEntry> obc) {

		return getFileEntries(folderId, start, end, obc);
	}

	@Override
	public List<FileEntry> getFileEntries(
		long folderId, int start, int end, OrderByComparator<FileEntry> obc) {

		return subList(getFileEntries(folderId), start, end, obc);
	}

	@Override
	public List<FileEntry> getFileEntries(
		long folderId, long fileEntryTypeId, int start, int end,
		OrderByComparator<FileEntry> obc) {

		return new ArrayList<>();
	}

	@Override
	public List<FileEntry> getFileEntries(
			long folderId, String[] mimeTypes, int start, int end,
			OrderByComparator<FileEntry> obc)
		throws PortalException {

		List<FileEntry> fileEntries = _cmisModelCache.getFileEntries(folderId);

		if ((fileEntries == null) || (mimeTypes != null)) {
			fileEntries = new ArrayList<>();

			List<String> documentIds = getDocumentIds(
				getSession(), folderId, mimeTypes);

			for (String documentId : documentIds) {
				FileEntry fileEntry = toFileEntry(documentId);

				fileEntries.add(fileEntry);
			}

			if (mimeTypes == null) {
				_cmisModelCache.putFileEntries(folderId, fileEntries);
			}
		}

		return subList(fileEntries, start, end, obc);
	}

	@Override
	public int getFileEntriesCount(long folderId) {
		List<FileEntry> fileEntries = getFileEntries(folderId);

		return fileEntries.size();
	}

	@Override
	public int getFileEntriesCount(long folderId, int status) {
		List<FileEntry> fileEntries = getFileEntries(folderId);

		return fileEntries.size();
	}

	@Override
	public int getFileEntriesCount(long folderId, long fileEntryTypeId) {
		List<FileEntry> fileEntries = getFileEntries(folderId, fileEntryTypeId);

		return fileEntries.size();
	}

	@Override
	public int getFileEntriesCount(long folderId, String[] mimeTypes)
		throws PortalException {

		List<String> documentIds = getDocumentIds(
			getSession(), folderId, mimeTypes);

		return documentIds.size();
	}

	@Override
	public FileEntry getFileEntry(long fileEntryId) throws PortalException {
		try {
			FileEntry fileEntry = _cmisModelCache.getFileEntry(fileEntryId);

			if (fileEntry == null) {
				Document document = getDocument(getSession(), fileEntryId);

				fileEntry = toFileEntry(document);

				_cmisModelCache.putFileEntry(fileEntry);
			}

			return fileEntry;
		}
		catch (PortalException | SystemException e) {
			throw e;
		}
		catch (Exception e) {
			processException(e);

			throw new RepositoryException(e);
		}
	}

	@Override
	public FileEntry getFileEntry(long folderId, String title)
		throws PortalException {

		try {
			Session session = getSession();

			String objectId = getObjectId(session, folderId, true, title);

			if (objectId != null) {
				CmisObject cmisObject = session.getObject(objectId);

				Document document = (Document)cmisObject;

				return toFileEntry(document);
			}
		}
		catch (CmisObjectNotFoundException confe) {
			throw new NoSuchFileEntryException(
				StringBundler.concat(
					"No CMIS file entry with {folderId=", folderId, ", title=",
					title, "}"),
				confe);
		}
		catch (PortalException | SystemException e) {
			throw e;
		}
		catch (Exception e) {
			processException(e);

			throw new RepositoryException(e);
		}

		throw new NoSuchFileEntryException(
			StringBundler.concat(
				"No CMIS file entry with {folderId=", folderId, ", title=",
				title, "}"));
	}

	@Override
	public FileEntry getFileEntryByUuid(String uuid) throws PortalException {
		try {
			Session session = getSession();

			RepositoryEntry repositoryEntry =
				repositoryEntryLocalService.getRepositoryEntry(
					uuid, getGroupId());

			String objectId = repositoryEntry.getMappedId();

			return toFileEntry((Document)session.getObject(objectId));
		}
		catch (CmisObjectNotFoundException confe) {
			throw new NoSuchFileEntryException(
				"No CMIS file entry with {uuid=" + uuid + "}", confe);
		}
		catch (NoSuchRepositoryEntryException nsree) {
			throw new NoSuchFileEntryException(nsree);
		}
		catch (SystemException se) {
			throw se;
		}
		catch (Exception e) {
			processException(e);

			throw new RepositoryException(e);
		}
	}

	@Override
	public FileShortcut getFileShortcut(long fileShortcutId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public FileVersion getFileVersion(long fileVersionId)
		throws PortalException {

		try {
			return getFileVersion(getSession(), null, fileVersionId);
		}
		catch (PortalException | SystemException e) {
			throw e;
		}
		catch (Exception e) {
			processException(e);

			throw new RepositoryException(e);
		}
	}

	@Override
	public Folder getFolder(long folderId) throws PortalException {
		try {
			return getFolder(getSession(), folderId);
		}
		catch (PortalException | SystemException e) {
			throw e;
		}
		catch (Exception e) {
			processException(e);

			throw new RepositoryException(e);
		}
	}

	@Override
	public Folder getFolder(long parentFolderId, String name)
		throws PortalException {

		try {
			Session session = getSession();

			String objectId = getObjectId(session, parentFolderId, false, name);

			if (objectId != null) {
				CmisObject cmisObject = session.getObject(objectId);

				return toFolder(
					(org.apache.chemistry.opencmis.client.api.Folder)
						cmisObject);
			}
		}
		catch (CmisObjectNotFoundException confe) {
			throw new NoSuchFolderException(
				StringBundler.concat(
					"No CMIS folder with {parentFolderId=", parentFolderId,
					", name=", name, "}"),
				confe);
		}
		catch (PortalException | SystemException e) {
			throw e;
		}
		catch (Exception e) {
			processException(e);

			throw new RepositoryException(e);
		}

		throw new NoSuchFolderException(
			StringBundler.concat(
				"No CMIS folder with {parentFolderId=", parentFolderId,
				", name=", name, "}"));
	}

	@Override
	public List<Folder> getFolders(
			long parentFolderId, boolean includeMountfolders, int start,
			int end, OrderByComparator<Folder> obc)
		throws PortalException {

		return subList(getFolders(parentFolderId), start, end, obc);
	}

	@Override
	public List<Object> getFoldersAndFileEntries(
		long folderId, int start, int end, OrderByComparator<?> obc) {

		return subList(
			getFoldersAndFileEntries(folderId), start, end,
			(OrderByComparator<Object>)obc);
	}

	@Override
	public List<Object> getFoldersAndFileEntries(
			long folderId, String[] mimeTypes, int start, int end,
			OrderByComparator<?> obc)
		throws PortalException {

		List<Object> foldersAndFileEntries =
			_cmisModelCache.getFoldersAndFileEntries(folderId);

		if ((foldersAndFileEntries == null) || (mimeTypes != null)) {
			foldersAndFileEntries = new ArrayList<>();

			foldersAndFileEntries.addAll(getFolders(folderId));

			List<FileEntry> fileEntries = getFileEntries(
				folderId, mimeTypes, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				null);

			foldersAndFileEntries.addAll(fileEntries);

			if (mimeTypes == null) {
				_cmisModelCache.putFoldersAndFileEntries(
					folderId, foldersAndFileEntries);
			}
		}

		return subList(
			foldersAndFileEntries, start, end, (OrderByComparator<Object>)obc);
	}

	@Override
	public int getFoldersAndFileEntriesCount(long folderId) {
		List<Object> foldersAndFileEntries = getFoldersAndFileEntries(folderId);

		return foldersAndFileEntries.size();
	}

	@Override
	public int getFoldersAndFileEntriesCount(long folderId, String[] mimeTypes)
		throws PortalException {

		if (ArrayUtil.isNotEmpty(mimeTypes)) {
			List<Folder> folders = getFolders(folderId);

			List<String> documentIds = getDocumentIds(
				getSession(), folderId, mimeTypes);

			return folders.size() + documentIds.size();
		}

		List<Object> foldersAndFileEntries = getFoldersAndFileEntries(folderId);

		return foldersAndFileEntries.size();
	}

	@Override
	public int getFoldersCount(long parentFolderId, boolean includeMountfolders)
		throws PortalException {

		List<Folder> folders = getFolders(parentFolderId);

		return folders.size();
	}

	@Override
	public int getFoldersFileEntriesCount(List<Long> folderIds, int status) {
		int count = 0;

		for (long folderId : folderIds) {
			List<FileEntry> fileEntries = getFileEntries(folderId);

			count += fileEntries.size();
		}

		return count;
	}

	@Override
	public String getLatestVersionId(String objectId) {
		try {
			Session session = getSession();

			Document document = (Document)session.getObject(objectId);

			List<Document> documentVersions = document.getAllVersions();

			document = documentVersions.get(0);

			return document.getId();
		}
		catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	@Override
	public List<Folder> getMountFolders(
		long parentFolderId, int start, int end,
		OrderByComparator<Folder> obc) {

		return new ArrayList<>();
	}

	@Override
	public int getMountFoldersCount(long parentFolderId) {
		return 0;
	}

	@Override
	public String getObjectName(String objectId) throws PortalException {
		Session session = getSession();

		CmisObject cmisObject = session.getObject(objectId);

		return cmisObject.getName();
	}

	@Override
	public List<String> getObjectPaths(String objectId) throws PortalException {
		Session session = getSession();

		CmisObject cmisObject = session.getObject(objectId);

		if (cmisObject instanceof FileableCmisObject) {
			FileableCmisObject fileableCmisObject =
				(FileableCmisObject)cmisObject;

			return fileableCmisObject.getPaths();
		}

		throw new RepositoryException(
			"CMIS object is unfileable for id " + objectId);
	}

	public Session getSession() throws PortalException {
		Session session = _cmisSessionCache.get(_sessionKey);

		if (session == null) {
			SessionImpl sessionImpl =
				(SessionImpl)_cmisRepositoryHandler.getSession();

			session = sessionImpl.getSession();

			_cmisSessionCache.put(_sessionKey, session);
		}

		if (_cmisRepositoryDetector == null) {
			_cmisRepositoryDetector = new CMISRepositoryDetector(
				session.getRepositoryInfo());
		}

		return session;
	}

	@Override
	public void getSubfolderIds(List<Long> folderIds, long folderId) {
		try {
			List<Folder> subfolders = getFolders(
				folderId, false, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

			getSubfolderIds(folderIds, subfolders, true);
		}
		catch (SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	@Override
	public List<Long> getSubfolderIds(long folderId, boolean recurse) {
		try {
			List<Long> subfolderIds = new ArrayList<>();

			List<Folder> subfolders = getFolders(
				folderId, false, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

			getSubfolderIds(subfolderIds, subfolders, recurse);

			return subfolderIds;
		}
		catch (SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x)
	 */
	@Deprecated
	@Override
	public String[][] getSupportedParameters() {
		return _cmisRepositoryHandler.getSupportedParameters();
	}

	@Override
	public void initRepository() throws PortalException {
		try {
			_sessionKey = Session.class.getName(
			).concat(
				StringPool.POUND
			).concat(
				String.valueOf(getRepositoryId())
			);

			Session session = getSession();

			session.getRepositoryInfo();
		}
		catch (PortalException | SystemException e) {
			throw e;
		}
		catch (Exception e) {
			processException(e);

			throw new RepositoryException(
				StringBundler.concat(
					"Unable to initialize CMIS session for repository with ",
					"{repositoryId=", getRepositoryId(), "}"),
				e);
		}
	}

	@Override
	public boolean isCancelCheckOutAllowable(String objectId)
		throws PortalException {

		return isActionAllowable(objectId, Action.CAN_CANCEL_CHECK_OUT);
	}

	@Override
	public boolean isCheckInAllowable(String objectId) throws PortalException {
		return isActionAllowable(objectId, Action.CAN_CHECK_IN);
	}

	@Override
	public boolean isCheckOutAllowable(String objectId) throws PortalException {
		return isActionAllowable(objectId, Action.CAN_CHECK_OUT);
	}

	public boolean isDocumentRetrievableByVersionSeriesId() {
		return _cmisRepositoryHandler.isDocumentRetrievableByVersionSeriesId();
	}

	public boolean isRefreshBeforePermissionCheck() {
		return _cmisRepositoryHandler.isRefreshBeforePermissionCheck();
	}

	@Override
	public boolean isSupportsMinorVersions() throws PortalException {
		try {
			Session session = getSession();

			RepositoryInfo repositoryInfo = session.getRepositoryInfo();

			return _cmisRepositoryHandler.isSupportsMinorVersions(
				repositoryInfo.getProductName());
		}
		catch (PortalException | SystemException e) {
			throw e;
		}
		catch (Exception e) {
			processException(e);

			throw new RepositoryException(e);
		}
	}

	@Override
	public Lock lockFolder(long folderId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Lock lockFolder(
		long folderId, String owner, boolean inheritable, long expirationTime) {

		throw new UnsupportedOperationException();
	}

	@Override
	public FileEntry moveFileEntry(
			long userId, long fileEntryId, long newFolderId,
			ServiceContext serviceContext)
		throws PortalException {

		try {
			Session session = getSession();

			String newFolderObjectId = toFolderId(session, newFolderId);

			Document document = getDocument(session, fileEntryId);

			validateTitle(session, newFolderId, document.getName());

			List<org.apache.chemistry.opencmis.client.api.Folder>
				parentFolders = document.getParents();

			org.apache.chemistry.opencmis.client.api.Folder oldFolder =
				parentFolders.get(0);

			String oldFolderObjectId = oldFolder.getId();

			if (oldFolderObjectId.equals(newFolderObjectId)) {
				return toFileEntry(document);
			}

			document = (Document)document.move(
				new ObjectIdImpl(oldFolderObjectId),
				new ObjectIdImpl(newFolderObjectId));

			String versionSeriesId = toFileEntryId(fileEntryId);

			String newObjectId = document.getVersionSeriesId();

			if (!versionSeriesId.equals(newObjectId)) {
				document = (Document)session.getObject(newObjectId);

				updateMappedId(fileEntryId, document.getVersionSeriesId());
			}

			return toFileEntry(document);
		}
		catch (CmisObjectNotFoundException confe) {
			throw new NoSuchFolderException(
				"No CMIS folder with {folderId=" + newFolderId + "}", confe);
		}
		catch (PortalException | SystemException e) {
			throw e;
		}
		catch (Exception e) {
			processException(e);

			throw new RepositoryException(e);
		}
	}

	@Override
	public Folder moveFolder(
			long userId, long folderId, long parentFolderId,
			ServiceContext serviceContext)
		throws PortalException {

		try {
			Session session = getSession();

			org.apache.chemistry.opencmis.client.api.Folder cmisFolder =
				getCmisFolder(session, folderId);

			validateTitle(session, parentFolderId, cmisFolder.getName());

			org.apache.chemistry.opencmis.client.api.Folder parentCmisFolder =
				cmisFolder.getFolderParent();

			if (parentCmisFolder == null) {
				throw new RepositoryException(
					"Unable to move CMIS root folder with {folderId=" +
						folderId + "}");
			}

			String objectId = toFolderId(session, folderId);

			String sourceFolderId = parentCmisFolder.getId();

			String targetFolderId = toFolderId(session, parentFolderId);

			if (!sourceFolderId.equals(targetFolderId) &&
				!targetFolderId.equals(objectId)) {

				cmisFolder =
					(org.apache.chemistry.opencmis.client.api.Folder)
						cmisFolder.move(
							new ObjectIdImpl(sourceFolderId),
							new ObjectIdImpl(targetFolderId));
			}

			return toFolder(cmisFolder);
		}
		catch (CmisObjectNotFoundException confe) {
			throw new NoSuchFolderException(
				"No CMIS folder with {folderId=" + parentFolderId + "}", confe);
		}
		catch (PortalException | SystemException e) {
			throw e;
		}
		catch (Exception e) {
			processException(e);

			throw new RepositoryException(e);
		}
	}

	@Override
	public Lock refreshFileEntryLock(
		String lockUuid, long companyId, long expirationTime) {

		throw new UnsupportedOperationException();
	}

	@Override
	public Lock refreshFolderLock(
		String lockUuid, long companyId, long expirationTime) {

		throw new UnsupportedOperationException();
	}

	@Override
	public void revertFileEntry(
			long userId, long fileEntryId, String version,
			ServiceContext serviceContext)
		throws PortalException {

		try {
			Document document = getDocument(getSession(), fileEntryId);

			Document oldVersion = null;

			List<Document> documentVersions = document.getAllVersions();

			for (Document currentVersion : documentVersions) {
				String currentVersionLabel = currentVersion.getVersionLabel();

				if (Validator.isNull(currentVersionLabel)) {
					currentVersionLabel = DLFileEntryConstants.VERSION_DEFAULT;
				}

				if (currentVersionLabel.equals(version)) {
					oldVersion = currentVersion;

					break;
				}
			}

			String mimeType = oldVersion.getContentStreamMimeType();
			String changeLog = LanguageUtil.format(
				serviceContext.getLocale(), "reverted-to-x", version, false);
			String title = oldVersion.getName();
			ContentStream contentStream = oldVersion.getContentStream();

			updateFileEntry(
				userId, fileEntryId, contentStream.getFileName(), mimeType,
				title, StringPool.BLANK, changeLog,
				DLVersionNumberIncrease.MAJOR, contentStream.getStream(),
				contentStream.getLength(), serviceContext);
		}
		catch (PortalException | SystemException e) {
			throw e;
		}
		catch (Exception e) {
			processException(e);

			throw new RepositoryException(e);
		}
	}

	@Override
	public Hits search(long creatorUserId, int status, int start, int end) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Hits search(
		long creatorUserId, long folderId, String[] mimeTypes, int status,
		int start, int end) {

		throw new UnsupportedOperationException();
	}

	@Override
	public Hits search(SearchContext searchContext, Query query)
		throws SearchException {

		try {
			QueryConfig queryConfig = searchContext.getQueryConfig();

			queryConfig.setScoreEnabled(false);

			return doSearch(searchContext, query);
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	public FileEntry toFileEntry(Document document) throws PortalException {
		return toFileEntry(document, false);
	}

	@Override
	public FileEntry toFileEntry(String objectId) throws PortalException {
		return toFileEntry(objectId, false);
	}

	public FileVersion toFileVersion(FileEntry fileEntry, Document version)
		throws PortalException {

		RepositoryEntry repositoryEntry = getRepositoryEntry(version.getId());

		return new CMISFileVersion(
			this, fileEntry, repositoryEntry.getUuid(),
			repositoryEntry.getRepositoryEntryId(), version);
	}

	public Folder toFolder(
			org.apache.chemistry.opencmis.client.api.Folder cmisFolder)
		throws PortalException {

		RepositoryEntry repositoryEntry = getRepositoryEntry(
			cmisFolder.getId());

		return new CMISFolder(
			this, repositoryEntry.getUuid(),
			repositoryEntry.getRepositoryEntryId(), cmisFolder);
	}

	@Override
	public Folder toFolder(String objectId) throws PortalException {
		try {
			Session session = getSession();

			org.apache.chemistry.opencmis.client.api.Folder cmisFolder =
				(org.apache.chemistry.opencmis.client.api.Folder)
					session.getObject(objectId);

			return toFolder(cmisFolder);
		}
		catch (CmisObjectNotFoundException confe) {
			throw new NoSuchFolderException(
				"No CMIS folder with {objectId=" + objectId + "}", confe);
		}
		catch (SystemException se) {
			throw se;
		}
		catch (Exception e) {
			processException(e);

			throw new RepositoryException(e);
		}
	}

	@Override
	public void unlockFolder(long folderId, String lockUuid) {
		throw new UnsupportedOperationException();
	}

	@Override
	public FileEntry updateFileEntry(
			long userId, long fileEntryId, String sourceFileName,
			String mimeType, String title, String description, String changeLog,
			DLVersionNumberIncrease dlVersionNumberIncrease, InputStream is,
			long size, ServiceContext serviceContext)
		throws PortalException {

		Document document = null;

		ObjectId checkOutDocumentObjectId = null;

		try {
			Session session = getSession();

			document = getDocument(session, fileEntryId);

			String versionSeriesCheckedOutId =
				document.getVersionSeriesCheckedOutId();

			if (Validator.isNotNull(versionSeriesCheckedOutId)) {
				document = (Document)session.getObject(
					versionSeriesCheckedOutId);

				document.refresh();
			}

			String currentTitle = document.getName();

			AllowableActions allowableActions = document.getAllowableActions();

			Set<Action> allowableActionsSet =
				allowableActions.getAllowableActions();

			if (allowableActionsSet.contains(Action.CAN_CHECK_OUT)) {
				checkOutDocumentObjectId = document.checkOut();

				document = (Document)session.getObject(
					checkOutDocumentObjectId);
			}

			Map<String, Object> properties = null;

			ContentStream contentStream = null;

			if (Validator.isNotNull(title) && !title.equals(currentTitle)) {
				properties = new HashMap<>();

				properties.put(PropertyIds.NAME, title);
			}

			if (is != null) {
				contentStream = new ContentStreamImpl(
					sourceFileName, BigInteger.valueOf(size), mimeType, is);
			}

			checkUpdatable(allowableActionsSet, properties, contentStream);

			if (checkOutDocumentObjectId != null) {
				boolean majorVersion = false;

				if (!isSupportsMinorVersions() ||
					(dlVersionNumberIncrease ==
						DLVersionNumberIncrease.MAJOR)) {

					majorVersion = true;
				}

				document.checkIn(
					majorVersion, properties, contentStream, changeLog);

				checkOutDocumentObjectId = null;
			}
			else {
				if (properties != null) {
					document = (Document)document.updateProperties(properties);
				}

				if (contentStream != null) {
					document.setContentStream(contentStream, true, false);
				}
			}

			String versionSeriesId = toFileEntryId(fileEntryId);

			document = (Document)session.getObject(versionSeriesId);

			return toFileEntry(document);
		}
		catch (PortalException | SystemException e) {
			throw e;
		}
		catch (Exception e) {
			processException(e);

			throw new RepositoryException(e);
		}
		finally {
			if (checkOutDocumentObjectId != null) {
				document.cancelCheckOut();
			}
		}
	}

	@Override
	public FileEntry updateFileEntry(
			String objectId, String mimeType, Map<String, Object> properties,
			InputStream is, String sourceFileName, long size,
			ServiceContext serviceContext)
		throws PortalException {

		try {
			Session session = getSession();

			Document document = (Document)session.getObject(objectId);

			AllowableActions allowableActions = document.getAllowableActions();

			Set<Action> allowableActionsSet =
				allowableActions.getAllowableActions();

			ContentStream contentStream = null;

			if (is != null) {
				is = new Base64.InputStream(is, Base64.ENCODE);

				contentStream = new ContentStreamImpl(
					sourceFileName, BigInteger.valueOf(size), mimeType, is);
			}

			checkUpdatable(allowableActionsSet, properties, contentStream);

			if (properties != null) {
				document = (Document)document.updateProperties(properties);
			}

			if (contentStream != null) {
				document.setContentStream(contentStream, true, false);
			}

			return toFileEntry(document);
		}
		catch (PortalException | SystemException e) {
			throw e;
		}
		catch (Exception e) {
			processException(e);

			throw new RepositoryException(e);
		}
	}

	@Override
	public FileShortcut updateFileShortcut(
		long userId, long fileShortcutId, long folderId, long toFileEntryId,
		ServiceContext serviceContext) {

		throw new UnsupportedOperationException();
	}

	@Override
	public void updateFileShortcuts(
		long oldToFileEntryId, long newToFileEntryId) {

		throw new UnsupportedOperationException();
	}

	@Override
	public Folder updateFolder(
			long folderId, String name, String description,
			ServiceContext serviceContext)
		throws PortalException {

		try {
			Session session = getSession();

			String objectId = toFolderId(session, folderId);

			org.apache.chemistry.opencmis.client.api.Folder cmisFolder =
				(org.apache.chemistry.opencmis.client.api.Folder)
					session.getObject(objectId);

			String currentTitle = cmisFolder.getName();

			Map<String, Object> properties = new HashMap<>();

			if (Validator.isNotNull(name) && !name.equals(currentTitle)) {
				properties.put(PropertyIds.NAME, name);
			}

			ObjectId cmisFolderObjectId = cmisFolder.updateProperties(
				properties, true);

			String newObjectId = cmisFolderObjectId.getId();

			if (!objectId.equals(newObjectId)) {
				cmisFolder =
					(org.apache.chemistry.opencmis.client.api.Folder)
						session.getObject(newObjectId);

				updateMappedId(folderId, newObjectId);
			}

			return toFolder(cmisFolder);
		}
		catch (CmisObjectNotFoundException confe) {
			throw new NoSuchFolderException(
				"No CMIS folder with {folderId=" + folderId + "}", confe);
		}
		catch (PortalException | SystemException e) {
			throw e;
		}
		catch (Exception e) {
			processException(e);

			throw new RepositoryException(e);
		}
	}

	@Override
	public boolean verifyFileEntryCheckOut(long fileEntryId, String lockUuid) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean verifyInheritableLock(long folderId, String lockUuid) {
		throw new UnsupportedOperationException();
	}

	protected void cacheFoldersAndFileEntries(long folderId) {
		try {
			if (_cmisModelCache.getFoldersAndFileEntries(folderId) != null) {
				return;
			}

			List<Object> foldersAndFileEntries = new ArrayList<>();
			List<Folder> folders = new ArrayList<>();
			List<FileEntry> fileEntries = new ArrayList<>();

			Session session = getSession();

			org.apache.chemistry.opencmis.client.api.Folder cmisParentFolder =
				getCmisFolder(session, folderId);

			Folder parentFolder = toFolder(cmisParentFolder);

			OperationContext operationContext =
				session.createOperationContext();

			operationContext.setFilter(
				_toSet(
					"cmis:contentStreamLength", "cmis:isPrivateWorkingCopy",
					"cmis:isVersionSeriesCheckedOut",
					"cmis:lastModificationDate", "cmis:lastModifiedBy",
					"cmis:name", "cmis:versionLabel", "cmis:versionSeriesId"));

			ItemIterable<CmisObject> cmisObjects = cmisParentFolder.getChildren(
				operationContext);

			for (CmisObject cmisObject : cmisObjects) {
				if (cmisObject instanceof
						org.apache.chemistry.opencmis.client.api.Folder) {

					CMISFolder cmisFolder = (CMISFolder)toFolder(
						(org.apache.chemistry.opencmis.client.api.Folder)
							cmisObject);

					cmisFolder.setParentFolder(parentFolder);

					foldersAndFileEntries.add(cmisFolder);
					folders.add(cmisFolder);

					_cmisModelCache.putFolder(cmisFolder);
				}
				else if (cmisObject instanceof Document) {
					Document document = (Document)cmisObject;

					CMISFileEntry cmisFileEntry = (CMISFileEntry)toFileEntry(
						document);

					cmisFileEntry.setParentFolder(parentFolder);

					Boolean privateWorkingCopy =
						document.isPrivateWorkingCopy();

					if (((privateWorkingCopy != null) && privateWorkingCopy) ||
						Objects.equals(document.getVersionLabel(), "pwc")) {

						foldersAndFileEntries.remove(cmisFileEntry);
						fileEntries.remove(cmisFileEntry);
					}

					foldersAndFileEntries.add(cmisFileEntry);
					fileEntries.add(cmisFileEntry);

					_cmisModelCache.putFileEntry(cmisFileEntry);
				}
			}

			_cmisModelCache.putFoldersAndFileEntries(
				folderId, foldersAndFileEntries);

			_cmisModelCache.putFolders(folderId, folders);

			_cmisModelCache.putFileEntries(folderId, fileEntries);
		}
		catch (SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new RepositoryException(e);
		}
	}

	protected void checkUpdatable(
			Set<Action> allowableActionsSet, Map<String, Object> properties,
			ContentStream contentStream)
		throws PrincipalException {

		if ((properties != null) &&
			!allowableActionsSet.contains(Action.CAN_UPDATE_PROPERTIES)) {

			throw new PrincipalException.MustHavePermission(
				0, Action.CAN_UPDATE_PROPERTIES.toString());
		}

		if ((contentStream != null) &&
			!allowableActionsSet.contains(Action.CAN_SET_CONTENT_STREAM)) {

			throw new PrincipalException.MustHavePermission(
				0, Action.CAN_SET_CONTENT_STREAM.toString());
		}
	}

	protected void deleteMappedFileEntry(Document document)
		throws PortalException {

		if (_cmisRepositoryConfiguration.deleteDepth() == _DELETE_NONE) {
			return;
		}

		List<Document> documentVersions = document.getAllVersions();

		List<String> mappedIds = new ArrayList<>(documentVersions.size() + 1);

		for (Document version : documentVersions) {
			mappedIds.add(version.getId());
		}

		mappedIds.add(document.getId());

		repositoryEntryLocalService.deleteRepositoryEntries(
			getRepositoryId(), mappedIds);
	}

	protected void deleteMappedFolder(
			org.apache.chemistry.opencmis.client.api.Folder cmisFolder)
		throws PortalException {

		if (_cmisRepositoryConfiguration.deleteDepth() == _DELETE_NONE) {
			return;
		}

		ItemIterable<CmisObject> cmisObjects = cmisFolder.getChildren();

		for (CmisObject cmisObject : cmisObjects) {
			if (cmisObject instanceof Document) {
				Document document = (Document)cmisObject;

				deleteMappedFileEntry(document);
			}
			else if (cmisObject instanceof
						org.apache.chemistry.opencmis.client.api.Folder) {

				org.apache.chemistry.opencmis.client.api.Folder cmisSubfolder =
					(org.apache.chemistry.opencmis.client.api.Folder)cmisObject;

				try {
					repositoryEntryLocalService.deleteRepositoryEntry(
						getRepositoryId(), cmisObject.getId());

					if (_cmisRepositoryConfiguration.deleteDepth() ==
							_DELETE_DEEP) {

						deleteMappedFolder(cmisSubfolder);
					}
				}
				catch (NoSuchRepositoryEntryException nsree) {
					if (_log.isWarnEnabled()) {
						_log.warn(nsree, nsree);
					}
				}
			}
		}
	}

	protected Hits doSearch(SearchContext searchContext, Query query)
		throws Exception {

		long startTime = System.currentTimeMillis();

		Session session = getSession();

		RepositoryInfo repositoryInfo = session.getRepositoryInfo();

		RepositoryCapabilities repositoryCapabilities =
			repositoryInfo.getCapabilities();

		QueryConfig queryConfig = searchContext.getQueryConfig();

		CapabilityQuery capabilityQuery =
			repositoryCapabilities.getQueryCapability();

		queryConfig.setAttribute("capabilityQuery", capabilityQuery.value());

		queryConfig.setAttribute(
			"repositoryProductName", repositoryInfo.getProductName());
		queryConfig.setAttribute(
			"repositoryProductVersion", repositoryInfo.getProductVersion());

		String queryString = _cmisSearchQueryBuilder.buildQuery(
			searchContext, query);

		if (_cmisRepositoryDetector.isNuxeo5_4()) {
			queryString +=
				" AND (" + PropertyIds.IS_LATEST_VERSION + " = true)";
		}

		if (_log.isDebugEnabled()) {
			_log.debug("CMIS search query: " + queryString);
		}

		boolean searchAllVersions =
			_cmisRepositoryDetector.isNuxeo5_5OrHigher();

		ItemIterable<QueryResult> queryResults = session.query(
			queryString, searchAllVersions);

		int start = searchContext.getStart();
		int end = searchContext.getEnd();

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS)) {
			start = 0;
		}

		int total = 0;

		List<com.liferay.portal.kernel.search.Document> documents =
			new ArrayList<>();
		List<String> snippets = new ArrayList<>();
		List<Float> scores = new ArrayList<>();

		for (QueryResult queryResult : queryResults) {
			total++;

			if (total <= start) {
				continue;
			}

			if ((total > end) && (end != QueryUtil.ALL_POS)) {
				continue;
			}

			String objectId = queryResult.getPropertyValueByQueryName(
				PropertyIds.OBJECT_ID);

			if (_log.isDebugEnabled()) {
				_log.debug("Search result object ID " + objectId);
			}

			FileEntry fileEntry = null;

			try {
				fileEntry = toFileEntry(objectId, true);
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					Throwable cause = e.getCause();

					if (cause != null) {
						cause = cause.getCause();
					}

					if (cause instanceof CmisObjectNotFoundException) {
						_log.debug(
							"Search result ignored for CMIS document which " +
								"has a version with an invalid object ID " +
									cause.getMessage());
					}
					else {
						_log.debug(
							"Search result ignored for invalid object ID", e);
					}
				}

				total--;

				continue;
			}

			com.liferay.portal.kernel.search.Document document =
				new DocumentImpl();

			DocumentHelper documentHelper = new DocumentHelper(document);

			documentHelper.setEntryKey(
				fileEntry.getModelClassName(), fileEntry.getFileEntryId());

			document.addKeyword(Field.TITLE, fileEntry.getTitle());

			documents.add(document);

			if (queryConfig.isScoreEnabled()) {
				Object scoreObj = queryResult.getPropertyValueByQueryName(
					"HITS");

				if (scoreObj != null) {
					scores.add(Float.valueOf(scoreObj.toString()));
				}
				else {
					scores.add(1.0F);
				}
			}
			else {
				scores.add(1.0F);
			}

			snippets.add(StringPool.BLANK);
		}

		float searchTime =
			(float)(System.currentTimeMillis() - startTime) / Time.SECOND;

		Hits hits = new HitsImpl();

		hits.setDocs(
			documents.toArray(
				new com.liferay.portal.kernel.search.Document[0]));
		hits.setLength(total);
		hits.setQuery(query);
		hits.setQueryTerms(new String[0]);
		hits.setScores(ArrayUtil.toFloatArray(scores));
		hits.setSearchTime(searchTime);
		hits.setSnippets(snippets.toArray(new String[0]));
		hits.setStart(startTime);

		return hits;
	}

	protected org.apache.chemistry.opencmis.client.api.Folder getCmisFolder(
			Session session, long folderId)
		throws PortalException {

		Folder folder = getFolder(session, folderId);

		return (org.apache.chemistry.opencmis.client.api.Folder)
			folder.getModel();
	}

	protected List<String> getCmisFolderIds(Session session, long folderId)
		throws PortalException {

		StringBundler sb = new StringBundler(4);

		sb.append("SELECT cmis:objectId FROM cmis:folder");

		if (folderId > 0) {
			sb.append(" WHERE IN_FOLDER(");

			String objectId = toFolderId(session, folderId);

			sb.append(StringUtil.quote(objectId));

			sb.append(StringPool.CLOSE_PARENTHESIS);
		}

		String query = sb.toString();

		if (_log.isDebugEnabled()) {
			_log.debug("Calling query " + query);
		}

		ItemIterable<QueryResult> queryResults = session.query(
			query, isAllVersionsSearchableSupported(session));

		List<String> cmsFolderIds = new ArrayList<>();

		for (QueryResult queryResult : queryResults) {
			PropertyData<String> propertyData = queryResult.getPropertyById(
				PropertyIds.OBJECT_ID);

			List<String> values = propertyData.getValues();

			String value = values.get(0);

			cmsFolderIds.add(value);
		}

		return cmsFolderIds;
	}

	protected Document getDocument(Session session, long fileEntryId)
		throws PortalException {

		try {
			String versionSeriesId = toFileEntryId(fileEntryId);

			CmisObject object = session.getObject(versionSeriesId);

			if (!(object instanceof Document)) {
				throw new NoSuchFileEntryException(
					"No CMIS file entry with {fileEntryId=" + fileEntryId +
						"}");
			}

			return (Document)object;
		}
		catch (CmisObjectNotFoundException confe) {
			throw new NoSuchFileEntryException(
				"No CMIS file entry with {fileEntryId=" + fileEntryId + "}",
				confe);
		}
	}

	protected List<String> getDocumentIds(
			Session session, long folderId, String[] mimeTypes)
		throws PortalException {

		StringBundler sb = new StringBundler();

		sb.append("SELECT cmis:objectId FROM cmis:document");

		if (ArrayUtil.isNotEmpty(mimeTypes)) {
			sb.append(" WHERE cmis:contentStreamMimeType IN (");

			for (int i = 0; i < mimeTypes.length; i++) {
				sb.append(StringUtil.quote(mimeTypes[i]));

				if ((i + 1) < mimeTypes.length) {
					sb.append(", ");
				}
			}

			sb.append(StringPool.CLOSE_PARENTHESIS);
		}

		if (folderId > 0) {
			if (ArrayUtil.isNotEmpty(mimeTypes)) {
				sb.append(" AND ");
			}
			else {
				sb.append(" WHERE ");
			}

			sb.append("IN_FOLDER(");

			String objectId = toFolderId(session, folderId);

			sb.append(StringUtil.quote(objectId));

			sb.append(StringPool.CLOSE_PARENTHESIS);
		}

		String query = sb.toString();

		if (_log.isDebugEnabled()) {
			_log.debug("Calling query " + query);
		}

		ItemIterable<QueryResult> queryResults = session.query(query, false);

		List<String> cmisDocumentIds = new ArrayList<>();

		for (QueryResult queryResult : queryResults) {
			String objectId = queryResult.getPropertyValueByQueryName(
				PropertyIds.OBJECT_ID);

			cmisDocumentIds.add(objectId);
		}

		return cmisDocumentIds;
	}

	protected List<FileEntry> getFileEntries(long folderId) {
		cacheFoldersAndFileEntries(folderId);

		return _cmisModelCache.getFileEntries(folderId);
	}

	protected List<FileEntry> getFileEntries(long folderId, long repositoryId) {
		return new ArrayList<>();
	}

	protected FileVersion getFileVersion(
			Session session, FileEntry fileEntry, long fileVersionId)
		throws PortalException {

		try {
			String objectId = toFileVersionId(fileVersionId);

			return toFileVersion(
				fileEntry, (Document)session.getObject(objectId));
		}
		catch (CmisObjectNotFoundException confe) {
			throw new NoSuchFileVersionException(
				"No CMIS file version with {fileVersionId=" + fileVersionId +
					"}",
				confe);
		}
	}

	protected Folder getFolder(Session session, long folderId)
		throws PortalException {

		try {
			Folder folder = _cmisModelCache.getFolder(folderId);

			if (folder == null) {
				String objectId = toFolderId(session, folderId);

				CmisObject cmisObject = session.getObject(objectId);

				Object object = toFolderOrFileEntry(cmisObject);

				if (!(object instanceof Folder)) {
					throw new NoSuchFolderException(
						"No CMIS folder with {folderId=" + folderId + "}");
				}

				folder = (Folder)object;

				_cmisModelCache.putFolder(folder);
			}

			return folder;
		}
		catch (CmisObjectNotFoundException confe) {
			throw new NoSuchFolderException(
				"No CMIS folder with {folderId=" + folderId + "}", confe);
		}
	}

	protected List<Folder> getFolders(long parentFolderId)
		throws PortalException {

		List<Folder> folders = _cmisModelCache.getFolders(parentFolderId);

		if (folders == null) {
			List<String> folderIds = getCmisFolderIds(
				getSession(), parentFolderId);

			folders = new ArrayList<>(folderIds.size());

			for (String folderId : folderIds) {
				folders.add(toFolder(folderId));
			}

			_cmisModelCache.putFolders(parentFolderId, folders);
		}

		return folders;
	}

	protected List<Object> getFoldersAndFileEntries(long folderId) {
		cacheFoldersAndFileEntries(folderId);

		return _cmisModelCache.getFoldersAndFileEntries(folderId);
	}

	protected String getObjectId(
			Session session, long folderId, boolean fileEntry, String name)
		throws PortalException {

		String objectId = toFolderId(session, folderId);

		StringBundler sb = new StringBundler(7);

		sb.append("SELECT cmis:objectId FROM ");

		if (fileEntry) {
			sb.append("cmis:document ");
		}
		else {
			sb.append("cmis:folder ");
		}

		sb.append("WHERE cmis:name = '");
		sb.append(name);
		sb.append("' AND IN_FOLDER('");
		sb.append(objectId);
		sb.append("')");

		String query = sb.toString();

		if (_log.isDebugEnabled()) {
			_log.debug("Calling query " + query);
		}

		ItemIterable<QueryResult> queryResults = session.query(query, false);

		Iterator<QueryResult> itr = queryResults.iterator();

		if (itr.hasNext()) {
			QueryResult queryResult = itr.next();

			PropertyData<String> propertyData = queryResult.getPropertyById(
				PropertyIds.OBJECT_ID);

			List<String> values = propertyData.getValues();

			return values.get(0);
		}

		return null;
	}

	protected void getSubfolderIds(
			List<Long> subfolderIds, List<Folder> subfolders, boolean recurse)
		throws PortalException {

		for (Folder subfolder : subfolders) {
			long subfolderId = subfolder.getFolderId();

			subfolderIds.add(subfolderId);

			if (recurse) {
				List<Folder> subSubFolders = getFolders(
					subfolderId, false, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null);

				getSubfolderIds(subfolderIds, subSubFolders, recurse);
			}
		}
	}

	protected boolean isActionAllowable(String objectId, Action action)
		throws PortalException {

		Session session = getSession();

		Document document = (Document)session.getObject(objectId);

		AllowableActions allowableActions = document.getAllowableActions();

		Set<Action> allowableActionsSet =
			allowableActions.getAllowableActions();

		if (allowableActionsSet.contains(action)) {
			return true;
		}

		return false;
	}

	protected boolean isAllVersionsSearchableSupported(Session session) {
		RepositoryInfo repositoryInfo = session.getRepositoryInfo();

		RepositoryCapabilities repositoryCapabilities =
			repositoryInfo.getCapabilities();

		return repositoryCapabilities.isAllVersionsSearchableSupported();
	}

	protected void processException(Exception e) throws PortalException {
		String message = e.getMessage();

		if (((e instanceof CmisRuntimeException) &&
			 message.contains("authorized")) ||
			(e instanceof CmisPermissionDeniedException)) {

			String login = null;

			try {
				login = _cmisRepositoryHandler.getLogin();
			}
			catch (Exception e2) {
			}

			throw new PrincipalException.MustBeAuthenticated(login);
		}
	}

	protected <E> List<E> subList(
		List<E> list, int start, int end, OrderByComparator<E> obc) {

		if ((obc != null) &&
			(obc instanceof RepositoryModelCreateDateComparator ||
			 obc instanceof RepositoryModelModifiedDateComparator ||
			 obc instanceof RepositoryModelSizeComparator ||
			 obc instanceof RepositoryModelTitleComparator)) {

			list = ListUtil.sort(list, obc);
		}

		return ListUtil.subList(list, start, end);
	}

	protected FileEntry toFileEntry(Document document, boolean strict)
		throws PortalException {

		RepositoryEntry repositoryEntry = null;

		if (isDocumentRetrievableByVersionSeriesId()) {
			repositoryEntry = getRepositoryEntry(document.getVersionSeriesId());
		}
		else {
			repositoryEntry = getRepositoryEntry(document.getId());
		}

		return new CMISFileEntry(
			this, repositoryEntry.getUuid(),
			repositoryEntry.getRepositoryEntryId(), document, _lockManager);
	}

	protected FileEntry toFileEntry(String objectId, boolean strict)
		throws PortalException {

		try {
			Session session = getSession();

			Document document = (Document)session.getObject(objectId);

			return toFileEntry(document, strict);
		}
		catch (CmisObjectNotFoundException confe) {
			throw new NoSuchFileEntryException(
				"No CMIS file entry with {objectId=" + objectId + "}", confe);
		}
		catch (SystemException se) {
			throw se;
		}
		catch (Exception e) {
			processException(e);

			throw new RepositoryException(e);
		}
	}

	protected String toFileEntryId(long fileEntryId) throws PortalException {
		RepositoryEntry repositoryEntry =
			repositoryEntryLocalService.fetchRepositoryEntry(fileEntryId);

		if (repositoryEntry == null) {
			throw new NoSuchFileEntryException(
				"No CMIS file entry with {fileEntryId=" + fileEntryId + "}");
		}

		return repositoryEntry.getMappedId();
	}

	protected String toFileVersionId(long fileVersionId)
		throws PortalException {

		RepositoryEntry repositoryEntry =
			repositoryEntryLocalService.fetchRepositoryEntry(fileVersionId);

		if (repositoryEntry == null) {
			throw new NoSuchFileVersionException(
				"No CMIS file version with {fileVersionId=" + fileVersionId +
					"}");
		}

		return repositoryEntry.getMappedId();
	}

	protected String toFolderId(Session session, long folderId)
		throws PortalException {

		RepositoryEntry repositoryEntry =
			repositoryEntryLocalService.fetchRepositoryEntry(folderId);

		if (repositoryEntry != null) {
			return repositoryEntry.getMappedId();
		}

		DLFolder dlFolder = dlFolderLocalService.fetchFolder(folderId);

		if (dlFolder == null) {
			throw new NoSuchFolderException(
				"No CMIS folder with {folderId=" + folderId + "}");
		}
		else if (!dlFolder.isMountPoint()) {
			throw new RepositoryException(
				"CMIS repository should not be used with {folderId=" +
					folderId + "}");
		}

		RepositoryInfo repositoryInfo = session.getRepositoryInfo();

		repositoryEntry = repositoryEntryLocalService.getRepositoryEntry(
			dlFolder.getUserId(), getGroupId(), getRepositoryId(),
			repositoryInfo.getRootFolderId());

		return repositoryEntry.getMappedId();
	}

	protected Object toFolderOrFileEntry(CmisObject cmisObject)
		throws PortalException {

		if (cmisObject instanceof Document) {
			return toFileEntry((Document)cmisObject);
		}
		else if (cmisObject instanceof
					org.apache.chemistry.opencmis.client.api.Folder) {

			org.apache.chemistry.opencmis.client.api.Folder cmisFolder =
				(org.apache.chemistry.opencmis.client.api.Folder)cmisObject;

			return toFolder(cmisFolder);
		}

		return null;
	}

	protected void updateMappedId(long repositoryEntryId, String mappedId)
		throws PortalException {

		RepositoryEntry repositoryEntry =
			repositoryEntryLocalService.getRepositoryEntry(repositoryEntryId);

		if (!mappedId.equals(repositoryEntry.getMappedId())) {
			RepositoryEntryLocalServiceUtil.updateRepositoryEntry(
				repositoryEntryId, mappedId);
		}
	}

	protected void validateTitle(Session session, long folderId, String title)
		throws PortalException {

		String objectId = getObjectId(session, folderId, true, title);

		if (objectId != null) {
			throw new DuplicateFileEntryException(title);
		}

		objectId = getObjectId(session, folderId, false, title);

		if (objectId != null) {
			throw new DuplicateFolderNameException(title);
		}
	}

	private final <T> Set<T> _toSet(T... items) {
		HashSet<T> set = new HashSet<>();

		for (T item : items) {
			set.add(item);
		}

		return set;
	}

	private static final int _DELETE_DEEP = -1;

	private static final int _DELETE_NONE = 0;

	private static final Log _log = LogFactoryUtil.getLog(CMISRepository.class);

	private static final CMISModelCache _cmisModelCache = new CMISModelCache();

	private final CMISRepositoryConfiguration _cmisRepositoryConfiguration;
	private CMISRepositoryDetector _cmisRepositoryDetector;
	private final CMISRepositoryHandler _cmisRepositoryHandler;
	private final CMISSearchQueryBuilder _cmisSearchQueryBuilder;
	private final CMISSessionCache _cmisSessionCache;
	private final LockManager _lockManager;
	private String _sessionKey;

}