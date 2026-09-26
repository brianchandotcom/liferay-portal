/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.encryptor;

import com.liferay.portal.kernel.exception.CompanyKeyException;
import com.liferay.portal.kernel.module.service.Snapshot;

import java.security.Key;

/**
 * @author Christopher Kian
 */
public class CompanyKeyResolverUtil {

	public static boolean isWrappedKey(String keyString) {
		if ((keyString != null) && keyString.startsWith("${wrappedKey:")) {
			return true;
		}

		return false;
	}

	public static Key unwrapKey(long companyId, String keyString) {
		if (!isWrappedKey(keyString)) {
			return EncryptorUtil.deserializeKey(keyString);
		}

		CompanyKeyResolver companyKeyResolver =
			_companyKeyResolverSnapshot.get();

		if (companyKeyResolver == null) {
			throw new CompanyKeyException(
				"Key resolver is not available for company " + companyId);
		}

		return companyKeyResolver.unwrapKey(companyId, keyString);
	}

	public static String wrapKey(long companyId, Key key) {
		CompanyKeyResolver companyKeyResolver =
			_companyKeyResolverSnapshot.get();

		if ((companyKeyResolver == null) ||
			!companyKeyResolver.isEnabled(companyId)) {

			return EncryptorUtil.serializeKey(key);
		}

		return companyKeyResolver.wrapKey(companyId, key);
	}

	private static final Snapshot<CompanyKeyResolver>
		_companyKeyResolverSnapshot = new Snapshot<>(
			CompanyKeyResolverUtil.class, CompanyKeyResolver.class, null, true);

}