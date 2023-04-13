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

package com.liferay.dynamic.data.mapping.form.web.internal.portlet.action.helper;

import com.liferay.dynamic.data.mapping.exception.FormInstanceExpiredException;
import com.liferay.dynamic.data.mapping.exception.FormInstanceSubmissionLimitException;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluatorFieldContextKey;
import com.liferay.dynamic.data.mapping.form.web.internal.display.context.util.DDMFormInstanceExpirationStatusUtil;
import com.liferay.dynamic.data.mapping.form.web.internal.display.context.util.DDMFormInstanceSubmissionLimitStatusUtil;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordVersionLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Leonardo Barros
 */
@Component(service = AddFormInstanceRecordMVCCommandHelper.class)
public class AddFormInstanceRecordMVCCommandHelper {

	public void updateNonevaluableDDMFormFields(
			Map<String, DDMFormField> ddmFormFieldsMap,
			Map<DDMFormEvaluatorFieldContextKey, Map<String, Object>>
				ddmFormFieldsPropertyChanges,
			Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap,
			DDMFormLayout ddmFormLayout, Set<Integer> disabledPagesIndexes)
		throws Exception {

		Set<String> nonevaluableFieldNames = new HashSet<>();

		for (Map.Entry<DDMFormEvaluatorFieldContextKey, Map<String, Object>>
				entry : ddmFormFieldsPropertyChanges.entrySet()) {

			if (!MapUtil.getBoolean(entry.getValue(), "readOnly") &&
				MapUtil.getBoolean(entry.getValue(), "visible", true)) {

				continue;
			}

			DDMFormEvaluatorFieldContextKey ddmFormEvaluatorFieldContextKey =
				entry.getKey();

			nonevaluableFieldNames.add(
				ddmFormEvaluatorFieldContextKey.getName());
		}

		for (Integer disabledPagesIndex : disabledPagesIndexes) {
			DDMFormLayoutPage ddmFormLayoutPage =
				ddmFormLayout.getDDMFormLayoutPage(disabledPagesIndex);

			for (DDMFormLayoutRow ddmFormLayoutRow :
					ddmFormLayoutPage.getDDMFormLayoutRows()) {

				for (DDMFormLayoutColumn ddmFormLayoutColumn :
						ddmFormLayoutRow.getDDMFormLayoutColumns()) {

					nonevaluableFieldNames.addAll(
						ddmFormLayoutColumn.getDDMFormFieldNames());
				}
			}
		}

		for (String nonevaluableFieldName : nonevaluableFieldNames) {
			DDMFormField ddmFormField = ddmFormFieldsMap.get(
				nonevaluableFieldName);

			if (ddmFormField == null) {
				continue;
			}

			ddmFormField.setDDMFormFieldValidation(null);
			ddmFormField.setRequired(false);

			for (DDMFormFieldValue ddmFormFieldValue :
					ddmFormFieldValuesMap.get(ddmFormField.getName())) {

				Value value = ddmFormFieldValue.getValue();

				if (value == null) {
					continue;
				}

				if (ddmFormField.isLocalizable()) {
					LocalizedValue localizedValue = new LocalizedValue(
						value.getDefaultLocale());

					for (Locale availableLocale : value.getAvailableLocales()) {
						localizedValue.addString(
							availableLocale, StringPool.BLANK);
					}

					ddmFormFieldValue.setValue(localizedValue);
				}
				else {
					ddmFormFieldValue.setValue(
						new UnlocalizedValue(StringPool.BLANK));
				}
			}
		}
	}

	public void validateExpirationStatus(
			DDMFormInstance ddmFormInstance, PortletRequest portletRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (DDMFormInstanceExpirationStatusUtil.isFormExpired(
				ddmFormInstance, themeDisplay.getTimeZone())) {

			throw new FormInstanceExpiredException(
				"Form instance " + ddmFormInstance.getFormInstanceId() +
					" is expired");
		}
	}

	public void validateSubmissionLimitStatus(
			DDMFormInstance ddmFormInstance,
			DDMFormInstanceRecordVersionLocalService
				ddmFormInstanceRecordVersionLocalService,
			PortletRequest portletRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (DDMFormInstanceSubmissionLimitStatusUtil.isSubmissionLimitReached(
				ddmFormInstance, ddmFormInstanceRecordVersionLocalService,
				themeDisplay.getUser())) {

			throw new FormInstanceSubmissionLimitException(
				StringBundler.concat(
					"User ", themeDisplay.getUserId(),
					" has already submitted an entry in form instance ",
					ddmFormInstance.getFormInstanceId()));
		}
	}

}