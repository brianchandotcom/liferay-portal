/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		public FDSResponse(List<FDSDataRow> fdsDataRows, int totalCount) {
			_fdsDataRows = fdsDataRows;
			_totalCount = totalCount;
		}

		@JsonProperty("items")
		private final List<FDSDataRow> _fdsDataRows;

		@JsonProperty("totalCount")
		private final int _totalCount;

	}

}