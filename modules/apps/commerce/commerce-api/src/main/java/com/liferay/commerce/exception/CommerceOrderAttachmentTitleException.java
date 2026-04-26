/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceOrderAttachmentTitleException extends PortalException {

	public CommerceOrderAttachmentTitleException() {
	}

	public CommerceOrderAttachmentTitleException(String msg) {
		super(msg);
	}

	public CommerceOrderAttachmentTitleException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public CommerceOrderAttachmentTitleException(Throwable throwable) {
		super(throwable);
	}

}