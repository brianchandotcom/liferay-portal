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

import java.util.Set;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

/**
 * @author Chema Balsas
 */
public class ProgressBarTag extends BaseContainerTag {

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		if (_value == _maxValue) {
			setStatus("success");
		}

		if (_status.equals("complete")) {
			setStatus("success");
			setValue(_maxValue);
		}

		return super.doStartTag();
	}

	public int getMaxValue() {
		return _maxValue;
	}

	public int getMinValue() {
		return _minValue;
	}

	public String getStatus() {
		return _status;
	}

	public int getValue() {
		return _value;
	}

	public void setMaxValue(int maxValue) {
		_maxValue = maxValue;
	}

	public void setMinValue(int minValue) {
		_minValue = minValue;
	}

	public void setStatus(String status) {
		_status = status;
	}

	public void setValue(int value) {
		_value = value;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_maxValue = 100;
		_minValue = 0;
		_status = "info";
		_value = 0;
	}

	@Override
	protected String processCssClasses(Set<String> cssClasses) {
		cssClasses.add("progress-group");
		cssClasses.add("progress-" + _status);

		return super.processCssClasses(cssClasses);
	}

	@Override
	protected int processStartTag() throws Exception {
		super.processStartTag();

		JspWriter jspWriter = pageContext.getOut();

		jspWriter.write("<div class=\"progress\"><div aria-valuemax=\"");
		jspWriter.write(String.valueOf(_maxValue));
		jspWriter.write("\" aria-valuemin=\"");
		jspWriter.write(String.valueOf(_minValue));
		jspWriter.write("\" aria-valuenow=\"");
		jspWriter.write(String.valueOf(_value));
		jspWriter.write("\" class=\"progress-bar\" role=\"progressbar\" ");
		jspWriter.write("style=\"width: ");
		jspWriter.write(String.valueOf(_value));
		jspWriter.write("%\"></div></div>");

		jspWriter.write("<div class=\"progress-group-addon\">");

		if (_status.equals("success")) {
			jspWriter.write("<div class=\"progress-group-feedback\">");

			IconTag iconTag = new IconTag();

			iconTag.setSymbol("check-circle");

			iconTag.doTag(pageContext);

			jspWriter.write("</div>");
		}
		else {
			jspWriter.write(String.valueOf(_value));
			jspWriter.write("%");
		}

		jspWriter.write("</div>");

		return SKIP_BODY;
	}

	private static final String _ATTRIBUTE_NAMESPACE = "clay:progressbar:";

	private int _maxValue = 100;
	private int _minValue;
	private String _status = "info";
	private int _value;

}