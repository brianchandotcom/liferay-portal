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
 * @author Carlos Correa
 */
public class Schema {

	public Schema(Builder builder) {
		_builder = builder;
	}

	public String getDescription() {
		return _builder._description;
	}

	public String getExternalReferenceCode() {
		return _builder._externalReferenceCode;
	}

	public String getName() {
		return _builder._name;
	}

	public List<Property> getProperties() {
		return _builder._properties;
	}

	public static class Builder {

		public Schema build() {
			return new Schema(this);
		}

		public Builder setDescription(String description) {
			_description = description;

			return this;
		}

		public Builder setExternalReferenceCode(String externalReferenceCode) {
			_externalReferenceCode = externalReferenceCode;

			return this;
		}

		public Builder setName(String name) {
			_name = name;

			return this;
		}

		public Builder setProperties(List<Property> properties) {
			_properties = properties;

			return this;
		}

		private String _description;
		private String _externalReferenceCode;
		private String _name;
		private List<Property> _properties;

	}

	private final Builder _builder;

}