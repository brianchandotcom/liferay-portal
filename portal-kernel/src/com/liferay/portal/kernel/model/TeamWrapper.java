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

import com.liferay.exportimport.kernel.lar.StagedModelType;

import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;

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
public class TeamWrapper extends BaseModelWrapper<Team> implements Team,
	ModelWrapper<Team> {
	public TeamWrapper(Team team) {
		super(team);
	}

	/**
	* Returns the company ID of this team.
	*
	* @return the company ID of this team
	*/
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	* Returns the create date of this team.
	*
	* @return the create date of this team
	*/
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	* Returns the description of this team.
	*
	* @return the description of this team
	*/
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	/**
	* Returns the group ID of this team.
	*
	* @return the group ID of this team
	*/
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	* Returns the last publish date of this team.
	*
	* @return the last publish date of this team
	*/
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	/**
	* Returns the modified date of this team.
	*
	* @return the modified date of this team
	*/
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	* Returns the mvcc version of this team.
	*
	* @return the mvcc version of this team
	*/
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	* Returns the name of this team.
	*
	* @return the name of this team
	*/
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	* Returns the primary key of this team.
	*
	* @return the primary key of this team
	*/
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	@Override
	public Role getRole()
		throws com.liferay.portal.kernel.exception.PortalException {
		return model.getRole();
	}

	/**
	* Returns the team ID of this team.
	*
	* @return the team ID of this team
	*/
	@Override
	public long getTeamId() {
		return model.getTeamId();
	}

	/**
	* Returns the user ID of this team.
	*
	* @return the user ID of this team
	*/
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	* Returns the user name of this team.
	*
	* @return the user name of this team
	*/
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	* Returns the user uuid of this team.
	*
	* @return the user uuid of this team
	*/
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	* Returns the uuid of this team.
	*
	* @return the uuid of this team
	*/
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	* Sets the company ID of this team.
	*
	* @param companyId the company ID of this team
	*/
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this team.
	*
	* @param createDate the create date of this team
	*/
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	* Sets the description of this team.
	*
	* @param description the description of this team
	*/
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	* Sets the group ID of this team.
	*
	* @param groupId the group ID of this team
	*/
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	* Sets the last publish date of this team.
	*
	* @param lastPublishDate the last publish date of this team
	*/
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	* Sets the modified date of this team.
	*
	* @param modifiedDate the modified date of this team
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the mvcc version of this team.
	*
	* @param mvccVersion the mvcc version of this team
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	* Sets the name of this team.
	*
	* @param name the name of this team
	*/
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	* Sets the primary key of this team.
	*
	* @param primaryKey the primary key of this team
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	* Sets the team ID of this team.
	*
	* @param teamId the team ID of this team
	*/
	@Override
	public void setTeamId(long teamId) {
		model.setTeamId(teamId);
	}

	/**
	* Sets the user ID of this team.
	*
	* @param userId the user ID of this team
	*/
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	* Sets the user name of this team.
	*
	* @param userName the user name of this team
	*/
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	* Sets the user uuid of this team.
	*
	* @param userUuid the user uuid of this team
	*/
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	* Sets the uuid of this team.
	*
	* @param uuid the uuid of this team
	*/
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected TeamWrapper wrap(Team team) {
		return new TeamWrapper(team);
	}
}