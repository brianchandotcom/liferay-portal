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

package com.liferay.petra.apache.http.components;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.Map;

/**
 * @author Hugo Huijser
 */
public class URIBuilder {

	public static URIBuilderWrapper create(String string)
		throws URISyntaxException {

		return new URIBuilderWrapper(new URI(string));
	}

	public static URIBuilderWrapper create(URI uri) {
		return new URIBuilderWrapper(uri);
	}

	public static final class URIBuilderWrapper {

		public URIBuilderWrapper(URI uri) {
			_uriBuilder = new org.apache.http.client.utils.URIBuilder(uri);
		}

		public URIBuilderWrapper addParameter(String name, String value) {
			if (value != null) {
				_uriBuilder.addParameter(name, value);
			}

			return this;
		}

		public URIBuilderWrapper addParameter(
			String name,
			UnsafeSupplier<String, Exception> valueUnsafeSupplier) {

			if (valueUnsafeSupplier == null) {
				return this;
			}

			try {
				String value = valueUnsafeSupplier.get();

				if (value != null) {
					_uriBuilder.addParameter(name, value);
				}

				return this;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		}

		public URIBuilderWrapper addParameter(
			UnsafeSupplier<String, Exception> nameUnsafeSupplier,
			String value) {

			try {
				String name = nameUnsafeSupplier.get();

				if (name != null) {
					_uriBuilder.addParameter(name, value);
				}

				return this;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		}

		public URIBuilderWrapper addParameter(
			UnsafeSupplier<String, Exception> nameUnsafeSupplier,
			UnsafeSupplier<String, Exception> valueUnsafeSupplier) {

			try {
				if ((nameUnsafeSupplier != null) &&
					(valueUnsafeSupplier != null)) {

					String name = nameUnsafeSupplier.get();
					String value = valueUnsafeSupplier.get();

					if ((name != null) && (value != null)) {
						_uriBuilder.addParameter(name, value);
					}
				}

				return this;
			}
			catch (Exception exception) {
				throw new RuntimeException(exception);
			}
		}

		public URIBuilderWrapper addParameters(
			Map<String, String> parameterMap) {

			for (Map.Entry<String, String> entry : parameterMap.entrySet()) {
				_uriBuilder.addParameter(entry.getKey(), entry.getValue());
			}

			return this;
		}

		public URI build() throws URISyntaxException {
			return _uriBuilder.build();
		}

		private final org.apache.http.client.utils.URIBuilder _uriBuilder;

	}

	@FunctionalInterface
	private interface UnsafeSupplier<String, E extends Throwable> {

		public String get() throws E;

	}

}