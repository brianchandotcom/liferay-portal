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

package com.liferay.portal.events;

import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.metadata.RawMetadataProcessorUtil;
import com.liferay.portal.kernel.upgrade.util.UpgradeProcessUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.UnsecureSAXReaderUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryTypeException;
import com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata;
import com.liferay.portlet.documentlibrary.model.DLFileEntryTypeConstants;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.RawMetadataProcessor;
import com.liferay.portlet.dynamicdatamapping.io.DDMFormXSDDeserializerUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormLayout;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.storage.StorageType;
import com.liferay.portlet.dynamicdatamapping.util.DDMUtil;
import com.liferay.portlet.dynamicdatamapping.util.DefaultDDMStructureUtil;

import java.io.StringReader;

import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Sergio González
 * @author Miguel Pastor
 * @author Roberto Díaz
 */
public class AddDefaultDocumentLibraryStructuresAction extends SimpleAction {

	@Override
	public void run(String[] ids) throws ActionException {
		try {
			doRun(GetterUtil.getLong(ids[0]));
		}
		catch (Exception e) {
			throw new ActionException(e);
		}
	}

	protected void addDLFileEntryType(
			long userId, long groupId, String languageKey,
			String dlFileEntryTypeKey, List<String> ddmStructureNames,
			ServiceContext serviceContext)
		throws Exception {

		List<Long> ddmStructureIds = new ArrayList<>();

		for (String ddmStructureName : ddmStructureNames) {
			String ddmStructureKey = ddmStructureName;

			DDMStructure ddmStructure =
				DDMStructureLocalServiceUtil.fetchStructure(
					groupId,
					PortalUtil.getClassNameId(DLFileEntryMetadata.class),
					ddmStructureKey);

			if (ddmStructure == null) {
				continue;
			}

			ddmStructureIds.add(ddmStructure.getStructureId());
		}

		Locale locale = PortalUtil.getSiteDefaultLocale(groupId);

		String definition =
			DefaultDDMStructureUtil.getDynamicDDMStructureDefinition(
				AddDefaultDocumentLibraryStructuresAction.class.
					getClassLoader(),
				_STRUCTURES_FILE_PATH, languageKey, locale);

		DDMForm ddmForm = DDMFormXSDDeserializerUtil.deserialize(definition);

		serviceContext.setAttribute("ddmForm", ddmForm);

		try {
			DLFileEntryTypeLocalServiceUtil.getFileEntryType(
				groupId, dlFileEntryTypeKey);
		}
		catch (NoSuchFileEntryTypeException nsfete) {
			Map<Locale, String> localizationMap = getLocalizationMap(
				languageKey);

			DLFileEntryTypeLocalServiceUtil.addFileEntryType(
				userId, groupId, dlFileEntryTypeKey, localizationMap,
				localizationMap,
				ArrayUtil.toArray(
					ddmStructureIds.toArray(new Long[ddmStructureIds.size()])),
				serviceContext);
		}
	}

	protected void addDLFileEntryTypes(
			long userId, long groupId, ServiceContext serviceContext)
		throws Exception {

		List<String> ddmStructureNames = new ArrayList<>();

		addDLFileEntryType(
			userId, groupId, DLFileEntryTypeConstants.NAME_CONTRACT,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_KEY_CONTRACT,
			ddmStructureNames, serviceContext);

		ddmStructureNames.clear();

		ddmStructureNames.add("Marketing Campaign Theme Metadata");

		addDLFileEntryType(
			userId, groupId, DLFileEntryTypeConstants.NAME_MARKETING_BANNER,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_KEY_MARKETING_BANNER,
			ddmStructureNames, serviceContext);

		ddmStructureNames.clear();

		ddmStructureNames.add("Learning Module Metadata");

		addDLFileEntryType(
			userId, groupId, DLFileEntryTypeConstants.NAME_ONLINE_TRAINING,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_KEY_ONLINE_TRAINING,
			ddmStructureNames, serviceContext);

		ddmStructureNames.clear();

		ddmStructureNames.add("Meeting Metadata");

		addDLFileEntryType(
			userId, groupId, DLFileEntryTypeConstants.NAME_SALES_PRESENTATION,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_KEY_SALES_PRESENTATION,
			ddmStructureNames, serviceContext);

		if (UpgradeProcessUtil.isCreateIGImageDocumentType()) {
			addDLFileEntryType(
				userId, groupId, DLFileEntryTypeConstants.NAME_IG_IMAGE,
				DLFileEntryTypeConstants.FILE_ENTRY_TYPE_KEY_IG_IMAGE,
				ddmStructureNames, serviceContext);
		}
	}

