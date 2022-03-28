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

package com.liferay.oauth2.provider.internal.test.util;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.net.URI;

import org.apache.cxf.rs.security.jose.common.JoseType;
import org.apache.cxf.rs.security.jose.jwa.SignatureAlgorithm;
import org.apache.cxf.rs.security.jose.jwk.JsonWebKeys;
import org.apache.cxf.rs.security.jose.jwk.JwkUtils;
import org.apache.cxf.rs.security.jose.jws.JwsHeaders;
import org.apache.cxf.rs.security.jose.jws.JwsJwtCompactProducer;
import org.apache.cxf.rs.security.jose.jwt.JwtClaims;
import org.apache.cxf.rs.security.jose.jwt.JwtToken;
import org.apache.cxf.rs.security.oauth2.utils.OAuthUtils;

/**
 * @author Arthur Chan
 */
public class JWTAssertionUtil {

	public static final String JWKS = JSONUtil.put(
		"keys", JSONUtil.put(_createTestRSAKeyPairJSONWebKey01())
	).toString();

	public static String getJWTAssertion(
		String issuer, String subject, URI audience) {

		JwsHeaders jwsHeaders = new JwsHeaders(
			JoseType.JWT, SignatureAlgorithm.RS256);

		jwsHeaders.setKeyId(_KEY_ID);

		JwsJwtCompactProducer jwsJwtCompactProducer = new JwsJwtCompactProducer(
			new JwtToken(jwsHeaders, _getJWTClaims(issuer, subject, audience)));

		JsonWebKeys jsonWebKeys = JwkUtils.readJwkSet(JWKS);

		return jwsJwtCompactProducer.signWith(
			jsonWebKeys.getKey(jwsHeaders.getKeyId()));
	}

	private static JSONObject _createAsymmetricPrivateKeyJSONObject(
		String alg, String d, String kid, String kty, String use) {

		JSONObject keyJSONObject = _createKeyJSONObject(alg, kid, kty, use);

		keyJSONObject.put("d", d);

		return keyJSONObject;
	}

	private static JSONObject _createKeyJSONObject(
		String alg, String kid, String kty, String use) {

		JSONObject keyJSONObject = JSONUtil.put(
			"alg", alg
		).put(
			"kty", kty
		).put(
			"use", use
		);

		if (kid != null) {
			keyJSONObject.put("kid", kid);
		}

		return keyJSONObject;
	}

	private static JSONObject _createRSAKeyPairJSONObject(
		String alg, String d, String dp, String dq, String e, String kid,
		String kty, String n, String p, String q, String qi, String use) {

		JSONObject rsaKeyJSONObject = _createAsymmetricPrivateKeyJSONObject(
			alg, d, kid, kty, use);

		if (dp != null) {
			rsaKeyJSONObject.put("dp", dp);
		}

		if (dq != null) {
			rsaKeyJSONObject.put("dq", dq);
		}

		if (e != null) {
			rsaKeyJSONObject.put("e", e);
		}

		if (n != null) {
			rsaKeyJSONObject.put("n", n);
		}

		if (p != null) {
			rsaKeyJSONObject.put("p", p);
		}

		if (q != null) {
			rsaKeyJSONObject.put("q", q);
		}

		if (qi != null) {
			rsaKeyJSONObject.put("qi", qi);
		}

		return rsaKeyJSONObject;
	}

