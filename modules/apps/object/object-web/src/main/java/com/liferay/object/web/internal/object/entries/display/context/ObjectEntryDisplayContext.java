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
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.constants.FieldConstants;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemList;
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.exception.NoSuchObjectLayoutException;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectLayout;
import com.liferay.object.model.ObjectLayoutBox;
import com.liferay.object.model.ObjectLayoutColumn;
import com.liferay.object.model.ObjectLayoutRow;
import com.liferay.object.model.ObjectLayoutTab;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectEntryService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectLayoutLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.web.internal.constants.ObjectWebKeys;
import com.liferay.object.web.internal.display.context.util.ObjectRequestHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.util.TransformUtil;
import com.liferay.taglib.servlet.PipingServletResponseFactory;

import java.io.Serializable;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Marco Leo
 */
public class ObjectEntryDisplayContext {

	public ObjectEntryDisplayContext(
		DDMFormRenderer ddmFormRenderer, HttpServletRequest httpServletRequest,
		ListTypeEntryLocalService listTypeEntryLocalService,
		ObjectEntryService objectEntryService,
		ObjectFieldLocalService objectFieldLocalService,
		ObjectLayoutLocalService objectLayoutLocalService,
		ObjectRelationshipLocalService objectRelationshipLocalService) {

		_ddmFormRenderer = ddmFormRenderer;
		_listTypeEntryLocalService = listTypeEntryLocalService;
		_objectEntryService = objectEntryService;
		_objectFieldLocalService = objectFieldLocalService;
		_objectLayoutLocalService = objectLayoutLocalService;
		_objectRelationshipLocalService = objectRelationshipLocalService;

		_objectRequestHelper = new ObjectRequestHelper(httpServletRequest);
	}

	public List<NavigationItem> getNavigationItems() throws PortalException {
		ObjectLayout objectLayout = getObjectLayout();

		if (objectLayout == null) {
			return Collections.emptyList();
		}

		NavigationItemList navigationItemList = new NavigationItemList();

		ObjectLayoutTab currentObjectLayoutTab = getObjectLayoutTab();
		LiferayPortletResponse liferayPortletResponse =
			_objectRequestHelper.getLiferayPortletResponse();

		for (ObjectLayoutTab objectLayoutTab :
				objectLayout.getObjectLayoutTabs()) {

			navigationItemList.add(
				navigationItem -> {
					navigationItem.setActive(
						objectLayoutTab.getObjectLayoutTabId() ==
							currentObjectLayoutTab.getObjectLayoutTabId());
					navigationItem.setHref(
						liferayPortletResponse.createRenderURL(),
						"objectLayoutTabId",
						objectLayoutTab.getObjectLayoutTabId());
					navigationItem.setLabel(
						objectLayoutTab.getName(
							_objectRequestHelper.getLocale()));
				});
		}

		return navigationItemList;
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

	public ObjectLayout getObjectLayout() throws PortalException {
		ObjectDefinition objectDefinition = getObjectDefinition();

		try {
			return _objectLayoutLocalService.getDefaultObjectLayout(
				objectDefinition.getObjectDefinitionId());
		}
		catch (NoSuchObjectLayoutException noSuchObjectLayoutException) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					noSuchObjectLayoutException, noSuchObjectLayoutException);
			}

