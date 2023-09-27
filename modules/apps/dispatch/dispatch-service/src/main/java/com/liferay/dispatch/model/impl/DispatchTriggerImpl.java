/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dispatch.model.impl;

import com.liferay.dispatch.executor.DispatchTaskStatus;
import com.liferay.dispatch.model.DispatchLog;
import com.liferay.dispatch.service.DispatchLogLocalServiceUtil;
import com.liferay.dispatch.service.DispatchTriggerLocalServiceUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;

import java.util.Date;
import java.util.TimeZone;

/**
 * @author Alessio Antonio Rendina
 * @author Igor Beslic
 */
public class DispatchTriggerImpl extends DispatchTriggerBaseImpl {

	@Override
	public UnicodeProperties getDispatchTaskSettingsUnicodeProperties() {
		if (_dispatchTaskSettingsUnicodeProperties == null) {
			_dispatchTaskSettingsUnicodeProperties =
				UnicodePropertiesBuilder.create(
					true
				).fastLoad(
					getDispatchTaskSettings()
				).build();
		}

		return _dispatchTaskSettingsUnicodeProperties;
	}

	@Override
	public DispatchTaskStatus getDispatchTaskStatus() {
		if (_dispatchTaskStatus != null) {
			return _dispatchTaskStatus;
		}

		DispatchLog dispatchLog =
			DispatchLogLocalServiceUtil.fetchLatestDispatchLog(
				getDispatchTriggerId());

		if (dispatchLog == null) {
			return DispatchTaskStatus.NEVER_RAN;
		}

		_dispatchTaskStatus = DispatchTaskStatus.valueOf(
			dispatchLog.getStatus());

		return _dispatchTaskStatus;
	}

	@Override
	public Date getNextFireDate() {
		if ((_nextFireDate != null) &&
			(_nextFireDate.getTime() > System.currentTimeMillis())) {

			return _nextFireDate;
		}

		_nextFireDate = DispatchTriggerLocalServiceUtil.fetchNextFireDate(
			getDispatchTriggerId());

		return _nextFireDate;
	}

	@Override
	public Date getTimeZoneEndDate() {
		return _getTimeZoneDate(getEndDate(), getTimeZoneId());
	}

	@Override
	public Date getTimeZoneStartDate() {
		return _getTimeZoneDate(getStartDate(), getTimeZoneId());
	}

	@Override
	public void setDispatchTaskSettings(String dispatchTaskSettings) {
		super.setDispatchTaskSettings(dispatchTaskSettings);

		_dispatchTaskSettingsUnicodeProperties = null;
	}

	@Override
	public void setDispatchTaskSettingsUnicodeProperties(
		UnicodeProperties dispatchTaskSettingsUnicodeProperties) {

		_dispatchTaskSettingsUnicodeProperties =
			dispatchTaskSettingsUnicodeProperties;

		if (_dispatchTaskSettingsUnicodeProperties == null) {
			_dispatchTaskSettingsUnicodeProperties = new UnicodeProperties();
		}

		super.setDispatchTaskSettings(
			_dispatchTaskSettingsUnicodeProperties.toString());
	}

	private Date _getTimeZoneDate(Date date, String timeZoneId) {
		if (date == null) {
			return null;
		}

		TimeZone timeZone = TimeZone.getTimeZone(timeZoneId);

		return new Date(date.getTime() + timeZone.getOffset(date.getTime()));
	}

	private transient UnicodeProperties _dispatchTaskSettingsUnicodeProperties;
	private transient DispatchTaskStatus _dispatchTaskStatus;
	private transient Date _nextFireDate;

}