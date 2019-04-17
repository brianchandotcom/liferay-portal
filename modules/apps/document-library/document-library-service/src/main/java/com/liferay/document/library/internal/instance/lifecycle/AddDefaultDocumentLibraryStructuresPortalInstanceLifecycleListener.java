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
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.document.library.kernel.util.RawMetadataProcessor;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializerSerializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializerSerializeResponse;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializerTracker;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureConstants;
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
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.UnsecureSAXReaderUtil;

import java.io.StringReader;

import java.lang.reflect.Field;

import java.util.HashMap;
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
	immediate = true, service = PortalInstanceLifecycleListener.class
)
public class AddDefaultDocumentLibraryStructuresPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		if (!_dlConfiguration.addDefaultStructures()) {
			return;
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGuestPermissions(true);
		serviceContext.setAddGroupPermissions(true);

		Group group = _groupLocalService.getCompanyGroup(
			company.getCompanyId());

		serviceContext.setScopeGroupId(group.getGroupId());

		long defaultUserId = _userLocalService.getDefaultUserId(
			company.getCompanyId());

		serviceContext.setUserId(defaultUserId);

		_defaultDDMStructureHelper.addDDMStructures(
			defaultUserId, group.getGroupId(),
			_portal.getClassNameId(DLFileEntryMetadata.class), getClassLoader(),
			"com/liferay/document/library/events/dependencies" +
				"/document-library-structures.xml",
			serviceContext);

		_dlFileEntryTypeLocalService.getBasicDocumentDLFileEntryType();

