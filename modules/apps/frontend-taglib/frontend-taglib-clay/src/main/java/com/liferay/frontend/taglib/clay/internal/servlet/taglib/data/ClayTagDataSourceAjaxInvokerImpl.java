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

package com.liferay.frontend.taglib.clay.internal.servlet.taglib.data;

import com.liferay.frontend.taglib.clay.internal.ClayTagDataSourceProvider;
import com.liferay.frontend.taglib.clay.servlet.taglib.data.ClayTagDataSource;
import com.liferay.frontend.taglib.clay.servlet.taglib.data.ClayTagDataSourceAjaxInvoker;
import com.liferay.frontend.taglib.clay.servlet.taglib.data.Pagination;
import com.liferay.portal.kernel.json.JSONFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Iván Zaera Avellón
 */
@Component(service = ClayTagDataSourceAjaxInvoker.class)
public class ClayTagDataSourceAjaxInvokerImpl
	implements ClayTagDataSourceAjaxInvoker {

	@Override
	public String getItems(
			HttpServletRequest request, String dataSourceKey, int page,
			int pageSize)
		throws IllegalArgumentException {

		ClayTagDataSource<?> clayTagDataSource =
			ClayTagDataSourceProvider.getClayTagDataSource(dataSourceKey);

		if (clayTagDataSource == null) {
			return null;
		}

		Pagination pagination = new Pagination(pageSize, page);

		List<?> items = clayTagDataSource.getItems(request, pagination);

		Map<String, Object> map = new HashMap<>();

		map.put("items", items);

		return _jsonFactory.looseSerializeDeep(map);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void setClayTagDataSource(
		ClayTagDataSource clayTagDataSource, Map properties) {

		_clayTagDataSources.put(
			(String)properties.get("clay.tag.data.source.key"),
			clayTagDataSource);
	}

	protected void unsetClayTagDataSource(
		ClayTagDataSource clayTagDataSource, Map properties) {

		_clayTagDataSources.remove(
			(String)properties.get("clay.tag.data.source.key"));
	}

	private final Map<String, ClayTagDataSource> _clayTagDataSources =
		new ConcurrentHashMap<>();

	@Reference
	private JSONFactory _jsonFactory;

}