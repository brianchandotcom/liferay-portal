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

package com.liferay.portal;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lar.MissingReferences;

/**
 * @author Julio Camarero
 */
public class MissingReferenceException extends PortalException {

	public MissingReferenceException() {
		_missingReferences = null;
	}

	public MissingReferenceException(MissingReferences missingReferences) {
		_missingReferences = missingReferences;
	}

	public MissingReferenceException(String msg) {
		super(msg);

		_missingReferences = null;
	}

	public MissingReferenceException(String msg, Throwable cause) {
		super(msg, cause);

		_missingReferences = null;
	}

	public MissingReferenceException(Throwable cause) {
		super(cause);

		_missingReferences = null;
	}

	public MissingReferences getMissingReferences() {
		return _missingReferences;
	}

	private final MissingReferences _missingReferences;

}