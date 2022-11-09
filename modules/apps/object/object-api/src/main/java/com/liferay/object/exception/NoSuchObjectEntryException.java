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