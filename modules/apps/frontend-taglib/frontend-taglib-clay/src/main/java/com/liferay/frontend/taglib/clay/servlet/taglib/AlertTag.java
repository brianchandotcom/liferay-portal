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
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.util.TagResourceBundleUtil;

import java.util.Set;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

/**
 * @author Chema Balsas
 */
public class AlertTag extends BaseContainerTag {

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		setDynamicAttribute(StringPool.BLANK, "role", "alert");

		return super.doStartTag();
	}

	public boolean getAutoClose() {
		return _autoClose;
	}

	public boolean getDismissible() {
		return _dismissible;
	}

	public String getDisplayType() {
		return _displayType;
	}

	public String getMessage() {
		return _message;
	}

	public String getTitle() {
		return _title;
	}

	public String getVariant() {
		return _variant;
	}

	public void setAutoClose(boolean autoClose) {
		_autoClose = autoClose;
	}

	public void setDismissible(boolean dismissible) {
		_dismissible = dismissible;
	}

	public void setDisplayType(String displayType) {
		_displayType = displayType;
	}

	public void setMessage(String message) {
		_message = message;
	}

	public void setTitle(String title) {
		_title = title;
	}

	public void setVariant(String variant) {
		_variant = variant;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_autoClose = false;
		_dismissible = false;
		_displayType = "info";
		_message = null;
		_title = null;
		_variant = null;
	}

	@Override
	protected String processCssClasses(Set<String> cssClasses) {
		cssClasses.add("alert");

		if (_dismissible) {
			cssClasses.add("alert-dismissible");
		}

		if (Validator.isNotNull(_variant) && _variant.equals("stripe")) {
			cssClasses.add("alert-fluid");
		}

		if (Validator.isNotNull(_displayType)) {
			cssClasses.add("alert-" + _displayType);
		}

		return super.processCssClasses(cssClasses);
	}

	@Override
	protected int processEndTag() throws Exception {
		JspWriter jspWriter = pageContext.getOut();

		jspWriter.write("</div></div>");

		if (_dismissible) {
			jspWriter.write("<button class=\"close\" onclick=\"");
			jspWriter.write("event.target.closest('[role=alert]').remove()\"");
			jspWriter.write(" type=\"button\">");

			IconTag iconTag = new IconTag();

			iconTag.setSymbol("times");

			iconTag.doTag(pageContext);

			jspWriter.write("</button>");
		}

		if (Validator.isNotNull(_variant) && _variant.equals("stripe")) {
			jspWriter.write("</div>");
		}

		jspWriter.write("</div>");

		return super.processEndTag();
	}

	@Override
	protected int processStartTag() throws Exception {
		super.processStartTag();

		JspWriter jspWriter = pageContext.getOut();

		if (Validator.isNotNull(_variant) && _variant.equals("stripe")) {
			jspWriter.write("<div class=\"container-fluid ");
			jspWriter.write("container-fluid-max-xl\">");
		}

		jspWriter.write("<div class=\"alert-autofit-row autofit-row\"><div ");
		jspWriter.write("class=\"autofit-col\"><div ");
		jspWriter.write("class=\"autofit-section\"><span ");
		jspWriter.write("class=\"alert-indicator\">");

		IconTag iconTag = new IconTag();

		iconTag.setSymbol(_getIcon(_displayType));

		iconTag.doTag(pageContext);

		jspWriter.write("</span></div></div><div class=\"autofit-col ");
		jspWriter.write("autofit-col-expand\"><div class=\"autofit-section\">");
		jspWriter.write("<strong class=\"lead\">");
		jspWriter.write(_getTitle(_title, _displayType));
		jspWriter.write(":</strong>");

		if (Validator.isNotNull(_message)) {
			jspWriter.write(
				LanguageUtil.get(
					TagResourceBundleUtil.getResourceBundle(pageContext),
					_message));

			return SKIP_BODY;
		}

		return EVAL_BODY_INCLUDE;
	}

	private String _getIcon(String displayType) {
		if (displayType.equals("danger")) {
			return "exclamation-full";
		}
		else if (displayType.equals("success")) {
			return "check-circle-full";
		}
		else if (displayType.equals("warning")) {
			return "warning-full";
		}
		else {
			return "info-circle";
		}
	}

	private String _getTitle(String title, String displayType) {
		if (Validator.isNull(title)) {
			title = displayType;
		}

		if (title.equals("danger")) {
			title = "error";
		}

		return LanguageUtil.get(
			TagResourceBundleUtil.getResourceBundle(pageContext), title);
	}

	private static final String _ATTRIBUTE_NAMESPACE = "clay:alert:";

	private boolean _autoClose;
	private boolean _dismissible;
	private String _displayType = "info";
	private String _message;
	private String _title;
	private String _variant;

}