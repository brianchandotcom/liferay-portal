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

package com.liferay.portal.search.hits;

import java.util.Collection;
import java.util.stream.Stream;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Wade Cao
 */
@ProviderType
public interface SearchHitsBuilder {

	public SearchHitsBuilder addSearchHit(SearchHit searchHit);

	public SearchHitsBuilder addSearchHits(Collection<SearchHit> searchHits);

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #addSearchHits(Collection)}
	 */
	@Deprecated
	public SearchHitsBuilder addSearchHits(Stream<SearchHit> searchHitStream);

	public SearchHits build();

	public SearchHitsBuilder maxScore(float maxScore);

	public SearchHitsBuilder searchTime(long searchTime);

	public SearchHitsBuilder totalHits(long totalHits);

}