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

package com.liferay.commerce.product.model;

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
 * This class is a wrapper for {@link CPFriendlyURLEntry}.
 * </p>
 *
 * @author Marco Leo
 * @see CPFriendlyURLEntry
 * @generated
 */
@ProviderType
public class CPFriendlyURLEntryWrapper implements CPFriendlyURLEntry,
	ModelWrapper<CPFriendlyURLEntry> {
	public CPFriendlyURLEntryWrapper(CPFriendlyURLEntry cpFriendlyUrlEntry) {
		_cpFriendlyUrlEntry = cpFriendlyUrlEntry;
	}

	@Override
	public Class<?> getModelClass() {
		return CPFriendlyURLEntry.class;
	}

	@Override
	public String getModelClassName() {
		return CPFriendlyURLEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("CPFriendlyURLEntryId", getCPFriendlyURLEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("languageId", getLanguageId());
		attributes.put("urlTitle", getUrlTitle());
		attributes.put("main", getMain());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long CPFriendlyURLEntryId = (Long)attributes.get("CPFriendlyURLEntryId");

		if (CPFriendlyURLEntryId != null) {
			setCPFriendlyURLEntryId(CPFriendlyURLEntryId);
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

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		String languageId = (String)attributes.get("languageId");

		if (languageId != null) {
			setLanguageId(languageId);
		}

		String urlTitle = (String)attributes.get("urlTitle");

		if (urlTitle != null) {
			setUrlTitle(urlTitle);
		}

		Boolean main = (Boolean)attributes.get("main");

		if (main != null) {
			setMain(main);
		}
	}

	@Override
	public CPFriendlyURLEntry toEscapedModel() {
		return new CPFriendlyURLEntryWrapper(_cpFriendlyUrlEntry.toEscapedModel());
	}

	@Override
	public CPFriendlyURLEntry toUnescapedModel() {
		return new CPFriendlyURLEntryWrapper(_cpFriendlyUrlEntry.toUnescapedModel());
	}

	/**
	* Returns the main of this cp friendly url entry.
	*
	* @return the main of this cp friendly url entry
	*/
	@Override
	public boolean getMain() {
		return _cpFriendlyUrlEntry.getMain();
	}

	@Override
	public boolean isCachedModel() {
		return _cpFriendlyUrlEntry.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _cpFriendlyUrlEntry.isEscapedModel();
	}

	/**
	* Returns <code>true</code> if this cp friendly url entry is main.
	*
	* @return <code>true</code> if this cp friendly url entry is main; <code>false</code> otherwise
	*/
	@Override
	public boolean isMain() {
		return _cpFriendlyUrlEntry.isMain();
	}

	@Override
	public boolean isNew() {
		return _cpFriendlyUrlEntry.isNew();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _cpFriendlyUrlEntry.getExpandoBridge();
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CPFriendlyURLEntry> toCacheModel() {
		return _cpFriendlyUrlEntry.toCacheModel();
	}

	@Override
	public int compareTo(CPFriendlyURLEntry cpFriendlyUrlEntry) {
		return _cpFriendlyUrlEntry.compareTo(cpFriendlyUrlEntry);
	}

	@Override
	public int hashCode() {
		return _cpFriendlyUrlEntry.hashCode();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _cpFriendlyUrlEntry.getPrimaryKeyObj();
	}

	@Override
	public java.lang.Object clone() {
		return new CPFriendlyURLEntryWrapper((CPFriendlyURLEntry)_cpFriendlyUrlEntry.clone());
	}

	/**
	* Returns the fully qualified class name of this cp friendly url entry.
	*
	* @return the fully qualified class name of this cp friendly url entry
	*/
	@Override
	public java.lang.String getClassName() {
		return _cpFriendlyUrlEntry.getClassName();
	}

	/**
	* Returns the language ID of this cp friendly url entry.
	*
	* @return the language ID of this cp friendly url entry
	*/
	@Override
	public java.lang.String getLanguageId() {
		return _cpFriendlyUrlEntry.getLanguageId();
	}

	/**
	* Returns the url title of this cp friendly url entry.
	*
	* @return the url title of this cp friendly url entry
	*/
	@Override
	public java.lang.String getUrlTitle() {
		return _cpFriendlyUrlEntry.getUrlTitle();
	}

	/**
	* Returns the user name of this cp friendly url entry.
	*
	* @return the user name of this cp friendly url entry
	*/
	@Override
	public java.lang.String getUserName() {
		return _cpFriendlyUrlEntry.getUserName();
	}

	/**
	* Returns the user uuid of this cp friendly url entry.
	*
	* @return the user uuid of this cp friendly url entry
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _cpFriendlyUrlEntry.getUserUuid();
	}

	/**
	* Returns the uuid of this cp friendly url entry.
	*
	* @return the uuid of this cp friendly url entry
	*/
	@Override
	public java.lang.String getUuid() {
		return _cpFriendlyUrlEntry.getUuid();
	}

	@Override
	public java.lang.String toString() {
		return _cpFriendlyUrlEntry.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _cpFriendlyUrlEntry.toXmlString();
	}

	/**
	* Returns the create date of this cp friendly url entry.
	*
	* @return the create date of this cp friendly url entry
	*/
	@Override
	public Date getCreateDate() {
		return _cpFriendlyUrlEntry.getCreateDate();
	}

	/**
	* Returns the modified date of this cp friendly url entry.
	*
	* @return the modified date of this cp friendly url entry
	*/
	@Override
	public Date getModifiedDate() {
		return _cpFriendlyUrlEntry.getModifiedDate();
	}

	/**
	* Returns the cp friendly url entry ID of this cp friendly url entry.
	*
	* @return the cp friendly url entry ID of this cp friendly url entry
	*/
	@Override
	public long getCPFriendlyURLEntryId() {
		return _cpFriendlyUrlEntry.getCPFriendlyURLEntryId();
	}

	/**
	* Returns the class name ID of this cp friendly url entry.
	*
	* @return the class name ID of this cp friendly url entry
	*/
	@Override
	public long getClassNameId() {
		return _cpFriendlyUrlEntry.getClassNameId();
	}

	/**
	* Returns the class pk of this cp friendly url entry.
	*
	* @return the class pk of this cp friendly url entry
	*/
	@Override
	public long getClassPK() {
		return _cpFriendlyUrlEntry.getClassPK();
	}

	/**
	* Returns the company ID of this cp friendly url entry.
	*
	* @return the company ID of this cp friendly url entry
	*/
	@Override
	public long getCompanyId() {
		return _cpFriendlyUrlEntry.getCompanyId();
	}

	/**
	* Returns the group ID of this cp friendly url entry.
	*
	* @return the group ID of this cp friendly url entry
	*/
	@Override
	public long getGroupId() {
		return _cpFriendlyUrlEntry.getGroupId();
	}

	/**
	* Returns the primary key of this cp friendly url entry.
	*
	* @return the primary key of this cp friendly url entry
	*/
	@Override
	public long getPrimaryKey() {
		return _cpFriendlyUrlEntry.getPrimaryKey();
	}

	/**
	* Returns the user ID of this cp friendly url entry.
	*
	* @return the user ID of this cp friendly url entry
	*/
	@Override
	public long getUserId() {
		return _cpFriendlyUrlEntry.getUserId();
	}

	@Override
	public void persist() {
		_cpFriendlyUrlEntry.persist();
	}

	/**
	* Sets the cp friendly url entry ID of this cp friendly url entry.
	*
	* @param CPFriendlyURLEntryId the cp friendly url entry ID of this cp friendly url entry
	*/
	@Override
	public void setCPFriendlyURLEntryId(long CPFriendlyURLEntryId) {
		_cpFriendlyUrlEntry.setCPFriendlyURLEntryId(CPFriendlyURLEntryId);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_cpFriendlyUrlEntry.setCachedModel(cachedModel);
	}

	@Override
	public void setClassName(java.lang.String className) {
		_cpFriendlyUrlEntry.setClassName(className);
	}

	/**
	* Sets the class name ID of this cp friendly url entry.
	*
	* @param classNameId the class name ID of this cp friendly url entry
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_cpFriendlyUrlEntry.setClassNameId(classNameId);
	}

	/**
	* Sets the class pk of this cp friendly url entry.
	*
	* @param classPK the class pk of this cp friendly url entry
	*/
	@Override
	public void setClassPK(long classPK) {
		_cpFriendlyUrlEntry.setClassPK(classPK);
	}

	/**
	* Sets the company ID of this cp friendly url entry.
	*
	* @param companyId the company ID of this cp friendly url entry
	*/
	@Override
	public void setCompanyId(long companyId) {
		_cpFriendlyUrlEntry.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this cp friendly url entry.
	*
	* @param createDate the create date of this cp friendly url entry
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_cpFriendlyUrlEntry.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_cpFriendlyUrlEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_cpFriendlyUrlEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_cpFriendlyUrlEntry.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this cp friendly url entry.
	*
	* @param groupId the group ID of this cp friendly url entry
	*/
	@Override
	public void setGroupId(long groupId) {
		_cpFriendlyUrlEntry.setGroupId(groupId);
	}

	/**
	* Sets the language ID of this cp friendly url entry.
	*
	* @param languageId the language ID of this cp friendly url entry
	*/
	@Override
	public void setLanguageId(java.lang.String languageId) {
		_cpFriendlyUrlEntry.setLanguageId(languageId);
	}

	/**
	* Sets whether this cp friendly url entry is main.
	*
	* @param main the main of this cp friendly url entry
	*/
	@Override
	public void setMain(boolean main) {
		_cpFriendlyUrlEntry.setMain(main);
	}

	/**
	* Sets the modified date of this cp friendly url entry.
	*
	* @param modifiedDate the modified date of this cp friendly url entry
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_cpFriendlyUrlEntry.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_cpFriendlyUrlEntry.setNew(n);
	}

	/**
	* Sets the primary key of this cp friendly url entry.
	*
	* @param primaryKey the primary key of this cp friendly url entry
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_cpFriendlyUrlEntry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_cpFriendlyUrlEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the url title of this cp friendly url entry.
	*
	* @param urlTitle the url title of this cp friendly url entry
	*/
	@Override
	public void setUrlTitle(java.lang.String urlTitle) {
		_cpFriendlyUrlEntry.setUrlTitle(urlTitle);
	}

	/**
	* Sets the user ID of this cp friendly url entry.
	*
	* @param userId the user ID of this cp friendly url entry
	*/
	@Override
	public void setUserId(long userId) {
		_cpFriendlyUrlEntry.setUserId(userId);
	}

	/**
	* Sets the user name of this cp friendly url entry.
	*
	* @param userName the user name of this cp friendly url entry
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_cpFriendlyUrlEntry.setUserName(userName);
	}

	/**
	* Sets the user uuid of this cp friendly url entry.
	*
	* @param userUuid the user uuid of this cp friendly url entry
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_cpFriendlyUrlEntry.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this cp friendly url entry.
	*
	* @param uuid the uuid of this cp friendly url entry
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_cpFriendlyUrlEntry.setUuid(uuid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CPFriendlyURLEntryWrapper)) {
			return false;
		}

		CPFriendlyURLEntryWrapper cpFriendlyUrlEntryWrapper = (CPFriendlyURLEntryWrapper)obj;

		if (Objects.equals(_cpFriendlyUrlEntry,
					cpFriendlyUrlEntryWrapper._cpFriendlyUrlEntry)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _cpFriendlyUrlEntry.getStagedModelType();
	}

	@Override
	public CPFriendlyURLEntry getWrappedModel() {
		return _cpFriendlyUrlEntry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _cpFriendlyUrlEntry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _cpFriendlyUrlEntry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_cpFriendlyUrlEntry.resetOriginalValues();
	}

	private final CPFriendlyURLEntry _cpFriendlyUrlEntry;
}