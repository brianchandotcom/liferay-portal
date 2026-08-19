/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.key.provider.aws.internal.secret;

import com.amazonaws.services.secretsmanager.AWSSecretsManager;

import com.liferay.portal.security.key.provider.aws.internal.fips.AWSSecretsManagerFIPSValidator;
import com.liferay.portal.security.key.provider.aws.internal.util.AWSClientManager;

/**
 * @author Christopher Kian
 */
public class AWSSecretsManagerSecretProviderContext {

	public AWSSecretsManagerSecretProviderContext(
		String accountId, AWSClientManager<AWSSecretsManager> awsClientManager,
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

	public String getAccountId() {
		return _accountId;
	}

	public AWSClientManager<AWSSecretsManager> getAWSClientManager() {
		return _awsClientManager;
	}

	public AWSSecretsManagerFIPSValidator getAWSSecretsManagerFIPSValidator() {
		return _awsSecretsManagerFIPSValidator;
	}

	public long getRecoveryWindowInDays() {
		return _recoveryWindowInDays;
	}

	public String getRegion() {
		return _region;
	}

	public String getSecretARNTemplate() {
		return _secretARNTemplate;
	}

	public boolean isEnabled() {
		return _enabled;
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