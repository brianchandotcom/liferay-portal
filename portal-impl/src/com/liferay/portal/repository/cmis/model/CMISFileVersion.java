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

package com.liferay.portal.repository.cmis.model;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;
import com.liferay.portal.repository.cmis.CMISRepository;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.service.CMISRepositoryLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.service.DLAppHelperLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.InputStream;
import java.io.Serializable;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.exceptions.CmisObjectNotFoundException;

/**
 * @author Alexander Chow
 */
public class CMISFileVersion extends CMISModel implements FileVersion {

	public CMISFileVersion(
		CMISRepository cmisRepository, String uuid, long fileVersionId,
		Document document) {

		_cmisRepository = cmisRepository;
		_uuid = uuid;
		_fileVersionId = fileVersionId;
		_document = document;
	}

	@Override
	public Object clone() {
		CMISFileVersion cmisFileVersion = new CMISFileVersion(
			_cmisRepository, _uuid, _fileVersionId, _document);

		cmisFileVersion.setCompanyId(getCompanyId());
		setFileVersionId(getFileVersionId());
		cmisFileVersion.setGroupId(getGroupId());

		try {
			cmisFileVersion.setParentFolder(getParentFolder());
		}
		catch (Exception e) {
		}

		cmisFileVersion.setPrimaryKey(getPrimaryKey());

		return cmisFileVersion;
	}

	public Map<String, Serializable> getAttributes() {
		return new HashMap<String, Serializable>();
	}

	public String getChangeLog() {
		return _document.getCheckinComment();
	}

	@Override
	public long getCompanyId() {
		return _cmisRepository.getCompanyId();
	}

	public InputStream getContentStream(boolean incrementCounter) {
		ContentStream contentStream = _document.getContentStream();

		try {
			DLAppHelperLocalServiceUtil.getFileAsStream(
				PrincipalThreadLocal.getUserId(), getFileEntry(),
				incrementCounter);
		}
		catch (Exception e) {
			_log.error(e);
		}

		return contentStream.getStream();
	}

	public Date getCreateDate() {
		Calendar creationDate = _document.getCreationDate();

		return creationDate.getTime();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return null;
	}

	public String getExtension() {
		return FileUtil.getExtension(getTitle());
	}

	public String getExtraSettings() {
		return null;
	}

	public FileEntry getFileEntry() throws PortalException, SystemException {
		Document document = null;

		try {
			List<Document> allVersions = _document.getAllVersions();

			if (allVersions.isEmpty()) {
				document = _document;
			}
			else {
				document = allVersions.get(0);
			}
		}
		catch (CmisObjectNotFoundException confe) {
			throw new NoSuchFileEntryException(confe);
		}

		return CMISRepositoryLocalServiceUtil.toFileEntry(
			getRepositoryId(), document);
	}

	public long getFileEntryId() {
		try {
			return getFileEntry().getFileEntryId();
		}
		catch (NoSuchFileEntryException nsfee) {
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return 0;
	}

	public long getFileVersionId() {
		return _fileVersionId;
	}

	public long getGroupId() {
		return _cmisRepository.getGroupId();
	}

	public String getIcon() {
		return DLUtil.getFileIcon(getExtension());
	}

	public String getMimeType() {
		String mimeType = _document.getContentStreamMimeType();

		if (Validator.isNotNull(mimeType)) {
			return mimeType;
		}

		return MimeTypesUtil.getContentType(getTitle());
	}

	public Object getModel() {
		return _document;
	}

	public Class<?> getModelClass() {
		return CMISFileVersion.class;
	}

	@Override
	public String getModelClassName() {
		return CMISFileVersion.class.getName();
	}

	public Date getModifiedDate() {
		Calendar modificationDate = _document.getLastModificationDate();

		return modificationDate.getTime();
	}

	@Override
	public long getPrimaryKey() {
		return _fileVersionId;
	}

	public Serializable getPrimaryKeyObj() {
		return getPrimaryKey();
	}

	public long getRepositoryId() {
		return _cmisRepository.getRepositoryId();
	}

	public long getSize() {
		return _document.getContentStreamLength();
	}

	public int getStatus() {
		return 0;
	}

	public long getStatusByUserId() {
		return 0;
	}

	public String getStatusByUserName() {
		return null;
	}

	public String getStatusByUserUuid() {
		return null;
	}

	public Date getStatusDate() {
		return getModifiedDate();
	}

	public String getTitle() {
		return _document.getName();
	}

	public long getUserId() {
		try {
			return UserLocalServiceUtil.getDefaultUserId(getCompanyId());
		}
		catch (Exception e) {
			return 0;
		}
	}

	public String getUserName() {
		return _document.getCreatedBy();
	}

	public String getUserUuid() {
		try {
			User user = UserLocalServiceUtil.getDefaultUser(getCompanyId());

			return user.getUserUuid();
		}
		catch (Exception e) {
			return StringPool.BLANK;
		}
	}

	public String getUuid() {
		return _uuid;
	}

	public String getVersion() {
		return GetterUtil.getString(_document.getVersionLabel());
	}

	public boolean isApproved() {
		return false;
	}

	public boolean isDefaultRepository() {
		return false;
	}

	public boolean isDraft() {
		return false;
	}

	public boolean isEscapedModel() {
		return false;
	}

	public boolean isExpired() {
		return false;
	}

	public boolean isInTrash() {
		return false;
	}

	public boolean isInTrashContainer() {
		return false;
	}

	public boolean isPending() {
		return false;
	}

	public void setCompanyId(long companyId) {
		_cmisRepository.setCompanyId(companyId);
	}

	public void setCreateDate(Date date) {
	}

	public void setFileVersionId(long fileVersionId) {
		_fileVersionId = fileVersionId;
	}

	public void setGroupId(long groupId) {
		_cmisRepository.setGroupId(groupId);
	}

	public void setModifiedDate(Date date) {
	}

	public void setPrimaryKey(long primaryKey) {
		setFileVersionId(primaryKey);
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

	public void setUuid(String uuid) {
	}

	public FileVersion toEscapedModel() {
		return this;
	}

	public FileVersion toUnescapedModel() {
		return this;
	}

	@Override
	protected CMISRepository getCmisRepository() {
		return _cmisRepository;
	}

	private static Log _log = LogFactoryUtil.getLog(CMISFileVersion.class);

	private CMISRepository _cmisRepository;
	private Document _document;
	private long _fileVersionId;
	private String _uuid;

}