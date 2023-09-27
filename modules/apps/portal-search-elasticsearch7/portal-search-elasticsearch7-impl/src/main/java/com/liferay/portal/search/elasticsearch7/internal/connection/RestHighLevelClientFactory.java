/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.connection;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.search.elasticsearch7.internal.util.ClassLoaderUtil;

import java.io.IOException;
import java.io.InputStream;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.security.KeyStore;

import java.util.concurrent.Future;

import javax.net.ssl.SSLContext;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.concurrent.BasicFuture;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.nio.protocol.HttpAsyncRequestProducer;
import org.apache.http.nio.protocol.HttpAsyncResponseConsumer;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;

import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

/**
 * @author André de Oliveira
 */
public class RestHighLevelClientFactory {

	public static Builder builder() {
		return new Builder();
	}

	public RestHighLevelClient newRestHighLevelClient() {
		RestClientBuilder restClientBuilder = RestClient.builder(
			_getHttpHosts()
		).setDefaultHeaders(
			new Header[] {
				new BasicHeader(
					HttpHeaders.ACCEPT,
					"application/vnd.elasticsearch+json;compatible-with=7"),
				new BasicHeader(
					HttpHeaders.CONTENT_TYPE,
					"application/vnd.elasticsearch+json;compatible-with=7")
			}
		).setHttpClientConfigCallback(
			this::_customizeHttpClient
		).setRequestConfigCallback(
			this::_customizeRequestConfig
		);

		return ClassLoaderUtil.getWithContextClassLoader(
			() -> new RestHighLevelClient(restClientBuilder), getClass());
	}

	public static class Builder {

		public Builder authenticationEnabled(boolean authenticationEnabled) {
			_restHighLevelClientFactory._authenticationEnabled =
				authenticationEnabled;

			return this;
		}

		public RestHighLevelClientFactory build() {
			return new RestHighLevelClientFactory(_restHighLevelClientFactory);
		}

		public Builder httpSSLEnabled(boolean httpSSLEnabled) {
			_restHighLevelClientFactory._httpSSLEnabled = httpSSLEnabled;

			return this;
		}

		public Builder maxConnections(int maxConnections) {
			_restHighLevelClientFactory._maxConnections = maxConnections;

			return this;
		}

		public Builder maxConnectionsPerRoute(int maxConnectionsPerRoute) {
			_restHighLevelClientFactory._maxConnectionsPerRoute =
				maxConnectionsPerRoute;

			return this;
		}

		public Builder networkHostAddresses(String[] networkHostAddresses) {
			_restHighLevelClientFactory._networkHostAddresses =
				networkHostAddresses;

			return this;
		}

		public Builder password(String password) {
			_restHighLevelClientFactory._password = password;

			return this;
		}

		public Builder proxyConfig(ProxyConfig proxyConfig) {
			_restHighLevelClientFactory._proxyConfig = proxyConfig;

			return this;
		}

		public Builder truststorePassword(String truststorePassword) {
			_restHighLevelClientFactory._truststorePassword =
				truststorePassword;

			return this;
		}

		public Builder truststorePath(String truststorePath) {
			_restHighLevelClientFactory._truststorePath = truststorePath;

			return this;
		}

		public Builder truststoreType(String truststoreType) {
			_restHighLevelClientFactory._truststoreType = truststoreType;

			return this;
		}

		public Builder userName(String userName) {
			_restHighLevelClientFactory._userName = userName;

			return this;
		}

		private final RestHighLevelClientFactory _restHighLevelClientFactory =
			new RestHighLevelClientFactory();

	}

	private RestHighLevelClientFactory() {
	}

	private RestHighLevelClientFactory(
		RestHighLevelClientFactory restHighLevelClientFactory) {

		_authenticationEnabled =
			restHighLevelClientFactory._authenticationEnabled;
		_httpSSLEnabled = restHighLevelClientFactory._httpSSLEnabled;
		_maxConnections = restHighLevelClientFactory._maxConnections;
		_maxConnectionsPerRoute =
			restHighLevelClientFactory._maxConnectionsPerRoute;
		_networkHostAddresses =
			restHighLevelClientFactory._networkHostAddresses;
		_password = restHighLevelClientFactory._password;
		_truststorePassword = restHighLevelClientFactory._truststorePassword;
		_truststorePath = restHighLevelClientFactory._truststorePath;
		_truststoreType = restHighLevelClientFactory._truststoreType;
		_proxyConfig = restHighLevelClientFactory._proxyConfig;
		_userName = restHighLevelClientFactory._userName;
	}

