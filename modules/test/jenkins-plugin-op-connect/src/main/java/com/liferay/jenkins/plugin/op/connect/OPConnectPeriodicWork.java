/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.plugin.op.connect;

import hudson.Extension;

import hudson.model.AsyncPeriodicWork;
import hudson.model.TaskListener;

/**
 * @author Michael Hashimoto
 */
@Extension
public class OPConnectPeriodicWork extends AsyncPeriodicWork {

	public OPConnectPeriodicWork() {
		super("1Password Connect secret refresh");
	}

	@Override
	public long getRecurrencePeriod() {
		return MIN;
	}

	@Override
	protected void execute(TaskListener taskListener) {
		long refreshIntervalMinutes = OPConnectUtil.getRefreshIntervalMinutes();

		if (refreshIntervalMinutes <= 0) {
			return;
		}

		long currentTime = System.currentTimeMillis();

		if ((currentTime - _lastRefreshTime) < (refreshIntervalMinutes * MIN)) {
			return;
		}

		_lastRefreshTime = currentTime;

		OPConnectUtil.refreshSecretValues();
	}

	private long _lastRefreshTime;

}