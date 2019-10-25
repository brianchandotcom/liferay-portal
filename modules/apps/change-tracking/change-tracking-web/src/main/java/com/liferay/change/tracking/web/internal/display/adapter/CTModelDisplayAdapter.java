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

package com.liferay.change.tracking.web.internal.display.adapter;

import com.liferay.change.tracking.display.CTDisplay;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.util.HtmlUtil;

import java.io.Writer;

import java.util.Map;
import java.util.function.Function;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Samuel Trong Tran
 */
public class CTModelDisplayAdapter<T extends BaseModel<T>>
	implements CTDisplay<T> {

	public static <T extends BaseModel<T>> CTDisplay<T> getInstance() {
		return (CTDisplay<T>)_INSTANCE;
	}

	@Override
	public Class<T> getModelClass() {
		return null;
	}

	@Override
	public void render(
			HttpServletRequest request, HttpServletResponse response,
			T baseModel)
		throws Exception {

		Writer writer = response.getWriter();

		writer.append("<table>");

		Map<String, Function<T, Object>> attributeGetterFunctions =
			baseModel.getAttributeGetterFunctions();

		for (Map.Entry<String, Function<T, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			Function<T, Object> function = entry.getValue();

			writer.append("<tr><td>");
			writer.append(entry.getKey());
			writer.append("</td><td>");
			writer.append(
				HtmlUtil.escape(String.valueOf(function.apply(baseModel))));
			writer.append("</td></tr>");
		}

		writer.append("</table>");
	}

	private CTModelDisplayAdapter() {
	}

	private static final CTDisplay<?> _INSTANCE = new CTModelDisplayAdapter<>();

}