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
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluator;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluatorEvaluateRequest;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluatorEvaluateResponse;
import com.liferay.dynamic.data.mapping.form.evaluator.DDMFormEvaluatorFieldContextKey;
import com.liferay.dynamic.data.mapping.form.web.internal.display.context.util.DDMFormInstanceExpirationStatusUtil;
import com.liferay.dynamic.data.mapping.form.web.internal.display.context.util.DDMFormInstanceSubmissionLimitStatusUtil;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordVersionLocalService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true, service = AddFormInstanceRecordMVCCommandHelper.class
)
public class AddFormInstanceRecordMVCCommandHelper {

	public void updateRequiredFieldsAccordingToVisibility(
			ActionRequest actionRequest, DDMForm ddmForm,
			DDMFormValues ddmFormValues, Locale locale)
		throws Exception {

		DDMFormEvaluatorEvaluateResponse ddmFormEvaluatorEvaluateResponse =
			evaluate(actionRequest, ddmForm, ddmFormValues, locale);

		Set<String> invisibleFields = _getInvisibleFields(
			ddmFormEvaluatorEvaluateResponse);

		Set<String> fieldsFromDisabledPages = _getFieldNamesFromDisabledPages(
			ddmFormEvaluatorEvaluateResponse, getDDMFormLayout(actionRequest));

		invisibleFields.addAll(fieldsFromDisabledPages);

		_removeValue(
			ddmFormValues.getDDMFormFieldValuesMap(true), invisibleFields);

		_removeDDMValidationExpression(
			ddmForm.getDDMFormFields(), invisibleFields);

		List<DDMFormField> requiredFields = _getRequiredFields(ddmForm);

		if (requiredFields.isEmpty() || invisibleFields.isEmpty()) {
			return;
		}

		_removeRequiredProperty(invisibleFields, requiredFields);
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

	protected DDMFormEvaluatorEvaluateResponse evaluate(
			ActionRequest actionRequest, DDMForm ddmForm,
			DDMFormValues ddmFormValues, Locale locale)
		throws Exception {

		DDMFormEvaluatorEvaluateRequest.Builder builder =
			DDMFormEvaluatorEvaluateRequest.Builder.newBuilder(
				ddmForm, ddmFormValues, locale);

		return _ddmFormEvaluator.evaluate(
			builder.withCompanyId(
				_portal.getCompanyId(actionRequest)
			).withDDMFormInstanceId(
				ParamUtil.getLong(actionRequest, "formInstanceId")
			).withGroupId(
				ParamUtil.getLong(actionRequest, "groupId")
			).withTimeZoneId(
				_getTimeZoneId(actionRequest)
			).withUserId(
				_portal.getUserId(actionRequest)
			).build());
	}

	protected DDMFormLayout getDDMFormLayout(ActionRequest actionRequest)
		throws PortalException {

		long formInstanceId = ParamUtil.getLong(
			actionRequest, "formInstanceId");

		DDMFormInstance formInstance = _ddmFormInstanceService.getFormInstance(
			formInstanceId);

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			formInstance.getStructureId());

		return ddmStructure.getDDMFormLayout();
	}

	private Set<String> _getFieldNamesFromDisabledPages(
		DDMFormEvaluatorEvaluateResponse ddmFormEvaluatorEvaluateResponse,
		DDMFormLayout ddmFormLayout) {

		Set<Integer> disabledPagesIndexes =
			ddmFormEvaluatorEvaluateResponse.getDisabledPagesIndexes();

		Stream<Integer> disabledPagesIndexesStream =
			disabledPagesIndexes.stream();

		return disabledPagesIndexesStream.map(
			index -> _getFieldNamesFromPage(index, ddmFormLayout)
		).flatMap(
			field -> field.stream()
		).collect(
			Collectors.toSet()
		);
	}

	private Set<String> _getFieldNamesFromPage(
		int index, DDMFormLayout ddmFormLayout) {

		DDMFormLayoutPage ddmFormLayoutPage =
			ddmFormLayout.getDDMFormLayoutPage(index);

		List<DDMFormLayoutRow> ddmFormLayoutRows =
			ddmFormLayoutPage.getDDMFormLayoutRows();

		Set<String> fieldNames = new HashSet<>();

		for (DDMFormLayoutRow ddmFormLayoutRow : ddmFormLayoutRows) {
			for (DDMFormLayoutColumn ddmFormLayoutColumn :
					ddmFormLayoutRow.getDDMFormLayoutColumns()) {

				fieldNames.addAll(ddmFormLayoutColumn.getDDMFormFieldNames());
			}
		}

		return fieldNames;
	}

