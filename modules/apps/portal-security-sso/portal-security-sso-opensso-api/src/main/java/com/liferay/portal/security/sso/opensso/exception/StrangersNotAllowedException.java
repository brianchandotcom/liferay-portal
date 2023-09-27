/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.sso.opensso.exception;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Stian Sigvartsen
 */
public class StrangersNotAllowedException extends PortalException {

	public StrangersNotAllowedException(long companyId) {
		super(String.format("Company %s does not allow strangers", companyId));

		this.companyId = companyId;
	}

	public final long companyId;

	private static final long serialVersionUID = 1L;

}