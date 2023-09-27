/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.deploy.hot;

import com.liferay.portal.kernel.deploy.hot.HotDeployException;

/**
 * @author Zoltán Takács
 */
public class DuplicateCustomJspException extends HotDeployException {

	public DuplicateCustomJspException() {
	}

	public DuplicateCustomJspException(String msg) {
		super(msg);
	}

	public DuplicateCustomJspException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public DuplicateCustomJspException(Throwable throwable) {
		super(throwable);
	}

}