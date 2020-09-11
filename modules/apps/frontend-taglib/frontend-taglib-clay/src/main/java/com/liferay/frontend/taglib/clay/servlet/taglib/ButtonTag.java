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
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.util.TagResourceBundleUtil;

import java.util.Map;
import java.util.Set;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

/**
 * @author Chema Balsas
 */
public class ButtonTag extends BaseContainerTag {

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		setContainerElement("button");

		if (Validator.isNotNull(_ariaLabel)) {
			setDynamicAttribute(StringPool.BLANK, "aria-label", _ariaLabel);
		}

		if (_disabled) {
			setDynamicAttribute(StringPool.BLANK, "disabled", _disabled);
		}

		if (Validator.isNotNull(_title)) {
			setDynamicAttribute(
				StringPool.BLANK, "title",
				LanguageUtil.get(
					TagResourceBundleUtil.getResourceBundle(pageContext),
					_title));
		}

		setDynamicAttribute(StringPool.BLANK, "type", _type);

		return super.doStartTag();
	}

	public boolean getAlert() {
		return _alert;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public String getAriaLabel() {
		return _ariaLabel;
	}

	public boolean getBlock() {
		return _block;
	}

	public boolean getBorderless() {
		return _borderless;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public boolean getDisabled() {
		return _disabled;
	}

	public String getDisplayType() {
		return _displayType;
	}

	public String getIcon() {
		return _icon;
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

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #getLarge()}
	 */
	@Deprecated
	public String getSize() {
		if (_small) {
			return "sm";
		}

		return null;
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
	public String getTitle() {
		return _title;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public String getType() {
		return _type;
	}

	public void setAlert(boolean alert) {
		_alert = alert;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public void setAriaLabel(String ariaLabel) {
		_ariaLabel = ariaLabel;
	}

	public void setBlock(boolean block) {
		_block = block;
	}

	public void setBorderless(boolean borderless) {
		_borderless = borderless;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public void setDisabled(boolean disabled) {
		_disabled = disabled;
	}

	public void setDisplayType(String displayType) {
		_displayType = displayType;
	}

	public void setIcon(String icon) {
		_icon = icon;
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

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #setLarge(boolean)}
	 */
	@Deprecated
	public void setSize(String size) {
		setSmall(size.equals("sm"));
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
	public void setTitle(String title) {
		_title = title;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public void setType(String type) {
		_type = type;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_alert = false;
		_ariaLabel = null;
		_block = false;
		_borderless = false;
		_disabled = false;
		_displayType = "primary";
		_icon = null;
		_label = null;
		_monospaced = false;
		_outline = false;
		_small = false;
		_title = null;
		_type = "button";
	}

	@Override
	protected String getHydratedModuleName() {
		if ((getAdditionalProps() != null) && (getPropsTransformer() != null)) {
			return "frontend-taglib-clay/Button";
		}

		return null;
	}

	@Override
	protected Map<String, Object> prepareProps(Map<String, Object> props) {
		props.put("block", _block);
		props.put("borderless", _borderless);
		props.put("displayType", _displayType);
		props.put("icon", _icon);

		if (Validator.isNotNull(_label)) {
			props.put(
				"label",
				LanguageUtil.get(
					TagResourceBundleUtil.getResourceBundle(pageContext),
					_label));
		}

		props.put("monospaced", _monospaced);
		props.put("outline", _outline);
		props.put("small", _small);
		props.put("type", _type);

		return super.prepareProps(props);
	}

	@Override
	protected String processCssClasses(Set<String> cssClasses) {
		cssClasses.add("btn");

		if (_alert) {
			cssClasses.add("alert-btn");
		}

		if (_block) {
			cssClasses.add("btn-block");
		}

		if ((Validator.isNotNull(_icon) && Validator.isNull(_label)) ||
			_monospaced) {

			cssClasses.add("btn-monospaced");
		}

		if (_borderless) {
			cssClasses.add("btn-outline-borderless");
		}

		if (_small) {
			cssClasses.add("btn-sm");
		}

		if (Validator.isNotNull(_displayType)) {
			if (_outline || _borderless) {
				cssClasses.add("btn-outline-" + _displayType);
			}
			else {
				cssClasses.add("btn-" + _displayType);
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
				jspWriter.write("<span class=\"inline-item");

				if (Validator.isNotNull(_label)) {
					jspWriter.write(" inline-item-before");
				}

				jspWriter.write("\"><svg class=\"lexicon-icon lexicon-icon-");
				jspWriter.write(_icon);
				jspWriter.write("\" role=\"presentation\" viewBox=\"0 0 512 ");
				jspWriter.write("512\"><use xlink:href=\"");

				ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
					WebKeys.THEME_DISPLAY);

				String pathThemeImages = themeDisplay.getPathThemeImages();

				String spritemap = pathThemeImages.concat("/clay/icons.svg");

				jspWriter.write(spritemap);

				jspWriter.write("#");
				jspWriter.write(_icon);
				jspWriter.write("\" /></svg></span>");
			}

			if (Validator.isNotNull(_label)) {
				jspWriter.write(
					LanguageUtil.get(
						TagResourceBundleUtil.getResourceBundle(pageContext),
						_label));
			}

			return SKIP_BODY;
		}

		return EVAL_BODY_INCLUDE;
	}

	private static final String _ATTRIBUTE_NAMESPACE = "clay:button:";

	private boolean _alert;
	private String _ariaLabel;
	private boolean _block;
	private boolean _borderless;
	private boolean _disabled;
	private String _displayType = "primary";
	private String _icon;
	private String _label;
	private boolean _monospaced;
	private boolean _outline;
	private boolean _small;
	private String _title;
	private String _type = "button";

}