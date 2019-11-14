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

package com.liferay.change.tracking.web.internal.display;

import com.liferay.change.tracking.display.CTDisplayRenderer;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.util.HtmlUtil;

import java.io.Writer;

import java.util.Locale;
import java.util.Map;
import java.util.function.Function;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Samuel Trong Tran
 */
public class CTModelDisplayRendererAdapter<T extends CTModel<T>>
	implements CTDisplayRenderer<T> {

	@SuppressWarnings("unchecked")
	public static <T extends CTModel<T>> CTDisplayRenderer<T> getInstance() {
		return (CTDisplayRenderer<T>)_INSTANCE;
	}

	@Override
	public String getEditURL(HttpServletRequest httpServletRequest, T ctModel) {
		return null;
	}

	@Override
	public Class<T> getModelClass() {
		return null;
	}

	@Override
	public String getTypeName(Locale locale) {
		return null;
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, T ctModel)
		throws Exception {

		Writer writer = httpServletResponse.getWriter();

		writer.write("<div class=\"table-responsive\"><table class=\"table\">");

		Map<String, Function<T, Object>> attributeGetterFunctions =
			ctModel.getAttributeGetterFunctions();

		for (Map.Entry<String, Function<T, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			Function<T, Object> function = entry.getValue();

			writer.write("<tr><td>");
			writer.write(entry.getKey());
			writer.write("</td><td>");

			Object attributeValue = function.apply(ctModel);

			if (attributeValue instanceof String) {
				writer.write(HtmlUtil.escape(attributeValue.toString()));
			}
			else {
				writer.write(String.valueOf(attributeValue));
			}

			writer.write("</td></tr>");
		}

		writer.write("</table></div>");
	}

	private CTModelDisplayRendererAdapter() {
	}

	private static final CTDisplayRenderer<?> _INSTANCE =
		new CTModelDisplayRendererAdapter<>();

}