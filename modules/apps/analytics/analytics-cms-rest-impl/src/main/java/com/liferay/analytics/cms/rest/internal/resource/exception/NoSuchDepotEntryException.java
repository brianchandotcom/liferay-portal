/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.cms.rest.internal.resource.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Leslie Wong
 */
public class NoSuchDepotEntryException extends NoSuchModelException {

	public NoSuchDepotEntryException() {
	}

	public NoSuchDepotEntryException(String msg) {
		super(msg);
	}

	public NoSuchDepotEntryException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public NoSuchDepotEntryException(Throwable throwable) {
		super(throwable);
	}

}