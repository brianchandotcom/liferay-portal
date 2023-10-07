/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.scim.user.manager;

import com.liferay.portal.kernel.exception.PortalException;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Rafael Praxedes
 */
@ProviderType
public interface ScimUserManager {

	public ScimUser addOrUpdateScimUser(ScimUser scimUser)
		throws PortalException;

	public ScimUser fetchScimUser(long companyId, long userId);

}