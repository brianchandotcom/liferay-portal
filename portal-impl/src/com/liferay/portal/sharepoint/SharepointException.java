/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.sharepoint;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Bruno Farache
 */
public class SharepointException extends PortalException {

	public SharepointException() {
	}

	public SharepointException(String msg) {
		super(msg);
	}

	public SharepointException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public SharepointException(Throwable throwable) {
		super(throwable);
	}

}