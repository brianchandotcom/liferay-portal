/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.model;

import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;
import java.util.Objects;

/**
 * @author Mikel Lorza
 */
public class LockedLayoutOrder {

	public LockedLayoutOrder(
		boolean ascending, Locale locale,
		LockedLayoutOrderType lockedLayoutOrderType) {

		_ascending = ascending;
		_locale = locale;
		_lockedLayoutOrderType = lockedLayoutOrderType;
	}

	public Locale getLocale() {
		return _locale;
	}

	public LockedLayoutOrderType getLockedLayoutOrderType() {
		return _lockedLayoutOrderType;
	}

	public boolean isAscending() {
		return _ascending;
	}

	public enum LockedLayoutOrderType {

		LAST_AUTOSAVE("last-autosave"), NAME("name"), USER("user");

		public static LockedLayoutOrderType create(String value) {
			if (Validator.isNull(value)) {
				return null;
			}

			for (LockedLayoutOrderType lockedLayoutType : values()) {
				if (Objects.equals(lockedLayoutType.getValue(), value)) {
					return lockedLayoutType;
				}
			}

			return null;
		}

		public String getValue() {
			return _value;
		}

		private LockedLayoutOrderType(String value) {
			_value = value;
		}

		private final String _value;

	}

	private final boolean _ascending;
	private final Locale _locale;
	private final LockedLayoutOrderType _lockedLayoutOrderType;

}