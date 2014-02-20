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

import com.liferay.portal.kernel.search.util.SearchUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;

import javax.portlet.PortletURL;

/**
 * @author Brian Wing Shun Chan
 * @author Ryan Park
 * @author Tibor Lipusz
 */
public class Summary {

	public Summary(
		Locale locale, String title, String content, PortletURL portletURL) {

		_title = title;
		_content = content;
		_locale = locale;
		_portletURL = portletURL;
	}

	public Summary(String title, String content, PortletURL portletURL) {
		_title = title;
		_content = content;
		_portletURL = portletURL;
	}

	public String getContent() {
		return _content;
	}

	public String getContent(boolean escape, boolean highlight) {
		return _escapeAndHighlight(_content, escape, highlight);
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

	public String[] getQueryTerms() {
		return _queryTerms;
	}

	public String getTitle() {
		return _title;
	}

	public String getTitle(boolean escape, boolean highlight) {
		return _escapeAndHighlight(_title, escape, highlight);
	}

	public boolean isHighlight() {
		return _highlight;
	}

	public void setContent(String content) {
		_content = content;

		if ((_content != null) && (_maxContentLength > 0) &&
			(_content.length() > _maxContentLength)) {

			_content = StringUtil.shorten(_content, _maxContentLength);
		}
	}

	public void setHighlight(boolean highlight) {
		_highlight = highlight;
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

	public void setQueryTerms(String[] queryTerms) {
		if (ArrayUtil.isEmpty(queryTerms)) {
			return;
		}

		_queryTerms = queryTerms;
	}

	public void setTitle(String title) {
		_title = title;
	}

	private String _escapeAndHighlight(
		String s, boolean escape, boolean highlight) {

		if (Validator.isNull(s) || ArrayUtil.isEmpty(_queryTerms)) {
			return s;
		}

		String result = s;

		if (highlight) {
			result = SearchUtil.highlight(
				result, _queryTerms, ESCAPE_SAFE_HIGHLIGHT_1,
				ESCAPE_SAFE_HIGHLIGHT_2);
		}

		if (escape) {
			result = HtmlUtil.escape(result);
		}

		if (highlight) {
			result = StringUtil.replace(
				result, new String[] {
					ESCAPE_SAFE_HIGHLIGHT_1, ESCAPE_SAFE_HIGHLIGHT_2},
				new String[] {
					SearchUtil.DEFAULT_HIGHLIGHT_1,
					SearchUtil.DEFAULT_HIGHLIGHT_2});
		}

		return result;
	}

	private static final String ESCAPE_SAFE_HIGHLIGHT_1 = "[@HIGHLIGHT1@]";

	private static final String ESCAPE_SAFE_HIGHLIGHT_2 = "[@HIGHLIGHT2@]";

	private String _content;
	private boolean _highlight;
	private Locale _locale;
	private int _maxContentLength;
	private PortletURL _portletURL;
	private String[] _queryTerms;
	private String _title;

}