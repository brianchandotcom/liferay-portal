/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.solr8.internal.http;

import java.security.KeyStore;

/**
 * @author Michael C. Han
 */
public interface KeyStoreLoader {

	public KeyStore load(
			String keyStoreType, String keyStoreLocation,
			char[] keyStorePassword)
		throws Exception;

}