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
 * This class is a wrapper for {@link VirtualHost}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see VirtualHost
 * @generated
 */
public class VirtualHostWrapper implements VirtualHost,
	ModelWrapper<VirtualHost> {
	public VirtualHostWrapper(VirtualHost virtualHost) {
		_virtualHost = virtualHost;
	}

	@Override
	public Class<?> getModelClass() {
		return VirtualHost.class;
	}

	@Override
	public String getModelClassName() {
		return VirtualHost.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("virtualHostId", getVirtualHostId());
		attributes.put("companyId", getCompanyId());
		attributes.put("layoutSetId", getLayoutSetId());
		attributes.put("hostname", getHostname());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long virtualHostId = (Long)attributes.get("virtualHostId");

		if (virtualHostId != null) {
			setVirtualHostId(virtualHostId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long layoutSetId = (Long)attributes.get("layoutSetId");

		if (layoutSetId != null) {
			setLayoutSetId(layoutSetId);
		}

		String hostname = (String)attributes.get("hostname");

		if (hostname != null) {
			setHostname(hostname);
		}
	}

	/**
	* Returns the primary key of this virtual host.
	*
	* @return the primary key of this virtual host
	*/
	@Override
	public long getPrimaryKey() {
		return _virtualHost.getPrimaryKey();
	}

	/**
	* Sets the primary key of this virtual host.
	*
	* @param primaryKey the primary key of this virtual host
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_virtualHost.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the virtual host ID of this virtual host.
	*
	* @return the virtual host ID of this virtual host
	*/
	@Override
	public long getVirtualHostId() {
		return _virtualHost.getVirtualHostId();
	}

	/**
	* Sets the virtual host ID of this virtual host.
	*
	* @param virtualHostId the virtual host ID of this virtual host
	*/
	@Override
	public void setVirtualHostId(long virtualHostId) {
		_virtualHost.setVirtualHostId(virtualHostId);
	}

	/**
	* Returns the company ID of this virtual host.
	*
	* @return the company ID of this virtual host
	*/
	@Override
	public long getCompanyId() {
		return _virtualHost.getCompanyId();
	}

	/**
	* Sets the company ID of this virtual host.
	*
	* @param companyId the company ID of this virtual host
	*/
	@Override
	public void setCompanyId(long companyId) {
		_virtualHost.setCompanyId(companyId);
	}

	/**
	* Returns the layout set ID of this virtual host.
	*
	* @return the layout set ID of this virtual host
	*/
	@Override
	public long getLayoutSetId() {
		return _virtualHost.getLayoutSetId();
	}

	/**
	* Sets the layout set ID of this virtual host.
	*
	* @param layoutSetId the layout set ID of this virtual host
	*/
	@Override
	public void setLayoutSetId(long layoutSetId) {
		_virtualHost.setLayoutSetId(layoutSetId);
	}

	/**
	* Returns the hostname of this virtual host.
	*
	* @return the hostname of this virtual host
	*/
	@Override
	public java.lang.String getHostname() {
		return _virtualHost.getHostname();
	}

	/**
	* Sets the hostname of this virtual host.
	*
	* @param hostname the hostname of this virtual host
	*/
	@Override
	public void setHostname(java.lang.String hostname) {
		_virtualHost.setHostname(hostname);
	}

	@Override
	public boolean isNew() {
		return _virtualHost.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_virtualHost.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _virtualHost.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_virtualHost.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _virtualHost.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _virtualHost.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_virtualHost.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _virtualHost.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_virtualHost.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_virtualHost.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_virtualHost.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new VirtualHostWrapper((VirtualHost)_virtualHost.clone());
	}

	@Override
	public int compareTo(com.liferay.portal.model.VirtualHost virtualHost) {
		return _virtualHost.compareTo(virtualHost);
	}

	@Override
	public int hashCode() {
		return _virtualHost.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portal.model.VirtualHost> toCacheModel() {
		return _virtualHost.toCacheModel();
	}

	@Override
	public com.liferay.portal.model.VirtualHost toEscapedModel() {
		return new VirtualHostWrapper(_virtualHost.toEscapedModel());
	}

	@Override
	public com.liferay.portal.model.VirtualHost toUnescapedModel() {
		return new VirtualHostWrapper(_virtualHost.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _virtualHost.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _virtualHost.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_virtualHost.persist();
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public VirtualHost getWrappedVirtualHost() {
		return _virtualHost;
	}

	@Override
	public VirtualHost getWrappedModel() {
		return _virtualHost;
	}

	@Override
	public void resetOriginalValues() {
		_virtualHost.resetOriginalValues();
	}

	private VirtualHost _virtualHost;
}