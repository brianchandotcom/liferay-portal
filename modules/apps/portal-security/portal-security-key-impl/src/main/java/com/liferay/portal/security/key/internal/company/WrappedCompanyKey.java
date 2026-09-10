/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.key.internal.company;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.encryptor.CompanyKeyUtil;
import com.liferay.portal.kernel.exception.CompanyKeyResolutionException;
import com.liferay.portal.kernel.util.Validator;

import java.util.Arrays;
import java.util.Base64;

/**
 * @author Christopher Kian
 */
public class WrappedCompanyKey {

	public static WrappedCompanyKey parse(
		long companyId, String serializedKey) {

		if (!CompanyKeyUtil.isWrappedKey(serializedKey) ||
			!serializedKey.endsWith(StringPool.CLOSE_CURLY_BRACE)) {

			throw new CompanyKeyResolutionException(
				"Wrapped key is malformed for company " + companyId);
		}

		String body = serializedKey.substring(
			CompanyKeyUtil.WRAPPED_KEY_PREFIX.length(),
			serializedKey.length() - 1);

		int versionIndex = body.indexOf(CharPool.COLON);

		if (versionIndex <= 0) {
			throw new CompanyKeyResolutionException(
				"Wrapped key is malformed for company " + companyId);
		}

		String version = body.substring(0, versionIndex);

		if (!version.equals(CompanyKeyUtil.WRAPPED_KEY_VERSION)) {
			throw new CompanyKeyResolutionException(
				StringBundler.concat(
					"Wrapped key version ", version,
					" is not supported for company ", companyId));
		}

		body = body.substring(versionIndex + 1);

		int colonIndex = body.indexOf(CharPool.COLON);
		int pipeIndex = body.indexOf(CharPool.PIPE);

		if ((colonIndex <= 0) || (pipeIndex <= (colonIndex + 1)) ||
			(pipeIndex >= (body.length() - 1))) {

			throw new CompanyKeyResolutionException(
				"Wrapped key is malformed for company " + companyId);
		}

		byte[] ciphertext;

		try {
			Base64.Decoder decoder = Base64.getDecoder();

			ciphertext = decoder.decode(body.substring(pipeIndex + 1));
		}
		catch (IllegalArgumentException illegalArgumentException) {
			throw new CompanyKeyResolutionException(
				"Wrapped key ciphertext is not valid Base64 for company " +
					companyId,
				illegalArgumentException);
		}

		return new WrappedCompanyKey(
			ciphertext, body.substring(colonIndex + 1, pipeIndex),
			body.substring(0, colonIndex));
	}

	public WrappedCompanyKey(
		byte[] ciphertext, String identifier, String providerId) {

		if (ciphertext == null) {
			throw new IllegalArgumentException("Ciphertext is null");
		}

		if (Validator.isNull(identifier)) {
			throw new IllegalArgumentException("Identifier is null");
		}

		if ((identifier.indexOf(CharPool.CLOSE_CURLY_BRACE) != -1) ||
			(identifier.indexOf(CharPool.PIPE) != -1)) {

			throw new IllegalArgumentException(
				"Identifier contains a reserved character");
		}

		if (Validator.isNull(providerId)) {
			throw new IllegalArgumentException("Provider ID is null");
		}

		if ((providerId.indexOf(CharPool.CLOSE_CURLY_BRACE) != -1) ||
			(providerId.indexOf(CharPool.COLON) != -1) ||
			(providerId.indexOf(CharPool.PIPE) != -1)) {

			throw new IllegalArgumentException(
				"Provider ID contains a reserved character");
		}

		_ciphertext = Arrays.copyOf(ciphertext, ciphertext.length);
		_identifier = identifier;
		_providerId = providerId;
	}

	public byte[] getCiphertext() {
		return Arrays.copyOf(_ciphertext, _ciphertext.length);
	}

	public String getIdentifier() {
		return _identifier;
	}

	public String getProviderId() {
		return _providerId;
	}

	public String serialize() {
		Base64.Encoder encoder = Base64.getEncoder();

		return StringBundler.concat(
			CompanyKeyUtil.WRAPPED_KEY_PREFIX,
			CompanyKeyUtil.WRAPPED_KEY_VERSION, StringPool.COLON, _providerId,
			StringPool.COLON, _identifier, StringPool.PIPE,
			encoder.encodeToString(_ciphertext), StringPool.CLOSE_CURLY_BRACE);
	}

	private final byte[] _ciphertext;
	private final String _identifier;
	private final String _providerId;

}