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

package com.liferay.commerce.frontend.internal.clay.data.set;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import com.liferay.commerce.frontend.clay.data.set.ClayDataSetAction;
import com.liferay.commerce.frontend.clay.data.set.ClayDataSetActionProvider;
import com.liferay.commerce.frontend.clay.data.set.ClayDataSetActionProviderRegistry;
import com.liferay.commerce.frontend.clay.data.set.ClayDataSetDataJSONBuilder;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(service = ClayDataSetDataJSONBuilder.class)
public class ClayDataSetDataJSONBuilderImpl
	implements ClayDataSetDataJSONBuilder {

	@Override
	public String build(
			long groupId, String tableName, List<Object> items,
			HttpServletRequest httpServletRequest)
		throws Exception {

		List<ClayDataSetDataRow> clayDataSetDataRows = _getClayTableRows(
			items, tableName, httpServletRequest, groupId);

		return _OBJECT_MAPPER.writeValueAsString(clayDataSetDataRows);
	}

	@Override
	public String build(
			long groupId, String tableName, List<Object> items, int totalItems,
			HttpServletRequest httpServletRequest)
		throws Exception {

		ClayDataSetResponse clayDataSetResponse = new ClayDataSetResponse(
			_getClayTableRows(items, tableName, httpServletRequest, groupId),
			totalItems);

		return _OBJECT_MAPPER.writeValueAsString(clayDataSetResponse);
	}

	private List<ClayDataSetDataRow> _getClayTableRows(
			List<Object> items, String tableName,
			HttpServletRequest httpServletRequest, long groupId)
		throws PortalException {

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

					List<ClayDataSetAction> clayDataSetActions =
						clayDataSetActionProvider.clayDataSetActions(
							httpServletRequest, groupId, item);

					if (clayDataSetActions != null) {
						clayDataSetDataRow.addActionItems(clayDataSetActions);
					}
				}
			}

			clayDataSetDataRows.add(clayDataSetDataRow);
		}

		return clayDataSetDataRows;
	}

	private static final ObjectMapper _OBJECT_MAPPER = new ObjectMapper() {
		{
			configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
			disable(SerializationFeature.INDENT_OUTPUT);
		}
	};

	@Reference
	private ClayDataSetActionProviderRegistry
		_clayDataSetActionProviderRegistry;

}