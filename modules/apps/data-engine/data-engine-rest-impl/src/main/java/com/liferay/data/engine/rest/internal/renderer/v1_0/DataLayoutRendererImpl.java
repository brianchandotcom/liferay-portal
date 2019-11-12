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

package com.liferay.data.engine.rest.internal.renderer.v1_0;

import com.liferay.data.engine.renderer.DataLayoutRenderer;
import com.liferay.data.engine.renderer.DataLayoutRendererContext;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.service.DDMStructureLayoutLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureVersionLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcela Cunha
 */
@Component(immediate = true, service = DataLayoutRenderer.class)
public class DataLayoutRendererImpl implements DataLayoutRenderer {

	@Override
	public String render(
			Long dataLayoutId,
			DataLayoutRendererContext dataLayoutRendererContext)
		throws Exception {

		DDMStructureLayout ddmStructureLayout =
			_ddmStructureLayoutLocalService.getStructureLayout(dataLayoutId);

		DDMStructureVersion ddmStructureVersion =
			_ddmStructureVersionLocalService.getDDMStructureVersion(
				ddmStructureLayout.getStructureVersionId());

		DDMStructure ddmStructure = ddmStructureVersion.getStructure();

		DDMFormDeserializerDeserializeRequest.Builder builder =
			DDMFormDeserializerDeserializeRequest.Builder.newBuilder(
				ddmStructure.getDefinition());

		DDMFormDeserializerDeserializeResponse
			ddmFormDeserializerDeserializeResponse =
				_ddmFormDeserializer.deserialize(builder.build());

		return _ddmFormRenderer.render(
			ddmFormDeserializerDeserializeResponse.getDDMForm(),
			ddmStructureLayout.getDDMFormLayout(),
			_toDDMFormRenderingContext(
				dataLayoutRendererContext,
				ddmFormDeserializerDeserializeResponse.getDDMForm()));
	}

	private DDMFormFieldValue _createDDMFormFieldValue(
		Map<String, Object> dataRecordValues, DDMFormField ddmFormField,
		Locale locale) {

		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		String name = ddmFormField.getName();

		ddmFormFieldValue.setName(name);

		Object value = dataRecordValues.get(name);

		if (value != null) {
			if (value instanceof Object[]) {
				JSONArray jsonArray = JSONUtil.putAll((Object[])value);

				value = jsonArray.toString();
			}

			if (ddmFormField.isLocalizable()) {
				LocalizedValue localizedValue = new LocalizedValue();

				localizedValue.addString(locale, String.valueOf(value));

				ddmFormFieldValue.setValue(localizedValue);
			}
			else {
				ddmFormFieldValue.setValue(
					new UnlocalizedValue(String.valueOf(value)));
			}
		}

		if (ListUtil.isNotEmpty(ddmFormField.getNestedDDMFormFields())) {
			for (DDMFormField nestedDDMFormField :
					ddmFormField.getNestedDDMFormFields()) {

				ddmFormFieldValue.addNestedDDMFormFieldValue(
					_createDDMFormFieldValue(
						dataRecordValues, nestedDDMFormField, locale));
			}
		}

		return ddmFormFieldValue;
	}

	private DDMFormRenderingContext _toDDMFormRenderingContext(
		DataLayoutRendererContext dataLayoutRendererContext, DDMForm ddmForm) {

		DDMFormRenderingContext ddmFormRenderingContext =
			new DDMFormRenderingContext();

		ddmFormRenderingContext.setContainerId(
			dataLayoutRendererContext.getContainerId());
		ddmFormRenderingContext.setDDMFormValues(
			_toDDMFormValues(
				dataLayoutRendererContext.getDataRecordValues(), ddmForm,
				_portal.getLocale(
					dataLayoutRendererContext.getHttpServletRequest())));
		ddmFormRenderingContext.setHttpServletRequest(
			dataLayoutRendererContext.getHttpServletRequest());
		ddmFormRenderingContext.setHttpServletResponse(
			dataLayoutRendererContext.getHttpServletResponse());
		ddmFormRenderingContext.setLocale(
			_portal.getLocale(
				dataLayoutRendererContext.getHttpServletRequest()));
		ddmFormRenderingContext.setPortletNamespace(
			dataLayoutRendererContext.getPortletNamespace());
		ddmFormRenderingContext.setShowSubmitButton(false);

		return ddmFormRenderingContext;
	}

	private DDMFormValues _toDDMFormValues(
		Map<String, Object> dataRecordValues, DDMForm ddmForm, Locale locale) {

		Map<String, DDMFormField> ddmFormFields = ddmForm.getDDMFormFieldsMap(
			true);

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		ddmFormValues.addAvailableLocale(locale);
		ddmFormValues.setDefaultLocale(locale);

		for (Map.Entry<String, DDMFormField> entry : ddmFormFields.entrySet()) {
			ddmFormValues.addDDMFormFieldValue(
				_createDDMFormFieldValue(
					dataRecordValues, entry.getValue(), locale));
		}

		return ddmFormValues;
	}

	@Reference(target = "(ddm.form.deserializer.type=json)")
	private DDMFormDeserializer _ddmFormDeserializer;

	@Reference
	private DDMFormRenderer _ddmFormRenderer;

	@Reference
	private DDMStructureLayoutLocalService _ddmStructureLayoutLocalService;

	@Reference
	private DDMStructureVersionLocalService _ddmStructureVersionLocalService;

	@Reference
	private Portal _portal;

}