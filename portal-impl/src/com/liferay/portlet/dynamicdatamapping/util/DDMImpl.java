/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatamapping.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.upload.UploadRequest;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeFormatter;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.storage.Field;
import com.liferay.portlet.dynamicdatamapping.storage.FieldConstants;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;
import com.liferay.portlet.dynamicdatamapping.util.comparator.StructureIdComparator;
import com.liferay.portlet.dynamicdatamapping.util.comparator.StructureModifiedDateComparator;
import com.liferay.portlet.dynamicdatamapping.util.comparator.TemplateIdComparator;
import com.liferay.portlet.dynamicdatamapping.util.comparator.TemplateModifiedDateComparator;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eduardo Lundgren
 * @author Brian Wing Shun Chan
 * @author Eduardo Garcia
 * @author Marcellus Tavares
 */
@DoPrivileged
public class DDMImpl implements DDM {

	public static final String FIELDS_DISPLAY_NAME = "_fieldsDisplay";

	public static final String INSTANCE_SEPARATOR = "_INSTANCE_";

	public static final String TYPE_CHECKBOX = "checkbox";

	public static final String TYPE_DDM_DATE = "ddm-date";

	public static final String TYPE_DDM_DOCUMENTLIBRARY = "ddm-documentlibrary";

	public static final String TYPE_DDM_LINK_TO_PAGE = "ddm-link-to-page";

	public static final String TYPE_RADIO = "radio";

	public static final String TYPE_SELECT = "select";

	@Override
	public DDMDisplay getDDMDisplay(ServiceContext serviceContext) {
		String refererPortletName = (String)serviceContext.getAttribute(
			"refererPortletName");

		if (refererPortletName == null) {
			refererPortletName = serviceContext.getPortletId();
		}

		return DDMDisplayRegistryUtil.getDDMDisplay(refererPortletName);
	}

	@Override
	public Serializable getDisplayFieldValue(
			ThemeDisplay themeDisplay, Serializable fieldValue, String type)
		throws Exception {

		if (fieldValue instanceof Date) {
			Date valueDate = (Date)fieldValue;

			DateFormat dateFormat = DateFormatFactoryUtil.getDate(
				themeDisplay.getLocale());

			fieldValue = dateFormat.format(valueDate);
		}
		else if (type.equals(DDMImpl.TYPE_CHECKBOX)) {
			Boolean valueBoolean = (Boolean)fieldValue;

			if (valueBoolean) {
				fieldValue = LanguageUtil.get(themeDisplay.getLocale(), "yes");
			}
			else {
				fieldValue = LanguageUtil.get(themeDisplay.getLocale(), "no");
			}
		}
		else if (type.equals(DDMImpl.TYPE_DDM_DOCUMENTLIBRARY)) {
			if (Validator.isNull(fieldValue)) {
				return StringPool.BLANK;
			}

			String valueString = String.valueOf(fieldValue);

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				valueString);

			String uuid = jsonObject.getString("uuid");
			long groupId = jsonObject.getLong("groupId");

			FileEntry fileEntry =
				DLAppLocalServiceUtil.getFileEntryByUuidAndGroupId(
					uuid, groupId);

			fieldValue = DLUtil.getPreviewURL(
				fileEntry, fileEntry.getFileVersion(), null, StringPool.BLANK,
				false, true);
		}
		else if (type.equals(DDMImpl.TYPE_DDM_LINK_TO_PAGE)) {
			if (Validator.isNull(fieldValue)) {
				return StringPool.BLANK;
			}

			String valueString = String.valueOf(fieldValue);

			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				valueString);

			long groupId = jsonObject.getLong("groupId");
			boolean privateLayout = jsonObject.getBoolean("privateLayout");
			long layoutId = jsonObject.getLong("layoutId");

			Layout layout = LayoutLocalServiceUtil.getLayout(
				groupId, privateLayout, layoutId);

