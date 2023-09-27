/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.token.definition;

import com.liferay.portal.kernel.json.JSONObject;

import java.util.Collection;
import java.util.Locale;
import java.util.Objects;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Iván Zaera
 */
@ProviderType
public interface FrontendToken {

	public <T> T getDefaultValue();

	public Collection<FrontendTokenMapping> getFrontendTokenMappings();

	public Collection<FrontendTokenMapping> getFrontendTokenMappings(
		String type);

	public FrontendTokenSet getFrontendTokenSet();

	public JSONObject getJSONObject(Locale locale);

	public String getName();

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