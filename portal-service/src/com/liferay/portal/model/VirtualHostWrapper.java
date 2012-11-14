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
 * This class is a wrapper for {@link VirtualHost}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       VirtualHost
 * @generated
 */
public class VirtualHostWrapper implements VirtualHost,
	ModelWrapper<VirtualHost> {
	public VirtualHostWrapper(VirtualHost virtualHost) {
		_virtualHost = virtualHost;
	}

	public Class<?> getModelClass() {
		return VirtualHost.class;
	}

	public String getModelClassName() {
		return VirtualHost.class.getName();
	}

	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("virtualHostId", getVirtualHostId());
		attributes.put("companyId", getCompanyId());
		attributes.put("layoutSetId", getLayoutSetId());
		attributes.put("hostname", getHostname());

		return attributes;
	}

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
	public long getPrimaryKey() {
		return _virtualHost.getPrimaryKey();
	}

	/**
	* Sets the primary key of this virtual host.
	*
	* @param primaryKey the primary key of this virtual host
	*/
	public void setPrimaryKey(long primaryKey) {
		_virtualHost.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the virtual host ID of this virtual host.
	*
	* @return the virtual host ID of this virtual host
	*/
	public long getVirtualHostId() {
		return _virtualHost.getVirtualHostId();
	}

	/**
	* Sets the virtual host ID of this virtual host.
	*
	* @param virtualHostId the virtual host ID of this virtual host
	*/
	public void setVirtualHostId(long virtualHostId) {
		_virtualHost.setVirtualHostId(virtualHostId);
	}

	/**
	* Returns the company ID of this virtual host.
	*
	* @return the company ID of this virtual host
	*/
	public long getCompanyId() {
		return _virtualHost.getCompanyId();
	}

	/**
	* Sets the company ID of this virtual host.
	*
	* @param companyId the company ID of this virtual host
	*/
	public void setCompanyId(long companyId) {
		_virtualHost.setCompanyId(companyId);
	}

	/**
	* Returns the layout set ID of this virtual host.
	*
	* @return the layout set ID of this virtual host
	*/
	public long getLayoutSetId() {
		return _virtualHost.getLayoutSetId();
	}

	/**
	* Sets the layout set ID of this virtual host.
	*
	* @param layoutSetId the layout set ID of this virtual host
	*/
	public void setLayoutSetId(long layoutSetId) {
		_virtualHost.setLayoutSetId(layoutSetId);
	}

	/**
	* Returns the hostname of this virtual host.
	*
	* @return the hostname of this virtual host
	*/
	public java.lang.String getHostname() {
		return _virtualHost.getHostname();
	}

	/**
	* Sets the hostname of this virtual host.
	*
	* @param hostname the hostname of this virtual host
	*/
	public void setHostname(java.lang.String hostname) {
		_virtualHost.setHostname(hostname);
	}

	public boolean isNew() {
		return _virtualHost.isNew();
	}

	public void setNew(boolean n) {
		_virtualHost.setNew(n);
	}

	public boolean isCachedModel() {
		return _virtualHost.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_virtualHost.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _virtualHost.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _virtualHost.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_virtualHost.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _virtualHost.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_virtualHost.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new VirtualHostWrapper((VirtualHost)_virtualHost.clone());
	}

	public int compareTo(com.liferay.portal.model.VirtualHost virtualHost) {
		return _virtualHost.compareTo(virtualHost);
	}

	@Override
	public int hashCode() {
		return _virtualHost.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portal.model.VirtualHost> toCacheModel() {
		return _virtualHost.toCacheModel();
	}

	public com.liferay.portal.model.VirtualHost toEscapedModel() {
		return new VirtualHostWrapper(_virtualHost.toEscapedModel());
	}

	public com.liferay.portal.model.VirtualHost toUnescapedModel() {
		return new VirtualHostWrapper(_virtualHost.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _virtualHost.toString();
	}

	public java.lang.String toXmlString() {
		return _virtualHost.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_virtualHost.persist();
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public VirtualHost getWrappedVirtualHost() {
		return _virtualHost;
	}

	public VirtualHost getWrappedModel() {
		return _virtualHost;
	}

	public void resetOriginalValues() {
		_virtualHost.resetOriginalValues();
	}

	private VirtualHost _virtualHost;
}