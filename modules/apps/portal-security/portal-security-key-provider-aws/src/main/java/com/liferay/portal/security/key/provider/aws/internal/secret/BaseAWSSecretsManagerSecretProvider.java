/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.key.provider.aws.internal.secret;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.CreateSecretRequest;
import com.amazonaws.services.secretsmanager.model.DeleteSecretRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
import com.amazonaws.services.secretsmanager.model.ListSecretsRequest;
import com.amazonaws.services.secretsmanager.model.ListSecretsResult;
import com.amazonaws.services.secretsmanager.model.PutSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.ResourceNotFoundException;
import com.amazonaws.services.secretsmanager.model.SecretListEntry;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.key.KeyReference;
import com.liferay.portal.security.key.provider.aws.internal.fips.AWSSecretsManagerFIPSValidator;
import com.liferay.portal.security.key.provider.aws.internal.util.AWSARNUtil;
import com.liferay.portal.security.key.provider.aws.internal.util.AWSByteBufferUtil;
import com.liferay.portal.security.key.provider.aws.internal.util.AWSClientManager;
import com.liferay.portal.security.key.secret.Secret;
import com.liferay.portal.security.key.secret.exception.SecretException;
import com.liferay.portal.security.key.spi.ProviderStatus;
import com.liferay.portal.security.key.spi.secret.SecretProvider;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Christopher Kian
 */
