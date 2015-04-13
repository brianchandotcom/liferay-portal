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

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;

import javax.portlet.PortletURL;

/**
 * @author Brian Wing Shun Chan
 * @author Ryan Park
 */
public class Summary {

	public Summary(
		Locale locale, String title, String content, PortletURL portletURL) {

		_locale = locale;
		_title = title;
		_content = content;
		_portletURL = portletURL;
	}

	public Summary(String title, String content, PortletURL portletURL) {
		this(
			LocaleThreadLocal.getThemeDisplayLocale(), title, content,
			portletURL);
	}

	public String getContent() {
		if (Validator.isNull(_content)) {
			return StringPool.BLANK;
		}

		return _content;
	}

	public Locale getLocale() {
		return _locale;
	}

	public int getMaxContentLength() {
		return _maxContentLength;
	}

	public PortletURL getPortletURL() {
		return _portletURL;
	}

	public String getTitle() {
		if (Validator.isNull(_title)) {
			return StringPool.BLANK;
		}

		return _title;
	}

	public void setContent(String content) {
		_content = content;

		if ((_content != null) && (_maxContentLength > 0) &&
			(_content.length() > _maxContentLength)) {

			_content = StringUtil.shorten(_content, _maxContentLength);
		}
	}

	public void setLocale(Locale locale) {
		_locale = locale;
	}

	public void setMaxContentLength(int maxContentLength) {
		_maxContentLength = maxContentLength;

		setContent(_content);
	}

	public void setPortletURL(PortletURL portletURL) {
		_portletURL = portletURL;
	}

	public void setTitle(String title) {
		_title = title;
	}

	private String _content;
	private Locale _locale;
	private int _maxContentLength;
	private PortletURL _portletURL;
	private String _title;

}