/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

/**
 * <p>
 * This class is a wrapper for {@link ResourceBlockPermission}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ResourceBlockPermission
 * @generated
 */
public class ResourceBlockPermissionWrapper implements ResourceBlockPermission,
	ModelWrapper<ResourceBlockPermission> {
	public ResourceBlockPermissionWrapper(
		ResourceBlockPermission resourceBlockPermission) {
		_resourceBlockPermission = resourceBlockPermission;
	}

	public Class<?> getModelClass() {
		return ResourceBlockPermission.class;
	}

	public String getModelClassName() {
		return ResourceBlockPermission.class.getName();
	}

	/**
	* Returns the primary key of this resource block permission.
	*
	* @return the primary key of this resource block permission
	*/
	public long getPrimaryKey() {
		return _resourceBlockPermission.getPrimaryKey();
	}

	/**
	* Sets the primary key of this resource block permission.
	*
	* @param primaryKey the primary key of this resource block permission
	*/
	public void setPrimaryKey(long primaryKey) {
		_resourceBlockPermission.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the resource block permission ID of this resource block permission.
	*
	* @return the resource block permission ID of this resource block permission
	*/
	public long getResourceBlockPermissionId() {
		return _resourceBlockPermission.getResourceBlockPermissionId();
	}

	/**
	* Sets the resource block permission ID of this resource block permission.
	*
	* @param resourceBlockPermissionId the resource block permission ID of this resource block permission
	*/
	public void setResourceBlockPermissionId(long resourceBlockPermissionId) {
		_resourceBlockPermission.setResourceBlockPermissionId(resourceBlockPermissionId);
	}

	/**
	* Returns the resource block ID of this resource block permission.
	*
	* @return the resource block ID of this resource block permission
	*/
	public long getResourceBlockId() {
		return _resourceBlockPermission.getResourceBlockId();
	}

	/**
	* Sets the resource block ID of this resource block permission.
	*
	* @param resourceBlockId the resource block ID of this resource block permission
	*/
	public void setResourceBlockId(long resourceBlockId) {
		_resourceBlockPermission.setResourceBlockId(resourceBlockId);
	}

	/**
	* Returns the role ID of this resource block permission.
	*
	* @return the role ID of this resource block permission
	*/
	public long getRoleId() {
		return _resourceBlockPermission.getRoleId();
	}

	/**
	* Sets the role ID of this resource block permission.
	*
	* @param roleId the role ID of this resource block permission
	*/
	public void setRoleId(long roleId) {
		_resourceBlockPermission.setRoleId(roleId);
	}

	/**
	* Returns the action IDs of this resource block permission.
	*
	* @return the action IDs of this resource block permission
	*/
	public long getActionIds() {
		return _resourceBlockPermission.getActionIds();
	}

	/**
	* Sets the action IDs of this resource block permission.
	*
	* @param actionIds the action IDs of this resource block permission
	*/
	public void setActionIds(long actionIds) {
		_resourceBlockPermission.setActionIds(actionIds);
	}

	public boolean isNew() {
		return _resourceBlockPermission.isNew();
	}

	public void setNew(boolean n) {
		_resourceBlockPermission.setNew(n);
	}

	public boolean isCachedModel() {
		return _resourceBlockPermission.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_resourceBlockPermission.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _resourceBlockPermission.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _resourceBlockPermission.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_resourceBlockPermission.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _resourceBlockPermission.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_resourceBlockPermission.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new ResourceBlockPermissionWrapper((ResourceBlockPermission)_resourceBlockPermission.clone());
	}

	public int compareTo(
		com.liferay.portal.model.ResourceBlockPermission resourceBlockPermission) {
		return _resourceBlockPermission.compareTo(resourceBlockPermission);
	}

	@Override
	public int hashCode() {
		return _resourceBlockPermission.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portal.model.ResourceBlockPermission> toCacheModel() {
		return _resourceBlockPermission.toCacheModel();
	}

	public com.liferay.portal.model.ResourceBlockPermission toEscapedModel() {
		return new ResourceBlockPermissionWrapper(_resourceBlockPermission.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _resourceBlockPermission.toString();
	}

	public java.lang.String toXmlString() {
		return _resourceBlockPermission.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_resourceBlockPermission.persist();
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public ResourceBlockPermission getWrappedResourceBlockPermission() {
		return _resourceBlockPermission;
	}

	public ResourceBlockPermission getWrappedModel() {
		return _resourceBlockPermission;
	}

	public void resetOriginalValues() {
		_resourceBlockPermission.resetOriginalValues();
	}

	private ResourceBlockPermission _resourceBlockPermission;
}