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

package com.liferay.modern.site.building.fragment.model;

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
 * This class is a wrapper for {@link MSBFragmentEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MSBFragmentEntry
 * @generated
 */
@ProviderType
public class MSBFragmentEntryWrapper implements MSBFragmentEntry,
	ModelWrapper<MSBFragmentEntry> {
	public MSBFragmentEntryWrapper(MSBFragmentEntry msbFragmentEntry) {
		_msbFragmentEntry = msbFragmentEntry;
	}

	@Override
	public Class<?> getModelClass() {
		return MSBFragmentEntry.class;
	}

	@Override
	public String getModelClassName() {
		return MSBFragmentEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("msbFragmentEntryId", getMsbFragmentEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("msbFragmentCollectionId", getMsbFragmentCollectionId());
		attributes.put("name", getName());
		attributes.put("css", getCss());
		attributes.put("html", getHtml());
		attributes.put("js", getJs());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long msbFragmentEntryId = (Long)attributes.get("msbFragmentEntryId");

		if (msbFragmentEntryId != null) {
			setMsbFragmentEntryId(msbFragmentEntryId);
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

		Long msbFragmentCollectionId = (Long)attributes.get(
				"msbFragmentCollectionId");

		if (msbFragmentCollectionId != null) {
			setMsbFragmentCollectionId(msbFragmentCollectionId);
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
	}

	@Override
	public java.lang.Object clone() {
		return new MSBFragmentEntryWrapper((MSBFragmentEntry)_msbFragmentEntry.clone());
	}

	@Override
	public int compareTo(MSBFragmentEntry msbFragmentEntry) {
		return _msbFragmentEntry.compareTo(msbFragmentEntry);
	}

	/**
	* Returns the company ID of this msb fragment entry.
	*
	* @return the company ID of this msb fragment entry
	*/
	@Override
	public long getCompanyId() {
		return _msbFragmentEntry.getCompanyId();
	}

	/**
	* Returns the create date of this msb fragment entry.
	*
	* @return the create date of this msb fragment entry
	*/
	@Override
	public Date getCreateDate() {
		return _msbFragmentEntry.getCreateDate();
	}

	/**
	* Returns the css of this msb fragment entry.
	*
	* @return the css of this msb fragment entry
	*/
	@Override
	public java.lang.String getCss() {
		return _msbFragmentEntry.getCss();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _msbFragmentEntry.getExpandoBridge();
	}

	/**
	* Returns the group ID of this msb fragment entry.
	*
	* @return the group ID of this msb fragment entry
	*/
	@Override
	public long getGroupId() {
		return _msbFragmentEntry.getGroupId();
	}

	/**
	* Returns the html of this msb fragment entry.
	*
	* @return the html of this msb fragment entry
	*/
	@Override
	public java.lang.String getHtml() {
		return _msbFragmentEntry.getHtml();
	}

	/**
	* Returns the js of this msb fragment entry.
	*
	* @return the js of this msb fragment entry
	*/
	@Override
	public java.lang.String getJs() {
		return _msbFragmentEntry.getJs();
	}

	/**
	* Returns the modified date of this msb fragment entry.
	*
	* @return the modified date of this msb fragment entry
	*/
	@Override
	public Date getModifiedDate() {
		return _msbFragmentEntry.getModifiedDate();
	}

	/**
	* Returns the msb fragment collection ID of this msb fragment entry.
	*
	* @return the msb fragment collection ID of this msb fragment entry
	*/
	@Override
	public long getMsbFragmentCollectionId() {
		return _msbFragmentEntry.getMsbFragmentCollectionId();
	}

	/**
	* Returns the msb fragment entry ID of this msb fragment entry.
	*
	* @return the msb fragment entry ID of this msb fragment entry
	*/
	@Override
	public long getMsbFragmentEntryId() {
		return _msbFragmentEntry.getMsbFragmentEntryId();
	}

	/**
	* Returns the name of this msb fragment entry.
	*
	* @return the name of this msb fragment entry
	*/
	@Override
	public java.lang.String getName() {
		return _msbFragmentEntry.getName();
	}

	/**
	* Returns the primary key of this msb fragment entry.
	*
	* @return the primary key of this msb fragment entry
	*/
	@Override
	public long getPrimaryKey() {
		return _msbFragmentEntry.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _msbFragmentEntry.getPrimaryKeyObj();
	}

	/**
	* Returns the user ID of this msb fragment entry.
	*
	* @return the user ID of this msb fragment entry
	*/
	@Override
	public long getUserId() {
		return _msbFragmentEntry.getUserId();
	}

	/**
	* Returns the user name of this msb fragment entry.
	*
	* @return the user name of this msb fragment entry
	*/
	@Override
	public java.lang.String getUserName() {
		return _msbFragmentEntry.getUserName();
	}

	/**
	* Returns the user uuid of this msb fragment entry.
	*
	* @return the user uuid of this msb fragment entry
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _msbFragmentEntry.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _msbFragmentEntry.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _msbFragmentEntry.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _msbFragmentEntry.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _msbFragmentEntry.isNew();
	}

	@Override
	public void persist() {
		_msbFragmentEntry.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_msbFragmentEntry.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this msb fragment entry.
	*
	* @param companyId the company ID of this msb fragment entry
	*/
	@Override
	public void setCompanyId(long companyId) {
		_msbFragmentEntry.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this msb fragment entry.
	*
	* @param createDate the create date of this msb fragment entry
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_msbFragmentEntry.setCreateDate(createDate);
	}

	/**
	* Sets the css of this msb fragment entry.
	*
	* @param css the css of this msb fragment entry
	*/
	@Override
	public void setCss(java.lang.String css) {
		_msbFragmentEntry.setCss(css);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_msbFragmentEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_msbFragmentEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_msbFragmentEntry.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this msb fragment entry.
	*
	* @param groupId the group ID of this msb fragment entry
	*/
	@Override
	public void setGroupId(long groupId) {
		_msbFragmentEntry.setGroupId(groupId);
	}

	/**
	* Sets the html of this msb fragment entry.
	*
	* @param html the html of this msb fragment entry
	*/
	@Override
	public void setHtml(java.lang.String html) {
		_msbFragmentEntry.setHtml(html);
	}

	/**
	* Sets the js of this msb fragment entry.
	*
	* @param js the js of this msb fragment entry
	*/
	@Override
	public void setJs(java.lang.String js) {
		_msbFragmentEntry.setJs(js);
	}

	/**
	* Sets the modified date of this msb fragment entry.
	*
	* @param modifiedDate the modified date of this msb fragment entry
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_msbFragmentEntry.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the msb fragment collection ID of this msb fragment entry.
	*
	* @param msbFragmentCollectionId the msb fragment collection ID of this msb fragment entry
	*/
	@Override
	public void setMsbFragmentCollectionId(long msbFragmentCollectionId) {
		_msbFragmentEntry.setMsbFragmentCollectionId(msbFragmentCollectionId);
	}

	/**
	* Sets the msb fragment entry ID of this msb fragment entry.
	*
	* @param msbFragmentEntryId the msb fragment entry ID of this msb fragment entry
	*/
	@Override
	public void setMsbFragmentEntryId(long msbFragmentEntryId) {
		_msbFragmentEntry.setMsbFragmentEntryId(msbFragmentEntryId);
	}

	/**
	* Sets the name of this msb fragment entry.
	*
	* @param name the name of this msb fragment entry
	*/
	@Override
	public void setName(java.lang.String name) {
		_msbFragmentEntry.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_msbFragmentEntry.setNew(n);
	}

	/**
	* Sets the primary key of this msb fragment entry.
	*
	* @param primaryKey the primary key of this msb fragment entry
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_msbFragmentEntry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_msbFragmentEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the user ID of this msb fragment entry.
	*
	* @param userId the user ID of this msb fragment entry
	*/
	@Override
	public void setUserId(long userId) {
		_msbFragmentEntry.setUserId(userId);
	}

	/**
	* Sets the user name of this msb fragment entry.
	*
	* @param userName the user name of this msb fragment entry
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_msbFragmentEntry.setUserName(userName);
	}

	/**
	* Sets the user uuid of this msb fragment entry.
	*
	* @param userUuid the user uuid of this msb fragment entry
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_msbFragmentEntry.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<MSBFragmentEntry> toCacheModel() {
		return _msbFragmentEntry.toCacheModel();
	}

	@Override
	public MSBFragmentEntry toEscapedModel() {
		return new MSBFragmentEntryWrapper(_msbFragmentEntry.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _msbFragmentEntry.toString();
	}

	@Override
	public MSBFragmentEntry toUnescapedModel() {
		return new MSBFragmentEntryWrapper(_msbFragmentEntry.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _msbFragmentEntry.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof MSBFragmentEntryWrapper)) {
			return false;
		}

		MSBFragmentEntryWrapper msbFragmentEntryWrapper = (MSBFragmentEntryWrapper)obj;

		if (Objects.equals(_msbFragmentEntry,
					msbFragmentEntryWrapper._msbFragmentEntry)) {
			return true;
		}

		return false;
	}

	@Override
	public MSBFragmentEntry getWrappedModel() {
		return _msbFragmentEntry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _msbFragmentEntry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _msbFragmentEntry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_msbFragmentEntry.resetOriginalValues();
	}

	private final MSBFragmentEntry _msbFragmentEntry;
}