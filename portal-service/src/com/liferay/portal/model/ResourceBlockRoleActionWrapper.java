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
 * This class is a wrapper for {@link ResourceBlockRoleAction}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ResourceBlockRoleAction
 * @generated
 */
public class ResourceBlockRoleActionWrapper implements ResourceBlockRoleAction {
	public ResourceBlockRoleActionWrapper(
		ResourceBlockRoleAction resourceBlockRoleAction) {
		_resourceBlockRoleAction = resourceBlockRoleAction;
	}

	public Class<?> getModelClass() {
		return ResourceBlockRoleAction.class;
	}

	public String getModelClassName() {
		return ResourceBlockRoleAction.class.getName();
	}

	/**
	* Returns the primary key of this resource block role action.
	*
	* @return the primary key of this resource block role action
	*/
	public com.liferay.portal.service.persistence.ResourceBlockRoleActionPK getPrimaryKey() {
		return _resourceBlockRoleAction.getPrimaryKey();
	}

	/**
	* Sets the primary key of this resource block role action.
	*
	* @param primaryKey the primary key of this resource block role action
	*/
	public void setPrimaryKey(
		com.liferay.portal.service.persistence.ResourceBlockRoleActionPK primaryKey) {
		_resourceBlockRoleAction.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the action IDs of this resource block role action.
	*
	* @return the action IDs of this resource block role action
	*/
	public long getActionIds() {
		return _resourceBlockRoleAction.getActionIds();
	}

	/**
	* Sets the action IDs of this resource block role action.
	*
	* @param actionIds the action IDs of this resource block role action
	*/
	public void setActionIds(long actionIds) {
		_resourceBlockRoleAction.setActionIds(actionIds);
	}

	/**
	* Returns the resource block ID of this resource block role action.
	*
	* @return the resource block ID of this resource block role action
	*/
	public long getResourceBlockId() {
		return _resourceBlockRoleAction.getResourceBlockId();
	}

	/**
	* Sets the resource block ID of this resource block role action.
	*
	* @param resourceBlockId the resource block ID of this resource block role action
	*/
	public void setResourceBlockId(long resourceBlockId) {
		_resourceBlockRoleAction.setResourceBlockId(resourceBlockId);
	}

	/**
	* Returns the role ID of this resource block role action.
	*
	* @return the role ID of this resource block role action
	*/
	public long getRoleId() {
		return _resourceBlockRoleAction.getRoleId();
	}

	/**
	* Sets the role ID of this resource block role action.
	*
	* @param roleId the role ID of this resource block role action
	*/
	public void setRoleId(long roleId) {
		_resourceBlockRoleAction.setRoleId(roleId);
	}

	public boolean isNew() {
		return _resourceBlockRoleAction.isNew();
	}

	public void setNew(boolean n) {
		_resourceBlockRoleAction.setNew(n);
	}

	public boolean isCachedModel() {
		return _resourceBlockRoleAction.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_resourceBlockRoleAction.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _resourceBlockRoleAction.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_resourceBlockRoleAction.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _resourceBlockRoleAction.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_resourceBlockRoleAction.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _resourceBlockRoleAction.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_resourceBlockRoleAction.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new ResourceBlockRoleActionWrapper((ResourceBlockRoleAction)_resourceBlockRoleAction.clone());
	}

	public int compareTo(
		com.liferay.portal.model.ResourceBlockRoleAction resourceBlockRoleAction) {
		return _resourceBlockRoleAction.compareTo(resourceBlockRoleAction);
	}

	@Override
	public int hashCode() {
		return _resourceBlockRoleAction.hashCode();
	}

	public com.liferay.portal.model.ResourceBlockRoleAction toEscapedModel() {
		return new ResourceBlockRoleActionWrapper(_resourceBlockRoleAction.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _resourceBlockRoleAction.toString();
	}

	public java.lang.String toXmlString() {
		return _resourceBlockRoleAction.toXmlString();
	}

	public void save()
		throws com.liferay.portal.kernel.exception.SystemException {
		_resourceBlockRoleAction.save();
	}

	public ResourceBlockRoleAction getWrappedResourceBlockRoleAction() {
		return _resourceBlockRoleAction;
	}

	public void resetOriginalValues() {
		_resourceBlockRoleAction.resetOriginalValues();
	}

	private ResourceBlockRoleAction _resourceBlockRoleAction;
}