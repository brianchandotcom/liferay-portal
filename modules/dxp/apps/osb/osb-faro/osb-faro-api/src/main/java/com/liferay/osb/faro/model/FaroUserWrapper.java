/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link FaroUser}.
 * </p>
 *
 * @author Matthew Kong
 * @see FaroUser
 * @generated
 */
public class FaroUserWrapper
	extends BaseModelWrapper<FaroUser>
	implements FaroUser, ModelWrapper<FaroUser> {

	public FaroUserWrapper(FaroUser faroUser) {
		super(faroUser);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("faroUserId", getFaroUserId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
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
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long faroUserId = (Long)attributes.get("faroUserId");

		if (faroUserId != null) {
			setFaroUserId(faroUserId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
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
	public FaroUser cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this faro user.
	 *
	 * @return the company ID of this faro user
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create time of this faro user.
	 *
	 * @return the create time of this faro user
	 */
	@Override
	public long getCreateTime() {
		return model.getCreateTime();
	}

	/**
	 * Returns the email address of this faro user.
	 *
	 * @return the email address of this faro user
	 */
	@Override
	public String getEmailAddress() {
		return model.getEmailAddress();
	}

	/**
	 * Returns the faro user ID of this faro user.
	 *
	 * @return the faro user ID of this faro user
	 */
	@Override
	public long getFaroUserId() {
		return model.getFaroUserId();
	}

	/**
	 * Returns the faro user uuid of this faro user.
	 *
	 * @return the faro user uuid of this faro user
	 */
	@Override
	public String getFaroUserUuid() {
		return model.getFaroUserUuid();
	}

	/**
	 * Returns the group ID of this faro user.
	 *
	 * @return the group ID of this faro user
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the key of this faro user.
	 *
	 * @return the key of this faro user
	 */
	@Override
	public String getKey() {
		return model.getKey();
	}

	/**
	 * Returns the live user ID of this faro user.
	 *
	 * @return the live user ID of this faro user
	 */
	@Override
	public long getLiveUserId() {
		return model.getLiveUserId();
	}

	/**
	 * Returns the live user uuid of this faro user.
	 *
	 * @return the live user uuid of this faro user
	 */
	@Override
	public String getLiveUserUuid() {
		return model.getLiveUserUuid();
	}

	/**
	 * Returns the modified time of this faro user.
	 *
	 * @return the modified time of this faro user
	 */
	@Override
	public long getModifiedTime() {
		return model.getModifiedTime();
	}

	/**
	 * Returns the mvcc version of this faro user.
	 *
	 * @return the mvcc version of this faro user
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this faro user.
	 *
	 * @return the primary key of this faro user
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the role ID of this faro user.
	 *
	 * @return the role ID of this faro user
	 */
	@Override
	public long getRoleId() {
		return model.getRoleId();
	}

	/**
	 * Returns the status of this faro user.
	 *
	 * @return the status of this faro user
	 */
	@Override
	public int getStatus() {
		return model.getStatus();
	}

	/**
	 * Returns the user ID of this faro user.
	 *
	 * @return the user ID of this faro user
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this faro user.
	 *
	 * @return the user name of this faro user
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this faro user.
	 *
	 * @return the user uuid of this faro user
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
	 * Sets the company ID of this faro user.
	 *
	 * @param companyId the company ID of this faro user
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create time of this faro user.
	 *
	 * @param createTime the create time of this faro user
	 */
	@Override
	public void setCreateTime(long createTime) {
		model.setCreateTime(createTime);
	}

	/**
	 * Sets the email address of this faro user.
	 *
	 * @param emailAddress the email address of this faro user
	 */
	@Override
	public void setEmailAddress(String emailAddress) {
		model.setEmailAddress(emailAddress);
	}

	/**
	 * Sets the faro user ID of this faro user.
	 *
	 * @param faroUserId the faro user ID of this faro user
	 */
	@Override
	public void setFaroUserId(long faroUserId) {
		model.setFaroUserId(faroUserId);
	}

	/**
	 * Sets the faro user uuid of this faro user.
	 *
	 * @param faroUserUuid the faro user uuid of this faro user
	 */
	@Override
	public void setFaroUserUuid(String faroUserUuid) {
		model.setFaroUserUuid(faroUserUuid);
	}

	/**
	 * Sets the group ID of this faro user.
	 *
	 * @param groupId the group ID of this faro user
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the key of this faro user.
	 *
	 * @param key the key of this faro user
	 */
	@Override
	public void setKey(String key) {
		model.setKey(key);
	}

	/**
	 * Sets the live user ID of this faro user.
	 *
	 * @param liveUserId the live user ID of this faro user
	 */
	@Override
	public void setLiveUserId(long liveUserId) {
		model.setLiveUserId(liveUserId);
	}

	/**
	 * Sets the live user uuid of this faro user.
	 *
	 * @param liveUserUuid the live user uuid of this faro user
	 */
	@Override
	public void setLiveUserUuid(String liveUserUuid) {
		model.setLiveUserUuid(liveUserUuid);
	}

	/**
	 * Sets the modified time of this faro user.
	 *
	 * @param modifiedTime the modified time of this faro user
	 */
	@Override
	public void setModifiedTime(long modifiedTime) {
		model.setModifiedTime(modifiedTime);
	}

	/**
	 * Sets the mvcc version of this faro user.
	 *
	 * @param mvccVersion the mvcc version of this faro user
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this faro user.
	 *
	 * @param primaryKey the primary key of this faro user
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the role ID of this faro user.
	 *
	 * @param roleId the role ID of this faro user
	 */
	@Override
	public void setRoleId(long roleId) {
		model.setRoleId(roleId);
	}

	/**
	 * Sets the status of this faro user.
	 *
	 * @param status the status of this faro user
	 */
	@Override
	public void setStatus(int status) {
		model.setStatus(status);
	}

	/**
	 * Sets the user ID of this faro user.
	 *
	 * @param userId the user ID of this faro user
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this faro user.
	 *
	 * @param userName the user name of this faro user
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this faro user.
	 *
	 * @param userUuid the user uuid of this faro user
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
	protected FaroUserWrapper wrap(FaroUser faroUser) {
		return new FaroUserWrapper(faroUser);
	}

}