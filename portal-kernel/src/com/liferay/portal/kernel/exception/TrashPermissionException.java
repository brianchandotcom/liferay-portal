/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.exception;

/**
 * @author Zsolt Berentey
 */
public class TrashPermissionException extends PortalException {

	public static final int DELETE = 1;

	public static final int EMPTY_TRASH = 2;

	public static final int MOVE = 3;

	public static final int RESTORE = 4;

	public static final int RESTORE_OVERWRITE = 5;

	public static final int RESTORE_RENAME = 6;

	public TrashPermissionException(int type) {
		_type = type;
	}

	public int getType() {
		return _type;
	}

	private final int _type;

}