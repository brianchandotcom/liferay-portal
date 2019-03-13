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

package com.liferay.frontend.taglib.clay.sample.web.internal.data;

import com.liferay.frontend.taglib.clay.servlet.taglib.data.ClayTagDataSource;
import com.liferay.frontend.taglib.clay.servlet.taglib.data.Pagination;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringBundler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Rodolfo Roza Miranda
 */
@Component(
	immediate = true, property = "clay.tag.data.source.key=SampleTable",
	service = ClayTagDataSource.class
)
public class SampleTableClayTagDataSource
	implements ClayTagDataSource<Map<String, Object>> {

	public SampleTableClayTagDataSource() {
		for (int i = 0; i < (90 + Math.random() * 20); i++) {
			String name = StringBundler.concat(
				_getQualifier(), StringPool.SPACE, _getColor(), "berry");

			int calories = 1 + (int)(Math.random() * 450);

			int portion = 90 + (int)(Math.random() * 20);

			_items.add(_getItem(name, calories, portion));
		}
	}

	@Override
	public List<Map<String, Object>> getItems(
		HttpServletRequest request, Pagination pagination) {

		return _items.subList(
			pagination.getStartPosition(),
			Math.min(pagination.getEndPosition(), _items.size()));
	}

	@Override
	public int getTotalItemsCount() {
		return _items.size();
	}

	private String _getColor() {
		int i = (int)(Math.random() * _COLOR.length) % _COLOR.length;

		return _COLOR[i];
	}

	private Map<String, Object> _getItem(
		String name, int calories, int portion) {

		Map<String, Object> item = new HashMap<>();

		item.put("calories", calories);
		item.put("name", name);
		item.put("portion", portion);

		return item;
	}

	private String _getQualifier() {
		int i = (int)(Math.random() * _QUALIFIER.length) % _QUALIFIER.length;

		return _QUALIFIER[i];
	}

	private static final String[] _COLOR = {
		"Blue", "Red", "Yellow", "Green", "Purple", "Orange", "Black", "White",
		"Ochre", "Salmon", "Pink", "Lime", "Grey", "Silver", "Golden", "Rasp",
		"Straw"
	};

	private static final String[] _QUALIFIER = {
		"Soft", "Dried", "Moist", "Wild", "Domesticated", "Round", "Square",
		"Big", "Small", "Huge", "Tiny", "Bright", "Dusty"
	};

	private final List<Map<String, Object>> _items = new ArrayList<>();

}