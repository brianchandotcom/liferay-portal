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

package com.liferay.document.library.repository.cmis.internal.model;

import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.service.DLAppHelperLocalServiceUtil;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.document.library.repository.cmis.internal.CMISRepository;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.RepositoryModelOperation;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.Validator;

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
		CMISRepository cmisRepository, FileEntry fileEntry, String uuid,
		long fileVersionId, Document document) {

		_cmisRepository = cmisRepository;
		_fileEntry = fileEntry;
		_uuid = uuid;
		_fileVersionId = fileVersionId;
		_document = document;
	}

	@Override
	public Object clone() {
		CMISFileVersion cmisFileVersion = new CMISFileVersion(
			_cmisRepository, _fileEntry, _uuid, _fileVersionId, _document);

		cmisFileVersion.setCompanyId(getCompanyId());
		cmisFileVersion.setFileVersionId(getFileVersionId());
		cmisFileVersion.setGroupId(getGroupId());

		try {
			cmisFileVersion.setParentFolder(getParentFolder());
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}

		cmisFileVersion.setPrimaryKey(getPrimaryKey());

		return cmisFileVersion;
	}

	@Override
	public void execute(RepositoryModelOperation repositoryModelOperation)
		throws PortalException {

		repositoryModelOperation.execute(this);
	}

	@Override
	public Map<String, Serializable> getAttributes() {
		return new HashMap<>();
	}

	@Override
	public String getChangeLog() {
		return _document.getCheckinComment();
	}

	@Override
	public long getCompanyId() {
		return _cmisRepository.getCompanyId();
	}

	@Override
	public InputStream getContentStream(boolean incrementCounter) {
		ContentStream contentStream = _document.getContentStream();

		try {
			DLAppHelperLocalServiceUtil.getFileAsStream(
				PrincipalThreadLocal.getUserId(), getFileEntry(),
				incrementCounter);
		}
		catch (Exception exception) {
			_log.error("Unable to get content stream", exception);
		}

		return contentStream.getStream();
	}

	@Override
	public Date getCreateDate() {
		Calendar creationDate = _document.getCreationDate();

		return creationDate.getTime();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return null;
	}

	@Override
	public Date getExpirationDate() {
		return null;
	}

	@Override
	public String getExtension() {
		return FileUtil.getExtension(getTitle());
	}

	@Override
	public String getExtraSettings() {
		return null;
	}

	@Override
	public FileEntry getFileEntry() throws PortalException {
		if (_fileEntry != null) {
			return _fileEntry;
		}

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
		catch (CmisObjectNotFoundException cmisObjectNotFoundException) {
			throw new NoSuchFileEntryException(cmisObjectNotFoundException);
		}

		_fileEntry = _cmisRepository.toFileEntry(document);

		return _fileEntry;
	}

	@Override
	public long getFileEntryId() {
		try {
			return getFileEntry().getFileEntryId();
		}
		catch (NoSuchFileEntryException noSuchFileEntryException) {
			if (_log.isDebugEnabled()) {
				_log.debug(noSuchFileEntryException, noSuchFileEntryException);
			}
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}

		return 0;
	}

	@Override
	public String getFileName() {
		return DLUtil.getSanitizedFileName(getTitle(), getExtension());
	}

	@Override
	public long getFileVersionId() {
		return _fileVersionId;
	}

	@Override
	public long getGroupId() {
		return _cmisRepository.getGroupId();
	}

	@Override
	public String getIcon() {
		return DLUtil.getFileIcon(getExtension());
	}

	@Override
	public Date getLastPublishDate() {
		return null;
	}

	@Override
	public String getMimeType() {
		String mimeType = _document.getContentStreamMimeType();

		if (Validator.isNotNull(mimeType)) {
			return mimeType;
		}

		return MimeTypesUtil.getContentType(getTitle());
	}

	@Override
	public Object getModel() {
		return _document;
	}

	@Override
	public Class<?> getModelClass() {
		return CMISFileVersion.class;
	}

	@Override
	public String getModelClassName() {
		return CMISFileVersion.class.getName();
	}

	@Override
	public Date getModifiedDate() {
		Calendar modificationDate = _document.getLastModificationDate();

		return modificationDate.getTime();
	}

	@Override
	public long getPrimaryKey() {
		return _fileVersionId;
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return getPrimaryKey();
	}

	@Override
	public long getRepositoryId() {
		return _cmisRepository.getRepositoryId();
	}

	@Override
	public Date getReviewDate() {
		return null;
	}

	@Override
	public long getSize() {
		return _document.getContentStreamLength();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return new StagedModelType(FileVersion.class);
	}

	@Override
	public int getStatus() {
		return 0;
	}

	@Override
	public long getStatusByUserId() {
		return 0;
	}

	@Override
	public String getStatusByUserName() {
		return _document.getLastModifiedBy();
	}

	@Override
	public String getStatusByUserUuid() {
		return null;
	}

	@Override
	public Date getStatusDate() {
		return getModifiedDate();
	}

	@Override
	public String getTitle() {
		return _document.getName();
	}

	@Override
	public long getUserId() {
		try {
			return UserLocalServiceUtil.getDefaultUserId(getCompanyId());
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}

			return 0;
		}
	}

	@Override
	public String getUserName() {
		return _document.getCreatedBy();
	}

	@Override
	public String getUserUuid() {
		try {
			User user = UserLocalServiceUtil.getDefaultUser(getCompanyId());

			return user.getUserUuid();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}

			return StringPool.BLANK;
		}
	}

	@Override
	public String getUuid() {
		return _uuid;
	}

	@Override
	public String getVersion() {
		return GetterUtil.getString(_document.getVersionLabel());
	}

	@Override
	public boolean isApproved() {
		return false;
	}

	@Override
	public boolean isDefaultRepository() {
		return false;
	}

	@Override
	public boolean isDraft() {
		return false;
	}

	@Override
	public boolean isEscapedModel() {
		return false;
	}

	@Override
	public boolean isExpired() {
		return false;
	}

	@Override
	public boolean isPending() {
		return false;
	}

	@Override
	public void setCompanyId(long companyId) {
		_cmisRepository.setCompanyId(companyId);
	}

	@Override
	public void setCreateDate(Date createDate) {
	}

	public void setFileVersionId(long fileVersionId) {
		_fileVersionId = fileVersionId;
	}

	@Override
	public void setGroupId(long groupId) {
		_cmisRepository.setGroupId(groupId);
	}

	@Override
	public void setLastPublishDate(Date lastPublishDate) {
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
	}

	public void setPrimaryKey(long primaryKey) {
		setFileVersionId(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		Long primaryKeyLong = (Long)primaryKeyObj;

		setPrimaryKey(primaryKeyLong.longValue());
	}

	@Override
	public void setUserId(long userId) {
	}

	@Override
	public void setUserName(String userName) {
	}

	@Override
	public void setUserUuid(String userUuid) {
	}

	@Override
	public void setUuid(String uuid) {
	}

	@Override
	public FileVersion toEscapedModel() {
		return this;
	}

	@Override
	public FileVersion toUnescapedModel() {
		return this;
	}

	@Override
	protected CMISRepository getCmisRepository() {
		return _cmisRepository;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CMISFileVersion.class);

	private final CMISRepository _cmisRepository;
	private final Document _document;
	private FileEntry _fileEntry;
	private long _fileVersionId;
	private final String _uuid;

}