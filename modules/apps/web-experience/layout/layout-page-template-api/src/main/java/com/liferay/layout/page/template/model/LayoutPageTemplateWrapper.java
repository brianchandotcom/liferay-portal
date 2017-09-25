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
 * This class is a wrapper for {@link LayoutPageTemplate}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplate
 * @generated
 */
@ProviderType
public class LayoutPageTemplateWrapper implements LayoutPageTemplate,
	ModelWrapper<LayoutPageTemplate> {
	public LayoutPageTemplateWrapper(LayoutPageTemplate layoutPageTemplate) {
		_layoutPageTemplate = layoutPageTemplate;
	}

	@Override
	public Class<?> getModelClass() {
		return LayoutPageTemplate.class;
	}

	@Override
	public String getModelClassName() {
		return LayoutPageTemplate.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("layoutPageTemplateId", getLayoutPageTemplateId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("layoutPageTemplateFolderId",
			getLayoutPageTemplateFolderId());
		attributes.put("name", getName());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long layoutPageTemplateId = (Long)attributes.get("layoutPageTemplateId");

		if (layoutPageTemplateId != null) {
			setLayoutPageTemplateId(layoutPageTemplateId);
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

		Long layoutPageTemplateFolderId = (Long)attributes.get(
				"layoutPageTemplateFolderId");

		if (layoutPageTemplateFolderId != null) {
			setLayoutPageTemplateFolderId(layoutPageTemplateFolderId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new LayoutPageTemplateWrapper((LayoutPageTemplate)_layoutPageTemplate.clone());
	}

	@Override
	public int compareTo(LayoutPageTemplate layoutPageTemplate) {
		return _layoutPageTemplate.compareTo(layoutPageTemplate);
	}

	/**
	* Returns the company ID of this layout page template.
	*
	* @return the company ID of this layout page template
	*/
	@Override
	public long getCompanyId() {
		return _layoutPageTemplate.getCompanyId();
	}

	/**
	* Returns the create date of this layout page template.
	*
	* @return the create date of this layout page template
	*/
	@Override
	public Date getCreateDate() {
		return _layoutPageTemplate.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _layoutPageTemplate.getExpandoBridge();
	}

	/**
	* Returns the group ID of this layout page template.
	*
	* @return the group ID of this layout page template
	*/
	@Override
	public long getGroupId() {
		return _layoutPageTemplate.getGroupId();
	}

	/**
	* Returns the layout page template folder ID of this layout page template.
	*
	* @return the layout page template folder ID of this layout page template
	*/
	@Override
	public long getLayoutPageTemplateFolderId() {
		return _layoutPageTemplate.getLayoutPageTemplateFolderId();
	}

	/**
	* Returns the layout page template ID of this layout page template.
	*
	* @return the layout page template ID of this layout page template
	*/
	@Override
	public long getLayoutPageTemplateId() {
		return _layoutPageTemplate.getLayoutPageTemplateId();
	}

	/**
	* Returns the modified date of this layout page template.
	*
	* @return the modified date of this layout page template
	*/
	@Override
	public Date getModifiedDate() {
		return _layoutPageTemplate.getModifiedDate();
	}

	/**
	* Returns the name of this layout page template.
	*
	* @return the name of this layout page template
	*/
	@Override
	public java.lang.String getName() {
		return _layoutPageTemplate.getName();
	}

	/**
	* Returns the primary key of this layout page template.
	*
	* @return the primary key of this layout page template
	*/
	@Override
	public long getPrimaryKey() {
		return _layoutPageTemplate.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _layoutPageTemplate.getPrimaryKeyObj();
	}

	/**
	* Returns the user ID of this layout page template.
	*
	* @return the user ID of this layout page template
	*/
	@Override
	public long getUserId() {
		return _layoutPageTemplate.getUserId();
	}

	/**
	* Returns the user name of this layout page template.
	*
	* @return the user name of this layout page template
	*/
	@Override
	public java.lang.String getUserName() {
		return _layoutPageTemplate.getUserName();
	}

	/**
	* Returns the user uuid of this layout page template.
	*
	* @return the user uuid of this layout page template
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _layoutPageTemplate.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _layoutPageTemplate.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _layoutPageTemplate.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _layoutPageTemplate.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _layoutPageTemplate.isNew();
	}

	@Override
	public void persist() {
		_layoutPageTemplate.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_layoutPageTemplate.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this layout page template.
	*
	* @param companyId the company ID of this layout page template
	*/
	@Override
	public void setCompanyId(long companyId) {
		_layoutPageTemplate.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this layout page template.
	*
	* @param createDate the create date of this layout page template
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_layoutPageTemplate.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_layoutPageTemplate.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_layoutPageTemplate.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_layoutPageTemplate.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this layout page template.
	*
	* @param groupId the group ID of this layout page template
	*/
	@Override
	public void setGroupId(long groupId) {
		_layoutPageTemplate.setGroupId(groupId);
	}

	/**
	* Sets the layout page template folder ID of this layout page template.
	*
	* @param layoutPageTemplateFolderId the layout page template folder ID of this layout page template
	*/
	@Override
	public void setLayoutPageTemplateFolderId(long layoutPageTemplateFolderId) {
		_layoutPageTemplate.setLayoutPageTemplateFolderId(layoutPageTemplateFolderId);
	}

	/**
	* Sets the layout page template ID of this layout page template.
	*
	* @param layoutPageTemplateId the layout page template ID of this layout page template
	*/
	@Override
	public void setLayoutPageTemplateId(long layoutPageTemplateId) {
		_layoutPageTemplate.setLayoutPageTemplateId(layoutPageTemplateId);
	}

	/**
	* Sets the modified date of this layout page template.
	*
	* @param modifiedDate the modified date of this layout page template
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_layoutPageTemplate.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this layout page template.
	*
	* @param name the name of this layout page template
	*/
	@Override
	public void setName(java.lang.String name) {
		_layoutPageTemplate.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_layoutPageTemplate.setNew(n);
	}

	/**
	* Sets the primary key of this layout page template.
	*
	* @param primaryKey the primary key of this layout page template
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_layoutPageTemplate.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_layoutPageTemplate.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the user ID of this layout page template.
	*
	* @param userId the user ID of this layout page template
	*/
	@Override
	public void setUserId(long userId) {
		_layoutPageTemplate.setUserId(userId);
	}

	/**
	* Sets the user name of this layout page template.
	*
	* @param userName the user name of this layout page template
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_layoutPageTemplate.setUserName(userName);
	}

	/**
	* Sets the user uuid of this layout page template.
	*
	* @param userUuid the user uuid of this layout page template
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_layoutPageTemplate.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<LayoutPageTemplate> toCacheModel() {
		return _layoutPageTemplate.toCacheModel();
	}

	@Override
	public LayoutPageTemplate toEscapedModel() {
		return new LayoutPageTemplateWrapper(_layoutPageTemplate.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _layoutPageTemplate.toString();
	}

	@Override
	public LayoutPageTemplate toUnescapedModel() {
		return new LayoutPageTemplateWrapper(_layoutPageTemplate.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _layoutPageTemplate.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof LayoutPageTemplateWrapper)) {
			return false;
		}

		LayoutPageTemplateWrapper layoutPageTemplateWrapper = (LayoutPageTemplateWrapper)obj;

		if (Objects.equals(_layoutPageTemplate,
					layoutPageTemplateWrapper._layoutPageTemplate)) {
			return true;
		}

		return false;
	}

	@Override
	public LayoutPageTemplate getWrappedModel() {
		return _layoutPageTemplate;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _layoutPageTemplate.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _layoutPageTemplate.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_layoutPageTemplate.resetOriginalValues();
	}

	private final LayoutPageTemplate _layoutPageTemplate;
}