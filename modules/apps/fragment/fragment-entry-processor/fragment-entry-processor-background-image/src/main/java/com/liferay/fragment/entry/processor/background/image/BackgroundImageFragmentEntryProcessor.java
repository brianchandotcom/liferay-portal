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
import com.liferay.fragment.entry.processor.util.FragmentEntryProcessorUtil;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.processor.FragmentEntryProcessor;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

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
	public String processFragmentEntryLinkHTML(
			FragmentEntryLink fragmentEntryLink, String html, String mode,
			Locale locale, long[] segmentsExperienceIds, long previewClassPK,
			int previewType)
		throws PortalException {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			fragmentEntryLink.getEditableValues());

		Document document = _getDocument(html);

		Map<Long, Map<String, Object>> assetEntriesFieldValues =
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

			if (_fragmentEntryProcessorUtil.isAssetDisplayPage(mode)) {
				value = jsonObject.getString("mappedField");
			}

			if (_fragmentEntryProcessorUtil.isMapped(editableValueJSONObject)) {
				Object fieldValue = _fragmentEntryProcessorUtil.getMappedValue(
					jsonObject, assetEntriesFieldValues, mode, locale,
					previewClassPK, previewType);

				if (fieldValue != null) {
					value = String.valueOf(fieldValue);
				}
			}

			if (Validator.isNull(value)) {
				value = _fragmentEntryProcessorUtil.getEditableValue(
					editableValueJSONObject, locale, segmentsExperienceIds);
			}

			element.attr(
				"style",
				"background-image: url(" + value + "); background-size: cover");
		}

		if (Objects.equals(
				mode, FragmentEntryLinkConstants.ASSET_DISPLAY_PAGE) ||
			Objects.equals(mode, FragmentEntryLinkConstants.VIEW)) {

			for (Element element :
					document.select("[data-lfr-background-image-id]")) {

				element.removeAttr("data-lfr-background-image-id");
			}
		}

		Element bodyElement = document.body();

		return bodyElement.html();
	}

	@Override
	public void validateFragmentEntryHTML(String html) {
	}

	private Document _getDocument(String html) {
		Document document = Jsoup.parseBodyFragment(html);

		Document.OutputSettings outputSettings = new Document.OutputSettings();

		outputSettings.prettyPrint(false);

		document.outputSettings(outputSettings);

		return document;
	}

	@Reference
	private FragmentEntryProcessorUtil _fragmentEntryProcessorUtil;

}