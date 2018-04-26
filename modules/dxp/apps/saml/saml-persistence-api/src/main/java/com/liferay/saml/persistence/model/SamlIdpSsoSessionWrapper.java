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

package com.liferay.saml.persistence.model;

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
 * This class is a wrapper for {@link SamlIdpSsoSession}.
 * </p>
 *
 * @author Mika Koivisto
 * @see SamlIdpSsoSession
 * @generated
 */
@ProviderType
public class SamlIdpSsoSessionWrapper implements SamlIdpSsoSession,
	ModelWrapper<SamlIdpSsoSession> {
	public SamlIdpSsoSessionWrapper(SamlIdpSsoSession samlIdpSsoSession) {
		_samlIdpSsoSession = samlIdpSsoSession;
	}

	@Override
	public Class<?> getModelClass() {
		return SamlIdpSsoSession.class;
	}

	@Override
	public String getModelClassName() {
		return SamlIdpSsoSession.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("samlIdpSsoSessionId", getSamlIdpSsoSessionId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("samlIdpSsoSessionKey", getSamlIdpSsoSessionKey());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long samlIdpSsoSessionId = (Long)attributes.get("samlIdpSsoSessionId");

		if (samlIdpSsoSessionId != null) {
			setSamlIdpSsoSessionId(samlIdpSsoSessionId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		String samlIdpSsoSessionKey = (String)attributes.get(
				"samlIdpSsoSessionKey");

		if (samlIdpSsoSessionKey != null) {
			setSamlIdpSsoSessionKey(samlIdpSsoSessionKey);
		}
	}

	@Override
	public Object clone() {
		return new SamlIdpSsoSessionWrapper((SamlIdpSsoSession)_samlIdpSsoSession.clone());
	}

	@Override
	public int compareTo(SamlIdpSsoSession samlIdpSsoSession) {
		return _samlIdpSsoSession.compareTo(samlIdpSsoSession);
	}

	/**
	* Returns the company ID of this saml idp sso session.
	*
	* @return the company ID of this saml idp sso session
	*/
	@Override
	public long getCompanyId() {
		return _samlIdpSsoSession.getCompanyId();
	}

	/**
	* Returns the create date of this saml idp sso session.
	*
	* @return the create date of this saml idp sso session
	*/
	@Override
	public Date getCreateDate() {
		return _samlIdpSsoSession.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _samlIdpSsoSession.getExpandoBridge();
	}

	/**
	* Returns the modified date of this saml idp sso session.
	*
	* @return the modified date of this saml idp sso session
	*/
	@Override
	public Date getModifiedDate() {
		return _samlIdpSsoSession.getModifiedDate();
	}

	/**
	* Returns the primary key of this saml idp sso session.
	*
	* @return the primary key of this saml idp sso session
	*/
	@Override
	public long getPrimaryKey() {
		return _samlIdpSsoSession.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _samlIdpSsoSession.getPrimaryKeyObj();
	}

	/**
	* Returns the saml idp sso session ID of this saml idp sso session.
	*
	* @return the saml idp sso session ID of this saml idp sso session
	*/
	@Override
	public long getSamlIdpSsoSessionId() {
		return _samlIdpSsoSession.getSamlIdpSsoSessionId();
	}

	/**
	* Returns the saml idp sso session key of this saml idp sso session.
	*
	* @return the saml idp sso session key of this saml idp sso session
	*/
	@Override
	public String getSamlIdpSsoSessionKey() {
		return _samlIdpSsoSession.getSamlIdpSsoSessionKey();
	}

	/**
	* Returns the user ID of this saml idp sso session.
	*
	* @return the user ID of this saml idp sso session
	*/
	@Override
	public long getUserId() {
		return _samlIdpSsoSession.getUserId();
	}

	/**
	* Returns the user name of this saml idp sso session.
	*
	* @return the user name of this saml idp sso session
	*/
	@Override
	public String getUserName() {
		return _samlIdpSsoSession.getUserName();
	}

	/**
	* Returns the user uuid of this saml idp sso session.
	*
	* @return the user uuid of this saml idp sso session
	*/
	@Override
	public String getUserUuid() {
		return _samlIdpSsoSession.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _samlIdpSsoSession.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _samlIdpSsoSession.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _samlIdpSsoSession.isEscapedModel();
	}

	@Override
	public boolean isExpired() {
		return _samlIdpSsoSession.isExpired();
	}

	@Override
	public boolean isNew() {
		return _samlIdpSsoSession.isNew();
	}

	@Override
	public void persist() {
		_samlIdpSsoSession.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_samlIdpSsoSession.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this saml idp sso session.
	*
	* @param companyId the company ID of this saml idp sso session
	*/
	@Override
	public void setCompanyId(long companyId) {
		_samlIdpSsoSession.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this saml idp sso session.
	*
	* @param createDate the create date of this saml idp sso session
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_samlIdpSsoSession.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_samlIdpSsoSession.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_samlIdpSsoSession.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_samlIdpSsoSession.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the modified date of this saml idp sso session.
	*
	* @param modifiedDate the modified date of this saml idp sso session
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_samlIdpSsoSession.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_samlIdpSsoSession.setNew(n);
	}

	/**
	* Sets the primary key of this saml idp sso session.
	*
	* @param primaryKey the primary key of this saml idp sso session
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_samlIdpSsoSession.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_samlIdpSsoSession.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the saml idp sso session ID of this saml idp sso session.
	*
	* @param samlIdpSsoSessionId the saml idp sso session ID of this saml idp sso session
	*/
	@Override
	public void setSamlIdpSsoSessionId(long samlIdpSsoSessionId) {
		_samlIdpSsoSession.setSamlIdpSsoSessionId(samlIdpSsoSessionId);
	}

	/**
	* Sets the saml idp sso session key of this saml idp sso session.
	*
	* @param samlIdpSsoSessionKey the saml idp sso session key of this saml idp sso session
	*/
	@Override
	public void setSamlIdpSsoSessionKey(String samlIdpSsoSessionKey) {
		_samlIdpSsoSession.setSamlIdpSsoSessionKey(samlIdpSsoSessionKey);
	}

	/**
	* Sets the user ID of this saml idp sso session.
	*
	* @param userId the user ID of this saml idp sso session
	*/
	@Override
	public void setUserId(long userId) {
		_samlIdpSsoSession.setUserId(userId);
	}

	/**
	* Sets the user name of this saml idp sso session.
	*
	* @param userName the user name of this saml idp sso session
	*/
	@Override
	public void setUserName(String userName) {
		_samlIdpSsoSession.setUserName(userName);
	}

	/**
	* Sets the user uuid of this saml idp sso session.
	*
	* @param userUuid the user uuid of this saml idp sso session
	*/
	@Override
	public void setUserUuid(String userUuid) {
		_samlIdpSsoSession.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<SamlIdpSsoSession> toCacheModel() {
		return _samlIdpSsoSession.toCacheModel();
	}

	@Override
	public SamlIdpSsoSession toEscapedModel() {
		return new SamlIdpSsoSessionWrapper(_samlIdpSsoSession.toEscapedModel());
	}

	@Override
	public String toString() {
		return _samlIdpSsoSession.toString();
	}

	@Override
	public SamlIdpSsoSession toUnescapedModel() {
		return new SamlIdpSsoSessionWrapper(_samlIdpSsoSession.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _samlIdpSsoSession.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SamlIdpSsoSessionWrapper)) {
			return false;
		}

		SamlIdpSsoSessionWrapper samlIdpSsoSessionWrapper = (SamlIdpSsoSessionWrapper)obj;

		if (Objects.equals(_samlIdpSsoSession,
					samlIdpSsoSessionWrapper._samlIdpSsoSession)) {
			return true;
		}

		return false;
	}

	@Override
	public SamlIdpSsoSession getWrappedModel() {
		return _samlIdpSsoSession;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _samlIdpSsoSession.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _samlIdpSsoSession.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_samlIdpSsoSession.resetOriginalValues();
	}

	private final SamlIdpSsoSession _samlIdpSsoSession;
}