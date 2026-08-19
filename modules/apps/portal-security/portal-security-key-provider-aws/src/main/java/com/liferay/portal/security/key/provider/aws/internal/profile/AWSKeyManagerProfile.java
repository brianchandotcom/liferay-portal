/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.key.provider.aws.internal.profile;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.kms.AWSKMS;
import com.amazonaws.services.kms.AWSKMSClientBuilder;
import com.amazonaws.services.kms.model.DescribeKeyRequest;
import com.amazonaws.services.kms.model.DescribeKeyResult;
import com.amazonaws.services.kms.model.KeyMetadata;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.key.provider.aws.internal.configuration.AWSKMSCompanyCryptoProviderConfiguration;
import com.liferay.portal.security.key.provider.aws.internal.configuration.AWSKMSSystemCryptoProviderConfiguration;
import com.liferay.portal.security.key.provider.aws.internal.configuration.AWSSecretsManagerCompanySecretProviderConfiguration;
import com.liferay.portal.security.key.provider.aws.internal.configuration.AWSSecretsManagerSystemSecretProviderConfiguration;
import com.liferay.portal.security.key.provider.aws.internal.fips.AWSKMSFIPSValidator;
import com.liferay.portal.security.key.provider.aws.internal.profile.configuration.AWSKeyManagerProfileConfiguration;
import com.liferay.portal.security.key.provider.aws.internal.util.AWSARNUtil;
import com.liferay.portal.security.key.provider.aws.internal.util.AWSAccountUtil;
import com.liferay.portal.security.key.provider.aws.internal.util.AWSClientManager;
import com.liferay.portal.security.key.provider.aws.internal.util.AWSRegionUtil;
import com.liferay.portal.security.key.spi.profile.KeyManagerProfile;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Christopher Kian
 */
@Component(
	configurationPid = "com.liferay.portal.security.key.provider.aws.internal.profile.configuration.AWSKeyManagerProfileConfiguration",
	property = "keymanager.profile.id=aws", service = KeyManagerProfile.class
)
public class AWSKeyManagerProfile implements KeyManagerProfile {

	@Override
	public String getCompanyDEKProviderId() {
		return "aws-company-crypto";
	}

	@Override
	public String getCompanyKEKProviderId() {
		return "aws-company-crypto";
	}

	@Override
	public String getCompanySecretProviderId() {
		if (_isDBVaultLayer()) {
			return "db-company-secret";
		}

		return "aws-company-secret";
	}

	@Override
	public String getProfileId() {
		return "aws";
	}

	@Override
	public String getSystemDEKProviderId() {
		return "aws-system-crypto";
	}

	@Override
	public String getSystemKEKProviderId() {
		return "aws-system-crypto";
	}

	@Override
	public String getSystemSecretProviderId() {
		if (_isDBVaultLayer()) {
			return "db-system-secret";
		}

		return "aws-system-secret";
	}

