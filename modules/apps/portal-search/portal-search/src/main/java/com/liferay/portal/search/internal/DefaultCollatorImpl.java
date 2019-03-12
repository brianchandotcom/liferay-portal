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

package com.liferay.portal.search.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.suggest.Collator;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Daniela Zapata
 * @author David Gonzalez
 */
@Component(immediate = true, service = Collator.class)
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
					suggestion =
						StringUtil.toUpperCase(suggestion.substring(0, 1)) +
							suggestion.substring(1);
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