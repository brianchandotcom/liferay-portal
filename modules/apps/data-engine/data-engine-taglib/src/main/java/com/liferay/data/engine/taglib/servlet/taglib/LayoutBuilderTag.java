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

import com.liferay.data.engine.taglib.servlet.taglib.base.BaseLayoutBuilderTag;
import com.liferay.data.engine.taglib.servlet.taglib.util.DataEngineTaglibUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Leonardo Barros
 */
public class LayoutBuilderTag extends BaseLayoutBuilderTag {

	@Override
	protected void setAttributes(HttpServletRequest request) {
		super.setAttributes(request);

		setNamespacedAttribute(
			request, "availableLocales",
			new Locale[] {LocaleThreadLocal.getThemeDisplayLocale()});

		setNamespacedAttribute(
			request, "dataLayout", JSONFactoryUtil.createJSONObject());

		setNamespacedAttribute(
			request, "fieldTypes",
			DataEngineTaglibUtil.getFieldTypesJSONArray(request));

		setNamespacedAttribute(
			request, "fieldTypesModules",
			DataEngineTaglibUtil.resolveFieldTypesModules());

		setNamespacedAttribute(
			request, "layoutBuilderModule",
			DataEngineTaglibUtil.resolveModule(
				"data-engine-taglib/layout_builder/js/LayoutBuilder.es"));
	}

}