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

package com.liferay.social.activities.web.internal.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.social.activities.constants.SocialActivitiesPortletKeys;
import com.liferay.social.activities.web.internal.constants.SocialActivitiesWebKeys;
import com.liferay.social.activities.web.internal.util.SocialActivitiesQueryHelper;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + SocialActivitiesPortletKeys.SOCIAL_ACTIVITIES,
		"mvc.command.name=/"
	},
	service = MVCRenderCommand.class
)
public class ViewMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		renderRequest.setAttribute(
			SocialActivitiesWebKeys.SOCIAL_ACTIVITIES_QUERY_HELPER,
			_socialActivitiesQueryHelper);

		return "/view.jsp";
	}

	@Reference(unbind = "-")
	protected void setSocialActivitiesQueryHelper(
		SocialActivitiesQueryHelper socialActivitiesQueryHelper) {

		_socialActivitiesQueryHelper = socialActivitiesQueryHelper;
	}

	private SocialActivitiesQueryHelper _socialActivitiesQueryHelper;

}