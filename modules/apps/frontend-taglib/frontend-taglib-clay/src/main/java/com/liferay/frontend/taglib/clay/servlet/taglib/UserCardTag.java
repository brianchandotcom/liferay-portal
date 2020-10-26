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
import com.liferay.frontend.taglib.clay.servlet.taglib.soy.UserCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

/**
 * @author Carlos Lancha
 */
public class UserCardTag extends BaseContainerTag {

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		return super.doStartTag();
	}

	public List<DropdownItem> getActionDropdownItems() {
		if ((_actionDropdownItems == null) && (_userCard != null)) {
			return _userCard.getActionDropdownItems();
		}

		return _actionDropdownItems;
	}

	@Override
	public String getCssClass() {
		if ((super.getCssClass() == null) && (_userCard != null)) {
			if (_userCard.getCssClass() != null) {
				return _userCard.getCssClass();
			}

			if (_userCard.getElementClasses() != null) {
				return _userCard.getElementClasses();
			}
		}

		return super.getCssClass();
	}

	public String getHref() {
		if ((_href == null) && (_userCard != null)) {
			return _userCard.getHref();
		}

		return _href;
	}

	public String getIcon() {
		if (_icon == null) {
			if (_userCard != null) {
				return _userCard.getIcon();
			}

			return "user";
		}

		return _icon;
	}

	@Override
	public String getId() {
		if ((super.getId() == null) && (_userCard != null)) {
			return _userCard.getId();
		}

		return super.getId();
	}

	public String getImageAlt() {
		if ((_imageAlt == null) && (_userCard != null)) {
			return _userCard.getImageAlt();
		}

		return _imageAlt;
	}

	public String getImageSrc() {
		if ((_imageSrc == null) && (_userCard != null)) {
			return _userCard.getImageSrc();
		}

		return _imageSrc;
	}

	public String getInputName() {
		if ((_inputName == null) && (_userCard != null)) {
			return _userCard.getInputName();
		}

		return _inputName;
	}

	public String getInputValue() {
		if ((_inputValue == null) && (_userCard != null)) {
			return _userCard.getInputValue();
		}

		return _inputValue;
	}

	public List<LabelItem> getLabels() {
		if ((_labels == null) && (_userCard != null)) {
			return _userCard.getLabels();
		}

		return _labels;
	}

	public String getName() {
		if ((_name == null) && (_userCard != null)) {
			return _userCard.getName();
		}

		return _name;
	}

	public String getSubtitle() {
		if ((_subtitle == null) && (_userCard != null)) {
			return _userCard.getSubtitle();
		}

		return _subtitle;
	}

	public UserCard getUserCard() {
		return _userCard;
	}

	public String getUserColorClass() {
		if ((_userColorClass == null) && (_userCard != null)) {
			return _userCard.getUserColorClass();
		}

		return _userColorClass;
	}

	public Boolean isDisabled() {
		if (_disabled == null) {
			if (_userCard != null) {
				return _userCard.isDisabled();
			}

			return false;
		}

		return _disabled;
	}

	public Boolean isSelectable() {
		if (_selectable == null) {
			if (_userCard != null) {
				return _userCard.isSelectable();
			}

			return true;
		}

		return _selectable;
	}

	public Boolean isSelected() {
		if (_selected == null) {
			if (_userCard != null) {
				return _userCard.isSelected();
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

	public void setHref(String href) {
		_href = href;
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

	public void setInputName(String inputName) {
		_inputName = inputName;
	}

	public void setInputValue(String inputValue) {
		_inputValue = inputValue;
	}

	public void setLabels(List<LabelItem> labels) {
		_labels = labels;
	}

	public void setName(String name) {
		_name = name;
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

	public void setSubtitle(String subtitle) {
		_subtitle = subtitle;
	}

	public void setUserCard(UserCard userCard) {
		_userCard = userCard;
	}

	public void setUserColorClass(String userColorClass) {
		_userColorClass = userColorClass;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_actionDropdownItems = null;
		_disabled = null;
		_groupName = null;
		_href = null;
		_icon = null;
		_imageAlt = null;
		_imageSrc = null;
		_inputName = null;
		_inputValue = null;
		_labels = null;
		_name = null;
		_selectable = null;
		_selected = null;
		_spritemap = null;
		_subtitle = null;
		_userCard = null;
		_userColorClass = null;
	}

	@Override
	protected String getHydratedModuleName() {
		return "frontend-taglib-clay/cards/UserCard";
	}

	@Override
	protected Map<String, Object> prepareProps(Map<String, Object> props) {
		props.put("actions", getActionDropdownItems());
		props.put("description", getSubtitle());
		props.put("disabled", isDisabled());
		props.put("href", getHref());
		props.put("inputName", getInputName());
		props.put("inputValue", getInputValue());
		props.put("userImageAlt", getImageAlt());
		props.put("userImageSrc", getImageSrc());
		props.put("userSymbol", getIcon());
		props.put("labels", getLabels());
		props.put("name", getName());
		props.put("selectable", isSelectable());
		props.put("selected", isSelected());
		props.put("userDisplayType", getUserColorClass());

		return super.prepareProps(props);
	}

	@Override
	protected String processCssClasses(Set<String> cssClasses) {
		Boolean selectable = isSelectable();

		if ((selectable == null) || !selectable) {
			cssClasses.add("card");
		}

		cssClasses.add("card-type-asset");
		cssClasses.add("form-check");
		cssClasses.add("form-check-card");
		cssClasses.add("form-check-top-left");
		cssClasses.add("user-card");

		return super.processCssClasses(cssClasses);
	}

	@Override
	protected int processStartTag() throws Exception {
		super.processStartTag();

		JspWriter jspWriter = pageContext.getOut();

		jspWriter.write("<div class=\"card\"><div class=\"aspect-ratio ");
		jspWriter.write("card-item-first\">");

		Boolean disabled = isDisabled();
		Boolean selectable = isSelectable();

		if (selectable) {
			jspWriter.write("<div class=\"custom-checkbox custom-control\">");
			jspWriter.write("<label><input ");

			jspWriter.write("class=\"custom-control-input\"");

			Boolean selected = isSelected();

			if (selected) {
				jspWriter.write("checked ");
			}

			if (disabled) {
				jspWriter.write("disabled ");
			}

			String inputName = getInputName();

			if (Validator.isNotNull(inputName)) {
				jspWriter.write("name=\"");
				jspWriter.write(inputName);
				jspWriter.write("\" ");
			}

			jspWriter.write("type=\"checkbox\" ");

			String inputValue = getInputValue();

			if (Validator.isNotNull(inputValue)) {
				jspWriter.write("value=\"");
				jspWriter.write(inputValue);
				jspWriter.write("\"");
			}

			jspWriter.write("/><span class=\"custom-control-label\"></span>");
		}

		jspWriter.write("<div class=\"aspect-ratio-item-center-middle ");
		jspWriter.write("card-type-asset-icon\">");

		StickerTag stickerTag = new StickerTag();

		stickerTag.setCssClass("sticker-user-icon");
		stickerTag.setDisplayType(getUserColorClass());
		stickerTag.setShape("circle");

		String imageSrc = getImageSrc();

		if (Validator.isNotNull(imageSrc)) {
			stickerTag.setImageAlt(getImageAlt());
			stickerTag.setImageSrc(imageSrc);
		}
		else {
			stickerTag.setIcon(getIcon());
		}

		stickerTag.doTag(pageContext);

		jspWriter.write("</div>");

		if (selectable) {
			jspWriter.write("</label></div>");
		}

		jspWriter.write("</div><div class=\"card-body\"><div ");
		jspWriter.write("class=\"card-row\"><div class=\"autofit-col ");
		jspWriter.write("autofit-col-expand\"><p class=\"card-title\"><span ");
		jspWriter.write("class=\"text-truncate-inline\">");

		String href = getHref();
		String name = getName();

		if (((disabled == null) || !disabled) && Validator.isNotNull(href)) {
			jspWriter.write("<a class=\"text-truncate\" href=\"");
			jspWriter.write(href);
			jspWriter.write("\">");
			jspWriter.write(name);
			jspWriter.write("</a>");
		}
		else {
			jspWriter.write("<span class=\"text-truncate\">");
			jspWriter.write(name);
			jspWriter.write("</span>");
		}

		jspWriter.write("</span></p><p class=\"card-subtitle\"><span class=\"");
		jspWriter.write("text-truncate-inline\"><span class=\"text-truncate\"");
		jspWriter.write(">");
		jspWriter.write(getSubtitle());
		jspWriter.write("</span></span></p>");

		List<LabelItem> labels = getLabels();

		if (!ListUtil.isEmpty(labels)) {
			jspWriter.write("<div class=\"card-detail\">");

			for (LabelItem labelItem : labels) {
				LabelTag labelTag = new LabelTag();

				if ((boolean)labelItem.get("dismissible")) {
					labelTag.setDismissible(true);
				}

				String displayType = (String)labelItem.get("displayType");

				if (Validator.isNotNull(displayType)) {
					labelTag.setDisplayType(displayType);
				}

				labelTag.setLabel((String)labelItem.get("label"));

				if ((boolean)labelItem.get("large")) {
					labelTag.setLarge(true);
				}

				labelTag.doTag(pageContext);
			}

			jspWriter.write("</div>");
		}

		jspWriter.write("</div>");

		if (!ListUtil.isEmpty(getActionDropdownItems())) {
			jspWriter.write("<div class=\"autofit-col\"><div class=\"dropdown");
			jspWriter.write("\"><div class=\"component-action dropdown-toggle");
			jspWriter.write("\">");

			IconTag iconTag = new IconTag();

			iconTag.setSymbol("ellipsis-v");

			iconTag.doTag(pageContext);

			jspWriter.write("</div></div></div>");
		}

		jspWriter.write("</div></div></div>");

		return SKIP_BODY;
	}

	private static final String _ATTRIBUTE_NAMESPACE = "clay:user_card:";

	private List<DropdownItem> _actionDropdownItems;
	private Boolean _disabled;
	private String _groupName;
	private String _href;
	private String _icon;
	private String _imageAlt;
	private String _imageSrc;
	private String _inputName;
	private String _inputValue;
	private List<LabelItem> _labels;
	private String _name;
	private Boolean _selectable;
	private Boolean _selected;
	private String _spritemap;
	private String _subtitle;
	private UserCard _userCard;
	private String _userColorClass;

}