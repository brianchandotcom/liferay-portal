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

package com.liferay.search.experiences.web.internal.blueprint.admin.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.search.experiences.constants.SXPPortletKeys;
import com.liferay.search.experiences.exception.SXPElementReadOnlyException;
import com.liferay.search.experiences.model.SXPElement;
import com.liferay.search.experiences.service.SXPElementService;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Olivia Yu
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"javax.portlet.name=" + SXPPortletKeys.SXP_BLUEPRINT_ADMIN,
		"mvc.command.name=/sxp_blueprint_admin/edit_sxp_element"
	},
	service = MVCActionCommand.class
)
public class EditSXPElementMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.DELETE)) {
				_deleteSXPElements(actionRequest);
			}
		}
		catch (Exception exception) {
			if (exception instanceof SXPElementReadOnlyException) {
				hideDefaultErrorMessage(actionRequest);
			}

			SessionErrors.add(actionRequest, exception.getClass(), exception);

			String redirect = ParamUtil.getString(actionRequest, "redirect");

			sendRedirect(actionRequest, actionResponse, redirect);
		}
	}

	@Reference(unbind = "-")
	protected void setSXPElementService(SXPElementService sxpElementService) {
		_sxpElementService = sxpElementService;
	}

	private void _deleteSXPElements(ActionRequest actionRequest)
		throws Exception {

		long[] deleteSXPElementIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "id"), 0L);

		for (long deleteSXPElementId : deleteSXPElementIds) {
			SXPElement sxpElement = _sxpElementService.getSXPElement(
				deleteSXPElementId);

			if (sxpElement.isReadOnly()) {
				throw new SXPElementReadOnlyException();
			}
		}

		for (long deleteSXPElementId : deleteSXPElementIds) {
			_sxpElementService.deleteSXPElement(deleteSXPElementId);
		}
	}

	private SXPElementService _sxpElementService;

}