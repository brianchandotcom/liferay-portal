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

package com.liferay.dynamic.data.mapping.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class StructureDuplicateStructureKeyException extends PortalException {

	public StructureDuplicateStructureKeyException() {
	}

	public StructureDuplicateStructureKeyException(String msg) {
		super(msg);
	}

	public StructureDuplicateStructureKeyException(
		String msg, Throwable cause) {

		super(msg, cause);
	}

	public StructureDuplicateStructureKeyException(Throwable cause) {
		super(cause);
	}

	public String getStructureKey() {
		return _structureKey;
	}

	public void setStructureKey(String structureKey) {
		_structureKey = structureKey;
	}

	private String _structureKey;

}