/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.form.internal.dto.v1_0.util;

import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormTemplateContextFactory;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.headless.form.dto.v1_0.FormContext;
import com.liferay.headless.form.dto.v1_0.FormFieldContext;
import com.liferay.headless.form.dto.v1_0.FormFieldValue;
import com.liferay.headless.form.dto.v1_0.FormPageContext;
import com.liferay.petra.function.transform.TransformUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Victor Oliveira
 */
public class FormContextUtil {

	public static FormContext evaluateContext(
			DDMFormInstance ddmFormInstance,
			DDMFormRenderingContext ddmFormRenderingContext,
			DDMFormTemplateContextFactory ddmFormTemplateContextFactory,
			FormFieldValue[] formFieldValues, Locale locale)
		throws Exception {

		ddmFormRenderingContext.setDDMFormValues(
			DDMFormValuesUtil.createDDMFormValues(
				ddmFormInstance, formFieldValues, locale));
		ddmFormRenderingContext.setLocale(locale);

		DDMStructure ddmStructure = ddmFormInstance.getStructure();

		Map<String, Object> ddmFormTemplateContext =
			ddmFormTemplateContextFactory.create(
				ddmStructure.getDDMForm(), ddmStructure.getDDMFormLayout(),
				ddmFormRenderingContext);

		return new FormContext() {
			{
				setFormPageContexts(
					() -> TransformUtil.transformToArray(
						_getMaps(ddmFormTemplateContext, "pages"),
						FormContextUtil::_toFormPageContext,
						FormPageContext.class));
				setReadOnly(
					() -> _getBoolean(ddmFormTemplateContext, "readOnly"));
				setShowRequiredFieldsWarning(
					() -> _getBoolean(
						ddmFormTemplateContext, "showRequiredFieldsWarning"));
				setShowSubmitButton(
					() -> _getBoolean(
						ddmFormTemplateContext, "showSubmitButton"));
			}
		};
	}

	private static Boolean _getBoolean(Map<String, Object> map, String key) {
		return (Boolean)map.getOrDefault(key, false);
	}

	private static List<Map<String, Object>> _getMaps(
		Map<String, Object> map, String key) {

		return (List<Map<String, Object>>)map.getOrDefault(
			key, new ArrayList<>());
	}

	private static String _getString(Map<String, Object> map, String key) {
		return String.valueOf(map.getOrDefault(key, ""));
	}

	private static FormFieldContext _toFormFieldContext(
		Map<String, Object> fieldContext) {

		return new FormFieldContext() {
			{
				setEvaluable(() -> _getBoolean(fieldContext, "evaluable"));
				setName(() -> _getString(fieldContext, "fieldName"));
				setReadOnly(() -> _getBoolean(fieldContext, "readOnly"));
				setRequired(() -> _getBoolean(fieldContext, "required"));
				setValid(() -> _getBoolean(fieldContext, "valid"));
				setValue(() -> _getString(fieldContext, "value"));
				setValueChanged(
					() -> _getBoolean(fieldContext, "valueChanged"));
				setVisible(() -> _getBoolean(fieldContext, "visible"));
			}
		};
	}

	private static FormPageContext _toFormPageContext(
		Map<String, Object> formPageContext) {

		return new FormPageContext() {
			{
				setEnabled(() -> _getBoolean(formPageContext, "enabled"));
				setFormFieldContexts(
					() -> {
						List<FormFieldContext> formFieldContextsList =
							new ArrayList<>();

						for (Map<String, Object> rowsMap :
								_getMaps(formPageContext, "rows")) {

							for (Map<String, Object> columnsMap :
									_getMaps(rowsMap, "columns")) {

								for (Map<String, Object> fieldsMap :
										_getMaps(columnsMap, "fields")) {

									formFieldContextsList.add(
										_toFormFieldContext(fieldsMap));
								}
							}
						}

						return formFieldContextsList.toArray(
							new FormFieldContext[0]);
					});
				setShowRequiredFieldsWarning(
					() -> _getBoolean(
						formPageContext, "showRequiredFieldsWarning"));
			}
		};
	}

}