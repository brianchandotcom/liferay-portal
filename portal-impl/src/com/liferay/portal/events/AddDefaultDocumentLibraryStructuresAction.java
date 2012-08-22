/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.metadata.RawMetadataProcessorUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryTypeException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata;
import com.liferay.portlet.documentlibrary.model.DLFileEntryTypeConstants;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureConstants;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;

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
 */
public class AddDefaultDocumentLibraryStructuresAction
	extends BaseDefaultDDMStructureAction {

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
			long userId, long groupId, String dlFileEntryTypeName,
			String dlFileEntryTypeDescription, String dynamicDDMStructureName,
			List<String> ddmStructureNames, ServiceContext serviceContext)
		throws Exception {

		List<Long> ddmStructureIds = new ArrayList<Long>();

		for (String ddmStructureName : ddmStructureNames) {
			String ddmStructureKey = ddmStructureName;

			DDMStructure ddmStructure =
				DDMStructureLocalServiceUtil.fetchStructure(
					groupId, ddmStructureKey);

			if (ddmStructure == null) {
				continue;
			}

			ddmStructureIds.add(ddmStructure.getStructureId());
		}

		String xsd = getDynamicDDMStructureXSD(
			"document-library-structures.xml", dynamicDDMStructureName);

		serviceContext.setAttribute("xsd", xsd);

		try {
			DLFileEntryTypeLocalServiceUtil.getFileEntryType(
				groupId, dlFileEntryTypeName);
		}
		catch (NoSuchFileEntryTypeException nsfete) {
			DLFileEntryTypeLocalServiceUtil.addFileEntryType(
				userId, groupId, dlFileEntryTypeName,
				dlFileEntryTypeDescription,
				ArrayUtil.toArray(
					ddmStructureIds.toArray(new Long[ddmStructureIds.size()])),
				serviceContext);
		}
	}

	protected void addDLFileEntryTypes(
			long userId, long groupId, ServiceContext serviceContext)
		throws Exception {

		List<String> ddmStructureNames = new ArrayList<String>();

		addDLFileEntryType(
			userId, groupId, DLFileEntryTypeConstants.NAME_CONTRACT,
			"Legal Contracts", DLFileEntryTypeConstants.NAME_CONTRACT,
			ddmStructureNames, serviceContext);

		ddmStructureNames.clear();

		ddmStructureNames.add("Marketing Campaign Theme Metadata");

		addDLFileEntryType(
			userId, groupId, DLFileEntryTypeConstants.NAME_MARKETING_BANNER,
			"Marketing Banner", DLFileEntryTypeConstants.NAME_MARKETING_BANNER,
			ddmStructureNames, serviceContext);

		ddmStructureNames.clear();

		ddmStructureNames.add("Learning Module Metadata");

		addDLFileEntryType(
			userId, groupId, DLFileEntryTypeConstants.NAME_ONLINE_TRAINING,
			"Online Training", DLFileEntryTypeConstants.NAME_ONLINE_TRAINING,
			ddmStructureNames, serviceContext);

		ddmStructureNames.clear();

		ddmStructureNames.add("Meeting Metadata");

		addDLFileEntryType(
			userId, groupId, DLFileEntryTypeConstants.NAME_SALES_PRESENTATION,
			"Sales Presentation",
			DLFileEntryTypeConstants.NAME_SALES_PRESENTATION, ddmStructureNames,
			serviceContext);
	}

	protected void addDLRawMetadataStructures(
		long userId, long groupId, ServiceContext serviceContext)
		throws Exception {

		String xsd = buildDLRawMetadataXML(
			RawMetadataProcessorUtil.getFields());

		Document document = SAXReaderUtil.read(new StringReader(xsd));

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
				DDMStructureLocalServiceUtil.fetchStructure(groupId, name);

			if (ddmStructure != null) {
				ddmStructure.setXsd(structureElementRootXML);

				DDMStructureLocalServiceUtil.updateDDMStructure(ddmStructure);
			}
			else {
				Map<Locale, String> nameMap = new HashMap<Locale, String>();

				nameMap.put(LocaleUtil.getDefault(), name);

				Map<Locale, String> descriptionMap =
					new HashMap<Locale, String>();

				descriptionMap.put(LocaleUtil.getDefault(), description);

				DDMStructureLocalServiceUtil.addStructure(
					userId, groupId,
					PortalUtil.getClassNameId(DLFileEntry.class), name, nameMap,
					descriptionMap, structureElementRootXML, "xml",
					DDMStructureConstants.TYPE_DEFAULT, serviceContext);
			}
		}
	}

	protected String buildDLRawMetadataElementXML(String name, Field field) {
		StringBundler sb = new StringBundler(16);

		sb.append("<dynamic-element dataType=\"string\" name=\"");

		Class<?> fieldClass = field.getDeclaringClass();

		sb.append(fieldClass.getSimpleName());
		sb.append(StringPool.UNDERLINE);
		sb.append(field.getName());
		sb.append("\" type=\"text\">");
		sb.append("<meta-data locale=\"");
		sb.append(LocaleUtil.getDefault());
		sb.append("\">");
		sb.append("<entry name=\"label\"><![CDATA[metadata.");
		sb.append(fieldClass.getSimpleName());
		sb.append(StringPool.PERIOD);
		sb.append(field.getName());
		sb.append("]]></entry><entry name=\"predefinedValue\">");
		sb.append("<![CDATA[]]></entry><entry name=\"required\">");
		sb.append("<![CDATA[false]]></entry><entry name=\"showLabel\">");
		sb.append("<![CDATA[true]]></entry></meta-data></dynamic-element>");

		return sb.toString();
	}

	protected String buildDLRawMetadataStructureXML(
		String name, Field[] fields) {

		StringBundler sb = new StringBundler(12 + fields.length);

		sb.append("<structure><name><![CDATA[");
		sb.append(name);
		sb.append("]]></name>");
		sb.append("<description><![CDATA[");
		sb.append(name);
		sb.append("]]></description>");
		sb.append("<root available-locales=\"");
		sb.append(LocaleUtil.getDefault());
		sb.append("\" default-locale=\"");
		sb.append(LocaleUtil.getDefault());
		sb.append("\">");

		for (Field field : fields) {
			sb.append(buildDLRawMetadataElementXML(name, field));
		}

		sb.append("</root></structure>");

		return sb.toString();
	}

	protected String buildDLRawMetadataXML(Map<String, Field[]> fields) {
		StringBundler sb = new StringBundler(2 + fields.size());

		sb.append("<?xml version=\"1.0\"?><root>");

		for (String key : fields.keySet()) {
			sb.append(buildDLRawMetadataStructureXML(key, fields.get(key)));
		}

		sb.append("</root>");

		return sb.toString();
	}

	protected void doRun(long companyId) throws Exception {
		ServiceContext serviceContext = new ServiceContext();

		Group group = GroupLocalServiceUtil.getCompanyGroup(companyId);

		serviceContext.setScopeGroupId(group.getGroupId());

		long defaultUserId = UserLocalServiceUtil.getDefaultUserId(companyId);

		serviceContext.setUserId(defaultUserId);

		addDDMStructures(
			defaultUserId, group.getGroupId(),
			PortalUtil.getClassNameId(DLFileEntryMetadata.class),
			"document-library-structures.xml", serviceContext);
		addDLFileEntryTypes(defaultUserId, group.getGroupId(), serviceContext);
		addDLRawMetadataStructures(
			defaultUserId, group.getGroupId(), serviceContext);
	}

}