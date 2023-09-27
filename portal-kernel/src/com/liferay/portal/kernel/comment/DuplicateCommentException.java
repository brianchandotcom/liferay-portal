/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.comment;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author André de Oliveira
 */
public class DuplicateCommentException extends PortalException {

	public DuplicateCommentException() {
	}

	public DuplicateCommentException(String msg) {
		super(msg);
	}

	public DuplicateCommentException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public DuplicateCommentException(Throwable throwable) {
		super(throwable);
	}

}