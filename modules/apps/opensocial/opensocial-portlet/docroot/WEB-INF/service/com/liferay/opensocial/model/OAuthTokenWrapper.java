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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;

/**
 * <p>
 * This class is a wrapper for {@link OAuthToken}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see OAuthToken
 * @generated
 */
@ProviderType
public class OAuthTokenWrapper extends BaseModelWrapper<OAuthToken>
	implements OAuthToken, ModelWrapper<OAuthToken> {
	public OAuthTokenWrapper(OAuthToken oAuthToken) {
		super(oAuthToken);
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