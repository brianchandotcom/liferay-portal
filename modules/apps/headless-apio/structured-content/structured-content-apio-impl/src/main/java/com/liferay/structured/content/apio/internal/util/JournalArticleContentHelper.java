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

import com.liferay.apio.architect.functional.Try;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleService;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.security.xml.SecureXMLFactoryProviderUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.structured.content.apio.architect.model.StructuredContentLocation;
import com.liferay.structured.content.apio.architect.model.StructuredContentValue;

import java.io.StringWriter;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author Javier Gamarra
 */
@Component(service = JournalArticleContentHelper.class)
public class JournalArticleContentHelper {

	public String createJournalArticleContent(
		List<? extends StructuredContentValue> structuredContentValues,
		DDMStructure ddmStructure, Locale locale) {

		return Try.fromFallible(
			() -> {
				String localeId = LocaleUtil.toLanguageId(locale);

				Document document = _getDocument();

				Element rootElement = _getRootElement(localeId, document);

				document.appendChild(rootElement);

				DDMForm ddmForm = ddmStructure.getDDMForm();

				for (DDMFormField ddmFormField : ddmForm.getDDMFormFields()) {
					String name = ddmFormField.getName();

					String type = _getType(ddmFormField);

					Element dynamicElement = _getDynamicElement(
						document, name, type);

					Optional<? extends StructuredContentValue>
						structuredContentValueOptional =
							_findStructuredContentValueOptional(
								structuredContentValues, name);

					Element contentElement = _getContentElement(
						document, localeId, structuredContentValueOptional,
						type);

					dynamicElement.appendChild(contentElement);

					rootElement.appendChild(dynamicElement);
				}

				return _toString(document);
			}
		).orElse(
			""
		);
	}

	private Optional<? extends StructuredContentValue>
		_findStructuredContentValueOptional(
			List<? extends StructuredContentValue> structuredContentValues,
			String name) {

		Stream<? extends StructuredContentValue> stream =
			structuredContentValues.stream();

		return stream.filter(
			structuredContentValue ->
				Objects.equals(name, structuredContentValue.getName())
		).findFirst();
	}

	private Element _getContentElement(
		Document document, String localeId,
		Optional<? extends StructuredContentValue>
			structuredContentValueOptional,
		String type) {

		Element element = document.createElement("dynamic-content");

		element.setAttribute("language-id", localeId);

		return structuredContentValueOptional.map(
			structuredContentValue -> {
				if (type.equals("list") &&
					_isJsonArray(structuredContentValue.getValue())) {

					return _getDynamicContentListElement(
						document, element, structuredContentValue);
				}

				String data = _getData(structuredContentValue, type);

				element.appendChild(document.createCDATASection(data));

				return element;
			}
		).orElse(
			element
		);
	}

	private String _getData(
		StructuredContentValue structuredContentValue, String type) {

		if (type.equals("image") || type.equals("document-library")) {
			return _getFileData(structuredContentValue, type);
		}
		else if (type.equals("ddm-geolocation")) {
			return _getGeoLocationData(structuredContentValue);
		}
		else if (type.equals("ddm-journal-article")) {
			return _getStructuredContentData(structuredContentValue);
		}

		return structuredContentValue.getValue();
	}

	private Document _getDocument() throws ParserConfigurationException {
		DocumentBuilderFactory documentBuilderFactory =
			SecureXMLFactoryProviderUtil.newDocumentBuilderFactory();

		DocumentBuilder documentBuilder =
			documentBuilderFactory.newDocumentBuilder();

		return documentBuilder.newDocument();
	}

	private String _getDocumentType(String type) {
		if (type.equals("document-library")) {
			return "document";
		}

		return "journal";
	}

	private Element _getDynamicContentListElement(
		Document document, Element element,
		StructuredContentValue structuredContentValue) {

		return Try.fromFallible(
			structuredContentValue::getValue
		).map(
			JSONFactoryUtil::createJSONArray
		).map(
			jsonArray -> {
				for (int i = 0; i < jsonArray.length(); i++) {
					Element optionElement = document.createElement("option");

					String value = jsonArray.getString(i);

					optionElement.appendChild(
						document.createCDATASection(value));

					element.appendChild(optionElement);
				}

				return element;
			}
		).orElse(
			element
		);
	}

	private Element _getDynamicElement(
		Document document, String name, String type) {

		Element element = document.createElement("dynamic-element");

		element.setAttribute("index-type", "keyword");
		element.setAttribute("name", name);
		element.setAttribute("type", type);

		return element;
	}

	private String _getFileData(
		StructuredContentValue structuredContentValue, String type) {

		return Try.fromFallible(
			structuredContentValue::getDocument
		).map(
			_dlAppService::getFileEntry
		).map(
			fileEntry -> {
				JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

				jsonObject.put("alt", structuredContentValue.getValue());
				jsonObject.put("fileEntryId", fileEntry.getFileEntryId());
				jsonObject.put("groupId", fileEntry.getGroupId());
				jsonObject.put("name", fileEntry.getFileName());
				jsonObject.put("resourcePrimKey", fileEntry.getPrimaryKey());
				jsonObject.put("title", fileEntry.getFileName());
				jsonObject.put("type", _getDocumentType(type));
				jsonObject.put("uuid", fileEntry.getUuid());

				return jsonObject.toString();
			}
		).orElse(
			null
		);
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

	private Element _getRootElement(String localeId, Document document) {
		Element element = document.createElement("root");

		element.setAttribute("available-locales", localeId);
		element.setAttribute("default-locale", localeId);

		return element;
	}

	private String _getStructuredContentData(
		StructuredContentValue structuredContentValue) {

		return Try.fromFallible(
			structuredContentValue::getStructuredContentId
		).map(
			_journalArticleService::getArticle
		).map(
			journalArticle -> {
				JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

				jsonObject.put("className", JournalArticle.class.getName());
				jsonObject.put("classPK", journalArticle.getResourcePrimKey());
				jsonObject.put("title", journalArticle.getTitle());

				return jsonObject.toString();
			}
		).orElse(
			null
		);
	}

	private String _getType(DDMFormField ddmFormField) {
		String type = ddmFormField.getType();

		if (type.equals("ddm-image") || type.equals("ddm-documentlibrary") ||
			type.equals("checkbox")) {

			return ddmFormField.getDataType();
		}
		else if (type.equals("ddm-text-html")) {
			return "text_area";
		}
		else if (type.equals("select")) {
			return "list";
		}

		return type;
	}

	private boolean _isJsonArray(String value) {
		return Try.fromFallible(
			() -> JSONFactoryUtil.createJSONArray(value)
		).isSuccess();
	}

	private String _toString(Document document) throws TransformerException {
		TransformerFactory transformerFactory =
			TransformerFactory.newInstance();

		Transformer transformer = transformerFactory.newTransformer();

		StringWriter stringWriter = new StringWriter();

		transformer.transform(
			new DOMSource(document), new StreamResult(stringWriter));

		return stringWriter.toString();
	}

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private JournalArticleService _journalArticleService;

}