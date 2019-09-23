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

package com.liferay.sync.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link SyncDevice}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SyncDevice
 * @generated
 */
public class SyncDeviceWrapper
	extends BaseModelWrapper<SyncDevice>
	implements ModelWrapper<SyncDevice>, SyncDevice {

	public SyncDeviceWrapper(SyncDevice syncDevice) {
		super(syncDevice);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("syncDeviceId", getSyncDeviceId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("type", getType());
		attributes.put("buildNumber", getBuildNumber());
		attributes.put("featureSet", getFeatureSet());
		attributes.put("hostname", getHostname());
		attributes.put("status", getStatus());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long syncDeviceId = (Long)attributes.get("syncDeviceId");

		if (syncDeviceId != null) {
			setSyncDeviceId(syncDeviceId);
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

		String type = (String)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		Long buildNumber = (Long)attributes.get("buildNumber");

		if (buildNumber != null) {
			setBuildNumber(buildNumber);
		}

		Integer featureSet = (Integer)attributes.get("featureSet");

		if (featureSet != null) {
			setFeatureSet(featureSet);
		}

		String hostname = (String)attributes.get("hostname");

		if (hostname != null) {
			setHostname(hostname);
		}

		Integer status = (Integer)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}
	}

	@Override
	public void checkStatus()
		throws com.liferay.portal.kernel.exception.PortalException {

		model.checkStatus();
	}

	/**
	 * Returns the build number of this sync device.
	 *
	 * @return the build number of this sync device
	 */
	@Override
	public long getBuildNumber() {
		return model.getBuildNumber();
	}

	/**
	 * Returns the company ID of this sync device.
	 *
	 * @return the company ID of this sync device
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this sync device.
	 *
	 * @return the create date of this sync device
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the feature set of this sync device.
	 *
	 * @return the feature set of this sync device
	 */
	@Override
	public int getFeatureSet() {
		return model.getFeatureSet();
	}

	/**
	 * Returns the hostname of this sync device.
	 *
	 * @return the hostname of this sync device
	 */
	@Override
	public String getHostname() {
		return model.getHostname();
	}

	/**
	 * Returns the modified date of this sync device.
	 *
	 * @return the modified date of this sync device
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the primary key of this sync device.
	 *
	 * @return the primary key of this sync device
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the status of this sync device.
	 *
	 * @return the status of this sync device
	 */
	@Override
	public int getStatus() {
		return model.getStatus();
	}

	/**
	 * Returns the sync device ID of this sync device.
	 *
	 * @return the sync device ID of this sync device
	 */
	@Override
	public long getSyncDeviceId() {
		return model.getSyncDeviceId();
	}

	/**
	 * Returns the type of this sync device.
	 *
	 * @return the type of this sync device
	 */
	@Override
	public String getType() {
		return model.getType();
	}

	/**
	 * Returns the user ID of this sync device.
	 *
	 * @return the user ID of this sync device
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this sync device.
	 *
	 * @return the user name of this sync device
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this sync device.
	 *
	 * @return the user uuid of this sync device
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this sync device.
	 *
	 * @return the uuid of this sync device
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	@Override
	public boolean hasSetModifiedDate() {
		return model.hasSetModifiedDate();
	}

	@Override
	public boolean isSupported() {
		return model.isSupported();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a sync device model instance should use the <code>SyncDevice</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the build number of this sync device.
	 *
	 * @param buildNumber the build number of this sync device
	 */
	@Override
	public void setBuildNumber(long buildNumber) {
		model.setBuildNumber(buildNumber);
	}

	/**
	 * Sets the company ID of this sync device.
	 *
	 * @param companyId the company ID of this sync device
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this sync device.
	 *
	 * @param createDate the create date of this sync device
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the feature set of this sync device.
	 *
	 * @param featureSet the feature set of this sync device
	 */
	@Override
	public void setFeatureSet(int featureSet) {
		model.setFeatureSet(featureSet);
	}

	/**
	 * Sets the hostname of this sync device.
	 *
	 * @param hostname the hostname of this sync device
	 */
	@Override
	public void setHostname(String hostname) {
		model.setHostname(hostname);
	}

	/**
	 * Sets the modified date of this sync device.
	 *
	 * @param modifiedDate the modified date of this sync device
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the primary key of this sync device.
	 *
	 * @param primaryKey the primary key of this sync device
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the status of this sync device.
	 *
	 * @param status the status of this sync device
	 */
	@Override
	public void setStatus(int status) {
		model.setStatus(status);
	}

	/**
	 * Sets the sync device ID of this sync device.
	 *
	 * @param syncDeviceId the sync device ID of this sync device
	 */
	@Override
	public void setSyncDeviceId(long syncDeviceId) {
		model.setSyncDeviceId(syncDeviceId);
	}

	/**
	 * Sets the type of this sync device.
	 *
	 * @param type the type of this sync device
	 */
	@Override
	public void setType(String type) {
		model.setType(type);
	}

	/**
	 * Sets the user ID of this sync device.
	 *
	 * @param userId the user ID of this sync device
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this sync device.
	 *
	 * @param userName the user name of this sync device
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this sync device.
	 *
	 * @param userUuid the user uuid of this sync device
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this sync device.
	 *
	 * @param uuid the uuid of this sync device
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public boolean supports(int featureSet) {
		return model.supports(featureSet);
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected SyncDeviceWrapper wrap(SyncDevice syncDevice) {
		return new SyncDeviceWrapper(syncDevice);
	}

}