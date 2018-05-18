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

package com.liferay.commerce.product.definitions.web.internal.portlet.action;

import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.definitions.web.portlet.action.ActionHelper;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.service.CPDefinitionOptionValueRelService;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CPPortletKeys.CP_DEFINITIONS,
		"mvc.command.name=editProductDefinitionOptionValueRel"
	},
	service = MVCActionCommand.class
)
public class EditCPDefinitionOptionValueRelMVCActionCommand
	extends BaseMVCActionCommand {

	protected void deleteCPDefinitionOptionValueRels(
			ActionRequest actionRequest)
		throws Exception {

		long cpDefinitionOptionValueRelId = ParamUtil.getLong(
			actionRequest, "cpDefinitionOptionValueRelId");

		if (cpDefinitionOptionValueRelId > 0) {
			_cpDefinitionOptionValueRelService.deleteCPDefinitionOptionValueRel(
				cpDefinitionOptionValueRelId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				CPDefinitionOptionValueRel cpDefinitionOptionValueRel =
					updateCPDefinitionOptionValueRel(actionRequest);

				jsonObject.put(
					"cpDefinitionOptionValueRelId",
					cpDefinitionOptionValueRel.
						getCPDefinitionOptionValueRelId());
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteCPDefinitionOptionValueRels(actionRequest);
			}

			jsonObject.put("success", true);
		}
		catch (Exception e) {
			_log.error(e);

			jsonObject.put("message", e.getMessage());
			jsonObject.put("success", false);
		}

		hideDefaultSuccessMessage(actionRequest);

		_actionHelper.writeJSON(actionRequest, actionResponse, jsonObject);
	}

	protected CPDefinitionOptionValueRel updateCPDefinitionOptionValueRel(
			ActionRequest actionRequest)
		throws Exception {

		long cpDefinitionOptionValueRelId = ParamUtil.getLong(
			actionRequest, "cpDefinitionOptionValueRelId");

		long cpDefinitionOptionRelId = ParamUtil.getLong(
			actionRequest, "cpDefinitionOptionRelId");
		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "name");
		double priority = ParamUtil.getDouble(actionRequest, "priority");
		String key = ParamUtil.getString(actionRequest, "key");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CPDefinitionOptionValueRel.class.getName(), actionRequest);

		CPDefinitionOptionValueRel cpDefinitionOptionValueRel = null;

		if (cpDefinitionOptionValueRelId <= 0) {

			// Add commerce product definition option value rel

			cpDefinitionOptionValueRel =
				_cpDefinitionOptionValueRelService.
					addCPDefinitionOptionValueRel(
						cpDefinitionOptionRelId, nameMap, priority, key,
						serviceContext);
		}
		else {

			// Update commerce product definition option value rel

			cpDefinitionOptionValueRel =
				_cpDefinitionOptionValueRelService.
					updateCPDefinitionOptionValueRel(
						cpDefinitionOptionValueRelId, nameMap, priority, key,
						serviceContext);
		}

		return cpDefinitionOptionValueRel;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditCPDefinitionOptionValueRelMVCActionCommand.class);

	@Reference
	private ActionHelper _actionHelper;

	@Reference
	private CPDefinitionOptionValueRelService
		_cpDefinitionOptionValueRelService;

	@Reference
	private JSONFactory _jsonFactory;

}