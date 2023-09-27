/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.resources.importer.internal.util;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class ImporterException extends PortalException {

	public ImporterException() {
	}

	public ImporterException(String msg) {
		super(msg);
	}

	public ImporterException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public ImporterException(Throwable throwable) {
		super(throwable);
	}

}