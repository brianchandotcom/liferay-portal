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

package com.liferay.digital.signature.internal.http;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;

import java.util.Base64;

import net.oauth.signature.pem.PEMReader;
import net.oauth.signature.pem.PKCS1EncodedKeySpec;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true, service = DSHttp.class)
public class DSHttp {

	public JSONObject get(long groupId, String location) {
		try {
			return _invoke(groupId, location, Http.Method.GET, null);
		}
		catch (Exception exception) {
			return ReflectionUtil.throwException(exception);
		}
	}

	public JSONObject post(
		long groupId, String location, JSONObject bodyJSONObject) {

		try {
			return _invoke(groupId, location, Http.Method.POST, bodyJSONObject);
		}
		catch (Exception exception) {
			return ReflectionUtil.throwException(exception);
		}
	}

	private String _encode(byte[] bytes) {
		//com.liferay.portal.kernel.util.Base64.encode(bytes);

		Base64.Encoder encoder = Base64.getUrlEncoder();

		return encoder.encodeToString(bytes);
	}

	private String _getDocuSignAccessToken(long groupId) throws Exception {
		if (_log.isDebugEnabled()) {
			_log.debug("Get DocuSign access token for group " + groupId);
		}

		Http.Options options = new Http.Options();

		options.setParts(
			HashMapBuilder.put(
				"assertion", _getJWT(groupId)
			).put(
				"grant_type", "urn:ietf:params:oauth:grant-type:jwt-bearer"
			).build());
		options.setLocation("https://account-d.docusign.com/oauth/token");
		options.setPost(true);

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			_http.URLtoString(options));

		return jsonObject.getString("access_token");
	}

	private String _getDocuSignAPIAccountId(long groupId) {
		if (_log.isDebugEnabled()) {
			_log.debug("Get DocuSign API account ID for group " + groupId);
		}

		return "22d6e23f-1da3-440d-883d-";
	}

	private String _getDocuSignAPIUsername(long groupId) {
		if (_log.isDebugEnabled()) {
			_log.debug("Get DocuSign API username for group " + groupId);
		}

		return "d5bb12fc-1460-4a8f-b33e-";
	}

	private String _getDocuSignIntegrationKey(long groupId) {
		if (_log.isDebugEnabled()) {
			_log.debug("Get DocuSign integration key for group " + groupId);
		}

		return "4ac993f9-a4d6-4086-8c59-";
	}

	private String _getJWT(long groupId) throws Exception {
		Signature signature = Signature.getInstance("SHA256withRSA");

		signature.initSign(_readPrivateKey());

		String headerJSON = JSONUtil.put(
			"alg", "RS256"
		).put(
			"typ", "JWT"
		).toString();

		long unixTime = System.currentTimeMillis() / Time.SECOND;

		String bodyJSON = JSONUtil.put(
			"aud", "account-d.docusign.com"
		).put(
			"exp", unixTime + 3600
		).put(
			"iat", unixTime
		).put(
			"iss", _getDocuSignIntegrationKey(groupId)
		).put(
			"scope", "signature"
		).put(
			"sub", _getDocuSignAPIUsername(groupId)
		).toString();

		String token =
			_encode(headerJSON.getBytes()) + "." + _encode(bodyJSON.getBytes());

		signature.update(token.getBytes());

		return StringUtil.removeSubstring(
			token + "." + _encode(signature.sign()), "=");
	}

	private JSONObject _invoke(
			long groupId, String location, Http.Method method,
			JSONObject bodyJSONObject)
		throws Exception {

		Http.Options options = new Http.Options();

		if (bodyJSONObject != null) {
			options.addHeader(
				HttpHeaders.CONTENT_TYPE, ContentTypes.APPLICATION_JSON);
		}

		options.addHeader(
			"Authorization", "Bearer " + _getDocuSignAccessToken(groupId));

		if (bodyJSONObject != null) {
			options.setBody(
				bodyJSONObject.toString(), ContentTypes.APPLICATION_JSON,
				StringPool.UTF8);
		}

		options.setLocation(
			StringBundler.concat(
				"https://demo.docusign.net/restapi/v2.1/accounts/",
				_getDocuSignAPIAccountId(groupId), "/", location));
		options.setMethod(method);

		return _jsonFactory.createJSONObject(_http.URLtoString(options));
	}

	private PrivateKey _readPrivateKey() throws Exception {
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");

		Class<?> clazz = DSHttp.class;

		PEMReader pemReader = new PEMReader(
			clazz.getResourceAsStream("dependencies/private_key.txt"));

		PKCS1EncodedKeySpec encodedKeySpec = new PKCS1EncodedKeySpec(
			pemReader.getDerBytes());

		return keyFactory.generatePrivate(encodedKeySpec.getKeySpec());
	}

	private static final Log _log = LogFactoryUtil.getLog(DSHttp.class);

	@Reference
	private Http _http;

	@Reference
	private JSONFactory _jsonFactory;

}