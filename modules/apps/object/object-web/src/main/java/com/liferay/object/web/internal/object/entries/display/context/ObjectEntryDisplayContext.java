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
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemList;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.info.item.criterion.InfoItemItemSelectorCriterion;
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
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectLayoutLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.web.internal.constants.ObjectWebKeys;
import com.liferay.object.web.internal.display.context.util.ObjectRequestHelper;
import com.liferay.object.web.internal.item.selector.ObjectEntryItemSelectorReturnType;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.util.TransformUtil;
import com.liferay.taglib.servlet.PipingServletResponseFactory;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
		ItemSelector itemSelector,
		ListTypeEntryLocalService listTypeEntryLocalService,
		ObjectDefinitionLocalService objectDefinitionLocalService,
		ObjectEntryService objectEntryService,
		ObjectFieldLocalService objectFieldLocalService,
		ObjectLayoutLocalService objectLayoutLocalService,
		ObjectRelationshipLocalService objectRelationshipLocalService) {

		_ddmFormRenderer = ddmFormRenderer;
		_itemSelector = itemSelector;
		_listTypeEntryLocalService = listTypeEntryLocalService;
		_objectDefinitionLocalService = objectDefinitionLocalService;
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

		ObjectEntry objectEntry = getObjectEntry();

		if (objectEntry == null) {
			return navigationItemList;
		}

		ObjectLayoutTab currentObjectLayoutTab = getObjectLayoutTab();
		LiferayPortletResponse liferayPortletResponse =
			_objectRequestHelper.getLiferayPortletResponse();
		long objectEntryId = objectEntry.getObjectEntryId();

		for (ObjectLayoutTab objectLayoutTab :
				objectLayout.getObjectLayoutTabs()) {

			if (objectLayoutTab.getObjectRelationshipId() > 0) {
				ObjectRelationship objectRelationship =
					_objectRelationshipLocalService.getObjectRelationship(
						objectLayoutTab.getObjectRelationshipId());

				ObjectDefinition objectDefinition =
					_objectDefinitionLocalService.getObjectDefinition(
						objectRelationship.getObjectDefinitionId2());

				if (!objectDefinition.isActive()) {
					continue;
				}
			}

			navigationItemList.add(
				NavigationItemBuilder.setActive(
					objectLayoutTab.getObjectLayoutTabId() ==
						currentObjectLayoutTab.getObjectLayoutTabId()
				).setHref(
					PortletURLBuilder.create(
						liferayPortletResponse.createRenderURL()
					).setMVCRenderCommandName(
						"/object_entries/edit_object_entry"
					).setParameter(
						"objectEntryId", objectEntryId
					).setParameter(
						"objectLayoutTabId",
						objectLayoutTab.getObjectLayoutTabId()
					).buildString()
				).setLabel(
					objectLayoutTab.getName(_objectRequestHelper.getLocale())
				).build());
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

	public CreationMenu getRelatedModelCreationMenu() throws PortalException {
		LiferayPortletResponse liferayPortletResponse =
			_objectRequestHelper.getLiferayPortletResponse();

		return CreationMenuBuilder.addDropdownItem(
			dropdownItem -> {
				dropdownItem.setHref(
					liferayPortletResponse.getNamespace() +
						"selectRelatedModel");
				dropdownItem.setLabel(
					LanguageUtil.get(_objectRequestHelper.getRequest(), "add"));
				dropdownItem.setTarget("event");
			}
		).build();
	}

	public String getRelatedObjectEntryItemSelectorURL()
		throws PortalException {

		RequestBackedPortletURLFactory requestBackedPortletURLFactory =
			RequestBackedPortletURLFactoryUtil.create(
				_objectRequestHelper.getRequest());

		LiferayPortletResponse liferayPortletResponse =
			_objectRequestHelper.getLiferayPortletResponse();

		InfoItemItemSelectorCriterion infoItemItemSelectorCriterion =
			new InfoItemItemSelectorCriterion();

		infoItemItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
			Collections.<ItemSelectorReturnType>singletonList(
				new ObjectEntryItemSelectorReturnType()));

		ObjectLayoutTab objectLayoutTab = getObjectLayoutTab();

		ObjectRelationship objectRelationship =
			_objectRelationshipLocalService.getObjectRelationship(
				objectLayoutTab.getObjectRelationshipId());

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				objectRelationship.getObjectDefinitionId2());

		infoItemItemSelectorCriterion.setItemType(
			objectDefinition.getClassName());

		return PortletURLBuilder.create(
			_itemSelector.getItemSelectorURL(
				requestBackedPortletURLFactory,
				liferayPortletResponse.getNamespace() +
					"selectRelatedModalEntry",
				infoItemItemSelectorCriterion)
		).buildString();
	}

	public String renderDDMForm(PageContext pageContext)
		throws PortalException {

		ObjectLayoutTab objectLayoutTab = getObjectLayoutTab();

		DDMForm ddmForm = _getDDMForm(objectLayoutTab);

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

		if (objectLayoutTab == null) {
			return _ddmFormRenderer.render(ddmForm, ddmFormRenderingContext);
		}

		return _ddmFormRenderer.render(
			ddmForm, _getDDMFormLayout(ddmForm, objectLayoutTab),
			ddmFormRenderingContext);
	}

	private void _addDDMFormFields(
			DDMForm ddmForm, List<ObjectField> objectFields,
			ObjectLayoutTab objectLayoutTab, boolean readOnly)
		throws PortalException {

		for (ObjectLayoutBox objectLayoutBox :
				objectLayoutTab.getObjectLayoutBoxes()) {

			List<DDMFormField> nestedDDMFormFields = _getNestedDDMFormFields(
				objectFields, objectLayoutBox, readOnly);

			if (nestedDDMFormFields.isEmpty()) {
				continue;
			}

			ddmForm.addDDMFormField(
				new DDMFormField(
					String.valueOf(objectLayoutBox.getPrimaryKey()),
					"fieldset") {

					{
						setLabel(
							new LocalizedValue() {
								{
									addString(
										_objectRequestHelper.getLocale(),
										objectLayoutBox.getName(
											_objectRequestHelper.getLocale()));
								}
							});
						setLocalizable(false);
						setNestedDDMFormFields(nestedDDMFormFields);
						setProperty(
							"collapsible", objectLayoutBox.isCollapsable());
						setProperty("rows", _getRows(objectLayoutBox));
						setReadOnly(false);
						setRepeatable(false);
						setRequired(false);
						setShowLabel(true);
					}
				});
		}
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

	private DDMForm _getDDMForm(ObjectLayoutTab objectLayoutTab)
		throws PortalException {

		DDMForm ddmForm = new DDMForm();

		ddmForm.addAvailableLocale(_objectRequestHelper.getLocale());

		boolean readOnly = false;

		ObjectEntry objectEntry = getObjectEntry();

		if (objectEntry != null) {
			readOnly = !_objectEntryService.hasModelResourcePermission(
				objectEntry, ActionKeys.UPDATE);
		}

		ObjectDefinition objectDefinition = getObjectDefinition();

		List<ObjectField> objectFields =
			_objectFieldLocalService.getObjectFields(
				objectDefinition.getObjectDefinitionId());

		if (objectLayoutTab == null) {
			for (ObjectField objectField : objectFields) {
				if (!_isActive(objectField)) {
					continue;
				}

				ddmForm.addDDMFormField(
					_getDDMFormField(objectField, readOnly));
			}
		}
		else {
			_addDDMFormFields(ddmForm, objectFields, objectLayoutTab, readOnly);
		}

		ddmForm.setDefaultLocale(_objectRequestHelper.getLocale());

		return ddmForm;
	}

	private DDMFormField _getDDMFormField(
		ObjectField objectField, boolean readOnly) {

		// TODO Store the type and the object field type in the database

		DDMFormField ddmFormField = new DDMFormField(
			objectField.getName(), DDMFormFieldTypeConstants.TEXT);

		_setDDMFormFieldProperties(
			ddmFormField,
			GetterUtil.getLong(objectField.getListTypeDefinitionId()),
			objectField.getRelationshipType(), objectField.getType());

		LocalizedValue ddmFormFieldLabelLocalizedValue = new LocalizedValue(
			_objectRequestHelper.getLocale());

		ddmFormFieldLabelLocalizedValue.addString(
			_objectRequestHelper.getLocale(),
			objectField.getLabel(_objectRequestHelper.getLocale()));

		ddmFormField.setLabel(ddmFormFieldLabelLocalizedValue);

		ddmFormField.setReadOnly(readOnly);
		ddmFormField.setRequired(objectField.isRequired());

		if (Validator.isNotNull(objectField.getRelationshipType())) {
			ObjectRelationship objectRelationship =
				_objectRelationshipLocalService.
					fetchObjectRelationshipByObjectFieldId2(
						objectField.getObjectFieldId());

			ddmFormField.setProperty(
				"objectDefinitionId",
				String.valueOf(objectRelationship.getObjectDefinitionId1()));
		}

		return ddmFormField;
	}

	private DDMFormLayout _getDDMFormLayout(
		DDMForm ddmForm, ObjectLayoutTab objectLayoutTab) {

		DDMFormLayout ddmFormLayout = new DDMFormLayout();

		DDMFormLayoutPage ddmFormLayoutPage = new DDMFormLayoutPage();

		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(false);

		for (ObjectLayoutBox objectLayoutBox :
				objectLayoutTab.getObjectLayoutBoxes()) {

			if (!ddmFormFieldsMap.containsKey(
					String.valueOf(objectLayoutBox.getPrimaryKey()))) {

				continue;
			}

			DDMFormLayoutRow ddmFormLayoutRow = new DDMFormLayoutRow();

			DDMFormLayoutColumn ddmFormLayoutColumn = new DDMFormLayoutColumn();

			ddmFormLayoutColumn.setDDMFormFieldNames(
				ListUtil.fromArray(
					String.valueOf(objectLayoutBox.getPrimaryKey())));

			ddmFormLayoutColumn.setSize(12);

			ddmFormLayoutRow.addDDMFormLayoutColumn(ddmFormLayoutColumn);

			ddmFormLayoutPage.addDDMFormLayoutRow(ddmFormLayoutRow);
		}

		ddmFormLayout.addDDMFormLayoutPage(ddmFormLayoutPage);

		return ddmFormLayout;
	}

	private DDMFormValues _getDDMFormValues(
		DDMForm ddmForm, ObjectEntry objectEntry) {

		Map<String, Serializable> values = objectEntry.getValues();

		if (values.isEmpty()) {
			return null;
		}

		DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

		ddmFormValues.addAvailableLocale(_objectRequestHelper.getLocale());

		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(false);

		ddmFormValues.setDDMFormFieldValues(
			TransformUtil.transform(
				ddmFormFieldsMap.values(),
				ddmFormField -> {
					DDMFormFieldValue ddmFormFieldValue =
						new DDMFormFieldValue();

					ddmFormFieldValue.setName(ddmFormField.getName());
					ddmFormFieldValue.setNestedDDMFormFields(
						_getNestedDDMFormFieldValues(
							ddmFormField.getNestedDDMFormFields(), values));

					if (!StringUtil.equals(
							ddmFormField.getType(),
							DDMFormFieldTypeConstants.FIELDSET)) {

						_setDDMFormFieldValueValue(
							ddmFormField.getName(), ddmFormFieldValue, values);
					}

					return ddmFormFieldValue;
				}));

		ddmFormValues.setDefaultLocale(_objectRequestHelper.getLocale());

		return ddmFormValues;
	}

	private List<DDMFormField> _getNestedDDMFormFields(
			List<ObjectField> objectFields, ObjectLayoutBox objectLayoutBox,
			boolean readOnly)
		throws PortalException {

		List<DDMFormField> nestedDDMFormFields = new ArrayList<>();

		for (ObjectLayoutRow objectLayoutRow :
				objectLayoutBox.getObjectLayoutRows()) {

			for (ObjectLayoutColumn objectLayoutColumn :
					objectLayoutRow.getObjectLayoutColumns()) {

				Stream<ObjectField> stream = objectFields.stream();

				Optional<ObjectField> objectFieldOptional = stream.filter(
					objectField ->
						objectField.getObjectFieldId() ==
							objectLayoutColumn.getObjectFieldId()
				).findFirst();

				if (objectFieldOptional.isPresent()) {
					ObjectField objectField = objectFieldOptional.get();

					if (!_isActive(objectField)) {
						continue;
					}

					_objectFieldNames.put(
						objectLayoutColumn.getObjectFieldId(),
						objectField.getName());
					nestedDDMFormFields.add(
						_getDDMFormField(objectField, readOnly));
				}
			}
		}

		return nestedDDMFormFields;
	}

	private List<DDMFormFieldValue> _getNestedDDMFormFieldValues(
		List<DDMFormField> ddmFormFields, Map<String, Serializable> values) {

		return TransformUtil.transform(
			ddmFormFields,
			ddmFormField -> {
				DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

				ddmFormFieldValue.setName(ddmFormField.getName());

				_setDDMFormFieldValueValue(
					ddmFormField.getName(), ddmFormFieldValue, values);

				return ddmFormFieldValue;
			});
	}

	private String _getRows(ObjectLayoutBox objectLayoutBox) {
		JSONArray rowsJSONArray = JSONFactoryUtil.createJSONArray();

		for (ObjectLayoutRow objectLayoutRow :
				objectLayoutBox.getObjectLayoutRows()) {

			JSONArray columnsJSONArray = JSONFactoryUtil.createJSONArray();

			for (ObjectLayoutColumn objectLayoutColumn :
					objectLayoutRow.getObjectLayoutColumns()) {

				columnsJSONArray.put(
					JSONUtil.put(
						"fields",
						JSONUtil.put(
							_objectFieldNames.get(
								objectLayoutColumn.getObjectFieldId()))
					).put(
						"size", objectLayoutColumn.getSize()
					));
			}

			rowsJSONArray.put(JSONUtil.put("columns", columnsJSONArray));
		}

		return rowsJSONArray.toString();
	}

	private boolean _isActive(ObjectField objectField) throws PortalException {
		if (Validator.isNotNull(objectField.getRelationshipType())) {
			ObjectRelationship objectRelationship =
				_objectRelationshipLocalService.
					fetchObjectRelationshipByObjectFieldId2(
						objectField.getObjectFieldId());

			ObjectDefinition relatedObjectDefinition =
				_objectDefinitionLocalService.getObjectDefinition(
					objectRelationship.getObjectDefinitionId1());

			return relatedObjectDefinition.isActive();
		}

		return true;
	}

	private void _setDDMFormFieldProperties(
		DDMFormField ddmFormField, long listTypeDefinitionId,
		String relationshipType, String type) {

		if (Validator.isNotNull(relationshipType)) {
			ddmFormField.setType("object-relationship");
		}
		else if (StringUtil.equals(type, "BigDecimal") ||
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

	private void _setDDMFormFieldValueValue(
		String ddmFormFieldName, DDMFormFieldValue ddmFormFieldValue,
		Map<String, Serializable> values) {

		Serializable serializable = values.get(ddmFormFieldName);

		if (serializable == null) {
			ddmFormFieldValue.setValue(
				new UnlocalizedValue(GetterUtil.DEFAULT_STRING));
		}
		else {
			ddmFormFieldValue.setValue(
				new UnlocalizedValue(String.valueOf(serializable)));
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectEntryDisplayContext.class);

	private final DDMFormRenderer _ddmFormRenderer;
	private final ItemSelector _itemSelector;
	private final ListTypeEntryLocalService _listTypeEntryLocalService;
	private final ObjectDefinitionLocalService _objectDefinitionLocalService;
	private ObjectEntry _objectEntry;
	private final ObjectEntryService _objectEntryService;
	private final ObjectFieldLocalService _objectFieldLocalService;
	private final Map<Long, String> _objectFieldNames = new HashMap<>();
	private final ObjectLayoutLocalService _objectLayoutLocalService;
	private final ObjectRelationshipLocalService
		_objectRelationshipLocalService;
	private final ObjectRequestHelper _objectRequestHelper;

}