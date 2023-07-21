/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.digital.signature.internal.http;

import com.liferay.digital.signature.configuration.DigitalSignatureConfiguration;
import com.liferay.digital.signature.configuration.DigitalSignatureConfigurationUtil;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.SingleVMPool;
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

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(service = DSHttp.class)
public class DSHttp {

	public JSONObject get(long companyId, long groupId, String location) {
		try {
			return _invoke(companyId, groupId, location, Http.Method.GET, null);
		}
		catch (Exception exception) {
			return ReflectionUtil.throwException(exception);
		}
	}

	public byte[] getAsBytes(long companyId, long groupId, String location) {
		try {
			return _invokeAsBytes(
				companyId, groupId, location, Http.Method.GET, null);
		}
		catch (Exception exception) {
			return ReflectionUtil.throwException(exception);
		}
	}

	public JSONObject post(
		long companyId, long groupId, String location,
		JSONObject bodyJSONObject) {

		try {
			return _invoke(
				companyId, groupId, location, Http.Method.POST, bodyJSONObject);
		}
		catch (Exception exception) {
			return ReflectionUtil.throwException(exception);
		}
	}

	public JSONObject put(
		long companyId, long groupId, String location,
		JSONObject bodyJSONObject) {

		try {
			return _invoke(
				companyId, groupId, location, Http.Method.PUT, bodyJSONObject);
		}
		catch (Exception exception) {
			return ReflectionUtil.throwException(exception);
		}
	}

	@Activate
	protected void activate() {
		_portalCache =
			(PortalCache<String, String>)_singleVMPool.getPortalCache(
				DSHttp.class.getName());
	}

	@Deactivate
	protected void deactivate() {
		_singleVMPool.removePortalCache(DSHttp.class.getName());
	}

	private String _createAccessToken(
		DigitalSignatureConfiguration digitalSignatureConfiguration) {

		try {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Get DocuSign access token for integration key " +
						digitalSignatureConfiguration.integrationKey());
			}

			Http.Options options = new Http.Options();

			options.setParts(
				HashMapBuilder.put(
					"assertion", _getJWT(digitalSignatureConfiguration)
				).put(
					"grant_type", "urn:ietf:params:oauth:grant-type:jwt-bearer"
				).build());
			options.setLocation("https://account-d.docusign.com/oauth/token");
			options.setPost(true);

			JSONObject jsonObject = _jsonFactory.createJSONObject(
				_http.URLtoString(options));

			return jsonObject.getString("access_token");
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return StringPool.BLANK;
		}
	}

	private String _encode(byte[] bytes) {
		//com.liferay.portal.kernel.util.Base64.encode(bytes);

		Base64.Encoder encoder = Base64.getUrlEncoder();

		return encoder.encodeToString(bytes);
	}

	private String _getAccessToken(
		DigitalSignatureConfiguration digitalSignatureConfiguration) {

		String key = StringBundler.concat(
			digitalSignatureConfiguration.apiUsername(), StringPool.POUND,
			digitalSignatureConfiguration.integrationKey(), StringPool.POUND,
			digitalSignatureConfiguration.rsaPrivateKey());

		String accessToken = _portalCache.get(key);

		if (accessToken != null) {
			return accessToken;
		}

		accessToken = _createAccessToken(digitalSignatureConfiguration);

		_portalCache.put(key, accessToken, _REFRESH_TIME_IN_SECONDS);

		return accessToken;
	}

	private String _getDocuSignAccessToken(
			DigitalSignatureConfiguration digitalSignatureConfiguration)
		throws Exception {

		return _getAccessToken(digitalSignatureConfiguration);
	}

	private String _getJWT(
			DigitalSignatureConfiguration digitalSignatureConfiguration)
		throws Exception {

		Signature signature = Signature.getInstance("SHA256withRSA");

		signature.initSign(
			_readPrivateKey(digitalSignatureConfiguration.rsaPrivateKey()));

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
			"iss", digitalSignatureConfiguration.integrationKey()
		).put(
			"scope", "signature"
		).put(
			"sub", digitalSignatureConfiguration.apiUsername()
		).toString();

		String token =
			_encode(headerJSON.getBytes()) + "." + _encode(bodyJSON.getBytes());

		signature.update(token.getBytes());

		return StringUtil.removeSubstring(
			token + "." + _encode(signature.sign()), "=");
	}

	private JSONObject _invoke(
			long companyId, long groupId, String location, Http.Method method,
			JSONObject bodyJSONObject)
		throws Exception {

		byte[] bytes = _invokeAsBytes(
			companyId, groupId, location, method, bodyJSONObject);

		if (bytes == null) {
			return _jsonFactory.createJSONObject();
		}

		return _jsonFactory.createJSONObject(new String(bytes));
	}

	private byte[] _invokeAsBytes(
			long companyId, long groupId, String location, Http.Method method,
			JSONObject bodyJSONObject)
		throws Exception {

		Http.Options options = new Http.Options();

		if (bodyJSONObject != null) {
			options.addHeader(
				HttpHeaders.CONTENT_TYPE, ContentTypes.APPLICATION_JSON);
		}

		DigitalSignatureConfiguration digitalSignatureConfiguration =
			DigitalSignatureConfigurationUtil.getDigitalSignatureConfiguration(
				companyId, groupId);

		options.addHeader(
			"Authorization",
			"Bearer " + _getDocuSignAccessToken(digitalSignatureConfiguration));

		if (bodyJSONObject != null) {
			options.setBody(
				bodyJSONObject.toString(), ContentTypes.APPLICATION_JSON,
				StringPool.UTF8);
		}

		options.setLocation(
			StringBundler.concat(
				digitalSignatureConfiguration.accountBaseURI(),
				"/restapi/v2.1/accounts/",
				digitalSignatureConfiguration.apiAccountId(), "/", location));
		options.setMethod(method);

		return _http.URLtoByteArray(options);
	}

	private PrivateKey _readPrivateKey(String rsaPrivateKey) throws Exception {
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");

		byte[] rsaPrivateKeyBytes =
			(rsaPrivateKey == null) ? new byte[0] : rsaPrivateKey.getBytes();

		PEMReader pemReader = new PEMReader(rsaPrivateKeyBytes);

		PKCS1EncodedKeySpec pkcs1EncodedKeySpec = new PKCS1EncodedKeySpec(
			pemReader.getDerBytes());

		return keyFactory.generatePrivate(pkcs1EncodedKeySpec.getKeySpec());
	}

	private static final int _REFRESH_TIME_IN_SECONDS =
		(int)(Time.MINUTE * 45 / Time.SECOND);

	private static final Log _log = LogFactoryUtil.getLog(DSHttp.class);

	@Reference
	private Http _http;

	@Reference
	private JSONFactory _jsonFactory;

	private PortalCache<String, String> _portalCache;

	@Reference
	private SingleVMPool _singleVMPool;

}