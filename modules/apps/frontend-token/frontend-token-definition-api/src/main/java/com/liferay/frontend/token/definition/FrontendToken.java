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

package com.liferay.frontend.token.definition;

import java.util.Collection;
import java.util.Locale;
import java.util.Objects;

/**
 * @author Iván Zaera
 */
public interface FrontendToken {

	/**
	 * Get the default token value
	 *
	 * @see Type for a list of valid token values
	 */
	public <T> T getDefaultValue();

	public Collection<FrontendTokenMapping> getFrontendTokenMappings();

	public FrontendTokenSet getFrontendTokenSet();

	public String getJSON(Locale locale);

	public Type getType();

	public static enum Type {

		BOOLEAN("Boolean"), DOUBLE("Number"), INT("Integer"), STRING("String");

		public static Type parse(String value) {
			for (Type type : values()) {
				if (Objects.equals(type.getValue(), value)) {
					return type;
				}
			}

			throw new IllegalArgumentException("Unknown value: " + value);
		}

		public String getValue() {
			return _value;
		}

		@Override
		public String toString() {
			return _value;
		}

		private Type(String value) {
			_value = value;
		}

		private final String _value;

	}

}