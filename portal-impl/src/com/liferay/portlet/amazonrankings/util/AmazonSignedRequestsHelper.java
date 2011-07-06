package com.liferay.portlet.amazonrankings.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.SignatureException;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import sun.misc.BASE64Encoder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import jodd.util.StringPool;

public class AmazonSignedRequestsHelper {

	private static final String UTF8_CHARSET = "UTF-8";
	private static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";
	private static final String REQUEST_URI = "/onca/xml";
	private static final String REQUEST_METHOD = "GET";
	private static final String ECS_ENDPOINT = "ecs.amazonaws.com";

	/**
	 * Computes RFC 2104-compliant HMAC signature.
	 *
	 * @param data data to be signed.
	 * @return base64-encoded RFC 2104-compliant HMAC signature.
	 * @throws java.security.SignatureException
	 *          when signature generation fails
	 */
	private static String generateSignature(String data)
		throws java.security.SignatureException {
		
		String result = null;
		try {
			SecretKeySpec signingKey =
				new SecretKeySpec(AmazonRankingsUtil.getAmazonSecretAccessKey().getBytes(),
						HMAC_SHA256_ALGORITHM);

			Mac mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
			mac.init(signingKey);

			byte[] rawHmac = mac.doFinal(data.getBytes());

			result = new BASE64Encoder().encode(rawHmac);

		} catch (Exception e) {
			_log.error("Failed to generate HMAC : " + e.getMessage());
		}

		return result.replace("+", "%2B").replace("=", "%3D");
	}

	public static String generateUrlWithSignature(Map<String, String> params) {

		SortedMap<String, String> sortedParamMap =
			new TreeMap<String, String>(params);

		String canonicalQS = canonicalize(sortedParamMap);
		
		String toSign =
			REQUEST_METHOD + StringPool.NEWLINE
			+ ECS_ENDPOINT + StringPool.NEWLINE
			+ REQUEST_URI + StringPool.NEWLINE
			+ canonicalQS;

		String url = null;
		try {
			String signature = generateSignature(toSign);
			url = "http://" + ECS_ENDPOINT + REQUEST_URI + StringPool.QUESTION_MARK +
				canonicalQS + StringPool.AMPERSAND + "Signature" + StringPool.EQUALS + signature;
		} catch (SignatureException e) {
			_log.error("Failed to generate signature : " + e.getMessage());
		}

		return url;
	}


	private static String canonicalize(SortedMap<String, String> sortedParamMap) {
		
		if (sortedParamMap.isEmpty()) {
			return StringPool.EMPTY;
		}

		StringBuffer buffer = new StringBuffer();
		Iterator<Map.Entry<String, String>> parameterIterator =
			sortedParamMap.entrySet().iterator();

		while (parameterIterator.hasNext()) {
			Map.Entry<String, String> kvpair = parameterIterator.next();
			buffer.append(percentEncodeRfc3986(kvpair.getKey()));
			buffer.append(StringPool.EQUALS);
			buffer.append(percentEncodeRfc3986(kvpair.getValue()));
			if (parameterIterator.hasNext()) {
				buffer.append(StringPool.AMPERSAND);
			}
		}
		return buffer.toString();
	}

	private static String percentEncodeRfc3986(String inputString) {
		String encodedString = null;
		try {
			encodedString = URLEncoder.encode(inputString, UTF8_CHARSET)
			.replace(StringPool.PLUS, "%2B")
			.replace(StringPool.ASTERISK, "%2A")
			.replace("%7E", "~");
		} catch (UnsupportedEncodingException e) {
			encodedString = inputString;
		}
		return encodedString;
	}
	
	private static Log _log = LogFactoryUtil.getLog(AmazonSignedRequestsHelper.class);

}