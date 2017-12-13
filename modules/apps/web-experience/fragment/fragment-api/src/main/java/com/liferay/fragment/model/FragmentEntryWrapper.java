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

package com.liferay.fragment.model;

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
 * This class is a wrapper for {@link FragmentEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FragmentEntry
 * @generated
 */
@ProviderType
public class FragmentEntryWrapper implements FragmentEntry,
	ModelWrapper<FragmentEntry> {
	public FragmentEntryWrapper(FragmentEntry fragmentEntry) {
		_fragmentEntry = fragmentEntry;
	}

	@Override
	public Class<?> getModelClass() {
		return FragmentEntry.class;
	}

	@Override
	public String getModelClassName() {
		return FragmentEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("fragmentEntryId", getFragmentEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("fragmentCollectionId", getFragmentCollectionId());
		attributes.put("name", getName());
		attributes.put("css", getCss());
		attributes.put("html", getHtml());
		attributes.put("js", getJs());
		attributes.put("htmlPreviewEntryId", getHtmlPreviewEntryId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long fragmentEntryId = (Long)attributes.get("fragmentEntryId");

		if (fragmentEntryId != null) {
			setFragmentEntryId(fragmentEntryId);
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

		Long fragmentCollectionId = (Long)attributes.get("fragmentCollectionId");

		if (fragmentCollectionId != null) {
			setFragmentCollectionId(fragmentCollectionId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String css = (String)attributes.get("css");

		if (css != null) {
			setCss(css);
		}

		String html = (String)attributes.get("html");

		if (html != null) {
			setHtml(html);
		}

		String js = (String)attributes.get("js");

		if (js != null) {
			setJs(js);
		}

		Long htmlPreviewEntryId = (Long)attributes.get("htmlPreviewEntryId");

		if (htmlPreviewEntryId != null) {
			setHtmlPreviewEntryId(htmlPreviewEntryId);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new FragmentEntryWrapper((FragmentEntry)_fragmentEntry.clone());
	}

	@Override
	public int compareTo(FragmentEntry fragmentEntry) {
		return _fragmentEntry.compareTo(fragmentEntry);
	}

	/**
	* Returns the company ID of this fragment entry.
	*
	* @return the company ID of this fragment entry
	*/
	@Override
	public long getCompanyId() {
		return _fragmentEntry.getCompanyId();
	}

	@Override
	public java.lang.String getContent() {
		return _fragmentEntry.getContent();
	}

	/**
	* Returns the create date of this fragment entry.
	*
	* @return the create date of this fragment entry
	*/
	@Override
	public Date getCreateDate() {
		return _fragmentEntry.getCreateDate();
	}

	/**
	* Returns the css of this fragment entry.
	*
	* @return the css of this fragment entry
	*/
	@Override
	public java.lang.String getCss() {
		return _fragmentEntry.getCss();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _fragmentEntry.getExpandoBridge();
	}

	/**
	* Returns the fragment collection ID of this fragment entry.
	*
	* @return the fragment collection ID of this fragment entry
	*/
	@Override
	public long getFragmentCollectionId() {
		return _fragmentEntry.getFragmentCollectionId();
	}

	/**
	* Returns the fragment entry ID of this fragment entry.
	*
	* @return the fragment entry ID of this fragment entry
	*/
	@Override
	public long getFragmentEntryId() {
		return _fragmentEntry.getFragmentEntryId();
	}

	/**
	* Returns the group ID of this fragment entry.
	*
	* @return the group ID of this fragment entry
	*/
	@Override
	public long getGroupId() {
		return _fragmentEntry.getGroupId();
	}

	/**
	* Returns the html of this fragment entry.
	*
	* @return the html of this fragment entry
	*/
	@Override
	public java.lang.String getHtml() {
		return _fragmentEntry.getHtml();
	}

	/**
	* Returns the html preview entry ID of this fragment entry.
	*
	* @return the html preview entry ID of this fragment entry
	*/
	@Override
	public long getHtmlPreviewEntryId() {
		return _fragmentEntry.getHtmlPreviewEntryId();
	}

	@Override
	public java.lang.String getImagePreviewURL(
		com.liferay.portal.kernel.theme.ThemeDisplay themeDisplay) {
		return _fragmentEntry.getImagePreviewURL(themeDisplay);
	}

	/**
	* Returns the js of this fragment entry.
	*
	* @return the js of this fragment entry
	*/
	@Override
	public java.lang.String getJs() {
		return _fragmentEntry.getJs();
	}

	/**
	* Returns the modified date of this fragment entry.
	*
	* @return the modified date of this fragment entry
	*/
	@Override
	public Date getModifiedDate() {
		return _fragmentEntry.getModifiedDate();
	}

	/**
	* Returns the name of this fragment entry.
	*
	* @return the name of this fragment entry
	*/
	@Override
	public java.lang.String getName() {
		return _fragmentEntry.getName();
	}

	/**
	* Returns the primary key of this fragment entry.
	*
	* @return the primary key of this fragment entry
	*/
	@Override
	public long getPrimaryKey() {
		return _fragmentEntry.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _fragmentEntry.getPrimaryKeyObj();
	}

	/**
	* Returns the user ID of this fragment entry.
	*
	* @return the user ID of this fragment entry
	*/
	@Override
	public long getUserId() {
		return _fragmentEntry.getUserId();
	}

	/**
	* Returns the user name of this fragment entry.
	*
	* @return the user name of this fragment entry
	*/
	@Override
	public java.lang.String getUserName() {
		return _fragmentEntry.getUserName();
	}

	/**
	* Returns the user uuid of this fragment entry.
	*
	* @return the user uuid of this fragment entry
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _fragmentEntry.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _fragmentEntry.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _fragmentEntry.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _fragmentEntry.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _fragmentEntry.isNew();
	}

	@Override
	public void persist() {
		_fragmentEntry.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_fragmentEntry.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this fragment entry.
	*
	* @param companyId the company ID of this fragment entry
	*/
	@Override
	public void setCompanyId(long companyId) {
		_fragmentEntry.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this fragment entry.
	*
	* @param createDate the create date of this fragment entry
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_fragmentEntry.setCreateDate(createDate);
	}

	/**
	* Sets the css of this fragment entry.
	*
	* @param css the css of this fragment entry
	*/
	@Override
	public void setCss(java.lang.String css) {
		_fragmentEntry.setCss(css);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_fragmentEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_fragmentEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_fragmentEntry.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the fragment collection ID of this fragment entry.
	*
	* @param fragmentCollectionId the fragment collection ID of this fragment entry
	*/
	@Override
	public void setFragmentCollectionId(long fragmentCollectionId) {
		_fragmentEntry.setFragmentCollectionId(fragmentCollectionId);
	}

	/**
	* Sets the fragment entry ID of this fragment entry.
	*
	* @param fragmentEntryId the fragment entry ID of this fragment entry
	*/
	@Override
	public void setFragmentEntryId(long fragmentEntryId) {
		_fragmentEntry.setFragmentEntryId(fragmentEntryId);
	}

	/**
	* Sets the group ID of this fragment entry.
	*
	* @param groupId the group ID of this fragment entry
	*/
	@Override
	public void setGroupId(long groupId) {
		_fragmentEntry.setGroupId(groupId);
	}

	/**
	* Sets the html of this fragment entry.
	*
	* @param html the html of this fragment entry
	*/
	@Override
	public void setHtml(java.lang.String html) {
		_fragmentEntry.setHtml(html);
	}

	/**
	* Sets the html preview entry ID of this fragment entry.
	*
	* @param htmlPreviewEntryId the html preview entry ID of this fragment entry
	*/
	@Override
	public void setHtmlPreviewEntryId(long htmlPreviewEntryId) {
		_fragmentEntry.setHtmlPreviewEntryId(htmlPreviewEntryId);
	}

	/**
	* Sets the js of this fragment entry.
	*
	* @param js the js of this fragment entry
	*/
	@Override
	public void setJs(java.lang.String js) {
		_fragmentEntry.setJs(js);
	}

	/**
	* Sets the modified date of this fragment entry.
	*
	* @param modifiedDate the modified date of this fragment entry
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_fragmentEntry.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this fragment entry.
	*
	* @param name the name of this fragment entry
	*/
	@Override
	public void setName(java.lang.String name) {
		_fragmentEntry.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_fragmentEntry.setNew(n);
	}

	/**
	* Sets the primary key of this fragment entry.
	*
	* @param primaryKey the primary key of this fragment entry
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_fragmentEntry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_fragmentEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the user ID of this fragment entry.
	*
	* @param userId the user ID of this fragment entry
	*/
	@Override
	public void setUserId(long userId) {
		_fragmentEntry.setUserId(userId);
	}

	/**
	* Sets the user name of this fragment entry.
	*
	* @param userName the user name of this fragment entry
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_fragmentEntry.setUserName(userName);
	}

	/**
	* Sets the user uuid of this fragment entry.
	*
	* @param userUuid the user uuid of this fragment entry
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_fragmentEntry.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<FragmentEntry> toCacheModel() {
		return _fragmentEntry.toCacheModel();
	}

	@Override
	public FragmentEntry toEscapedModel() {
		return new FragmentEntryWrapper(_fragmentEntry.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _fragmentEntry.toString();
	}

	@Override
	public FragmentEntry toUnescapedModel() {
		return new FragmentEntryWrapper(_fragmentEntry.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _fragmentEntry.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof FragmentEntryWrapper)) {
			return false;
		}

		FragmentEntryWrapper fragmentEntryWrapper = (FragmentEntryWrapper)obj;

		if (Objects.equals(_fragmentEntry, fragmentEntryWrapper._fragmentEntry)) {
			return true;
		}

		return false;
	}

	@Override
	public FragmentEntry getWrappedModel() {
		return _fragmentEntry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _fragmentEntry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _fragmentEntry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_fragmentEntry.resetOriginalValues();
	}

	private final FragmentEntry _fragmentEntry;
}