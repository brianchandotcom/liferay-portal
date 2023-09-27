/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth.client.persistence.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link OAuthClientASLocalMetadata}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see OAuthClientASLocalMetadata
 * @generated
 */
public class OAuthClientASLocalMetadataWrapper
	extends BaseModelWrapper<OAuthClientASLocalMetadata>
	implements ModelWrapper<OAuthClientASLocalMetadata>,
			   OAuthClientASLocalMetadata {

	public OAuthClientASLocalMetadataWrapper(
		OAuthClientASLocalMetadata oAuthClientASLocalMetadata) {

		super(oAuthClientASLocalMetadata);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put(
			"oAuthClientASLocalMetadataId", getOAuthClientASLocalMetadataId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("localWellKnownURI", getLocalWellKnownURI());
		attributes.put("metadataJSON", getMetadataJSON());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long oAuthClientASLocalMetadataId = (Long)attributes.get(
			"oAuthClientASLocalMetadataId");

		if (oAuthClientASLocalMetadataId != null) {
			setOAuthClientASLocalMetadataId(oAuthClientASLocalMetadataId);
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

		String localWellKnownURI = (String)attributes.get("localWellKnownURI");

		if (localWellKnownURI != null) {
			setLocalWellKnownURI(localWellKnownURI);
		}

		String metadataJSON = (String)attributes.get("metadataJSON");

		if (metadataJSON != null) {
			setMetadataJSON(metadataJSON);
		}
	}

	@Override
	public OAuthClientASLocalMetadata cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this o auth client as local metadata.
	 *
	 * @return the company ID of this o auth client as local metadata
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this o auth client as local metadata.
	 *
	 * @return the create date of this o auth client as local metadata
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the local well known uri of this o auth client as local metadata.
	 *
	 * @return the local well known uri of this o auth client as local metadata
	 */
	@Override
	public String getLocalWellKnownURI() {
		return model.getLocalWellKnownURI();
	}

	/**
	 * Returns the metadata json of this o auth client as local metadata.
	 *
	 * @return the metadata json of this o auth client as local metadata
	 */
	@Override
	public String getMetadataJSON() {
		return model.getMetadataJSON();
	}

	/**
	 * Returns the modified date of this o auth client as local metadata.
	 *
	 * @return the modified date of this o auth client as local metadata
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this o auth client as local metadata.
	 *
	 * @return the mvcc version of this o auth client as local metadata
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the o auth client as local metadata ID of this o auth client as local metadata.
	 *
	 * @return the o auth client as local metadata ID of this o auth client as local metadata
	 */
	@Override
	public long getOAuthClientASLocalMetadataId() {
		return model.getOAuthClientASLocalMetadataId();
	}

	/**
	 * Returns the primary key of this o auth client as local metadata.
	 *
	 * @return the primary key of this o auth client as local metadata
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this o auth client as local metadata.
	 *
	 * @return the user ID of this o auth client as local metadata
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this o auth client as local metadata.
	 *
	 * @return the user name of this o auth client as local metadata
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this o auth client as local metadata.
	 *
	 * @return the user uuid of this o auth client as local metadata
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
	 * Sets the company ID of this o auth client as local metadata.
	 *
	 * @param companyId the company ID of this o auth client as local metadata
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this o auth client as local metadata.
	 *
	 * @param createDate the create date of this o auth client as local metadata
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the local well known uri of this o auth client as local metadata.
	 *
	 * @param localWellKnownURI the local well known uri of this o auth client as local metadata
	 */
	@Override
	public void setLocalWellKnownURI(String localWellKnownURI) {
		model.setLocalWellKnownURI(localWellKnownURI);
	}

	/**
	 * Sets the metadata json of this o auth client as local metadata.
	 *
	 * @param metadataJSON the metadata json of this o auth client as local metadata
	 */
	@Override
	public void setMetadataJSON(String metadataJSON) {
		model.setMetadataJSON(metadataJSON);
	}

	/**
	 * Sets the modified date of this o auth client as local metadata.
	 *
	 * @param modifiedDate the modified date of this o auth client as local metadata
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this o auth client as local metadata.
	 *
	 * @param mvccVersion the mvcc version of this o auth client as local metadata
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the o auth client as local metadata ID of this o auth client as local metadata.
	 *
	 * @param oAuthClientASLocalMetadataId the o auth client as local metadata ID of this o auth client as local metadata
	 */
	@Override
	public void setOAuthClientASLocalMetadataId(
		long oAuthClientASLocalMetadataId) {

		model.setOAuthClientASLocalMetadataId(oAuthClientASLocalMetadataId);
	}

	/**
	 * Sets the primary key of this o auth client as local metadata.
	 *
	 * @param primaryKey the primary key of this o auth client as local metadata
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this o auth client as local metadata.
	 *
	 * @param userId the user ID of this o auth client as local metadata
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this o auth client as local metadata.
	 *
	 * @param userName the user name of this o auth client as local metadata
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this o auth client as local metadata.
	 *
	 * @param userUuid the user uuid of this o auth client as local metadata
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
	protected OAuthClientASLocalMetadataWrapper wrap(
		OAuthClientASLocalMetadata oAuthClientASLocalMetadata) {

		return new OAuthClientASLocalMetadataWrapper(
			oAuthClientASLocalMetadata);
	}

}