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
 * This class is a wrapper for {@link FaroPreferences}.
 * </p>
 *
 * @author Matthew Kong
 * @see FaroPreferences
 * @generated
 */
public class FaroPreferencesWrapper
	implements FaroPreferences, ModelWrapper<FaroPreferences> {

	public FaroPreferencesWrapper(FaroPreferences faroPreferences) {
		_faroPreferences = faroPreferences;
	}

	@Override
	public Class<?> getModelClass() {
		return FaroPreferences.class;
	}

	@Override
	public String getModelClassName() {
		return FaroPreferences.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("faroPreferencesId", getFaroPreferencesId());
		attributes.put("groupId", getGroupId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createTime", getCreateTime());
		attributes.put("modifiedTime", getModifiedTime());
		attributes.put("ownerId", getOwnerId());
		attributes.put("preferences", getPreferences());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long faroPreferencesId = (Long)attributes.get("faroPreferencesId");

		if (faroPreferencesId != null) {
			setFaroPreferencesId(faroPreferencesId);
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

		Long ownerId = (Long)attributes.get("ownerId");

		if (ownerId != null) {
			setOwnerId(ownerId);
		}

		String preferences = (String)attributes.get("preferences");

		if (preferences != null) {
			setPreferences(preferences);
		}
	}

	@Override
	public Object clone() {
		return new FaroPreferencesWrapper(
			(FaroPreferences)_faroPreferences.clone());
	}

	@Override
	public int compareTo(FaroPreferences faroPreferences) {
		return _faroPreferences.compareTo(faroPreferences);
	}

	/**
	 * Returns the create time of this faro preferences.
	 *
	 * @return the create time of this faro preferences
	 */
	@Override
	public long getCreateTime() {
		return _faroPreferences.getCreateTime();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _faroPreferences.getExpandoBridge();
	}

	/**
	 * Returns the faro preferences ID of this faro preferences.
	 *
	 * @return the faro preferences ID of this faro preferences
	 */
	@Override
	public long getFaroPreferencesId() {
		return _faroPreferences.getFaroPreferencesId();
	}

	/**
	 * Returns the group ID of this faro preferences.
	 *
	 * @return the group ID of this faro preferences
	 */
	@Override
	public long getGroupId() {
		return _faroPreferences.getGroupId();
	}

	/**
	 * Returns the modified time of this faro preferences.
	 *
	 * @return the modified time of this faro preferences
	 */
	@Override
	public long getModifiedTime() {
		return _faroPreferences.getModifiedTime();
	}

	/**
	 * Returns the owner ID of this faro preferences.
	 *
	 * @return the owner ID of this faro preferences
	 */
	@Override
	public long getOwnerId() {
		return _faroPreferences.getOwnerId();
	}

	/**
	 * Returns the preferences of this faro preferences.
	 *
	 * @return the preferences of this faro preferences
	 */
	@Override
	public String getPreferences() {
		return _faroPreferences.getPreferences();
	}

	/**
	 * Returns the primary key of this faro preferences.
	 *
	 * @return the primary key of this faro preferences
	 */
	@Override
	public long getPrimaryKey() {
		return _faroPreferences.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _faroPreferences.getPrimaryKeyObj();
	}

	/**
	 * Returns the user ID of this faro preferences.
	 *
	 * @return the user ID of this faro preferences
	 */
	@Override
	public long getUserId() {
		return _faroPreferences.getUserId();
	}

	/**
	 * Returns the user name of this faro preferences.
	 *
	 * @return the user name of this faro preferences
	 */
	@Override
	public String getUserName() {
		return _faroPreferences.getUserName();
	}

	/**
	 * Returns the user uuid of this faro preferences.
	 *
	 * @return the user uuid of this faro preferences
	 */
	@Override
	public String getUserUuid() {
		return _faroPreferences.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _faroPreferences.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _faroPreferences.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _faroPreferences.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _faroPreferences.isNew();
	}

	@Override
	public void persist() {
		_faroPreferences.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_faroPreferences.setCachedModel(cachedModel);
	}

	/**
	 * Sets the create time of this faro preferences.
	 *
	 * @param createTime the create time of this faro preferences
	 */
	@Override
	public void setCreateTime(long createTime) {
		_faroPreferences.setCreateTime(createTime);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_faroPreferences.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_faroPreferences.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_faroPreferences.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	 * Sets the faro preferences ID of this faro preferences.
	 *
	 * @param faroPreferencesId the faro preferences ID of this faro preferences
	 */
	@Override
	public void setFaroPreferencesId(long faroPreferencesId) {
		_faroPreferences.setFaroPreferencesId(faroPreferencesId);
	}

	/**
	 * Sets the group ID of this faro preferences.
	 *
	 * @param groupId the group ID of this faro preferences
	 */
	@Override
	public void setGroupId(long groupId) {
		_faroPreferences.setGroupId(groupId);
	}

	/**
	 * Sets the modified time of this faro preferences.
	 *
	 * @param modifiedTime the modified time of this faro preferences
	 */
	@Override
	public void setModifiedTime(long modifiedTime) {
		_faroPreferences.setModifiedTime(modifiedTime);
	}

	@Override
	public void setNew(boolean n) {
		_faroPreferences.setNew(n);
	}

	/**
	 * Sets the owner ID of this faro preferences.
	 *
	 * @param ownerId the owner ID of this faro preferences
	 */
	@Override
	public void setOwnerId(long ownerId) {
		_faroPreferences.setOwnerId(ownerId);
	}

	/**
	 * Sets the preferences of this faro preferences.
	 *
	 * @param preferences the preferences of this faro preferences
	 */
	@Override
	public void setPreferences(String preferences) {
		_faroPreferences.setPreferences(preferences);
	}

	/**
	 * Sets the primary key of this faro preferences.
	 *
	 * @param primaryKey the primary key of this faro preferences
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_faroPreferences.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_faroPreferences.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets the user ID of this faro preferences.
	 *
	 * @param userId the user ID of this faro preferences
	 */
	@Override
	public void setUserId(long userId) {
		_faroPreferences.setUserId(userId);
	}

	/**
	 * Sets the user name of this faro preferences.
	 *
	 * @param userName the user name of this faro preferences
	 */
	@Override
	public void setUserName(String userName) {
		_faroPreferences.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this faro preferences.
	 *
	 * @param userUuid the user uuid of this faro preferences
	 */
	@Override
	public void setUserUuid(String userUuid) {
		_faroPreferences.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<FaroPreferences>
		toCacheModel() {

		return _faroPreferences.toCacheModel();
	}

	@Override
	public FaroPreferences toEscapedModel() {
		return new FaroPreferencesWrapper(_faroPreferences.toEscapedModel());
	}

	@Override
	public String toString() {
		return _faroPreferences.toString();
	}

	@Override
	public FaroPreferences toUnescapedModel() {
		return new FaroPreferencesWrapper(_faroPreferences.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _faroPreferences.toXmlString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof FaroPreferencesWrapper)) {
			return false;
		}

		FaroPreferencesWrapper faroPreferencesWrapper =
			(FaroPreferencesWrapper)object;

		if (Objects.equals(
				_faroPreferences, faroPreferencesWrapper._faroPreferences)) {

			return true;
		}

		return false;
	}

	@Override
	public FaroPreferences getWrappedModel() {
		return _faroPreferences;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _faroPreferences.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _faroPreferences.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_faroPreferences.resetOriginalValues();
	}

	private final FaroPreferences _faroPreferences;

}