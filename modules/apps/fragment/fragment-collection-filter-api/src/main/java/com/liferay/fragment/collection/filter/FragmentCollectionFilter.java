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

package com.liferay.fragment.collection.filter;

import aQute.bnd.annotation.ProviderType;

import com.liferay.fragment.renderer.FragmentRendererContext;
import com.liferay.petra.string.StringPool;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Pablo Molina
 */
@ProviderType
public interface FragmentCollectionFilter {

	public default String getConfiguration() {
		return StringPool.BLANK;
	}

	public String getFilterKey();

	public default String getFilterValueLabel(
		String filterValue, Locale locale) {

		return filterValue;
	}

	public String getLabel(Locale locale);

	public void render(
		FragmentRendererContext fragmentRendererContext,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse);

}