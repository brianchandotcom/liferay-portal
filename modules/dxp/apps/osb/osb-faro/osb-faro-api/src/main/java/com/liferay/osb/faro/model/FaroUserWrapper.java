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
 * This class is a wrapper for {@link FaroUser}.
 * </p>
 *
 * @author Matthew Kong
 * @see FaroUser
 * @generated
 */
public class FaroUserWrapper implements FaroUser, ModelWrapper<FaroUser> {

	public FaroUserWrapper(FaroUser faroUser) {
		_faroUser = faroUser;
	}

	@Override
	public Class<?> getModelClass() {
		return FaroUser.class;
	}

	@Override
	public String getModelClassName() {
		return FaroUser.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("faroUserId", getFaroUserId());
		attributes.put("groupId", getGroupId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createTime", getCreateTime());
		attributes.put("modifiedTime", getModifiedTime());
		attributes.put("liveUserId", getLiveUserId());
		attributes.put("roleId", getRoleId());
		attributes.put("emailAddress", getEmailAddress());
		attributes.put("key", getKey());
		attributes.put("status", getStatus());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long faroUserId = (Long)attributes.get("faroUserId");

		if (faroUserId != null) {
			setFaroUserId(faroUserId);
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

		Long liveUserId = (Long)attributes.get("liveUserId");

		if (liveUserId != null) {
			setLiveUserId(liveUserId);
		}

		Long roleId = (Long)attributes.get("roleId");

		if (roleId != null) {
			setRoleId(roleId);
		}

		String emailAddress = (String)attributes.get("emailAddress");

		if (emailAddress != null) {
			setEmailAddress(emailAddress);
		}

		String key = (String)attributes.get("key");

		if (key != null) {
			setKey(key);
		}

		Integer status = (Integer)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}
	}

	@Override
	public Object clone() {
		return new FaroUserWrapper((FaroUser)_faroUser.clone());
	}

	@Override
	public int compareTo(FaroUser faroUser) {
		return _faroUser.compareTo(faroUser);
	}

	/**
	 * Returns the create time of this faro user.
	 *
	 * @return the create time of this faro user
	 */
	@Override
	public long getCreateTime() {
		return _faroUser.getCreateTime();
	}

	/**
	 * Returns the email address of this faro user.
	 *
	 * @return the email address of this faro user
	 */
	@Override
	public String getEmailAddress() {
		return _faroUser.getEmailAddress();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _faroUser.getExpandoBridge();
	}

	/**
	 * Returns the faro user ID of this faro user.
	 *
	 * @return the faro user ID of this faro user
	 */
	@Override
	public long getFaroUserId() {
		return _faroUser.getFaroUserId();
	}

	/**
	 * Returns the faro user uuid of this faro user.
	 *
	 * @return the faro user uuid of this faro user
	 */
	@Override
	public String getFaroUserUuid() {
		return _faroUser.getFaroUserUuid();
	}

	/**
	 * Returns the group ID of this faro user.
	 *
	 * @return the group ID of this faro user
	 */
	@Override
	public long getGroupId() {
		return _faroUser.getGroupId();
	}

	/**
	 * Returns the key of this faro user.
	 *
	 * @return the key of this faro user
	 */
	@Override
	public String getKey() {
		return _faroUser.getKey();
	}

	/**
	 * Returns the live user ID of this faro user.
	 *
	 * @return the live user ID of this faro user
	 */
	@Override
	public long getLiveUserId() {
		return _faroUser.getLiveUserId();
	}

	/**
	 * Returns the live user uuid of this faro user.
	 *
	 * @return the live user uuid of this faro user
	 */
	@Override
	public String getLiveUserUuid() {
		return _faroUser.getLiveUserUuid();
	}

	/**
	 * Returns the modified time of this faro user.
	 *
	 * @return the modified time of this faro user
	 */
	@Override
	public long getModifiedTime() {
		return _faroUser.getModifiedTime();
	}

	/**
	 * Returns the primary key of this faro user.
	 *
	 * @return the primary key of this faro user
	 */
	@Override
	public long getPrimaryKey() {
		return _faroUser.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _faroUser.getPrimaryKeyObj();
	}

	/**
	 * Returns the role ID of this faro user.
	 *
	 * @return the role ID of this faro user
	 */
	@Override
	public long getRoleId() {
		return _faroUser.getRoleId();
	}

	/**
	 * Returns the status of this faro user.
	 *
	 * @return the status of this faro user
	 */
	@Override
	public int getStatus() {
		return _faroUser.getStatus();
	}

	/**
	 * Returns the user ID of this faro user.
	 *
	 * @return the user ID of this faro user
	 */
	@Override
	public long getUserId() {
		return _faroUser.getUserId();
	}

	/**
	 * Returns the user name of this faro user.
	 *
	 * @return the user name of this faro user
	 */
	@Override
	public String getUserName() {
		return _faroUser.getUserName();
	}

	/**
	 * Returns the user uuid of this faro user.
	 *
	 * @return the user uuid of this faro user
	 */
	@Override
	public String getUserUuid() {
		return _faroUser.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _faroUser.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _faroUser.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _faroUser.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _faroUser.isNew();
	}

	@Override
	public void persist() {
		_faroUser.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_faroUser.setCachedModel(cachedModel);
	}

	/**
	 * Sets the create time of this faro user.
	 *
	 * @param createTime the create time of this faro user
	 */
	@Override
	public void setCreateTime(long createTime) {
		_faroUser.setCreateTime(createTime);
	}

	/**
	 * Sets the email address of this faro user.
	 *
	 * @param emailAddress the email address of this faro user
	 */
	@Override
	public void setEmailAddress(String emailAddress) {
		_faroUser.setEmailAddress(emailAddress);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_faroUser.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_faroUser.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_faroUser.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	 * Sets the faro user ID of this faro user.
	 *
	 * @param faroUserId the faro user ID of this faro user
	 */
	@Override
	public void setFaroUserId(long faroUserId) {
		_faroUser.setFaroUserId(faroUserId);
	}

	/**
	 * Sets the faro user uuid of this faro user.
	 *
	 * @param faroUserUuid the faro user uuid of this faro user
	 */
	@Override
	public void setFaroUserUuid(String faroUserUuid) {
		_faroUser.setFaroUserUuid(faroUserUuid);
	}

	/**
	 * Sets the group ID of this faro user.
	 *
	 * @param groupId the group ID of this faro user
	 */
	@Override
	public void setGroupId(long groupId) {
		_faroUser.setGroupId(groupId);
	}

	/**
	 * Sets the key of this faro user.
	 *
	 * @param key the key of this faro user
	 */
	@Override
	public void setKey(String key) {
		_faroUser.setKey(key);
	}

	/**
	 * Sets the live user ID of this faro user.
	 *
	 * @param liveUserId the live user ID of this faro user
	 */
	@Override
	public void setLiveUserId(long liveUserId) {
		_faroUser.setLiveUserId(liveUserId);
	}

	/**
	 * Sets the live user uuid of this faro user.
	 *
	 * @param liveUserUuid the live user uuid of this faro user
	 */
	@Override
	public void setLiveUserUuid(String liveUserUuid) {
		_faroUser.setLiveUserUuid(liveUserUuid);
	}

	/**
	 * Sets the modified time of this faro user.
	 *
	 * @param modifiedTime the modified time of this faro user
	 */
	@Override
	public void setModifiedTime(long modifiedTime) {
		_faroUser.setModifiedTime(modifiedTime);
	}

	@Override
	public void setNew(boolean n) {
		_faroUser.setNew(n);
	}

	/**
	 * Sets the primary key of this faro user.
	 *
	 * @param primaryKey the primary key of this faro user
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_faroUser.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_faroUser.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets the role ID of this faro user.
	 *
	 * @param roleId the role ID of this faro user
	 */
	@Override
	public void setRoleId(long roleId) {
		_faroUser.setRoleId(roleId);
	}

	/**
	 * Sets the status of this faro user.
	 *
	 * @param status the status of this faro user
	 */
	@Override
	public void setStatus(int status) {
		_faroUser.setStatus(status);
	}

	/**
	 * Sets the user ID of this faro user.
	 *
	 * @param userId the user ID of this faro user
	 */
	@Override
	public void setUserId(long userId) {
		_faroUser.setUserId(userId);
	}

	/**
	 * Sets the user name of this faro user.
	 *
	 * @param userName the user name of this faro user
	 */
	@Override
	public void setUserName(String userName) {
		_faroUser.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this faro user.
	 *
	 * @param userUuid the user uuid of this faro user
	 */
	@Override
	public void setUserUuid(String userUuid) {
		_faroUser.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<FaroUser> toCacheModel() {
		return _faroUser.toCacheModel();
	}

	@Override
	public FaroUser toEscapedModel() {
		return new FaroUserWrapper(_faroUser.toEscapedModel());
	}

	@Override
	public String toString() {
		return _faroUser.toString();
	}

	@Override
	public FaroUser toUnescapedModel() {
		return new FaroUserWrapper(_faroUser.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _faroUser.toXmlString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FaroUserWrapper)) {
			return false;
		}

		FaroUserWrapper faroUserWrapper = (FaroUserWrapper)object;

		if (Objects.equals(_faroUser, faroUserWrapper._faroUser)) {
			return true;
		}

		return false;
	}

	@Override
	public FaroUser getWrappedModel() {
		return _faroUser;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _faroUser.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _faroUser.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_faroUser.resetOriginalValues();
	}

	private final FaroUser _faroUser;

}