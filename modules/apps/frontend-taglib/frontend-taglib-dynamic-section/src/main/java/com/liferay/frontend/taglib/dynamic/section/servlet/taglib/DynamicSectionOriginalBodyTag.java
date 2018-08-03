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

package com.liferay.frontend.taglib.dynamic.section.servlet.taglib;

import com.liferay.petra.string.StringBundler;
import com.liferay.taglib.TagSupport;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;

/**
 * @author Matthew Tambara
 */
public class DynamicSectionOriginalBodyTag extends TagSupport {

	@Override
	public int doEndTag() throws JspException {
		ServletRequest servletRequest = pageContext.getRequest();

		StringBundler sb = (StringBundler)servletRequest.getAttribute(
			_PREFIX.concat(_name));

		if (sb == null) {
			throw new IllegalArgumentException(
				"No original body for name " + _name);
		}

		try {
			sb.writeTo(pageContext.getOut());

			return EVAL_PAGE;
		}
		catch (IOException ioe) {
			throw new JspException(ioe);
		}
		finally {
			_name = null;
		}
	}

	public void setName(String name) {
		_name = name;
	}

	private static final String _PREFIX =
		DynamicSectionTag.class.getName() + "#";

	private String _name;

}