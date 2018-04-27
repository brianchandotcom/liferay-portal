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
	public CPFriendlyURLEntryWrapper(CPFriendlyURLEntry cpFriendlyURLEntry) {
		_cpFriendlyURLEntry = cpFriendlyURLEntry;
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
		attributes.put("main", isMain());

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
	public Object clone() {
		return new CPFriendlyURLEntryWrapper((CPFriendlyURLEntry)_cpFriendlyURLEntry.clone());
	}

	@Override
	public int compareTo(CPFriendlyURLEntry cpFriendlyURLEntry) {
		return _cpFriendlyURLEntry.compareTo(cpFriendlyURLEntry);
	}

	/**
	* Returns the fully qualified class name of this cp friendly url entry.
	*
	* @return the fully qualified class name of this cp friendly url entry
	*/
	@Override
	public String getClassName() {
		return _cpFriendlyURLEntry.getClassName();
	}

	/**
	* Returns the class name ID of this cp friendly url entry.
	*
	* @return the class name ID of this cp friendly url entry
	*/
	@Override
	public long getClassNameId() {
		return _cpFriendlyURLEntry.getClassNameId();
	}

	/**
	* Returns the class pk of this cp friendly url entry.
	*
	* @return the class pk of this cp friendly url entry
	*/
	@Override
	public long getClassPK() {
		return _cpFriendlyURLEntry.getClassPK();
	}

	/**
	* Returns the company ID of this cp friendly url entry.
	*
	* @return the company ID of this cp friendly url entry
	*/
	@Override
	public long getCompanyId() {
		return _cpFriendlyURLEntry.getCompanyId();
	}

	/**
	* Returns the cp friendly url entry ID of this cp friendly url entry.
	*
	* @return the cp friendly url entry ID of this cp friendly url entry
	*/
	@Override
	public long getCPFriendlyURLEntryId() {
		return _cpFriendlyURLEntry.getCPFriendlyURLEntryId();
	}

	/**
	* Returns the create date of this cp friendly url entry.
	*
	* @return the create date of this cp friendly url entry
	*/
	@Override
	public Date getCreateDate() {
		return _cpFriendlyURLEntry.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _cpFriendlyURLEntry.getExpandoBridge();
	}

	/**
	* Returns the group ID of this cp friendly url entry.
	*
	* @return the group ID of this cp friendly url entry
	*/
	@Override
	public long getGroupId() {
		return _cpFriendlyURLEntry.getGroupId();
	}

	/**
	* Returns the language ID of this cp friendly url entry.
	*
	* @return the language ID of this cp friendly url entry
	*/
	@Override
	public String getLanguageId() {
		return _cpFriendlyURLEntry.getLanguageId();
	}

	@Override
	public java.util.Locale getLocale() {
		return _cpFriendlyURLEntry.getLocale();
	}

	/**
	* Returns the main of this cp friendly url entry.
	*
	* @return the main of this cp friendly url entry
	*/
	@Override
	public boolean getMain() {
		return _cpFriendlyURLEntry.getMain();
	}

	/**
	* Returns the modified date of this cp friendly url entry.
	*
	* @return the modified date of this cp friendly url entry
	*/
	@Override
	public Date getModifiedDate() {
		return _cpFriendlyURLEntry.getModifiedDate();
	}

	/**
	* Returns the primary key of this cp friendly url entry.
	*
	* @return the primary key of this cp friendly url entry
	*/
	@Override
	public long getPrimaryKey() {
		return _cpFriendlyURLEntry.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _cpFriendlyURLEntry.getPrimaryKeyObj();
	}

	/**
	* Returns the url title of this cp friendly url entry.
	*
	* @return the url title of this cp friendly url entry
	*/
	@Override
	public String getUrlTitle() {
		return _cpFriendlyURLEntry.getUrlTitle();
	}

	/**
	* Returns the user ID of this cp friendly url entry.
	*
	* @return the user ID of this cp friendly url entry
	*/
	@Override
	public long getUserId() {
		return _cpFriendlyURLEntry.getUserId();
	}

	/**
	* Returns the user name of this cp friendly url entry.
	*
	* @return the user name of this cp friendly url entry
	*/
	@Override
	public String getUserName() {
		return _cpFriendlyURLEntry.getUserName();
	}

	/**
	* Returns the user uuid of this cp friendly url entry.
	*
	* @return the user uuid of this cp friendly url entry
	*/
	@Override
	public String getUserUuid() {
		return _cpFriendlyURLEntry.getUserUuid();
	}

	/**
	* Returns the uuid of this cp friendly url entry.
	*
	* @return the uuid of this cp friendly url entry
	*/
	@Override
	public String getUuid() {
		return _cpFriendlyURLEntry.getUuid();
	}

	@Override
	public int hashCode() {
		return _cpFriendlyURLEntry.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _cpFriendlyURLEntry.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _cpFriendlyURLEntry.isEscapedModel();
	}

	/**
	* Returns <code>true</code> if this cp friendly url entry is main.
	*
	* @return <code>true</code> if this cp friendly url entry is main; <code>false</code> otherwise
	*/
	@Override
	public boolean isMain() {
		return _cpFriendlyURLEntry.isMain();
	}

	@Override
	public boolean isNew() {
		return _cpFriendlyURLEntry.isNew();
	}

	@Override
	public void persist() {
		_cpFriendlyURLEntry.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_cpFriendlyURLEntry.setCachedModel(cachedModel);
	}

	@Override
	public void setClassName(String className) {
		_cpFriendlyURLEntry.setClassName(className);
	}

	/**
	* Sets the class name ID of this cp friendly url entry.
	*
	* @param classNameId the class name ID of this cp friendly url entry
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_cpFriendlyURLEntry.setClassNameId(classNameId);
	}

	/**
	* Sets the class pk of this cp friendly url entry.
	*
	* @param classPK the class pk of this cp friendly url entry
	*/
	@Override
	public void setClassPK(long classPK) {
		_cpFriendlyURLEntry.setClassPK(classPK);
	}

	/**
	* Sets the company ID of this cp friendly url entry.
	*
	* @param companyId the company ID of this cp friendly url entry
	*/
	@Override
	public void setCompanyId(long companyId) {
		_cpFriendlyURLEntry.setCompanyId(companyId);
	}

	/**
	* Sets the cp friendly url entry ID of this cp friendly url entry.
	*
	* @param CPFriendlyURLEntryId the cp friendly url entry ID of this cp friendly url entry
	*/
	@Override
	public void setCPFriendlyURLEntryId(long CPFriendlyURLEntryId) {
		_cpFriendlyURLEntry.setCPFriendlyURLEntryId(CPFriendlyURLEntryId);
	}

	/**
	* Sets the create date of this cp friendly url entry.
	*
	* @param createDate the create date of this cp friendly url entry
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_cpFriendlyURLEntry.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_cpFriendlyURLEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_cpFriendlyURLEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_cpFriendlyURLEntry.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this cp friendly url entry.
	*
	* @param groupId the group ID of this cp friendly url entry
	*/
	@Override
	public void setGroupId(long groupId) {
		_cpFriendlyURLEntry.setGroupId(groupId);
	}

	/**
	* Sets the language ID of this cp friendly url entry.
	*
	* @param languageId the language ID of this cp friendly url entry
	*/
	@Override
	public void setLanguageId(String languageId) {
		_cpFriendlyURLEntry.setLanguageId(languageId);
	}

	/**
	* Sets whether this cp friendly url entry is main.
	*
	* @param main the main of this cp friendly url entry
	*/
	@Override
	public void setMain(boolean main) {
		_cpFriendlyURLEntry.setMain(main);
	}

	/**
	* Sets the modified date of this cp friendly url entry.
	*
	* @param modifiedDate the modified date of this cp friendly url entry
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_cpFriendlyURLEntry.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_cpFriendlyURLEntry.setNew(n);
	}

	/**
	* Sets the primary key of this cp friendly url entry.
	*
	* @param primaryKey the primary key of this cp friendly url entry
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_cpFriendlyURLEntry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_cpFriendlyURLEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the url title of this cp friendly url entry.
	*
	* @param urlTitle the url title of this cp friendly url entry
	*/
	@Override
	public void setUrlTitle(String urlTitle) {
		_cpFriendlyURLEntry.setUrlTitle(urlTitle);
	}

	/**
	* Sets the user ID of this cp friendly url entry.
	*
	* @param userId the user ID of this cp friendly url entry
	*/
	@Override
	public void setUserId(long userId) {
		_cpFriendlyURLEntry.setUserId(userId);
	}

	/**
	* Sets the user name of this cp friendly url entry.
	*
	* @param userName the user name of this cp friendly url entry
	*/
	@Override
	public void setUserName(String userName) {
		_cpFriendlyURLEntry.setUserName(userName);
	}

	/**
	* Sets the user uuid of this cp friendly url entry.
	*
	* @param userUuid the user uuid of this cp friendly url entry
	*/
	@Override
	public void setUserUuid(String userUuid) {
		_cpFriendlyURLEntry.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this cp friendly url entry.
	*
	* @param uuid the uuid of this cp friendly url entry
	*/
	@Override
	public void setUuid(String uuid) {
		_cpFriendlyURLEntry.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CPFriendlyURLEntry> toCacheModel() {
		return _cpFriendlyURLEntry.toCacheModel();
	}

	@Override
	public CPFriendlyURLEntry toEscapedModel() {
		return new CPFriendlyURLEntryWrapper(_cpFriendlyURLEntry.toEscapedModel());
	}

	@Override
	public String toString() {
		return _cpFriendlyURLEntry.toString();
	}

	@Override
	public CPFriendlyURLEntry toUnescapedModel() {
		return new CPFriendlyURLEntryWrapper(_cpFriendlyURLEntry.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _cpFriendlyURLEntry.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CPFriendlyURLEntryWrapper)) {
			return false;
		}

		CPFriendlyURLEntryWrapper cpFriendlyURLEntryWrapper = (CPFriendlyURLEntryWrapper)obj;

		if (Objects.equals(_cpFriendlyURLEntry,
					cpFriendlyURLEntryWrapper._cpFriendlyURLEntry)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _cpFriendlyURLEntry.getStagedModelType();
	}

	@Override
	public CPFriendlyURLEntry getWrappedModel() {
		return _cpFriendlyURLEntry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _cpFriendlyURLEntry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _cpFriendlyURLEntry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_cpFriendlyURLEntry.resetOriginalValues();
	}

	private final CPFriendlyURLEntry _cpFriendlyURLEntry;
}