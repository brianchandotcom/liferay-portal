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

package com.liferay.marketplace.util.comparator;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.LayoutTemplate;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.util.CollatorUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import java.text.Collator;

import java.util.Comparator;
import java.util.Locale;

import javax.servlet.ServletContext;

/**
 * @author Ryan Park
 */
public class PluginComparator implements Comparator, Serializable {

	public PluginComparator() {
		_locale = LocaleUtil.getDefault();
		_servletContext = ServletContextPool.get(PortalUtil.getPathContext());

		_collator = CollatorUtil.getInstance(_locale);
	}

	public PluginComparator(ServletContext servletContext, Locale locale) {
		_servletContext = servletContext;

		_locale = locale;

		_collator = CollatorUtil.getInstance(_locale);
	}

	@Override
	public int compare(Object plugin1, Object plugin2) {
		String name1 = _getName(plugin1);
		String name2 = _getName(plugin2);

		return _collator.compare(name1, name2);
	}

	private String _getName(Object plugin) {
		String name = StringPool.BLANK;

		if (plugin instanceof LayoutTemplate) {
			LayoutTemplate layoutTemplate = (LayoutTemplate)plugin;

			name = layoutTemplate.getName();
		}
		else if (plugin instanceof Portlet) {
			Portlet portlet = (Portlet)plugin;

			name = PortalUtil.getPortletTitle(
				portlet, _servletContext, _locale);
		}
		else if (plugin instanceof Theme) {
			Theme theme = (Theme)plugin;

			name = theme.getName();
		}

		return StringUtil.toLowerCase(name);
	}

	private final Collator _collator;
	private final Locale _locale;
	private final ServletContext _servletContext;

}