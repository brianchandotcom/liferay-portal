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

package com.liferay.dynamic.data.mapping.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import com.liferay.portlet.exportimport.lar.StagedModelType;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link DDMStructureLayout}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMStructureLayout
 * @generated
 */
@ProviderType
public class DDMStructureLayoutWrapper implements DDMStructureLayout,
	ModelWrapper<DDMStructureLayout> {
	public DDMStructureLayoutWrapper(DDMStructureLayout ddmStructureLayout) {
		_ddmStructureLayout = ddmStructureLayout;
	}

	@Override
	public Class<?> getModelClass() {
		return DDMStructureLayout.class;
	}

	@Override
	public String getModelClassName() {
		return DDMStructureLayout.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("structureLayoutId", getStructureLayoutId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("structureVersionId", getStructureVersionId());
		attributes.put("definition", getDefinition());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long structureLayoutId = (Long)attributes.get("structureLayoutId");

		if (structureLayoutId != null) {
			setStructureLayoutId(structureLayoutId);
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

		Long structureVersionId = (Long)attributes.get("structureVersionId");

		if (structureVersionId != null) {
			setStructureVersionId(structureVersionId);
		}

		String definition = (String)attributes.get("definition");

		if (definition != null) {
			setDefinition(definition);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new DDMStructureLayoutWrapper((DDMStructureLayout)_ddmStructureLayout.clone());
	}

	@Override
	public int compareTo(
		com.liferay.dynamic.data.mapping.model.DDMStructureLayout ddmStructureLayout) {
		return _ddmStructureLayout.compareTo(ddmStructureLayout);
	}

	/**
	* Returns the company ID of this d d m structure layout.
	*
	* @return the company ID of this d d m structure layout
	*/
	@Override
	public long getCompanyId() {
		return _ddmStructureLayout.getCompanyId();
	}

	/**
	* Returns the create date of this d d m structure layout.
	*
	* @return the create date of this d d m structure layout
	*/
	@Override
	public Date getCreateDate() {
		return _ddmStructureLayout.getCreateDate();
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMFormLayout getDDMFormLayout() {
		return _ddmStructureLayout.getDDMFormLayout();
	}

	/**
	* Returns the definition of this d d m structure layout.
	*
	* @return the definition of this d d m structure layout
	*/
	@Override
	public java.lang.String getDefinition() {
		return _ddmStructureLayout.getDefinition();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _ddmStructureLayout.getExpandoBridge();
	}

	/**
	* Returns the group ID of this d d m structure layout.
	*
	* @return the group ID of this d d m structure layout
	*/
	@Override
	public long getGroupId() {
		return _ddmStructureLayout.getGroupId();
	}

	/**
	* Returns the modified date of this d d m structure layout.
	*
	* @return the modified date of this d d m structure layout
	*/
	@Override
	public Date getModifiedDate() {
		return _ddmStructureLayout.getModifiedDate();
	}

	/**
	* Returns the primary key of this d d m structure layout.
	*
	* @return the primary key of this d d m structure layout
	*/
	@Override
	public long getPrimaryKey() {
		return _ddmStructureLayout.getPrimaryKey();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _ddmStructureLayout.getPrimaryKeyObj();
	}

	/**
	* Returns the structure layout ID of this d d m structure layout.
	*
	* @return the structure layout ID of this d d m structure layout
	*/
	@Override
	public long getStructureLayoutId() {
		return _ddmStructureLayout.getStructureLayoutId();
	}

	/**
	* Returns the structure version ID of this d d m structure layout.
	*
	* @return the structure version ID of this d d m structure layout
	*/
	@Override
	public long getStructureVersionId() {
		return _ddmStructureLayout.getStructureVersionId();
	}

	/**
	* Returns the user ID of this d d m structure layout.
	*
	* @return the user ID of this d d m structure layout
	*/
	@Override
	public long getUserId() {
		return _ddmStructureLayout.getUserId();
	}

	/**
	* Returns the user name of this d d m structure layout.
	*
	* @return the user name of this d d m structure layout
	*/
	@Override
	public java.lang.String getUserName() {
		return _ddmStructureLayout.getUserName();
	}

	/**
	* Returns the user uuid of this d d m structure layout.
	*
	* @return the user uuid of this d d m structure layout
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _ddmStructureLayout.getUserUuid();
	}

	/**
	* Returns the uuid of this d d m structure layout.
	*
	* @return the uuid of this d d m structure layout
	*/
	@Override
	public java.lang.String getUuid() {
		return _ddmStructureLayout.getUuid();
	}

	@Override
	public int hashCode() {
		return _ddmStructureLayout.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _ddmStructureLayout.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _ddmStructureLayout.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _ddmStructureLayout.isNew();
	}

	@Override
	public void persist() {
		_ddmStructureLayout.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_ddmStructureLayout.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this d d m structure layout.
	*
	* @param companyId the company ID of this d d m structure layout
	*/
	@Override
	public void setCompanyId(long companyId) {
		_ddmStructureLayout.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this d d m structure layout.
	*
	* @param createDate the create date of this d d m structure layout
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_ddmStructureLayout.setCreateDate(createDate);
	}

	/**
	* Sets the definition of this d d m structure layout.
	*
	* @param definition the definition of this d d m structure layout
	*/
	@Override
	public void setDefinition(java.lang.String definition) {
		_ddmStructureLayout.setDefinition(definition);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_ddmStructureLayout.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_ddmStructureLayout.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_ddmStructureLayout.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this d d m structure layout.
	*
	* @param groupId the group ID of this d d m structure layout
	*/
	@Override
	public void setGroupId(long groupId) {
		_ddmStructureLayout.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this d d m structure layout.
	*
	* @param modifiedDate the modified date of this d d m structure layout
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_ddmStructureLayout.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_ddmStructureLayout.setNew(n);
	}

	/**
	* Sets the primary key of this d d m structure layout.
	*
	* @param primaryKey the primary key of this d d m structure layout
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_ddmStructureLayout.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_ddmStructureLayout.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the structure layout ID of this d d m structure layout.
	*
	* @param structureLayoutId the structure layout ID of this d d m structure layout
	*/
	@Override
	public void setStructureLayoutId(long structureLayoutId) {
		_ddmStructureLayout.setStructureLayoutId(structureLayoutId);
	}

	/**
	* Sets the structure version ID of this d d m structure layout.
	*
	* @param structureVersionId the structure version ID of this d d m structure layout
	*/
	@Override
	public void setStructureVersionId(long structureVersionId) {
		_ddmStructureLayout.setStructureVersionId(structureVersionId);
	}

	/**
	* Sets the user ID of this d d m structure layout.
	*
	* @param userId the user ID of this d d m structure layout
	*/
	@Override
	public void setUserId(long userId) {
		_ddmStructureLayout.setUserId(userId);
	}

	/**
	* Sets the user name of this d d m structure layout.
	*
	* @param userName the user name of this d d m structure layout
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_ddmStructureLayout.setUserName(userName);
	}

	/**
	* Sets the user uuid of this d d m structure layout.
	*
	* @param userUuid the user uuid of this d d m structure layout
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_ddmStructureLayout.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this d d m structure layout.
	*
	* @param uuid the uuid of this d d m structure layout
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_ddmStructureLayout.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.dynamic.data.mapping.model.DDMStructureLayout> toCacheModel() {
		return _ddmStructureLayout.toCacheModel();
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMStructureLayout toEscapedModel() {
		return new DDMStructureLayoutWrapper(_ddmStructureLayout.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _ddmStructureLayout.toString();
	}

	@Override
	public com.liferay.dynamic.data.mapping.model.DDMStructureLayout toUnescapedModel() {
		return new DDMStructureLayoutWrapper(_ddmStructureLayout.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _ddmStructureLayout.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DDMStructureLayoutWrapper)) {
			return false;
		}

		DDMStructureLayoutWrapper ddmStructureLayoutWrapper = (DDMStructureLayoutWrapper)obj;

		if (Validator.equals(_ddmStructureLayout,
					ddmStructureLayoutWrapper._ddmStructureLayout)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _ddmStructureLayout.getStagedModelType();
	}

	@Override
	public DDMStructureLayout getWrappedModel() {
		return _ddmStructureLayout;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _ddmStructureLayout.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _ddmStructureLayout.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_ddmStructureLayout.resetOriginalValues();
	}

	private final DDMStructureLayout _ddmStructureLayout;
}