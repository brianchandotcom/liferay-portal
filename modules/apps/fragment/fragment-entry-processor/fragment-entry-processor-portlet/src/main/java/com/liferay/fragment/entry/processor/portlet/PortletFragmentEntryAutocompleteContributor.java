/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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