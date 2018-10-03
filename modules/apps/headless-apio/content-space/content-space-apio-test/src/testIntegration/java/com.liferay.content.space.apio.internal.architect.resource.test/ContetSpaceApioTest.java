package com.liferay.content.space.apio.internal.architect.resource.test;

import com.jayway.jsonpath.JsonPath;
import com.liferay.petra.json.web.service.client.JSONWebServiceClient;
import com.liferay.petra.json.web.service.client.JSONWebServiceInvocationException;
import com.liferay.petra.json.web.service.client.JSONWebServiceTransportException;
import com.liferay.petra.json.web.service.client.internal.JSONWebServiceClientImpl;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Map;

/**
 * @author Cristina González
 */
@RunAsClient
@RunWith(Arquillian.class)
public class ContetSpaceApioTest {

	@Before
	public void setUp() throws MalformedURLException {
		_jsonWebServiceClient = new JSONWebServiceClientImpl();

		_rootEndpointURL = new URL(_url, "/o/api");

		_jsonWebServiceClient.setHostName(_rootEndpointURL.getHost());
		_jsonWebServiceClient.setHostPort(_rootEndpointURL.getPort());

		_jsonWebServiceClient.setLogin("test@liferay.com");
		_jsonWebServiceClient.setPassword("test");
		_jsonWebServiceClient.setProtocol(_rootEndpointURL.getProtocol());
	}

	@Test
	public void testContentSpaceLinkExistsInRootEndpoint() throws Exception {
		Assert.assertNotNull(
			JsonPath.read(
				_get(_rootEndpointURL.toExternalForm(), Collections.emptyMap()),
				"$._links.content-space.href"));
	}

	private String _get(String url, Map<String, String> parameters)
		throws JSONWebServiceInvocationException,
		JSONWebServiceTransportException {

		return _jsonWebServiceClient.doGet(
			url, parameters,
			Collections.singletonMap("Accept", "application/hal+json"));
	}

	private JSONWebServiceClient _jsonWebServiceClient;
	private URL _rootEndpointURL;

	@ArquillianResource
	private URL _url;
}
