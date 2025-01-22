/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.web.internal.portlet.action;

import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.service.CTCollectionService;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseTransactionalMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(
	property = {
		"javax.portlet.name=" + CTPortletKeys.PUBLICATIONS,
		"mvc.command.name=/change_tracking/discard_changes"
	},
	service = MVCActionCommand.class
)
public class DiscardChangesMVCActionCommand
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

		long ctCollectionId = ParamUtil.getLong(
			actionRequest, "ctCollectionId");

		long modelClassNameId = ParamUtil.getLong(
			actionRequest, "modelClassNameId");
		long modelClassPK = ParamUtil.getLong(actionRequest, "modelClassPK");

		if ((modelClassNameId <= 0) || (modelClassPK <= 0)) {
			long[] ctEntryIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "ctEntryIds"), 0L);

			for (long ctEntryId : ctEntryIds) {
				CTEntry ctEntry = _ctEntryLocalService.fetchCTEntry(ctEntryId);

				if (ctEntry != null) {
					_ctCollectionService.discardCTEntry(
						ctCollectionId, ctEntry.getModelClassNameId(),
						ctEntry.getModelClassPK());
				}
			}
		}
		else {
			_ctCollectionService.discardCTEntry(
				ctCollectionId, modelClassNameId, modelClassPK);
		}

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		if (Validator.isNotNull(redirect)) {
			sendRedirect(actionRequest, actionResponse, redirect);
		}
	}

	@Reference
	private CTCollectionService _ctCollectionService;

	@Reference
	private CTEntryLocalService _ctEntryLocalService;

}