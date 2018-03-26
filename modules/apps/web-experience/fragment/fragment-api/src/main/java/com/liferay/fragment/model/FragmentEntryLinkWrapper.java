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
 * This class is a wrapper for {@link FragmentEntryLink}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FragmentEntryLink
 * @generated
 */
@ProviderType
public class FragmentEntryLinkWrapper implements FragmentEntryLink,
	ModelWrapper<FragmentEntryLink> {
	public FragmentEntryLinkWrapper(FragmentEntryLink fragmentEntryLink) {
		_fragmentEntryLink = fragmentEntryLink;
	}

	@Override
	public Class<?> getModelClass() {
		return FragmentEntryLink.class;
	}

	@Override
	public String getModelClassName() {
		return FragmentEntryLink.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("fragmentEntryLinkId", getFragmentEntryLinkId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("originalFragmentEntryLinkId",
			getOriginalFragmentEntryLinkId());
		attributes.put("fragmentEntryId", getFragmentEntryId());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("css", getCss());
		attributes.put("html", getHtml());
		attributes.put("js", getJs());
		attributes.put("editableValues", getEditableValues());
		attributes.put("position", getPosition());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long fragmentEntryLinkId = (Long)attributes.get("fragmentEntryLinkId");

		if (fragmentEntryLinkId != null) {
			setFragmentEntryLinkId(fragmentEntryLinkId);
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

		Long originalFragmentEntryLinkId = (Long)attributes.get(
				"originalFragmentEntryLinkId");

		if (originalFragmentEntryLinkId != null) {
			setOriginalFragmentEntryLinkId(originalFragmentEntryLinkId);
		}

		Long fragmentEntryId = (Long)attributes.get("fragmentEntryId");

		if (fragmentEntryId != null) {
			setFragmentEntryId(fragmentEntryId);
		}

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
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

		String editableValues = (String)attributes.get("editableValues");

		if (editableValues != null) {
			setEditableValues(editableValues);
		}

		Integer position = (Integer)attributes.get("position");

		if (position != null) {
			setPosition(position);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new FragmentEntryLinkWrapper((FragmentEntryLink)_fragmentEntryLink.clone());
	}

	@Override
	public int compareTo(FragmentEntryLink fragmentEntryLink) {
		return _fragmentEntryLink.compareTo(fragmentEntryLink);
	}

	/**
	* Returns the fully qualified class name of this fragment entry link.
	*
	* @return the fully qualified class name of this fragment entry link
	*/
	@Override
	public java.lang.String getClassName() {
		return _fragmentEntryLink.getClassName();
	}

	/**
	* Returns the class name ID of this fragment entry link.
	*
	* @return the class name ID of this fragment entry link
	*/
	@Override
	public long getClassNameId() {
		return _fragmentEntryLink.getClassNameId();
	}

	/**
	* Returns the class pk of this fragment entry link.
	*
	* @return the class pk of this fragment entry link
	*/
	@Override
	public long getClassPK() {
		return _fragmentEntryLink.getClassPK();
	}

	/**
	* Returns the company ID of this fragment entry link.
	*
	* @return the company ID of this fragment entry link
	*/
	@Override
	public long getCompanyId() {
		return _fragmentEntryLink.getCompanyId();
	}

	/**
	* Returns the create date of this fragment entry link.
	*
	* @return the create date of this fragment entry link
	*/
	@Override
	public Date getCreateDate() {
		return _fragmentEntryLink.getCreateDate();
	}

	/**
	* Returns the css of this fragment entry link.
	*
	* @return the css of this fragment entry link
	*/
	@Override
	public java.lang.String getCss() {
		return _fragmentEntryLink.getCss();
	}

	/**
	* Returns the editable values of this fragment entry link.
	*
	* @return the editable values of this fragment entry link
	*/
	@Override
	public java.lang.String getEditableValues() {
		return _fragmentEntryLink.getEditableValues();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _fragmentEntryLink.getExpandoBridge();
	}

	/**
	* Returns the fragment entry ID of this fragment entry link.
	*
	* @return the fragment entry ID of this fragment entry link
	*/
	@Override
	public long getFragmentEntryId() {
		return _fragmentEntryLink.getFragmentEntryId();
	}

	/**
	* Returns the fragment entry link ID of this fragment entry link.
	*
	* @return the fragment entry link ID of this fragment entry link
	*/
	@Override
	public long getFragmentEntryLinkId() {
		return _fragmentEntryLink.getFragmentEntryLinkId();
	}

	/**
	* Returns the group ID of this fragment entry link.
	*
	* @return the group ID of this fragment entry link
	*/
	@Override
	public long getGroupId() {
		return _fragmentEntryLink.getGroupId();
	}

	/**
	* Returns the html of this fragment entry link.
	*
	* @return the html of this fragment entry link
	*/
	@Override
	public java.lang.String getHtml() {
		return _fragmentEntryLink.getHtml();
	}

	/**
	* Returns the js of this fragment entry link.
	*
	* @return the js of this fragment entry link
	*/
	@Override
	public java.lang.String getJs() {
		return _fragmentEntryLink.getJs();
	}

	/**
	* Returns the modified date of this fragment entry link.
	*
	* @return the modified date of this fragment entry link
	*/
	@Override
	public Date getModifiedDate() {
		return _fragmentEntryLink.getModifiedDate();
	}

	/**
	* Returns the original fragment entry link ID of this fragment entry link.
	*
	* @return the original fragment entry link ID of this fragment entry link
	*/
	@Override
	public long getOriginalFragmentEntryLinkId() {
		return _fragmentEntryLink.getOriginalFragmentEntryLinkId();
	}

	/**
	* Returns the position of this fragment entry link.
	*
	* @return the position of this fragment entry link
	*/
	@Override
	public int getPosition() {
		return _fragmentEntryLink.getPosition();
	}

	/**
	* Returns the primary key of this fragment entry link.
	*
	* @return the primary key of this fragment entry link
	*/
	@Override
	public long getPrimaryKey() {
		return _fragmentEntryLink.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _fragmentEntryLink.getPrimaryKeyObj();
	}

	/**
	* Returns the user ID of this fragment entry link.
	*
	* @return the user ID of this fragment entry link
	*/
	@Override
	public long getUserId() {
		return _fragmentEntryLink.getUserId();
	}

	/**
	* Returns the user name of this fragment entry link.
	*
	* @return the user name of this fragment entry link
	*/
	@Override
	public java.lang.String getUserName() {
		return _fragmentEntryLink.getUserName();
	}

	/**
	* Returns the user uuid of this fragment entry link.
	*
	* @return the user uuid of this fragment entry link
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _fragmentEntryLink.getUserUuid();
	}

	/**
	* Returns the uuid of this fragment entry link.
	*
	* @return the uuid of this fragment entry link
	*/
	@Override
	public java.lang.String getUuid() {
		return _fragmentEntryLink.getUuid();
	}

	@Override
	public int hashCode() {
		return _fragmentEntryLink.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _fragmentEntryLink.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _fragmentEntryLink.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _fragmentEntryLink.isNew();
	}

	@Override
	public void persist() {
		_fragmentEntryLink.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_fragmentEntryLink.setCachedModel(cachedModel);
	}

	@Override
	public void setClassName(java.lang.String className) {
		_fragmentEntryLink.setClassName(className);
	}

	/**
	* Sets the class name ID of this fragment entry link.
	*
	* @param classNameId the class name ID of this fragment entry link
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_fragmentEntryLink.setClassNameId(classNameId);
	}

	/**
	* Sets the class pk of this fragment entry link.
	*
	* @param classPK the class pk of this fragment entry link
	*/
	@Override
	public void setClassPK(long classPK) {
		_fragmentEntryLink.setClassPK(classPK);
	}

	/**
	* Sets the company ID of this fragment entry link.
	*
	* @param companyId the company ID of this fragment entry link
	*/
	@Override
	public void setCompanyId(long companyId) {
		_fragmentEntryLink.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this fragment entry link.
	*
	* @param createDate the create date of this fragment entry link
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_fragmentEntryLink.setCreateDate(createDate);
	}

	/**
	* Sets the css of this fragment entry link.
	*
	* @param css the css of this fragment entry link
	*/
	@Override
	public void setCss(java.lang.String css) {
		_fragmentEntryLink.setCss(css);
	}

	/**
	* Sets the editable values of this fragment entry link.
	*
	* @param editableValues the editable values of this fragment entry link
	*/
	@Override
	public void setEditableValues(java.lang.String editableValues) {
		_fragmentEntryLink.setEditableValues(editableValues);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_fragmentEntryLink.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_fragmentEntryLink.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_fragmentEntryLink.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the fragment entry ID of this fragment entry link.
	*
	* @param fragmentEntryId the fragment entry ID of this fragment entry link
	*/
	@Override
	public void setFragmentEntryId(long fragmentEntryId) {
		_fragmentEntryLink.setFragmentEntryId(fragmentEntryId);
	}

	/**
	* Sets the fragment entry link ID of this fragment entry link.
	*
	* @param fragmentEntryLinkId the fragment entry link ID of this fragment entry link
	*/
	@Override
	public void setFragmentEntryLinkId(long fragmentEntryLinkId) {
		_fragmentEntryLink.setFragmentEntryLinkId(fragmentEntryLinkId);
	}

	/**
	* Sets the group ID of this fragment entry link.
	*
	* @param groupId the group ID of this fragment entry link
	*/
	@Override
	public void setGroupId(long groupId) {
		_fragmentEntryLink.setGroupId(groupId);
	}

	/**
	* Sets the html of this fragment entry link.
	*
	* @param html the html of this fragment entry link
	*/
	@Override
	public void setHtml(java.lang.String html) {
		_fragmentEntryLink.setHtml(html);
	}

	/**
	* Sets the js of this fragment entry link.
	*
	* @param js the js of this fragment entry link
	*/
	@Override
	public void setJs(java.lang.String js) {
		_fragmentEntryLink.setJs(js);
	}

	/**
	* Sets the modified date of this fragment entry link.
	*
	* @param modifiedDate the modified date of this fragment entry link
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_fragmentEntryLink.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_fragmentEntryLink.setNew(n);
	}

	/**
	* Sets the original fragment entry link ID of this fragment entry link.
	*
	* @param originalFragmentEntryLinkId the original fragment entry link ID of this fragment entry link
	*/
	@Override
	public void setOriginalFragmentEntryLinkId(long originalFragmentEntryLinkId) {
		_fragmentEntryLink.setOriginalFragmentEntryLinkId(originalFragmentEntryLinkId);
	}

	/**
	* Sets the position of this fragment entry link.
	*
	* @param position the position of this fragment entry link
	*/
	@Override
	public void setPosition(int position) {
		_fragmentEntryLink.setPosition(position);
	}

	/**
	* Sets the primary key of this fragment entry link.
	*
	* @param primaryKey the primary key of this fragment entry link
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_fragmentEntryLink.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_fragmentEntryLink.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the user ID of this fragment entry link.
	*
	* @param userId the user ID of this fragment entry link
	*/
	@Override
	public void setUserId(long userId) {
		_fragmentEntryLink.setUserId(userId);
	}

	/**
	* Sets the user name of this fragment entry link.
	*
	* @param userName the user name of this fragment entry link
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_fragmentEntryLink.setUserName(userName);
	}

	/**
	* Sets the user uuid of this fragment entry link.
	*
	* @param userUuid the user uuid of this fragment entry link
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_fragmentEntryLink.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this fragment entry link.
	*
	* @param uuid the uuid of this fragment entry link
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_fragmentEntryLink.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<FragmentEntryLink> toCacheModel() {
		return _fragmentEntryLink.toCacheModel();
	}

	@Override
	public FragmentEntryLink toEscapedModel() {
		return new FragmentEntryLinkWrapper(_fragmentEntryLink.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _fragmentEntryLink.toString();
	}

	@Override
	public FragmentEntryLink toUnescapedModel() {
		return new FragmentEntryLinkWrapper(_fragmentEntryLink.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _fragmentEntryLink.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof FragmentEntryLinkWrapper)) {
			return false;
		}

		FragmentEntryLinkWrapper fragmentEntryLinkWrapper = (FragmentEntryLinkWrapper)obj;

		if (Objects.equals(_fragmentEntryLink,
					fragmentEntryLinkWrapper._fragmentEntryLink)) {
			return true;
		}

		return false;
	}

	@Override
	public StagedModelType getStagedModelType() {
		return _fragmentEntryLink.getStagedModelType();
	}

	@Override
	public FragmentEntryLink getWrappedModel() {
		return _fragmentEntryLink;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _fragmentEntryLink.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _fragmentEntryLink.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_fragmentEntryLink.resetOriginalValues();
	}

	private final FragmentEntryLink _fragmentEntryLink;
}