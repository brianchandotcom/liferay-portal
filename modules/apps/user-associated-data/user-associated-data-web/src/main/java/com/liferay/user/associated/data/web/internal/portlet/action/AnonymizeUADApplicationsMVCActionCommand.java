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

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.user.associated.data.anonymizer.UADAnonymizer;
import com.liferay.user.associated.data.constants.UserAssociatedDataPortletKeys;
import com.liferay.user.associated.data.display.UADDisplay;
import com.liferay.user.associated.data.web.internal.util.UADAnonymizerHelper;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + UserAssociatedDataPortletKeys.USER_ASSOCIATED_DATA,
		"mvc.command.name=/anonymize_uad_applications"
	},
	service = MVCActionCommand.class
)
public class AnonymizeUADApplicationsMVCActionCommand
	extends BaseUADMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		User selectedUser = getSelectedUser(actionRequest);

		User anonymousUser = _uadAnonymizerHelper.getAnonymousUser(
			selectedUser.getCompanyId());

		long[] groupIds = ParamUtil.getLongValues(actionRequest, "groupIds");

		for (String applicationKey : getApplicationKeys(actionRequest)) {
			for (UADDisplay uadDisplay :
					uadRegistry.getApplicationUADDisplays(applicationKey)) {

				Class<?> typeClass = uadDisplay.getTypeClass();

				UADAnonymizer uadAnonymizer = uadRegistry.getUADAnonymizer(
					typeClass.getName());

				List<Object> entities = uadDisplay.search(
					selectedUser.getUserId(), groupIds, null, null, null,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS);

				for (Object entity : entities) {
					uadAnonymizer.autoAnonymize(
						entity, selectedUser.getUserId(), anonymousUser);
				}
			}
		}

		doReviewableRedirect(actionRequest, actionResponse);
	}

	@Reference
	private UADAnonymizerHelper _uadAnonymizerHelper;

}