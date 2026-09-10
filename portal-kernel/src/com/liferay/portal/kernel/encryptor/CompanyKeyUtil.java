/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.encryptor;

import com.liferay.portal.kernel.exception.CompanyKeyResolutionException;
import com.liferay.portal.kernel.module.service.Snapshot;

import java.security.Key;

/**
 * @author Christopher Kian
 */
public class CompanyKeyUtil {

	public static final String WRAPPED_KEY_PREFIX = "${wrappedKey:";

	public static final String WRAPPED_KEY_VERSION = "v1";

	public static Key deserializeKey(long companyId, String serializedKey) {
		if (!isWrappedKey(serializedKey)) {
			return EncryptorUtil.deserializeKey(serializedKey);
		}

		CompanyKeyResolver companyKeyResolver =
			_companyKeyResolverSnapshot.get();

		if (companyKeyResolver == null) {
			throw new CompanyKeyResolutionException(
				"Key resolver is not available for company " + companyId);
		}

		return companyKeyResolver.deserializeKey(companyId, serializedKey);
	}

	public static boolean isWrappedKey(String serializedKey) {
		if ((serializedKey != null) &&
			serializedKey.startsWith(WRAPPED_KEY_PREFIX)) {

			return true;
		}

		return false;
	}

	public static String serializeKey(long companyId, Key key) {
		CompanyKeyResolver companyKeyResolver =
			_companyKeyResolverSnapshot.get();

		if ((companyKeyResolver == null) ||
			!companyKeyResolver.isEnabled(companyId)) {

			return EncryptorUtil.serializeKey(key);
		}

		return companyKeyResolver.serializeKey(companyId, key);
	}

	private static final Snapshot<CompanyKeyResolver>
		_companyKeyResolverSnapshot = new Snapshot<>(
			CompanyKeyUtil.class, CompanyKeyResolver.class, null, true);

}