			fieldValue = PortalUtil.getLayoutFriendlyURL(layout, themeDisplay);
		}
		else if (type.equals(DDMImpl.TYPE_RADIO) ||
				 type.equals(DDMImpl.TYPE_SELECT)) {

			String valueString = String.valueOf(fieldValue);

			JSONArray jsonArray = JSONFactoryUtil.createJSONArray(valueString);

			String[] stringArray = ArrayUtil.toStringArray(jsonArray);

			fieldValue = stringArray[0];
		}

		return fieldValue;
	}

	@Override
	public Fields getFields(
			long ddmStructureId, long ddmTemplateId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return getFields(
			ddmStructureId, ddmTemplateId, StringPool.BLANK, serviceContext);
	}

	@Override
	public Fields getFields(
			long ddmStructureId, long ddmTemplateId, String fieldNamespace,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		DDMStructure ddmStructure = getDDMStructure(
			ddmStructureId, ddmTemplateId);

		Set<String> fieldNames = ddmStructure.getFieldNames();

		Fields fields = new Fields();

		for (String fieldName : fieldNames) {
			List<Serializable> fieldValues = getFieldValues(
				ddmStructure, fieldName, fieldNamespace, serviceContext);

			if ((fieldValues == null) || fieldValues.isEmpty()) {
				continue;
			}

			Field field = createField(
				ddmStructure, fieldName, fieldValues, serviceContext);

			fields.put(field);
		}

		return fields;
	}

	@Override
	public Fields getFields(long ddmStructureId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		return getFields(ddmStructureId, 0, serviceContext);
	}

	@Override
	public Fields getFields(
			long ddmStructureId, String fieldNamespace,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return getFields(ddmStructureId, 0, fieldNamespace, serviceContext);
	}

	@Override
	public String[] getFieldsDisplayValues(Field fieldsDisplayField)
		throws Exception {

		DDMStructure ddmStructure = fieldsDisplayField.getDDMStructure();

		List<String> fieldsDisplayValues = new ArrayList<String>();

		String[] values = StringUtil.split(
			(String)fieldsDisplayField.getValue());

		for (String value : values) {
			String fieldName = StringUtil.extractFirst(
				value, DDMImpl.INSTANCE_SEPARATOR);

			if (ddmStructure.hasField(fieldName)) {
				fieldsDisplayValues.add(fieldName);
			}
		}

		return fieldsDisplayValues.toArray(
			new String[fieldsDisplayValues.size()]);
	}

	@Override
	public Serializable getIndexedFieldValue(
			Serializable fieldValue, String type)
		throws Exception {

		if (fieldValue instanceof Date) {
			Date valueDate = (Date)fieldValue;

			DateFormat dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
				"yyyyMMddHHmmss");

			fieldValue = dateFormat.format(valueDate);
		}
		else if (type.equals(DDMImpl.TYPE_RADIO) ||
				 type.equals(DDMImpl.TYPE_SELECT)) {

			String valueString = (String)fieldValue;

			JSONArray jsonArray = JSONFactoryUtil.createJSONArray(valueString);

			String[] stringArray = ArrayUtil.toStringArray(jsonArray);

			fieldValue = stringArray[0];
		}

		return fieldValue;
	}

	@Override
	public OrderByComparator getStructureOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator orderByComparator = null;

		if (orderByCol.equals("id")) {
			orderByComparator = new StructureIdComparator(orderByAsc);
		}
		else if (orderByCol.equals("modified-date")) {
			orderByComparator = new StructureModifiedDateComparator(orderByAsc);
		}

		return orderByComparator;
	}

	@Override
	public OrderByComparator getTemplateOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator orderByComparator = null;

		if (orderByCol.equals("id")) {
			orderByComparator = new TemplateIdComparator(orderByAsc);
		}
		else if (orderByCol.equals("modified-date")) {
			orderByComparator = new TemplateModifiedDateComparator(orderByAsc);
		}

		return orderByComparator;
	}

	@Override
	public Fields mergeFields(Fields newFields, Fields existingFields) {
		Iterator<Field> itr = newFields.iterator(true);

		while (itr.hasNext()) {
			Field newField = itr.next();

			Field existingField = existingFields.get(newField.getName());

			if (existingField == null) {
				existingFields.put(newField);
			}
			else {
				for (Locale locale : newField.getAvailableLocales()) {
					existingField.setValues(locale, newField.getValues(locale));
				}

				existingField.setDefaultLocale(newField.getDefaultLocale());
			}

		}

		return existingFields;
	}

	protected Field createField(
			DDMStructure ddmStructure, String fieldName,
			List<Serializable> fieldValues, ServiceContext serviceContext)
		throws PortalException, SystemException {

		Field field = new Field();

		field.setDDMStructureId(ddmStructure.getStructureId());

		String languageId = GetterUtil.getString(
			serviceContext.getAttribute("languageId"),
			serviceContext.getLanguageId());

		Locale locale = LocaleUtil.fromLanguageId(languageId);

		String defaultLanguageId = GetterUtil.getString(
			serviceContext.getAttribute("defaultLanguageId"));

		Locale defaultLocale = LocaleUtil.fromLanguageId(defaultLanguageId);

		if (ddmStructure.isFieldPrivate(fieldName)) {
			locale = LocaleUtil.getDefault();

			defaultLocale = LocaleUtil.getDefault();
		}

		field.setDefaultLocale(defaultLocale);

		field.setName(fieldName);
		field.setValues(locale, fieldValues);

		return field;
	}

	protected DDMStructure getDDMStructure(
			long ddmStructureId, long ddmTemplateId)
		throws PortalException, SystemException {

		DDMStructure ddmStructure = DDMStructureLocalServiceUtil.getStructure(
			ddmStructureId);

		try {
			DDMTemplate ddmTemplate = DDMTemplateLocalServiceUtil.getTemplate(
				ddmTemplateId);

			// Clone ddmStructure to make sure changes are never persisted

			ddmStructure = (DDMStructure)ddmStructure.clone();

			ddmStructure.setXsd(ddmTemplate.getScript());
		}
		catch (NoSuchTemplateException nste) {
		}

		return ddmStructure;
	}

	protected List<String> getFieldNames(
		String fieldNamespace, String fieldName,
		ServiceContext serviceContext) {

		String[] fieldsDisplayValues = StringUtil.split(
			(String)serviceContext.getAttribute(
				fieldNamespace + FIELDS_DISPLAY_NAME));

		List<String> privateFieldNames = ListUtil.fromArray(
			PropsValues.DYNAMIC_DATA_MAPPING_STRUCTURE_PRIVATE_FIELD_NAMES);

		List<String> fieldNames = new ArrayList<String>();

		if ((fieldsDisplayValues.length == 0) ||
			privateFieldNames.contains(fieldName)) {

			fieldNames.add(fieldNamespace + fieldName);
		}
		else {
			for (String namespacedFieldName : fieldsDisplayValues) {
				String fieldNameValue = StringUtil.extractFirst(
					namespacedFieldName, INSTANCE_SEPARATOR);

				if (fieldNameValue.equals(fieldName)) {
					fieldNames.add(fieldNamespace + namespacedFieldName);
				}
			}
		}

		return fieldNames;
	}

	protected List<Serializable> getFieldValues(
			DDMStructure ddmStructure, String fieldName, String fieldNamespace,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		String fieldDataType = ddmStructure.getFieldDataType(fieldName);
		String fieldType = ddmStructure.getFieldType(fieldName);

		List<String> fieldNames = getFieldNames(
			fieldNamespace, fieldName, serviceContext);

		List<Serializable> fieldValues = new ArrayList<Serializable>(
			fieldNames.size());

		for (String fieldNameValue : fieldNames) {
			Serializable fieldValue = serviceContext.getAttribute(
				fieldNameValue);

			if (fieldDataType.equals(FieldConstants.DATE)) {
				int fieldValueMonth = GetterUtil.getInteger(
					serviceContext.getAttribute(fieldNameValue + "Month"));
				int fieldValueDay = GetterUtil.getInteger(
					serviceContext.getAttribute(fieldNameValue + "Day"));
				int fieldValueYear = GetterUtil.getInteger(
					serviceContext.getAttribute(fieldNameValue + "Year"));

				Date fieldValueDate = PortalUtil.getDate(
					fieldValueMonth, fieldValueDay, fieldValueYear);

				if (fieldValueDate != null) {
					fieldValue = String.valueOf(fieldValueDate.getTime());
				}
			}
			else if (fieldDataType.equals(FieldConstants.IMAGE) &&
					 Validator.isNull(fieldValue)) {

				HttpServletRequest request = serviceContext.getRequest();

				if (!(request instanceof UploadRequest)) {
					return null;
				}

				UploadRequest uploadRequest = (UploadRequest)request;

				File file = uploadRequest.getFile(fieldNameValue);

				try {
					byte[] bytes = FileUtil.getBytes(file);

					if ((bytes != null) && (bytes.length > 0)) {
						fieldValue = UnicodeFormatter.bytesToHex(bytes);
					}
					else {
						fieldValue = "update";
					}
				}
				catch (IOException ioe) {
					return null;
				}
			}

			if (fieldValue == null) {
				return null;
			}

			if (DDMImpl.TYPE_RADIO.equals(fieldType) ||
				DDMImpl.TYPE_SELECT.equals(fieldType)) {

				if (fieldValue instanceof String) {
					fieldValue = new String[] {String.valueOf(fieldValue)};
				}

				fieldValue = JSONFactoryUtil.serialize(fieldValue);
			}

			Serializable fieldValueSerializable =
				FieldConstants.getSerializable(
					fieldDataType, GetterUtil.getString(fieldValue));

			fieldValues.add(fieldValueSerializable);
		}

		return fieldValues;
	}

}