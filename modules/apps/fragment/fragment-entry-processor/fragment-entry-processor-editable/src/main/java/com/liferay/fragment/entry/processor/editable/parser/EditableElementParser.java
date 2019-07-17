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

package com.liferay.fragment.entry.processor.editable.parser;

import com.liferay.fragment.exception.FragmentEntryContentException;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Locale;

import org.jsoup.nodes.Element;

/**
 * Provides a utility to replace an editable element value.
 *
 * @author Pavel Savinov
 */
public interface EditableElementParser {

	public default JSONObject getAttributes(Element element) {
		return JSONFactoryUtil.createJSONObject();
	}

	public String getFieldTemplate();

	public default JSONObject getFieldTemplateConfigJSONObject(
		String fieldName, Locale locale, Object fieldValue) {

		return JSONFactoryUtil.createJSONObject();
	}

	public String getValue(Element element);

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public default boolean isCss() {
		return false;
	}

	public default String parseFieldValue(Object fieldValue) {
		return GetterUtil.get(fieldValue, StringPool.BLANK);
	}

	/**
	 * Replaces the editable element value with a new one.
	 *
	 * @param element the editable element to replace
	 * @param value the new element value
	 */
	public void replace(Element element, String value);

	/**
	 * Replaces the editable element value with a new one and applies the
	 * configuration values.
	 *
	 * @param element the editable element to replace
	 * @param value the new element value
	 * @param configJSONObject the configuration values
	 */
	public default void replace(
		Element element, String value, JSONObject configJSONObject) {

		replace(element, value);
	}

	/**
	 * Validates the editable element.
	 *
	 * @param  element the editable element to validate
	 * @throws FragmentEntryContentException if an invalid editable element is
	 *         detected
	 */
	public default void validate(Element element)
		throws FragmentEntryContentException {
	}

}