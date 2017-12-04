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
 * This class is a wrapper for {@link HtmlPreview}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see HtmlPreview
 * @generated
 */
@ProviderType
public class HtmlPreviewWrapper implements HtmlPreview,
	ModelWrapper<HtmlPreview> {
	public HtmlPreviewWrapper(HtmlPreview htmlPreview) {
		_htmlPreview = htmlPreview;
	}

	@Override
	public Class<?> getModelClass() {
		return HtmlPreview.class;
	}

	@Override
	public String getModelClassName() {
		return HtmlPreview.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("htmlPreviewId", getHtmlPreviewId());
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
		Long htmlPreviewId = (Long)attributes.get("htmlPreviewId");

		if (htmlPreviewId != null) {
			setHtmlPreviewId(htmlPreviewId);
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
		return new HtmlPreviewWrapper((HtmlPreview)_htmlPreview.clone());
	}

	@Override
	public int compareTo(HtmlPreview htmlPreview) {
		return _htmlPreview.compareTo(htmlPreview);
	}

	/**
	* Returns the fully qualified class name of this html preview.
	*
	* @return the fully qualified class name of this html preview
	*/
	@Override
	public java.lang.String getClassName() {
		return _htmlPreview.getClassName();
	}

	/**
	* Returns the class name ID of this html preview.
	*
	* @return the class name ID of this html preview
	*/
	@Override
	public long getClassNameId() {
		return _htmlPreview.getClassNameId();
	}

	/**
	* Returns the class pk of this html preview.
	*
	* @return the class pk of this html preview
	*/
	@Override
	public long getClassPK() {
		return _htmlPreview.getClassPK();
	}

	/**
	* Returns the company ID of this html preview.
	*
	* @return the company ID of this html preview
	*/
	@Override
	public long getCompanyId() {
		return _htmlPreview.getCompanyId();
	}

	/**
	* Returns the create date of this html preview.
	*
	* @return the create date of this html preview
	*/
	@Override
	public Date getCreateDate() {
		return _htmlPreview.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _htmlPreview.getExpandoBridge();
	}

	/**
	* Returns the file entry ID of this html preview.
	*
	* @return the file entry ID of this html preview
	*/
	@Override
	public long getFileEntryId() {
		return _htmlPreview.getFileEntryId();
	}

	/**
	* Returns the group ID of this html preview.
	*
	* @return the group ID of this html preview
	*/
	@Override
	public long getGroupId() {
		return _htmlPreview.getGroupId();
	}

	/**
	* Returns the html preview ID of this html preview.
	*
	* @return the html preview ID of this html preview
	*/
	@Override
	public long getHtmlPreviewId() {
		return _htmlPreview.getHtmlPreviewId();
	}

	/**
	* Returns the modified date of this html preview.
	*
	* @return the modified date of this html preview
	*/
	@Override
	public Date getModifiedDate() {
		return _htmlPreview.getModifiedDate();
	}

	/**
	* Returns the primary key of this html preview.
	*
	* @return the primary key of this html preview
	*/
	@Override
	public long getPrimaryKey() {
		return _htmlPreview.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _htmlPreview.getPrimaryKeyObj();
	}

	/**
	* Returns the user ID of this html preview.
	*
	* @return the user ID of this html preview
	*/
	@Override
	public long getUserId() {
		return _htmlPreview.getUserId();
	}

	/**
	* Returns the user name of this html preview.
	*
	* @return the user name of this html preview
	*/
	@Override
	public java.lang.String getUserName() {
		return _htmlPreview.getUserName();
	}

	/**
	* Returns the user uuid of this html preview.
	*
	* @return the user uuid of this html preview
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _htmlPreview.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _htmlPreview.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _htmlPreview.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _htmlPreview.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _htmlPreview.isNew();
	}

	@Override
	public void persist() {
		_htmlPreview.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_htmlPreview.setCachedModel(cachedModel);
	}

	@Override
	public void setClassName(java.lang.String className) {
		_htmlPreview.setClassName(className);
	}

	/**
	* Sets the class name ID of this html preview.
	*
	* @param classNameId the class name ID of this html preview
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_htmlPreview.setClassNameId(classNameId);
	}

	/**
	* Sets the class pk of this html preview.
	*
	* @param classPK the class pk of this html preview
	*/
	@Override
	public void setClassPK(long classPK) {
		_htmlPreview.setClassPK(classPK);
	}

	/**
	* Sets the company ID of this html preview.
	*
	* @param companyId the company ID of this html preview
	*/
	@Override
	public void setCompanyId(long companyId) {
		_htmlPreview.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this html preview.
	*
	* @param createDate the create date of this html preview
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_htmlPreview.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_htmlPreview.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_htmlPreview.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_htmlPreview.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the file entry ID of this html preview.
	*
	* @param fileEntryId the file entry ID of this html preview
	*/
	@Override
	public void setFileEntryId(long fileEntryId) {
		_htmlPreview.setFileEntryId(fileEntryId);
	}

	/**
	* Sets the group ID of this html preview.
	*
	* @param groupId the group ID of this html preview
	*/
	@Override
	public void setGroupId(long groupId) {
		_htmlPreview.setGroupId(groupId);
	}

	/**
	* Sets the html preview ID of this html preview.
	*
	* @param htmlPreviewId the html preview ID of this html preview
	*/
	@Override
	public void setHtmlPreviewId(long htmlPreviewId) {
		_htmlPreview.setHtmlPreviewId(htmlPreviewId);
	}

	/**
	* Sets the modified date of this html preview.
	*
	* @param modifiedDate the modified date of this html preview
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_htmlPreview.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_htmlPreview.setNew(n);
	}

	/**
	* Sets the primary key of this html preview.
	*
	* @param primaryKey the primary key of this html preview
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_htmlPreview.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_htmlPreview.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the user ID of this html preview.
	*
	* @param userId the user ID of this html preview
	*/
	@Override
	public void setUserId(long userId) {
		_htmlPreview.setUserId(userId);
	}

	/**
	* Sets the user name of this html preview.
	*
	* @param userName the user name of this html preview
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_htmlPreview.setUserName(userName);
	}

	/**
	* Sets the user uuid of this html preview.
	*
	* @param userUuid the user uuid of this html preview
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_htmlPreview.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<HtmlPreview> toCacheModel() {
		return _htmlPreview.toCacheModel();
	}

	@Override
	public HtmlPreview toEscapedModel() {
		return new HtmlPreviewWrapper(_htmlPreview.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _htmlPreview.toString();
	}

	@Override
	public HtmlPreview toUnescapedModel() {
		return new HtmlPreviewWrapper(_htmlPreview.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _htmlPreview.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof HtmlPreviewWrapper)) {
			return false;
		}

		HtmlPreviewWrapper htmlPreviewWrapper = (HtmlPreviewWrapper)obj;

		if (Objects.equals(_htmlPreview, htmlPreviewWrapper._htmlPreview)) {
			return true;
		}

		return false;
	}

	@Override
	public HtmlPreview getWrappedModel() {
		return _htmlPreview;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _htmlPreview.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _htmlPreview.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_htmlPreview.resetOriginalValues();
	}

	private final HtmlPreview _htmlPreview;
}