	@Override
	public void initialize() throws Exception {
		validateCredentials();

		String region = _awsKeyManagerProfileConfiguration.awsRegion();

		if (Validator.isNull(region)) {
			region = AWSRegionUtil.getRegion();
		}

		if (Validator.isNull(region)) {
			throw new IllegalStateException(
				"Unable to resolve AWS region; configure \"aws-region\" or " +
					"export \"AWS_REGION\"");
		}

		String accountId = _awsKeyManagerProfileConfiguration.awsAccountId();

		if (Validator.isNull(accountId)) {
			accountId = AWSAccountUtil.getAccountId();
		}

		boolean strictMode = false;

		if (_awsKeyManagerProfileConfiguration.strictMode() ||
			PropsValues.FIPS_ENABLED) {

			strictMode = true;

			if (_log.isInfoEnabled()) {
				_log.info(
					StringBundler.concat(
						"AWS profile: strict mode enabled; FIPS endpoint ",
						"kms-fips.", region,
						".amazonaws.com, enforcing FIPS approved key origins"));
			}
		}
		else if (_log.isWarnEnabled()) {
			_log.warn(
				"AWS profile: standard mode; providers permit software " +
					"backed CMKs, which is not strict mode compliant");
		}

		_verifyKMSKey(accountId, region, strictMode);

		_updateProviderConfiguration(
			accountId, true,
			AWSKMSCompanyCryptoProviderConfiguration.class.getName(), region,
			strictMode);
		_updateProviderConfiguration(
			accountId, true,
			AWSKMSSystemCryptoProviderConfiguration.class.getName(), region,
			strictMode);

		boolean secretsEnabled = !_isDBVaultLayer();

		_updateProviderConfiguration(
			accountId, secretsEnabled,
			AWSSecretsManagerCompanySecretProviderConfiguration.class.getName(),
			region, strictMode);
		_updateProviderConfiguration(
			accountId, secretsEnabled,
			AWSSecretsManagerSystemSecretProviderConfiguration.class.getName(),
			region, strictMode);

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"AWS profile initialized {region=", region, ", accountId=",
					accountId, ", strictMode=", strictMode, "}"));
		}
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_awsKeyManagerProfileConfiguration =
			ConfigurableUtil.createConfigurable(
				AWSKeyManagerProfileConfiguration.class, properties);
	}

	protected String getKeyOrigin(
			String keyARN, String region, boolean useFIPSEndpoint)
		throws Exception {

		AWSClientManager<AWSKMS> awsClientManager = new AWSClientManager<>(
			AWSKeyManagerProfile::_buildAWSKMS,
			"kms-fips.{region}.amazonaws.com", region, useFIPSEndpoint);

		try {
			return awsClientManager.execute(
				awsKMS -> {
					DescribeKeyResult describeKeyResult = awsKMS.describeKey(
						new DescribeKeyRequest(
						).withKeyId(
							keyARN
						));

					KeyMetadata keyMetadata =
						describeKeyResult.getKeyMetadata();

					return keyMetadata.getOrigin();
				});
		}
		finally {
			awsClientManager.close();
		}
	}

	protected void validateCredentials() throws Exception {
		try {
			DefaultAWSCredentialsProviderChain
				defaultAWSCredentialsProviderChain =
					DefaultAWSCredentialsProviderChain.getInstance();

			defaultAWSCredentialsProviderChain.getCredentials();
		}
		catch (Exception exception) {
			throw new IllegalStateException(
				"AWS credentials are not available via the default " +
					"credential provider chain (env, EC2 instance profile, " +
						"IRSA)",
				exception);
		}
	}

	private static AWSKMS _buildAWSKMS(
		AWSCredentialsProvider awsCredentialsProvider,
		AwsClientBuilder.EndpointConfiguration endpointConfiguration,
		String region) {

		AWSKMSClientBuilder awsKMSClientBuilder = AWSKMSClientBuilder.standard(
		).withCredentials(
			awsCredentialsProvider
		);

		if (endpointConfiguration != null) {
			awsKMSClientBuilder.withEndpointConfiguration(
				endpointConfiguration);
		}
		else if (Validator.isNotNull(region)) {
			awsKMSClientBuilder.withRegion(region);
		}

		return awsKMSClientBuilder.build();
	}

	private boolean _isDBVaultLayer() {
		return Objects.equals(
			_awsKeyManagerProfileConfiguration.vaultLayer(), "db");
	}

	private void _updateProviderConfiguration(
			String accountId, boolean enabled, String pid, String region,
			boolean strictMode)
		throws Exception {

		Configuration configuration = _configurationAdmin.getConfiguration(
			pid, "?");

		Dictionary<String, Object> properties = configuration.getProperties();

		if (properties == null) {
			properties = new Hashtable<>();
		}

		if (accountId != null) {
			properties.put("awsAccountId", accountId);
		}

		properties.put("awsRegion", region);
		properties.put("enabled", enabled);
		properties.put("fipsEnforced", strictMode);
		properties.put("useFIPSEndpoint", strictMode);

		configuration.update(properties);
	}

	private void _verifyKMSKey(
			String accountId, String region, boolean strictMode)
		throws Exception {

		String keyARNTemplate =
			_awsKeyManagerProfileConfiguration.keyARNTemplate();

		if (Validator.isNull(keyARNTemplate)) {
			if (strictMode) {
				throw new IllegalStateException(
					"Strict mode requires a CMK to verify; configure " +
						"\"key-arn-template\"");
			}

			return;
		}

		String keyARN = AWSARNUtil.resolve(
			accountId, keyARNTemplate, CompanyConstants.SYSTEM,
			StringPool.BLANK, region);
		String keyOrigin = null;

		try {
			keyOrigin = getKeyOrigin(keyARN, region, strictMode);
		}
		catch (Exception exception) {
			if (strictMode) {
				throw new IllegalStateException(
					"Unable to verify AWS KMS key " + keyARN, exception);
			}

			if (_log.isWarnEnabled()) {
				_log.warn("Unable to verify AWS KMS key " + keyARN, exception);
			}

			return;
		}

		AWSKMSFIPSValidator awsKMSFIPSValidator = new AWSKMSFIPSValidator(
			"AES_256_GCM", strictMode);

		awsKMSFIPSValidator.validateKeyOrigin(keyOrigin);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AWSKeyManagerProfile.class);

	private volatile AWSKeyManagerProfileConfiguration
		_awsKeyManagerProfileConfiguration;

	@Reference
	private ConfigurationAdmin _configurationAdmin;

}