/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
 */
@Deprecated
public class InvalidDDMStructureFieldNameException extends PortalException {

	public InvalidDDMStructureFieldNameException(String msg, String fieldName) {
		super(msg);

		_fieldName = fieldName;
	}

	public String getFieldName() {
		return _fieldName;
	}

	private final String _fieldName;

}