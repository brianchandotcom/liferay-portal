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
		Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "title");
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
						cpDefinitionOptionRelId, titleMap, priority, key,
						serviceContext);
		}
		else {

			// Update commerce product definition option value rel

			cpDefinitionOptionValueRel =
				_cpDefinitionOptionValueRelService.
					updateCPDefinitionOptionValueRel(
						cpDefinitionOptionValueRelId, titleMap, priority, key,
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