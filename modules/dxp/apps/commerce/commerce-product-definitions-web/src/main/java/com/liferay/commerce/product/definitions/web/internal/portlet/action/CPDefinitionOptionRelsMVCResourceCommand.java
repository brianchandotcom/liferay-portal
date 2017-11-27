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
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.service.CPDefinitionOptionRelService;
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
import com.liferay.portal.kernel.util.ParamUtil;
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
	immediate = true,
	property = {
		"javax.portlet.name=" + CPPortletKeys.CP_DEFINITIONS,
		"mvc.command.name=cpDefinitionOptionRels"
	},
	service = MVCResourceCommand.class
)
public class CPDefinitionOptionRelsMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long cpDefinitionId = ParamUtil.getLong(
			resourceRequest, "cpDefinitionId");

		JSONArray jsonArray = _jsonFactory.createJSONArray();

		List<CPDefinitionOptionRel> cpDefinitionOptionRels =
			_cpDefinitionOptionRelService.getCPDefinitionOptionRels(
				cpDefinitionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (CPDefinitionOptionRel cpDefinitionOptionRel :
				cpDefinitionOptionRels) {

			JSONObject jsonObject = _jsonFactory.createJSONObject();

			jsonObject.put(
				"cpDefinitionOptionRelId",
				cpDefinitionOptionRel.getCPDefinitionOptionRelId());

			jsonObject.put(
				"title",
				cpDefinitionOptionRel.getTitle(themeDisplay.getLanguageId()));

			jsonObject.put("priority", cpDefinitionOptionRel.getPriority());

			Map<String, Object> properties =
				_ddmFormFieldTypeServicesTracker.getDDMFormFieldTypeProperties(
					cpDefinitionOptionRel.getDDMFormFieldTypeName());

			String fieldTypeDataDomain = MapUtil.getString(
				properties, "ddm.form.field.type.data.domain");

			if (Validator.isNotNull(fieldTypeDataDomain)) {
				if (fieldTypeDataDomain.equals("list")) {
					jsonObject.put("hasValues", true);
				}
			}

			jsonArray.put(jsonObject);
		}

		HttpServletResponse response = _portal.getHttpServletResponse(
			resourceResponse);

		response.setContentType(ContentTypes.APPLICATION_JSON);

		ServletResponseUtil.write(response, jsonArray.toString());
	}

	@Reference
	private CPDefinitionOptionRelService _cpDefinitionOptionRelService;

	@Reference
	private DDMFormFieldTypeServicesTracker _ddmFormFieldTypeServicesTracker;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Portal _portal;

}