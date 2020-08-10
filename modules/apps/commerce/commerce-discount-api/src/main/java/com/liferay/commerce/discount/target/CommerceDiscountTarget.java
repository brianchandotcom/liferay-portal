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

package com.liferay.commerce.discount.target;

import aQute.bnd.annotation.ProviderType;

import java.util.Locale;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
@ProviderType
public interface CommerceDiscountTarget {

	public String getKey();

	public String getLabel(Locale locale);

	public Type getType();

	public enum Type {

		APPLY_TO_PRODUCT("APPLY_TO_PRODUCT"),
		APPLY_TO_SHIPPING("APPLY_TO_SHIPPING"),
		APPLY_TO_SUBTOTAL("APPLY_TO_SUBTOTAL"),
		APPLY_TO_TOTAL("APPLY_TO_TOTAL");

		public String getValue() {
			return _value;
		}

		@Override
		public String toString() {
			return getValue();
		}

		private Type(final String value) {
			_value = value;
		}

		private final String _value;

	}

}