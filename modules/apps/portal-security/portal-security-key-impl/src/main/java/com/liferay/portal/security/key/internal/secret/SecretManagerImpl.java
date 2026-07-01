/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.key.internal.secret;

import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.security.key.KeyReference;
import com.liferay.portal.security.key.secret.SecretManager;
import com.liferay.portal.security.key.secret.SecretManagerException;
import com.liferay.portal.security.key.secret.SecureSecret;
import com.liferay.portal.security.key.spi.ModuleStatus;
import com.liferay.portal.security.key.spi.profile.KeyManagerProfile;
import com.liferay.portal.security.key.spi.profile.KeyManagerProfileRegistry;
import com.liferay.portal.security.key.spi.secret.SecretVaultProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tomas Polesovsky
 * @author Christopher Kian
 */
@Component(service = SecretManager.class)
public class SecretManagerImpl implements SecretManager {

	@Override
	public void deleteSecret(long companyId, KeyReference keyReference)
		throws SecretManagerException {

		if (keyReference == null) {
			throw new IllegalArgumentException("Key reference is null");
		}

		try {
			SecretVaultProvider secretVaultProvider = _getSecretVaultProvider(
				companyId,
				_getSecretVaultProviderId(
					companyId, keyReference.getProviderId()));

			secretVaultProvider.deleteSecret(
				companyId, keyReference.getIdentifier());
		}
		catch (SecretManagerException secretManagerException) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to delete secret", secretManagerException);
			}

			throw secretManagerException;
		}
	}

	@Override
	public List<KeyReference> getKeyReferences(
			long companyId, String providerId)
		throws SecretManagerException {

		if (providerId == null) {
			throw new IllegalArgumentException("Provider ID is null");
		}

		try {
			String resolvedProviderId = _getSecretVaultProviderId(
				companyId, providerId);

			SecretVaultProvider secretVaultProvider = _getSecretVaultProvider(
				companyId, resolvedProviderId);

			List<String> identifiers = secretVaultProvider.getSecretIdentifiers(
				companyId);

			if (identifiers == null) {
				return new ArrayList<>();
			}

			return TransformUtil.transform(
				identifiers,
				identifier -> new KeyReference(
					identifier, resolvedProviderId, KeyReference.Type.SECRET));
		}
		catch (SecretManagerException secretManagerException) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to list secret identifiers",
					secretManagerException);
			}

			throw secretManagerException;
		}
	}

	@Override
	public List<String> getProviderIds(long companyId) {
		List<String> providerIds = new ArrayList<>();

		ServiceTrackerMap<String, List<SecretVaultProvider>> serviceTrackerMap =
			_serviceTrackerMap;

		if (serviceTrackerMap == null) {
			return providerIds;
		}

		for (String providerId : serviceTrackerMap.keySet()) {
			List<SecretVaultProvider> secretVaultProviders =
				serviceTrackerMap.getService(providerId);

			if (secretVaultProviders == null) {
				continue;
			}

			for (SecretVaultProvider secretVaultProvider :
					secretVaultProviders) {

				if (secretVaultProvider.isAllowedCompany(companyId)) {
					providerIds.add(providerId);

					break;
				}
			}
		}

		return providerIds;
	}

	@Override
	public SecureSecret getSecret(long companyId, KeyReference keyReference)
		throws SecretManagerException {

		if (keyReference == null) {
			throw new IllegalArgumentException("Key reference is null");
		}

		try {
			SecretVaultProvider secretVaultProvider = _getSecretVaultProvider(
				companyId,
				_getSecretVaultProviderId(
					companyId, keyReference.getProviderId()));

			return secretVaultProvider.getSecret(
				companyId, keyReference.getIdentifier());
		}
		catch (SecretManagerException secretManagerException) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to get secret", secretManagerException);
			}

			throw secretManagerException;
		}
	}

	@Override
	public KeyReference putSecret(long companyId, SecureSecret secureSecret)
		throws SecretManagerException {

		if (secureSecret == null) {
			throw new IllegalArgumentException("Secure secret is null");
		}

		try {
			KeyReference keyReference = secureSecret.getKeyReference();

			String providerId = _getSecretVaultProviderId(
				companyId, keyReference.getProviderId());

			SecretVaultProvider secretVaultProvider = _getSecretVaultProvider(
				companyId, providerId);

			secretVaultProvider.putSecret(companyId, secureSecret);

			return new KeyReference(
				keyReference.getIdentifier(), providerId,
				KeyReference.Type.SECRET);
		}
		catch (SecretManagerException secretManagerException) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to put secret", secretManagerException);
			}

			throw secretManagerException;
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, SecretVaultProvider.class,
			"(keymanager.provider.id=*)",
			new PropertyServiceReferenceMapper<>("keymanager.provider.id"));
	}

	@Deactivate
	protected void deactivate() {
		if (_serviceTrackerMap != null) {
			_serviceTrackerMap.close();

			_serviceTrackerMap = null;
		}
	}

	private SecretVaultProvider _getSecretVaultProvider(
			long companyId, String providerId)
		throws SecretManagerException {

		List<SecretVaultProvider> secretVaultProviders =
			_serviceTrackerMap.getService(providerId);

		if (secretVaultProviders != null) {
			for (SecretVaultProvider secretVaultProvider :
					secretVaultProviders) {

				if (!secretVaultProvider.isAllowedCompany(companyId)) {
					continue;
				}

				if (secretVaultProvider.getModuleStatus() ==
						ModuleStatus.ERROR) {

					throw new SecretManagerException(
						StringBundler.concat(
							"Secret vault provider ", providerId,
							" is in an error state for company ID ",
							companyId));
				}

				return secretVaultProvider;
			}
		}

		throw new SecretManagerException(
			StringBundler.concat(
				"No secret vault provider found for ID ", providerId,
				" and company ID ", companyId));
	}

	private String _getSecretVaultProviderId(long companyId, String providerId)
		throws SecretManagerException {

		if (providerId == null) {
			throw new IllegalArgumentException("Provider ID is null");
		}

		if (!Objects.equals(providerId, StringPool.STAR)) {
			return providerId;
		}

		KeyManagerProfile activeProfile =
			_keyManagerProfileRegistry.getActiveKeyManagerProfile();

		if (activeProfile == null) {
			throw new SecretManagerException(
				StringBundler.concat(
					"No active KeyManagerProfile found to resolve the ",
					"provider wildcard for company ID ", companyId));
		}

		if (companyId == CompanyConstants.SYSTEM) {
			providerId = activeProfile.getSystemSecretProviderId();
		}
		else {
			providerId = activeProfile.getCompanySecretProviderId();
		}

		if (providerId == null) {
			throw new SecretManagerException(
				StringBundler.concat(
					"The active KeyManagerProfile does not configure a ",
					(companyId == CompanyConstants.SYSTEM) ? "system" :
						"company",
					" secret provider ID"));
		}

		return providerId;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SecretManagerImpl.class);

	@Reference
	private KeyManagerProfileRegistry _keyManagerProfileRegistry;

	private ServiceTrackerMap<String, List<SecretVaultProvider>>
		_serviceTrackerMap;

}