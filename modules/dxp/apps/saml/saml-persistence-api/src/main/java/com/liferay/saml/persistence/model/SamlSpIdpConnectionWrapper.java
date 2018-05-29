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
 * This class is a wrapper for {@link SamlSpIdpConnection}.
 * </p>
 *
 * @author Mika Koivisto
 * @see SamlSpIdpConnection
 * @generated
 */
@ProviderType
public class SamlSpIdpConnectionWrapper implements SamlSpIdpConnection,
	ModelWrapper<SamlSpIdpConnection> {
	public SamlSpIdpConnectionWrapper(SamlSpIdpConnection samlSpIdpConnection) {
		_samlSpIdpConnection = samlSpIdpConnection;
	}

	@Override
	public Class<?> getModelClass() {
		return SamlSpIdpConnection.class;
	}

	@Override
	public String getModelClassName() {
		return SamlSpIdpConnection.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("samlSpIdpConnectionId", getSamlSpIdpConnectionId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("samlIdpEntityId", getSamlIdpEntityId());
		attributes.put("assertionSignatureRequired",
			isAssertionSignatureRequired());
		attributes.put("clockSkew", getClockSkew());
		attributes.put("enabled", isEnabled());
		attributes.put("forceAuthn", isForceAuthn());
		attributes.put("ldapImportEnabled", isLdapImportEnabled());
		attributes.put("metadataUrl", getMetadataUrl());
		attributes.put("metadataXml", getMetadataXml());
		attributes.put("metadataUpdatedDate", getMetadataUpdatedDate());
		attributes.put("name", getName());
		attributes.put("nameIdFormat", getNameIdFormat());
		attributes.put("signAuthnRequest", isSignAuthnRequest());
		attributes.put("userAttributeMappings", getUserAttributeMappings());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long samlSpIdpConnectionId = (Long)attributes.get(
				"samlSpIdpConnectionId");

		if (samlSpIdpConnectionId != null) {
			setSamlSpIdpConnectionId(samlSpIdpConnectionId);
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

		String samlIdpEntityId = (String)attributes.get("samlIdpEntityId");

		if (samlIdpEntityId != null) {
			setSamlIdpEntityId(samlIdpEntityId);
		}

		Boolean assertionSignatureRequired = (Boolean)attributes.get(
				"assertionSignatureRequired");

		if (assertionSignatureRequired != null) {
			setAssertionSignatureRequired(assertionSignatureRequired);
		}

		Long clockSkew = (Long)attributes.get("clockSkew");

		if (clockSkew != null) {
			setClockSkew(clockSkew);
		}

		Boolean enabled = (Boolean)attributes.get("enabled");

		if (enabled != null) {
			setEnabled(enabled);
		}

		Boolean forceAuthn = (Boolean)attributes.get("forceAuthn");

		if (forceAuthn != null) {
			setForceAuthn(forceAuthn);
		}

		Boolean ldapImportEnabled = (Boolean)attributes.get("ldapImportEnabled");

		if (ldapImportEnabled != null) {
			setLdapImportEnabled(ldapImportEnabled);
		}

		String metadataUrl = (String)attributes.get("metadataUrl");

		if (metadataUrl != null) {
			setMetadataUrl(metadataUrl);
		}

		String metadataXml = (String)attributes.get("metadataXml");

		if (metadataXml != null) {
			setMetadataXml(metadataXml);
		}

		Date metadataUpdatedDate = (Date)attributes.get("metadataUpdatedDate");

		if (metadataUpdatedDate != null) {
			setMetadataUpdatedDate(metadataUpdatedDate);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String nameIdFormat = (String)attributes.get("nameIdFormat");

		if (nameIdFormat != null) {
			setNameIdFormat(nameIdFormat);
		}

		Boolean signAuthnRequest = (Boolean)attributes.get("signAuthnRequest");

		if (signAuthnRequest != null) {
			setSignAuthnRequest(signAuthnRequest);
		}

		String userAttributeMappings = (String)attributes.get(
				"userAttributeMappings");

		if (userAttributeMappings != null) {
			setUserAttributeMappings(userAttributeMappings);
		}
	}

	@Override
	public Object clone() {
		return new SamlSpIdpConnectionWrapper((SamlSpIdpConnection)_samlSpIdpConnection.clone());
	}

	@Override
	public int compareTo(SamlSpIdpConnection samlSpIdpConnection) {
		return _samlSpIdpConnection.compareTo(samlSpIdpConnection);
	}

	/**
	* Returns the assertion signature required of this saml sp idp connection.
	*
	* @return the assertion signature required of this saml sp idp connection
	*/
	@Override
	public boolean getAssertionSignatureRequired() {
		return _samlSpIdpConnection.getAssertionSignatureRequired();
	}

	/**
	* Returns the clock skew of this saml sp idp connection.
	*
	* @return the clock skew of this saml sp idp connection
	*/
	@Override
	public long getClockSkew() {
		return _samlSpIdpConnection.getClockSkew();
	}

	/**
	* Returns the company ID of this saml sp idp connection.
	*
	* @return the company ID of this saml sp idp connection
	*/
	@Override
	public long getCompanyId() {
		return _samlSpIdpConnection.getCompanyId();
	}

	/**
	* Returns the create date of this saml sp idp connection.
	*
	* @return the create date of this saml sp idp connection
	*/
	@Override
	public Date getCreateDate() {
		return _samlSpIdpConnection.getCreateDate();
	}

	/**
	* Returns the enabled of this saml sp idp connection.
	*
	* @return the enabled of this saml sp idp connection
	*/
	@Override
	public boolean getEnabled() {
		return _samlSpIdpConnection.getEnabled();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _samlSpIdpConnection.getExpandoBridge();
	}

	/**
	* Returns the force authn of this saml sp idp connection.
	*
	* @return the force authn of this saml sp idp connection
	*/
	@Override
	public boolean getForceAuthn() {
		return _samlSpIdpConnection.getForceAuthn();
	}

	/**
	* Returns the ldap import enabled of this saml sp idp connection.
	*
	* @return the ldap import enabled of this saml sp idp connection
	*/
	@Override
	public boolean getLdapImportEnabled() {
		return _samlSpIdpConnection.getLdapImportEnabled();
	}

	/**
	* Returns the metadata updated date of this saml sp idp connection.
	*
	* @return the metadata updated date of this saml sp idp connection
	*/
	@Override
	public Date getMetadataUpdatedDate() {
		return _samlSpIdpConnection.getMetadataUpdatedDate();
	}

	/**
	* Returns the metadata url of this saml sp idp connection.
	*
	* @return the metadata url of this saml sp idp connection
	*/
	@Override
	public String getMetadataUrl() {
		return _samlSpIdpConnection.getMetadataUrl();
	}

	/**
	* Returns the metadata xml of this saml sp idp connection.
	*
	* @return the metadata xml of this saml sp idp connection
	*/
	@Override
	public String getMetadataXml() {
		return _samlSpIdpConnection.getMetadataXml();
	}

	/**
	* Returns the modified date of this saml sp idp connection.
	*
	* @return the modified date of this saml sp idp connection
	*/
	@Override
	public Date getModifiedDate() {
		return _samlSpIdpConnection.getModifiedDate();
	}

	/**
	* Returns the name of this saml sp idp connection.
	*
	* @return the name of this saml sp idp connection
	*/
	@Override
	public String getName() {
		return _samlSpIdpConnection.getName();
	}

	/**
	* Returns the name ID format of this saml sp idp connection.
	*
	* @return the name ID format of this saml sp idp connection
	*/
	@Override
	public String getNameIdFormat() {
		return _samlSpIdpConnection.getNameIdFormat();
	}

	/**
	* Returns the primary key of this saml sp idp connection.
	*
	* @return the primary key of this saml sp idp connection
	*/
	@Override
	public long getPrimaryKey() {
		return _samlSpIdpConnection.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _samlSpIdpConnection.getPrimaryKeyObj();
	}

	/**
	* Returns the saml idp entity ID of this saml sp idp connection.
	*
	* @return the saml idp entity ID of this saml sp idp connection
	*/
	@Override
	public String getSamlIdpEntityId() {
		return _samlSpIdpConnection.getSamlIdpEntityId();
	}

	/**
	* Returns the saml sp idp connection ID of this saml sp idp connection.
	*
	* @return the saml sp idp connection ID of this saml sp idp connection
	*/
	@Override
	public long getSamlSpIdpConnectionId() {
		return _samlSpIdpConnection.getSamlSpIdpConnectionId();
	}

	/**
	* Returns the sign authn request of this saml sp idp connection.
	*
	* @return the sign authn request of this saml sp idp connection
	*/
	@Override
	public boolean getSignAuthnRequest() {
		return _samlSpIdpConnection.getSignAuthnRequest();
	}

	/**
	* Returns the user attribute mappings of this saml sp idp connection.
	*
	* @return the user attribute mappings of this saml sp idp connection
	*/
	@Override
	public String getUserAttributeMappings() {
		return _samlSpIdpConnection.getUserAttributeMappings();
	}

	/**
	* Returns the user ID of this saml sp idp connection.
	*
	* @return the user ID of this saml sp idp connection
	*/
	@Override
	public long getUserId() {
		return _samlSpIdpConnection.getUserId();
	}

	/**
	* Returns the user name of this saml sp idp connection.
	*
	* @return the user name of this saml sp idp connection
	*/
	@Override
	public String getUserName() {
		return _samlSpIdpConnection.getUserName();
	}

	/**
	* Returns the user uuid of this saml sp idp connection.
	*
	* @return the user uuid of this saml sp idp connection
	*/
	@Override
	public String getUserUuid() {
		return _samlSpIdpConnection.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _samlSpIdpConnection.hashCode();
	}

	/**
	* Returns <code>true</code> if this saml sp idp connection is assertion signature required.
	*
	* @return <code>true</code> if this saml sp idp connection is assertion signature required; <code>false</code> otherwise
	*/
	@Override
	public boolean isAssertionSignatureRequired() {
		return _samlSpIdpConnection.isAssertionSignatureRequired();
	}

	@Override
	public boolean isCachedModel() {
		return _samlSpIdpConnection.isCachedModel();
	}

	/**
	* Returns <code>true</code> if this saml sp idp connection is enabled.
	*
	* @return <code>true</code> if this saml sp idp connection is enabled; <code>false</code> otherwise
	*/
	@Override
	public boolean isEnabled() {
		return _samlSpIdpConnection.isEnabled();
	}

	@Override
	public boolean isEscapedModel() {
		return _samlSpIdpConnection.isEscapedModel();
	}

	/**
	* Returns <code>true</code> if this saml sp idp connection is force authn.
	*
	* @return <code>true</code> if this saml sp idp connection is force authn; <code>false</code> otherwise
	*/
	@Override
	public boolean isForceAuthn() {
		return _samlSpIdpConnection.isForceAuthn();
	}

	/**
	* Returns <code>true</code> if this saml sp idp connection is ldap import enabled.
	*
	* @return <code>true</code> if this saml sp idp connection is ldap import enabled; <code>false</code> otherwise
	*/
	@Override
	public boolean isLdapImportEnabled() {
		return _samlSpIdpConnection.isLdapImportEnabled();
	}

	@Override
	public boolean isNew() {
		return _samlSpIdpConnection.isNew();
	}

	/**
	* Returns <code>true</code> if this saml sp idp connection is sign authn request.
	*
	* @return <code>true</code> if this saml sp idp connection is sign authn request; <code>false</code> otherwise
	*/
	@Override
	public boolean isSignAuthnRequest() {
		return _samlSpIdpConnection.isSignAuthnRequest();
	}

	@Override
	public void persist() {
		_samlSpIdpConnection.persist();
	}

	/**
	* Sets whether this saml sp idp connection is assertion signature required.
	*
	* @param assertionSignatureRequired the assertion signature required of this saml sp idp connection
	*/
	@Override
	public void setAssertionSignatureRequired(
		boolean assertionSignatureRequired) {
		_samlSpIdpConnection.setAssertionSignatureRequired(assertionSignatureRequired);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_samlSpIdpConnection.setCachedModel(cachedModel);
	}

	/**
	* Sets the clock skew of this saml sp idp connection.
	*
	* @param clockSkew the clock skew of this saml sp idp connection
	*/
	@Override
	public void setClockSkew(long clockSkew) {
		_samlSpIdpConnection.setClockSkew(clockSkew);
	}

	/**
	* Sets the company ID of this saml sp idp connection.
	*
	* @param companyId the company ID of this saml sp idp connection
	*/
	@Override
	public void setCompanyId(long companyId) {
		_samlSpIdpConnection.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this saml sp idp connection.
	*
	* @param createDate the create date of this saml sp idp connection
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_samlSpIdpConnection.setCreateDate(createDate);
	}

	/**
	* Sets whether this saml sp idp connection is enabled.
	*
	* @param enabled the enabled of this saml sp idp connection
	*/
	@Override
	public void setEnabled(boolean enabled) {
		_samlSpIdpConnection.setEnabled(enabled);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_samlSpIdpConnection.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_samlSpIdpConnection.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_samlSpIdpConnection.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets whether this saml sp idp connection is force authn.
	*
	* @param forceAuthn the force authn of this saml sp idp connection
	*/
	@Override
	public void setForceAuthn(boolean forceAuthn) {
		_samlSpIdpConnection.setForceAuthn(forceAuthn);
	}

	/**
	* Sets whether this saml sp idp connection is ldap import enabled.
	*
	* @param ldapImportEnabled the ldap import enabled of this saml sp idp connection
	*/
	@Override
	public void setLdapImportEnabled(boolean ldapImportEnabled) {
		_samlSpIdpConnection.setLdapImportEnabled(ldapImportEnabled);
	}

	/**
	* Sets the metadata updated date of this saml sp idp connection.
	*
	* @param metadataUpdatedDate the metadata updated date of this saml sp idp connection
	*/
	@Override
	public void setMetadataUpdatedDate(Date metadataUpdatedDate) {
		_samlSpIdpConnection.setMetadataUpdatedDate(metadataUpdatedDate);
	}

	/**
	* Sets the metadata url of this saml sp idp connection.
	*
	* @param metadataUrl the metadata url of this saml sp idp connection
	*/
	@Override
	public void setMetadataUrl(String metadataUrl) {
		_samlSpIdpConnection.setMetadataUrl(metadataUrl);
	}

	/**
	* Sets the metadata xml of this saml sp idp connection.
	*
	* @param metadataXml the metadata xml of this saml sp idp connection
	*/
	@Override
	public void setMetadataXml(String metadataXml) {
		_samlSpIdpConnection.setMetadataXml(metadataXml);
	}

	/**
	* Sets the modified date of this saml sp idp connection.
	*
	* @param modifiedDate the modified date of this saml sp idp connection
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_samlSpIdpConnection.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this saml sp idp connection.
	*
	* @param name the name of this saml sp idp connection
	*/
	@Override
	public void setName(String name) {
		_samlSpIdpConnection.setName(name);
	}

	/**
	* Sets the name ID format of this saml sp idp connection.
	*
	* @param nameIdFormat the name ID format of this saml sp idp connection
	*/
	@Override
	public void setNameIdFormat(String nameIdFormat) {
		_samlSpIdpConnection.setNameIdFormat(nameIdFormat);
	}

	@Override
	public void setNew(boolean n) {
		_samlSpIdpConnection.setNew(n);
	}

	/**
	* Sets the primary key of this saml sp idp connection.
	*
	* @param primaryKey the primary key of this saml sp idp connection
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_samlSpIdpConnection.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_samlSpIdpConnection.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the saml idp entity ID of this saml sp idp connection.
	*
	* @param samlIdpEntityId the saml idp entity ID of this saml sp idp connection
	*/
	@Override
	public void setSamlIdpEntityId(String samlIdpEntityId) {
		_samlSpIdpConnection.setSamlIdpEntityId(samlIdpEntityId);
	}

	/**
	* Sets the saml sp idp connection ID of this saml sp idp connection.
	*
	* @param samlSpIdpConnectionId the saml sp idp connection ID of this saml sp idp connection
	*/
	@Override
	public void setSamlSpIdpConnectionId(long samlSpIdpConnectionId) {
		_samlSpIdpConnection.setSamlSpIdpConnectionId(samlSpIdpConnectionId);
	}

	/**
	* Sets whether this saml sp idp connection is sign authn request.
	*
	* @param signAuthnRequest the sign authn request of this saml sp idp connection
	*/
	@Override
	public void setSignAuthnRequest(boolean signAuthnRequest) {
		_samlSpIdpConnection.setSignAuthnRequest(signAuthnRequest);
	}

	/**
	* Sets the user attribute mappings of this saml sp idp connection.
	*
	* @param userAttributeMappings the user attribute mappings of this saml sp idp connection
	*/
	@Override
	public void setUserAttributeMappings(String userAttributeMappings) {
		_samlSpIdpConnection.setUserAttributeMappings(userAttributeMappings);
	}

	/**
	* Sets the user ID of this saml sp idp connection.
	*
	* @param userId the user ID of this saml sp idp connection
	*/
	@Override
	public void setUserId(long userId) {
		_samlSpIdpConnection.setUserId(userId);
	}

	/**
	* Sets the user name of this saml sp idp connection.
	*
	* @param userName the user name of this saml sp idp connection
	*/
	@Override
	public void setUserName(String userName) {
		_samlSpIdpConnection.setUserName(userName);
	}

	/**
	* Sets the user uuid of this saml sp idp connection.
	*
	* @param userUuid the user uuid of this saml sp idp connection
	*/
	@Override
	public void setUserUuid(String userUuid) {
		_samlSpIdpConnection.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<SamlSpIdpConnection> toCacheModel() {
		return _samlSpIdpConnection.toCacheModel();
	}

	@Override
	public SamlSpIdpConnection toEscapedModel() {
		return new SamlSpIdpConnectionWrapper(_samlSpIdpConnection.toEscapedModel());
	}

	@Override
	public String toString() {
		return _samlSpIdpConnection.toString();
	}

	@Override
	public SamlSpIdpConnection toUnescapedModel() {
		return new SamlSpIdpConnectionWrapper(_samlSpIdpConnection.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _samlSpIdpConnection.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SamlSpIdpConnectionWrapper)) {
			return false;
		}

		SamlSpIdpConnectionWrapper samlSpIdpConnectionWrapper = (SamlSpIdpConnectionWrapper)obj;

		if (Objects.equals(_samlSpIdpConnection,
					samlSpIdpConnectionWrapper._samlSpIdpConnection)) {
			return true;
		}

		return false;
	}

	@Override
	public SamlSpIdpConnection getWrappedModel() {
		return _samlSpIdpConnection;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _samlSpIdpConnection.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _samlSpIdpConnection.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_samlSpIdpConnection.resetOriginalValues();
	}

	private final SamlSpIdpConnection _samlSpIdpConnection;
}