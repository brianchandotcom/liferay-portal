/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link RememberMeToken}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RememberMeToken
 * @generated
 */
public class RememberMeTokenWrapper
	extends BaseModelWrapper<RememberMeToken>
	implements ModelWrapper<RememberMeToken>, RememberMeToken {

	public RememberMeTokenWrapper(RememberMeToken rememberMeToken) {
		super(rememberMeToken);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("rememberMeTokenId", getRememberMeTokenId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("createDate", getCreateDate());
		attributes.put("expirationDate", getExpirationDate());
		attributes.put("value", getValue());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long rememberMeTokenId = (Long)attributes.get("rememberMeTokenId");

		if (rememberMeTokenId != null) {
			setRememberMeTokenId(rememberMeTokenId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date expirationDate = (Date)attributes.get("expirationDate");

		if (expirationDate != null) {
			setExpirationDate(expirationDate);
		}

		String value = (String)attributes.get("value");

		if (value != null) {
			setValue(value);
		}
	}

	@Override
	public RememberMeToken cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this remember me token.
	 *
	 * @return the company ID of this remember me token
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this remember me token.
	 *
	 * @return the create date of this remember me token
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the expiration date of this remember me token.
	 *
	 * @return the expiration date of this remember me token
	 */
	@Override
	public Date getExpirationDate() {
		return model.getExpirationDate();
	}

	/**
	 * Returns the mvcc version of this remember me token.
	 *
	 * @return the mvcc version of this remember me token
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this remember me token.
	 *
	 * @return the primary key of this remember me token
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the remember me token ID of this remember me token.
	 *
	 * @return the remember me token ID of this remember me token
	 */
	@Override
	public long getRememberMeTokenId() {
		return model.getRememberMeTokenId();
	}

	/**
	 * Returns the user ID of this remember me token.
	 *
	 * @return the user ID of this remember me token
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user uuid of this remember me token.
	 *
	 * @return the user uuid of this remember me token
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the value of this remember me token.
	 *
	 * @return the value of this remember me token
	 */
	@Override
	public String getValue() {
		return model.getValue();
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
	 * Sets the company ID of this remember me token.
	 *
	 * @param companyId the company ID of this remember me token
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this remember me token.
	 *
	 * @param createDate the create date of this remember me token
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the expiration date of this remember me token.
	 *
	 * @param expirationDate the expiration date of this remember me token
	 */
	@Override
	public void setExpirationDate(Date expirationDate) {
		model.setExpirationDate(expirationDate);
	}

	/**
	 * Sets the mvcc version of this remember me token.
	 *
	 * @param mvccVersion the mvcc version of this remember me token
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this remember me token.
	 *
	 * @param primaryKey the primary key of this remember me token
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the remember me token ID of this remember me token.
	 *
	 * @param rememberMeTokenId the remember me token ID of this remember me token
	 */
	@Override
	public void setRememberMeTokenId(long rememberMeTokenId) {
		model.setRememberMeTokenId(rememberMeTokenId);
	}

	/**
	 * Sets the user ID of this remember me token.
	 *
	 * @param userId the user ID of this remember me token
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user uuid of this remember me token.
	 *
	 * @param userUuid the user uuid of this remember me token
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the value of this remember me token.
	 *
	 * @param value the value of this remember me token
	 */
	@Override
	public void setValue(String value) {
		model.setValue(value);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	protected RememberMeTokenWrapper wrap(RememberMeToken rememberMeToken) {
		return new RememberMeTokenWrapper(rememberMeToken);
	}

}