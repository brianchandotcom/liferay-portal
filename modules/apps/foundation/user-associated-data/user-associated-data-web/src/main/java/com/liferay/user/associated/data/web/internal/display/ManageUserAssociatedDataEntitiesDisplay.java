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

package com.liferay.user.associated.data.web.internal.display;

import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.user.associated.data.display.UADEntityDisplay;
import com.liferay.user.associated.data.entity.UADEntity;

/**
 * @author Drew Brokke
 */
public class ManageUserAssociatedDataEntitiesDisplay {

	public ManageUserAssociatedDataEntitiesDisplay(
		UADEntityDisplay uadEntityDisplay,
		SearchContainer<UADEntity> uadEntitySearchContainer) {

		_uadEntityDisplay = uadEntityDisplay;
		_uadEntitySearchContainer = uadEntitySearchContainer;
	}

	public int getCount() {
		return _count;
	}

	public UADEntityDisplay getUADEntityDisplay() {
		return _uadEntityDisplay;
	}

	public SearchContainer<UADEntity> getUADEntitySearchContainer() {
		return _uadEntitySearchContainer;
	}

	public String getUADEntitySetName() {
		return _uadEntitySetName;
	}

	public String getUADRegistryKey() {
		return _uadRegistryKey;
	}

	public void setCount(int count) {
		_count = count;
	}

	public void setUAdEntitySetName(String uadEntitySetName) {
		_uadEntitySetName = uadEntitySetName;
	}

	public void setUAdRegistryKey(String uadRegistryKey) {
		_uadRegistryKey = uadRegistryKey;
	}

	private int _count;
	private final UADEntityDisplay _uadEntityDisplay;
	private final SearchContainer<UADEntity> _uadEntitySearchContainer;
	private String _uadEntitySetName;
	private String _uadRegistryKey;

}