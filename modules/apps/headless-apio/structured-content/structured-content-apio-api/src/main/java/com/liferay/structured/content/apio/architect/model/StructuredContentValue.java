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

package com.liferay.structured.content.apio.architect.model;

/**
 * Represents a structured content values exposed through the API.
 *
 * @author Alejandro Hernández
 * @author Cristina González
 * @review
 */
public interface StructuredContentValue {

	/**
	 * Returns the structured content value's document.
	 *
	 * @return the document
	 * @review
	 */
	public Long getDocument();

	/**
	 * Returns the structured content value's name.
	 *
	 * @return the name
	 * @review
	 */
	public String getName();

	/**
	 * Returns the structured content value's structured content ID.
	 *
	 * @return the structured content ID
	 * @review
	 */
	public Long getStructuredContentId();

	/**
	 * Returns the structured content value's structured content location.
	 *
	 * @return the structured content location
	 * @review
	 */
	public StructuredContentLocation getStructuredContentLocation();

	/**
	 * Returns the structured content value's value.
	 *
	 * @return the structured content value
	 * @review
	 */
	public String getValue();

}