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

package com.liferay.depot.web.internal.item.selector.criteria.depot.group.criterion;

import com.liferay.item.selector.BaseItemSelectorCriterion;

/**
 * @author Cristina González
 */
public class DepotGroupItemSelectorCriterion extends BaseItemSelectorCriterion {

	public DepotGroupItemSelectorCriterion() {
	}

	public DepotGroupItemSelectorCriterion(boolean privateLayout) {
		_privateLayout = privateLayout;
	}

	public String getTarget() {
		return _target;
	}

	public boolean isAllowNavigation() {
		return _allowNavigation;
	}

	public boolean isIncludeFormsSite() {
		return _includeFormsSite;
	}

	public boolean isIncludeUserPersonalSite() {
		return _includeUserPersonalSite;
	}

	public boolean isPrivateLayout() {
		return _privateLayout;
	}

	public void setAllowNavigation(boolean allowNavigation) {
		_allowNavigation = allowNavigation;
	}

	public void setIncludeFormsSite(boolean includeFormsSite) {
		_includeFormsSite = includeFormsSite;
	}

	public void setIncludeUserPersonalSite(boolean includeUserPersonalSite) {
		_includeUserPersonalSite = includeUserPersonalSite;
	}

	public void setPrivateLayout(boolean privateLayout) {
		_privateLayout = privateLayout;
	}

	public void setTarget(String target) {
		_target = target;
	}

	private boolean _allowNavigation = true;
	private boolean _includeFormsSite;
	private boolean _includeUserPersonalSite;
	private boolean _privateLayout;
	private String _target;

}