	private CredentialsProvider _createCredentialsProvider() {
		CredentialsProvider credentialsProvider =
			new BasicCredentialsProvider();

		if (_proxyConfig.shouldApplyCredentials()) {
			credentialsProvider.setCredentials(
				new AuthScope(_proxyConfig.getHost(), _proxyConfig.getPort()),
				new UsernamePasswordCredentials(
					_proxyConfig.getUserName(), _proxyConfig.getPassword()));
		}

		credentialsProvider.setCredentials(
			AuthScope.ANY,
			new UsernamePasswordCredentials(_userName, _password));

		return credentialsProvider;
	}

	private SSLContext _createSSLContext() {
		try {
			Path path = Paths.get(_truststorePath);

			InputStream inputStream = Files.newInputStream(path);

			KeyStore keyStore = KeyStore.getInstance(_truststoreType);

			keyStore.load(inputStream, _truststorePassword.toCharArray());

			SSLContextBuilder sslContextBuilder = SSLContexts.custom();

			sslContextBuilder.loadKeyMaterial(
				keyStore, _truststorePassword.toCharArray());
			sslContextBuilder.loadTrustMaterial(keyStore, null);

			return sslContextBuilder.build();
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	private HttpAsyncClientBuilder _customizeHttpClient(
		HttpAsyncClientBuilder httpAsyncClientBuilder) {

		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

		if (_authenticationEnabled) {
			httpClientBuilder.setDefaultCredentialsProvider(
				_createCredentialsProvider());
		}

		if (_httpSSLEnabled) {
			httpClientBuilder.setSSLContext(_createSSLContext());
		}

		if ((_proxyConfig != null) && _proxyConfig.shouldApplyConfig()) {
			httpClientBuilder.setProxy(
				new HttpHost(
					_proxyConfig.getHost(), _proxyConfig.getPort(), "http"));
		}

		httpClientBuilder.disableAutomaticRetries();
		httpClientBuilder.disableConnectionState();
		httpClientBuilder.disableContentCompression();
		httpClientBuilder.disableCookieManagement();
		httpClientBuilder.disableDefaultUserAgent();
		httpClientBuilder.disableRedirectHandling();

		httpClientBuilder.setMaxConnPerRoute(_maxConnectionsPerRoute);
		httpClientBuilder.setMaxConnTotal(_maxConnections);

		CloseableHttpClient closeableHttpClient = httpClientBuilder.build();

		CloseableHttpAsyncClient closeableHttpAsyncClient =
			new CloseableHttpAsyncClient() {

				@Override
				public void close() throws IOException {
					closeableHttpClient.close();
				}

				@Override
				public <T> Future<T> execute(
					HttpAsyncRequestProducer httpAsyncRequestProducer,
					HttpAsyncResponseConsumer<T> httpAsyncResponseConsumer,
					HttpContext httpContext, FutureCallback<T> futureCallback) {

					BasicFuture<T> basicFuture = new BasicFuture<>(
						futureCallback);

					try (CloseableHttpResponse closeableHttpResponse =
							closeableHttpClient.execute(
								httpAsyncRequestProducer.getTarget(),
								httpAsyncRequestProducer.generateRequest(),
								httpContext)) {

						HttpEntity httpEntity =
							closeableHttpResponse.getEntity();

						if (httpEntity != null) {
							closeableHttpResponse.setEntity(
								new BufferedHttpEntity(httpEntity));
						}

						basicFuture.completed((T)closeableHttpResponse);
					}
					catch (Exception exception) {
						basicFuture.failed(exception);
					}

					return basicFuture;
				}

				@Override
				public boolean isRunning() {
					return true;
				}

				@Override
				public void start() {
				}

			};

		return new HttpAsyncClientBuilder() {

			@Override
			public CloseableHttpAsyncClient build() {
				return closeableHttpAsyncClient;
			}

		};
	}

	private RequestConfig.Builder _customizeRequestConfig(
		RequestConfig.Builder requestConfigBuilder) {

		return requestConfigBuilder.setSocketTimeout(120000);
	}

	private HttpHost[] _getHttpHosts() {
		return TransformUtil.transform(
			_networkHostAddresses, HttpHost::create, HttpHost.class);
	}

	private boolean _authenticationEnabled;
	private boolean _httpSSLEnabled;
	private int _maxConnections;
	private int _maxConnectionsPerRoute;
	private String[] _networkHostAddresses;
	private String _password;
	private ProxyConfig _proxyConfig;
	private String _truststorePassword;
	private String _truststorePath;
	private String _truststoreType;
	private String _userName;

}