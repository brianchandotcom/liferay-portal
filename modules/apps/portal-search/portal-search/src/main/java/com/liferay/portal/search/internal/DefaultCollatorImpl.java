/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
@Component(service = Collator.class)
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