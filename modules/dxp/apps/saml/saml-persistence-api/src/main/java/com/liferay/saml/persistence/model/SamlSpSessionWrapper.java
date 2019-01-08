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
 * This class is a wrapper for {@link SamlSpSession}.
 * </p>
 *
 * @author Mika Koivisto
 * @see SamlSpSession
 * @generated
 */
@ProviderType
public class SamlSpSessionWrapper extends BaseModelWrapper<SamlSpSession>
	implements SamlSpSession, ModelWrapper<SamlSpSession> {
	public SamlSpSessionWrapper(SamlSpSession samlSpSession) {
		super(samlSpSession);
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

	/**
	* Returns the assertion xml of this saml sp session.
	*
	* @return the assertion xml of this saml sp session
	*/
	@Override
	public String getAssertionXml() {
		return model.getAssertionXml();
	}

	/**
	* Returns the company ID of this saml sp session.
	*
	* @return the company ID of this saml sp session
	*/
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	* Returns the create date of this saml sp session.
	*
	* @return the create date of this saml sp session
	*/
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	* Returns the j session ID of this saml sp session.
	*
	* @return the j session ID of this saml sp session
	*/
	@Override
	public String getJSessionId() {
		return model.getJSessionId();
	}

	/**
	* Returns the modified date of this saml sp session.
	*
	* @return the modified date of this saml sp session
	*/
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	* Returns the name ID format of this saml sp session.
	*
	* @return the name ID format of this saml sp session
	*/
	@Override
	public String getNameIdFormat() {
		return model.getNameIdFormat();
	}

	/**
	* Returns the name ID name qualifier of this saml sp session.
	*
	* @return the name ID name qualifier of this saml sp session
	*/
	@Override
	public String getNameIdNameQualifier() {
		return model.getNameIdNameQualifier();
	}

	/**
	* Returns the name ID sp name qualifier of this saml sp session.
	*
	* @return the name ID sp name qualifier of this saml sp session
	*/
	@Override
	public String getNameIdSPNameQualifier() {
		return model.getNameIdSPNameQualifier();
	}

	/**
	* Returns the name ID value of this saml sp session.
	*
	* @return the name ID value of this saml sp session
	*/
	@Override
	public String getNameIdValue() {
		return model.getNameIdValue();
	}

	/**
	* Returns the primary key of this saml sp session.
	*
	* @return the primary key of this saml sp session
	*/
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	* Returns the saml sp session ID of this saml sp session.
	*
	* @return the saml sp session ID of this saml sp session
	*/
	@Override
	public long getSamlSpSessionId() {
		return model.getSamlSpSessionId();
	}

	/**
	* Returns the saml sp session key of this saml sp session.
	*
	* @return the saml sp session key of this saml sp session
	*/
	@Override
	public String getSamlSpSessionKey() {
		return model.getSamlSpSessionKey();
	}

	/**
	* Returns the session index of this saml sp session.
	*
	* @return the session index of this saml sp session
	*/
	@Override
	public String getSessionIndex() {
		return model.getSessionIndex();
	}

	/**
	* Returns the terminated of this saml sp session.
	*
	* @return the terminated of this saml sp session
	*/
	@Override
	public boolean getTerminated() {
		return model.getTerminated();
	}

	/**
	* Returns the user ID of this saml sp session.
	*
	* @return the user ID of this saml sp session
	*/
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	* Returns the user name of this saml sp session.
	*
	* @return the user name of this saml sp session
	*/
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	* Returns the user uuid of this saml sp session.
	*
	* @return the user uuid of this saml sp session
	*/
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	* Returns <code>true</code> if this saml sp session is terminated.
	*
	* @return <code>true</code> if this saml sp session is terminated; <code>false</code> otherwise
	*/
	@Override
	public boolean isTerminated() {
		return model.isTerminated();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	* Sets the assertion xml of this saml sp session.
	*
	* @param assertionXml the assertion xml of this saml sp session
	*/
	@Override
	public void setAssertionXml(String assertionXml) {
		model.setAssertionXml(assertionXml);
	}

	/**
	* Sets the company ID of this saml sp session.
	*
	* @param companyId the company ID of this saml sp session
	*/
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this saml sp session.
	*
	* @param createDate the create date of this saml sp session
	*/
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	* Sets the j session ID of this saml sp session.
	*
	* @param jSessionId the j session ID of this saml sp session
	*/
	@Override
	public void setJSessionId(String jSessionId) {
		model.setJSessionId(jSessionId);
	}

	/**
	* Sets the modified date of this saml sp session.
	*
	* @param modifiedDate the modified date of this saml sp session
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name ID format of this saml sp session.
	*
	* @param nameIdFormat the name ID format of this saml sp session
	*/
	@Override
	public void setNameIdFormat(String nameIdFormat) {
		model.setNameIdFormat(nameIdFormat);
	}

	/**
	* Sets the name ID name qualifier of this saml sp session.
	*
	* @param nameIdNameQualifier the name ID name qualifier of this saml sp session
	*/
	@Override
	public void setNameIdNameQualifier(String nameIdNameQualifier) {
		model.setNameIdNameQualifier(nameIdNameQualifier);
	}

	/**
	* Sets the name ID sp name qualifier of this saml sp session.
	*
	* @param nameIdSPNameQualifier the name ID sp name qualifier of this saml sp session
	*/
	@Override
	public void setNameIdSPNameQualifier(String nameIdSPNameQualifier) {
		model.setNameIdSPNameQualifier(nameIdSPNameQualifier);
	}

	/**
	* Sets the name ID value of this saml sp session.
	*
	* @param nameIdValue the name ID value of this saml sp session
	*/
	@Override
	public void setNameIdValue(String nameIdValue) {
		model.setNameIdValue(nameIdValue);
	}

	/**
	* Sets the primary key of this saml sp session.
	*
	* @param primaryKey the primary key of this saml sp session
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	* Sets the saml sp session ID of this saml sp session.
	*
	* @param samlSpSessionId the saml sp session ID of this saml sp session
	*/
	@Override
	public void setSamlSpSessionId(long samlSpSessionId) {
		model.setSamlSpSessionId(samlSpSessionId);
	}

	/**
	* Sets the saml sp session key of this saml sp session.
	*
	* @param samlSpSessionKey the saml sp session key of this saml sp session
	*/
	@Override
	public void setSamlSpSessionKey(String samlSpSessionKey) {
		model.setSamlSpSessionKey(samlSpSessionKey);
	}

	/**
	* Sets the session index of this saml sp session.
	*
	* @param sessionIndex the session index of this saml sp session
	*/
	@Override
	public void setSessionIndex(String sessionIndex) {
		model.setSessionIndex(sessionIndex);
	}

	/**
	* Sets whether this saml sp session is terminated.
	*
	* @param terminated the terminated of this saml sp session
	*/
	@Override
	public void setTerminated(boolean terminated) {
		model.setTerminated(terminated);
	}

	/**
	* Sets the user ID of this saml sp session.
	*
	* @param userId the user ID of this saml sp session
	*/
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	* Sets the user name of this saml sp session.
	*
	* @param userName the user name of this saml sp session
	*/
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	* Sets the user uuid of this saml sp session.
	*
	* @param userUuid the user uuid of this saml sp session
	*/
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected SamlSpSessionWrapper wrap(SamlSpSession samlSpSession) {
		return new SamlSpSessionWrapper(samlSpSession);
	}
}