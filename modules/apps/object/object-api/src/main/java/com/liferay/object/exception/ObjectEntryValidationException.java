/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.exception;

import com.liferay.object.entry.validation.ValidationError;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * @author Jhosseph Gonzalez
 */
public class ObjectEntryValidationException extends PortalException {

	public ObjectEntryValidationException(
		List<ValidationError> validationErrors) {

		_validationErrors = validationErrors;
	}

	public List<ValidationError> getValidationErrors() {
		return _validationErrors;
	}

	private final List<ValidationError> _validationErrors;

}