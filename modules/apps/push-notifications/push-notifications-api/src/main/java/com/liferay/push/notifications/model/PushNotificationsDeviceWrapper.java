/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.push.notifications.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link PushNotificationsDevice}.
 * </p>
 *
 * @author Bruno Farache
 * @see PushNotificationsDevice
 * @generated
 */
public class PushNotificationsDeviceWrapper
	extends BaseModelWrapper<PushNotificationsDevice>
	implements ModelWrapper<PushNotificationsDevice>, PushNotificationsDevice {

	public PushNotificationsDeviceWrapper(
		PushNotificationsDevice pushNotificationsDevice) {

		super(pushNotificationsDevice);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put(
			"pushNotificationsDeviceId", getPushNotificationsDeviceId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("createDate", getCreateDate());
		attributes.put("platform", getPlatform());
		attributes.put("token", getToken());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long pushNotificationsDeviceId = (Long)attributes.get(
			"pushNotificationsDeviceId");

		if (pushNotificationsDeviceId != null) {
			setPushNotificationsDeviceId(pushNotificationsDeviceId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		String platform = (String)attributes.get("platform");

		if (platform != null) {
			setPlatform(platform);
		}

		String token = (String)attributes.get("token");

		if (token != null) {
			setToken(token);
		}
	}

	@Override
	public PushNotificationsDevice cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this push notifications device.
	 *
	 * @return the company ID of this push notifications device
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this push notifications device.
	 *
	 * @return the create date of this push notifications device
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the platform of this push notifications device.
	 *
	 * @return the platform of this push notifications device
	 */
	@Override
	public String getPlatform() {
		return model.getPlatform();
	}

	/**
	 * Returns the primary key of this push notifications device.
	 *
	 * @return the primary key of this push notifications device
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the push notifications device ID of this push notifications device.
	 *
	 * @return the push notifications device ID of this push notifications device
	 */
	@Override
	public long getPushNotificationsDeviceId() {
		return model.getPushNotificationsDeviceId();
	}

	/**
	 * Returns the token of this push notifications device.
	 *
	 * @return the token of this push notifications device
	 */
	@Override
	public String getToken() {
		return model.getToken();
	}

	/**
	 * Returns the user ID of this push notifications device.
	 *
	 * @return the user ID of this push notifications device
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user uuid of this push notifications device.
	 *
	 * @return the user uuid of this push notifications device
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
	 * Sets the company ID of this push notifications device.
	 *
	 * @param companyId the company ID of this push notifications device
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this push notifications device.
	 *
	 * @param createDate the create date of this push notifications device
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the platform of this push notifications device.
	 *
	 * @param platform the platform of this push notifications device
	 */
	@Override
	public void setPlatform(String platform) {
		model.setPlatform(platform);
	}

	/**
	 * Sets the primary key of this push notifications device.
	 *
	 * @param primaryKey the primary key of this push notifications device
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the push notifications device ID of this push notifications device.
	 *
	 * @param pushNotificationsDeviceId the push notifications device ID of this push notifications device
	 */
	@Override
	public void setPushNotificationsDeviceId(long pushNotificationsDeviceId) {
		model.setPushNotificationsDeviceId(pushNotificationsDeviceId);
	}

	/**
	 * Sets the token of this push notifications device.
	 *
	 * @param token the token of this push notifications device
	 */
	@Override
	public void setToken(String token) {
		model.setToken(token);
	}

	/**
	 * Sets the user ID of this push notifications device.
	 *
	 * @param userId the user ID of this push notifications device
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user uuid of this push notifications device.
	 *
	 * @param userUuid the user uuid of this push notifications device
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	protected PushNotificationsDeviceWrapper wrap(
		PushNotificationsDevice pushNotificationsDevice) {

		return new PushNotificationsDeviceWrapper(pushNotificationsDevice);
	}

}