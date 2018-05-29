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
 * This class is a wrapper for {@link OAuthApplication}.
 * </p>
 *
 * @author Ivica Cardic
 * @see OAuthApplication
 * @generated
 */
@ProviderType
public class OAuthApplicationWrapper implements OAuthApplication,
	ModelWrapper<OAuthApplication> {
	public OAuthApplicationWrapper(OAuthApplication oAuthApplication) {
		_oAuthApplication = oAuthApplication;
	}

	@Override
	public Class<?> getModelClass() {
		return OAuthApplication.class;
	}

	@Override
	public String getModelClassName() {
		return OAuthApplication.class.getName();
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

	@Override
	public Object clone() {
		return new OAuthApplicationWrapper((OAuthApplication)_oAuthApplication.clone());
	}

	@Override
	public int compareTo(OAuthApplication oAuthApplication) {
		return _oAuthApplication.compareTo(oAuthApplication);
	}

	/**
	* Returns the access level of this o auth application.
	*
	* @return the access level of this o auth application
	*/
	@Override
	public int getAccessLevel() {
		return _oAuthApplication.getAccessLevel();
	}

	@Override
	public String getAccessLevelLabel() {
		return _oAuthApplication.getAccessLevelLabel();
	}

	/**
	* Returns the callback uri of this o auth application.
	*
	* @return the callback uri of this o auth application
	*/
	@Override
	public String getCallbackURI() {
		return _oAuthApplication.getCallbackURI();
	}

	/**
	* Returns the company ID of this o auth application.
	*
	* @return the company ID of this o auth application
	*/
	@Override
	public long getCompanyId() {
		return _oAuthApplication.getCompanyId();
	}

	/**
	* Returns the consumer key of this o auth application.
	*
	* @return the consumer key of this o auth application
	*/
	@Override
	public String getConsumerKey() {
		return _oAuthApplication.getConsumerKey();
	}

	/**
	* Returns the consumer secret of this o auth application.
	*
	* @return the consumer secret of this o auth application
	*/
	@Override
	public String getConsumerSecret() {
		return _oAuthApplication.getConsumerSecret();
	}

	/**
	* Returns the create date of this o auth application.
	*
	* @return the create date of this o auth application
	*/
	@Override
	public Date getCreateDate() {
		return _oAuthApplication.getCreateDate();
	}

	/**
	* Returns the description of this o auth application.
	*
	* @return the description of this o auth application
	*/
	@Override
	public String getDescription() {
		return _oAuthApplication.getDescription();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _oAuthApplication.getExpandoBridge();
	}

	/**
	* Returns the logo ID of this o auth application.
	*
	* @return the logo ID of this o auth application
	*/
	@Override
	public long getLogoId() {
		return _oAuthApplication.getLogoId();
	}

	/**
	* Returns the modified date of this o auth application.
	*
	* @return the modified date of this o auth application
	*/
	@Override
	public Date getModifiedDate() {
		return _oAuthApplication.getModifiedDate();
	}

	/**
	* Returns the name of this o auth application.
	*
	* @return the name of this o auth application
	*/
	@Override
	public String getName() {
		return _oAuthApplication.getName();
	}

	/**
	* Returns the o auth application ID of this o auth application.
	*
	* @return the o auth application ID of this o auth application
	*/
	@Override
	public long getOAuthApplicationId() {
		return _oAuthApplication.getOAuthApplicationId();
	}

	/**
	* Returns the primary key of this o auth application.
	*
	* @return the primary key of this o auth application
	*/
	@Override
	public long getPrimaryKey() {
		return _oAuthApplication.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _oAuthApplication.getPrimaryKeyObj();
	}

	/**
	* Returns the shareable access token of this o auth application.
	*
	* @return the shareable access token of this o auth application
	*/
	@Override
	public boolean getShareableAccessToken() {
		return _oAuthApplication.getShareableAccessToken();
	}

	/**
	* Returns the user ID of this o auth application.
	*
	* @return the user ID of this o auth application
	*/
	@Override
	public long getUserId() {
		return _oAuthApplication.getUserId();
	}

	/**
	* Returns the user name of this o auth application.
	*
	* @return the user name of this o auth application
	*/
	@Override
	public String getUserName() {
		return _oAuthApplication.getUserName();
	}

	/**
	* Returns the user uuid of this o auth application.
	*
	* @return the user uuid of this o auth application
	*/
	@Override
	public String getUserUuid() {
		return _oAuthApplication.getUserUuid();
	}

	/**
	* Returns the website url of this o auth application.
	*
	* @return the website url of this o auth application
	*/
	@Override
	public String getWebsiteURL() {
		return _oAuthApplication.getWebsiteURL();
	}

	@Override
	public int hashCode() {
		return _oAuthApplication.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _oAuthApplication.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _oAuthApplication.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _oAuthApplication.isNew();
	}

	/**
	* Returns <code>true</code> if this o auth application is shareable access token.
	*
	* @return <code>true</code> if this o auth application is shareable access token; <code>false</code> otherwise
	*/
	@Override
	public boolean isShareableAccessToken() {
		return _oAuthApplication.isShareableAccessToken();
	}

	@Override
	public void persist() {
		_oAuthApplication.persist();
	}

	/**
	* Sets the access level of this o auth application.
	*
	* @param accessLevel the access level of this o auth application
	*/
	@Override
	public void setAccessLevel(int accessLevel) {
		_oAuthApplication.setAccessLevel(accessLevel);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_oAuthApplication.setCachedModel(cachedModel);
	}

	/**
	* Sets the callback uri of this o auth application.
	*
	* @param callbackURI the callback uri of this o auth application
	*/
	@Override
	public void setCallbackURI(String callbackURI) {
		_oAuthApplication.setCallbackURI(callbackURI);
	}

	/**
	* Sets the company ID of this o auth application.
	*
	* @param companyId the company ID of this o auth application
	*/
	@Override
	public void setCompanyId(long companyId) {
		_oAuthApplication.setCompanyId(companyId);
	}

	/**
	* Sets the consumer key of this o auth application.
	*
	* @param consumerKey the consumer key of this o auth application
	*/
	@Override
	public void setConsumerKey(String consumerKey) {
		_oAuthApplication.setConsumerKey(consumerKey);
	}

	/**
	* Sets the consumer secret of this o auth application.
	*
	* @param consumerSecret the consumer secret of this o auth application
	*/
	@Override
	public void setConsumerSecret(String consumerSecret) {
		_oAuthApplication.setConsumerSecret(consumerSecret);
	}

	/**
	* Sets the create date of this o auth application.
	*
	* @param createDate the create date of this o auth application
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_oAuthApplication.setCreateDate(createDate);
	}

	/**
	* Sets the description of this o auth application.
	*
	* @param description the description of this o auth application
	*/
	@Override
	public void setDescription(String description) {
		_oAuthApplication.setDescription(description);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_oAuthApplication.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_oAuthApplication.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_oAuthApplication.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the logo ID of this o auth application.
	*
	* @param logoId the logo ID of this o auth application
	*/
	@Override
	public void setLogoId(long logoId) {
		_oAuthApplication.setLogoId(logoId);
	}

	/**
	* Sets the modified date of this o auth application.
	*
	* @param modifiedDate the modified date of this o auth application
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_oAuthApplication.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this o auth application.
	*
	* @param name the name of this o auth application
	*/
	@Override
	public void setName(String name) {
		_oAuthApplication.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_oAuthApplication.setNew(n);
	}

	/**
	* Sets the o auth application ID of this o auth application.
	*
	* @param oAuthApplicationId the o auth application ID of this o auth application
	*/
	@Override
	public void setOAuthApplicationId(long oAuthApplicationId) {
		_oAuthApplication.setOAuthApplicationId(oAuthApplicationId);
	}

	/**
	* Sets the primary key of this o auth application.
	*
	* @param primaryKey the primary key of this o auth application
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_oAuthApplication.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_oAuthApplication.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets whether this o auth application is shareable access token.
	*
	* @param shareableAccessToken the shareable access token of this o auth application
	*/
	@Override
	public void setShareableAccessToken(boolean shareableAccessToken) {
		_oAuthApplication.setShareableAccessToken(shareableAccessToken);
	}

	/**
	* Sets the user ID of this o auth application.
	*
	* @param userId the user ID of this o auth application
	*/
	@Override
	public void setUserId(long userId) {
		_oAuthApplication.setUserId(userId);
	}

	/**
	* Sets the user name of this o auth application.
	*
	* @param userName the user name of this o auth application
	*/
	@Override
	public void setUserName(String userName) {
		_oAuthApplication.setUserName(userName);
	}

	/**
	* Sets the user uuid of this o auth application.
	*
	* @param userUuid the user uuid of this o auth application
	*/
	@Override
	public void setUserUuid(String userUuid) {
		_oAuthApplication.setUserUuid(userUuid);
	}

	/**
	* Sets the website url of this o auth application.
	*
	* @param websiteURL the website url of this o auth application
	*/
	@Override
	public void setWebsiteURL(String websiteURL) {
		_oAuthApplication.setWebsiteURL(websiteURL);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<OAuthApplication> toCacheModel() {
		return _oAuthApplication.toCacheModel();
	}

	@Override
	public OAuthApplication toEscapedModel() {
		return new OAuthApplicationWrapper(_oAuthApplication.toEscapedModel());
	}

	@Override
	public String toString() {
		return _oAuthApplication.toString();
	}

	@Override
	public OAuthApplication toUnescapedModel() {
		return new OAuthApplicationWrapper(_oAuthApplication.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _oAuthApplication.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof OAuthApplicationWrapper)) {
			return false;
		}

		OAuthApplicationWrapper oAuthApplicationWrapper = (OAuthApplicationWrapper)obj;

		if (Objects.equals(_oAuthApplication,
					oAuthApplicationWrapper._oAuthApplication)) {
			return true;
		}

		return false;
	}

	@Override
	public OAuthApplication getWrappedModel() {
		return _oAuthApplication;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _oAuthApplication.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _oAuthApplication.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_oAuthApplication.resetOriginalValues();
	}

	private final OAuthApplication _oAuthApplication;
}