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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.oauth.service.http.OAuthApplicationServiceSoap}.
 *
 * @author Ivica Cardic
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class OAuthApplicationSoap implements Serializable {

	public static OAuthApplicationSoap toSoapModel(OAuthApplication model) {
		OAuthApplicationSoap soapModel = new OAuthApplicationSoap();

		soapModel.setOAuthApplicationId(model.getOAuthApplicationId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setName(model.getName());
		soapModel.setDescription(model.getDescription());
		soapModel.setConsumerKey(model.getConsumerKey());
		soapModel.setConsumerSecret(model.getConsumerSecret());
		soapModel.setAccessLevel(model.getAccessLevel());
		soapModel.setLogoId(model.getLogoId());
		soapModel.setShareableAccessToken(model.isShareableAccessToken());
		soapModel.setCallbackURI(model.getCallbackURI());
		soapModel.setWebsiteURL(model.getWebsiteURL());

		return soapModel;
	}

	public static OAuthApplicationSoap[] toSoapModels(
		OAuthApplication[] models) {

		OAuthApplicationSoap[] soapModels =
			new OAuthApplicationSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static OAuthApplicationSoap[][] toSoapModels(
		OAuthApplication[][] models) {

		OAuthApplicationSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new OAuthApplicationSoap[models.length][models[0].length];
		}
		else {
			soapModels = new OAuthApplicationSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static OAuthApplicationSoap[] toSoapModels(
		List<OAuthApplication> models) {

		List<OAuthApplicationSoap> soapModels =
			new ArrayList<OAuthApplicationSoap>(models.size());

		for (OAuthApplication model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new OAuthApplicationSoap[soapModels.size()]);
	}

	public OAuthApplicationSoap() {
	}

	public long getPrimaryKey() {
		return _oAuthApplicationId;
	}

	public void setPrimaryKey(long pk) {
		setOAuthApplicationId(pk);
	}

	public long getOAuthApplicationId() {
		return _oAuthApplicationId;
	}

	public void setOAuthApplicationId(long oAuthApplicationId) {
		_oAuthApplicationId = oAuthApplicationId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public String getUserName() {
		return _userName;
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getDescription() {
		return _description;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public String getConsumerKey() {
		return _consumerKey;
	}

	public void setConsumerKey(String consumerKey) {
		_consumerKey = consumerKey;
	}

	public String getConsumerSecret() {
		return _consumerSecret;
	}

	public void setConsumerSecret(String consumerSecret) {
		_consumerSecret = consumerSecret;
	}

	public int getAccessLevel() {
		return _accessLevel;
	}

	public void setAccessLevel(int accessLevel) {
		_accessLevel = accessLevel;
	}

	public long getLogoId() {
		return _logoId;
	}

	public void setLogoId(long logoId) {
		_logoId = logoId;
	}

	public boolean getShareableAccessToken() {
		return _shareableAccessToken;
	}

	public boolean isShareableAccessToken() {
		return _shareableAccessToken;
	}

	public void setShareableAccessToken(boolean shareableAccessToken) {
		_shareableAccessToken = shareableAccessToken;
	}

	public String getCallbackURI() {
		return _callbackURI;
	}

	public void setCallbackURI(String callbackURI) {
		_callbackURI = callbackURI;
	}

	public String getWebsiteURL() {
		return _websiteURL;
	}

	public void setWebsiteURL(String websiteURL) {
		_websiteURL = websiteURL;
	}

	private long _oAuthApplicationId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _name;
	private String _description;
	private String _consumerKey;
	private String _consumerSecret;
	private int _accessLevel;
	private long _logoId;
	private boolean _shareableAccessToken;
	private String _callbackURI;
	private String _websiteURL;

}