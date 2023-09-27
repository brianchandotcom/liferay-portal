/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dispatch.talend.web.internal.process;

import java.io.Serializable;

/**
 * @author Matija Petanjek
 */
public class TalendProcessOutput implements Serializable {

	public TalendProcessOutput(int exitCode, String stdOut, String stdErr) {
		_exitCode = exitCode;
		_stdOut = stdOut;
		_stdErr = stdErr;
	}

	public int getExitCode() {
		return _exitCode;
	}

	public String getStdErr() {
		return _stdErr;
	}

	public String getStdOut() {
		return _stdOut;
	}

	public boolean hasException() {
		if (_exitCode == 0) {
			return false;
		}

		return true;
	}

	private static final long serialVersionUID = 1L;

	private final int _exitCode;
	private final String _stdErr;
	private final String _stdOut;

}