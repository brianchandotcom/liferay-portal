/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.grouped.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Andrea Di Giorgi
 */
public class DuplicateCPDefinitionGroupedEntryException
	extends PortalException {

	public DuplicateCPDefinitionGroupedEntryException() {
	}

	public DuplicateCPDefinitionGroupedEntryException(String msg) {
		super(msg);
	}

	public DuplicateCPDefinitionGroupedEntryException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public DuplicateCPDefinitionGroupedEntryException(Throwable throwable) {
		super(throwable);
	}

}