/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.digital.signature.internal.web.cache;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.webcache.WebCacheItem;
import com.liferay.portal.kernel.webcache.WebCachePoolUtil;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;

import java.util.Base64;

import net.oauth.signature.pem.PEMReader;
import net.oauth.signature.pem.PKCS1EncodedKeySpec;

/**
 * @author Brian Wing Shun Chan
 */
public class DSAccessTokenWebCacheItem implements WebCacheItem {

	public static JSONObject get(
		String apiUsername, String integrationKey, String rsaPrivateKey) {

		return (JSONObject)WebCachePoolUtil.get(
			StringBundler.concat(
				DSAccessTokenWebCacheItem.class.getName(), StringPool.POUND,
				apiUsername, StringPool.POUND, integrationKey, StringPool.POUND,
				rsaPrivateKey),
			new DSAccessTokenWebCacheItem(
				apiUsername, integrationKey, rsaPrivateKey));
	}

	public DSAccessTokenWebCacheItem(
		String apiUsername, String integrationKey, String rsaPrivateKey) {

		_apiUsername = apiUsername;
		_integrationKey = integrationKey;

		if (rsaPrivateKey != null) {
			_rsaPrivateKeyBytes = rsaPrivateKey.getBytes();
		}
		else {
			_rsaPrivateKeyBytes = new byte[0];
		}
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
					"grant_type", "urn:ietf:params:oauth:grant-type:jwt-bearer"
				).build());
			options.setLocation("https://account-d.docusign.com/oauth/token");
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
			"sub", _apiUsername
		).toString();

		String token =
			_encode(headerJSON.getBytes()) + "." + _encode(bodyJSON.getBytes());

		signature.update(token.getBytes());

		return StringUtil.removeSubstring(
			token + "." + _encode(signature.sign()), "=");
	}

	private PrivateKey _readPrivateKey() throws Exception {
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");

		PEMReader pemReader = new PEMReader(_rsaPrivateKeyBytes);

		PKCS1EncodedKeySpec pkcs1EncodedKeySpec = new PKCS1EncodedKeySpec(
			pemReader.getDerBytes());

		return keyFactory.generatePrivate(pkcs1EncodedKeySpec.getKeySpec());
	}

	private static final long _REFRESH_TIME = Time.MINUTE * 45;

	private static final Log _log = LogFactoryUtil.getLog(
		DSAccessTokenWebCacheItem.class);

	private final String _apiUsername;
	private final String _integrationKey;
	private final byte[] _rsaPrivateKeyBytes;

}