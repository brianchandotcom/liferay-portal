/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.search;

import com.liferay.portal.kernel.search.Collator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.util.List;
import java.util.Map;

/**
 * @author Daniela Zapata
 * @author David Gonzalez
 */
public class DefaultCollatorImpl implements Collator {

	public String collate(
		Map<String, List<String>> suggestionsMap, List<String> tokens) {

		StringBundler collationBundler = new StringBundler(tokens.size() * 2);

		for (String token : tokens) {
			List<String> suggestions = suggestionsMap.get(token);

			if ((suggestions != null) && !suggestions.isEmpty()) {
				String suggestion = suggestions.get(0);

				if (Character.isUpperCase(token.charAt(0))) {
					suggestion = suggestion.substring(
						0, 1).toUpperCase().concat(suggestion.substring(1));
				}

				collationBundler.append(suggestion);
				collationBundler.append(StringPool.SPACE);
			}
			else {
				collationBundler.append(token);
				collationBundler.append(StringPool.SPACE);
			}
		}

		String collatedValue = collationBundler.toString();

		return collatedValue.trim();
	}

}