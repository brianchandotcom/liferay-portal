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

import com.liferay.frontend.taglib.clay.servlet.taglib.soy.FileCard;

import javax.servlet.jsp.JspException;

/**
 * @author Julien Castelain
 */
public class FileCardTag extends VerticalCardTag {

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		if (getIcon() == null) {
			setIcon("documents-and-media");
		}

		if (getStickerIcon() == null) {
			setStickerIcon("document-default");
		}

		return super.doStartTag();
	}

	public void setFileCard(FileCard fileCard) {
		setCardModel(fileCard);
	}

	private static final String _ATTRIBUTE_NAMESPACE = "clay:filecard:";

}