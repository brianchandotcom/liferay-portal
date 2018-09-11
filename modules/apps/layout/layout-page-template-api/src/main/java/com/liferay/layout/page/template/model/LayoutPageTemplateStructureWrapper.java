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

package com.liferay.layout.page.template.model;

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
 * This class is a wrapper for {@link LayoutPageTemplateStructure}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplateStructure
 * @generated
 */
@ProviderType
public class LayoutPageTemplateStructureWrapper
	implements LayoutPageTemplateStructure,
		ModelWrapper<LayoutPageTemplateStructure> {
	public LayoutPageTemplateStructureWrapper(
		LayoutPageTemplateStructure layoutPageTemplateStructure) {
		_layoutPageTemplateStructure = layoutPageTemplateStructure;
	}

	@Override
	public Class<?> getModelClass() {
		return LayoutPageTemplateStructure.class;
	}

	@Override
	public String getModelClassName() {
		return LayoutPageTemplateStructure.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("layoutPageTemplateStructureId",
			getLayoutPageTemplateStructureId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("data", getData());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long layoutPageTemplateStructureId = (Long)attributes.get(
				"layoutPageTemplateStructureId");

		if (layoutPageTemplateStructureId != null) {
			setLayoutPageTemplateStructureId(layoutPageTemplateStructureId);
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

		String data = (String)attributes.get("data");

		if (data != null) {
			setData(data);
		}
	}

	@Override
	public Object clone() {
		return new LayoutPageTemplateStructureWrapper((LayoutPageTemplateStructure)_layoutPageTemplateStructure.clone());
	}

	@Override
	public int compareTo(
		LayoutPageTemplateStructure layoutPageTemplateStructure) {
		return _layoutPageTemplateStructure.compareTo(layoutPageTemplateStructure);
	}

	/**
	* Returns the fully qualified class name of this layout page template structure.
	*
	* @return the fully qualified class name of this layout page template structure
	*/
	@Override
	public String getClassName() {
		return _layoutPageTemplateStructure.getClassName();
	}

	/**
	* Returns the class name ID of this layout page template structure.
	*
	* @return the class name ID of this layout page template structure
	*/
	@Override
	public long getClassNameId() {
		return _layoutPageTemplateStructure.getClassNameId();
	}

	/**
	* Returns the class pk of this layout page template structure.
	*
	* @return the class pk of this layout page template structure
	*/
	@Override
	public long getClassPK() {
		return _layoutPageTemplateStructure.getClassPK();
	}

	/**
	* Returns the company ID of this layout page template structure.
	*
	* @return the company ID of this layout page template structure
	*/
	@Override
	public long getCompanyId() {
		return _layoutPageTemplateStructure.getCompanyId();
	}

	/**
	* Returns the create date of this layout page template structure.
	*
	* @return the create date of this layout page template structure
	*/
	@Override
	public Date getCreateDate() {
		return _layoutPageTemplateStructure.getCreateDate();
	}

	/**
	* Returns the data of this layout page template structure.
	*
	* @return the data of this layout page template structure
	*/
	@Override
	public String getData() {
		return _layoutPageTemplateStructure.getData();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _layoutPageTemplateStructure.getExpandoBridge();
	}

	/**
	* Returns the group ID of this layout page template structure.
	*
	* @return the group ID of this layout page template structure
	*/
	@Override
	public long getGroupId() {
		return _layoutPageTemplateStructure.getGroupId();
	}

	/**
	* Returns the layout page template structure ID of this layout page template structure.
	*
	* @return the layout page template structure ID of this layout page template structure
	*/
	@Override
	public long getLayoutPageTemplateStructureId() {
		return _layoutPageTemplateStructure.getLayoutPageTemplateStructureId();
	}

	/**
	* Returns the modified date of this layout page template structure.
	*
	* @return the modified date of this layout page template structure
	*/
	@Override
	public Date getModifiedDate() {
		return _layoutPageTemplateStructure.getModifiedDate();
	}

	/**
	* Returns the primary key of this layout page template structure.
	*
	* @return the primary key of this layout page template structure
	*/
	@Override
	public long getPrimaryKey() {
		return _layoutPageTemplateStructure.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _layoutPageTemplateStructure.getPrimaryKeyObj();
	}

	/**
	* Returns the user ID of this layout page template structure.
	*
	* @return the user ID of this layout page template structure
	*/
	@Override
	public long getUserId() {
		return _layoutPageTemplateStructure.getUserId();
	}

	/**
	* Returns the user name of this layout page template structure.
	*
	* @return the user name of this layout page template structure
	*/
	@Override
	public String getUserName() {
		return _layoutPageTemplateStructure.getUserName();
	}

	/**
	* Returns the user uuid of this layout page template structure.
	*
	* @return the user uuid of this layout page template structure
	*/
	@Override
	public String getUserUuid() {
		return _layoutPageTemplateStructure.getUserUuid();
	}

	/**
	* Returns the uuid of this layout page template structure.
	*
	* @return the uuid of this layout page template structure
	*/
	@Override
	public String getUuid() {
		return _layoutPageTemplateStructure.getUuid();
	}

	@Override
	public int hashCode() {
		return _layoutPageTemplateStructure.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _layoutPageTemplateStructure.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _layoutPageTemplateStructure.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _layoutPageTemplateStructure.isNew();
	}

	@Override
	public void persist() {
		_layoutPageTemplateStructure.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_layoutPageTemplateStructure.setCachedModel(cachedModel);
	}

	@Override
	public void setClassName(String className) {
		_layoutPageTemplateStructure.setClassName(className);
	}

	/**
	* Sets the class name ID of this layout page template structure.
	*
	* @param classNameId the class name ID of this layout page template structure
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_layoutPageTemplateStructure.setClassNameId(classNameId);
	}

	/**
	* Sets the class pk of this layout page template structure.
	*
	* @param classPK the class pk of this layout page template structure
	*/
	@Override
	public void setClassPK(long classPK) {
		_layoutPageTemplateStructure.setClassPK(classPK);
	}

	/**
	* Sets the company ID of this layout page template structure.
	*
	* @param companyId the company ID of this layout page template structure
	*/
	@Override
	public void setCompanyId(long companyId) {
		_layoutPageTemplateStructure.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this layout page template structure.
	*
	* @param createDate the create date of this layout page template structure
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_layoutPageTemplateStructure.setCreateDate(createDate);
	}

	/**
	* Sets the data of this layout page template structure.
	*
	* @param data the data of this layout page template structure
	*/
	@Override
	public void setData(String data) {
		_layoutPageTemplateStructure.setData(data);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_layoutPageTemplateStructure.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_layoutPageTemplateStructure.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_layoutPageTemplateStructure.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this layout page template structure.
	*
	* @param groupId the group ID of this layout page template structure
	*/
	@Override
	public void setGroupId(long groupId) {
		_layoutPageTemplateStructure.setGroupId(groupId);
	}

	/**
	* Sets the layout page template structure ID of this layout page template structure.
	*
	* @param layoutPageTemplateStructureId the layout page template structure ID of this layout page template structure
	*/
	@Override
	public void setLayoutPageTemplateStructureId(
		long layoutPageTemplateStructureId) {
		_layoutPageTemplateStructure.setLayoutPageTemplateStructureId(layoutPageTemplateStructureId);
	}

	/**
	* Sets the modified date of this layout page template structure.
	*
	* @param modifiedDate the modified date of this layout page template structure
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_layoutPageTemplateStructure.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_layoutPageTemplateStructure.setNew(n);
	}

	/**
	* Sets the primary key of this layout page template structure.
	*
	* @param primaryKey the primary key of this layout page template structure
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_layoutPageTemplateStructure.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_layoutPageTemplateStructure.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the user ID of this layout page template structure.
	*
	* @param userId the user ID of this layout page template structure
	*/
	@Override
	public void setUserId(long userId) {
		_layoutPageTemplateStructure.setUserId(userId);
	}

	/**
	* Sets the user name of this layout page template structure.
	*
	* @param userName the user name of this layout page template structure
	*/
	@Override
	public void setUserName(String userName) {
		_layoutPageTemplateStructure.setUserName(userName);
	}

	/**
	* Sets the user uuid of this layout page template structure.
	*
	* @param userUuid the user uuid of this layout page template structure
	*/
	@Override
	public void setUserUuid(String userUuid) {
		_layoutPageTemplateStructure.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this layout page template structure.
	*
	* @param uuid the uuid of this layout page template structure
	*/
	@Override
	public void setUuid(String uuid) {
		_layoutPageTemplateStructure.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<LayoutPageTemplateStructure> toCacheModel() {
		return _layoutPageTemplateStructure.toCacheModel();
	}

	@Override
	public LayoutPageTemplateStructure toEscapedModel() {
		return new LayoutPageTemplateStructureWrapper(_layoutPageTemplateStructure.toEscapedModel());
	}

	@Override
	public String toString() {
		return _layoutPageTemplateStructure.toString();
	}

	@Override
	public LayoutPageTemplateStructure toUnescapedModel() {
		return new LayoutPageTemplateStructureWrapper(_layoutPageTemplateStructure.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _layoutPageTemplateStructure.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof LayoutPageTemplateStructureWrapper)) {
			return false;
		}

		LayoutPageTemplateStructureWrapper layoutPageTemplateStructureWrapper = (LayoutPageTemplateStructureWrapper)obj;

		if (Objects.equals(_layoutPageTemplateStructure,
					layoutPageTemplateStructureWrapper._layoutPageTemplateStructure)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _layoutPageTemplateStructure.getStagedModelType();
	}

	@Override
	public LayoutPageTemplateStructure getWrappedModel() {
		return _layoutPageTemplateStructure;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _layoutPageTemplateStructure.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _layoutPageTemplateStructure.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_layoutPageTemplateStructure.resetOriginalValues();
	}

	private final LayoutPageTemplateStructure _layoutPageTemplateStructure;
}