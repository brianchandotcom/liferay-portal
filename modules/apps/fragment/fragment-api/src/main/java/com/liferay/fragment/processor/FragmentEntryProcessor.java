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

package com.liferay.fragment.processor;

import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;

/**
 * @author Pavel Savinov
 */
public interface FragmentEntryProcessor {

	public default void deleteFragmentEntryLinkData(
		FragmentEntryLink fragmentEntryLink) {
	}

	public default JSONArray getAvailableTagsJSONArray() {
		return null;
	}

	public default JSONObject getDefaultEditableValuesJSONObject(
		String html, String configuration) {

		return null;
	}

	public default String processFragmentEntryLinkCSS(
			FragmentEntryLink fragmentEntryLink, String css,
			FragmentEntryProcessorContext fragmentEntryProcessorContext)
		throws PortalException {

		return css;
	}

	public default String processFragmentEntryLinkHTML(
			FragmentEntryLink fragmentEntryLink, String html,
			FragmentEntryProcessorContext fragmentEntryProcessorContext)
		throws PortalException {

		return fragmentEntryLink.getHtml();
	}

	public void validateFragmentEntryHTML(String html, String configuration)
		throws PortalException;

}