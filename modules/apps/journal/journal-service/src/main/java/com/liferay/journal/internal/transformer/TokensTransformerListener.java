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

package com.liferay.journal.internal.transformer;

import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.constants.JournalTransformerListenerKeys;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.templateparser.BaseTransformerListener;
import com.liferay.portal.kernel.templateparser.TransformerListener;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + JournalPortletKeys.JOURNAL,
	service = TransformerListener.class
)
public class TokensTransformerListener extends BaseTransformerListener {

	@Override
	public String onOutput(
		String output, String languageId, Map<String, String> tokens) {

		if (_log.isDebugEnabled()) {
			_log.debug("onOutput");
		}

		return replace(output, tokens);
	}

	@Override
	public String onScript(
		String script, Document document, String languageId,
		Map<String, String> tokens) {

		if (_log.isDebugEnabled()) {
			_log.debug("onScript");
		}

		return replace(script, tokens);
	}

	/**
	 * Replace the standard tokens in a given string with their values.
	 *
	 * @return the processed string
	 */
	protected String replace(String s, Map<String, String> tokens) {
		if (tokens.isEmpty()) {
			return s;
		}

		List<String> escapedKeysList = null;
		List<String> escapedValuesList = null;

		List<String> keysList = null;
		List<String> valuesList = null;

		List<String> tempEscapedKeysList = null;
		List<String> tempEscapedValuesList = null;

		boolean hasKey = false;

		for (Map.Entry<String, String> entry : tokens.entrySet()) {
			String key = entry.getKey();

			if (Validator.isNotNull(key) && s.contains(key)) {
				if (!hasKey) {
					escapedKeysList = new ArrayList<>();
					escapedValuesList = new ArrayList<>();
					keysList = new ArrayList<>();
					valuesList = new ArrayList<>();
					tempEscapedKeysList = new ArrayList<>();
					tempEscapedValuesList = new ArrayList<>();

					hasKey = true;
				}

				String actualKey = StringPool.AT.concat(
					key
				).concat(
					StringPool.AT
				);

				String escapedKey = StringPool.AT.concat(
					actualKey
				).concat(
					StringPool.AT
				);

				String tempEscapedKey =
					JournalTransformerListenerKeys.TEMP_ESCAPED_AT_OPEN.concat(
						key
					).concat(
						JournalTransformerListenerKeys.TEMP_ESCAPED_AT_CLOSE
					);

				escapedKeysList.add(escapedKey);
				escapedValuesList.add(tempEscapedKey);

				keysList.add(actualKey);
				valuesList.add(GetterUtil.getString(entry.getValue()));

				tempEscapedKeysList.add(tempEscapedKey);
				tempEscapedValuesList.add(actualKey);
			}
		}

		if (!hasKey) {
			return s;
		}

		s = StringUtil.replace(
			s, escapedKeysList.toArray(new String[0]),
			escapedValuesList.toArray(new String[0]));

		s = StringUtil.replace(
			s, keysList.toArray(new String[0]),
			valuesList.toArray(new String[0]));

		s = StringUtil.replace(
			s, tempEscapedKeysList.toArray(new String[0]),
			tempEscapedValuesList.toArray(new String[0]));

		return s;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TokensTransformerListener.class);

}