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

package com.liferay.fragment.entry.processor.portlet;

import com.liferay.fragment.processor.FragmentEntryAutocompleteContributor;
import com.liferay.fragment.processor.PortletRegistry;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(service = FragmentEntryAutocompleteContributor.class)
public class PortletFragmentEntryAutocompleteContributor
	implements FragmentEntryAutocompleteContributor {

	@Override
	public JSONArray getAvailableTagsJSONArray() {
		JSONArray jsonArray = _jsonFactory.createJSONArray();

		for (String alias : _portletRegistry.getPortletAliases()) {
			jsonArray.put(
				JSONUtil.put(
					"content",
					StringBundler.concat(
						"<lfr-widget-", alias, "></lfr-widget-", alias, ">")
				).put(
					"name", "lfr-widget-" + alias
				));
		}

		return jsonArray;
	}

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private PortletRegistry _portletRegistry;

}