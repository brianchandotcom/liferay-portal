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
 * This class is a wrapper for {@link CPDisplayLayout}.
 * </p>
 *
 * @author Marco Leo
 * @see CPDisplayLayout
 * @generated
 */
@ProviderType
public class CPDisplayLayoutWrapper implements CPDisplayLayout,
	ModelWrapper<CPDisplayLayout> {
	public CPDisplayLayoutWrapper(CPDisplayLayout cpDisplayLayout) {
		_cpDisplayLayout = cpDisplayLayout;
	}

	@Override
	public Class<?> getModelClass() {
		return CPDisplayLayout.class;
	}

	@Override
	public String getModelClassName() {
		return CPDisplayLayout.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("CPDisplayLayoutId", getCPDisplayLayoutId());
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

		Long CPDisplayLayoutId = (Long)attributes.get("CPDisplayLayoutId");

		if (CPDisplayLayoutId != null) {
			setCPDisplayLayoutId(CPDisplayLayoutId);
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
	public java.lang.Object clone() {
		return new CPDisplayLayoutWrapper((CPDisplayLayout)_cpDisplayLayout.clone());
	}

	@Override
	public int compareTo(CPDisplayLayout cpDisplayLayout) {
		return _cpDisplayLayout.compareTo(cpDisplayLayout);
	}

	/**
	* Returns the fully qualified class name of this cp display layout.
	*
	* @return the fully qualified class name of this cp display layout
	*/
	@Override
	public java.lang.String getClassName() {
		return _cpDisplayLayout.getClassName();
	}

	/**
	* Returns the class name ID of this cp display layout.
	*
	* @return the class name ID of this cp display layout
	*/
	@Override
	public long getClassNameId() {
		return _cpDisplayLayout.getClassNameId();
	}

	/**
	* Returns the class pk of this cp display layout.
	*
	* @return the class pk of this cp display layout
	*/
	@Override
	public long getClassPK() {
		return _cpDisplayLayout.getClassPK();
	}

	/**
	* Returns the company ID of this cp display layout.
	*
	* @return the company ID of this cp display layout
	*/
	@Override
	public long getCompanyId() {
		return _cpDisplayLayout.getCompanyId();
	}

	/**
	* Returns the cp display layout ID of this cp display layout.
	*
	* @return the cp display layout ID of this cp display layout
	*/
	@Override
	public long getCPDisplayLayoutId() {
		return _cpDisplayLayout.getCPDisplayLayoutId();
	}

	/**
	* Returns the create date of this cp display layout.
	*
	* @return the create date of this cp display layout
	*/
	@Override
	public Date getCreateDate() {
		return _cpDisplayLayout.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _cpDisplayLayout.getExpandoBridge();
	}

	/**
	* Returns the group ID of this cp display layout.
	*
	* @return the group ID of this cp display layout
	*/
	@Override
	public long getGroupId() {
		return _cpDisplayLayout.getGroupId();
	}

	/**
	* Returns the layout uuid of this cp display layout.
	*
	* @return the layout uuid of this cp display layout
	*/
	@Override
	public java.lang.String getLayoutUuid() {
		return _cpDisplayLayout.getLayoutUuid();
	}

	/**
	* Returns the modified date of this cp display layout.
	*
	* @return the modified date of this cp display layout
	*/
	@Override
	public Date getModifiedDate() {
		return _cpDisplayLayout.getModifiedDate();
	}

	/**
	* Returns the primary key of this cp display layout.
	*
	* @return the primary key of this cp display layout
	*/
	@Override
	public long getPrimaryKey() {
		return _cpDisplayLayout.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _cpDisplayLayout.getPrimaryKeyObj();
	}

	/**
	* Returns the user ID of this cp display layout.
	*
	* @return the user ID of this cp display layout
	*/
	@Override
	public long getUserId() {
		return _cpDisplayLayout.getUserId();
	}

	/**
	* Returns the user name of this cp display layout.
	*
	* @return the user name of this cp display layout
	*/
	@Override
	public java.lang.String getUserName() {
		return _cpDisplayLayout.getUserName();
	}

	/**
	* Returns the user uuid of this cp display layout.
	*
	* @return the user uuid of this cp display layout
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _cpDisplayLayout.getUserUuid();
	}

	/**
	* Returns the uuid of this cp display layout.
	*
	* @return the uuid of this cp display layout
	*/
	@Override
	public java.lang.String getUuid() {
		return _cpDisplayLayout.getUuid();
	}

	@Override
	public int hashCode() {
		return _cpDisplayLayout.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _cpDisplayLayout.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _cpDisplayLayout.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _cpDisplayLayout.isNew();
	}

	@Override
	public void persist() {
		_cpDisplayLayout.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_cpDisplayLayout.setCachedModel(cachedModel);
	}

	@Override
	public void setClassName(java.lang.String className) {
		_cpDisplayLayout.setClassName(className);
	}

	/**
	* Sets the class name ID of this cp display layout.
	*
	* @param classNameId the class name ID of this cp display layout
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_cpDisplayLayout.setClassNameId(classNameId);
	}

	/**
	* Sets the class pk of this cp display layout.
	*
	* @param classPK the class pk of this cp display layout
	*/
	@Override
	public void setClassPK(long classPK) {
		_cpDisplayLayout.setClassPK(classPK);
	}

	/**
	* Sets the company ID of this cp display layout.
	*
	* @param companyId the company ID of this cp display layout
	*/
	@Override
	public void setCompanyId(long companyId) {
		_cpDisplayLayout.setCompanyId(companyId);
	}

	/**
	* Sets the cp display layout ID of this cp display layout.
	*
	* @param CPDisplayLayoutId the cp display layout ID of this cp display layout
	*/
	@Override
	public void setCPDisplayLayoutId(long CPDisplayLayoutId) {
		_cpDisplayLayout.setCPDisplayLayoutId(CPDisplayLayoutId);
	}

	/**
	* Sets the create date of this cp display layout.
	*
	* @param createDate the create date of this cp display layout
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_cpDisplayLayout.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_cpDisplayLayout.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_cpDisplayLayout.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_cpDisplayLayout.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this cp display layout.
	*
	* @param groupId the group ID of this cp display layout
	*/
	@Override
	public void setGroupId(long groupId) {
		_cpDisplayLayout.setGroupId(groupId);
	}

	/**
	* Sets the layout uuid of this cp display layout.
	*
	* @param layoutUuid the layout uuid of this cp display layout
	*/
	@Override
	public void setLayoutUuid(java.lang.String layoutUuid) {
		_cpDisplayLayout.setLayoutUuid(layoutUuid);
	}

	/**
	* Sets the modified date of this cp display layout.
	*
	* @param modifiedDate the modified date of this cp display layout
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_cpDisplayLayout.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_cpDisplayLayout.setNew(n);
	}

	/**
	* Sets the primary key of this cp display layout.
	*
	* @param primaryKey the primary key of this cp display layout
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_cpDisplayLayout.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_cpDisplayLayout.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the user ID of this cp display layout.
	*
	* @param userId the user ID of this cp display layout
	*/
	@Override
	public void setUserId(long userId) {
		_cpDisplayLayout.setUserId(userId);
	}

	/**
	* Sets the user name of this cp display layout.
	*
	* @param userName the user name of this cp display layout
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_cpDisplayLayout.setUserName(userName);
	}

	/**
	* Sets the user uuid of this cp display layout.
	*
	* @param userUuid the user uuid of this cp display layout
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_cpDisplayLayout.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this cp display layout.
	*
	* @param uuid the uuid of this cp display layout
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_cpDisplayLayout.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CPDisplayLayout> toCacheModel() {
		return _cpDisplayLayout.toCacheModel();
	}

	@Override
	public CPDisplayLayout toEscapedModel() {
		return new CPDisplayLayoutWrapper(_cpDisplayLayout.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _cpDisplayLayout.toString();
	}

	@Override
	public CPDisplayLayout toUnescapedModel() {
		return new CPDisplayLayoutWrapper(_cpDisplayLayout.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _cpDisplayLayout.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CPDisplayLayoutWrapper)) {
			return false;
		}

		CPDisplayLayoutWrapper cpDisplayLayoutWrapper = (CPDisplayLayoutWrapper)obj;

		if (Objects.equals(_cpDisplayLayout,
					cpDisplayLayoutWrapper._cpDisplayLayout)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _cpDisplayLayout.getStagedModelType();
	}

	@Override
	public CPDisplayLayout getWrappedModel() {
		return _cpDisplayLayout;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _cpDisplayLayout.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _cpDisplayLayout.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_cpDisplayLayout.resetOriginalValues();
	}

	private final CPDisplayLayout _cpDisplayLayout;
}