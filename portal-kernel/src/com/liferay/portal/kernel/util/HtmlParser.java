/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author Guilherme Camacho
 */
public interface HtmlParser {

	public String extractText(String html);

	public String findAttributeValue(
		Predicate<Function<String, String>> findValuePredicate,
		Function<Function<String, String>, String> returnValueFunction,
		String html, String startTagName);

	public String render(String html);

}