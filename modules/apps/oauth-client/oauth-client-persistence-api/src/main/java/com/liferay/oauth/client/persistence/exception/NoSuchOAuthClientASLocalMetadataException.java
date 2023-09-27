/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth.client.persistence.exception;

import com.liferay.portal.kernel.exception.NoSuchModelException;

/**
 * @author Brian Wing Shun Chan
 */
public class NoSuchOAuthClientASLocalMetadataException
	extends NoSuchModelException {

	public NoSuchOAuthClientASLocalMetadataException() {
	}

	public NoSuchOAuthClientASLocalMetadataException(String msg) {
		super(msg);
	}

	public NoSuchOAuthClientASLocalMetadataException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public NoSuchOAuthClientASLocalMetadataException(Throwable throwable) {
		super(throwable);
	}

}