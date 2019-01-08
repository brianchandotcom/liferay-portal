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
 * This class is a wrapper for {@link OAuthApplication}.
 * </p>
 *
 * @author Ivica Cardic
 * @see OAuthApplication
 * @generated
 */
@ProviderType
public class OAuthApplicationWrapper extends BaseModelWrapper<OAuthApplication>
	implements OAuthApplication, ModelWrapper<OAuthApplication> {
	public OAuthApplicationWrapper(OAuthApplication oAuthApplication) {
		super(oAuthApplication);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("oAuthApplicationId", getOAuthApplicationId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("consumerKey", getConsumerKey());
		attributes.put("consumerSecret", getConsumerSecret());
		attributes.put("accessLevel", getAccessLevel());
		attributes.put("logoId", getLogoId());
		attributes.put("shareableAccessToken", isShareableAccessToken());
		attributes.put("callbackURI", getCallbackURI());
		attributes.put("websiteURL", getWebsiteURL());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long oAuthApplicationId = (Long)attributes.get("oAuthApplicationId");

		if (oAuthApplicationId != null) {
			setOAuthApplicationId(oAuthApplicationId);
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

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		String consumerKey = (String)attributes.get("consumerKey");

		if (consumerKey != null) {
			setConsumerKey(consumerKey);
		}

		String consumerSecret = (String)attributes.get("consumerSecret");

		if (consumerSecret != null) {
			setConsumerSecret(consumerSecret);
		}

		Integer accessLevel = (Integer)attributes.get("accessLevel");

		if (accessLevel != null) {
			setAccessLevel(accessLevel);
		}

		Long logoId = (Long)attributes.get("logoId");

		if (logoId != null) {
			setLogoId(logoId);
		}

		Boolean shareableAccessToken = (Boolean)attributes.get(
				"shareableAccessToken");

		if (shareableAccessToken != null) {
			setShareableAccessToken(shareableAccessToken);
		}

		String callbackURI = (String)attributes.get("callbackURI");

		if (callbackURI != null) {
			setCallbackURI(callbackURI);
		}

		String websiteURL = (String)attributes.get("websiteURL");

		if (websiteURL != null) {
			setWebsiteURL(websiteURL);
		}
	}

	/**
	* Returns the access level of this o auth application.
	*
	* @return the access level of this o auth application
	*/
	@Override
	public int getAccessLevel() {
		return model.getAccessLevel();
	}

	@Override
	public String getAccessLevelLabel() {
		return model.getAccessLevelLabel();
	}

	/**
	* Returns the callback uri of this o auth application.
	*
	* @return the callback uri of this o auth application
	*/
	@Override
	public String getCallbackURI() {
		return model.getCallbackURI();
	}

	/**
	* Returns the company ID of this o auth application.
	*
	* @return the company ID of this o auth application
	*/
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	* Returns the consumer key of this o auth application.
	*
	* @return the consumer key of this o auth application
	*/
	@Override
	public String getConsumerKey() {
		return model.getConsumerKey();
	}

	/**
	* Returns the consumer secret of this o auth application.
	*
	* @return the consumer secret of this o auth application
	*/
	@Override
	public String getConsumerSecret() {
		return model.getConsumerSecret();
	}

	/**
	* Returns the create date of this o auth application.
	*
	* @return the create date of this o auth application
	*/
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	* Returns the description of this o auth application.
	*
	* @return the description of this o auth application
	*/
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	/**
	* Returns the logo ID of this o auth application.
	*
	* @return the logo ID of this o auth application
	*/
	@Override
	public long getLogoId() {
		return model.getLogoId();
	}

	/**
	* Returns the modified date of this o auth application.
	*
	* @return the modified date of this o auth application
	*/
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	* Returns the name of this o auth application.
	*
	* @return the name of this o auth application
	*/
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	* Returns the o auth application ID of this o auth application.
	*
	* @return the o auth application ID of this o auth application
	*/
	@Override
	public long getOAuthApplicationId() {
		return model.getOAuthApplicationId();
	}

	/**
	* Returns the primary key of this o auth application.
	*
	* @return the primary key of this o auth application
	*/
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	* Returns the shareable access token of this o auth application.
	*
	* @return the shareable access token of this o auth application
	*/
	@Override
	public boolean getShareableAccessToken() {
		return model.getShareableAccessToken();
	}

	/**
	* Returns the user ID of this o auth application.
	*
	* @return the user ID of this o auth application
	*/
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	* Returns the user name of this o auth application.
	*
	* @return the user name of this o auth application
	*/
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	* Returns the user uuid of this o auth application.
	*
	* @return the user uuid of this o auth application
	*/
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	* Returns the website url of this o auth application.
	*
	* @return the website url of this o auth application
	*/
	@Override
	public String getWebsiteURL() {
		return model.getWebsiteURL();
	}

	/**
	* Returns <code>true</code> if this o auth application is shareable access token.
	*
	* @return <code>true</code> if this o auth application is shareable access token; <code>false</code> otherwise
	*/
	@Override
	public boolean isShareableAccessToken() {
		return model.isShareableAccessToken();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	* Sets the access level of this o auth application.
	*
	* @param accessLevel the access level of this o auth application
	*/
	@Override
	public void setAccessLevel(int accessLevel) {
		model.setAccessLevel(accessLevel);
	}

	/**
	* Sets the callback uri of this o auth application.
	*
	* @param callbackURI the callback uri of this o auth application
	*/
	@Override
	public void setCallbackURI(String callbackURI) {
		model.setCallbackURI(callbackURI);
	}

	/**
	* Sets the company ID of this o auth application.
	*
	* @param companyId the company ID of this o auth application
	*/
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	* Sets the consumer key of this o auth application.
	*
	* @param consumerKey the consumer key of this o auth application
	*/
	@Override
	public void setConsumerKey(String consumerKey) {
		model.setConsumerKey(consumerKey);
	}

	/**
	* Sets the consumer secret of this o auth application.
	*
	* @param consumerSecret the consumer secret of this o auth application
	*/
	@Override
	public void setConsumerSecret(String consumerSecret) {
		model.setConsumerSecret(consumerSecret);
	}

	/**
	* Sets the create date of this o auth application.
	*
	* @param createDate the create date of this o auth application
	*/
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	* Sets the description of this o auth application.
	*
	* @param description the description of this o auth application
	*/
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	* Sets the logo ID of this o auth application.
	*
	* @param logoId the logo ID of this o auth application
	*/
	@Override
	public void setLogoId(long logoId) {
		model.setLogoId(logoId);
	}

	/**
	* Sets the modified date of this o auth application.
	*
	* @param modifiedDate the modified date of this o auth application
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this o auth application.
	*
	* @param name the name of this o auth application
	*/
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	* Sets the o auth application ID of this o auth application.
	*
	* @param oAuthApplicationId the o auth application ID of this o auth application
	*/
	@Override
	public void setOAuthApplicationId(long oAuthApplicationId) {
		model.setOAuthApplicationId(oAuthApplicationId);
	}

	/**
	* Sets the primary key of this o auth application.
	*
	* @param primaryKey the primary key of this o auth application
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	* Sets whether this o auth application is shareable access token.
	*
	* @param shareableAccessToken the shareable access token of this o auth application
	*/
	@Override
	public void setShareableAccessToken(boolean shareableAccessToken) {
		model.setShareableAccessToken(shareableAccessToken);
	}

	/**
	* Sets the user ID of this o auth application.
	*
	* @param userId the user ID of this o auth application
	*/
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	* Sets the user name of this o auth application.
	*
	* @param userName the user name of this o auth application
	*/
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	* Sets the user uuid of this o auth application.
	*
	* @param userUuid the user uuid of this o auth application
	*/
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	* Sets the website url of this o auth application.
	*
	* @param websiteURL the website url of this o auth application
	*/
	@Override
	public void setWebsiteURL(String websiteURL) {
		model.setWebsiteURL(websiteURL);
	}

	@Override
	protected OAuthApplicationWrapper wrap(OAuthApplication oAuthApplication) {
		return new OAuthApplicationWrapper(oAuthApplication);
	}
}