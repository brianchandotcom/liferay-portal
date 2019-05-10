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

import java.util.Collection;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Wade Cao
 * @author André de Oliveira
 */
@ProviderType
public interface HighlightBuilder {

	public HighlightBuilder addFieldConfig(FieldConfig fieldConfig);

	public Highlight build();

	public HighlightBuilder fieldConfigs(Collection<FieldConfig> fieldConfigs);

	public HighlightBuilder forceSource(Boolean forceSource);

	public HighlightBuilder fragmenter(String fragmenter);

	public HighlightBuilder fragmentSize(Integer fragmentSize);

	public HighlightBuilder highlighterType(String highlighterType);

	public HighlightBuilder highlightFilter(Boolean highlightFilter);

	public HighlightBuilder highlightQuery(Query highlightQuery);

	public HighlightBuilder numOfFragments(Integer numOfFragments);

	public HighlightBuilder postTags(String... postTags);

	public HighlightBuilder preTags(String... preTags);

	public HighlightBuilder requireFieldMatch(Boolean requireFieldMatch);

}