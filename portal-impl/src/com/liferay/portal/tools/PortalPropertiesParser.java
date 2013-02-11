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

package com.liferay.portal.tools;

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import jodd.io.StreamUtil;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.AttributesImpl;

/**
 * @author Igor Spasic
 * @author Sergio González
 */
public class PortalPropertiesParser implements XMLReader {

	public static void main(String[] args) throws Exception {
		PortalPropertiesParser portalPropertiesParser =
			new PortalPropertiesParser();

		String destinationHtml = "properties-doc.html";

		if (args.length >= 1) {
			destinationHtml = args[0];
		}

		if (args.length >= 2) {
			boolean fullToc = GetterUtil.getBoolean(args[1]);

			portalPropertiesParser.setGenerateFullToc(fullToc);
		}

		InputStream portalPropertiesInputStream =
			ClassLoader.getSystemResourceAsStream("portal.properties");

		InputStream xslInputStream = ClassLoader.getSystemResourceAsStream(
			"com/liferay/portal/tools/dependencies/properties-html.xsl");

		try {
			InputSource inputSource = new InputSource(
				portalPropertiesInputStream);

			SAXSource saxSource = new SAXSource(
				portalPropertiesParser, inputSource);

			StreamSource xslSource = new StreamSource(xslInputStream);

			TransformerFactory transformerFactory =
				TransformerFactory.newInstance();

			Transformer transformer = transformerFactory.newTransformer(
				xslSource);

			transformer.transform(saxSource, new StreamResult(destinationHtml));
		}
		finally {
			xslInputStream.close();
			portalPropertiesInputStream.close();
		}
	}

	public ContentHandler getContentHandler() {
		return _contentHandler;
	}

	public DTDHandler getDTDHandler() {
		return null;
	}

	public EntityResolver getEntityResolver() {
		return null;
	}

	public ErrorHandler getErrorHandler() {
		return null;
	}

	public boolean getFeature(String s) {
		return false;
	}

	public Object getProperty(String s) {
		return null;
	}

	public boolean isGenerateFullToc() {
		return _generateFullToc;
	}

	public void parse(InputSource source) throws IOException, SAXException {
		InputStream is = source.getByteStream();

		byte[] bytes = StreamUtil.readBytes(is);

		String content = new String(bytes, StringPool.UTF8);

		_parse(content);
	}

	public void parse(String uri) {
	}

	public void setContentHandler(ContentHandler handler) {
		_contentHandler = handler;
	}

	public void setDTDHandler(DTDHandler d) {
	}

	public void setEntityResolver(EntityResolver e) {
	}

	public void setErrorHandler(ErrorHandler handler) {
	}

	public void setFeature(String s, boolean b) {
	}

	public void setGenerateFullToc(boolean generateFullToc) {
		this._generateFullToc = generateFullToc;
	}

	public void setProperty(String s, Object o) {
	}

	private void _attributeAdd(
		AttributesImpl attributes, String key, String value) {

		attributes.addAttribute(_namespaceURI, key, key, "", value);
	}

	private void _createProperty(
			String propertyTagName, Attributes attrs, PropertyData propertyData)
		throws SAXException {

		_tagStart(propertyTagName, attrs);

		_tag("name", propertyData.name);
		_tag("anchor", propertyData.anchor);
		_tag("description", propertyData.description);
		_tag("value", propertyData.value);

		for (String alternativeValue : propertyData.alternativeValues) {
			_tag("value", alternativeValue, "alt", "true");
		}

		_tagEnd(propertyTagName);
	}

	private void _createSection(Section section) throws SAXException {
		_tagStart("section");

		_tag("title", section.title);

		_tag("anchor", section.anchor);

		_tagStart("content");

		for (PropertyData propertyData : section.propertyDataList) {
			AttributesImpl attrs = new AttributesImpl();

			_attributeAdd(attrs, "hidden", String.valueOf(propertyData.hidden));

			_attributeAdd(attrs, "group", String.valueOf(propertyData.group));

			if (propertyData.groupStart) {
				_attributeAdd(attrs, "prefix", propertyData.prefix);
			}
			else {
				_attributeAdd(attrs, "prefix", StringPool.BLANK);
			}

			String propertyTagName = "property";

			if (!propertyData.groupStart && (propertyData.group > 0)) {
				propertyTagName = propertyTagName.concat("-group");
			}

			_createProperty(propertyTagName, attrs, propertyData);

			// Duplicate property to property-group if group is started

			if (propertyData.groupStart && (propertyData.group > 0)) {
				propertyTagName = propertyTagName.concat("-group");

				_createProperty(propertyTagName, attrs, propertyData);
			}
		}

		_tagEnd("content");

		_tagEnd("section");
	}

	private String _makePrefix(String name, int minLeft, int deepRight) {
		int pos = 0;

		while (minLeft > 0) {
			pos = name.indexOf(CharPool.PERIOD, pos);

			if (pos == -1) {
				break;
			}

			pos++;

			minLeft--;
		}

		if (pos == -1) {
			return StringPool.BLANK;
		}

		String prefix;

		if (pos > 0) {
			pos--;

			prefix = name.substring(0, pos);

			name = name.substring(pos);
		}
		else {
			prefix = StringPool.BLANK;
		}

		while (deepRight > 0) {
			pos = name.lastIndexOf(CharPool.PERIOD);

			if (pos == -1) {
				return prefix;
			}

			name = name.substring(0, pos);

			deepRight--;
		}

		return prefix + name;
	}

