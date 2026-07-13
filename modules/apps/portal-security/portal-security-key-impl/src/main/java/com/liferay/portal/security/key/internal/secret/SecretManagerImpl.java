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
import com.liferay.portal.security.key.secret.Secret;
import com.liferay.portal.security.key.secret.SecretException;
import com.liferay.portal.security.key.secret.SecretManager;
import com.liferay.portal.security.key.spi.ProviderStatus;
import com.liferay.portal.security.key.spi.profile.KeyManagerProfile;
import com.liferay.portal.security.key.spi.profile.KeyManagerProfileRegistry;
import com.liferay.portal.security.key.spi.secret.SecretProvider;

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
		throws SecretException {

		if (keyReference == null) {
			throw new IllegalArgumentException("Key reference is null");
		}

		try {
			SecretProvider secretProvider = _getSecretProvider(
				companyId,
				_getSecretProviderId(companyId, keyReference.getProviderId()));

			secretProvider.deleteSecret(
				companyId, keyReference.getIdentifier());
		}
		catch (SecretException secretException) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to delete secret", secretException);
			}

			throw secretException;
		}
	}

	@Override
	public List<KeyReference> getKeyReferences(
			long companyId, String providerId)
		throws SecretException {

		if (providerId == null) {
			throw new IllegalArgumentException("Provider ID is null");
		}

		try {
			String resolvedProviderId = _getSecretProviderId(
				companyId, providerId);

			SecretProvider secretProvider = _getSecretProvider(
				companyId, resolvedProviderId);

			List<String> identifiers = secretProvider.getSecretIdentifiers(
				companyId);

			if (identifiers == null) {
				return new ArrayList<>();
			}

			return TransformUtil.transform(
				identifiers,
				identifier -> new KeyReference(
					identifier, resolvedProviderId, KeyReference.Type.SECRET));
		}
		catch (SecretException secretException) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to list secret identifiers", secretException);
			}

			throw secretException;
		}
	}

	@Override
	public List<String> getProviderIds(long companyId) {
		List<String> providerIds = new ArrayList<>();

		ServiceTrackerMap<String, List<SecretProvider>> serviceTrackerMap =
			_serviceTrackerMap;

		if (serviceTrackerMap == null) {
			return providerIds;
		}

		for (String providerId : serviceTrackerMap.keySet()) {
			List<SecretProvider> secretProviders = serviceTrackerMap.getService(
				providerId);

			if (secretProviders == null) {
				continue;
			}

			for (SecretProvider secretProvider : secretProviders) {
				if (secretProvider.isAllowedCompany(companyId)) {
					providerIds.add(providerId);

					break;
				}
			}
		}

		return providerIds;
	}

	@Override
	public Secret getSecret(long companyId, KeyReference keyReference)
		throws SecretException {

		if (keyReference == null) {
			throw new IllegalArgumentException("Key reference is null");
		}

		try {
			SecretProvider secretProvider = _getSecretProvider(
				companyId,
				_getSecretProviderId(companyId, keyReference.getProviderId()));

			return secretProvider.getSecret(
				companyId, keyReference.getIdentifier());
		}
		catch (SecretException secretException) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to get secret", secretException);
			}

			throw secretException;
		}
	}

	@Override
	public KeyReference putSecret(long companyId, Secret secret)
		throws SecretException {

		if (secret == null) {
			throw new IllegalArgumentException("Secret is null");
		}

		try {
			KeyReference keyReference = secret.getKeyReference();

			String providerId = _getSecretProviderId(
				companyId, keyReference.getProviderId());

			SecretProvider secretProvider = _getSecretProvider(
				companyId, providerId);

			secretProvider.putSecret(companyId, secret);

			return new KeyReference(
				keyReference.getIdentifier(), providerId,
				KeyReference.Type.SECRET);
		}
		catch (SecretException secretException) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to put secret", secretException);
			}

			throw secretException;
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, SecretProvider.class, "(secret.provider.id=*)",
			new PropertyServiceReferenceMapper<>("secret.provider.id"));
	}

	@Deactivate
	protected void deactivate() {
		if (_serviceTrackerMap != null) {
			_serviceTrackerMap.close();

			_serviceTrackerMap = null;
		}
	}

	private SecretProvider _getSecretProvider(long companyId, String providerId)
		throws SecretException {

		List<SecretProvider> secretProviders = _serviceTrackerMap.getService(
			providerId);

		if (secretProviders != null) {
			for (SecretProvider secretProvider : secretProviders) {
				if (!secretProvider.isAllowedCompany(companyId)) {
					continue;
				}

				if (secretProvider.getStatus() == ProviderStatus.ERROR) {
					throw new SecretException(
						StringBundler.concat(
							"Secret provider ", providerId,
							" is in an error state for company ID ",
							companyId));
				}

				return secretProvider;
			}
		}

		throw new SecretException(
			StringBundler.concat(
				"No secret provider found for ID ", providerId,
				" and company ID ", companyId));
	}

	private String _getSecretProviderId(long companyId, String providerId)
		throws SecretException {

		if (providerId == null) {
			throw new IllegalArgumentException("Provider ID is null");
		}

		if (!Objects.equals(providerId, StringPool.STAR)) {
			return providerId;
		}

		KeyManagerProfile activeProfile =
			_keyManagerProfileRegistry.getActiveKeyManagerProfile();

		if (activeProfile == null) {
			throw new SecretException(
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
			throw new SecretException(
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

	private ServiceTrackerMap<String, List<SecretProvider>> _serviceTrackerMap;

}