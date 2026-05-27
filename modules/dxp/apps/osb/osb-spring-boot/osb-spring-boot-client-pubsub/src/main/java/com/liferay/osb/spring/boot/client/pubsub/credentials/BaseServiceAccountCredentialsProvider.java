/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.distributed.messaging.google.pubsub.connector;

import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.Credentials;
import com.google.auth.oauth2.ServiceAccountCredentials;

import com.liferay.portal.kernel.util.GetterUtil;

import java.io.IOException;

import java.util.Arrays;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Amos Fong
 */
public abstract class BaseServiceAccountCredentialsProvider
	implements ServiceAccountCredentialsProvider {

	@Override
	public CredentialsProvider getCredentialsProvider() throws IOException {
		Credentials credentials = ServiceAccountCredentials.fromPkcs8(
			_clientId, _clientEmailAddress, _privateKeyPkcs8, _privateKeyId,
			Arrays.asList(SCOPE));

		return FixedCredentialsProvider.create(credentials);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_clientEmailAddress = GetterUtil.getString(
			properties.get("clientEmailAddress"));
		_clientId = GetterUtil.getString(properties.get("clientId"));
		_privateKeyId = GetterUtil.getString(properties.get("privateKeyId"));
		_privateKeyPkcs8 = GetterUtil.getString(
			properties.get("privateKeyPkcs8"));
	}

	private String _clientEmailAddress;
	private String _clientId;
	private String _privateKeyId;
	private String _privateKeyPkcs8;

}