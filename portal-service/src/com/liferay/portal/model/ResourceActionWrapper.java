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
 * This class is a wrapper for {@link ResourceAction}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ResourceAction
 * @generated
 */
@ProviderType
public class ResourceActionWrapper implements ResourceAction,
	ModelWrapper<ResourceAction> {
	public ResourceActionWrapper(ResourceAction resourceAction) {
		_resourceAction = resourceAction;
	}

	@Override
	public Class<?> getModelClass() {
		return ResourceAction.class;
	}

	@Override
	public String getModelClassName() {
		return ResourceAction.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("resourceActionId", getResourceActionId());
		attributes.put("name", getName());
		attributes.put("actionId", getActionId());
		attributes.put("bitwiseValue", getBitwiseValue());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long resourceActionId = (Long)attributes.get("resourceActionId");

		if (resourceActionId != null) {
			setResourceActionId(resourceActionId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String actionId = (String)attributes.get("actionId");

		if (actionId != null) {
			setActionId(actionId);
		}

		Long bitwiseValue = (Long)attributes.get("bitwiseValue");

		if (bitwiseValue != null) {
			setBitwiseValue(bitwiseValue);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new ResourceActionWrapper((ResourceAction)_resourceAction.clone());
	}

	@Override
	public int compareTo(com.liferay.portal.model.ResourceAction resourceAction) {
		return _resourceAction.compareTo(resourceAction);
	}

	/**
	* Returns the action ID of this resource action.
	*
	* @return the action ID of this resource action
	*/
	@Override
	public java.lang.String getActionId() {
		return _resourceAction.getActionId();
	}

	/**
	* Returns the bitwise value of this resource action.
	*
	* @return the bitwise value of this resource action
	*/
	@Override
	public long getBitwiseValue() {
		return _resourceAction.getBitwiseValue();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _resourceAction.getExpandoBridge();
	}

	/**
	* Returns the mvcc version of this resource action.
	*
	* @return the mvcc version of this resource action
	*/
	@Override
	public long getMvccVersion() {
		return _resourceAction.getMvccVersion();
	}

	/**
	* Returns the name of this resource action.
	*
	* @return the name of this resource action
	*/
	@Override
	public java.lang.String getName() {
		return _resourceAction.getName();
	}

	/**
	* Returns the primary key of this resource action.
	*
	* @return the primary key of this resource action
	*/
	@Override
	public long getPrimaryKey() {
		return _resourceAction.getPrimaryKey();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _resourceAction.getPrimaryKeyObj();
	}

	/**
	* Returns the resource action ID of this resource action.
	*
	* @return the resource action ID of this resource action
	*/
	@Override
	public long getResourceActionId() {
		return _resourceAction.getResourceActionId();
	}

	@Override
	public int hashCode() {
		return _resourceAction.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _resourceAction.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _resourceAction.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _resourceAction.isNew();
	}

	@Override
	public void persist() {
		_resourceAction.persist();
	}

	/**
	* Sets the action ID of this resource action.
	*
	* @param actionId the action ID of this resource action
	*/
	@Override
	public void setActionId(java.lang.String actionId) {
		_resourceAction.setActionId(actionId);
	}

	/**
	* Sets the bitwise value of this resource action.
	*
	* @param bitwiseValue the bitwise value of this resource action
	*/
	@Override
	public void setBitwiseValue(long bitwiseValue) {
		_resourceAction.setBitwiseValue(bitwiseValue);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_resourceAction.setCachedModel(cachedModel);
	}

	@Override
	public void setExpandoBridgeAttributes(BaseModel<?> baseModel) {
		_resourceAction.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_resourceAction.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_resourceAction.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the mvcc version of this resource action.
	*
	* @param mvccVersion the mvcc version of this resource action
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		_resourceAction.setMvccVersion(mvccVersion);
	}

	/**
	* Sets the name of this resource action.
	*
	* @param name the name of this resource action
	*/
	@Override
	public void setName(java.lang.String name) {
		_resourceAction.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_resourceAction.setNew(n);
	}

	/**
	* Sets the primary key of this resource action.
	*
	* @param primaryKey the primary key of this resource action
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_resourceAction.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_resourceAction.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the resource action ID of this resource action.
	*
	* @param resourceActionId the resource action ID of this resource action
	*/
	@Override
	public void setResourceActionId(long resourceActionId) {
		_resourceAction.setResourceActionId(resourceActionId);
	}

	@Override
	public CacheModel<com.liferay.portal.model.ResourceAction> toCacheModel() {
		return _resourceAction.toCacheModel();
	}

	@Override
	public com.liferay.portal.model.ResourceAction toEscapedModel() {
		return new ResourceActionWrapper(_resourceAction.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _resourceAction.toString();
	}

	@Override
	public com.liferay.portal.model.ResourceAction toUnescapedModel() {
		return new ResourceActionWrapper(_resourceAction.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _resourceAction.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ResourceActionWrapper)) {
			return false;
		}

		ResourceActionWrapper resourceActionWrapper = (ResourceActionWrapper)obj;

		if (Validator.equals(_resourceAction,
					resourceActionWrapper._resourceAction)) {
			return true;
		}

		return false;
	}

	@Override
	public ResourceAction getWrappedModel() {
		return _resourceAction;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _resourceAction.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _resourceAction.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_resourceAction.resetOriginalValues();
	}

	private final ResourceAction _resourceAction;
}