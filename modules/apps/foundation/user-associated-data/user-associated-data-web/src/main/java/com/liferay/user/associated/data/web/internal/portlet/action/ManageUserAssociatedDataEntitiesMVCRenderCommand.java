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

package com.liferay.user.associated.data.web.internal.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.user.associated.data.constants.UserAssociatedDataPortletKeys;
import com.liferay.user.associated.data.registry.UADRegistry;
import com.liferay.user.associated.data.util.UADEntityTypeComposite;
import com.liferay.user.associated.data.web.internal.constants.UserAssociatedDataWebKeys;

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

	public static int[] getStartAndEnd(RenderRequest request) {
		String start = request.getParameter("start");
		String end = request.getParameter("end");

		if (Validator.isNull(start) || Validator.isNull(end)) {
			int cur = ParamUtil.getInteger(request, "cur", 1);
			int delta = ParamUtil.getInteger(request, "delta", 20);

			return new int[] {(cur - 1) * delta, cur * delta};
		}

		int startValue = GetterUtil.getInteger(start);
		int endValue = GetterUtil.getInteger(end);

		if ((startValue < 0) && (endValue > 0)) {
			return new int[] {0, endValue};
		}
		else if ((startValue >= 0) && (endValue == -1)) {
			return new int[] {startValue, Integer.MAX_VALUE};
		}

		return new int[] {startValue, endValue};
	}

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		long selUserId = ParamUtil.getLong(renderRequest, "selUserId");
		String uadRegistryKey = ParamUtil.getString(
			renderRequest, "uadRegistryKey");

		int[] startAndEnd = getStartAndEnd(renderRequest);

		UADEntityTypeComposite uadEntityTypeComposite =
			_uadRegistry.getUADEntityTypeComposite(
				selUserId, uadRegistryKey, startAndEnd[0], startAndEnd[1]);

		renderRequest.setAttribute(
			UserAssociatedDataWebKeys.UAD_ENTITY_TYPE_COMPOSITE,
			uadEntityTypeComposite);

		return "/manage_user_associated_data_entities.jsp";
	}

	@Reference
	private UADRegistry _uadRegistry;

}