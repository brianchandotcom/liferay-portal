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

package com.liferay.object.web.internal.object.definitions.display.context;

import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.dynamic.data.mapping.util.DDMFormLayoutFactory;
import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.object.action.executor.ObjectActionExecutor;
import com.liferay.object.action.executor.ObjectActionExecutorRegistry;
import com.liferay.object.action.trigger.ObjectActionTrigger;
import com.liferay.object.action.trigger.ObjectActionTriggerRegistry;
import com.liferay.object.constants.ObjectActionExecutorConstants;
import com.liferay.object.model.ObjectAction;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.web.internal.constants.ObjectWebKeys;
import com.liferay.object.web.internal.display.context.helper.ObjectRequestHelper;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.vulcan.util.TransformUtil;
import com.liferay.taglib.servlet.PipingServletResponseFactory;

import java.io.Serializable;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Marco Leo
 */
public class ObjectDefinitionsActionsDisplayContext {

	public ObjectDefinitionsActionsDisplayContext(
		DDMFormRenderer ddmFormRenderer, HttpServletRequest httpServletRequest,
		ObjectActionExecutorRegistry objectActionExecutorRegistry,
		ObjectActionTriggerRegistry objectActionTriggerRegistry,
		ModelResourcePermission<ObjectDefinition>
			objectDefinitionModelResourcePermission,
		JSONFactory jsonFactory) {

		_ddmFormRenderer = ddmFormRenderer;
		_objectActionExecutorRegistry = objectActionExecutorRegistry;
		_objectActionTriggerRegistry = objectActionTriggerRegistry;
		_objectDefinitionModelResourcePermission =
			objectDefinitionModelResourcePermission;
		_jsonFactory = jsonFactory;

		_objectRequestHelper = new ObjectRequestHelper(httpServletRequest);
	}

	public String getAPIURL() {
		return "/o/object-admin/v1.0/object-definitions/" +
			getObjectDefinitionId() + "/object-actions";
	}

	public CreationMenu getCreationMenu() throws Exception {
		CreationMenu creationMenu = new CreationMenu();

		if (!hasUpdateObjectDefinitionPermission()) {
			return creationMenu;
		}

		creationMenu.addDropdownItem(
			dropdownItem -> {
				dropdownItem.setHref("addObjectAction");
				dropdownItem.setLabel(
					LanguageUtil.get(
						_objectRequestHelper.getRequest(),
						"add-object-action"));
				dropdownItem.setTarget("event");
			});

		return creationMenu;
	}

	public List<FDSActionDropdownItem> getFDSActionDropdownItems()
		throws Exception {

		return Arrays.asList(
			new FDSActionDropdownItem(
				PortletURLBuilder.create(
					getPortletURL()
				).setMVCRenderCommandName(
					"/object_definitions/edit_object_action"
				).setParameter(
					"objectActionId", "{id}"
				).setWindowState(
					LiferayWindowState.POP_UP
				).buildString(),
				"view", "view",
				LanguageUtil.get(_objectRequestHelper.getRequest(), "view"),
				"get", null, "sidePanel"),
			new FDSActionDropdownItem(
				"/o/object-admin/v1.0/object-actions/{id}", "trash", "delete",
				LanguageUtil.get(_objectRequestHelper.getRequest(), "delete"),
				"delete", "delete", "async"));
	}

	public ObjectAction getObjectAction() {
		HttpServletRequest httpServletRequest =
			_objectRequestHelper.getRequest();

		return (ObjectAction)httpServletRequest.getAttribute(
			ObjectWebKeys.OBJECT_ACTION);
	}

	public ObjectActionExecutor getObjectActionExecutor() {
		ObjectAction objectAction = getObjectAction();

		return _objectActionExecutorRegistry.getObjectActionExecutor(
			objectAction.getObjectActionExecutorKey());
	}

	public JSONArray getObjectActionExecutorsJSONArray() {
		JSONArray objectActionExecutorsJSONArray =
			_jsonFactory.createJSONArray();

		List<ObjectActionExecutor> objectActionExecutors =
			_objectActionExecutorRegistry.getObjectActionExecutors();

		objectActionExecutors.forEach(
			objectActionExecutor -> objectActionExecutorsJSONArray.put(
				JSONUtil.put(
					"description",
					LanguageUtil.get(
						_objectRequestHelper.getLocale(),
						"object-action-executor-help[" +
							objectActionExecutor.getKey() + "]")
				).put(
					"key", objectActionExecutor.getKey()
				).put(
					"label",
					LanguageUtil.get(
						_objectRequestHelper.getLocale(),
						"object-action-executor[" +
							objectActionExecutor.getKey() + "]")
				)));

		return objectActionExecutorsJSONArray;
	}

