/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.oauth.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
public class OAuthUserWrapper extends BaseModelWrapper<OAuthUser>
	implements OAuthUser, ModelWrapper<OAuthUser> {
	public OAuthUserWrapper(OAuthUser oAuthUser) {
		super(oAuthUser);
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

	/**
	* Returns the access secret of this o auth user.
	*
	* @return the access secret of this o auth user
	*/
	@Override
	public String getAccessSecret() {
		return model.getAccessSecret();
	}

	/**
	* Returns the access token of this o auth user.
	*
	* @return the access token of this o auth user
	*/
	@Override
	public String getAccessToken() {
		return model.getAccessToken();
	}

	/**
	* Returns the company ID of this o auth user.
	*
	* @return the company ID of this o auth user
	*/
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	* Returns the create date of this o auth user.
	*
	* @return the create date of this o auth user
	*/
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	* Returns the modified date of this o auth user.
	*
	* @return the modified date of this o auth user
	*/
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	* Returns the o auth application ID of this o auth user.
	*
	* @return the o auth application ID of this o auth user
	*/
	@Override
	public long getOAuthApplicationId() {
		return model.getOAuthApplicationId();
	}

	/**
	* Returns the o auth user ID of this o auth user.
	*
	* @return the o auth user ID of this o auth user
	*/
	@Override
	public long getOAuthUserId() {
		return model.getOAuthUserId();
	}

	/**
	* Returns the o auth user uuid of this o auth user.
	*
	* @return the o auth user uuid of this o auth user
	*/
	@Override
	public String getOAuthUserUuid() {
		return model.getOAuthUserUuid();
	}

	/**
	* Returns the primary key of this o auth user.
	*
	* @return the primary key of this o auth user
	*/
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	* Returns the user ID of this o auth user.
	*
	* @return the user ID of this o auth user
	*/
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	* Returns the user name of this o auth user.
	*
	* @return the user name of this o auth user
	*/
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	* Returns the user uuid of this o auth user.
	*
	* @return the user uuid of this o auth user
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
	* Sets the access secret of this o auth user.
	*
	* @param accessSecret the access secret of this o auth user
	*/
	@Override
	public void setAccessSecret(String accessSecret) {
		model.setAccessSecret(accessSecret);
	}

	/**
	* Sets the access token of this o auth user.
	*
	* @param accessToken the access token of this o auth user
	*/
	@Override
	public void setAccessToken(String accessToken) {
		model.setAccessToken(accessToken);
	}

	/**
	* Sets the company ID of this o auth user.
	*
	* @param companyId the company ID of this o auth user
	*/
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this o auth user.
	*
	* @param createDate the create date of this o auth user
	*/
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	* Sets the modified date of this o auth user.
	*
	* @param modifiedDate the modified date of this o auth user
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the o auth application ID of this o auth user.
	*
	* @param oAuthApplicationId the o auth application ID of this o auth user
	*/
	@Override
	public void setOAuthApplicationId(long oAuthApplicationId) {
		model.setOAuthApplicationId(oAuthApplicationId);
	}

	/**
	* Sets the o auth user ID of this o auth user.
	*
	* @param oAuthUserId the o auth user ID of this o auth user
	*/
	@Override
	public void setOAuthUserId(long oAuthUserId) {
		model.setOAuthUserId(oAuthUserId);
	}

	/**
	* Sets the o auth user uuid of this o auth user.
	*
	* @param oAuthUserUuid the o auth user uuid of this o auth user
	*/
	@Override
	public void setOAuthUserUuid(String oAuthUserUuid) {
		model.setOAuthUserUuid(oAuthUserUuid);
	}

	/**
	* Sets the primary key of this o auth user.
	*
	* @param primaryKey the primary key of this o auth user
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	* Sets the user ID of this o auth user.
	*
	* @param userId the user ID of this o auth user
	*/
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	* Sets the user name of this o auth user.
	*
	* @param userName the user name of this o auth user
	*/
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	* Sets the user uuid of this o auth user.
	*
	* @param userUuid the user uuid of this o auth user
	*/
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected OAuthUserWrapper wrap(OAuthUser oAuthUser) {
		return new OAuthUserWrapper(oAuthUser);
	}
}