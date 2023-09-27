/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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