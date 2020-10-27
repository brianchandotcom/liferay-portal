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

package com.liferay.frontend.taglib.clay.sample.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.soy.UserCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItemListBuilder;
import com.liferay.portal.kernel.security.RandomUtil;

import java.util.List;

/**
 * @author Carlos Lancha
 */
public class ClaySampleUserCard implements UserCard {

	public List<DropdownItem> getActionDropdownItems() {
		if (_actionDropdownItems != null) {
			return _actionDropdownItems;
		}

		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.setHref("#1");
				dropdownItem.setLabel("Edit");
				dropdownItem.setSeparator(true);
			}
		).add(
			dropdownItem -> {
				dropdownItem.setHref("#2");
				dropdownItem.setLabel("Save");
			}
		).build();
	}

	public String getCssClass() {
		if (_cssClass != null) {
			return _cssClass;
		}

		return "custom-css-class";
	}

	public String getHref() {
		if (_href != null) {
			return _href;
		}

		return "#user-card-href";
	}

	public String getIcon() {
		return _icon;
	}

	public String getId() {
		if (_id != null) {
			return _id;
		}

		_currentIdNumber = _currentIdNumber + 1;

		return "userCardId" + _currentIdNumber;
	}

	public String getImageAlt() {
		if (_imageAlt != null) {
			return _imageAlt;
		}

		return "User Card Image Alt Text";
	}

	public String getImageSrc() {
		return _imageSrc;
	}

	public String getInputName() {
		if (_inputName != null) {
			return _inputName;
		}

		return "user-card-input-name";
	}

	public String getInputValue() {
		if (_inputValue != null) {
			return _inputValue;
		}

		return "user-card-input-value";
	}

	public List<LabelItem> getLabels() {
		if (_labels != null) {
			return _labels;
		}

		int numItems = 1 + RandomUtil.nextInt(3);

		return LabelItemListBuilder.add(
			labelItem -> {
				labelItem.setLabel("Approved");
				labelItem.setStyle("success");
			}
		).add(
			() -> numItems > 1, labelItem -> labelItem.setLabel("Pending")
		).add(
			() -> numItems > 2,
			labelItem -> {
				labelItem.setLabel("Canceled");
				labelItem.setStyle("danger");
			}
		).build();
	}

	public String getName() {
		if (_name != null) {
			return _name;
		}

		return "User Name";
	}

	public String getSubtitle() {
		if (_subtitle != null) {
			return _subtitle;
		}

		return "Latest Action";
	}

	public String getUserColorClass() {
		if (_userColorClass != null) {
			return _userColorClass;
		}

		return "info";
	}

	public boolean isDisabled() {
		return _disabled;
	}

	public boolean isSelectable() {
		return _selectable;
	}

	public boolean isSelected() {
		return _selected;
	}

	public void setActionDropdownItems(List<DropdownItem> actionDropdownItems) {
		_actionDropdownItems = actionDropdownItems;
	}

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setDisabled(boolean disabled) {
		_disabled = disabled;
	}

	public void setHref(String href) {
		_href = href;
	}

	public void setIcon(String icon) {
		_icon = icon;
	}

	public void setId(String id) {
		_id = id;
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

	public void setSelectable(boolean selectable) {
		_selectable = selectable;
	}

	public void setSelected(boolean selected) {
		_selected = selected;
	}

	public void setSubtitle(String subtitle) {
		_subtitle = subtitle;
	}

	public void setUserColorClass(String userColorClass) {
		_userColorClass = userColorClass;
	}

	private List<DropdownItem> _actionDropdownItems;
	private String _cssClass;
	private int _currentIdNumber;
	private boolean _disabled;
	private String _href;
	private String _icon;
	private String _id;
	private String _imageAlt;
	private String _imageSrc;
	private String _inputName;
	private String _inputValue;
	private List<LabelItem> _labels;
	private String _name;
	private boolean _selectable = true;
	private boolean _selected;
	private String _subtitle;
	private String _userColorClass;

}