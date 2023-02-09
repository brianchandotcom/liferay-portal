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

import com.liferay.frontend.taglib.clay.internal.servlet.taglib.BaseContainerTag;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.TabsItem;
import com.liferay.petra.string.StringPool;

import java.util.List;
import java.util.Set;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTag;

/**
 * @author Carlos Lancha
 */
public class TabsPanelTag extends BaseContainerTag implements BodyTag {

	@Override
	public int doStartTag() throws JspException {
		_tabsTag = (TabsTag)findAncestorWithClass(this, TabsTag.class);

		if (_tabsTag == null) {
			throw new JspException();
		}

		_tabsTag.setPanelsCount(_tabsTag.getPanelsCount() + 1);

		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		setDynamicAttribute(StringPool.BLANK, "role", "tabpanel");
		setDynamicAttribute(StringPool.BLANK, "tabindex", "0");

		return super.doStartTag();
	}

	@Override
	protected String processCssClasses(Set<String> cssClasses) {
		cssClasses.add("tab-pane");

		if (_isActive()) {
			cssClasses.add("active");
			cssClasses.add("show");
		}

		if (_tabsTag.isFade()) {
			cssClasses.add("fade");
		}

		return super.processCssClasses(cssClasses);
	}

	private boolean _isActive() {
		List<TabsItem> tabsItems = _tabsTag.getTabsItems();

		TabsItem tabsItem = tabsItems.get(_tabsTag.getPanelsCount() - 1);

		Boolean active = (Boolean)tabsItem.get("active");

		if ((active != null) && active) {
			return true;
		}

		return false;
	}

	private static final String _ATTRIBUTE_NAMESPACE = "clay:tabs:panel:";

	private TabsTag _tabsTag;

}