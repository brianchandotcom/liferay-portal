/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.persistence.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Mika Koivisto
 */
public class DuplicateSamlSpIdpConnectionSamlIdpEntityIdException
	extends PortalException {

	public DuplicateSamlSpIdpConnectionSamlIdpEntityIdException() {
	}

	public DuplicateSamlSpIdpConnectionSamlIdpEntityIdException(String msg) {
		super(msg);
	}

	public DuplicateSamlSpIdpConnectionSamlIdpEntityIdException(
		String msg, Throwable throwable) {

		super(msg, throwable);
	}

	public DuplicateSamlSpIdpConnectionSamlIdpEntityIdException(
		Throwable throwable) {

		super(throwable);
	}

}