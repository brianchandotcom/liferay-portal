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
 * This class is a wrapper for {@link ResourceBlockPermission}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ResourceBlockPermission
 * @generated
 */
@ProviderType
public class ResourceBlockPermissionWrapper implements ResourceBlockPermission,
	ModelWrapper<ResourceBlockPermission> {
	public ResourceBlockPermissionWrapper(
		ResourceBlockPermission resourceBlockPermission) {
		_resourceBlockPermission = resourceBlockPermission;
	}

	@Override
	public Class<?> getModelClass() {
		return ResourceBlockPermission.class;
	}

	@Override
	public String getModelClassName() {
		return ResourceBlockPermission.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("resourceBlockPermissionId",
			getResourceBlockPermissionId());
		attributes.put("companyId", getCompanyId());
		attributes.put("resourceBlockId", getResourceBlockId());
		attributes.put("roleId", getRoleId());
		attributes.put("actionIds", getActionIds());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long resourceBlockPermissionId = (Long)attributes.get(
				"resourceBlockPermissionId");

		if (resourceBlockPermissionId != null) {
			setResourceBlockPermissionId(resourceBlockPermissionId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long resourceBlockId = (Long)attributes.get("resourceBlockId");

		if (resourceBlockId != null) {
			setResourceBlockId(resourceBlockId);
		}

		Long roleId = (Long)attributes.get("roleId");

		if (roleId != null) {
			setRoleId(roleId);
		}

		Long actionIds = (Long)attributes.get("actionIds");

		if (actionIds != null) {
			setActionIds(actionIds);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new ResourceBlockPermissionWrapper((ResourceBlockPermission)_resourceBlockPermission.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portal.model.ResourceBlockPermission resourceBlockPermission) {
		return _resourceBlockPermission.compareTo(resourceBlockPermission);
	}

	/**
	* Returns the action IDs of this resource block permission.
	*
	* @return the action IDs of this resource block permission
	*/
	@Override
	public long getActionIds() {
		return _resourceBlockPermission.getActionIds();
	}

	/**
	* Returns the company ID of this resource block permission.
	*
	* @return the company ID of this resource block permission
	*/
	@Override
	public long getCompanyId() {
		return _resourceBlockPermission.getCompanyId();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _resourceBlockPermission.getExpandoBridge();
	}

	/**
	* Returns the mvcc version of this resource block permission.
	*
	* @return the mvcc version of this resource block permission
	*/
	@Override
	public long getMvccVersion() {
		return _resourceBlockPermission.getMvccVersion();
	}

	/**
	* Returns the primary key of this resource block permission.
	*
	* @return the primary key of this resource block permission
	*/
	@Override
	public long getPrimaryKey() {
		return _resourceBlockPermission.getPrimaryKey();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _resourceBlockPermission.getPrimaryKeyObj();
	}

	/**
	* Returns the resource block ID of this resource block permission.
	*
	* @return the resource block ID of this resource block permission
	*/
	@Override
	public long getResourceBlockId() {
		return _resourceBlockPermission.getResourceBlockId();
	}

	/**
	* Returns the resource block permission ID of this resource block permission.
	*
	* @return the resource block permission ID of this resource block permission
	*/
	@Override
	public long getResourceBlockPermissionId() {
		return _resourceBlockPermission.getResourceBlockPermissionId();
	}

	/**
	* Returns the role ID of this resource block permission.
	*
	* @return the role ID of this resource block permission
	*/
	@Override
	public long getRoleId() {
		return _resourceBlockPermission.getRoleId();
	}

	@Override
	public int hashCode() {
		return _resourceBlockPermission.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _resourceBlockPermission.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _resourceBlockPermission.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _resourceBlockPermission.isNew();
	}

	@Override
	public void persist() {
		_resourceBlockPermission.persist();
	}

	/**
	* Sets the action IDs of this resource block permission.
	*
	* @param actionIds the action IDs of this resource block permission
	*/
	@Override
	public void setActionIds(long actionIds) {
		_resourceBlockPermission.setActionIds(actionIds);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_resourceBlockPermission.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this resource block permission.
	*
	* @param companyId the company ID of this resource block permission
	*/
	@Override
	public void setCompanyId(long companyId) {
		_resourceBlockPermission.setCompanyId(companyId);
	}

	@Override
	public void setExpandoBridgeAttributes(BaseModel<?> baseModel) {
		_resourceBlockPermission.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_resourceBlockPermission.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_resourceBlockPermission.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the mvcc version of this resource block permission.
	*
	* @param mvccVersion the mvcc version of this resource block permission
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		_resourceBlockPermission.setMvccVersion(mvccVersion);
	}

	@Override
	public void setNew(boolean n) {
		_resourceBlockPermission.setNew(n);
	}

	/**
	* Sets the primary key of this resource block permission.
	*
	* @param primaryKey the primary key of this resource block permission
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_resourceBlockPermission.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_resourceBlockPermission.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the resource block ID of this resource block permission.
	*
	* @param resourceBlockId the resource block ID of this resource block permission
	*/
	@Override
	public void setResourceBlockId(long resourceBlockId) {
		_resourceBlockPermission.setResourceBlockId(resourceBlockId);
	}

	/**
	* Sets the resource block permission ID of this resource block permission.
	*
	* @param resourceBlockPermissionId the resource block permission ID of this resource block permission
	*/
	@Override
	public void setResourceBlockPermissionId(long resourceBlockPermissionId) {
		_resourceBlockPermission.setResourceBlockPermissionId(resourceBlockPermissionId);
	}

	/**
	* Sets the role ID of this resource block permission.
	*
	* @param roleId the role ID of this resource block permission
	*/
	@Override
	public void setRoleId(long roleId) {
		_resourceBlockPermission.setRoleId(roleId);
	}

	@Override
	public CacheModel<com.liferay.portal.model.ResourceBlockPermission> toCacheModel() {
		return _resourceBlockPermission.toCacheModel();
	}

	@Override
	public com.liferay.portal.model.ResourceBlockPermission toEscapedModel() {
		return new ResourceBlockPermissionWrapper(_resourceBlockPermission.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _resourceBlockPermission.toString();
	}

	@Override
	public com.liferay.portal.model.ResourceBlockPermission toUnescapedModel() {
		return new ResourceBlockPermissionWrapper(_resourceBlockPermission.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _resourceBlockPermission.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ResourceBlockPermissionWrapper)) {
			return false;
		}

		ResourceBlockPermissionWrapper resourceBlockPermissionWrapper = (ResourceBlockPermissionWrapper)obj;

		if (Validator.equals(_resourceBlockPermission,
					resourceBlockPermissionWrapper._resourceBlockPermission)) {
			return true;
		}

		return false;
	}

	@Override
	public ResourceBlockPermission getWrappedModel() {
		return _resourceBlockPermission;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _resourceBlockPermission.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _resourceBlockPermission.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_resourceBlockPermission.resetOriginalValues();
	}

	private final ResourceBlockPermission _resourceBlockPermission;
}