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

package com.liferay.portal.rolling.restart.sample.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link RollingRestartSampleEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RollingRestartSampleEntry
 * @generated
 */
public class RollingRestartSampleEntryWrapper
	extends BaseModelWrapper<RollingRestartSampleEntry>
	implements ModelWrapper<RollingRestartSampleEntry>,
			   RollingRestartSampleEntry {

	public RollingRestartSampleEntryWrapper(
		RollingRestartSampleEntry rollingRestartSampleEntry) {

		super(rollingRestartSampleEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("entryId", getEntryId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("status", getStatus());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long entryId = (Long)attributes.get("entryId");

		if (entryId != null) {
			setEntryId(entryId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		Integer status = (Integer)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}
	}

	/**
	 * Returns the company ID of this rolling restart sample entry.
	 *
	 * @return the company ID of this rolling restart sample entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this rolling restart sample entry.
	 *
	 * @return the create date of this rolling restart sample entry
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the description of this rolling restart sample entry.
	 *
	 * @return the description of this rolling restart sample entry
	 */
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	/**
	 * Returns the entry ID of this rolling restart sample entry.
	 *
	 * @return the entry ID of this rolling restart sample entry
	 */
	@Override
	public long getEntryId() {
		return model.getEntryId();
	}

	/**
	 * Returns the modified date of this rolling restart sample entry.
	 *
	 * @return the modified date of this rolling restart sample entry
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this rolling restart sample entry.
	 *
	 * @return the mvcc version of this rolling restart sample entry
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this rolling restart sample entry.
	 *
	 * @return the name of this rolling restart sample entry
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this rolling restart sample entry.
	 *
	 * @return the primary key of this rolling restart sample entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the status of this rolling restart sample entry.
	 *
	 * @return the status of this rolling restart sample entry
	 */
	@Override
	public int getStatus() {
		return model.getStatus();
	}

	/**
	 * Returns the user ID of this rolling restart sample entry.
	 *
	 * @return the user ID of this rolling restart sample entry
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user uuid of this rolling restart sample entry.
	 *
	 * @return the user uuid of this rolling restart sample entry
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
	 * Sets the company ID of this rolling restart sample entry.
	 *
	 * @param companyId the company ID of this rolling restart sample entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this rolling restart sample entry.
	 *
	 * @param createDate the create date of this rolling restart sample entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the description of this rolling restart sample entry.
	 *
	 * @param description the description of this rolling restart sample entry
	 */
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	 * Sets the entry ID of this rolling restart sample entry.
	 *
	 * @param entryId the entry ID of this rolling restart sample entry
	 */
	@Override
	public void setEntryId(long entryId) {
		model.setEntryId(entryId);
	}

	/**
	 * Sets the modified date of this rolling restart sample entry.
	 *
	 * @param modifiedDate the modified date of this rolling restart sample entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this rolling restart sample entry.
	 *
	 * @param mvccVersion the mvcc version of this rolling restart sample entry
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this rolling restart sample entry.
	 *
	 * @param name the name of this rolling restart sample entry
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this rolling restart sample entry.
	 *
	 * @param primaryKey the primary key of this rolling restart sample entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the status of this rolling restart sample entry.
	 *
	 * @param status the status of this rolling restart sample entry
	 */
	@Override
	public void setStatus(int status) {
		model.setStatus(status);
	}

	/**
	 * Sets the user ID of this rolling restart sample entry.
	 *
	 * @param userId the user ID of this rolling restart sample entry
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user uuid of this rolling restart sample entry.
	 *
	 * @param userUuid the user uuid of this rolling restart sample entry
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected RollingRestartSampleEntryWrapper wrap(
		RollingRestartSampleEntry rollingRestartSampleEntry) {

		return new RollingRestartSampleEntryWrapper(rollingRestartSampleEntry);
	}

}