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

package com.liferay.object.web.internal.object.entries.display.context;

import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.constants.FieldConstants;
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectEntryService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.web.internal.constants.ObjectWebKeys;
import com.liferay.object.web.internal.display.context.util.ObjectRequestHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.vulcan.util.TransformUtil;
import com.liferay.taglib.servlet.PipingServletResponseFactory;

import java.io.Serializable;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Marco Leo
 */
public class ObjectEntriesDetailsDisplayContext {

	public ObjectEntriesDetailsDisplayContext(
		DDMFormRenderer ddmFormRenderer, HttpServletRequest httpServletRequest,
		ListTypeEntryLocalService listTypeEntryLocalService,
		ObjectEntryService objectEntryService,
		ObjectFieldLocalService objectFieldLocalService) {

		_ddmFormRenderer = ddmFormRenderer;
		_listTypeEntryLocalService = listTypeEntryLocalService;
		_objectEntryService = objectEntryService;
		_objectFieldLocalService = objectFieldLocalService;

		_objectRequestHelper = new ObjectRequestHelper(httpServletRequest);
	}

	public ObjectDefinition getObjectDefinition() {
		HttpServletRequest httpServletRequest =
			_objectRequestHelper.getRequest();

		return (ObjectDefinition)httpServletRequest.getAttribute(
			ObjectWebKeys.OBJECT_DEFINITION);
	}

	public ObjectEntry getObjectEntry() throws PortalException {
		if (_objectEntry != null) {
			return _objectEntry;
		}

		long objectEntryId = ParamUtil.getLong(
			_objectRequestHelper.getRequest(), "objectEntryId");

		_objectEntry = _objectEntryService.fetchObjectEntry(objectEntryId);

		return _objectEntry;
	}

	public String renderDDMForm(PageContext pageContext) throws Exception {
		DDMForm ddmForm = _getDDMForm();

		DDMFormRenderingContext ddmFormRenderingContext =
			new DDMFormRenderingContext();

		ddmFormRenderingContext.setContainerId("editObjectEntry");

		ObjectEntry objectEntry = getObjectEntry();

		if (objectEntry != null) {
			DDMFormValues ddmFormValues = _getDDMFormValues(
				ddmForm, objectEntry);

			if (ddmFormValues != null) {
				ddmFormRenderingContext.setDDMFormValues(ddmFormValues);
			}
		}

		ddmFormRenderingContext.setHttpServletRequest(
			_objectRequestHelper.getRequest());
		ddmFormRenderingContext.setHttpServletResponse(
			PipingServletResponseFactory.createPipingServletResponse(
				pageContext));
		ddmFormRenderingContext.setLocale(_objectRequestHelper.getLocale());

		LiferayPortletResponse liferayPortletResponse =
			_objectRequestHelper.getLiferayPortletResponse();

		ddmFormRenderingContext.setPortletNamespace(
			liferayPortletResponse.getNamespace());

		ddmFormRenderingContext.setShowRequiredFieldsWarning(true);

		return _ddmFormRenderer.render(ddmForm, ddmFormRenderingContext);
	}

	private DDMFormFieldOptions _getDDMFieldOptions(long listTypeDefinitionId) {
		List<ListTypeEntry> listTypeEntries =
			_listTypeEntryLocalService.getListTypeEntries(listTypeDefinitionId);

		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		for (ListTypeEntry listTypeEntry : listTypeEntries) {
			ddmFormFieldOptions.addOptionLabel(
				listTypeEntry.getKey(), _objectRequestHelper.getLocale(),
				GetterUtil.getString(
					listTypeEntry.getName(_objectRequestHelper.getLocale()),
					listTypeEntry.getName(
						listTypeEntry.getDefaultLanguageId())));
		}

		return ddmFormFieldOptions;
	}

