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

package com.liferay.structured.content.apio.architect.resource;

import java.util.List;
import java.util.Locale;

/**
 * Holds information about a structured content field.
 *
 * @author Cristina González
 * @review
 */
public interface StructuredContentField {

	/**
	 * Returns the dataType of the structured content field.
	 *
	 * @return the data type.
	 *
	 * @review
	*/
	public String getDataType();

	/**
	 * Returns the filter and sort identifier of the structured content field.
	 *
	 * @return the filter and sort identifier.
	 *
	 * @review
	 */
	public String getFilterAndSortIdentifier();

	/**
	 * Returns the input control of the structured content field.
	 *
	 * @return the input control.
	 *
	 * @review
	 */
	public String getInputControl();

	/**
	 * Returns the label of the structured content given a locale.
	 * @param  locale the locale.
	 * @return the input control.
	 *
	 * @review
	 */
	public String getLocalizedLabel(Locale locale);

	/**
	 * Returns the value of the structured content given a locale.
	 *
	 * @param  locale the locale.
	 * @return the vale.
	 *
	 * @review
	 */
	public String getLocalizedValue(Locale locale);

	/**
	 * Returns the name of the structured content field.
	 *
	 * @return the name.
	 *
	 * @review
	 */
	public String getName();

	/**
	 * Returns the nested fields of the structured content field.
	 *
	 * @return the nested fields.
	 *
	 * @review
	 */
	public List<StructuredContentField> getNestedFields();

}