	private Set<String> _getInvisibleFields(
		DDMFormEvaluatorEvaluateResponse ddmFormEvaluatorEvaluateResponse) {

		Map<DDMFormEvaluatorFieldContextKey, Map<String, Object>>
			ddmFormFieldsPropertyChanges =
				ddmFormEvaluatorEvaluateResponse.
					getDDMFormFieldsPropertyChanges();

		Set<Map.Entry<DDMFormEvaluatorFieldContextKey, Map<String, Object>>>
			entrySet = ddmFormFieldsPropertyChanges.entrySet();

		Stream<Map.Entry<DDMFormEvaluatorFieldContextKey, Map<String, Object>>>
			stream = entrySet.stream();

		return stream.filter(
			result -> !MapUtil.getBoolean(result.getValue(), "visible", true)
		).map(
			result -> {
				DDMFormEvaluatorFieldContextKey ddmFormFieldContextKey =
					result.getKey();

				return ddmFormFieldContextKey.getName();
			}
		).collect(
			Collectors.toSet()
		);
	}

	private List<DDMFormField> _getRequiredFields(DDMForm ddmForm) {
		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(true);

		Collection<DDMFormField> ddmFormFields = ddmFormFieldsMap.values();

		Stream<DDMFormField> stream = ddmFormFields.stream();

		return stream.filter(
			ddmFormField -> ddmFormField.isRequired()
		).collect(
			Collectors.toList()
		);
	}

	private String _getTimeZoneId(ActionRequest actionRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (themeDisplay == null) {
			return StringPool.BLANK;
		}

		User user = themeDisplay.getUser();

		return user.getTimeZoneId();
	}

	private void _removeDDMValidationExpression(DDMFormField ddmFormField) {
		ddmFormField.setDDMFormFieldValidation(null);
	}

	private void _removeDDMValidationExpression(
		List<DDMFormField> ddmFormFields, Set<String> invisibleFields) {

		Stream<DDMFormField> stream = ddmFormFields.stream();

		stream.filter(
			ddmFormField -> invisibleFields.contains(ddmFormField.getName())
		).forEach(
			this::_removeDDMValidationExpression
		);
	}

	private void _removeRequiredProperty(DDMFormField ddmFormField) {
		ddmFormField.setRequired(false);
	}

	private void _removeRequiredProperty(
		Set<String> invisibleFields, List<DDMFormField> requiredFields) {

		Stream<DDMFormField> stream = requiredFields.stream();

		stream.filter(
			field -> invisibleFields.contains(field.getName())
		).forEach(
			this::_removeRequiredProperty
		);
	}

	private void _removeValue(DDMFormFieldValue ddmFormFieldValue) {
		DDMFormField ddmFormField = ddmFormFieldValue.getDDMFormField();

		if (ddmFormField.isLocalizable()) {
			Value value = ddmFormFieldValue.getValue();

			LocalizedValue localizedValue = new LocalizedValue(
				value.getDefaultLocale());

			for (Locale availableLocale : value.getAvailableLocales()) {
				localizedValue.addString(availableLocale, StringPool.BLANK);
			}

			ddmFormFieldValue.setValue(localizedValue);
		}
		else {
			ddmFormFieldValue.setValue(new UnlocalizedValue(StringPool.BLANK));
		}
	}

	private void _removeValue(
		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap,
		Set<String> invisibleFields) {

		Stream<String> stream = invisibleFields.stream();

		stream.map(
			ddmFormFieldValuesMap::get
		).flatMap(
			List::stream
		).filter(
			ddmFormFieldValue -> ddmFormFieldValue.getValue() != null
		).forEach(
			this::_removeValue
		);
	}

	@Reference
	private DDMFormEvaluator _ddmFormEvaluator;

	@Reference
	private DDMFormInstanceService _ddmFormInstanceService;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private Portal _portal;

}