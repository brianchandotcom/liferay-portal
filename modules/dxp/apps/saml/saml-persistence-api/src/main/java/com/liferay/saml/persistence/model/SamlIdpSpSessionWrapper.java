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
 * This class is a wrapper for {@link SamlIdpSpSession}.
 * </p>
 *
 * @author Mika Koivisto
 * @see SamlIdpSpSession
 * @generated
 */
@ProviderType
public class SamlIdpSpSessionWrapper extends BaseModelWrapper<SamlIdpSpSession>
	implements SamlIdpSpSession, ModelWrapper<SamlIdpSpSession> {
	public SamlIdpSpSessionWrapper(SamlIdpSpSession samlIdpSpSession) {
		super(samlIdpSpSession);
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

	/**
	* Returns the company ID of this saml idp sp session.
	*
	* @return the company ID of this saml idp sp session
	*/
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	* Returns the create date of this saml idp sp session.
	*
	* @return the create date of this saml idp sp session
	*/
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	* Returns the modified date of this saml idp sp session.
	*
	* @return the modified date of this saml idp sp session
	*/
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	* Returns the name ID format of this saml idp sp session.
	*
	* @return the name ID format of this saml idp sp session
	*/
	@Override
	public String getNameIdFormat() {
		return model.getNameIdFormat();
	}

	/**
	* Returns the name ID value of this saml idp sp session.
	*
	* @return the name ID value of this saml idp sp session
	*/
	@Override
	public String getNameIdValue() {
		return model.getNameIdValue();
	}

	/**
	* Returns the primary key of this saml idp sp session.
	*
	* @return the primary key of this saml idp sp session
	*/
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	* Returns the saml idp sp session ID of this saml idp sp session.
	*
	* @return the saml idp sp session ID of this saml idp sp session
	*/
	@Override
	public long getSamlIdpSpSessionId() {
		return model.getSamlIdpSpSessionId();
	}

	/**
	* Returns the saml idp sso session ID of this saml idp sp session.
	*
	* @return the saml idp sso session ID of this saml idp sp session
	*/
	@Override
	public long getSamlIdpSsoSessionId() {
		return model.getSamlIdpSsoSessionId();
	}

	/**
	* Returns the saml sp entity ID of this saml idp sp session.
	*
	* @return the saml sp entity ID of this saml idp sp session
	*/
	@Override
	public String getSamlSpEntityId() {
		return model.getSamlSpEntityId();
	}

	/**
	* Returns the user ID of this saml idp sp session.
	*
	* @return the user ID of this saml idp sp session
	*/
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	* Returns the user name of this saml idp sp session.
	*
	* @return the user name of this saml idp sp session
	*/
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	* Returns the user uuid of this saml idp sp session.
	*
	* @return the user uuid of this saml idp sp session
	*/
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	* Sets the company ID of this saml idp sp session.
	*
	* @param companyId the company ID of this saml idp sp session
	*/
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this saml idp sp session.
	*
	* @param createDate the create date of this saml idp sp session
	*/
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	* Sets the modified date of this saml idp sp session.
	*
	* @param modifiedDate the modified date of this saml idp sp session
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name ID format of this saml idp sp session.
	*
	* @param nameIdFormat the name ID format of this saml idp sp session
	*/
	@Override
	public void setNameIdFormat(String nameIdFormat) {
		model.setNameIdFormat(nameIdFormat);
	}

	/**
	* Sets the name ID value of this saml idp sp session.
	*
	* @param nameIdValue the name ID value of this saml idp sp session
	*/
	@Override
	public void setNameIdValue(String nameIdValue) {
		model.setNameIdValue(nameIdValue);
	}

	/**
	* Sets the primary key of this saml idp sp session.
	*
	* @param primaryKey the primary key of this saml idp sp session
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	* Sets the saml idp sp session ID of this saml idp sp session.
	*
	* @param samlIdpSpSessionId the saml idp sp session ID of this saml idp sp session
	*/
	@Override
	public void setSamlIdpSpSessionId(long samlIdpSpSessionId) {
		model.setSamlIdpSpSessionId(samlIdpSpSessionId);
	}

	/**
	* Sets the saml idp sso session ID of this saml idp sp session.
	*
	* @param samlIdpSsoSessionId the saml idp sso session ID of this saml idp sp session
	*/
	@Override
	public void setSamlIdpSsoSessionId(long samlIdpSsoSessionId) {
		model.setSamlIdpSsoSessionId(samlIdpSsoSessionId);
	}

	/**
	* Sets the saml sp entity ID of this saml idp sp session.
	*
	* @param samlSpEntityId the saml sp entity ID of this saml idp sp session
	*/
	@Override
	public void setSamlSpEntityId(String samlSpEntityId) {
		model.setSamlSpEntityId(samlSpEntityId);
	}

	/**
	* Sets the user ID of this saml idp sp session.
	*
	* @param userId the user ID of this saml idp sp session
	*/
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	* Sets the user name of this saml idp sp session.
	*
	* @param userName the user name of this saml idp sp session
	*/
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	* Sets the user uuid of this saml idp sp session.
	*
	* @param userUuid the user uuid of this saml idp sp session
	*/
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected SamlIdpSpSessionWrapper wrap(SamlIdpSpSession samlIdpSpSession) {
		return new SamlIdpSpSessionWrapper(samlIdpSpSession);
	}
}