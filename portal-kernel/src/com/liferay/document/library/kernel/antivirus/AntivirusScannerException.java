/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.kernel.antivirus;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Michael C. Han
 * @author Hugo Huijser
 * @author Raymond Augé
 */
public class AntivirusScannerException extends PortalException {

	public static final int PROCESS_FAILURE = 1;

	public static final int SIZE_LIMIT_EXCEEDED = 3;

	public static final int VIRUS_DETECTED = 2;

	public AntivirusScannerException(int type) {
		_type = type;
	}

	public AntivirusScannerException(int type, Throwable throwable) {
		super(throwable);

		_type = type;
	}

	public AntivirusScannerException(String msg, int type) {
		super(msg);

		_type = type;
	}

	public AntivirusScannerException(String msg, Throwable throwable) {
		super(msg, throwable);

		_type = 0;
	}

	public AntivirusScannerException(Throwable throwable) {
		super(throwable);

		_type = 0;
	}

	public String getMessageKey() {
		if (_type == PROCESS_FAILURE) {
			return "unable-to-scan-file-for-viruses";
		}
		else if (_type == SIZE_LIMIT_EXCEEDED) {
			return "unable-to-scan-file-for-viruses.-size-limit-exceeded";
		}
		else if (_type == VIRUS_DETECTED) {
			return "a-virus-was-detected-in-the-file";
		}

		return "an-unexpected-error-occurred-while-scanning-for-viruses";
	}

	public int getType() {
		return _type;
	}

	private final int _type;

}