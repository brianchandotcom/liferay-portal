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

import com.liferay.frontend.taglib.dynamic.section.DynamicSection;
import com.liferay.frontend.taglib.dynamic.section.DynamicSectionReplace;
import com.liferay.frontend.taglib.dynamic.section.internal.util.DynamicSectionUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.taglib.BaseBodyTagSupport;

import java.util.List;

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

			if (_dynamicSectionReplace != null) {
				jspWriter.write(_dynamicSectionReplace.replace(pageContext));
			}
			else if (_dynamicSections != null) {
				ServletRequest servletRequest = pageContext.getRequest();

				String key = _PREFIX.concat(_name);

				StringBundler sb = getBodyContentAsStringBundler();

				StringBundler originalBodySB = new StringBundler(sb.index());

				originalBodySB.append(sb);

				servletRequest.setAttribute(key, originalBodySB);

				for (DynamicSection dynamicSection : _dynamicSections) {
					sb = dynamicSection.modify(sb, pageContext);
				}

				servletRequest.removeAttribute(key);

				sb.writeTo(jspWriter);
			}

			return EVAL_PAGE;
		}
		catch (Exception e) {
			throw new JspException(e);
		}
		finally {
			_dynamicSectionReplace = null;
			_dynamicSections = null;
			_name = null;
		}
	}

	@Override
	public int doStartTag() {
		_dynamicSectionReplace = DynamicSectionUtil.getReplace(_name);

		if (_dynamicSectionReplace != null) {
			return SKIP_BODY;
		}

		List<DynamicSection> dynamicSections = DynamicSectionUtil.getServices(
			_name);

		if ((dynamicSections != null) && !dynamicSections.isEmpty()) {
			_dynamicSections = dynamicSections;

			return EVAL_BODY_BUFFERED;
		}

		return EVAL_BODY_INCLUDE;
	}

	public void setName(String name) {
		_name = name;
	}

	private static final String _PREFIX =
		DynamicSectionTag.class.getName() + "#";

	private DynamicSectionReplace _dynamicSectionReplace;
	private List<DynamicSection> _dynamicSections;
	private String _name;

}