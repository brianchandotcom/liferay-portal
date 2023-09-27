/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public RecordSetDuplicateRecordSetKeyException(Throwable throwable) {
		super(throwable);
	}

	public String getRecordSetKey() {
		return _recordSetKey;
	}

	public void setRecordSetKey(String recordSetKey) {
		_recordSetKey = recordSetKey;
	}

	private String _recordSetKey;

}