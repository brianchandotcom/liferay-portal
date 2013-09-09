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
import com.liferay.portal.kernel.util.StringUtil;

import java.util.List;
import java.util.Map;

/**
 * @author Daniela Zapata
 * @author David Gonzalez
 */
public class DefaultCollatorImpl implements Collator {

	@Override
	public String collate(
		Map<String, List<String>> suggestionsMap, List<String> tokens) {

		StringBundler sb = new StringBundler(tokens.size() * 2);

		for (String token : tokens) {
			List<String> suggestions = suggestionsMap.get(token);

			if ((suggestions != null) && !suggestions.isEmpty()) {
				String suggestion = suggestions.get(0);

				if (Character.isUpperCase(token.charAt(0))) {
					suggestion = StringUtil.toUpperCase(
						suggestion.substring(0, 1)).concat(
							suggestion.substring(1));
				}

				sb.append(suggestion);
				sb.append(StringPool.SPACE);
			}
			else {
				sb.append(token);
				sb.append(StringPool.SPACE);
			}
		}

		String collatedValue = sb.toString();

		return collatedValue.trim();
	}

}