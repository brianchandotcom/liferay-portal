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

package com.liferay.portal.repository.cmis.model;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.RepositoryException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Lock;
import com.liferay.portal.model.User;
import com.liferay.portal.repository.cmis.CMISRepository;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.CMISRepositoryLocalServiceUtil;
import com.liferay.portal.service.persistence.LockUtil;
import com.liferay.portlet.documentlibrary.NoSuchFileVersionException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.service.DLAppHelperLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.DLUtil;

import java.io.InputStream;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.commons.data.AllowableActions;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.enums.Action;

/**
 * @author Alexander Chow
 */
public class CMISFileEntry extends CMISModel implements FileEntry {

	public CMISFileEntry(
		CMISRepository cmisRepository, String uuid, long fileEntryId,
		Document document) {

		_cmisRepository = cmisRepository;
		_uuid = uuid;
		_fileEntryId = fileEntryId;
		_document = document;
	}

	public boolean containsPermission(
			PermissionChecker permissionChecker, String actionId)
		throws SystemException {

		return containsPermission(_document, actionId);
	}

	public Map<String, Serializable> getAttributes() {
		return new HashMap<String, Serializable>();
	}

	@Override
	public long getCompanyId() {
		return _cmisRepository.getCompanyId();
	}

	public InputStream getContentStream() {
		ContentStream contentStream = _document.getContentStream();

		try {
			DLAppHelperLocalServiceUtil.getFileAsStream(
				PrincipalThreadLocal.getUserId(), this, true);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return contentStream.getStream();
	}

	public InputStream getContentStream(String version)
		throws PortalException {

		if (Validator.isNull(version)) {
			return getContentStream();
		}

		for (Document document : getAllVersions()) {
			if (version.equals(document.getVersionLabel())) {
				ContentStream contentStream = document.getContentStream();

				try {
					DLAppHelperLocalServiceUtil.getFileAsStream(
						PrincipalThreadLocal.getUserId(), this, true);
				}
				catch (Exception e) {
					_log.error(e, e);
				}

				return contentStream.getStream();
			}
		}

		throw new NoSuchFileVersionException(
			"No CMIS file version with {fileEntryId=" + getFileEntryId() +
				", version=" + version + "}");
	}

	public Date getCreateDate() {
		return _document.getCreationDate().getTime();
	}

	public String getExtension() {
		return FileUtil.getExtension(getTitle());
	}

	public long getFileEntryId() {
		return _fileEntryId;
	}

	public FileVersion getFileVersion()
		throws PortalException, SystemException {

		return getLatestFileVersion();
	}

	public FileVersion getFileVersion(String version)
		throws PortalException, SystemException {

		if (Validator.isNull(version)) {
			return getFileVersion();
		}

		for (Document document : getAllVersions()) {
			if (version.equals(document.getVersionLabel())) {
				return CMISRepositoryLocalServiceUtil.toFileVersion(
					getRepositoryId(), document);
			}
		}

		throw new NoSuchFileVersionException(
			"No CMIS file version with {fileEntryId=" + getFileEntryId() +
				", version=" + version + "}");
	}

	public List<FileVersion> getFileVersions(int status)
		throws SystemException {

		List<Document> documents = getAllVersions();

		List<FileVersion> fileVersions = new ArrayList<FileVersion>(
			documents.size());

		try {
			for (Document document : documents) {
				FileVersion fileVersion =
					CMISRepositoryLocalServiceUtil.toFileVersion(
						getRepositoryId(), document);

				fileVersions.add(fileVersion);
			}
		}
		catch (PortalException pe) {
			throw new RepositoryException(pe);
		}

		return fileVersions;
	}

	public Folder getFolder() {
		Folder parentFolder = null;

		try {
			parentFolder = super.getParentFolder();

			if (parentFolder != null) {
				return parentFolder;
			}
		}
		catch (Exception e) {
		}

		try {
			parentFolder = CMISRepositoryLocalServiceUtil.toFolder(
				getRepositoryId(), _document.getParents().get(0));

			setParentFolder(parentFolder);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return parentFolder;
	}

	public long getFolderId() {
		Folder folder = getFolder();

		return folder.getFolderId();
	}

	public long getGroupId() {
		return _cmisRepository.getGroupId();
	}

	public String getIcon() {
		return DLUtil.getFileIcon(getExtension());
	}

	public FileVersion getLatestFileVersion()
		throws PortalException, SystemException {

		if (_latestFileVersion != null) {
			return _latestFileVersion;
		}

		List<Document> documents = getAllVersions();

		Document latestDocumentVersion = documents.get(0);

		_latestFileVersion = CMISRepositoryLocalServiceUtil.toFileVersion(
			getRepositoryId(), latestDocumentVersion);

		return _latestFileVersion;
	}

	public Lock getLock() {
		if (!isCheckedOut()) {
			return null;
		}

		String checkedOutBy = _document.getVersionSeriesCheckedOutBy();

		User user = getUser(checkedOutBy);

		Lock lock = LockUtil.create(0);

		lock.setCompanyId(getCompanyId());

		if (user != null) {
			lock.setUserId(user.getUserId());
			lock.setUserName(user.getFullName());
		}

		lock.setCreateDate(new Date());

		return lock;
	}

	public String getMimeType() {
		return _document.getContentStreamMimeType();
	}

	public String getMimeType(String version) {
		if (Validator.isNull(version)) {
			return getMimeType();
		}

		for (Document document : getAllVersions()) {
			if (version.equals(document.getVersionLabel())) {
				return document.getContentStreamMimeType();
			}
		}

		return ContentTypes.APPLICATION_OCTET_STREAM;
	}

	public Object getModel() {
		return _document;
	}

	public Class<?> getModelClass() {
		return DLFileEntry.class;
	}

	@Override
	public String getModelClassName() {
		return DLFileEntry.class.getName();
	}

	public Date getModifiedDate() {
		return _document.getLastModificationDate().getTime();
	}

	@Override
	public long getPrimaryKey() {
		return _fileEntryId;
	}

	public Serializable getPrimaryKeyObj() {
		return getPrimaryKey();
	}

	public int getReadCount() {
		return 0;
	}

	public long getRepositoryId() {
		return _cmisRepository.getRepositoryId();
	}

	public long getSize() {
		return _document.getContentStreamLength();
	}

	public String getTitle() {
		return _document.getName();
	}

	public long getUserId() {
		User user = getUser(_document.getCreatedBy());

		if (user == null) {
			return 0;
		}
		else {
			return user.getUserId();
		}
	}

	public String getUserName() {
		User user = getUser(_document.getCreatedBy());

		if (user == null) {
			return StringPool.BLANK;
		}
		else {
			return user.getFullName();
		}
	}

	public String getUserUuid() {
		User user = getUser(_document.getCreatedBy());

		try {
			return user.getUserUuid();
		}
		catch (Exception e) {
		}

		return StringPool.BLANK;
	}

	public String getUuid() {
		return _uuid;
	}

	public String getVersion() {
		return GetterUtil.getString(_document.getVersionLabel(), null);
	}

	public long getVersionUserId() {
		return 0;
	}

	public String getVersionUserName() {
		return _document.getLastModifiedBy();
	}

	public String getVersionUserUuid() {
		return StringPool.BLANK;
	}

	public boolean hasLock() {
		if (!isCheckedOut()) {
			return false;
		}

 		AllowableActions allowableActions = _document.getAllowableActions();

		Set<Action> allowableActionsSet =
			allowableActions.getAllowableActions();

		return allowableActionsSet.contains(Action.CAN_CHECK_IN);
	}

	public boolean isCheckedOut() {
		return _document.isVersionSeriesCheckedOut();
	}

	public boolean isDefaultRepository() {
		return false;
	}

	public boolean isEscapedModel() {
		return false;
	}

	public boolean isSupportsLocking() {
		return true;
	}

	public boolean isSupportsMetadata() {
		return false;
	}

	public boolean isSupportsSocial() {
		return false;
	}

	public void setCompanyId(long companyId) {
		_cmisRepository.setCompanyId(companyId);
	}

	public void setCreateDate(Date date) {
	}

	public void setFileEntryId(long fileEntryId) {
		_fileEntryId = fileEntryId;
	}

	public void setGroupId(long groupId) {
		_cmisRepository.setGroupId(groupId);
	}

	public void setModifiedDate(Date date) {
	}

	public void setPrimaryKey(long primaryKey) {
		setFileEntryId(primaryKey);
	}

	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	public void setUserId(long userId) {
	}

	public void setUserName(String userName) {
	}

	public void setUserUuid(String userUuid) {
	}

	public FileEntry toEscapedModel() {
		return this;
	}

	protected List<Document> getAllVersions() {
		if (_allVersions == null) {
			_document.refresh();

			_allVersions = _document.getAllVersions();
		}

		return _allVersions;
	}

	@Override
	protected CMISRepository getCmisRepository() {
		return _cmisRepository;
	}

	private static Log _log = LogFactoryUtil.getLog(CMISFileEntry.class);

	private CMISRepository _cmisRepository;
	private Document _document;
	private List<Document> _allVersions;
	private long _fileEntryId;
	private FileVersion _latestFileVersion;
	private String _uuid;

}