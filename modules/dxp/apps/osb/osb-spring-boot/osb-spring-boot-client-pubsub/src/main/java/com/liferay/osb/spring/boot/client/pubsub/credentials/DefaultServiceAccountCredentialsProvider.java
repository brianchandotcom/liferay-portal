/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.spring.boot.client.pubsub.credentials;

import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;

import java.io.IOException;

import java.util.Arrays;

/**
 * @author Amos Fong
 * @author Kyle Bischof
 */
public class DefaultServiceAccountCredentialsProvider
	implements ServiceAccountCredentialsProvider {

	@Override
	public CredentialsProvider getCredentialsProvider() throws IOException {
		GoogleCredentials googleCredentials =
			GoogleCredentials.getApplicationDefault();

		return FixedCredentialsProvider.create(
			googleCredentials.createScoped(Arrays.asList(SCOPE)));
	}

}