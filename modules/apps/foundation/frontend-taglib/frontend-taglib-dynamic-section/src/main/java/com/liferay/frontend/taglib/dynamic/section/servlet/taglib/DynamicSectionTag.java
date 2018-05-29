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

import com.liferay.frontend.taglib.dynamic.section.internal.util.DynamicSectionUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.taglib.BaseBodyTagSupport;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTag;

/**
 * @author Matthew Tambara
 */
public class DynamicSectionTag extends BaseBodyTagSupport implements BodyTag {

	@Override
	public int doEndTag() throws JspException {
		try {
			JspWriter jspWriter = pageContext.getOut();

			if (_hasReplace) {
				jspWriter.write(DynamicSectionUtil.replace(_name, pageContext));
			}
			else if (_hasServices) {
				ServletRequest servletRequest = pageContext.getRequest();

				String key = _PREFIX.concat(_name);

				StringBundler sb = null;

				if (_useOriginalBody) {
					sb = (StringBundler)servletRequest.getAttribute(key);
				}
				else {
					sb = getBodyContentAsStringBundler();

					servletRequest.setAttribute(key, sb);

					sb = DynamicSectionUtil.modify(_name, pageContext, sb);

					servletRequest.removeAttribute(key);
				}

				sb.writeTo(jspWriter);
			}

			return EVAL_PAGE;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
		finally {
			_name = null;
			_hasServices = false;
			_hasReplace = false;
			_useOriginalBody = false;
		}
	}

	@Override
	public int doStartTag() {
		_hasReplace = DynamicSectionUtil.hasReplace(_name);

		if (_hasReplace) {
			return SKIP_BODY;
		}

		_hasServices = DynamicSectionUtil.hasServices(_name);

		if (_hasServices) {
			return EVAL_BODY_BUFFERED;
		}

		return EVAL_BODY_INCLUDE;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setUseOriginalBody(boolean useOriginalBody) {
		_useOriginalBody = useOriginalBody;
	}

	private static final String _PREFIX =
		DynamicSectionTag.class.getName() + "#";

	private boolean _hasReplace;
	private boolean _hasServices;
	private String _name;
	private boolean _useOriginalBody;

}