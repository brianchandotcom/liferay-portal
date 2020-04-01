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

package com.liferay.fragment.entry.processor.drop.zone;

import com.liferay.fragment.exception.FragmentEntryContentException;
import com.liferay.fragment.processor.FragmentEntryProcessor;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collection;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true, property = "fragment.entry.processor.priority:Integer=6",
	service = FragmentEntryProcessor.class
)
public class DropZoneFragmentEntryProcessor implements FragmentEntryProcessor {

	@Override
	public JSONArray getAvailableTagsJSONArray() {
		return JSONUtil.put(
			JSONUtil.put(
				"content", "<lfr-drop-zone id=\"\"></lfr-drop-zone>"
			).put(
				"name", "lfr-drop-zone"
			));
	}

	@Override
	public JSONObject getDefaultEditableValuesJSONObject(
		String html, String configuration) {

		Document document = _getDocument(html);

		Elements elements = document.select("lfr-drop-zone");

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (Element element : elements) {
			jsonArray.put(
				JSONUtil.put(
					"id", element.attr("id")
				).put(
					"uuid", StringPool.BLANK
				));
		}

		return JSONUtil.put("dropZones", jsonArray);
	}

	@Override
	public void validateFragmentEntryHTML(String html, String configuration)
		throws PortalException {

		Document document = _getDocument(html);

		Elements elements = document.select("lfr-drop-zone");

		for (Element element : elements) {
			if (Validator.isNull(element.attr("id"))) {
				ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
					"content.Language", getClass());

				throw new FragmentEntryContentException(
					LanguageUtil.get(
						resourceBundle, "drop-zone-id-must-not-be-empty"));
			}
		}

		Stream<Element> uniqueElementsStream = elements.stream();

		Map<String, Long> idsMap = uniqueElementsStream.collect(
			Collectors.groupingBy(
				element -> element.attr("id"), Collectors.counting()));

		Collection<String> ids = idsMap.keySet();

		Stream<String> idsStream = ids.stream();

		idsStream = idsStream.filter(id -> idsMap.get(id) > 1);

		if (idsStream.count() > 0) {
			ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
				"content.Language", getClass());

			throw new FragmentEntryContentException(
				LanguageUtil.get(
					resourceBundle,
					"you-must-define-a-unique-id-for-each-drop-zone"));
		}
	}

	private Document _getDocument(String html) {
		Document document = Jsoup.parseBodyFragment(html);

		Document.OutputSettings outputSettings = new Document.OutputSettings();

		outputSettings.prettyPrint(false);

		document.outputSettings(outputSettings);

		return document;
	}

}