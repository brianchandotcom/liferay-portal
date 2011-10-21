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
 * This class is a wrapper for {@link Resource}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       Resource
 * @generated
 */
public class ResourceWrapper implements Resource, ModelWrapper<Resource> {
	public ResourceWrapper(Resource resource) {
		_resource = resource;
	}

	public Class<?> getModelClass() {
		return Resource.class;
	}

	public String getModelClassName() {
		return Resource.class.getName();
	}

	/**
	* Returns the primary key of this resource.
	*
	* @return the primary key of this resource
	*/
	public long getPrimaryKey() {
		return _resource.getPrimaryKey();
	}

	/**
	* Sets the primary key of this resource.
	*
	* @param primaryKey the primary key of this resource
	*/
	public void setPrimaryKey(long primaryKey) {
		_resource.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the resource ID of this resource.
	*
	* @return the resource ID of this resource
	*/
	public long getResourceId() {
		return _resource.getResourceId();
	}

	/**
	* Sets the resource ID of this resource.
	*
	* @param resourceId the resource ID of this resource
	*/
	public void setResourceId(long resourceId) {
		_resource.setResourceId(resourceId);
	}

	/**
	* Returns the code ID of this resource.
	*
	* @return the code ID of this resource
	*/
	public long getCodeId() {
		return _resource.getCodeId();
	}

	/**
	* Sets the code ID of this resource.
	*
	* @param codeId the code ID of this resource
	*/
	public void setCodeId(long codeId) {
		_resource.setCodeId(codeId);
	}

	/**
	* Returns the prim key of this resource.
	*
	* @return the prim key of this resource
	*/
	public java.lang.String getPrimKey() {
		return _resource.getPrimKey();
	}

	/**
	* Sets the prim key of this resource.
	*
	* @param primKey the prim key of this resource
	*/
	public void setPrimKey(java.lang.String primKey) {
		_resource.setPrimKey(primKey);
	}

	public boolean isNew() {
		return _resource.isNew();
	}

	public void setNew(boolean n) {
		_resource.setNew(n);
	}

	public boolean isCachedModel() {
		return _resource.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_resource.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _resource.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _resource.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_resource.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _resource.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_resource.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new ResourceWrapper((Resource)_resource.clone());
	}

	public int compareTo(com.liferay.portal.model.Resource resource) {
		return _resource.compareTo(resource);
	}

	@Override
	public int hashCode() {
		return _resource.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portal.model.Resource> toCacheModel() {
		return _resource.toCacheModel();
	}

	public com.liferay.portal.model.Resource toEscapedModel() {
		return new ResourceWrapper(_resource.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _resource.toString();
	}

	public java.lang.String toXmlString() {
		return _resource.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_resource.persist();
	}

	public long getCompanyId()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _resource.getCompanyId();
	}

	public java.lang.String getName()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _resource.getName();
	}

	public int getScope()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _resource.getScope();
	}

	public void setCompanyId(long companyId) {
		_resource.setCompanyId(companyId);
	}

	public void setName(java.lang.String name) {
		_resource.setName(name);
	}

	public void setScope(int scope) {
		_resource.setScope(scope);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public Resource getWrappedResource() {
		return _resource;
	}

	public Resource getWrappedModel() {
		return _resource;
	}

	public void resetOriginalValues() {
		_resource.resetOriginalValues();
	}

	private Resource _resource;
}