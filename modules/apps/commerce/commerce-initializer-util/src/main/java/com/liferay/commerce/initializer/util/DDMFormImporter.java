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

package com.liferay.commerce.initializer.util;

import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordSetConstants;
import com.liferay.dynamic.data.lists.model.DDLRecordSetSettings;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormLayoutDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.util.DDMFormFactoryHelper;
import com.liferay.dynamic.data.mapping.util.DDMUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Steven Smith
 */
@Component(enabled = false, service = DDMFormImporter.class)
public class DDMFormImporter {

	public void importDDMForms(
			JSONArray jsonArray, long scopeGroupId, long userId)
		throws Exception {

		User user = _userLocalService.fetchUser(userId);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setCompanyId(user.getCompanyId());
		serviceContext.setScopeGroupId(scopeGroupId);
		serviceContext.setUserId(userId);

		for (int i = 0; i < jsonArray.length(); i++) {
			_addDDMFormInstance(
				jsonArray.getJSONObject(i), userId, scopeGroupId,
				serviceContext);
		}
	}

	protected void updatePermissions(
			long companyId, String name, String primKey, JSONArray jsonArray)
		throws PortalException {

		if (jsonArray == null) {
			jsonArray = JSONFactoryUtil.createJSONArray(
				"[{\"actionIds\": [\"VIEW\", \"ADD_FORM_INSTANCE_RECORD\"]," +
					"\"roleName\": \"Site Member\", \"scope\": 4}]");
		}

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			int scope = jsonObject.getInt("scope");

			String roleName = jsonObject.getString("roleName");

			Role role = _roleLocalService.getRole(companyId, roleName);

			String[] actionIds = new String[0];

			JSONArray actionIdsJSONArray = jsonObject.getJSONArray("actionIds");

			if (actionIdsJSONArray != null) {
				for (int j = 0; j < actionIdsJSONArray.length(); j++) {
					actionIds = ArrayUtil.append(
						actionIds, actionIdsJSONArray.getString(j));
				}
			}

			_resourcePermissionLocalService.setResourcePermissions(
				companyId, name, scope, primKey, role.getRoleId(), actionIds);
		}
	}

	private void _addDDMFormInstance(
			JSONObject jsonObject, long userId, long scopeGroupId,
			ServiceContext serviceContext)
		throws Exception {

		//DDM Form

		Locale locale = serviceContext.getLocale();

		Map<Locale, String> nameMap = HashMapBuilder.put(
			locale, jsonObject.getString("name")
		).build();

		Map<Locale, String> descriptionMap = HashMapBuilder.put(
			locale, jsonObject.getString("description")
		).build();

		String jsonForm = jsonObject.getString("ddmStructure");

		String jsonFormSettings = jsonObject.getString("ddmFormSettings");

		DDMStructure ddmStructure = _createDDMStructure(
			nameMap, descriptionMap, jsonForm, jsonFormSettings,
			serviceContext);

		String jsonFormLayout = jsonObject.getString("ddmStructureLayout");

		_updateDDMStructureLayout(
			ddmStructure, ddmStructure.getDDMForm(), jsonFormLayout,
			serviceContext);

		DDMFormValuesDeserializerDeserializeRequest
			ddmFormValuesDeserializerDeserializeRequest =
				DDMFormValuesDeserializerDeserializeRequest.Builder.newBuilder(
					jsonFormSettings, ddmStructure.getDDMForm()
				).build();

		DDMFormValuesDeserializerDeserializeResponse
			ddmFormValuesDeserializerDeserializeResponse =
				_ddmFormValuesDeserializer.deserialize(
					ddmFormValuesDeserializerDeserializeRequest);

		DDMFormValues settingsDDMFormValues =
			ddmFormValuesDeserializerDeserializeResponse.getDDMFormValues();

		DDMFormInstance ddmFormInstance =
			_ddmFormInstanceLocalService.addFormInstance(
				userId, scopeGroupId, ddmStructure.getStructureId(), nameMap,
				descriptionMap, settingsDDMFormValues, serviceContext);

		JSONArray permissionsJSONArray = jsonObject.getJSONArray("permissions");

		if ((permissionsJSONArray != null) &&
			(permissionsJSONArray.length() > 0)) {

			updatePermissions(
				ddmFormInstance.getCompanyId(),
				ddmFormInstance.getModelClassName(),
				String.valueOf(ddmFormInstance.getPrimaryKey()),
				permissionsJSONArray);
		}
		else {

			// Give site members view and add form instance permissions

			updatePermissions(
				ddmFormInstance.getCompanyId(),
				ddmFormInstance.getModelClassName(),
				String.valueOf(ddmFormInstance.getPrimaryKey()), null);
		}
	}

	private List<DDMFormFieldValue> _createDDMFormFieldValues(
			DDMForm ddmForm, String jsonFormSettings)
		throws Exception {

		List<DDMFormField> ddmFormFields = ddmForm.getDDMFormFields();

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			jsonFormSettings);

		Stream<DDMFormField> ddmFormFieldsStream = ddmFormFields.stream();

		return ddmFormFieldsStream.map(
			formField -> {
				DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

				ddmFormFieldValue.setName(formField.getName());

				UnlocalizedValue unlocalizedValue = new UnlocalizedValue(
					jsonObject.getString(formField.getName()));

				ddmFormFieldValue.setValue(unlocalizedValue);

				return ddmFormFieldValue;
			}
		).collect(
			Collectors.toList()
		);
	}

	private DDMStructure _createDDMStructure(
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			String jsonForm, String jsonFormSettings,
			ServiceContext serviceContext)
		throws Exception {

		jsonForm = StringUtil.replace(
			jsonForm, "[$LOCALE$]",
			LocaleUtil.toLanguageId(serviceContext.getLocale()));

		DDMFormDeserializerDeserializeRequest
			ddmFormDeserializerDeserializeRequest =
				DDMFormDeserializerDeserializeRequest.Builder.newBuilder(
					jsonForm
				).build();

		DDMFormDeserializerDeserializeResponse
			ddmFormDeserializerDeserializeResponse =
				_ddmFormDeserializer.deserialize(
					ddmFormDeserializerDeserializeRequest);

		DDMForm ddmForm = ddmFormDeserializerDeserializeResponse.getDDMForm();

		DDMFormLayout defaultDDMFormLayout = DDMUtil.getDefaultDDMFormLayout(
			ddmForm);

		defaultDDMFormLayout.setPaginationMode(StringPool.BLANK);

		long classNameId = _classNameLocalService.getClassNameId(
			DDLRecordSet.class);
		long userId = serviceContext.getUserId();
		long groupId = serviceContext.getScopeGroupId();

		DDMStructure ddmStructure = _ddmStructureLocalService.addStructure(
			userId, groupId, DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
			classNameId, StringPool.BLANK, nameMap, descriptionMap, ddmForm,
			defaultDDMFormLayout, StorageType.DEFAULT.toString(),
			DDMStructureConstants.TYPE_AUTO, serviceContext);

		DDLRecordSet ddlRecordSet = _ddlRecordSetLocalService.addRecordSet(
			userId, groupId, ddmStructure.getStructureId(), null, nameMap,
			descriptionMap, DDLRecordSetConstants.MIN_DISPLAY_ROWS_DEFAULT,
			DDLRecordSetConstants.SCOPE_FORMS, serviceContext);

		DDMFormFactoryHelper ddmFormFactoryHelper = new DDMFormFactoryHelper(
			DDLRecordSetSettings.class);

		DDMForm settingsDDMForm = ddmFormFactoryHelper.createDDMForm();

		DDMFormValues ddmFormValues = new DDMFormValues(settingsDDMForm);

		ddmFormValues.setDefaultLocale(serviceContext.getLocale());
		ddmFormValues.addAvailableLocale(serviceContext.getLocale());
		ddmFormValues.setDDMFormFieldValues(
			_createDDMFormFieldValues(settingsDDMForm, jsonFormSettings));

		_ddlRecordSetLocalService.updateRecordSet(
			ddlRecordSet.getRecordSetId(), ddmFormValues);

		return ddmStructure;
	}

	private void _updateDDMStructureLayout(
			DDMStructure structure, DDMForm ddmForm, String jsonFormLayout,
			ServiceContext serviceContext)
		throws Exception {

		long userId = serviceContext.getUserId();

		DDMFormLayoutDeserializerDeserializeRequest
			ddmFormLayoutDeserializerDeserializeRequest =
				DDMFormLayoutDeserializerDeserializeRequest.Builder.newBuilder(
					jsonFormLayout
				).build();

		DDMFormLayoutDeserializerDeserializeResponse
			ddmFormLayoutDeserializerDeserializeResponse =
				_ddmFormLayoutDeserializer.deserialize(
					ddmFormLayoutDeserializerDeserializeRequest);

		DDMFormLayout formLayout =
			ddmFormLayoutDeserializerDeserializeResponse.getDDMFormLayout();

		_ddmStructureLocalService.updateStructure(
			userId, structure.getStructureId(), ddmForm, formLayout,
			serviceContext);
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private DDLRecordSetLocalService _ddlRecordSetLocalService;

	@Reference
	private DDMFormDeserializer _ddmFormDeserializer;

	@Reference
	private DDMFormInstanceLocalService _ddmFormInstanceLocalService;

	@Reference
	private DDMFormLayoutDeserializer _ddmFormLayoutDeserializer;

	@Reference
	private DDMFormValuesDeserializer _ddmFormValuesDeserializer;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserLocalService _userLocalService;

}