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

package com.liferay.batch.planner.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link BatchPlannerPolicy}.
 * </p>
 *
 * @author Igor Beslic
 * @see BatchPlannerPolicy
 * @generated
 */
public class BatchPlannerPolicyWrapper
	extends BaseModelWrapper<BatchPlannerPolicy>
	implements BatchPlannerPolicy, ModelWrapper<BatchPlannerPolicy> {

	public BatchPlannerPolicyWrapper(BatchPlannerPolicy batchPlannerPolicy) {
		super(batchPlannerPolicy);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("batchPlannerPolicyId", getBatchPlannerPolicyId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("batchPlannerPlanId", getBatchPlannerPlanId());
		attributes.put("name", getName());
		attributes.put("value", getValue());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long batchPlannerPolicyId = (Long)attributes.get(
			"batchPlannerPolicyId");

		if (batchPlannerPolicyId != null) {
			setBatchPlannerPolicyId(batchPlannerPolicyId);
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

		Long batchPlannerPlanId = (Long)attributes.get("batchPlannerPlanId");

		if (batchPlannerPlanId != null) {
			setBatchPlannerPlanId(batchPlannerPlanId);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String value = (String)attributes.get("value");

		if (value != null) {
			setValue(value);
		}
	}

	/**
	 * Returns the batch planner plan ID of this batch planner policy.
	 *
	 * @return the batch planner plan ID of this batch planner policy
	 */
	@Override
	public long getBatchPlannerPlanId() {
		return model.getBatchPlannerPlanId();
	}

	/**
	 * Returns the batch planner policy ID of this batch planner policy.
	 *
	 * @return the batch planner policy ID of this batch planner policy
	 */
	@Override
	public long getBatchPlannerPolicyId() {
		return model.getBatchPlannerPolicyId();
	}

	/**
	 * Returns the company ID of this batch planner policy.
	 *
	 * @return the company ID of this batch planner policy
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this batch planner policy.
	 *
	 * @return the create date of this batch planner policy
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the modified date of this batch planner policy.
	 *
	 * @return the modified date of this batch planner policy
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this batch planner policy.
	 *
	 * @return the mvcc version of this batch planner policy
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this batch planner policy.
	 *
	 * @return the name of this batch planner policy
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this batch planner policy.
	 *
	 * @return the primary key of this batch planner policy
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this batch planner policy.
	 *
	 * @return the user ID of this batch planner policy
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this batch planner policy.
	 *
	 * @return the user name of this batch planner policy
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this batch planner policy.
	 *
	 * @return the user uuid of this batch planner policy
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the value of this batch planner policy.
	 *
	 * @return the value of this batch planner policy
	 */
	@Override
	public String getValue() {
		return model.getValue();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the batch planner plan ID of this batch planner policy.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID of this batch planner policy
	 */
	@Override
	public void setBatchPlannerPlanId(long batchPlannerPlanId) {
		model.setBatchPlannerPlanId(batchPlannerPlanId);
	}

	/**
	 * Sets the batch planner policy ID of this batch planner policy.
	 *
	 * @param batchPlannerPolicyId the batch planner policy ID of this batch planner policy
	 */
	@Override
	public void setBatchPlannerPolicyId(long batchPlannerPolicyId) {
		model.setBatchPlannerPolicyId(batchPlannerPolicyId);
	}

	/**
	 * Sets the company ID of this batch planner policy.
	 *
	 * @param companyId the company ID of this batch planner policy
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this batch planner policy.
	 *
	 * @param createDate the create date of this batch planner policy
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the modified date of this batch planner policy.
	 *
	 * @param modifiedDate the modified date of this batch planner policy
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this batch planner policy.
	 *
	 * @param mvccVersion the mvcc version of this batch planner policy
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this batch planner policy.
	 *
	 * @param name the name of this batch planner policy
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this batch planner policy.
	 *
	 * @param primaryKey the primary key of this batch planner policy
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this batch planner policy.
	 *
	 * @param userId the user ID of this batch planner policy
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this batch planner policy.
	 *
	 * @param userName the user name of this batch planner policy
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this batch planner policy.
	 *
	 * @param userUuid the user uuid of this batch planner policy
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the value of this batch planner policy.
	 *
	 * @param value the value of this batch planner policy
	 */
	@Override
	public void setValue(String value) {
		model.setValue(value);
	}

	@Override
	protected BatchPlannerPolicyWrapper wrap(
		BatchPlannerPolicy batchPlannerPolicy) {

		return new BatchPlannerPolicyWrapper(batchPlannerPolicy);
	}

}