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
public class FileCardTag extends BaseClayCardTag {

	@Override
	public int doStartTag() {
		setComponentBaseName("ClayFileCard");

		if (_fileCard != null) {
			_populateContext();
		}

		return super.doStartTag();
	}

	public void setFileCard(FileCard fileCard) {
		_fileCard = fileCard;

		super.setBaseClayCard(fileCard);
	}

	public void setIcon(String icon) {
		putValue("icon", icon);
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

		if (context.get("icon") == null) {
			setIcon(_fileCard.getIcon());
		}

		if (context.get("labels") == null) {
			setLabels(_fileCard.getLabels());
		}

		if (context.get("labelStylesMap") == null) {
			setLabelStylesMap(_fileCard.getLabelStylesMap());
		}

		if (context.get("stickerClasses") == null) {
			setStickerCssClass(_fileCard.getStickerCssClass());
		}

		if (context.get("stickerIcon") == null) {
			setStickerIcon(_fileCard.getStickerIcon());
		}

		if (context.get("stickerImageAlt") == null) {
			setStickerImageAlt(_fileCard.getStickerImageAlt());
		}

		if (context.get("stickerImageSrc") == null) {
			setStickerImageSrc(_fileCard.getStickerImageSrc());
		}

		if (context.get("stickerLabel") == null) {
			setStickerLabel(_fileCard.getStickerLabel());
		}

		if (context.get("stickerShape") == null) {
			setStickerShape(_fileCard.getStickerShape());
		}

		if (context.get("stickerStyle") == null) {
			setStickerStyle(_fileCard.getStickerStyle());
		}

		if (context.get("subtitle") == null) {
			setSubtitle(_fileCard.getSubtitle());
		}

		if (context.get("title") == null) {
			setTitle(_fileCard.getTitle());
		}
	}

	private FileCard _fileCard;

}