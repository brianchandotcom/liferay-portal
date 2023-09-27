/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dispatch.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link DispatchTrigger}.
 * </p>
 *
 * @author Matija Petanjek
 * @see DispatchTrigger
 * @generated
 */
public class DispatchTriggerWrapper
	extends BaseModelWrapper<DispatchTrigger>
	implements DispatchTrigger, ModelWrapper<DispatchTrigger> {

	public DispatchTriggerWrapper(DispatchTrigger dispatchTrigger) {
		super(dispatchTrigger);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("externalReferenceCode", getExternalReferenceCode());
		attributes.put("dispatchTriggerId", getDispatchTriggerId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("active", isActive());
		attributes.put("cronExpression", getCronExpression());
		attributes.put("dispatchTaskClusterMode", getDispatchTaskClusterMode());
		attributes.put(
			"dispatchTaskExecutorType", getDispatchTaskExecutorType());
		attributes.put("dispatchTaskSettings", getDispatchTaskSettings());
		attributes.put("endDate", getEndDate());
		attributes.put("name", getName());
		attributes.put("overlapAllowed", isOverlapAllowed());
		attributes.put("startDate", getStartDate());
		attributes.put("system", isSystem());
		attributes.put("timeZoneId", getTimeZoneId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		String externalReferenceCode = (String)attributes.get(
			"externalReferenceCode");

		if (externalReferenceCode != null) {
			setExternalReferenceCode(externalReferenceCode);
		}

		Long dispatchTriggerId = (Long)attributes.get("dispatchTriggerId");

		if (dispatchTriggerId != null) {
			setDispatchTriggerId(dispatchTriggerId);
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

		String cronExpression = (String)attributes.get("cronExpression");

		if (cronExpression != null) {
			setCronExpression(cronExpression);
		}

		Integer dispatchTaskClusterMode = (Integer)attributes.get(
			"dispatchTaskClusterMode");

		if (dispatchTaskClusterMode != null) {
			setDispatchTaskClusterMode(dispatchTaskClusterMode);
		}

		String dispatchTaskExecutorType = (String)attributes.get(
			"dispatchTaskExecutorType");

		if (dispatchTaskExecutorType != null) {
			setDispatchTaskExecutorType(dispatchTaskExecutorType);
		}

		String dispatchTaskSettings = (String)attributes.get(
			"dispatchTaskSettings");

		if (dispatchTaskSettings != null) {
			setDispatchTaskSettings(dispatchTaskSettings);
		}

		Date endDate = (Date)attributes.get("endDate");

		if (endDate != null) {
			setEndDate(endDate);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		Boolean overlapAllowed = (Boolean)attributes.get("overlapAllowed");

		if (overlapAllowed != null) {
			setOverlapAllowed(overlapAllowed);
		}

		Date startDate = (Date)attributes.get("startDate");

		if (startDate != null) {
			setStartDate(startDate);
		}

		Boolean system = (Boolean)attributes.get("system");

		if (system != null) {
			setSystem(system);
		}

		String timeZoneId = (String)attributes.get("timeZoneId");

		if (timeZoneId != null) {
			setTimeZoneId(timeZoneId);
		}
	}

	@Override
	public DispatchTrigger cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the active of this dispatch trigger.
	 *
	 * @return the active of this dispatch trigger
	 */
	@Override
	public boolean getActive() {
		return model.getActive();
	}

	/**
	 * Returns the company ID of this dispatch trigger.
	 *
	 * @return the company ID of this dispatch trigger
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this dispatch trigger.
	 *
	 * @return the create date of this dispatch trigger
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the cron expression of this dispatch trigger.
	 *
	 * @return the cron expression of this dispatch trigger
	 */
	@Override
	public String getCronExpression() {
		return model.getCronExpression();
	}

	/**
	 * Returns the dispatch task cluster mode of this dispatch trigger.
	 *
	 * @return the dispatch task cluster mode of this dispatch trigger
	 */
	@Override
	public int getDispatchTaskClusterMode() {
		return model.getDispatchTaskClusterMode();
	}

	/**
	 * Returns the dispatch task executor type of this dispatch trigger.
	 *
	 * @return the dispatch task executor type of this dispatch trigger
	 */
	@Override
	public String getDispatchTaskExecutorType() {
		return model.getDispatchTaskExecutorType();
	}

	/**
	 * Returns the dispatch task settings of this dispatch trigger.
	 *
	 * @return the dispatch task settings of this dispatch trigger
	 */
	@Override
	public String getDispatchTaskSettings() {
		return model.getDispatchTaskSettings();
	}

	@Override
	public com.liferay.portal.kernel.util.UnicodeProperties
		getDispatchTaskSettingsUnicodeProperties() {

		return model.getDispatchTaskSettingsUnicodeProperties();
	}

	@Override
	public com.liferay.dispatch.executor.DispatchTaskStatus
		getDispatchTaskStatus() {

		return model.getDispatchTaskStatus();
	}

	/**
	 * Returns the dispatch trigger ID of this dispatch trigger.
	 *
	 * @return the dispatch trigger ID of this dispatch trigger
	 */
	@Override
	public long getDispatchTriggerId() {
		return model.getDispatchTriggerId();
	}

	/**
	 * Returns the end date of this dispatch trigger.
	 *
	 * @return the end date of this dispatch trigger
	 */
	@Override
	public Date getEndDate() {
		return model.getEndDate();
	}

	/**
	 * Returns the external reference code of this dispatch trigger.
	 *
	 * @return the external reference code of this dispatch trigger
	 */
	@Override
	public String getExternalReferenceCode() {
		return model.getExternalReferenceCode();
	}

	/**
	 * Returns the modified date of this dispatch trigger.
	 *
	 * @return the modified date of this dispatch trigger
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this dispatch trigger.
	 *
	 * @return the mvcc version of this dispatch trigger
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this dispatch trigger.
	 *
	 * @return the name of this dispatch trigger
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	@Override
	public Date getNextFireDate() {
		return model.getNextFireDate();
	}

	/**
	 * Returns the overlap allowed of this dispatch trigger.
	 *
	 * @return the overlap allowed of this dispatch trigger
	 */
	@Override
	public boolean getOverlapAllowed() {
		return model.getOverlapAllowed();
	}

	/**
	 * Returns the primary key of this dispatch trigger.
	 *
	 * @return the primary key of this dispatch trigger
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the start date of this dispatch trigger.
	 *
	 * @return the start date of this dispatch trigger
	 */
	@Override
	public Date getStartDate() {
		return model.getStartDate();
	}

	/**
	 * Returns the system of this dispatch trigger.
	 *
	 * @return the system of this dispatch trigger
	 */
	@Override
	public boolean getSystem() {
		return model.getSystem();
	}

	@Override
	public Date getTimeZoneEndDate() {
		return model.getTimeZoneEndDate();
	}

	/**
	 * Returns the time zone ID of this dispatch trigger.
	 *
	 * @return the time zone ID of this dispatch trigger
	 */
	@Override
	public String getTimeZoneId() {
		return model.getTimeZoneId();
	}

	@Override
	public Date getTimeZoneStartDate() {
		return model.getTimeZoneStartDate();
	}

	/**
	 * Returns the user ID of this dispatch trigger.
	 *
	 * @return the user ID of this dispatch trigger
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this dispatch trigger.
	 *
	 * @return the user name of this dispatch trigger
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this dispatch trigger.
	 *
	 * @return the user uuid of this dispatch trigger
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this dispatch trigger.
	 *
	 * @return the uuid of this dispatch trigger
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns <code>true</code> if this dispatch trigger is active.
	 *
	 * @return <code>true</code> if this dispatch trigger is active; <code>false</code> otherwise
	 */
	@Override
	public boolean isActive() {
		return model.isActive();
	}

	/**
	 * Returns <code>true</code> if this dispatch trigger is overlap allowed.
	 *
	 * @return <code>true</code> if this dispatch trigger is overlap allowed; <code>false</code> otherwise
	 */
	@Override
	public boolean isOverlapAllowed() {
		return model.isOverlapAllowed();
	}

	/**
	 * Returns <code>true</code> if this dispatch trigger is system.
	 *
	 * @return <code>true</code> if this dispatch trigger is system; <code>false</code> otherwise
	 */
	@Override
	public boolean isSystem() {
		return model.isSystem();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets whether this dispatch trigger is active.
	 *
	 * @param active the active of this dispatch trigger
	 */
	@Override
	public void setActive(boolean active) {
		model.setActive(active);
	}

	/**
	 * Sets the company ID of this dispatch trigger.
	 *
	 * @param companyId the company ID of this dispatch trigger
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this dispatch trigger.
	 *
	 * @param createDate the create date of this dispatch trigger
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the cron expression of this dispatch trigger.
	 *
	 * @param cronExpression the cron expression of this dispatch trigger
	 */
	@Override
	public void setCronExpression(String cronExpression) {
		model.setCronExpression(cronExpression);
	}

	/**
	 * Sets the dispatch task cluster mode of this dispatch trigger.
	 *
	 * @param dispatchTaskClusterMode the dispatch task cluster mode of this dispatch trigger
	 */
	@Override
	public void setDispatchTaskClusterMode(int dispatchTaskClusterMode) {
		model.setDispatchTaskClusterMode(dispatchTaskClusterMode);
	}

	/**
	 * Sets the dispatch task executor type of this dispatch trigger.
	 *
	 * @param dispatchTaskExecutorType the dispatch task executor type of this dispatch trigger
	 */
	@Override
	public void setDispatchTaskExecutorType(String dispatchTaskExecutorType) {
		model.setDispatchTaskExecutorType(dispatchTaskExecutorType);
	}

	/**
	 * Sets the dispatch task settings of this dispatch trigger.
	 *
	 * @param dispatchTaskSettings the dispatch task settings of this dispatch trigger
	 */
	@Override
	public void setDispatchTaskSettings(String dispatchTaskSettings) {
		model.setDispatchTaskSettings(dispatchTaskSettings);
	}

	@Override
	public void setDispatchTaskSettingsUnicodeProperties(
		com.liferay.portal.kernel.util.UnicodeProperties
			dispatchTaskSettingsUnicodeProperties) {

		model.setDispatchTaskSettingsUnicodeProperties(
			dispatchTaskSettingsUnicodeProperties);
	}

	/**
	 * Sets the dispatch trigger ID of this dispatch trigger.
	 *
	 * @param dispatchTriggerId the dispatch trigger ID of this dispatch trigger
	 */
	@Override
	public void setDispatchTriggerId(long dispatchTriggerId) {
		model.setDispatchTriggerId(dispatchTriggerId);
	}

	/**
	 * Sets the end date of this dispatch trigger.
	 *
	 * @param endDate the end date of this dispatch trigger
	 */
	@Override
	public void setEndDate(Date endDate) {
		model.setEndDate(endDate);
	}

	/**
	 * Sets the external reference code of this dispatch trigger.
	 *
	 * @param externalReferenceCode the external reference code of this dispatch trigger
	 */
	@Override
	public void setExternalReferenceCode(String externalReferenceCode) {
		model.setExternalReferenceCode(externalReferenceCode);
	}

	/**
	 * Sets the modified date of this dispatch trigger.
	 *
	 * @param modifiedDate the modified date of this dispatch trigger
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this dispatch trigger.
	 *
	 * @param mvccVersion the mvcc version of this dispatch trigger
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this dispatch trigger.
	 *
	 * @param name the name of this dispatch trigger
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets whether this dispatch trigger is overlap allowed.
	 *
	 * @param overlapAllowed the overlap allowed of this dispatch trigger
	 */
	@Override
	public void setOverlapAllowed(boolean overlapAllowed) {
		model.setOverlapAllowed(overlapAllowed);
	}

	/**
	 * Sets the primary key of this dispatch trigger.
	 *
	 * @param primaryKey the primary key of this dispatch trigger
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the start date of this dispatch trigger.
	 *
	 * @param startDate the start date of this dispatch trigger
	 */
	@Override
	public void setStartDate(Date startDate) {
		model.setStartDate(startDate);
	}

	/**
	 * Sets whether this dispatch trigger is system.
	 *
	 * @param system the system of this dispatch trigger
	 */
	@Override
	public void setSystem(boolean system) {
		model.setSystem(system);
	}

	/**
	 * Sets the time zone ID of this dispatch trigger.
	 *
	 * @param timeZoneId the time zone ID of this dispatch trigger
	 */
	@Override
	public void setTimeZoneId(String timeZoneId) {
		model.setTimeZoneId(timeZoneId);
	}

	/**
	 * Sets the user ID of this dispatch trigger.
	 *
	 * @param userId the user ID of this dispatch trigger
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this dispatch trigger.
	 *
	 * @param userName the user name of this dispatch trigger
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this dispatch trigger.
	 *
	 * @param userUuid the user uuid of this dispatch trigger
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this dispatch trigger.
	 *
	 * @param uuid the uuid of this dispatch trigger
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected DispatchTriggerWrapper wrap(DispatchTrigger dispatchTrigger) {
		return new DispatchTriggerWrapper(dispatchTrigger);
	}

}