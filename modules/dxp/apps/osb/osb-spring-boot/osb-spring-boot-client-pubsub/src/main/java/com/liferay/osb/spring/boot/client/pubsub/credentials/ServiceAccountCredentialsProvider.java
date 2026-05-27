/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.distributed.messaging.google.pubsub.connector;

import com.google.api.gax.core.CredentialsProvider;

import java.io.IOException;

/**
 * @author Amos Fong
 */
public interface ServiceAccountCredentialsProvider {

	public static final String SCOPE =
		"https://www.googleapis.com/auth/cloud-platform";

	public CredentialsProvider getCredentialsProvider() throws IOException;

}