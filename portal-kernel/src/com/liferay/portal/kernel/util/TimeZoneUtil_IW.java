/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

/**
 * @author Brian Wing Shun Chan
 */
public class TimeZoneUtil_IW {
	public static TimeZoneUtil_IW getInstance() {
		return _instance;
	}

	public java.util.TimeZone getDefault() {
		return TimeZoneUtil.getDefault();
	}

	public com.liferay.portal.kernel.util.TimeZoneUtil getWrappedInstance() {
		return TimeZoneUtil.getInstance();
	}

	public java.util.TimeZone getTimeZone(java.lang.String timeZoneId) {
		return TimeZoneUtil.getTimeZone(timeZoneId);
	}

	public void setDefault(java.lang.String timeZoneId) {
		TimeZoneUtil.setDefault(timeZoneId);
	}

	private TimeZoneUtil_IW() {
	}

	private static TimeZoneUtil_IW _instance = new TimeZoneUtil_IW();
}