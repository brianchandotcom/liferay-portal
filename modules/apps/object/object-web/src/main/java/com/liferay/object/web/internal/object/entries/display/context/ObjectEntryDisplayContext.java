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
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidation;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldValidationExpression;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemList;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.InfoItemItemSelectorReturnType;
import com.liferay.item.selector.criteria.info.item.criterion.InfoItemItemSelectorCriterion;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.constants.ObjectLayoutBoxConstants;
import com.liferay.object.exception.NoSuchObjectLayoutException;
import com.liferay.object.field.business.type.ObjectFieldBusinessType;
import com.liferay.object.field.business.type.ObjectFieldBusinessTypeServicesTracker;
import com.liferay.object.field.render.ObjectFieldRenderingContext;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.model.ObjectLayout;
import com.liferay.object.model.ObjectLayoutBox;
import com.liferay.object.model.ObjectLayoutColumn;
import com.liferay.object.model.ObjectLayoutRow;
import com.liferay.object.model.ObjectLayoutTab;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.rest.dto.v1_0.FileEntry;
import com.liferay.object.rest.dto.v1_0.ListEntry;
import com.liferay.object.rest.dto.v1_0.ObjectEntry;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManager;
import com.liferay.object.rest.manager.v1_0.ObjectEntryManagerServicesTracker;
import com.liferay.object.scope.ObjectScopeProvider;
import com.liferay.object.scope.ObjectScopeProviderRegistry;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryService;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.object.service.ObjectLayoutLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.object.web.internal.constants.ObjectWebKeys;
import com.liferay.object.web.internal.display.context.helper.ObjectRequestHelper;
import com.liferay.object.web.internal.util.ObjectDefinitionPermissionUtil;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.util.TransformUtil;
import com.liferay.taglib.servlet.PipingServletResponseFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Marco Leo
 */
public class ObjectEntryDisplayContext {

