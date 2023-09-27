/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.xml;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.xml.XMLSchema;
import com.liferay.portal.util.EntityResolver;

import org.dom4j.io.SAXReader;

import org.xml.sax.XMLReader;

/**
 * @author Raymond Augé
 */
public class SAXReaderFactory {

	public static SAXReader getSAXReader(
		XMLReader xmlReader, boolean validate, boolean secure) {

		SAXReader saxReader = null;

		DocumentFactory documentFactory = DocumentFactory.getInstance();

		try {
			saxReader = new SAXReader(xmlReader, validate);

			saxReader.setDocumentFactory(documentFactory);
			saxReader.setEntityResolver(new EntityResolver());
			saxReader.setFeature(_FEATURES_DYNAMIC, validate);
			saxReader.setFeature(_FEATURES_VALIDATION, validate);
			saxReader.setFeature(_FEATURES_VALIDATION_SCHEMA, validate);
			saxReader.setFeature(
				_FEATURES_VALIDATION_SCHEMA_FULL_CHECKING, validate);

			if (!secure) {
				saxReader.setFeature(_FEATURES_DISALLOW_DOCTYPE_DECL, false);
				saxReader.setFeature(_FEATURES_LOAD_DTD_GRAMMAR, validate);
				saxReader.setFeature(_FEATURES_LOAD_EXTERNAL_DTD, validate);
			}
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"XSD validation is disabled because " +
						exception.getMessage());
			}

			saxReader = new SAXReader(xmlReader, false);

			saxReader.setDocumentFactory(documentFactory);
			saxReader.setEntityResolver(new EntityResolver());
		}

		return saxReader;
	}

	public static SAXReader getSAXReader(
		XMLReader xmlReader, XMLSchema xmlSchema, boolean validate,
		boolean secure) {

		SAXReader saxReader = getSAXReader(xmlReader, validate, secure);

		if ((xmlSchema == null) || !validate) {
			return saxReader;
		}

		try {
			saxReader.setProperty(
				_PROPERTY_SCHEMA_LANGUAGE, xmlSchema.getSchemaLanguage());
			saxReader.setProperty(
				_PROPERTY_SCHEMA_SOURCE, xmlSchema.getSchemaSource());
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"XSD validation is disabled because " +
						exception.getMessage());
			}
		}

		return saxReader;
	}

	private static final String _FEATURES_DISALLOW_DOCTYPE_DECL =
		"http://apache.org/xml/features/disallow-doctype-decl";

	private static final String _FEATURES_DYNAMIC =
		"http://apache.org/xml/features/validation/dynamic";

	private static final String _FEATURES_LOAD_DTD_GRAMMAR =
		"http://apache.org/xml/features/nonvalidating/load-dtd-grammar";

	private static final String _FEATURES_LOAD_EXTERNAL_DTD =
		"http://apache.org/xml/features/nonvalidating/load-external-dtd";

	private static final String _FEATURES_VALIDATION =
		"http://xml.org/sax/features/validation";

	private static final String _FEATURES_VALIDATION_SCHEMA =
		"http://apache.org/xml/features/validation/schema";

	private static final String _FEATURES_VALIDATION_SCHEMA_FULL_CHECKING =
		"http://apache.org/xml/features/validation/schema-full-checking";

	private static final String _PROPERTY_SCHEMA_LANGUAGE =
		"http://java.sun.com/xml/jaxp/properties/schemaLanguage";

	private static final String _PROPERTY_SCHEMA_SOURCE =
		"http://java.sun.com/xml/jaxp/properties/schemaSource";

	private static final Log _log = LogFactoryUtil.getLog(
		SAXReaderFactory.class);

}