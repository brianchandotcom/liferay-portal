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

import java.util.List;

/**
 * @author Luis Miguel Barcos
 */
public class ApiApplication {

	public String getBaseURL() {
		return _builder._baseURL;
	}

	public long getCompanyId() {
		return _builder._companyId;
	}

	public String getDescription() {
		return _builder._description;
	}

	public List<Endpoint> getEndpoints() {
		return _builder._endpoints;
	}

	public String getOsgiJaxRsName() {
		return _builder._osgiJaxRsName;
	}

	public List<Schema> getSchemas() {
		return _builder._schemas;
	}

	public String getTitle() {
		return _builder._title;
	}

	public String getVersion() {
		return _builder._version;
	}

	// TODO Implements a Builder that requires required fields.

	public static class Builder {

		public ApiApplication build() {
			return new ApiApplication(this);
		}

		public Builder setBaseURL(String baseURL) {
			_baseURL = baseURL;

			return this;
		}

		public Builder setCompanyId(long companyId) {
			_companyId = companyId;

			return this;
		}

		public Builder setDescription(String description) {
			_description = description;

			return this;
		}

		public Builder setEndpoints(List<Endpoint> endpoints) {
			_endpoints = endpoints;

			return this;
		}

		public Builder setOsgiJaxRsName(String osgiJaxRsName) {
			_osgiJaxRsName = osgiJaxRsName;

			return this;
		}

		public Builder setSchemas(List<Schema> schemas) {
			_schemas = schemas;

			return this;
		}

		public Builder setTitle(String title) {
			_title = title;

			return this;
		}

		public Builder setVersion(String version) {
			_version = version;

			return this;
		}

		private String _baseURL;
		private long _companyId;
		private String _description;
		private List<Endpoint> _endpoints;
		private String _osgiJaxRsName;
		private List<Schema> _schemas;
		private String _title;
		private String _version;

	}

	private ApiApplication(Builder builder) {
		_builder = builder;
	}

	private final Builder _builder;

}