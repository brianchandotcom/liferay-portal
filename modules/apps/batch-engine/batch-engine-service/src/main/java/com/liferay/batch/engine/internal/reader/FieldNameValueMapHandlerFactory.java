/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.internal.reader;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Igor Beslic
 */
public class FieldNameValueMapHandlerFactory {

	public static FieldNameValueMapHandler getFieldNameValueMapHandler(
		String fieldName) {

		if (fieldName.lastIndexOf(I18nFieldNameValueMapHandler._I18N_SUFFIX) >
				-1) {

			return _i18nFieldNameValueMapHandler;
		}

		return _baseFieldNameValueMapHandler;
	}

	public interface FieldNameValueMapHandler {

		public void handle(
			String fieldName, Map<String, Object> fieldNameValueMap,
			String value);

	}

	private static final FieldNameValueMapHandler
		_baseFieldNameValueMapHandler = new BaseFieldNameValueMapHandler();
	private static final FieldNameValueMapHandler
		_i18nFieldNameValueMapHandler = new I18nFieldNameValueMapHandler();

	private static class BaseFieldNameValueMapHandler
		implements FieldNameValueMapHandler {

		@Override
		public void handle(
			String fieldName, Map<String, Object> fieldNameValueMap,
			String value) {

			fieldNameValueMap.put(fieldName, getValue(value));
		}

		protected String getValue(String value) {
			value = value.trim();

			if (value.isEmpty()) {
				return null;
			}

			return value;
		}

	}

	private static class I18nFieldNameValueMapHandler
		extends BaseFieldNameValueMapHandler
		implements FieldNameValueMapHandler {

		@Override
		public void handle(
			String fieldName, Map<String, Object> fieldNameValueMap,
			String value) {

			String key = fieldName.substring(
				fieldName.lastIndexOf(_I18N_SUFFIX) + 6);

			Map<String, String> valueMap =
				(Map<String, String>)fieldNameValueMap.get(fieldName);

			if (valueMap == null) {
				valueMap = new HashMap<>();

				fieldNameValueMap.put(fieldName, valueMap);
			}

			valueMap.put(key, getValue(value));
		}

		private static final String _I18N_SUFFIX = "_i18n";

	}

}