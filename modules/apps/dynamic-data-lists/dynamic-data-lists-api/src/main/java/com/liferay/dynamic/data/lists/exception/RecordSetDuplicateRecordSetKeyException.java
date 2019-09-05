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
 * Thrown when the system identifies a violation of the Record Set Key unique
 * property.
 *
 * @author Brian Wing Shun Chan
 */
public class RecordSetDuplicateRecordSetKeyException extends PortalException {

	public RecordSetDuplicateRecordSetKeyException() {
	}

	public RecordSetDuplicateRecordSetKeyException(String msg) {
		super(msg);
	}

	public RecordSetDuplicateRecordSetKeyException(
		String msg, Throwable cause) {

		super(msg, cause);
	}

	public RecordSetDuplicateRecordSetKeyException(Throwable cause) {
		super(cause);
	}

	public String getRecordSetKey() {
		return _recordSetKey;
	}

	public void setRecordSetKey(String recordSetKey) {
		_recordSetKey = recordSetKey;
	}

	private String _recordSetKey;

}