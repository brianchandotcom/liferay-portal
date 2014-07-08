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

package com.liferay.portal.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link ResourceBlock}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ResourceBlock
 * @generated
 */
@ProviderType
public class ResourceBlockWrapper implements ResourceBlock,
	ModelWrapper<ResourceBlock> {
	public ResourceBlockWrapper(ResourceBlock resourceBlock) {
		_resourceBlock = resourceBlock;
	}

	@Override
	public Class<?> getModelClass() {
		return ResourceBlock.class;
	}

	@Override
	public String getModelClassName() {
		return ResourceBlock.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("resourceBlockId", getResourceBlockId());
		attributes.put("companyId", getCompanyId());
		attributes.put("groupId", getGroupId());
		attributes.put("name", getName());
		attributes.put("permissionsHash", getPermissionsHash());
		attributes.put("referenceCount", getReferenceCount());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

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
	@Override
	public long getPrimaryKey() {
		return _resourceBlock.getPrimaryKey();
	}

	/**
	* Sets the primary key of this resource block.
	*
	* @param primaryKey the primary key of this resource block
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_resourceBlock.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the mvcc version of this resource block.
	*
	* @return the mvcc version of this resource block
	*/
	@Override
	public long getMvccVersion() {
		return _resourceBlock.getMvccVersion();
	}

	/**
	* Sets the mvcc version of this resource block.
	*
	* @param mvccVersion the mvcc version of this resource block
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		_resourceBlock.setMvccVersion(mvccVersion);
	}

	/**
	* Returns the resource block ID of this resource block.
	*
	* @return the resource block ID of this resource block
	*/
	@Override
	public long getResourceBlockId() {
		return _resourceBlock.getResourceBlockId();
	}

	/**
	* Sets the resource block ID of this resource block.
	*
	* @param resourceBlockId the resource block ID of this resource block
	*/
	@Override
	public void setResourceBlockId(long resourceBlockId) {
		_resourceBlock.setResourceBlockId(resourceBlockId);
	}

	/**
	* Returns the company ID of this resource block.
	*
	* @return the company ID of this resource block
	*/
	@Override
	public long getCompanyId() {
		return _resourceBlock.getCompanyId();
	}

	/**
	* Sets the company ID of this resource block.
	*
	* @param companyId the company ID of this resource block
	*/
	@Override
	public void setCompanyId(long companyId) {
		_resourceBlock.setCompanyId(companyId);
	}

	/**
	* Returns the group ID of this resource block.
	*
	* @return the group ID of this resource block
	*/
	@Override
	public long getGroupId() {
		return _resourceBlock.getGroupId();
	}

	/**
	* Sets the group ID of this resource block.
	*
	* @param groupId the group ID of this resource block
	*/
	@Override
	public void setGroupId(long groupId) {
		_resourceBlock.setGroupId(groupId);
	}

	/**
	* Returns the name of this resource block.
	*
	* @return the name of this resource block
	*/
	@Override
	public java.lang.String getName() {
		return _resourceBlock.getName();
	}

	/**
	* Sets the name of this resource block.
	*
	* @param name the name of this resource block
	*/
	@Override
	public void setName(java.lang.String name) {
		_resourceBlock.setName(name);
	}

	/**
	* Returns the permissions hash of this resource block.
	*
	* @return the permissions hash of this resource block
	*/
	@Override
	public java.lang.String getPermissionsHash() {
		return _resourceBlock.getPermissionsHash();
	}

	/**
	* Sets the permissions hash of this resource block.
	*
	* @param permissionsHash the permissions hash of this resource block
	*/
	@Override
	public void setPermissionsHash(java.lang.String permissionsHash) {
		_resourceBlock.setPermissionsHash(permissionsHash);
	}

	/**
	* Returns the reference count of this resource block.
	*
	* @return the reference count of this resource block
	*/
	@Override
	public long getReferenceCount() {
		return _resourceBlock.getReferenceCount();
	}

	/**
	* Sets the reference count of this resource block.
	*
	* @param referenceCount the reference count of this resource block
	*/
	@Override
	public void setReferenceCount(long referenceCount) {
		_resourceBlock.setReferenceCount(referenceCount);
	}

	@Override
	public boolean isNew() {
		return _resourceBlock.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_resourceBlock.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _resourceBlock.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_resourceBlock.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _resourceBlock.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _resourceBlock.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_resourceBlock.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _resourceBlock.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_resourceBlock.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_resourceBlock.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_resourceBlock.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new ResourceBlockWrapper((ResourceBlock)_resourceBlock.clone());
	}

	@Override
	public int compareTo(com.liferay.portal.model.ResourceBlock resourceBlock) {
		return _resourceBlock.compareTo(resourceBlock);
	}

	@Override
	public int hashCode() {
		return _resourceBlock.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portal.model.ResourceBlock> toCacheModel() {
		return _resourceBlock.toCacheModel();
	}

	@Override
	public com.liferay.portal.model.ResourceBlock toEscapedModel() {
		return new ResourceBlockWrapper(_resourceBlock.toEscapedModel());
	}

	@Override
	public com.liferay.portal.model.ResourceBlock toUnescapedModel() {
		return new ResourceBlockWrapper(_resourceBlock.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _resourceBlock.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _resourceBlock.toXmlString();
	}

	@Override
	public void persist() {
		_resourceBlock.persist();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ResourceBlockWrapper)) {
			return false;
		}

		ResourceBlockWrapper resourceBlockWrapper = (ResourceBlockWrapper)obj;

		if (Validator.equals(_resourceBlock, resourceBlockWrapper._resourceBlock)) {
			return true;
		}

		return false;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	@Deprecated
	public ResourceBlock getWrappedResourceBlock() {
		return _resourceBlock;
	}

	@Override
	public ResourceBlock getWrappedModel() {
		return _resourceBlock;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _resourceBlock.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _resourceBlock.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_resourceBlock.resetOriginalValues();
	}

	private ResourceBlock _resourceBlock;
}