	protected void addDLRawMetadataStructures(
			long userId, long groupId, ServiceContext serviceContext)
		throws Exception {

		Locale locale = PortalUtil.getSiteDefaultLocale(groupId);

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
				DDMStructureLocalServiceUtil.fetchStructure(
					groupId,
					PortalUtil.getClassNameId(RawMetadataProcessor.class),
					name);

			DDMForm ddmForm = DDMFormXSDDeserializerUtil.deserialize(
				structureElementRootXML);

			if (ddmStructure != null) {
				ddmStructure.setDDMForm(ddmForm);

				DDMStructureLocalServiceUtil.updateDDMStructure(ddmStructure);
			}
			else {
				Map<Locale, String> nameMap = new HashMap<>();

				nameMap.put(locale, name);

				Map<Locale, String> descriptionMap = new HashMap<>();

				descriptionMap.put(locale, description);

				DDMFormLayout ddmFormLayout = DDMUtil.getDefaultDDMFormLayout(
					ddmForm);

				DDMStructureLocalServiceUtil.addStructure(
					userId, groupId,
					DDMStructureConstants.DEFAULT_PARENT_STRUCTURE_ID,
					PortalUtil.getClassNameId(RawMetadataProcessor.class), name,
					nameMap, descriptionMap, ddmForm, ddmFormLayout,
					StorageType.JSON.toString(),
					DDMStructureConstants.TYPE_DEFAULT, serviceContext);
			}
		}
	}

	protected String buildDLRawMetadataElementXML(Field field, Locale locale) {
		StringBundler sb = new StringBundler(15);

		sb.append("<dynamic-element dataType=\"string\" indexType=\"text\" ");
		sb.append("name=\"");

		Class<?> fieldClass = field.getDeclaringClass();

		sb.append(fieldClass.getSimpleName());
		sb.append(StringPool.UNDERLINE);
		sb.append(field.getName());
		sb.append("\" required=\"false\" showLabel=\"true\" type=\"text\">");
		sb.append("<meta-data locale=\"");
		sb.append(locale);
		sb.append("\">");
		sb.append("<entry name=\"label\"><![CDATA[metadata.");
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
		sb.append("]]></name>");
		sb.append("<description><![CDATA[");
		sb.append(name);
		sb.append("]]></description>");
		sb.append("<root available-locales=\"");
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

		for (String key : fields.keySet()) {
			sb.append(
				buildDLRawMetadataStructureXML(key, fields.get(key), locale));
		}

		sb.append("</root>");

		return sb.toString();
	}

	protected void doRun(long companyId) throws Exception {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGuestPermissions(true);
		serviceContext.setAddGroupPermissions(true);

		Group group = GroupLocalServiceUtil.getCompanyGroup(companyId);

		serviceContext.setScopeGroupId(group.getGroupId());

		long defaultUserId = UserLocalServiceUtil.getDefaultUserId(companyId);

		serviceContext.setUserId(defaultUserId);

		DefaultDDMStructureUtil.addDDMStructures(
			defaultUserId, group.getGroupId(),
			PortalUtil.getClassNameId(DLFileEntryMetadata.class),
			AddDefaultDocumentLibraryStructuresAction.class.getClassLoader(),
			_STRUCTURES_FILE_PATH, serviceContext);
		addDLFileEntryTypes(defaultUserId, group.getGroupId(), serviceContext);
		addDLRawMetadataStructures(
			defaultUserId, group.getGroupId(), serviceContext);
	}

	protected Map<Locale, String> getLocalizationMap(String content) {
		Map<Locale, String> localizationMap = new HashMap<>();

		Locale defaultLocale = LocaleUtil.getDefault();

		String defaultValue = LanguageUtil.get(defaultLocale, content);

		for (Locale locale : LanguageUtil.getSupportedLocales()) {
			String value = LanguageUtil.get(locale, content);

			if (!locale.equals(defaultLocale) && value.equals(defaultValue)) {
				continue;
			}

			localizationMap.put(locale, value);
		}

		return localizationMap;
	}

	private static final String _STRUCTURES_FILE_PATH =
		"com/liferay/portal/events/dependencies/" +
			"document-library-structures.xml";

}