	private void _parse(String content) throws SAXException {
		_contentHandler.startDocument();

		_tagStart("params");

		_tag("fullToc", String.valueOf(_generateFullToc));

		_tagEnd("params");

		_tagStart("properties");

		int pos = 0;

		while (true) {
			pos = content.indexOf(_DOUBLE_HASH_SPACE, pos);

			if (pos == -1) {
				break;
			}

			Section section = new Section();

			int titleX = pos + _DOUBLE_HASH_SPACE.length();
			int titleY = content.indexOf(CharPool.NEW_LINE, pos);

			section.title = content.substring(titleX, titleY).trim();
			section.anchor = nameToAnchor(section.title);

			int sectionX = titleY + 5;
			int sectionY = content.indexOf(_DOUBLE_POUND, sectionX);

			if (sectionY == -1) {
				sectionY = content.length();
			}

			String sectionContent = content.substring(titleY, sectionY);

			section.propertyDataList = _parseSectionContent(sectionContent);

			_resolveGroups(section.propertyDataList);

			_createSection(section);

			pos = sectionY;
		}

		_tagEnd("properties");

		_contentHandler.endDocument();
	}

	private List<PropertyData> _parseSectionContent(String content) {
		List<PropertyData> propertyDataList = new ArrayList<PropertyData>();

		String[] lines = StringUtil.split(content, CharPool.NEW_LINE);

		PropertyData propertyData = new PropertyData();

		boolean hidden;

		for (int i = 0; i < lines.length; i++) {
			String line = lines[i];
			line = line.trim();

			hidden = false;

			if ((line.length() >= 1) && (line.charAt(0) == CharPool.POUND)) {

				// Comments

				if ((line.length() >= 2) && (line.charAt(1) != ' ')) {
					line = line.substring(1);

					hidden = true;
				}
				else {
					propertyData.description = propertyData.description.concat(
						line.substring(1)).concat(StringPool.NEW_LINE);

					continue;
				}
			}

			// Values

			int pos = line.indexOf(CharPool.EQUAL);

			if (pos == -1) {
				continue;
			}

			propertyData.name = line.substring(0, pos);
			propertyData.anchor = nameToAnchor(propertyData.name + '+' + i);
			propertyData.hidden = hidden;
			propertyData.prefix = _makePrefix(
				propertyData.name, _PARAM_MIN_LEFT, _PARAM_DEEP_RIGHT);

			String value = line.substring(pos + 1);

			while (value.endsWith("\\")) {
				value = value.substring(0, value.length() - 1);

				i++;

				value += lines[i].trim();
			}

			propertyData.value = value;

			// Check previous property for alternative values

			if (Validator.isNull(propertyData.description) &&
				!propertyDataList.isEmpty()) {

				PropertyData previousData = propertyDataList.get(
					propertyDataList.size() - 1);

				if (previousData.name.equals(propertyData.name)) {
					if (previousData.value.equals(propertyData.value)) {
						propertyData = new PropertyData();
						continue;
					}

					previousData.alternativeValues.add(propertyData.value);

					propertyData = new PropertyData();

					continue;
				}
			}

			propertyDataList.add(propertyData);
			propertyData = new PropertyData();
		}

		return propertyDataList;
	}

	private void _resolveGroups(List<PropertyData> propertyDataList) {
		int groupCount = 0;
		boolean newGroup = true;

		for (int i = 1; i < propertyDataList.size(); i++) {
			PropertyData prev = propertyDataList.get(i - 1);
			PropertyData curr = propertyDataList.get(i);

			if ((prev.hidden == curr.hidden) &&
				prev.alternativeValues.isEmpty() &&
				curr.alternativeValues.isEmpty() &&
				prev.prefix.equals(curr.prefix) &&
				Validator.isNull(curr.description)) {

				// Group founded

				if (newGroup == true) {
					groupCount++;
					prev.group = groupCount;
					prev.groupStart = true;
					newGroup = false;
				}

				curr.group = prev.group;
			}
			else {
				newGroup = true;
			}
		}
	}

	private void _tag(String tagName, String text) throws SAXException {
		_tagStart(tagName);
		_tagText(text);
		_tagEnd(tagName);
	}

	private void _tag(String tagName, String text, String key, String value)
		throws SAXException {

		AttributesImpl attributes = new AttributesImpl();

		_attributeAdd(attributes, key, value);

		_tagStart(tagName, attributes);
		_tagText(text);
		_tagEnd(tagName);
	}

	private void _tagEnd(String tagName) throws SAXException {
		_contentHandler.endElement(_namespaceURI, tagName, tagName);
	}

	private void _tagStart(String tagName) throws SAXException {
		_contentHandler.startElement(
			_namespaceURI, tagName, tagName, _attributes);
	}

	private void _tagStart(String tagName, Attributes attributes)
		throws SAXException {

		_contentHandler.startElement(
			_namespaceURI, tagName, tagName, attributes);
	}

	private void _tagText(String text) throws SAXException {
		_contentHandler.characters(text.toCharArray(), 0, text.length());
	}

	private String nameToAnchor(String name) {
		return name.replace(CharPool.SPACE, CharPool.PLUS);
	}

	private static final String _DOUBLE_HASH_SPACE = "## ";

	private static final String _DOUBLE_POUND = "##";

	private static final int _PARAM_DEEP_RIGHT = 1;

	private static final int _PARAM_MIN_LEFT = 2;

	private AttributesImpl _attributes = new AttributesImpl();
	private ContentHandler _contentHandler;
	private boolean _generateFullToc = false;
	private String _namespaceURI = StringPool.BLANK;

	private static class PropertyData {
		public List<String> alternativeValues = new ArrayList<String>();
		public String anchor = StringPool.BLANK;
		public String description = StringPool.BLANK;
		public int group;
		public boolean groupStart;
		public boolean hidden;
		public String name = StringPool.BLANK;
		public String prefix = StringPool.BLANK;
		public String value = StringPool.BLANK;

	}

	private static class Section {
		public String anchor;

		public List<PropertyData> propertyDataList;

		public String title;

	}

}