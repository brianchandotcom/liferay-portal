/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.cms.rest.internal.client;

import java.util.Objects;

/**
 * @author Rachael Koestartyo
 */
public enum GroupBy {

	CATEGORY("category"), STRUCTURE("structure"), TAG("tag"),
	VOCABULARY("vocabulary");

	public static GroupBy parse(String value) {
		if (Objects.equals(CATEGORY.getValue(), value)) {
			return CATEGORY;
		}
		else if (Objects.equals(STRUCTURE.getValue(), value)) {
			return STRUCTURE;
		}
		else if (Objects.equals(TAG.getValue(), value)) {
			return TAG;
		}
		else if (Objects.equals(VOCABULARY.getValue(), value)) {
			return VOCABULARY;
		}

		throw new IllegalArgumentException("Invalid value " + value);
	}

	public String getValue() {
		return _value;
	}

	private GroupBy(String value) {
		_value = value;
	}

	private final String _value;

}