public abstract class BaseAWSSecretsManagerSecretProvider
	implements SecretProvider {

	@Override
	public void deleteSecret(long companyId, String secretIdentifier)
		throws SecretException {

		Configuration configuration = _getConfiguration(companyId);

		String secretARN = _resolveSecretARN(
			companyId, configuration, secretIdentifier);

		try {
			configuration._awsClientManager.execute(
				awsSecretsManager -> awsSecretsManager.deleteSecret(
					new DeleteSecretRequest(
					).withRecoveryWindowInDays(
						configuration._recoveryWindowInDays
					).withSecretId(
						secretARN
					)));
		}
		catch (Exception exception) {
			throw new SecretException(
				"Unable to delete AWS secret " + secretARN, exception);
		}
	}

	@Override
	public ProviderStatus getProviderStatus() {
		Configuration configuration = _configuration;

		if ((configuration == null) || !configuration._enabled ||
			Validator.isNull(configuration._region)) {

			return ProviderStatus.DEGRADED;
		}

		return ProviderStatus.OPERATIONAL;
	}

	@Override
	public Secret getSecret(long companyId, String secretIdentifier)
		throws SecretException {

		Configuration configuration = _getConfiguration(companyId);
		byte[] bytes = null;

		String secretARN = _resolveSecretARN(
			companyId, configuration, secretIdentifier);

		try {
			GetSecretValueResult getSecretValueResult =
				configuration._awsClientManager.execute(
					awsSecretsManager -> awsSecretsManager.getSecretValue(
						new GetSecretValueRequest(
						).withSecretId(
							secretARN
						)));

			bytes = _getBytes(getSecretValueResult, secretARN);

			return new Secret(
				bytes,
				new KeyReference(
					secretARN, getProviderId(), KeyReference.Type.SECRET));
		}
		catch (SecretException secretException) {
			throw secretException;
		}
		catch (Exception exception) {
			throw new SecretException(
				"Unable to read AWS secret " + secretARN, exception);
		}
		finally {
			if (bytes != null) {
				Arrays.fill(bytes, (byte)0);
			}
		}
	}

	@Override
	public List<String> getSecretIdentifiers(long companyId)
		throws SecretException {

		Configuration configuration = _getConfiguration(companyId);

		String secretNamePrefix = _resolveSecretNamePrefix(
			companyId, configuration);

		if (Validator.isNull(secretNamePrefix)) {
			return new ArrayList<>();
		}

		try {
			return configuration._awsClientManager.execute(
				awsSecretsManager -> {
					List<String> secretIdentifiers = new ArrayList<>();

					ListSecretsRequest listSecretsRequest =
						new ListSecretsRequest(
						).withMaxResults(
							100
						);

					while (true) {
						ListSecretsResult listSecretsResult =
							awsSecretsManager.listSecrets(listSecretsRequest);

						for (SecretListEntry secretListEntry :
								listSecretsResult.getSecretList()) {

							String name = secretListEntry.getName();

							if ((name != null) &&
								name.startsWith(secretNamePrefix)) {

								secretIdentifiers.add(
									name.substring(secretNamePrefix.length()));
							}
						}

						String nextToken = listSecretsResult.getNextToken();

						if (nextToken == null) {
							break;
						}

						listSecretsRequest.setNextToken(nextToken);
					}

					return secretIdentifiers;
				});
		}
		catch (Exception exception) {
			throw new SecretException(
				"Unable to list AWS secrets under " + secretNamePrefix,
				exception);
		}
	}

	@Override
	public void putSecret(long companyId, Secret secret)
		throws SecretException {

		byte[] bytes = secret.getBytes();

		Configuration configuration = _getConfiguration(companyId);

		KeyReference keyReference = secret.getKeyReference();

		String secretARN = _resolveSecretARN(
			companyId, configuration, keyReference.getIdentifier());

		String secretName = _getSecretName(secretARN);

		try {
			configuration._awsClientManager.execute(
				awsSecretsManager -> {
					try {
						awsSecretsManager.putSecretValue(
							new PutSecretValueRequest(
							).withSecretBinary(
								ByteBuffer.wrap(bytes)
							).withSecretId(
								secretARN
							));
					}
					catch (ResourceNotFoundException
								resourceNotFoundException) {

						if (_log.isDebugEnabled()) {
							_log.debug(
								"Creating AWS secret " + secretName,
								resourceNotFoundException);
						}

						awsSecretsManager.createSecret(
							new CreateSecretRequest(
							).withName(
								secretName
							).withSecretBinary(
								ByteBuffer.wrap(bytes)
							));
					}

					return null;
				});
		}
		catch (Exception exception) {
			throw new SecretException(
				"Unable to write AWS secret " + secretARN, exception);
		}
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		String accountId = GetterUtil.getString(properties.get("awsAccountId"));
		boolean enabled = GetterUtil.getBoolean(properties.get("enabled"));
		boolean fipsEnforced = GetterUtil.getBoolean(
			properties.get("fipsEnforced"));
		long recoveryWindowInDays = GetterUtil.getLong(
			properties.get("recoveryWindowInDays"), 30);
		String region = GetterUtil.getString(properties.get("awsRegion"));
		String secretARNTemplate = GetterUtil.getString(
			properties.get("arnTemplate"));
		boolean useFIPSEndpoint = GetterUtil.getBoolean(
			properties.get("useFIPSEndpoint"));

		Configuration configuration = _configuration;

		AWSClientManager<AWSSecretsManager> awsClientManager = null;

		if (configuration != null) {
			awsClientManager = configuration._awsClientManager;
		}

		if (awsClientManager == null) {
			awsClientManager = new AWSClientManager<>(
				BaseAWSSecretsManagerSecretProvider::_buildAWSSecretsManager,
				"secretsmanager-fips.{region}.amazonaws.com", region,
				useFIPSEndpoint);
		}
		else {
			awsClientManager.updateConfiguration(region, useFIPSEndpoint);
		}

		_configuration = new Configuration(
			accountId, awsClientManager,
			new AWSSecretsManagerFIPSValidator(fipsEnforced, useFIPSEndpoint),
			enabled, recoveryWindowInDays, region, secretARNTemplate);

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"Activated ", getProviderId(), " in region ", region));
		}
	}

	@Deactivate
	protected void deactivate() {
		Configuration configuration = _configuration;

		_configuration = null;

		if (configuration != null) {
			configuration._awsClientManager.close();
		}
	}

	protected abstract String getProviderId();

	protected static class Configuration {

		public Configuration(
			String accountId,
			AWSClientManager<AWSSecretsManager> awsClientManager,
			AWSSecretsManagerFIPSValidator awsSecretsManagerFIPSValidator,
			boolean enabled, long recoveryWindowInDays, String region,
			String secretARNTemplate) {

			_accountId = accountId;
			_awsClientManager = awsClientManager;
			_awsSecretsManagerFIPSValidator = awsSecretsManagerFIPSValidator;
			_enabled = enabled;
			_recoveryWindowInDays = recoveryWindowInDays;
			_region = region;
			_secretARNTemplate = secretARNTemplate;
		}

		private final String _accountId;
		private final AWSClientManager<AWSSecretsManager> _awsClientManager;
		private final AWSSecretsManagerFIPSValidator
			_awsSecretsManagerFIPSValidator;
		private final boolean _enabled;
		private final long _recoveryWindowInDays;
		private final String _region;
		private final String _secretARNTemplate;

	}

	private static AWSSecretsManager _buildAWSSecretsManager(
		AWSCredentialsProvider awsCredentialsProvider,
		AwsClientBuilder.EndpointConfiguration endpointConfiguration,
		String region) {

		AWSSecretsManagerClientBuilder awsSecretsManagerClientBuilder =
			AWSSecretsManagerClientBuilder.standard(
			).withCredentials(
				awsCredentialsProvider
			);

		if (endpointConfiguration != null) {
			awsSecretsManagerClientBuilder.withEndpointConfiguration(
				endpointConfiguration);
		}
		else if (Validator.isNotNull(region)) {
			awsSecretsManagerClientBuilder.withRegion(region);
		}

		return awsSecretsManagerClientBuilder.build();
	}

	private byte[] _getBytes(
			GetSecretValueResult getSecretValueResult, String secretARN)
		throws SecretException {

		ByteBuffer secretBinary = getSecretValueResult.getSecretBinary();

		if (secretBinary != null) {
			return AWSByteBufferUtil.getBytes(secretBinary);
		}

		String secretString = getSecretValueResult.getSecretString();

		if (secretString != null) {
			return secretString.getBytes(StandardCharsets.UTF_8);
		}

		throw new SecretException(
			"AWS secret " + secretARN + " has no binary or string value");
	}

	private Configuration _getConfiguration(long companyId)
		throws SecretException {

		Configuration configuration = _configuration;

		if ((configuration == null) || !configuration._enabled) {
			throw new SecretException(
				"Provider " + getProviderId() + " is not enabled");
		}

		if (!isAllowedCompany(companyId)) {
			throw new SecretException(
				StringBundler.concat(
					"Provider ", getProviderId(),
					" does not handle company ID ", companyId));
		}

		configuration._awsSecretsManagerFIPSValidator.validateEndpoint();

		return configuration;
	}

	private String _getSecretName(String secretARN) {
		int index = secretARN.indexOf(":secret:");

		if (index >= 0) {
			return secretARN.substring(index + ":secret:".length());
		}

		return secretARN;
	}

	private String _resolveSecretARN(
		long companyId, Configuration configuration, String identifier) {

		return AWSARNUtil.resolve(
			configuration._accountId, configuration._secretARNTemplate,
			companyId, identifier, configuration._region);
	}

	private String _resolveSecretNamePrefix(
		long companyId, Configuration configuration) {

		if (Validator.isNull(configuration._secretARNTemplate)) {
			return null;
		}

		String arn = AWSARNUtil.resolve(
			configuration._accountId, configuration._secretARNTemplate,
			companyId, StringPool.BLANK, configuration._region);

		return _getSecretName(arn);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseAWSSecretsManagerSecretProvider.class);

	private volatile Configuration _configuration;

}