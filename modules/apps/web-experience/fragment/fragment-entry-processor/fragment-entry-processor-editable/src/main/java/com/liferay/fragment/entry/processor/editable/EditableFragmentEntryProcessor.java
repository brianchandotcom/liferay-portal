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

import com.liferay.fragment.exception.FragmentEntryContentException;
import com.liferay.fragment.processor.FragmentEntryProcessor;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.XPath;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;

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
	public void validateFragmentEntryHTML(String html) throws PortalException {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", getClass());

		Document document = null;

		try {
			document = SAXReaderUtil.read(html);
		}
		catch (DocumentException de) {
			throw new FragmentEntryContentException(
				LanguageUtil.get(resourceBundle, "invalid-fragment-entry-html"),
				de);
		}

		XPath uniqueXPath = SAXReaderUtil.createXPath("//*[@id]");

		List<Node> uniqueNodes = uniqueXPath.selectNodes(document);

		Map<String, Long> idMap = uniqueNodes.stream().collect(
			Collectors.groupingBy(
				node -> ((Element)node).attributeValue("id"),
				Collectors.counting()));

		Collection<String> ids = idMap.keySet();

		Stream<String> idStream = ids.stream().filter(id -> idMap.get(id) > 1);

		List<String> nonUniqueIds = idStream.collect(Collectors.toList());

		if (!nonUniqueIds.isEmpty()) {
			throw new FragmentEntryContentException(
				LanguageUtil.get(
					resourceBundle,
					"each-editable-element-should-have-a-unique-id"));
		}

		XPath editableXPath = SAXReaderUtil.createXPath("//" + _LFR_EDITABLE);

		List<Node> editableNodes = editableXPath.selectNodes(document);

		if (!editableNodes.stream().allMatch(
				node -> Validator.isNotNull(
					((Element)node).attributeValue("id")))) {

			throw new FragmentEntryContentException(
				LanguageUtil.get(
					resourceBundle,
					"each-editable-element-should-have-a-unique-id"));
		}
	}

	private static final String _LFR_EDITABLE = "lfr-editable";

}