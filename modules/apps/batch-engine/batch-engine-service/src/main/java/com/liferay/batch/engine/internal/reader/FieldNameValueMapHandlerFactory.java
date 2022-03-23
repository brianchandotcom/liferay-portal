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

package com.liferay.batch.engine.internal.reader;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Igor Beslic
 */
public class FieldNameValueMapHandlerFactory {

	public static FieldNameValueMapHandler getHandler(String fieldName) {
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

			fieldName = fieldName.substring(
				0, fieldName.lastIndexOf(_I18N_SUFFIX));

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