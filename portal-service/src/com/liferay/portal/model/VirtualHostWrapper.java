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
 * This class is a wrapper for {@link VirtualHost}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see VirtualHost
 * @generated
 */
@ProviderType
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

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("virtualHostId", getVirtualHostId());
		attributes.put("companyId", getCompanyId());
		attributes.put("layoutSetId", getLayoutSetId());
		attributes.put("hostname", getHostname());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

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

	@Override
	public java.lang.Object clone() {
		return new VirtualHostWrapper((VirtualHost)_virtualHost.clone());
	}

	@Override
	public int compareTo(com.liferay.portal.model.VirtualHost virtualHost) {
		return _virtualHost.compareTo(virtualHost);
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

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _virtualHost.getExpandoBridge();
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
	* Returns the layout set ID of this virtual host.
	*
	* @return the layout set ID of this virtual host
	*/
	@Override
	public long getLayoutSetId() {
		return _virtualHost.getLayoutSetId();
	}

	/**
	* Returns the mvcc version of this virtual host.
	*
	* @return the mvcc version of this virtual host
	*/
	@Override
	public long getMvccVersion() {
		return _virtualHost.getMvccVersion();
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

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _virtualHost.getPrimaryKeyObj();
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

	@Override
	public int hashCode() {
		return _virtualHost.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _virtualHost.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _virtualHost.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _virtualHost.isNew();
	}

	@Override
	public void persist() {
		_virtualHost.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_virtualHost.setCachedModel(cachedModel);
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

	@Override
	public void setExpandoBridgeAttributes(BaseModel<?> baseModel) {
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

	/**
	* Sets the hostname of this virtual host.
	*
	* @param hostname the hostname of this virtual host
	*/
	@Override
	public void setHostname(java.lang.String hostname) {
		_virtualHost.setHostname(hostname);
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
	* Sets the mvcc version of this virtual host.
	*
	* @param mvccVersion the mvcc version of this virtual host
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		_virtualHost.setMvccVersion(mvccVersion);
	}

	@Override
	public void setNew(boolean n) {
		_virtualHost.setNew(n);
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

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_virtualHost.setPrimaryKeyObj(primaryKeyObj);
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

	@Override
	public CacheModel<com.liferay.portal.model.VirtualHost> toCacheModel() {
		return _virtualHost.toCacheModel();
	}

	@Override
	public com.liferay.portal.model.VirtualHost toEscapedModel() {
		return new VirtualHostWrapper(_virtualHost.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _virtualHost.toString();
	}

	@Override
	public com.liferay.portal.model.VirtualHost toUnescapedModel() {
		return new VirtualHostWrapper(_virtualHost.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _virtualHost.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof VirtualHostWrapper)) {
			return false;
		}

		VirtualHostWrapper virtualHostWrapper = (VirtualHostWrapper)obj;

		if (Validator.equals(_virtualHost, virtualHostWrapper._virtualHost)) {
			return true;
		}

		return false;
	}

	@Override
	public VirtualHost getWrappedModel() {
		return _virtualHost;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _virtualHost.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _virtualHost.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_virtualHost.resetOriginalValues();
	}

	private final VirtualHost _virtualHost;
}