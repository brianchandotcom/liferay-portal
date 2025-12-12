/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.web.internal.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Ankita Malik
 */
public class FileEntryWorkflowInProgressException extends PortalException {

	public FileEntryWorkflowInProgressException() {
	}

	public FileEntryWorkflowInProgressException(String msg) {
		super(msg);
	}

}