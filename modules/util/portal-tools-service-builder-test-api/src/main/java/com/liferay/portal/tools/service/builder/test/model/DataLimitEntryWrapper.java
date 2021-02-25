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

package com.liferay.portal.tools.service.builder.test.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link DataLimitEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DataLimitEntry
 * @generated
 */
public class DataLimitEntryWrapper
	extends BaseModelWrapper<DataLimitEntry>
	implements DataLimitEntry, ModelWrapper<DataLimitEntry> {

	public DataLimitEntryWrapper(DataLimitEntry dataLimitEntry) {
		super(dataLimitEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("dataLimitEntryId", getDataLimitEntryId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long dataLimitEntryId = (Long)attributes.get("dataLimitEntryId");

		if (dataLimitEntryId != null) {
			setDataLimitEntryId(dataLimitEntryId);
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
	}

	/**
	 * Returns the company ID of this data limit entry.
	 *
	 * @return the company ID of this data limit entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this data limit entry.
	 *
	 * @return the create date of this data limit entry
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the data limit entry ID of this data limit entry.
	 *
	 * @return the data limit entry ID of this data limit entry
	 */
	@Override
	public long getDataLimitEntryId() {
		return model.getDataLimitEntryId();
	}

	/**
	 * Returns the modified date of this data limit entry.
	 *
	 * @return the modified date of this data limit entry
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the primary key of this data limit entry.
	 *
	 * @return the primary key of this data limit entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this data limit entry.
	 *
	 * @return the user ID of this data limit entry
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this data limit entry.
	 *
	 * @return the user name of this data limit entry
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this data limit entry.
	 *
	 * @return the user uuid of this data limit entry
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
	 * Sets the company ID of this data limit entry.
	 *
	 * @param companyId the company ID of this data limit entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this data limit entry.
	 *
	 * @param createDate the create date of this data limit entry
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the data limit entry ID of this data limit entry.
	 *
	 * @param dataLimitEntryId the data limit entry ID of this data limit entry
	 */
	@Override
	public void setDataLimitEntryId(long dataLimitEntryId) {
		model.setDataLimitEntryId(dataLimitEntryId);
	}

	/**
	 * Sets the modified date of this data limit entry.
	 *
	 * @param modifiedDate the modified date of this data limit entry
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the primary key of this data limit entry.
	 *
	 * @param primaryKey the primary key of this data limit entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this data limit entry.
	 *
	 * @param userId the user ID of this data limit entry
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this data limit entry.
	 *
	 * @param userName the user name of this data limit entry
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this data limit entry.
	 *
	 * @param userUuid the user uuid of this data limit entry
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected DataLimitEntryWrapper wrap(DataLimitEntry dataLimitEntry) {
		return new DataLimitEntryWrapper(dataLimitEntry);
	}

}