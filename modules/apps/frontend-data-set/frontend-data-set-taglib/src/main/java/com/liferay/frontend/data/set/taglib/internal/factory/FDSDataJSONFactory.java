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

package com.liferay.frontend.data.set.taglib.internal.factory;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import com.liferay.frontend.data.set.model.FDSDataRow;
import com.liferay.frontend.data.set.provider.FDSActionProvider;
import com.liferay.frontend.data.set.provider.FDSActionProviderRegistry;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(service = FDSDataJSONFactory.class)
public class FDSDataJSONFactory {

	public String create(
			long groupId, String tableName, List<Object> items,
			HttpServletRequest httpServletRequest)
		throws Exception {

		List<FDSDataRow> fdsDataRows = _getFDSTableRows(
			items, tableName, httpServletRequest, groupId);

		return _objectMapper.writeValueAsString(fdsDataRows);
	}

	public String create(
			long groupId, String tableName, List<Object> items, int itemsCount,
			HttpServletRequest httpServletRequest)
		throws Exception {

		FDSResponse fdsResponse = new FDSResponse(
			_getFDSTableRows(items, tableName, httpServletRequest, groupId),
			itemsCount);

		return _objectMapper.writeValueAsString(fdsResponse);
	}

	private List<FDSDataRow> _getFDSTableRows(
			List<Object> items, String tableName,
			HttpServletRequest httpServletRequest, long groupId)
		throws Exception {

		List<FDSDataRow> fdsDataRows = new ArrayList<>();

		List<FDSActionProvider> fdsActionProviders =
			_fdsActionProviderRegistry.getFDSActionProviders(tableName);

		for (Object item : items) {
			FDSDataRow fdsDataRow = new FDSDataRow(item);

			if (fdsActionProviders != null) {
				for (FDSActionProvider fdsActionProvider : fdsActionProviders) {
					List<DropdownItem> actionDropdownItems =
						fdsActionProvider.getDropdownItems(
							groupId, httpServletRequest, item);

					if (actionDropdownItems != null) {
						fdsDataRow.addActionDropdownItems(actionDropdownItems);
					}
				}
			}

			fdsDataRows.add(fdsDataRow);
		}

		return fdsDataRows;
	}

	private static final ObjectMapper _objectMapper = new ObjectMapper() {
		{
			configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
			disable(SerializationFeature.INDENT_OUTPUT);
		}
	};

	@Reference
	private FDSActionProviderRegistry _fdsActionProviderRegistry;

	private class FDSResponse {

		public FDSResponse(List<FDSDataRow> fdsRows, int totalCount) {
			_fdsRows = fdsRows;
			_totalCount = totalCount;
		}

		@JsonProperty("items")
		private final List<FDSDataRow> _fdsRows;

		@JsonProperty("totalCount")
		private final int _totalCount;

	}

}