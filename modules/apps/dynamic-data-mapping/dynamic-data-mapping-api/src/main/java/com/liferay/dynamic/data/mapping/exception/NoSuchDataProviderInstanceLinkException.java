/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuchDataProviderInstanceLinkException
	extends NoSuchModelException {

	public NoSuchDataProviderInstanceLinkException() {
	}

	public NoSuchDataProviderInstanceLinkException(String msg) {
		super(msg);
	}

	public NoSuchDataProviderInstanceLinkException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public NoSuchDataProviderInstanceLinkException(Throwable throwable) {
		super(throwable);
	}

}