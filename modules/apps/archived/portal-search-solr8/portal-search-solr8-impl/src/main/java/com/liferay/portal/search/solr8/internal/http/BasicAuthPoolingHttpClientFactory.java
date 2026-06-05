/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.solr8.internal.http;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.solr8.configuration.SolrHttpClientFactoryConfiguration;

import java.util.Map;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;

/**
 * @author László Csontos
 * @author Bruno Farache
 * @author André de Oliveira
 */
@Component(
	configurationPid = "com.liferay.portal.search.solr8.configuration.SolrHttpClientFactoryConfiguration",
	property = "type=BASIC", service = HttpClientFactory.class
)
public class BasicAuthPoolingHttpClientFactory
	extends BasePoolingHttpClientFactory {

	public void setAuthScope(AuthScope authScope) {
		_authScope = authScope;
	}

	public void setPassword(String password) {
		_password = password;
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_solrHttpClientFactoryConfiguration =
			ConfigurableUtil.createConfigurable(
				SolrHttpClientFactoryConfiguration.class, properties);

		setDefaultMaxConnectionsPerRoute(
			_solrHttpClientFactoryConfiguration.
				defaultMaxConnectionsPerRoute());

		setMaxTotalConnections(
			_solrHttpClientFactoryConfiguration.maxTotalConnections());

		String basicAuthPassword =
			_solrHttpClientFactoryConfiguration.basicAuthPassword();

		setPassword(basicAuthPassword);

		String basicAuthUserName =
			_solrHttpClientFactoryConfiguration.basicAuthUserName();

		setUserName(basicAuthUserName);
	}

	@Override
	protected void configure(HttpClientBuilder httpClientBuilder) {
		if (Validator.isBlank(_userName)) {
			return;
		}

		if (_authScope == null) {
			_authScope = AuthScope.ANY;
		}

		if (Validator.isNull(_password)) {
			_password = StringPool.BLANK;
		}

		CredentialsProvider credentialsProvider =
			new BasicCredentialsProvider();

		credentialsProvider.setCredentials(
			_authScope, new UsernamePasswordCredentials(_userName, _password));

		httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
	}

	@Override
	protected PoolingHttpClientConnectionManager
		createPoolingHttpClientConnectionManager() {

		return new PoolingHttpClientConnectionManager();
	}

	@Deactivate
	protected void deactivate() {
		shutdown();
	}

	private AuthScope _authScope;
	private String _password;
	private volatile SolrHttpClientFactoryConfiguration
		_solrHttpClientFactoryConfiguration;
	private String _userName;

}