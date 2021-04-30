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
 * This class is a wrapper for {@link BatchPlannerMapping}.
 * </p>
 *
 * @author Igor Beslic
 * @see BatchPlannerMapping
 * @generated
 */
public class BatchPlannerMappingWrapper
	extends BaseModelWrapper<BatchPlannerMapping>
	implements BatchPlannerMapping, ModelWrapper<BatchPlannerMapping> {

	public BatchPlannerMappingWrapper(BatchPlannerMapping batchPlannerMapping) {
		super(batchPlannerMapping);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("batchPlannerMappingId", getBatchPlannerMappingId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("batchPlannerPlanId", getBatchPlannerPlanId());
		attributes.put("externalName", getExternalName());
		attributes.put("externalType", getExternalType());
		attributes.put("internalName", getInternalName());
		attributes.put("internalType", getInternalType());
		attributes.put(
			"transformationExpression", getTransformationExpression());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long batchPlannerMappingId = (Long)attributes.get(
			"batchPlannerMappingId");

		if (batchPlannerMappingId != null) {
			setBatchPlannerMappingId(batchPlannerMappingId);
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

		String externalName = (String)attributes.get("externalName");

		if (externalName != null) {
			setExternalName(externalName);
		}

		String externalType = (String)attributes.get("externalType");

		if (externalType != null) {
			setExternalType(externalType);
		}

		String internalName = (String)attributes.get("internalName");

		if (internalName != null) {
			setInternalName(internalName);
		}

		String internalType = (String)attributes.get("internalType");

		if (internalType != null) {
			setInternalType(internalType);
		}

		String transformationExpression = (String)attributes.get(
			"transformationExpression");

		if (transformationExpression != null) {
			setTransformationExpression(transformationExpression);
		}
	}

	/**
	 * Returns the batch planner mapping ID of this batch planner mapping.
	 *
	 * @return the batch planner mapping ID of this batch planner mapping
	 */
	@Override
	public long getBatchPlannerMappingId() {
		return model.getBatchPlannerMappingId();
	}

	/**
	 * Returns the batch planner plan ID of this batch planner mapping.
	 *
	 * @return the batch planner plan ID of this batch planner mapping
	 */
	@Override
	public long getBatchPlannerPlanId() {
		return model.getBatchPlannerPlanId();
	}

	/**
	 * Returns the company ID of this batch planner mapping.
	 *
	 * @return the company ID of this batch planner mapping
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this batch planner mapping.
	 *
	 * @return the create date of this batch planner mapping
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the external name of this batch planner mapping.
	 *
	 * @return the external name of this batch planner mapping
	 */
	@Override
	public String getExternalName() {
		return model.getExternalName();
	}

	/**
	 * Returns the external type of this batch planner mapping.
	 *
	 * @return the external type of this batch planner mapping
	 */
	@Override
	public String getExternalType() {
		return model.getExternalType();
	}

	/**
	 * Returns the internal name of this batch planner mapping.
	 *
	 * @return the internal name of this batch planner mapping
	 */
	@Override
	public String getInternalName() {
		return model.getInternalName();
	}

	/**
	 * Returns the internal type of this batch planner mapping.
	 *
	 * @return the internal type of this batch planner mapping
	 */
	@Override
	public String getInternalType() {
		return model.getInternalType();
	}

	/**
	 * Returns the modified date of this batch planner mapping.
	 *
	 * @return the modified date of this batch planner mapping
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this batch planner mapping.
	 *
	 * @return the mvcc version of this batch planner mapping
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this batch planner mapping.
	 *
	 * @return the primary key of this batch planner mapping
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the transformation expression of this batch planner mapping.
	 *
	 * @return the transformation expression of this batch planner mapping
	 */
	@Override
	public String getTransformationExpression() {
		return model.getTransformationExpression();
	}

	/**
	 * Returns the user ID of this batch planner mapping.
	 *
	 * @return the user ID of this batch planner mapping
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this batch planner mapping.
	 *
	 * @return the user name of this batch planner mapping
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this batch planner mapping.
	 *
	 * @return the user uuid of this batch planner mapping
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
	 * Sets the batch planner mapping ID of this batch planner mapping.
	 *
	 * @param batchPlannerMappingId the batch planner mapping ID of this batch planner mapping
	 */
	@Override
	public void setBatchPlannerMappingId(long batchPlannerMappingId) {
		model.setBatchPlannerMappingId(batchPlannerMappingId);
	}

	/**
	 * Sets the batch planner plan ID of this batch planner mapping.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID of this batch planner mapping
	 */
	@Override
	public void setBatchPlannerPlanId(long batchPlannerPlanId) {
		model.setBatchPlannerPlanId(batchPlannerPlanId);
	}

	/**
	 * Sets the company ID of this batch planner mapping.
	 *
	 * @param companyId the company ID of this batch planner mapping
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this batch planner mapping.
	 *
	 * @param createDate the create date of this batch planner mapping
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the external name of this batch planner mapping.
	 *
	 * @param externalName the external name of this batch planner mapping
	 */
	@Override
	public void setExternalName(String externalName) {
		model.setExternalName(externalName);
	}

	/**
	 * Sets the external type of this batch planner mapping.
	 *
	 * @param externalType the external type of this batch planner mapping
	 */
	@Override
	public void setExternalType(String externalType) {
		model.setExternalType(externalType);
	}

	/**
	 * Sets the internal name of this batch planner mapping.
	 *
	 * @param internalName the internal name of this batch planner mapping
	 */
	@Override
	public void setInternalName(String internalName) {
		model.setInternalName(internalName);
	}

	/**
	 * Sets the internal type of this batch planner mapping.
	 *
	 * @param internalType the internal type of this batch planner mapping
	 */
	@Override
	public void setInternalType(String internalType) {
		model.setInternalType(internalType);
	}

	/**
	 * Sets the modified date of this batch planner mapping.
	 *
	 * @param modifiedDate the modified date of this batch planner mapping
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this batch planner mapping.
	 *
	 * @param mvccVersion the mvcc version of this batch planner mapping
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this batch planner mapping.
	 *
	 * @param primaryKey the primary key of this batch planner mapping
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the transformation expression of this batch planner mapping.
	 *
	 * @param transformationExpression the transformation expression of this batch planner mapping
	 */
	@Override
	public void setTransformationExpression(String transformationExpression) {
		model.setTransformationExpression(transformationExpression);
	}

	/**
	 * Sets the user ID of this batch planner mapping.
	 *
	 * @param userId the user ID of this batch planner mapping
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this batch planner mapping.
	 *
	 * @param userName the user name of this batch planner mapping
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this batch planner mapping.
	 *
	 * @param userUuid the user uuid of this batch planner mapping
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected BatchPlannerMappingWrapper wrap(
		BatchPlannerMapping batchPlannerMapping) {

		return new BatchPlannerMappingWrapper(batchPlannerMapping);
	}

}