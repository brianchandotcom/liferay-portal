/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.clay.sample.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.UserCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;

import java.util.List;

/**
 * @author Carlos Lancha
 */
public class ClaySampleUserCard implements UserCard {

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		if (_actionDropdownItems != null) {
			return _actionDropdownItems;
		}

		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.setHref("#1");
				dropdownItem.setLabel("Edit");
			}
		).add(
			dropdownItem -> {
				dropdownItem.setHref("#2");
				dropdownItem.setLabel("Save");
			}
		).build();
	}

	@Override
	public String getCssClass() {
		if (_cssClass != null) {
			return _cssClass;
		}

		return "custom-css-class";
	}

	@Override
	public String getHref() {
		if (_href != null) {
			return _href;
		}

		return "#user-card-href";
	}

	@Override
	public String getIcon() {
		return _icon;
	}

	@Override
	public String getId() {
		if (_id != null) {
			return _id;
		}

		_currentIdNumber = _currentIdNumber + 1;

		return "userCardId" + _currentIdNumber;
	}

	@Override
	public String getImageAlt() {
		if (_imageAlt != null) {
			return _imageAlt;
		}

		return "User Card Image Alt Text";
	}

	@Override
	public String getImageSrc() {
		return _imageSrc;
	}

	@Override
	public String getInputName() {
		if (_inputName != null) {
			return _inputName;
		}

		return "user-card-input-name";
	}

	@Override
	public String getInputValue() {
		if (_inputValue != null) {
			return _inputValue;
		}

		return "user-card-input-value";
	}

	@Override
	public String getName() {
		if (_name != null) {
			return _name;
		}

		return "User Name";
	}

	@Override
	public String getSubtitle() {
		if (_subtitle != null) {
			return _subtitle;
		}

		return "Latest Action";
	}

	@Override
	public String getUserColorClass() {
		if (_userColorClass != null) {
			return _userColorClass;
		}

		return "info";
	}

	@Override
	public boolean isDisabled() {
		return _disabled;
	}

	@Override
	public boolean isSelectable() {
		return _selectable;
	}

	@Override
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
	private String _name;
	private boolean _selectable = true;
	private boolean _selected;
	private String _subtitle;
	private String _userColorClass;

}