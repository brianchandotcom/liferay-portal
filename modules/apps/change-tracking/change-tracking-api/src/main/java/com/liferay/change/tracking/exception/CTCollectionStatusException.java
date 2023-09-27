/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class CTCollectionStatusException extends PortalException {

	public CTCollectionStatusException() {
	}

	public CTCollectionStatusException(String msg) {
		super(msg);
	}

	public CTCollectionStatusException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public CTCollectionStatusException(Throwable throwable) {
		super(throwable);
	}

}