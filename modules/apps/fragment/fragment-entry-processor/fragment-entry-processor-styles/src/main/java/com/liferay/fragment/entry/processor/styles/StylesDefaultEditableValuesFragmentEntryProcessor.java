/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.entry.processor.styles;

import com.liferay.fragment.entry.processor.constants.FragmentEntryProcessorConstants;
import com.liferay.fragment.processor.DefaultEditableValuesFragmentEntryProcessor;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import org.osgi.service.component.annotations.Component;

/**
 * @author Víctor Galán
 */
@Component(
	property = "fragment.entry.processor.priority:Integer=7",
	service = DefaultEditableValuesFragmentEntryProcessor.class
)
public class StylesDefaultEditableValuesFragmentEntryProcessor
	implements DefaultEditableValuesFragmentEntryProcessor {

	@Override
	public JSONObject getDefaultEditableValuesJSONObject(
		String configuration, Document document) {

		Elements elements = document.select("[data-lfr-styles]");

		if (elements.isEmpty()) {
			return null;
		}

		return JSONUtil.put("hasCommonStyles", true);
	}

	@Override
	public String getKey() {
		return FragmentEntryProcessorConstants.
			KEY_STYLES_FRAGMENT_ENTRY_PROCESSOR;
	}

}