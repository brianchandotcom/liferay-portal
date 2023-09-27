/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.opensaml.integration.internal.transport;

import com.liferay.petra.concurrent.DCLSingleton;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.saml.opensaml.integration.internal.transport.configuration.HttpClientFactoryConfiguration;

import java.io.IOException;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.pool.PoolStats;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Michael C. Han
 */
@Component(
	configurationPid = "com.liferay.saml.opensaml.integration.internal.transport.configuration.HttpClientFactoryConfiguration",
	service = HttpClientFactory.class
)
public class HttpClientFactory {

	public HttpClient getHttpClient() {
		return _closeableHttpClientDCLSingleton.getSingleton(
			this::_createCloseableHttpClient);
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_httpClientFactoryConfiguration = ConfigurableUtil.createConfigurable(
			HttpClientFactoryConfiguration.class, properties);
	}

	@Deactivate
	protected void deactivate() {
		_closeableHttpClientDCLSingleton.destroy(
			closeableHttpClient -> {
				try {
					closeableHttpClient.close();
				}
				catch (IOException ioException) {
					if (_log.isDebugEnabled()) {
						_log.debug(ioException);
					}
				}
			});

		if (_log.isDebugEnabled()) {
			_log.debug("Shutting down " + getClass().getName());
		}

		if (_poolingClientConnectionManager == null) {
			return;
		}

		int retry = 0;

		while (retry < 10) {
			PoolStats poolStats =
				_poolingClientConnectionManager.getTotalStats();

			int availableConnections = poolStats.getAvailable();

			if (availableConnections <= 0) {
				break;
			}

			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						getClass().getName(), " is waiting on ",
						availableConnections, " connections"));
			}

			_poolingClientConnectionManager.closeIdleConnections(
				200, TimeUnit.MILLISECONDS);

			try {
				Thread.sleep(500);
			}
			catch (InterruptedException interruptedException) {
				if (_log.isDebugEnabled()) {
					_log.debug(interruptedException);
				}
			}

			retry++;
		}

		_poolingClientConnectionManager.shutdown();

		_poolingClientConnectionManager = null;

		if (_log.isDebugEnabled()) {
			_log.debug(toString() + " was shut down");
		}
	}

	private CloseableHttpClient _createCloseableHttpClient() {
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

		_poolingClientConnectionManager =
			new PoolingHttpClientConnectionManager();

		_poolingClientConnectionManager.setDefaultMaxPerRoute(
			_httpClientFactoryConfiguration.defaultMaxConnectionsPerRoute());

		SocketConfig.Builder socketConfigBuilder = SocketConfig.custom();

		socketConfigBuilder.setSoTimeout(
			_httpClientFactoryConfiguration.soTimeout());

		_poolingClientConnectionManager.setDefaultSocketConfig(
			socketConfigBuilder.build());

		_poolingClientConnectionManager.setMaxTotal(
			_httpClientFactoryConfiguration.maxTotalConnections());

		httpClientBuilder.setConnectionManager(_poolingClientConnectionManager);

		RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();

		requestConfigBuilder.setConnectTimeout(
			_httpClientFactoryConfiguration.connectionManagerTimeout());

		httpClientBuilder.setDefaultRequestConfig(requestConfigBuilder.build());

		httpClientBuilder.setRetryHandler(
			new DefaultHttpRequestRetryHandler(0, false));

		return httpClientBuilder.build();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		HttpClientFactory.class);

	private final DCLSingleton<CloseableHttpClient>
		_closeableHttpClientDCLSingleton = new DCLSingleton<>();
	private HttpClientFactoryConfiguration _httpClientFactoryConfiguration;
	private PoolingHttpClientConnectionManager _poolingClientConnectionManager;

}