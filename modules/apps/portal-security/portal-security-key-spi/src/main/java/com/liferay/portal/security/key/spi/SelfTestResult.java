/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.key.spi;

/**
 * @author Christopher Kian
 */
public class SelfTestResult {

	public SelfTestResult(String failedSelfTestName) {
		_failedSelfTestName = failedSelfTestName;
	}

	public String getFailedSelfTestName() {
		return _failedSelfTestName;
	}

	public boolean isPassed() {
		if (_failedSelfTestName == null) {
			return true;
		}

		return false;
	}

	private final String _failedSelfTestName;

}