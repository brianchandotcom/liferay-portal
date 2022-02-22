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
 * This class is a wrapper for {@link BatchPlannerPlan}.
 * </p>
 *
 * @author Igor Beslic
 * @see BatchPlannerPlan
 * @generated
 */
public class BatchPlannerPlanWrapper
	extends BaseModelWrapper<BatchPlannerPlan>
	implements BatchPlannerPlan, ModelWrapper<BatchPlannerPlan> {

	public BatchPlannerPlanWrapper(BatchPlannerPlan batchPlannerPlan) {
		super(batchPlannerPlan);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("batchPlannerPlanId", getBatchPlannerPlanId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("active", isActive());
		attributes.put("export", isExport());
		attributes.put("externalType", getExternalType());
		attributes.put("externalURL", getExternalURL());
		attributes.put("internalClassName", getInternalClassName());
		attributes.put("name", getName());
		attributes.put("taskItemDelegateName", getTaskItemDelegateName());
		attributes.put("template", isTemplate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long batchPlannerPlanId = (Long)attributes.get("batchPlannerPlanId");

		if (batchPlannerPlanId != null) {
			setBatchPlannerPlanId(batchPlannerPlanId);
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

		Boolean active = (Boolean)attributes.get("active");

		if (active != null) {
			setActive(active);
		}

		Boolean export = (Boolean)attributes.get("export");

		if (export != null) {
			setExport(export);
		}

		String externalType = (String)attributes.get("externalType");

		if (externalType != null) {
			setExternalType(externalType);
		}

		String externalURL = (String)attributes.get("externalURL");

		if (externalURL != null) {
			setExternalURL(externalURL);
		}

		String internalClassName = (String)attributes.get("internalClassName");

		if (internalClassName != null) {
			setInternalClassName(internalClassName);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String taskItemDelegateName = (String)attributes.get(
			"taskItemDelegateName");

		if (taskItemDelegateName != null) {
			setTaskItemDelegateName(taskItemDelegateName);
		}

		Boolean template = (Boolean)attributes.get("template");

		if (template != null) {
			setTemplate(template);
		}
	}

	@Override
	public BatchPlannerPlan cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	@Override
	public BatchPlannerLog fetchBatchPlannerLog() {
		return model.fetchBatchPlannerLog();
	}

	@Override
	public BatchPlannerPolicy fetchBatchPlannerPolicy(String name) {
		return model.fetchBatchPlannerPolicy(name);
	}

	/**
	 * Returns the active of this batch planner plan.
	 *
	 * @return the active of this batch planner plan
	 */
	@Override
	public boolean getActive() {
		return model.getActive();
	}

	@Override
	public BatchPlannerLog getBatchPlannerLog()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getBatchPlannerLog();
	}

	@Override
	public java.util.List<BatchPlannerMapping> getBatchPlannerMappings() {
		return model.getBatchPlannerMappings();
	}

	/**
	 * Returns the batch planner plan ID of this batch planner plan.
	 *
	 * @return the batch planner plan ID of this batch planner plan
	 */
	@Override
	public long getBatchPlannerPlanId() {
		return model.getBatchPlannerPlanId();
	}

	@Override
	public java.util.List<BatchPlannerPolicy> getBatchPlannerPolicies() {
		return model.getBatchPlannerPolicies();
	}

	@Override
	public BatchPlannerPolicy getBatchPlannerPolicy(String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getBatchPlannerPolicy(name);
	}

	/**
	 * Returns the company ID of this batch planner plan.
	 *
	 * @return the company ID of this batch planner plan
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this batch planner plan.
	 *
	 * @return the create date of this batch planner plan
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the export of this batch planner plan.
	 *
	 * @return the export of this batch planner plan
	 */
	@Override
	public boolean getExport() {
		return model.getExport();
	}

	/**
	 * Returns the external type of this batch planner plan.
	 *
	 * @return the external type of this batch planner plan
	 */
	@Override
	public String getExternalType() {
		return model.getExternalType();
	}

	/**
	 * Returns the external url of this batch planner plan.
	 *
	 * @return the external url of this batch planner plan
	 */
	@Override
	public String getExternalURL() {
		return model.getExternalURL();
	}

	/**
	 * Returns the internal class name of this batch planner plan.
	 *
	 * @return the internal class name of this batch planner plan
	 */
	@Override
	public String getInternalClassName() {
		return model.getInternalClassName();
	}

	/**
	 * Returns the modified date of this batch planner plan.
	 *
	 * @return the modified date of this batch planner plan
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this batch planner plan.
	 *
	 * @return the mvcc version of this batch planner plan
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this batch planner plan.
	 *
	 * @return the name of this batch planner plan
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this batch planner plan.
	 *
	 * @return the primary key of this batch planner plan
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the task item delegate name of this batch planner plan.
	 *
	 * @return the task item delegate name of this batch planner plan
	 */
	@Override
	public String getTaskItemDelegateName() {
		return model.getTaskItemDelegateName();
	}

	/**
	 * Returns the template of this batch planner plan.
	 *
	 * @return the template of this batch planner plan
	 */
	@Override
	public boolean getTemplate() {
		return model.getTemplate();
	}

	/**
	 * Returns the user ID of this batch planner plan.
	 *
	 * @return the user ID of this batch planner plan
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this batch planner plan.
	 *
	 * @return the user name of this batch planner plan
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this batch planner plan.
	 *
	 * @return the user uuid of this batch planner plan
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns <code>true</code> if this batch planner plan is active.
	 *
	 * @return <code>true</code> if this batch planner plan is active; <code>false</code> otherwise
	 */
	@Override
	public boolean isActive() {
		return model.isActive();
	}

	/**
	 * Returns <code>true</code> if this batch planner plan is export.
	 *
	 * @return <code>true</code> if this batch planner plan is export; <code>false</code> otherwise
	 */
	@Override
	public boolean isExport() {
		return model.isExport();
	}

	/**
	 * Returns <code>true</code> if this batch planner plan is template.
	 *
	 * @return <code>true</code> if this batch planner plan is template; <code>false</code> otherwise
	 */
	@Override
	public boolean isTemplate() {
		return model.isTemplate();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets whether this batch planner plan is active.
	 *
	 * @param active the active of this batch planner plan
	 */
	@Override
	public void setActive(boolean active) {
		model.setActive(active);
	}

	/**
	 * Sets the batch planner plan ID of this batch planner plan.
	 *
	 * @param batchPlannerPlanId the batch planner plan ID of this batch planner plan
	 */
	@Override
	public void setBatchPlannerPlanId(long batchPlannerPlanId) {
		model.setBatchPlannerPlanId(batchPlannerPlanId);
	}

	/**
	 * Sets the company ID of this batch planner plan.
	 *
	 * @param companyId the company ID of this batch planner plan
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this batch planner plan.
	 *
	 * @param createDate the create date of this batch planner plan
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets whether this batch planner plan is export.
	 *
	 * @param export the export of this batch planner plan
	 */
	@Override
	public void setExport(boolean export) {
		model.setExport(export);
	}

	/**
	 * Sets the external type of this batch planner plan.
	 *
	 * @param externalType the external type of this batch planner plan
	 */
	@Override
	public void setExternalType(String externalType) {
		model.setExternalType(externalType);
	}

	/**
	 * Sets the external url of this batch planner plan.
	 *
	 * @param externalURL the external url of this batch planner plan
	 */
	@Override
	public void setExternalURL(String externalURL) {
		model.setExternalURL(externalURL);
	}

	/**
	 * Sets the internal class name of this batch planner plan.
	 *
	 * @param internalClassName the internal class name of this batch planner plan
	 */
	@Override
	public void setInternalClassName(String internalClassName) {
		model.setInternalClassName(internalClassName);
	}

	/**
	 * Sets the modified date of this batch planner plan.
	 *
	 * @param modifiedDate the modified date of this batch planner plan
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this batch planner plan.
	 *
	 * @param mvccVersion the mvcc version of this batch planner plan
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this batch planner plan.
	 *
	 * @param name the name of this batch planner plan
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this batch planner plan.
	 *
	 * @param primaryKey the primary key of this batch planner plan
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the task item delegate name of this batch planner plan.
	 *
	 * @param taskItemDelegateName the task item delegate name of this batch planner plan
	 */
	@Override
	public void setTaskItemDelegateName(String taskItemDelegateName) {
		model.setTaskItemDelegateName(taskItemDelegateName);
	}

	/**
	 * Sets whether this batch planner plan is template.
	 *
	 * @param template the template of this batch planner plan
	 */
	@Override
	public void setTemplate(boolean template) {
		model.setTemplate(template);
	}

	/**
	 * Sets the user ID of this batch planner plan.
	 *
	 * @param userId the user ID of this batch planner plan
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this batch planner plan.
	 *
	 * @param userName the user name of this batch planner plan
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this batch planner plan.
	 *
	 * @param userUuid the user uuid of this batch planner plan
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected BatchPlannerPlanWrapper wrap(BatchPlannerPlan batchPlannerPlan) {
		return new BatchPlannerPlanWrapper(batchPlannerPlan);
	}

}