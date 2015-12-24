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

package com.liferay.wiki.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link WikiPageResource}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see WikiPageResource
 * @generated
 */
@ProviderType
public class WikiPageResourceWrapper implements WikiPageResource,
	ModelWrapper<WikiPageResource> {
	public WikiPageResourceWrapper(WikiPageResource wikiPageResource) {
		_wikiPageResource = wikiPageResource;
	}

	@Override
	public Class<?> getModelClass() {
		return WikiPageResource.class;
	}

	@Override
	public String getModelClassName() {
		return WikiPageResource.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("resourcePrimKey", getResourcePrimKey());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("nodeId", getNodeId());
		attributes.put("title", getTitle());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long resourcePrimKey = (Long)attributes.get("resourcePrimKey");

		if (resourcePrimKey != null) {
			setResourcePrimKey(resourcePrimKey);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long nodeId = (Long)attributes.get("nodeId");

		if (nodeId != null) {
			setNodeId(nodeId);
		}

		String title = (String)attributes.get("title");

		if (title != null) {
			setTitle(title);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new WikiPageResourceWrapper((WikiPageResource)_wikiPageResource.clone());
	}

	@Override
	public int compareTo(
		com.liferay.wiki.model.WikiPageResource wikiPageResource) {
		return _wikiPageResource.compareTo(wikiPageResource);
	}

	/**
	* Returns the company ID of this wiki page resource.
	*
	* @return the company ID of this wiki page resource
	*/
	@Override
	public long getCompanyId() {
		return _wikiPageResource.getCompanyId();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _wikiPageResource.getExpandoBridge();
	}

	/**
	* Returns the group ID of this wiki page resource.
	*
	* @return the group ID of this wiki page resource
	*/
	@Override
	public long getGroupId() {
		return _wikiPageResource.getGroupId();
	}

	/**
	* Returns the node ID of this wiki page resource.
	*
	* @return the node ID of this wiki page resource
	*/
	@Override
	public long getNodeId() {
		return _wikiPageResource.getNodeId();
	}

	/**
	* Returns the primary key of this wiki page resource.
	*
	* @return the primary key of this wiki page resource
	*/
	@Override
	public long getPrimaryKey() {
		return _wikiPageResource.getPrimaryKey();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _wikiPageResource.getPrimaryKeyObj();
	}

	/**
	* Returns the resource prim key of this wiki page resource.
	*
	* @return the resource prim key of this wiki page resource
	*/
	@Override
	public long getResourcePrimKey() {
		return _wikiPageResource.getResourcePrimKey();
	}

	/**
	* Returns the title of this wiki page resource.
	*
	* @return the title of this wiki page resource
	*/
	@Override
	public java.lang.String getTitle() {
		return _wikiPageResource.getTitle();
	}

	/**
	* Returns the uuid of this wiki page resource.
	*
	* @return the uuid of this wiki page resource
	*/
	@Override
	public java.lang.String getUuid() {
		return _wikiPageResource.getUuid();
	}

	@Override
	public int hashCode() {
		return _wikiPageResource.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _wikiPageResource.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _wikiPageResource.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _wikiPageResource.isNew();
	}

	@Override
	public void persist() {
		_wikiPageResource.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_wikiPageResource.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this wiki page resource.
	*
	* @param companyId the company ID of this wiki page resource
	*/
	@Override
	public void setCompanyId(long companyId) {
		_wikiPageResource.setCompanyId(companyId);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_wikiPageResource.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_wikiPageResource.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_wikiPageResource.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this wiki page resource.
	*
	* @param groupId the group ID of this wiki page resource
	*/
	@Override
	public void setGroupId(long groupId) {
		_wikiPageResource.setGroupId(groupId);
	}

	@Override
	public void setNew(boolean n) {
		_wikiPageResource.setNew(n);
	}

	/**
	* Sets the node ID of this wiki page resource.
	*
	* @param nodeId the node ID of this wiki page resource
	*/
	@Override
	public void setNodeId(long nodeId) {
		_wikiPageResource.setNodeId(nodeId);
	}

	/**
	* Sets the primary key of this wiki page resource.
	*
	* @param primaryKey the primary key of this wiki page resource
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_wikiPageResource.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_wikiPageResource.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the resource prim key of this wiki page resource.
	*
	* @param resourcePrimKey the resource prim key of this wiki page resource
	*/
	@Override
	public void setResourcePrimKey(long resourcePrimKey) {
		_wikiPageResource.setResourcePrimKey(resourcePrimKey);
	}

	/**
	* Sets the title of this wiki page resource.
	*
	* @param title the title of this wiki page resource
	*/
	@Override
	public void setTitle(java.lang.String title) {
		_wikiPageResource.setTitle(title);
	}

	/**
	* Sets the uuid of this wiki page resource.
	*
	* @param uuid the uuid of this wiki page resource
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_wikiPageResource.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.wiki.model.WikiPageResource> toCacheModel() {
		return _wikiPageResource.toCacheModel();
	}

	@Override
	public com.liferay.wiki.model.WikiPageResource toEscapedModel() {
		return new WikiPageResourceWrapper(_wikiPageResource.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _wikiPageResource.toString();
	}

	@Override
	public com.liferay.wiki.model.WikiPageResource toUnescapedModel() {
		return new WikiPageResourceWrapper(_wikiPageResource.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _wikiPageResource.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof WikiPageResourceWrapper)) {
			return false;
		}

		WikiPageResourceWrapper wikiPageResourceWrapper = (WikiPageResourceWrapper)obj;

		if (Validator.equals(_wikiPageResource,
					wikiPageResourceWrapper._wikiPageResource)) {
			return true;
		}

		return false;
	}

	@Override
	public WikiPageResource getWrappedModel() {
		return _wikiPageResource;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _wikiPageResource.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _wikiPageResource.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_wikiPageResource.resetOriginalValues();
	}

	private final WikiPageResource _wikiPageResource;
}