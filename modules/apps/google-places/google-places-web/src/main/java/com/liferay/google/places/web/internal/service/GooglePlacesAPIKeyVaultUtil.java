/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.google.places.web.internal.service;

import com.liferay.google.places.constants.GooglePlacesWebKeys;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.key.KeyReference;
import com.liferay.portal.security.key.KeyReferenceUtil;
import com.liferay.portal.security.key.secret.Secret;
import com.liferay.portal.security.key.secret.SecretManager;
import com.liferay.portal.security.key.secret.exception.SecretException;

/**
 * @author Pedro Victor Silvestre
 */
public class GooglePlacesAPIKeyVaultUtil {

	public static String getCompanyIdentifier(long companyId) {
		return StringBundler.concat(
			_IDENTIFIER_PREFIX, "company/", companyId, StringPool.SLASH,
			GooglePlacesWebKeys.GOOGLE_PLACES_API_KEY);
	}

	public static String getGroupIdentifier(long groupId) {
		return StringBundler.concat(
			_IDENTIFIER_PREFIX, "group/", groupId, StringPool.SLASH,
			GooglePlacesWebKeys.GOOGLE_PLACES_API_KEY);
	}

	public static String vault(long companyId, String identifier, String value)
		throws SecretException {

		if (!PropsValues.FIPS_ENABLED || Validator.isNull(value)) {
			return value;
		}

		if (KeyReferenceUtil.isKeyReference(value)) {
			_checkKeyReference(identifier, value);

			return value;
		}

		SecretManager secretManager = _secretManagerSnapshot.get();

		if (secretManager == null) {
			throw new IllegalStateException("Secret manager is unavailable");
		}

		try (Secret secret = new Secret(
				new KeyReference(
					identifier, StringPool.STAR, KeyReference.Type.SECRET),
				value)) {

			return KeyReferenceUtil.toKeyReferenceString(
				secretManager.putSecret(companyId, secret));
		}
	}

	private static void _checkKeyReference(String identifier, String value)
		throws SecretException {

		KeyReference keyReference = KeyReferenceUtil.parseKeyReference(value);

		if (keyReference == null) {
			throw new SecretException("Unable to parse the key reference");
		}

		String valueIdentifier = keyReference.getIdentifier();

		if (!valueIdentifier.startsWith(_IDENTIFIER_PREFIX) ||
			valueIdentifier.equals(identifier)) {

			return;
		}

		throw new SecretException(
			StringBundler.concat(
				"Identifier \"", identifier,
				"\" cannot reference a value belonging to \"", valueIdentifier,
				"\""));
	}

	private static final String _IDENTIFIER_PREFIX = "preference/";

	private static final Snapshot<SecretManager> _secretManagerSnapshot =
		new Snapshot<>(
			GooglePlacesAPIKeyVaultUtil.class, SecretManager.class, null, true);

}