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
import com.liferay.portal.kernel.exception.NoSuchModelException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.user.associated.data.anonymizer.UADAnonymizer;
import com.liferay.user.associated.data.constants.UserAssociatedDataPortletKeys;
import com.liferay.user.associated.data.display.UADDisplay;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Samuel Trong Tran
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + UserAssociatedDataPortletKeys.USER_ASSOCIATED_DATA,
		"mvc.command.name=/delete_uad_applications"
	},
	service = MVCActionCommand.class
)
public class DeleteUADApplicationsMVCActionCommand
	extends BaseUADMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long[] groupIds = ParamUtil.getLongValues(actionRequest, "groupIds");

		long selectedUserId = getSelectedUserId(actionRequest);

		for (String applicationKey : getApplicationKeys(actionRequest)) {
			for (UADDisplay uadDisplay :
					uadRegistry.getApplicationUADDisplays(applicationKey)) {

				Class<?> typeClass = uadDisplay.getTypeClass();

				UADAnonymizer uadAnonymizer = uadRegistry.getUADAnonymizer(
					typeClass.getName());

				List<Object> entities = uadDisplay.search(
					selectedUserId, groupIds, null, null, null,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS);

				for (Object entity : entities) {
					try {
						uadAnonymizer.delete(entity);
					}
					catch (NoSuchModelException nsme) {
						if (_log.isDebugEnabled()) {
							_log.debug(nsme, nsme);
						}
					}
				}
			}
		}

		doReviewableRedirect(actionRequest, actionResponse);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DeleteUADApplicationsMVCActionCommand.class);

}