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
import com.liferay.portal.kernel.util.Validator;

import java.util.Set;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

/**
 * @author Chema Balsas
 */
public class StickerTag extends BaseContainerTag {

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		if (getContainerElement() == null) {
			setContainerElement("span");
		}

		return super.doStartTag();
	}

	public String getDisplayType() {
		return _displayType;
	}

	public String getIcon() {
		return _icon;
	}

	public String getImageAlt() {
		return _imageAlt;
	}

	public String getImageSrc() {
		return _imageSrc;
	}

	public boolean getInline() {
		return _inline;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public String getLabel() {
		return _label;
	}

	public boolean getOutside() {
		return _outside;
	}

	public String getPosition() {
		return _position;
	}

	public String getShape() {
		return _shape;
	}

	public String getSize() {
		return _size;
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

	public void setDisplayType(String displayType) {
		_displayType = displayType;
	}

	public void setIcon(String icon) {
		_icon = icon;
	}

	public void setImageAlt(String imageAlt) {
		_imageAlt = imageAlt;
	}

	public void setImageSrc(String imageSrc) {
		_imageSrc = imageSrc;
	}

	public void setInline(boolean inline) {
		_inline = inline;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public void setLabel(String label) {
		_label = label;
	}

	public void setOutside(boolean outside) {
		_outside = outside;
	}

	public void setPosition(String position) {
		_position = position;
	}

	public void setShape(String shape) {
		_shape = shape;
	}

	public void setSize(String size) {
		_size = size;
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

		_displayType = null;
		_icon = null;
		_imageAlt = null;
		_imageSrc = null;
		_inline = false;
		_label = null;
		_outside = false;
		_position = null;
		_shape = "rounded";
		_size = null;
		_spritemap = null;
	}

	@Override
	protected String processCssClasses(Set<String> cssClasses) {
		cssClasses.add("sticker");

		if (Validator.isNotNull(_displayType)) {
			cssClasses.add("sticker-" + _displayType);
		}

		if (Validator.isNotNull(_position)) {
			cssClasses.add("sticker-" + _position);

			if (_outside) {
				cssClasses.add("sticker-outside");
			}
		}

		if (Validator.isNotNull(_shape)) {
			cssClasses.add("sticker-" + _shape);
		}

		if (Validator.isNotNull(_size)) {
			cssClasses.add("sticker-" + _size);
		}

		return super.processCssClasses(cssClasses);
	}

	@Override
	protected int processEndTag() throws Exception {
		JspWriter jspWriter = pageContext.getOut();

		jspWriter.write("</span>");

		return super.processEndTag();
	}

	@Override
	protected int processStartTag() throws Exception {
		super.processStartTag();

		JspWriter jspWriter = pageContext.getOut();

		jspWriter.write("<span class=\"sticker-overlay");

		if (_inline) {
			jspWriter.write(" inline-item");
		}

		jspWriter.write("\">");

		if (Validator.isNotNull(_icon)) {
			IconTag iconTag = new IconTag();

			iconTag.setSymbol(_icon);

			iconTag.doTag(pageContext);

			return SKIP_BODY;
		}
		else if (Validator.isNotNull(_imageSrc)) {
			jspWriter.write("<img alt=\"");
			jspWriter.write(_imageAlt);
			jspWriter.write("\" class=\"sticker-img\" src=\"");
			jspWriter.write(_imageSrc);
			jspWriter.write("\" />");

			return SKIP_BODY;
		}

		return EVAL_BODY_INCLUDE;
	}

	private static final String _ATTRIBUTE_NAMESPACE = "clay:sticker:";

	private String _displayType;
	private String _icon;
	private String _imageAlt;
	private String _imageSrc;
	private boolean _inline;
	private String _label;
	private boolean _outside;
	private String _position;
	private String _shape = "rounded";
	private String _size;
	private String _spritemap;

}