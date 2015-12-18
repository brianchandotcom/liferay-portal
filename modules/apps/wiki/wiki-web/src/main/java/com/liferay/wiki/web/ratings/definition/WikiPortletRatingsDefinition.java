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

package com.liferay.wiki.web.ratings.definition;

import com.liferay.portlet.ratings.RatingsType;
import com.liferay.portlet.ratings.definition.PortletRatingsDefinition;
import com.liferay.wiki.constants.WikiPortletKeys;

import org.osgi.service.component.annotations.Component;

/**
 * @author Roberto Díaz
 */
@Component(property = {"model.class.name=com.liferay.wiki.model.WikiPage"})
public class WikiPortletRatingsDefinition implements PortletRatingsDefinition {

	@Override
	public RatingsType getDefaultRatingsType() {
		return RatingsType.STARS;
	}

	@Override
	public String getPortletId() {
		return WikiPortletKeys.WIKI;
	}

}