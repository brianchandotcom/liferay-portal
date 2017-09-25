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

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link LayoutPageTemplateFolder}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplateFolder
 * @generated
 */
@ProviderType
public class LayoutPageTemplateFolderWrapper implements LayoutPageTemplateFolder,
	ModelWrapper<LayoutPageTemplateFolder> {
	public LayoutPageTemplateFolderWrapper(
		LayoutPageTemplateFolder layoutPageTemplateFolder) {
		_layoutPageTemplateFolder = layoutPageTemplateFolder;
	}

	@Override
	public Class<?> getModelClass() {
		return LayoutPageTemplateFolder.class;
	}

	@Override
	public String getModelClassName() {
		return LayoutPageTemplateFolder.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("layoutPageTemplateFolderId",
			getLayoutPageTemplateFolderId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());
		attributes.put("description", getDescription());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long layoutPageTemplateFolderId = (Long)attributes.get(
				"layoutPageTemplateFolderId");

		if (layoutPageTemplateFolderId != null) {
			setLayoutPageTemplateFolderId(layoutPageTemplateFolderId);
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

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new LayoutPageTemplateFolderWrapper((LayoutPageTemplateFolder)_layoutPageTemplateFolder.clone());
	}

	@Override
	public int compareTo(LayoutPageTemplateFolder layoutPageTemplateFolder) {
		return _layoutPageTemplateFolder.compareTo(layoutPageTemplateFolder);
	}

	/**
	* Returns the company ID of this layout page template folder.
	*
	* @return the company ID of this layout page template folder
	*/
	@Override
	public long getCompanyId() {
		return _layoutPageTemplateFolder.getCompanyId();
	}

	/**
	* Returns the create date of this layout page template folder.
	*
	* @return the create date of this layout page template folder
	*/
	@Override
	public Date getCreateDate() {
		return _layoutPageTemplateFolder.getCreateDate();
	}

	/**
	* Returns the description of this layout page template folder.
	*
	* @return the description of this layout page template folder
	*/
	@Override
	public java.lang.String getDescription() {
		return _layoutPageTemplateFolder.getDescription();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _layoutPageTemplateFolder.getExpandoBridge();
	}

	/**
	* Returns the group ID of this layout page template folder.
	*
	* @return the group ID of this layout page template folder
	*/
	@Override
	public long getGroupId() {
		return _layoutPageTemplateFolder.getGroupId();
	}

	/**
	* Returns the layout page template folder ID of this layout page template folder.
	*
	* @return the layout page template folder ID of this layout page template folder
	*/
	@Override
	public long getLayoutPageTemplateFolderId() {
		return _layoutPageTemplateFolder.getLayoutPageTemplateFolderId();
	}

	/**
	* Returns the modified date of this layout page template folder.
	*
	* @return the modified date of this layout page template folder
	*/
	@Override
	public Date getModifiedDate() {
		return _layoutPageTemplateFolder.getModifiedDate();
	}

	/**
	* Returns the name of this layout page template folder.
	*
	* @return the name of this layout page template folder
	*/
	@Override
	public java.lang.String getName() {
		return _layoutPageTemplateFolder.getName();
	}

	/**
	* Returns the primary key of this layout page template folder.
	*
	* @return the primary key of this layout page template folder
	*/
	@Override
	public long getPrimaryKey() {
		return _layoutPageTemplateFolder.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _layoutPageTemplateFolder.getPrimaryKeyObj();
	}

	/**
	* Returns the user ID of this layout page template folder.
	*
	* @return the user ID of this layout page template folder
	*/
	@Override
	public long getUserId() {
		return _layoutPageTemplateFolder.getUserId();
	}

	/**
	* Returns the user name of this layout page template folder.
	*
	* @return the user name of this layout page template folder
	*/
	@Override
	public java.lang.String getUserName() {
		return _layoutPageTemplateFolder.getUserName();
	}

	/**
	* Returns the user uuid of this layout page template folder.
	*
	* @return the user uuid of this layout page template folder
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _layoutPageTemplateFolder.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _layoutPageTemplateFolder.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _layoutPageTemplateFolder.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _layoutPageTemplateFolder.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _layoutPageTemplateFolder.isNew();
	}

	@Override
	public void persist() {
		_layoutPageTemplateFolder.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_layoutPageTemplateFolder.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this layout page template folder.
	*
	* @param companyId the company ID of this layout page template folder
	*/
	@Override
	public void setCompanyId(long companyId) {
		_layoutPageTemplateFolder.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this layout page template folder.
	*
	* @param createDate the create date of this layout page template folder
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_layoutPageTemplateFolder.setCreateDate(createDate);
	}

	/**
	* Sets the description of this layout page template folder.
	*
	* @param description the description of this layout page template folder
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_layoutPageTemplateFolder.setDescription(description);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_layoutPageTemplateFolder.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_layoutPageTemplateFolder.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_layoutPageTemplateFolder.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this layout page template folder.
	*
	* @param groupId the group ID of this layout page template folder
	*/
	@Override
	public void setGroupId(long groupId) {
		_layoutPageTemplateFolder.setGroupId(groupId);
	}

	/**
	* Sets the layout page template folder ID of this layout page template folder.
	*
	* @param layoutPageTemplateFolderId the layout page template folder ID of this layout page template folder
	*/
	@Override
	public void setLayoutPageTemplateFolderId(long layoutPageTemplateFolderId) {
		_layoutPageTemplateFolder.setLayoutPageTemplateFolderId(layoutPageTemplateFolderId);
	}

	/**
	* Sets the modified date of this layout page template folder.
	*
	* @param modifiedDate the modified date of this layout page template folder
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_layoutPageTemplateFolder.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this layout page template folder.
	*
	* @param name the name of this layout page template folder
	*/
	@Override
	public void setName(java.lang.String name) {
		_layoutPageTemplateFolder.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_layoutPageTemplateFolder.setNew(n);
	}

	/**
	* Sets the primary key of this layout page template folder.
	*
	* @param primaryKey the primary key of this layout page template folder
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_layoutPageTemplateFolder.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_layoutPageTemplateFolder.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the user ID of this layout page template folder.
	*
	* @param userId the user ID of this layout page template folder
	*/
	@Override
	public void setUserId(long userId) {
		_layoutPageTemplateFolder.setUserId(userId);
	}

	/**
	* Sets the user name of this layout page template folder.
	*
	* @param userName the user name of this layout page template folder
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_layoutPageTemplateFolder.setUserName(userName);
	}

	/**
	* Sets the user uuid of this layout page template folder.
	*
	* @param userUuid the user uuid of this layout page template folder
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_layoutPageTemplateFolder.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<LayoutPageTemplateFolder> toCacheModel() {
		return _layoutPageTemplateFolder.toCacheModel();
	}

	@Override
	public LayoutPageTemplateFolder toEscapedModel() {
		return new LayoutPageTemplateFolderWrapper(_layoutPageTemplateFolder.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _layoutPageTemplateFolder.toString();
	}

	@Override
	public LayoutPageTemplateFolder toUnescapedModel() {
		return new LayoutPageTemplateFolderWrapper(_layoutPageTemplateFolder.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _layoutPageTemplateFolder.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof LayoutPageTemplateFolderWrapper)) {
			return false;
		}

		LayoutPageTemplateFolderWrapper layoutPageTemplateFolderWrapper = (LayoutPageTemplateFolderWrapper)obj;

		if (Objects.equals(_layoutPageTemplateFolder,
					layoutPageTemplateFolderWrapper._layoutPageTemplateFolder)) {
			return true;
		}

		return false;
	}

	@Override
	public LayoutPageTemplateFolder getWrappedModel() {
		return _layoutPageTemplateFolder;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _layoutPageTemplateFolder.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _layoutPageTemplateFolder.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_layoutPageTemplateFolder.resetOriginalValues();
	}

	private final LayoutPageTemplateFolder _layoutPageTemplateFolder;
}