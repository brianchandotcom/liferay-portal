/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.deploy.auto;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Ivica Cardic
 * @author Brian Wing Shun Chan
 */
public class AutoDeployException extends PortalException {

	public AutoDeployException() {
	}

	public AutoDeployException(String msg) {
		super(msg);
	}

	public AutoDeployException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public AutoDeployException(Throwable throwable) {
		super(throwable);
	}

}