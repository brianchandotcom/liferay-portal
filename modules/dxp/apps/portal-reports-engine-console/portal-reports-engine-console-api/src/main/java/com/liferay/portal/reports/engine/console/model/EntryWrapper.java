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

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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
public class EntryWrapper implements Entry, ModelWrapper<Entry> {
	public EntryWrapper(Entry entry) {
		_entry = entry;
	}

	@Override
	public Class<?> getModelClass() {
		return Entry.class;
	}

	@Override
	public String getModelClassName() {
		return Entry.class.getName();
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
	public Object clone() {
		return new EntryWrapper((Entry)_entry.clone());
	}

	@Override
	public int compareTo(Entry entry) {
		return _entry.compareTo(entry);
	}

	@Override
	public String getAttachmentsDir() {
		return _entry.getAttachmentsDir();
	}

	@Override
	public String[] getAttachmentsFiles()
		throws com.liferay.portal.kernel.exception.PortalException {
		return _entry.getAttachmentsFiles();
	}

	/**
	* Returns the company ID of this entry.
	*
	* @return the company ID of this entry
	*/
	@Override
	public long getCompanyId() {
		return _entry.getCompanyId();
	}

	/**
	* Returns the create date of this entry.
	*
	* @return the create date of this entry
	*/
	@Override
	public Date getCreateDate() {
		return _entry.getCreateDate();
	}

	/**
	* Returns the definition ID of this entry.
	*
	* @return the definition ID of this entry
	*/
	@Override
	public long getDefinitionId() {
		return _entry.getDefinitionId();
	}

	/**
	* Returns the email delivery of this entry.
	*
	* @return the email delivery of this entry
	*/
	@Override
	public String getEmailDelivery() {
		return _entry.getEmailDelivery();
	}

	/**
	* Returns the email notifications of this entry.
	*
	* @return the email notifications of this entry
	*/
	@Override
	public String getEmailNotifications() {
		return _entry.getEmailNotifications();
	}

	/**
	* Returns the end date of this entry.
	*
	* @return the end date of this entry
	*/
	@Override
	public Date getEndDate() {
		return _entry.getEndDate();
	}

	/**
	* Returns the entry ID of this entry.
	*
	* @return the entry ID of this entry
	*/
	@Override
	public long getEntryId() {
		return _entry.getEntryId();
	}

	/**
	* Returns the error message of this entry.
	*
	* @return the error message of this entry
	*/
	@Override
	public String getErrorMessage() {
		return _entry.getErrorMessage();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _entry.getExpandoBridge();
	}

	/**
	* Returns the format of this entry.
	*
	* @return the format of this entry
	*/
	@Override
	public String getFormat() {
		return _entry.getFormat();
	}

	/**
	* Returns the group ID of this entry.
	*
	* @return the group ID of this entry
	*/
	@Override
	public long getGroupId() {
		return _entry.getGroupId();
	}

	@Override
	public String getJobName() {
		return _entry.getJobName();
	}

	/**
	* Returns the modified date of this entry.
	*
	* @return the modified date of this entry
	*/
	@Override
	public Date getModifiedDate() {
		return _entry.getModifiedDate();
	}

	/**
	* Returns the page url of this entry.
	*
	* @return the page url of this entry
	*/
	@Override
	public String getPageURL() {
		return _entry.getPageURL();
	}

	/**
	* Returns the portlet ID of this entry.
	*
	* @return the portlet ID of this entry
	*/
	@Override
	public String getPortletId() {
		return _entry.getPortletId();
	}

	/**
	* Returns the primary key of this entry.
	*
	* @return the primary key of this entry
	*/
	@Override
	public long getPrimaryKey() {
		return _entry.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _entry.getPrimaryKeyObj();
	}

	/**
	* Returns the recurrence of this entry.
	*
	* @return the recurrence of this entry
	*/
	@Override
	public String getRecurrence() {
		return _entry.getRecurrence();
	}

	@Override
	public com.liferay.portal.kernel.cal.TZSRecurrence getRecurrenceObj() {
		return _entry.getRecurrenceObj();
	}

	/**
	* Returns the repeating of this entry.
	*
	* @return the repeating of this entry
	*/
	@Override
	public boolean getRepeating() {
		return _entry.getRepeating();
	}

	/**
	* Returns the report parameters of this entry.
	*
	* @return the report parameters of this entry
	*/
	@Override
	public String getReportParameters() {
		return _entry.getReportParameters();
	}

	/**
	* Returns the schedule request of this entry.
	*
	* @return the schedule request of this entry
	*/
	@Override
	public boolean getScheduleRequest() {
		return _entry.getScheduleRequest();
	}

	@Override
	public String getSchedulerRequestName() {
		return _entry.getSchedulerRequestName();
	}

	/**
	* Returns the start date of this entry.
	*
	* @return the start date of this entry
	*/
	@Override
	public Date getStartDate() {
		return _entry.getStartDate();
	}

	/**
	* Returns the status of this entry.
	*
	* @return the status of this entry
	*/
	@Override
	public String getStatus() {
		return _entry.getStatus();
	}

	/**
	* Returns the user ID of this entry.
	*
	* @return the user ID of this entry
	*/
	@Override
	public long getUserId() {
		return _entry.getUserId();
	}

	/**
	* Returns the user name of this entry.
	*
	* @return the user name of this entry
	*/
	@Override
	public String getUserName() {
		return _entry.getUserName();
	}

	/**
	* Returns the user uuid of this entry.
	*
	* @return the user uuid of this entry
	*/
	@Override
	public String getUserUuid() {
		return _entry.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _entry.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _entry.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _entry.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _entry.isNew();
	}

	/**
	* Returns <code>true</code> if this entry is repeating.
	*
	* @return <code>true</code> if this entry is repeating; <code>false</code> otherwise
	*/
	@Override
	public boolean isRepeating() {
		return _entry.isRepeating();
	}

	/**
	* Returns <code>true</code> if this entry is schedule request.
	*
	* @return <code>true</code> if this entry is schedule request; <code>false</code> otherwise
	*/
	@Override
	public boolean isScheduleRequest() {
		return _entry.isScheduleRequest();
	}

	@Override
	public void persist() {
		_entry.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_entry.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this entry.
	*
	* @param companyId the company ID of this entry
	*/
	@Override
	public void setCompanyId(long companyId) {
		_entry.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this entry.
	*
	* @param createDate the create date of this entry
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_entry.setCreateDate(createDate);
	}

	/**
	* Sets the definition ID of this entry.
	*
	* @param definitionId the definition ID of this entry
	*/
	@Override
	public void setDefinitionId(long definitionId) {
		_entry.setDefinitionId(definitionId);
	}

	/**
	* Sets the email delivery of this entry.
	*
	* @param emailDelivery the email delivery of this entry
	*/
	@Override
	public void setEmailDelivery(String emailDelivery) {
		_entry.setEmailDelivery(emailDelivery);
	}

	/**
	* Sets the email notifications of this entry.
	*
	* @param emailNotifications the email notifications of this entry
	*/
	@Override
	public void setEmailNotifications(String emailNotifications) {
		_entry.setEmailNotifications(emailNotifications);
	}

	/**
	* Sets the end date of this entry.
	*
	* @param endDate the end date of this entry
	*/
	@Override
	public void setEndDate(Date endDate) {
		_entry.setEndDate(endDate);
	}

	/**
	* Sets the entry ID of this entry.
	*
	* @param entryId the entry ID of this entry
	*/
	@Override
	public void setEntryId(long entryId) {
		_entry.setEntryId(entryId);
	}

	/**
	* Sets the error message of this entry.
	*
	* @param errorMessage the error message of this entry
	*/
	@Override
	public void setErrorMessage(String errorMessage) {
		_entry.setErrorMessage(errorMessage);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_entry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_entry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_entry.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the format of this entry.
	*
	* @param format the format of this entry
	*/
	@Override
	public void setFormat(String format) {
		_entry.setFormat(format);
	}

	/**
	* Sets the group ID of this entry.
	*
	* @param groupId the group ID of this entry
	*/
	@Override
	public void setGroupId(long groupId) {
		_entry.setGroupId(groupId);
	}

	/**
	* Sets the modified date of this entry.
	*
	* @param modifiedDate the modified date of this entry
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_entry.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_entry.setNew(n);
	}

	/**
	* Sets the page url of this entry.
	*
	* @param pageURL the page url of this entry
	*/
	@Override
	public void setPageURL(String pageURL) {
		_entry.setPageURL(pageURL);
	}

	/**
	* Sets the portlet ID of this entry.
	*
	* @param portletId the portlet ID of this entry
	*/
	@Override
	public void setPortletId(String portletId) {
		_entry.setPortletId(portletId);
	}

	/**
	* Sets the primary key of this entry.
	*
	* @param primaryKey the primary key of this entry
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_entry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_entry.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the recurrence of this entry.
	*
	* @param recurrence the recurrence of this entry
	*/
	@Override
	public void setRecurrence(String recurrence) {
		_entry.setRecurrence(recurrence);
	}

	/**
	* Sets whether this entry is repeating.
	*
	* @param repeating the repeating of this entry
	*/
	@Override
	public void setRepeating(boolean repeating) {
		_entry.setRepeating(repeating);
	}

	/**
	* Sets the report parameters of this entry.
	*
	* @param reportParameters the report parameters of this entry
	*/
	@Override
	public void setReportParameters(String reportParameters) {
		_entry.setReportParameters(reportParameters);
	}

	/**
	* Sets whether this entry is schedule request.
	*
	* @param scheduleRequest the schedule request of this entry
	*/
	@Override
	public void setScheduleRequest(boolean scheduleRequest) {
		_entry.setScheduleRequest(scheduleRequest);
	}

	/**
	* Sets the start date of this entry.
	*
	* @param startDate the start date of this entry
	*/
	@Override
	public void setStartDate(Date startDate) {
		_entry.setStartDate(startDate);
	}

	/**
	* Sets the status of this entry.
	*
	* @param status the status of this entry
	*/
	@Override
	public void setStatus(String status) {
		_entry.setStatus(status);
	}

	/**
	* Sets the user ID of this entry.
	*
	* @param userId the user ID of this entry
	*/
	@Override
	public void setUserId(long userId) {
		_entry.setUserId(userId);
	}

	/**
	* Sets the user name of this entry.
	*
	* @param userName the user name of this entry
	*/
	@Override
	public void setUserName(String userName) {
		_entry.setUserName(userName);
	}

	/**
	* Sets the user uuid of this entry.
	*
	* @param userUuid the user uuid of this entry
	*/
	@Override
	public void setUserUuid(String userUuid) {
		_entry.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<Entry> toCacheModel() {
		return _entry.toCacheModel();
	}

	@Override
	public Entry toEscapedModel() {
		return new EntryWrapper(_entry.toEscapedModel());
	}

	@Override
	public String toString() {
		return _entry.toString();
	}

	@Override
	public Entry toUnescapedModel() {
		return new EntryWrapper(_entry.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _entry.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof EntryWrapper)) {
			return false;
		}

		EntryWrapper entryWrapper = (EntryWrapper)obj;

		if (Objects.equals(_entry, entryWrapper._entry)) {
			return true;
		}

		return false;
	}

	@Override
	public Entry getWrappedModel() {
		return _entry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _entry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _entry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_entry.resetOriginalValues();
	}

	private final Entry _entry;
}