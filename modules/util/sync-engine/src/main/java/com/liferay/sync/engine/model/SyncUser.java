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

package com.liferay.sync.engine.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import com.liferay.sync.engine.service.persistence.BasePersistenceImpl;

/**
 * @author Dennis Ju
 */
@DatabaseTable(daoClass = BasePersistenceImpl.class, tableName = "SyncUser")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SyncUser extends BaseModel {

	public String getEmailAddress() {
		return emailAddress;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getFullName() {
		if (lastName.isEmpty()) {
			return firstName;
		}

		return firstName + " " + lastName;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public String getLastName() {
		return lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public long getPortraitId() {
		return portraitId;
	}

	public String getScreenName() {
		return screenName;
	}

	public long getSyncAccountId() {
		return syncAccountId;
	}

	public long getSyncUserId() {
		return syncUserId;
	}

	public long getUserId() {
		return userId;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public void setPortraitId(long portraitId) {
		this.portraitId = portraitId;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public void setSyncAccountId(long syncAccountId) {
		this.syncAccountId = syncAccountId;
	}

	public void setSyncUserId(long syncUserId) {
		this.syncUserId = syncUserId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	@DatabaseField(useGetSet = true)
	protected String emailAddress;

	@DatabaseField(useGetSet = true)
	protected String firstName;

	@DatabaseField(useGetSet = true)
	protected String jobTitle;

	@DatabaseField(useGetSet = true)
	protected String lastName;

	@DatabaseField(useGetSet = true)
	protected String middleName;

	@DatabaseField(useGetSet = true)
	protected long portraitId;

	@DatabaseField(useGetSet = true)
	protected String screenName;

	@DatabaseField(useGetSet = true)
	protected long syncAccountId;

	@DatabaseField(generatedId = true, useGetSet = true)
	protected long syncUserId;

	@DatabaseField(useGetSet = true)
	protected long userId;

}