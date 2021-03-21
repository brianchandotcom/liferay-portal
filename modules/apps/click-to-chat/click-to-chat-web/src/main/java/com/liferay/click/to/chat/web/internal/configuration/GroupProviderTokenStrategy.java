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

package com.liferay.click.to.chat.web.internal.configuration;

import java.util.Objects;

/**
 * @author José Abelenda
 */
public enum GroupProviderTokenStrategy {

	ALWAYS_INHERIT("ALWAYS_INHERIT"), PROVIDE("PROVIDE"),
	PROVIDE_OR_INHERIT("PROVIDE_OR_INHERIT");

	public static GroupProviderTokenStrategy parse(String value) {
		for (GroupProviderTokenStrategy strategy :
				GroupProviderTokenStrategy.values()) {

			if (Objects.equals(strategy.getValue(), value)) {
				return strategy;
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

	private GroupProviderTokenStrategy(String value) {
		_value = value;
	}

	private final String _value;

}