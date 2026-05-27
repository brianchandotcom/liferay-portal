/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.distributed.messaging.google.pubsub.connector;

import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;

import java.io.IOException;

import java.util.Arrays;

/**
 * @author Amos Fong
 */
public class DefaultServiceAccountCredentialsProvider
	implements ServiceAccountCredentialsProvider {

	public CredentialsProvider getCredentialsProvider() throws IOException {
		GoogleCredentials googleCredentials =
			GoogleCredentials.getApplicationDefault();

		return FixedCredentialsProvider.create(
			googleCredentials.createScoped(Arrays.asList(SCOPE)));
	}

}