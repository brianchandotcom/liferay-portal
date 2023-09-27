/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.service.persistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public interface OAuth2ScopeGrantFinder {

	public java.util.Collection
		<com.liferay.oauth2.provider.model.OAuth2ScopeGrant> findByC_A_B_A(
			long companyId, String applicationName, String bundleSymbolicName,
			String accessTokenContent);

}