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

package com.liferay.search.experiences.web.internal.blueprint.portlet.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.search.experiences.constants.SXPPortletKeys;
import com.liferay.search.experiences.exception.SXPElementDefaultEntryException;
import com.liferay.search.experiences.service.SXPElementService;
import com.liferay.search.experiences.web.internal.blueprint.constants.SXPBlueprintMVCCommandNames;
import com.liferay.search.experiences.web.internal.blueprint.constants.SXPBlueprintWebKeys;
import com.liferay.search.experiences.web.internal.blueprint.util.SXPBlueprintRequestUtil;

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
		"javax.portlet.name=" + SXPPortletKeys.SXP_BLUEPRINT,
		"mvc.command.name=" + SXPBlueprintMVCCommandNames.DELETE_SXP_ELEMENT
	},
	service = MVCActionCommand.class
)
public class DeleteSXPElementMVCActionCommand extends BaseMVCActionCommand {

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
		long[] deleteIds = _getElementIds(actionRequest);

		try {
			for (long sxpElementId : deleteIds) {
				_sxpElementService.deleteSXPElement(sxpElementId);
			}
		}
		catch (PortalException portalException) {
			if (portalException instanceof SXPElementDefaultEntryException) {
				if (_log.isDebugEnabled()) {
					_log.debug(portalException.getMessage(), portalException);
				}

				SessionErrors.add(
					actionRequest, SXPElementDefaultEntryException.class);

				hideDefaultErrorMessage(actionRequest);
			}
			else {
				_log.error(portalException.getMessage(), portalException);

				SessionErrors.add(
					actionRequest, SXPBlueprintWebKeys.ERROR,
					portalException.getMessage());
			}
		}
	}

	private long[] _getElementIds(ActionRequest actionRequest) {
		long[] sxpElementIds = null;

		long sxpElementId = SXPBlueprintRequestUtil.getSXPElementId(
			actionRequest);

		if (sxpElementId > 0) {
			sxpElementIds = new long[] {sxpElementId};
		}
		else {
			sxpElementIds = ParamUtil.getLongValues(
				actionRequest, SXPBlueprintWebKeys.ROW_IDS);
		}

		return sxpElementIds;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DeleteSXPElementMVCActionCommand.class);

	@Reference
	private SXPElementService _sxpElementService;

}