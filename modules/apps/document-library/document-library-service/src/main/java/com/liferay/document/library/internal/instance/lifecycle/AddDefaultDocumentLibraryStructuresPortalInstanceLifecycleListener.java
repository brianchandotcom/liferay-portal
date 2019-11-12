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

package com.liferay.document.library.internal.instance.lifecycle;

import com.liferay.document.library.configuration.DLConfiguration;
import com.liferay.document.library.kernel.util.RawMetadataProcessor;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializerSerializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializerSerializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.dynamic.data.mapping.util.DefaultDDMStructureHelper;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.metadata.RawMetadataProcessorUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Portal;

import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 * @author Miguel Pastor
 * @author Roberto Díaz
 */
@Component(
	configurationPid = "com.liferay.document.library.configuration.DLConfiguration",
	service = PortalInstanceLifecycleListener.class
)
public class AddDefaultDocumentLibraryStructuresPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		if (!_dlConfiguration.addDefaultStructures()) {
			return;
		}

		addDLRawMetadataStructures(company.getCompanyId());
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_dlConfiguration = ConfigurableUtil.createConfigurable(
			DLConfiguration.class, properties);
	}

	protected void addDLRawMetadataStructures(long companyId) throws Exception {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGuestPermissions(true);
		serviceContext.setAddGroupPermissions(true);

		Group group = _groupLocalService.getCompanyGroup(companyId);

		serviceContext.setScopeGroupId(group.getGroupId());

		long defaultUserId = _userLocalService.getDefaultUserId(companyId);

		serviceContext.setUserId(defaultUserId);

		Locale locale = _portal.getSiteDefaultLocale(group.getGroupId());

		Map<String, Field[]> fields = RawMetadataProcessorUtil.getFields();

		for (Map.Entry<String, Field[]> entry : fields.entrySet()) {
			String name = entry.getKey();

			DDMForm ddmForm = _buildDDMForm(entry.getValue(), locale);

			DDMStructure ddmStructure =
				_ddmStructureLocalService.fetchStructure(
					group.getGroupId(),
					_portal.getClassNameId(RawMetadataProcessor.class), name);

			if (ddmStructure != null) {
				String definition = _serializeJSONDDMForm(ddmForm);

				if (!definition.equals(ddmStructure.getDefinition())) {
					ddmStructure.setDDMForm(ddmForm);

					_ddmStructureLocalService.updateDDMStructure(ddmStructure);
				}
			}
			else {
				Map<Locale, String> nameMap = HashMapBuilder.put(
					locale, name
				).build();

				Map<Locale, String> descriptionMap = HashMapBuilder.put(
					locale, name
				).build();

				DDMFormLayout ddmFormLayout = _ddm.getDefaultDDMFormLayout(
					ddmForm);

				_ddmStructureLocalService.addStructure(
					defaultUserId, group.getGroupId(),
					DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
					_portal.getClassNameId(RawMetadataProcessor.class), name,
					nameMap, descriptionMap, ddmForm, ddmFormLayout,
					StorageType.JSON.toString(),
					DDMStructureConstants.TYPE_DEFAULT, serviceContext);
			}
		}
	}

	@Reference(unbind = "-")
	protected void setDDM(DDM ddm) {
		_ddm = ddm;
	}

	@Reference(unbind = "-")
	protected void setDDMStructureLocalService(
		DDMStructureLocalService ddmStructureLocalService) {

		_ddmStructureLocalService = ddmStructureLocalService;
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private DDMForm _buildDDMForm(Field[] fields, Locale locale) {
		DDMForm ddmForm = new DDMForm();

		ddmForm.setAvailableLocales(Collections.singleton(locale));
		ddmForm.setDefaultLocale(locale);

		List<DDMFormField> ddmFormFields = new ArrayList<>();

		for (Field field : fields) {
			Class<?> fieldClass = field.getDeclaringClass();

			DDMFormField ddmFormField = new DDMFormField(
				StringBundler.concat(
					fieldClass.getSimpleName(), StringPool.UNDERLINE,
					field.getName()),
				"text");

			ddmFormField.setDataType("string");
			ddmFormField.setIndexType("text");
			ddmFormField.setLocalizable(false);
			ddmFormField.setMultiple(false);
			ddmFormField.setReadOnly(false);
			ddmFormField.setRepeatable(false);
			ddmFormField.setRequired(false);
			ddmFormField.setShowLabel(true);

			LocalizedValue label = ddmFormField.getLabel();

			label.addString(
				locale,
				StringBundler.concat(
					"metadata.", fieldClass.getSimpleName(), StringPool.PERIOD,
					field.getName()));
			label.setDefaultLocale(locale);

			LocalizedValue predefinedValue = ddmFormField.getPredefinedValue();

			predefinedValue.addString(locale, StringPool.BLANK);
			predefinedValue.setDefaultLocale(locale);

			LocalizedValue style = ddmFormField.getStyle();

			style.setDefaultLocale(locale);

			LocalizedValue tip = ddmFormField.getTip();

			tip.setDefaultLocale(locale);

			DDMFormFieldOptions ddmFormFieldOptions =
				ddmFormField.getDDMFormFieldOptions();

			ddmFormFieldOptions.setDefaultLocale(locale);

			ddmFormFields.add(ddmFormField);
		}

		ddmForm.setDDMFormFields(ddmFormFields);

		return ddmForm;
	}

	private String _serializeJSONDDMForm(DDMForm ddmForm) {
		DDMFormSerializerSerializeRequest.Builder builder =
			DDMFormSerializerSerializeRequest.Builder.newBuilder(ddmForm);

		DDMFormSerializerSerializeResponse ddmFormSerializerSerializeResponse =
			_jsonDDMFormSerializer.serialize(builder.build());

		return ddmFormSerializerSerializeResponse.getContent();
	}

	private DDM _ddm;
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference
	private DefaultDDMStructureHelper _defaultDDMStructureHelper;

	private volatile DLConfiguration _dlConfiguration;
	private GroupLocalService _groupLocalService;

	@Reference(target = "(ddm.form.serializer.type=json)")
	private DDMFormSerializer _jsonDDMFormSerializer;

	@Reference
	private Portal _portal;

	private UserLocalService _userLocalService;

}