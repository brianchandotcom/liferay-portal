/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.changeset.exception;

/**
 * @author Akos Thurzo
 */
public class ExportImportEntityException extends Exception {

	public static final int TYPE_GROUP_NOT_STAGED = 1;

	public static final int TYPE_INVALID_COMMAND = 2;

	public static final int TYPE_NO_DATA_FOUND = 3;

	public static final int TYPE_PORTLET_NOT_STAGED = 4;

	public ExportImportEntityException(int type) {
		_type = type;
	}

	public int getType() {
		return _type;
	}

	private final int _type;

}