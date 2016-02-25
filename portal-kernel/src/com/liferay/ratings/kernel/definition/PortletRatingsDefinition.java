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

package com.liferay.ratings.kernel.definition;

import com.liferay.ratings.kernel.RatingsType;

/**
 * Provides an interface for defining the ratings used by an entity (or
 * entities) in a portlet.
 *
 * <p>
 * This information is used to render a user interface with the entities using
 * ratings, grouped by portlet to allow changing the ratings type.
 * </p>
 *
 * <p>
 * Implementations must be registered in the OSGI Registry with the property
 * &quot;model.class.name&quot; to specify which entity this definition applies
 * to.
 * </p>
 *
 * @author Roberto Díaz
 * @author Sergio González
 */
public interface PortletRatingsDefinition {

	/**
	 * Returns the default ratings type of the entity. This ratings type is used
	 * as long as no other ratings type has been set.
	 *
	 * @return the default ratings type of the entity
	 */
	public RatingsType getDefaultRatingsType();

	/**
	 * Returns the ID of the main portlet associated to the entity. This portlet
	 * ID is used to group the entity; so if the entity is used by more than one
	 * portlet, the ID of the most relevant portlet is returned.
	 *
	 * @return the main portlet ID associated to the entity.
	 */
	public String getPortletId();

}