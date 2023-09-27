/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.importer.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Jürgen Kappler
 */
public class DropzoneLayoutStructureItemException extends PortalException {

	public DropzoneLayoutStructureItemException() {
	}

	public DropzoneLayoutStructureItemException(String msg) {
		super(msg);
	}

	public DropzoneLayoutStructureItemException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public DropzoneLayoutStructureItemException(Throwable throwable) {
		super(throwable);
	}

}