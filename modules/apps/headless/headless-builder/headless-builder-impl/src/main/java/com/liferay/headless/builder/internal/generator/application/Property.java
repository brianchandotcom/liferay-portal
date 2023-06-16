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

/**
 * @author Carlos Correa
 */
public class Property {

	public Property(Builder builder) {
		_builder = builder;
	}

	public String getDescription() {
		return _builder._description;
	}

	public String getName() {
		return _builder._name;
	}

	public String getType() {
		return _builder._type;
	}

	public static class Builder {

		public Property build() {
			return new Property(this);
		}

		public Builder setDescription(String description) {
			_description = description;

			return this;
		}

		public Builder setName(String name) {
			_name = name;

			return this;
		}

		public Builder setType(String type) {
			_type = type;

			return this;
		}

		private String _description;
		private String _name;
		private String _type;

	}

	private final Builder _builder;

}