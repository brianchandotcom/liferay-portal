/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
 * This class is a wrapper for {@link ResourceTypePermission}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ResourceTypePermission
 * @generated
 */
public class ResourceTypePermissionWrapper implements ResourceTypePermission,
	ModelWrapper<ResourceTypePermission> {
	public ResourceTypePermissionWrapper(
		ResourceTypePermission resourceTypePermission) {
		_resourceTypePermission = resourceTypePermission;
	}

	@Override
	public Class<?> getModelClass() {
		return ResourceTypePermission.class;
	}

	@Override
	public String getModelClassName() {
		return ResourceTypePermission.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("resourceTypePermissionId", getResourceTypePermissionId());
		attributes.put("companyId", getCompanyId());
		attributes.put("groupId", getGroupId());
		attributes.put("name", getName());
		attributes.put("roleId", getRoleId());
		attributes.put("actionIds", getActionIds());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long resourceTypePermissionId = (Long)attributes.get(
				"resourceTypePermissionId");

		if (resourceTypePermissionId != null) {
			setResourceTypePermissionId(resourceTypePermissionId);
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

		Long roleId = (Long)attributes.get("roleId");

		if (roleId != null) {
			setRoleId(roleId);
		}

		Long actionIds = (Long)attributes.get("actionIds");

		if (actionIds != null) {
			setActionIds(actionIds);
		}
	}

	/**
	* Returns the primary key of this resource type permission.
	*
	* @return the primary key of this resource type permission
	*/
	@Override
	public long getPrimaryKey() {
		return _resourceTypePermission.getPrimaryKey();
	}

	/**
	* Sets the primary key of this resource type permission.
	*
	* @param primaryKey the primary key of this resource type permission
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_resourceTypePermission.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the resource type permission ID of this resource type permission.
	*
	* @return the resource type permission ID of this resource type permission
	*/
	@Override
	public long getResourceTypePermissionId() {
		return _resourceTypePermission.getResourceTypePermissionId();
	}

	/**
	* Sets the resource type permission ID of this resource type permission.
	*
	* @param resourceTypePermissionId the resource type permission ID of this resource type permission
	*/
	@Override
	public void setResourceTypePermissionId(long resourceTypePermissionId) {
		_resourceTypePermission.setResourceTypePermissionId(resourceTypePermissionId);
	}

	/**
	* Returns the company ID of this resource type permission.
	*
	* @return the company ID of this resource type permission
	*/
	@Override
	public long getCompanyId() {
		return _resourceTypePermission.getCompanyId();
	}

	/**
	* Sets the company ID of this resource type permission.
	*
	* @param companyId the company ID of this resource type permission
	*/
	@Override
	public void setCompanyId(long companyId) {
		_resourceTypePermission.setCompanyId(companyId);
	}

	/**
	* Returns the group ID of this resource type permission.
	*
	* @return the group ID of this resource type permission
	*/
	@Override
	public long getGroupId() {
		return _resourceTypePermission.getGroupId();
	}

	/**
	* Sets the group ID of this resource type permission.
	*
	* @param groupId the group ID of this resource type permission
	*/
	@Override
	public void setGroupId(long groupId) {
		_resourceTypePermission.setGroupId(groupId);
	}

	/**
	* Returns the name of this resource type permission.
	*
	* @return the name of this resource type permission
	*/
	@Override
	public java.lang.String getName() {
		return _resourceTypePermission.getName();
	}

	/**
	* Sets the name of this resource type permission.
	*
	* @param name the name of this resource type permission
	*/
	@Override
	public void setName(java.lang.String name) {
		_resourceTypePermission.setName(name);
	}

	/**
	* Returns the role ID of this resource type permission.
	*
	* @return the role ID of this resource type permission
	*/
	@Override
	public long getRoleId() {
		return _resourceTypePermission.getRoleId();
	}

	/**
	* Sets the role ID of this resource type permission.
	*
	* @param roleId the role ID of this resource type permission
	*/
	@Override
	public void setRoleId(long roleId) {
		_resourceTypePermission.setRoleId(roleId);
	}

	/**
	* Returns the action IDs of this resource type permission.
	*
	* @return the action IDs of this resource type permission
	*/
	@Override
	public long getActionIds() {
		return _resourceTypePermission.getActionIds();
	}

	/**
	* Sets the action IDs of this resource type permission.
	*
	* @param actionIds the action IDs of this resource type permission
	*/
	@Override
	public void setActionIds(long actionIds) {
		_resourceTypePermission.setActionIds(actionIds);
	}

	@Override
	public boolean isNew() {
		return _resourceTypePermission.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_resourceTypePermission.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _resourceTypePermission.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_resourceTypePermission.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _resourceTypePermission.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _resourceTypePermission.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_resourceTypePermission.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _resourceTypePermission.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_resourceTypePermission.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_resourceTypePermission.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_resourceTypePermission.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new ResourceTypePermissionWrapper((ResourceTypePermission)_resourceTypePermission.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portal.model.ResourceTypePermission resourceTypePermission) {
		return _resourceTypePermission.compareTo(resourceTypePermission);
	}

	@Override
	public int hashCode() {
		return _resourceTypePermission.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portal.model.ResourceTypePermission> toCacheModel() {
		return _resourceTypePermission.toCacheModel();
	}

	@Override
	public com.liferay.portal.model.ResourceTypePermission toEscapedModel() {
		return new ResourceTypePermissionWrapper(_resourceTypePermission.toEscapedModel());
	}

	@Override
	public com.liferay.portal.model.ResourceTypePermission toUnescapedModel() {
		return new ResourceTypePermissionWrapper(_resourceTypePermission.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _resourceTypePermission.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _resourceTypePermission.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_resourceTypePermission.persist();
	}

	@Override
	public boolean isCompanyScope() {
		return _resourceTypePermission.isCompanyScope();
	}

	@Override
	public boolean isGroupScope() {
		return _resourceTypePermission.isGroupScope();
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public ResourceTypePermission getWrappedResourceTypePermission() {
		return _resourceTypePermission;
	}

	@Override
	public ResourceTypePermission getWrappedModel() {
		return _resourceTypePermission;
	}

	@Override
	public void resetOriginalValues() {
		_resourceTypePermission.resetOriginalValues();
	}

	private ResourceTypePermission _resourceTypePermission;
}