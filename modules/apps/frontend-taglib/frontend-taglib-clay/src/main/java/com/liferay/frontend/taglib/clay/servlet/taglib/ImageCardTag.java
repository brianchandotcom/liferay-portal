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

import com.liferay.frontend.taglib.clay.servlet.taglib.soy.ImageCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;

import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;

/**
 * @author Julien Castelain
 */
public class ImageCardTag extends VerticalCardTag {

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		return super.doStartTag();
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		List<DropdownItem> actionDropdownItems = super.getActionDropdownItems();

		if ((actionDropdownItems == null) && (_imageCard != null)) {
			return _imageCard.getActionDropdownItems();
		}

		return actionDropdownItems;
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public String getGroupName() {
		String groupName = super.getGroupName();

		if ((groupName == null) && (_imageCard != null)) {
			return _imageCard.getGroupName();
		}

		return groupName;
	}

	@Override
	public String getHref() {
		String href = super.getHref();

		if ((href == null) && (_imageCard != null)) {
			return _imageCard.getHref();
		}

		return href;
	}

	@Override
	public String getIcon() {
		String icon = super.getIcon();

		if ((icon == null) && (_imageCard != null)) {
			return _imageCard.getIcon();
		}

		return icon;
	}

	@Override
	public String getImageAlt() {
		String imageAlt = super.getImageAlt();

		if ((imageAlt == null) && (_imageCard != null)) {
			return _imageCard.getImageAlt();
		}

		return imageAlt;
	}

	public ImageCard getImageCard() {
		return _imageCard;
	}

	@Override
	public String getImageSrc() {
		String imageSrc = super.getImageSrc();

		if ((imageSrc == null) && (_imageCard != null)) {
			return _imageCard.getImageSrc();
		}

		return imageSrc;
	}

	@Override
	public String getInputName() {
		String inputName = super.getInputName();

		if ((inputName == null) && (_imageCard != null)) {
			return _imageCard.getInputName();
		}

		return inputName;
	}

	@Override
	public String getInputValue() {
		String inputValue = super.getInputValue();

		if ((inputValue == null) && (_imageCard != null)) {
			return _imageCard.getInputValue();
		}

		return inputValue;
	}

	@Override
	public List<LabelItem> getLabels() {
		List<LabelItem> labels = super.getLabels();

		if ((labels == null) && (_imageCard != null)) {
			return _imageCard.getLabels();
		}

		return labels;
	}

	@Override
	public Map<String, String> getLabelStylesMap() {
		Map<String, String> labelStylesMap = super.getLabelStylesMap();

		if ((labelStylesMap == null) && (_imageCard != null)) {
			return _imageCard.getLabelStylesMap();
		}

		return labelStylesMap;
	}

	@Override
	public String getStickerCssClass() {
		String stickerCssClass = super.getStickerCssClass();

		if ((stickerCssClass == null) && (_imageCard != null)) {
			return _imageCard.getStickerCssClass();
		}

		return stickerCssClass;
	}

	@Override
	public String getStickerIcon() {
		String stickerIcon = super.getStickerIcon();

		if ((stickerIcon == null) && (_imageCard != null)) {
			return _imageCard.getStickerIcon();
		}

		return stickerIcon;
	}

	@Override
	public String getStickerImageAlt() {
		String stickerImageAlt = super.getStickerImageAlt();

		if ((stickerImageAlt == null) && (_imageCard != null)) {
			return _imageCard.getStickerImageAlt();
		}

		return stickerImageAlt;
	}

	@Override
	public String getStickerImageSrc() {
		String stickerImageSrc = super.getStickerImageSrc();

		if ((stickerImageSrc == null) && (_imageCard != null)) {
			return _imageCard.getStickerImageSrc();
		}

		return stickerImageSrc;
	}

	@Override
	public String getStickerLabel() {
		String stickerLabel = super.getStickerLabel();

		if ((stickerLabel == null) && (_imageCard != null)) {
			return _imageCard.getStickerLabel();
		}

		return stickerLabel;
	}

	@Override
	public String getStickerShape() {
		String stickerShape = super.getStickerShape();

		if ((stickerShape == null) && (_imageCard != null)) {
			return _imageCard.getStickerShape();
		}

		return stickerShape;
	}

	@Override
	public String getStickerStyle() {
		String stickerStyle = super.getStickerStyle();

		if ((stickerStyle == null) && (_imageCard != null)) {
			return _imageCard.getStickerStyle();
		}

		return stickerStyle;
	}

	@Override
	public String getSubtitle() {
		String subtitle = super.getSubtitle();

		if ((subtitle == null) && (_imageCard != null)) {
			return _imageCard.getSubtitle();
		}

		return subtitle;
	}

	@Override
	public String getTitle() {
		String title = super.getTitle();

		if ((title == null) && (_imageCard != null)) {
			return _imageCard.getTitle();
		}

		return title;
	}

	@Override
	public Boolean isDisabled() {
		Boolean disabled = super.isDisabled();

		if (disabled == null) {
			if (_imageCard != null) {
				return _imageCard.isDisabled();
			}

			return false;
		}

		return disabled;
	}

	@Override
	public Boolean isFlushHorizontal() {
		Boolean flushHorizontal = super.isFlushHorizontal();

		if (flushHorizontal == null) {
			if (_imageCard != null) {
				return _imageCard.isFlushHorizontal();
			}

			return false;
		}

		return flushHorizontal;
	}

	@Override
	public Boolean isFlushVertical() {
		Boolean flushVertical = super.isFlushVertical();

		if (flushVertical == null) {
			if (_imageCard != null) {
				return _imageCard.isFlushVertical();
			}

			return false;
		}

		return flushVertical;
	}

	@Override
	public Boolean isSelectable() {
		Boolean selectable = super.isSelectable();

		if (selectable == null) {
			if (_imageCard != null) {
				return _imageCard.isSelectable();
			}

			return true;
		}

		return selectable;
	}

	@Override
	public Boolean isSelected() {
		Boolean selected = super.isSelected();

		if (selected == null) {
			if (_imageCard != null) {
				return _imageCard.isSelected();
			}

			return false;
		}

		return selected;
	}

	public void setImageCard(ImageCard imageCard) {
		_imageCard = imageCard;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_imageCard = null;
	}

	private static final String _ATTRIBUTE_NAMESPACE = "clay:imagecard:";

	private ImageCard _imageCard;

}