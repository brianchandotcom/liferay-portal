/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.taglib.clay.sample.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.HorizontalCard;
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