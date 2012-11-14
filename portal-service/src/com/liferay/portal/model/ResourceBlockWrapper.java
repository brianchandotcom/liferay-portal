/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link ResourceBlock}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ResourceBlock
 * @generated
 */
public class ResourceBlockWrapper implements ResourceBlock,
	ModelWrapper<ResourceBlock> {
	public ResourceBlockWrapper(ResourceBlock resourceBlock) {
		_resourceBlock = resourceBlock;
	}

	public Class<?> getModelClass() {
		return ResourceBlock.class;
	}

	public String getModelClassName() {
		return ResourceBlock.class.getName();
	}

	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("resourceBlockId", getResourceBlockId());
		attributes.put("companyId", getCompanyId());
		attributes.put("groupId", getGroupId());
		attributes.put("name", getName());
		attributes.put("permissionsHash", getPermissionsHash());
		attributes.put("referenceCount", getReferenceCount());

		return attributes;
	}

	public void setModelAttributes(Map<String, Object> attributes) {
		Long resourceBlockId = (Long)attributes.get("resourceBlockId");

		if (resourceBlockId != null) {
			setResourceBlockId(resourceBlockId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String permissionsHash = (String)attributes.get("permissionsHash");

		if (permissionsHash != null) {
			setPermissionsHash(permissionsHash);
		}

		Long referenceCount = (Long)attributes.get("referenceCount");

		if (referenceCount != null) {
			setReferenceCount(referenceCount);
		}
	}

	/**
	* Returns the primary key of this resource block.
	*
	* @return the primary key of this resource block
	*/
	public long getPrimaryKey() {
		return _resourceBlock.getPrimaryKey();
	}

	/**
	* Sets the primary key of this resource block.
	*
	* @param primaryKey the primary key of this resource block
	*/
	public void setPrimaryKey(long primaryKey) {
		_resourceBlock.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the resource block ID of this resource block.
	*
	* @return the resource block ID of this resource block
	*/
	public long getResourceBlockId() {
		return _resourceBlock.getResourceBlockId();
	}

	/**
	* Sets the resource block ID of this resource block.
	*
	* @param resourceBlockId the resource block ID of this resource block
	*/
	public void setResourceBlockId(long resourceBlockId) {
		_resourceBlock.setResourceBlockId(resourceBlockId);
	}

	/**
	* Returns the company ID of this resource block.
	*
	* @return the company ID of this resource block
	*/
	public long getCompanyId() {
		return _resourceBlock.getCompanyId();
	}

	/**
	* Sets the company ID of this resource block.
	*
	* @param companyId the company ID of this resource block
	*/
	public void setCompanyId(long companyId) {
		_resourceBlock.setCompanyId(companyId);
	}

	/**
	* Returns the group ID of this resource block.
	*
	* @return the group ID of this resource block
	*/
	public long getGroupId() {
		return _resourceBlock.getGroupId();
	}

	/**
	* Sets the group ID of this resource block.
	*
	* @param groupId the group ID of this resource block
	*/
	public void setGroupId(long groupId) {
		_resourceBlock.setGroupId(groupId);
	}

	/**
	* Returns the name of this resource block.
	*
	* @return the name of this resource block
	*/
	public java.lang.String getName() {
		return _resourceBlock.getName();
	}

	/**
	* Sets the name of this resource block.
	*
	* @param name the name of this resource block
	*/
	public void setName(java.lang.String name) {
		_resourceBlock.setName(name);
	}

	/**
	* Returns the permissions hash of this resource block.
	*
	* @return the permissions hash of this resource block
	*/
	public java.lang.String getPermissionsHash() {
		return _resourceBlock.getPermissionsHash();
	}

	/**
	* Sets the permissions hash of this resource block.
	*
	* @param permissionsHash the permissions hash of this resource block
	*/
	public void setPermissionsHash(java.lang.String permissionsHash) {
		_resourceBlock.setPermissionsHash(permissionsHash);
	}

	/**
	* Returns the reference count of this resource block.
	*
	* @return the reference count of this resource block
	*/
	public long getReferenceCount() {
		return _resourceBlock.getReferenceCount();
	}

	/**
	* Sets the reference count of this resource block.
	*
	* @param referenceCount the reference count of this resource block
	*/
	public void setReferenceCount(long referenceCount) {
		_resourceBlock.setReferenceCount(referenceCount);
	}

	public boolean isNew() {
		return _resourceBlock.isNew();
	}

	public void setNew(boolean n) {
		_resourceBlock.setNew(n);
	}

	public boolean isCachedModel() {
		return _resourceBlock.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_resourceBlock.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _resourceBlock.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _resourceBlock.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_resourceBlock.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _resourceBlock.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_resourceBlock.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new ResourceBlockWrapper((ResourceBlock)_resourceBlock.clone());
	}

	public int compareTo(com.liferay.portal.model.ResourceBlock resourceBlock) {
		return _resourceBlock.compareTo(resourceBlock);
	}

	@Override
	public int hashCode() {
		return _resourceBlock.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portal.model.ResourceBlock> toCacheModel() {
		return _resourceBlock.toCacheModel();
	}

	public com.liferay.portal.model.ResourceBlock toEscapedModel() {
		return new ResourceBlockWrapper(_resourceBlock.toEscapedModel());
	}

	public com.liferay.portal.model.ResourceBlock toUnescapedModel() {
		return new ResourceBlockWrapper(_resourceBlock.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _resourceBlock.toString();
	}

	public java.lang.String toXmlString() {
		return _resourceBlock.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_resourceBlock.persist();
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public ResourceBlock getWrappedResourceBlock() {
		return _resourceBlock;
	}

	public ResourceBlock getWrappedModel() {
		return _resourceBlock;
	}

	public void resetOriginalValues() {
		_resourceBlock.resetOriginalValues();
	}

	private ResourceBlock _resourceBlock;
}