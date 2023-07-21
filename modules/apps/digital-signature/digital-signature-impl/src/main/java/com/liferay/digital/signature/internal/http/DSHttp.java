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
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.webcache.WebCacheItem;

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
			(PortalCache<String, JSONObject>)_singleVMPool.getPortalCache(
				DSHttp.class.getName());
	}

	@Deactivate
	protected void deactivate() {
		_singleVMPool.removePortalCache(DSHttp.class.getName());
	}

	private String _getDocuSignAccessToken(
			DigitalSignatureConfiguration digitalSignatureConfiguration)
		throws Exception {

		JSONObject jsonObject = DSAccessTokenWebCacheItem.get(
			digitalSignatureConfiguration.apiUsername(),
			digitalSignatureConfiguration.integrationKey(),
			digitalSignatureConfiguration.rsaPrivateKey(), _portalCache);

		return jsonObject.getString("access_token");
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

	@Reference
	private Http _http;

	@Reference
	private JSONFactory _jsonFactory;

	private PortalCache<String, JSONObject> _portalCache;

	@Reference
	private SingleVMPool _singleVMPool;

	private static class DSAccessTokenWebCacheItem implements WebCacheItem {

		public static JSONObject get(
			String apiUserName, String integrationKey, String rsaPrivateKey,
			PortalCache<String, JSONObject> portalCache) {

			String key = StringBundler.concat(
				apiUserName, StringPool.POUND, integrationKey, StringPool.POUND,
				rsaPrivateKey);

			JSONObject jsonObject = portalCache.get(key);

			if (jsonObject != null) {
				return jsonObject;
			}

			DSAccessTokenWebCacheItem dsAccessTokenWebCacheItem =
				new DSAccessTokenWebCacheItem(
					apiUserName, integrationKey, rsaPrivateKey);

			jsonObject = dsAccessTokenWebCacheItem.convert(key);

			portalCache.put(
				key, jsonObject,
				(int)
					(dsAccessTokenWebCacheItem.getRefreshTime() / Time.SECOND));

			return jsonObject;
		}

		public DSAccessTokenWebCacheItem(
			String apiUserName, String integrationKey, String rsaPrivateKey) {

			_apiUserName = apiUserName;
			_integrationKey = integrationKey;
			_rsaPrivateKey = rsaPrivateKey;
		}

		@Override
		public JSONObject convert(String key) {
			try {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Get DocuSign access token for integration key " +
							_integrationKey);
				}

				Http.Options options = new Http.Options();

				options.setParts(
					HashMapBuilder.put(
						"assertion", _getJWT()
					).put(
						"grant_type",
						"urn:ietf:params:oauth:grant-type:jwt-bearer"
					).build());
				options.setLocation(
					"https://account-d.docusign.com/oauth/token");
				options.setPost(true);

				return JSONFactoryUtil.createJSONObject(
					HttpUtil.URLtoString(options));
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception);
				}

				return JSONFactoryUtil.createJSONObject();
			}
		}

		@Override
		public long getRefreshTime() {
			return _REFRESH_TIME;
		}

		private String _encode(byte[] bytes) {
			//com.liferay.portal.kernel.util.Base64.encode(bytes);

			Base64.Encoder encoder = Base64.getUrlEncoder();

			return encoder.encodeToString(bytes);
		}

		private String _getJWT() throws Exception {
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
				"iss", _integrationKey
			).put(
				"scope", "signature"
			).put(
				"sub", _apiUserName
			).toString();

			String token =
				_encode(headerJSON.getBytes()) + "." +
					_encode(bodyJSON.getBytes());

			signature.update(token.getBytes());

			return StringUtil.removeSubstring(
				token + "." + _encode(signature.sign()), "=");
		}

		private PrivateKey _readPrivateKey() throws Exception {
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");

			byte[] rsaPrivateKeyBytes =
				(_rsaPrivateKey == null) ? new byte[0] :
					_rsaPrivateKey.getBytes();

			PEMReader pemReader = new PEMReader(rsaPrivateKeyBytes);

			PKCS1EncodedKeySpec pkcs1EncodedKeySpec = new PKCS1EncodedKeySpec(
				pemReader.getDerBytes());

			return keyFactory.generatePrivate(pkcs1EncodedKeySpec.getKeySpec());
		}

		private static final long _REFRESH_TIME = Time.MINUTE * 45;

		private static final Log _log = LogFactoryUtil.getLog(
			DSAccessTokenWebCacheItem.class);

		private final String _apiUserName;
		private final String _integrationKey;
		private final String _rsaPrivateKey;

	}

}