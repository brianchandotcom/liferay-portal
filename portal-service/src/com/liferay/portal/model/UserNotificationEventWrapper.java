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

package com.liferay.portal.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Map;

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
public class UserNotificationEventWrapper implements UserNotificationEvent,
	ModelWrapper<UserNotificationEvent> {
	public UserNotificationEventWrapper(
		UserNotificationEvent userNotificationEvent) {
		_userNotificationEvent = userNotificationEvent;
	}

	@Override
	public Class<?> getModelClass() {
		return UserNotificationEvent.class;
	}

	@Override
	public String getModelClassName() {
		return UserNotificationEvent.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("userNotificationEventId", getUserNotificationEventId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("type", getType());
		attributes.put("timestamp", getTimestamp());
		attributes.put("deliveryType", getDeliveryType());
		attributes.put("deliverBy", getDeliverBy());
		attributes.put("delivered", getDelivered());
		attributes.put("payload", getPayload());
		attributes.put("actionRequired", getActionRequired());
		attributes.put("archived", getArchived());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long userNotificationEventId = (Long)attributes.get(
				"userNotificationEventId");

		if (userNotificationEventId != null) {
			setUserNotificationEventId(userNotificationEventId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String type = (String)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		Long timestamp = (Long)attributes.get("timestamp");

		if (timestamp != null) {
			setTimestamp(timestamp);
		}

		Integer deliveryType = (Integer)attributes.get("deliveryType");

		if (deliveryType != null) {
			setDeliveryType(deliveryType);
		}

		Long deliverBy = (Long)attributes.get("deliverBy");

		if (deliverBy != null) {
			setDeliverBy(deliverBy);
		}

		Boolean delivered = (Boolean)attributes.get("delivered");

		if (delivered != null) {
			setDelivered(delivered);
		}

		String payload = (String)attributes.get("payload");

		if (payload != null) {
			setPayload(payload);
		}

		Boolean actionRequired = (Boolean)attributes.get("actionRequired");

		if (actionRequired != null) {
			setActionRequired(actionRequired);
		}

		Boolean archived = (Boolean)attributes.get("archived");

		if (archived != null) {
			setArchived(archived);
		}
	}

	@Override
	public java.lang.Object clone() {
		return new UserNotificationEventWrapper((UserNotificationEvent)_userNotificationEvent.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portal.model.UserNotificationEvent userNotificationEvent) {
		return _userNotificationEvent.compareTo(userNotificationEvent);
	}

	/**
	* Returns the action required of this user notification event.
	*
	* @return the action required of this user notification event
	*/
	@Override
	public boolean getActionRequired() {
		return _userNotificationEvent.getActionRequired();
	}

	/**
	* Returns the archived of this user notification event.
	*
	* @return the archived of this user notification event
	*/
	@Override
	public boolean getArchived() {
		return _userNotificationEvent.getArchived();
	}

	/**
	* Returns the company ID of this user notification event.
	*
	* @return the company ID of this user notification event
	*/
	@Override
	public long getCompanyId() {
		return _userNotificationEvent.getCompanyId();
	}

	/**
	* Returns the deliver by of this user notification event.
	*
	* @return the deliver by of this user notification event
	*/
	@Override
	public long getDeliverBy() {
		return _userNotificationEvent.getDeliverBy();
	}

	/**
	* Returns the delivered of this user notification event.
	*
	* @return the delivered of this user notification event
	*/
	@Override
	public boolean getDelivered() {
		return _userNotificationEvent.getDelivered();
	}

	/**
	* Returns the delivery type of this user notification event.
	*
	* @return the delivery type of this user notification event
	*/
	@Override
	public int getDeliveryType() {
		return _userNotificationEvent.getDeliveryType();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _userNotificationEvent.getExpandoBridge();
	}

	/**
	* Returns the mvcc version of this user notification event.
	*
	* @return the mvcc version of this user notification event
	*/
	@Override
	public long getMvccVersion() {
		return _userNotificationEvent.getMvccVersion();
	}

	/**
	* Returns the payload of this user notification event.
	*
	* @return the payload of this user notification event
	*/
	@Override
	public java.lang.String getPayload() {
		return _userNotificationEvent.getPayload();
	}

	/**
	* Returns the primary key of this user notification event.
	*
	* @return the primary key of this user notification event
	*/
	@Override
	public long getPrimaryKey() {
		return _userNotificationEvent.getPrimaryKey();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _userNotificationEvent.getPrimaryKeyObj();
	}

	/**
	* Returns the timestamp of this user notification event.
	*
	* @return the timestamp of this user notification event
	*/
	@Override
	public long getTimestamp() {
		return _userNotificationEvent.getTimestamp();
	}

	/**
	* Returns the type of this user notification event.
	*
	* @return the type of this user notification event
	*/
	@Override
	public java.lang.String getType() {
		return _userNotificationEvent.getType();
	}

	/**
	* Returns the user ID of this user notification event.
	*
	* @return the user ID of this user notification event
	*/
	@Override
	public long getUserId() {
		return _userNotificationEvent.getUserId();
	}

	/**
	* Returns the user notification event ID of this user notification event.
	*
	* @return the user notification event ID of this user notification event
	*/
	@Override
	public long getUserNotificationEventId() {
		return _userNotificationEvent.getUserNotificationEventId();
	}

	/**
	* Returns the user uuid of this user notification event.
	*
	* @return the user uuid of this user notification event
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _userNotificationEvent.getUserUuid();
	}

	/**
	* Returns the uuid of this user notification event.
	*
	* @return the uuid of this user notification event
	*/
	@Override
	public java.lang.String getUuid() {
		return _userNotificationEvent.getUuid();
	}

	@Override
	public int hashCode() {
		return _userNotificationEvent.hashCode();
	}

	/**
	* Returns <code>true</code> if this user notification event is action required.
	*
	* @return <code>true</code> if this user notification event is action required; <code>false</code> otherwise
	*/
	@Override
	public boolean isActionRequired() {
		return _userNotificationEvent.isActionRequired();
	}

	/**
	* Returns <code>true</code> if this user notification event is archived.
	*
	* @return <code>true</code> if this user notification event is archived; <code>false</code> otherwise
	*/
	@Override
	public boolean isArchived() {
		return _userNotificationEvent.isArchived();
	}

	@Override
	public boolean isCachedModel() {
		return _userNotificationEvent.isCachedModel();
	}

	/**
	* Returns <code>true</code> if this user notification event is delivered.
	*
	* @return <code>true</code> if this user notification event is delivered; <code>false</code> otherwise
	*/
	@Override
	public boolean isDelivered() {
		return _userNotificationEvent.isDelivered();
	}

	@Override
	public boolean isEscapedModel() {
		return _userNotificationEvent.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _userNotificationEvent.isNew();
	}

	@Override
	public void persist() {
		_userNotificationEvent.persist();
	}

	/**
	* Sets whether this user notification event is action required.
	*
	* @param actionRequired the action required of this user notification event
	*/
	@Override
	public void setActionRequired(boolean actionRequired) {
		_userNotificationEvent.setActionRequired(actionRequired);
	}

	/**
	* Sets whether this user notification event is archived.
	*
	* @param archived the archived of this user notification event
	*/
	@Override
	public void setArchived(boolean archived) {
		_userNotificationEvent.setArchived(archived);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_userNotificationEvent.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this user notification event.
	*
	* @param companyId the company ID of this user notification event
	*/
	@Override
	public void setCompanyId(long companyId) {
		_userNotificationEvent.setCompanyId(companyId);
	}

	/**
	* Sets the deliver by of this user notification event.
	*
	* @param deliverBy the deliver by of this user notification event
	*/
	@Override
	public void setDeliverBy(long deliverBy) {
		_userNotificationEvent.setDeliverBy(deliverBy);
	}

	/**
	* Sets whether this user notification event is delivered.
	*
	* @param delivered the delivered of this user notification event
	*/
	@Override
	public void setDelivered(boolean delivered) {
		_userNotificationEvent.setDelivered(delivered);
	}

	/**
	* Sets the delivery type of this user notification event.
	*
	* @param deliveryType the delivery type of this user notification event
	*/
	@Override
	public void setDeliveryType(int deliveryType) {
		_userNotificationEvent.setDeliveryType(deliveryType);
	}

	@Override
	public void setExpandoBridgeAttributes(BaseModel<?> baseModel) {
		_userNotificationEvent.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_userNotificationEvent.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_userNotificationEvent.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the mvcc version of this user notification event.
	*
	* @param mvccVersion the mvcc version of this user notification event
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		_userNotificationEvent.setMvccVersion(mvccVersion);
	}

	@Override
	public void setNew(boolean n) {
		_userNotificationEvent.setNew(n);
	}

	/**
	* Sets the payload of this user notification event.
	*
	* @param payload the payload of this user notification event
	*/
	@Override
	public void setPayload(java.lang.String payload) {
		_userNotificationEvent.setPayload(payload);
	}

	/**
	* Sets the primary key of this user notification event.
	*
	* @param primaryKey the primary key of this user notification event
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_userNotificationEvent.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_userNotificationEvent.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the timestamp of this user notification event.
	*
	* @param timestamp the timestamp of this user notification event
	*/
	@Override
	public void setTimestamp(long timestamp) {
		_userNotificationEvent.setTimestamp(timestamp);
	}

	/**
	* Sets the type of this user notification event.
	*
	* @param type the type of this user notification event
	*/
	@Override
	public void setType(java.lang.String type) {
		_userNotificationEvent.setType(type);
	}

	/**
	* Sets the user ID of this user notification event.
	*
	* @param userId the user ID of this user notification event
	*/
	@Override
	public void setUserId(long userId) {
		_userNotificationEvent.setUserId(userId);
	}

	/**
	* Sets the user notification event ID of this user notification event.
	*
	* @param userNotificationEventId the user notification event ID of this user notification event
	*/
	@Override
	public void setUserNotificationEventId(long userNotificationEventId) {
		_userNotificationEvent.setUserNotificationEventId(userNotificationEventId);
	}

	/**
	* Sets the user uuid of this user notification event.
	*
	* @param userUuid the user uuid of this user notification event
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_userNotificationEvent.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this user notification event.
	*
	* @param uuid the uuid of this user notification event
	*/
	@Override
	public void setUuid(java.lang.String uuid) {
		_userNotificationEvent.setUuid(uuid);
	}

	@Override
	public CacheModel<com.liferay.portal.model.UserNotificationEvent> toCacheModel() {
		return _userNotificationEvent.toCacheModel();
	}

	@Override
	public com.liferay.portal.model.UserNotificationEvent toEscapedModel() {
		return new UserNotificationEventWrapper(_userNotificationEvent.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _userNotificationEvent.toString();
	}

	@Override
	public com.liferay.portal.model.UserNotificationEvent toUnescapedModel() {
		return new UserNotificationEventWrapper(_userNotificationEvent.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _userNotificationEvent.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof UserNotificationEventWrapper)) {
			return false;
		}

		UserNotificationEventWrapper userNotificationEventWrapper = (UserNotificationEventWrapper)obj;

		if (Validator.equals(_userNotificationEvent,
					userNotificationEventWrapper._userNotificationEvent)) {
			return true;
		}

		return false;
	}

	@Override
	public UserNotificationEvent getWrappedModel() {
		return _userNotificationEvent;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _userNotificationEvent.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _userNotificationEvent.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_userNotificationEvent.resetOriginalValues();
	}

	private final UserNotificationEvent _userNotificationEvent;
}