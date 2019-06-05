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

package com.liferay.frontend.taglib.clay.servlet.taglib.soy.base;

import com.liferay.frontend.taglib.clay.servlet.taglib.soy.BaseClayCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.portal.kernel.dao.search.ResultRow;
import com.liferay.portal.kernel.dao.search.RowChecker;

import java.util.List;
import java.util.Map;

/**
 * @author Julien Castelain
 */
public class BaseClayCardTag extends BaseClayTag {

	@Override
	public int doStartTag() {
		setHydrate(true);
		setModuleBaseName("card");

		if (_baseClayCard != null) {
			_populateContext();
		}

		return super.doStartTag();
	}

	public void setActionDropdownItems(List<DropdownItem> actionDropdownItems) {
		putValue("actionItems", actionDropdownItems);
	}

	public void setAspectRatioCssClasses(String aspectRatioCssClasses) {
		putValue("aspectRatioClasses", aspectRatioCssClasses);
	}

	public void setBaseClayCard(BaseClayCard baseClayCard) {
		_baseClayCard = baseClayCard;
	}

	public void setDisabled(Boolean disabled) {
		putValue("disabled", disabled);
	}

	public void setGroupName(String groupName) {
		putValue("groupName", groupName);
	}

	public void setHref(String href) {
		putValue("href", href);
	}

	public void setInputName(String inputName) {
		putValue("inputName", inputName);
	}

	public void setInputValue(String inputValue) {
		putValue("inputValue", inputValue);
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public void setResultRow(ResultRow resultRow) {
	}

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public void setRowChecker(RowChecker rowChecker) {
	}

	public void setSelectable(Boolean selectable) {
		putValue("selectable", selectable);
	}

	public void setSelected(Boolean selected) {
		putValue("selected", selected);
	}

	private void _populateContext() {
		Map<String, Object> context = getContext();

		if (context.get("actionItems") == null) {
			setActionDropdownItems(_baseClayCard.getActionDropdownItems());
		}

		if (context.get("componentId") == null) {
			setComponentId(_baseClayCard.getComponentId());
		}

		if (context.get("data") == null) {
			setData(_baseClayCard.getData());
		}

		if (context.get("defaultEventHandler") == null) {
			setDefaultEventHandler(_baseClayCard.getDefaultEventHandler());
		}

		if (context.get("disabled") == null) {
			setDisabled(_baseClayCard.isDisabled());
		}

		if (context.get("elementClasses") == null) {
			setElementClasses(_baseClayCard.getElementClasses());
		}

		if (context.get("groupName") == null) {
			setGroupName(_baseClayCard.getGroupName());
		}

		if (context.get("href") == null) {
			setHref(_baseClayCard.getHref());
		}

		if (context.get("id") == null) {
			setId(_baseClayCard.getId());
		}

		if (context.get("inputName") == null) {
			setInputName(_baseClayCard.getInputName());
		}

		if (context.get("inputValue") == null) {
			setInputValue(_baseClayCard.getInputValue());
		}

		if (context.get("selectable") == null) {
			setSelectable(_baseClayCard.isSelectable());
		}

		if (context.get("selected") == null) {
			setSelected(_baseClayCard.isSelected());
		}

		if (context.get("spritemap") == null) {
			setSpritemap(_baseClayCard.getSpritemap());
		}
	}

	private BaseClayCard _baseClayCard;

}