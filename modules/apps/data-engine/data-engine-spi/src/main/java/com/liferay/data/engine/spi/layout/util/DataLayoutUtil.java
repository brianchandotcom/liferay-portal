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

package com.liferay.data.engine.spi.layout.util;

import com.liferay.data.engine.spi.field.type.util.LocalizedValueUtil;
import com.liferay.data.engine.spi.layout.SPIDataLayout;
import com.liferay.data.engine.spi.layout.SPIDataLayoutColumn;
import com.liferay.data.engine.spi.layout.SPIDataLayoutPage;
import com.liferay.data.engine.spi.layout.SPIDataLayoutRow;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;

/**
 * @author Leonardo Barros
 */
public class DataLayoutUtil {

	public static SPIDataLayout toSPIDataLayout(String json) throws Exception {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(json);

		return new SPIDataLayout() {
			{
				setDataLayoutPages(
					JSONUtil.toArray(
						jsonObject.getJSONArray("pages"),
						pageJSONObject -> _toSPIDataLayoutPage(pageJSONObject),
						SPIDataLayoutPage.class));
				setDefaultLanguageId(jsonObject.getString("defaultLanguageId"));
				setPaginationMode(jsonObject.getString("paginationMode"));
			}
		};
	}

	private static SPIDataLayoutColumn _toSPIDataLayoutColumn(
		JSONObject jsonObject) {

		return new SPIDataLayoutColumn() {
			{
				setColumnSize(jsonObject.getInt("size"));
				setFieldNames(
					JSONUtil.toStringArray(jsonObject.getJSONArray("fields")));
			}
		};
	}

	private static SPIDataLayoutPage _toSPIDataLayoutPage(JSONObject jsonObject)
		throws Exception {

		return new SPIDataLayoutPage() {
			{
				setDataLayoutRows(
					JSONUtil.toArray(
						jsonObject.getJSONArray("rows"),
						rowJSONObject -> _toSPIDataLayoutRow(rowJSONObject),
						SPIDataLayoutRow.class));
				setDescription(
					LocalizedValueUtil.toLocalizedValues(
						jsonObject.getJSONObject("description")));
				setTitle(
					LocalizedValueUtil.toLocalizedValues(
						jsonObject.getJSONObject("title")));
			}
		};
	}

	private static SPIDataLayoutRow _toSPIDataLayoutRow(JSONObject jsonObject)
		throws Exception {

		return new SPIDataLayoutRow() {
			{
				setDataLayoutColums(
					JSONUtil.toArray(
						jsonObject.getJSONArray("columns"),
						columnJSONObject -> _toSPIDataLayoutColumn(
							columnJSONObject),
						SPIDataLayoutColumn.class));
			}
		};
	}

}