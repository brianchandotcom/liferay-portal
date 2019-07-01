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

package com.liferay.amazon.rankings.web.internal.util;

import com.liferay.amazon.rankings.web.internal.configuration.AmazonRankingsConfiguration;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.net.URLEncoder;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author Barrie Selack
 * @author Brian Wing Shun Chan
 */
public class AmazonSignedRequestsUtil {

	public static String generateUrlWithSignature(
			AmazonRankingsConfiguration amazonRankingsConfiguration,
			Map<String, String> parameters)
		throws Exception {

		String canonicalizedParameters = _canonicalizeParameters(parameters);

		String signature = _generateSignature(
			amazonRankingsConfiguration,
			"GET\necs.amazonaws.com\n/onca/xml\n" + canonicalizedParameters);

		return StringBundler.concat(
			"http://ecs.amazonaws.com/onca/xml?", canonicalizedParameters,
			"&Signature=", signature);
	}

	private static String _canonicalizeParameters(
			Map<String, String> parameters)
		throws Exception {

		if (parameters.isEmpty()) {
			return StringPool.BLANK;
		}

		parameters = new TreeMap<>(parameters);

		Set<Map.Entry<String, String>> parametersSet = parameters.entrySet();

		StringBundler sb = new StringBundler(parametersSet.size() * 4);

		for (Map.Entry<String, String> parameter : parametersSet) {
			sb.append(_rfc3986Encode(parameter.getKey()));
			sb.append(StringPool.EQUAL);
			sb.append(_rfc3986Encode(parameter.getValue()));
			sb.append(StringPool.AMPERSAND);
		}

		sb.setIndex(sb.index() - 1);

		return sb.toString();
	}

	private static String _generateSignature(
			AmazonRankingsConfiguration amazonRankingsConfiguration,
			String data)
		throws Exception {

		if (Validator.isNull(
				amazonRankingsConfiguration.amazonSecretAccessKey())) {

			return StringPool.BLANK;
		}

		String amazonSecretAccessKey =
			amazonRankingsConfiguration.amazonSecretAccessKey();

		SecretKeySpec secretKeySpec = new SecretKeySpec(
			amazonSecretAccessKey.getBytes(), _HMAC_SHA256_ALGORITHM);

		Mac mac = Mac.getInstance(_HMAC_SHA256_ALGORITHM);

		mac.init(secretKeySpec);

		byte[] bytes = mac.doFinal(data.getBytes());

		String signature = Base64.encode(bytes);

		return StringUtil.replace(
			signature, new char[] {CharPool.EQUAL, CharPool.PLUS},
			new String[] {"%3D", "%2B"});
	}

	private static String _rfc3986Encode(String string) throws Exception {
		if (Validator.isNull(string)) {
			return StringPool.BLANK;
		}

		string = URLEncoder.encode(string, StringPool.UTF8);

		string = StringUtil.replace(
			string, new String[] {StringPool.STAR, StringPool.PLUS, "%7E"},
			new String[] {"%2A", "%2B", "~"});

		return string;
	}

	private static final String _HMAC_SHA256_ALGORITHM = "HmacSHA256";

}