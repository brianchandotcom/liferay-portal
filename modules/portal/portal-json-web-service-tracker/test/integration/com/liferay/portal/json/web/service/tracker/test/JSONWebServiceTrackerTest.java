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

package com.liferay.portal.json.web.service.tracker.test;

import com.liferay.arquillian.deploymentscenario.annotations.BndFile;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;
import java.io.InputStream;

import java.net.URISyntaxException;
import java.net.URL;

import java.util.Random;

import org.apache.http.HttpEntity;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Carlos Sierra Andrés
 */
@BndFile("test/integration/bnd.bnd")
@RunAsClient
@RunWith(Arquillian.class)
public class JSONWebServiceTrackerTest {

	@Test
	public void testWebServiceContextAppearsInTheSummary() throws IOException {
		URL url = new URL(_url, "/api/jsonws");

		String body = StringUtil.read(url.openStream());

		Assert.assertTrue(body.contains("test"));
	}

	@Test
	public void testWebServiceInvocation()
		throws IOException, URISyntaxException {

		HttpClientBuilder httpClientBuilder = HttpClients.custom();

		CredentialsProvider credentialsProvider =
			new BasicCredentialsProvider();

		Random random = new Random();

		int a = random.nextInt(50);
		int b = random.nextInt(50);

		final URL url = new URL(
			_url, "/api/jsonws/test.testweb/sum/a/" + a + "/b/" + b);

		credentialsProvider.setCredentials(
			new AuthScope(url.getHost(), url.getPort()),
			new UsernamePasswordCredentials("test@liferay.com", "test"));

		httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);

		CloseableHttpClient closeableHttpClient = httpClientBuilder.build();

		HttpGet httpGet = new HttpGet(url.toURI());

		CloseableHttpResponse closeableHttpResponse =
			closeableHttpClient.execute(httpGet);

		HttpEntity httpEntity = closeableHttpResponse.getEntity();

		InputStream inputStream = httpEntity.getContent();

		String string = StringUtil.read(inputStream);

		Assert.assertEquals(a + b, Integer.parseInt(string));
	}

	@ArquillianResource
	private final URL _url;

}