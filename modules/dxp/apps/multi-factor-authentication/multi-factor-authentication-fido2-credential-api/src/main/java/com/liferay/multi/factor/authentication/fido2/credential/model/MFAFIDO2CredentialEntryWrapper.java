/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.multi.factor.authentication.fido2.credential.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link MFAFIDO2CredentialEntry}.
 * </p>
 *
 * @author Arthur Chan
 * @see MFAFIDO2CredentialEntry
 * @generated
 */
public class MFAFIDO2CredentialEntryWrapper
	extends BaseModelWrapper<MFAFIDO2CredentialEntry>
	implements MFAFIDO2CredentialEntry, ModelWrapper<MFAFIDO2CredentialEntry> {

	public MFAFIDO2CredentialEntryWrapper(
		MFAFIDO2CredentialEntry mfaFIDO2CredentialEntry) {

		super(mfaFIDO2CredentialEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put(
			"mfaFIDO2CredentialEntryId", getMfaFIDO2CredentialEntryId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("credentialKey", getCredentialKey());
		attributes.put("credentialKeyHash", getCredentialKeyHash());
		attributes.put("credentialType", getCredentialType());
		attributes.put("failedAttempts", getFailedAttempts());
		attributes.put("publicKeyCOSE", getPublicKeyCOSE());
		attributes.put("signatureCount", getSignatureCount());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long mfaFIDO2CredentialEntryId = (Long)attributes.get(
			"mfaFIDO2CredentialEntryId");

		if (mfaFIDO2CredentialEntryId != null) {
			setMfaFIDO2CredentialEntryId(mfaFIDO2CredentialEntryId);
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

		String credentialKey = (String)attributes.get("credentialKey");

		if (credentialKey != null) {
			setCredentialKey(credentialKey);
		}

		Long credentialKeyHash = (Long)attributes.get("credentialKeyHash");

		if (credentialKeyHash != null) {
			setCredentialKeyHash(credentialKeyHash);
		}

		Integer credentialType = (Integer)attributes.get("credentialType");

		if (credentialType != null) {
			setCredentialType(credentialType);
		}

		Integer failedAttempts = (Integer)attributes.get("failedAttempts");

		if (failedAttempts != null) {
			setFailedAttempts(failedAttempts);
		}

		String publicKeyCOSE = (String)attributes.get("publicKeyCOSE");

		if (publicKeyCOSE != null) {
			setPublicKeyCOSE(publicKeyCOSE);
		}

		Long signatureCount = (Long)attributes.get("signatureCount");

		if (signatureCount != null) {
			setSignatureCount(signatureCount);
		}
	}

	@Override
	public MFAFIDO2CredentialEntry cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this mfafido2 credential entry.
	 *
	 * @return the company ID of this mfafido2 credential entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this mfafido2 credential entry.
	 *
	 * @return the create date of this mfafido2 credential entry
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the credential key of this mfafido2 credential entry.
	 *
	 * @return the credential key of this mfafido2 credential entry
	 */
	@Override
	public String getCredentialKey() {
		return model.getCredentialKey();
	}

	/**
	 * Returns the credential key hash of this mfafido2 credential entry.
	 *
	 * @return the credential key hash of this mfafido2 credential entry
	 */
	@Override
	public long getCredentialKeyHash() {
		return model.getCredentialKeyHash();
	}

	/**
	 * Returns the credential type of this mfafido2 credential entry.
	 *
	 * @return the credential type of this mfafido2 credential entry
	 */
	@Override
	public int getCredentialType() {
		return model.getCredentialType();
	}

	/**
	 * Returns the failed attempts of this mfafido2 credential entry.
	 *
	 * @return the failed attempts of this mfafido2 credential entry
	 */
	@Override
	public int getFailedAttempts() {
		return model.getFailedAttempts();
	}

	/**
	 * Returns the mfa fido2 credential entry ID of this mfafido2 credential entry.
	 *
	 * @return the mfa fido2 credential entry ID of this mfafido2 credential entry
	 */
	@Override
	public long getMfaFIDO2CredentialEntryId() {
		return model.getMfaFIDO2CredentialEntryId();
	}

	/**
	 * Returns the modified date of this mfafido2 credential entry.
	 *
	 * @return the modified date of this mfafido2 credential entry
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this mfafido2 credential entry.
	 *
	 * @return the mvcc version of this mfafido2 credential entry
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this mfafido2 credential entry.
	 *
	 * @return the primary key of this mfafido2 credential entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the public key cose of this mfafido2 credential entry.
	 *
	 * @return the public key cose of this mfafido2 credential entry
	 */
	@Override
	public String getPublicKeyCOSE() {
		return model.getPublicKeyCOSE();
	}

	/**
	 * Returns the signature count of this mfafido2 credential entry.
	 *
	 * @return the signature count of this mfafido2 credential entry
	 */
	@Override
	public long getSignatureCount() {
		return model.getSignatureCount();
	}

	/**
	 * Returns the user ID of this mfafido2 credential entry.
	 *
	 * @return the user ID of this mfafido2 credential entry
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this mfafido2 credential entry.
	 *
	 * @return the user name of this mfafido2 credential entry
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this mfafido2 credential entry.
	 *
	 * @return the user uuid of this mfafido2 credential entry
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
	 * Sets the company ID of this mfafido2 credential entry.
	 *
	 * @param companyId the company ID of this mfafido2 credential entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this mfafido2 credential entry.
	 *
	 * @param createDate the create date of this mfafido2 credential entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the credential key of this mfafido2 credential entry.
	 *
	 * @param credentialKey the credential key of this mfafido2 credential entry
	 */
	@Override
	public void setCredentialKey(String credentialKey) {
		model.setCredentialKey(credentialKey);
	}

	/**
	 * Sets the credential key hash of this mfafido2 credential entry.
	 *
	 * @param credentialKeyHash the credential key hash of this mfafido2 credential entry
	 */
	@Override
	public void setCredentialKeyHash(long credentialKeyHash) {
		model.setCredentialKeyHash(credentialKeyHash);
	}

	/**
	 * Sets the credential type of this mfafido2 credential entry.
	 *
	 * @param credentialType the credential type of this mfafido2 credential entry
	 */
	@Override
	public void setCredentialType(int credentialType) {
		model.setCredentialType(credentialType);
	}

	/**
	 * Sets the failed attempts of this mfafido2 credential entry.
	 *
	 * @param failedAttempts the failed attempts of this mfafido2 credential entry
	 */
	@Override
	public void setFailedAttempts(int failedAttempts) {
		model.setFailedAttempts(failedAttempts);
	}

	/**
	 * Sets the mfa fido2 credential entry ID of this mfafido2 credential entry.
	 *
	 * @param mfaFIDO2CredentialEntryId the mfa fido2 credential entry ID of this mfafido2 credential entry
	 */
	@Override
	public void setMfaFIDO2CredentialEntryId(long mfaFIDO2CredentialEntryId) {
		model.setMfaFIDO2CredentialEntryId(mfaFIDO2CredentialEntryId);
	}

	/**
	 * Sets the modified date of this mfafido2 credential entry.
	 *
	 * @param modifiedDate the modified date of this mfafido2 credential entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this mfafido2 credential entry.
	 *
	 * @param mvccVersion the mvcc version of this mfafido2 credential entry
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this mfafido2 credential entry.
	 *
	 * @param primaryKey the primary key of this mfafido2 credential entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the public key cose of this mfafido2 credential entry.
	 *
	 * @param publicKeyCOSE the public key cose of this mfafido2 credential entry
	 */
	@Override
	public void setPublicKeyCOSE(String publicKeyCOSE) {
		model.setPublicKeyCOSE(publicKeyCOSE);
	}

	/**
	 * Sets the signature count of this mfafido2 credential entry.
	 *
	 * @param signatureCount the signature count of this mfafido2 credential entry
	 */
	@Override
	public void setSignatureCount(long signatureCount) {
		model.setSignatureCount(signatureCount);
	}

	/**
	 * Sets the user ID of this mfafido2 credential entry.
	 *
	 * @param userId the user ID of this mfafido2 credential entry
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this mfafido2 credential entry.
	 *
	 * @param userName the user name of this mfafido2 credential entry
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this mfafido2 credential entry.
	 *
	 * @param userUuid the user uuid of this mfafido2 credential entry
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	protected MFAFIDO2CredentialEntryWrapper wrap(
		MFAFIDO2CredentialEntry mfaFIDO2CredentialEntry) {

		return new MFAFIDO2CredentialEntryWrapper(mfaFIDO2CredentialEntry);
	}

}