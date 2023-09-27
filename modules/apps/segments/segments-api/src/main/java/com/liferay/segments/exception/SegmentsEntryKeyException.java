/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Eduardo García
 */
public class SegmentsEntryKeyException extends PortalException {

	public SegmentsEntryKeyException() {
	}

	public SegmentsEntryKeyException(String msg) {
		super(msg);
	}

	public SegmentsEntryKeyException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public SegmentsEntryKeyException(Throwable throwable) {
		super(throwable);
	}

}