/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.util.spring.boot;

import java.util.Objects;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import reactor.core.Disposable;
import reactor.core.publisher.Mono;

/**
 * @author Nilton Vieira
 */
public abstract class BaseRestController {

	protected Disposable asyncDelete(
		String authorization, String bodyValue, String path) {

		return getWebClient(
		).method(
			HttpMethod.DELETE
		).uri(
			uriBuilder -> uriBuilder.path(
				path
			).build()
		).header(
			HttpHeaders.AUTHORIZATION, authorization
		).bodyValue(
			bodyValue
		).exchangeToMono(
			getExchangeToMono()
		).subscribe();
	}

	protected Disposable asyncGet(String authorization, String path) {
		return getWebClient(
		).get(
		).uri(
			uriBuilder -> uriBuilder.path(
				path
			).build()
		).header(
			HttpHeaders.AUTHORIZATION, authorization
		).exchangeToMono(
			getExchangeToMono()
		).subscribe();
	}

	protected Disposable asyncPatch(
		String authorization, String body, String path) {

		return getWebClient(
		).patch(
		).uri(
			uriBuilder -> uriBuilder.path(
				path
			).build()
		).bodyValue(
			body
		).header(
			HttpHeaders.AUTHORIZATION, authorization
		).exchangeToMono(
			getExchangeToMono()
		).subscribe();
	}

	protected Disposable asyncPost(
		String authorization, String body, String path) {

		return getWebClient(
		).post(
		).uri(
			uriBuilder -> uriBuilder.path(
				path
			).build()
		).bodyValue(
			body
		).header(
			HttpHeaders.AUTHORIZATION, authorization
		).exchangeToMono(
			getExchangeToMono()
		).subscribe();
	}

	protected Disposable asyncPut(
		String authorization, String bodyValue, String path) {

		return getWebClient(
		).put(
		).uri(
			uriBuilder -> uriBuilder.path(
				path
			).build()
		).header(
			HttpHeaders.AUTHORIZATION, authorization
		).bodyValue(
			bodyValue
		).exchangeToMono(
			getExchangeToMono()
		).subscribe();
	}

	protected String delete(
		String authorization, String bodyValue, String path) {

		return getWebClient(
		).method(
			HttpMethod.DELETE
		).uri(
			uriBuilder -> uriBuilder.path(
				path
			).build()
		).header(
			HttpHeaders.AUTHORIZATION, authorization
		).bodyValue(
			bodyValue
		).exchangeToMono(
			getExchangeToMono()
		).block();
	}

	protected String get(String authorization, String path) {
		return getWebClient(
		).get(
		).uri(
			uriBuilder -> uriBuilder.path(
				path
			).build()
		).header(
			HttpHeaders.AUTHORIZATION, authorization
		).exchangeToMono(
			getExchangeToMono()
		).block();
	}

	protected Function<ClientResponse, Mono<String>> getExchangeToMono() {
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

	protected WebClient getWebClient() {
		if (_webClient != null) {
			return _webClient;
		}

		return WebClient.builder(
		).baseUrl(
			_lxcDXPServerProtocol + "://" + _lxcDXPMainDomain
		).defaultHeader(
			HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE
		).defaultHeader(
			HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE
		).build();
	}

	protected String patch(String authorization, String body, String path) {
		return getWebClient(
		).patch(
		).uri(
			uriBuilder -> uriBuilder.path(
				path
			).build()
		).bodyValue(
			body
		).header(
			HttpHeaders.AUTHORIZATION, authorization
		).exchangeToMono(
			getExchangeToMono()
		).block();
	}

	protected String post(String authorization, String body, String path) {
		return getWebClient(
		).post(
		).uri(
			uriBuilder -> uriBuilder.path(
				path
			).build()
		).bodyValue(
			body
		).header(
			HttpHeaders.AUTHORIZATION, authorization
		).exchangeToMono(
			getExchangeToMono()
		).block();
	}

	protected String put(String authorization, String bodyValue, String path) {
		return getWebClient(
		).put(
		).uri(
			uriBuilder -> uriBuilder.path(
				path
			).build()
		).header(
			HttpHeaders.AUTHORIZATION, authorization
		).bodyValue(
			bodyValue
		).exchangeToMono(
			getExchangeToMono()
		).block();
	}

	protected void setWebClient(WebClient webClient) {
		_webClient = webClient;
	}

	@Value("${com.liferay.lxc.dxp.mainDomain}")
	private String _lxcDXPMainDomain;

	@Value("${com.liferay.lxc.dxp.server.protocol}")
	private String _lxcDXPServerProtocol;

	private WebClient _webClient;

}