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

package com.liferay.portal.kernel.model;

import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link ResourcePermission}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ResourcePermission
 * @generated
 */
public class ResourcePermissionWrapper
	extends BaseModelWrapper<ResourcePermission>
	implements ModelWrapper<ResourcePermission>, ResourcePermission {

	public ResourcePermissionWrapper(ResourcePermission resourcePermission) {
		super(resourcePermission);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("resourcePermissionId", getResourcePermissionId());
		attributes.put("companyId", getCompanyId());
		attributes.put("name", getName());
		attributes.put("scope", getScope());
		attributes.put("primKey", getPrimKey());
		attributes.put("primKeyId", getPrimKeyId());
		attributes.put("roleId", getRoleId());
		attributes.put("ownerId", getOwnerId());
		attributes.put("actionIds", getActionIds());
		attributes.put("viewActionId", isViewActionId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long resourcePermissionId = (Long)attributes.get(
			"resourcePermissionId");

		if (resourcePermissionId != null) {
			setResourcePermissionId(resourcePermissionId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		Integer scope = (Integer)attributes.get("scope");

		if (scope != null) {
			setScope(scope);
		}

		String primKey = (String)attributes.get("primKey");

		if (primKey != null) {
			setPrimKey(primKey);
		}

		Long primKeyId = (Long)attributes.get("primKeyId");

		if (primKeyId != null) {
			setPrimKeyId(primKeyId);
		}

		Long roleId = (Long)attributes.get("roleId");

		if (roleId != null) {
			setRoleId(roleId);
		}

		Long ownerId = (Long)attributes.get("ownerId");

		if (ownerId != null) {
			setOwnerId(ownerId);
		}

		Long actionIds = (Long)attributes.get("actionIds");

		if (actionIds != null) {
			setActionIds(actionIds);
		}

		Boolean viewActionId = (Boolean)attributes.get("viewActionId");

		if (viewActionId != null) {
			setViewActionId(viewActionId);
		}
	}

	@Override
	public void addResourceAction(String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		model.addResourceAction(actionId);
	}

	/**
	 * Returns the action IDs of this resource permission.
	 *
	 * @return the action IDs of this resource permission
	 */
	@Override
	public long getActionIds() {
		return model.getActionIds();
	}

	/**
	 * Returns the company ID of this resource permission.
	 *
	 * @return the company ID of this resource permission
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the mvcc version of this resource permission.
	 *
	 * @return the mvcc version of this resource permission
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this resource permission.
	 *
	 * @return the name of this resource permission
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the owner ID of this resource permission.
	 *
	 * @return the owner ID of this resource permission
	 */
	@Override
	public long getOwnerId() {
		return model.getOwnerId();
	}

	/**
	 * Returns the primary key of this resource permission.
	 *
	 * @return the primary key of this resource permission
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the prim key of this resource permission.
	 *
	 * @return the prim key of this resource permission
	 */
	@Override
	public String getPrimKey() {
		return model.getPrimKey();
	}

	/**
	 * Returns the prim key ID of this resource permission.
	 *
	 * @return the prim key ID of this resource permission
	 */
	@Override
	public long getPrimKeyId() {
		return model.getPrimKeyId();
	}

	/**
	 * Returns the resource permission ID of this resource permission.
	 *
	 * @return the resource permission ID of this resource permission
	 */
	@Override
	public long getResourcePermissionId() {
		return model.getResourcePermissionId();
	}

	/**
	 * Returns the role ID of this resource permission.
	 *
	 * @return the role ID of this resource permission
	 */
	@Override
	public long getRoleId() {
		return model.getRoleId();
	}

	/**
	 * Returns the scope of this resource permission.
	 *
	 * @return the scope of this resource permission
	 */
	@Override
	public int getScope() {
		return model.getScope();
	}

	/**
	 * Returns the view action ID of this resource permission.
	 *
	 * @return the view action ID of this resource permission
	 */
	@Override
	public boolean getViewActionId() {
		return model.getViewActionId();
	}

	@Override
	public boolean hasAction(ResourceAction resourceAction) {
		return model.hasAction(resourceAction);
	}

	@Override
	public boolean hasActionId(String actionId) {
		return model.hasActionId(actionId);
	}

	/**
	 * Returns <code>true</code> if this resource permission is view action ID.
	 *
	 * @return <code>true</code> if this resource permission is view action ID; <code>false</code> otherwise
	 */
	@Override
	public boolean isViewActionId() {
		return model.isViewActionId();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a resource permission model instance should use the <code>ResourcePermission</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	@Override
	public void removeResourceAction(String actionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		model.removeResourceAction(actionId);
	}

	/**
	 * Sets the action IDs of this resource permission.
	 *
	 * @param actionIds the action IDs of this resource permission
	 */
	@Override
	public void setActionIds(long actionIds) {
		model.setActionIds(actionIds);
	}

	/**
	 * Sets the company ID of this resource permission.
	 *
	 * @param companyId the company ID of this resource permission
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the mvcc version of this resource permission.
	 *
	 * @param mvccVersion the mvcc version of this resource permission
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this resource permission.
	 *
	 * @param name the name of this resource permission
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the owner ID of this resource permission.
	 *
	 * @param ownerId the owner ID of this resource permission
	 */
	@Override
	public void setOwnerId(long ownerId) {
		model.setOwnerId(ownerId);
	}

	/**
	 * Sets the primary key of this resource permission.
	 *
	 * @param primaryKey the primary key of this resource permission
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the prim key of this resource permission.
	 *
	 * @param primKey the prim key of this resource permission
	 */
	@Override
	public void setPrimKey(String primKey) {
		model.setPrimKey(primKey);
	}

	/**
	 * Sets the prim key ID of this resource permission.
	 *
	 * @param primKeyId the prim key ID of this resource permission
	 */
	@Override
	public void setPrimKeyId(long primKeyId) {
		model.setPrimKeyId(primKeyId);
	}

	/**
	 * Sets the resource permission ID of this resource permission.
	 *
	 * @param resourcePermissionId the resource permission ID of this resource permission
	 */
	@Override
	public void setResourcePermissionId(long resourcePermissionId) {
		model.setResourcePermissionId(resourcePermissionId);
	}

	/**
	 * Sets the role ID of this resource permission.
	 *
	 * @param roleId the role ID of this resource permission
	 */
	@Override
	public void setRoleId(long roleId) {
		model.setRoleId(roleId);
	}

	/**
	 * Sets the scope of this resource permission.
	 *
	 * @param scope the scope of this resource permission
	 */
	@Override
	public void setScope(int scope) {
		model.setScope(scope);
	}

	/**
	 * Sets whether this resource permission is view action ID.
	 *
	 * @param viewActionId the view action ID of this resource permission
	 */
	@Override
	public void setViewActionId(boolean viewActionId) {
		model.setViewActionId(viewActionId);
	}

	@Override
	protected ResourcePermissionWrapper wrap(
		ResourcePermission resourcePermission) {

		return new ResourcePermissionWrapper(resourcePermission);
	}

}