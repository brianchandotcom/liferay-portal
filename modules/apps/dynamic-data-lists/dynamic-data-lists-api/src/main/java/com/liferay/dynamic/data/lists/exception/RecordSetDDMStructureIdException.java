/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public RecordSetDDMStructureIdException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public RecordSetDDMStructureIdException(Throwable throwable) {
		super(throwable);
	}

}