	private DDMForm _getDDMForm() {
		ObjectDefinition objectDefinition = getObjectDefinition();

		DDMForm ddmForm = new DDMForm();

		ddmForm.addAvailableLocale(_objectRequestHelper.getLocale());

		List<ObjectField> objectFields =
			_objectFieldLocalService.getObjectFields(
				objectDefinition.getObjectDefinitionId());

		for (ObjectField objectField : objectFields) {
			ddmForm.addDDMFormField(_getDDMFormField(objectField));
		}

		ddmForm.setDefaultLocale(_objectRequestHelper.getLocale());

		return ddmForm;
	}

	private DDMFormField _getDDMFormField(ObjectField objectField) {
		DDMFormField ddmFormField = new DDMFormField(
			objectField.getName(), DDMFormFieldTypeConstants.TEXT);

		_setDDMFormFieldProperties(
			ddmFormField,
			GetterUtil.getLong(objectField.getListTypeDefinitionId()),
			objectField.getType());

		LocalizedValue ddmFormFieldLabelLocalizedValue = new LocalizedValue(
			_objectRequestHelper.getLocale());

		ddmFormFieldLabelLocalizedValue.addString(
			_objectRequestHelper.getLocale(),
			objectField.getLabel(_objectRequestHelper.getLocale()));

		ddmFormField.setLabel(ddmFormFieldLabelLocalizedValue);

		ddmFormField.setRequired(objectField.isRequired());

		return ddmFormField;
	}

	private DDMFormValues _getDDMFormValues(
		DDMForm ddmForm, ObjectEntry objectEntry) {

		Map<String, Serializable> values = objectEntry.getValues();

		if (values.isEmpty()) {
			return null;
		}

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		ddmFormValues.addAvailableLocale(_objectRequestHelper.getLocale());
		ddmFormValues.setDDMFormFieldValues(
			TransformUtil.transform(
				values.entrySet(),
				entry -> {
					DDMFormFieldValue ddmFormFieldValue =
						new DDMFormFieldValue();

					ddmFormFieldValue.setName(entry.getKey());

					Serializable serializable = entry.getValue();

					if (serializable == null) {
						ddmFormFieldValue.setValue(
							new UnlocalizedValue(GetterUtil.DEFAULT_STRING));
					}
					else {
						ddmFormFieldValue.setValue(
							new UnlocalizedValue(String.valueOf(serializable)));
					}

					return ddmFormFieldValue;
				}));
		ddmFormValues.setDefaultLocale(_objectRequestHelper.getLocale());

		return ddmFormValues;
	}

	private void _setDDMFormFieldProperties(
		DDMFormField ddmFormField, long listTypeDefinitionId, String type) {

		if (_doubleTypes.contains(type)) {
			ddmFormField.setType(DDMFormFieldTypeConstants.NUMERIC);
			ddmFormField.setProperty(
				FieldConstants.DATA_TYPE, FieldConstants.DOUBLE);
		}
		else if (_integerTypes.contains(type)) {
			ddmFormField.setType(DDMFormFieldTypeConstants.NUMERIC);
			ddmFormField.setProperty(
				FieldConstants.DATA_TYPE, FieldConstants.INTEGER);
		}
		else if (StringUtil.equals(type, "Boolean")) {
			ddmFormField.setType(DDMFormFieldTypeConstants.CHECKBOX);
		}
		else if (StringUtil.equals(type, "Date")) {
			ddmFormField.setType(DDMFormFieldTypeConstants.DATE);
		}
		else if (StringUtil.equals(type, "String") &&
				 (listTypeDefinitionId > 0)) {

			ddmFormField.setType(DDMFormFieldTypeConstants.SELECT);
			ddmFormField.setDDMFormFieldOptions(
				_getDDMFieldOptions(listTypeDefinitionId));
		}
	}

	private static final List<String> _doubleTypes = Arrays.asList(
		"BigDecimal", "Double");
	private static final List<String> _integerTypes = Arrays.asList(
		"Integer", "Long");

	private final DDMFormRenderer _ddmFormRenderer;
	private final ListTypeEntryLocalService _listTypeEntryLocalService;
	private ObjectEntry _objectEntry;
	private final ObjectEntryService _objectEntryService;
	private final ObjectFieldLocalService _objectFieldLocalService;
	private final ObjectRequestHelper _objectRequestHelper;

}