/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.process;

/**
 * @author Shuyang Zhou
 */
public class TerminationProcessException extends ProcessException {

	public TerminationProcessException(int exitCode) {
		this("Subprocess terminated with exit code " + exitCode, exitCode);
	}

	public TerminationProcessException(String message, int exitCode) {
		super(message);

		_exitCode = exitCode;
	}

	public int getExitCode() {
		return _exitCode;
	}

	private final int _exitCode;

}