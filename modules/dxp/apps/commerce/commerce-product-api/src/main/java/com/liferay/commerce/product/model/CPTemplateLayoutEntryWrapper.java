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
 * This class is a wrapper for {@link CPTemplateLayoutEntry}.
 * </p>
 *
 * @author Marco Leo
 * @see CPTemplateLayoutEntry
 * @generated
 */
@ProviderType
public class CPTemplateLayoutEntryWrapper implements CPTemplateLayoutEntry,
	ModelWrapper<CPTemplateLayoutEntry> {
	public CPTemplateLayoutEntryWrapper(
		CPTemplateLayoutEntry cpTemplateLayoutEntry) {
		_cpTemplateLayoutEntry = cpTemplateLayoutEntry;
	}

	@Override
	public Class<?> getModelClass() {
		return CPTemplateLayoutEntry.class;
	}

	@Override
	public String getModelClassName() {
		return CPTemplateLayoutEntry.class.getName();
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
		attributes.put("layoutUuid", getLayoutUuid());

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

		String layoutUuid = (String)attributes.get("layoutUuid");

		if (layoutUuid != null) {
			setLayoutUuid(layoutUuid);
		}
	}

	@Override
	public CPTemplateLayoutEntry toEscapedModel() {
		return new CPTemplateLayoutEntryWrapper(_cpTemplateLayoutEntry.toEscapedModel());
	}

	@Override
	public CPTemplateLayoutEntry toUnescapedModel() {
		return new CPTemplateLayoutEntryWrapper(_cpTemplateLayoutEntry.toUnescapedModel());
	}

	@Override
	public boolean isCachedModel() {
		return _cpTemplateLayoutEntry.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _cpTemplateLayoutEntry.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _cpTemplateLayoutEntry.isNew();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _cpTemplateLayoutEntry.getExpandoBridge();
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CPTemplateLayoutEntry> toCacheModel() {
		return _cpTemplateLayoutEntry.toCacheModel();
	}

	@Override
	public int compareTo(CPTemplateLayoutEntry cpTemplateLayoutEntry) {
		return _cpTemplateLayoutEntry.compareTo(cpTemplateLayoutEntry);
	}

	@Override
	public int hashCode() {
		return _cpTemplateLayoutEntry.hashCode();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _cpTemplateLayoutEntry.getPrimaryKeyObj();
	}

	@Override
	public java.lang.Object clone() {
		return new CPTemplateLayoutEntryWrapper((CPTemplateLayoutEntry)_cpTemplateLayoutEntry.clone());
	}

	/**
	* Returns the fully qualified class name of this cp template layout entry.
	*
	* @return the fully qualified class name of this cp template layout entry
	*/
	@Override
	public java.lang.String getClassName() {
		return _cpTemplateLayoutEntry.getClassName();
	}

	/**
	* Returns the layout uuid of this cp template layout entry.
	*
	* @return the layout uuid of this cp template layout entry
	*/
	@Override
	public java.lang.String getLayoutUuid() {
		return _cpTemplateLayoutEntry.getLayoutUuid();
	}

	/**
	* Returns the user name of this cp template layout entry.
	*
	* @return the user name of this cp template layout entry
	*/
	@Override
	public java.lang.String getUserName() {
		return _cpTemplateLayoutEntry.getUserName();
	}

	/**
	* Returns the user uuid of this cp template layout entry.
	*
	* @return the user uuid of this cp template layout entry
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _cpTemplateLayoutEntry.getUserUuid();
	}

	/**
	* Returns the uuid of this cp template layout entry.
	*
	* @return the uuid of this cp template layout entry
	*/
	@Override
	public java.lang.String getUuid() {
		return _cpTemplateLayoutEntry.getUuid();
	}

	@Override
	public java.lang.String toString() {
		return _cpTemplateLayoutEntry.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _cpTemplateLayoutEntry.toXmlString();
	}

	/**
	* Returns the create date of this cp template layout entry.
	*
	* @return the create date of this cp template layout entry
	*/
	@Override
	public Date getCreateDate() {
		return _cpTemplateLayoutEntry.getCreateDate();
	}

	/**
	* Returns the modified date of this cp template layout entry.
	*
	* @return the modified date of this cp template layout entry
	*/
	@Override
	public Date getModifiedDate() {
		return _cpTemplateLayoutEntry.getModifiedDate();
	}

	/**
	* Returns the cp friendly url entry ID of this cp template layout entry.
	*
	* @return the cp friendly url entry ID of this cp template layout entry
	*/
	@Override
	public long getCPFriendlyURLEntryId() {
		return _cpTemplateLayoutEntry.getCPFriendlyURLEntryId();
	}

	/**
	* Returns the class name ID of this cp template layout entry.
	*
	* @return the class name ID of this cp template layout entry
	*/
	@Override
	public long getClassNameId() {
		return _cpTemplateLayoutEntry.getClassNameId();
	}

	/**
	* Returns the class pk of this cp template layout entry.
	*
	* @return the class pk of this cp template layout entry
	*/
	@Override
	public long getClassPK() {
		return _cpTemplateLayoutEntry.getClassPK();
	}

	/**
	* Returns the company ID of this cp template layout entry.
	*
	* @return the company ID of this cp template layout entry
	*/
	@Override
	public long getCompanyId() {
		return _cpTemplateLayoutEntry.getCompanyId();
	}

	/**
	* Returns the group ID of this cp template layout entry.
	*
	* @return the group ID of this cp template layout entry
	*/
	@Override
	public long getGroupId() {
		return _cpTemplateLayoutEntry.getGroupId();
	}

	/**
	* Returns the primary key of this cp template layout entry.
	*
	* @return the primary key of this cp template layout entry
	*/
	@Override
	public long getPrimaryKey() {
		return _cpTemplateLayoutEntry.getPrimaryKey();
	}

	/**
	* Returns the user ID of this cp template layout entry.
	*
	* @return the user ID of this cp template layout entry
	*/
	@Override
	public long getUserId() {
		return _cpTemplateLayoutEntry.getUserId();
	}

	@Override
	public void persist() {
		_cpTemplateLayoutEntry.persist();
	}

	/**
	* Sets the cp friendly url entry ID of this cp template layout entry.
	*
	* @param CPFriendlyURLEntryId the cp friendly url entry ID of this cp template layout entry
	*/
	@Override
	public void setCPFriendlyURLEntryId(long CPFriendlyURLEntryId) {
		_cpTemplateLayoutEntry.setCPFriendlyURLEntryId(CPFriendlyURLEntryId);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_cpTemplateLayoutEntry.setCachedModel(cachedModel);
	}

	@Override
	public void setClassName(java.lang.String className) {
		_cpTemplateLayoutEntry.setClassName(className);
	}

	/**
	* Sets the class name ID of this cp template layout entry.
	*
	* @param classNameId the class name ID of this cp template layout entry
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_cpTemplateLayoutEntry.setClassNameId(classNameId);
	}

	/**
	* Sets the class pk of this cp template layout entry.
	*
	* @param classPK the class pk of this cp template layout entry
	*/
	@Override
	public void setClassPK(long classPK) {
		_cpTemplateLayoutEntry.setClassPK(classPK);
	}

	/**
	* Sets the company ID of this cp template layout entry.
	*
	* @param companyId the company ID of this cp template layout entry
	*/
	@Override
	public void setCompanyId(long companyId) {
		_cpTemplateLayoutEntry.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this cp template layout entry.
	*
	* @param createDate the create date of this cp template layout entry
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_cpTemplateLayoutEntry.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_cpTemplateLayoutEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_cpTemplateLayoutEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_cpTemplateLayoutEntry.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this cp template layout entry.
	*
	* @param groupId the group ID of this cp template layout entry
	*/
	@Override
	public void setGroupId(long groupId) {
		_cpTemplateLayoutEntry.setGroupId(groupId);
	}

	/**
	* Sets the layout uuid of this cp template layout entry.
	*
	* @param layoutUuid the layout uuid of this cp template layout entry
	*/
	@Override
	public void setLayoutUuid(java.lang.String layoutUuid) {
		_cpTemplateLayoutEntry.setLayoutUuid(layoutUuid);
	}

	/**
	* Sets the modified date of this cp template layout entry.
	*
	* @param modifiedDate the modified date of this cp template layout entry
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_cpTemplateLayoutEntry.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_cpTemplateLayoutEntry.setNew(n);
	}

	/**
	* Sets the primary key of this cp template layout entry.
	*
	* @param primaryKey the primary key of this cp template layout entry
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_cpTemplateLayoutEntry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_cpTemplateLayoutEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the user ID of this cp template layout entry.
	*
	* @param userId the user ID of this cp template layout entry
	*/
	@Override
	public void setUserId(long userId) {
		_cpTemplateLayoutEntry.setUserId(userId);
	}

	/**
	* Sets the user name of this cp template layout entry.
	*
	* @param userName the user name of this cp template layout entry
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_cpTemplateLayoutEntry.setUserName(userName);
	}

	/**
	* Sets the user uuid of this cp template layout entry.
	*
	* @param userUuid the user uuid of this cp template layout entry
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_cpTemplateLayoutEntry.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this cp template layout entry.
	*
	* @param uuid the uuid of this cp template layout entry
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_cpTemplateLayoutEntry.setUuid(uuid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CPTemplateLayoutEntryWrapper)) {
			return false;
		}

		CPTemplateLayoutEntryWrapper cpTemplateLayoutEntryWrapper = (CPTemplateLayoutEntryWrapper)obj;

		if (Objects.equals(_cpTemplateLayoutEntry,
					cpTemplateLayoutEntryWrapper._cpTemplateLayoutEntry)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _cpTemplateLayoutEntry.getStagedModelType();
	}

	@Override
	public CPTemplateLayoutEntry getWrappedModel() {
		return _cpTemplateLayoutEntry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _cpTemplateLayoutEntry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _cpTemplateLayoutEntry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_cpTemplateLayoutEntry.resetOriginalValues();
	}

	private final CPTemplateLayoutEntry _cpTemplateLayoutEntry;
}