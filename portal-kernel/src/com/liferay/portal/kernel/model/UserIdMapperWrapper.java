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

package com.liferay.portal.kernel.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link UserIdMapper}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see UserIdMapper
 * @generated
 */
@ProviderType
public class UserIdMapperWrapper extends BaseModelWrapper<UserIdMapper>
	implements UserIdMapper, ModelWrapper<UserIdMapper> {
	public UserIdMapperWrapper(UserIdMapper userIdMapper) {
		super(userIdMapper);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("userIdMapperId", getUserIdMapperId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("type", getType());
		attributes.put("description", getDescription());
		attributes.put("externalUserId", getExternalUserId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long userIdMapperId = (Long)attributes.get("userIdMapperId");

		if (userIdMapperId != null) {
			setUserIdMapperId(userIdMapperId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String type = (String)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		String externalUserId = (String)attributes.get("externalUserId");

		if (externalUserId != null) {
			setExternalUserId(externalUserId);
		}
	}

	/**
	* Returns the company ID of this user ID mapper.
	*
	* @return the company ID of this user ID mapper
	*/
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	* Returns the description of this user ID mapper.
	*
	* @return the description of this user ID mapper
	*/
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	/**
	* Returns the external user ID of this user ID mapper.
	*
	* @return the external user ID of this user ID mapper
	*/
	@Override
	public String getExternalUserId() {
		return model.getExternalUserId();
	}

	/**
	* Returns the mvcc version of this user ID mapper.
	*
	* @return the mvcc version of this user ID mapper
	*/
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	* Returns the primary key of this user ID mapper.
	*
	* @return the primary key of this user ID mapper
	*/
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	* Returns the type of this user ID mapper.
	*
	* @return the type of this user ID mapper
	*/
	@Override
	public String getType() {
		return model.getType();
	}

	/**
	* Returns the user ID of this user ID mapper.
	*
	* @return the user ID of this user ID mapper
	*/
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	* Returns the user ID mapper ID of this user ID mapper.
	*
	* @return the user ID mapper ID of this user ID mapper
	*/
	@Override
	public long getUserIdMapperId() {
		return model.getUserIdMapperId();
	}

	/**
	* Returns the user uuid of this user ID mapper.
	*
	* @return the user uuid of this user ID mapper
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
	* Sets the company ID of this user ID mapper.
	*
	* @param companyId the company ID of this user ID mapper
	*/
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	* Sets the description of this user ID mapper.
	*
	* @param description the description of this user ID mapper
	*/
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	* Sets the external user ID of this user ID mapper.
	*
	* @param externalUserId the external user ID of this user ID mapper
	*/
	@Override
	public void setExternalUserId(String externalUserId) {
		model.setExternalUserId(externalUserId);
	}

	/**
	* Sets the mvcc version of this user ID mapper.
	*
	* @param mvccVersion the mvcc version of this user ID mapper
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	* Sets the primary key of this user ID mapper.
	*
	* @param primaryKey the primary key of this user ID mapper
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	* Sets the type of this user ID mapper.
	*
	* @param type the type of this user ID mapper
	*/
	@Override
	public void setType(String type) {
		model.setType(type);
	}

	/**
	* Sets the user ID of this user ID mapper.
	*
	* @param userId the user ID of this user ID mapper
	*/
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	* Sets the user ID mapper ID of this user ID mapper.
	*
	* @param userIdMapperId the user ID mapper ID of this user ID mapper
	*/
	@Override
	public void setUserIdMapperId(long userIdMapperId) {
		model.setUserIdMapperId(userIdMapperId);
	}

	/**
	* Sets the user uuid of this user ID mapper.
	*
	* @param userUuid the user uuid of this user ID mapper
	*/
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected UserIdMapperWrapper wrap(UserIdMapper userIdMapper) {
		return new UserIdMapperWrapper(userIdMapper);
	}
}