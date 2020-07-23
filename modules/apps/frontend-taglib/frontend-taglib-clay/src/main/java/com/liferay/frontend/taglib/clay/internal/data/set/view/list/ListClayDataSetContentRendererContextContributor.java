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

package com.liferay.frontend.taglib.clay.internal.data.set.view.list;

import com.liferay.frontend.taglib.clay.data.set.ClayDataSetContentRendererContextContributor;
import com.liferay.frontend.taglib.clay.data.set.ClayDataSetDisplayView;
import com.liferay.frontend.taglib.clay.data.set.constants.ClayDataSetConstants;
import com.liferay.frontend.taglib.clay.data.set.view.list.BaseListClayDataSetDisplayView;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marco Leo
 */
@Component(
	property = "clay.data.set.content.renderer.name=" + ClayDataSetConstants.LIST,
	service = ClayDataSetContentRendererContextContributor.class
)
public class ListClayDataSetContentRendererContextContributor
	implements ClayDataSetContentRendererContextContributor {

	@Override
	public Map<String, Object> getContentRendererContext(
		ClayDataSetDisplayView clayDataSetDisplayView, Locale locale) {

		if (clayDataSetDisplayView instanceof BaseListClayDataSetDisplayView) {
			return _serialize(
				(BaseListClayDataSetDisplayView)clayDataSetDisplayView);
		}

		return Collections.emptyMap();
	}

	private Map<String, Object> _serialize(
		BaseListClayDataSetDisplayView baseListClayDataSetDisplayView) {

		Map<String, Object> schema = HashMapBuilder.<String, Object>put(
			"description", baseListClayDataSetDisplayView.getDescription()
		).put(
			"image", baseListClayDataSetDisplayView.getImage()
		).put(
			"sticker", baseListClayDataSetDisplayView.getSticker()
		).put(
			"symbol", baseListClayDataSetDisplayView.getSymbol()
		).put(
			"title",
			() -> {
				String title = baseListClayDataSetDisplayView.getTitle();

				if (title.contains(StringPool.PERIOD)) {
					return StringUtil.split(title, StringPool.PERIOD);
				}

				return title;
			}
		).build();

		return HashMapBuilder.<String, Object>put(
			"schema", schema
		).build();
	}

}