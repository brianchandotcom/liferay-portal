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

package com.liferay.structured.content.apio.internal.util;

import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializerSerializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializerSerializeResponse;
import com.liferay.dynamic.data.mapping.io.DDMFormValuesSerializerTracker;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.UnlocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.journal.util.JournalConverter;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.structured.content.apio.architect.model.StructuredContentLocation;
import com.liferay.structured.content.apio.architect.model.StructuredContentValue;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Javier Gamarra
 * @author Cristina González
 */
@Component(service = JournalArticleContentHelper.class)
public class JournalArticleContentHelper {

	public String createJournalArticleContent(
		DDMStructure ddmStructure,
		Map<Locale, List<? extends StructuredContentValue>>
			structuredContentValuesMap) {

		Locale originalSiteDefaultLocale =
			LocaleThreadLocal.getSiteDefaultLocale();

		String instanceId = StringUtil.randomString();

		try {
			LocaleThreadLocal.setSiteDefaultLocale(
				LocaleUtil.fromLanguageId(ddmStructure.getDefaultLanguageId()));

			DDMForm ddmForm = ddmStructure.getDDMForm();

			DDMFormValues ddmFormValues = new DDMFormValues(ddmForm);

			ddmFormValues.setAvailableLocales(ddmForm.getAvailableLocales());
			ddmFormValues.setDefaultLocale(ddmForm.getDefaultLocale());

			List<DDMFormField> ddmFormFields = ddmForm.getDDMFormFields();

			for (DDMFormField ddmFormField : ddmFormFields) {
				ddmFormValues.addDDMFormFieldValue(
					getDDMFormFieldValue(
						ddmFormField, structuredContentValuesMap, instanceId));
			}

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setAttribute(
				"ddmFormValues", serializeDDMFormValues(ddmFormValues));

			Fields fields = _ddm.getFields(
				ddmStructure.getStructureId(), serviceContext);

			return _journalConverter.getContent(ddmStructure, fields);
		}
		catch (PortalException pe) {
			throw new BadRequestException(
				"Invalid Structured Content Value " + pe.getMessage(), pe);
		}
		catch (BadRequestException bre) {
			throw bre;
		}
		catch (Throwable t) {
			throw new InternalServerErrorException(t);
		}
		finally {
			LocaleThreadLocal.setSiteDefaultLocale(originalSiteDefaultLocale);
		}
	}

	protected DDMFormFieldValue getDDMFormFieldValue(
		DDMFormField ddmFormField,
		Map<Locale, List<? extends StructuredContentValue>>
			structuredContentValuesMap,
		String instanceId) {

		DDMFormFieldValue ddmFormFieldValue = new DDMFormFieldValue();

		ddmFormFieldValue.setName(ddmFormField.getName());
		ddmFormFieldValue.setInstanceId(instanceId);

		ddmFormFieldValue.setValue(
			_getValue(ddmFormField, structuredContentValuesMap));

		return ddmFormFieldValue;
	}

	protected String serializeDDMFormValues(DDMFormValues ddmFormValues) {
		DDMFormValuesSerializer ddmFormValuesSerializer =
			_ddmFormValuesSerializerTracker.getDDMFormValuesSerializer("json");

		DDMFormValuesSerializerSerializeResponse
			ddmFormValuesSerializerSerializeResponse =
				ddmFormValuesSerializer.serialize(
					DDMFormValuesSerializerSerializeRequest.Builder.newBuilder(
						ddmFormValues
					).build());

		return ddmFormValuesSerializerSerializeResponse.getContent();
	}

	private String _getData(
		DDMFormField ddmFormField,
		List<? extends StructuredContentValue> structuredContentValues) {

		Stream<? extends StructuredContentValue> stream =
			structuredContentValues.stream();

		return stream.filter(
			structuredContentValue -> Objects.equals(
				structuredContentValue.getName(), ddmFormField.getName())
		).findFirst(
		).map(
			structuredContentValue -> {
				try {
					return _getData(ddmFormField, structuredContentValue);
				}
				catch (PortalException pe) {
					throw new BadRequestException(
						"Invalid Structured Content Value " + pe.getMessage(),
						pe);
				}
			}
		).orElse(
			StringPool.BLANK
		);
	}

