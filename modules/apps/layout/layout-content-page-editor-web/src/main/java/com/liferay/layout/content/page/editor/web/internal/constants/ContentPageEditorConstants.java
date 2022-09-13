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

package com.liferay.layout.content.page.editor.web.internal.constants;

import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Pavel Savinov
 */
public class ContentPageEditorConstants {

	public static final String TYPE_COMPOSITION = "composition";

	public static final Map<String, List<Map<String, Object>>>
		layoutElementMapsListMap =
			LinkedHashMapBuilder.<String, List<Map<String, Object>>>put(
				"layout-elements",
				() -> {
					List<Map<String, Object>> layoutElementMapsList =
						new LinkedList<>();

					layoutElementMapsList.add(
						HashMapBuilder.<String, Object>put(
							"fragmentEntryKey", "container"
						).put(
							"icon", "container"
						).put(
							"itemType", "container"
						).put(
							"languageKey", "container"
						).build());

					layoutElementMapsList.add(
						HashMapBuilder.<String, Object>put(
							"fragmentEntryKey", "row"
						).put(
							"icon", "table"
						).put(
							"itemType", "row"
						).put(
							"languageKey", "grid"
						).build());

					return layoutElementMapsList;
				}
			).put(
				"INPUTS",
				ListUtil.fromArray(
					HashMapBuilder.<String, Object>put(
						"fragmentEntryKey", "form"
					).put(
						"icon", "container"
					).put(
						"itemType", "form"
					).put(
						"languageKey", "form-container"
					).build())
			).put(
				"content-display",
				ListUtil.fromArray(
					HashMapBuilder.<String, Object>put(
						"fragmentEntryKey", "collection-display"
					).put(
						"icon", "list"
					).put(
						"itemType", "collection"
					).put(
						"languageKey", "collection-display"
					).build())
			).build();

}