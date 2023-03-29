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
 * This class is a wrapper for {@link FaroNotification}.
 * </p>
 *
 * @author Matthew Kong
 * @see FaroNotification
 * @generated
 */
public class FaroNotificationWrapper
	implements FaroNotification, ModelWrapper<FaroNotification> {

	public FaroNotificationWrapper(FaroNotification faroNotification) {
		_faroNotification = faroNotification;
	}

	@Override
	public Class<?> getModelClass() {
		return FaroNotification.class;
	}

	@Override
	public String getModelClassName() {
		return FaroNotification.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("faroNotificationId", getFaroNotificationId());
		attributes.put("groupId", getGroupId());
		attributes.put("userId", getUserId());
		attributes.put("createTime", getCreateTime());
		attributes.put("modifiedTime", getModifiedTime());
		attributes.put("ownerId", getOwnerId());
		attributes.put("scope", getScope());
		attributes.put("read", isRead());
		attributes.put("type", getType());
		attributes.put("subtype", getSubtype());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long faroNotificationId = (Long)attributes.get("faroNotificationId");

		if (faroNotificationId != null) {
			setFaroNotificationId(faroNotificationId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		Long createTime = (Long)attributes.get("createTime");

		if (createTime != null) {
			setCreateTime(createTime);
		}

		Long modifiedTime = (Long)attributes.get("modifiedTime");

		if (modifiedTime != null) {
			setModifiedTime(modifiedTime);
		}

		Long ownerId = (Long)attributes.get("ownerId");

		if (ownerId != null) {
			setOwnerId(ownerId);
		}

		String scope = (String)attributes.get("scope");

		if (scope != null) {
			setScope(scope);
		}

		Boolean read = (Boolean)attributes.get("read");

		if (read != null) {
			setRead(read);
		}

		String type = (String)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		String subtype = (String)attributes.get("subtype");

		if (subtype != null) {
			setSubtype(subtype);
		}
	}

	@Override
	public Object clone() {
		return new FaroNotificationWrapper(
			(FaroNotification)_faroNotification.clone());
	}

	@Override
	public int compareTo(FaroNotification faroNotification) {
		return _faroNotification.compareTo(faroNotification);
	}

	/**
	 * Returns the create time of this faro notification.
	 *
	 * @return the create time of this faro notification
	 */
	@Override
	public long getCreateTime() {
		return _faroNotification.getCreateTime();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _faroNotification.getExpandoBridge();
	}

	/**
	 * Returns the faro notification ID of this faro notification.
	 *
	 * @return the faro notification ID of this faro notification
	 */
	@Override
	public long getFaroNotificationId() {
		return _faroNotification.getFaroNotificationId();
	}

	/**
	 * Returns the group ID of this faro notification.
	 *
	 * @return the group ID of this faro notification
	 */
	@Override
	public long getGroupId() {
		return _faroNotification.getGroupId();
	}

	/**
	 * Returns the modified time of this faro notification.
	 *
	 * @return the modified time of this faro notification
	 */
	@Override
	public long getModifiedTime() {
		return _faroNotification.getModifiedTime();
	}

	/**
	 * Returns the owner ID of this faro notification.
	 *
	 * @return the owner ID of this faro notification
	 */
	@Override
	public long getOwnerId() {
		return _faroNotification.getOwnerId();
	}

	/**
	 * Returns the primary key of this faro notification.
	 *
	 * @return the primary key of this faro notification
	 */
	@Override
	public long getPrimaryKey() {
		return _faroNotification.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _faroNotification.getPrimaryKeyObj();
	}

	/**
	 * Returns the read of this faro notification.
	 *
	 * @return the read of this faro notification
	 */
	@Override
	public boolean getRead() {
		return _faroNotification.getRead();
	}

	/**
	 * Returns the scope of this faro notification.
	 *
	 * @return the scope of this faro notification
	 */
	@Override
	public String getScope() {
		return _faroNotification.getScope();
	}

	/**
	 * Returns the subtype of this faro notification.
	 *
	 * @return the subtype of this faro notification
	 */
	@Override
	public String getSubtype() {
		return _faroNotification.getSubtype();
	}

	/**
	 * Returns the type of this faro notification.
	 *
	 * @return the type of this faro notification
	 */
	@Override
	public String getType() {
		return _faroNotification.getType();
	}

	/**
	 * Returns the user ID of this faro notification.
	 *
	 * @return the user ID of this faro notification
	 */
	@Override
	public long getUserId() {
		return _faroNotification.getUserId();
	}

	/**
	 * Returns the user uuid of this faro notification.
	 *
	 * @return the user uuid of this faro notification
	 */
	@Override
	public String getUserUuid() {
		return _faroNotification.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _faroNotification.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _faroNotification.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _faroNotification.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _faroNotification.isNew();
	}

	/**
	 * Returns <code>true</code> if this faro notification is read.
	 *
	 * @return <code>true</code> if this faro notification is read; <code>false</code> otherwise
	 */
	@Override
	public boolean isRead() {
		return _faroNotification.isRead();
	}

	@Override
	public void persist() {
		_faroNotification.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_faroNotification.setCachedModel(cachedModel);
	}

	/**
	 * Sets the create time of this faro notification.
	 *
	 * @param createTime the create time of this faro notification
	 */
	@Override
	public void setCreateTime(long createTime) {
		_faroNotification.setCreateTime(createTime);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_faroNotification.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_faroNotification.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_faroNotification.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	 * Sets the faro notification ID of this faro notification.
	 *
	 * @param faroNotificationId the faro notification ID of this faro notification
	 */
	@Override
	public void setFaroNotificationId(long faroNotificationId) {
		_faroNotification.setFaroNotificationId(faroNotificationId);
	}

	/**
	 * Sets the group ID of this faro notification.
	 *
	 * @param groupId the group ID of this faro notification
	 */
	@Override
	public void setGroupId(long groupId) {
		_faroNotification.setGroupId(groupId);
	}

	/**
	 * Sets the modified time of this faro notification.
	 *
	 * @param modifiedTime the modified time of this faro notification
	 */
	@Override
	public void setModifiedTime(long modifiedTime) {
		_faroNotification.setModifiedTime(modifiedTime);
	}

	@Override
	public void setNew(boolean n) {
		_faroNotification.setNew(n);
	}

	/**
	 * Sets the owner ID of this faro notification.
	 *
	 * @param ownerId the owner ID of this faro notification
	 */
	@Override
	public void setOwnerId(long ownerId) {
		_faroNotification.setOwnerId(ownerId);
	}

	/**
	 * Sets the primary key of this faro notification.
	 *
	 * @param primaryKey the primary key of this faro notification
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_faroNotification.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_faroNotification.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets whether this faro notification is read.
	 *
	 * @param read the read of this faro notification
	 */
	@Override
	public void setRead(boolean read) {
		_faroNotification.setRead(read);
	}

	/**
	 * Sets the scope of this faro notification.
	 *
	 * @param scope the scope of this faro notification
	 */
	@Override
	public void setScope(String scope) {
		_faroNotification.setScope(scope);
	}

	/**
	 * Sets the subtype of this faro notification.
	 *
	 * @param subtype the subtype of this faro notification
	 */
	@Override
	public void setSubtype(String subtype) {
		_faroNotification.setSubtype(subtype);
	}

	/**
	 * Sets the type of this faro notification.
	 *
	 * @param type the type of this faro notification
	 */
	@Override
	public void setType(String type) {
		_faroNotification.setType(type);
	}

	/**
	 * Sets the user ID of this faro notification.
	 *
	 * @param userId the user ID of this faro notification
	 */
	@Override
	public void setUserId(long userId) {
		_faroNotification.setUserId(userId);
	}

	/**
	 * Sets the user uuid of this faro notification.
	 *
	 * @param userUuid the user uuid of this faro notification
	 */
	@Override
	public void setUserUuid(String userUuid) {
		_faroNotification.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<FaroNotification>
		toCacheModel() {

		return _faroNotification.toCacheModel();
	}

	@Override
	public FaroNotification toEscapedModel() {
		return new FaroNotificationWrapper(_faroNotification.toEscapedModel());
	}

	@Override
	public String toString() {
		return _faroNotification.toString();
	}

	@Override
	public FaroNotification toUnescapedModel() {
		return new FaroNotificationWrapper(
			_faroNotification.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _faroNotification.toXmlString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FaroNotificationWrapper)) {
			return false;
		}

		FaroNotificationWrapper faroNotificationWrapper =
			(FaroNotificationWrapper)object;

		if (Objects.equals(
				_faroNotification, faroNotificationWrapper._faroNotification)) {

			return true;
		}

		return false;
	}

	@Override
	public FaroNotification getWrappedModel() {
		return _faroNotification;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _faroNotification.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _faroNotification.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_faroNotification.resetOriginalValues();
	}

	private final FaroNotification _faroNotification;

}