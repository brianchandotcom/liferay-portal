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

package com.liferay.change.tracking.web.internal.portlet.action;

import com.liferay.change.tracking.conflict.ConflictInfo;
import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTCollectionService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cheryl Tang
 */
@Component(
	property = {
		"javax.portlet.name=" + CTPortletKeys.PUBLICATIONS,
		"mvc.command.name=/change_tracking/move_changes"
	},
	service = MVCActionCommand.class
)
public class MoveChangesMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long fromCTCollectionId = ParamUtil.getLong(
			actionRequest, "fromCTCollectionId");

		long toCTCollectionId = ParamUtil.getLong(
			actionRequest, "toCTCollectionId");

		if ((fromCTCollectionId != toCTCollectionId) &&
			(fromCTCollectionId != CTConstants.CT_COLLECTION_ID_PRODUCTION) &&
			(toCTCollectionId != CTConstants.CT_COLLECTION_ID_PRODUCTION)) {

			long[] ctEntryIds = ParamUtil.getLongValues(
				actionRequest, "ctEntryIds");

			try {
				CTCollection fromCTCollection =
					_ctCollectionLocalService.getCTCollection(
						fromCTCollectionId);

				CTCollection toCTCollection =
					_ctCollectionLocalService.getCTCollection(toCTCollectionId);

				Map<Long, List<ConflictInfo>> conflictInfoMap =
					_ctCollectionLocalService.checkConflicts(
						fromCTCollection.getCompanyId(), ctEntryIds,
						fromCTCollectionId, fromCTCollection.getName(),
						toCTCollectionId, toCTCollection.getName());

				if (conflictInfoMap.isEmpty()) {
					_ctCollectionService.moveCTEntries(
						fromCTCollectionId, toCTCollectionId, ctEntryIds);
				}
				else {
					SessionErrors.add(actionRequest, "conflictsFound");
				}
			}
			catch (PortalException portalException) {
				SessionErrors.add(actionRequest, portalException.getClass());
			}
		}

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		JSONPortletResponseUtil.writeJSON(
			actionRequest, actionResponse,
			JSONUtil.put(
				"redirect", true
			).put(
				"redirectURL", redirect
			));
	}

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference
	private CTCollectionService _ctCollectionService;

}