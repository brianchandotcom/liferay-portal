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

package com.liferay.frontend.taglib.servlet.taglib;

import com.liferay.frontend.taglib.servlet.taglib.util.AddMenuKeys;

import java.util.Map;

/**
 * @author Ambrín Chaudhary
 */
public class AddMenuItem extends MenuItem {

	public AddMenuItem(
		Map<String, Object> anchorData, String id, String label,
		AddMenuKeys.AddMenuType addMenuType, String url) {

		super(anchorData, id, label, url);

		_addMenuType = addMenuType;
	}

	public AddMenuItem(
		Map<String, Object> anchorData, String id, String label, String url) {

		super(anchorData, id, label, url);

		_addMenuType = AddMenuKeys.AddMenuType.DEFAULT;
	}

	public AddMenuItem(
		Map<String, Object> anchorData, String cssClass, String id,
		String label, AddMenuKeys.AddMenuType addMenuType, String url) {

		super(anchorData, cssClass, id, label, url);

		_addMenuType = addMenuType;
	}

	public AddMenuItem(
		Map<String, Object> anchorData, String cssClass, String id,
		String label, String url) {

		super(anchorData, cssClass, id, label, url);

		_addMenuType = AddMenuKeys.AddMenuType.DEFAULT;
	}

	public AddMenuItem(String label, String url) {
		super(label, url);

		_addMenuType = AddMenuKeys.AddMenuType.DEFAULT;
	}

	public AddMenuItem(String id, String label, String url) {
		super(id, label, url);

		_addMenuType = AddMenuKeys.AddMenuType.DEFAULT;
	}

	public AddMenuItem(String cssClass, String id, String label, String url) {
		super(cssClass, id, label, url);

		_addMenuType = AddMenuKeys.AddMenuType.DEFAULT;
	}

	public AddMenuKeys.AddMenuType getType() {
		return _addMenuType;
	}

	public void setType(AddMenuKeys.AddMenuType addMenuType) {
		_addMenuType = addMenuType;
	}

	private AddMenuKeys.AddMenuType _addMenuType;

}