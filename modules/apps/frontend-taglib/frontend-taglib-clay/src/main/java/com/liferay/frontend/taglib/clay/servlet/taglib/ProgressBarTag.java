/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.clay.servlet.taglib;

import com.liferay.frontend.taglib.clay.internal.servlet.taglib.BaseContainerTag;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.taglib.util.TagResourceBundleUtil;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Chema Balsas
 */
public class ProgressBarTag extends BaseContainerTag {

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		_value = Math.min(Math.max(_value, 0), 100);

		return super.doStartTag();
	}

	public Map<String, String> getMessages() {
		return _messages;
	}

	public int getValue() {
		return _value;
	}

	public boolean isWarn() {
		return _warn;
	}

	public void setMessages(Map<String, String> messages) {
		_messages = messages;
	}

	public void setValue(int value) {
		_value = value;
	}

	public void setWarn(boolean warn) {
		_warn = warn;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_messages = new HashMap<>();
		_value = 0;
		_warn = false;
	}

	@Override
	protected String processCssClasses(Set<String> cssClasses) {
		cssClasses.add("progress-group");

		if (_warn) {
			cssClasses.add("progress-warning");
		}
		else if (_isComplete()) {
			cssClasses.add("progress-success");
		}

		return super.processCssClasses(cssClasses);
	}

	@Override
	protected int processStartTag() throws Exception {
		super.processStartTag();

		JspWriter jspWriter = pageContext.getOut();

		jspWriter.write("<div class=\"progress\"><div aria-label=\"");
		jspWriter.write(_getAriaLabel());
		jspWriter.write(
			"\" aria-valuemax=\"100\" aria-valuemin=\"0\" aria-valuenow=\"");
		jspWriter.write(String.valueOf(_value));
		jspWriter.write(
			"\" class=\"progress-bar\" role=\"progressbar\" style=\"width: ");
		jspWriter.write(String.valueOf(_value));
		jspWriter.write("%\"></div></div>");

		jspWriter.write("<div class=\"progress-group-addon\">");

		if (_isComplete()) {
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

	private String _getAriaLabel() {

		// Prioritize warning state over progress values for accessibility

		if (_warn) {
			return LanguageUtil.format(
				TagResourceBundleUtil.getResourceBundle(pageContext),
				_messages.getOrDefault(
					"ariaLabelAttention", "attention-value-is-at-x"),
				_value);
		}

		// Complete state

		if (_isComplete()) {
			return LanguageUtil.format(
				TagResourceBundleUtil.getResourceBundle(pageContext),
				_messages.getOrDefault("ariaLabelComplete", "complete"),
				_value);
		}

		// Default: In progress state

		return LanguageUtil.format(
			TagResourceBundleUtil.getResourceBundle(pageContext),
			_messages.getOrDefault("ariaLabelInProgress", "progress-x"),
			_value);
	}

	private boolean _isComplete() {
		if (_value == 100) {
			return true;
		}

		return false;
	}

	private static final String _ATTRIBUTE_NAMESPACE = "clay:progressbar:";

	private Map<String, String> _messages = new HashMap<>();
	private int _value;
	private boolean _warn;

}