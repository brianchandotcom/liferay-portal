/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Murilo Stodolni
 */
public class ObjectFolderLabelException extends PortalException {

	public ObjectFolderLabelException() {
	}

	public ObjectFolderLabelException(String msg) {
		super(msg);
	}

	public ObjectFolderLabelException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public ObjectFolderLabelException(Throwable throwable) {
		super(throwable);
	}

}