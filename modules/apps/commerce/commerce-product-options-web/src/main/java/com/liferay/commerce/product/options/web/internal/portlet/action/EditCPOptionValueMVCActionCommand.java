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

package com.liferay.commerce.product.options.web.internal.portlet.action;

import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.exception.CPOptionValueKeyException;
import com.liferay.commerce.product.model.CPOptionValue;
import com.liferay.commerce.product.service.CPOptionValueService;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"javax.portlet.name=" + CPPortletKeys.CP_OPTIONS,
		"mvc.command.name=/commerce_product_options/edit_cp_option_value"
	},
	service = MVCActionCommand.class
)
public class EditCPOptionValueMVCActionCommand extends BaseMVCActionCommand {

	protected void deleteCPOptionValues(ActionRequest actionRequest)
		throws Exception {

		long[] deleteCPOptionValueIds = null;

		long cpOptionValueId = ParamUtil.getLong(
			actionRequest, "cpOptionValueId");

		if (cpOptionValueId > 0) {
			deleteCPOptionValueIds = new long[] {cpOptionValueId};
		}
		else {
			deleteCPOptionValueIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "deleteCPOptionValueIds"),
				0L);
		}

		for (long deleteCPOptionValueId : deleteCPOptionValueIds) {
			_cpOptionValueService.deleteCPOptionValue(deleteCPOptionValueId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		HttpServletResponse httpServletResponse =
			_portal.getHttpServletResponse(actionResponse);

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		try {
			if (cmd.equals(Constants.DELETE)) {
				deleteCPOptionValues(actionRequest);
			}
			else if (cmd.equals(Constants.ADD) ||
					 cmd.equals(Constants.UPDATE)) {

				CPOptionValue cpOptionValue = updateCPOptionValue(
					actionRequest);

				jsonObject.put(
					"cpOptionValueId", cpOptionValue.getCPOptionValueId());
			}

			jsonObject.put("success", true);
		}
		catch (Exception exception) {
			String message = LanguageUtil.get(
				actionRequest.getLocale(), "your-request-failed-to-complete");

			if (!Validator.isBlank(exception.getMessage())) {
				message = exception.getMessage();
			}
			else {
				_log.error(exception, exception);
			}

			jsonObject.put(
				"message", message
			).put(
				"success", false
			);
		}

		hideDefaultSuccessMessage(actionRequest);

		httpServletResponse.setContentType(ContentTypes.APPLICATION_JSON);

		writeJSON(actionRequest, actionResponse, jsonObject);
	}

	protected CPOptionValue updateCPOptionValue(ActionRequest actionRequest)
		throws Exception {

		long cpOptionValueId = ParamUtil.getLong(
			actionRequest, "cpOptionValueId");

		long cpOptionId = ParamUtil.getLong(actionRequest, "cpOptionId");
		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "name");
		double priority = ParamUtil.getDouble(actionRequest, "priority");
		String key = ParamUtil.getString(actionRequest, "key");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CPOptionValue.class.getName(), actionRequest);

		CPOptionValue cpOptionValue = null;

		try {
			if (cpOptionValueId <= 0) {

				// Add commerce product option value

				cpOptionValue = _cpOptionValueService.addCPOptionValue(
					cpOptionId, nameMap, priority, key, serviceContext);
			}
			else {

				// Update commerce product option value

				cpOptionValue = _cpOptionValueService.updateCPOptionValue(
					cpOptionValueId, nameMap, priority, key, serviceContext);
			}
		}
		catch (CPOptionValueKeyException cpOptionValueKeyException) {
			throw new CPOptionValueKeyException(
				LanguageUtil.format(
					_portal.getHttpServletRequest(actionRequest),
					"the-key-x-is-already-being-used", key),
				cpOptionValueKeyException);
		}

		return cpOptionValue;
	}

	protected void writeJSON(
			PortletRequest portletRequest, ActionResponse actionResponse,
			Object object)
		throws IOException {

		HttpServletResponse httpServletResponse =
			_portal.getHttpServletResponse(actionResponse);

		httpServletResponse.setContentType(ContentTypes.APPLICATION_JSON);

		ServletResponseUtil.write(httpServletResponse, object.toString());

		httpServletResponse.flushBuffer();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditCPOptionValueMVCActionCommand.class);

	@Reference
	private CPOptionValueService _cpOptionValueService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Portal _portal;

}