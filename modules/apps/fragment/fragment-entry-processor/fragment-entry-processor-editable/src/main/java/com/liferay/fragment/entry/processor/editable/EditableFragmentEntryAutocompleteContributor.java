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

package com.liferay.fragment.entry.processor.editable;

import com.liferay.fragment.entry.processor.editable.parser.EditableElementParser;
import com.liferay.fragment.processor.FragmentEntryAutocompleteContributor;
import com.liferay.fragment.processor.PortletRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(service = FragmentEntryAutocompleteContributor.class)
public class EditableFragmentEntryAutocompleteContributor
	implements FragmentEntryAutocompleteContributor {

	@Override
	public JSONArray getAvailableTagsJSONArray() {
		JSONArray jsonArray = _jsonFactory.createJSONArray();

		for (String key : _editableElementParserServiceTrackerMap.keySet()) {
			StringBundler sb = new StringBundler(
				2 + (5 * _REQUIRED_ATTRIBUTE_NAMES.length));

			sb.append("<lfr-editable");

			for (String attributeName : _REQUIRED_ATTRIBUTE_NAMES) {
				sb.append(StringPool.SPACE);
				sb.append(attributeName);
				sb.append("=\"");

				String value = StringPool.BLANK;

				if (attributeName.equals("type")) {
					value = key;
				}

				sb.append(value);
				sb.append("\"");
			}

			sb.append("></lfr-editable>");

			jsonArray.put(
				JSONUtil.put(
					"content", sb.toString()
				).put(
					"name", "lfr-editable:" + key
				));
		}

		return jsonArray;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_editableElementParserServiceTrackerMap =
			ServiceTrackerMapFactory.openSingleValueMap(
				bundleContext, EditableElementParser.class, "type");
	}

	@Deactivate
	protected void deactivate() {
		_editableElementParserServiceTrackerMap.close();
	}

	private static final String[] _REQUIRED_ATTRIBUTE_NAMES = {"id", "type"};

	private ServiceTrackerMap<String, EditableElementParser>
		_editableElementParserServiceTrackerMap;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private PortletRegistry _portletRegistry;

}