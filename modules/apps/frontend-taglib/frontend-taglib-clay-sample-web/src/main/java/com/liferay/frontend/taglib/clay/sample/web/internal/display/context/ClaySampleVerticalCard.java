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

import com.liferay.frontend.taglib.clay.servlet.taglib.soy.VerticalCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItemListBuilder;
import com.liferay.portal.kernel.security.RandomUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.List;
import java.util.Map;

/**
 * @author Julien Castelain
 */
public class ClaySampleVerticalCard implements VerticalCard {

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		if (_actionDropdownItems != null) {
			return _actionDropdownItems;
		}

		_actionDropdownItems = DropdownItemListBuilder.addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.add(
						dropdownItem -> {
							dropdownItem.setHref("#1");
							dropdownItem.setLabel("Option 1");
						}
					).add(
						dropdownItem -> {
							dropdownItem.setHref("#2");
							dropdownItem.setLabel("Option 2");
						}
					).add(
						dropdownItem -> dropdownItem.setType("divider")
					).build());

				dropdownGroupItem.setLabel("Group 1");
			}
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					DropdownItemListBuilder.add(
						dropdownItem -> {
							dropdownItem.setHref("#3");
							dropdownItem.setLabel("Option 1");
						}
					).add(
						dropdownItem -> {
							dropdownItem.setHref("#4");
							dropdownItem.setLabel("Option 2");
						}
					).build());

				dropdownGroupItem.setLabel("Group 2");
			}
		).build();

		return _actionDropdownItems;
	}

	@Override
	public String getCssClass() {
		if (_cssClass != null) {
			return _cssClass;
		}

		return "custom-vertical-card-css-class";
	}

	@Override
	public Map<String, String> getDynamicAttributes() {
		return HashMapBuilder.put(
			"data-id", getId()
		).put(
			"data-type", "vertical-card"
		).build();
	}

	@Override
	public String getHref() {
		if (_href != null) {
			return _href;
		}

		return "#vertical-card-href";
	}

	@Override
	public String getIcon() {
		if (_icon != null) {
			return _icon;
		}

		return "list";
	}

	@Override
	public String getId() {
		if (_id != null) {
			return _id;
		}

		_currentIdNumber = _currentIdNumber + 1;

		return "verticalCardId" + _currentIdNumber;
	}

	@Override
	public String getImageAlt() {
		return _imageAlt;
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

		return "vertical-card-input-name";
	}

	@Override
	public String getInputValue() {
		if (_inputValue != null) {
			return _inputValue;
		}

		return "vertical-card-input-value";
	}

	@Override
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
				labelItem.setLabel("Rejected");
				labelItem.setStyle("warning");
			}
		).build();
	}

	@Override
	public String getStickerCssClass() {
		return _stickerCssClass;
	}

	@Override
	public String getStickerIcon() {
		return _stickerIcon;
	}

	@Override
	public String getStickerImageAlt() {
		return _stickerImageAlt;
	}

	@Override
	public String getStickerImageSrc() {
		return _stickerImageSrc;
	}

	@Override
	public String getStickerLabel() {
		if (_stickerLabel != null) {
			return _stickerLabel;
		}

		return "JPG";
	}

	@Override
	public String getStickerShape() {
		if (_stickerShape != null) {
			return _stickerShape;
		}

		return "circle";
	}

	@Override
	public String getStickerStyle() {
		if (_stickerStyle != null) {
			return _stickerStyle;
		}

		return "warning";
	}

	@Override
	public String getSubtitle() {
		if (_subtitle != null) {
			return _subtitle;
		}

		return "Card Action";
	}

	@Override
	public String getTitle() {
		if (_title != null) {
			return _title;
		}

		return "Vertical Card";
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

	public void setLabels(List<LabelItem> labels) {
		_labels = labels;
	}

	public void setSelectable(boolean selectable) {
		_selectable = selectable;
	}

	public void setSelected(boolean selected) {
		_selected = selected;
	}

	public void setStickerCssClass(String stickerCssClass) {
		_stickerCssClass = stickerCssClass;
	}

	public void setStickerIcon(String stickerIcon) {
		_stickerIcon = stickerIcon;
	}

	public void setStickerImageAlt(String stickerImageAlt) {
		_stickerImageAlt = stickerImageAlt;
	}

	public void setStickerImageSrc(String stickerImageSrc) {
		_stickerImageSrc = stickerImageSrc;
	}

	public void setStickerLabel(String stickerLabel) {
		_stickerLabel = stickerLabel;
	}

	public void setStickerShape(String stickerShape) {
		_stickerShape = stickerShape;
	}

	public void setStickerStyle(String stickerStyle) {
		_stickerStyle = stickerStyle;
	}

	public void setSubtitle(String subtitle) {
		_subtitle = subtitle;
	}

	public void setTitle(String title) {
		_title = title;
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
	private boolean _selectable = true;
	private boolean _selected;
	private String _stickerCssClass;
	private String _stickerIcon;
	private String _stickerImageAlt;
	private String _stickerImageSrc;
	private String _stickerLabel;
	private String _stickerShape;
	private String _stickerStyle;
	private String _subtitle;
	private String _title;

}