/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link CTRemote}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CTRemote
 * @generated
 */
public class CTRemoteWrapper
	extends BaseModelWrapper<CTRemote>
	implements CTRemote, ModelWrapper<CTRemote> {

	public CTRemoteWrapper(CTRemote ctRemote) {
		super(ctRemote);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctRemoteId", getCtRemoteId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("url", getUrl());
		attributes.put("clientId", getClientId());
		attributes.put("clientSecret", getClientSecret());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long ctRemoteId = (Long)attributes.get("ctRemoteId");

		if (ctRemoteId != null) {
			setCtRemoteId(ctRemoteId);
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

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		String url = (String)attributes.get("url");

		if (url != null) {
			setUrl(url);
		}

		String clientId = (String)attributes.get("clientId");

		if (clientId != null) {
			setClientId(clientId);
		}

		String clientSecret = (String)attributes.get("clientSecret");

		if (clientSecret != null) {
			setClientSecret(clientSecret);
		}
	}

	@Override
	public CTRemote cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the client ID of this ct remote.
	 *
	 * @return the client ID of this ct remote
	 */
	@Override
	public String getClientId() {
		return model.getClientId();
	}

	/**
	 * Returns the client secret of this ct remote.
	 *
	 * @return the client secret of this ct remote
	 */
	@Override
	public String getClientSecret() {
		return model.getClientSecret();
	}

	/**
	 * Returns the company ID of this ct remote.
	 *
	 * @return the company ID of this ct remote
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this ct remote.
	 *
	 * @return the create date of this ct remote
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct remote ID of this ct remote.
	 *
	 * @return the ct remote ID of this ct remote
	 */
	@Override
	public long getCtRemoteId() {
		return model.getCtRemoteId();
	}

	/**
	 * Returns the description of this ct remote.
	 *
	 * @return the description of this ct remote
	 */
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	/**
	 * Returns the modified date of this ct remote.
	 *
	 * @return the modified date of this ct remote
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this ct remote.
	 *
	 * @return the mvcc version of this ct remote
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this ct remote.
	 *
	 * @return the name of this ct remote
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this ct remote.
	 *
	 * @return the primary key of this ct remote
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the url of this ct remote.
	 *
	 * @return the url of this ct remote
	 */
	@Override
	public String getUrl() {
		return model.getUrl();
	}

	/**
	 * Returns the user ID of this ct remote.
	 *
	 * @return the user ID of this ct remote
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this ct remote.
	 *
	 * @return the user uuid of this ct remote
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
	 * Sets the client ID of this ct remote.
	 *
	 * @param clientId the client ID of this ct remote
	 */
	@Override
	public void setClientId(String clientId) {
		model.setClientId(clientId);
	}

	/**
	 * Sets the client secret of this ct remote.
	 *
	 * @param clientSecret the client secret of this ct remote
	 */
	@Override
	public void setClientSecret(String clientSecret) {
		model.setClientSecret(clientSecret);
	}

	/**
	 * Sets the company ID of this ct remote.
	 *
	 * @param companyId the company ID of this ct remote
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this ct remote.
	 *
	 * @param createDate the create date of this ct remote
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct remote ID of this ct remote.
	 *
	 * @param ctRemoteId the ct remote ID of this ct remote
	 */
	@Override
	public void setCtRemoteId(long ctRemoteId) {
		model.setCtRemoteId(ctRemoteId);
	}

	/**
	 * Sets the description of this ct remote.
	 *
	 * @param description the description of this ct remote
	 */
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	 * Sets the modified date of this ct remote.
	 *
	 * @param modifiedDate the modified date of this ct remote
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this ct remote.
	 *
	 * @param mvccVersion the mvcc version of this ct remote
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this ct remote.
	 *
	 * @param name the name of this ct remote
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this ct remote.
	 *
	 * @param primaryKey the primary key of this ct remote
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the url of this ct remote.
	 *
	 * @param url the url of this ct remote
	 */
	@Override
	public void setUrl(String url) {
		model.setUrl(url);
	}

	/**
	 * Sets the user ID of this ct remote.
	 *
	 * @param userId the user ID of this ct remote
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user uuid of this ct remote.
	 *
	 * @param userUuid the user uuid of this ct remote
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
	protected CTRemoteWrapper wrap(CTRemote ctRemote) {
		return new CTRemoteWrapper(ctRemote);
	}

}