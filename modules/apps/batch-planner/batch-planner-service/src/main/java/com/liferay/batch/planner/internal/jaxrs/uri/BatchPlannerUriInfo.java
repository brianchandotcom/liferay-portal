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

package com.liferay.batch.planner.internal.jaxrs.uri;

import java.net.URI;

import java.util.List;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

/**
 * @author Igor Beslic
 */
public class BatchPlannerUriInfo implements UriInfo {

	@Override
	public URI getAbsolutePath() {
		return null;
	}

	@Override
	public UriBuilder getAbsolutePathBuilder() {
		return null;
	}

	@Override
	public URI getBaseUri() {
		return null;
	}

	@Override
	public UriBuilder getBaseUriBuilder() {
		return null;
	}

	@Override
	public List<Object> getMatchedResources() {
		return null;
	}

	@Override
	public List<String> getMatchedURIs() {
		return null;
	}

	@Override
	public List<String> getMatchedURIs(boolean decode) {
		return null;
	}

	@Override
	public String getPath() {
		return null;
	}

	@Override
	public String getPath(boolean decode) {
		return null;
	}

	@Override
	public MultivaluedMap<String, String> getPathParameters() {
		return _pathParameters;
	}

	@Override
	public MultivaluedMap<String, String> getPathParameters(boolean decode) {
		return null;
	}

	@Override
	public List<PathSegment> getPathSegments() {
		return null;
	}

	@Override
	public List<PathSegment> getPathSegments(boolean decode) {
		return null;
	}

	@Override
	public MultivaluedMap<String, String> getQueryParameters() {
		return _queryParameters;
	}

	@Override
	public MultivaluedMap<String, String> getQueryParameters(boolean decode) {
		return null;
	}

	@Override
	public URI getRequestUri() {
		return null;
	}

	@Override
	public UriBuilder getRequestUriBuilder() {
		return null;
	}

	@Override
	public URI relativize(URI uri) {
		return null;
	}

	@Override
	public URI resolve(URI uri) {
		return null;
	}

	public static class Builder {

		public BatchPlannerUriInfo build() {
			return new BatchPlannerUriInfo(this);
		}

		public Builder delimiter(String delimiter) {
			_queryParameters.putSingle("delimiter", delimiter);

			return this;
		}

		public Builder pathParameter(String name, String value) {
			_pathParameters.putSingle(name, value);

			return this;
		}

		public Builder queryParameter(String name, String value) {
			_queryParameters.putSingle(name, value);

			return this;
		}

		public Builder taskItemDelegateName(String taskItemDelegateName) {
			_pathParameters.putSingle(
				"taskItemDelegateName", taskItemDelegateName);

			return this;
		}

		private final MultivaluedHashMap<String, String> _pathParameters =
			new MultivaluedHashMap<>();
		private final MultivaluedHashMap<String, String> _queryParameters =
			new MultivaluedHashMap<>();

	}

	private BatchPlannerUriInfo(Builder builder) {
		_pathParameters = builder._pathParameters;
		_queryParameters = builder._queryParameters;
	}

	private final MultivaluedHashMap<String, String> _pathParameters;
	private final MultivaluedHashMap<String, String> _queryParameters;

}