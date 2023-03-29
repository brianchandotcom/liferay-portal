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

package com.liferay.osb.faro.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Matthew Kong
 * @generated
 */
public class FaroProjectSoap implements Serializable {

	public static FaroProjectSoap toSoapModel(FaroProject model) {
		FaroProjectSoap soapModel = new FaroProjectSoap();

		soapModel.setFaroProjectId(model.getFaroProjectId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateTime(model.getCreateTime());
		soapModel.setModifiedTime(model.getModifiedTime());
		soapModel.setName(model.getName());
		soapModel.setAccountKey(model.getAccountKey());
		soapModel.setAccountName(model.getAccountName());
		soapModel.setCorpProjectName(model.getCorpProjectName());
		soapModel.setCorpProjectUuid(model.getCorpProjectUuid());
		soapModel.setIpAddresses(model.getIpAddresses());
		soapModel.setIncidentReportEmailAddresses(
			model.getIncidentReportEmailAddresses());
		soapModel.setLastAccessTime(model.getLastAccessTime());
		soapModel.setRecommendationsEnabled(model.isRecommendationsEnabled());
		soapModel.setServerLocation(model.getServerLocation());
		soapModel.setServices(model.getServices());
		soapModel.setState(model.getState());
		soapModel.setSubscription(model.getSubscription());
		soapModel.setTimeZoneId(model.getTimeZoneId());
		soapModel.setWeDeployKey(model.getWeDeployKey());

		return soapModel;
	}

	public static FaroProjectSoap[] toSoapModels(FaroProject[] models) {
		FaroProjectSoap[] soapModels = new FaroProjectSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static FaroProjectSoap[][] toSoapModels(FaroProject[][] models) {
		FaroProjectSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new FaroProjectSoap[models.length][models[0].length];
		}
		else {
			soapModels = new FaroProjectSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static FaroProjectSoap[] toSoapModels(List<FaroProject> models) {
		List<FaroProjectSoap> soapModels = new ArrayList<FaroProjectSoap>(
			models.size());

		for (FaroProject model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new FaroProjectSoap[soapModels.size()]);
	}

	public FaroProjectSoap() {
	}

	public long getPrimaryKey() {
		return _faroProjectId;
	}

	public void setPrimaryKey(long pk) {
		setFaroProjectId(pk);
	}

	public long getFaroProjectId() {
		return _faroProjectId;
	}

	public void setFaroProjectId(long faroProjectId) {
		_faroProjectId = faroProjectId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
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

	public long getCreateTime() {
		return _createTime;
	}

	public void setCreateTime(long createTime) {
		_createTime = createTime;
	}

	public long getModifiedTime() {
		return _modifiedTime;
	}

	public void setModifiedTime(long modifiedTime) {
		_modifiedTime = modifiedTime;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getAccountKey() {
		return _accountKey;
	}

	public void setAccountKey(String accountKey) {
		_accountKey = accountKey;
	}

	public String getAccountName() {
		return _accountName;
	}

	public void setAccountName(String accountName) {
		_accountName = accountName;
	}

	public String getCorpProjectName() {
		return _corpProjectName;
	}

	public void setCorpProjectName(String corpProjectName) {
		_corpProjectName = corpProjectName;
	}

	public String getCorpProjectUuid() {
		return _corpProjectUuid;
	}

	public void setCorpProjectUuid(String corpProjectUuid) {
		_corpProjectUuid = corpProjectUuid;
	}

	public String getIpAddresses() {
		return _ipAddresses;
	}

	public void setIpAddresses(String ipAddresses) {
		_ipAddresses = ipAddresses;
	}

	public String getIncidentReportEmailAddresses() {
		return _incidentReportEmailAddresses;
	}

	public void setIncidentReportEmailAddresses(
		String incidentReportEmailAddresses) {

		_incidentReportEmailAddresses = incidentReportEmailAddresses;
	}

	public long getLastAccessTime() {
		return _lastAccessTime;
	}

	public void setLastAccessTime(long lastAccessTime) {
		_lastAccessTime = lastAccessTime;
	}

	public boolean getRecommendationsEnabled() {
		return _recommendationsEnabled;
	}

	public boolean isRecommendationsEnabled() {
		return _recommendationsEnabled;
	}

	public void setRecommendationsEnabled(boolean recommendationsEnabled) {
		_recommendationsEnabled = recommendationsEnabled;
	}

	public String getServerLocation() {
		return _serverLocation;
	}

	public void setServerLocation(String serverLocation) {
		_serverLocation = serverLocation;
	}

	public String getServices() {
		return _services;
	}

	public void setServices(String services) {
		_services = services;
	}

	public String getState() {
		return _state;
	}

	public void setState(String state) {
		_state = state;
	}

	public String getSubscription() {
		return _subscription;
	}

	public void setSubscription(String subscription) {
		_subscription = subscription;
	}

	public String getTimeZoneId() {
		return _timeZoneId;
	}

	public void setTimeZoneId(String timeZoneId) {
		_timeZoneId = timeZoneId;
	}

	public String getWeDeployKey() {
		return _weDeployKey;
	}

	public void setWeDeployKey(String weDeployKey) {
		_weDeployKey = weDeployKey;
	}

	private long _faroProjectId;
	private long _groupId;
	private long _userId;
	private String _userName;
	private long _createTime;
	private long _modifiedTime;
	private String _name;
	private String _accountKey;
	private String _accountName;
	private String _corpProjectName;
	private String _corpProjectUuid;
	private String _ipAddresses;
	private String _incidentReportEmailAddresses;
	private long _lastAccessTime;
	private boolean _recommendationsEnabled;
	private String _serverLocation;
	private String _services;
	private String _state;
	private String _subscription;
	private String _timeZoneId;
	private String _weDeployKey;

}