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

package com.liferay.portal.apio.test.util;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.ValidatableResponse;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Cristina González
 */
public class ApioClientBuilder {

	public static RequestSpecification given() {
		return new RequestSpecification(
			_withoutAuthentication, Collections.emptyMap());
	}

	public static class RequestSpecification {

		public RequestSpecification(
			Authentication authentication, Map<String, String> headers) {

			_authentication = authentication;
			_headers = headers;
		}

		public RequestSpecification basicAuth(String user, String password) {
			return new RequestSpecification(
				new BasicAuthentication(user, password), _headers);
		}

		public RequestSpecification header(String name, String value) {
			HashMap<String, String> headers = new HashMap<>(_headers);

			headers.put(name, value);

			return new RequestSpecification(_authentication, headers);
		}

		public Response when() {
			return new Response(null, this);
		}

		protected io.restassured.specification.RequestSpecification
			getRestAssuredRequestSpecification() {

			io.restassured.specification.RequestSpecification
				requestSpecification = _authentication.auth(
					RestAssured.given());

			return requestSpecification.headers(_headers);
		}

		private final Authentication _authentication;
		private final Map<String, String> _headers;

	}

	public static class Response {

		public Response(
			ValidatableResponse validatableResponse,
			RequestSpecification requestSpecification) {

			_validatableResponse = validatableResponse;
			_requestSpecification = requestSpecification;
		}

		public Response follow(String jsonPath) {
			ExtractableResponse extractableResponse =
				_validatableResponse.extract();

			return get(extractableResponse.path(jsonPath));
		}

		public Response get(String url) {
			io.restassured.specification.RequestSpecification
				requestSpecification =
					_requestSpecification.getRestAssuredRequestSpecification();

			io.restassured.response.Response response =
				requestSpecification.get(url);

			return new Response(response.then(), _requestSpecification);
		}

		public ValidatableResponse then() {
			return _validatableResponse;
		}

		private final RequestSpecification _requestSpecification;
		private final ValidatableResponse _validatableResponse;

	}

	public interface Authentication {

		public io.restassured.specification.RequestSpecification auth(
			io.restassured.specification.RequestSpecification
				requestSpecification);

	}

	protected static class BasicAuthentication implements Authentication {

		@Override
		public io.restassured.specification.RequestSpecification auth(
			io.restassured.specification.RequestSpecification
				requestSpecification) {

			return requestSpecification.auth().basic(_user, _password);
		}

		protected BasicAuthentication(String user, String password) {
			_user = user;
			_password = password;
		}

		private final String _password;
		private final String _user;

	}

	protected static class WithoutAuthentication implements Authentication {

		@Override
		public io.restassured.specification.RequestSpecification auth(
			io.restassured.specification.RequestSpecification
				requestSpecification) {

			return requestSpecification;
		}

	}

	private static final WithoutAuthentication _withoutAuthentication =
		new WithoutAuthentication();

}