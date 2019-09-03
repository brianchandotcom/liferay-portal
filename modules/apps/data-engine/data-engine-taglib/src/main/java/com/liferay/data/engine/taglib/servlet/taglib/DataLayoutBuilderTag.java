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

package com.liferay.data.engine.taglib.servlet.taglib;

import com.liferay.data.engine.taglib.servlet.taglib.base.BaseDataLayoutBuilderTag;
import com.liferay.data.engine.taglib.servlet.taglib.util.DataLayoutTaglibUtil;

import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

/**
 * @author Jeyvison Nascimento
 * @author Leonardo Barros
 */
public class DataLayoutBuilderTag extends BaseDataLayoutBuilderTag {

	@Override
	public int doStartTag() throws JspException {
		int result = super.doStartTag();

		Set<Locale> availableLocales = DataLayoutTaglibUtil.getAvailableLocales(
			getDataLayoutId(), request);

		setNamespacedAttribute(
			request, "availableLocales",
			availableLocales.toArray(new Locale[0]));
		setNamespacedAttribute(
			request, "dataLayout",
			DataLayoutTaglibUtil.getDataLayoutJSONObject(
				availableLocales, getDataLayoutId(), request,
				(HttpServletResponse)pageContext.getResponse()));

		setNamespacedAttribute(
			request, "dataLayoutBuilderModule",
			DataLayoutTaglibUtil.resolveModule(
				"data-engine-taglib/data_layout_builder/js" +
					"/DataLayoutBuilder.es"));
		setNamespacedAttribute(
			request, "fieldTypes",
			DataLayoutTaglibUtil.getFieldTypesJSONArray(request));
		setNamespacedAttribute(
			request, "fieldTypesModules",
			DataLayoutTaglibUtil.resolveFieldTypesModules());

		return result;
	}

}