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

package com.liferay.oauth2.provider.client.test;

import aQute.bnd.osgi.Analyzer;
import aQute.bnd.osgi.Jar;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.CookieKeys;
import com.liferay.portal.kernel.util.Digester;
import com.liferay.portal.kernel.util.DigesterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.DigesterImpl;
import com.liferay.portal.util.HttpImpl;
import com.liferay.shrinkwrap.osgi.api.BndProjectBuilder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import java.util.Map;
import java.util.Properties;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.provider.json.JSONProvider;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.Node;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.Asset;
import org.jboss.shrinkwrap.api.asset.ByteArrayAsset;
import org.jboss.shrinkwrap.api.exporter.ZipExporter;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

import org.junit.BeforeClass;

import org.osgi.framework.BundleActivator;

/**
 * @author Carlos Sierra Andrés
 */
public abstract class BaseClientTestCase {

	public static Archive<?> getDeployment(
			Class<? extends BundleActivator> bundleActivatorClass)
		throws Exception {

		String javaClassPathString = System.getProperty("java.class.path");

		String[] javaClassPaths = StringUtil.split(
			javaClassPathString, File.pathSeparator);

		BndProjectBuilder bndProjectBuilder = ShrinkWrap.create(
			BndProjectBuilder.class);

		for (String javaClassPath : javaClassPaths) {
			File file = new File(javaClassPath);

			if (file.isDirectory() ||
				StringUtil.endsWith(javaClassPath, ".zip") ||
				StringUtil.endsWith(javaClassPath, ".jar")) {

				bndProjectBuilder.addClassPath(file);
			}
		}

		Analyzer analyzer = new Analyzer();
		File bndFile = new File("bnd.bnd");

		JavaArchive javaArchive = bndProjectBuilder.setBndFile(
			bndFile
		).as(
			JavaArchive.class
		);

		javaArchive.addClass(bundleActivatorClass);

		ZipExporter zipExporter = javaArchive.as(ZipExporter.class);

		Jar jar = new Jar(
			javaArchive.getName(), zipExporter.exportAsInputStream());

		Properties analyzerProperties = new Properties();

		analyzerProperties.putAll(analyzer.loadProperties(bndFile));

		analyzer.setJar(jar);

		Node node = javaArchive.get("META-INF/MANIFEST.MF");

		Asset asset = node.getAsset();

		try (InputStream inputStream = asset.openStream()) {
			Manifest manifest = new Manifest(inputStream);

			Attributes attributes = manifest.getMainAttributes();

			attributes.remove(new Attributes.Name("Import-Package"));

			attributes.putValue(
				"Bundle-Activator", bundleActivatorClass.getName());

			analyzer.mergeManifest(manifest);
		}

		Manifest manifest = analyzer.calcManifest();

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		manifest.write(baos);

		Asset byteArrayAsset = new ByteArrayAsset(baos.toByteArray());

		javaArchive.delete("META-INF/MANIFEST.MF");

		javaArchive.add(byteArrayAsset, "META-INF/MANIFEST.MF");

		return javaArchive;
	}

	@BeforeClass
	public static void setUpClass() {
		System.setProperty("sun.net.http.allowRestrictedHeaders", "true");

		HttpUtil httpUtil = new HttpUtil();

		httpUtil.setHttp(new HttpImpl());

		DigesterUtil digesterUtil = new DigesterUtil();

		digesterUtil.setDigester(new DigesterImpl());
	}

	protected static Client getClient() {
		Client client = ClientBuilder.newClient();

		return client.register(JSONProvider.class);
	}

	protected Invocation.Builder authorize(
		Invocation.Builder invocataionBuilder, String token) {

		return invocataionBuilder.header("Authorization", "Bearer " + token);
	}

	protected Cookie getAuthenticatedCookie(
			String login, String password, String hostname)
		throws URISyntaxException {

		Invocation.Builder invocationBuilder = getInvocationBuilder(
			hostname, getLoginWebTarget());

		MultivaluedHashMap<String, String> formData =
			new MultivaluedHashMap<>();

		formData.add("login", login);
		formData.add("password", password);

		Response response = invocationBuilder.post(Entity.form(formData));

		Map<String, NewCookie> cookies = response.getCookies();

		NewCookie cookie = cookies.get(CookieKeys.JSESSIONID);

		if (cookie == null) {
			return null;
		}

		return cookie.toCookie();
	}

