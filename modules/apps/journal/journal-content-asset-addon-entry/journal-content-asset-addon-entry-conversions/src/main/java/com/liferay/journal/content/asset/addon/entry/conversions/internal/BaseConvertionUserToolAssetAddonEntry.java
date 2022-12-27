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

package com.liferay.journal.content.asset.addon.entry.conversions.internal;

import com.liferay.document.library.kernel.document.conversion.DocumentConversionUtil;
import com.liferay.journal.content.asset.addon.entry.UserToolAssetAddonEntry;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.BaseJSPAssetAddonEntry;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Julio Camarero
 */
public abstract class BaseConvertionUserToolAssetAddonEntry
	extends BaseJSPAssetAddonEntry implements UserToolAssetAddonEntry {

	public abstract String getExtension();

	@Override
	public String getJspPath() {
		return "/conversions.jsp";
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.format(
			locale, "download-as-x", StringUtil.toUpperCase(getExtension()));
	}

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		httpServletRequest.setAttribute("extension", getExtension());

		super.include(httpServletRequest, httpServletResponse);
	}

	@Override
	public boolean isEnabled() {
		if (!DocumentConversionUtil.isEnabled() ||
			!ArrayUtil.contains(
				DocumentConversionUtil.getConversions("html"),
				getExtension())) {

			return false;
		}

		return super.isEnabled();
	}

}