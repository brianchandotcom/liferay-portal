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

import com.liferay.frontend.taglib.clay.servlet.taglib.soy.HorizontalCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;

import java.util.List;

/**
 * @author Marko Cikos
 */
public class ClaySampleHorizontalCard implements HorizontalCard {

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.setHref("#1");
				dropdownItem.setLabel("Custom Edit");
			}
		).add(
			dropdownItem -> {
				dropdownItem.setHref("#2");
				dropdownItem.setLabel("Custom Save");
			}
		).build();
	}

	@Override
	public String getCssClass() {
		return "custom-horizontal-card-css-class";
	}

	@Override
	public String getHref() {
		return "#custom-horizontal-card-href";
	}

	@Override
	public String getIcon() {
		return "page";
	}

	@Override
	public String getId() {
		return "customHorizontalCardId";
	}

	@Override
	public String getInputName() {
		return "custom-horizontal-card-input-name";
	}

	@Override
	public String getInputValue() {
		return "custom-horizontal-card-input-value";
	}

	@Override
	public String getTitle() {
		return "asset-title";
	}

	@Override
	public boolean isDisabled() {
		return false;
	}

	@Override
	public boolean isSelectable() {
		return true;
	}

	@Override
	public boolean isSelected() {
		return true;
	}

}