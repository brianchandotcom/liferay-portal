/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.util.structure.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Eudaldo Alonso
 */
public class NoSuchLayoutStructureItemException extends PortalException {

	public NoSuchLayoutStructureItemException() {
	}

	public NoSuchLayoutStructureItemException(String msg) {
		super(msg);
	}

}