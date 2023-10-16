/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.web.internal.portlet.action;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.change.tracking.service.CTCollectionService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

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
		long modelClassNameId = ParamUtil.getLong(
			actionRequest, "modelClassNameId");
		long modelClassPK = ParamUtil.getLong(actionRequest, "modelClassPK");

		if ((fromCTCollectionId != toCTCollectionId) &&
			(fromCTCollectionId != CTConstants.CT_COLLECTION_ID_PRODUCTION) &&
			(toCTCollectionId != CTConstants.CT_COLLECTION_ID_PRODUCTION)) {

			try {
				_ctCollectionService.moveCTEntry(
					fromCTCollectionId, toCTCollectionId, modelClassNameId,
					modelClassPK);
			}
			catch (PortalException portalException) {
				SessionErrors.add(actionRequest, portalException.getClass());
			}
		}
		else {
			SessionErrors.add(actionRequest, "failedMove");
		}

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		if (!SessionErrors.isEmpty(actionRequest)) {
			redirect = PortletURLBuilder.createRenderURL(
				_portal.getLiferayPortletResponse(actionResponse)
			).setMVCRenderCommandName(
				"/change_tracking/view_move_changes"
			).setRedirect(
				PortletURLBuilder.createRenderURL(
					_portal.getLiferayPortletResponse(actionResponse)
				).setMVCRenderCommandName(
					"/change_tracking/view_changes"
				).setParameter(
					"ctCollectionId", fromCTCollectionId
				).buildString()
			).setParameter(
				"ctCollectionId", fromCTCollectionId
			).setParameter(
				"modelClassNameId", modelClassNameId
			).setParameter(
				"modelClassPK", modelClassPK
			).buildString();
		}

		actionResponse.sendRedirect(redirect);
	}

	@Reference
	private CTCollectionService _ctCollectionService;

	@Reference
	private Portal _portal;

}