/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.key.spi.secret;

import com.liferay.portal.security.key.secret.SecretManagerException;
import com.liferay.portal.security.key.secret.SecureSecret;
import com.liferay.portal.security.key.spi.SecurityModuleProvider;

import java.util.List;

/**
 * @author Tomas Polesovsky
 * @author Christopher Kian
 */
public interface SecretVaultProvider extends SecurityModuleProvider {

	public void deleteSecret(long companyId, String identifier)
		throws SecretManagerException;

	public SecureSecret getSecret(long companyId, String identifier)
		throws SecretManagerException;

	public List<String> getSecretIdentifiers(long companyId)
		throws SecretManagerException;

	public void putSecret(long companyId, SecureSecret secureSecret)
		throws SecretManagerException;

}