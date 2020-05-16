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

package com.liferay.frontend.taglib.clay.servlet.taglib;

import com.liferay.frontend.taglib.clay.internal.servlet.taglib.BaseContainerTag;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.util.Set;

import javax.servlet.jsp.JspException;

/**
 * @author Chema Balsas
 */
public class ContentRowTag extends BaseContainerTag {

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		return super.doStartTag();
	}

	public String getFloatElements() {
		return _floatElements;
	}

	public String getNoGutters() {
		return _noGutters;
	}

	public boolean getPadded() {
		return _padded;
	}

	public String getVerticalAlign() {
		return _verticalAlign;
	}

	public void setFloatElements(String floatElements) {
		_floatElements = floatElements;
	}

	public void setNoGutters(String noGutters) {
		_noGutters = noGutters;
	}

	public void setPadded(boolean padded) {
		_padded = padded;
	}

	public void setVerticalAlign(String verticalAlign) {
		_verticalAlign = verticalAlign;
	}

	@Override
	protected String _getClassName(Set className) {
		className.add("autofit-row");

		if (_floatElements != null) {
			if (_floatElements.equals(StringPool.BLANK)) {
				className.add("autofit-float");
			}
			else {
				className.add("autofit-float-" + _floatElements);
			}
		}

		if (_padded) {
			className.add("autofit-padded");
		}

		if (_noGutters != null) {
			if (_noGutters.equals("x") || _noGutters.equals("y")) {
				className.add("autofit-padded-no-gutters-" + _noGutters);
			}
			else {
				className.add("autofit-padded-no-gutters");
			}
		}

		if (Validator.isNotNull(_verticalAlign)) {
			className.add("autofit-row-" + _verticalAlign);
		}

		return super._getClassName(className);
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_floatElements = null;
		_noGutters = null;
		_padded = false;
		_verticalAlign = null;
	}

	@Override
	protected String getEndPage() {
		return _END_PAGE;
	}

	@Override
	protected String getStartPage() {
		return _START_PAGE;
	}

	private static final String _ATTRIBUTE_NAMESPACE = "clay:content-row:";

	private static final String _END_PAGE = "/content_row/end.jsp";

	private static final String _START_PAGE = "/content_row/start.jsp";

	private String _floatElements;
	private String _noGutters;
	private boolean _padded;
	private String _verticalAlign;

}