/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */
package com.liferay.object.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Marco Leo
 */
public class ObjectFolderItemObjectDefinitionIdException extends PortalException {

	public ObjectFolderItemObjectDefinitionIdException() {
	}

	public ObjectFolderItemObjectDefinitionIdException(String msg) {
		super(msg);
	}

	public ObjectFolderItemObjectDefinitionIdException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public ObjectFolderItemObjectDefinitionIdException(Throwable throwable) {
		super(throwable);
	}

}