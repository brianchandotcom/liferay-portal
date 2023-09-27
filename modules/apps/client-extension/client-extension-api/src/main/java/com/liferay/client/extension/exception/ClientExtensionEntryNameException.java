/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class ClientExtensionEntryNameException extends PortalException {

	public ClientExtensionEntryNameException() {
	}

	public ClientExtensionEntryNameException(String msg) {
		super(msg);
	}

	public ClientExtensionEntryNameException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public ClientExtensionEntryNameException(Throwable throwable) {
		super(throwable);
	}

}