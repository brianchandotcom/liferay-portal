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

package com.liferay.sharing.web.internal.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.sharing.model.SharingEntry;
import com.liferay.sharing.service.SharingEntryLocalService;
import com.liferay.sharing.service.SharingEntryService;
import com.liferay.sharing.web.internal.constants.SharingPortletKeys;
import com.liferay.sharing.web.internal.display.SharingEntryPermissionDisplayAction;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = {
		"javax.portlet.name=" + SharingPortletKeys.MANAGE_COLLABORATORS,
		"mvc.command.name=/sharing/manage_collaborators"
	},
	service = MVCActionCommand.class
)
public class ManageCollaboratorsMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long[] deleteSharingEntryIds = ParamUtil.getLongValues(
			actionRequest, "deleteSharingEntryIds");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		for (long sharingEntryId : deleteSharingEntryIds) {
			_sharingEntryService.deleteSharingEntry(
				sharingEntryId, serviceContext);
		}

		String[] sharingEntryIdActionIdPairs = ParamUtil.getParameterValues(
			actionRequest, "sharingEntryIdActionIdPairs", new String[0], false);

		for (String sharingEntryIdActionIdPair : sharingEntryIdActionIdPairs) {
			String[] parts = StringUtil.split(sharingEntryIdActionIdPair);

			long sharingEntryId = Long.valueOf(parts[0]);

			SharingEntryPermissionDisplayAction
				sharingEntryPermissionDisplayActionKey =
					SharingEntryPermissionDisplayAction.parseFromActionId(
						parts[1]);

			SharingEntry sharingEntry =
				_sharingEntryLocalService.getSharingEntry(sharingEntryId);

			_sharingEntryService.updateSharingEntry(
				sharingEntryId,
				sharingEntryPermissionDisplayActionKey.getSharingEntryActions(),
				sharingEntry.isShareable(), sharingEntry.getExpirationDate(),
				serviceContext);
		}
	}

	@Reference
	private SharingEntryLocalService _sharingEntryLocalService;

	@Reference
	private SharingEntryService _sharingEntryService;

	@Reference
	private UserLocalService _userLocalService;

}