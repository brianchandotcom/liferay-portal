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
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Arrays;
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

	@Override
	public List<Map<String, Object>> getItems(
		HttpServletRequest httpServletRequest) {

		return Arrays.asList(
			_getItem("Blueberry", 57, 100), _getItem("Strawberry", 33, 100),
			_getItem("Raspberry", 53, 100));
	}

	private Map<String, Object> _getItem(
		String name, int calories, int portion) {

		return HashMapBuilder.<String, Object>put(
			"calories", calories
		).put(
			"name", name
		).put(
			"portion", portion
		).build();
	}

}