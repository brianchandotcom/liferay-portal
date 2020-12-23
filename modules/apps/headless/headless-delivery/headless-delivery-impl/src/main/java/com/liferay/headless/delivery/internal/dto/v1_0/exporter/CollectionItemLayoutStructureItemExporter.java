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

package com.liferay.headless.delivery.internal.dto.v1_0.exporter;

import com.liferay.headless.delivery.dto.v1_0.PageCollectionItemDefinition;
import com.liferay.headless.delivery.dto.v1_0.PageElement;
import com.liferay.layout.util.structure.CollectionItemLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jürgen Kappler
 */
@Component(service = LayoutStructureItemExporter.class)
public class CollectionItemLayoutStructureItemExporter
	implements LayoutStructureItemExporter {

	@Override
	public String getClassName() {
		return CollectionItemLayoutStructureItem.class.getName();
	}

	@Override
	public PageElement getPageElement(
		long groupId, LayoutStructureItem layoutStructureItem,
		boolean saveInlineContent, boolean saveMappingConfiguration) {

		CollectionItemLayoutStructureItem collectionItemLayoutStructureItem =
			(CollectionItemLayoutStructureItem)layoutStructureItem;

		return new PageElement() {
			{
				definition = new PageCollectionItemDefinition() {
					{
						collectionItemConfig = _getConfigAsMap(
							collectionItemLayoutStructureItem.
								getItemConfigJSONObject());
					}
				};
				type = Type.COLLECTION_ITEM;
			}
		};
	}

	private Map<String, Object> _getConfigAsMap(JSONObject jsonObject) {
		if (jsonObject == null) {
			return null;
		}

		return new HashMap<String, Object>() {
			{
				Set<String> keys = jsonObject.keySet();

				Iterator<String> iterator = keys.iterator();

				while (iterator.hasNext()) {
					String key = iterator.next();

					put(key, jsonObject.get(key));
				}
			}
		};
	}

}