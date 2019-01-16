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

import java.util.Map;

/**
 * @author Julien Castelain
 */
public class HorizontalCardTag extends BaseClayCardTag {

	@Override
	public int doStartTag() {
		setComponentBaseName("ClayHorizontalCard");

		if (_horizontalCard != null) {
			_populateContext();
		}

		return super.doStartTag();
	}

	public void setHorizontalCard(HorizontalCard horizontalCard) {
		_horizontalCard = horizontalCard;

		super.setBaseClayCard(horizontalCard);
	}

	public void setIcon(String icon) {
		putValue("icon", icon);
	}

	public void setTitle(String title) {
		putValue("title", title);
	}

	private void _populateContext() {
		Map<String, Object> context = getContext();

		if (context.get("icon") == null) {
			setIcon(_horizontalCard.getIcon());
		}

		if (context.get("title") == null) {
			setTitle(_horizontalCard.getTitle());
		}
	}

	private HorizontalCard _horizontalCard;

}