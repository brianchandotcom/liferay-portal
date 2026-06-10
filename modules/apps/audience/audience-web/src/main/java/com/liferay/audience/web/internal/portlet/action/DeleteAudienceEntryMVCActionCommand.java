/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.audience.web.internal.portlet.action;

import com.liferay.audience.constants.AudiencePortletKeys;
import com.liferay.audience.service.AudienceEntryService;
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
		"jakarta.portlet.name=" + AudiencePortletKeys.AUDIENCE,
		"mvc.command.name=/audience/delete_audience_entry"
	},
	service = MVCActionCommand.class
)
public class DeleteAudienceEntryMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long[] deleteAudienceEntryIds = null;

		long audienceEntryId = ParamUtil.getLong(
			actionRequest, "audienceEntryId");

		if (audienceEntryId > 0) {
			deleteAudienceEntryIds = new long[] {audienceEntryId};
		}
		else {
			deleteAudienceEntryIds = ParamUtil.getLongValues(
				actionRequest, "rowIds");
		}

		for (long deleteAudienceEntryId : deleteAudienceEntryIds) {
			_audienceEntryService.deleteAudienceEntry(deleteAudienceEntryId);
		}

		sendRedirect(actionRequest, actionResponse);
	}

	@Reference
	private AudienceEntryService _audienceEntryService;

}