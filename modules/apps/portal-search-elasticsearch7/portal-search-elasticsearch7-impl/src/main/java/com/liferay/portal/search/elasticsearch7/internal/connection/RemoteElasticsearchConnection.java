/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.search.elasticsearch7.internal.connection;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.search.elasticsearch7.configuration.ElasticsearchConfiguration;
import com.liferay.portal.search.elasticsearch7.configuration.XPackSecurityConfiguration;

import java.io.InputStream;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.security.KeyStore;

import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;

import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	configurationPid = {
		"com.liferay.portal.search.elasticsearch7.configuration.ElasticsearchConfiguration",
		"com.liferay.portal.search.elasticsearch7.configuration.XPackSecurityConfiguration"
	},
	immediate = true, property = "operation.mode=REMOTE",
	service = ElasticsearchConnection.class
)
public class RemoteElasticsearchConnection extends BaseElasticsearchConnection {

	@Override
	public OperationMode getOperationMode() {
		return OperationMode.REMOTE;
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		replaceConfigurations(properties);
	}

	protected void configureSecurity(RestClientBuilder restClientBuilder) {
		restClientBuilder.setHttpClientConfigCallback(
			new RestClientBuilder.HttpClientConfigCallback() {

				@Override
				public HttpAsyncClientBuilder customizeHttpClient(
					HttpAsyncClientBuilder httpClientBuilder) {

					httpClientBuilder.setDefaultCredentialsProvider(
						createCredentialsProvider());

					if (xPackSecurityConfiguration.transportSSLEnabled()) {
						httpClientBuilder.setSSLContext(createSSLContext());
					}

					return httpClientBuilder;
				}

			});
	}

	protected CredentialsProvider createCredentialsProvider() {
		String usernamePassword =
			xPackSecurityConfiguration.username() + StringPool.COLON +
				xPackSecurityConfiguration.password();

		CredentialsProvider credentialsProvider =
			new BasicCredentialsProvider();

		credentialsProvider.setCredentials(
			AuthScope.ANY, new UsernamePasswordCredentials(usernamePassword));

		return credentialsProvider;
	}

	protected RestHighLevelClient createRestHighLevelClient() {
		String[] networkHostAddresses =
			elasticsearchConfiguration.networkHostAddresses();

		HttpHost[] httpHosts = new HttpHost[networkHostAddresses.length];

		for (int i = 0; i < networkHostAddresses.length; i++) {
			httpHosts[i] = HttpHost.create(networkHostAddresses[i]);
		}

		RestClientBuilder restClientBuilder = RestClient.builder(httpHosts);

		if (xPackSecurityConfiguration.requiresAuthentication()) {
			configureSecurity(restClientBuilder);
		}

		return new RestHighLevelClient(restClientBuilder);
	}

	protected SSLContext createSSLContext() {

		// This method will be updated in
		// https://issues.liferay.com/browse/LPS-102296
		// [Technical Task] Security and Encryption over https connection

		// These are the existing properties for the transport client in
		// XPackSecuritySettingsContributor. We need to determine which ones
		// still apply to the REST client

		//"xpack.security.transport.ssl.enabled", "true"

		//"xpack.security.transport.ssl.verification_mode",
		//XPackSecurityConfiguration.transportSSLVerificationMode()

		String certificateFormat =
			xPackSecurityConfiguration.certificateFormat();

		if (certificateFormat.equals("PKCS#12")) {
			//"xpack.ssl.certificate",
			//xPackSecurityConfiguration.sslCertificatePath()

			//"xpack.ssl.certificate_authorities",
			//xPackSecurityConfiguration.sslCertificateAuthoritiesPaths()

			//"xpack.ssl.key", xPackSecurityConfiguration.sslKeyPath()
		}
		else {
			//"xpack.ssl.keystore.password",
			//xPackSecurityConfiguration.sslKeystorePassword()

			//"xpack.ssl.keystore.path",
			//xPackSecurityConfiguration.sslKeystorePath()

			//"xpack.ssl.truststore.password",
			//xPackSecurityConfiguration.sslTruststorePassword()

			//"xpack.ssl.truststore.path",
			//xPackSecurityConfiguration.sslTruststorePath()
		}

		String keyStoreFilePath = xPackSecurityConfiguration.sslKeystorePath();

		Path keyStorePath = Paths.get(keyStoreFilePath);

		SSLContext sslContext;

		try (InputStream is = Files.newInputStream(keyStorePath)) {
			KeyStore truststore = KeyStore.getInstance("jks");

			String keyStorePass =
				xPackSecurityConfiguration.sslKeystorePassword();

			truststore.load(is, keyStorePass.toCharArray());

			SSLContextBuilder sslBuilder = SSLContexts.custom();

			sslBuilder.loadTrustMaterial(truststore, null);

			sslContext = sslBuilder.build();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}

		return sslContext;
	}

	@Deactivate
	protected void deactivate(Map<String, Object> properties) {
		close();
	}

	@Modified
	protected synchronized void modified(Map<String, Object> properties) {
		replaceConfigurations(properties);

		if (isConnected()) {
			close();
		}

		if (!isConnected() &&
			(elasticsearchConfiguration.operationMode() ==
				com.liferay.portal.search.elasticsearch7.configuration.
					OperationMode.REMOTE)) {

			connect();
		}
	}

	protected void replaceConfigurations(Map<String, Object> properties) {
		elasticsearchConfiguration = ConfigurableUtil.createConfigurable(
			ElasticsearchConfiguration.class, properties);
		xPackSecurityConfiguration = ConfigurableUtil.createConfigurable(
			XPackSecurityConfiguration.class, properties);
	}

	@Reference
	protected Props props;

	protected volatile XPackSecurityConfiguration xPackSecurityConfiguration;

}