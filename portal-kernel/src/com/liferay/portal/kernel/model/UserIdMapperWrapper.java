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

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
public class UserIdMapperWrapper implements UserIdMapper,
	ModelWrapper<UserIdMapper> {
	public UserIdMapperWrapper(UserIdMapper userIdMapper) {
		_userIdMapper = userIdMapper;
	}

	@Override
	public Class<?> getModelClass() {
		return UserIdMapper.class;
	}

	@Override
	public String getModelClassName() {
		return UserIdMapper.class.getName();
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

	@Override
	public CacheModel<UserIdMapper> toCacheModel() {
		return _userIdMapper.toCacheModel();
	}

	@Override
	public UserIdMapper toEscapedModel() {
		return new UserIdMapperWrapper(_userIdMapper.toEscapedModel());
	}

	@Override
	public UserIdMapper toUnescapedModel() {
		return new UserIdMapperWrapper(_userIdMapper.toUnescapedModel());
	}

	@Override
	public boolean isCachedModel() {
		return _userIdMapper.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _userIdMapper.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _userIdMapper.isNew();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _userIdMapper.getExpandoBridge();
	}

	@Override
	public int compareTo(UserIdMapper userIdMapper) {
		return _userIdMapper.compareTo(userIdMapper);
	}

	@Override
	public int hashCode() {
		return _userIdMapper.hashCode();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _userIdMapper.getPrimaryKeyObj();
	}

	@Override
	public java.lang.Object clone() {
		return new UserIdMapperWrapper((UserIdMapper)_userIdMapper.clone());
	}

	/**
	* Returns the description of this user ID mapper.
	*
	* @return the description of this user ID mapper
	*/
	@Override
	public java.lang.String getDescription() {
		return _userIdMapper.getDescription();
	}

	/**
	* Returns the external user ID of this user ID mapper.
	*
	* @return the external user ID of this user ID mapper
	*/
	@Override
	public java.lang.String getExternalUserId() {
		return _userIdMapper.getExternalUserId();
	}

	/**
	* Returns the type of this user ID mapper.
	*
	* @return the type of this user ID mapper
	*/
	@Override
	public java.lang.String getType() {
		return _userIdMapper.getType();
	}

	/**
	* Returns the user uuid of this user ID mapper.
	*
	* @return the user uuid of this user ID mapper
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _userIdMapper.getUserUuid();
	}

	@Override
	public java.lang.String toString() {
		return _userIdMapper.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _userIdMapper.toXmlString();
	}

	/**
	* Returns the company ID of this user ID mapper.
	*
	* @return the company ID of this user ID mapper
	*/
	@Override
	public long getCompanyId() {
		return _userIdMapper.getCompanyId();
	}

	/**
	* Returns the mvcc version of this user ID mapper.
	*
	* @return the mvcc version of this user ID mapper
	*/
	@Override
	public long getMvccVersion() {
		return _userIdMapper.getMvccVersion();
	}

	/**
	* Returns the primary key of this user ID mapper.
	*
	* @return the primary key of this user ID mapper
	*/
	@Override
	public long getPrimaryKey() {
		return _userIdMapper.getPrimaryKey();
	}

	/**
	* Returns the user ID of this user ID mapper.
	*
	* @return the user ID of this user ID mapper
	*/
	@Override
	public long getUserId() {
		return _userIdMapper.getUserId();
	}

	/**
	* Returns the user ID mapper ID of this user ID mapper.
	*
	* @return the user ID mapper ID of this user ID mapper
	*/
	@Override
	public long getUserIdMapperId() {
		return _userIdMapper.getUserIdMapperId();
	}

	@Override
	public void persist() {
		_userIdMapper.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_userIdMapper.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this user ID mapper.
	*
	* @param companyId the company ID of this user ID mapper
	*/
	@Override
	public void setCompanyId(long companyId) {
		_userIdMapper.setCompanyId(companyId);
	}

	/**
	* Sets the description of this user ID mapper.
	*
	* @param description the description of this user ID mapper
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_userIdMapper.setDescription(description);
	}

	@Override
	public void setExpandoBridgeAttributes(BaseModel<?> baseModel) {
		_userIdMapper.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_userIdMapper.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_userIdMapper.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the external user ID of this user ID mapper.
	*
	* @param externalUserId the external user ID of this user ID mapper
	*/
	@Override
	public void setExternalUserId(java.lang.String externalUserId) {
		_userIdMapper.setExternalUserId(externalUserId);
	}

	/**
	* Sets the mvcc version of this user ID mapper.
	*
	* @param mvccVersion the mvcc version of this user ID mapper
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		_userIdMapper.setMvccVersion(mvccVersion);
	}

	@Override
	public void setNew(boolean n) {
		_userIdMapper.setNew(n);
	}

	/**
	* Sets the primary key of this user ID mapper.
	*
	* @param primaryKey the primary key of this user ID mapper
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_userIdMapper.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_userIdMapper.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the type of this user ID mapper.
	*
	* @param type the type of this user ID mapper
	*/
	@Override
	public void setType(java.lang.String type) {
		_userIdMapper.setType(type);
	}

	/**
	* Sets the user ID of this user ID mapper.
	*
	* @param userId the user ID of this user ID mapper
	*/
	@Override
	public void setUserId(long userId) {
		_userIdMapper.setUserId(userId);
	}

	/**
	* Sets the user ID mapper ID of this user ID mapper.
	*
	* @param userIdMapperId the user ID mapper ID of this user ID mapper
	*/
	@Override
	public void setUserIdMapperId(long userIdMapperId) {
		_userIdMapper.setUserIdMapperId(userIdMapperId);
	}

	/**
	* Sets the user uuid of this user ID mapper.
	*
	* @param userUuid the user uuid of this user ID mapper
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_userIdMapper.setUserUuid(userUuid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof UserIdMapperWrapper)) {
			return false;
		}

		UserIdMapperWrapper userIdMapperWrapper = (UserIdMapperWrapper)obj;

		if (Objects.equals(_userIdMapper, userIdMapperWrapper._userIdMapper)) {
			return true;
		}

		return false;
	}

	@Override
	public UserIdMapper getWrappedModel() {
		return _userIdMapper;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _userIdMapper.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _userIdMapper.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_userIdMapper.resetOriginalValues();
	}

	private final UserIdMapper _userIdMapper;
}