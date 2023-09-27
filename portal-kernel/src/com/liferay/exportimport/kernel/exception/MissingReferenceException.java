/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.kernel.exception;

import com.liferay.exportimport.kernel.lar.MissingReferences;
import com.liferay.portal.kernel.exception.PortalException;

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

	public MissingReferenceException(String msg, Throwable throwable) {
		super(msg, throwable);

		_missingReferences = null;
	}

	public MissingReferenceException(Throwable throwable) {
		super(throwable);

		_missingReferences = null;
	}

	public MissingReferences getMissingReferences() {
		return _missingReferences;
	}

	private final MissingReferences _missingReferences;

}