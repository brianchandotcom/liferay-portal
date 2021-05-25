/**
 * Copyright (c) 2000-2021 Liferay, Inc. All rights reserved.
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

package com.liferay.mule.internal.connection;

import java.io.InputStream;

import java.util.HashMap;
import java.util.Map;

import org.mule.runtime.api.util.MultiMap;

/**
 * @author Matija Petanjek
 */
public class ResourceContext {

	public byte[] getBytes() {
		return bytes;
	}

	public long getConnectionTimeout() {
		return connectionTimeout;
	}

	public String getContentType() {
		return contentType;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public String getJaxRSAppBase() {
		return jaxRSAppBase;
	}

	public Map<String, String> getPathParams() {
		return pathParams;
	}

	public MultiMap<String, String> getQueryParams() {
		return queryParams;
	}

	public static class Builder {

		public ResourceContext build() {
			ResourceContext resourceContext = new ResourceContext();

			resourceContext.bytes = bytes;
			resourceContext.connectionTimeout = connectionTimeout;
			resourceContext.contentType = contentType;
			resourceContext.endpoint = endpoint;
			resourceContext.inputStream = inputStream;
			resourceContext.jaxRSAppBase = jaxRSAppBase;
			resourceContext.pathParams = pathParams;
			resourceContext.queryParams = queryParams;

			return resourceContext;
		}

		public Builder bytes(byte[] bytes) {
			this.bytes = bytes;

			return this;
		}

		public Builder connectionTimeout(long connectionTimeout) {
			this.connectionTimeout = connectionTimeout;

			return this;
		}

		public Builder contentType(String contentType) {
			this.contentType = contentType;

			return this;
		}

		public Builder endpoint(String endpoint) {
			this.endpoint = endpoint;

			return this;
		}

		public Builder inputStream(InputStream inputStream) {
			this.inputStream = inputStream;

			return this;
		}

		public Builder jaxRSAppBase(String jaxRSAppBase) {
			this.jaxRSAppBase = jaxRSAppBase;

			return this;
		}

		public Builder pathParams(Map<String, String> pathParams) {
			this.pathParams = pathParams;

			return this;
		}

		public Builder queryParams(MultiMap<String, String> queryParams) {
			this.queryParams = queryParams;

			return this;
		}

		private byte[] bytes;
		private long connectionTimeout;
		private String contentType = "application/json";
		private String endpoint;
		private InputStream inputStream;
		private String jaxRSAppBase;
		private Map<String, String> pathParams = new HashMap<>();
		private MultiMap<String, String> queryParams = new MultiMap<>();

	}

	private ResourceContext() {
	}

	private byte[] bytes;
	private long connectionTimeout;
	private String contentType;
	private String endpoint;
	private InputStream inputStream;
	private String jaxRSAppBase;
	private Map<String, String> pathParams;
	private MultiMap<String, String> queryParams;

}