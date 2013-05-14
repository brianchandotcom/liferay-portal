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

package com.liferay.portal.kernel.servlet.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

/**
 * <p>
 * See http://issues.liferay.com/browse/LPS-13878.
 * </p>
 *
 * @author Shuyang Zhou
 */
public class TagSupport implements Tag {

	public static Tag findAncestorWithClass(Tag fromTag, Class<?> clazz) {
		boolean isInterface = false;

		if ((fromTag == null) || (clazz == null) ||
			(!Tag.class.isAssignableFrom(clazz) &&
			 !(isInterface = clazz.isInterface()))) {

			return null;
		}

		while (true) {
			Tag parentTag = fromTag.getParent();

			if (parentTag == null) {
				return null;
			}

			if ((isInterface && clazz.isInstance(parentTag)) ||
				clazz.isAssignableFrom(parentTag.getClass())) {

				return parentTag;
			}
			else {
				fromTag = parentTag;
			}
		}
	}

	@Override
	@SuppressWarnings("unused")
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}

	@Override
	@SuppressWarnings("unused")
	public int doStartTag() throws JspException {
		return SKIP_BODY;
	}

	@Override
	public Tag getParent() {
		return _parent;
	}

	@Override
	public void release() {
		_parent = null;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		this.pageContext = pageContext;
	}

	@Override
	public void setParent(Tag tag) {
		_parent = tag;
	}

	protected PageContext pageContext;

	private Tag _parent;

}