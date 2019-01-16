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

import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.RenderRequest;

/**
 * @author Eudaldo Alonso
 */
public abstract class BaseHorizontalCard
	extends BaseBaseClayCard implements HorizontalCard {

	public BaseHorizontalCard(
		BaseModel<?> baseModel, RenderRequest renderRequest,
		RowChecker rowChecker) {

		super(baseModel, rowChecker);

		this.renderRequest = renderRequest;

		themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	protected final RenderRequest renderRequest;
	protected final ThemeDisplay themeDisplay;

}