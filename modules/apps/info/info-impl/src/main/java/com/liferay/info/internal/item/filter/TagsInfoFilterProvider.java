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

package com.liferay.info.internal.item.filter;

import com.liferay.info.filter.InfoFilterProvider;
import com.liferay.info.filter.TagsInfoFilter;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.osgi.service.component.annotations.Component;

/**
 * @author Pablo Molina
 */
@Component(immediate = true, service = InfoFilterProvider.class)
public class TagsInfoFilterProvider
	implements InfoFilterProvider<TagsInfoFilter> {

	@Override
	public TagsInfoFilter create(Map<String, String[]> values) {
		TagsInfoFilter tagsInfoFilter = new TagsInfoFilter();

		tagsInfoFilter.setTagNames(_getAssetTagNames(values));

		return tagsInfoFilter;
	}

	private String[][] _getAssetTagNames(Map<String, String[]> values) {
		Set<String[]> assetTagIdSet = new HashSet<>();

		for (Map.Entry<String, String[]> entry : values.entrySet()) {
			if (!StringUtil.startsWith(
					entry.getKey(),
					TagsInfoFilter.FILTER_TYPE_NAME + StringPool.UNDERLINE)) {

				continue;
			}

			assetTagIdSet.add(
				ArrayUtil.filter(
					GetterUtil.getStringValues(entry.getValue()),
					tagName -> !Objects.isNull(tagName)));
		}

		return assetTagIdSet.toArray(new String[assetTagIdSet.size()][]);
	}

}