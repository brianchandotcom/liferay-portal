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
 * This class is a wrapper for {@link LayoutPageTemplateFragment}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplateFragment
 * @generated
 */
@ProviderType
public class LayoutPageTemplateFragmentWrapper
	implements LayoutPageTemplateFragment,
		ModelWrapper<LayoutPageTemplateFragment> {
	public LayoutPageTemplateFragmentWrapper(
		LayoutPageTemplateFragment layoutPageTemplateFragment) {
		_layoutPageTemplateFragment = layoutPageTemplateFragment;
	}

	@Override
	public Class<?> getModelClass() {
		return LayoutPageTemplateFragment.class;
	}

	@Override
	public String getModelClassName() {
		return LayoutPageTemplateFragment.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("layoutPageTemplateFragmentId",
			getLayoutPageTemplateFragmentId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("layoutPageTemplateEntryId",
			getLayoutPageTemplateEntryId());
		attributes.put("fragmentEntryId", getFragmentEntryId());
		attributes.put("position", getPosition());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long layoutPageTemplateFragmentId = (Long)attributes.get(
				"layoutPageTemplateFragmentId");

		if (layoutPageTemplateFragmentId != null) {
			setLayoutPageTemplateFragmentId(layoutPageTemplateFragmentId);
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

		Long layoutPageTemplateEntryId = (Long)attributes.get(
				"layoutPageTemplateEntryId");

		if (layoutPageTemplateEntryId != null) {
			setLayoutPageTemplateEntryId(layoutPageTemplateEntryId);
		}

		Long fragmentEntryId = (Long)attributes.get("fragmentEntryId");

		if (fragmentEntryId != null) {
			setFragmentEntryId(fragmentEntryId);
		}

		Integer position = (Integer)attributes.get("position");

		if (position != null) {
			setPosition(position);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new LayoutPageTemplateFragmentWrapper((LayoutPageTemplateFragment)_layoutPageTemplateFragment.clone());
	}

	@Override
	public int compareTo(LayoutPageTemplateFragment layoutPageTemplateFragment) {
		return _layoutPageTemplateFragment.compareTo(layoutPageTemplateFragment);
	}

	/**
	* Returns the company ID of this layout page template fragment.
	*
	* @return the company ID of this layout page template fragment
	*/
	@Override
	public long getCompanyId() {
		return _layoutPageTemplateFragment.getCompanyId();
	}

	/**
	* Returns the create date of this layout page template fragment.
	*
	* @return the create date of this layout page template fragment
	*/
	@Override
	public Date getCreateDate() {
		return _layoutPageTemplateFragment.getCreateDate();
	}

	@Override
	public java.lang.String getCss()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPageTemplateFragment.getCss();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _layoutPageTemplateFragment.getExpandoBridge();
	}

	/**
	* Returns the fragment entry ID of this layout page template fragment.
	*
	* @return the fragment entry ID of this layout page template fragment
	*/
	@Override
	public long getFragmentEntryId() {
		return _layoutPageTemplateFragment.getFragmentEntryId();
	}

	/**
	* Returns the group ID of this layout page template fragment.
	*
	* @return the group ID of this layout page template fragment
	*/
	@Override
	public long getGroupId() {
		return _layoutPageTemplateFragment.getGroupId();
	}

	@Override
	public java.lang.String getHtml()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPageTemplateFragment.getHtml();
	}

	@Override
	public java.lang.String getJs()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutPageTemplateFragment.getJs();
	}

	/**
	* Returns the layout page template entry ID of this layout page template fragment.
	*
	* @return the layout page template entry ID of this layout page template fragment
	*/
	@Override
	public long getLayoutPageTemplateEntryId() {
		return _layoutPageTemplateFragment.getLayoutPageTemplateEntryId();
	}

	/**
	* Returns the layout page template fragment ID of this layout page template fragment.
	*
	* @return the layout page template fragment ID of this layout page template fragment
	*/
	@Override
	public long getLayoutPageTemplateFragmentId() {
		return _layoutPageTemplateFragment.getLayoutPageTemplateFragmentId();
	}

	/**
	* Returns the modified date of this layout page template fragment.
	*
	* @return the modified date of this layout page template fragment
	*/
	@Override
	public Date getModifiedDate() {
		return _layoutPageTemplateFragment.getModifiedDate();
	}

	/**
	* Returns the position of this layout page template fragment.
	*
	* @return the position of this layout page template fragment
	*/
	@Override
	public int getPosition() {
		return _layoutPageTemplateFragment.getPosition();
	}

	/**
	* Returns the primary key of this layout page template fragment.
	*
	* @return the primary key of this layout page template fragment
	*/
	@Override
	public long getPrimaryKey() {
		return _layoutPageTemplateFragment.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _layoutPageTemplateFragment.getPrimaryKeyObj();
	}

	/**
	* Returns the user ID of this layout page template fragment.
	*
	* @return the user ID of this layout page template fragment
	*/
	@Override
	public long getUserId() {
		return _layoutPageTemplateFragment.getUserId();
	}

	/**
	* Returns the user name of this layout page template fragment.
	*
	* @return the user name of this layout page template fragment
	*/
	@Override
	public java.lang.String getUserName() {
		return _layoutPageTemplateFragment.getUserName();
	}

	/**
	* Returns the user uuid of this layout page template fragment.
	*
	* @return the user uuid of this layout page template fragment
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _layoutPageTemplateFragment.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _layoutPageTemplateFragment.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _layoutPageTemplateFragment.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _layoutPageTemplateFragment.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _layoutPageTemplateFragment.isNew();
	}

	@Override
	public void persist() {
		_layoutPageTemplateFragment.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_layoutPageTemplateFragment.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this layout page template fragment.
	*
	* @param companyId the company ID of this layout page template fragment
	*/
	@Override
	public void setCompanyId(long companyId) {
		_layoutPageTemplateFragment.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this layout page template fragment.
	*
	* @param createDate the create date of this layout page template fragment
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_layoutPageTemplateFragment.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_layoutPageTemplateFragment.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_layoutPageTemplateFragment.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_layoutPageTemplateFragment.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the fragment entry ID of this layout page template fragment.
	*
	* @param fragmentEntryId the fragment entry ID of this layout page template fragment
	*/
	@Override
	public void setFragmentEntryId(long fragmentEntryId) {
		_layoutPageTemplateFragment.setFragmentEntryId(fragmentEntryId);
	}

	/**
	* Sets the group ID of this layout page template fragment.
	*
	* @param groupId the group ID of this layout page template fragment
	*/
	@Override
	public void setGroupId(long groupId) {
		_layoutPageTemplateFragment.setGroupId(groupId);
	}

	/**
	* Sets the layout page template entry ID of this layout page template fragment.
	*
	* @param layoutPageTemplateEntryId the layout page template entry ID of this layout page template fragment
	*/
	@Override
	public void setLayoutPageTemplateEntryId(long layoutPageTemplateEntryId) {
		_layoutPageTemplateFragment.setLayoutPageTemplateEntryId(layoutPageTemplateEntryId);
	}

	/**
	* Sets the layout page template fragment ID of this layout page template fragment.
	*
	* @param layoutPageTemplateFragmentId the layout page template fragment ID of this layout page template fragment
	*/
	@Override
	public void setLayoutPageTemplateFragmentId(
		long layoutPageTemplateFragmentId) {
		_layoutPageTemplateFragment.setLayoutPageTemplateFragmentId(layoutPageTemplateFragmentId);
	}

	/**
	* Sets the modified date of this layout page template fragment.
	*
	* @param modifiedDate the modified date of this layout page template fragment
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_layoutPageTemplateFragment.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_layoutPageTemplateFragment.setNew(n);
	}

	/**
	* Sets the position of this layout page template fragment.
	*
	* @param position the position of this layout page template fragment
	*/
	@Override
	public void setPosition(int position) {
		_layoutPageTemplateFragment.setPosition(position);
	}

	/**
	* Sets the primary key of this layout page template fragment.
	*
	* @param primaryKey the primary key of this layout page template fragment
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_layoutPageTemplateFragment.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_layoutPageTemplateFragment.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the user ID of this layout page template fragment.
	*
	* @param userId the user ID of this layout page template fragment
	*/
	@Override
	public void setUserId(long userId) {
		_layoutPageTemplateFragment.setUserId(userId);
	}

	/**
	* Sets the user name of this layout page template fragment.
	*
	* @param userName the user name of this layout page template fragment
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_layoutPageTemplateFragment.setUserName(userName);
	}

	/**
	* Sets the user uuid of this layout page template fragment.
	*
	* @param userUuid the user uuid of this layout page template fragment
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_layoutPageTemplateFragment.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<LayoutPageTemplateFragment> toCacheModel() {
		return _layoutPageTemplateFragment.toCacheModel();
	}

	@Override
	public LayoutPageTemplateFragment toEscapedModel() {
		return new LayoutPageTemplateFragmentWrapper(_layoutPageTemplateFragment.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _layoutPageTemplateFragment.toString();
	}

	@Override
	public LayoutPageTemplateFragment toUnescapedModel() {
		return new LayoutPageTemplateFragmentWrapper(_layoutPageTemplateFragment.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _layoutPageTemplateFragment.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof LayoutPageTemplateFragmentWrapper)) {
			return false;
		}

		LayoutPageTemplateFragmentWrapper layoutPageTemplateFragmentWrapper = (LayoutPageTemplateFragmentWrapper)obj;

		if (Objects.equals(_layoutPageTemplateFragment,
					layoutPageTemplateFragmentWrapper._layoutPageTemplateFragment)) {
			return true;
		}

		return false;
	}

	@Override
	public LayoutPageTemplateFragment getWrappedModel() {
		return _layoutPageTemplateFragment;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _layoutPageTemplateFragment.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _layoutPageTemplateFragment.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_layoutPageTemplateFragment.resetOriginalValues();
	}

	private final LayoutPageTemplateFragment _layoutPageTemplateFragment;
}