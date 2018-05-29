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
 * This class is a wrapper for {@link SamlSpMessage}.
 * </p>
 *
 * @author Mika Koivisto
 * @see SamlSpMessage
 * @generated
 */
@ProviderType
public class SamlSpMessageWrapper implements SamlSpMessage,
	ModelWrapper<SamlSpMessage> {
	public SamlSpMessageWrapper(SamlSpMessage samlSpMessage) {
		_samlSpMessage = samlSpMessage;
	}

	@Override
	public Class<?> getModelClass() {
		return SamlSpMessage.class;
	}

	@Override
	public String getModelClassName() {
		return SamlSpMessage.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("samlSpMessageId", getSamlSpMessageId());
		attributes.put("companyId", getCompanyId());
		attributes.put("createDate", getCreateDate());
		attributes.put("samlIdpEntityId", getSamlIdpEntityId());
		attributes.put("samlIdpResponseKey", getSamlIdpResponseKey());
		attributes.put("expirationDate", getExpirationDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long samlSpMessageId = (Long)attributes.get("samlSpMessageId");

		if (samlSpMessageId != null) {
			setSamlSpMessageId(samlSpMessageId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		String samlIdpEntityId = (String)attributes.get("samlIdpEntityId");

		if (samlIdpEntityId != null) {
			setSamlIdpEntityId(samlIdpEntityId);
		}

		String samlIdpResponseKey = (String)attributes.get("samlIdpResponseKey");

		if (samlIdpResponseKey != null) {
			setSamlIdpResponseKey(samlIdpResponseKey);
		}

		Date expirationDate = (Date)attributes.get("expirationDate");

		if (expirationDate != null) {
			setExpirationDate(expirationDate);
		}
	}

	@Override
	public Object clone() {
		return new SamlSpMessageWrapper((SamlSpMessage)_samlSpMessage.clone());
	}

	@Override
	public int compareTo(SamlSpMessage samlSpMessage) {
		return _samlSpMessage.compareTo(samlSpMessage);
	}

	/**
	* Returns the company ID of this saml sp message.
	*
	* @return the company ID of this saml sp message
	*/
	@Override
	public long getCompanyId() {
		return _samlSpMessage.getCompanyId();
	}

	/**
	* Returns the create date of this saml sp message.
	*
	* @return the create date of this saml sp message
	*/
	@Override
	public Date getCreateDate() {
		return _samlSpMessage.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _samlSpMessage.getExpandoBridge();
	}

	/**
	* Returns the expiration date of this saml sp message.
	*
	* @return the expiration date of this saml sp message
	*/
	@Override
	public Date getExpirationDate() {
		return _samlSpMessage.getExpirationDate();
	}

	/**
	* Returns the primary key of this saml sp message.
	*
	* @return the primary key of this saml sp message
	*/
	@Override
	public long getPrimaryKey() {
		return _samlSpMessage.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _samlSpMessage.getPrimaryKeyObj();
	}

	/**
	* Returns the saml idp entity ID of this saml sp message.
	*
	* @return the saml idp entity ID of this saml sp message
	*/
	@Override
	public String getSamlIdpEntityId() {
		return _samlSpMessage.getSamlIdpEntityId();
	}

	/**
	* Returns the saml idp response key of this saml sp message.
	*
	* @return the saml idp response key of this saml sp message
	*/
	@Override
	public String getSamlIdpResponseKey() {
		return _samlSpMessage.getSamlIdpResponseKey();
	}

	/**
	* Returns the saml sp message ID of this saml sp message.
	*
	* @return the saml sp message ID of this saml sp message
	*/
	@Override
	public long getSamlSpMessageId() {
		return _samlSpMessage.getSamlSpMessageId();
	}

	@Override
	public int hashCode() {
		return _samlSpMessage.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _samlSpMessage.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _samlSpMessage.isEscapedModel();
	}

	@Override
	public boolean isExpired() {
		return _samlSpMessage.isExpired();
	}

	@Override
	public boolean isNew() {
		return _samlSpMessage.isNew();
	}

	@Override
	public void persist() {
		_samlSpMessage.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_samlSpMessage.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this saml sp message.
	*
	* @param companyId the company ID of this saml sp message
	*/
	@Override
	public void setCompanyId(long companyId) {
		_samlSpMessage.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this saml sp message.
	*
	* @param createDate the create date of this saml sp message
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_samlSpMessage.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_samlSpMessage.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_samlSpMessage.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_samlSpMessage.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the expiration date of this saml sp message.
	*
	* @param expirationDate the expiration date of this saml sp message
	*/
	@Override
	public void setExpirationDate(Date expirationDate) {
		_samlSpMessage.setExpirationDate(expirationDate);
	}

	@Override
	public void setNew(boolean n) {
		_samlSpMessage.setNew(n);
	}

	/**
	* Sets the primary key of this saml sp message.
	*
	* @param primaryKey the primary key of this saml sp message
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_samlSpMessage.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_samlSpMessage.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the saml idp entity ID of this saml sp message.
	*
	* @param samlIdpEntityId the saml idp entity ID of this saml sp message
	*/
	@Override
	public void setSamlIdpEntityId(String samlIdpEntityId) {
		_samlSpMessage.setSamlIdpEntityId(samlIdpEntityId);
	}

	/**
	* Sets the saml idp response key of this saml sp message.
	*
	* @param samlIdpResponseKey the saml idp response key of this saml sp message
	*/
	@Override
	public void setSamlIdpResponseKey(String samlIdpResponseKey) {
		_samlSpMessage.setSamlIdpResponseKey(samlIdpResponseKey);
	}

	/**
	* Sets the saml sp message ID of this saml sp message.
	*
	* @param samlSpMessageId the saml sp message ID of this saml sp message
	*/
	@Override
	public void setSamlSpMessageId(long samlSpMessageId) {
		_samlSpMessage.setSamlSpMessageId(samlSpMessageId);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<SamlSpMessage> toCacheModel() {
		return _samlSpMessage.toCacheModel();
	}

	@Override
	public SamlSpMessage toEscapedModel() {
		return new SamlSpMessageWrapper(_samlSpMessage.toEscapedModel());
	}

	@Override
	public String toString() {
		return _samlSpMessage.toString();
	}

	@Override
	public SamlSpMessage toUnescapedModel() {
		return new SamlSpMessageWrapper(_samlSpMessage.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _samlSpMessage.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SamlSpMessageWrapper)) {
			return false;
		}

		SamlSpMessageWrapper samlSpMessageWrapper = (SamlSpMessageWrapper)obj;

		if (Objects.equals(_samlSpMessage, samlSpMessageWrapper._samlSpMessage)) {
			return true;
		}

		return false;
	}

	@Override
	public SamlSpMessage getWrappedModel() {
		return _samlSpMessage;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _samlSpMessage.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _samlSpMessage.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_samlSpMessage.resetOriginalValues();
	}

	private final SamlSpMessage _samlSpMessage;
}