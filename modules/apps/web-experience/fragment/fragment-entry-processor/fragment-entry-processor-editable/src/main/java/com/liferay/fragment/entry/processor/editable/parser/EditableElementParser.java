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

import org.jsoup.nodes.Element;

/**
 * This service provides a utility to replace editable element value.
 *
 * @author Pavel Savinov
 */
public interface EditableElementParser {

	/**
	 * Replaces editable element value with the provided one.
	 *
	 * @param element Editable element to replace
	 * @param value New element value
	 */
	public void replace(Element element, String value);

}