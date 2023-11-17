/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jenkins.results.parser.jethr0;

import com.liferay.jenkins.results.parser.JenkinsMaster;

import java.net.URL;

/**
 * @author Michael Hashimoto
 */
public class LocalJethr0Client extends BaseJethr0Client {

	protected LocalJethr0Client(JenkinsMaster jenkinsMaster) {
		super(jenkinsMaster);

		_jmsBrokerURL = getBuildPropertyString("jethr0.jms.broker.url");
		_jmsJethr0ToJRPQueueName = getBuildPropertyString(
			"jethr0.jms.jethr0.jrp.queue.name");
		_jmsJRPToJethr0QueueName = getBuildPropertyString(
			"jethr0.jms.jrp.jethr0.queue.name");
		_jmsUserName = getBuildPropertyString("jethr0.jms.user.name");
		_jmsUserPassword = getBuildPropertyString("jethr0.jms.user.password");
		_jmsGitHubToJethr0QueueName = getBuildPropertyString(
			"jethr0.jms.github.jethr0.queue.name");
		_liferayDXPURL = getBuildPropertyURL("jethr0.liferay.dxp.url");
		_oAuthClientSecret = getBuildPropertyString(
			"jethr0.liferay.oauth.client.secret");
		_oAuthExternalReferenceCode = getBuildPropertyString(
			"jethr0.liferay.oauth.external.reference.code");
		_springBootURL = getBuildPropertyURL("jethr0.spring.boot.url");

		connect();
	}

	@Override
	protected String getJMSBrokerURL() {
		return _jmsBrokerURL;
	}

	@Override
	protected String getJMSGitHubToJethr0QueueName() {
		return _jmsGitHubToJethr0QueueName;
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

	private final String _jmsBrokerURL;
	private final String _jmsGitHubToJethr0QueueName;
	private final String _jmsJethr0ToJRPQueueName;
	private final String _jmsJRPToJethr0QueueName;
	private final String _jmsUserName;
	private final String _jmsUserPassword;
	private final URL _liferayDXPURL;
	private final String _oAuthClientSecret;
	private final String _oAuthExternalReferenceCode;
	private final URL _springBootURL;

}