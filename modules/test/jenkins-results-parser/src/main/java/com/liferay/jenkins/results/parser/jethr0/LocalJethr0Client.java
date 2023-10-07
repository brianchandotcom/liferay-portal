/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
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

		_activeMQBrokerURL = getBuildPropertyString(
			"jethr0.activemq.broker.url");
		_activeMQQueueName = getBuildPropertyString(
			"jethr0.activemq.queue.name");
		_activeMQURL = getBuildPropertyURL("jethr0.activemq.url");
		_activeMQUserName = getBuildPropertyString("jethr0.activemq.user.name");
		_activeMQUserPassword = getBuildPropertyString(
			"jethr0.activemq.user.password");
		_liferayDXPURL = getBuildPropertyURL("jethr0.liferay.dxp.url");
		_oAuthClientSecret = getBuildPropertyString(
			"jethr0.liferay.oauth.client.secret");
		_oAuthExternalReferenceCode = getBuildPropertyString(
			"jethr0.liferay.oauth.external.reference.code");
		_springBootURL = getBuildPropertyURL("jethr0.spring.boot.url");
	}

	@Override
	protected String getActiveMQBrokerURL() {
		return _activeMQBrokerURL;
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