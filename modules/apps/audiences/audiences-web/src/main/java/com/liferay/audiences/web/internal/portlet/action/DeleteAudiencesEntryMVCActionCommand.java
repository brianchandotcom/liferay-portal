/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.audiences.web.internal.portlet.action;

import com.liferay.audiences.constants.AudiencesPortletKeys;
import com.liferay.audiences.service.AudiencesEntryService;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;

import jakarta.portlet.ActionRequest;
import jakarta.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = {
		"jakarta.portlet.name=" + AudiencesPortletKeys.AUDIENCES,
		"mvc.command.name=/audiences/delete_audiences_entry"
	},
	service = MVCActionCommand.class
)
public class DeleteAudiencesEntryMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long[] deleteAudiencesEntryIds = null;

		long audiencesEntryId = ParamUtil.getLong(
			actionRequest, "audiencesEntryId");

		if (audiencesEntryId > 0) {
			deleteAudiencesEntryIds = new long[] {audiencesEntryId};
		}
		else {
			deleteAudiencesEntryIds = ParamUtil.getLongValues(
				actionRequest, "rowIds");
		}

		for (long deleteAudiencesEntryId : deleteAudiencesEntryIds) {
			_audiencesEntryService.deleteAudiencesEntry(deleteAudiencesEntryId);
		}

		sendRedirect(actionRequest, actionResponse);
	}

	@Reference
	private AudiencesEntryService _audiencesEntryService;

}