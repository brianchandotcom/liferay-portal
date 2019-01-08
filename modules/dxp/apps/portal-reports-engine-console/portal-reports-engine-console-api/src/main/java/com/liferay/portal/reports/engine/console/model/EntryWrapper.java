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

package com.liferay.portal.reports.engine.console.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link Entry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see Entry
 * @generated
 */
@ProviderType
public class EntryWrapper extends BaseModelWrapper<Entry> implements Entry,
	ModelWrapper<Entry> {
	public EntryWrapper(Entry entry) {
		super(entry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("entryId", getEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("definitionId", getDefinitionId());
		attributes.put("format", getFormat());
		attributes.put("scheduleRequest", isScheduleRequest());
		attributes.put("startDate", getStartDate());
		attributes.put("endDate", getEndDate());
		attributes.put("repeating", isRepeating());
		attributes.put("recurrence", getRecurrence());
		attributes.put("emailNotifications", getEmailNotifications());
		attributes.put("emailDelivery", getEmailDelivery());
		attributes.put("portletId", getPortletId());
		attributes.put("pageURL", getPageURL());
		attributes.put("reportParameters", getReportParameters());
		attributes.put("status", getStatus());
		attributes.put("errorMessage", getErrorMessage());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long entryId = (Long)attributes.get("entryId");

		if (entryId != null) {
			setEntryId(entryId);
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

		Long definitionId = (Long)attributes.get("definitionId");

		if (definitionId != null) {
			setDefinitionId(definitionId);
		}

		String format = (String)attributes.get("format");

		if (format != null) {
			setFormat(format);
		}

		Boolean scheduleRequest = (Boolean)attributes.get("scheduleRequest");

		if (scheduleRequest != null) {
			setScheduleRequest(scheduleRequest);
		}

		Date startDate = (Date)attributes.get("startDate");

		if (startDate != null) {
			setStartDate(startDate);
		}

		Date endDate = (Date)attributes.get("endDate");

		if (endDate != null) {
			setEndDate(endDate);
		}

		Boolean repeating = (Boolean)attributes.get("repeating");

		if (repeating != null) {
			setRepeating(repeating);
		}

		String recurrence = (String)attributes.get("recurrence");

		if (recurrence != null) {
			setRecurrence(recurrence);
		}

		String emailNotifications = (String)attributes.get("emailNotifications");

		if (emailNotifications != null) {
			setEmailNotifications(emailNotifications);
		}

		String emailDelivery = (String)attributes.get("emailDelivery");

		if (emailDelivery != null) {
			setEmailDelivery(emailDelivery);
		}

		String portletId = (String)attributes.get("portletId");

		if (portletId != null) {
			setPortletId(portletId);
		}

		String pageURL = (String)attributes.get("pageURL");

		if (pageURL != null) {
			setPageURL(pageURL);
		}

		String reportParameters = (String)attributes.get("reportParameters");

		if (reportParameters != null) {
			setReportParameters(reportParameters);
		}

		String status = (String)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}

		String errorMessage = (String)attributes.get("errorMessage");

		if (errorMessage != null) {
			setErrorMessage(errorMessage);
		}
	}

	@Override
	public String getAttachmentsDir() {
		return model.getAttachmentsDir();
	}

	@Override
	public String[] getAttachmentsFiles()
		throws com.liferay.portal.kernel.exception.PortalException {
		return model.getAttachmentsFiles();
	}

	/**
	* Returns the company ID of this entry.
	*
	* @return the company ID of this entry
	*/
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	* Returns the create date of this entry.
	*
	* @return the create date of this entry
	*/
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	* Returns the definition ID of this entry.
	*
	* @return the definition ID of this entry
	*/
	@Override
	public long getDefinitionId() {
		return model.getDefinitionId();
	}

	/**
	* Returns the email delivery of this entry.
	*
	* @return the email delivery of this entry
	*/
	@Override
	public String getEmailDelivery() {
		return model.getEmailDelivery();
	}

	/**
	* Returns the email notifications of this entry.
	*
	* @return the email notifications of this entry
	*/
	@Override
	public String getEmailNotifications() {
		return model.getEmailNotifications();
	}

	/**
	* Returns the end date of this entry.
	*
	* @return the end date of this entry
	*/
	@Override
	public Date getEndDate() {
		return model.getEndDate();
	}

	/**
	* Returns the entry ID of this entry.
	*
	* @return the entry ID of this entry
	*/
	@Override
	public long getEntryId() {
		return model.getEntryId();
	}

	/**
	* Returns the error message of this entry.
	*
	* @return the error message of this entry
	*/
	@Override
	public String getErrorMessage() {
		return model.getErrorMessage();
	}

	/**
	* Returns the format of this entry.
	*
	* @return the format of this entry
	*/
	@Override
	public String getFormat() {
		return model.getFormat();
	}

	/**
	* Returns the group ID of this entry.
	*
	* @return the group ID of this entry
	*/
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	@Override
	public String getJobName() {
		return model.getJobName();
	}

	/**
	* Returns the modified date of this entry.
	*
	* @return the modified date of this entry
	*/
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	* Returns the page url of this entry.
	*
	* @return the page url of this entry
	*/
	@Override
	public String getPageURL() {
		return model.getPageURL();
	}

	/**
	* Returns the portlet ID of this entry.
	*
	* @return the portlet ID of this entry
	*/
	@Override
	public String getPortletId() {
		return model.getPortletId();
	}

	/**
	* Returns the primary key of this entry.
	*
	* @return the primary key of this entry
	*/
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	* Returns the recurrence of this entry.
	*
	* @return the recurrence of this entry
	*/
	@Override
	public String getRecurrence() {
		return model.getRecurrence();
	}

	@Override
	public com.liferay.portal.kernel.cal.TZSRecurrence getRecurrenceObj() {
		return model.getRecurrenceObj();
	}

	/**
	* Returns the repeating of this entry.
	*
	* @return the repeating of this entry
	*/
	@Override
	public boolean getRepeating() {
		return model.getRepeating();
	}

	/**
	* Returns the report parameters of this entry.
	*
	* @return the report parameters of this entry
	*/
	@Override
	public String getReportParameters() {
		return model.getReportParameters();
	}

	/**
	* Returns the schedule request of this entry.
	*
	* @return the schedule request of this entry
	*/
	@Override
	public boolean getScheduleRequest() {
		return model.getScheduleRequest();
	}

	@Override
	public String getSchedulerRequestName() {
		return model.getSchedulerRequestName();
	}

	/**
	* Returns the start date of this entry.
	*
	* @return the start date of this entry
	*/
	@Override
	public Date getStartDate() {
		return model.getStartDate();
	}

	/**
	* Returns the status of this entry.
	*
	* @return the status of this entry
	*/
	@Override
	public String getStatus() {
		return model.getStatus();
	}

	/**
	* Returns the user ID of this entry.
	*
	* @return the user ID of this entry
	*/
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	* Returns the user name of this entry.
	*
	* @return the user name of this entry
	*/
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	* Returns the user uuid of this entry.
	*
	* @return the user uuid of this entry
	*/
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	* Returns <code>true</code> if this entry is repeating.
	*
	* @return <code>true</code> if this entry is repeating; <code>false</code> otherwise
	*/
	@Override
	public boolean isRepeating() {
		return model.isRepeating();
	}

	/**
	* Returns <code>true</code> if this entry is schedule request.
	*
	* @return <code>true</code> if this entry is schedule request; <code>false</code> otherwise
	*/
	@Override
	public boolean isScheduleRequest() {
		return model.isScheduleRequest();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	* Sets the company ID of this entry.
	*
	* @param companyId the company ID of this entry
	*/
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this entry.
	*
	* @param createDate the create date of this entry
	*/
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	* Sets the definition ID of this entry.
	*
	* @param definitionId the definition ID of this entry
	*/
	@Override
	public void setDefinitionId(long definitionId) {
		model.setDefinitionId(definitionId);
	}

	/**
	* Sets the email delivery of this entry.
	*
	* @param emailDelivery the email delivery of this entry
	*/
	@Override
	public void setEmailDelivery(String emailDelivery) {
		model.setEmailDelivery(emailDelivery);
	}

	/**
	* Sets the email notifications of this entry.
	*
	* @param emailNotifications the email notifications of this entry
	*/
	@Override
	public void setEmailNotifications(String emailNotifications) {
		model.setEmailNotifications(emailNotifications);
	}

	/**
	* Sets the end date of this entry.
	*
	* @param endDate the end date of this entry
	*/
	@Override
	public void setEndDate(Date endDate) {
		model.setEndDate(endDate);
	}

	/**
	* Sets the entry ID of this entry.
	*
	* @param entryId the entry ID of this entry
	*/
	@Override
	public void setEntryId(long entryId) {
		model.setEntryId(entryId);
	}

	/**
	* Sets the error message of this entry.
	*
	* @param errorMessage the error message of this entry
	*/
	@Override
	public void setErrorMessage(String errorMessage) {
		model.setErrorMessage(errorMessage);
	}

	/**
	* Sets the format of this entry.
	*
	* @param format the format of this entry
	*/
	@Override
	public void setFormat(String format) {
		model.setFormat(format);
	}

	/**
	* Sets the group ID of this entry.
	*
	* @param groupId the group ID of this entry
	*/
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this entry.
	*
	* @param modifiedDate the modified date of this entry
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the page url of this entry.
	*
	* @param pageURL the page url of this entry
	*/
	@Override
	public void setPageURL(String pageURL) {
		model.setPageURL(pageURL);
	}

	/**
	* Sets the portlet ID of this entry.
	*
	* @param portletId the portlet ID of this entry
	*/
	@Override
	public void setPortletId(String portletId) {
		model.setPortletId(portletId);
	}

	/**
	* Sets the primary key of this entry.
	*
	* @param primaryKey the primary key of this entry
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	* Sets the recurrence of this entry.
	*
	* @param recurrence the recurrence of this entry
	*/
	@Override
	public void setRecurrence(String recurrence) {
		model.setRecurrence(recurrence);
	}

	/**
	* Sets whether this entry is repeating.
	*
	* @param repeating the repeating of this entry
	*/
	@Override
	public void setRepeating(boolean repeating) {
		model.setRepeating(repeating);
	}

	/**
	* Sets the report parameters of this entry.
	*
	* @param reportParameters the report parameters of this entry
	*/
	@Override
	public void setReportParameters(String reportParameters) {
		model.setReportParameters(reportParameters);
	}

	/**
	* Sets whether this entry is schedule request.
	*
	* @param scheduleRequest the schedule request of this entry
	*/
	@Override
	public void setScheduleRequest(boolean scheduleRequest) {
		model.setScheduleRequest(scheduleRequest);
	}

	/**
	* Sets the start date of this entry.
	*
	* @param startDate the start date of this entry
	*/
	@Override
	public void setStartDate(Date startDate) {
		model.setStartDate(startDate);
	}

	/**
	* Sets the status of this entry.
	*
	* @param status the status of this entry
	*/
	@Override
	public void setStatus(String status) {
		model.setStatus(status);
	}

	/**
	* Sets the user ID of this entry.
	*
	* @param userId the user ID of this entry
	*/
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	* Sets the user name of this entry.
	*
	* @param userName the user name of this entry
	*/
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	* Sets the user uuid of this entry.
	*
	* @param userUuid the user uuid of this entry
	*/
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected EntryWrapper wrap(Entry entry) {
		return new EntryWrapper(entry);
	}
}