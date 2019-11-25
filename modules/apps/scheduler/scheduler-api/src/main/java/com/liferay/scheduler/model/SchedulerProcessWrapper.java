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

package com.liferay.scheduler.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link SchedulerProcess}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see SchedulerProcess
 * @generated
 */
public class SchedulerProcessWrapper
	extends BaseModelWrapper<SchedulerProcess>
	implements ModelWrapper<SchedulerProcess>, SchedulerProcess {

	public SchedulerProcessWrapper(SchedulerProcess schedulerProcess) {
		super(schedulerProcess);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("schedulerProcessId", getSchedulerProcessId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());
		attributes.put("type", getType());
		attributes.put("typeSettings", getTypeSettings());
		attributes.put("system", isSystem());
		attributes.put("active", isActive());
		attributes.put("cronExpression", getCronExpression());
		attributes.put("startDate", getStartDate());
		attributes.put("endDate", getEndDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long schedulerProcessId = (Long)attributes.get("schedulerProcessId");

		if (schedulerProcessId != null) {
			setSchedulerProcessId(schedulerProcessId);
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

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String type = (String)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		String typeSettings = (String)attributes.get("typeSettings");

		if (typeSettings != null) {
			setTypeSettings(typeSettings);
		}

		Boolean system = (Boolean)attributes.get("system");

		if (system != null) {
			setSystem(system);
		}

		Boolean active = (Boolean)attributes.get("active");

		if (active != null) {
			setActive(active);
		}

		String cronExpression = (String)attributes.get("cronExpression");

		if (cronExpression != null) {
			setCronExpression(cronExpression);
		}

		Date startDate = (Date)attributes.get("startDate");

		if (startDate != null) {
			setStartDate(startDate);
		}

		Date endDate = (Date)attributes.get("endDate");

		if (endDate != null) {
			setEndDate(endDate);
		}
	}

	/**
	 * Returns the active of this scheduler process.
	 *
	 * @return the active of this scheduler process
	 */
	@Override
	public boolean getActive() {
		return model.getActive();
	}

	/**
	 * Returns the company ID of this scheduler process.
	 *
	 * @return the company ID of this scheduler process
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this scheduler process.
	 *
	 * @return the create date of this scheduler process
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the cron expression of this scheduler process.
	 *
	 * @return the cron expression of this scheduler process
	 */
	@Override
	public String getCronExpression() {
		return model.getCronExpression();
	}

	/**
	 * Returns the end date of this scheduler process.
	 *
	 * @return the end date of this scheduler process
	 */
	@Override
	public Date getEndDate() {
		return model.getEndDate();
	}

	/**
	 * Returns the modified date of this scheduler process.
	 *
	 * @return the modified date of this scheduler process
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this scheduler process.
	 *
	 * @return the mvcc version of this scheduler process
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this scheduler process.
	 *
	 * @return the name of this scheduler process
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this scheduler process.
	 *
	 * @return the primary key of this scheduler process
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the scheduler process ID of this scheduler process.
	 *
	 * @return the scheduler process ID of this scheduler process
	 */
	@Override
	public long getSchedulerProcessId() {
		return model.getSchedulerProcessId();
	}

	/**
	 * Returns the start date of this scheduler process.
	 *
	 * @return the start date of this scheduler process
	 */
	@Override
	public Date getStartDate() {
		return model.getStartDate();
	}

	/**
	 * Returns the system of this scheduler process.
	 *
	 * @return the system of this scheduler process
	 */
	@Override
	public boolean getSystem() {
		return model.getSystem();
	}

	/**
	 * Returns the type of this scheduler process.
	 *
	 * @return the type of this scheduler process
	 */
	@Override
	public String getType() {
		return model.getType();
	}

	/**
	 * Returns the type settings of this scheduler process.
	 *
	 * @return the type settings of this scheduler process
	 */
	@Override
	public String getTypeSettings() {
		return model.getTypeSettings();
	}

	/**
	 * Returns the user ID of this scheduler process.
	 *
	 * @return the user ID of this scheduler process
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this scheduler process.
	 *
	 * @return the user name of this scheduler process
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this scheduler process.
	 *
	 * @return the user uuid of this scheduler process
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns <code>true</code> if this scheduler process is active.
	 *
	 * @return <code>true</code> if this scheduler process is active; <code>false</code> otherwise
	 */
	@Override
	public boolean isActive() {
		return model.isActive();
	}

	/**
	 * Returns <code>true</code> if this scheduler process is system.
	 *
	 * @return <code>true</code> if this scheduler process is system; <code>false</code> otherwise
	 */
	@Override
	public boolean isSystem() {
		return model.isSystem();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a scheduler process model instance should use the <code>SchedulerProcess</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets whether this scheduler process is active.
	 *
	 * @param active the active of this scheduler process
	 */
	@Override
	public void setActive(boolean active) {
		model.setActive(active);
	}

	/**
	 * Sets the company ID of this scheduler process.
	 *
	 * @param companyId the company ID of this scheduler process
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this scheduler process.
	 *
	 * @param createDate the create date of this scheduler process
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the cron expression of this scheduler process.
	 *
	 * @param cronExpression the cron expression of this scheduler process
	 */
	@Override
	public void setCronExpression(String cronExpression) {
		model.setCronExpression(cronExpression);
	}

	/**
	 * Sets the end date of this scheduler process.
	 *
	 * @param endDate the end date of this scheduler process
	 */
	@Override
	public void setEndDate(Date endDate) {
		model.setEndDate(endDate);
	}

	/**
	 * Sets the modified date of this scheduler process.
	 *
	 * @param modifiedDate the modified date of this scheduler process
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this scheduler process.
	 *
	 * @param mvccVersion the mvcc version of this scheduler process
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this scheduler process.
	 *
	 * @param name the name of this scheduler process
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this scheduler process.
	 *
	 * @param primaryKey the primary key of this scheduler process
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the scheduler process ID of this scheduler process.
	 *
	 * @param schedulerProcessId the scheduler process ID of this scheduler process
	 */
	@Override
	public void setSchedulerProcessId(long schedulerProcessId) {
		model.setSchedulerProcessId(schedulerProcessId);
	}

	/**
	 * Sets the start date of this scheduler process.
	 *
	 * @param startDate the start date of this scheduler process
	 */
	@Override
	public void setStartDate(Date startDate) {
		model.setStartDate(startDate);
	}

	/**
	 * Sets whether this scheduler process is system.
	 *
	 * @param system the system of this scheduler process
	 */
	@Override
	public void setSystem(boolean system) {
		model.setSystem(system);
	}

	/**
	 * Sets the type of this scheduler process.
	 *
	 * @param type the type of this scheduler process
	 */
	@Override
	public void setType(String type) {
		model.setType(type);
	}

	/**
	 * Sets the type settings of this scheduler process.
	 *
	 * @param typeSettings the type settings of this scheduler process
	 */
	@Override
	public void setTypeSettings(String typeSettings) {
		model.setTypeSettings(typeSettings);
	}

	/**
	 * Sets the user ID of this scheduler process.
	 *
	 * @param userId the user ID of this scheduler process
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this scheduler process.
	 *
	 * @param userName the user name of this scheduler process
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this scheduler process.
	 *
	 * @param userUuid the user uuid of this scheduler process
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected SchedulerProcessWrapper wrap(SchedulerProcess schedulerProcess) {
		return new SchedulerProcessWrapper(schedulerProcess);
	}

}