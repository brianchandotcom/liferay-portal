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

package com.liferay.commerce.data.integration.web.internal.portlet.action;

import com.liferay.commerce.data.integration.constants.CommerceDataIntegrationPortletKeys;
import com.liferay.commerce.data.integration.service.CommerceDataIntegrationProcessLogService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author guywandji
 * @author Alessio Antonio Rendina
 */
@Component(
	property = {
		"javax.portlet.name=" + CommerceDataIntegrationPortletKeys.COMMERCE_DATA_INTEGRATION,
		"mvc.command.name=editCommerceDataIntegrationProcessLog"
	},
	service = MVCActionCommand.class
)
public class EditCommerceDataIntegrationProcessLogActionCommand
	extends BaseMVCActionCommand {

	protected void deleteCommerceDataIntegrationProcessLog(
			ActionRequest actionRequest)
		throws PortalException {

		long[] deleteCDataIntegrationProcessLogIds = null;

		long cDataIntegrationProcessLogId = ParamUtil.getLong(
			actionRequest, "cDataIntegrationProcessLogId");

		if (cDataIntegrationProcessLogId > 0) {
			deleteCDataIntegrationProcessLogIds = new long[] {
				cDataIntegrationProcessLogId
			};
		}
		else {
			deleteCDataIntegrationProcessLogIds = StringUtil.split(
				ParamUtil.getString(
					actionRequest, "deleteCDataIntegrationProcessLogIds"),
				0L);
		}

		for (long deleteCDataIntegrationProcessId :
				deleteCDataIntegrationProcessLogIds) {

			_commerceDataIntegrationProcessLogService.
				deleteCommerceDataIntegrationProcessLog(
					deleteCDataIntegrationProcessId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (Constants.DELETE.equals(cmd)) {
				deleteCommerceDataIntegrationProcessLog(actionRequest);
			}
		}
		catch (Exception e) {
			SessionErrors.add(actionRequest, e.getClass());

			actionResponse.setRenderParameter("mvcPath", "/error.jsp");
		}
	}

	@Reference
	private CommerceDataIntegrationProcessLogService
		_commerceDataIntegrationProcessLogService;

}