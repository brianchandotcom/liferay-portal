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
import com.liferay.frontend.taglib.clay.servlet.taglib.soy.HorizontalCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.taglib.util.TagResourceBundleUtil;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

/**
 * @author Marko Cikos
 */
public class HorizontalCardTag extends BaseContainerTag {

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		Map<String, String> data = getData();

		if (data != null) {
			for (Map.Entry<String, String> entry : data.entrySet()) {
				setDynamicAttribute(
					StringPool.BLANK, "data-" + entry.getKey(),
					entry.getValue());
			}
		}

		return super.doStartTag();
	}

	public List<DropdownItem> getActionDropdownItems() {
		if ((_actionDropdownItems == null) && (_horizontalCard != null)) {
			return _horizontalCard.getActionDropdownItems();
		}

		return _actionDropdownItems;
	}

	@Override
	public String getCssClass() {
		if ((super.getCssClass() == null) && (_horizontalCard != null)) {
			if (_horizontalCard.getCssClass() != null) {
				return _horizontalCard.getCssClass();
			}

			if (_horizontalCard.getElementClasses() != null) {
				return _horizontalCard.getElementClasses();
			}
		}

		return super.getCssClass();
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
	 */
	@Deprecated
	public Map<String, String> getData() {
		if ((_data == null) && (_horizontalCard != null)) {
			return _horizontalCard.getData();
		}

		return _data;
	}

	public String getHref() {
		if ((_href == null) && (_horizontalCard != null)) {
			return _horizontalCard.getHref();
		}

		return _href;
	}

	public String getIcon() {
		if (_icon == null) {
			if ((_horizontalCard != null) &&
				(_horizontalCard.getIcon() != null)) {

				return _horizontalCard.getIcon();
			}

			return "folder";
		}

		return _icon;
	}

	@Override
	public String getId() {
		if ((super.getId() == null) && (_horizontalCard != null)) {
			return _horizontalCard.getId();
		}

		return super.getId();
	}

	public String getInputName() {
		if ((_inputName == null) && (_horizontalCard != null)) {
			return _horizontalCard.getInputName();
		}

		return _inputName;
	}

	public String getInputValue() {
		if ((_inputValue == null) && (_horizontalCard != null)) {
			return _horizontalCard.getInputValue();
		}

		return _inputValue;
	}

	public String getTitle() {
		String title = _title;

		if ((_title == null) && (_horizontalCard != null)) {
			title = _horizontalCard.getTitle();
		}

		return LanguageUtil.get(
			TagResourceBundleUtil.getResourceBundle(pageContext), title);
	}

	public Boolean isDisabled() {
		if (_disabled == null) {
			if (_horizontalCard != null) {
				return _horizontalCard.isDisabled();
			}

			return false;
		}

		return _disabled;
	}

	public Boolean isSelectable() {
		if (_selectable == null) {
			if (_horizontalCard != null) {
				return _horizontalCard.isSelectable();
			}

			return false;
		}

		return _selectable;
	}

	public Boolean isSelected() {
		if (_selected == null) {
			if (_horizontalCard != null) {
				return _horizontalCard.isSelected();
			}

			return false;
		}

		return _selected;
	}

	public void setActionDropdownItems(List<DropdownItem> actionDropdownItems) {
		_actionDropdownItems = actionDropdownItems;
	}

	public void setDisabled(Boolean disabled) {
		_disabled = disabled;
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
	 */
	@Deprecated
	public void setGroupName(String groupName) {
		_groupName = groupName;
	}

	public void setHorizontalCard(HorizontalCard horizontalCard) {
		_horizontalCard = horizontalCard;
	}

	public void setHref(String href) {
		_href = href;
	}

	public void setIcon(String icon) {
		_icon = icon;
	}

	public void setInputName(String inputName) {
		_inputName = inputName;
	}

	public void setInputValue(String inputValue) {
		_inputValue = inputValue;
	}

	public void setSelectable(Boolean selectable) {
		_selectable = selectable;
	}

	public void setSelected(Boolean selected) {
		_selected = selected;
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
	 */
	@Deprecated
	public void setSpritemap(String spritemap) {
		_spritemap = spritemap;
	}

	public void setTitle(String title) {
		_title = title;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_actionDropdownItems = null;
		_data = null;
		_disabled = null;
		_groupName = null;
		_horizontalCard = null;
		_href = null;
		_icon = null;
		_inputName = null;
		_inputValue = null;
		_selectable = null;
		_selected = null;
		_spritemap = null;
		_title = null;
	}

	@Override
	protected String getHydratedModuleName() {
		return "frontend-taglib-clay/HorizontalCard";
	}

	@Override
	protected Map<String, Object> prepareProps(Map<String, Object> props) {
		props.put("actions", getActionDropdownItems());
		props.put("disabled", isDisabled());
		props.put("href", getHref());
		props.put("inputName", getInputName());
		props.put("inputValue", getInputValue());
		props.put("selectable", isSelectable());
		props.put("selected", isSelected());
		props.put("symbol", getIcon());
		props.put("title", getTitle());

		return super.prepareProps(props);
	}

	@Override
	protected String processCssClasses(Set<String> cssClasses) {
		if (isSelectable()) {
			cssClasses.add("card-type-directory");
			cssClasses.add("form-check");
			cssClasses.add("form-check-card");
			cssClasses.add("form-check-middle-left");
		}
		else {
			cssClasses.add("card");
		}

		return super.processCssClasses(cssClasses);
	}

	@Override
	protected int processStartTag() throws Exception {
		super.processStartTag();

		JspWriter jspWriter = pageContext.getOut();

		if (isSelectable()) {
			jspWriter.write("<div class=\"custom-control custom-checkbox\">");
			jspWriter.write("<label><input");

			if (isSelected()) {
				jspWriter.write(" checked");
			}

			jspWriter.write(" class=\"custom-control-input\"");

			if (isDisabled()) {
				jspWriter.write(" disabled");
			}

			String inputName = getInputName();

			if (inputName != null) {
				jspWriter.write(" name=\"");
				jspWriter.write(inputName);
				jspWriter.write("\"");
			}

			jspWriter.write(" type=\"checkbox\"");

			String inputValue = getInputValue();

			if (inputValue != null) {
				jspWriter.write(" value=\"");
				jspWriter.write(inputValue);
				jspWriter.write("\"");
			}

			jspWriter.write(" /><span class=\"custom-control-label\"></span>");

			_writeBody(jspWriter);

			jspWriter.write("</label></div>");
		}
		else {
			_writeBody(jspWriter);
		}

		return SKIP_BODY;
	}

	private void _writeBody(JspWriter jspWriter) throws Exception {
		jspWriter.write("<div class=\"card card-horizontal\"><div class=\"");
		jspWriter.write("card-body\"><div class=\"card-row\"><div class=\"");
		jspWriter.write("autofit-col\">");

		StickerTag stickerTag = new StickerTag();

		stickerTag.setIcon(getIcon());
		stickerTag.setInline(true);

		stickerTag.doTag(pageContext);

		jspWriter.write("</div><div class=\"autofit-col autofit-col-expand");
		jspWriter.write(" autofit-col-gutters\"><p class=\"card-title\"");

		String title = getTitle();

		if (title != null) {
			jspWriter.write(" title=\"");
			jspWriter.write(title);
			jspWriter.write("\"");
		}

		jspWriter.write("><span class=\"text-truncate-inline\">");

		String href = getHref();
		Boolean disabled = isDisabled();

		if ((href != null) && !disabled) {
			LinkTag linkTag = new LinkTag();

			linkTag.setCssClass("text-truncate");
			linkTag.setHref(href);

			if (title != null) {
				linkTag.setLabel(title);
			}

			linkTag.doTag(pageContext);
		}
		else {
			jspWriter.write("<span class=\"text-truncate\">");

			if (title != null) {
				jspWriter.write(title);
			}

			jspWriter.write("</span>");
		}

		jspWriter.write("</span></p></div>");

		if (!ListUtil.isEmpty(getActionDropdownItems())) {
			jspWriter.write("<div class=\"autofit-col\">");

			DropdownActionsTag dropdownActionsTag = new DropdownActionsTag();

			dropdownActionsTag.setDropdownItems(getActionDropdownItems());

			if (disabled) {
				dropdownActionsTag.setDynamicAttribute(
					StringPool.BLANK, "disabled", disabled);
			}

			dropdownActionsTag.doTag(pageContext);

			jspWriter.write("</div>");
		}

		jspWriter.write("</div></div></div>");
	}

	private static final String _ATTRIBUTE_NAMESPACE = "clay:horizontal-card:";

	private List<DropdownItem> _actionDropdownItems;
	private Map<String, String> _data;
	private Boolean _disabled;
	private String _groupName;
	private HorizontalCard _horizontalCard;
	private String _href;
	private String _icon;
	private String _inputName;
	private String _inputValue;
	private Boolean _selectable;
	private Boolean _selected;
	private String _spritemap;
	private String _title;

}