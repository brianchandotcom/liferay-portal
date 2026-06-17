/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.key;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.Objects;

/**
 * @author Tomas Polesovsky
 * @author Christopher Kian
 */
public class KeyReference implements Serializable {

	public KeyReference(String identifier, String providerId, Type type) {
		if (Validator.isNull(identifier)) {
			throw new IllegalArgumentException("Identifier is null");
		}

		if (Validator.isNull(providerId)) {
			throw new IllegalArgumentException("Provider ID is null");
		}

		if ((providerId.indexOf(CharPool.CLOSE_CURLY_BRACE) >= 0) ||
			(providerId.indexOf(CharPool.COLON) >= 0)) {

			throw new IllegalArgumentException(
				"Provider ID must not contain colons or a closing curly brace");
		}

		if (type == null) {
			throw new IllegalArgumentException("Type is null");
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
			"{identifier=", _identifier, ", providerId=", _providerId,
			", type=", typeString, "}");
	}

	public enum Type {

		CRYPTO, SECRET

	}

	private static final long serialVersionUID = 1L;

	private final String _identifier;
	private final String _providerId;
	private final Type _type;

}