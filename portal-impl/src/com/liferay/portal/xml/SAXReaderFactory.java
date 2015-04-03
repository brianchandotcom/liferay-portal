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

package com.liferay.portal.xml;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.xml.XMLSchema;
import com.liferay.portal.util.EntityResolver;

import org.xml.sax.XMLReader;

/**
 * @author Raymond Augé
 */
public class SAXReaderFactory {

	public static final org.dom4j.io.SAXReader getSAXReader(
		XMLReader xmlReader, boolean validate, boolean secure) {

		org.dom4j.io.SAXReader reader = null;

		try {
			reader = new org.dom4j.io.SAXReader(xmlReader, validate);

			reader.setEntityResolver(new EntityResolver());
			reader.setFeature(_FEATURES_DYNAMIC, validate);
			reader.setFeature(_FEATURES_VALIDATION, validate);
			reader.setFeature(_FEATURES_VALIDATION_SCHEMA, validate);
			reader.setFeature(
				_FEATURES_VALIDATION_SCHEMA_FULL_CHECKING, validate);

			if (!secure) {
				reader.setFeature(_FEATURES_DISALLOW_DOCTYPE_DECL, false);
				reader.setFeature(_FEATURES_LOAD_DTD_GRAMMAR, validate);
				reader.setFeature(_FEATURES_LOAD_EXTERNAL_DTD, validate);
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"XSD validation is disabled because " + e.getMessage());
			}

			reader = new org.dom4j.io.SAXReader(xmlReader, false);

			reader.setEntityResolver(new EntityResolver());
		}

		return reader;
	}

	public static final org.dom4j.io.SAXReader getSAXReader(
		XMLReader xmlReader, XMLSchema xmlSchema, boolean validate,
		boolean secure) {

		org.dom4j.io.SAXReader saxReader = getSAXReader(
			xmlReader, validate, secure);

		if ((xmlSchema == null) || (validate == false)) {
			return saxReader;
		}

		try {
			saxReader.setProperty(
				_PROPERTY_SCHEMA_LANGUAGE, xmlSchema.getSchemaLanguage());
			saxReader.setProperty(
				_PROPERTY_SCHEMA_SOURCE, xmlSchema.getSchemaSource());
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"XSD validation is disabled because " + e.getMessage());
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