	protected BiFunction<String, Invocation.Builder, Response>
		getAuthorizationCode(String user, String password, String hostname) {

		return (clientId, invocationBuilder) -> {
			String authorizationCode = getAuthorizationCode(
				user, password, hostname,
				webTarget -> webTarget.queryParam(
					"client_id", clientId
				).queryParam(
					"response_type", "code"
				));

			MultivaluedHashMap<String, String> formData =
				new MultivaluedHashMap<>();

			formData.add("client_id", clientId);
			formData.add("client_secret", "oauthTestApplicationSecret");
			formData.add("grant_type", "authorization_code");
			formData.add("code", authorizationCode);

			return invocationBuilder.post(Entity.form(formData));
		};
	}

	protected String getAuthorizationCode(
		String login, String password, String hostname,
		Function<WebTarget, WebTarget> authorizeRequestFunction) {

		try {
			Invocation.Builder invocationBuilder = getInvocationBuilder(
				hostname,
				authorizeRequestFunction.apply(getAuthorizeWebTarget()));

			Cookie authenticatedCookie = getAuthenticatedCookie(
				login, password, hostname);

			Response response = invocationBuilder.accept(
				"text/html"
			).cookie(
				authenticatedCookie
			).get();

			URI location = response.getLocation();

			if (location == null) {
				throw new RuntimeException(
					"Invalid authorization response: " + response.getStatus());
			}

			Map<String, String[]> parameterMap = HttpUtil.getParameterMap(
				location.getQuery());

			if (parameterMap.containsKey("error")) {
				return parameterMap.get("error")[0];
			}

			MultivaluedHashMap<String, String> formData =
				new MultivaluedHashMap<>();

			formData.add("oauthDecision", "allow");

			for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
				String key = entry.getKey();

				if (!StringUtil.startsWith(key, "oauth2_")) {
					continue;
				}

				formData.add(
					key.substring("oauth2_".length()), entry.getValue()[0]);
			}

			invocationBuilder = getInvocationBuilder(
				hostname, getAuthorizeDecisionWebTarget());

			invocationBuilder = invocationBuilder.cookie(authenticatedCookie);

			response = invocationBuilder.post(Entity.form(formData));

			location = response.getLocation();

			if (location == null) {
				throw new RuntimeException(
					"Invalid authorization decision response: " +
						response.getStatus());
			}

			parameterMap = HttpUtil.getParameterMap(location.getQuery());

			if (parameterMap.containsKey("error")) {
				return parameterMap.get("error")[0];
			}

			return parameterMap.get("code")[0];
		}
		catch (URISyntaxException urise) {
			throw new RuntimeException(urise);
		}
	}

	protected BiFunction<String, Invocation.Builder, Response>
		getAuthorizationCodePKCE(
			String user, String password, String hostname) {

		return (clientId, invocationBuilder) -> {
			String codeVerifier = RandomTestUtil.randomString();

			String base64Digest = DigesterUtil.digestBase64(
				Digester.SHA_256, codeVerifier);

			String base64UrlDigest = StringUtil.replace(
				base64Digest, new char[] {CharPool.PLUS, CharPool.SLASH},
				new char[] {CharPool.MINUS, CharPool.UNDERLINE});

			base64UrlDigest = StringUtil.removeChar(
				base64UrlDigest, CharPool.EQUAL);

			final String codeChallenge = base64UrlDigest;

			String authorizationCode = getAuthorizationCode(
				user, password, hostname,
				webTarget -> webTarget.queryParam(
					"client_id", clientId
				).queryParam(
					"code_challenge", codeChallenge
				).queryParam(
					"response_type", "code"
				));

			MultivaluedHashMap<String, String> formData =
				new MultivaluedHashMap<>();

			formData.add("client_id", clientId);
			formData.add("code", authorizationCode);
			formData.add("code_verifier", codeVerifier);
			formData.add("grant_type", "authorization_code");

			return invocationBuilder.post(Entity.form(formData));
		};
	}

	protected WebTarget getAuthorizeDecisionWebTarget()
		throws URISyntaxException {

		WebTarget webTarget = getAuthorizeWebTarget();

		return webTarget.path("decision");
	}

	protected WebTarget getAuthorizeWebTarget() throws URISyntaxException {
		WebTarget webTarget = getOAuth2WebTarget();

		return webTarget.path("authorize");
	}

	protected BiFunction<String, Invocation.Builder, Response>
		getClientCredentials(String scope) {

		return (clientId, invocationBuilder) -> {
			MultivaluedHashMap<String, String> formData =
				new MultivaluedHashMap<>();

			formData.add("client_id", clientId);
			formData.add("client_secret", "oauthTestApplicationSecret");
			formData.add("grant_type", "client_credentials");
			formData.add("scope", scope);

			return invocationBuilder.post(Entity.form(formData));
		};
	}

	protected Response getClientCredentials(
		String clientId, Invocation.Builder invocationBuilder) {

		MultivaluedHashMap<String, String> formData =
			new MultivaluedHashMap<>();

		formData.add("client_id", clientId);
		formData.add("client_secret", "oauthTestApplicationSecret");
		formData.add("grant_type", "client_credentials");

		return invocationBuilder.post(Entity.form(formData));
	}

	protected Invocation.Builder getInvocationBuilder(
		String hostname, WebTarget webTarget) {

		Invocation.Builder invocationBuilder = webTarget.request();

		if (hostname != null) {
			invocationBuilder = invocationBuilder.header("Host", hostname);
		}

		return invocationBuilder;
	}

	protected WebTarget getJsonWebTarget(String... paths)
		throws URISyntaxException {

		Client client = getClient();

		WebTarget webTarget = client.target(_url.toURI());

		webTarget = webTarget.path("api");
		webTarget = webTarget.path("jsonws");

		for (String path : paths) {
			webTarget = webTarget.path(path);
		}

		return webTarget;
	}

	protected WebTarget getLoginWebTarget() throws URISyntaxException {
		Client client = getClient();

		WebTarget webTarget = client.target(_url.toURI());

		webTarget = webTarget.path("c");
		webTarget = webTarget.path("portal");
		webTarget = webTarget.path("login");

		return webTarget;
	}

	protected WebTarget getOAuth2WebTarget() throws URISyntaxException {
		Client client = getClient();

		WebTarget webTarget = client.target(_url.toURI());

		webTarget = webTarget.path("o");
		webTarget = webTarget.path("oauth2");

		return webTarget;
	}

	protected BiFunction<String, Invocation.Builder, Response>
		getResourceOwnerPassword(String user, String password) {

		return (clientId, invocationBuilder) -> {
			MultivaluedHashMap<String, String> formData =
				new MultivaluedHashMap<>();

			formData.add("client_id", clientId);
			formData.add("client_secret", "oauthTestApplicationSecret");
			formData.add("grant_type", "password");
			formData.add("username", user);
			formData.add("password", password);

			return invocationBuilder.post(Entity.form(formData));
		};
	}

	protected BiFunction<String, Invocation.Builder, Response>
		getResourceOwnerPassword(String user, String password, String scope) {

		return (clientId, invocationBuilder) -> {
			MultivaluedHashMap<String, String> formData =
				new MultivaluedHashMap<>();

			formData.add("client_id", clientId);
			formData.add("client_secret", "oauthTestApplicationSecret");
			formData.add("grant_type", "password");
			formData.add("username", user);
			formData.add("password", password);
			formData.add("scope", scope);

			return invocationBuilder.post(Entity.form(formData));
		};
	}

	protected String getToken(String clientId) throws URISyntaxException {
		return getToken(clientId, null);
	}

	protected String getToken(String clientId, String hostname)
		throws URISyntaxException {

		return parseTokenString(
			getClientCredentials(
				clientId, getTokenInvocationBuilder(hostname)));
	}

	protected <T> T getToken(
			String clientId, String hostname,
			BiFunction<String, Invocation.Builder, Response>
				credentialsBiFunction,
			Function<Response, T> tokenParser)
		throws URISyntaxException {

		return tokenParser.apply(
			credentialsBiFunction.apply(
				clientId, getTokenInvocationBuilder(hostname)));
	}

	protected Invocation.Builder getTokenInvocationBuilder(String hostname)
		throws URISyntaxException {

		return getInvocationBuilder(hostname, getTokenWebTarget());
	}

	protected WebTarget getTokenWebTarget() throws URISyntaxException {
		WebTarget webTarget = getOAuth2WebTarget();

		return webTarget.path("token");
	}

	protected WebTarget getWebTarget(String... paths)
		throws URISyntaxException {

		Client client = getClient();

		WebTarget target = client.target(_url.toURI());

		target = target.path("o");
		target = target.path("oauth2-test");

		for (String path : paths) {
			target = target.path(path);
		}

		return target;
	}

	protected String parseError(Response response) {
		return parseJsonField(response, "error");
	}

	protected String parseJsonField(Response response, String field) {
		JSONObject jsonObject = parseJSONObject(response);

		try {
			return jsonObject.getString(field);
		}
		catch (JSONException jsone) {
			throw new IllegalArgumentException(
				"The token service returned " + jsonObject.toString());
		}
	}

	protected JSONObject parseJSONObject(Response response) {
		String json = response.readEntity(String.class);

		try {
			return new JSONObject(json);
		}
		catch (JSONException jsone) {
			throw new IllegalArgumentException(
				"The token service returned " + json);
		}
	}

	protected String parseScopeString(Response response) {
		return parseJsonField(response, "scope");
	}

	protected String parseTokenString(Response response) {
		return parseJsonField(response, "access_token");
	}

	@ArquillianResource
	private URL _url;

}