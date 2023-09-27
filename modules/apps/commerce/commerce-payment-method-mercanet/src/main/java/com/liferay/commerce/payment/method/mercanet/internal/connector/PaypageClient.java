/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.method.mercanet.internal.connector;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.liferay.portal.kernel.util.Validator;

import com.worldline.sips.model.InitializationResponse;
import com.worldline.sips.model.PaymentRequest;
import com.worldline.sips.model.PaypageResponse;
import com.worldline.sips.util.SealCalculator;

import java.io.IOException;

import java.net.URI;

import java.util.Map;
import java.util.Objects;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

/**
 * @author Luca Pellizzon
 */
public class PaypageClient {

	public PaypageClient(
			Environment environment, String merchantId, Integer keyVersion,
			String secretKey)
		throws Exception {

		if (environment == null) {
			throw new Exception("Invalid environment specified");
		}

		if (Validator.isBlank(merchantId)) {
			throw new Exception("Invalid merchant ID specified");
		}

		if (keyVersion == null) {
			throw new Exception("Invalid key version specified");
		}

		if (Validator.isBlank(secretKey)) {
			throw new Exception("Invalid key specified");
		}

		_environment = environment;
		_merchantId = merchantId;
		_keyVersion = keyVersion;
		_secretKey = secretKey;
	}

	public PaypageResponse decodeResponse(Map<String, String> parameters)
		throws Exception {

		_verifySeal(parameters.get("Data"), parameters.get("Seal"));

		ObjectMapper objectMapper = new ObjectMapper();

		return objectMapper.convertValue(parameters, PaypageResponse.class);
	}

	public InitializationResponse initialize(PaymentRequest paymentRequest)
		throws Exception {

		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

		try (CloseableHttpClient httpClient = httpClientBuilder.build()) {
			paymentRequest.setMerchantId(_merchantId);
			paymentRequest.setKeyVersion(_keyVersion);
			paymentRequest.setSeal(
				SealCalculator.calculate(
					SealCalculator.getSealString(paymentRequest), _secretKey));

			ObjectMapper objectMapper = new ObjectMapper();

			StringEntity requestEntity = new StringEntity(
				objectMapper.writeValueAsString(paymentRequest),
				ContentType.APPLICATION_JSON);

			HttpPost postMethod = new HttpPost(_getEnvironmentUrl());

			postMethod.setEntity(requestEntity);

			CloseableHttpResponse rawResponse = httpClient.execute(postMethod);

			InitializationResponse initializationResponse =
				objectMapper.readValue(
					EntityUtils.toString(rawResponse.getEntity()),
					InitializationResponse.class);

			_verifySeal(initializationResponse);

			return initializationResponse;
		}
		catch (JsonMappingException | JsonParseException exception) {
			throw new Exception(
				"Exception while parsing PaymentRequest", exception);
		}
		catch (IOException ioException) {
			throw new Exception(
				"Exception while processing response from server", ioException);
		}
	}

	private URI _getEnvironmentUrl() {
		return URI.create(_environment.getUrl());
	}

	private void _verifySeal(InitializationResponse initializationResponse)
		throws Exception {

		if (initializationResponse.getSeal() != null) {
			String correctSeal = SealCalculator.calculate(
				SealCalculator.getSealString(initializationResponse),
				_secretKey);

			if (!Objects.equals(
					correctSeal, initializationResponse.getSeal())) {

				throw new Exception(
					"The initialization response has been tampered with");
			}
		}
	}

	private void _verifySeal(String data, String seal) throws Exception {
		String correctSeal = DigestUtils.sha256Hex(data + _secretKey);

		if (!Objects.equals(correctSeal, seal)) {
			throw new Exception(
				"The payment page response has been tampered with");
		}
	}

	private final Environment _environment;
	private final Integer _keyVersion;
	private final String _merchantId;
	private final String _secretKey;

}