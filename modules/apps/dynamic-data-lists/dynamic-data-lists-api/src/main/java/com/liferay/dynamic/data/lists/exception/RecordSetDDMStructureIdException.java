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

package com.liferay.dynamic.data.lists.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * Thrown when the system is unable to find the required DDM Structure set for a
 * Record Set.
 *
 * @author Brian Wing Shun Chan
 */
public class RecordSetDDMStructureIdException extends PortalException {

	public RecordSetDDMStructureIdException() {
	}

	public RecordSetDDMStructureIdException(String msg) {
		super(msg);
	}

	public RecordSetDDMStructureIdException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public RecordSetDDMStructureIdException(Throwable cause) {
		super(cause);
	}

}