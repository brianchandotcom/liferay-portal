/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.audit;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Michael C. Han
 */
public class AuditException extends PortalException {

	public AuditException() {
	}

	public AuditException(String msg) {
		super(msg);
	}

	public AuditException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public AuditException(Throwable throwable) {
		super(throwable);
	}

}