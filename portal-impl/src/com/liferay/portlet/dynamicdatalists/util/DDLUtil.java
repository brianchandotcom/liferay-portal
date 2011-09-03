/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatalists.util;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.templateparser.Transformer;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.dynamicdatalists.model.DDLRecord;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.storage.Field;
import com.liferay.portlet.dynamicdatamapping.storage.FieldConstants;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;
import com.liferay.portlet.dynamicdatamapping.util.DDMXMLUtil;
import com.liferay.portlet.journal.util.JournalUtil;
import com.liferay.util.portlet.PortletRequestUtil;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Marcelllus Tavares
 * @author Eduardo Lundgren
 */
public class DDLUtil {

	public static void addAllReservedEls(
		Element rootElement, Map<String, String> tokens,
		DDLRecordSet recordSet) {

		JournalUtil.addReservedEl(
			rootElement, tokens, DDLConstants.RESERVED_RECORD_SET_ID,
			String.valueOf(recordSet.getRecordSetId()));

		JournalUtil.addReservedEl(
			rootElement, tokens, DDLConstants.RESERVED_RECORD_SET_NAME,
			recordSet.getName());

		JournalUtil.addReservedEl(
			rootElement, tokens, DDLConstants.RESERVED_RECORD_SET_DESCRIPTION,
			recordSet.getDescription());

		JournalUtil.addReservedEl(
			rootElement, tokens, DDLConstants.RESERVED_DDM_STRUCTURE_ID,
			String.valueOf(recordSet.getDDMStructureId()));
	}

	public static JSONObject getRecordJSONObject(DDLRecord record)
		throws Exception {

		DDLRecordSet recordSet = record.getRecordSet();

		DDMStructure ddmStructure = recordSet.getDDMStructure();

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		for (String fieldName : ddmStructure.getFieldNames()) {
			jsonObject.put(fieldName, StringPool.BLANK);
		}

		jsonObject.put("displayIndex", record.getDisplayIndex());
		jsonObject.put("recordId", record.getRecordId());

		Fields fields = record.getFields();

		Iterator<Field> itr = fields.iterator();

		while (itr.hasNext()) {
			Field field = itr.next();

			String fieldName = field.getName();
			String fieldValue = String.valueOf(field.getValue());

			if (ddmStructure.getFieldDisplayChildLabelAsValue(fieldName)) {
				Map<String, String> childFields = ddmStructure.getFields(
					fieldName, FieldConstants.VALUE, fieldValue);

				if (childFields != null) {
					fieldValue = childFields.get(FieldConstants.LABEL);
				}
			}

			jsonObject.put(fieldName, fieldValue);
		}

		return jsonObject;
	}

	public static JSONArray getRecordSetJSONArray(DDLRecordSet recordSet)
		throws Exception {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		DDMStructure ddmStructure = recordSet.getDDMStructure();

		Map<String, Map<String, String>> fieldsMap =
			ddmStructure.getFieldsMap();

		for (Map<String, String> fields : fieldsMap.values()) {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

			String dataType = fields.get(FieldConstants.DATA_TYPE);

			jsonObject.put("dataType", dataType);

			boolean editable = GetterUtil.getBoolean(
				fields.get(FieldConstants.EDITABLE), true);

			jsonObject.put("editable", editable);

			String label = fields.get(FieldConstants.LABEL);

			jsonObject.put("label", label);

			String name = fields.get(FieldConstants.NAME);

			jsonObject.put("name", name);

			boolean required = GetterUtil.getBoolean(
				fields.get(FieldConstants.REQUIRED));

			jsonObject.put("required", required);

			boolean sortable = GetterUtil.getBoolean(
				fields.get(FieldConstants.SORTABLE), true);

			jsonObject.put("sortable", sortable);

			String type = fields.get(FieldConstants.TYPE);

			jsonObject.put("type", type);

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	public static JSONArray getRecordsJSONArray(DDLRecordSet recordSet)
		throws Exception {

		return getRecordsJSONArray(recordSet.getRecords());
	}

	public static JSONArray getRecordsJSONArray(List<DDLRecord> records)
		throws Exception {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (DDLRecord record : records) {
			JSONObject jsonObject = getRecordJSONObject(record);

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	public static String getTemplateContent(
			long ddmTemplateId, DDLRecordSet recordSet,
			ThemeDisplay themeDisplay, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		String viewMode = ParamUtil.getString(renderRequest, "viewMode");

		String languageId = LanguageUtil.getLanguageId(renderRequest);

		String xmlRequest = PortletRequestUtil.toXML(
			renderRequest, renderResponse);

		if (Validator.isNull(xmlRequest)) {
			xmlRequest = "<request />";
		}

		Map<String, String> tokens = JournalUtil.getTokens(
			recordSet.getGroupId(), themeDisplay, xmlRequest);

		String xml = StringPool.BLANK;

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("root");

		Document requestDocument = SAXReaderUtil.read(xmlRequest);

		rootElement.add(requestDocument.getRootElement().createCopy());

		addAllReservedEls(rootElement, tokens, recordSet);

		xml = DDMXMLUtil.formatXML(document);

		DDMTemplate template = DDMTemplateLocalServiceUtil.getTemplate(
			ddmTemplateId);

		return _transformer.transform(
			themeDisplay, tokens, viewMode, languageId, xml,
			template.getScript(), template.getLanguage());
	}

	private static Transformer _transformer = new DDLTransformer();

}