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

package com.liferay.portal.kernel.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

/**
 * <p>
 * This class is a wrapper for {@link UserNotificationEvent}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see UserNotificationEvent
 * @generated
 */
@ProviderType
public class UserNotificationEventWrapper extends BaseModelWrapper<UserNotificationEvent>
	implements UserNotificationEvent, ModelWrapper<UserNotificationEvent> {
	public UserNotificationEventWrapper(
		UserNotificationEvent userNotificationEvent) {
		super(userNotificationEvent);
	}

	/**
	* Returns the action required of this user notification event.
	*
	* @return the action required of this user notification event
	*/
	@Override
	public boolean getActionRequired() {
		return model.getActionRequired();
	}

	/**
	* Returns the archived of this user notification event.
	*
	* @return the archived of this user notification event
	*/
	@Override
	public boolean getArchived() {
		return model.getArchived();
	}

	/**
	* Returns the company ID of this user notification event.
	*
	* @return the company ID of this user notification event
	*/
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	* Returns the deliver by of this user notification event.
	*
	* @return the deliver by of this user notification event
	*/
	@Override
	public long getDeliverBy() {
		return model.getDeliverBy();
	}

	/**
	* Returns the delivered of this user notification event.
	*
	* @return the delivered of this user notification event
	*/
	@Override
	public boolean getDelivered() {
		return model.getDelivered();
	}

	/**
	* Returns the delivery type of this user notification event.
	*
	* @return the delivery type of this user notification event
	*/
	@Override
	public int getDeliveryType() {
		return model.getDeliveryType();
	}

	/**
	* Returns the mvcc version of this user notification event.
	*
	* @return the mvcc version of this user notification event
	*/
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	* Returns the payload of this user notification event.
	*
	* @return the payload of this user notification event
	*/
	@Override
	public String getPayload() {
		return model.getPayload();
	}

	/**
	* Returns the primary key of this user notification event.
	*
	* @return the primary key of this user notification event
	*/
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	* Returns the timestamp of this user notification event.
	*
	* @return the timestamp of this user notification event
	*/
	@Override
	public long getTimestamp() {
		return model.getTimestamp();
	}

	/**
	* Returns the type of this user notification event.
	*
	* @return the type of this user notification event
	*/
	@Override
	public String getType() {
		return model.getType();
	}

	/**
	* Returns the user ID of this user notification event.
	*
	* @return the user ID of this user notification event
	*/
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	* Returns the user notification event ID of this user notification event.
	*
	* @return the user notification event ID of this user notification event
	*/
	@Override
	public long getUserNotificationEventId() {
		return model.getUserNotificationEventId();
	}

	/**
	* Returns the user uuid of this user notification event.
	*
	* @return the user uuid of this user notification event
	*/
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	* Returns the uuid of this user notification event.
	*
	* @return the uuid of this user notification event
	*/
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	* Returns <code>true</code> if this user notification event is action required.
	*
	* @return <code>true</code> if this user notification event is action required; <code>false</code> otherwise
	*/
	@Override
	public boolean isActionRequired() {
		return model.isActionRequired();
	}

	/**
	* Returns <code>true</code> if this user notification event is archived.
	*
	* @return <code>true</code> if this user notification event is archived; <code>false</code> otherwise
	*/
	@Override
	public boolean isArchived() {
		return model.isArchived();
	}

	/**
	* Returns <code>true</code> if this user notification event is delivered.
	*
	* @return <code>true</code> if this user notification event is delivered; <code>false</code> otherwise
	*/
	@Override
	public boolean isDelivered() {
		return model.isDelivered();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	* Sets whether this user notification event is action required.
	*
	* @param actionRequired the action required of this user notification event
	*/
	@Override
	public void setActionRequired(boolean actionRequired) {
		model.setActionRequired(actionRequired);
	}

	/**
	* Sets whether this user notification event is archived.
	*
	* @param archived the archived of this user notification event
	*/
	@Override
	public void setArchived(boolean archived) {
		model.setArchived(archived);
	}

	/**
	* Sets the company ID of this user notification event.
	*
	* @param companyId the company ID of this user notification event
	*/
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	* Sets the deliver by of this user notification event.
	*
	* @param deliverBy the deliver by of this user notification event
	*/
	@Override
	public void setDeliverBy(long deliverBy) {
		model.setDeliverBy(deliverBy);
	}

	/**
	* Sets whether this user notification event is delivered.
	*
	* @param delivered the delivered of this user notification event
	*/
	@Override
	public void setDelivered(boolean delivered) {
		model.setDelivered(delivered);
	}

	/**
	* Sets the delivery type of this user notification event.
	*
	* @param deliveryType the delivery type of this user notification event
	*/
	@Override
	public void setDeliveryType(int deliveryType) {
		model.setDeliveryType(deliveryType);
	}

	/**
	* Sets the mvcc version of this user notification event.
	*
	* @param mvccVersion the mvcc version of this user notification event
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	* Sets the payload of this user notification event.
	*
	* @param payload the payload of this user notification event
	*/
	@Override
	public void setPayload(String payload) {
		model.setPayload(payload);
	}

	/**
	* Sets the primary key of this user notification event.
	*
	* @param primaryKey the primary key of this user notification event
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	* Sets the timestamp of this user notification event.
	*
	* @param timestamp the timestamp of this user notification event
	*/
	@Override
	public void setTimestamp(long timestamp) {
		model.setTimestamp(timestamp);
	}

	/**
	* Sets the type of this user notification event.
	*
	* @param type the type of this user notification event
	*/
	@Override
	public void setType(String type) {
		model.setType(type);
	}

	/**
	* Sets the user ID of this user notification event.
	*
	* @param userId the user ID of this user notification event
	*/
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	* Sets the user notification event ID of this user notification event.
	*
	* @param userNotificationEventId the user notification event ID of this user notification event
	*/
	@Override
	public void setUserNotificationEventId(long userNotificationEventId) {
		model.setUserNotificationEventId(userNotificationEventId);
	}

	/**
	* Sets the user uuid of this user notification event.
	*
	* @param userUuid the user uuid of this user notification event
	*/
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this user notification event.
	*
	* @param uuid the uuid of this user notification event
	*/
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	protected UserNotificationEventWrapper wrap(
		UserNotificationEvent userNotificationEvent) {
		return new UserNotificationEventWrapper(userNotificationEvent);
	}
}