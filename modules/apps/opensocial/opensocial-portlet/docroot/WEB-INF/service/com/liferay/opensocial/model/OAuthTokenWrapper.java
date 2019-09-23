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

package com.liferay.opensocial.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link OAuthToken}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see OAuthToken
 * @generated
 */
public class OAuthTokenWrapper
	extends BaseModelWrapper<OAuthToken>
	implements ModelWrapper<OAuthToken>, OAuthToken {

	public OAuthTokenWrapper(OAuthToken oAuthToken) {
		super(oAuthToken);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("oAuthTokenId", getOAuthTokenId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("gadgetKey", getGadgetKey());
		attributes.put("serviceName", getServiceName());
		attributes.put("moduleId", getModuleId());
		attributes.put("accessToken", getAccessToken());
		attributes.put("tokenName", getTokenName());
		attributes.put("tokenSecret", getTokenSecret());
		attributes.put("sessionHandle", getSessionHandle());
		attributes.put("expiration", getExpiration());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long oAuthTokenId = (Long)attributes.get("oAuthTokenId");

		if (oAuthTokenId != null) {
			setOAuthTokenId(oAuthTokenId);
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

		String gadgetKey = (String)attributes.get("gadgetKey");

		if (gadgetKey != null) {
			setGadgetKey(gadgetKey);
		}

		String serviceName = (String)attributes.get("serviceName");

		if (serviceName != null) {
			setServiceName(serviceName);
		}

		Long moduleId = (Long)attributes.get("moduleId");

		if (moduleId != null) {
			setModuleId(moduleId);
		}

		String accessToken = (String)attributes.get("accessToken");

		if (accessToken != null) {
			setAccessToken(accessToken);
		}

		String tokenName = (String)attributes.get("tokenName");

		if (tokenName != null) {
			setTokenName(tokenName);
		}

		String tokenSecret = (String)attributes.get("tokenSecret");

		if (tokenSecret != null) {
			setTokenSecret(tokenSecret);
		}

		String sessionHandle = (String)attributes.get("sessionHandle");

		if (sessionHandle != null) {
			setSessionHandle(sessionHandle);
		}

		Long expiration = (Long)attributes.get("expiration");

		if (expiration != null) {
			setExpiration(expiration);
		}
	}

	/**
	 * Returns the access token of this o auth token.
	 *
	 * @return the access token of this o auth token
	 */
	@Override
	public String getAccessToken() {
		return model.getAccessToken();
	}

	/**
	 * Returns the company ID of this o auth token.
	 *
	 * @return the company ID of this o auth token
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this o auth token.
	 *
	 * @return the create date of this o auth token
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the expiration of this o auth token.
	 *
	 * @return the expiration of this o auth token
	 */
	@Override
	public long getExpiration() {
		return model.getExpiration();
	}

	/**
	 * Returns the gadget key of this o auth token.
	 *
	 * @return the gadget key of this o auth token
	 */
	@Override
	public String getGadgetKey() {
		return model.getGadgetKey();
	}

	/**
	 * Returns the modified date of this o auth token.
	 *
	 * @return the modified date of this o auth token
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the module ID of this o auth token.
	 *
	 * @return the module ID of this o auth token
	 */
	@Override
	public long getModuleId() {
		return model.getModuleId();
	}

	/**
	 * Returns the o auth token ID of this o auth token.
	 *
	 * @return the o auth token ID of this o auth token
	 */
	@Override
	public long getOAuthTokenId() {
		return model.getOAuthTokenId();
	}

	/**
	 * Returns the primary key of this o auth token.
	 *
	 * @return the primary key of this o auth token
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the service name of this o auth token.
	 *
	 * @return the service name of this o auth token
	 */
	@Override
	public String getServiceName() {
		return model.getServiceName();
	}

	/**
	 * Returns the session handle of this o auth token.
	 *
	 * @return the session handle of this o auth token
	 */
	@Override
	public String getSessionHandle() {
		return model.getSessionHandle();
	}

	/**
	 * Returns the token name of this o auth token.
	 *
	 * @return the token name of this o auth token
	 */
	@Override
	public String getTokenName() {
		return model.getTokenName();
	}

	/**
	 * Returns the token secret of this o auth token.
	 *
	 * @return the token secret of this o auth token
	 */
	@Override
	public String getTokenSecret() {
		return model.getTokenSecret();
	}

	/**
	 * Returns the user ID of this o auth token.
	 *
	 * @return the user ID of this o auth token
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this o auth token.
	 *
	 * @return the user name of this o auth token
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this o auth token.
	 *
	 * @return the user uuid of this o auth token
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a o auth token model instance should use the <code>OAuthToken</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the access token of this o auth token.
	 *
	 * @param accessToken the access token of this o auth token
	 */
	@Override
	public void setAccessToken(String accessToken) {
		model.setAccessToken(accessToken);
	}

	/**
	 * Sets the company ID of this o auth token.
	 *
	 * @param companyId the company ID of this o auth token
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this o auth token.
	 *
	 * @param createDate the create date of this o auth token
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the expiration of this o auth token.
	 *
	 * @param expiration the expiration of this o auth token
	 */
	@Override
	public void setExpiration(long expiration) {
		model.setExpiration(expiration);
	}

	/**
	 * Sets the gadget key of this o auth token.
	 *
	 * @param gadgetKey the gadget key of this o auth token
	 */
	@Override
	public void setGadgetKey(String gadgetKey) {
		model.setGadgetKey(gadgetKey);
	}

	/**
	 * Sets the modified date of this o auth token.
	 *
	 * @param modifiedDate the modified date of this o auth token
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the module ID of this o auth token.
	 *
	 * @param moduleId the module ID of this o auth token
	 */
	@Override
	public void setModuleId(long moduleId) {
		model.setModuleId(moduleId);
	}

	/**
	 * Sets the o auth token ID of this o auth token.
	 *
	 * @param oAuthTokenId the o auth token ID of this o auth token
	 */
	@Override
	public void setOAuthTokenId(long oAuthTokenId) {
		model.setOAuthTokenId(oAuthTokenId);
	}

	/**
	 * Sets the primary key of this o auth token.
	 *
	 * @param primaryKey the primary key of this o auth token
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the service name of this o auth token.
	 *
	 * @param serviceName the service name of this o auth token
	 */
	@Override
	public void setServiceName(String serviceName) {
		model.setServiceName(serviceName);
	}

	/**
	 * Sets the session handle of this o auth token.
	 *
	 * @param sessionHandle the session handle of this o auth token
	 */
	@Override
	public void setSessionHandle(String sessionHandle) {
		model.setSessionHandle(sessionHandle);
	}

	/**
	 * Sets the token name of this o auth token.
	 *
	 * @param tokenName the token name of this o auth token
	 */
	@Override
	public void setTokenName(String tokenName) {
		model.setTokenName(tokenName);
	}

	/**
	 * Sets the token secret of this o auth token.
	 *
	 * @param tokenSecret the token secret of this o auth token
	 */
	@Override
	public void setTokenSecret(String tokenSecret) {
		model.setTokenSecret(tokenSecret);
	}

	/**
	 * Sets the user ID of this o auth token.
	 *
	 * @param userId the user ID of this o auth token
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this o auth token.
	 *
	 * @param userName the user name of this o auth token
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this o auth token.
	 *
	 * @param userUuid the user uuid of this o auth token
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected OAuthTokenWrapper wrap(OAuthToken oAuthToken) {
		return new OAuthTokenWrapper(oAuthToken);
	}

}