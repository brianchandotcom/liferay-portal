/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.punchout.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Jaclyn Ong
 */
@ProviderType
public interface PunchOutAccountRoleHelper {

	public boolean hasPunchOutRole(long userId, long commerceAccountId)
		throws PortalException;

}