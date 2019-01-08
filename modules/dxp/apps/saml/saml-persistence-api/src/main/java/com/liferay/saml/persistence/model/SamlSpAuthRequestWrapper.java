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

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link SamlSpAuthRequest}.
 * </p>
 *
 * @author Mika Koivisto
 * @see SamlSpAuthRequest
 * @generated
 */
@ProviderType
public class SamlSpAuthRequestWrapper extends BaseModelWrapper<SamlSpAuthRequest>
	implements SamlSpAuthRequest, ModelWrapper<SamlSpAuthRequest> {
	public SamlSpAuthRequestWrapper(SamlSpAuthRequest samlSpAuthRequest) {
		super(samlSpAuthRequest);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("samlSpAuthnRequestId", getSamlSpAuthnRequestId());
		attributes.put("companyId", getCompanyId());
		attributes.put("createDate", getCreateDate());
		attributes.put("samlIdpEntityId", getSamlIdpEntityId());
		attributes.put("samlSpAuthRequestKey", getSamlSpAuthRequestKey());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long samlSpAuthnRequestId = (Long)attributes.get("samlSpAuthnRequestId");

		if (samlSpAuthnRequestId != null) {
			setSamlSpAuthnRequestId(samlSpAuthnRequestId);
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

		String samlSpAuthRequestKey = (String)attributes.get(
				"samlSpAuthRequestKey");

		if (samlSpAuthRequestKey != null) {
			setSamlSpAuthRequestKey(samlSpAuthRequestKey);
		}
	}

	/**
	* Returns the company ID of this saml sp auth request.
	*
	* @return the company ID of this saml sp auth request
	*/
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	* Returns the create date of this saml sp auth request.
	*
	* @return the create date of this saml sp auth request
	*/
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	* Returns the primary key of this saml sp auth request.
	*
	* @return the primary key of this saml sp auth request
	*/
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	* Returns the saml idp entity ID of this saml sp auth request.
	*
	* @return the saml idp entity ID of this saml sp auth request
	*/
	@Override
	public String getSamlIdpEntityId() {
		return model.getSamlIdpEntityId();
	}

	/**
	* Returns the saml sp authn request ID of this saml sp auth request.
	*
	* @return the saml sp authn request ID of this saml sp auth request
	*/
	@Override
	public long getSamlSpAuthnRequestId() {
		return model.getSamlSpAuthnRequestId();
	}

	/**
	* Returns the saml sp auth request key of this saml sp auth request.
	*
	* @return the saml sp auth request key of this saml sp auth request
	*/
	@Override
	public String getSamlSpAuthRequestKey() {
		return model.getSamlSpAuthRequestKey();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	* Sets the company ID of this saml sp auth request.
	*
	* @param companyId the company ID of this saml sp auth request
	*/
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this saml sp auth request.
	*
	* @param createDate the create date of this saml sp auth request
	*/
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	* Sets the primary key of this saml sp auth request.
	*
	* @param primaryKey the primary key of this saml sp auth request
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	* Sets the saml idp entity ID of this saml sp auth request.
	*
	* @param samlIdpEntityId the saml idp entity ID of this saml sp auth request
	*/
	@Override
	public void setSamlIdpEntityId(String samlIdpEntityId) {
		model.setSamlIdpEntityId(samlIdpEntityId);
	}

	/**
	* Sets the saml sp authn request ID of this saml sp auth request.
	*
	* @param samlSpAuthnRequestId the saml sp authn request ID of this saml sp auth request
	*/
	@Override
	public void setSamlSpAuthnRequestId(long samlSpAuthnRequestId) {
		model.setSamlSpAuthnRequestId(samlSpAuthnRequestId);
	}

	/**
	* Sets the saml sp auth request key of this saml sp auth request.
	*
	* @param samlSpAuthRequestKey the saml sp auth request key of this saml sp auth request
	*/
	@Override
	public void setSamlSpAuthRequestKey(String samlSpAuthRequestKey) {
		model.setSamlSpAuthRequestKey(samlSpAuthRequestKey);
	}

	@Override
	protected SamlSpAuthRequestWrapper wrap(SamlSpAuthRequest samlSpAuthRequest) {
		return new SamlSpAuthRequestWrapper(samlSpAuthRequest);
	}
}