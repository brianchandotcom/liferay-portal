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
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.util.TagResourceBundleUtil;

import java.util.Set;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

/**
 * @author Chema Balsas
 */
public class LabelTag extends BaseContainerTag {

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		if (getContainerElement() == null) {
			setContainerElement("span");
		}

		return super.doStartTag();
	}

	public boolean getDismissible() {
		return _dismissible;
	}

	public String getDisplayType() {
		return _displayType;
	}

	public String getLabel() {
		return _label;
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link #isLarge()}
	 */
	@Deprecated
	public boolean getLarge() {
		return _large;
	}

	public boolean isLarge() {
		return _large;
	}

	public boolean isTranslated() {
		return _translated;
	}

	public void setDismissible(boolean dismissible) {
		_dismissible = dismissible;
	}

	public void setDisplayType(String displayType) {
		_displayType = displayType;
	}

	public void setLabel(String label) {
		_label = label;
	}

	public void setLarge(boolean large) {
		_large = large;
	}

	public void setTranslated(boolean translated) {
		_translated = translated;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_dismissible = false;
		_displayType = "secondary";
		_label = null;
		_large = false;
		_translated = true;
	}

	@Override
	protected String processCssClasses(Set<String> cssClasses) {
		cssClasses.add("label");
		cssClasses.add("label-" + _displayType);

		if (_dismissible) {
			cssClasses.add("label-dismissible");
		}

		if (_large) {
			cssClasses.add("label-lg");
		}

		return super.processCssClasses(cssClasses);
	}

	@Override
	protected int processStartTag() throws Exception {
		super.processStartTag();

		if (Validator.isNotNull(_label)) {
			JspWriter jspWriter = pageContext.getOut();

			jspWriter.write("<span class=\"label-item label-item-expand\">");

			String labelValue = _label;

			if (_translated) {
				labelValue = LanguageUtil.get(
					TagResourceBundleUtil.getResourceBundle(pageContext),
					_label);
			}

			jspWriter.write(HtmlUtil.escape(labelValue));
			jspWriter.write("</span>");

			if (_dismissible) {
				jspWriter.write("<span class=\"label-item label-item-after\">");

				jspWriter.write("<button class=\"close\" type=\"button\">");

				IconTag iconTag = new IconTag();

				iconTag.setSymbol("times-small");

				iconTag.doTag(pageContext);

				jspWriter.write("</button>");
				jspWriter.write("</span>");
			}

			return SKIP_BODY;
		}

		return EVAL_BODY_INCLUDE;
	}

	private static final String _ATTRIBUTE_NAMESPACE = "clay:label:";

	private boolean _dismissible;
	private String _displayType = "secondary";
	private String _label;
	private boolean _large;
	private boolean _translated = true;

}