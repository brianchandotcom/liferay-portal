/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link Team}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see Team
 * @generated
 */
@ProviderType
public class TeamWrapper implements Team, ModelWrapper<Team> {
	public TeamWrapper(Team team) {
		_team = team;
	}

	@Override
	public Class<?> getModelClass() {
		return Team.class;
	}

	@Override
	public String getModelClassName() {
		return Team.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("teamId", getTeamId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("groupId", getGroupId());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("mvccVersion", getMvccVersion());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long teamId = (Long)attributes.get("teamId");

		if (teamId != null) {
			setTeamId(teamId);
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

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}
	}

	/**
	* Returns the primary key of this team.
	*
	* @return the primary key of this team
	*/
	@Override
	public long getPrimaryKey() {
		return _team.getPrimaryKey();
	}

	/**
	* Sets the primary key of this team.
	*
	* @param primaryKey the primary key of this team
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_team.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the team ID of this team.
	*
	* @return the team ID of this team
	*/
	@Override
	public long getTeamId() {
		return _team.getTeamId();
	}

	/**
	* Sets the team ID of this team.
	*
	* @param teamId the team ID of this team
	*/
	@Override
	public void setTeamId(long teamId) {
		_team.setTeamId(teamId);
	}

	/**
	* Returns the company ID of this team.
	*
	* @return the company ID of this team
	*/
	@Override
	public long getCompanyId() {
		return _team.getCompanyId();
	}

	/**
	* Sets the company ID of this team.
	*
	* @param companyId the company ID of this team
	*/
	@Override
	public void setCompanyId(long companyId) {
		_team.setCompanyId(companyId);
	}

	/**
	* Returns the user ID of this team.
	*
	* @return the user ID of this team
	*/
	@Override
	public long getUserId() {
		return _team.getUserId();
	}

	/**
	* Sets the user ID of this team.
	*
	* @param userId the user ID of this team
	*/
	@Override
	public void setUserId(long userId) {
		_team.setUserId(userId);
	}

	/**
	* Returns the user uuid of this team.
	*
	* @return the user uuid of this team
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _team.getUserUuid();
	}

	/**
	* Sets the user uuid of this team.
	*
	* @param userUuid the user uuid of this team
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_team.setUserUuid(userUuid);
	}

	/**
	* Returns the user name of this team.
	*
	* @return the user name of this team
	*/
	@Override
	public java.lang.String getUserName() {
		return _team.getUserName();
	}

	/**
	* Sets the user name of this team.
	*
	* @param userName the user name of this team
	*/
	@Override
	public void setUserName(java.lang.String userName) {
		_team.setUserName(userName);
	}

	/**
	* Returns the create date of this team.
	*
	* @return the create date of this team
	*/
	@Override
	public java.util.Date getCreateDate() {
		return _team.getCreateDate();
	}

	/**
	* Sets the create date of this team.
	*
	* @param createDate the create date of this team
	*/
	@Override
	public void setCreateDate(java.util.Date createDate) {
		_team.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this team.
	*
	* @return the modified date of this team
	*/
	@Override
	public java.util.Date getModifiedDate() {
		return _team.getModifiedDate();
	}

	/**
	* Sets the modified date of this team.
	*
	* @param modifiedDate the modified date of this team
	*/
	@Override
	public void setModifiedDate(java.util.Date modifiedDate) {
		_team.setModifiedDate(modifiedDate);
	}

	/**
	* Returns the group ID of this team.
	*
	* @return the group ID of this team
	*/
	@Override
	public long getGroupId() {
		return _team.getGroupId();
	}

	/**
	* Sets the group ID of this team.
	*
	* @param groupId the group ID of this team
	*/
	@Override
	public void setGroupId(long groupId) {
		_team.setGroupId(groupId);
	}

	/**
	* Returns the name of this team.
	*
	* @return the name of this team
	*/
	@Override
	public java.lang.String getName() {
		return _team.getName();
	}

	/**
	* Sets the name of this team.
	*
	* @param name the name of this team
	*/
	@Override
	public void setName(java.lang.String name) {
		_team.setName(name);
	}

	/**
	* Returns the description of this team.
	*
	* @return the description of this team
	*/
	@Override
	public java.lang.String getDescription() {
		return _team.getDescription();
	}

	/**
	* Sets the description of this team.
	*
	* @param description the description of this team
	*/
	@Override
	public void setDescription(java.lang.String description) {
		_team.setDescription(description);
	}

	/**
	* Returns the mvcc version of this team.
	*
	* @return the mvcc version of this team
	*/
	@Override
	public long getMvccVersion() {
		return _team.getMvccVersion();
	}

	/**
	* Sets the mvcc version of this team.
	*
	* @param mvccVersion the mvcc version of this team
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		_team.setMvccVersion(mvccVersion);
	}

	@Override
	public boolean isNew() {
		return _team.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_team.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _team.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_team.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _team.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _team.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_team.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _team.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_team.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_team.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_team.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new TeamWrapper((Team)_team.clone());
	}

	@Override
	public int compareTo(com.liferay.portal.model.Team team) {
		return _team.compareTo(team);
	}

	@Override
	public int hashCode() {
		return _team.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portal.model.Team> toCacheModel() {
		return _team.toCacheModel();
	}

	@Override
	public com.liferay.portal.model.Team toEscapedModel() {
		return new TeamWrapper(_team.toEscapedModel());
	}

	@Override
	public com.liferay.portal.model.Team toUnescapedModel() {
		return new TeamWrapper(_team.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _team.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _team.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_team.persist();
	}

	@Override
	public com.liferay.portal.model.Role getRole()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _team.getRole();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof TeamWrapper)) {
			return false;
		}

		TeamWrapper teamWrapper = (TeamWrapper)obj;

		if (Validator.equals(_team, teamWrapper._team)) {
			return true;
		}

		return false;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	@Deprecated
	public Team getWrappedTeam() {
		return _team;
	}

	@Override
	public Team getWrappedModel() {
		return _team;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _team.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _team.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_team.resetOriginalValues();
	}

	private Team _team;
}