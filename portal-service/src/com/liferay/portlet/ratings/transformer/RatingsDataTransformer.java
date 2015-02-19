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

package com.liferay.portlet.ratings.transformer;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portlet.ratings.RatingsType;

/**
 * An interface defining the transformations that will be applied to the ratings
 * data when the ratings type used by an entity is changed to use a different
 * ratings type.
 *
 * <p>
 * RatingsDataTransformer implementation will need to be registered in the
 * OSGI Registry. The portal will invoke the highest ranking OSGI component
 * implementing this interface when the ratings type of an entity is changed.
 * </p>
 *
 * @author Roberto Díaz
 * @author Sergio González
 */
public interface RatingsDataTransformer {

	/**
	 * Defines the transformations that will be applied on a ratings entry when
	 * ratings type is changed from <code>fromRatingsType</code> to
	 * <code>toRatingsType</code>.
	 *
	 * <p>
	 * This methow will return a
	 * <code>ActionableDynamicQuery.PerformActionMethod</code> that will operate
	 * on a {@link com.liferay.portlet.ratings.model.RatingsEntry} entity to
	 * transform its values based when the ratings type is changed.
	 * </p>
	 *
	 * @param  fromRatingsType the previous ratings type
	 * @param  toRatingsType the final ratings type
	 * @return a ActionableDynamicQuery.PerformActionMethod with the actions
	 *         that needs to be applied to the RatingsEntry when the ratings
	 *         type changes.
	 * @throws PortalException if the transformation cannot be applied
	 *         successfully.
	 */
	public ActionableDynamicQuery.PerformActionMethod transformRatingsData(
			final RatingsType fromRatingsType, final RatingsType toRatingsType)
		throws PortalException;

}