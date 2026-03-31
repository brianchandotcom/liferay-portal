/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.processor;

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;

/**
 * @author Pavel Savinov
 */
public interface FragmentEntryProcessor {

	public default JSONArray getAvailableTagsJSONArray() {
		return null;
	}

	public default JSONArray getDataAttributesJSONArray() {
		return null;
	}

	public default JSONObject getDefaultEditableValuesJSONObject(
		JSONObject configurationJSONObject, String html) {

		return null;
	}

	public default String processFragmentEntryLinkCSS(
			String css, FragmentEntryLink fragmentEntryLink,
			FragmentEntryProcessorContext fragmentEntryProcessorContext)
		throws PortalException {

		return css;
	}

	public default String processFragmentEntryLinkHTML(
			FragmentEntryLink fragmentEntryLink,
			FragmentEntryProcessorContext fragmentEntryProcessorContext,
			String html)
		throws PortalException {

		return html;
	}

	public default String processFragmentEntryLinkHTML(
			JSONObject editableValuesJSONObject,
			FragmentEntryLink fragmentEntryLink,
			FragmentEntryProcessorContext fragmentEntryProcessorContext,
			String html)
		throws PortalException {

		return processFragmentEntryLinkHTML(
			fragmentEntryLink, fragmentEntryProcessorContext, html);
	}

}