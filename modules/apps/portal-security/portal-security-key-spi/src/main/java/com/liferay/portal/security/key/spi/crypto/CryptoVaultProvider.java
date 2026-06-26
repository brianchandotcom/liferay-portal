/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.key.spi.crypto;

import com.liferay.portal.security.key.crypto.CryptoKey;
import com.liferay.portal.security.key.crypto.CryptoManagerException;
import com.liferay.portal.security.key.crypto.CryptoServiceResult;
import com.liferay.portal.security.key.spi.SecurityModuleProvider;

import java.security.Key;

import java.util.List;

/**
 * @author Tomas Polesovsky
 * @author Christopher Kian
 */
public interface CryptoVaultProvider extends SecurityModuleProvider {

	public CryptoServiceResult<byte[]> decrypt(
			byte[] ciphertext, long companyId, String identifier)
		throws CryptoManagerException;

	public void deleteKey(long companyId, String identifier)
		throws CryptoManagerException;

	public CryptoServiceResult<byte[]> encrypt(
			long companyId, String identifier, byte[] plaintext)
		throws CryptoManagerException;

	public CryptoServiceResult<Key> exportKey(long companyId, String identifier)
		throws CryptoManagerException;

	public CryptoServiceResult<String> generateAsymmetricKeyPair(
			String algorithm, long companyId, String identifier)
		throws CryptoManagerException;

	public CryptoServiceResult<String> generateSecretKey(
			String algorithm, long companyId, String identifier)
		throws CryptoManagerException;

	public CryptoKey getCryptoKey(long companyId, String identifier)
		throws CryptoManagerException;

	public List<String> getKeyIdentifiers(long companyId)
		throws CryptoManagerException;

	public CryptoServiceResult<String> importSecretKey(
			String algorithm, long companyId, String identifier,
			byte[] rawKeyMaterial)
		throws CryptoManagerException;

	public CryptoServiceResult<String> unwrap(
			long companyId, String identifier, String masterIdentifier,
			String wrappedKeyAlgorithm, byte[] wrappedKeyBytes,
			int wrappedKeyCipherType)
		throws CryptoManagerException;

	public CryptoServiceResult<byte[]> wrap(
			long companyId, String keyToWrapIdentifier, String masterIdentifier)
		throws CryptoManagerException;

}