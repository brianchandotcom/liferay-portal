/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.upload;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Roberto Díaz
 */
public class UploadRequestSizeException extends PortalException {

	public UploadRequestSizeException() {
	}

	public UploadRequestSizeException(String msg) {
		super(msg);
	}

	public UploadRequestSizeException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public UploadRequestSizeException(Throwable throwable) {
		super(throwable);
	}

}