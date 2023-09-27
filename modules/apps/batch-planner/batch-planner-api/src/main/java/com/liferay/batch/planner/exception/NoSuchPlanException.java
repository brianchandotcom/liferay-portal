/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.planner.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Igor Beslic
 */
public class NoSuchPlanException extends NoSuchModelException {

	public NoSuchPlanException() {
	}

	public NoSuchPlanException(String msg) {
		super(msg);
	}

	public NoSuchPlanException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchPlanException(Throwable throwable) {
		super(throwable);
	}

}