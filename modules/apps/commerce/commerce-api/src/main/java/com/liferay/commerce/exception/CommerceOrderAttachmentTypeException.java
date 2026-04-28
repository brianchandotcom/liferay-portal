/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Stefano Motta
 */
public class CommerceOrderAttachmentTypeException extends PortalException {

	public CommerceOrderAttachmentTypeException() {
	}

	public CommerceOrderAttachmentTypeException(String msg) {
		super(msg);
	}

	public CommerceOrderAttachmentTypeException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public CommerceOrderAttachmentTypeException(Throwable throwable) {
		super(throwable);
	}

}