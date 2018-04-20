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

package com.liferay.portal.reports.engine.console.model;

import aQute.bnd.annotation.ProviderType;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.portal.reports.engine.console.service.http.EntryServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portal.reports.engine.console.service.http.EntryServiceSoap
 * @generated
 */
@ProviderType
public class EntrySoap implements Serializable {
	public static EntrySoap toSoapModel(Entry model) {
		EntrySoap soapModel = new EntrySoap();

		soapModel.setEntryId(model.getEntryId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setDefinitionId(model.getDefinitionId());
		soapModel.setFormat(model.getFormat());
		soapModel.setScheduleRequest(model.isScheduleRequest());
		soapModel.setStartDate(model.getStartDate());
		soapModel.setEndDate(model.getEndDate());
		soapModel.setRepeating(model.isRepeating());
		soapModel.setRecurrence(model.getRecurrence());
		soapModel.setEmailNotifications(model.getEmailNotifications());
		soapModel.setEmailDelivery(model.getEmailDelivery());
		soapModel.setPortletId(model.getPortletId());
		soapModel.setPageURL(model.getPageURL());
		soapModel.setReportParameters(model.getReportParameters());
		soapModel.setStatus(model.getStatus());
		soapModel.setErrorMessage(model.getErrorMessage());

		return soapModel;
	}

	public static EntrySoap[] toSoapModels(Entry[] models) {
		EntrySoap[] soapModels = new EntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static EntrySoap[][] toSoapModels(Entry[][] models) {
		EntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new EntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new EntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static EntrySoap[] toSoapModels(List<Entry> models) {
		List<EntrySoap> soapModels = new ArrayList<EntrySoap>(models.size());

		for (Entry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new EntrySoap[soapModels.size()]);
	}

	public EntrySoap() {
	}

	public long getPrimaryKey() {
		return _entryId;
	}

	public void setPrimaryKey(long pk) {
		setEntryId(pk);
	}

	public long getEntryId() {
		return _entryId;
	}

	public void setEntryId(long entryId) {
		_entryId = entryId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public String getUserName() {
		return _userName;
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public long getDefinitionId() {
		return _definitionId;
	}

	public void setDefinitionId(long definitionId) {
		_definitionId = definitionId;
	}

	public String getFormat() {
		return _format;
	}

	public void setFormat(String format) {
		_format = format;
	}

	public boolean getScheduleRequest() {
		return _scheduleRequest;
	}

	public boolean isScheduleRequest() {
		return _scheduleRequest;
	}

	public void setScheduleRequest(boolean scheduleRequest) {
		_scheduleRequest = scheduleRequest;
	}

	public Date getStartDate() {
		return _startDate;
	}

	public void setStartDate(Date startDate) {
		_startDate = startDate;
	}

	public Date getEndDate() {
		return _endDate;
	}

	public void setEndDate(Date endDate) {
		_endDate = endDate;
	}

	public boolean getRepeating() {
		return _repeating;
	}

	public boolean isRepeating() {
		return _repeating;
	}

	public void setRepeating(boolean repeating) {
		_repeating = repeating;
	}

	public String getRecurrence() {
		return _recurrence;
	}

	public void setRecurrence(String recurrence) {
		_recurrence = recurrence;
	}

	public String getEmailNotifications() {
		return _emailNotifications;
	}

	public void setEmailNotifications(String emailNotifications) {
		_emailNotifications = emailNotifications;
	}

	public String getEmailDelivery() {
		return _emailDelivery;
	}

	public void setEmailDelivery(String emailDelivery) {
		_emailDelivery = emailDelivery;
	}

	public String getPortletId() {
		return _portletId;
	}

	public void setPortletId(String portletId) {
		_portletId = portletId;
	}

	public String getPageURL() {
		return _pageURL;
	}

	public void setPageURL(String pageURL) {
		_pageURL = pageURL;
	}

	public String getReportParameters() {
		return _reportParameters;
	}

	public void setReportParameters(String reportParameters) {
		_reportParameters = reportParameters;
	}

	public String getStatus() {
		return _status;
	}

	public void setStatus(String status) {
		_status = status;
	}

	public String getErrorMessage() {
		return _errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		_errorMessage = errorMessage;
	}

	private long _entryId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _definitionId;
	private String _format;
	private boolean _scheduleRequest;
	private Date _startDate;
	private Date _endDate;
	private boolean _repeating;
	private String _recurrence;
	private String _emailNotifications;
	private String _emailDelivery;
	private String _portletId;
	private String _pageURL;
	private String _reportParameters;
	private String _status;
	private String _errorMessage;
}