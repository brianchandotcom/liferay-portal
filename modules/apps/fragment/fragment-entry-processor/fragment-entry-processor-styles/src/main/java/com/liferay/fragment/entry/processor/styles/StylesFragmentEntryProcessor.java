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

package com.liferay.fragment.entry.processor.styles;

import com.liferay.fragment.processor.FragmentEntryProcessor;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import org.osgi.service.component.annotations.Component;

/**
 * @author Víctor Galán
 */
@Component(
	property = "fragment.entry.processor.priority:Integer=7",
	service = FragmentEntryProcessor.class
)
public class StylesFragmentEntryProcessor implements FragmentEntryProcessor {

	@Override
	public JSONArray getDataAttributesJSONArray() {
		return JSONUtil.put("lfr-styles");
	}

	@Override
	public JSONObject getDefaultEditableValuesJSONObject(
		String html, String configuration) {

		Document document = _getDocument(html);

		Elements elements = document.select("[data-lfr-styles]");

		if (elements.isEmpty()) {
			return null;
		}

		return JSONUtil.put("hasCommonStyles", true);
	}

	private Document _getDocument(String html) {
		Document document = Jsoup.parseBodyFragment(html);

		Document.OutputSettings outputSettings = new Document.OutputSettings();

		outputSettings.prettyPrint(false);

		document.outputSettings(outputSettings);

		return document;
	}

}