	private static JSONObject _createTestRSAKeyPairJSONWebKey01() {
		return _createRSAKeyPairJSONObject(
			"RS256",
			StringBundler.concat(//d
				"iNf_TOwPcGZyxldVnEXEJOzkjX1IVAp2RJF9Z6LMDLiJ",
				"P2HaeWQ1pJHmV_KaTAX-QQs-_9yIym0Y_7ybmFMF3IKd5rSdWUgDopiluH7lj",
				"B-ed_9grsRWHFkIctlNDzmVba-kCYEdLyZ43xDSFwVeNosQh3FnqMRl2xnbJt",
				"p43PiSZS6Gf_2hE189uA4sGLNbkFTyZ2Y61w550aHbGACZqgG1gY2SToYJxeD",
				"M08bwbgCfgv1dR1OrQCNBLkUluWoUfrm7kBpkWrEugOmtGlZ0vynbFMoXMaRs",
				"NySxUWfwA1xd49NjC0nXVGJKggLbYTyHqyA5eJNAX8IM7LBfGRENyQ"),
			StringBundler.concat(//dp
				"M9QuHImuXwU9bEmHQAis3sg9UCe6y--j3s9le5MiBtqD",
				"4XiqojJrisCkZ56Lcmkq2sG7LtOqCrnwMTIrPptSizDnNs18-1ZZuW8OaMyw-",
				"jhDTM5cLXjaY6VKznh2qg4QR6gOle9SxdVoNNKAhLlQlC3noVSaFoGBXgFkFf",
				"8WdOE"),
			StringBundler.concat(//dq
				"DRR9MXaoj29ycKzBTL-NZK8yLMChLh5lJjimxuSn9zEx",
				"qygSDToPPg_1SU2gQxeE_iKEj5rkC0Ckzk3rDopNTWid1F0sMaAl2sbVr7NsS",
				"7tAXsVrno_m-laDun84JMXOj86nJxTjq6taaZhVZVfCFUnVsUGFZK1FHLEjKz",
				"iSskE"),
			"AQAB", _KEY_ID, "RSA",
			StringBundler.concat(//n
				"n8lN23sleK5k1Lhp3r8xhdmJ3qFezuT3xZ30bdmfISXo",
				"MyvYVVdMoA41Fx_cPB3NqylbBYDLWL6YknRi_38dHHx0pF_t0ay6V2Hut_zju",
				"KuCNBrp20m04c5oCa1vUM_Jqj9TKIoj4PJSR6Tknnxw7pr0PUFMBTfYHZdMAS",
				"zPtZNqsqkT4scEsAy3fsE9twiG3S9u4tmKOEQqX7wLtL1kwBig_Hh5_RXPQfI",
				"4MoV3iMzw-k-urHJQ5cRJxzYOxNqoj1oDJxWCDXmrm9idFH0Lrs6rb0rQ6jCk",
				"BjEM9Q_rM0ZzoiB0NXbaQTrgxlHGUrpTDlEukKGQObWyYNvktv-OYw"),
			StringBundler.concat(//p
				"37P2B_RQiNP4M-khV2Z0qTlkfcrFy02v2xko6xqqYqxJTnD2eM0_WGqKQVTBb",
				"q2thTPkw44Kw18jhqYcVm7jyQcN4zcYKEAElQ3jztJOWKLkTOiuu5D-DXuF3P",
				"yUaL7klMbqp8EGBYh23abM3i0jkNWT0HWJfnEpQ8FzlLChptc"),
			StringBundler.concat(//q
				"ttr9tDJZc8Swf4TpzV7qY2r36k9lSH7eLVA35KpQJ9FNj9JqAminUvWyvqFJb",
				"oN_3zVxsxUrJdNxhrOfsogSxOkF8364ShECWBCgP2fBC8U_dIVfc_XRYNiTts",
				"S7MbCsbe_HhXaKFArRFt3eq_erFn5qU2W28ip5Hgw5d3eV_1U"),
			StringBundler.concat(//qi
				"olPn50M0v0XPWrJKy_e34C5GRilM5fHZgI89MYKnvSWw",
				"WwPlqKvvMxRamTxlzofXMex52zYyfI-AWJhCW2djpX-wU7ifWyAx7VLiPDfMq",
				"ljb2eeolRKoywK3zu4EU_OI8doOG3kAyjunNOU96tfy4XbLuUM0Cr_BRlDp4c",
				"HuA2s"),  "sig");
	}

	private static JwtClaims _getJWTClaims(
		String issuer, String subject, URI accessTokenEndpoint) {

		JwtClaims jwtClaims = new JwtClaims();

		jwtClaims.setIssuer(issuer);

		jwtClaims.setIssuedAt(OAuthUtils.getIssuedAt());

		jwtClaims.setExpiryTime(jwtClaims.getIssuedAt() + 3600L);

		jwtClaims.setAudience(accessTokenEndpoint.toString());

		jwtClaims.setSubject(subject);

		return jwtClaims;
	}

	private JWTAssertionUtil() {
	}

	private static final String _KEY_ID = "_createTestRSAKeyPairJSONWebKey01";

}