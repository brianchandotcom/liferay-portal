/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Jorge Ferrer
 */
public class NoSuchFormVariationException extends PortalException {

	public NoSuchFormVariationException(
		String formVariationKey, Throwable throwable) {

		super(
			"Unable to get form variation with key " + formVariationKey,
			throwable);

		_formVariationKey = formVariationKey;
	}

	public String getFormVariationKey() {
		return _formVariationKey;
	}

	private final String _formVariationKey;

}