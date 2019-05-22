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
import com.liferay.portal.kernel.util.LocaleThreadLocal;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jeyvison Nascimento
 */
public class DataLayoutBuilderTag extends BaseDataLayoutBuilderTag {

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		super.setAttributes(httpServletRequest);

		setNamespacedAttribute(
			httpServletRequest, "availableLocales",
			new Locale[] {LocaleThreadLocal.getThemeDisplayLocale()});

		setNamespacedAttribute(
			httpServletRequest, "dataLayout",
			DataLayoutTaglibUtil.getDataLayoutJSONObject(
				getDataLayoutId(), LocaleThreadLocal.getThemeDisplayLocale(),
				httpServletRequest));

		setNamespacedAttribute(
			httpServletRequest, "fieldTypes",
			DataLayoutTaglibUtil.getFieldTypesJSONArray(httpServletRequest));

		setNamespacedAttribute(
			httpServletRequest, "fieldTypesModules",
			DataLayoutTaglibUtil.resolveFieldTypesModules());

		setNamespacedAttribute(
			httpServletRequest, "dataLayoutBuilderModule",
			DataLayoutTaglibUtil.resolveModule(
				"data-engine-taglib/data_layout_builder/js" +
					"/DataLayoutBuilder.es"));
	}

}