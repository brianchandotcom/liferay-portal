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
import com.liferay.commerce.product.options.web.internal.util.CPOptionsPortletUtil;
import com.liferay.commerce.product.service.CPOptionService;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;
import java.util.Map;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

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
		"mvc.command.name=cpOptions"
	},
	service = MVCResourceCommand.class
)
public class CPOptionsMVCResourceCommand extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		JSONArray jsonArray = _jsonFactory.createJSONArray();

		List<CPOption> cpOptions = _cpOptionService.findCPOptionByCompanyId(
			themeDisplay.getCompanyId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			CPOptionsPortletUtil.getCPOptionOrderByComparator("name", "asc"));

		for (CPOption cpOption : cpOptions) {
			JSONObject jsonObject = _jsonFactory.createJSONObject();

			jsonObject.put(
				"cpOptionId", cpOption.getCPOptionId()
			).put(
				"key", cpOption.getKey()
			).put(
				"name", cpOption.getName(themeDisplay.getLanguageId())
			);

			if (_hasDDMFormFieldTypeProperties(
					cpOption.getDDMFormFieldTypeName()) &&
				_isListFieldTypeDataDomain(
					cpOption.getDDMFormFieldTypeName())) {

				jsonObject.put("hasValues", true);
			}

			jsonArray.put(jsonObject);
		}

		HttpServletResponse httpServletResponse =
			_portal.getHttpServletResponse(resourceResponse);

		httpServletResponse.setContentType(ContentTypes.APPLICATION_JSON);

		ServletResponseUtil.write(httpServletResponse, jsonArray.toString());
	}

	private boolean _hasDDMFormFieldTypeProperties(
		String ddmFormFieldTypeName) {

		Map<String, Object> properties =
			_ddmFormFieldTypeServicesTracker.getDDMFormFieldTypeProperties(
				ddmFormFieldTypeName);

		if (properties == null) {
			return false;
		}

		return true;
	}

	private boolean _isListFieldTypeDataDomain(String ddmFormFieldTypeName) {
		Map<String, Object> properties =
			_ddmFormFieldTypeServicesTracker.getDDMFormFieldTypeProperties(
				ddmFormFieldTypeName);

		String fieldTypeDataDomain = MapUtil.getString(
			properties, "ddm.form.field.type.data.domain");

		if (Validator.isNotNull(fieldTypeDataDomain) &&
			fieldTypeDataDomain.equals("list")) {

			return true;
		}

		return false;
	}

	@Reference
	private CPOptionService _cpOptionService;

	@Reference
	private DDMFormFieldTypeServicesTracker _ddmFormFieldTypeServicesTracker;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Portal _portal;

}