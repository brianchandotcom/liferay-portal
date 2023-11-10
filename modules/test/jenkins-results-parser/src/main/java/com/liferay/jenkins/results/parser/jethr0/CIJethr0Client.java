/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
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
	public String getJMSBrokerURL() {
		return _jmsBrokerURL;
	}

	protected CIJethr0Client(JenkinsMaster jenkinsMaster) {
		super(jenkinsMaster);

		_jmsBrokerURL = _getSecretString("jms.broker.url");
		_jmsJethr0ToJRPQueueName = _getSecretString(
			"jms.jethr0.jrp.queue.name");
		_jmsJRPToJethr0QueueName = _getSecretString(
			"jms.jrp.jethr0.queue.name");
		_jmsUserName = _getSecretString("jms.user.name");
		_jmsUserPassword = _getSecretString("jms.user.password");
		_jmsWebhookToJethr0QueueName = _getSecretString(
			"jms.webhook.jethr0.queue.name");
		_liferayDXPURL = _getSecretURL("liferay.dxp.url");
		_oAuthExternalReferenceCode = _getSecretString(
			"liferay.oauth.external.reference.code");
		_oAuthClientSecret = _getSecretString("liferay.oauth.client.secret");
		_springBootURL = _getSecretURL("jethr0.spring.boot.url");

		connect();
	}

	@Override
	protected String getJMSJethr0ToJRPQueueName() {
		return _jmsJethr0ToJRPQueueName;
	}

	@Override
	protected String getJMSJRPToJethr0QueueName() {
		return _jmsJRPToJethr0QueueName;
	}

	@Override
	protected String getJMSUserName() {
		return _jmsUserName;
	}

	@Override
	protected String getJMSUserPassword() {
		return _jmsUserPassword;
	}

	@Override
	protected String getJMSWebhookToJethr0QueueName() {
		return _jmsWebhookToJethr0QueueName;
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
	private final String _jmsBrokerURL;
	private final String _jmsJethr0ToJRPQueueName;
	private final String _jmsJRPToJethr0QueueName;
	private final String _jmsUserName;
	private final String _jmsUserPassword;
	private final String _jmsWebhookToJethr0QueueName;
	private final URL _liferayDXPURL;
	private final String _oAuthClientSecret;
	private final String _oAuthExternalReferenceCode;
	private final URL _springBootURL;

}