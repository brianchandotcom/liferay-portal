/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.search.tuning.blueprints.admin.web.internal.portlet.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.search.tuning.blueprints.admin.web.internal.constants.BlueprintsAdminMVCCommandNames;
import com.liferay.portal.search.tuning.blueprints.admin.web.internal.constants.BlueprintsAdminWebKeys;
import com.liferay.portal.search.tuning.blueprints.admin.web.internal.util.BlueprintsAdminRequestUtil;
import com.liferay.portal.search.tuning.blueprints.constants.BlueprintsPortletKeys;
import com.liferay.portal.search.tuning.blueprints.service.ElementService;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + BlueprintsPortletKeys.BLUEPRINTS_ADMIN,
		"mvc.command.name=" + BlueprintsAdminMVCCommandNames.DELETE_ELEMENT
	},
	service = MVCActionCommand.class
)
public class DeleteElementMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		_delete(actionRequest);

		sendRedirect(
			actionRequest, actionResponse,
			ParamUtil.getString(actionRequest, "redirect"));
	}

	private void _delete(ActionRequest actionRequest) {
		long[] deleteConfigurationIds = _getElementIds(actionRequest);

		try {
			for (long elementId : deleteConfigurationIds) {
				_elementService.deleteElement(elementId);
			}
		}
		catch (PortalException portalException) {
			_log.error(portalException.getMessage(), portalException);

			SessionErrors.add(
				actionRequest, BlueprintsAdminWebKeys.ERROR,
				portalException.getMessage());
		}
	}

	private long[] _getElementIds(ActionRequest actionRequest) {
		long[] elementIds = null;

		long elementId = BlueprintsAdminRequestUtil.getElementId(actionRequest);

		if (elementId > 0) {
			elementIds = new long[] {elementId};
		}
		else {
			elementIds = ParamUtil.getLongValues(
				actionRequest, BlueprintsAdminWebKeys.ROW_IDS);
		}

		return elementIds;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DeleteElementMVCActionCommand.class);

	@Reference
	private ElementService _elementService;

}