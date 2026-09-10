/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.encryptor;

import java.security.Key;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Christopher Kian
 */
@ProviderType
public interface CompanyKeyResolver {

	public Key deserializeKey(long companyId, String serializedKey);

	public boolean isEnabled(long companyId);

	public String serializeKey(long companyId, Key key);

}