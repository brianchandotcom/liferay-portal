/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.highlight.HighlightUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Locale;

/**
 * @author Brian Wing Shun Chan
 * @author Ryan Park
 * @author Tibor Lipusz
 */
public class Summary {

	public Summary(Locale locale, String title, String content) {
		_locale = locale;
		_title = title;
		_content = content;
	}

	public Summary(String title, String content) {
		this(LocaleThreadLocal.getThemeDisplayLocale(), title, content);
	}

	public String getContent() {
		if (Validator.isNull(_content)) {
			return StringPool.BLANK;
		}

		if ((_maxContentLength <= 0) ||
			(_content.length() <= _maxContentLength)) {

			return _content;
		}

		if (!ArrayUtil.isEmpty(_queryTerms)) {
			int index = StringUtil.indexOfAny(_content, _queryTerms);

			if (index > _maxContentLength) {
				_content = _content.substring(index);
			}
		}

		_content = StringUtil.shorten(_content, _maxContentLength);

		return _content;
	}

	public String getHighlightedContent() {
		return _escapeAndHighlight(_content);
	}

	public String getHighlightedTitle() {
		return _escapeAndHighlight(_title);
	}

	public Locale getLocale() {
		return _locale;
	}

	public int getMaxContentLength() {
		return _maxContentLength;
	}

	public String[] getQueryTerms() {
		return _queryTerms;
	}

	public String getTitle() {
		if (Validator.isNull(_title)) {
			return StringPool.BLANK;
		}

		return _title;
	}

	public boolean isEscape() {
		return _escape;
	}

	public boolean isHighlight() {
		return _highlight;
	}

	public void setContent(String content) {
		_content = content;
	}

	public void setEscape(boolean escape) {
		_escape = escape;
	}

	public void setHighlight(boolean highlight) {
		_highlight = highlight;
	}

	public void setLocale(Locale locale) {
		_locale = locale;
	}

	public void setMaxContentLength(int maxContentLength) {
		_maxContentLength = maxContentLength;
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

	private String _escapeAndHighlight(String text) {
		if (!_highlight || Validator.isNull(text) ||
			ArrayUtil.isEmpty(_queryTerms)) {

			if (_escape) {
				return HtmlUtil.escape(text);
			}

			return text;
		}

		text = HighlightUtil.highlight(
			text, _queryTerms, _ESCAPE_SAFE_HIGHLIGHTS[0],
			_ESCAPE_SAFE_HIGHLIGHTS[1]);

		if (_escape) {
			text = HtmlUtil.escape(text);
		}

		return StringUtil.replace(
			text, _ESCAPE_SAFE_HIGHLIGHTS, HighlightUtil.HIGHLIGHTS);
	}

	private static final String[] _ESCAPE_SAFE_HIGHLIGHTS = {
		"[@HIGHLIGHT1@]", "[@HIGHLIGHT2@]"
	};

	private String _content;
	private boolean _escape = true;
	private boolean _highlight;
	private Locale _locale;
	private int _maxContentLength;
	private String[] _queryTerms;
	private String _title;

}