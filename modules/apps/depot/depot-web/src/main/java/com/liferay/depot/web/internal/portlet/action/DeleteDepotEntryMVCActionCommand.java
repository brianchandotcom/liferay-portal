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

package com.liferay.depot.web.internal.portlet.action;

import com.liferay.depot.exception.DepotEntryStagedException;
import com.liferay.depot.service.DepotEntryService;
import com.liferay.depot.web.internal.constants.DepotPortletKeys;
import com.liferay.document.library.kernel.exception.RequiredFileEntryTypeException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = {
		"javax.portlet.name=" + DepotPortletKeys.DEPOT_ADMIN,
		"mvc.command.name=/depot/delete_depot_entry"
	},
	service = MVCActionCommand.class
)
public class DeleteDepotEntryMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			_deleteDepotEntry(actionRequest);
		}
		catch (DepotEntryStagedException depotEntryStagedException) {
			SessionErrors.add(actionRequest, DepotEntryStagedException.class);
		}
		catch (SystemException systemException) {
			if (_isRequiredFileEntryTypeException(systemException)) {
				SessionErrors.add(
					actionRequest, RequiredFileEntryTypeException.class);
			}
			else {
				throw systemException;
			}
		}
	}

	private void _deleteDepotEntry(ActionRequest actionRequest)
		throws PortalException {

		long depotEntryId = ParamUtil.getLong(actionRequest, "depotEntryId");

		if (depotEntryId > 0) {
			_depotEntryService.deleteDepotEntry(depotEntryId);
		}
		else {
			long[] deleteDepotEntryIds = ParamUtil.getLongValues(
				actionRequest, "rowIds");

			for (long deleteDepotEntryId : deleteDepotEntryIds) {
				_depotEntryService.deleteDepotEntry(deleteDepotEntryId);
			}
		}
	}

	private boolean _isRequiredFileEntryTypeException(Exception exception) {
		Throwable throwable = exception.getCause();

		while ((throwable != null) &&
			   !(throwable instanceof RequiredFileEntryTypeException)) {

			throwable = throwable.getCause();
		}

		if (throwable == null) {
			return false;
		}

		return true;
	}

	@Reference
	private DepotEntryService _depotEntryService;

}