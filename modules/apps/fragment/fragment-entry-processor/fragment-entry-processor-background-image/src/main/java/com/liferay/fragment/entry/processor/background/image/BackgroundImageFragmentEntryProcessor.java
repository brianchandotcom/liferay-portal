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

package com.liferay.fragment.entry.processor.background.image;

import com.liferay.fragment.constants.FragmentEntryLinkConstants;
import com.liferay.fragment.entry.processor.helper.FragmentEntryProcessorHelper;
import com.liferay.fragment.exception.FragmentEntryContentException;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.FragmentEntryProcessor;
import com.liferay.fragment.processor.FragmentEntryProcessorContext;
import com.liferay.info.type.WebImage;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true, property = "fragment.entry.processor.priority:Integer=5",
	service = FragmentEntryProcessor.class
)
public class BackgroundImageFragmentEntryProcessor
	implements FragmentEntryProcessor {

	@Override
	public JSONObject getDefaultEditableValuesJSONObject(
		String html, String configuration) {

		JSONObject defaultEditableValuesJSONObject =
			JSONFactoryUtil.createJSONObject();

		Document document = _getDocument(html);

		for (Element element :
				document.select("[data-lfr-background-image-id]")) {

			String id = element.attr("data-lfr-background-image-id");

			defaultEditableValuesJSONObject.put(
				id, JSONFactoryUtil.createJSONObject());
		}

		return defaultEditableValuesJSONObject;
	}

	@Override
	public String processFragmentEntryLinkHTML(
			FragmentEntryLink fragmentEntryLink, String html,
			FragmentEntryProcessorContext fragmentEntryProcessorContext)
		throws PortalException {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			fragmentEntryLink.getEditableValues());

		Document document = _getDocument(html);

		Map<Long, Map<String, Object>> infoDisplaysFieldValues =
			new HashMap<>();

		for (Element element :
				document.select("[data-lfr-background-image-id]")) {

			String id = element.attr("data-lfr-background-image-id");

			Class<?> clazz = getClass();

			JSONObject editableValuesJSONObject = jsonObject.getJSONObject(
				clazz.getName());

			if ((editableValuesJSONObject == null) ||
				!editableValuesJSONObject.has(id)) {

				continue;
			}

			JSONObject editableValueJSONObject =
				editableValuesJSONObject.getJSONObject(id);

			String value = StringPool.BLANK;

			if (_fragmentEntryProcessorHelper.isAssetDisplayPage(
					fragmentEntryProcessorContext.getMode())) {

				String mappedField = editableValueJSONObject.getString(
					"mappedField");

				Optional<Map<String, Object>> fieldValuesOptional =
					fragmentEntryProcessorContext.getFieldValuesOptional();

				Map<String, Object> fieldValues = fieldValuesOptional.orElse(
					new HashMap<>());

				value = _getImageURL(fieldValues.get(mappedField));
			}

			if (_fragmentEntryProcessorHelper.isMapped(
					editableValueJSONObject)) {

				Object fieldValue =
					_fragmentEntryProcessorHelper.getMappedValue(
						editableValueJSONObject, infoDisplaysFieldValues,
						fragmentEntryProcessorContext);

				if (fieldValue != null) {
					value = _getImageURL(fieldValue);
				}
			}

			if (Validator.isNull(value)) {
				value = _fragmentEntryProcessorHelper.getEditableValue(
					editableValueJSONObject,
					fragmentEntryProcessorContext.getLocale());
			}

			if (Validator.isNotNull(value)) {
				long fileEntryId = 0;

				if (JSONUtil.isValid(value)) {
					JSONObject valueJSONObject =
						JSONFactoryUtil.createJSONObject(value);

					fileEntryId = valueJSONObject.getLong("fileEntryId");

					value = valueJSONObject.getString("url", value);
				}

				StringBundler sb = new StringBundler(6);

				sb.append("background-image: url(");
				sb.append(value);
				sb.append("); background-size: cover;");

				if (fileEntryId > 0) {
					sb.append("--background-image-file-entry-id:");
					sb.append(fileEntryId);
					sb.append(StringPool.SEMICOLON);
				}

				element.attr("style", sb.toString());
			}
		}

		if (Objects.equals(
				fragmentEntryProcessorContext.getMode(),
				FragmentEntryLinkConstants.ASSET_DISPLAY_PAGE) ||
			Objects.equals(
				fragmentEntryProcessorContext.getMode(),
				FragmentEntryLinkConstants.VIEW)) {

			for (Element element :
					document.select("[data-lfr-background-image-id]")) {

				element.removeAttr("data-lfr-background-image-id");
			}
		}

		Element bodyElement = document.body();

		return bodyElement.html();
	}

	@Override
	public void validateFragmentEntryHTML(String html, String configuration)
		throws PortalException {

		Document document = _getDocument(html);

		Elements elements = document.select("[data-lfr-background-image-id]");

		Set<String> ids = new HashSet<>();

		for (Element element : elements) {
			if (ids.add(element.attr("data-lfr-background-image-id"))) {
				continue;
			}

			ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
				"content.Language", getClass());

			throw new FragmentEntryContentException(
				LanguageUtil.get(
					resourceBundle,
					"you-must-define-a-unique-id-for-each-background-image-" +
						"element"));
		}
	}

	private Document _getDocument(String html) {
		Document document = Jsoup.parseBodyFragment(html);

		Document.OutputSettings outputSettings = new Document.OutputSettings();

		outputSettings.prettyPrint(false);

		document.outputSettings(outputSettings);

		return document;
	}

	private String _getImageURL(Object fieldValue) {
		if (fieldValue instanceof JSONObject) {
			JSONObject fieldValueJSONObject = (JSONObject)fieldValue;

			return fieldValueJSONObject.getString("url");
		}

		if (fieldValue instanceof WebImage) {
			WebImage webImage = (WebImage)fieldValue;

			return String.valueOf(webImage.toJSONObject());
		}

		return String.valueOf(fieldValue);
	}

	@Reference
	private FragmentEntryProcessorHelper _fragmentEntryProcessorHelper;

}