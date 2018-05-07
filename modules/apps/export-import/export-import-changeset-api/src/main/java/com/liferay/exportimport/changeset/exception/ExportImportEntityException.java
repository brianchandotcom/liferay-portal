/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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