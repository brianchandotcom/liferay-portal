/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		attributes.put("externalFieldName", getExternalFieldName());
		attributes.put("externalFieldType", getExternalFieldType());
		attributes.put("internalFieldName", getInternalFieldName());
		attributes.put("internalFieldType", getInternalFieldType());
		attributes.put("script", getScript());

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

		String externalFieldName = (String)attributes.get("externalFieldName");

		if (externalFieldName != null) {
			setExternalFieldName(externalFieldName);
		}

		String externalFieldType = (String)attributes.get("externalFieldType");

		if (externalFieldType != null) {
			setExternalFieldType(externalFieldType);
		}

		String internalFieldName = (String)attributes.get("internalFieldName");

		if (internalFieldName != null) {
			setInternalFieldName(internalFieldName);
		}

		String internalFieldType = (String)attributes.get("internalFieldType");

		if (internalFieldType != null) {
			setInternalFieldType(internalFieldType);
		}

		String script = (String)attributes.get("script");

		if (script != null) {
			setScript(script);
		}
	}

	@Override
	public BatchPlannerMapping cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
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
	 * Returns the external field name of this batch planner mapping.
	 *
	 * @return the external field name of this batch planner mapping
	 */
	@Override
	public String getExternalFieldName() {
		return model.getExternalFieldName();
	}

	/**
	 * Returns the external field type of this batch planner mapping.
	 *
	 * @return the external field type of this batch planner mapping
	 */
	@Override
	public String getExternalFieldType() {
		return model.getExternalFieldType();
	}

	/**
	 * Returns the internal field name of this batch planner mapping.
	 *
	 * @return the internal field name of this batch planner mapping
	 */
	@Override
	public String getInternalFieldName() {
		return model.getInternalFieldName();
	}

	/**
	 * Returns the internal field type of this batch planner mapping.
	 *
	 * @return the internal field type of this batch planner mapping
	 */
	@Override
	public String getInternalFieldType() {
		return model.getInternalFieldType();
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
	 * Returns the script of this batch planner mapping.
	 *
	 * @return the script of this batch planner mapping
	 */
	@Override
	public String getScript() {
		return model.getScript();
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
	 * Sets the external field name of this batch planner mapping.
	 *
	 * @param externalFieldName the external field name of this batch planner mapping
	 */
	@Override
	public void setExternalFieldName(String externalFieldName) {
		model.setExternalFieldName(externalFieldName);
	}

	/**
	 * Sets the external field type of this batch planner mapping.
	 *
	 * @param externalFieldType the external field type of this batch planner mapping
	 */
	@Override
	public void setExternalFieldType(String externalFieldType) {
		model.setExternalFieldType(externalFieldType);
	}

	/**
	 * Sets the internal field name of this batch planner mapping.
	 *
	 * @param internalFieldName the internal field name of this batch planner mapping
	 */
	@Override
	public void setInternalFieldName(String internalFieldName) {
		model.setInternalFieldName(internalFieldName);
	}

	/**
	 * Sets the internal field type of this batch planner mapping.
	 *
	 * @param internalFieldType the internal field type of this batch planner mapping
	 */
	@Override
	public void setInternalFieldType(String internalFieldType) {
		model.setInternalFieldType(internalFieldType);
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
	 * Sets the script of this batch planner mapping.
	 *
	 * @param script the script of this batch planner mapping
	 */
	@Override
	public void setScript(String script) {
		model.setScript(script);
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
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	protected BatchPlannerMappingWrapper wrap(
		BatchPlannerMapping batchPlannerMapping) {

		return new BatchPlannerMappingWrapper(batchPlannerMapping);
	}

}