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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.user.associated.data.anonymizer.UADAnonymizer;
import com.liferay.user.associated.data.constants.UserAssociatedDataPortletKeys;
import com.liferay.user.associated.data.web.internal.constants.UADWebKeys;
import com.liferay.user.associated.data.web.internal.registry.UADRegistry;
import com.liferay.user.associated.data.web.internal.util.SelectedUserHelper;

import java.util.stream.Stream;

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
		"mvc.command.name=/view_uad_summary"
	},
	service = MVCRenderCommand.class
)
public class ViewUADSummaryMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			User selectedUser = _selectedUserHelper.getSelectedUser(
				renderRequest);

			renderRequest.setAttribute(
				UADWebKeys.VIEW_UAD_SUMMARY_STEP, _determineStep(selectedUser));
		}
		catch (Exception pe) {
			throw new PortletException(pe);
		}

		return "/view_uad_summary.jsp";
	}

	private int _determineStep(User selectedUser) throws Exception {
		if (selectedUser.isActive()) {
			return 1;
		}

		Stream<UADAnonymizer> uadAnonymizerStream =
			_uadRegistry.getUADAnonymizerStream();

		int uadEntitiesCount = uadAnonymizerStream.mapToInt(
			uadAnonymizer -> {
				try {
					return (int)uadAnonymizer.count(selectedUser.getUserId());
				}
				catch (PortalException pe) {
					throw new SystemException(pe);
				}
			}
		).sum();

		if (uadEntitiesCount > 0) {
			return 2;
		}

		return 3;
	}

	@Reference
	private SelectedUserHelper _selectedUserHelper;

	@Reference
	private UADRegistry _uadRegistry;

}