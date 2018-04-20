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
 * This class is a wrapper for {@link SamlSpSession}.
 * </p>
 *
 * @author Mika Koivisto
 * @see SamlSpSession
 * @generated
 */
@ProviderType
public class SamlSpSessionWrapper implements SamlSpSession,
	ModelWrapper<SamlSpSession> {
	public SamlSpSessionWrapper(SamlSpSession samlSpSession) {
		_samlSpSession = samlSpSession;
	}

	@Override
	public Class<?> getModelClass() {
		return SamlSpSession.class;
	}

	@Override
	public String getModelClassName() {
		return SamlSpSession.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("samlSpSessionId", getSamlSpSessionId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("samlSpSessionKey", getSamlSpSessionKey());
		attributes.put("assertionXml", getAssertionXml());
		attributes.put("jSessionId", getJSessionId());
		attributes.put("nameIdFormat", getNameIdFormat());
		attributes.put("nameIdNameQualifier", getNameIdNameQualifier());
		attributes.put("nameIdSPNameQualifier", getNameIdSPNameQualifier());
		attributes.put("nameIdValue", getNameIdValue());
		attributes.put("sessionIndex", getSessionIndex());
		attributes.put("terminated", isTerminated());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long samlSpSessionId = (Long)attributes.get("samlSpSessionId");

		if (samlSpSessionId != null) {
			setSamlSpSessionId(samlSpSessionId);
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

		String samlSpSessionKey = (String)attributes.get("samlSpSessionKey");

		if (samlSpSessionKey != null) {
			setSamlSpSessionKey(samlSpSessionKey);
		}

		String assertionXml = (String)attributes.get("assertionXml");

		if (assertionXml != null) {
			setAssertionXml(assertionXml);
		}

		String jSessionId = (String)attributes.get("jSessionId");

		if (jSessionId != null) {
			setJSessionId(jSessionId);
		}

		String nameIdFormat = (String)attributes.get("nameIdFormat");

		if (nameIdFormat != null) {
			setNameIdFormat(nameIdFormat);
		}

		String nameIdNameQualifier = (String)attributes.get(
				"nameIdNameQualifier");

		if (nameIdNameQualifier != null) {
			setNameIdNameQualifier(nameIdNameQualifier);
		}

		String nameIdSPNameQualifier = (String)attributes.get(
				"nameIdSPNameQualifier");

		if (nameIdSPNameQualifier != null) {
			setNameIdSPNameQualifier(nameIdSPNameQualifier);
		}

		String nameIdValue = (String)attributes.get("nameIdValue");

		if (nameIdValue != null) {
			setNameIdValue(nameIdValue);
		}

		String sessionIndex = (String)attributes.get("sessionIndex");

		if (sessionIndex != null) {
			setSessionIndex(sessionIndex);
		}

		Boolean terminated = (Boolean)attributes.get("terminated");

		if (terminated != null) {
			setTerminated(terminated);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new SamlSpSessionWrapper((SamlSpSession)_samlSpSession.clone());
	}

	@Override
	public int compareTo(SamlSpSession samlSpSession) {
		return _samlSpSession.compareTo(samlSpSession);
	}

	/**
	* Returns the assertion xml of this saml sp session.
	*
	* @return the assertion xml of this saml sp session
	*/
	@Override
	public java.lang.String getAssertionXml() {
		return _samlSpSession.getAssertionXml();
	}

	/**
	* Returns the company ID of this saml sp session.
	*
	* @return the company ID of this saml sp session
	*/
	@Override
	public long getCompanyId() {
		return _samlSpSession.getCompanyId();
	}

	/**
	* Returns the create date of this saml sp session.
	*
	* @return the create date of this saml sp session
	*/
	@Override
	public Date getCreateDate() {
		return _samlSpSession.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _samlSpSession.getExpandoBridge();
	}

	/**
	* Returns the j session ID of this saml sp session.
	*
	* @return the j session ID of this saml sp session
	*/
	@Override
	public java.lang.String getJSessionId() {
		return _samlSpSession.getJSessionId();
	}

	/**
	* Returns the modified date of this saml sp session.
	*
	* @return the modified date of this saml sp session
	*/
	@Override
	public Date getModifiedDate() {
		return _samlSpSession.getModifiedDate();
	}

	/**
	* Returns the name ID format of this saml sp session.
	*
	* @return the name ID format of this saml sp session
	*/
	@Override
	public java.lang.String getNameIdFormat() {
		return _samlSpSession.getNameIdFormat();
	}

	/**
	* Returns the name ID name qualifier of this saml sp session.
	*
	* @return the name ID name qualifier of this saml sp session
	*/
	@Override
	public java.lang.String getNameIdNameQualifier() {
		return _samlSpSession.getNameIdNameQualifier();
	}

	/**
	* Returns the name ID sp name qualifier of this saml sp session.
	*
	* @return the name ID sp name qualifier of this saml sp session
	*/
	@Override
	public java.lang.String getNameIdSPNameQualifier() {
		return _samlSpSession.getNameIdSPNameQualifier();
	}

	/**
	* Returns the name ID value of this saml sp session.
	*
	* @return the name ID value of this saml sp session
	*/
	@Override
	public java.lang.String getNameIdValue() {
		return _samlSpSession.getNameIdValue();
	}

	/**
	* Returns the primary key of this saml sp session.
	*
	* @return the primary key of this saml sp session
	*/
	@Override
	public long getPrimaryKey() {
		return _samlSpSession.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _samlSpSession.getPrimaryKeyObj();
	}

	/**
	* Returns the saml sp session ID of this saml sp session.
	*
	* @return the saml sp session ID of this saml sp session
	*/
	@Override
	public long getSamlSpSessionId() {
		return _samlSpSession.getSamlSpSessionId();
	}

	/**
	* Returns the saml sp session key of this saml sp session.
	*
	* @return the saml sp session key of this saml sp session
	*/
	@Override
	public java.lang.String getSamlSpSessionKey() {
		return _samlSpSession.getSamlSpSessionKey();
	}

	/**
	* Returns the session index of this saml sp session.
	*
	* @return the session index of this saml sp session
	*/
	@Override
	public java.lang.String getSessionIndex() {
		return _samlSpSession.getSessionIndex();
	}

	/**
	* Returns the terminated of this saml sp session.
	*
	* @return the terminated of this saml sp session
	*/
	@Override
	public boolean getTerminated() {
		return _samlSpSession.getTerminated();
	}

	/**
	* Returns the user ID of this saml sp session.
	*
	* @return the user ID of this saml sp session
	*/
	@Override
	public long getUserId() {
		return _samlSpSession.getUserId();
	}

	/**
	* Returns the user name of this saml sp session.
	*
	* @return the user name of this saml sp session
	*/
	@Override
	public java.lang.String getUserName() {
		return _samlSpSession.getUserName();
	}

	/**
	* Returns the user uuid of this saml sp session.
	*
	* @return the user uuid of this saml sp session
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _samlSpSession.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _samlSpSession.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _samlSpSession.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _samlSpSession.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _samlSpSession.isNew();
	}

	/**
	* Returns <code>true</code> if this saml sp session is terminated.
	*
	* @return <code>true</code> if this saml sp session is terminated; <code>false</code> otherwise
	*/
	@Override
	public boolean isTerminated() {
		return _samlSpSession.isTerminated();
	}

	@Override
	public void persist() {
		_samlSpSession.persist();
	}

	/**
	* Sets the assertion xml of this saml sp session.
	*
	* @param assertionXml the assertion xml of this saml sp session
	*/
	@Override
	public void setAssertionXml(java.lang.String assertionXml) {
		_samlSpSession.setAssertionXml(assertionXml);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_samlSpSession.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this saml sp session.
	*
	* @param companyId the company ID of this saml sp session
	*/
	@Override
	public void setCompanyId(long companyId) {
		_samlSpSession.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this saml sp session.
	*
	* @param createDate the create date of this saml sp session
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_samlSpSession.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_samlSpSession.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_samlSpSession.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_samlSpSession.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the j session ID of this saml sp session.
	*
	* @param jSessionId the j session ID of this saml sp session
	*/
	@Override
	public void setJSessionId(java.lang.String jSessionId) {
		_samlSpSession.setJSessionId(jSessionId);
	}

	/**
	* Sets the modified date of this saml sp session.
	*
	* @param modifiedDate the modified date of this saml sp session
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_samlSpSession.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name ID format of this saml sp session.
	*
	* @param nameIdFormat the name ID format of this saml sp session
	*/
	@Override
	public void setNameIdFormat(java.lang.String nameIdFormat) {
		_samlSpSession.setNameIdFormat(nameIdFormat);
	}

	/**
	* Sets the name ID name qualifier of this saml sp session.
	*
	* @param nameIdNameQualifier the name ID name qualifier of this saml sp session
	*/
	@Override
	public void setNameIdNameQualifier(java.lang.String nameIdNameQualifier) {
		_samlSpSession.setNameIdNameQualifier(nameIdNameQualifier);
	}

	/**
	* Sets the name ID sp name qualifier of this saml sp session.
	*
	* @param nameIdSPNameQualifier the name ID sp name qualifier of this saml sp session
	*/
	@Override
	public void setNameIdSPNameQualifier(java.lang.String nameIdSPNameQualifier) {
		_samlSpSession.setNameIdSPNameQualifier(nameIdSPNameQualifier);
	}

	/**
	* Sets the name ID value of this saml sp session.
	*
	* @param nameIdValue the name ID value of this saml sp session
	*/
	@Override
	public void setNameIdValue(java.lang.String nameIdValue) {
		_samlSpSession.setNameIdValue(nameIdValue);
	}

	@Override
	public void setNew(boolean n) {
		_samlSpSession.setNew(n);
	}

	/**
	* Sets the primary key of this saml sp session.
	*
	* @param primaryKey the primary key of this saml sp session
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_samlSpSession.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_samlSpSession.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the saml sp session ID of this saml sp session.
	*
	* @param samlSpSessionId the saml sp session ID of this saml sp session
	*/
	@Override
	public void setSamlSpSessionId(long samlSpSessionId) {
		_samlSpSession.setSamlSpSessionId(samlSpSessionId);
	}

	/**
	* Sets the saml sp session key of this saml sp session.
	*
	* @param samlSpSessionKey the saml sp session key of this saml sp session
	*/
	@Override
	public void setSamlSpSessionKey(java.lang.String samlSpSessionKey) {
		_samlSpSession.setSamlSpSessionKey(samlSpSessionKey);
	}

	/**
	* Sets the session index of this saml sp session.
	*
	* @param sessionIndex the session index of this saml sp session
	*/
	@Override
	public void setSessionIndex(java.lang.String sessionIndex) {
		_samlSpSession.setSessionIndex(sessionIndex);
	}

	/**
	* Sets whether this saml sp session is terminated.
	*
	* @param terminated the terminated of this saml sp session
	*/
	@Override
	public void setTerminated(boolean terminated) {
		_samlSpSession.setTerminated(terminated);
	}

	/**
	* Sets the user ID of this saml sp session.
	*
	* @param userId the user ID of this saml sp session
	*/
	@Override
	public void setUserId(long userId) {
		_samlSpSession.setUserId(userId);
	}

	/**
	* Sets the user name of this saml sp session.
	*
	* @param userName the user name of this saml sp session
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_samlSpSession.setUserName(userName);
	}

	/**
	* Sets the user uuid of this saml sp session.
	*
	* @param userUuid the user uuid of this saml sp session
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_samlSpSession.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<SamlSpSession> toCacheModel() {
		return _samlSpSession.toCacheModel();
	}

	@Override
	public SamlSpSession toEscapedModel() {
		return new SamlSpSessionWrapper(_samlSpSession.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _samlSpSession.toString();
	}

	@Override
	public SamlSpSession toUnescapedModel() {
		return new SamlSpSessionWrapper(_samlSpSession.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _samlSpSession.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SamlSpSessionWrapper)) {
			return false;
		}

		SamlSpSessionWrapper samlSpSessionWrapper = (SamlSpSessionWrapper)obj;

		if (Objects.equals(_samlSpSession, samlSpSessionWrapper._samlSpSession)) {
			return true;
		}

		return false;
	}

	@Override
	public SamlSpSession getWrappedModel() {
		return _samlSpSession;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _samlSpSession.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _samlSpSession.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_samlSpSession.resetOriginalValues();
	}

	private final SamlSpSession _samlSpSession;
}