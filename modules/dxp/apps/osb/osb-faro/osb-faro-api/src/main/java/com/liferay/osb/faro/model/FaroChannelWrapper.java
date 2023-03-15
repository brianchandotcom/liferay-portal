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
 * This class is a wrapper for {@link FaroChannel}.
 * </p>
 *
 * @author Matthew Kong
 * @see FaroChannel
 * @generated
 */
public class FaroChannelWrapper
	implements FaroChannel, ModelWrapper<FaroChannel> {

	public FaroChannelWrapper(FaroChannel faroChannel) {
		_faroChannel = faroChannel;
	}

	@Override
	public Class<?> getModelClass() {
		return FaroChannel.class;
	}

	@Override
	public String getModelClassName() {
		return FaroChannel.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("faroChannelId", getFaroChannelId());
		attributes.put("groupId", getGroupId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createTime", getCreateTime());
		attributes.put("modifiedTime", getModifiedTime());
		attributes.put("channelId", getChannelId());
		attributes.put("name", getName());
		attributes.put("permissionType", getPermissionType());
		attributes.put("workspaceGroupId", getWorkspaceGroupId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long faroChannelId = (Long)attributes.get("faroChannelId");

		if (faroChannelId != null) {
			setFaroChannelId(faroChannelId);
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

		String channelId = (String)attributes.get("channelId");

		if (channelId != null) {
			setChannelId(channelId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		Integer permissionType = (Integer)attributes.get("permissionType");

		if (permissionType != null) {
			setPermissionType(permissionType);
		}

		Long workspaceGroupId = (Long)attributes.get("workspaceGroupId");

		if (workspaceGroupId != null) {
			setWorkspaceGroupId(workspaceGroupId);
		}
	}

	@Override
	public Object clone() {
		return new FaroChannelWrapper((FaroChannel)_faroChannel.clone());
	}

	@Override
	public int compareTo(FaroChannel faroChannel) {
		return _faroChannel.compareTo(faroChannel);
	}

	/**
	 * Returns the channel ID of this faro channel.
	 *
	 * @return the channel ID of this faro channel
	 */
	@Override
	public String getChannelId() {
		return _faroChannel.getChannelId();
	}

	/**
	 * Returns the create time of this faro channel.
	 *
	 * @return the create time of this faro channel
	 */
	@Override
	public long getCreateTime() {
		return _faroChannel.getCreateTime();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _faroChannel.getExpandoBridge();
	}

	/**
	 * Returns the faro channel ID of this faro channel.
	 *
	 * @return the faro channel ID of this faro channel
	 */
	@Override
	public long getFaroChannelId() {
		return _faroChannel.getFaroChannelId();
	}

	/**
	 * Returns the group ID of this faro channel.
	 *
	 * @return the group ID of this faro channel
	 */
	@Override
	public long getGroupId() {
		return _faroChannel.getGroupId();
	}

	/**
	 * Returns the modified time of this faro channel.
	 *
	 * @return the modified time of this faro channel
	 */
	@Override
	public long getModifiedTime() {
		return _faroChannel.getModifiedTime();
	}

	/**
	 * Returns the name of this faro channel.
	 *
	 * @return the name of this faro channel
	 */
	@Override
	public String getName() {
		return _faroChannel.getName();
	}

	/**
	 * Returns the permission type of this faro channel.
	 *
	 * @return the permission type of this faro channel
	 */
	@Override
	public int getPermissionType() {
		return _faroChannel.getPermissionType();
	}

	/**
	 * Returns the primary key of this faro channel.
	 *
	 * @return the primary key of this faro channel
	 */
	@Override
	public long getPrimaryKey() {
		return _faroChannel.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _faroChannel.getPrimaryKeyObj();
	}

	/**
	 * Returns the user ID of this faro channel.
	 *
	 * @return the user ID of this faro channel
	 */
	@Override
	public long getUserId() {
		return _faroChannel.getUserId();
	}

	/**
	 * Returns the user name of this faro channel.
	 *
	 * @return the user name of this faro channel
	 */
	@Override
	public String getUserName() {
		return _faroChannel.getUserName();
	}

	/**
	 * Returns the user uuid of this faro channel.
	 *
	 * @return the user uuid of this faro channel
	 */
	@Override
	public String getUserUuid() {
		return _faroChannel.getUserUuid();
	}

	/**
	 * Returns the workspace group ID of this faro channel.
	 *
	 * @return the workspace group ID of this faro channel
	 */
	@Override
	public long getWorkspaceGroupId() {
		return _faroChannel.getWorkspaceGroupId();
	}

	@Override
	public int hashCode() {
		return _faroChannel.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _faroChannel.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _faroChannel.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _faroChannel.isNew();
	}

	@Override
	public void persist() {
		_faroChannel.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_faroChannel.setCachedModel(cachedModel);
	}

	/**
	 * Sets the channel ID of this faro channel.
	 *
	 * @param channelId the channel ID of this faro channel
	 */
	@Override
	public void setChannelId(String channelId) {
		_faroChannel.setChannelId(channelId);
	}

	/**
	 * Sets the create time of this faro channel.
	 *
	 * @param createTime the create time of this faro channel
	 */
	@Override
	public void setCreateTime(long createTime) {
		_faroChannel.setCreateTime(createTime);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_faroChannel.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_faroChannel.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_faroChannel.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	 * Sets the faro channel ID of this faro channel.
	 *
	 * @param faroChannelId the faro channel ID of this faro channel
	 */
	@Override
	public void setFaroChannelId(long faroChannelId) {
		_faroChannel.setFaroChannelId(faroChannelId);
	}

	/**
	 * Sets the group ID of this faro channel.
	 *
	 * @param groupId the group ID of this faro channel
	 */
	@Override
	public void setGroupId(long groupId) {
		_faroChannel.setGroupId(groupId);
	}

	/**
	 * Sets the modified time of this faro channel.
	 *
	 * @param modifiedTime the modified time of this faro channel
	 */
	@Override
	public void setModifiedTime(long modifiedTime) {
		_faroChannel.setModifiedTime(modifiedTime);
	}

	/**
	 * Sets the name of this faro channel.
	 *
	 * @param name the name of this faro channel
	 */
	@Override
	public void setName(String name) {
		_faroChannel.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_faroChannel.setNew(n);
	}

	/**
	 * Sets the permission type of this faro channel.
	 *
	 * @param permissionType the permission type of this faro channel
	 */
	@Override
	public void setPermissionType(int permissionType) {
		_faroChannel.setPermissionType(permissionType);
	}

	/**
	 * Sets the primary key of this faro channel.
	 *
	 * @param primaryKey the primary key of this faro channel
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_faroChannel.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_faroChannel.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets the user ID of this faro channel.
	 *
	 * @param userId the user ID of this faro channel
	 */
	@Override
	public void setUserId(long userId) {
		_faroChannel.setUserId(userId);
	}

	/**
	 * Sets the user name of this faro channel.
	 *
	 * @param userName the user name of this faro channel
	 */
	@Override
	public void setUserName(String userName) {
		_faroChannel.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this faro channel.
	 *
	 * @param userUuid the user uuid of this faro channel
	 */
	@Override
	public void setUserUuid(String userUuid) {
		_faroChannel.setUserUuid(userUuid);
	}

	/**
	 * Sets the workspace group ID of this faro channel.
	 *
	 * @param workspaceGroupId the workspace group ID of this faro channel
	 */
	@Override
	public void setWorkspaceGroupId(long workspaceGroupId) {
		_faroChannel.setWorkspaceGroupId(workspaceGroupId);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<FaroChannel>
		toCacheModel() {

		return _faroChannel.toCacheModel();
	}

	@Override
	public FaroChannel toEscapedModel() {
		return new FaroChannelWrapper(_faroChannel.toEscapedModel());
	}

	@Override
	public String toString() {
		return _faroChannel.toString();
	}

	@Override
	public FaroChannel toUnescapedModel() {
		return new FaroChannelWrapper(_faroChannel.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _faroChannel.toXmlString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FaroChannelWrapper)) {
			return false;
		}

		FaroChannelWrapper faroChannelWrapper = (FaroChannelWrapper)object;

		if (Objects.equals(_faroChannel, faroChannelWrapper._faroChannel)) {
			return true;
		}

		return false;
	}

	@Override
	public FaroChannel getWrappedModel() {
		return _faroChannel;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _faroChannel.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _faroChannel.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_faroChannel.resetOriginalValues();
	}

	private final FaroChannel _faroChannel;

}