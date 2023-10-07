/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.jethr0;

import com.liferay.jenkins.results.parser.JenkinsMaster;
import com.liferay.jenkins.results.parser.SecretsUtil;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Michael Hashimoto
 */
public class CIJethr0Client extends BaseJethr0Client {

	@Override
	public String getActiveMQBrokerURL() {
		return _activeMQBrokerURL;
	}

	protected CIJethr0Client(JenkinsMaster jenkinsMaster) {
		super(jenkinsMaster);

		_activeMQBrokerURL = _getSecretString("activemq.broker.url");
		_activeMQURL = _getSecretURL("activemq.url");
		_activeMQQueueName = _getSecretString("activemq.queue.name");
		_activeMQUserName = _getSecretString("activemq.user.name");
		_activeMQUserPassword = _getSecretString("activemq.user.password");
		_liferayDXPURL = _getSecretURL("liferay.dxp.url");
		_oAuthExternalReferenceCode = _getSecretString(
			"liferay.oauth.external.reference.code");
		_oAuthClientSecret = _getSecretString("liferay.oauth.client.secret");
		_springBootURL = _getSecretURL("jethr0.spring.boot.url");
	}

	@Override
	protected String getActiveMQQueueName() {
		return _activeMQQueueName;
	}

	@Override
	protected URL getActiveMQURL() {
		return _activeMQURL;
	}

	@Override
	protected String getActiveMQUserName() {
		return _activeMQUserName;
	}

	@Override
	protected String getActiveMQUserPassword() {
		return _activeMQUserPassword;
	}

	@Override
	protected URL getLiferayDXPURL() {
		return _liferayDXPURL;
	}

	@Override
	protected String getOAuthClientSecret() {
		return _oAuthClientSecret;
	}

	@Override
	protected String getOAuthExternalReferenceCode() {
		return _oAuthExternalReferenceCode;
	}

	@Override
	protected URL getSpringBootURL() {
		return _springBootURL;
	}

	private String _get1PasswordItemTitle() {
		if (_1PasswordItemTitle != null) {
			return _1PasswordItemTitle;
		}

		_1PasswordItemTitle = getBuildPropertyString(
			"jethr0.1password.item.title");

		return _1PasswordItemTitle;
	}

	private String _get1PasswordVaultName() {
		if (_1PasswordVaultName != null) {
			return _1PasswordVaultName;
		}

		_1PasswordVaultName = getBuildPropertyString(
			"jethr0.1password.vault.name");

		return _1PasswordVaultName;
	}

	private String _getSecretString(String fieldLabel) {
		return SecretsUtil.getSecret(
			_get1PasswordVaultName(), _get1PasswordItemTitle(), fieldLabel);
	}

	private URL _getSecretURL(String fieldLabel) {
		try {
			return new URL(
				SecretsUtil.getSecret(
					_get1PasswordVaultName(), _get1PasswordItemTitle(),
					fieldLabel));
		}
		catch (MalformedURLException malformedURLException) {
			throw new RuntimeException(malformedURLException);
		}
	}

	private String _1PasswordItemTitle;
	private String _1PasswordVaultName;
	private final String _activeMQBrokerURL;
	private final String _activeMQQueueName;
	private final URL _activeMQURL;
	private final String _activeMQUserName;
	private final String _activeMQUserPassword;
	private final URL _liferayDXPURL;
	private final String _oAuthClientSecret;
	private final String _oAuthExternalReferenceCode;
	private final URL _springBootURL;

}