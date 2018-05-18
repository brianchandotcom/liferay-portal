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

package com.liferay.commerce.product.content.web.internal.portlet.action;

import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.util.CPContentContributor;
import com.liferay.commerce.product.util.CPContentContributorRegistry;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.io.IOException;

import java.util.Iterator;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CPPortletKeys.CP_CONTENT_WEB,
		"mvc.command.name=checkCPInstance"
	},
	service = MVCActionCommand.class
)
public class CheckCPInstanceMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		long cpDefinitionId = ParamUtil.getLong(
			actionRequest, "cpDefinitionId");
		String ddmFormValues = ParamUtil.getString(
			actionRequest, "ddmFormValues");

		try {
			CPInstance cpInstance = _cpInstanceHelper.getCPInstance(
				cpDefinitionId, ddmFormValues);

			if (cpInstance != null) {
				jsonObject.put("cpInstanceExist", true);
				jsonObject.put("cpInstanceId", cpInstance.getCPInstanceId());
				jsonObject.put("gtin", cpInstance.getGtin());
				jsonObject.put(
					"manufacturerPartNumber",
					cpInstance.getManufacturerPartNumber());

				jsonObject.put("sku", cpInstance.getSku());

				List<CPContentContributor> cpContentContributors =
					_cpContentContributorRegistry.getCPContentContributors();

				for (CPContentContributor cpContentContributor :
						cpContentContributors) {

					JSONObject valueJSONObject = cpContentContributor.getValue(
						cpInstance,
						_portal.getHttpServletRequest(actionRequest));

					Iterator<String> iterator = valueJSONObject.keys();

					while (iterator.hasNext()) {
						String key = iterator.next();

						jsonObject.put(key, valueJSONObject.get(key));
					}
				}
			}
			else {
				jsonObject.put("cpInstanceExist", false);
			}

			jsonObject.put("success", true);
		}
		catch (Exception e) {
			_log.error(e, e);

			jsonObject.put("error", e.getMessage());
			jsonObject.put("success", false);
		}

		hideDefaultErrorMessage(actionRequest);
		hideDefaultSuccessMessage(actionRequest);

		writeJSON(actionResponse, jsonObject);
	}

	protected void writeJSON(ActionResponse actionResponse, Object object)
		throws IOException {

		HttpServletResponse httpServletResponse =
			_portal.getHttpServletResponse(actionResponse);

		httpServletResponse.setContentType(ContentTypes.APPLICATION_JSON);

		ServletResponseUtil.write(httpServletResponse, object.toString());

		httpServletResponse.flushBuffer();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CheckCPInstanceMVCActionCommand.class);

	@Reference
	private CPContentContributorRegistry _cpContentContributorRegistry;

	@Reference
	private CPInstanceHelper _cpInstanceHelper;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Portal _portal;

}