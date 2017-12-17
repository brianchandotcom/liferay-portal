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

package com.liferay.html.preview.model;

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
 * This class is a wrapper for {@link HtmlPreviewEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see HtmlPreviewEntry
 * @generated
 */
@ProviderType
public class HtmlPreviewEntryWrapper implements HtmlPreviewEntry,
	ModelWrapper<HtmlPreviewEntry> {
	public HtmlPreviewEntryWrapper(HtmlPreviewEntry htmlPreviewEntry) {
		_htmlPreviewEntry = htmlPreviewEntry;
	}

	@Override
	public Class<?> getModelClass() {
		return HtmlPreviewEntry.class;
	}

	@Override
	public String getModelClassName() {
		return HtmlPreviewEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("htmlPreviewEntryId", getHtmlPreviewEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("fileEntryId", getFileEntryId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long htmlPreviewEntryId = (Long)attributes.get("htmlPreviewEntryId");

		if (htmlPreviewEntryId != null) {
			setHtmlPreviewEntryId(htmlPreviewEntryId);
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

		Long fileEntryId = (Long)attributes.get("fileEntryId");

		if (fileEntryId != null) {
			setFileEntryId(fileEntryId);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new HtmlPreviewEntryWrapper((HtmlPreviewEntry)_htmlPreviewEntry.clone());
	}

	@Override
	public int compareTo(HtmlPreviewEntry htmlPreviewEntry) {
		return _htmlPreviewEntry.compareTo(htmlPreviewEntry);
	}

	/**
	* Returns the fully qualified class name of this html preview entry.
	*
	* @return the fully qualified class name of this html preview entry
	*/
	@Override
	public java.lang.String getClassName() {
		return _htmlPreviewEntry.getClassName();
	}

	/**
	* Returns the class name ID of this html preview entry.
	*
	* @return the class name ID of this html preview entry
	*/
	@Override
	public long getClassNameId() {
		return _htmlPreviewEntry.getClassNameId();
	}

	/**
	* Returns the class pk of this html preview entry.
	*
	* @return the class pk of this html preview entry
	*/
	@Override
	public long getClassPK() {
		return _htmlPreviewEntry.getClassPK();
	}

	/**
	* Returns the company ID of this html preview entry.
	*
	* @return the company ID of this html preview entry
	*/
	@Override
	public long getCompanyId() {
		return _htmlPreviewEntry.getCompanyId();
	}

	/**
	* Returns the create date of this html preview entry.
	*
	* @return the create date of this html preview entry
	*/
	@Override
	public Date getCreateDate() {
		return _htmlPreviewEntry.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _htmlPreviewEntry.getExpandoBridge();
	}

	/**
	* Returns the file entry ID of this html preview entry.
	*
	* @return the file entry ID of this html preview entry
	*/
	@Override
	public long getFileEntryId() {
		return _htmlPreviewEntry.getFileEntryId();
	}

	/**
	* Returns the group ID of this html preview entry.
	*
	* @return the group ID of this html preview entry
	*/
	@Override
	public long getGroupId() {
		return _htmlPreviewEntry.getGroupId();
	}

	/**
	* Returns the html preview entry ID of this html preview entry.
	*
	* @return the html preview entry ID of this html preview entry
	*/
	@Override
	public long getHtmlPreviewEntryId() {
		return _htmlPreviewEntry.getHtmlPreviewEntryId();
	}

	@Override
	public java.lang.String getImagePreviewURL(
		com.liferay.portal.kernel.theme.ThemeDisplay themeDisplay) {
		return _htmlPreviewEntry.getImagePreviewURL(themeDisplay);
	}

	/**
	* Returns the modified date of this html preview entry.
	*
	* @return the modified date of this html preview entry
	*/
	@Override
	public Date getModifiedDate() {
		return _htmlPreviewEntry.getModifiedDate();
	}

	/**
	* Returns the primary key of this html preview entry.
	*
	* @return the primary key of this html preview entry
	*/
	@Override
	public long getPrimaryKey() {
		return _htmlPreviewEntry.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _htmlPreviewEntry.getPrimaryKeyObj();
	}

	/**
	* Returns the user ID of this html preview entry.
	*
	* @return the user ID of this html preview entry
	*/
	@Override
	public long getUserId() {
		return _htmlPreviewEntry.getUserId();
	}

	/**
	* Returns the user name of this html preview entry.
	*
	* @return the user name of this html preview entry
	*/
	@Override
	public java.lang.String getUserName() {
		return _htmlPreviewEntry.getUserName();
	}

	/**
	* Returns the user uuid of this html preview entry.
	*
	* @return the user uuid of this html preview entry
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _htmlPreviewEntry.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _htmlPreviewEntry.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _htmlPreviewEntry.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _htmlPreviewEntry.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _htmlPreviewEntry.isNew();
	}

	@Override
	public void persist() {
		_htmlPreviewEntry.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_htmlPreviewEntry.setCachedModel(cachedModel);
	}

	@Override
	public void setClassName(java.lang.String className) {
		_htmlPreviewEntry.setClassName(className);
	}

	/**
	* Sets the class name ID of this html preview entry.
	*
	* @param classNameId the class name ID of this html preview entry
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_htmlPreviewEntry.setClassNameId(classNameId);
	}

	/**
	* Sets the class pk of this html preview entry.
	*
	* @param classPK the class pk of this html preview entry
	*/
	@Override
	public void setClassPK(long classPK) {
		_htmlPreviewEntry.setClassPK(classPK);
	}

	/**
	* Sets the company ID of this html preview entry.
	*
	* @param companyId the company ID of this html preview entry
	*/
	@Override
	public void setCompanyId(long companyId) {
		_htmlPreviewEntry.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this html preview entry.
	*
	* @param createDate the create date of this html preview entry
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_htmlPreviewEntry.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_htmlPreviewEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_htmlPreviewEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_htmlPreviewEntry.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the file entry ID of this html preview entry.
	*
	* @param fileEntryId the file entry ID of this html preview entry
	*/
	@Override
	public void setFileEntryId(long fileEntryId) {
		_htmlPreviewEntry.setFileEntryId(fileEntryId);
	}

	/**
	* Sets the group ID of this html preview entry.
	*
	* @param groupId the group ID of this html preview entry
	*/
	@Override
	public void setGroupId(long groupId) {
		_htmlPreviewEntry.setGroupId(groupId);
	}

	/**
	* Sets the html preview entry ID of this html preview entry.
	*
	* @param htmlPreviewEntryId the html preview entry ID of this html preview entry
	*/
	@Override
	public void setHtmlPreviewEntryId(long htmlPreviewEntryId) {
		_htmlPreviewEntry.setHtmlPreviewEntryId(htmlPreviewEntryId);
	}

	/**
	* Sets the modified date of this html preview entry.
	*
	* @param modifiedDate the modified date of this html preview entry
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_htmlPreviewEntry.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_htmlPreviewEntry.setNew(n);
	}

	/**
	* Sets the primary key of this html preview entry.
	*
	* @param primaryKey the primary key of this html preview entry
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_htmlPreviewEntry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_htmlPreviewEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the user ID of this html preview entry.
	*
	* @param userId the user ID of this html preview entry
	*/
	@Override
	public void setUserId(long userId) {
		_htmlPreviewEntry.setUserId(userId);
	}

	/**
	* Sets the user name of this html preview entry.
	*
	* @param userName the user name of this html preview entry
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_htmlPreviewEntry.setUserName(userName);
	}

	/**
	* Sets the user uuid of this html preview entry.
	*
	* @param userUuid the user uuid of this html preview entry
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_htmlPreviewEntry.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<HtmlPreviewEntry> toCacheModel() {
		return _htmlPreviewEntry.toCacheModel();
	}

	@Override
	public HtmlPreviewEntry toEscapedModel() {
		return new HtmlPreviewEntryWrapper(_htmlPreviewEntry.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _htmlPreviewEntry.toString();
	}

	@Override
	public HtmlPreviewEntry toUnescapedModel() {
		return new HtmlPreviewEntryWrapper(_htmlPreviewEntry.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _htmlPreviewEntry.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof HtmlPreviewEntryWrapper)) {
			return false;
		}

		HtmlPreviewEntryWrapper htmlPreviewEntryWrapper = (HtmlPreviewEntryWrapper)obj;

		if (Objects.equals(_htmlPreviewEntry,
					htmlPreviewEntryWrapper._htmlPreviewEntry)) {
			return true;
		}

		return false;
	}

	@Override
	public HtmlPreviewEntry getWrappedModel() {
		return _htmlPreviewEntry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _htmlPreviewEntry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _htmlPreviewEntry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_htmlPreviewEntry.resetOriginalValues();
	}

	private final HtmlPreviewEntry _htmlPreviewEntry;
}