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

package com.liferay.adaptive.media.finder;

import com.liferay.adaptive.media.AdaptiveMedia;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.function.Function;
import java.util.stream.Stream;

/**
 * An {@link AMFinder} is responsible for locating and returning media related
 * to a model.
 *
 * <p>
 * All media matching the query is sorted by score and returned. Better matches
 * are prioritized before worse ones.
 * </p>
 *
 * @author Adolfo Pérez
 */
public interface AMFinder<B extends AMQueryBuilder<M, T>, M, T> {

	/**
	 * Returns all {@link AdaptiveMedia} instances for the model that matches
	 * the query. The function is invoked with an instance of an implementation
	 * dependent {@link AMQueryBuilder}, that callers must use to create the
	 * query.
	 *
	 * @param  amQueryBuilderFunction a function to be invoked with an {@link
	 *         AMQueryBuilder} argument. The query builder provides operations
	 *         to filter and sort the returned media.
	 * @return a non-<code>null</code>, possibly empty stream of all media
	 *         instances matching the query ordered by score: better matches are
	 *         prioritized first
	 * @throws PortalException if an error occurred while calling any Liferay
	 *         service
	 */
	public Stream<AdaptiveMedia<T>> getAdaptiveMediaStream(
			Function<B, AMQuery<M, T>> amQueryBuilderFunction)
		throws PortalException;

}