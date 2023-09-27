/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.sso.facebook.connect.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Stian Sigvartsen
 */
public class MustVerifyEmailAddressException extends PortalException {

	public MustVerifyEmailAddressException(long companyId) {
		super(
			String.format(
				"Company %s requires strangers to verify their email address",
				companyId));

		this.companyId = companyId;
	}

	public final long companyId;

}