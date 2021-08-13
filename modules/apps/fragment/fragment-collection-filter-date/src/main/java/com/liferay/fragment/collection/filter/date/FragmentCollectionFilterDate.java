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

package com.liferay.fragment.collection.filter.date;

import com.liferay.fragment.collection.filter.FragmentCollectionFilter;
import com.liferay.fragment.renderer.FragmentRendererContext;
import com.liferay.portal.kernel.language.LanguageUtil;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Pablo Molina
 */
@Component(immediate = true, service = FragmentCollectionFilter.class)
public class FragmentCollectionFilterDate implements FragmentCollectionFilter {

	@Override
	public String getFilterKey() {
		return "date";
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "date");
	}

	@Override
	public void render(
		FragmentRendererContext fragmentRendererContext,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {
	}

}