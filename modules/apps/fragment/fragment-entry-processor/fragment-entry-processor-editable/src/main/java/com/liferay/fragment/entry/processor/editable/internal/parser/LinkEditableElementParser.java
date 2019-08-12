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

package com.liferay.fragment.entry.processor.editable.internal.parser;

import com.liferay.fragment.entry.processor.editable.EditableFragmentEntryProcessor;
import com.liferay.fragment.entry.processor.editable.parser.EditableElementParser;
import com.liferay.fragment.entry.processor.editable.parser.util.EditableElementParserUtil;
import com.liferay.fragment.exception.FragmentEntryContentException;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import org.jsoup.nodes.Element;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jürgen Kappler
 */
@Component(
	immediate = true, property = "type=link",
	service = EditableElementParser.class
)
public class LinkEditableElementParser implements EditableElementParser {

	@Override
	public JSONObject getAttributes(Element element) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		List<Element> elements = element.getElementsByTag("a");

		if (ListUtil.isEmpty(elements)) {
			return jsonObject;
		}

		Element replaceableElement = elements.get(0);

		String href = replaceableElement.attr("href");

		if (Validator.isNotNull(href)) {
			jsonObject.put("href", href);
		}

		return jsonObject;
	}

	@Override
	public String getFieldTemplate() {
		return _TMPL_LINK_FIELD_TEMPLATE;
	}

	@Override
	public String getValue(Element element) {
		List<Element> elements = element.getElementsByTag("a");

		if (ListUtil.isEmpty(elements)) {
			return StringPool.BLANK;
		}

		Element replaceableElement = elements.get(0);

		return replaceableElement.html();
	}

	@Override
	public void replace(Element element, String value) {
		replace(element, value, null);
	}

	@Override
	public void replace(
		Element element, String value, JSONObject configJSONObject) {

		List<Element> elements = element.getElementsByTag("a");

		if (ListUtil.isEmpty(elements)) {
			return;
		}

		Element replaceableElement = elements.get(0);

		Element bodyElement = EditableElementParserUtil.getDocumentBody(value);

		if (configJSONObject == null) {
			replaceableElement.html(bodyElement.html());

			return;
		}

		EditableElementParserUtil.addAttribute(
			replaceableElement, configJSONObject, "href", "href");
		EditableElementParserUtil.addAttribute(
			replaceableElement, configJSONObject, "target", "target");

		String buttonType = configJSONObject.getString("buttonType");

		if (!buttonType.isEmpty()) {
			for (String className : replaceableElement.classNames()) {
				if (className.startsWith("btn-") ||
					Objects.equals(className, "btn")) {

					replaceableElement.removeClass(className);
				}
			}

			if (Objects.equals(buttonType, "link")) {
				replaceableElement.addClass("link");
			}
			else {
				EditableElementParserUtil.addClass(
					replaceableElement, configJSONObject, "btn btn-",
					"buttonType");
			}
		}

		replaceableElement.html(bodyElement.html());
	}

	@Override
	public void validate(Element element) throws FragmentEntryContentException {
		List<Element> elements = element.getElementsByTag("a");

		if (elements.size() != 1) {
			ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
				"content.Language", getClass());

			throw new FragmentEntryContentException(
				LanguageUtil.format(
					resourceBundle,
					"each-editable-image-element-must-contain-an-a-tag",
					new Object[] {"<em>", "</em>"}, false));
		}
	}

	private static final String _TMPL_LINK_FIELD_TEMPLATE = StringUtil.read(
		EditableFragmentEntryProcessor.class,
		"/META-INF/resources/fragment/entry/processor/editable" +
			"/link_field_template.tmpl");

}