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

package com.liferay.control.menu;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Julio Camarero
 */
public abstract class BaseControlMenuEntry implements ControlMenuEntry {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ControlMenuEntry)) {
			return false;
		}

		ControlMenuEntry controlMenuEntry = (ControlMenuEntry)obj;

		if (Validator.equals(getKey(), controlMenuEntry.getKey())) {
			return true;
		}

		return false;
	}

	@Override
	public Map<String, Object> getData(HttpServletRequest request) {
		return new HashMap<>();
	}

	@Override
	public String getIconCssClass(HttpServletRequest request) {
		return StringPool.BLANK;
	}

	@Override
	public String getKey() {
		Class<?> clazz = getClass();

		return clazz.getName();
	}

	@Override
	public String getLinkCssClass(HttpServletRequest request) {
		return StringPool.BLANK;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, getKey());
	}

	@Override
	public boolean include(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		return false;
	}

	@Override
	public boolean isShow(HttpServletRequest request) throws PortalException {
		return true;
	}

	@Override
	public boolean isUseDialog() {
		return false;
	}

}