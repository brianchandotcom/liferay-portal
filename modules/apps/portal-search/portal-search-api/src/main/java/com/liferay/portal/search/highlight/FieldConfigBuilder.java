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

package com.liferay.portal.search.highlight;

import com.liferay.portal.search.query.Query;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Wade Cao
 * @author André de Oliveira
 */
@ProviderType
public interface FieldConfigBuilder {

	public FieldConfigBuilder boundaryChars(char... boundaryChars);

	public FieldConfigBuilder boundaryMaxScan(Integer boundaryMaxScan);

	public FieldConfigBuilder boundaryScannerLocale(
		String boundaryScannerLocale);

	public FieldConfigBuilder boundaryScannerType(String boundaryScannerType);

	public FieldConfig build();

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             Highlights#fieldConfigBuilder(String)}
	 */
	@Deprecated
	public FieldConfigBuilder field(String field);

	public FieldConfigBuilder forceSource(Boolean forceSource);

	public FieldConfigBuilder fragmenter(String fragmenter);

	public FieldConfigBuilder fragmentOffset(Integer fragmentOffset);

	public FieldConfigBuilder fragmentSize(Integer fragmentSize);

	public FieldConfigBuilder highlighterType(String highlighterType);

	public FieldConfigBuilder highlightFilter(Boolean highlightFilter);

	public FieldConfigBuilder highlightQuery(Query highlightQuery);

	public FieldConfigBuilder matchedFields(String... matchedFields);

	public FieldConfigBuilder noMatchSize(Integer noMatchSize);

	public FieldConfigBuilder numFragments(Integer numFragments);

	public FieldConfigBuilder order(String order);

	public FieldConfigBuilder phraseLimit(Integer phraseLimit);

	public FieldConfigBuilder postTags(String... postTags);

	public FieldConfigBuilder preTags(String... preTags);

	public FieldConfigBuilder requireFieldMatch(Boolean requireFieldMatch);

}