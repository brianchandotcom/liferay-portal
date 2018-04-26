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

package com.liferay.oauth.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link OAuthUser}.
 * </p>
 *
 * @author Ivica Cardic
 * @see OAuthUser
 * @generated
 */
@ProviderType
public class OAuthUserWrapper implements OAuthUser, ModelWrapper<OAuthUser> {
	public OAuthUserWrapper(OAuthUser oAuthUser) {
		_oAuthUser = oAuthUser;
	}

	@Override
	public Class<?> getModelClass() {
		return OAuthUser.class;
	}

	@Override
	public String getModelClassName() {
		return OAuthUser.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("oAuthUserId", getOAuthUserId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("oAuthApplicationId", getOAuthApplicationId());
		attributes.put("accessToken", getAccessToken());
		attributes.put("accessSecret", getAccessSecret());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long oAuthUserId = (Long)attributes.get("oAuthUserId");

		if (oAuthUserId != null) {
			setOAuthUserId(oAuthUserId);
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

		Long oAuthApplicationId = (Long)attributes.get("oAuthApplicationId");

		if (oAuthApplicationId != null) {
			setOAuthApplicationId(oAuthApplicationId);
		}

		String accessToken = (String)attributes.get("accessToken");

		if (accessToken != null) {
			setAccessToken(accessToken);
		}

		String accessSecret = (String)attributes.get("accessSecret");

		if (accessSecret != null) {
			setAccessSecret(accessSecret);
		}
	}

	@Override
	public Object clone() {
		return new OAuthUserWrapper((OAuthUser)_oAuthUser.clone());
	}

	@Override
	public int compareTo(OAuthUser oAuthUser) {
		return _oAuthUser.compareTo(oAuthUser);
	}

	/**
	* Returns the access secret of this o auth user.
	*
	* @return the access secret of this o auth user
	*/
	@Override
	public String getAccessSecret() {
		return _oAuthUser.getAccessSecret();
	}

	/**
	* Returns the access token of this o auth user.
	*
	* @return the access token of this o auth user
	*/
	@Override
	public String getAccessToken() {
		return _oAuthUser.getAccessToken();
	}

	/**
	* Returns the company ID of this o auth user.
	*
	* @return the company ID of this o auth user
	*/
	@Override
	public long getCompanyId() {
		return _oAuthUser.getCompanyId();
	}

	/**
	* Returns the create date of this o auth user.
	*
	* @return the create date of this o auth user
	*/
	@Override
	public Date getCreateDate() {
		return _oAuthUser.getCreateDate();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _oAuthUser.getExpandoBridge();
	}

	/**
	* Returns the modified date of this o auth user.
	*
	* @return the modified date of this o auth user
	*/
	@Override
	public Date getModifiedDate() {
		return _oAuthUser.getModifiedDate();
	}

	/**
	* Returns the o auth application ID of this o auth user.
	*
	* @return the o auth application ID of this o auth user
	*/
	@Override
	public long getOAuthApplicationId() {
		return _oAuthUser.getOAuthApplicationId();
	}

	/**
	* Returns the o auth user ID of this o auth user.
	*
	* @return the o auth user ID of this o auth user
	*/
	@Override
	public long getOAuthUserId() {
		return _oAuthUser.getOAuthUserId();
	}

	/**
	* Returns the o auth user uuid of this o auth user.
	*
	* @return the o auth user uuid of this o auth user
	*/
	@Override
	public String getOAuthUserUuid() {
		return _oAuthUser.getOAuthUserUuid();
	}

	/**
	* Returns the primary key of this o auth user.
	*
	* @return the primary key of this o auth user
	*/
	@Override
	public long getPrimaryKey() {
		return _oAuthUser.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _oAuthUser.getPrimaryKeyObj();
	}

	/**
	* Returns the user ID of this o auth user.
	*
	* @return the user ID of this o auth user
	*/
	@Override
	public long getUserId() {
		return _oAuthUser.getUserId();
	}

	/**
	* Returns the user name of this o auth user.
	*
	* @return the user name of this o auth user
	*/
	@Override
	public String getUserName() {
		return _oAuthUser.getUserName();
	}

	/**
	* Returns the user uuid of this o auth user.
	*
	* @return the user uuid of this o auth user
	*/
	@Override
	public String getUserUuid() {
		return _oAuthUser.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _oAuthUser.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _oAuthUser.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _oAuthUser.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _oAuthUser.isNew();
	}

	@Override
	public void persist() {
		_oAuthUser.persist();
	}

	/**
	* Sets the access secret of this o auth user.
	*
	* @param accessSecret the access secret of this o auth user
	*/
	@Override
	public void setAccessSecret(String accessSecret) {
		_oAuthUser.setAccessSecret(accessSecret);
	}

	/**
	* Sets the access token of this o auth user.
	*
	* @param accessToken the access token of this o auth user
	*/
	@Override
	public void setAccessToken(String accessToken) {
		_oAuthUser.setAccessToken(accessToken);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_oAuthUser.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this o auth user.
	*
	* @param companyId the company ID of this o auth user
	*/
	@Override
	public void setCompanyId(long companyId) {
		_oAuthUser.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this o auth user.
	*
	* @param createDate the create date of this o auth user
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_oAuthUser.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_oAuthUser.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_oAuthUser.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_oAuthUser.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the modified date of this o auth user.
	*
	* @param modifiedDate the modified date of this o auth user
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_oAuthUser.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_oAuthUser.setNew(n);
	}

	/**
	* Sets the o auth application ID of this o auth user.
	*
	* @param oAuthApplicationId the o auth application ID of this o auth user
	*/
	@Override
	public void setOAuthApplicationId(long oAuthApplicationId) {
		_oAuthUser.setOAuthApplicationId(oAuthApplicationId);
	}

	/**
	* Sets the o auth user ID of this o auth user.
	*
	* @param oAuthUserId the o auth user ID of this o auth user
	*/
	@Override
	public void setOAuthUserId(long oAuthUserId) {
		_oAuthUser.setOAuthUserId(oAuthUserId);
	}

	/**
	* Sets the o auth user uuid of this o auth user.
	*
	* @param oAuthUserUuid the o auth user uuid of this o auth user
	*/
	@Override
	public void setOAuthUserUuid(String oAuthUserUuid) {
		_oAuthUser.setOAuthUserUuid(oAuthUserUuid);
	}

	/**
	* Sets the primary key of this o auth user.
	*
	* @param primaryKey the primary key of this o auth user
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_oAuthUser.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_oAuthUser.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the user ID of this o auth user.
	*
	* @param userId the user ID of this o auth user
	*/
	@Override
	public void setUserId(long userId) {
		_oAuthUser.setUserId(userId);
	}

	/**
	* Sets the user name of this o auth user.
	*
	* @param userName the user name of this o auth user
	*/
	@Override
	public void setUserName(String userName) {
		_oAuthUser.setUserName(userName);
	}

	/**
	* Sets the user uuid of this o auth user.
	*
	* @param userUuid the user uuid of this o auth user
	*/
	@Override
	public void setUserUuid(String userUuid) {
		_oAuthUser.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<OAuthUser> toCacheModel() {
		return _oAuthUser.toCacheModel();
	}

	@Override
	public OAuthUser toEscapedModel() {
		return new OAuthUserWrapper(_oAuthUser.toEscapedModel());
	}

	@Override
	public String toString() {
		return _oAuthUser.toString();
	}

	@Override
	public OAuthUser toUnescapedModel() {
		return new OAuthUserWrapper(_oAuthUser.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _oAuthUser.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof OAuthUserWrapper)) {
			return false;
		}

		OAuthUserWrapper oAuthUserWrapper = (OAuthUserWrapper)obj;

		if (Objects.equals(_oAuthUser, oAuthUserWrapper._oAuthUser)) {
			return true;
		}

		return false;
	}

	@Override
	public OAuthUser getWrappedModel() {
		return _oAuthUser;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _oAuthUser.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _oAuthUser.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_oAuthUser.resetOriginalValues();
	}

	private final OAuthUser _oAuthUser;
}