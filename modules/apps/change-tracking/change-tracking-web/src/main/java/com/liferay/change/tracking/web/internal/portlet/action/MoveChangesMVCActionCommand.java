/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.web.internal.portlet.action;

import com.liferay.change.tracking.conflict.ConflictInfo;
import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTProcess;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTCollectionService;
import com.liferay.portal.background.task.model.BackgroundTask;
import com.liferay.portal.background.task.service.BackgroundTaskLocalService;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

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
					CTProcess ctProcess = _ctCollectionService.moveCTEntries(
						fromCTCollectionId, toCTCollectionId, ctEntryIds);

					if (ctProcess != null) {
						BackgroundTask backgroundTask =
							_backgroundTaskLocalService.fetchBackgroundTask(
								ctProcess.getBackgroundTaskId());

						if (backgroundTask.getStatus() ==
								BackgroundTaskConstants.STATUS_FAILED) {

							SessionErrors.add(actionRequest, "failedMove");
						}
					}
				}
				else {
					SessionErrors.add(actionRequest, "failedMove");
				}
			}
			catch (PortalException portalException) {
				SessionErrors.add(actionRequest, portalException.getClass());
			}

			String redirect = ParamUtil.getString(actionRequest, "redirect");

			actionResponse.sendRedirect(redirect);
		}
	}

	@Reference
	private BackgroundTaskLocalService _backgroundTaskLocalService;

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference
	private CTCollectionService _ctCollectionService;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}