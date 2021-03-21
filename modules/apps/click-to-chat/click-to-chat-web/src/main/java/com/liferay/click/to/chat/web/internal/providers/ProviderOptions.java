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

package com.liferay.click.to.chat.web.internal.providers;

import java.util.Objects;

/**
 * @author José Abelenda
 */
public enum ProviderOptions {

	CHATWOOT("CHATWOOT"), CRISP("CRISP"), JIVOCHAT("JIVOCHAT"),
	LIVEPERSON("LIVEPERSON"), SMARTSUPP("SMARTSUPP"), TIDIO("TIDIO");

	public static ProviderOptions parse(String value) {
		for (ProviderOptions provider : ProviderOptions.values()) {
			if (Objects.equals(provider.getValue(), value)) {
				return provider;
			}
		}

		throw new IllegalArgumentException("Invalid value " + value);
	}

	public String getValue() {
		return _value;
	}

	@Override
	public String toString() {
		return _value;
	}

	private ProviderOptions(String value) {
		_value = value;
	}

	private final String _value;

}