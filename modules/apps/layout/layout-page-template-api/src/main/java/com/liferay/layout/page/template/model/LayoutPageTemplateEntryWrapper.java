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
		attributes.put("classTypeId", getClassTypeId());
		attributes.put("name", getName());
		attributes.put("type", getType());
		attributes.put("htmlPreviewEntryId", getHtmlPreviewEntryId());
		attributes.put("defaultTemplate", isDefaultTemplate());
		attributes.put("status", getStatus());
		attributes.put("statusByUserId", getStatusByUserId());
		attributes.put("statusByUserName", getStatusByUserName());
		attributes.put("statusDate", getStatusDate());
		attributes.put("layoutPrototypeId", getLayoutPrototypeId());

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

		Long classTypeId = (Long)attributes.get("classTypeId");

		if (classTypeId != null) {
			setClassTypeId(classTypeId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		Integer type = (Integer)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		Long htmlPreviewEntryId = (Long)attributes.get("htmlPreviewEntryId");

		if (htmlPreviewEntryId != null) {
			setHtmlPreviewEntryId(htmlPreviewEntryId);
		}

		Boolean defaultTemplate = (Boolean)attributes.get("defaultTemplate");

		if (defaultTemplate != null) {
			setDefaultTemplate(defaultTemplate);
		}

		Integer status = (Integer)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}

		Long statusByUserId = (Long)attributes.get("statusByUserId");

		if (statusByUserId != null) {
			setStatusByUserId(statusByUserId);
		}

		String statusByUserName = (String)attributes.get("statusByUserName");

		if (statusByUserName != null) {
			setStatusByUserName(statusByUserName);
		}

		Date statusDate = (Date)attributes.get("statusDate");

		if (statusDate != null) {
			setStatusDate(statusDate);
		}

		Long layoutPrototypeId = (Long)attributes.get("layoutPrototypeId");

		if (layoutPrototypeId != null) {
			setLayoutPrototypeId(layoutPrototypeId);
		}
	}

	@Override
	public Object clone() {
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
	public String getClassName() {
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
	* Returns the class type ID of this layout page template entry.
	*
	* @return the class type ID of this layout page template entry
	*/
	@Override
	public long getClassTypeId() {
		return _layoutPageTemplateEntry.getClassTypeId();
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
	public String getContent()
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
	public String getImagePreviewURL(
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
	* Returns the layout prototype ID of this layout page template entry.
	*
	* @return the layout prototype ID of this layout page template entry
	*/
	@Override
	public long getLayoutPrototypeId() {
		return _layoutPageTemplateEntry.getLayoutPrototypeId();
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
	public String getName() {
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
	* Returns the status of this layout page template entry.
	*
	* @return the status of this layout page template entry
	*/
	@Override
	public int getStatus() {
		return _layoutPageTemplateEntry.getStatus();
	}

	/**
	* Returns the status by user ID of this layout page template entry.
	*
	* @return the status by user ID of this layout page template entry
	*/
	@Override
	public long getStatusByUserId() {
		return _layoutPageTemplateEntry.getStatusByUserId();
	}

	/**
	* Returns the status by user name of this layout page template entry.
	*
	* @return the status by user name of this layout page template entry
	*/
	@Override
	public String getStatusByUserName() {
		return _layoutPageTemplateEntry.getStatusByUserName();
	}

	/**
	* Returns the status by user uuid of this layout page template entry.
	*
	* @return the status by user uuid of this layout page template entry
	*/
	@Override
	public String getStatusByUserUuid() {
		return _layoutPageTemplateEntry.getStatusByUserUuid();
	}

	/**
	* Returns the status date of this layout page template entry.
	*
	* @return the status date of this layout page template entry
	*/
	@Override
	public Date getStatusDate() {
		return _layoutPageTemplateEntry.getStatusDate();
	}

	/**
	* Returns the type of this layout page template entry.
	*
	* @return the type of this layout page template entry
	*/
	@Override
	public int getType() {
		return _layoutPageTemplateEntry.getType();
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
	public String getUserName() {
		return _layoutPageTemplateEntry.getUserName();
	}

	/**
	* Returns the user uuid of this layout page template entry.
	*
	* @return the user uuid of this layout page template entry
	*/
	@Override
	public String getUserUuid() {
		return _layoutPageTemplateEntry.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _layoutPageTemplateEntry.hashCode();
	}

	/**
	* Returns <code>true</code> if this layout page template entry is approved.
	*
	* @return <code>true</code> if this layout page template entry is approved; <code>false</code> otherwise
	*/
	@Override
	public boolean isApproved() {
		return _layoutPageTemplateEntry.isApproved();
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

	/**
	* Returns <code>true</code> if this layout page template entry is denied.
	*
	* @return <code>true</code> if this layout page template entry is denied; <code>false</code> otherwise
	*/
	@Override
	public boolean isDenied() {
		return _layoutPageTemplateEntry.isDenied();
	}

	/**
	* Returns <code>true</code> if this layout page template entry is a draft.
	*
	* @return <code>true</code> if this layout page template entry is a draft; <code>false</code> otherwise
	*/
	@Override
	public boolean isDraft() {
		return _layoutPageTemplateEntry.isDraft();
	}

	@Override
	public boolean isEscapedModel() {
		return _layoutPageTemplateEntry.isEscapedModel();
	}

	/**
	* Returns <code>true</code> if this layout page template entry is expired.
	*
	* @return <code>true</code> if this layout page template entry is expired; <code>false</code> otherwise
	*/
	@Override
	public boolean isExpired() {
		return _layoutPageTemplateEntry.isExpired();
	}

	/**
	* Returns <code>true</code> if this layout page template entry is inactive.
	*
	* @return <code>true</code> if this layout page template entry is inactive; <code>false</code> otherwise
	*/
	@Override
	public boolean isInactive() {
		return _layoutPageTemplateEntry.isInactive();
	}

	/**
	* Returns <code>true</code> if this layout page template entry is incomplete.
	*
	* @return <code>true</code> if this layout page template entry is incomplete; <code>false</code> otherwise
	*/
	@Override
	public boolean isIncomplete() {
		return _layoutPageTemplateEntry.isIncomplete();
	}

	@Override
	public boolean isNew() {
		return _layoutPageTemplateEntry.isNew();
	}

	/**
	* Returns <code>true</code> if this layout page template entry is pending.
	*
	* @return <code>true</code> if this layout page template entry is pending; <code>false</code> otherwise
	*/
	@Override
	public boolean isPending() {
		return _layoutPageTemplateEntry.isPending();
	}

	/**
	* Returns <code>true</code> if this layout page template entry is scheduled.
	*
	* @return <code>true</code> if this layout page template entry is scheduled; <code>false</code> otherwise
	*/
	@Override
	public boolean isScheduled() {
		return _layoutPageTemplateEntry.isScheduled();
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
	public void setClassName(String className) {
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
	* Sets the class type ID of this layout page template entry.
	*
	* @param classTypeId the class type ID of this layout page template entry
	*/
	@Override
	public void setClassTypeId(long classTypeId) {
		_layoutPageTemplateEntry.setClassTypeId(classTypeId);
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
	* Sets the layout prototype ID of this layout page template entry.
	*
	* @param layoutPrototypeId the layout prototype ID of this layout page template entry
	*/
	@Override
	public void setLayoutPrototypeId(long layoutPrototypeId) {
		_layoutPageTemplateEntry.setLayoutPrototypeId(layoutPrototypeId);
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
	public void setName(String name) {
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
	* Sets the status of this layout page template entry.
	*
	* @param status the status of this layout page template entry
	*/
	@Override
	public void setStatus(int status) {
		_layoutPageTemplateEntry.setStatus(status);
	}

	/**
	* Sets the status by user ID of this layout page template entry.
	*
	* @param statusByUserId the status by user ID of this layout page template entry
	*/
	@Override
	public void setStatusByUserId(long statusByUserId) {
		_layoutPageTemplateEntry.setStatusByUserId(statusByUserId);
	}

	/**
	* Sets the status by user name of this layout page template entry.
	*
	* @param statusByUserName the status by user name of this layout page template entry
	*/
	@Override
	public void setStatusByUserName(String statusByUserName) {
		_layoutPageTemplateEntry.setStatusByUserName(statusByUserName);
	}

	/**
	* Sets the status by user uuid of this layout page template entry.
	*
	* @param statusByUserUuid the status by user uuid of this layout page template entry
	*/
	@Override
	public void setStatusByUserUuid(String statusByUserUuid) {
		_layoutPageTemplateEntry.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	* Sets the status date of this layout page template entry.
	*
	* @param statusDate the status date of this layout page template entry
	*/
	@Override
	public void setStatusDate(Date statusDate) {
		_layoutPageTemplateEntry.setStatusDate(statusDate);
	}

	/**
	* Sets the type of this layout page template entry.
	*
	* @param type the type of this layout page template entry
	*/
	@Override
	public void setType(int type) {
		_layoutPageTemplateEntry.setType(type);
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
	public void setUserName(String userName) {
		_layoutPageTemplateEntry.setUserName(userName);
	}

	/**
	* Sets the user uuid of this layout page template entry.
	*
	* @param userUuid the user uuid of this layout page template entry
	*/
	@Override
	public void setUserUuid(String userUuid) {
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
	public String toString() {
		return _layoutPageTemplateEntry.toString();
	}

	@Override
	public LayoutPageTemplateEntry toUnescapedModel() {
		return new LayoutPageTemplateEntryWrapper(_layoutPageTemplateEntry.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
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