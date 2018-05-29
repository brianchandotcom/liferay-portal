/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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
 * This class is a wrapper for {@link SamlIdpSpSession}.
 * </p>
 *
 * @author Mika Koivisto
 * @see SamlIdpSpSession
 * @generated
 */
@ProviderType
public class SamlIdpSpSessionWrapper implements SamlIdpSpSession,
	ModelWrapper<SamlIdpSpSession> {
	public SamlIdpSpSessionWrapper(SamlIdpSpSession samlIdpSpSession) {
		_samlIdpSpSession = samlIdpSpSession;
	}

	@Override
	public Class<?> getModelClass() {
		return SamlIdpSpSession.class;
	}

	@Override
	public String getModelClassName() {
		return SamlIdpSpSession.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("samlIdpSpSessionId", getSamlIdpSpSessionId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("samlIdpSsoSessionId", getSamlIdpSsoSessionId());
		attributes.put("samlSpEntityId", getSamlSpEntityId());
		attributes.put("nameIdFormat", getNameIdFormat());
		attributes.put("nameIdValue", getNameIdValue());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long samlIdpSpSessionId = (Long)attributes.get("samlIdpSpSessionId");

		if (samlIdpSpSessionId != null) {
			setSamlIdpSpSessionId(samlIdpSpSessionId);
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

		Long samlIdpSsoSessionId = (Long)attributes.get("samlIdpSsoSessionId");

		if (samlIdpSsoSessionId != null) {
			setSamlIdpSsoSessionId(samlIdpSsoSessionId);
		}

		String samlSpEntityId = (String)attributes.get("samlSpEntityId");

		if (samlSpEntityId != null) {
			setSamlSpEntityId(samlSpEntityId);
		}

		String nameIdFormat = (String)attributes.get("nameIdFormat");

		if (nameIdFormat != null) {
			setNameIdFormat(nameIdFormat);
		}

		String nameIdValue = (String)attributes.get("nameIdValue");

		if (nameIdValue != null) {
			setNameIdValue(nameIdValue);
		}
	}

	@Override
	public Object clone() {
		return new SamlIdpSpSessionWrapper((SamlIdpSpSession)_samlIdpSpSession.clone());
	}

	@Override
	public int compareTo(SamlIdpSpSession samlIdpSpSession) {
		return _samlIdpSpSession.compareTo(samlIdpSpSession);
	}

	/**
	* Returns the company ID of this saml idp sp session.
	*
	* @return the company ID of this saml idp sp session
	*/
	@Override
	public long getCompanyId() {
		return _samlIdpSpSession.getCompanyId();
	}

	/**
	* Returns the create date of this saml idp sp session.
	*
	* @return the create date of this saml idp sp session
	*/
	@Override
	public Date getCreateDate() {
		return _samlIdpSpSession.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _samlIdpSpSession.getExpandoBridge();
	}

	/**
	* Returns the modified date of this saml idp sp session.
	*
	* @return the modified date of this saml idp sp session
	*/
	@Override
	public Date getModifiedDate() {
		return _samlIdpSpSession.getModifiedDate();
	}

	/**
	* Returns the name ID format of this saml idp sp session.
	*
	* @return the name ID format of this saml idp sp session
	*/
	@Override
	public String getNameIdFormat() {
		return _samlIdpSpSession.getNameIdFormat();
	}

	/**
	* Returns the name ID value of this saml idp sp session.
	*
	* @return the name ID value of this saml idp sp session
	*/
	@Override
	public String getNameIdValue() {
		return _samlIdpSpSession.getNameIdValue();
	}

	/**
	* Returns the primary key of this saml idp sp session.
	*
	* @return the primary key of this saml idp sp session
	*/
	@Override
	public long getPrimaryKey() {
		return _samlIdpSpSession.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _samlIdpSpSession.getPrimaryKeyObj();
	}

	/**
	* Returns the saml idp sp session ID of this saml idp sp session.
	*
	* @return the saml idp sp session ID of this saml idp sp session
	*/
	@Override
	public long getSamlIdpSpSessionId() {
		return _samlIdpSpSession.getSamlIdpSpSessionId();
	}

	/**
	* Returns the saml idp sso session ID of this saml idp sp session.
	*
	* @return the saml idp sso session ID of this saml idp sp session
	*/
	@Override
	public long getSamlIdpSsoSessionId() {
		return _samlIdpSpSession.getSamlIdpSsoSessionId();
	}

	/**
	* Returns the saml sp entity ID of this saml idp sp session.
	*
	* @return the saml sp entity ID of this saml idp sp session
	*/
	@Override
	public String getSamlSpEntityId() {
		return _samlIdpSpSession.getSamlSpEntityId();
	}

	/**
	* Returns the user ID of this saml idp sp session.
	*
	* @return the user ID of this saml idp sp session
	*/
	@Override
	public long getUserId() {
		return _samlIdpSpSession.getUserId();
	}

	/**
	* Returns the user name of this saml idp sp session.
	*
	* @return the user name of this saml idp sp session
	*/
	@Override
	public String getUserName() {
		return _samlIdpSpSession.getUserName();
	}

	/**
	* Returns the user uuid of this saml idp sp session.
	*
	* @return the user uuid of this saml idp sp session
	*/
	@Override
	public String getUserUuid() {
		return _samlIdpSpSession.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _samlIdpSpSession.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _samlIdpSpSession.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _samlIdpSpSession.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _samlIdpSpSession.isNew();
	}

	@Override
	public void persist() {
		_samlIdpSpSession.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_samlIdpSpSession.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this saml idp sp session.
	*
	* @param companyId the company ID of this saml idp sp session
	*/
	@Override
	public void setCompanyId(long companyId) {
		_samlIdpSpSession.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this saml idp sp session.
	*
	* @param createDate the create date of this saml idp sp session
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_samlIdpSpSession.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_samlIdpSpSession.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_samlIdpSpSession.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_samlIdpSpSession.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the modified date of this saml idp sp session.
	*
	* @param modifiedDate the modified date of this saml idp sp session
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_samlIdpSpSession.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name ID format of this saml idp sp session.
	*
	* @param nameIdFormat the name ID format of this saml idp sp session
	*/
	@Override
	public void setNameIdFormat(String nameIdFormat) {
		_samlIdpSpSession.setNameIdFormat(nameIdFormat);
	}

	/**
	* Sets the name ID value of this saml idp sp session.
	*
	* @param nameIdValue the name ID value of this saml idp sp session
	*/
	@Override
	public void setNameIdValue(String nameIdValue) {
		_samlIdpSpSession.setNameIdValue(nameIdValue);
	}

	@Override
	public void setNew(boolean n) {
		_samlIdpSpSession.setNew(n);
	}

	/**
	* Sets the primary key of this saml idp sp session.
	*
	* @param primaryKey the primary key of this saml idp sp session
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_samlIdpSpSession.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_samlIdpSpSession.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the saml idp sp session ID of this saml idp sp session.
	*
	* @param samlIdpSpSessionId the saml idp sp session ID of this saml idp sp session
	*/
	@Override
	public void setSamlIdpSpSessionId(long samlIdpSpSessionId) {
		_samlIdpSpSession.setSamlIdpSpSessionId(samlIdpSpSessionId);
	}

	/**
	* Sets the saml idp sso session ID of this saml idp sp session.
	*
	* @param samlIdpSsoSessionId the saml idp sso session ID of this saml idp sp session
	*/
	@Override
	public void setSamlIdpSsoSessionId(long samlIdpSsoSessionId) {
		_samlIdpSpSession.setSamlIdpSsoSessionId(samlIdpSsoSessionId);
	}

	/**
	* Sets the saml sp entity ID of this saml idp sp session.
	*
	* @param samlSpEntityId the saml sp entity ID of this saml idp sp session
	*/
	@Override
	public void setSamlSpEntityId(String samlSpEntityId) {
		_samlIdpSpSession.setSamlSpEntityId(samlSpEntityId);
	}

	/**
	* Sets the user ID of this saml idp sp session.
	*
	* @param userId the user ID of this saml idp sp session
	*/
	@Override
	public void setUserId(long userId) {
		_samlIdpSpSession.setUserId(userId);
	}

	/**
	* Sets the user name of this saml idp sp session.
	*
	* @param userName the user name of this saml idp sp session
	*/
	@Override
	public void setUserName(String userName) {
		_samlIdpSpSession.setUserName(userName);
	}

	/**
	* Sets the user uuid of this saml idp sp session.
	*
	* @param userUuid the user uuid of this saml idp sp session
	*/
	@Override
	public void setUserUuid(String userUuid) {
		_samlIdpSpSession.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<SamlIdpSpSession> toCacheModel() {
		return _samlIdpSpSession.toCacheModel();
	}

	@Override
	public SamlIdpSpSession toEscapedModel() {
		return new SamlIdpSpSessionWrapper(_samlIdpSpSession.toEscapedModel());
	}

	@Override
	public String toString() {
		return _samlIdpSpSession.toString();
	}

	@Override
	public SamlIdpSpSession toUnescapedModel() {
		return new SamlIdpSpSessionWrapper(_samlIdpSpSession.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _samlIdpSpSession.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SamlIdpSpSessionWrapper)) {
			return false;
		}

		SamlIdpSpSessionWrapper samlIdpSpSessionWrapper = (SamlIdpSpSessionWrapper)obj;

		if (Objects.equals(_samlIdpSpSession,
					samlIdpSpSessionWrapper._samlIdpSpSession)) {
			return true;
		}

		return false;
	}

	@Override
	public SamlIdpSpSession getWrappedModel() {
		return _samlIdpSpSession;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _samlIdpSpSession.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _samlIdpSpSession.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_samlIdpSpSession.resetOriginalValues();
	}

	private final SamlIdpSpSession _samlIdpSpSession;
}