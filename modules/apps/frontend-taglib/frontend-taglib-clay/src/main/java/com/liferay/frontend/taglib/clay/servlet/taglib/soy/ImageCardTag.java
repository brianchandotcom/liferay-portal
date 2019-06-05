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

package com.liferay.frontend.taglib.clay.servlet.taglib.soy;

import com.liferay.frontend.taglib.clay.servlet.taglib.soy.base.BaseClayCardTag;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;

import java.util.List;
import java.util.Map;

/**
 * @author Julien Castelain
 */
public class ImageCardTag extends BaseClayCardTag {

	@Override
	public int doStartTag() {
		setComponentBaseName("ClayImageCard");

		if (_imageCard != null) {
			_populateContext();
		}

		return super.doStartTag();
	}

	public void setIcon(String icon) {
		putValue("icon", icon);
	}

	public void setImageAlt(String imageAlt) {
		putValue("imageAlt", imageAlt);
	}

	public void setImageCard(ImageCard imageCard) {
		_imageCard = imageCard;

		super.setBaseClayCard(imageCard);
	}

	public void setImageSrc(String imageSrc) {
		putValue("imageSrc", imageSrc);
	}

	public void setLabels(List<LabelItem> labelItems) {
		putValue("labels", labelItems);
	}

	public void setLabelStylesMap(Map<String, String> labelStylesMap) {
		putValue("labelStylesMap", labelStylesMap);
	}

	public void setStickerCssClass(String stickerCssClass) {
		putValue("stickerClasses", stickerCssClass);
	}

	public void setStickerIcon(String stickerIcon) {
		putValue("stickerIcon", stickerIcon);
	}

	public void setStickerImageAlt(String stickerImageAlt) {
		putValue("stickerImageAlt", stickerImageAlt);
	}

	public void setStickerImageSrc(String stickerImageSrc) {
		putValue("stickerImageSrc", stickerImageSrc);
	}

	public void setStickerLabel(String stickerLabel) {
		putValue("stickerLabel", stickerLabel);
	}

	public void setStickerShape(String stickerShape) {
		putValue("stickerShape", stickerShape);
	}

	public void setStickerStyle(String stickerStyle) {
		putValue("stickerStyle", stickerStyle);
	}

	public void setSubtitle(String subtitle) {
		putValue("subtitle", subtitle);
	}

	public void setTitle(String title) {
		putValue("title", title);
	}

	private void _populateContext() {
		Map<String, Object> context = getContext();

		if (context.get("aspectRatioClasses") == null) {
			setAspectRatioCssClasses(_imageCard.getAspectRatioCssClasses());
		}

		if (context.get("icon") == null) {
			setIcon(_imageCard.getIcon());
		}

		if (context.get("imageAlt") == null) {
			setImageAlt(_imageCard.getImageAlt());
		}

		if (context.get("imageSrc") == null) {
			setImageSrc(_imageCard.getImageSrc());
		}

		if (context.get("labels") == null) {
			setLabels(_imageCard.getLabels());
		}

		if (context.get("labelStylesMap") == null) {
			setLabelStylesMap(_imageCard.getLabelStylesMap());
		}

		if (context.get("stickerClasses") == null) {
			setStickerCssClass(_imageCard.getStickerCssClass());
		}

		if (context.get("stickerIcon") == null) {
			setStickerIcon(_imageCard.getStickerIcon());
		}

		if (context.get("stickerImageAlt") == null) {
			setStickerImageAlt(_imageCard.getStickerImageAlt());
		}

		if (context.get("stickerImageSrc") == null) {
			setStickerImageSrc(_imageCard.getStickerImageSrc());
		}

		if (context.get("stickerLabel") == null) {
			setStickerLabel(_imageCard.getStickerLabel());
		}

		if (context.get("stickerShape") == null) {
			setStickerShape(_imageCard.getStickerShape());
		}

		if (context.get("stickerStyle") == null) {
			setStickerStyle(_imageCard.getStickerStyle());
		}

		if (context.get("subtitle") == null) {
			setSubtitle(_imageCard.getSubtitle());
		}

		if (context.get("title") == null) {
			setTitle(_imageCard.getTitle());
		}
	}

	private ImageCard _imageCard;

}