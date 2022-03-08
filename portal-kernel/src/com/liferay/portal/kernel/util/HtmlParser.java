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