	public JSONArray getObjectActionTriggersJSONArray() {
		JSONArray objectActionTriggersJSONArray =
			_jsonFactory.createJSONArray();

		ObjectDefinition objectDefinition = getObjectDefinition();

		List<ObjectActionTrigger> objectActionTriggers =
			_objectActionTriggerRegistry.getObjectActionTriggers(
				objectDefinition.getClassName());

		objectActionTriggers.forEach(
			objectActionTrigger -> objectActionTriggersJSONArray.put(
				JSONUtil.put(
					"description",
					LanguageUtil.get(
						_objectRequestHelper.getLocale(),
						"object-action-trigger-help[" +
							objectActionTrigger.getKey() + "]")
				).put(
					"key", objectActionTrigger.getKey()
				).put(
					"label",
					LanguageUtil.get(
						_objectRequestHelper.getLocale(),
						"object-action-trigger[" +
							objectActionTrigger.getKey() + "]")
				)));

		return objectActionTriggersJSONArray;
	}

	public ObjectDefinition getObjectDefinition() {
		HttpServletRequest httpServletRequest =
			_objectRequestHelper.getRequest();

		return (ObjectDefinition)httpServletRequest.getAttribute(
			ObjectWebKeys.OBJECT_DEFINITION);
	}

	public long getObjectDefinitionId() {
		HttpServletRequest httpServletRequest =
			_objectRequestHelper.getRequest();

		ObjectDefinition objectDefinition =
			(ObjectDefinition)httpServletRequest.getAttribute(
				ObjectWebKeys.OBJECT_DEFINITION);

		return objectDefinition.getObjectDefinitionId();
	}

	public PortletURL getPortletURL() throws PortletException {
		return PortletURLUtil.clone(
			PortletURLUtil.getCurrent(
				_objectRequestHelper.getLiferayPortletRequest(),
				_objectRequestHelper.getLiferayPortletResponse()),
			_objectRequestHelper.getLiferayPortletResponse());
	}

	public boolean hasUpdateObjectDefinitionPermission()
		throws PortalException {

		return _objectDefinitionModelResourcePermission.contains(
			_objectRequestHelper.getPermissionChecker(),
			getObjectDefinitionId(), ActionKeys.UPDATE);
	}

	public String renderDDMForm(PageContext pageContext)
		throws PortalException {

		ObjectActionExecutor objectActionExecutor = getObjectActionExecutor();

		if (objectActionExecutor.getSettings() == null) {
			return "";
		}

		DDMForm ddmForm = DDMFormFactory.create(
			objectActionExecutor.getSettings());

		if (StringUtil.equals(
				objectActionExecutor.getKey(),
				ObjectActionExecutorConstants.KEY_WEBHOOK)) {

			List<DDMFormField> ddmFormFields = ddmForm.getDDMFormFields();

			Stream<DDMFormField> stream = ddmFormFields.stream();

			Optional<DDMFormField> ddmFormFieldOptional = stream.filter(
				ddmFormField -> ddmFormField.getName(
				).equals(
					"url"
				)
			).findFirst();

			if (ddmFormFieldOptional.isPresent()) {
				DDMFormField ddmFormField = ddmFormFieldOptional.get();

				ddmFormField.setProperty("required", true);
			}
		}

		DDMFormRenderingContext ddmFormRenderingContext =
			new DDMFormRenderingContext();

		ddmFormRenderingContext.setContainerId(
			"editObjectActionExecutorSettings");

		DDMFormValues ddmFormValues = _getDDMFormValues(
			ddmForm, getObjectAction());

		if (ddmFormValues != null) {
			ddmFormRenderingContext.setDDMFormValues(ddmFormValues);
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

		ddmFormRenderingContext.setReadOnly(
			!hasUpdateObjectDefinitionPermission());
		ddmFormRenderingContext.setShowRequiredFieldsWarning(true);

		return _ddmFormRenderer.render(
			ddmForm,
			DDMFormLayoutFactory.create(objectActionExecutor.getSettings()),
			ddmFormRenderingContext);
	}

	private DDMFormValues _getDDMFormValues(
		DDMForm ddmForm, ObjectAction objectAction) {

		UnicodeProperties parametersUnicodeProperties =
			objectAction.getParametersUnicodeProperties();

		if (parametersUnicodeProperties.isEmpty()) {
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

					Serializable serializable = parametersUnicodeProperties.get(
						ddmFormField.getName());

					if (serializable == null) {
						ddmFormFieldValue.setValue(
							new UnlocalizedValue(GetterUtil.DEFAULT_STRING));
					}
					else {
						ddmFormFieldValue.setValue(
							new UnlocalizedValue(
								_getValue(
									ddmFormField.getType(),
									String.valueOf(serializable))));
					}

					return ddmFormFieldValue;
				}));

		ddmFormValues.setDefaultLocale(_objectRequestHelper.getLocale());

		return ddmFormValues;
	}

	private String _getValue(String type, String value) {
		if (StringUtil.equals(type, DDMFormFieldTypeConstants.OBJECT_FIELD) ||
			StringUtil.equals(type, DDMFormFieldTypeConstants.SELECT)) {

			return JSONUtil.putAll(
				StringUtil.split(value)
			).toString();
		}

		return value;
	}

	private final DDMFormRenderer _ddmFormRenderer;
	private final JSONFactory _jsonFactory;
	private final ObjectActionExecutorRegistry _objectActionExecutorRegistry;
	private final ObjectActionTriggerRegistry _objectActionTriggerRegistry;
	private final ModelResourcePermission<ObjectDefinition>
		_objectDefinitionModelResourcePermission;
	private final ObjectRequestHelper _objectRequestHelper;

}