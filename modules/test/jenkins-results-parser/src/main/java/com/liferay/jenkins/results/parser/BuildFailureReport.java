/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser;

/**
 * @author Michael Hashimoto
 */
public class BuildFailureReport extends BaseFailureReport {

	@Override
	public String getMessage() {
		return _message;
	}

	protected BuildFailureReport(BuildReport buildReport, String message) {
		super(buildReport);

		_message = message;
	}

	private final String _message;

}