			return null;
		}
	}

	public ObjectLayoutTab getObjectLayoutTab() throws PortalException {
		ObjectLayout objectLayout = getObjectLayout();

		if (objectLayout == null) {
			return null;
		}

		List<ObjectLayoutTab> objectLayoutTabs =
			objectLayout.getObjectLayoutTabs();

		long objectLayoutTabId = ParamUtil.getLong(
			_objectRequestHelper.getRequest(), "objectLayoutTabId");

		if (objectLayoutTabId == 0) {
			return objectLayoutTabs.get(0);
		}

		Stream<ObjectLayoutTab> stream = objectLayoutTabs.stream();

		Optional<ObjectLayoutTab> optional = stream.filter(
			objectLayoutTab ->
				objectLayoutTab.getObjectLayoutTabId() == objectLayoutTabId
		).findFirst();

		return optional.get();
	}

	public String renderDDMForm(PageContext pageContext)
		throws PortalException {

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

		ObjectLayoutTab objectLayoutTab = getObjectLayoutTab();

		if (objectLayoutTab == null) {
			return _ddmFormRenderer.render(ddmForm, ddmFormRenderingContext);
		}

		return _ddmFormRenderer.render(
			ddmForm, _getDDMFormLayout(objectLayoutTab),
			ddmFormRenderingContext);
	}

	private DDMFormFieldOptions _getDDMFieldOptions(long listTypeDefinitionId) {
		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		List<ListTypeEntry> listTypeEntries =
			_listTypeEntryLocalService.getListTypeEntries(listTypeDefinitionId);

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

		// TODO Store the type and the object field type in the database

		String type = DDMFormFieldTypeConstants.TEXT;

		if (Validator.isNotNull(objectField.getRelationshipType())) {
			type = "object-relationship";
		}

		DDMFormField ddmFormField = new DDMFormField(
			objectField.getName(), type);

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

		if (Validator.isNotNull(objectField.getRelationshipType())) {
			ObjectRelationship objectRelationship =
				_objectRelationshipLocalService.
					fetchObjectRelationshipByObjectFieldId2(
						objectField.getObjectFieldId());

			ddmFormField.setProperty(
				"objectDefinitionId",
				objectRelationship.getObjectDefinitionId1());
		}

		return ddmFormField;
	}

	private DDMFormLayout _getDDMFormLayout(ObjectLayoutTab objectLayoutTab)
		throws PortalException {

		DDMFormLayout ddmFormLayout = new DDMFormLayout();

		DDMFormLayoutPage ddmFormLayoutPage = new DDMFormLayoutPage();

		for (ObjectLayoutBox objectLayoutBox :
				objectLayoutTab.getObjectLayoutBoxes()) {

			for (ObjectLayoutRow objectLayoutRow :
					objectLayoutBox.getObjectLayoutRows()) {

				ddmFormLayoutPage.addDDMFormLayoutRow(
					_getDDMFormLayoutRow(objectLayoutRow));
			}
		}

		ddmFormLayout.addDDMFormLayoutPage(ddmFormLayoutPage);

		return ddmFormLayout;
	}

	private DDMFormLayoutColumn _getDDMFormLayoutColumn(
			ObjectLayoutColumn objectLayoutColumn, int size)
		throws PortalException {

		DDMFormLayoutColumn ddmFormLayoutColumn = new DDMFormLayoutColumn();

		ObjectField objectField = _objectFieldLocalService.getObjectField(
			objectLayoutColumn.getObjectFieldId());

		ddmFormLayoutColumn.setDDMFormFieldNames(
			ListUtil.fromArray(objectField.getName()));

		ddmFormLayoutColumn.setSize(size);

		return ddmFormLayoutColumn;
	}

	private DDMFormLayoutRow _getDDMFormLayoutRow(
			ObjectLayoutRow objectLayoutRow)
		throws PortalException {

		DDMFormLayoutRow ddmFormLayoutRow = new DDMFormLayoutRow();

		List<ObjectLayoutColumn> objectLayoutColumns =
			objectLayoutRow.getObjectLayoutColumns();

		for (ObjectLayoutColumn objectLayoutColumn : objectLayoutColumns) {
			ddmFormLayoutRow.addDDMFormLayoutColumn(
				_getDDMFormLayoutColumn(
					objectLayoutColumn, 12 / objectLayoutColumns.size()));
		}

		return ddmFormLayoutRow;
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

		if (StringUtil.equals(type, "BigDecimal") ||
			StringUtil.equals(type, "Double")) {

			ddmFormField.setProperty(
				FieldConstants.DATA_TYPE, FieldConstants.DOUBLE);
			ddmFormField.setType(DDMFormFieldTypeConstants.NUMERIC);
		}
		else if (StringUtil.equals(type, "Boolean")) {
			ddmFormField.setType(DDMFormFieldTypeConstants.CHECKBOX);
		}
		else if (StringUtil.equals(type, "Integer") ||
				 StringUtil.equals(type, "Long")) {

			ddmFormField.setProperty(
				FieldConstants.DATA_TYPE, FieldConstants.INTEGER);
			ddmFormField.setType(DDMFormFieldTypeConstants.NUMERIC);
		}
		else if (StringUtil.equals(type, "Date")) {
			ddmFormField.setType(DDMFormFieldTypeConstants.DATE);
		}
		else if (StringUtil.equals(type, "String") &&
				 (listTypeDefinitionId > 0)) {

			ddmFormField.setDDMFormFieldOptions(
				_getDDMFieldOptions(listTypeDefinitionId));
			ddmFormField.setType(DDMFormFieldTypeConstants.SELECT);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectEntryDisplayContext.class);

	private final DDMFormRenderer _ddmFormRenderer;
	private final ListTypeEntryLocalService _listTypeEntryLocalService;
	private ObjectEntry _objectEntry;
	private final ObjectEntryService _objectEntryService;
	private final ObjectFieldLocalService _objectFieldLocalService;
	private final ObjectLayoutLocalService _objectLayoutLocalService;
	private final ObjectRelationshipLocalService
		_objectRelationshipLocalService;
	private final ObjectRequestHelper _objectRequestHelper;

}