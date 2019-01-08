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
 * This class is a wrapper for {@link SamlIdpSsoSession}.
 * </p>
 *
 * @author Mika Koivisto
 * @see SamlIdpSsoSession
 * @generated
 */
@ProviderType
public class SamlIdpSsoSessionWrapper extends BaseModelWrapper<SamlIdpSsoSession>
	implements SamlIdpSsoSession, ModelWrapper<SamlIdpSsoSession> {
	public SamlIdpSsoSessionWrapper(SamlIdpSsoSession samlIdpSsoSession) {
		super(samlIdpSsoSession);
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

	/**
	* Returns the company ID of this saml idp sso session.
	*
	* @return the company ID of this saml idp sso session
	*/
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	* Returns the create date of this saml idp sso session.
	*
	* @return the create date of this saml idp sso session
	*/
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	* Returns the modified date of this saml idp sso session.
	*
	* @return the modified date of this saml idp sso session
	*/
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	* Returns the primary key of this saml idp sso session.
	*
	* @return the primary key of this saml idp sso session
	*/
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	* Returns the saml idp sso session ID of this saml idp sso session.
	*
	* @return the saml idp sso session ID of this saml idp sso session
	*/
	@Override
	public long getSamlIdpSsoSessionId() {
		return model.getSamlIdpSsoSessionId();
	}

	/**
	* Returns the saml idp sso session key of this saml idp sso session.
	*
	* @return the saml idp sso session key of this saml idp sso session
	*/
	@Override
	public String getSamlIdpSsoSessionKey() {
		return model.getSamlIdpSsoSessionKey();
	}

	/**
	* Returns the user ID of this saml idp sso session.
	*
	* @return the user ID of this saml idp sso session
	*/
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	* Returns the user name of this saml idp sso session.
	*
	* @return the user name of this saml idp sso session
	*/
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	* Returns the user uuid of this saml idp sso session.
	*
	* @return the user uuid of this saml idp sso session
	*/
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	@Override
	public boolean isExpired() {
		return model.isExpired();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	* Sets the company ID of this saml idp sso session.
	*
	* @param companyId the company ID of this saml idp sso session
	*/
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this saml idp sso session.
	*
	* @param createDate the create date of this saml idp sso session
	*/
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	* Sets the modified date of this saml idp sso session.
	*
	* @param modifiedDate the modified date of this saml idp sso session
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the primary key of this saml idp sso session.
	*
	* @param primaryKey the primary key of this saml idp sso session
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	* Sets the saml idp sso session ID of this saml idp sso session.
	*
	* @param samlIdpSsoSessionId the saml idp sso session ID of this saml idp sso session
	*/
	@Override
	public void setSamlIdpSsoSessionId(long samlIdpSsoSessionId) {
		model.setSamlIdpSsoSessionId(samlIdpSsoSessionId);
	}

	/**
	* Sets the saml idp sso session key of this saml idp sso session.
	*
	* @param samlIdpSsoSessionKey the saml idp sso session key of this saml idp sso session
	*/
	@Override
	public void setSamlIdpSsoSessionKey(String samlIdpSsoSessionKey) {
		model.setSamlIdpSsoSessionKey(samlIdpSsoSessionKey);
	}

	/**
	* Sets the user ID of this saml idp sso session.
	*
	* @param userId the user ID of this saml idp sso session
	*/
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	* Sets the user name of this saml idp sso session.
	*
	* @param userName the user name of this saml idp sso session
	*/
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	* Sets the user uuid of this saml idp sso session.
	*
	* @param userUuid the user uuid of this saml idp sso session
	*/
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected SamlIdpSsoSessionWrapper wrap(SamlIdpSsoSession samlIdpSsoSession) {
		return new SamlIdpSsoSessionWrapper(samlIdpSsoSession);
	}
}