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

package com.liferay.friendly.url.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.exportimport.kernel.lar.StagedModelType;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link FriendlyURLEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FriendlyURLEntry
 * @generated
 */
@ProviderType
public class FriendlyURLEntryWrapper implements FriendlyURLEntry,
	ModelWrapper<FriendlyURLEntry> {
	public FriendlyURLEntryWrapper(FriendlyURLEntry friendlyURLEntry) {
		_friendlyURLEntry = friendlyURLEntry;
	}

	@Override
	public Class<?> getModelClass() {
		return FriendlyURLEntry.class;
	}

	@Override
	public String getModelClassName() {
		return FriendlyURLEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("friendlyURLEntryId", getFriendlyURLEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("defaultLanguageId", getDefaultLanguageId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long friendlyURLEntryId = (Long)attributes.get("friendlyURLEntryId");

		if (friendlyURLEntryId != null) {
			setFriendlyURLEntryId(friendlyURLEntryId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		String defaultLanguageId = (String)attributes.get("defaultLanguageId");

		if (defaultLanguageId != null) {
			setDefaultLanguageId(defaultLanguageId);
		}
	}

	@Override
	public Object clone() {
		return new FriendlyURLEntryWrapper((FriendlyURLEntry)_friendlyURLEntry.clone());
	}

	@Override
	public int compareTo(FriendlyURLEntry friendlyURLEntry) {
		return _friendlyURLEntry.compareTo(friendlyURLEntry);
	}

	@Override
	public String[] getAvailableLanguageIds() {
		return _friendlyURLEntry.getAvailableLanguageIds();
	}

	/**
	* Returns the fully qualified class name of this friendly url entry.
	*
	* @return the fully qualified class name of this friendly url entry
	*/
	@Override
	public String getClassName() {
		return _friendlyURLEntry.getClassName();
	}

	/**
	* Returns the class name ID of this friendly url entry.
	*
	* @return the class name ID of this friendly url entry
	*/
	@Override
	public long getClassNameId() {
		return _friendlyURLEntry.getClassNameId();
	}

	/**
	* Returns the class pk of this friendly url entry.
	*
	* @return the class pk of this friendly url entry
	*/
	@Override
	public long getClassPK() {
		return _friendlyURLEntry.getClassPK();
	}

	/**
	* Returns the company ID of this friendly url entry.
	*
	* @return the company ID of this friendly url entry
	*/
	@Override
	public long getCompanyId() {
		return _friendlyURLEntry.getCompanyId();
	}

	/**
	* Returns the create date of this friendly url entry.
	*
	* @return the create date of this friendly url entry
	*/
	@Override
	public Date getCreateDate() {
		return _friendlyURLEntry.getCreateDate();
	}

	/**
	* Returns the default language ID of this friendly url entry.
	*
	* @return the default language ID of this friendly url entry
	*/
	@Override
	public String getDefaultLanguageId() {
		return _friendlyURLEntry.getDefaultLanguageId();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _friendlyURLEntry.getExpandoBridge();
	}

	/**
	* Returns the friendly url entry ID of this friendly url entry.
	*
	* @return the friendly url entry ID of this friendly url entry
	*/
	@Override
	public long getFriendlyURLEntryId() {
		return _friendlyURLEntry.getFriendlyURLEntryId();
	}

	/**
	* Returns the group ID of this friendly url entry.
	*
	* @return the group ID of this friendly url entry
	*/
	@Override
	public long getGroupId() {
		return _friendlyURLEntry.getGroupId();
	}

	@Override
	public Map<String, String> getLanguageIdToUrlTitleMap() {
		return _friendlyURLEntry.getLanguageIdToUrlTitleMap();
	}

	/**
	* Returns the modified date of this friendly url entry.
	*
	* @return the modified date of this friendly url entry
	*/
	@Override
	public Date getModifiedDate() {
		return _friendlyURLEntry.getModifiedDate();
	}

	/**
	* Returns the mvcc version of this friendly url entry.
	*
	* @return the mvcc version of this friendly url entry
	*/
	@Override
	public long getMvccVersion() {
		return _friendlyURLEntry.getMvccVersion();
	}

	/**
	* Returns the primary key of this friendly url entry.
	*
	* @return the primary key of this friendly url entry
	*/
	@Override
	public long getPrimaryKey() {
		return _friendlyURLEntry.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _friendlyURLEntry.getPrimaryKeyObj();
	}

	@Override
	public String getUrlTitle() {
		return _friendlyURLEntry.getUrlTitle();
	}

	@Override
	public String getUrlTitle(String languageId) {
		return _friendlyURLEntry.getUrlTitle(languageId);
	}

	@Override
	public String getUrlTitle(String languageId, boolean useDefault) {
		return _friendlyURLEntry.getUrlTitle(languageId, useDefault);
	}

	@Override
	public String getUrlTitleMapAsXML() {
		return _friendlyURLEntry.getUrlTitleMapAsXML();
	}

	/**
	* Returns the uuid of this friendly url entry.
	*
	* @return the uuid of this friendly url entry
	*/
	@Override
	public String getUuid() {
		return _friendlyURLEntry.getUuid();
	}

	@Override
	public int hashCode() {
		return _friendlyURLEntry.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _friendlyURLEntry.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _friendlyURLEntry.isEscapedModel();
	}

	@Override
	public boolean isMain()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _friendlyURLEntry.isMain();
	}

	@Override
	public boolean isNew() {
		return _friendlyURLEntry.isNew();
	}

	@Override
	public void persist() {
		_friendlyURLEntry.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_friendlyURLEntry.setCachedModel(cachedModel);
	}

	@Override
	public void setClassName(String className) {
		_friendlyURLEntry.setClassName(className);
	}

	/**
	* Sets the class name ID of this friendly url entry.
	*
	* @param classNameId the class name ID of this friendly url entry
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_friendlyURLEntry.setClassNameId(classNameId);
	}

	/**
	* Sets the class pk of this friendly url entry.
	*
	* @param classPK the class pk of this friendly url entry
	*/
	@Override
	public void setClassPK(long classPK) {
		_friendlyURLEntry.setClassPK(classPK);
	}

	/**
	* Sets the company ID of this friendly url entry.
	*
	* @param companyId the company ID of this friendly url entry
	*/
	@Override
	public void setCompanyId(long companyId) {
		_friendlyURLEntry.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this friendly url entry.
	*
	* @param createDate the create date of this friendly url entry
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_friendlyURLEntry.setCreateDate(createDate);
	}

	/**
	* Sets the default language ID of this friendly url entry.
	*
	* @param defaultLanguageId the default language ID of this friendly url entry
	*/
	@Override
	public void setDefaultLanguageId(String defaultLanguageId) {
		_friendlyURLEntry.setDefaultLanguageId(defaultLanguageId);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_friendlyURLEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_friendlyURLEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_friendlyURLEntry.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the friendly url entry ID of this friendly url entry.
	*
	* @param friendlyURLEntryId the friendly url entry ID of this friendly url entry
	*/
	@Override
	public void setFriendlyURLEntryId(long friendlyURLEntryId) {
		_friendlyURLEntry.setFriendlyURLEntryId(friendlyURLEntryId);
	}

	/**
	* Sets the group ID of this friendly url entry.
	*
	* @param groupId the group ID of this friendly url entry
	*/
	@Override
	public void setGroupId(long groupId) {
		_friendlyURLEntry.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this friendly url entry.
	*
	* @param modifiedDate the modified date of this friendly url entry
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_friendlyURLEntry.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the mvcc version of this friendly url entry.
	*
	* @param mvccVersion the mvcc version of this friendly url entry
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		_friendlyURLEntry.setMvccVersion(mvccVersion);
	}

	@Override
	public void setNew(boolean n) {
		_friendlyURLEntry.setNew(n);
	}

	/**
	* Sets the primary key of this friendly url entry.
	*
	* @param primaryKey the primary key of this friendly url entry
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_friendlyURLEntry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_friendlyURLEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the uuid of this friendly url entry.
	*
	* @param uuid the uuid of this friendly url entry
	*/
	@Override
	public void setUuid(String uuid) {
		_friendlyURLEntry.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<FriendlyURLEntry> toCacheModel() {
		return _friendlyURLEntry.toCacheModel();
	}

	@Override
	public FriendlyURLEntry toEscapedModel() {
		return new FriendlyURLEntryWrapper(_friendlyURLEntry.toEscapedModel());
	}

	@Override
	public String toString() {
		return _friendlyURLEntry.toString();
	}

	@Override
	public FriendlyURLEntry toUnescapedModel() {
		return new FriendlyURLEntryWrapper(_friendlyURLEntry.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _friendlyURLEntry.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof FriendlyURLEntryWrapper)) {
			return false;
		}

		FriendlyURLEntryWrapper friendlyURLEntryWrapper = (FriendlyURLEntryWrapper)obj;

		if (Objects.equals(_friendlyURLEntry,
					friendlyURLEntryWrapper._friendlyURLEntry)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _friendlyURLEntry.getStagedModelType();
	}

	@Override
	public FriendlyURLEntry getWrappedModel() {
		return _friendlyURLEntry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _friendlyURLEntry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _friendlyURLEntry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_friendlyURLEntry.resetOriginalValues();
	}

	private final FriendlyURLEntry _friendlyURLEntry;
}