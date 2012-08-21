/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.templateparser.BaseTransformerListener;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
public class TokensTransformerListener extends BaseTransformerListener {

	public static final String TEMP_ESCAPED_AT_CLOSE =
		"[$_TEMP_ESCAPED_AT_CLOSE$]";

	public static final String TEMP_ESCAPED_AT_OPEN =
		"[$TEMP_ESCAPED_AT_OPEN$]";

	@Override
	public String onOutput(String s) {
		if (_log.isDebugEnabled()) {
			_log.debug("onOutput");
		}

		return replace(s);
	}

	@Override
	public String onScript(String s) {
		if (_log.isDebugEnabled()) {
			_log.debug("onScript");
		}

		return replace(s);
	}

	@Override
	public String onXml(String s) {
		if (_log.isDebugEnabled()) {
			_log.debug("onXml");
		}

		return s;
	}

	/**
	 * Replace the standard tokens in a given string with their values.
	 *
	 * @return the processed string
	 */
	protected String replace(String s) {
		Map<String, String> tokens = getTokens();

		Set<Map.Entry<String, String>> tokensSet = tokens.entrySet();

		if (tokensSet.size() == 0) {
			return s;
		}

		List<String> escapedKeysList = new ArrayList<String>();
		List<String> escapedValuesList = new ArrayList<String>();

		List<String> keysList = new ArrayList<String>();
		List<String> valuesList = new ArrayList<String>();

		List<String> tempEscapedKeysList = new ArrayList<String>();
		List<String> tempEscapedValuesList = new ArrayList<String>();

		for (Map.Entry<String, String> entry : tokensSet) {
			String key = entry.getKey();
			String value = GetterUtil.getString(entry.getValue());

			if (Validator.isNotNull(key)) {
				String escapedKey =
					StringPool.AT + StringPool.AT + key + StringPool.AT +
						StringPool.AT;

				String actualKey = StringPool.AT + key + StringPool.AT;

				String tempEscapedKey =
					TEMP_ESCAPED_AT_OPEN + key + TEMP_ESCAPED_AT_CLOSE;

				escapedKeysList.add(escapedKey);
				escapedValuesList.add(tempEscapedKey);

				keysList.add(actualKey);
				valuesList.add(value);

				tempEscapedKeysList.add(tempEscapedKey);
				tempEscapedValuesList.add(actualKey);
			}
		}

		s = StringUtil.replace(
			s, escapedKeysList.toArray(new String[escapedKeysList.size()]),
			escapedValuesList.toArray(new String[escapedValuesList.size()]));

		s = StringUtil.replace(
			s, keysList.toArray(new String[keysList.size()]),
			valuesList.toArray(new String[valuesList.size()]));

		s = StringUtil.replace(
			s,
			tempEscapedKeysList.toArray(new String[tempEscapedKeysList.size()]),
			tempEscapedValuesList.toArray(
				new String[tempEscapedValuesList.size()]));

		return s;
	}

	private static Log _log = LogFactoryUtil.getLog(
		TokensTransformerListener.class);

}