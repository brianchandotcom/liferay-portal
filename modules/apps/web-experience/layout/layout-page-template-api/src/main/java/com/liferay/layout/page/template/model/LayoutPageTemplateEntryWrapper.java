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
 * This class is a wrapper for {@link LayoutPageTemplateEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplateEntry
 * @generated
 */
@ProviderType
public class LayoutPageTemplateEntryWrapper implements LayoutPageTemplateEntry,
	ModelWrapper<LayoutPageTemplateEntry> {
	public LayoutPageTemplateEntryWrapper(
		LayoutPageTemplateEntry layoutPageTemplateEntry) {
		_layoutPageTemplateEntry = layoutPageTemplateEntry;
	}

	@Override
	public Class<?> getModelClass() {
		return LayoutPageTemplateEntry.class;
	}

	@Override
	public String getModelClassName() {
		return LayoutPageTemplateEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("layoutPageTemplateEntryId",
			getLayoutPageTemplateEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("layoutPageTemplateCollectionId",
			getLayoutPageTemplateCollectionId());
		attributes.put("classNameId", getClassNameId());
		attributes.put("name", getName());
		attributes.put("htmlPreviewEntryId", getHtmlPreviewEntryId());
		attributes.put("defaultTemplate", getDefaultTemplate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long layoutPageTemplateEntryId = (Long)attributes.get(
				"layoutPageTemplateEntryId");

		if (layoutPageTemplateEntryId != null) {
			setLayoutPageTemplateEntryId(layoutPageTemplateEntryId);
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

		Long layoutPageTemplateCollectionId = (Long)attributes.get(
				"layoutPageTemplateCollectionId");

		if (layoutPageTemplateCollectionId != null) {
			setLayoutPageTemplateCollectionId(layoutPageTemplateCollectionId);
		}

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		Long htmlPreviewEntryId = (Long)attributes.get("htmlPreviewEntryId");

		if (htmlPreviewEntryId != null) {
			setHtmlPreviewEntryId(htmlPreviewEntryId);
		}

		Boolean defaultTemplate = (Boolean)attributes.get("defaultTemplate");

		if (defaultTemplate != null) {
			setDefaultTemplate(defaultTemplate);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new LayoutPageTemplateEntryWrapper((LayoutPageTemplateEntry)_layoutPageTemplateEntry.clone());
	}

	@Override
	public int compareTo(LayoutPageTemplateEntry layoutPageTemplateEntry) {
		return _layoutPageTemplateEntry.compareTo(layoutPageTemplateEntry);
	}

	/**
	* Returns the fully qualified class name of this layout page template entry.
	*
	* @return the fully qualified class name of this layout page template entry
	*/
	@Override
	public java.lang.String getClassName() {
		return _layoutPageTemplateEntry.getClassName();
	}

	/**
	* Returns the class name ID of this layout page template entry.
	*
	* @return the class name ID of this layout page template entry
	*/
	@Override
	public long getClassNameId() {
		return _layoutPageTemplateEntry.getClassNameId();
	}

	/**
	* Returns the company ID of this layout page template entry.
	*
	* @return the company ID of this layout page template entry
	*/
	@Override
	public long getCompanyId() {
		return _layoutPageTemplateEntry.getCompanyId();
	}

	@Override
	public java.lang.String getContent()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPageTemplateEntry.getContent();
	}

	/**
	* Returns the create date of this layout page template entry.
	*
	* @return the create date of this layout page template entry
	*/
	@Override
	public Date getCreateDate() {
		return _layoutPageTemplateEntry.getCreateDate();
	}

	/**
	* Returns the default template of this layout page template entry.
	*
	* @return the default template of this layout page template entry
	*/
	@Override
	public boolean getDefaultTemplate() {
		return _layoutPageTemplateEntry.getDefaultTemplate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _layoutPageTemplateEntry.getExpandoBridge();
	}

	/**
	* Returns the group ID of this layout page template entry.
	*
	* @return the group ID of this layout page template entry
	*/
	@Override
	public long getGroupId() {
		return _layoutPageTemplateEntry.getGroupId();
	}

	/**
	* Returns the html preview entry ID of this layout page template entry.
	*
	* @return the html preview entry ID of this layout page template entry
	*/
	@Override
	public long getHtmlPreviewEntryId() {
		return _layoutPageTemplateEntry.getHtmlPreviewEntryId();
	}

	@Override
	public java.lang.String getImagePreviewURL(
		com.liferay.portal.kernel.theme.ThemeDisplay themeDisplay) {
		return _layoutPageTemplateEntry.getImagePreviewURL(themeDisplay);
	}

	/**
	* Returns the layout page template collection ID of this layout page template entry.
	*
	* @return the layout page template collection ID of this layout page template entry
	*/
	@Override
	public long getLayoutPageTemplateCollectionId() {
		return _layoutPageTemplateEntry.getLayoutPageTemplateCollectionId();
	}

	/**
	* Returns the layout page template entry ID of this layout page template entry.
	*
	* @return the layout page template entry ID of this layout page template entry
	*/
	@Override
	public long getLayoutPageTemplateEntryId() {
		return _layoutPageTemplateEntry.getLayoutPageTemplateEntryId();
	}

	/**
	* Returns the modified date of this layout page template entry.
	*
	* @return the modified date of this layout page template entry
	*/
	@Override
	public Date getModifiedDate() {
		return _layoutPageTemplateEntry.getModifiedDate();
	}

	/**
	* Returns the name of this layout page template entry.
	*
	* @return the name of this layout page template entry
	*/
	@Override
	public java.lang.String getName() {
		return _layoutPageTemplateEntry.getName();
	}

	/**
	* Returns the primary key of this layout page template entry.
	*
	* @return the primary key of this layout page template entry
	*/
	@Override
	public long getPrimaryKey() {
		return _layoutPageTemplateEntry.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _layoutPageTemplateEntry.getPrimaryKeyObj();
	}

	/**
	* Returns the user ID of this layout page template entry.
	*
	* @return the user ID of this layout page template entry
	*/
	@Override
	public long getUserId() {
		return _layoutPageTemplateEntry.getUserId();
	}

	/**
	* Returns the user name of this layout page template entry.
	*
	* @return the user name of this layout page template entry
	*/
	@Override
	public java.lang.String getUserName() {
		return _layoutPageTemplateEntry.getUserName();
	}

	/**
	* Returns the user uuid of this layout page template entry.
	*
	* @return the user uuid of this layout page template entry
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _layoutPageTemplateEntry.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _layoutPageTemplateEntry.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _layoutPageTemplateEntry.isCachedModel();
	}

	/**
	* Returns <code>true</code> if this layout page template entry is default template.
	*
	* @return <code>true</code> if this layout page template entry is default template; <code>false</code> otherwise
	*/
	@Override
	public boolean isDefaultTemplate() {
		return _layoutPageTemplateEntry.isDefaultTemplate();
	}

	@Override
	public boolean isEscapedModel() {
		return _layoutPageTemplateEntry.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _layoutPageTemplateEntry.isNew();
	}

	@Override
	public void persist() {
		_layoutPageTemplateEntry.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_layoutPageTemplateEntry.setCachedModel(cachedModel);
	}

	@Override
	public void setClassName(java.lang.String className) {
		_layoutPageTemplateEntry.setClassName(className);
	}

	/**
	* Sets the class name ID of this layout page template entry.
	*
	* @param classNameId the class name ID of this layout page template entry
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_layoutPageTemplateEntry.setClassNameId(classNameId);
	}

	/**
	* Sets the company ID of this layout page template entry.
	*
	* @param companyId the company ID of this layout page template entry
	*/
	@Override
	public void setCompanyId(long companyId) {
		_layoutPageTemplateEntry.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this layout page template entry.
	*
	* @param createDate the create date of this layout page template entry
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_layoutPageTemplateEntry.setCreateDate(createDate);
	}

	/**
	* Sets whether this layout page template entry is default template.
	*
	* @param defaultTemplate the default template of this layout page template entry
	*/
	@Override
	public void setDefaultTemplate(boolean defaultTemplate) {
		_layoutPageTemplateEntry.setDefaultTemplate(defaultTemplate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_layoutPageTemplateEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_layoutPageTemplateEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_layoutPageTemplateEntry.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this layout page template entry.
	*
	* @param groupId the group ID of this layout page template entry
	*/
	@Override
	public void setGroupId(long groupId) {
		_layoutPageTemplateEntry.setGroupId(groupId);
	}

	/**
	* Sets the html preview entry ID of this layout page template entry.
	*
	* @param htmlPreviewEntryId the html preview entry ID of this layout page template entry
	*/
	@Override
	public void setHtmlPreviewEntryId(long htmlPreviewEntryId) {
		_layoutPageTemplateEntry.setHtmlPreviewEntryId(htmlPreviewEntryId);
	}

	/**
	* Sets the layout page template collection ID of this layout page template entry.
	*
	* @param layoutPageTemplateCollectionId the layout page template collection ID of this layout page template entry
	*/
	@Override
	public void setLayoutPageTemplateCollectionId(
		long layoutPageTemplateCollectionId) {
		_layoutPageTemplateEntry.setLayoutPageTemplateCollectionId(layoutPageTemplateCollectionId);
	}

	/**
	* Sets the layout page template entry ID of this layout page template entry.
	*
	* @param layoutPageTemplateEntryId the layout page template entry ID of this layout page template entry
	*/
	@Override
	public void setLayoutPageTemplateEntryId(long layoutPageTemplateEntryId) {
		_layoutPageTemplateEntry.setLayoutPageTemplateEntryId(layoutPageTemplateEntryId);
	}

	/**
	* Sets the modified date of this layout page template entry.
	*
	* @param modifiedDate the modified date of this layout page template entry
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_layoutPageTemplateEntry.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this layout page template entry.
	*
	* @param name the name of this layout page template entry
	*/
	@Override
	public void setName(java.lang.String name) {
		_layoutPageTemplateEntry.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_layoutPageTemplateEntry.setNew(n);
	}

	/**
	* Sets the primary key of this layout page template entry.
	*
	* @param primaryKey the primary key of this layout page template entry
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_layoutPageTemplateEntry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_layoutPageTemplateEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the user ID of this layout page template entry.
	*
	* @param userId the user ID of this layout page template entry
	*/
	@Override
	public void setUserId(long userId) {
		_layoutPageTemplateEntry.setUserId(userId);
	}

	/**
	* Sets the user name of this layout page template entry.
	*
	* @param userName the user name of this layout page template entry
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_layoutPageTemplateEntry.setUserName(userName);
	}

	/**
	* Sets the user uuid of this layout page template entry.
	*
	* @param userUuid the user uuid of this layout page template entry
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_layoutPageTemplateEntry.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<LayoutPageTemplateEntry> toCacheModel() {
		return _layoutPageTemplateEntry.toCacheModel();
	}

	@Override
	public LayoutPageTemplateEntry toEscapedModel() {
		return new LayoutPageTemplateEntryWrapper(_layoutPageTemplateEntry.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _layoutPageTemplateEntry.toString();
	}

	@Override
	public LayoutPageTemplateEntry toUnescapedModel() {
		return new LayoutPageTemplateEntryWrapper(_layoutPageTemplateEntry.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _layoutPageTemplateEntry.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof LayoutPageTemplateEntryWrapper)) {
			return false;
		}

		LayoutPageTemplateEntryWrapper layoutPageTemplateEntryWrapper = (LayoutPageTemplateEntryWrapper)obj;

		if (Objects.equals(_layoutPageTemplateEntry,
					layoutPageTemplateEntryWrapper._layoutPageTemplateEntry)) {
			return true;
		}

		return false;
	}

	@Override
	public LayoutPageTemplateEntry getWrappedModel() {
		return _layoutPageTemplateEntry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _layoutPageTemplateEntry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _layoutPageTemplateEntry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_layoutPageTemplateEntry.resetOriginalValues();
	}

	private final LayoutPageTemplateEntry _layoutPageTemplateEntry;
}