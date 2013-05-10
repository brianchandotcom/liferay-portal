/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.util.comparator;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.util.PortalUtil;

import java.io.Serializable;

import java.util.Comparator;
import java.util.Locale;

import javax.servlet.ServletContext;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletTitleComparator
	implements Comparator<Portlet>, Serializable {

	public PortletTitleComparator(Locale locale) {
		_locale = locale;
	}

	public PortletTitleComparator(
		ServletContext servletContext, Locale locale) {

		_servletContext = servletContext;
		_locale = locale;
	}

	@Override
	public int compare(Portlet portlet1, Portlet portlet2) {
		String portletTitle1 = StringPool.BLANK;
		String portletTitle2 = StringPool.BLANK;

		if (_servletContext != null) {
			portletTitle1 = PortalUtil.getPortletTitle(
				portlet1, _servletContext, _locale);
			portletTitle2 = PortalUtil.getPortletTitle(
				portlet2, _servletContext, _locale);
		}
		else {
			portletTitle1 = PortalUtil.getPortletTitle(portlet1, _locale);
			portletTitle2 = PortalUtil.getPortletTitle(portlet2, _locale);
		}

		return portletTitle1.compareTo(portletTitle2);
	}

	private Locale _locale;
	private ServletContext _servletContext;

}