/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.util.spring.boot.service;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import org.apache.commons.logging.Log;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import reactor.core.Disposable;
import reactor.core.publisher.Mono;

/**
 * @author Nilton Vieira
 */
public abstract class BaseService {

	protected Disposable asyncDelete(
		String authorization, String body, String path) {

		return _getWebClient(
		).method(
			HttpMethod.DELETE
		).uri(
			path
		).header(
			HttpHeaders.AUTHORIZATION, authorization
		).bodyValue(
			body
		).exchangeToMono(
			_getExchangeToMonoFunction()
		).subscribe();
	}

	protected Disposable asyncGet(String authorization, String path) {
		return _getWebClient(
		).get(
		).uri(
			path
		).header(
			HttpHeaders.AUTHORIZATION, authorization
		).exchangeToMono(
			_getExchangeToMonoFunction()
		).subscribe();
	}

	protected Disposable asyncPatch(
		String authorization, String body, String path) {

		return _getWebClient(
		).patch(
		).uri(
			path
		).bodyValue(
			body
		).header(
			HttpHeaders.AUTHORIZATION, authorization
		).exchangeToMono(
			_getExchangeToMonoFunction()
		).subscribe();
	}

	protected Disposable asyncPost(
		String authorization, String body, String path) {

		return _getWebClient(
		).post(
		).uri(
			path
		).bodyValue(
			body
		).header(
			HttpHeaders.AUTHORIZATION, authorization
		).exchangeToMono(
			_getExchangeToMonoFunction()
		).subscribe();
	}

	protected Disposable asyncPut(
		String authorization, String body, String path) {

		return _getWebClient(
		).put(
		).uri(
			path
		).header(
			HttpHeaders.AUTHORIZATION, authorization
		).bodyValue(
			body
		).exchangeToMono(
			_getExchangeToMonoFunction()
		).subscribe();
	}

	protected String delete(String authorization, String body, String path) {
		return _getWebClient(
		).method(
			HttpMethod.DELETE
		).uri(
			path
		).header(
			HttpHeaders.AUTHORIZATION, authorization
		).bodyValue(
			body
		).exchangeToMono(
			_getExchangeToMonoFunction()
		).block();
	}

	protected String get(String authorization, String path) {
		return _getWebClient(
		).get(
		).uri(
			path
		).header(
			HttpHeaders.AUTHORIZATION, authorization
		).exchangeToMono(
			_getExchangeToMonoFunction()
		).block();
	}

	protected String getWebClientBaseURL() {
		return lxcDXPServerProtocol + "://" + lxcDXPMainDomain;
	}

	protected ExchangeFilterFunction getWebClientExchangeFilterFunction() {
		return (clientRequest, exchangeFunction) -> exchangeFunction.exchange(
			clientRequest);
	}

	protected void log(Jwt jwt, Log log) {
		if (log.isInfoEnabled()) {
			log.info("JWT Claims: " + jwt.getClaims());
			log.info("JWT ID: " + jwt.getId());
			log.info("JWT Subject: " + jwt.getSubject());
		}
	}

	protected void log(Jwt jwt, Log log, Map<String, String> parameters) {
		if (log.isInfoEnabled()) {
			log.info("JWT Claims: " + jwt.getClaims());
			log.info("JWT ID: " + jwt.getId());
			log.info("JWT Subject: " + jwt.getSubject());
			log.info("Parameters: " + parameters);
		}
	}

	protected void log(Jwt jwt, Log log, String json) {
		if (log.isInfoEnabled()) {
			try {
				JSONObject jsonObject = new JSONObject(json);

				log.info("JSON: " + jsonObject.toString(4));
			}
			catch (Exception exception) {
				log.error("JSON: " + json, exception);
			}

			log.info("JWT Claims: " + jwt.getClaims());
			log.info("JWT ID: " + jwt.getId());
			log.info("JWT Subject: " + jwt.getSubject());
		}
	}

	protected String patch(String authorization, String body, String path) {
		return _getWebClient(
		).patch(
		).uri(
			path
		).bodyValue(
			body
		).header(
			HttpHeaders.AUTHORIZATION, authorization
		).exchangeToMono(
			_getExchangeToMonoFunction()
		).block();
	}

	protected String post(String authorization, String body, String path) {
		return _getWebClient(
		).post(
		).uri(
			path
		).bodyValue(
			body
		).header(
			HttpHeaders.AUTHORIZATION, authorization
		).exchangeToMono(
			_getExchangeToMonoFunction()
		).block();
	}

	protected String put(String authorization, String body, String path) {
		return _getWebClient(
		).put(
		).uri(
			path
		).header(
			HttpHeaders.AUTHORIZATION, authorization
		).bodyValue(
			body
		).exchangeToMono(
			_getExchangeToMonoFunction()
		).block();
	}

	@Value("${com.liferay.lxc.dxp.mainDomain}")
	protected String lxcDXPMainDomain;

	@Value("${com.liferay.lxc.dxp.server.protocol}")
	protected String lxcDXPServerProtocol;

	private Function<ClientResponse, Mono<String>>
		_getExchangeToMonoFunction() {

		return clientResponse -> {
			HttpStatus httpStatus = clientResponse.statusCode();

			if (Objects.equals(
					clientResponse.statusCode(), HttpStatus.NO_CONTENT)) {

				return Mono.just("{}");
			}
			else if (httpStatus.is2xxSuccessful()) {
				return clientResponse.bodyToMono(String.class);
			}
			else if (httpStatus.is4xxClientError()) {
				return Mono.just(httpStatus.getReasonPhrase());
			}

			Mono<WebClientResponseException> mono =
				clientResponse.createException();

			return mono.flatMap(Mono::error);
		};
	}

	private WebClient _getWebClient() {
		return WebClient.builder(
		).baseUrl(
			getWebClientBaseURL()
		).defaultHeader(
			HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE
		).defaultHeader(
			HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE
		).exchangeStrategies(
			ExchangeStrategies.builder(
			).codecs(
				clientCodecConfigurer -> clientCodecConfigurer.defaultCodecs(
				).maxInMemorySize(
					16 * 1024 * 1024
				)
			).build()
		).filter(
			getWebClientExchangeFilterFunction()
		).build();
	}

}