/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.util;

import com.liferay.portal.kernel.util.Validator;

import java.util.function.Supplier;

/**
 * @author Petteri Karttunen
 */
public class OpenSearchStringUtil {

	@SafeVarargs
	public static String getFirstStringValue(Supplier<Object>... suppliers) {
		for (Supplier<Object> supplier : suppliers) {
			String value = String.valueOf(supplier.get());

			if (!Validator.isBlank(value)) {
				return value;
			}
		}

		return null;
	}

}