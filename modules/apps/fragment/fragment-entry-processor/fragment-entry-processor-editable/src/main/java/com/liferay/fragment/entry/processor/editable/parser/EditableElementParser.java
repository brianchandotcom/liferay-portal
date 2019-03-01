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
import com.liferay.portal.kernel.json.JSONObject;

import org.jsoup.nodes.Element;

/**
 * This service provides a utility to replace editable element value.
 *
 * @author Pavel Savinov
 */
public interface EditableElementParser {

	public String getFieldTemplate();

	public String getValue(Element element);

	public default boolean isCss() {
		return false;
	}

	/**
	 * Replaces editable element value with the provided one.
	 *
	 * @param element Editable element to replace
	 * @param value New element value
	 */
	public void replace(Element element, String value);

	/**
	 * Replaces editable element value with the provided one and apply the
	 * configuration values
	 *
	 * @param element Editable element to replace
	 * @param value New element value
	 * @param configJSONObject Configuration values
	 */
	public default void replace(
		Element element, String value, JSONObject configJSONObject) {

		replace(element, value);
	}

	/**
	 * Validates editable element
	 *
	 * @param  element Editable element to validate
	 * @throws FragmentEntryContentException In case of invalid editable element
	 */
	public default void validate(Element element)
		throws FragmentEntryContentException {
	}

}