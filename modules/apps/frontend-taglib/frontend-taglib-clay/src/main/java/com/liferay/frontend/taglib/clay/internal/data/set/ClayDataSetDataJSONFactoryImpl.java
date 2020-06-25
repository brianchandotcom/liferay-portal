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

package com.liferay.frontend.taglib.clay.internal.data.set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import com.liferay.frontend.taglib.clay.data.set.ClayDataSetActionProvider;
import com.liferay.frontend.taglib.clay.data.set.ClayDataSetActionProviderRegistry;
import com.liferay.frontend.taglib.clay.data.set.ClayDataSetDataJSONFactory;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(service = ClayDataSetDataJSONFactory.class)
public class ClayDataSetDataJSONFactoryImpl
	implements ClayDataSetDataJSONFactory {

	@Override
	public String create(
			long groupId, String tableName, List<Object> items,
			HttpServletRequest httpServletRequest)
		throws Exception {

		List<ClayDataSetDataRow> clayDataSetDataRows = _getClayTableRows(
			items, tableName, httpServletRequest, groupId);

		return _objectMapper.writeValueAsString(clayDataSetDataRows);
	}

	@Override
	public String create(
			long groupId, String tableName, List<Object> items, int itemsCount,
			HttpServletRequest httpServletRequest)
		throws Exception {

		ClayDataSetResponse clayDataSetResponse = new ClayDataSetResponse(
			_getClayTableRows(items, tableName, httpServletRequest, groupId),
			itemsCount);

		return _objectMapper.writeValueAsString(clayDataSetResponse);
	}

	private List<ClayDataSetDataRow> _getClayTableRows(
			List<Object> items, String tableName,
			HttpServletRequest httpServletRequest, long groupId)
		throws Exception {

		List<ClayDataSetDataRow> clayDataSetDataRows = new ArrayList<>();

		List<ClayDataSetActionProvider> clayDataSetActionProviders =
			_clayDataSetActionProviderRegistry.getClayDataSetActionProviders(
				tableName);

		for (Object item : items) {
			ClayDataSetDataRow clayDataSetDataRow = new ClayDataSetDataRow(
				item);

			if (clayDataSetActionProviders != null) {
				for (ClayDataSetActionProvider clayDataSetActionProvider :
						clayDataSetActionProviders) {

					List<DropdownItem> actionDropdownItems =
						clayDataSetActionProvider.getDropdownItems(
							httpServletRequest, groupId, item);

					if (actionDropdownItems != null) {
						clayDataSetDataRow.addActionDropdownItems(
							actionDropdownItems);
					}
				}
			}

			clayDataSetDataRows.add(clayDataSetDataRow);
		}

		return clayDataSetDataRows;
	}

	private static final ObjectMapper _objectMapper = new ObjectMapper() {
		{
			configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
			disable(SerializationFeature.INDENT_OUTPUT);
		}
	};

	@Reference
	private ClayDataSetActionProviderRegistry
		_clayDataSetActionProviderRegistry;

	private class ClayDataSetResponse {

		public ClayDataSetResponse(
			List<ClayDataSetDataRow> clayDataSetRows, int total) {

			_clayDataSetRows = clayDataSetRows;
			_total = total;
		}

		@JsonProperty("rows")
		private final List<ClayDataSetDataRow> _clayDataSetRows;

		@JsonProperty("total")
		private final int _total;

	}

}