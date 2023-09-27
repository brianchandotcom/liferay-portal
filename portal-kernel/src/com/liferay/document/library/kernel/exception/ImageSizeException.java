/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.kernel.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class ImageSizeException extends PortalException {

	public ImageSizeException() {
	}

	public ImageSizeException(String msg) {
		super(msg);
	}

	public ImageSizeException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public ImageSizeException(Throwable throwable) {
		super(throwable);
	}

}