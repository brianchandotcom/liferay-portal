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

package com.liferay.lcs.task;

import com.liferay.lcs.advisor.LCSPortletStateAdvisor;
import com.liferay.lcs.util.LCSConnectionManager;
import com.liferay.lcs.util.LCSUtil;
import com.liferay.portal.kernel.license.messaging.LCSPortletState;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Map;

/**
 * @author Igor Beslic
 */
public class LicenseManagerTask implements Task {

	public LicenseManagerTask() {
		if (_log.isTraceEnabled()) {
			_log.trace("Initialized " + this);
		}
	}

	@Override
	public void run() {
		if (_log.isTraceEnabled()) {
			_log.trace("Running license manager task");
		}

		Map<String, String> lcsConnectionMetadata =
			_lcsConnectionManager.getLCSConnectionMetadata();

		if (_log.isTraceEnabled()) {
			_log.trace("Checking LCS portlet state");
		}

		long currentTimeMills = System.currentTimeMillis();

		long licenseCheckTime = GetterUtil.getLong(
			lcsConnectionMetadata.get("licenseCheckTime"));

		if ((currentTimeMills - licenseCheckTime) < _LICENSE_CHECK_PERIOD) {
			LCSUtil.processLCSPortletState(
				_lcsPortletStateAdvisor.getLCSPortletState(false));

			return;
		}

		LCSPortletState lcsPortletState =
			_lcsPortletStateAdvisor.getLCSPortletState(true);

		if (_log.isTraceEnabled()) {
			_log.trace("LCS portlet state: " + lcsPortletState);
		}

		LCSUtil.processLCSPortletState(lcsPortletState);

		_lcsConnectionManager.putLCSConnectionMetadata(
			"licenseCheckTime", String.valueOf(currentTimeMills));
	}

	public void setLCSConnectionManager(
		LCSConnectionManager lcsConnectionManager) {

		_lcsConnectionManager = lcsConnectionManager;
	}

	public void setLCSPortletStateAdvisor(
		LCSPortletStateAdvisor lcsPortletStateAdvisor) {

		_lcsPortletStateAdvisor = lcsPortletStateAdvisor;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();

		if (_log.isTraceEnabled()) {
			_log.trace("Finalized " + this);
		}
	}

	private static final long _LICENSE_CHECK_PERIOD = 60000L * 15L;

	private static final Log _log = LogFactoryUtil.getLog(
		LicenseManagerTask.class);

	private LCSConnectionManager _lcsConnectionManager;
	private LCSPortletStateAdvisor _lcsPortletStateAdvisor;

}