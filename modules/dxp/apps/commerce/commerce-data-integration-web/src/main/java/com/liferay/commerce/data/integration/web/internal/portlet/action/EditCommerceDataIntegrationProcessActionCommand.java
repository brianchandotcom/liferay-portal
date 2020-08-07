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

import com.liferay.commerce.data.integration.constants.CommerceDataIntegrationConstants;
import com.liferay.commerce.data.integration.constants.CommerceDataIntegrationPortletKeys;
import com.liferay.commerce.data.integration.model.CommerceDataIntegrationProcess;
import com.liferay.commerce.data.integration.service.CommerceDataIntegrationProcessService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = {
		"javax.portlet.name=" + CommerceDataIntegrationPortletKeys.COMMERCE_DATA_INTEGRATION,
		"mvc.command.name=editCommerceDataIntegrationProcess"
	},
	service = MVCActionCommand.class
)
public class EditCommerceDataIntegrationProcessActionCommand
	extends BaseMVCActionCommand {

	protected void deleteCommerceDataIntegrationProcess(
			ActionRequest actionRequest)
		throws PortalException {

		long[] deleteCDataIntegrationProcessIds = null;

		long commerceDataIntegrationProcessId = ParamUtil.getLong(
			actionRequest, "commerceDataIntegrationProcessId");

		if (commerceDataIntegrationProcessId > 0) {
			deleteCDataIntegrationProcessIds = new long[] {
				commerceDataIntegrationProcessId
			};
		}
		else {
			deleteCDataIntegrationProcessIds = StringUtil.split(
				ParamUtil.getString(
					actionRequest, "deleteCDataIntegrationProcessIds"),
				0L);
		}

		for (long deleteCDataIntegrationProcessId :
				deleteCDataIntegrationProcessIds) {

			_commerceDataIntegrationProcessService.
				deleteCommerceDataIntegrationProcess(
					deleteCDataIntegrationProcessId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateCommerceDataIntegrationProcess(
					actionRequest, actionResponse);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteCommerceDataIntegrationProcess(actionRequest);
			}
			else if (cmd.equals("runProcess")) {
				HttpServletResponse httpServletResponse =
					_portal.getHttpServletResponse(actionResponse);

				httpServletResponse.setContentType(
					ContentTypes.APPLICATION_JSON);

				writeJSON(actionResponse, runProcess(actionRequest));

				hideDefaultSuccessMessage(actionRequest);
			}
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			SessionErrors.add(actionRequest, exception.getClass());
		}
	}

	protected JSONObject runProcess(ActionRequest actionRequest) {
		JSONObject jsonObject = _jsonFactory.createJSONObject();

		long commerceDataIntegrationProcessId = ParamUtil.getLong(
			actionRequest, "commerceDataIntegrationProcessId");

		try {
			_sendMessage(commerceDataIntegrationProcessId);

			Thread.sleep(2000);
		}
		catch (Exception exception) {
			hideDefaultErrorMessage(actionRequest);

			_log.error(exception, exception);

			jsonObject.put(
				"error", exception.getMessage()
			).put(
				"success", false
			);
		}

		jsonObject.put("success", true);

		return jsonObject;
	}

	protected CommerceDataIntegrationProcess
			updateCommerceDataIntegrationProcess(
				ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long commerceDataIntegrationProcessId = ParamUtil.getLong(
			actionRequest, "commerceDataIntegrationProcessId");

		String name = ParamUtil.getString(actionRequest, "name");

		String processType = ParamUtil.getString(actionRequest, "processType");

		UnicodeProperties typeSettingsUnicodeProperties = new UnicodeProperties(
			true);

		typeSettingsUnicodeProperties.fastLoad(
			ParamUtil.getString(actionRequest, "typeSettings"));

		CommerceDataIntegrationProcess commerceDataIntegrationProcess = null;

		if (commerceDataIntegrationProcessId > 0) {
			commerceDataIntegrationProcess =
				_commerceDataIntegrationProcessService.
					updateCommerceDataIntegrationProcess(
						commerceDataIntegrationProcessId, name,
						typeSettingsUnicodeProperties);
		}
		else {
			commerceDataIntegrationProcess =
				_commerceDataIntegrationProcessService.
					addCommerceDataIntegrationProcess(
						_portal.getUserId(actionRequest), name, processType,
						typeSettingsUnicodeProperties);
		}

		return commerceDataIntegrationProcess;
	}

	protected void writeJSON(ActionResponse actionResponse, Object object)
		throws IOException {

		HttpServletResponse httpServletResponse =
			_portal.getHttpServletResponse(actionResponse);

		httpServletResponse.setContentType(ContentTypes.APPLICATION_JSON);

		ServletResponseUtil.write(httpServletResponse, object.toString());

		httpServletResponse.flushBuffer();
	}

	private void _sendMessage(long commerceDataIntegrationProcessId) {
		JSONObject payLoad = JSONUtil.put(
			"commerceDataIntegrationProcessId",
			commerceDataIntegrationProcessId);

		MessageBusUtil.sendMessage(
			CommerceDataIntegrationConstants.EXECUTOR_DESTINATION_NAME,
			payLoad.toString());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditCommerceDataIntegrationProcessActionCommand.class);

	@Reference
	private CommerceDataIntegrationProcessService
		_commerceDataIntegrationProcessService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Portal _portal;

}