/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.upload;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class LiferayFileItemException extends PortalException {

	public LiferayFileItemException() {
	}

	public LiferayFileItemException(String msg) {
		super(msg);
	}

	public LiferayFileItemException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public LiferayFileItemException(Throwable throwable) {
		super(throwable);
	}

}