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

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseTransactionalMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.user.associated.data.constants.UserAssociatedDataPortletKeys;
import com.liferay.user.associated.data.web.internal.util.SelectedUserHelper;
import com.liferay.user.associated.data.web.internal.util.UADApplicationSummaryHelper;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + UserAssociatedDataPortletKeys.USER_ASSOCIATED_DATA,
		"mvc.command.name=/erase_personal_data"
	},
	service = MVCActionCommand.class
)
public class ErasePersonalDataMVCActionCommand
	extends BaseTransactionalMVCActionCommand {

	@Override
	protected void doTransactionalCommand(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		User selectedUser = _selectedUserHelper.getSelectedUser(actionRequest);

		if (selectedUser.isActive()) {
			_userGroupLocalService.clearUserUserGroups(
				selectedUser.getUserId());

			_userLocalService.updateStatus(
				selectedUser.getUserId(), WorkflowConstants.STATUS_INACTIVE,
				new ServiceContext());

			Group group = selectedUser.getGroup();

			group.setActive(true);

			_groupLocalService.updateGroup(group);
		}
		else {
			SessionMessages.add(
				actionRequest,
				_portal.getPortletId(actionRequest) +
					SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_SUCCESS_MESSAGE);
		}

		LiferayPortletURL redirect = PortletURLFactoryUtil.create(
			actionRequest, UserAssociatedDataPortletKeys.USER_ASSOCIATED_DATA,
			PortletRequest.RENDER_PHASE);

		redirect.setParameter(
			"p_u_i_d", String.valueOf(selectedUser.getUserId()));

		String mvcRenderCommandName = "/review_uad_data";

		int totalReviewableUADEntitiesCount =
			_uadApplicationSummaryHelper.getTotalReviewableUADEntitiesCount(
				selectedUser.getUserId());

		if (totalReviewableUADEntitiesCount == 0) {
			int totalNonreviewableUADEntitiesCount =
				_uadApplicationSummaryHelper.
					getTotalNonreviewableUADEntitiesCount(
						selectedUser.getUserId());

			if (totalNonreviewableUADEntitiesCount == 0) {
				mvcRenderCommandName = "/completed_data_erasure";
			}
			else {
				mvcRenderCommandName = "/anonymize_nonreviewable_uad_data";
			}
		}

		redirect.setParameter("mvcRenderCommandName", mvcRenderCommandName);

		actionResponse.sendRedirect(redirect.toString());
	}

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private SelectedUserHelper _selectedUserHelper;

	@Reference
	private UADApplicationSummaryHelper _uadApplicationSummaryHelper;

	@Reference
	private UserGroupLocalService _userGroupLocalService;

	@Reference
	private UserLocalService _userLocalService;

}