/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.workflow.metrics.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link WorkflowMetricsSLACalendar}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see WorkflowMetricsSLACalendar
 * @generated
 */
@ProviderType
public class WorkflowMetricsSLACalendarWrapper
	extends BaseModelWrapper<WorkflowMetricsSLACalendar>
	implements WorkflowMetricsSLACalendar,
			   ModelWrapper<WorkflowMetricsSLACalendar> {

	public WorkflowMetricsSLACalendarWrapper(
		WorkflowMetricsSLACalendar workflowMetricsSLACalendar) {

		super(workflowMetricsSLACalendar);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put(
			"workflowMetricsSLACalendarId", getWorkflowMetricsSLACalendarId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());

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

		Long workflowMetricsSLACalendarId = (Long)attributes.get(
			"workflowMetricsSLACalendarId");

		if (workflowMetricsSLACalendarId != null) {
			setWorkflowMetricsSLACalendarId(workflowMetricsSLACalendarId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
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
	 * Returns the company ID of this workflow metrics sla calendar.
	 *
	 * @return the company ID of this workflow metrics sla calendar
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this workflow metrics sla calendar.
	 *
	 * @return the create date of this workflow metrics sla calendar
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the group ID of this workflow metrics sla calendar.
	 *
	 * @return the group ID of this workflow metrics sla calendar
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the modified date of this workflow metrics sla calendar.
	 *
	 * @return the modified date of this workflow metrics sla calendar
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this workflow metrics sla calendar.
	 *
	 * @return the mvcc version of this workflow metrics sla calendar
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this workflow metrics sla calendar.
	 *
	 * @return the primary key of this workflow metrics sla calendar
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this workflow metrics sla calendar.
	 *
	 * @return the user ID of this workflow metrics sla calendar
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this workflow metrics sla calendar.
	 *
	 * @return the user name of this workflow metrics sla calendar
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this workflow metrics sla calendar.
	 *
	 * @return the user uuid of this workflow metrics sla calendar
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this workflow metrics sla calendar.
	 *
	 * @return the uuid of this workflow metrics sla calendar
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	/**
	 * Returns the workflow metrics sla calendar ID of this workflow metrics sla calendar.
	 *
	 * @return the workflow metrics sla calendar ID of this workflow metrics sla calendar
	 */
	@Override
	public long getWorkflowMetricsSLACalendarId() {
		return model.getWorkflowMetricsSLACalendarId();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this workflow metrics sla calendar.
	 *
	 * @param companyId the company ID of this workflow metrics sla calendar
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this workflow metrics sla calendar.
	 *
	 * @param createDate the create date of this workflow metrics sla calendar
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the group ID of this workflow metrics sla calendar.
	 *
	 * @param groupId the group ID of this workflow metrics sla calendar
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the modified date of this workflow metrics sla calendar.
	 *
	 * @param modifiedDate the modified date of this workflow metrics sla calendar
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this workflow metrics sla calendar.
	 *
	 * @param mvccVersion the mvcc version of this workflow metrics sla calendar
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this workflow metrics sla calendar.
	 *
	 * @param primaryKey the primary key of this workflow metrics sla calendar
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this workflow metrics sla calendar.
	 *
	 * @param userId the user ID of this workflow metrics sla calendar
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this workflow metrics sla calendar.
	 *
	 * @param userName the user name of this workflow metrics sla calendar
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this workflow metrics sla calendar.
	 *
	 * @param userUuid the user uuid of this workflow metrics sla calendar
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this workflow metrics sla calendar.
	 *
	 * @param uuid the uuid of this workflow metrics sla calendar
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	/**
	 * Sets the workflow metrics sla calendar ID of this workflow metrics sla calendar.
	 *
	 * @param workflowMetricsSLACalendarId the workflow metrics sla calendar ID of this workflow metrics sla calendar
	 */
	@Override
	public void setWorkflowMetricsSLACalendarId(
		long workflowMetricsSLACalendarId) {

		model.setWorkflowMetricsSLACalendarId(workflowMetricsSLACalendarId);
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected WorkflowMetricsSLACalendarWrapper wrap(
		WorkflowMetricsSLACalendar workflowMetricsSLACalendar) {

		return new WorkflowMetricsSLACalendarWrapper(
			workflowMetricsSLACalendar);
	}

}