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
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.util.TagResourceBundleUtil;

import java.util.Map;
import java.util.Set;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

/**
 * @author Chema Balsas
 */
public class LinkTag extends BaseContainerTag {

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		setContainerElement("a");

		if (Validator.isNotNull(_ariaLabel)) {
			setDynamicAttribute(StringPool.BLANK, "aria-label", _ariaLabel);
		}

		if (Validator.isNotNull(_href)) {
			setDynamicAttribute(StringPool.BLANK, "href", _href);
		}

		if (Validator.isNotNull(_target)) {
			Map<String, Object> dynamicAttributes = getDynamicAttributes();

			if (dynamicAttributes.get("rel") == null) {
				setDynamicAttribute(
					StringPool.BLANK, "rel", "noreferrer noopener");
			}

			setDynamicAttribute(StringPool.BLANK, "target", _target);
		}

		if (Validator.isNotNull(_title)) {
			setDynamicAttribute(
				StringPool.BLANK, "title",
				LanguageUtil.get(
					TagResourceBundleUtil.getResourceBundle(pageContext),
					_title));
		}

		if (Validator.isNotNull(_type) &&
			!(_type.equals("link") || _type.equals("button"))) {

			setDynamicAttribute(StringPool.BLANK, "type", _type);
		}

		return super.doStartTag();
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public String getAriaLabel() {
		return _ariaLabel;
	}

	public boolean getBorderless() {
		return _borderless;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getDisplayType()}
	 */
	@Deprecated
	public String getButtonStyle() {
		return getDisplayType();
	}

	public String getDisplayType() {
		return _displayType;
	}

	public String getDownload() {
		return _download;
	}

	public String getHref() {
		return _href;
	}

	public String getIcon() {
		return _icon;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public String getIconAlignment() {
		return _iconAlignment;
	}

	public String getLabel() {
		return _label;
	}

	public boolean getMonospaced() {
		return _monospaced;
	}

	public boolean getOutline() {
		return _outline;
	}

	public boolean getSmall() {
		return _small;
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
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public String getTarget() {
		return _target;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public String getTitle() {
		return _title;
	}

	public String getType() {
		return _type;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public void setAriaLabel(String ariaLabel) {
		_ariaLabel = ariaLabel;
	}

	public void setBorderless(boolean borderless) {
		_borderless = borderless;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #setDisplayType(String)}
	 */
	@Deprecated
	public void setButtonStyle(String buttonStyle) {
		setDisplayType(buttonStyle);
		setType("button");
	}

	public void setDisplayType(String displayType) {
		_displayType = displayType;
	}

	public void setDownload(String download) {
		_download = download;
	}

	public void setHref(String href) {
		_href = href;
	}

	public void setIcon(String icon) {
		_icon = icon;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public void setIconAlignment(String iconAlignment) {
		_iconAlignment = iconAlignment;
	}

	public void setLabel(String label) {
		_label = label;
	}

	public void setMonospaced(boolean monospaced) {
		_monospaced = monospaced;
	}

	public void setOutline(boolean outline) {
		_outline = outline;
	}

	public void setSmall(boolean small) {
		_small = small;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #setDisplayType(String)}
	 */
	@Deprecated
	public void setStyle(String style) {
		setDisplayType(style);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public void setTarget(String target) {
		_target = target;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public void setTitle(String title) {
		_title = title;
	}

	public void setType(String type) {
		_type = type;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_ariaLabel = null;
		_borderless = false;
		_displayType = null;
		_download = null;
		_href = null;
		_icon = null;
		_iconAlignment = null;
		_label = null;
		_monospaced = false;
		_outline = false;
		_small = false;
		_target = null;
		_title = null;
		_type = "link";
	}

	@Override
	protected String processCssClasses(Set<String> cssClasses) {
		String cssPrefix = "link-";

		if (Validator.isNotNull(_type) && _type.equals("button")) {
			cssPrefix = "btn-";

			cssClasses.add("btn");
		}

		if (_borderless) {
			cssClasses.add(cssPrefix + "outline-borderless");
		}

		if (_monospaced) {
			cssClasses.add(cssPrefix + "monospaced");
		}

		if (_small) {
			cssClasses.add(cssPrefix + "sm");
		}

		if (Validator.isNotNull(_displayType)) {
			if (_outline || _borderless) {
				cssClasses.add(cssPrefix + "outline-" + _displayType);
			}
			else {
				cssClasses.add(cssPrefix + _displayType);
			}
		}

		return super.processCssClasses(cssClasses);
	}

	@Override
	protected int processStartTag() throws Exception {
		super.processStartTag();

		if (Validator.isNotNull(_icon) || Validator.isNotNull(_label)) {
			JspWriter jspWriter = pageContext.getOut();

			if (Validator.isNotNull(_icon)) {
				IconTag iconTag = new IconTag();

				if (Validator.isNotNull(_label)) {
					iconTag.setCssClass("inline-item inline-item-before");
				}

				iconTag.setSymbol(_icon);

				iconTag.doTag(pageContext);
			}

			if (Validator.isNotNull(_label)) {
				String label = LanguageUtil.get(
					TagResourceBundleUtil.getResourceBundle(pageContext),
					_label);

				jspWriter.write(HtmlUtil.escape(label));
			}

			return SKIP_BODY;
		}

		return EVAL_BODY_INCLUDE;
	}

	private static final String _ATTRIBUTE_NAMESPACE = "clay:link:";

	private String _ariaLabel;
	private boolean _borderless;
	private String _displayType;
	private String _download;
	private String _href;
	private String _icon;
	private String _iconAlignment;
	private String _label;
	private boolean _monospaced;
	private boolean _outline;
	private boolean _small;
	private String _target;
	private String _title;
	private String _type = "link";

}