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
 * This class is a wrapper for {@link SamlIdpSpConnection}.
 * </p>
 *
 * @author Mika Koivisto
 * @see SamlIdpSpConnection
 * @generated
 */
@ProviderType
public class SamlIdpSpConnectionWrapper implements SamlIdpSpConnection,
	ModelWrapper<SamlIdpSpConnection> {
	public SamlIdpSpConnectionWrapper(SamlIdpSpConnection samlIdpSpConnection) {
		_samlIdpSpConnection = samlIdpSpConnection;
	}

	@Override
	public Class<?> getModelClass() {
		return SamlIdpSpConnection.class;
	}

	@Override
	public String getModelClassName() {
		return SamlIdpSpConnection.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("samlIdpSpConnectionId", getSamlIdpSpConnectionId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("samlSpEntityId", getSamlSpEntityId());
		attributes.put("assertionLifetime", getAssertionLifetime());
		attributes.put("attributeNames", getAttributeNames());
		attributes.put("attributesEnabled", isAttributesEnabled());
		attributes.put("attributesNamespaceEnabled",
			isAttributesNamespaceEnabled());
		attributes.put("enabled", isEnabled());
		attributes.put("metadataUrl", getMetadataUrl());
		attributes.put("metadataXml", getMetadataXml());
		attributes.put("metadataUpdatedDate", getMetadataUpdatedDate());
		attributes.put("name", getName());
		attributes.put("nameIdAttribute", getNameIdAttribute());
		attributes.put("nameIdFormat", getNameIdFormat());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long samlIdpSpConnectionId = (Long)attributes.get(
				"samlIdpSpConnectionId");

		if (samlIdpSpConnectionId != null) {
			setSamlIdpSpConnectionId(samlIdpSpConnectionId);
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

		String samlSpEntityId = (String)attributes.get("samlSpEntityId");

		if (samlSpEntityId != null) {
			setSamlSpEntityId(samlSpEntityId);
		}

		Integer assertionLifetime = (Integer)attributes.get("assertionLifetime");

		if (assertionLifetime != null) {
			setAssertionLifetime(assertionLifetime);
		}

		String attributeNames = (String)attributes.get("attributeNames");

		if (attributeNames != null) {
			setAttributeNames(attributeNames);
		}

		Boolean attributesEnabled = (Boolean)attributes.get("attributesEnabled");

		if (attributesEnabled != null) {
			setAttributesEnabled(attributesEnabled);
		}

		Boolean attributesNamespaceEnabled = (Boolean)attributes.get(
				"attributesNamespaceEnabled");

		if (attributesNamespaceEnabled != null) {
			setAttributesNamespaceEnabled(attributesNamespaceEnabled);
		}

		Boolean enabled = (Boolean)attributes.get("enabled");

		if (enabled != null) {
			setEnabled(enabled);
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

		String nameIdAttribute = (String)attributes.get("nameIdAttribute");

		if (nameIdAttribute != null) {
			setNameIdAttribute(nameIdAttribute);
		}

		String nameIdFormat = (String)attributes.get("nameIdFormat");

		if (nameIdFormat != null) {
			setNameIdFormat(nameIdFormat);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new SamlIdpSpConnectionWrapper((SamlIdpSpConnection)_samlIdpSpConnection.clone());
	}

	@Override
	public int compareTo(SamlIdpSpConnection samlIdpSpConnection) {
		return _samlIdpSpConnection.compareTo(samlIdpSpConnection);
	}

	/**
	* Returns the assertion lifetime of this saml idp sp connection.
	*
	* @return the assertion lifetime of this saml idp sp connection
	*/
	@Override
	public int getAssertionLifetime() {
		return _samlIdpSpConnection.getAssertionLifetime();
	}

	/**
	* Returns the attribute names of this saml idp sp connection.
	*
	* @return the attribute names of this saml idp sp connection
	*/
	@Override
	public java.lang.String getAttributeNames() {
		return _samlIdpSpConnection.getAttributeNames();
	}

	/**
	* Returns the attributes enabled of this saml idp sp connection.
	*
	* @return the attributes enabled of this saml idp sp connection
	*/
	@Override
	public boolean getAttributesEnabled() {
		return _samlIdpSpConnection.getAttributesEnabled();
	}

	/**
	* Returns the attributes namespace enabled of this saml idp sp connection.
	*
	* @return the attributes namespace enabled of this saml idp sp connection
	*/
	@Override
	public boolean getAttributesNamespaceEnabled() {
		return _samlIdpSpConnection.getAttributesNamespaceEnabled();
	}

	/**
	* Returns the company ID of this saml idp sp connection.
	*
	* @return the company ID of this saml idp sp connection
	*/
	@Override
	public long getCompanyId() {
		return _samlIdpSpConnection.getCompanyId();
	}

	/**
	* Returns the create date of this saml idp sp connection.
	*
	* @return the create date of this saml idp sp connection
	*/
	@Override
	public Date getCreateDate() {
		return _samlIdpSpConnection.getCreateDate();
	}

	/**
	* Returns the enabled of this saml idp sp connection.
	*
	* @return the enabled of this saml idp sp connection
	*/
	@Override
	public boolean getEnabled() {
		return _samlIdpSpConnection.getEnabled();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _samlIdpSpConnection.getExpandoBridge();
	}

	/**
	* Returns the metadata updated date of this saml idp sp connection.
	*
	* @return the metadata updated date of this saml idp sp connection
	*/
	@Override
	public Date getMetadataUpdatedDate() {
		return _samlIdpSpConnection.getMetadataUpdatedDate();
	}

	/**
	* Returns the metadata url of this saml idp sp connection.
	*
	* @return the metadata url of this saml idp sp connection
	*/
	@Override
	public java.lang.String getMetadataUrl() {
		return _samlIdpSpConnection.getMetadataUrl();
	}

	/**
	* Returns the metadata xml of this saml idp sp connection.
	*
	* @return the metadata xml of this saml idp sp connection
	*/
	@Override
	public java.lang.String getMetadataXml() {
		return _samlIdpSpConnection.getMetadataXml();
	}

	/**
	* Returns the modified date of this saml idp sp connection.
	*
	* @return the modified date of this saml idp sp connection
	*/
	@Override
	public Date getModifiedDate() {
		return _samlIdpSpConnection.getModifiedDate();
	}

	/**
	* Returns the name of this saml idp sp connection.
	*
	* @return the name of this saml idp sp connection
	*/
	@Override
	public java.lang.String getName() {
		return _samlIdpSpConnection.getName();
	}

	/**
	* Returns the name ID attribute of this saml idp sp connection.
	*
	* @return the name ID attribute of this saml idp sp connection
	*/
	@Override
	public java.lang.String getNameIdAttribute() {
		return _samlIdpSpConnection.getNameIdAttribute();
	}

	/**
	* Returns the name ID format of this saml idp sp connection.
	*
	* @return the name ID format of this saml idp sp connection
	*/
	@Override
	public java.lang.String getNameIdFormat() {
		return _samlIdpSpConnection.getNameIdFormat();
	}

	/**
	* Returns the primary key of this saml idp sp connection.
	*
	* @return the primary key of this saml idp sp connection
	*/
	@Override
	public long getPrimaryKey() {
		return _samlIdpSpConnection.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _samlIdpSpConnection.getPrimaryKeyObj();
	}

	/**
	* Returns the saml idp sp connection ID of this saml idp sp connection.
	*
	* @return the saml idp sp connection ID of this saml idp sp connection
	*/
	@Override
	public long getSamlIdpSpConnectionId() {
		return _samlIdpSpConnection.getSamlIdpSpConnectionId();
	}

	/**
	* Returns the saml sp entity ID of this saml idp sp connection.
	*
	* @return the saml sp entity ID of this saml idp sp connection
	*/
	@Override
	public java.lang.String getSamlSpEntityId() {
		return _samlIdpSpConnection.getSamlSpEntityId();
	}

	/**
	* Returns the user ID of this saml idp sp connection.
	*
	* @return the user ID of this saml idp sp connection
	*/
	@Override
	public long getUserId() {
		return _samlIdpSpConnection.getUserId();
	}

	/**
	* Returns the user name of this saml idp sp connection.
	*
	* @return the user name of this saml idp sp connection
	*/
	@Override
	public java.lang.String getUserName() {
		return _samlIdpSpConnection.getUserName();
	}

	/**
	* Returns the user uuid of this saml idp sp connection.
	*
	* @return the user uuid of this saml idp sp connection
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _samlIdpSpConnection.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _samlIdpSpConnection.hashCode();
	}

	/**
	* Returns <code>true</code> if this saml idp sp connection is attributes enabled.
	*
	* @return <code>true</code> if this saml idp sp connection is attributes enabled; <code>false</code> otherwise
	*/
	@Override
	public boolean isAttributesEnabled() {
		return _samlIdpSpConnection.isAttributesEnabled();
	}

	/**
	* Returns <code>true</code> if this saml idp sp connection is attributes namespace enabled.
	*
	* @return <code>true</code> if this saml idp sp connection is attributes namespace enabled; <code>false</code> otherwise
	*/
	@Override
	public boolean isAttributesNamespaceEnabled() {
		return _samlIdpSpConnection.isAttributesNamespaceEnabled();
	}

	@Override
	public boolean isCachedModel() {
		return _samlIdpSpConnection.isCachedModel();
	}

	/**
	* Returns <code>true</code> if this saml idp sp connection is enabled.
	*
	* @return <code>true</code> if this saml idp sp connection is enabled; <code>false</code> otherwise
	*/
	@Override
	public boolean isEnabled() {
		return _samlIdpSpConnection.isEnabled();
	}

	@Override
	public boolean isEscapedModel() {
		return _samlIdpSpConnection.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _samlIdpSpConnection.isNew();
	}

	@Override
	public void persist() {
		_samlIdpSpConnection.persist();
	}

	/**
	* Sets the assertion lifetime of this saml idp sp connection.
	*
	* @param assertionLifetime the assertion lifetime of this saml idp sp connection
	*/
	@Override
	public void setAssertionLifetime(int assertionLifetime) {
		_samlIdpSpConnection.setAssertionLifetime(assertionLifetime);
	}

	/**
	* Sets the attribute names of this saml idp sp connection.
	*
	* @param attributeNames the attribute names of this saml idp sp connection
	*/
	@Override
	public void setAttributeNames(java.lang.String attributeNames) {
		_samlIdpSpConnection.setAttributeNames(attributeNames);
	}

	/**
	* Sets whether this saml idp sp connection is attributes enabled.
	*
	* @param attributesEnabled the attributes enabled of this saml idp sp connection
	*/
	@Override
	public void setAttributesEnabled(boolean attributesEnabled) {
		_samlIdpSpConnection.setAttributesEnabled(attributesEnabled);
	}

	/**
	* Sets whether this saml idp sp connection is attributes namespace enabled.
	*
	* @param attributesNamespaceEnabled the attributes namespace enabled of this saml idp sp connection
	*/
	@Override
	public void setAttributesNamespaceEnabled(
		boolean attributesNamespaceEnabled) {
		_samlIdpSpConnection.setAttributesNamespaceEnabled(attributesNamespaceEnabled);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_samlIdpSpConnection.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this saml idp sp connection.
	*
	* @param companyId the company ID of this saml idp sp connection
	*/
	@Override
	public void setCompanyId(long companyId) {
		_samlIdpSpConnection.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this saml idp sp connection.
	*
	* @param createDate the create date of this saml idp sp connection
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_samlIdpSpConnection.setCreateDate(createDate);
	}

	/**
	* Sets whether this saml idp sp connection is enabled.
	*
	* @param enabled the enabled of this saml idp sp connection
	*/
	@Override
	public void setEnabled(boolean enabled) {
		_samlIdpSpConnection.setEnabled(enabled);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_samlIdpSpConnection.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_samlIdpSpConnection.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_samlIdpSpConnection.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the metadata updated date of this saml idp sp connection.
	*
	* @param metadataUpdatedDate the metadata updated date of this saml idp sp connection
	*/
	@Override
	public void setMetadataUpdatedDate(Date metadataUpdatedDate) {
		_samlIdpSpConnection.setMetadataUpdatedDate(metadataUpdatedDate);
	}

	/**
	* Sets the metadata url of this saml idp sp connection.
	*
	* @param metadataUrl the metadata url of this saml idp sp connection
	*/
	@Override
	public void setMetadataUrl(java.lang.String metadataUrl) {
		_samlIdpSpConnection.setMetadataUrl(metadataUrl);
	}

	/**
	* Sets the metadata xml of this saml idp sp connection.
	*
	* @param metadataXml the metadata xml of this saml idp sp connection
	*/
	@Override
	public void setMetadataXml(java.lang.String metadataXml) {
		_samlIdpSpConnection.setMetadataXml(metadataXml);
	}

	/**
	* Sets the modified date of this saml idp sp connection.
	*
	* @param modifiedDate the modified date of this saml idp sp connection
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_samlIdpSpConnection.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this saml idp sp connection.
	*
	* @param name the name of this saml idp sp connection
	*/
	@Override
	public void setName(java.lang.String name) {
		_samlIdpSpConnection.setName(name);
	}

	/**
	* Sets the name ID attribute of this saml idp sp connection.
	*
	* @param nameIdAttribute the name ID attribute of this saml idp sp connection
	*/
	@Override
	public void setNameIdAttribute(java.lang.String nameIdAttribute) {
		_samlIdpSpConnection.setNameIdAttribute(nameIdAttribute);
	}

	/**
	* Sets the name ID format of this saml idp sp connection.
	*
	* @param nameIdFormat the name ID format of this saml idp sp connection
	*/
	@Override
	public void setNameIdFormat(java.lang.String nameIdFormat) {
		_samlIdpSpConnection.setNameIdFormat(nameIdFormat);
	}

	@Override
	public void setNew(boolean n) {
		_samlIdpSpConnection.setNew(n);
	}

	/**
	* Sets the primary key of this saml idp sp connection.
	*
	* @param primaryKey the primary key of this saml idp sp connection
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_samlIdpSpConnection.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_samlIdpSpConnection.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the saml idp sp connection ID of this saml idp sp connection.
	*
	* @param samlIdpSpConnectionId the saml idp sp connection ID of this saml idp sp connection
	*/
	@Override
	public void setSamlIdpSpConnectionId(long samlIdpSpConnectionId) {
		_samlIdpSpConnection.setSamlIdpSpConnectionId(samlIdpSpConnectionId);
	}

	/**
	* Sets the saml sp entity ID of this saml idp sp connection.
	*
	* @param samlSpEntityId the saml sp entity ID of this saml idp sp connection
	*/
	@Override
	public void setSamlSpEntityId(java.lang.String samlSpEntityId) {
		_samlIdpSpConnection.setSamlSpEntityId(samlSpEntityId);
	}

	/**
	* Sets the user ID of this saml idp sp connection.
	*
	* @param userId the user ID of this saml idp sp connection
	*/
	@Override
	public void setUserId(long userId) {
		_samlIdpSpConnection.setUserId(userId);
	}

	/**
	* Sets the user name of this saml idp sp connection.
	*
	* @param userName the user name of this saml idp sp connection
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_samlIdpSpConnection.setUserName(userName);
	}

	/**
	* Sets the user uuid of this saml idp sp connection.
	*
	* @param userUuid the user uuid of this saml idp sp connection
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_samlIdpSpConnection.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<SamlIdpSpConnection> toCacheModel() {
		return _samlIdpSpConnection.toCacheModel();
	}

	@Override
	public SamlIdpSpConnection toEscapedModel() {
		return new SamlIdpSpConnectionWrapper(_samlIdpSpConnection.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _samlIdpSpConnection.toString();
	}

	@Override
	public SamlIdpSpConnection toUnescapedModel() {
		return new SamlIdpSpConnectionWrapper(_samlIdpSpConnection.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _samlIdpSpConnection.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SamlIdpSpConnectionWrapper)) {
			return false;
		}

		SamlIdpSpConnectionWrapper samlIdpSpConnectionWrapper = (SamlIdpSpConnectionWrapper)obj;

		if (Objects.equals(_samlIdpSpConnection,
					samlIdpSpConnectionWrapper._samlIdpSpConnection)) {
			return true;
		}

		return false;
	}

	@Override
	public SamlIdpSpConnection getWrappedModel() {
		return _samlIdpSpConnection;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _samlIdpSpConnection.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _samlIdpSpConnection.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_samlIdpSpConnection.resetOriginalValues();
	}

	private final SamlIdpSpConnection _samlIdpSpConnection;
}