/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.entry.processor.editable.internal.parser;

import com.liferay.fragment.entry.processor.editable.parser.EditableElementParser;
import com.liferay.fragment.entry.processor.editable.parser.util.EditableElementParserUtil;
import com.liferay.fragment.exception.FragmentEntryContentException;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import org.jsoup.nodes.Element;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(property = "type=link", service = EditableElementParser.class)
public class LinkEditableElementParser implements EditableElementParser {

	@Override
	public JSONObject getAttributes(Element element) {
		JSONObject jsonObject = _jsonFactory.createJSONObject();

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
	public String getValue(Element element) {
		List<Element> elements = element.getElementsByTag("a");

		if (ListUtil.isEmpty(elements)) {
			return StringPool.BLANK;
		}

		Element replaceableElement = elements.get(0);

		String html = replaceableElement.html();

		if (Validator.isNull(html.trim())) {
			ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
				"content.Language", getClass());

			return _language.get(resourceBundle, "example-link");
		}

		return html;
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

		String target = configJSONObject.getString("target");

		if (StringUtil.equalsIgnoreCase(target, "_parent") ||
			StringUtil.equalsIgnoreCase(target, "_top")) {

			configJSONObject.put("target", "_self");
		}

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
				_language.format(
					resourceBundle,
					"each-editable-link-element-must-contain-an-a-tag",
					new Object[] {"<em>", "</em>"}, false));
		}
	}

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

}