/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.internal.transformer;

import com.liferay.journal.constants.JournalPortletKeys;
import com.liferay.journal.constants.JournalTransformerListenerKeys;
import com.liferay.petra.string.StringBundler;
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

		List<String> escapedKeys = null;
		List<String> escapedValues = null;

		List<String> keys = null;
		List<String> values = null;

		List<String> tempEscapedKeys = null;
		List<String> tempEscapedValues = null;

		boolean hasKey = false;

		for (Map.Entry<String, String> entry : tokens.entrySet()) {
			String key = entry.getKey();

			if (Validator.isNull(key) || !s.contains(key)) {
				continue;
			}

			if (!hasKey) {
				escapedKeys = new ArrayList<>();
				escapedValues = new ArrayList<>();
				keys = new ArrayList<>();
				values = new ArrayList<>();
				tempEscapedKeys = new ArrayList<>();
				tempEscapedValues = new ArrayList<>();

				hasKey = true;
			}

			String actualKey = StringBundler.concat(
				StringPool.AT, key, StringPool.AT);

			String escapedKey = StringBundler.concat(
				StringPool.AT, actualKey, StringPool.AT);

			String tempEscapedKey = StringBundler.concat(
				JournalTransformerListenerKeys.TEMP_ESCAPED_AT_OPEN, key,
				JournalTransformerListenerKeys.TEMP_ESCAPED_AT_CLOSE);

			escapedKeys.add(escapedKey);
			escapedValues.add(tempEscapedKey);

			keys.add(actualKey);
			values.add(GetterUtil.getString(entry.getValue()));

			tempEscapedKeys.add(tempEscapedKey);
			tempEscapedValues.add(actualKey);
		}

		if (!hasKey) {
			return s;
		}

		s = StringUtil.replace(
			s, escapedKeys.toArray(new String[0]),
			escapedValues.toArray(new String[0]));

		s = StringUtil.replace(
			s, keys.toArray(new String[0]), values.toArray(new String[0]));

		s = StringUtil.replace(
			s, tempEscapedKeys.toArray(new String[0]),
			tempEscapedValues.toArray(new String[0]));

		return s;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TokensTransformerListener.class);

}