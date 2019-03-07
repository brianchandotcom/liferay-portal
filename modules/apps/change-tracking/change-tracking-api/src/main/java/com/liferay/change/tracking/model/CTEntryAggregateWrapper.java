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

package com.liferay.change.tracking.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link CTEntryAggregate}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CTEntryAggregate
 * @generated
 */
@ProviderType
public class CTEntryAggregateWrapper
	extends BaseModelWrapper<CTEntryAggregate>
	implements CTEntryAggregate, ModelWrapper<CTEntryAggregate> {

	public CTEntryAggregateWrapper(CTEntryAggregate ctEntryAggregate) {
		super(ctEntryAggregate);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("ctEntryAggregateId", getCtEntryAggregateId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("ownerCTEntryId", getOwnerCTEntryId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long ctEntryAggregateId = (Long)attributes.get("ctEntryAggregateId");

		if (ctEntryAggregateId != null) {
			setCtEntryAggregateId(ctEntryAggregateId);
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

		Long ownerCTEntryId = (Long)attributes.get("ownerCTEntryId");

		if (ownerCTEntryId != null) {
			setOwnerCTEntryId(ownerCTEntryId);
		}
	}

	/**
	 * Returns the company ID of this ct entry aggregate.
	 *
	 * @return the company ID of this ct entry aggregate
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this ct entry aggregate.
	 *
	 * @return the create date of this ct entry aggregate
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct entry aggregate ID of this ct entry aggregate.
	 *
	 * @return the ct entry aggregate ID of this ct entry aggregate
	 */
	@Override
	public long getCtEntryAggregateId() {
		return model.getCtEntryAggregateId();
	}

	/**
	 * Returns the modified date of this ct entry aggregate.
	 *
	 * @return the modified date of this ct entry aggregate
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the owner ct entry ID of this ct entry aggregate.
	 *
	 * @return the owner ct entry ID of this ct entry aggregate
	 */
	@Override
	public long getOwnerCTEntryId() {
		return model.getOwnerCTEntryId();
	}

	/**
	 * Returns the primary key of this ct entry aggregate.
	 *
	 * @return the primary key of this ct entry aggregate
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	@Override
	public java.util.List<CTEntry> getRelatedCTEntries() {
		return model.getRelatedCTEntries();
	}

	/**
	 * Returns the user ID of this ct entry aggregate.
	 *
	 * @return the user ID of this ct entry aggregate
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this ct entry aggregate.
	 *
	 * @return the user name of this ct entry aggregate
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this ct entry aggregate.
	 *
	 * @return the user uuid of this ct entry aggregate
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
	 * Sets the company ID of this ct entry aggregate.
	 *
	 * @param companyId the company ID of this ct entry aggregate
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this ct entry aggregate.
	 *
	 * @param createDate the create date of this ct entry aggregate
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct entry aggregate ID of this ct entry aggregate.
	 *
	 * @param ctEntryAggregateId the ct entry aggregate ID of this ct entry aggregate
	 */
	@Override
	public void setCtEntryAggregateId(long ctEntryAggregateId) {
		model.setCtEntryAggregateId(ctEntryAggregateId);
	}

	/**
	 * Sets the modified date of this ct entry aggregate.
	 *
	 * @param modifiedDate the modified date of this ct entry aggregate
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the owner ct entry ID of this ct entry aggregate.
	 *
	 * @param ownerCTEntryId the owner ct entry ID of this ct entry aggregate
	 */
	@Override
	public void setOwnerCTEntryId(long ownerCTEntryId) {
		model.setOwnerCTEntryId(ownerCTEntryId);
	}

	/**
	 * Sets the primary key of this ct entry aggregate.
	 *
	 * @param primaryKey the primary key of this ct entry aggregate
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this ct entry aggregate.
	 *
	 * @param userId the user ID of this ct entry aggregate
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this ct entry aggregate.
	 *
	 * @param userName the user name of this ct entry aggregate
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this ct entry aggregate.
	 *
	 * @param userUuid the user uuid of this ct entry aggregate
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected CTEntryAggregateWrapper wrap(CTEntryAggregate ctEntryAggregate) {
		return new CTEntryAggregateWrapper(ctEntryAggregate);
	}

}