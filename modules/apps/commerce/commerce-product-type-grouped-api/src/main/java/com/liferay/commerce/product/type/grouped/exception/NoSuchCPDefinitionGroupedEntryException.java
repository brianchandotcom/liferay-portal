/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.grouped.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Andrea Di Giorgi
 */
public class NoSuchCPDefinitionGroupedEntryException
	extends NoSuchModelException {

	public NoSuchCPDefinitionGroupedEntryException() {
	}

	public NoSuchCPDefinitionGroupedEntryException(String msg) {
		super(msg);
	}

	public NoSuchCPDefinitionGroupedEntryException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public NoSuchCPDefinitionGroupedEntryException(Throwable throwable) {
		super(throwable);
	}

}