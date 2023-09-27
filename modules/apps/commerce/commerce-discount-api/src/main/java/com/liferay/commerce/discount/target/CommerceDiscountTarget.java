/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.discount.target;

import java.util.Locale;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public interface CommerceDiscountTarget {

	public String getKey();

	public String getLabel(Locale locale);

	public Type getType();

	public enum Type {

		APPLY_TO_PRODUCT("APPLY_TO_PRODUCT"),
		APPLY_TO_SHIPPING("APPLY_TO_SHIPPING"), APPLY_TO_SKU("APPLY_TO_SKU"),
		APPLY_TO_SUBTOTAL("APPLY_TO_SUBTOTAL"),
		APPLY_TO_TOTAL("APPLY_TO_TOTAL");

		public String getValue() {
			return _value;
		}

		@Override
		public String toString() {
			return getValue();
		}

		private Type(String value) {
			_value = value;
		}

		private final String _value;

	}

}