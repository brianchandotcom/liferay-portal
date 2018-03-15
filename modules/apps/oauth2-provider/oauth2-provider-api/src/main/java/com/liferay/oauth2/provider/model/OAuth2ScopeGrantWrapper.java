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

package com.liferay.oauth2.provider.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link OAuth2ScopeGrant}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see OAuth2ScopeGrant
 * @generated
 */
@ProviderType
public class OAuth2ScopeGrantWrapper implements OAuth2ScopeGrant,
	ModelWrapper<OAuth2ScopeGrant> {
	public OAuth2ScopeGrantWrapper(OAuth2ScopeGrant oAuth2ScopeGrant) {
		_oAuth2ScopeGrant = oAuth2ScopeGrant;
	}

	@Override
	public Class<?> getModelClass() {
		return OAuth2ScopeGrant.class;
	}

	@Override
	public String getModelClassName() {
		return OAuth2ScopeGrant.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("applicationName", getApplicationName());
		attributes.put("bundleSymbolicName", getBundleSymbolicName());
		attributes.put("companyId", getCompanyId());
		attributes.put("oAuth2ScopeName", getOAuth2ScopeName());
		attributes.put("oAuth2TokenId", getOAuth2TokenId());
		attributes.put("createDate", getCreateDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String applicationName = (String)attributes.get("applicationName");

		if (applicationName != null) {
			setApplicationName(applicationName);
		}

		String bundleSymbolicName = (String)attributes.get("bundleSymbolicName");

		if (bundleSymbolicName != null) {
			setBundleSymbolicName(bundleSymbolicName);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		String oAuth2ScopeName = (String)attributes.get("oAuth2ScopeName");

		if (oAuth2ScopeName != null) {
			setOAuth2ScopeName(oAuth2ScopeName);
		}

		Long oAuth2TokenId = (Long)attributes.get("oAuth2TokenId");

		if (oAuth2TokenId != null) {
			setOAuth2TokenId(oAuth2TokenId);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new OAuth2ScopeGrantWrapper((OAuth2ScopeGrant)_oAuth2ScopeGrant.clone());
	}

	@Override
	public int compareTo(OAuth2ScopeGrant oAuth2ScopeGrant) {
		return _oAuth2ScopeGrant.compareTo(oAuth2ScopeGrant);
	}

	/**
	* Returns the application name of this o auth2 scope grant.
	*
	* @return the application name of this o auth2 scope grant
	*/
	@Override
	public java.lang.String getApplicationName() {
		return _oAuth2ScopeGrant.getApplicationName();
	}

	/**
	* Returns the bundle symbolic name of this o auth2 scope grant.
	*
	* @return the bundle symbolic name of this o auth2 scope grant
	*/
	@Override
	public java.lang.String getBundleSymbolicName() {
		return _oAuth2ScopeGrant.getBundleSymbolicName();
	}

	/**
	* Returns the company ID of this o auth2 scope grant.
	*
	* @return the company ID of this o auth2 scope grant
	*/
	@Override
	public long getCompanyId() {
		return _oAuth2ScopeGrant.getCompanyId();
	}

	/**
	* Returns the create date of this o auth2 scope grant.
	*
	* @return the create date of this o auth2 scope grant
	*/
	@Override
	public Date getCreateDate() {
		return _oAuth2ScopeGrant.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _oAuth2ScopeGrant.getExpandoBridge();
	}

	/**
	* Returns the o auth2 scope name of this o auth2 scope grant.
	*
	* @return the o auth2 scope name of this o auth2 scope grant
	*/
	@Override
	public java.lang.String getOAuth2ScopeName() {
		return _oAuth2ScopeGrant.getOAuth2ScopeName();
	}

	/**
	* Returns the o auth2 token ID of this o auth2 scope grant.
	*
	* @return the o auth2 token ID of this o auth2 scope grant
	*/
	@Override
	public long getOAuth2TokenId() {
		return _oAuth2ScopeGrant.getOAuth2TokenId();
	}

	/**
	* Returns the primary key of this o auth2 scope grant.
	*
	* @return the primary key of this o auth2 scope grant
	*/
	@Override
	public com.liferay.oauth2.provider.service.persistence.OAuth2ScopeGrantPK getPrimaryKey() {
		return _oAuth2ScopeGrant.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _oAuth2ScopeGrant.getPrimaryKeyObj();
	}

	@Override
	public int hashCode() {
		return _oAuth2ScopeGrant.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _oAuth2ScopeGrant.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _oAuth2ScopeGrant.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _oAuth2ScopeGrant.isNew();
	}

	@Override
	public void persist() {
		_oAuth2ScopeGrant.persist();
	}

	/**
	* Sets the application name of this o auth2 scope grant.
	*
	* @param applicationName the application name of this o auth2 scope grant
	*/
	@Override
	public void setApplicationName(java.lang.String applicationName) {
		_oAuth2ScopeGrant.setApplicationName(applicationName);
	}

	/**
	* Sets the bundle symbolic name of this o auth2 scope grant.
	*
	* @param bundleSymbolicName the bundle symbolic name of this o auth2 scope grant
	*/
	@Override
	public void setBundleSymbolicName(java.lang.String bundleSymbolicName) {
		_oAuth2ScopeGrant.setBundleSymbolicName(bundleSymbolicName);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_oAuth2ScopeGrant.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this o auth2 scope grant.
	*
	* @param companyId the company ID of this o auth2 scope grant
	*/
	@Override
	public void setCompanyId(long companyId) {
		_oAuth2ScopeGrant.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this o auth2 scope grant.
	*
	* @param createDate the create date of this o auth2 scope grant
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_oAuth2ScopeGrant.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_oAuth2ScopeGrant.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_oAuth2ScopeGrant.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_oAuth2ScopeGrant.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public void setNew(boolean n) {
		_oAuth2ScopeGrant.setNew(n);
	}

	/**
	* Sets the o auth2 scope name of this o auth2 scope grant.
	*
	* @param oAuth2ScopeName the o auth2 scope name of this o auth2 scope grant
	*/
	@Override
	public void setOAuth2ScopeName(java.lang.String oAuth2ScopeName) {
		_oAuth2ScopeGrant.setOAuth2ScopeName(oAuth2ScopeName);
	}

	/**
	* Sets the o auth2 token ID of this o auth2 scope grant.
	*
	* @param oAuth2TokenId the o auth2 token ID of this o auth2 scope grant
	*/
	@Override
	public void setOAuth2TokenId(long oAuth2TokenId) {
		_oAuth2ScopeGrant.setOAuth2TokenId(oAuth2TokenId);
	}

	/**
	* Sets the primary key of this o auth2 scope grant.
	*
	* @param primaryKey the primary key of this o auth2 scope grant
	*/
	@Override
	public void setPrimaryKey(
		com.liferay.oauth2.provider.service.persistence.OAuth2ScopeGrantPK primaryKey) {
		_oAuth2ScopeGrant.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_oAuth2ScopeGrant.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<OAuth2ScopeGrant> toCacheModel() {
		return _oAuth2ScopeGrant.toCacheModel();
	}

	@Override
	public OAuth2ScopeGrant toEscapedModel() {
		return new OAuth2ScopeGrantWrapper(_oAuth2ScopeGrant.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _oAuth2ScopeGrant.toString();
	}

	@Override
	public OAuth2ScopeGrant toUnescapedModel() {
		return new OAuth2ScopeGrantWrapper(_oAuth2ScopeGrant.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _oAuth2ScopeGrant.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof OAuth2ScopeGrantWrapper)) {
			return false;
		}

		OAuth2ScopeGrantWrapper oAuth2ScopeGrantWrapper = (OAuth2ScopeGrantWrapper)obj;

		if (Objects.equals(_oAuth2ScopeGrant,
					oAuth2ScopeGrantWrapper._oAuth2ScopeGrant)) {
			return true;
		}

		return false;
	}

	@Override
	public OAuth2ScopeGrant getWrappedModel() {
		return _oAuth2ScopeGrant;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _oAuth2ScopeGrant.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _oAuth2ScopeGrant.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_oAuth2ScopeGrant.resetOriginalValues();
	}

	private final OAuth2ScopeGrant _oAuth2ScopeGrant;
}