		addDLRawMetadataStructures(
			defaultUserId, group.getGroupId(), serviceContext);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_dlConfiguration = ConfigurableUtil.createConfigurable(
			DLConfiguration.class, properties);
	}

	protected void addDLRawMetadataStructures(
			long userId, long groupId, ServiceContext serviceContext)
		throws Exception {

		Locale locale = _portal.getSiteDefaultLocale(groupId);

		String xsd = buildDLRawMetadataXML(
			RawMetadataProcessorUtil.getFields(), locale);

		Document document = UnsecureSAXReaderUtil.read(new StringReader(xsd));

		Element rootElement = document.getRootElement();

		List<Element> structureElements = rootElement.elements("structure");

		for (Element structureElement : structureElements) {
			String name = structureElement.elementText("name");
			String description = structureElement.elementText("description");

			Element structureElementRootElement = structureElement.element(
				"root");

			String structureElementRootXML =
				structureElementRootElement.asXML();

			DDMStructure ddmStructure =
				_ddmStructureLocalService.fetchStructure(
					groupId, _portal.getClassNameId(RawMetadataProcessor.class),
					name);

			DDMFormDeserializerDeserializeRequest.Builder builder =
				DDMFormDeserializerDeserializeRequest.Builder.newBuilder(
					structureElementRootXML);

			DDMFormDeserializerDeserializeResponse
				ddmFormDeserializerDeserializeResponse =
					_ddmFormDeserializer.deserialize(builder.build());

			DDMForm ddmForm =
				ddmFormDeserializerDeserializeResponse.getDDMForm();

			if (ddmStructure != null) {
				String definition = _serializeJSONDDMForm(ddmForm);

				if (!definition.equals(ddmStructure.getDefinition())) {
					ddmStructure.setDDMForm(ddmForm);

					_ddmStructureLocalService.updateDDMStructure(ddmStructure);
				}
			}
			else {
				Map<Locale, String> nameMap = new HashMap<>();

				nameMap.put(locale, name);

				Map<Locale, String> descriptionMap = new HashMap<>();

				descriptionMap.put(locale, description);

				DDMFormLayout ddmFormLayout = _ddm.getDefaultDDMFormLayout(
					ddmForm);

				_ddmStructureLocalService.addStructure(
					userId, groupId,
					DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
					_portal.getClassNameId(RawMetadataProcessor.class), name,
					nameMap, descriptionMap, ddmForm, ddmFormLayout,
					StorageType.JSON.toString(),
					DDMStructureConstants.TYPE_DEFAULT, serviceContext);
			}
		}
	}

	protected String buildDLRawMetadataElementXML(Field field, Locale locale) {
		StringBundler sb = new StringBundler(14);

		sb.append("<dynamic-element dataType=\"string\" indexType=\"text\" ");
		sb.append("name=\"");

		Class<?> fieldClass = field.getDeclaringClass();

		sb.append(fieldClass.getSimpleName());

		sb.append(StringPool.UNDERLINE);
		sb.append(field.getName());
		sb.append("\" localizable=\"false\" required=\"false\" ");
		sb.append("showLabel=\"true\" type=\"text\"><meta-data locale=\"");
		sb.append(locale);
		sb.append("\"><entry name=\"label\"><![CDATA[metadata.");
		sb.append(fieldClass.getSimpleName());
		sb.append(StringPool.PERIOD);
		sb.append(field.getName());
		sb.append("]]></entry><entry name=\"predefinedValue\">");
		sb.append("<![CDATA[]]></entry></meta-data></dynamic-element>");

		return sb.toString();
	}

	protected String buildDLRawMetadataStructureXML(
		String name, Field[] fields, Locale locale) {

		StringBundler sb = new StringBundler(12 + fields.length);

		sb.append("<structure><name><![CDATA[");
		sb.append(name);
		sb.append("]]></name><description><![CDATA[");
		sb.append(name);
		sb.append("]]></description><root available-locales=\"");
		sb.append(locale);
		sb.append("\" default-locale=\"");
		sb.append(locale);
		sb.append("\">");

		for (Field field : fields) {
			sb.append(buildDLRawMetadataElementXML(field, locale));
		}

		sb.append("</root></structure>");

		return sb.toString();
	}

	protected String buildDLRawMetadataXML(
		Map<String, Field[]> fields, Locale locale) {

		StringBundler sb = new StringBundler(2 + fields.size());

		sb.append("<?xml version=\"1.0\"?><root>");

		for (Map.Entry<String, Field[]> entry : fields.entrySet()) {
			sb.append(
				buildDLRawMetadataStructureXML(
					entry.getKey(), entry.getValue(), locale));
		}

		sb.append("</root>");

		return sb.toString();
	}

	@Reference(unbind = "-")
	protected void setDDM(DDM ddm) {
		_ddm = ddm;
	}

	@Reference(
		target = "(component.name=com.liferay.dynamic.data.mapping.io.internal.DDMFormXSDDeserializer)",
		unbind = "-"
	)
	protected void setDDMFormDeserializer(
		DDMFormDeserializer ddmFormDeserializer) {

		_ddmFormDeserializer = ddmFormDeserializer;
	}

	@Reference(unbind = "-")
	protected void setDDMStructureLocalService(
		DDMStructureLocalService ddmStructureLocalService) {

		_ddmStructureLocalService = ddmStructureLocalService;
	}

	@Reference(unbind = "-")
	protected void setDefaultDDMStructureHelper(
		DefaultDDMStructureHelper defaultDDMStructureHelper) {

		_defaultDDMStructureHelper = defaultDDMStructureHelper;
	}

	@Reference(unbind = "-")
	protected void setDLFileEntryTypeLocalService(
		DLFileEntryTypeLocalService dlFileEntryTypeLocalService) {

		_dlFileEntryTypeLocalService = dlFileEntryTypeLocalService;
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

	private String _serializeJSONDDMForm(DDMForm ddmForm) {
		DDMFormSerializer ddmFormSerializer =
			_ddmFormSerializerTracker.getDDMFormSerializer("json");

		DDMFormSerializerSerializeRequest.Builder builder =
			DDMFormSerializerSerializeRequest.Builder.newBuilder(ddmForm);

		DDMFormSerializerSerializeResponse ddmFormSerializerSerializeResponse =
			ddmFormSerializer.serialize(builder.build());

		return ddmFormSerializerSerializeResponse.getContent();
	}

	private DDM _ddm;
	private DDMFormDeserializer _ddmFormDeserializer;

	@Reference
	private DDMFormSerializerTracker _ddmFormSerializerTracker;

	private DDMStructureLocalService _ddmStructureLocalService;
	private DefaultDDMStructureHelper _defaultDDMStructureHelper;
	private volatile DLConfiguration _dlConfiguration;
	private DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;
	private GroupLocalService _groupLocalService;

	@Reference
	private Portal _portal;

	private UserLocalService _userLocalService;

}