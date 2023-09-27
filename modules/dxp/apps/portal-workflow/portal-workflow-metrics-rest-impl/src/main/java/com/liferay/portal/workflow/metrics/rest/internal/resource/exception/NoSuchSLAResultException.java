/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.rest.internal.resource.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Feliphe Marinho
 */
public class NoSuchSLAResultException extends NoSuchModelException {

	public NoSuchSLAResultException() {
	}

	public NoSuchSLAResultException(String msg) {
		super(msg);
	}

	public NoSuchSLAResultException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchSLAResultException(Throwable throwable) {
		super(throwable);
	}

}