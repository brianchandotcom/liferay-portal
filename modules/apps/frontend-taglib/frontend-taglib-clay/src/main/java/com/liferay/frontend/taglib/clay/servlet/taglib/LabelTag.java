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

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getDismissible()}
	 */
	@Deprecated
	public boolean getCloseable() {
		return getDismissible();
	}

	public boolean getDismissible() {
		return _dismissible;
	}

	public String getDisplayType() {
		return _displayType;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public String getHref() {
		return _href;
	}

	public String getLabel() {
		return _label;
	}

	public boolean getLarge() {
		return _large;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public String getMessage() {
		return _message;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getLarge()}
	 */
	@Deprecated
	public String getSize() {
		if (_large) {
			return "lg";
		}

		return null;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public String getSpritemap() {
		return _spritemap;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getDisplayType()}
	 */
	@Deprecated
	public String getStyle() {
		return getDisplayType();
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #setDismissible(boolean)}
	 */
	@Deprecated
	public void setCloseable(boolean closeable) {
		setDismissible(closeable);
	}

	public void setDismissible(boolean dismissible) {
		_dismissible = dismissible;
	}

	public void setDisplayType(String displayType) {
		_displayType = displayType;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public void setHref(String href) {
		_href = href;
	}

	public void setLabel(String label) {
		_label = label;
	}

	public void setLarge(boolean large) {
		_large = large;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public void setMessage(String message) {
		_message = message;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #setLarge(boolean)}
	 */
	@Deprecated
	public void setSize(String size) {
		setLarge(size.equals("lg"));
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public void setSpritemap(String spritemap) {
		_spritemap = spritemap;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #setDisplayType(String)}
	 */
	@Deprecated
	public void setStyle(String style) {
		setDisplayType(style);
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_dismissible = false;
		_displayType = "secondary";
		_href = null;
		_label = null;
		_large = false;
		_message = null;
		_spritemap = null;
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

			jspWriter.write(
				LanguageUtil.get(
					TagResourceBundleUtil.getResourceBundle(pageContext),
					_label));

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
	private String _href;
	private String _label;
	private boolean _large;
	private String _message;
	private String _spritemap;

}