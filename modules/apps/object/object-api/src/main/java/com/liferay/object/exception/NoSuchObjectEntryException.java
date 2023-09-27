/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Marco Leo
 */
public class NoSuchObjectEntryException extends NoSuchModelException {

	public NoSuchObjectEntryException() {
	}

	public NoSuchObjectEntryException(String msg) {
		super(msg);
	}

	public NoSuchObjectEntryException(
		String externalReferenceCode, long objectDefinitionId) {

		_externalReferenceCode = externalReferenceCode;
		_objectDefinitionId = objectDefinitionId;
	}

	public NoSuchObjectEntryException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchObjectEntryException(Throwable throwable) {
		super(throwable);
	}

	public String getExternalReferenceCode() {
		return _externalReferenceCode;
	}

	public long getObjectDefinitionId() {
		return _objectDefinitionId;
	}

	private String _externalReferenceCode;
	private long _objectDefinitionId;

}