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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link FragmentEntryInstanceLink}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FragmentEntryInstanceLink
 * @generated
 */
@ProviderType
public class FragmentEntryInstanceLinkWrapper
	implements FragmentEntryInstanceLink,
		ModelWrapper<FragmentEntryInstanceLink> {
	public FragmentEntryInstanceLinkWrapper(
		FragmentEntryInstanceLink fragmentEntryInstanceLink) {
		_fragmentEntryInstanceLink = fragmentEntryInstanceLink;
	}

	@Override
	public Class<?> getModelClass() {
		return FragmentEntryInstanceLink.class;
	}

	@Override
	public String getModelClassName() {
		return FragmentEntryInstanceLink.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("fragmentEntryInstanceLinkId",
			getFragmentEntryInstanceLinkId());
		attributes.put("groupId", getGroupId());
		attributes.put("fragmentEntryId", getFragmentEntryId());
		attributes.put("layoutPageTemplateEntryId",
			getLayoutPageTemplateEntryId());
		attributes.put("position", getPosition());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long fragmentEntryInstanceLinkId = (Long)attributes.get(
				"fragmentEntryInstanceLinkId");

		if (fragmentEntryInstanceLinkId != null) {
			setFragmentEntryInstanceLinkId(fragmentEntryInstanceLinkId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long fragmentEntryId = (Long)attributes.get("fragmentEntryId");

		if (fragmentEntryId != null) {
			setFragmentEntryId(fragmentEntryId);
		}

		Long layoutPageTemplateEntryId = (Long)attributes.get(
				"layoutPageTemplateEntryId");

		if (layoutPageTemplateEntryId != null) {
			setLayoutPageTemplateEntryId(layoutPageTemplateEntryId);
		}

		Integer position = (Integer)attributes.get("position");

		if (position != null) {
			setPosition(position);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new FragmentEntryInstanceLinkWrapper((FragmentEntryInstanceLink)_fragmentEntryInstanceLink.clone());
	}

	@Override
	public int compareTo(FragmentEntryInstanceLink fragmentEntryInstanceLink) {
		return _fragmentEntryInstanceLink.compareTo(fragmentEntryInstanceLink);
	}

	@Override
	public java.lang.String getCss()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _fragmentEntryInstanceLink.getCss();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _fragmentEntryInstanceLink.getExpandoBridge();
	}

	/**
	* Returns the fragment entry ID of this fragment entry instance link.
	*
	* @return the fragment entry ID of this fragment entry instance link
	*/
	@Override
	public long getFragmentEntryId() {
		return _fragmentEntryInstanceLink.getFragmentEntryId();
	}

	/**
	* Returns the fragment entry instance link ID of this fragment entry instance link.
	*
	* @return the fragment entry instance link ID of this fragment entry instance link
	*/
	@Override
	public long getFragmentEntryInstanceLinkId() {
		return _fragmentEntryInstanceLink.getFragmentEntryInstanceLinkId();
	}

	/**
	* Returns the group ID of this fragment entry instance link.
	*
	* @return the group ID of this fragment entry instance link
	*/
	@Override
	public long getGroupId() {
		return _fragmentEntryInstanceLink.getGroupId();
	}

	@Override
	public java.lang.String getHtml()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _fragmentEntryInstanceLink.getHtml();
	}

	@Override
	public java.lang.String getJs()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _fragmentEntryInstanceLink.getJs();
	}

	/**
	* Returns the layout page template entry ID of this fragment entry instance link.
	*
	* @return the layout page template entry ID of this fragment entry instance link
	*/
	@Override
	public long getLayoutPageTemplateEntryId() {
		return _fragmentEntryInstanceLink.getLayoutPageTemplateEntryId();
	}

	/**
	* Returns the position of this fragment entry instance link.
	*
	* @return the position of this fragment entry instance link
	*/
	@Override
	public int getPosition() {
		return _fragmentEntryInstanceLink.getPosition();
	}

	/**
	* Returns the primary key of this fragment entry instance link.
	*
	* @return the primary key of this fragment entry instance link
	*/
	@Override
	public long getPrimaryKey() {
		return _fragmentEntryInstanceLink.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _fragmentEntryInstanceLink.getPrimaryKeyObj();
	}

	@Override
	public int hashCode() {
		return _fragmentEntryInstanceLink.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _fragmentEntryInstanceLink.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _fragmentEntryInstanceLink.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _fragmentEntryInstanceLink.isNew();
	}

	@Override
	public void persist() {
		_fragmentEntryInstanceLink.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_fragmentEntryInstanceLink.setCachedModel(cachedModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_fragmentEntryInstanceLink.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_fragmentEntryInstanceLink.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_fragmentEntryInstanceLink.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the fragment entry ID of this fragment entry instance link.
	*
	* @param fragmentEntryId the fragment entry ID of this fragment entry instance link
	*/
	@Override
	public void setFragmentEntryId(long fragmentEntryId) {
		_fragmentEntryInstanceLink.setFragmentEntryId(fragmentEntryId);
	}

	/**
	* Sets the fragment entry instance link ID of this fragment entry instance link.
	*
	* @param fragmentEntryInstanceLinkId the fragment entry instance link ID of this fragment entry instance link
	*/
	@Override
	public void setFragmentEntryInstanceLinkId(long fragmentEntryInstanceLinkId) {
		_fragmentEntryInstanceLink.setFragmentEntryInstanceLinkId(fragmentEntryInstanceLinkId);
	}

	/**
	* Sets the group ID of this fragment entry instance link.
	*
	* @param groupId the group ID of this fragment entry instance link
	*/
	@Override
	public void setGroupId(long groupId) {
		_fragmentEntryInstanceLink.setGroupId(groupId);
	}

	/**
	* Sets the layout page template entry ID of this fragment entry instance link.
	*
	* @param layoutPageTemplateEntryId the layout page template entry ID of this fragment entry instance link
	*/
	@Override
	public void setLayoutPageTemplateEntryId(long layoutPageTemplateEntryId) {
		_fragmentEntryInstanceLink.setLayoutPageTemplateEntryId(layoutPageTemplateEntryId);
	}

	@Override
	public void setNew(boolean n) {
		_fragmentEntryInstanceLink.setNew(n);
	}

	/**
	* Sets the position of this fragment entry instance link.
	*
	* @param position the position of this fragment entry instance link
	*/
	@Override
	public void setPosition(int position) {
		_fragmentEntryInstanceLink.setPosition(position);
	}

	/**
	* Sets the primary key of this fragment entry instance link.
	*
	* @param primaryKey the primary key of this fragment entry instance link
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_fragmentEntryInstanceLink.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_fragmentEntryInstanceLink.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<FragmentEntryInstanceLink> toCacheModel() {
		return _fragmentEntryInstanceLink.toCacheModel();
	}

	@Override
	public FragmentEntryInstanceLink toEscapedModel() {
		return new FragmentEntryInstanceLinkWrapper(_fragmentEntryInstanceLink.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _fragmentEntryInstanceLink.toString();
	}

	@Override
	public FragmentEntryInstanceLink toUnescapedModel() {
		return new FragmentEntryInstanceLinkWrapper(_fragmentEntryInstanceLink.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _fragmentEntryInstanceLink.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof FragmentEntryInstanceLinkWrapper)) {
			return false;
		}

		FragmentEntryInstanceLinkWrapper fragmentEntryInstanceLinkWrapper = (FragmentEntryInstanceLinkWrapper)obj;

		if (Objects.equals(_fragmentEntryInstanceLink,
					fragmentEntryInstanceLinkWrapper._fragmentEntryInstanceLink)) {
			return true;
		}

		return false;
	}

	@Override
	public FragmentEntryInstanceLink getWrappedModel() {
		return _fragmentEntryInstanceLink;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _fragmentEntryInstanceLink.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _fragmentEntryInstanceLink.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_fragmentEntryInstanceLink.resetOriginalValues();
	}

	private final FragmentEntryInstanceLink _fragmentEntryInstanceLink;
}