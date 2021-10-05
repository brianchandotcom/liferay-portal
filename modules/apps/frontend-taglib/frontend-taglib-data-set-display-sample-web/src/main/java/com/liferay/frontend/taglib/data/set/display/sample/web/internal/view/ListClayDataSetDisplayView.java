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

package com.liferay.frontend.taglib.data.set.display.sample.web.internal.view;

import com.liferay.frontend.taglib.clay.data.set.ClayDataSetDisplayView;
import com.liferay.frontend.taglib.clay.data.set.view.list.BaseListClayDataSetDisplayView;

import org.osgi.service.component.annotations.Component;

/**
 * @author Javier de Arcos
 */
@Component(
	enabled = false, property = "clay.data.set.display.name=dataSetDisplay",
	service = ClayDataSetDisplayView.class
)
public class ListClayDataSetDisplayView extends BaseListClayDataSetDisplayView {

	@Override
	public String getDescription() {
		return "fox";
	}

	@Override
	public String getTitle() {
		return "easy";
	}

}