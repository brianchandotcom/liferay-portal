/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model;

import com.liferay.portal.kernel.util.Validator;

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

		attributes.put("userIdMapperId", getUserIdMapperId());
		attributes.put("userId", getUserId());
		attributes.put("type", getType());
		attributes.put("description", getDescription());
		attributes.put("externalUserId", getExternalUserId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long userIdMapperId = (Long)attributes.get("userIdMapperId");

		if (userIdMapperId != null) {
			setUserIdMapperId(userIdMapperId);
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
	* Returns the primary key of this user ID mapper.
	*
	* @return the primary key of this user ID mapper
	*/
	@Override
	public long getPrimaryKey() {
		return _userIdMapper.getPrimaryKey();
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

	/**
	* Returns the user ID mapper ID of this user ID mapper.
	*
	* @return the user ID mapper ID of this user ID mapper
	*/
	@Override
	public long getUserIdMapperId() {
		return _userIdMapper.getUserIdMapperId();
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
	* Returns the user ID of this user ID mapper.
	*
	* @return the user ID of this user ID mapper
	*/
	@Override
	public long getUserId() {
		return _userIdMapper.getUserId();
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
	* Returns the user uuid of this user ID mapper.
	*
	* @return the user uuid of this user ID mapper
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _userIdMapper.getUserUuid();
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
	* Sets the type of this user ID mapper.
	*
	* @param type the type of this user ID mapper
	*/
	@Override
	public void setType(java.lang.String type) {
		_userIdMapper.setType(type);
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
	* Sets the description of this user ID mapper.
	*
	* @param description the description of this user ID mapper
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_userIdMapper.setDescription(description);
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
	* Sets the external user ID of this user ID mapper.
	*
	* @param externalUserId the external user ID of this user ID mapper
	*/
	@Override
	public void setExternalUserId(java.lang.String externalUserId) {
		_userIdMapper.setExternalUserId(externalUserId);
	}

	@Override
	public boolean isNew() {
		return _userIdMapper.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_userIdMapper.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _userIdMapper.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_userIdMapper.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _userIdMapper.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _userIdMapper.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_userIdMapper.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _userIdMapper.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_userIdMapper.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_userIdMapper.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_userIdMapper.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new UserIdMapperWrapper((UserIdMapper)_userIdMapper.clone());
	}

	@Override
	public int compareTo(com.liferay.portal.model.UserIdMapper userIdMapper) {
		return _userIdMapper.compareTo(userIdMapper);
	}

	@Override
	public int hashCode() {
		return _userIdMapper.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portal.model.UserIdMapper> toCacheModel() {
		return _userIdMapper.toCacheModel();
	}

	@Override
	public com.liferay.portal.model.UserIdMapper toEscapedModel() {
		return new UserIdMapperWrapper(_userIdMapper.toEscapedModel());
	}

	@Override
	public com.liferay.portal.model.UserIdMapper toUnescapedModel() {
		return new UserIdMapperWrapper(_userIdMapper.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _userIdMapper.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _userIdMapper.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_userIdMapper.persist();
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

		if (Validator.equals(_userIdMapper, userIdMapperWrapper._userIdMapper)) {
			return true;
		}

		return false;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public UserIdMapper getWrappedUserIdMapper() {
		return _userIdMapper;
	}

	@Override
	public UserIdMapper getWrappedModel() {
		return _userIdMapper;
	}

	@Override
	public void resetOriginalValues() {
		_userIdMapper.resetOriginalValues();
	}

	private UserIdMapper _userIdMapper;
}