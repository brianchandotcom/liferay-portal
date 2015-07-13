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

package com.liferay.comments.ratings.definition;

import com.liferay.portal.kernel.comments.Comment;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portlet.ratings.RatingsType;
import com.liferay.portlet.ratings.definition.PortletRatingsDefinition;

import org.osgi.service.component.annotations.Component;

/**
 * @author Roberto Díaz
 */
@Component(
	property = {
		"model.class.name=com.liferay.portlet.messageboards.model.MBDiscussion"
	}
)
public class CommentsPortletRatingsDefinition
	implements PortletRatingsDefinition {

	@Override
	public RatingsType getDefaultRatingsType() {
		return RatingsType.THUMBS;
	}

	@Override
	public String getPortletId() {
		return PortletProviderUtil.getPortletId(
			Comment.class.getName(), PortletProvider.Action.VIEW);
	}

}