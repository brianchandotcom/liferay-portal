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

package com.liferay.headless.builder.internal.generator.application;

import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Luis Miguel Barcos
 */
public class Endpoint {

	public Endpoint(Builder builder) {
		_builder = builder;
	}

	public Http.Method getMethod() {
		return _builder._method;
	}

	public String getPath() {
		return _builder._path;
	}

	public Schema getRequestSchema() {
		return _builder._requestSchema;
	}

	public Schema getResponseSchema() {
		return _builder._responseSchema;
	}

	public Scope getScope() {
		return _builder._scope;
	}

	public static class Builder {

		public Endpoint build() {
			return new Endpoint(this);
		}

		public Builder setMethod(String method) {
			_method = Http.Method.valueOf(StringUtil.toUpperCase(method));

			return this;
		}

		public Builder setPath(String path) {
			_path = path;

			return this;
		}

		public Builder setRequestSchema(Schema requestSchema) {
			_requestSchema = requestSchema;

			return this;
		}

		public Builder setResponseSchema(Schema responseSchema) {
			_responseSchema = responseSchema;

			return this;
		}

		public Builder setScope(String scope) {
			_scope = Scope.valueOf(StringUtil.toUpperCase(scope));

			return this;
		}

		private Http.Method _method;
		private String _path;
		private Schema _requestSchema;
		private Schema _responseSchema;
		private Scope _scope;

	}

	public enum Scope {

		COMPANY, GROUP

	}

	private final Builder _builder;

}