/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuchSLADefinitionException extends NoSuchModelException {

	public NoSuchSLADefinitionException() {
	}

	public NoSuchSLADefinitionException(String msg) {
		super(msg);
	}

	public NoSuchSLADefinitionException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchSLADefinitionException(Throwable throwable) {
		super(throwable);
	}

}