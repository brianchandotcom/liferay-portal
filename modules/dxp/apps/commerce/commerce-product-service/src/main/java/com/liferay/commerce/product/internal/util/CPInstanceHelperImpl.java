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

package com.liferay.commerce.product.internal.util;

import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPDefinitionOptionValueRelService;
import com.liferay.commerce.product.service.CPInstanceService;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.commerce.product.service.CPDefinitionOptionRelService;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liferay.portal.kernel.util.Validator;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(immediate = true)
public class CPInstanceHelperImpl
	implements CPInstanceHelper {

	public final String DDM_CONTAINER_ID = "cpDefinitionOptionsRender";

	@Override
	public String render(
			long cpDefinitionId,RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws PortalException {

		HttpServletRequest httpServletRequest =
			_portal.getHttpServletRequest(renderRequest);

		HttpServletResponse httpServletResponse =
			_portal.getHttpServletResponse(renderResponse);

		Locale locale = _portal.getLocale(httpServletRequest);

		DDMForm ddmForm = getDDMForm(cpDefinitionId,locale);

		DDMFormRenderingContext ddmFormRenderingContext =
			new DDMFormRenderingContext();


		ddmFormRenderingContext.setContainerId(DDM_CONTAINER_ID);
		ddmFormRenderingContext.setHttpServletRequest(httpServletRequest);
		ddmFormRenderingContext.setHttpServletResponse(httpServletResponse);
		ddmFormRenderingContext.setLocale(locale);
		ddmFormRenderingContext.setPortletNamespace(
			renderResponse.getNamespace());

		return _ddmFormRenderer.render(ddmForm, ddmFormRenderingContext);
	}

	@Override
	public DDMFormValues getDDMFormValues(
			long cpDefinitionId, Locale locale,
			String serializedDDMFormValues) {

		if (Validator.isNotNull(serializedDDMFormValues)) {
			try {
				DDMForm ddmForm = getDDMForm(cpDefinitionId,locale);

				return DDMUtil.getDDMFormValues(
					ddmForm, serializedDDMFormValues);
			}
			catch (PortalException pe) {
				if (_log.isDebugEnabled()) {
					_log.debug(pe, pe);
				}
			}
		}

		return null;
	}

	@Override
	public String getDDMContent(
		long cpDefinitionId, Locale locale,
		String serializedDDMFormValues) {

		DDMFormValues ddmFormValues = getDDMFormValues(
			cpDefinitionId,locale,serializedDDMFormValues);

		Map<String,List<DDMFormFieldValue>> ddmFormFieldValueMap =
			ddmFormValues.getDDMFormFieldValuesMap();

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (Map.Entry<String,List<DDMFormFieldValue>> entry :
				ddmFormFieldValueMap.entrySet())
		{
			for(DDMFormFieldValue ddmFormFieldValue: entry.getValue()){

				JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

				jsonObject.put("cpDefinitionOptionRelId", entry.getKey());

				Value value = ddmFormFieldValue.getValue();

				jsonObject.put(
					"cpDefinitionOptionValueRelId",
					value.getString(locale));

				jsonArray.put(jsonObject);
			}
		}

		return jsonArray.toString();
	}

	@Override
	public DDMForm getDDMForm(
			long cpDefinitionId, Locale locale)
		throws PortalException {

		DDMForm ddmForm = new DDMForm();

		List<CPDefinitionOptionRel> cpDefinitionOptionRels =
			_cpDefinitionOptionRelService.
				getSkuContributorCPDefinitionOptionRels(cpDefinitionId);

		for (CPDefinitionOptionRel cpDefinitionOptionRel :
			cpDefinitionOptionRels) {

			List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels =
				cpDefinitionOptionRel.getCPDefinitionOptionValueRels();

			DDMFormField ddmFormField = new DDMFormField(
				String.valueOf(cpDefinitionOptionRel.
					getCPDefinitionOptionRelId()),
				cpDefinitionOptionRel.getDDMFormFieldTypeName());

			if (!cpDefinitionOptionValueRels.isEmpty()) {
				DDMFormFieldOptions ddmFormFieldOptions =
					new DDMFormFieldOptions();

				for (CPDefinitionOptionValueRel cpDefinitionOptionValueRel :
					cpDefinitionOptionValueRels) {

					ddmFormFieldOptions.addOptionLabel(
						String.valueOf(cpDefinitionOptionValueRel.
							getCPDefinitionOptionValueRelId()),
						locale, cpDefinitionOptionValueRel.getTitle(locale));
				}

				ddmFormField.setDDMFormFieldOptions(ddmFormFieldOptions);
			}

			LocalizedValue localizedValue = new LocalizedValue(locale);

			localizedValue.addString(
				locale, cpDefinitionOptionRel.getTitle(locale));

			ddmFormField.setLabel(localizedValue);

			ddmFormField.setRequired(true);

			ddmForm.addDDMFormField(ddmFormField);
		}

		return ddmForm;
	}

	@Override
	public Map<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
			parseCPInstanceDDMContent(long cpInstanceId)
		throws PortalException {

		Map<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
			cpDefinitionOptionRelListMap = new HashMap<>();

		CPInstance cpInstance = _cpInstanceService.getCPInstance(cpInstanceId);

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray(
			cpInstance.getDDMContent());

		for(int i = 0; i < jsonArray.length(); i++)
		{
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			long cpDefinitionOptionRelId = jsonObject.getLong(
				"cpDefinitionOptionRelId");

			long cpDefinitionOptionValueRelId = jsonObject.getLong(
				"cpDefinitionOptionValueRelId");

			CPDefinitionOptionRel cpDefinitionOptionRel =
				_cpDefinitionOptionRelService.getCPDefinitionOptionRel(
					cpDefinitionOptionRelId);

			CPDefinitionOptionValueRel cpDefinitionOptionValueRel =
				_cpDefinitionOptionValueRelService.
					getCPDefinitionOptionValueRel(cpDefinitionOptionValueRelId);

			List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels =
				cpDefinitionOptionRelListMap.get(cpDefinitionOptionRel);

			if(cpDefinitionOptionValueRels == null){
				cpDefinitionOptionValueRels = new ArrayList<>();
				cpDefinitionOptionRelListMap.put(
					cpDefinitionOptionRel, cpDefinitionOptionValueRels);
			}

			cpDefinitionOptionValueRels.add(cpDefinitionOptionValueRel);

		}

		return cpDefinitionOptionRelListMap;
	}

	@Reference
	private CPDefinitionOptionRelService _cpDefinitionOptionRelService;

	@Reference
	private CPDefinitionOptionValueRelService
		_cpDefinitionOptionValueRelService;

	@Reference
	private CPInstanceService _cpInstanceService;

	@Reference
	private DDMFormRenderer _ddmFormRenderer;


	@Reference
	private Portal _portal;

	private static final Log _log = LogFactoryUtil.getLog(
		CPInstanceHelperImpl.class);

}