	public ObjectEntryDisplayContext(
		DDMFormRenderer ddmFormRenderer, HttpServletRequest httpServletRequest,
		ItemSelector itemSelector,
		ObjectDefinitionLocalService objectDefinitionLocalService,
		ObjectEntryManagerServicesTracker objectEntryManagerServicesTracker,
		ObjectEntryService objectEntryService,
		ObjectFieldBusinessTypeServicesTracker
			objectFieldBusinessTypeServicesTracker,
		ObjectFieldLocalService objectFieldLocalService,
		ObjectLayoutLocalService objectLayoutLocalService,
		ObjectRelationshipLocalService objectRelationshipLocalService,
		ObjectScopeProviderRegistry objectScopeProviderRegistry,
		boolean readOnly) {

		_ddmFormRenderer = ddmFormRenderer;
		_itemSelector = itemSelector;
		_objectDefinitionLocalService = objectDefinitionLocalService;
		_objectEntryManagerServicesTracker = objectEntryManagerServicesTracker;
		_objectEntryService = objectEntryService;
		_objectFieldBusinessTypeServicesTracker =
			objectFieldBusinessTypeServicesTracker;
		_objectFieldLocalService = objectFieldLocalService;
		_objectLayoutLocalService = objectLayoutLocalService;
		_objectRelationshipLocalService = objectRelationshipLocalService;
		_objectScopeProviderRegistry = objectScopeProviderRegistry;
		_readOnly = readOnly;

		_objectRequestHelper = new ObjectRequestHelper(httpServletRequest);
		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public ObjectLayoutBox getCategorizationObjectLayoutBox()
		throws PortalException {

		ObjectDefinition objectDefinition = getObjectDefinition();

		if (!StringUtil.equals(
				objectDefinition.getStorageType(),
				ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT)) {

			return null;
		}

		ObjectLayoutTab objectLayoutTab = getObjectLayoutTab();

		if (objectLayoutTab == null) {
			return null;
		}

		for (ObjectLayoutBox objectLayoutBox :
				objectLayoutTab.getObjectLayoutBoxes()) {

			if (StringUtil.equals(
					objectLayoutBox.getType(),
					ObjectLayoutBoxConstants.TYPE_CATEGORIZATION)) {

				return objectLayoutBox;
			}
		}

		return null;
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
					_getNavigationItemHref(
						liferayPortletResponse, objectEntry, objectLayoutTab)
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

		String externalReferenceCode = ParamUtil.getString(
			_objectRequestHelper.getRequest(), "externalReferenceCode");

		if (_readOnly && Validator.isNull(externalReferenceCode)) {
			HttpServletRequest httpServletRequest =
				_objectRequestHelper.getRequest();

			externalReferenceCode = (String)httpServletRequest.getAttribute(
				ObjectWebKeys.EXTERNAL_REFERENCE_CODE);
		}

		ObjectDefinition objectDefinition = getObjectDefinition();

		ObjectEntryManager objectEntryManager =
			_objectEntryManagerServicesTracker.getObjectEntryManager(
				objectDefinition.getStorageType());

		try {
			_objectEntry = objectEntryManager.getObjectEntry(
				_getDTOConverterContext(), externalReferenceCode,
				_objectRequestHelper.getCompanyId(), objectDefinition,
				String.valueOf(_getGroupId()));
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}
		}

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
				_log.debug(noSuchObjectLayoutException);
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
		if (_readOnly || isDefaultUser()) {
			return null;
		}

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
				new InfoItemItemSelectorReturnType()));

		ObjectLayoutTab objectLayoutTab = getObjectLayoutTab();

		ObjectRelationship objectRelationship =
			_objectRelationshipLocalService.getObjectRelationship(
				objectLayoutTab.getObjectRelationshipId());

		ObjectDefinition objectDefinition2 =
			_objectDefinitionLocalService.getObjectDefinition(
				objectRelationship.getObjectDefinitionId2());

		infoItemItemSelectorCriterion.setItemType(
			objectDefinition2.getClassName());

		return PortletURLBuilder.create(
			_itemSelector.getItemSelectorURL(
				requestBackedPortletURLFactory,
				liferayPortletResponse.getNamespace() +
					"selectRelatedModalEntry",
				infoItemItemSelectorCriterion)
		).setParameter(
			"objectDefinitionId",
			() -> {
				ObjectDefinition objectDefinition1 =
					_objectDefinitionLocalService.getObjectDefinition(
						objectRelationship.getObjectDefinitionId1());

				return objectDefinition1.getObjectDefinitionId();
			}
		).setParameter(
			"objectEntryId",
			() -> {
				ObjectEntry objectEntry = getObjectEntry();

				return GetterUtil.getLong(objectEntry.getId());
			}
		).setParameter(
			"objectRelationshipId", objectRelationship.getObjectRelationshipId()
		).setParameter(
			"objectRelationshipType", objectRelationship.getType()
		).buildString();
	}

	public Map<String, String> getRelationshipContextParams()
		throws PortalException {

		return HashMapBuilder.put(
			"objectEntryId", String.valueOf(_objectEntry.getId())
		).put(
			"objectRelationshipId",
			() -> {
				ObjectLayoutTab objectLayoutTab = getObjectLayoutTab();

				return String.valueOf(
					objectLayoutTab.getObjectRelationshipId());
			}
		).put(
			"readOnly", String.valueOf(_readOnly || isDefaultUser())
		).build();
	}

	public boolean isDefaultUser() {
		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (permissionChecker == null) {
			return true;
		}

		User user = permissionChecker.getUser();

		return user.isDefaultUser();
	}

	public boolean isReadOnly() {
		if (_readOnly) {
			return true;
		}

		try {
			ObjectEntry objectEntry = getObjectEntry();

			if (objectEntry == null) {
				return false;
			}

			return !ObjectDefinitionPermissionUtil.hasModelResourcePermission(
				getObjectDefinition(), objectEntry, _objectEntryService,
				ActionKeys.UPDATE);
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}
		}

		return false;
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

		ddmFormRenderingContext.setGroupId(_getGroupId());
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

	private ObjectFieldRenderingContext _createObjectFieldRenderingContext()
		throws PortalException {

		ObjectFieldRenderingContext objectFieldRenderingContext =
			new ObjectFieldRenderingContext();

		objectFieldRenderingContext.setGroupId(
			_objectRequestHelper.getScopeGroupId());
		objectFieldRenderingContext.setHttpServletRequest(
			_objectRequestHelper.getRequest());
		objectFieldRenderingContext.setLocale(_objectRequestHelper.getLocale());

		ObjectEntry objectEntry = getObjectEntry();

		if (objectEntry != null) {
			objectFieldRenderingContext.setExternalReferenceCode(
				objectEntry.getExternalReferenceCode());
			objectFieldRenderingContext.setProperties(
				objectEntry.getProperties());
		}

		objectFieldRenderingContext.setPortletId(
			_objectRequestHelper.getPortletId());
		objectFieldRenderingContext.setUserId(_objectRequestHelper.getUserId());

		return objectFieldRenderingContext;
	}

	private DDMForm _getDDMForm(ObjectLayoutTab objectLayoutTab)
		throws PortalException {

		DDMForm ddmForm = new DDMForm();

		ddmForm.addAvailableLocale(_objectRequestHelper.getLocale());

		ObjectDefinition objectDefinition = getObjectDefinition();

		boolean readOnly = _readOnly;

		if (!readOnly) {
			ObjectEntry objectEntry = getObjectEntry();

			if (objectEntry != null) {
				readOnly =
					!ObjectDefinitionPermissionUtil.hasModelResourcePermission(
						objectDefinition, objectEntry, _objectEntryService,
						ActionKeys.UPDATE);
			}
		}

		if (GetterUtil.getBoolean(PropsUtil.get("feature.flag.LPS-154872"))) {
			List<ObjectField> objectFields =
				_objectFieldLocalService.getCustomObjectFields(
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
				_addDDMFormFields(
					ddmForm, objectFields, objectLayoutTab, readOnly);
			}
		}
		else {
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
				_addDDMFormFields(
					ddmForm, objectFields, objectLayoutTab, readOnly);
			}
		}

		ddmForm.setDefaultLocale(_objectRequestHelper.getLocale());

		return ddmForm;
	}

	private DDMFormField _getDDMFormField(
			ObjectField objectField, boolean readOnly)
		throws PortalException {

		// TODO Store the type and the object field type in the database

		ObjectFieldBusinessType objectFieldBusinessType =
			_objectFieldBusinessTypeServicesTracker.getObjectFieldBusinessType(
				objectField.getBusinessType());

		DDMFormField ddmFormField = new DDMFormField(
			objectField.getName(),
			objectFieldBusinessType.getDDMFormFieldTypeName());

		Map<String, Object> properties = objectFieldBusinessType.getProperties(
			objectField, _createObjectFieldRenderingContext());

		ddmFormField.setDDMFormFieldValidation(
			_getDDMFormFieldValidation(
				objectField.getBusinessType(), objectField.getName(),
				properties));

		LocalizedValue ddmFormFieldLabelLocalizedValue = new LocalizedValue(
			_objectRequestHelper.getLocale());

		ddmFormFieldLabelLocalizedValue.addString(
			_objectRequestHelper.getLocale(),
			objectField.getLabel(_objectRequestHelper.getLocale()));

		ddmFormField.setLabel(ddmFormFieldLabelLocalizedValue);

		properties.forEach(
			(key, value) -> ddmFormField.setProperty(key, value));

		if (Validator.isNotNull(objectField.getRelationshipType())) {
			ObjectRelationship objectRelationship =
				_objectRelationshipLocalService.
					fetchObjectRelationshipByObjectFieldId2(
						objectField.getObjectFieldId());

			ddmFormField.setProperty(
				"objectDefinitionId",
				String.valueOf(objectRelationship.getObjectDefinitionId1()));

			long parameterObjectFieldId =
				objectRelationship.getParameterObjectFieldId();

			if (GetterUtil.getBoolean(
					PropsUtil.get("feature.flag.LPS-155537")) &&
				(parameterObjectFieldId > 0)) {

				ObjectField parameterObjectField =
					_objectFieldLocalService.getObjectField(
						parameterObjectFieldId);

				ddmFormField.setProperty(
					"parameterObjectFieldName", parameterObjectField.getName());
			}
		}

		ddmFormField.setReadOnly(readOnly);
		ddmFormField.setRequired(objectField.isRequired());

		return ddmFormField;
	}

	private DDMFormFieldValidation _getDDMFormFieldValidation(
		String businessType, String objectFieldName,
		Map<String, Object> properties) {

		int defaultMaxLength = 0;

		if (Objects.equals(
				businessType, ObjectFieldConstants.BUSINESS_TYPE_LONG_TEXT)) {

			defaultMaxLength = 65000;
		}
		else if (Objects.equals(
					businessType, ObjectFieldConstants.BUSINESS_TYPE_TEXT)) {

			defaultMaxLength = 280;
		}

		if ((defaultMaxLength > 0) &&
			GetterUtil.getBoolean(properties.get("showCounter"))) {

			DDMFormFieldValidation ddmFormFieldValidation =
				new DDMFormFieldValidation();

			DDMFormFieldValidationExpression ddmFormFieldValidationExpression =
				new DDMFormFieldValidationExpression();

			int maxLength = GetterUtil.getInteger(
				properties.get("maxLength"), defaultMaxLength);

			ddmFormFieldValidationExpression.setValue(
				StringBundler.concat(
					"length(", objectFieldName, ") <= ", maxLength));

			ddmFormFieldValidation.setDDMFormFieldValidationExpression(
				ddmFormFieldValidationExpression);

			return ddmFormFieldValidation;
		}

		return null;
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

		Map<String, Object> values = objectEntry.getProperties();

		if (values.isEmpty()) {
			return null;
		}

		_setDateDDMFormFieldValue(ddmForm.getDDMFormFields(), values);

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

						long value = GetterUtil.getLong(
							values.get(ddmFormField.getName()));

						if (StringUtil.equals(
								ddmFormField.getType(),
								"object-relationship") &&
							(value == 0)) {

							_setDDMFormFieldValueValue(
								ddmFormField.getName(), ddmFormFieldValue,
								Collections.emptyMap());
						}
						else {
							_setDDMFormFieldValueValue(
								ddmFormField.getName(), ddmFormFieldValue,
								values);
						}
					}

					return ddmFormFieldValue;
				}));

		ddmFormValues.setDefaultLocale(_objectRequestHelper.getLocale());

		return ddmFormValues;
	}

	private DTOConverterContext _getDTOConverterContext() {
		return new DefaultDTOConverterContext(
			false, null, null, _objectRequestHelper.getRequest(), null,
			_themeDisplay.getLocale(), null, _themeDisplay.getUser());
	}

	private long _getGroupId() {
		ObjectDefinition objectDefinition = getObjectDefinition();

		ObjectScopeProvider objectScopeProvider =
			_objectScopeProviderRegistry.getObjectScopeProvider(
				objectDefinition.getScope());

		try {
			return objectScopeProvider.getGroupId(
				_objectRequestHelper.getRequest());
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}

			return 0L;
		}
	}

	private String _getNavigationItemHref(
		LiferayPortletResponse liferayPortletResponse, ObjectEntry objectEntry,
		ObjectLayoutTab objectLayoutTab) {

		if (_readOnly) {
			LiferayPortletURL liferayPortletURL =
				(LiferayPortletURL)liferayPortletResponse.createResourceURL();

			liferayPortletURL.setLifecycle(PortletRequest.RENDER_PHASE);

			return PortletURLBuilder.create(
				liferayPortletURL
			).setParameter(
				"externalReferenceCode", objectEntry.getExternalReferenceCode()
			).setParameter(
				"objectLayoutTabId", objectLayoutTab.getObjectLayoutTabId()
			).buildString();
		}

		return PortletURLBuilder.create(
			liferayPortletResponse.createRenderURL()
		).setMVCRenderCommandName(
			"/object_entries/edit_object_entry"
		).setParameter(
			"externalReferenceCode", objectEntry.getExternalReferenceCode()
		).setParameter(
			"objectLayoutTabId", objectLayoutTab.getObjectLayoutTabId()
		).buildString();
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
		List<DDMFormField> ddmFormFields, Map<String, Object> values) {

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

	private void _removeTimeFromDateString(
		DDMFormField ddmFormField, Map<String, Object> values) {

		Object value = values.get(ddmFormField.getName());

		if (value == null) {
			return;
		}

		String valueString = String.valueOf(value);

		values.put(
			ddmFormField.getName(),
			valueString.replaceAll(
				" [0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}.[0-9]", ""));
	}

	private void _setDateDDMFormFieldValue(
		List<DDMFormField> ddmFormFields, Map<String, Object> values) {

		for (DDMFormField ddmFormField : ddmFormFields) {
			if (StringUtil.equals(ddmFormField.getType(), "date")) {
				_removeTimeFromDateString(ddmFormField, values);
			}
			else if (StringUtil.equals(ddmFormField.getType(), "fieldset")) {
				_setDateDDMFormFieldValue(
					ddmFormField.getNestedDDMFormFields(), values);
			}
		}
	}

	private void _setDDMFormFieldValueValue(
		String ddmFormFieldName, DDMFormFieldValue ddmFormFieldValue,
		Map<String, Object> values) {

		Object value = values.get(ddmFormFieldName);

		if (value == null) {
			ddmFormFieldValue.setValue(
				new UnlocalizedValue(GetterUtil.DEFAULT_STRING));
		}
		else if (value instanceof FileEntry) {
			FileEntry fileEntry = (FileEntry)value;

			ddmFormFieldValue.setValue(
				new UnlocalizedValue(String.valueOf(fileEntry.getId())));
		}
		else if (value instanceof ListEntry) {
			ListEntry listEntry = (ListEntry)value;

			ddmFormFieldValue.setValue(
				new UnlocalizedValue(listEntry.getKey()));
		}
		else {
			ddmFormFieldValue.setValue(
				new UnlocalizedValue(String.valueOf(value)));
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ObjectEntryDisplayContext.class);

	private final DDMFormRenderer _ddmFormRenderer;
	private final ItemSelector _itemSelector;
	private final ObjectDefinitionLocalService _objectDefinitionLocalService;
	private ObjectEntry _objectEntry;
	private final ObjectEntryManagerServicesTracker
		_objectEntryManagerServicesTracker;
	private final ObjectEntryService _objectEntryService;
	private final ObjectFieldBusinessTypeServicesTracker
		_objectFieldBusinessTypeServicesTracker;
	private final ObjectFieldLocalService _objectFieldLocalService;
	private final Map<Long, String> _objectFieldNames = new HashMap<>();
	private final ObjectLayoutLocalService _objectLayoutLocalService;
	private final ObjectRelationshipLocalService
		_objectRelationshipLocalService;
	private final ObjectRequestHelper _objectRequestHelper;
	private final ObjectScopeProviderRegistry _objectScopeProviderRegistry;
	private final boolean _readOnly;
	private final ThemeDisplay _themeDisplay;

}