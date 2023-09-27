/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.provisioning.client.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Marcos Martins
 */
public class NoSuchCorpProjectException extends NoSuchModelException {

	public NoSuchCorpProjectException() {
	}

	public NoSuchCorpProjectException(String msg) {
		super(msg);
	}

	public NoSuchCorpProjectException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchCorpProjectException(Throwable throwable) {
		super(throwable);
	}

}