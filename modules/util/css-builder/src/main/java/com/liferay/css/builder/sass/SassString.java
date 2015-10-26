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

package com.liferay.css.builder.sass;

import com.liferay.css.builder.CSSBuilder;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.rtl.css.RTLCSSConverter;

/**
 * @author Minhchau Dang
 * @author Shuyang Zhou
 */
public class SassString implements SassFragment {

	public SassString(CSSBuilder cssBuilder, String fileName, String cssContent)
		throws Exception {

		if (fileName.contains("_rtl")) {
			_ltrContent = StringPool.BLANK;
			_rtlContent = cssContent;
		}
		else {
			_ltrContent = cssContent;

			if (!cssBuilder.isRtlExcludedPath(fileName)) {
				_rtlContent = _getRtlCss(fileName, cssContent);
			}
			else {
				_rtlContent = null;
			}
		}
	}

	@Override
	public String getLtrContent() {
		return _ltrContent;
	}

	@Override
	public String getRtlContent() {
		return _rtlContent;
	}

	private String _getRtlCss(String fileName, String css) throws Exception {
		String rtlCss = css;

		try {
			if (_rtlCSSConverter == null) {
				_rtlCSSConverter = new RTLCSSConverter();
			}

			rtlCss = _rtlCSSConverter.process(rtlCss);
		}
		catch (Exception e) {
			System.out.println(
				"Unable to generate RTL version for " + fileName +
					StringPool.COMMA_AND_SPACE + e.getMessage());
		}

		return rtlCss;
	}

	private static RTLCSSConverter _rtlCSSConverter;

	private final String _ltrContent;
	private final String _rtlContent;

}