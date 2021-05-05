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
		attributes.put("contentFieldName", getContentFieldName());
		attributes.put("contentFieldType", getContentFieldType());
		attributes.put("openAPIFieldName", getOpenAPIFieldName());
		attributes.put("openAPIFieldType", getOpenAPIFieldType());
		attributes.put("transformationJavaCode", getTransformationJavaCode());

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

		String contentFieldName = (String)attributes.get("contentFieldName");

		if (contentFieldName != null) {
			setContentFieldName(contentFieldName);
		}

		String contentFieldType = (String)attributes.get("contentFieldType");

		if (contentFieldType != null) {
			setContentFieldType(contentFieldType);
		}

		String openAPIFieldName = (String)attributes.get("openAPIFieldName");

		if (openAPIFieldName != null) {
			setOpenAPIFieldName(openAPIFieldName);
		}

		String openAPIFieldType = (String)attributes.get("openAPIFieldType");

		if (openAPIFieldType != null) {
			setOpenAPIFieldType(openAPIFieldType);
		}

		String transformationJavaCode = (String)attributes.get(
			"transformationJavaCode");

		if (transformationJavaCode != null) {
			setTransformationJavaCode(transformationJavaCode);
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
	 * Returns the content field name of this batch planner mapping.
	 *
	 * @return the content field name of this batch planner mapping
	 */
	@Override
	public String getContentFieldName() {
		return model.getContentFieldName();
	}

	/**
	 * Returns the content field type of this batch planner mapping.
	 *
	 * @return the content field type of this batch planner mapping
	 */
	@Override
	public String getContentFieldType() {
		return model.getContentFieldType();
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
	 * Returns the open api field name of this batch planner mapping.
	 *
	 * @return the open api field name of this batch planner mapping
	 */
	@Override
	public String getOpenAPIFieldName() {
		return model.getOpenAPIFieldName();
	}

	/**
	 * Returns the open api field type of this batch planner mapping.
	 *
	 * @return the open api field type of this batch planner mapping
	 */
	@Override
	public String getOpenAPIFieldType() {
		return model.getOpenAPIFieldType();
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
	 * Returns the transformation java code of this batch planner mapping.
	 *
	 * @return the transformation java code of this batch planner mapping
	 */
	@Override
	public String getTransformationJavaCode() {
		return model.getTransformationJavaCode();
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
	 * Sets the content field name of this batch planner mapping.
	 *
	 * @param contentFieldName the content field name of this batch planner mapping
	 */
	@Override
	public void setContentFieldName(String contentFieldName) {
		model.setContentFieldName(contentFieldName);
	}

	/**
	 * Sets the content field type of this batch planner mapping.
	 *
	 * @param contentFieldType the content field type of this batch planner mapping
	 */
	@Override
	public void setContentFieldType(String contentFieldType) {
		model.setContentFieldType(contentFieldType);
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
	 * Sets the open api field name of this batch planner mapping.
	 *
	 * @param openAPIFieldName the open api field name of this batch planner mapping
	 */
	@Override
	public void setOpenAPIFieldName(String openAPIFieldName) {
		model.setOpenAPIFieldName(openAPIFieldName);
	}

	/**
	 * Sets the open api field type of this batch planner mapping.
	 *
	 * @param openAPIFieldType the open api field type of this batch planner mapping
	 */
	@Override
	public void setOpenAPIFieldType(String openAPIFieldType) {
		model.setOpenAPIFieldType(openAPIFieldType);
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
	 * Sets the transformation java code of this batch planner mapping.
	 *
	 * @param transformationJavaCode the transformation java code of this batch planner mapping
	 */
	@Override
	public void setTransformationJavaCode(String transformationJavaCode) {
		model.setTransformationJavaCode(transformationJavaCode);
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