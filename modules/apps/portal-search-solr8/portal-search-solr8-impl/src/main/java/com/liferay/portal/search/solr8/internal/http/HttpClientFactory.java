/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.solr8.internal.http;

import org.apache.http.client.HttpClient;

/**
 * @author André de Oliveira
 * @author László Csontos
 */
public interface HttpClientFactory {

	public HttpClient createInstance() throws Exception;

	public void shutdown();

}