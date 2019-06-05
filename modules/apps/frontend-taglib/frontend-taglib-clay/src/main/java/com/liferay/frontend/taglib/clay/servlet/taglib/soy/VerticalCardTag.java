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
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Map;

/**
 * @author Eudaldo Alonso
 */
public class VerticalCardTag extends BaseClayCardTag {

	@Override
	public int doStartTag() {
		Map<String, Object> context = getContext();

		String imageSrc = (String)context.get("imageSrc");

		if (((_verticalCard != null) &&
			 Validator.isNotNull(_verticalCard.getImageSrc())) ||
			Validator.isNotNull(imageSrc)) {

			setComponentBaseName("ClayImageCard");
		}
		else {
			setComponentBaseName("ClayFileCard");
		}

		if (_verticalCard != null) {
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

	public void setVerticalCard(VerticalCard verticalCard) {
		_verticalCard = verticalCard;

		super.setBaseClayCard(verticalCard);
	}

	private void _populateContext() {
		Map<String, Object> context = getContext();

		if (context.get("aspectRatioClasses") == null) {
			setAspectRatioCssClasses(_verticalCard.getAspectRatioCssClasses());
		}

		if (context.get("icon") == null) {
			setIcon(_verticalCard.getIcon());
		}

		if (context.get("imageAlt") == null) {
			setImageAlt(_verticalCard.getImageAlt());
		}

		if (context.get("imageSrc") == null) {
			setImageSrc(_verticalCard.getImageSrc());
		}

		if (context.get("labels") == null) {
			setLabels(_verticalCard.getLabels());
		}

		if (context.get("labelStylesMap") == null) {
			setLabelStylesMap(_verticalCard.getLabelStylesMap());
		}

		if (context.get("stickerClasses") == null) {
			setStickerCssClass(_verticalCard.getStickerCssClass());
		}

		if (context.get("stickerIcon") == null) {
			setStickerIcon(_verticalCard.getStickerIcon());
		}

		if (context.get("stickerImageAlt") == null) {
			setStickerImageAlt(_verticalCard.getStickerImageAlt());
		}

		if (context.get("stickerImageSrc") == null) {
			setStickerImageSrc(_verticalCard.getStickerImageSrc());
		}

		if (context.get("stickerLabel") == null) {
			setStickerLabel(_verticalCard.getStickerLabel());
		}

		if (context.get("stickerShape") == null) {
			setStickerShape(_verticalCard.getStickerShape());
		}

		if (context.get("stickerStyle") == null) {
			setStickerStyle(_verticalCard.getStickerStyle());
		}

		if (context.get("subtitle") == null) {
			setSubtitle(_verticalCard.getSubtitle());
		}

		if (context.get("title") == null) {
			setTitle(_verticalCard.getTitle());
		}
	}

	private VerticalCard _verticalCard;

}