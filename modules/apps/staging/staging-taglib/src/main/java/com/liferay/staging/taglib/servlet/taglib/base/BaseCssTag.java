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

package com.liferay.staging.taglib.servlet.taglib.base;

import com.liferay.frontend.taglib.util.TagAccessor;
import com.liferay.frontend.taglib.util.TagResourceHandler;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * @author Peter Borkuti
 */
public abstract class BaseCssTag extends IncludeTag implements TagAccessor {

	@Override
	public int doStartTag() throws JspException {
		_outputStylesheetLink();

		return super.doStartTag();
	}

	@Override
	public PageContext getPageContext() {
		return pageContext;
	}

	public abstract String getTagNameForCssPath();

	private void _outputStylesheetLink() {
		StringBundler sb = new StringBundler(2);

		sb.append(getTagNameForCssPath());
		sb.append("/css/main.css");

		_tagResourceHandler.outputBundleStyleSheet(sb.toString());
	}

	private static final Log _log = LogFactoryUtil.getLog(BaseCssTag.class);

	private final TagResourceHandler _tagResourceHandler =
		new TagResourceHandler(this, BaseCssTag.class);

}