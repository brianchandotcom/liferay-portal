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

package com.liferay.dispatch.model.impl;

import com.liferay.portal.kernel.scheduler.SchedulerEngineHelperUtil;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.util.Date;

/**
 * @author Alessio Antonio Rendina
 */
public class DispatchTriggerImpl extends DispatchTriggerBaseImpl {

	public DispatchTriggerImpl() {
	}

	@Override
	public Date getEndDate() throws SchedulerException {
		if (_endDate == null) {
			_endDate = SchedulerEngineHelperUtil.getEndTime(
				String.format("DISPATCH_JOB_%07d", getDispatchTriggerId()),
				String.format("DISPATCH_GROUP_%07d", getDispatchTriggerId()),
				StorageType.PERSISTED);
		}

		return _endDate;
	}

	@Override
	public Date getStartDate() throws SchedulerException {
		if (_startDate == null) {
			_startDate = SchedulerEngineHelperUtil.getStartTime(
				String.format("DISPATCH_JOB_%07d", getDispatchTriggerId()),
				String.format("DISPATCH_GROUP_%07d", getDispatchTriggerId()),
				StorageType.PERSISTED);
		}

		return _startDate;
	}

	@Override
	public UnicodeProperties getTypeSettingsProperties() {
		if (_typeSettingsUnicodeProperties == null) {
			_typeSettingsUnicodeProperties = new UnicodeProperties(true);

			_typeSettingsUnicodeProperties.fastLoad(getTypeSettings());
		}

		return _typeSettingsUnicodeProperties;
	}

	@Override
	public void setEndDate(Date endDate) {
		_endDate = endDate;
	}

	@Override
	public void setStartDate(Date startDate) {
		_startDate = startDate;
	}

	@Override
	public void setTypeSettings(String typeSettings) {
		super.setTypeSettings(typeSettings);

		_typeSettingsUnicodeProperties = null;
	}

	@Override
	public void setTypeSettingsProperties(
		UnicodeProperties typeSettingsUnicodeProperties) {

		_typeSettingsUnicodeProperties = typeSettingsUnicodeProperties;

		if (_typeSettingsUnicodeProperties == null) {
			_typeSettingsUnicodeProperties = new UnicodeProperties();
		}

		super.setTypeSettings(_typeSettingsUnicodeProperties.toString());
	}

	private Date _endDate;
	private Date _startDate;
	private transient UnicodeProperties _typeSettingsUnicodeProperties;

}