	private String _getData(
			DDMFormField ddmFormField,
			StructuredContentValue structuredContentValue)
		throws PortalException {

		if (Objects.equals(
				ddmFormField.getType(), DDMFormFieldType.DOCUMENT_LIBRARY)) {

			return _getFileData(ddmFormField, structuredContentValue);
		}
		else if (Objects.equals(
					ddmFormField.getType(), DDMFormFieldType.GEOLOCATION)) {

			return _getGeoLocationData(structuredContentValue);
		}
		else if (Objects.equals(
					ddmFormField.getType(), DDMFormFieldType.JOURNAL_ARTICLE)) {

			return _getStructuredContentData(structuredContentValue);
		}

		return Optional.ofNullable(
			structuredContentValue.getValue()
		).orElse(
			StringPool.BLANK
		);
	}

	private String _getDocumentType(String type) {
		if (Objects.equals(DDMFormFieldType.DOCUMENT_LIBRARY, type)) {
			return "document";
		}

		return "journal";
	}

	private String _getFileData(
			DDMFormField ddmFormField,
			StructuredContentValue structuredContentValue)
		throws PortalException {

		FileEntry fileEntry = _dlAppService.getFileEntry(
			structuredContentValue.getDocument());

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("alt", structuredContentValue.getValue());
		jsonObject.put("fileEntryId", fileEntry.getFileEntryId());
		jsonObject.put("groupId", fileEntry.getGroupId());
		jsonObject.put("name", fileEntry.getFileName());
		jsonObject.put("resourcePrimKey", fileEntry.getPrimaryKey());
		jsonObject.put("title", fileEntry.getFileName());
		jsonObject.put("type", _getDocumentType(ddmFormField.getType()));
		jsonObject.put("uuid", fileEntry.getUuid());

		return jsonObject.toString();
	}

	private String _getGeoLocationData(
		StructuredContentValue structuredContentValue) {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		StructuredContentLocation structuredContentLocation =
			structuredContentValue.getStructuredContentLocation();

		jsonObject.put("latitude", structuredContentLocation.getLatitude());
		jsonObject.put("longitude", structuredContentLocation.getLongitude());

		return jsonObject.toString();
	}

	private String _getStructuredContentData(
			StructuredContentValue structuredContentValue)
		throws PortalException {

		JournalArticle journalArticle = _journalArticleService.getArticle(
			structuredContentValue.getStructuredContentId());

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("className", JournalArticle.class.getName());
		jsonObject.put("classPK", journalArticle.getResourcePrimKey());
		jsonObject.put("title", journalArticle.getTitle());

		return jsonObject.toString();
	}

	private Value _getValue(
		DDMFormField ddmFormField,
		Map<Locale, List<? extends StructuredContentValue>>
			structuredContentValuesMap) {

		Set<Map.Entry<Locale, List<? extends StructuredContentValue>>> entries =
			structuredContentValuesMap.entrySet();

		DDMForm ddmForm = ddmFormField.getDDMForm();

		if (ddmFormField.isLocalizable()) {
			Stream<Map.Entry<Locale, List<? extends StructuredContentValue>>>
				stream = entries.stream();

			LocalizedValue localizedValue = new LocalizedValue(
				ddmForm.getDefaultLocale());

			stream.forEach(
				entry -> localizedValue.addString(
					entry.getKey(), _getData(ddmFormField, entry.getValue())));

			return localizedValue;
		}

		return Optional.ofNullable(
			structuredContentValuesMap.get(ddmForm.getDefaultLocale())
		).map(
			structuredContentValues -> new UnlocalizedValue(
				_getData(ddmFormField, structuredContentValues))
		).orElseGet(
			() -> new UnlocalizedValue(StringPool.BLANK)
		);
	}

	@Reference
	private DDM _ddm;

	@Reference
	private DDMFormValuesSerializerTracker _ddmFormValuesSerializerTracker;

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private JournalArticleService _journalArticleService;

	@Reference
	private JournalConverter _journalConverter;

}