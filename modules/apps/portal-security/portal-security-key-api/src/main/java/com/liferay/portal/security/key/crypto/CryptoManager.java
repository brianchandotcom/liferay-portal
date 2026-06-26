/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.key.crypto;

import com.liferay.portal.security.key.KeyReference;

import java.security.Key;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Tomas Polesovsky
 * @author Christopher Kian
 */
@ProviderType
public interface CryptoManager {

	public CryptoServiceResult<byte[]> decrypt(
			byte[] ciphertext, long companyId, KeyReference keyReference)
		throws CryptoManagerException;

	public void deleteKey(long companyId, KeyReference keyReference)
		throws CryptoManagerException;

	public CryptoServiceResult<byte[]> encrypt(
			long companyId, KeyReference keyReference, byte[] plaintext)
		throws CryptoManagerException;

	/**
	 * Exports the plaintext key material referenced by the given key reference.
	 * This is the only operation that removes a key from the vault in plaintext
	 * form, so treat it as a deliberate, audited action: prefer referencing keys
	 * by {@link KeyReference} and unwrapping into the vault, and call this method
	 * only when a caller genuinely requires the raw key outside the module.
	 *
	 * @return the plaintext key material paired with its service indicator
	 */
	public CryptoServiceResult<Key> exportKey(
			long companyId, KeyReference keyReference)
		throws CryptoManagerException;

	public CryptoServiceResult<KeyReference> generateAsymmetricKeyPair(
			String algorithm, long companyId, String identifier,
			String providerId)
		throws CryptoManagerException;

	public CryptoServiceResult<KeyReference> generateSecretKey(
			String algorithm, long companyId, String identifier,
			String providerId)
		throws CryptoManagerException;

	public CryptoKey getCryptoKey(long companyId, KeyReference keyReference)
		throws CryptoManagerException;

	public List<KeyReference> getKeyReferences(
			long companyId, String providerId)
		throws CryptoManagerException;

	public List<String> getProviderIds(long companyId)
		throws CryptoManagerException;

	/**
	 * Imports the given raw key material into the vault. This method zeroes the
	 * passed array before returning, so the caller must not reuse or rely on its
	 * contents afterward.
	 *
	 * @return the imported key reference paired with its service indicator
	 */
	public CryptoServiceResult<KeyReference> importSecretKey(
			String algorithm, long companyId, String identifier,
			String providerId, byte[] rawKeyMaterial)
		throws CryptoManagerException;

	public CryptoServiceResult<KeyReference> unwrap(
			long companyId, String identifier, KeyReference masterKeyReference,
			String wrappedKeyAlgorithm, byte[] wrappedKeyBytes,
			int wrappedKeyCipherType)
		throws CryptoManagerException;

	public CryptoServiceResult<byte[]> wrap(
			long companyId, KeyReference keyToWrapReference,
			KeyReference masterKeyReference)
		throws CryptoManagerException;

}