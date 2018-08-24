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

package com.liferay.document.library.opener.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link DLOpenerFileEntryReference}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLOpenerFileEntryReference
 * @generated
 */
@ProviderType
public class DLOpenerFileEntryReferenceWrapper
	implements DLOpenerFileEntryReference,
		ModelWrapper<DLOpenerFileEntryReference> {
	public DLOpenerFileEntryReferenceWrapper(
		DLOpenerFileEntryReference dlOpenerFileEntryReference) {
		_dlOpenerFileEntryReference = dlOpenerFileEntryReference;
	}

	@Override
	public Class<?> getModelClass() {
		return DLOpenerFileEntryReference.class;
	}

	@Override
	public String getModelClassName() {
		return DLOpenerFileEntryReference.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("dlOpenerFileEntryReferenceId",
			getDlOpenerFileEntryReferenceId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("referenceKey", getReferenceKey());
		attributes.put("fileEntryId", getFileEntryId());
		attributes.put("type", getType());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long dlOpenerFileEntryReferenceId = (Long)attributes.get(
				"dlOpenerFileEntryReferenceId");

		if (dlOpenerFileEntryReferenceId != null) {
			setDlOpenerFileEntryReferenceId(dlOpenerFileEntryReferenceId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		String referenceKey = (String)attributes.get("referenceKey");

		if (referenceKey != null) {
			setReferenceKey(referenceKey);
		}

		Long fileEntryId = (Long)attributes.get("fileEntryId");

		if (fileEntryId != null) {
			setFileEntryId(fileEntryId);
		}

		Integer type = (Integer)attributes.get("type");

		if (type != null) {
			setType(type);
		}
	}

	@Override
	public Object clone() {
		return new DLOpenerFileEntryReferenceWrapper((DLOpenerFileEntryReference)_dlOpenerFileEntryReference.clone());
	}

	@Override
	public int compareTo(DLOpenerFileEntryReference dlOpenerFileEntryReference) {
		return _dlOpenerFileEntryReference.compareTo(dlOpenerFileEntryReference);
	}

	/**
	* Returns the company ID of this dl opener file entry reference.
	*
	* @return the company ID of this dl opener file entry reference
	*/
	@Override
	public long getCompanyId() {
		return _dlOpenerFileEntryReference.getCompanyId();
	}

	/**
	* Returns the create date of this dl opener file entry reference.
	*
	* @return the create date of this dl opener file entry reference
	*/
	@Override
	public Date getCreateDate() {
		return _dlOpenerFileEntryReference.getCreateDate();
	}

	/**
	* Returns the dl opener file entry reference ID of this dl opener file entry reference.
	*
	* @return the dl opener file entry reference ID of this dl opener file entry reference
	*/
	@Override
	public long getDlOpenerFileEntryReferenceId() {
		return _dlOpenerFileEntryReference.getDlOpenerFileEntryReferenceId();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _dlOpenerFileEntryReference.getExpandoBridge();
	}

	/**
	* Returns the file entry ID of this dl opener file entry reference.
	*
	* @return the file entry ID of this dl opener file entry reference
	*/
	@Override
	public long getFileEntryId() {
		return _dlOpenerFileEntryReference.getFileEntryId();
	}

	/**
	* Returns the group ID of this dl opener file entry reference.
	*
	* @return the group ID of this dl opener file entry reference
	*/
	@Override
	public long getGroupId() {
		return _dlOpenerFileEntryReference.getGroupId();
	}

	/**
	* Returns the modified date of this dl opener file entry reference.
	*
	* @return the modified date of this dl opener file entry reference
	*/
	@Override
	public Date getModifiedDate() {
		return _dlOpenerFileEntryReference.getModifiedDate();
	}

	/**
	* Returns the primary key of this dl opener file entry reference.
	*
	* @return the primary key of this dl opener file entry reference
	*/
	@Override
	public long getPrimaryKey() {
		return _dlOpenerFileEntryReference.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _dlOpenerFileEntryReference.getPrimaryKeyObj();
	}

	/**
	* Returns the reference key of this dl opener file entry reference.
	*
	* @return the reference key of this dl opener file entry reference
	*/
	@Override
	public String getReferenceKey() {
		return _dlOpenerFileEntryReference.getReferenceKey();
	}

	/**
	* Returns the type of this dl opener file entry reference.
	*
	* @return the type of this dl opener file entry reference
	*/
	@Override
	public int getType() {
		return _dlOpenerFileEntryReference.getType();
	}

	/**
	* Returns the user ID of this dl opener file entry reference.
	*
	* @return the user ID of this dl opener file entry reference
	*/
	@Override
	public long getUserId() {
		return _dlOpenerFileEntryReference.getUserId();
	}

	/**
	* Returns the user name of this dl opener file entry reference.
	*
	* @return the user name of this dl opener file entry reference
	*/
	@Override
	public String getUserName() {
		return _dlOpenerFileEntryReference.getUserName();
	}

	/**
	* Returns the user uuid of this dl opener file entry reference.
	*
	* @return the user uuid of this dl opener file entry reference
	*/
	@Override
	public String getUserUuid() {
		return _dlOpenerFileEntryReference.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _dlOpenerFileEntryReference.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _dlOpenerFileEntryReference.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _dlOpenerFileEntryReference.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _dlOpenerFileEntryReference.isNew();
	}

	@Override
	public void persist() {
		_dlOpenerFileEntryReference.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_dlOpenerFileEntryReference.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this dl opener file entry reference.
	*
	* @param companyId the company ID of this dl opener file entry reference
	*/
	@Override
	public void setCompanyId(long companyId) {
		_dlOpenerFileEntryReference.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this dl opener file entry reference.
	*
	* @param createDate the create date of this dl opener file entry reference
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_dlOpenerFileEntryReference.setCreateDate(createDate);
	}

	/**
	* Sets the dl opener file entry reference ID of this dl opener file entry reference.
	*
	* @param dlOpenerFileEntryReferenceId the dl opener file entry reference ID of this dl opener file entry reference
	*/
	@Override
	public void setDlOpenerFileEntryReferenceId(
		long dlOpenerFileEntryReferenceId) {
		_dlOpenerFileEntryReference.setDlOpenerFileEntryReferenceId(dlOpenerFileEntryReferenceId);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_dlOpenerFileEntryReference.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_dlOpenerFileEntryReference.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_dlOpenerFileEntryReference.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the file entry ID of this dl opener file entry reference.
	*
	* @param fileEntryId the file entry ID of this dl opener file entry reference
	*/
	@Override
	public void setFileEntryId(long fileEntryId) {
		_dlOpenerFileEntryReference.setFileEntryId(fileEntryId);
	}

	/**
	* Sets the group ID of this dl opener file entry reference.
	*
	* @param groupId the group ID of this dl opener file entry reference
	*/
	@Override
	public void setGroupId(long groupId) {
		_dlOpenerFileEntryReference.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this dl opener file entry reference.
	*
	* @param modifiedDate the modified date of this dl opener file entry reference
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_dlOpenerFileEntryReference.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_dlOpenerFileEntryReference.setNew(n);
	}

	/**
	* Sets the primary key of this dl opener file entry reference.
	*
	* @param primaryKey the primary key of this dl opener file entry reference
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_dlOpenerFileEntryReference.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_dlOpenerFileEntryReference.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the reference key of this dl opener file entry reference.
	*
	* @param referenceKey the reference key of this dl opener file entry reference
	*/
	@Override
	public void setReferenceKey(String referenceKey) {
		_dlOpenerFileEntryReference.setReferenceKey(referenceKey);
	}

	/**
	* Sets the type of this dl opener file entry reference.
	*
	* @param type the type of this dl opener file entry reference
	*/
	@Override
	public void setType(int type) {
		_dlOpenerFileEntryReference.setType(type);
	}

	/**
	* Sets the user ID of this dl opener file entry reference.
	*
	* @param userId the user ID of this dl opener file entry reference
	*/
	@Override
	public void setUserId(long userId) {
		_dlOpenerFileEntryReference.setUserId(userId);
	}

	/**
	* Sets the user name of this dl opener file entry reference.
	*
	* @param userName the user name of this dl opener file entry reference
	*/
	@Override
	public void setUserName(String userName) {
		_dlOpenerFileEntryReference.setUserName(userName);
	}

	/**
	* Sets the user uuid of this dl opener file entry reference.
	*
	* @param userUuid the user uuid of this dl opener file entry reference
	*/
	@Override
	public void setUserUuid(String userUuid) {
		_dlOpenerFileEntryReference.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<DLOpenerFileEntryReference> toCacheModel() {
		return _dlOpenerFileEntryReference.toCacheModel();
	}

	@Override
	public DLOpenerFileEntryReference toEscapedModel() {
		return new DLOpenerFileEntryReferenceWrapper(_dlOpenerFileEntryReference.toEscapedModel());
	}

	@Override
	public String toString() {
		return _dlOpenerFileEntryReference.toString();
	}

	@Override
	public DLOpenerFileEntryReference toUnescapedModel() {
		return new DLOpenerFileEntryReferenceWrapper(_dlOpenerFileEntryReference.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _dlOpenerFileEntryReference.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DLOpenerFileEntryReferenceWrapper)) {
			return false;
		}

		DLOpenerFileEntryReferenceWrapper dlOpenerFileEntryReferenceWrapper = (DLOpenerFileEntryReferenceWrapper)obj;

		if (Objects.equals(_dlOpenerFileEntryReference,
					dlOpenerFileEntryReferenceWrapper._dlOpenerFileEntryReference)) {
			return true;
		}

		return false;
	}

	@Override
	public DLOpenerFileEntryReference getWrappedModel() {
		return _dlOpenerFileEntryReference;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _dlOpenerFileEntryReference.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _dlOpenerFileEntryReference.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_dlOpenerFileEntryReference.resetOriginalValues();
	}

	private final DLOpenerFileEntryReference _dlOpenerFileEntryReference;
}