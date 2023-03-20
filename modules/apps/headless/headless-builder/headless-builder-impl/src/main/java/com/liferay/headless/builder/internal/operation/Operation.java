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

package com.liferay.headless.builder.internal.operation;

import com.liferay.info.field.InfoField;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * @author Carlos Correa
 */
public interface Operation {

	public long getCompanyId();

	public String getKey();

	public String getMethod();

	public String getOperationType();

	public PathConfiguration getPathConfiguration();

	public Response getResponse(String mediaType, int responseCode);

	public static class Builder {

		public Operation build() {
			return new OperationImpl(this);
		}

		public Builder companyId(long companyId) {
			_companyId = companyId;

			return this;
		}

		public Builder method(String method) {
			_method = method;

			return this;
		}

		public Builder operationType(String operationType) {
			_operationType = operationType;

			return this;
		}

		public Builder pathConfiguration(PathConfiguration pathConfiguration) {
			_pathConfiguration = pathConfiguration;

			return this;
		}

		public Builder response(
			Response response, String mediaType, int responseCode) {

			_responses.compute(
				mediaType,
				(key, value) -> {
					if (value == null) {
						value = new HashMap<>();
					}

					value.put(responseCode, response);

					return value;
				});

			return this;
		}

		private long _companyId;
		private String _method;
		private String _operationType;
		private PathConfiguration _pathConfiguration;
		private Map<String, Map<Integer, Response>> _responses =
			new HashMap<>();

	}

	public class OperationImpl implements Operation {

		public OperationImpl(Builder builder) {
			_builder = builder;
		}

		@Override
		public long getCompanyId() {
			return _builder._companyId;
		}

		@Override
		public String getKey() {
			return StringBundler.concat(
				getCompanyId(), StringPool.POUND, getMethod(), StringPool.POUND,
				getPathConfiguration().getPath());
		}

		@Override
		public String getMethod() {
			return _builder._method;
		}

		@Override
		public String getOperationType() {
			return _builder._operationType;
		}

		@Override
		public PathConfiguration getPathConfiguration() {
			return _builder._pathConfiguration;
		}

		@Override
		public Response getResponse(String mediaType, int responseCode) {
			Map<Integer, Response> responses;

			if (Objects.equals(mediaType, "*/*")) {
				if (_builder._responses.containsKey(mediaType)) {
					responses = _builder._responses.get(mediaType);
				}
				else {
					List<String> mediaTypes = ListUtil.sort(
						new ArrayList<>(_builder._responses.keySet()));

					responses = _builder._responses.get(mediaTypes.get(0));
				}
			}
			else {
				responses = _builder._responses.get(mediaType);
			}

			return responses.get(responseCode);
		}

		private final Builder _builder;

	}

	public interface PathConfiguration {

		public String getPath();

		public List<String> getPathParameterNames();

		public Pattern getPattern();

	}

	public class Response {

		public Response(String entityName, Map<String, InfoField> infoFields) {
			_entityName = entityName;
			_infoFields = infoFields;
		}

		public String getEntityName() {
			return _entityName;
		}

		public Map<String, InfoField> getInfoFields() {
			return _infoFields;
		}

		private final String _entityName;
		private final Map<String, InfoField> _infoFields;

	}

}