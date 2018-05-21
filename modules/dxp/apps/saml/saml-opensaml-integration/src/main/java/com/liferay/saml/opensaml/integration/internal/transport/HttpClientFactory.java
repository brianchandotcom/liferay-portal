/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.saml.opensaml.integration.internal.transport;

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

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Michael C. Han
 */
@Component(
	configurationPid = "com.liferay.saml.opensaml.integration.internal.transport.configuration.HttpClientFactoryConfiguration",
	immediate = true, service = HttpClientFactory.class
)
public class HttpClientFactory {

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

		_poolingClientConnectionManager =
			new PoolingHttpClientConnectionManager();

		HttpClientFactoryConfiguration httpClientFactoryConfiguration =
			ConfigurableUtil.createConfigurable(
				HttpClientFactoryConfiguration.class, properties);

		_poolingClientConnectionManager.setDefaultMaxPerRoute(
			httpClientFactoryConfiguration.defaultMaxConnectionsPerRoute());

		SocketConfig.Builder socketConfigBuilder = SocketConfig.custom();

		socketConfigBuilder.setSoTimeout(
			httpClientFactoryConfiguration.soTimeout());

		_poolingClientConnectionManager.setDefaultSocketConfig(
			socketConfigBuilder.build());

		_poolingClientConnectionManager.setMaxTotal(
			httpClientFactoryConfiguration.maxTotalConnections());

		httpClientBuilder.setConnectionManager(_poolingClientConnectionManager);

		RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();

		requestConfigBuilder.setConnectTimeout(
			httpClientFactoryConfiguration.connectionManagerTimeout());

		httpClientBuilder.setDefaultRequestConfig(requestConfigBuilder.build());

		httpClientBuilder.setRetryHandler(
			new DefaultHttpRequestRetryHandler(0, false));

		_closeableHttpClient = httpClientBuilder.build();

		_httpClientServiceRegistration = bundleContext.registerService(
			HttpClient.class, _closeableHttpClient, null);
	}

	@Deactivate
	protected void deactivate() {
		if (_closeableHttpClient != null) {
			try {
				_closeableHttpClient.close();
			}
			catch (IOException ioe) {
				if (_log.isDebugEnabled()) {
					_log.debug(ioe, ioe);
				}
			}
		}

		if (_httpClientServiceRegistration != null) {
			_httpClientServiceRegistration.unregister();

			_httpClientServiceRegistration = null;
		}

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
						String.valueOf(availableConnections), " connections"));
			}

			_poolingClientConnectionManager.closeIdleConnections(
				200, TimeUnit.MILLISECONDS);

			try {
				Thread.sleep(500);
			}
			catch (InterruptedException ie) {
			}

			retry++;
		}

		_poolingClientConnectionManager.shutdown();

		_poolingClientConnectionManager = null;

		if (_log.isDebugEnabled()) {
			_log.debug(toString() + " was shut down");
		}
	}

	@Modified
	protected void modified(
		BundleContext bundleContext, Map<String, Object> properties) {

		deactivate();

		activate(bundleContext, properties);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		HttpClientFactory.class);

	private CloseableHttpClient _closeableHttpClient;
	private ServiceRegistration<HttpClient> _httpClientServiceRegistration;
	private PoolingHttpClientConnectionManager _poolingClientConnectionManager;

}