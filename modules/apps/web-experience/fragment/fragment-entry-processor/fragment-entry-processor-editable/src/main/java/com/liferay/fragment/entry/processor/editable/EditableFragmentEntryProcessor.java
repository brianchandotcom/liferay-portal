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

package com.liferay.fragment.entry.processor.editable;

import com.liferay.fragment.entry.processor.editable.parser.EditableElementParser;
import com.liferay.fragment.exception.FragmentEntryContentException;
import com.liferay.fragment.processor.FragmentEntryProcessor;
import com.liferay.fragment.util.HtmlParserUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.XPath;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Pavel Savinov
 */
@Component(
	immediate = true,
	property = {"fragment.entry.processor.priority:Integer=1"},
	service = FragmentEntryProcessor.class
)
public class EditableFragmentEntryProcessor implements FragmentEntryProcessor {

	@Override
	public String processFragmentEntryHTML(String html, JSONObject jsonObject)
		throws PortalException {

		Document document = _htmlParserUtil.parse(html);

		XPath editableXPath = SAXReaderUtil.createXPath("//lfr-editable");

		for (Node editableNode : editableXPath.selectNodes(document)) {
			Element element = (Element)editableNode;

			EditableElementParser editableElementParser =
				_editableElementParsers.get(element.attributeValue("type"));

			if (editableElementParser == null) {
				continue;
			}

			String id = element.attributeValue("id");

			editableElementParser.replace(element, jsonObject.getString(id));
		}

		Element rootElement = document.getRootElement();

		return rootElement.asXML();
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		unbind = "unregisterEditableElementParser"
	)
	public void registerEditableElementParser(
		EditableElementParser editableElementParser,
		Map<String, Object> properties) {

		String editableTagName = (String)properties.get("type");

		_editableElementParsers.put(editableTagName, editableElementParser);
	}

	public void unregisterEditableElementParser(
		EditableElementParser editableElementParser,
		Map<String, Object> properties) {

		String editableTagName = (String)properties.get("type");

		_editableElementParsers.remove(editableTagName);
	}

	@Override
	public void validateFragmentEntryHTML(String html) throws PortalException {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", getClass());

		Document document = _htmlParserUtil.parse(html);

		XPath uniqueXPath = SAXReaderUtil.createXPath("//*[@id]");

		List<Node> uniqueNodes = uniqueXPath.selectNodes(document);

		Stream<Node> uniqueNodesStream = uniqueNodes.stream();

		Map<String, Long> idsMap = uniqueNodesStream.collect(
			Collectors.groupingBy(
				node -> {
					Element element = (Element)node;

					return element.attributeValue("id");
				},
				Collectors.counting()));

		Collection<String> ids = idsMap.keySet();

		Stream<String> idsStream = ids.stream();

		idsStream = idsStream.filter(id -> idsMap.get(id) > 1);

		if (idsStream.count() > 0) {
			throw new FragmentEntryContentException(
				LanguageUtil.get(
					resourceBundle,
					"you-must-define-an-unique-id-for-each-editable-element"));
		}

		XPath editableXPath = SAXReaderUtil.createXPath("//lfr-editable");

		List<Node> editableNodes = editableXPath.selectNodes(document);

		Stream<Node> editableNodesStream = editableNodes.stream();

		if (!editableNodesStream.allMatch(
				node -> {
					Element element = (Element)node;

					if (Validator.isNotNull(element.attributeValue("id"))) {
						return true;
					}

					return false;
				})) {

			throw new FragmentEntryContentException(
				LanguageUtil.get(
					resourceBundle,
					"you-must-define-an-unique-id-for-each-editable-element"));
		}
	}

	private final Map<String, EditableElementParser> _editableElementParsers =
		new HashMap<>();

	@Reference
	private HtmlParserUtil _htmlParserUtil;

}