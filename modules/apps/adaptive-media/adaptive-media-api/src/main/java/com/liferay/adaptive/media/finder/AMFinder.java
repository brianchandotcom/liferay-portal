/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.finder;

import com.liferay.adaptive.media.AdaptiveMedia;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;
import java.util.function.Function;

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
	 * @return a non-<code>null</code>, possibly empty list of all media
	 *         instances matching the query ordered by score: better matches are
	 *         prioritized first
	 * @throws PortalException if an error occurred while calling any Liferay
	 *         service
	 */
	public List<AdaptiveMedia<T>> getAdaptiveMedias(
			Function<B, AMQuery<M, T>> amQueryBuilderFunction)
		throws PortalException;

}