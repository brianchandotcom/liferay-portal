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
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.security.xml.SecureXMLFactoryProviderUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.structured.content.apio.internal.architect.form.StructuredContentValuesForm;

import java.io.StringWriter;

import java.util.List;
import java.util.Locale;
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
		List<StructuredContentValuesForm> structuredContentValuesForms,
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

					Optional<StructuredContentValuesForm>
						structuredContentValuesFormOptional =
							_findFirstFormField(
								structuredContentValuesForms, name);

					String value = structuredContentValuesFormOptional.map(
						structuredContentValuesForm -> _getData(
							type, structuredContentValuesForm)
					).orElse(
						""
					);

					Element contentElement = _getContentElement(
						document, localeId, value);

					dynamicElement.appendChild(contentElement);

					rootElement.appendChild(dynamicElement);
				}

				return _transformToString(document);
			}
		).orElse(
			""
		);
	}

	private Optional<StructuredContentValuesForm> _findFirstFormField(
		List<StructuredContentValuesForm> structuredContentValuesForms,
		String name) {

		Stream<StructuredContentValuesForm> stream =
			structuredContentValuesForms.stream();

		return stream.filter(
			value -> name.equals(value.getName())
		).findFirst();
	}

	private Element _getContentElement(
		Document document, String localeId, String data) {

		Element element = document.createElement("dynamic-content");

		element.setAttribute("language-id", localeId);
		element.appendChild(document.createCDATASection(data));

		return element;
	}

	private String _getData(
		String type, StructuredContentValuesForm valuesForm) {

		if (type.equals("image") || type.equals("document-library")) {
			return _getFileData(valuesForm, type);
		}

		return valuesForm.getValue();
	}

	private Document _getDocument() throws ParserConfigurationException {
		DocumentBuilderFactory documentBuilderFactory =
			SecureXMLFactoryProviderUtil.newDocumentBuilderFactory();

		DocumentBuilder documentBuilder =
			documentBuilderFactory.newDocumentBuilder();

		return documentBuilder.newDocument();
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
		StructuredContentValuesForm structuredContentValuesForm,
		String type) {

		return Try.fromFallible(
			structuredContentValuesForm::getDocument
		).map(
			_dlAppService::getFileEntry
		).map(
			fileEntry -> {
				JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

				jsonObject.put("alt", structuredContentValuesForm.getValue());
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

	private String _getDocumentType(String type) {

		if (type.equals("document-library")) {
			return "document";
		}

		return "journal";
	}

	private Element _getRootElement(String localeId, Document document) {
		Element element = document.createElement("root");

		element.setAttribute("available-locales", localeId);
		element.setAttribute("default-locale", localeId);

		return element;
	}

	private String _getType(DDMFormField ddmFormField) {
		String type = ddmFormField.getType();

		if (type.equals("ddm-image") || (type.equals("ddm-documentlibrary"))) {
			return ddmFormField.getDataType();
		}

		return type;
	}

	private String _transformToString(Document document)
		throws TransformerException {

		TransformerFactory transformerFactory =
			TransformerFactory.newInstance();

		Transformer transformer = transformerFactory.newTransformer();

		DOMSource domSource = new DOMSource(document);

		StringWriter stringWriter = new StringWriter();

		transformer.transform(domSource, new StreamResult(stringWriter));

		return stringWriter.toString();
	}

	@Reference
	private DLAppService _dlAppService;

}