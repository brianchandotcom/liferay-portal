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
import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.service.CPOptionService;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
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

import java.io.IOException;

import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CPPortletKeys.CP_OPTIONS,
		"mvc.command.name=editProductOption"
	},
	service = MVCActionCommand.class
)
public class EditCPOptionMVCActionCommand extends BaseMVCActionCommand {

	protected void deleteCPOptions(long cpOptionId, ActionRequest actionRequest)
		throws Exception {

		long[] deleteCPOptionIds = null;

		if (cpOptionId > 0) {
			deleteCPOptionIds = new long[] {cpOptionId};
		}
		else {
			deleteCPOptionIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "deleteCPOptionIds"), 0L);
		}

		for (long deleteCPOptionId : deleteCPOptionIds) {
			_cpOptionService.deleteCPOption(deleteCPOptionId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		long cpOptionId = ParamUtil.getLong(actionRequest, "cpOptionId");

		HttpServletResponse httpServletResponse =
			_portal.getHttpServletResponse(actionResponse);

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		try {
			if (cmd.equals(Constants.DELETE)) {
				deleteCPOptions(cpOptionId, actionRequest);
			}
			else if (cmd.equals(Constants.ADD) ||
					 cmd.equals(Constants.UPDATE)) {

				CPOption cpOption = updateCPOption(cpOptionId, actionRequest);

				jsonObject.put("cpOptionId", cpOption.getCPOptionId());
			}

			jsonObject.put("success", true);
		}
		catch (Exception e) {
			_log.error(e);

			jsonObject.put("message", e.getMessage());
			jsonObject.put("success", false);
		}

		hideDefaultSuccessMessage(actionRequest);

		httpServletResponse.setContentType(ContentTypes.APPLICATION_JSON);

		writeJSON(actionResponse, jsonObject);
	}

	protected CPOption updateCPOption(
			long cpOptionId, ActionRequest actionRequest)
		throws Exception {

		Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "title");
		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(actionRequest, "description");
		String ddmFormFieldTypeName = ParamUtil.getString(
			actionRequest, "DDMFormFieldTypeName");
		boolean facetable = ParamUtil.getBoolean(actionRequest, "facetable");
		boolean required = ParamUtil.getBoolean(actionRequest, "required");
		boolean skuContributor = ParamUtil.getBoolean(
			actionRequest, "skuContributor");
		String key = ParamUtil.getString(actionRequest, "key");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CPOption.class.getName(), actionRequest);

		CPOption cpOption = null;

		if (cpOptionId <= 0) {

			// Add commerce product option

			cpOption = _cpOptionService.addCPOption(
				titleMap, descriptionMap, ddmFormFieldTypeName, facetable,
				required, skuContributor, key, serviceContext);
		}
		else {

			// Update commerce product option

			cpOption = _cpOptionService.updateCPOption(
				cpOptionId, titleMap, descriptionMap, ddmFormFieldTypeName,
				facetable, required, skuContributor, key, serviceContext);
		}

		return cpOption;
	}

	protected void writeJSON(ActionResponse actionResponse, Object jsonObj)
		throws IOException {

		HttpServletResponse httpServletResponse =
			_portal.getHttpServletResponse(actionResponse);

		httpServletResponse.setContentType(ContentTypes.APPLICATION_JSON);

		ServletResponseUtil.write(httpServletResponse, jsonObj.toString());

		httpServletResponse.flushBuffer();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditCPOptionMVCActionCommand.class);

	@Reference
	private CPOptionService _cpOptionService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Portal _portal;

}