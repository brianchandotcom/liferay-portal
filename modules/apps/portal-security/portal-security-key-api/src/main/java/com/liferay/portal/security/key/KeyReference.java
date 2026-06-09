/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.key;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.io.Serializable;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Tomas Polesovsky
 * @author Christopher Kian
 */
public class KeyReference implements Serializable {

	public static final String ANY_PROVIDER = StringPool.STAR;

	public static KeyReference fromString(String value) {
		if (value == null) {
			return null;
		}

		Matcher matcher = _pattern.matcher(value);

		if (!matcher.matches()) {
			return null;
		}

		Type type;
		String typeString = matcher.group(1);

		if (Objects.equals(typeString, "keyRef")) {
			type = Type.CRYPTO;
		}
		else if (Objects.equals(typeString, "secretRef")) {
			type = Type.SECRET;
		}
		else {
			return null;
		}

		String identifier = matcher.group(3);
		String providerId = matcher.group(2);

		return new KeyReference(identifier, providerId, type);
	}

	public static boolean isKeyReference(String value) {
		if (value == null) {
			return false;
		}

		Matcher matcher = _pattern.matcher(value);

		return matcher.matches();
	}

	public KeyReference(String identifier, String providerId, Type type) {
		if (identifier == null) {
			throw new IllegalArgumentException("Identifier must not be null");
		}

		if (identifier.isEmpty()) {
			throw new IllegalArgumentException("Identifier must not be empty");
		}

		if (providerId == null) {
			throw new IllegalArgumentException("Provider ID must not be null");
		}

		if (providerId.isEmpty()) {
			throw new IllegalArgumentException("Provider ID must not be empty");
		}

		if ((providerId.indexOf(CharPool.COLON) >= 0) ||
			(providerId.indexOf(CharPool.CLOSE_CURLY_BRACE) >= 0)) {

			throw new IllegalArgumentException(
				"Provider ID must not contain colons or closing curly braces");
		}

		if (type == null) {
			throw new IllegalArgumentException("Type must not be null");
		}

		_identifier = identifier;
		_providerId = providerId;
		_type = type;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof KeyReference)) {
			return false;
		}

		KeyReference keyReference = (KeyReference)object;

		if (Objects.equals(_identifier, keyReference._identifier) &&
			Objects.equals(_providerId, keyReference._providerId) &&
			(_type == keyReference._type)) {

			return true;
		}

		return false;
	}

	public String getIdentifier() {
		return _identifier;
	}

	public String getProviderId() {
		return _providerId;
	}

	public Type getType() {
		return _type;
	}

	@Override
	public int hashCode() {
		return Objects.hash(_identifier, _providerId, _type);
	}

	@Override
	public String toString() {
		String typeString = "keyRef";

		if (_type == Type.SECRET) {
			typeString = "secretRef";
		}

		return StringBundler.concat(
			"${", typeString, ":", _providerId, ":", _identifier, "}");
	}

	public enum Type {

		CRYPTO, SECRET

	}

	private static final Pattern _pattern = Pattern.compile(
		"\\$\\{(keyRef|secretRef):([^:]+):(.+)\\}");
	private static final long serialVersionUID = 1L;

	private final String _identifier;
	private final String _providerId;
	private final Type _type;

}