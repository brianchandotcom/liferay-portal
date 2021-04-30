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
 * This class is a wrapper for {@link BatchPlannerLog}.
 * </p>
 *
 * @author Igor Beslic
 * @see BatchPlannerLog
 * @generated
 */
public class BatchPlannerLogWrapper
	extends BaseModelWrapper<BatchPlannerLog>
	implements BatchPlannerLog, ModelWrapper<BatchPlannerLog> {

	public BatchPlannerLogWrapper(BatchPlannerLog batchPlannerLog) {
		super(batchPlannerLog);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("batchPlannerLogId", getBatchPlannerLogId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("batchPlannerPlanId", getBatchPlannerPlanId());
		attributes.put(
			"batchExternalReferenceCode", getBatchExternalReferenceCode());
		attributes.put(
			"dispatchExternalReferenceCode",
			getDispatchExternalReferenceCode());
		attributes.put("size", getSize());
		attributes.put("total", getTotal());
		attributes.put("status", getStatus());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long batchPlannerLogId = (Long)attributes.get("batchPlannerLogId");

		if (batchPlannerLogId != null) {
			setBatchPlannerLogId(batchPlannerLogId);
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

		String batchExternalReferenceCode = (String)attributes.get(
			"batchExternalReferenceCode");

		if (batchExternalReferenceCode != null) {
			setBatchExternalReferenceCode(batchExternalReferenceCode);
		}

		String dispatchExternalReferenceCode = (String)attributes.get(
			"dispatchExternalReferenceCode");

		if (dispatchExternalReferenceCode != null) {
			setDispatchExternalReferenceCode(dispatchExternalReferenceCode);
		}

		Integer size = (Integer)attributes.get("size");

		if (size != null) {
			setSize(size);
		}

		Integer total = (Integer)attributes.get("total");

		if (total != null) {
			setTotal(total);
		}

		String status = (String)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}
	}

	/**
	 * Returns the batch external reference code of this batch planner log.
	 *
	 * @return the batch external reference code of this batch planner log
	 */
	@Override
	public String getBatchExternalReferenceCode() {
		return model.getBatchExternalReferenceCode();
	}

	/**
	 * Returns the batch planner log ID of this batch planner log.
	 *
	 * @return the batch planner log ID of this batch planner log
	 */
	@Override
	public long getBatchPlannerLogId() {
		return model.getBatchPlannerLogId();
	}

	/**
	 * Returns the batch planner plan ID of this batch planner log.
	 *
	 * @return the batch planner plan ID of this batch planner log
	 */
	@Override
	public long getBatchPlannerPlanId() {
		return model.getBatchPlannerPlanId();
	}

	/**
	 * Returns the company ID of this batch planner log.
	 *
	 * @return the company ID of this batch planner log
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this batch planner log.
	 *
	 * @return the create date of this batch planner log
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the dispatch external reference code of this batch planner log.
	 *
	 * @return the dispatch external reference code of this batch planner log
	 */
	@Override
	public String getDispatchExternalReferenceCode() {
		return model.getDispatchExternalReferenceCode();
	}

	/**
	 * Returns the modified date of this batch planner log.
	 *
	 * @return the modified date of this batch planner log
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this batch planner log.
	 *
	 * @return the mvcc version of this batch planner log
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this batch planner log.
	 *
	 * @return the primary key of this batch planner log
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the size of this batch planner log.
	 *
	 * @return the size of this batch planner log
	 */
	@Override
	public Integer getSize() {
		return model.getSize();
	}

	/**
	 * Returns the status of this batch planner log.
	 *
	 * @return the status of this batch planner log
	 */
	@Override
	public String getStatus() {
		return model.getStatus();
	}

	/**
	 * Returns the total of this batch planner log.
	 *
	 * @return the total of this batch planner log
	 */
	@Override
	public Integer getTotal() {
		return model.getTotal();
	}

	/**
	 * Returns the user ID of this batch planner log.
	 *
	 * @return the user ID of this batch planner log
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this batch planner log.
	 *
	 * @return the user name of this batch planner log
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this batch planner log.
	 *
	 * @return the user uuid of this batch planner log
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
	 * Sets the batch external reference code of this batch planner log.
	 *
	 * @param batchExternalReferenceCode the batch external reference code of this batch planner log
	 */
	@Override
	public void setBatchExternalReferenceCode(
		String batchExternalReferenceCode) {

		model.setBatchExternalReferenceCode(batchExternalReferenceCode);
	}

	/**
	 * Sets the batch planner log ID of this batch planner log.
	 *
	 * @param batchPlannerLogId the batch planner log ID of this batch planner log
	 */
	@Override
	public void setBatchPlannerLogId(long batchPlannerLogId) {
		model.setBatchPlannerLogId(batchPlannerLogId);
	}

	/**
	 * Sets the batch planner plan ID of this batch planner log.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID of this batch planner log
	 */
	@Override
	public void setBatchPlannerPlanId(long batchPlannerPlanId) {
		model.setBatchPlannerPlanId(batchPlannerPlanId);
	}

	/**
	 * Sets the company ID of this batch planner log.
	 *
	 * @param companyId the company ID of this batch planner log
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this batch planner log.
	 *
	 * @param createDate the create date of this batch planner log
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the dispatch external reference code of this batch planner log.
	 *
	 * @param dispatchExternalReferenceCode the dispatch external reference code of this batch planner log
	 */
	@Override
	public void setDispatchExternalReferenceCode(
		String dispatchExternalReferenceCode) {

		model.setDispatchExternalReferenceCode(dispatchExternalReferenceCode);
	}

	/**
	 * Sets the modified date of this batch planner log.
	 *
	 * @param modifiedDate the modified date of this batch planner log
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this batch planner log.
	 *
	 * @param mvccVersion the mvcc version of this batch planner log
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this batch planner log.
	 *
	 * @param primaryKey the primary key of this batch planner log
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the size of this batch planner log.
	 *
	 * @param size the size of this batch planner log
	 */
	@Override
	public void setSize(Integer size) {
		model.setSize(size);
	}

	/**
	 * Sets the status of this batch planner log.
	 *
	 * @param status the status of this batch planner log
	 */
	@Override
	public void setStatus(String status) {
		model.setStatus(status);
	}

	/**
	 * Sets the total of this batch planner log.
	 *
	 * @param total the total of this batch planner log
	 */
	@Override
	public void setTotal(Integer total) {
		model.setTotal(total);
	}

	/**
	 * Sets the user ID of this batch planner log.
	 *
	 * @param userId the user ID of this batch planner log
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this batch planner log.
	 *
	 * @param userName the user name of this batch planner log
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this batch planner log.
	 *
	 * @param userUuid the user uuid of this batch planner log
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected BatchPlannerLogWrapper wrap(BatchPlannerLog batchPlannerLog) {
		return new BatchPlannerLogWrapper(batchPlannerLog);
	}

}