/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.monitoring;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public class MonitoringException extends PortalException {

	public MonitoringException() {
	}

	public MonitoringException(String msg) {
		super(msg);
	}

	public MonitoringException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public MonitoringException(Throwable throwable) {
		super(throwable);
	}

}