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

package com.liferay.sharepoint.rest.oauth2.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Adolfo Pérez
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class SharepointOAuth2TokenEntrySoap implements Serializable {

	public static SharepointOAuth2TokenEntrySoap toSoapModel(
		SharepointOAuth2TokenEntry model) {

		SharepointOAuth2TokenEntrySoap soapModel =
			new SharepointOAuth2TokenEntrySoap();

		soapModel.setSharepointOAuth2TokenEntryId(
			model.getSharepointOAuth2TokenEntryId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setAccessToken(model.getAccessToken());
		soapModel.setConfigurationPid(model.getConfigurationPid());
		soapModel.setExpirationDate(model.getExpirationDate());
		soapModel.setRefreshToken(model.getRefreshToken());

		return soapModel;
	}

	public static SharepointOAuth2TokenEntrySoap[] toSoapModels(
		SharepointOAuth2TokenEntry[] models) {

		SharepointOAuth2TokenEntrySoap[] soapModels =
			new SharepointOAuth2TokenEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static SharepointOAuth2TokenEntrySoap[][] toSoapModels(
		SharepointOAuth2TokenEntry[][] models) {

		SharepointOAuth2TokenEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new SharepointOAuth2TokenEntrySoap
					[models.length][models[0].length];
		}
		else {
			soapModels = new SharepointOAuth2TokenEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static SharepointOAuth2TokenEntrySoap[] toSoapModels(
		List<SharepointOAuth2TokenEntry> models) {

		List<SharepointOAuth2TokenEntrySoap> soapModels =
			new ArrayList<SharepointOAuth2TokenEntrySoap>(models.size());

		for (SharepointOAuth2TokenEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new SharepointOAuth2TokenEntrySoap[soapModels.size()]);
	}

	public SharepointOAuth2TokenEntrySoap() {
	}

	public long getPrimaryKey() {
		return _sharepointOAuth2TokenEntryId;
	}

	public void setPrimaryKey(long pk) {
		setSharepointOAuth2TokenEntryId(pk);
	}

	public long getSharepointOAuth2TokenEntryId() {
		return _sharepointOAuth2TokenEntryId;
	}

	public void setSharepointOAuth2TokenEntryId(
		long sharepointOAuth2TokenEntryId) {

		_sharepointOAuth2TokenEntryId = sharepointOAuth2TokenEntryId;
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

	public String getAccessToken() {
		return _accessToken;
	}

	public void setAccessToken(String accessToken) {
		_accessToken = accessToken;
	}

	public String getConfigurationPid() {
		return _configurationPid;
	}

	public void setConfigurationPid(String configurationPid) {
		_configurationPid = configurationPid;
	}

	public Date getExpirationDate() {
		return _expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		_expirationDate = expirationDate;
	}

	public String getRefreshToken() {
		return _refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		_refreshToken = refreshToken;
	}

	private long _sharepointOAuth2TokenEntryId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private String _accessToken;
	private String _configurationPid;
	private Date _expirationDate;
	private String _refreshToken;

}