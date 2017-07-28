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
import com.liferay.commerce.product.service.CPDefinitionOptionRelService;
import com.liferay.commerce.product.service.CPDefinitionOptionValueRelService;
import com.liferay.commerce.product.service.CPInstanceService;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.commerce.product.util.DDMFormValuesHelper;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(immediate = true)
public class CPInstanceHelperImpl implements CPInstanceHelper {

	@Override
	public DDMForm getDDMForm(
			long cpDefinitionId, Locale locale, boolean required)
		throws PortalException {

		DDMForm ddmForm = new DDMForm();

		List<CPDefinitionOptionRel> cpDefinitionOptionRels =
			_cpDefinitionOptionRelService.
				getSkuContributorCPDefinitionOptionRels(cpDefinitionId);

		for (CPDefinitionOptionRel cpDefinitionOptionRel :
				cpDefinitionOptionRels) {

			List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels =
				cpDefinitionOptionRel.getCPDefinitionOptionValueRels();

			if (Validator.isNull(
					cpDefinitionOptionRel.getDDMFormFieldTypeName())) {

				continue;
			}

			DDMFormField ddmFormField = new DDMFormField(
				String.valueOf(cpDefinitionOptionRel.
					getCPDefinitionOptionRelId()),
				cpDefinitionOptionRel.getDDMFormFieldTypeName());

			if (!cpDefinitionOptionValueRels.isEmpty()) {
				DDMFormFieldOptions ddmFormFieldOptions =
					new DDMFormFieldOptions();

				ddmFormFieldOptions.addOptionLabel(
					"_cpDefinitionOptionValueRelId_", locale, StringPool.BLANK);

				for (CPDefinitionOptionValueRel cpDefinitionOptionValueRel :
						cpDefinitionOptionValueRels) {

					ddmFormFieldOptions.addOptionLabel(
						"_cpDefinitionOptionValueRelId_" +
							cpDefinitionOptionValueRel.
								getCPDefinitionOptionValueRelId(),
						locale, cpDefinitionOptionValueRel.getTitle(locale));
				}

				ddmFormField.setDDMFormFieldOptions(ddmFormFieldOptions);
			}

			LocalizedValue localizedValue = new LocalizedValue(locale);

			localizedValue.addString(
				locale, cpDefinitionOptionRel.getTitle(locale));

			ddmFormField.setLabel(localizedValue);

			ddmFormField.setRequired(required);

			ddmForm.addDDMFormField(ddmFormField);
		}

		return ddmForm;
	}

	@Override
	public DDMFormValues getDDMFormValues(
		long cpDefinitionId, Locale locale, String serializedDDMFormValues) {

		if (Validator.isNotNull(serializedDDMFormValues)) {
			try {
				DDMForm ddmForm = getDDMForm(cpDefinitionId, locale, true);

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
	public Map<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
			parseJSONString(String json)
		throws PortalException {

		Map<CPDefinitionOptionRel, List<CPDefinitionOptionValueRel>>
			cpDefinitionOptionRelListMap = new HashMap<>();

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray(json);

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			long cpDefinitionOptionRelId = jsonObject.getLong(
				"cpDefinitionOptionRelId");
			long cpDefinitionOptionValueRelId = jsonObject.getLong(
				"cpDefinitionOptionValueRelId");

			CPDefinitionOptionRel cpDefinitionOptionRel =
				_cpDefinitionOptionRelService.fetchCPDefinitionOptionRel(
					cpDefinitionOptionRelId);

			if (cpDefinitionOptionRel == null) {
				continue;
			}

			CPDefinitionOptionValueRel cpDefinitionOptionValueRel =
				_cpDefinitionOptionValueRelService.
					fetchCPDefinitionOptionValueRel(
						cpDefinitionOptionValueRelId);

			if (cpDefinitionOptionValueRel == null) {
				continue;
			}

			List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels =
				cpDefinitionOptionRelListMap.get(cpDefinitionOptionRel);

			if (cpDefinitionOptionValueRels == null) {
				cpDefinitionOptionValueRels = new ArrayList<>();

				cpDefinitionOptionRelListMap.put(
					cpDefinitionOptionRel, cpDefinitionOptionValueRels);
			}

			cpDefinitionOptionValueRels.add(cpDefinitionOptionValueRel);
		}

		return cpDefinitionOptionRelListMap;
	}

	@Override
	public String render(
			long cpDefinitionId, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws PortalException {

		return render(
			cpDefinitionId, null, true, renderRequest, renderResponse);
	}

	@Override
	public String render(
			long cpDefinitionId, String json, boolean required,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortalException {

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			renderRequest);

		HttpServletResponse httpServletResponse =
			_portal.getHttpServletResponse(renderResponse);

		Locale locale = _portal.getLocale(httpServletRequest);

		DDMForm ddmForm = getDDMForm(cpDefinitionId, locale, required);

		DDMFormRenderingContext ddmFormRenderingContext =
			new DDMFormRenderingContext();

		ddmFormRenderingContext.setContainerId(String.valueOf(cpDefinitionId));
		ddmFormRenderingContext.setHttpServletRequest(httpServletRequest);
		ddmFormRenderingContext.setHttpServletResponse(httpServletResponse);
		ddmFormRenderingContext.setLocale(locale);
		ddmFormRenderingContext.setPortletNamespace(
			renderResponse.getNamespace());

		if (Validator.isNotNull(json)) {
			DDMFormValues ddmFormValues = _ddmFormValuesHelper.deserialize(
				ddmForm, json, locale);

			if (ddmFormValues != null) {
				ddmFormRenderingContext.setDDMFormValues(ddmFormValues);
			}
		}

		return _ddmFormRenderer.render(ddmForm, ddmFormRenderingContext);
	}

	@Override
	public String toJSON(
		long cpDefinitionId, Locale locale, String serializedDDMFormValues) {

		DDMFormValues ddmFormValues = getDDMFormValues(
			cpDefinitionId, locale, serializedDDMFormValues);

		return _ddmFormValuesHelper.serialize(ddmFormValues);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CPInstanceHelperImpl.class);

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
	private DDMFormValuesHelper _ddmFormValuesHelper;

	@Reference
	private Portal _portal;

}