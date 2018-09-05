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

package com.liferay.portal.kernel.util.comparator;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.util.CollatorUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.text.Collator;

import java.util.Comparator;
import java.util.Locale;

import javax.servlet.ServletContext;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletTitleComparator
	implements Comparator<Portlet>, Serializable {

	public PortletTitleComparator(Locale locale) {
		this(null, locale);
	}

	public PortletTitleComparator(
		ServletContext servletContext, Locale locale) {

		this(servletContext, locale, true);
	}

	public PortletTitleComparator(
		ServletContext servletContext, Locale locale, boolean ascending) {

		_servletContext = servletContext;
		_locale = locale;
		_ascending = ascending;

		_collator = CollatorUtil.getInstance(_locale);
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

		if (Validator.isNull(portletTitle1) &&
			Validator.isNull(portletTitle2)) {

			return 0;
		}

		if (Validator.isNull(portletTitle1)) {
			return 1;
		}

		if (Validator.isNull(portletTitle2)) {
			return -1;
		}

		int value = _collator.compare(portletTitle1, portletTitle2);

		if (_ascending) {
			return value;
		}

		return -value;
	}

	private final boolean _ascending;
	private final Collator _collator;
	private final Locale _locale;
	private final ServletContext _servletContext;

}