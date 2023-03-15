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

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link FaroProject}.
 * </p>
 *
 * @author Matthew Kong
 * @see FaroProject
 * @generated
 */
public class FaroProjectWrapper
	implements FaroProject, ModelWrapper<FaroProject> {

	public FaroProjectWrapper(FaroProject faroProject) {
		_faroProject = faroProject;
	}

	@Override
	public Class<?> getModelClass() {
		return FaroProject.class;
	}

	@Override
	public String getModelClassName() {
		return FaroProject.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("faroProjectId", getFaroProjectId());
		attributes.put("groupId", getGroupId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createTime", getCreateTime());
		attributes.put("modifiedTime", getModifiedTime());
		attributes.put("name", getName());
		attributes.put("accountKey", getAccountKey());
		attributes.put("accountName", getAccountName());
		attributes.put("corpProjectName", getCorpProjectName());
		attributes.put("corpProjectUuid", getCorpProjectUuid());
		attributes.put("ipAddresses", getIpAddresses());
		attributes.put(
			"incidentReportEmailAddresses", getIncidentReportEmailAddresses());
		attributes.put("lastAccessTime", getLastAccessTime());
		attributes.put("recommendationsEnabled", isRecommendationsEnabled());
		attributes.put("serverLocation", getServerLocation());
		attributes.put("services", getServices());
		attributes.put("state", getState());
		attributes.put("subscription", getSubscription());
		attributes.put("timeZoneId", getTimeZoneId());
		attributes.put("weDeployKey", getWeDeployKey());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long faroProjectId = (Long)attributes.get("faroProjectId");

		if (faroProjectId != null) {
			setFaroProjectId(faroProjectId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Long createTime = (Long)attributes.get("createTime");

		if (createTime != null) {
			setCreateTime(createTime);
		}

		Long modifiedTime = (Long)attributes.get("modifiedTime");

		if (modifiedTime != null) {
			setModifiedTime(modifiedTime);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String accountKey = (String)attributes.get("accountKey");

		if (accountKey != null) {
			setAccountKey(accountKey);
		}

		String accountName = (String)attributes.get("accountName");

		if (accountName != null) {
			setAccountName(accountName);
		}

		String corpProjectName = (String)attributes.get("corpProjectName");

		if (corpProjectName != null) {
			setCorpProjectName(corpProjectName);
		}

		String corpProjectUuid = (String)attributes.get("corpProjectUuid");

		if (corpProjectUuid != null) {
			setCorpProjectUuid(corpProjectUuid);
		}

		String ipAddresses = (String)attributes.get("ipAddresses");

		if (ipAddresses != null) {
			setIpAddresses(ipAddresses);
		}

		String incidentReportEmailAddresses = (String)attributes.get(
			"incidentReportEmailAddresses");

		if (incidentReportEmailAddresses != null) {
			setIncidentReportEmailAddresses(incidentReportEmailAddresses);
		}

		Long lastAccessTime = (Long)attributes.get("lastAccessTime");

		if (lastAccessTime != null) {
			setLastAccessTime(lastAccessTime);
		}

		Boolean recommendationsEnabled = (Boolean)attributes.get(
			"recommendationsEnabled");

		if (recommendationsEnabled != null) {
			setRecommendationsEnabled(recommendationsEnabled);
		}

		String serverLocation = (String)attributes.get("serverLocation");

		if (serverLocation != null) {
			setServerLocation(serverLocation);
		}

		String services = (String)attributes.get("services");

		if (services != null) {
			setServices(services);
		}

		String state = (String)attributes.get("state");

		if (state != null) {
			setState(state);
		}

		String subscription = (String)attributes.get("subscription");

		if (subscription != null) {
			setSubscription(subscription);
		}

		String timeZoneId = (String)attributes.get("timeZoneId");

		if (timeZoneId != null) {
			setTimeZoneId(timeZoneId);
		}

		String weDeployKey = (String)attributes.get("weDeployKey");

		if (weDeployKey != null) {
			setWeDeployKey(weDeployKey);
		}
	}

	@Override
	public Object clone() {
		return new FaroProjectWrapper((FaroProject)_faroProject.clone());
	}

	@Override
	public int compareTo(FaroProject faroProject) {
		return _faroProject.compareTo(faroProject);
	}

	/**
	 * Returns the account key of this faro project.
	 *
	 * @return the account key of this faro project
	 */
	@Override
	public String getAccountKey() {
		return _faroProject.getAccountKey();
	}

	/**
	 * Returns the account name of this faro project.
	 *
	 * @return the account name of this faro project
	 */
	@Override
	public String getAccountName() {
		return _faroProject.getAccountName();
	}

	/**
	 * Returns the corp project name of this faro project.
	 *
	 * @return the corp project name of this faro project
	 */
	@Override
	public String getCorpProjectName() {
		return _faroProject.getCorpProjectName();
	}

	/**
	 * Returns the corp project uuid of this faro project.
	 *
	 * @return the corp project uuid of this faro project
	 */
	@Override
	public String getCorpProjectUuid() {
		return _faroProject.getCorpProjectUuid();
	}

	/**
	 * Returns the create time of this faro project.
	 *
	 * @return the create time of this faro project
	 */
	@Override
	public long getCreateTime() {
		return _faroProject.getCreateTime();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _faroProject.getExpandoBridge();
	}

	/**
	 * Returns the faro project ID of this faro project.
	 *
	 * @return the faro project ID of this faro project
	 */
	@Override
	public long getFaroProjectId() {
		return _faroProject.getFaroProjectId();
	}

	/**
	 * Returns the group ID of this faro project.
	 *
	 * @return the group ID of this faro project
	 */
	@Override
	public long getGroupId() {
		return _faroProject.getGroupId();
	}

	/**
	 * Returns the incident report email addresses of this faro project.
	 *
	 * @return the incident report email addresses of this faro project
	 */
	@Override
	public String getIncidentReportEmailAddresses() {
		return _faroProject.getIncidentReportEmailAddresses();
	}

	/**
	 * Returns the ip addresses of this faro project.
	 *
	 * @return the ip addresses of this faro project
	 */
	@Override
	public String getIpAddresses() {
		return _faroProject.getIpAddresses();
	}

	/**
	 * Returns the last access time of this faro project.
	 *
	 * @return the last access time of this faro project
	 */
	@Override
	public long getLastAccessTime() {
		return _faroProject.getLastAccessTime();
	}

	/**
	 * Returns the modified time of this faro project.
	 *
	 * @return the modified time of this faro project
	 */
	@Override
	public long getModifiedTime() {
		return _faroProject.getModifiedTime();
	}

	/**
	 * Returns the name of this faro project.
	 *
	 * @return the name of this faro project
	 */
	@Override
	public String getName() {
		return _faroProject.getName();
	}

	/**
	 * Returns the primary key of this faro project.
	 *
	 * @return the primary key of this faro project
	 */
	@Override
	public long getPrimaryKey() {
		return _faroProject.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _faroProject.getPrimaryKeyObj();
	}

	@Override
	public String getProjectId() {
		return _faroProject.getProjectId();
	}

	/**
	 * Returns the recommendations enabled of this faro project.
	 *
	 * @return the recommendations enabled of this faro project
	 */
	@Override
	public boolean getRecommendationsEnabled() {
		return _faroProject.getRecommendationsEnabled();
	}

	/**
	 * Returns the server location of this faro project.
	 *
	 * @return the server location of this faro project
	 */
	@Override
	public String getServerLocation() {
		return _faroProject.getServerLocation();
	}

	/**
	 * Returns the services of this faro project.
	 *
	 * @return the services of this faro project
	 */
	@Override
	public String getServices() {
		return _faroProject.getServices();
	}

	/**
	 * Returns the state of this faro project.
	 *
	 * @return the state of this faro project
	 */
	@Override
	public String getState() {
		return _faroProject.getState();
	}

	/**
	 * Returns the subscription of this faro project.
	 *
	 * @return the subscription of this faro project
	 */
	@Override
	public String getSubscription() {
		return _faroProject.getSubscription();
	}

	/**
	 * Returns the time zone ID of this faro project.
	 *
	 * @return the time zone ID of this faro project
	 */
	@Override
	public String getTimeZoneId() {
		return _faroProject.getTimeZoneId();
	}

	/**
	 * Returns the user ID of this faro project.
	 *
	 * @return the user ID of this faro project
	 */
	@Override
	public long getUserId() {
		return _faroProject.getUserId();
	}

	/**
	 * Returns the user name of this faro project.
	 *
	 * @return the user name of this faro project
	 */
	@Override
	public String getUserName() {
		return _faroProject.getUserName();
	}

	/**
	 * Returns the user uuid of this faro project.
	 *
	 * @return the user uuid of this faro project
	 */
	@Override
	public String getUserUuid() {
		return _faroProject.getUserUuid();
	}

	/**
	 * Returns the we deploy key of this faro project.
	 *
	 * @return the we deploy key of this faro project
	 */
	@Override
	public String getWeDeployKey() {
		return _faroProject.getWeDeployKey();
	}

	@Override
	public int hashCode() {
		return _faroProject.hashCode();
	}

	@Override
	public boolean isAllowedIPAddress(String ipAddress) {
		return _faroProject.isAllowedIPAddress(ipAddress);
	}

	@Override
	public boolean isCachedModel() {
		return _faroProject.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _faroProject.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _faroProject.isNew();
	}

	/**
	 * Returns <code>true</code> if this faro project is recommendations enabled.
	 *
	 * @return <code>true</code> if this faro project is recommendations enabled; <code>false</code> otherwise
	 */
	@Override
	public boolean isRecommendationsEnabled() {
		return _faroProject.isRecommendationsEnabled();
	}

	@Override
	public boolean isTrial() {
		return _faroProject.isTrial();
	}

	@Override
	public void persist() {
		_faroProject.persist();
	}

	/**
	 * Sets the account key of this faro project.
	 *
	 * @param accountKey the account key of this faro project
	 */
	@Override
	public void setAccountKey(String accountKey) {
		_faroProject.setAccountKey(accountKey);
	}

	/**
	 * Sets the account name of this faro project.
	 *
	 * @param accountName the account name of this faro project
	 */
	@Override
	public void setAccountName(String accountName) {
		_faroProject.setAccountName(accountName);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_faroProject.setCachedModel(cachedModel);
	}

	/**
	 * Sets the corp project name of this faro project.
	 *
	 * @param corpProjectName the corp project name of this faro project
	 */
	@Override
	public void setCorpProjectName(String corpProjectName) {
		_faroProject.setCorpProjectName(corpProjectName);
	}

	/**
	 * Sets the corp project uuid of this faro project.
	 *
	 * @param corpProjectUuid the corp project uuid of this faro project
	 */
	@Override
	public void setCorpProjectUuid(String corpProjectUuid) {
		_faroProject.setCorpProjectUuid(corpProjectUuid);
	}

	/**
	 * Sets the create time of this faro project.
	 *
	 * @param createTime the create time of this faro project
	 */
	@Override
	public void setCreateTime(long createTime) {
		_faroProject.setCreateTime(createTime);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_faroProject.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_faroProject.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_faroProject.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	 * Sets the faro project ID of this faro project.
	 *
	 * @param faroProjectId the faro project ID of this faro project
	 */
	@Override
	public void setFaroProjectId(long faroProjectId) {
		_faroProject.setFaroProjectId(faroProjectId);
	}

	/**
	 * Sets the group ID of this faro project.
	 *
	 * @param groupId the group ID of this faro project
	 */
	@Override
	public void setGroupId(long groupId) {
		_faroProject.setGroupId(groupId);
	}

	/**
	 * Sets the incident report email addresses of this faro project.
	 *
	 * @param incidentReportEmailAddresses the incident report email addresses of this faro project
	 */
	@Override
	public void setIncidentReportEmailAddresses(
		String incidentReportEmailAddresses) {

		_faroProject.setIncidentReportEmailAddresses(
			incidentReportEmailAddresses);
	}

	/**
	 * Sets the ip addresses of this faro project.
	 *
	 * @param ipAddresses the ip addresses of this faro project
	 */
	@Override
	public void setIpAddresses(String ipAddresses) {
		_faroProject.setIpAddresses(ipAddresses);
	}

	/**
	 * Sets the last access time of this faro project.
	 *
	 * @param lastAccessTime the last access time of this faro project
	 */
	@Override
	public void setLastAccessTime(long lastAccessTime) {
		_faroProject.setLastAccessTime(lastAccessTime);
	}

	/**
	 * Sets the modified time of this faro project.
	 *
	 * @param modifiedTime the modified time of this faro project
	 */
	@Override
	public void setModifiedTime(long modifiedTime) {
		_faroProject.setModifiedTime(modifiedTime);
	}

	/**
	 * Sets the name of this faro project.
	 *
	 * @param name the name of this faro project
	 */
	@Override
	public void setName(String name) {
		_faroProject.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_faroProject.setNew(n);
	}

	/**
	 * Sets the primary key of this faro project.
	 *
	 * @param primaryKey the primary key of this faro project
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_faroProject.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_faroProject.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets whether this faro project is recommendations enabled.
	 *
	 * @param recommendationsEnabled the recommendations enabled of this faro project
	 */
	@Override
	public void setRecommendationsEnabled(boolean recommendationsEnabled) {
		_faroProject.setRecommendationsEnabled(recommendationsEnabled);
	}

	/**
	 * Sets the server location of this faro project.
	 *
	 * @param serverLocation the server location of this faro project
	 */
	@Override
	public void setServerLocation(String serverLocation) {
		_faroProject.setServerLocation(serverLocation);
	}

	/**
	 * Sets the services of this faro project.
	 *
	 * @param services the services of this faro project
	 */
	@Override
	public void setServices(String services) {
		_faroProject.setServices(services);
	}

	/**
	 * Sets the state of this faro project.
	 *
	 * @param state the state of this faro project
	 */
	@Override
	public void setState(String state) {
		_faroProject.setState(state);
	}

	/**
	 * Sets the subscription of this faro project.
	 *
	 * @param subscription the subscription of this faro project
	 */
	@Override
	public void setSubscription(String subscription) {
		_faroProject.setSubscription(subscription);
	}

	/**
	 * Sets the time zone ID of this faro project.
	 *
	 * @param timeZoneId the time zone ID of this faro project
	 */
	@Override
	public void setTimeZoneId(String timeZoneId) {
		_faroProject.setTimeZoneId(timeZoneId);
	}

	/**
	 * Sets the user ID of this faro project.
	 *
	 * @param userId the user ID of this faro project
	 */
	@Override
	public void setUserId(long userId) {
		_faroProject.setUserId(userId);
	}

	/**
	 * Sets the user name of this faro project.
	 *
	 * @param userName the user name of this faro project
	 */
	@Override
	public void setUserName(String userName) {
		_faroProject.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this faro project.
	 *
	 * @param userUuid the user uuid of this faro project
	 */
	@Override
	public void setUserUuid(String userUuid) {
		_faroProject.setUserUuid(userUuid);
	}

	/**
	 * Sets the we deploy key of this faro project.
	 *
	 * @param weDeployKey the we deploy key of this faro project
	 */
	@Override
	public void setWeDeployKey(String weDeployKey) {
		_faroProject.setWeDeployKey(weDeployKey);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<FaroProject>
		toCacheModel() {

		return _faroProject.toCacheModel();
	}

	@Override
	public FaroProject toEscapedModel() {
		return new FaroProjectWrapper(_faroProject.toEscapedModel());
	}

	@Override
	public String toString() {
		return _faroProject.toString();
	}

	@Override
	public FaroProject toUnescapedModel() {
		return new FaroProjectWrapper(_faroProject.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _faroProject.toXmlString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FaroProjectWrapper)) {
			return false;
		}

		FaroProjectWrapper faroProjectWrapper = (FaroProjectWrapper)object;

		if (Objects.equals(_faroProject, faroProjectWrapper._faroProject)) {
			return true;
		}

		return false;
	}

	@Override
	public FaroProject getWrappedModel() {
		return _faroProject;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _faroProject.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _faroProject.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_faroProject.resetOriginalValues();
	}

	private final FaroProject _faroProject;

}