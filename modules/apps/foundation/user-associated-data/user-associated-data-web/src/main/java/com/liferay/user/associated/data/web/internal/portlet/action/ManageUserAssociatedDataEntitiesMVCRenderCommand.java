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

package liferay.user.associated.data.web.internal.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.user.associated.data.constants.UserAssociatedDataPortletKeys;
import com.liferay.user.associated.data.registry.UADRegistry;
import com.liferay.user.associated.data.util.UADEntityTypeComposite;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author William Newbury
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + UserAssociatedDataPortletKeys.USER_ASSOCIATED_DATA,
		"mvc.command.name=/user_associated_data/manage_user_associated_data_entities"
	},
	service = MVCRenderCommand.class
)
public class ManageUserAssociatedDataEntitiesMVCRenderCommand
	implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		String key = ParamUtil.getString(renderRequest, "key");
		long userId = ParamUtil.getLong(renderRequest, "userId");

		UADEntityTypeComposite entityTypeComposite =
			_uadRegistry.getUADEntityTypeComposite(userId, key);

		renderRequest.setAttribute("entityTypeComposite", entityTypeComposite);

		return "/manage_user_associated_data_entities.jsp";
	}

	@Reference
	private UADRegistry _uadRegistry;

}