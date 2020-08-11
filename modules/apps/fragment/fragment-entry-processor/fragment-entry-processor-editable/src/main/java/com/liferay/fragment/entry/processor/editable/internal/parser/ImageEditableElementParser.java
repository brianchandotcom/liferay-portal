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
import com.liferay.fragment.exception.FragmentEntryContentException;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.info.type.WebImage;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Html;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

import org.jsoup.nodes.Element;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	immediate = true, property = "type=image",
	service = EditableElementParser.class
)
public class ImageEditableElementParser implements EditableElementParser {

	@Override
	public String getFieldTemplate() {
		return _TMPL_IMAGE_FIELD_TEMPLATE;
	}

	@Override
	public JSONObject getFieldTemplateConfigJSONObject(
		String fieldName, Locale locale, Object fieldValue) {

		String alt = StringPool.BLANK;

		if (fieldValue == null) {
			alt = StringUtil.replace(
				_TMPL_IMAGE_FIELD_ALT_TEMPLATE, "field_name", fieldName);
		}
		else if (fieldValue instanceof JSONObject) {
			JSONObject fieldValueJSONObject = (JSONObject)fieldValue;

			alt = fieldValueJSONObject.getString("alt");
		}
		else if (fieldValue instanceof WebImage) {
			WebImage webImage = (WebImage)fieldValue;

			Optional<InfoLocalizedValue<String>> altInfoLocalizedValueOptional =
				webImage.getAltInfoLocalizedValueOptional();

			if (altInfoLocalizedValueOptional.isPresent()) {
				InfoLocalizedValue<String> infoLocalizedValue =
					altInfoLocalizedValueOptional.get();

				alt = infoLocalizedValue.getValue();
			}
		}

		return JSONUtil.put("alt", alt);
	}

	@Override
	public String getValue(Element element) {
		List<Element> elements = element.getElementsByTag("img");

		if (ListUtil.isEmpty(elements)) {
			return StringPool.BLANK;
		}

		Element replaceableElement = elements.get(0);

		String src = replaceableElement.attr("src");

		if (Validator.isNull(src.trim())) {
			StringBundler sb = new StringBundler(4);

			sb.append("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAJ");
			sb.append("CAYAAAA7KqwyAAAACXBIWXMAAAsTAAALEwEAmpwYAAAAAXNSR0IArs");
			sb.append("4c6QAAAARnQU1BAACxjwv8YQUAAAAkSURBVHgB7cxBEQAACAIwtH8P");
			sb.append("zw52kxD8OBZgNXsPQUOUwCIgAz0DHTyygaAAAAAASUVORK5CYII=");

			return sb.toString();
		}

		return src;
	}

	@Override
	public String parseFieldValue(Object fieldValue) {
		if (fieldValue instanceof JSONObject) {
			JSONObject jsonObject = (JSONObject)fieldValue;

			return GetterUtil.getString(jsonObject.getString("url"));
		}
		else if (fieldValue instanceof WebImage) {
			WebImage webImage = (WebImage)fieldValue;

			return GetterUtil.getString(webImage.getUrl());
		}
		else {
			return StringPool.BLANK;
		}
	}

	@Override
	public void replace(Element element, String value) {
		replace(element, value, null);
	}

	@Override
	public void replace(
		Element element, String value, JSONObject configJSONObject) {

		List<Element> elements = element.getElementsByTag("img");

		if (ListUtil.isEmpty(elements)) {
			return;
		}

		Element replaceableElement = elements.get(0);

		if (JSONUtil.isValid(value)) {
			try {
				JSONObject jsonObject = JSONFactoryUtil.createJSONObject(value);

				value = jsonObject.getString("url");
			}
			catch (JSONException jsonException) {
				_log.error("Unable to parse JSON value " + value);

				value = StringPool.BLANK;
			}
		}

		value = value.trim();

		if (Validator.isNotNull(value)) {
			replaceableElement.attr("src", _html.unescape(value));
		}

		if (configJSONObject == null) {
			return;
		}

		String alt = configJSONObject.getString("alt");

		if (Validator.isNotNull(alt)) {
			replaceableElement.attr(
				"alt", StringUtil.trim(_html.unescape(alt)));
		}

		String imageLink = configJSONObject.getString("imageLink");

		if (Validator.isNull(imageLink)) {
			return;
		}

		String imageTarget = configJSONObject.getString("imageTarget");

		Element linkElement = new Element("a");

		linkElement.attr("href", imageLink);

		if (Validator.isNotNull(imageTarget)) {
			linkElement.attr("target", imageTarget);
		}

		linkElement.html(element.html());

		element.html(linkElement.outerHtml());
	}

	@Override
	public void validate(Element element) throws FragmentEntryContentException {
		List<Element> elements = element.getElementsByTag("img");

		if (elements.size() != 1) {
			ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
				"content.Language", getClass());

			throw new FragmentEntryContentException(
				LanguageUtil.format(
					resourceBundle,
					"each-editable-image-element-must-contain-an-img-tag",
					new Object[] {"<em>", "</em>"}, false));
		}
	}

	private static final String _TMPL_IMAGE_FIELD_ALT_TEMPLATE =
		StringUtil.read(
			EditableFragmentEntryProcessor.class,
			"/META-INF/resources/fragment/entry/processor/editable" +
				"/image_field_alt_template.tmpl");

	private static final String _TMPL_IMAGE_FIELD_TEMPLATE = StringUtil.read(
		EditableFragmentEntryProcessor.class,
		"/META-INF/resources/fragment/entry/processor/editable" +
			"/image_field_template.tmpl");

	private static final Log _log = LogFactoryUtil.getLog(
		ImageEditableElementParser.class);

	@Reference
	private Html _html;

}