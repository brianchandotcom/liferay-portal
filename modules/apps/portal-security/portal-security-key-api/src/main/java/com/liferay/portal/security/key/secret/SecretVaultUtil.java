/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.key.secret;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.key.KeyReference;
import com.liferay.portal.security.key.KeyReferenceUtil;
import com.liferay.portal.security.key.secret.exception.SecretException;

import java.util.Objects;

/**
 * @author Pedro Victor Silvestre
 */
public class SecretVaultUtil {

	public static String getIdentifier(String key, String scope) {
		return StringBundler.concat(
			_IDENTIFIER_PREFIX, scope, StringPool.SLASH, key);
	}

	public static String vault(long companyId, String identifier, String value)
		throws SecretException {

		if (!PropsValues.FIPS_ENABLED || Validator.isNull(value)) {
			return value;
		}

		if (KeyReferenceUtil.isKeyReference(value)) {
			_validateKeyReference(identifier, value);

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

	private static String _getKey(String identifier) {
		int index = identifier.lastIndexOf(CharPool.SLASH);

		if (index < 0) {
			return identifier;
		}

		return identifier.substring(index + 1);
	}

	private static void _validateKeyReference(String identifier, String value)
		throws SecretException {

		KeyReference keyReference = KeyReferenceUtil.parseKeyReference(value);

		if (keyReference == null) {
			throw new SecretException("Unable to parse the key reference");
		}

		String valueIdentifier = keyReference.getIdentifier();

		if (!valueIdentifier.startsWith(_IDENTIFIER_PREFIX) ||
			Objects.equals(_getKey(identifier), _getKey(valueIdentifier))) {

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
		new Snapshot<>(SecretVaultUtil.class, SecretManager.class, null, true);

}