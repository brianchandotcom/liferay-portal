/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.delivery.internal.dto.v1_0.mapper;

import com.liferay.headless.delivery.dto.v1_0.PageCollectionItemDefinition;
import com.liferay.headless.delivery.dto.v1_0.PageElement;
import com.liferay.layout.util.structure.CollectionItemLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jürgen Kappler
 */
@Component(service = LayoutStructureItemMapper.class)
public class CollectionItemLayoutStructureItemMapper
	implements LayoutStructureItemMapper {

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

				for (String key : keys) {
					put(key, jsonObject.get(key));
				}
			}
		};
	}

}