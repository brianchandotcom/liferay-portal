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

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

/**
 * Represents a structured content exposed through the API.
 *
 * @author Alejandro Hernández
 * @author Cristina González
 * @review
 */
public interface StructuredContent {

	/**
	 * Returns the structured content's categories.
	 *
	 * @return the categories
	 * @review
	 */
	public List<Long> getCategories();

	/**
	 * Returns the structured content's structure ID.
	 *
	 * @return the structure ID
	 * @review
	 */
	public Long getContentStructureId();

	/**
	 * Returns the structured content's description map for the supplied locale.
	 *
	 * @return the description map
	 * @review
	 */
	public Optional<Map<Locale, String>> getDescriptionMapOptional(
		Locale locale);

	/**
	 * Returns the structured content's keywords.
	 *
	 * @return the keywords
	 * @review
	 */
	public List<String> getKeywords();

	/**
	 * Returns the day from the structured content's publication date.
	 *
	 * @return the publication date's day
	 * @review
	 */
	public Optional<Integer> getPublishedDateDayOptional();

	/**
	 * Returns the hour from the structured content's publication date.
	 *
	 * @return the publication date's hour
	 * @review
	 */
	public Optional<Integer> getPublishedDateHourOptional();

	/**
	 * Returns the minute from the structured content's publication date.
	 *
	 * @return the publication date's minute
	 * @review
	 */
	public Optional<Integer> getPublishedDateMinuteOptional();

	/**
	 * Returns the month from the structured content's publication date.
	 *
	 * @return the publication date's month
	 * @review
	 */
	public Optional<Integer> getPublishedDateMonthOptional();

	/**
	 * Returns the year from the structured content's publication date.
	 *
	 * @return the publication date's year
	 * @review
	 */
	public Optional<Integer> getPublishedDateYearOptional();

	/**
	 * Returns the structured content's structured content values.
	 *
	 * @return the structured content values
	 * @review
	 */
	public List<? extends StructuredContentValue> getStructuredContentValues();

	/**
	 * Returns the structured content's title map.
	 *
	 * @return the title map
	 * @review
	 */
	public Map<Locale, String> getTitleMap(Locale locale);

}