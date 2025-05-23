/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;

/**
 * @author Crescenzo Rega
 */
public interface CommerceUserRoleHelper {

	public void checkCommerceUserRoles(ServiceContext serviceContext)
		throws PortalException;

	public boolean hasCommerceUserPermissions(long companyId)
		throws PortalException;

}