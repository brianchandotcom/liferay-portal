/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.web.internal.portlet.action;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.service.CTCollectionService;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseTransactionalMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

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
public class MoveChangesMVCActionCommand
	extends BaseTransactionalMVCActionCommand {

	@Override
	public boolean processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortletException {

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setProductionModeWithSafeCloseable()) {

			return super.processAction(actionRequest, actionResponse);
		}
	}

	@Override
	protected void doTransactionalCommand(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long fromCTCollectionId = ParamUtil.getLong(
			actionRequest, "fromCTCollectionId");
		long toCTCollectionId = ParamUtil.getLong(
			actionRequest, "toCTCollectionId");
		long modelClassNameId = ParamUtil.getLong(
			actionRequest, "modelClassNameId");
		long modelClassPK = ParamUtil.getLong(actionRequest, "modelClassPK");

		long[] ctEntryIds = new long[0];

		if ((modelClassNameId <= 0) || (modelClassPK <= 0)) {
			ctEntryIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "ctEntryIds"), 0L);
		}

		if ((fromCTCollectionId != toCTCollectionId) &&
			(fromCTCollectionId != CTConstants.CT_COLLECTION_ID_PRODUCTION) &&
			(toCTCollectionId != CTConstants.CT_COLLECTION_ID_PRODUCTION)) {

			try {
				if ((modelClassNameId > 0) || (modelClassPK > 0)) {
					_ctCollectionService.moveCTEntry(
						fromCTCollectionId, toCTCollectionId, modelClassNameId,
						modelClassPK);
				}
				else {
					for (long ctEntryId : ctEntryIds) {
						CTEntry ctEntry = _ctEntryLocalService.fetchCTEntry(
							ctEntryId);

						if (ctEntry.getCtCollectionId() == fromCTCollectionId) {
							_ctCollectionService.moveCTEntry(
								fromCTCollectionId, toCTCollectionId,
								ctEntry.getModelClassNameId(),
								ctEntry.getModelClassPK());
						}
					}
				}
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
			if ((modelClassNameId > 0) && (modelClassPK > 0)) {
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
			else {
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
					"ctEntryIds", StringUtil.merge(ctEntryIds)
				).buildString();
			}
		}

		actionResponse.sendRedirect(redirect);
	}

	@Reference
	private CTCollectionService _ctCollectionService;

	@Reference
	private CTEntryLocalService _ctEntryLocalService;

	@Reference
	private Portal _portal;

}